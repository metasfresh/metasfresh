/*
 * #%L
 * metasfresh-vatid-base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.vatid;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.vatid.VATaxIdCheckTargetRepo.CheckTarget;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Checks a whole selection of VAT-IDs: target selection, deterministic ordering, {@code MaxChecksPerRun}
 * throttling, and pending/checked reporting. Every actual check is delegated to
 * {@link VATaxIDCheckService#check(VATaxIDCheckRequest)}; this class owns only the loop around it. Shared
 * by the manual process and any future {@code AD_Scheduler} entry.
 *
 * <p>Invariants to know before changing anything here:
 * <ul>
 * <li>Selecting a partner also covers its locations — the window only offers {@code C_BPartner} records,
 * so a selected partner contributes its header plus every VAT-ID-carrying location of that partner.</li>
 * <li>{@code MaxChecksPerRun} bounds the <b>combined</b> partner+location work, not each type separately;
 * empty or {@code <= 0} means no limit. Untouched targets are reported pending so a re-run picks them up.</li>
 * <li><b>Starvation guard.</b> {@code VATaxIDCheckedAt} advances only on a completed check, so a target
 * that can never complete one would head every future nightly run forever. {@code VATaxIDLastAttemptedAt}
 * is therefore stamped unconditionally, in its own already-committed transaction, strictly before
 * {@link #checkOneInOwnTrx}; the nightly run sorts by it (nulls first). This bounds crowding-out, not
 * retry frequency.</li>
 * <li>Each target runs in its own new transaction, so one target's failure cannot affect another. The
 * point is independent commit per target, not escaping an ambient transaction.</li>
 * <li>A {@link VATaxIDCheckRequestRejectedException} — the service rejecting the REQUEST because this
 * system is misconfigured — aborts the whole loop instead of logging a per-target failure, which would
 * otherwise repeat the identical error for every target in the selection.</li>
 * <li>The availability pre-filter ({@code GET /check-status}) is asked once per distinct organisation, not
 * per VAT-ID. Skipped targets keep their stored status, never reach
 * {@link VATaxIDConfig#getOnServiceUnavailable()} (which would mass-mark a country {@code Invalid} during
 * an outage under a fail-closed policy), and do not count toward
 * {@link VATaxIDMassCheckResult#getPendingCount()}.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class VATaxIDMassCheckService
{
	private static final Logger logger = LogManager.getLogger(VATaxIDMassCheckService.class);

	@NonNull private final VATaxIDCheckService checkService;
	@NonNull private final VATaxIdCheckTargetRepo checkTargetRepo;
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Runs {@code request}'s combined partner+location selection, per the class javadoc.
	 *
	 * @return how many targets were checked and how many were left pending because of
	 * {@link VATaxIDMassCheckRequest#getMaxChecksPerRun()}.
	 */
	@NonNull
	public VATaxIDMassCheckResult run(@NonNull final VATaxIDMassCheckRequest request)
	{
		if (request.isNightlyRun())
		{
			return runNightly(request);
		}

		final int maxChecksPerRun = request.getMaxChecksPerRun();
		final int budget = maxChecksPerRun <= 0 ? Integer.MAX_VALUE : maxChecksPerRun;
		// Counted up front from the SAME queries the iterator streams, exactly as runNightly does: streaming
		// cannot know the eligible total the old materialised path reported against, so pending/checked report
		// against this count-query total instead. Unlike the old eligibleTargets.size() it is taken BEFORE the
		// availability pre-filter, so an unavailable-country target counts as pending -- again matching runNightly.
		final int dueCount = checkTargetRepo.countSelectedTargets(request.getSelectedBPartnersQuery());

		final Map<OrgId, ImmutableSet<String>> unavailableCountryCodesByOrg = new HashMap<>();
		// One line per skipped member state, emitted at the end: the streaming counterpart of what
		// skipUnavailableMemberStates logged on the old materialised path. Counts only, never the targets, so it
		// stays O(#countries) in memory however large the selection.
		final Map<String, Integer> skippedCountByCountryCode = new LinkedHashMap<>();

		int checkedCount = 0;
		boolean aborted = false;
		// The selection path skips a blank VAT-ID SILENTLY -- here a record without a VAT-ID is the ordinary
		// case, unlike the nightly path where the query cannot filter blanks and each one is a data defect worth
		// a log line. Hence the no-op callback.
		final Iterator<CheckTarget> targets = checkTargetRepo.iterateSelectedTargets(
				request.getSelectedBPartnersQuery(), logLabel -> {});

		// The budget and abort tests MUST stay BEFORE hasNext(), exactly as runNightly documents: the two grains
		// of iterateSelectedTargets concatenate LAZILY, so asking it for one more element once the partner grain
		// is spent is what creates and runs the C_BPartner_Location subquery. A run out of budget must stop
		// without firing it.
		while (checkedCount < budget && !aborted && targets.hasNext())
		{
			final CheckTarget target = targets.next();
			final CheckOutcome outcome = checkOneIfAvailable(request.getPinstanceId(), target, unavailableCountryCodesByOrg);
			if (outcome == CheckOutcome.SKIPPED)
			{
				// Read straight off the target: the availability DECISION stays inside checkOneIfAvailable, this
				// only tallies which member state it skipped so the operator-facing summary survives converging
				// onto it.
				skippedCountByCountryCode.merge(target.getVataxID().getCountryCodePrefix(), 1, Integer::sum);
			}
			checkedCount += outcome.isChecked() ? 1 : 0;
			aborted = outcome.isAborted();
		}

		// Preserved from the old skipUnavailableMemberStates: one summary line per skipped member state rather
		// than one per skipped VAT-ID. The abort line itself, if any, was already emitted by checkOneIfAvailable.
		skippedCountByCountryCode.forEach((countryCode, skippedCount) ->
				Loggables.addLog("VAT-ID check: member state {} reports itself unavailable, skipped {} VAT-IDs",
						countryCode, skippedCount));

		final int pendingCount = Math.max(0, dueCount - checkedCount);
		if (pendingCount > 0)
		{
			Loggables.addLog("VAT-ID check: checked {} of {} selected, MaxChecksPerRun={}, pendingCount={}",
					checkedCount, dueCount, maxChecksPerRun, pendingCount);
		}

		final VATaxIDCheckCallStats callStats = reportCallStats(request.getPinstanceId());

		return VATaxIDMassCheckResult.builder()
				.checkedCount(checkedCount)
				.pendingCount(pendingCount)
				.callCount(callStats.getCallCount())
				.averageResponseTimeMillis(callStats.getAverageResponseTimeMillis())
				.build();
	}

	/**
	 * The nightly sweep. Uses {@link VATaxIDMassCheckRequest#getMaxChecksPerRun()}  and ignores {@link VATaxIDMassCheckRequest#getSelectedBPartnersQuery()}.
	 *
	 * <p>Due-ness and ordering are the query's ({@code IBPartnerDAO}), evaluated per organisation with that
	 * organisation's own recheck window — organisations with the check switched off are never queried at
	 * all. The two grains run one after the other, headers then locations, each oldest-attempt-first; a
	 * single combined ordering across both would need a merge, and raising {@code MaxChecksPerRun} is the
	 * cheaper answer now that nothing is loaded up front.
     */
	@NonNull
	private VATaxIDMassCheckResult runNightly(@NonNull final VATaxIDMassCheckRequest request)
	{
		final Instant now = SystemTime.asInstant();
		final int maxChecksPerRun = request.getMaxChecksPerRun();
		final int budget = maxChecksPerRun <= 0 ? Integer.MAX_VALUE : maxChecksPerRun;
		final Map<OrgId, ImmutableSet<String>> unavailableCountryCodesByOrg = new HashMap<>();
		final ImmutableMap<OrgId, Integer> recheckAfterDaysByOrg = checkService.getRecheckAfterDaysByViesEnabledOrgId();

		int dueCount = 0;
		int checkedCount = 0;
		boolean aborted = false;

		for (final Map.Entry<OrgId, Integer> entry : recheckAfterDaysByOrg.entrySet())
		{
			final OrgId orgId = entry.getKey();
			final Instant lastCheckedBefore = entry.getValue() <= 0 ? null : now.minus(Duration.ofDays(entry.getValue()));

			dueCount += checkTargetRepo.countBPartnersDueForVATaxIDCheck(orgId, lastCheckedBefore)
					+ checkTargetRepo.countBPartnerLocationsDueForVATaxIDCheck(orgId, lastCheckedBefore);

			if (aborted || checkedCount >= budget)
			{
				break;
			}

			final Iterator<CheckTarget> dueTargets = checkTargetRepo.iterateTargetsDueForVATaxIDCheck(
					orgId, lastCheckedBefore, maxChecksPerRun, VATaxIDMassCheckService::logBlankVATaxIDSkipped);

			// The budget and abort tests MUST stay BEFORE hasNext(), however reorderable they look: && is
			// evaluated left to right, and dueTargets concatenates its two grains LAZILY -- asking it for one
			// more element once the partner grain is spent is exactly what creates and runs the
			// C_BPartner_Location query. With hasNext() first, a run that has already spent its budget on
			// partners would issue that query only to throw its result away.
			while (checkedCount < budget && !aborted && dueTargets.hasNext())
			{
				final CheckOutcome outcome = checkOneIfAvailable(request.getPinstanceId(), dueTargets.next(), unavailableCountryCodesByOrg);
				checkedCount += outcome.isChecked() ? 1 : 0;
				aborted = outcome.isAborted();
			}
		}

		final int pendingCount = Math.max(0, dueCount - checkedCount);
		if (pendingCount > 0)
		{
			Loggables.addLog("VAT-ID check: checked {} of {} due, MaxChecksPerRun={}, pendingCount={}",
					checkedCount, dueCount, maxChecksPerRun, pendingCount);
		}

		final VATaxIDCheckCallStats callStats = reportCallStats(request.getPinstanceId());
		return VATaxIDMassCheckResult.builder()
				.checkedCount(checkedCount)
				.pendingCount(pendingCount)
				.callCount(callStats.getCallCount())
				.averageResponseTimeMillis(callStats.getAverageResponseTimeMillis())
				.build();
	}

	/**
	 * Checks one streamed target unless its member state reports itself unavailable, in which case it keeps
	 * its stored status and does not consume budget. The single availability-aware step both {@link #run}'s
	 * selection loop and {@link #runNightly} converge onto.
	 */
	@NonNull
	private CheckOutcome checkOneIfAvailable(
			@Nullable final PInstanceId pinstanceId,
			@NonNull final CheckTarget checkTarget,
			@NonNull final Map<OrgId, ImmutableSet<String>> unavailableCountryCodesByOrg)
	{
		final ImmutableSet<String> unavailableCountryCodes = unavailableCountryCodesByOrg
				.computeIfAbsent(checkTarget.getOrgId(), checkService::getUnavailableCountryCodes);
		if (unavailableCountryCodes.contains(checkTarget.getVataxID().getCountryCodePrefix()))
		{
			return CheckOutcome.SKIPPED;
		}

		try
		{
			checkOneInOwnTrx(pinstanceId, checkTarget);
		}
		catch (final VATaxIDCheckRequestRejectedException ex)
		{
			// Same rule as the selection path: a rejected REQUEST is our own misconfiguration, so every
			// remaining target would hit it too. Stop, and say so once.
			Loggables.withWarnLoggerToo(logger).addLog(
					"VAT-ID check run ABORTED at {}: {} Correct the VAT-ID configuration, then start the run again.",
					checkTarget.getLogLabel(), ex.getMessage());
			return CheckOutcome.ABORTED;
		}
		return CheckOutcome.CHECKED;
	}

	/**
	 * Reports one record the nightly run had to skip. Only the nightly path calls this: its query cannot
	 * filter blanks out (the partial index serving it is predicated on {@code VATaxID IS NOT NULL} alone), so
	 * the record is a data defect an operator has to fix rather than a normal "no VAT-ID" record.
	 */
	private static void logBlankVATaxIDSkipped(@NonNull final String logLabel)
	{
		Loggables.withWarnLoggerToo(logger).addLog(
				"VAT-ID check: skipped {} -- its blank VAT-ID cannot be checked. Clear the column or enter a "
						+ "valid VAT-ID.", logLabel);
	}

	@Getter
	@RequiredArgsConstructor
	private enum CheckOutcome
	{
		CHECKED(true, false),
		SKIPPED(false, false),
		ABORTED(false, true);

		private final boolean checked;
		private final boolean aborted;
	}

	/**
	 * Logs, and returns, this run's "calls made, and average response time" summary line. Reported even at
	 * {@code 0} calls — a fully de-duplicated selection making no calls shows the de-duplication window
	 * working, not the run having done nothing. No pinstance (unit test, REST-triggered run outside any
	 * process) means nothing to attribute log rows to, so it is reported as zero rather than queried.
	 */
	@NonNull
	private VATaxIDCheckCallStats reportCallStats(@Nullable final PInstanceId pinstanceId)
	{
		if (pinstanceId == null)
		{
			return VATaxIDCheckCallStats.builder().callCount(0).averageResponseTimeMillis(0).build();
		}

		final VATaxIDCheckCallStats callStats = checkService.getCallStatsForRun(pinstanceId);
		Loggables.addLog("VAT-ID check: calls={}, averageResponseTimeMillis={}",
				callStats.getCallCount(), callStats.getAverageResponseTimeMillis());
		return callStats;
	}





	/**
	 * Runs one target's attempt stamp and its check.
	 *
	 * <p>The attempt stamp is its own already-committed transaction, written before the check starts
	 * ({@link #stampAttemptInOwnTrx}); a write sharing the check's transaction would be erased by exactly the
	 * rollback it exists to survive.
	 *
	 * <p>The check is wrapped in {@code callInNewTrx} — normally a hack, per
	 * {@code docs/coding-rules/java-general.md} — because each target's whole unit of work must commit or
	 * fail independently of every other target's. That unit includes the order-line-tax refresh a status
	 * change triggers: {@link VATaxIDCheckService} performs it inside the check, joining this transaction
	 * rather than opening its own, so a refresh failure still rolls the check back with it.
	 *
	 * <p>Two {@code catch} blocks, not one: a stamp failure means the check was never attempted and leaves no
	 * other trace, while a failure of the check itself means the attempt is already durably recorded. One
	 * shared message would hide the rarer signal.
	 *
	 * @throws VATaxIDCheckRequestRejectedException when the checking service rejected the request itself
	 * because of how this system is configured. Deliberately NOT swallowed here, unlike every other failure:
	 * it would repeat identically for every remaining target, so {@link #run} aborts on it instead.
	 */
	private void checkOneInOwnTrx(@Nullable final PInstanceId pinstanceId, @NonNull final CheckTarget checkTarget)
	{
		try
		{
			stampAttemptInOwnTrx(checkTarget);
		}
		catch (final Exception ex)
		{
			// The stamp write itself failed -- the check-and-refresh unit below never even starts, so
			// neither VATaxIDCheckedAt nor VATaxIDLastAttemptedAt advances for this target this run (see
			// stampAttemptInOwnTrx's javadoc). Reported distinctly from an ordinary check failure (below):
			// this log line is the ONLY place this failure can surface at all.
			Loggables.withWarnLoggerToo(logger).addLog(
					"VAT-ID check attempt-stamp write failed for {}: {} -- the check for this target was NOT "
							+ "attempted this run; it will keep sorting to the front of the nightly queue and "
							+ "waste one MaxChecksPerRun slot every run until the stamp write succeeds.",
					checkTarget.getLogLabel(), ex.getMessage());
			return;
		}

		try
		{
			// Isolation from every OTHER target's transaction in this run (see the method javadoc above) —
			// each target's check-plus-refresh must commit or fail as its own independent unit.
			trxManager.callInNewTrx(() -> checkService.check(VATaxIDCheckRequest.builder()
					.bpartnerId(checkTarget.getBpartnerId())
					.bpartnerLocationId(checkTarget.getBpartnerLocationId())
					.vataxID(checkTarget.getVataxID())
					.pinstanceId(pinstanceId)
					.build()));
		}
		catch (final VATaxIDCheckRequestRejectedException ex)
		{
			// Deliberately NOT swallowed by the generic catch below. This is not this target's failure: the
			// checking service rejected the REQUEST because of our configuration, so every remaining target
			// in the selection would hit the same wall. Propagated so run() can abort and say so once.
			// (It survives callInNewTrx unwrapped because AdempiereException.wrapIfNeeded returns an
			// AdempiereException subclass as-is.)
			throw ex;
		}
		catch (final Exception ex)
		{
			// One target's failure (a throwing checker, a value the format re-check rejects, or — per the
			// message wrapping below — a refresh failure) must not abort the run for the rest of the
			// selection. Deliberately worded to not claim the check itself failed: the wrapped message below
			// already says so explicitly when that is not what happened.
			Loggables.withWarnLoggerToo(logger)
					.addLog("VAT-ID check processing failed for {}: {}", checkTarget.getLogLabel(), ex.getMessage());
		}
	}

	/**
	 * Stamps {@code checkTarget}'s {@code VATaxIDLastAttemptedAt} unconditionally, recording that an attempt
	 * is about to happen rather than that one succeeded.
	 *
	 * <p>Deliberately {@code runInNewTrx} rather than {@code runInThreadInheritedTrx} — the one genuinely
	 * required use of the hack {@code docs/coding-rules/java-general.md} otherwise warns against. Joining the
	 * check-and-refresh transaction would let a rolled-back check erase the evidence that an attempt was
	 * made, which is the whole defect this mechanism prevents.
	 *
	 * <p>A chronic failure of this write itself is an accepted residual risk: such a target sorts first every
	 * run but consumes only its own slot, so it crowds out nobody. Diagnose it from the run log rather than
	 * auto-excluding after N failures.
	 */
	private void stampAttemptInOwnTrx(@NonNull final CheckTarget checkTarget)
	{
		final Instant attemptedAt = SystemTime.asInstant();
		final BPartnerLocationId bpartnerLocationId = checkTarget.getBpartnerLocationId();

		trxManager.runInNewTrx(() -> {
			if (bpartnerLocationId != null)
			{
				checkTargetRepo.stampVATaxIDCheckAttempt(bpartnerLocationId, attemptedAt);
			}
			else
			{
				checkTargetRepo.stampVATaxIDCheckAttempt(checkTarget.getBpartnerId(), attemptedAt);
			}
		});
	}
	
}
