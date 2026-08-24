/*
 * #%L
 * de.metas.vatid
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

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

		final ImmutableList<CheckTarget> allTargets = retrieveCheckTargets(request.getSelectedBPartnerIds());
		final ImmutableList<CheckTarget> eligibleTargets = skipUnavailableMemberStates(allTargets);

		final int maxChecksPerRun = request.getMaxChecksPerRun();
		final boolean unlimited = maxChecksPerRun <= 0;
		final int checksAllowed = unlimited ? eligibleTargets.size() : Math.min(maxChecksPerRun, eligibleTargets.size());

		int checkedCount = 0;
		for (final CheckTarget checkTarget : eligibleTargets)
		{
			if (checkedCount >= checksAllowed)
			{
				break;
			}

			try
			{
				checkOneInOwnTrx(request.getPinstanceId(), checkTarget);
			}
			catch (final VATaxIDCheckRequestRejectedException ex)
			{
				// The ONE failure that must stop the run rather than be carried past: the service rejected
				// the request because of our own configuration, so every remaining target would produce the
				// identical error. Carrying on would write one warn line per target for the whole selection
				// (up to MaxChecksPerRun) and report the run as merely having had failures.
				// Named error code first: it is what the service's own documentation is indexed by.
				// The rejected target counts as ATTEMPTED and is excluded from the not-attempted tally -- it
				// was attempt-stamped and its request did reach the service, which is what "attempted" means
				// throughout this class (see the class javadoc, "Starvation guard"). Lumping it in with the
				// targets that were never started would understate how far the run got.
				// The pending figure is restated here on purpose: it is one HIGHER than the not-attempted one
				// (it includes the rejected target, which still needs a check), and the process footer reports
				// it separately. Left unreconciled, the two numbers read as a contradiction in the same log.
				Loggables.withWarnLoggerToo(logger).addLog(
						"VAT-ID check run ABORTED after {} of {} targets: {} No VAT-ID status was changed by the "
								+ "rejected request, and the remaining {} targets were not attempted. {} of {} "
								+ "remain pending, the rejected target included. Correct the VAT-ID configuration, "
								+ "then start the run again.",
						checkedCount, eligibleTargets.size(), ex.getMessage(),
						eligibleTargets.size() - checkedCount - 1,
						eligibleTargets.size() - checkedCount, eligibleTargets.size());
				break;
			}
			checkedCount++;
		}

		// Reported even when the run aborted -- the targets it did get through are exactly what a re-run after
		// the fix must NOT be assumed to still need. On an abort this deliberately counts the REJECTED target
		// as pending as well, unlike the not-attempted tally logged above: nothing advanced its
		// VATaxIDCheckedAt, so it genuinely still needs a check. "Attempted" and "still needs checking" are
		// different questions, and this one is the second.
		final int pendingCount = eligibleTargets.size() - checkedCount;
		if (pendingCount > 0)
		{
			Loggables.addLog("VAT-ID check: checked {} of {} selected, MaxChecksPerRun={}, pendingCount={}",
					checkedCount, eligibleTargets.size(), maxChecksPerRun, pendingCount);
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
	 * The nightly sweep. Streams the due records instead of materialising them: the selection is the whole
	 * VAT-ID-bearing population, which on a real installation is tens of thousands of records, and the run
	 * only ever checks {@code MaxChecksPerRun} of them.
	 *
	 * <p>Due-ness and ordering are the query's ({@code IBPartnerDAO}), evaluated per organisation with that
	 * organisation's own recheck window — organisations with the check switched off are never queried at
	 * all. The two grains run one after the other, headers then locations, each oldest-attempt-first; a
	 * single combined ordering across both would need a merge, and raising {@code MaxChecksPerRun} is the
	 * cheaper answer now that nothing is loaded up front.
	 *
	 * <p>{@code pendingCount} is counted, not derived: the run stops reading once its budget is spent, so
	 * the number of targets still due can only come from a separate {@code count}. Unlike the selection
	 * path's figure it does not subtract targets whose member state is unavailable — no query can know
	 * that, since it is a live answer from the checking service.
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
			final Instant staleBefore = entry.getValue() <= 0 ? null : now.minus(Duration.ofDays(entry.getValue()));

			dueCount += bpartnerDAO.countBPartnersDueForVATaxIDCheck(orgId, staleBefore)
					+ bpartnerDAO.countBPartnerLocationsDueForVATaxIDCheck(orgId, staleBefore);

			if (aborted || checkedCount >= budget)
			{
				continue;
			}

			final Iterator<I_C_BPartner> partners = bpartnerDAO.iterateBPartnersDueForVATaxIDCheck(orgId, staleBefore);
			while (partners.hasNext() && checkedCount < budget && !aborted)
			{
				final I_C_BPartner bpartnerRecord = partners.next();
				final CheckTarget checkTarget = CheckTarget.ofPartner(
						BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()), bpartnerRecord);
				final CheckOutcome outcome = checkOneIfAvailable(request.getPinstanceId(), checkTarget, unavailableCountryCodesByOrg);
				checkedCount += outcome.isChecked() ? 1 : 0;
				aborted = outcome.isAborted();
			}

			final Iterator<I_C_BPartner_Location> locations = bpartnerDAO.iterateBPartnerLocationsDueForVATaxIDCheck(orgId, staleBefore);
			while (locations.hasNext() && checkedCount < budget && !aborted)
			{
				final CheckTarget checkTarget = CheckTarget.ofLocation(locations.next());
				final CheckOutcome outcome = checkOneIfAvailable(request.getPinstanceId(), checkTarget, unavailableCountryCodesByOrg);
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
	 * its stored status and does not consume budget. Mirrors what {@link #skipUnavailableMemberStates} and
	 * {@link #run}'s loop do together on the selection path.
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
	 * Availability pre-filter (see the class javadoc): removes every target whose member state
	 * {@link VATaxIDCheckService#getUnavailableCountryCodes(OrgId)} currently reports as unavailable,
	 * logging one summary line per skipped member state rather than one per skipped VAT-ID — the same
	 * "a handful of meaningful lines, not thousands" reasoning as the per-record status-change log.
	 * {@link #checkService} is asked at most once per distinct {@link CheckTarget#getOrgId()} in
	 * {@code checkTargets}, not once per target.
	 */
	@NonNull
	private ImmutableList<CheckTarget> skipUnavailableMemberStates(@NonNull final ImmutableList<CheckTarget> checkTargets)
	{
		final Map<OrgId, ImmutableSet<String>> unavailableCountryCodesByOrg = new HashMap<>();
		final ImmutableListMultimap.Builder<String, CheckTarget> skippedByCountryCode = ImmutableListMultimap.builder();
		final ImmutableList.Builder<CheckTarget> eligibleTargets = ImmutableList.builder();

		for (final CheckTarget checkTarget : checkTargets)
		{
			final ImmutableSet<String> unavailableCountryCodes = unavailableCountryCodesByOrg
					.computeIfAbsent(checkTarget.getOrgId(), checkService::getUnavailableCountryCodes);
			final String countryCode = checkTarget.getVataxID().getCountryCodePrefix();

			if (unavailableCountryCodes.contains(countryCode))
			{
				skippedByCountryCode.put(countryCode, checkTarget);
			}
			else
			{
				eligibleTargets.add(checkTarget);
			}
		}

		skippedByCountryCode.build().asMap().forEach((countryCode, skippedTargets) ->
				Loggables.addLog("VAT-ID check: member state {} reports itself unavailable, skipped {} VAT-IDs",
						countryCode, skippedTargets.size()));

		return eligibleTargets.build();
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
	 * @return the distinct {@code C_BPartner_ID}s tonight's sweep would touch, header- or location-due.
	 * Not used by {@link #run}, which streams instead of materialising ids — this exists so a test can
	 * assert what the selection contains without running the checks.
	 */
	@VisibleForTesting
	@NonNull
	public ImmutableList<BPartnerId> retrieveNightlyDueBPartnerIds()
	{
		final Instant now = SystemTime.asInstant();
		final LinkedHashSet<BPartnerId> bpartnerIds = new LinkedHashSet<>();

		checkService.getRecheckAfterDaysByViesEnabledOrgId().forEach((orgId, recheckAfterDays) -> {
			final Instant staleBefore = recheckAfterDays <= 0 ? null : now.minus(Duration.ofDays(recheckAfterDays));
			bpartnerDAO.iterateBPartnersDueForVATaxIDCheck(orgId, staleBefore)
					.forEachRemaining(record -> bpartnerIds.add(BPartnerId.ofRepoId(record.getC_BPartner_ID())));
			bpartnerDAO.iterateBPartnerLocationsDueForVATaxIDCheck(orgId, staleBefore)
					.forEachRemaining(record -> bpartnerIds.add(BPartnerId.ofRepoId(record.getC_BPartner_ID())));
		});

		return ImmutableList.copyOf(bpartnerIds);
	}



	@NonNull
	private static VATaxIDStatus resolveStatus(@Nullable final String statusCode)
	{
		return VATaxIDStatus.optionalOfNullableCode(statusCode).orElse(VATaxIDStatus.NotChecked);
	}

	@Nullable
	private static Instant toInstantOrNull(@Nullable final Timestamp timestamp)
	{
		return timestamp != null ? timestamp.toInstant() : null;
	}

	/**
	 * @return every VAT-ID to check for {@code selectedBPartnerIds}: the header of every one of them that
	 * carries one, plus every {@code C_BPartner_Location} of every one of them that carries one —
	 * regardless of whether that location's own partner header has a VAT-ID (see the class javadoc,
	 * "Selecting a partner also covers its locations").
	 *
	 * <p>Ordered per {@code selectedBPartnerIds}'s own order and, within one partner, the header before
	 * that partner's locations (themselves ordered by {@code C_BPartner_Location_ID}) — see the class
	 * javadoc, "Ordering". A partner with neither its own VAT-ID nor any location VAT-ID contributes
	 * nothing and is not counted towards either the checked or the pending count.
	 */
	@NonNull
	private ImmutableList<CheckTarget> retrieveCheckTargets(@NonNull final ImmutableList<BPartnerId> selectedBPartnerIds)
	{
		// The persistence access to C_BPartner / C_BPartner_Location belongs on IBPartnerDAO, which already
		// owns it (getByIds, retrieveBPartnerLocationsWithVATaxID, ...) — this service must not build its
		// own IQueryBL query for another module's table (docs/REVIEW.md).
		final ImmutableMap<BPartnerId, I_C_BPartner> selectedBPartnersById = bpartnerDAO.getByIds(selectedBPartnerIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						bpartnerRecord -> BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()),
						Function.identity()));

		final ImmutableListMultimap<BPartnerId, I_C_BPartner_Location> locationsByBPartnerId = bpartnerDAO
				.retrieveBPartnerLocationsWithVATaxID(selectedBPartnerIds)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						locationRecord -> BPartnerId.ofRepoId(locationRecord.getC_BPartner_ID()),
						Function.identity()));

		final ImmutableList.Builder<CheckTarget> checkTargets = ImmutableList.builder();
		for (final BPartnerId bpartnerId : selectedBPartnerIds)
		{
			final I_C_BPartner bpartnerRecord = selectedBPartnersById.get(bpartnerId);
			if (bpartnerRecord == null)
			{
				// Selected by the caller but no longer resolvable by the time this run fetched it (e.g. the
				// record was deleted in between) — nothing to check for it, and skipping is the only way to
				// avoid an NPE from a null bpartnerRecord below.
				continue;
			}

			if (!Check.isEmpty(bpartnerRecord.getVATaxID()))
			{
				checkTargets.add(CheckTarget.ofPartner(bpartnerId, bpartnerRecord));
			}

			for (final I_C_BPartner_Location bpartnerLocationRecord : locationsByBPartnerId.get(bpartnerId))
			{
				checkTargets.add(CheckTarget.ofLocation(bpartnerLocationRecord));
			}
		}
		return checkTargets.build();
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
				bpartnerDAO.stampVATaxIDCheckAttempt(bpartnerLocationId, attemptedAt);
			}
			else
			{
				bpartnerDAO.stampVATaxIDCheckAttempt(checkTarget.getBpartnerId(), attemptedAt);
			}
		});
	}

	/**
	 * One VAT-ID to check: either a partner header ({@link #bpartnerLocationId} {@code null}) or one of its
	 * locations. Carries everything {@link #checkOneInOwnTrx} needs so that method no longer has to branch on
	 * which table the record came from — the branch happens once, when a {@code CheckTarget} is built from
	 * the underlying {@code I_C_BPartner} / {@code I_C_BPartner_Location} record.
	 */
	@Value
	@Builder
	private static class CheckTarget
	{
		@NonNull BPartnerId bpartnerId;

		@Nullable BPartnerLocationId bpartnerLocationId;

		@NonNull VATIdentifier vataxID;

		@NonNull VATaxIDStatus previousStatus;

		/**
		 * When this record's status was last successfully determined — {@code null} if it never was. See
		 * the class javadoc, "Starvation guard": this is DIFFERENT from {@link #lastAttemptedAt}, which
		 * advances on every attempt regardless of outcome.
		 */
		@Nullable Instant checkedAt;

		/**
		 * When this record's check was last ATTEMPTED, regardless of outcome — {@code null} if it never
		 * was. Used by the nightly query's ordering (oldest attempt first, never-attempted first) to
		 * run's targets; unrelated to {@link #checkedAt}. See the class javadoc, "Starvation guard".
		 */
		@Nullable Instant lastAttemptedAt;

		@NonNull String logLabel;

		/**
		 * The organisation whose {@link VATaxIDConfig} governs this target's check: the record's OWN
		 * {@code AD_Org_ID} — the location's for a location target, not its parent partner's. Same
		 * organisation {@code VATaxIDCheckTrigger} gates the enqueue on and
		 * {@code VATaxIDParentStatusRepository} resolves at processing time; the three must agree or one
		 * gate answers a different question from the next.
		 */
		@NonNull OrgId orgId;

		@NonNull
		private static CheckTarget ofPartner(@NonNull final BPartnerId bpartnerId, @NonNull final I_C_BPartner bpartnerRecord)
		{
			return CheckTarget.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(null)
					.vataxID(VATIdentifier.of(bpartnerRecord.getVATaxID()))
					.previousStatus(resolveStatus(bpartnerRecord.getVATaxIDStatus()))
					.checkedAt(toInstantOrNull(bpartnerRecord.getVATaxIDCheckedAt()))
					.lastAttemptedAt(toInstantOrNull(bpartnerRecord.getVATaxIDLastAttemptedAt()))
					.logLabel("C_BPartner_ID=" + bpartnerRecord.getC_BPartner_ID())
					.orgId(OrgId.ofRepoId(bpartnerRecord.getAD_Org_ID()))
					.build();
		}

		@NonNull
		private static CheckTarget ofLocation(@NonNull final I_C_BPartner_Location bpartnerLocationRecord)
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID());
			return CheckTarget.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationRecord.getC_BPartner_Location_ID()))
					.vataxID(VATIdentifier.of(bpartnerLocationRecord.getVATaxID()))
					.previousStatus(resolveStatus(bpartnerLocationRecord.getVATaxIDStatus()))
					.checkedAt(toInstantOrNull(bpartnerLocationRecord.getVATaxIDCheckedAt()))
					.lastAttemptedAt(toInstantOrNull(bpartnerLocationRecord.getVATaxIDLastAttemptedAt()))
					.logLabel("C_BPartner_ID=" + bpartnerLocationRecord.getC_BPartner_ID()
							+ ", C_BPartner_Location_ID=" + bpartnerLocationRecord.getC_BPartner_Location_ID())
					.orgId(OrgId.ofRepoId(bpartnerLocationRecord.getAD_Org_ID()))
					.build();
		}
	}
}
