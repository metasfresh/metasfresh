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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Runs a whole {@link VATaxIDCheckRunRequest}: target selection, deterministic ordering,
 * {@code MaxChecksPerRun} throttling, the per-target check, and the pending/checked reporting. Every
 * actual check is delegated to {@link VATaxIDCheckService#check(VATaxIDCheckRequest)}; this class owns
 * only the looping around it. Shared by the manual process and any future {@code AD_Scheduler} entry.
 *
 * <p><b>Selecting a partner also covers its locations</b> — the window only lets a user select
 * {@code C_BPartner} records, so a selected partner contributes its own header plus every location of
 * that partner carrying a VAT-ID. The nightly run filters that set further.
 *
 * <p><b>{@code MaxChecksPerRun}</b> bounds the <em>combined</em> partner+location work, not each type
 * separately; empty or {@code <= 0} means no limit. Untouched targets are reported as pending so a re-run
 * picks them up.
 *
 * <p><b>Starvation guard.</b> {@code VATaxIDCheckedAt} advances only on a completed check, so a target
 * that can never complete one would sort first of every future nightly run forever and occupy the whole
 * budget. {@code VATaxIDLastAttemptedAt} is therefore stamped unconditionally, in its own already-committed
 * transaction, strictly before {@link #checkOneInOwnTrx} begins; the nightly run sorts by it (nulls first,
 * see {@link #filterAndOrderForNightlyRun}), so a just-failed target sorts behind a never-attempted one.
 * This bounds crowding-out, not retry frequency — a failing target stays due and keeps being retried.
 *
 * <p><b>Ordering.</b> {@link #retrieveCheckTargets(ImmutableList)} follows the caller's id order, header
 * before that partner's locations, so a throttled manual run always processes the same prefix. The nightly
 * run deliberately supersedes this with the attempt-time ordering above.
 *
 * <p><b>Each target runs in its own new transaction</b> ({@link #checkOneInOwnTrx}), together with the
 * order-line-tax refresh a status change triggers, so one target's failure cannot affect any other — the
 * point is independent commit per target, not escaping an ambient transaction.
 *
 * <p><b>Availability pre-filter</b> ({@code GET /check-status}): asked once per distinct organisation, not
 * per VAT-ID, skipping targets whose member state is reported unavailable. A skipped target keeps its
 * stored status and never reaches {@link VATaxIDConfig#getOnServiceUnavailable()}, which would otherwise
 * mass-mark a country's partners {@code Invalid} during an outage under a fail-closed policy. Skipped
 * targets do not count toward {@link VATaxIDCheckRunResult#getPendingCount()}.
 */
@Service
@RequiredArgsConstructor
public class VATaxIDCheckRunService
{
	private static final Logger logger = LogManager.getLogger(VATaxIDCheckRunService.class);

	@NonNull private final VATaxIDCheckService checkService;
	@NonNull private final VATaxIDOrderTaxRefresher orderTaxRefresher;
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	/**
	 * Runs {@code request}'s combined partner+location selection, per the class javadoc.
	 *
	 * @return how many targets were checked and how many were left pending because of
	 * {@link VATaxIDCheckRunRequest#getMaxChecksPerRun()}.
	 */
	@NonNull
	public VATaxIDCheckRunResult run(@NonNull final VATaxIDCheckRunRequest request)
	{
		final ImmutableList<CheckTarget> allTargets = retrieveCheckTargets(request.getSelectedBPartnerIds());
		final ImmutableList<CheckTarget> checkTargets = request.isNightlyRun()
				? filterAndOrderForNightlyRun(allTargets)
				: allTargets;
		final ImmutableList<CheckTarget> eligibleTargets = skipUnavailableMemberStates(checkTargets);

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

			checkOneInOwnTrx(request.getPinstanceId(), checkTarget);
			checkedCount++;
		}

		final int pendingCount = eligibleTargets.size() - checkedCount;
		if (pendingCount > 0)
		{
			Loggables.addLog("VAT-ID check: checked {} of {} selected, MaxChecksPerRun={}, pendingCount={}",
					checkedCount, eligibleTargets.size(), maxChecksPerRun, pendingCount);
		}

		final VATaxIDCheckCallStats callStats = reportCallStats(request.getPinstanceId());

		return VATaxIDCheckRunResult.builder()
				.checkedCount(checkedCount)
				.pendingCount(pendingCount)
				.callCount(callStats.getCallCount())
				.averageResponseTimeMillis(callStats.getAverageResponseTimeMillis())
				.build();
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
	 * Logs, and returns, {@link VATaxIDCheckService#getCallStatsForRun(PInstanceId)} for this run — the
	 * "calls made, and average response time" summary line. Reported even when {@code 0} calls were made:
	 * a run over a fully de-duplicated selection making zero calls is itself useful information, since it
	 * shows the de-duplication window is doing its job rather than the run having done nothing. No
	 * pinstance (e.g. a unit test or a REST-triggered run outside any process) means nothing to attribute
	 * the log rows to — reported as zero rather than queried, since {@code Loggables} is a no-op outside a
	 * process anyway.
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
	 * The nightly schedule's selection: every {@code C_BPartner_ID} with a due header VAT-ID or at least one
	 * due location VAT-ID. This is a coarse selection surface — the real per-target throttling and ordering
	 * happens in {@link #filterAndOrderForNightlyRun}, so this list's own order is never read downstream.
	 *
	 * <p><b>Location due-ness is tested independently of header due-ness</b>, so a stale location is reachable
	 * even when its partner's header carries no VAT-ID at all or is itself still fresh.
	 *
	 * <p>Scoped to the caller's own client. {@link #isDueForNightlyRecheck} also excludes records whose
	 * organisation has the online check off entirely — they could never advance either timestamp.
	 */
	@NonNull
	public ImmutableList<BPartnerId> retrieveAllBPartnerIdsWithVATaxID()
	{
		final Instant now = SystemTime.asInstant();
		final Map<OrgId, Integer> recheckAfterDaysByOrg = new HashMap<>();

		final ImmutableList<BPartnerId> allIdsWithVATaxID = bpartnerDAO.retrieveBPartnerIdsWithVATaxID();
		final ImmutableMap<BPartnerId, I_C_BPartner> bpartnersById = allIdsWithVATaxID.isEmpty()
				? ImmutableMap.of()
				: bpartnerDAO.getByIds(allIdsWithVATaxID).stream()
						.collect(ImmutableMap.toImmutableMap(
								bpartnerRecord -> BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()),
								Function.identity()));

		// Header-due ids, in the DAO's own C_BPartner_ID order, secondary-sorted by the header's own
		// VATaxIDLastAttemptedAt (nulls first) -- see the class javadoc, "Attempt vs success".
		final ImmutableList<BPartnerId> dueHeaderIds = allIdsWithVATaxID.stream()
				.map(bpartnersById::get)
				.filter(Objects::nonNull)
				.filter(bpartnerRecord -> isDueForNightlyRecheck(
						OrgId.ofRepoId(bpartnerRecord.getAD_Org_ID()),
						resolveStatus(bpartnerRecord.getVATaxIDStatus()),
						toInstantOrNull(bpartnerRecord.getVATaxIDCheckedAt()),
						now,
						recheckAfterDaysByOrg))
				.sorted(Comparator.comparing(
						(final I_C_BPartner bpartnerRecord) -> toInstantOrNull(bpartnerRecord.getVATaxIDLastAttemptedAt()),
						Comparator.nullsFirst(Comparator.naturalOrder())))
				.map(bpartnerRecord -> BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
				.collect(ImmutableList.toImmutableList());

		// Partners reached ONLY through a due location -- their header may carry no VAT-ID at all, or a
		// fresh one; either way the location's own staleness must still make the partner visible here.
		final Set<BPartnerId> idsWithDueLocation = bpartnerDAO.retrieveBPartnerLocationsWithVATaxID().stream()
				.filter(locationRecord -> isDueForNightlyRecheck(
						OrgId.ofRepoId(locationRecord.getAD_Org_ID()),
						resolveStatus(locationRecord.getVATaxIDStatus()),
						toInstantOrNull(locationRecord.getVATaxIDCheckedAt()),
						now,
						recheckAfterDaysByOrg))
				.map(locationRecord -> BPartnerId.ofRepoId(locationRecord.getC_BPartner_ID()))
				.collect(Collectors.toCollection(LinkedHashSet::new));

		final LinkedHashSet<BPartnerId> union = new LinkedHashSet<>(dueHeaderIds);
		idsWithDueLocation.forEach(union::add);

		return ImmutableList.copyOf(union);
	}

	/**
	 * @return whether a record is due for the nightly run: its organisation has the online check on, and it is
	 * either never checked or past that organisation's {@code RecheckAfterDays}. Shared by the header
	 * selection, the location selection and {@link #filterAndOrderForNightlyRun}, so the three cannot drift
	 * apart on what "due" means.
	 *
	 * <p>A coarse pre-filter over the same window {@link VATaxIDCheckService#check} applies, not a competing
	 * one — {@code check} still runs its own authoritative de-duplication. The VIES-enabled test
	 * short-circuits even the never-checked case: {@code check} does nothing for such an organisation, so
	 * reporting it due would starve the nightly budget.
	 */
	private boolean isDueForNightlyRecheck(
			@NonNull final OrgId orgId,
			@NonNull final VATaxIDStatus status,
			@Nullable final Instant checkedAt,
			@NonNull final Instant now,
			@NonNull final Map<OrgId, Integer> recheckAfterDaysByOrg)
	{
		if (!checkService.isViesCheckEnabled(orgId))
		{
			return false;
		}

		if (status == VATaxIDStatus.NotChecked)
		{
			return true;
		}

		if (checkedAt == null)
		{
			return true;
		}

		final int recheckAfterDays = recheckAfterDaysByOrg.computeIfAbsent(orgId, checkService::getRecheckAfterDays);
		if (recheckAfterDays <= 0)
		{
			return true;
		}

		return checkedAt.isBefore(now.minus(Duration.ofDays(recheckAfterDays)));
	}

	/**
	 * Filters {@code checkTargets} to those due for a nightly run, applying {@link #isDueForNightlyRecheck} at
	 * the same grain the caller selected (header or location), and re-orders the survivors by
	 * {@link CheckTarget#getLastAttemptedAt()}, nulls first.
	 *
	 * <p>Matching filter grain to expansion grain is the point: {@link #retrieveCheckTargets(ImmutableList)}'s
	 * unconditional per-partner expansion is right for a manual run, but for the nightly run it would burn the
	 * budget re-checking already-fresh locations. Applied only when
	 * {@link VATaxIDCheckRunRequest#isNightlyRun()}.
	 */
	@NonNull
	private ImmutableList<CheckTarget> filterAndOrderForNightlyRun(@NonNull final ImmutableList<CheckTarget> checkTargets)
	{
		final Instant now = SystemTime.asInstant();
		final Map<OrgId, Integer> recheckAfterDaysByOrg = new HashMap<>();

		return checkTargets.stream()
				.filter(checkTarget -> isDueForNightlyRecheck(
						checkTarget.getOrgId(), checkTarget.getPreviousStatus(), checkTarget.getCheckedAt(), now, recheckAfterDaysByOrg))
				.sorted(Comparator.comparing(CheckTarget::getLastAttemptedAt, Comparator.nullsFirst(Comparator.naturalOrder())))
				.collect(ImmutableList.toImmutableList());
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
	 * javadoc, "Deterministic ordering under throttling". A partner with neither its own VAT-ID nor any
	 * location VAT-ID contributes nothing and is not counted towards either the checked or the pending
	 * count.
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
				checkTargets.add(CheckTarget.ofLocation(bpartnerRecord, bpartnerLocationRecord));
			}
		}
		return checkTargets.build();
	}

	/**
	 * Runs one target's attempt stamp, its check, and the order-line-tax refresh a status change triggers.
	 *
	 * <p>The attempt stamp is its own already-committed transaction, written before the check-and-refresh
	 * unit starts ({@link #stampAttemptInOwnTrx}); a write sharing that unit's transaction would be erased by
	 * exactly the rollback it exists to survive.
	 *
	 * <p>The check-and-refresh unit is wrapped in {@code callInNewTrx} — normally a hack, per
	 * {@code docs/coding-rules/java-general.md} — because each target's whole unit of work must commit or
	 * fail independently of every other target's. The check and the refresh share that one transaction on
	 * purpose: a separately-committing refresh could leave refreshed order-line tax with no committed check
	 * behind it.
	 *
	 * <p>Two {@code catch} blocks, not one: a stamp failure means the check was never attempted and leaves no
	 * other trace, while a check-and-refresh failure means the attempt is already durably recorded. One
	 * shared message would hide the rarer signal.
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
			trxManager.callInNewTrx(() -> checkAndRefreshIfStatusChanged(pinstanceId, checkTarget));
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

	@NonNull
	private VATaxIDStatus checkAndRefreshIfStatusChanged(@Nullable final PInstanceId pinstanceId, @NonNull final CheckTarget checkTarget)
	{
		final VATaxIDStatus newStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(checkTarget.getBpartnerId())
				.bpartnerLocationId(checkTarget.getBpartnerLocationId())
				.vataxID(checkTarget.getVataxID())
				.pinstanceId(pinstanceId)
				.build());

		if (newStatus != checkTarget.getPreviousStatus())
		{
			try
			{
				orderTaxRefresher.refreshOrderLinesTaxForBPartner(checkTarget.getBpartnerId());
			}
			catch (final Exception ex)
			{
				// Re-thrown (not swallowed): the whole transaction — check included — must roll back
				// together (see the method javadoc). Wrapped so the outer catch's log line still names the
				// check as having succeeded, rather than being misread as a check failure.
				throw new AdempiereException(
						"VAT-ID check for " + checkTarget.getLogLabel()
								+ " succeeded (status " + checkTarget.getPreviousStatus() + " -> " + newStatus
								+ "), but refreshing its open orders' tax failed: " + ex.getMessage(),
						ex);
			}
		}

		return newStatus;
	}

	/**
	 * One VAT-ID to check: either a partner header ({@link #bpartnerLocationId} {@code null}) or one of its
	 * locations. Carries everything {@link #checkAndRefreshIfStatusChanged} needs so that method no longer
	 * has to branch on which table the record came from — the branch happens once, when a {@code CheckTarget}
	 * is built from the underlying {@code I_C_BPartner} / {@code I_C_BPartner_Location} record.
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
		 * the class javadoc, "Attempt vs success": this is DIFFERENT from {@link #lastAttemptedAt}, which
		 * advances on every attempt regardless of outcome.
		 */
		@Nullable Instant checkedAt;

		/**
		 * When this record's check was last ATTEMPTED, regardless of outcome — {@code null} if it never
		 * was. Used only by {@link VATaxIDCheckRunService#filterAndOrderForNightlyRun} to order the nightly
		 * run's targets; unrelated to {@link #checkedAt}. See the class javadoc, "Attempt vs success".
		 */
		@Nullable Instant lastAttemptedAt;

		@NonNull String logLabel;

		/**
		 * The organisation whose {@link VATaxIDConfig} governs this target's check — always the owning
		 * partner's, even for a location target: locations do not carry a materially different
		 * organisation in practice, and {@code CheckTarget} otherwise has no independent org of its own to
		 * resolve. Used by the availability pre-filter (see the class javadoc) and by the nightly due-ness
		 * filter ({@link VATaxIDCheckRunService#filterAndOrderForNightlyRun}).
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
		private static CheckTarget ofLocation(
				@NonNull final I_C_BPartner bpartnerRecord,
				@NonNull final I_C_BPartner_Location bpartnerLocationRecord)
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
					.orgId(OrgId.ofRepoId(bpartnerRecord.getAD_Org_ID()))
					.build();
		}
	}
}
