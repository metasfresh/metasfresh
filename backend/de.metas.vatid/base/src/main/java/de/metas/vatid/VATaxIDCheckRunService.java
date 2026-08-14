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
 * Runs a whole {@link VATaxIDCheckRunRequest}: the combined partner+location target selection and its
 * deterministic ordering, the {@code MaxChecksPerRun} throttling, the per-target check-and-refresh, and
 * the pending/checked reporting. The single collaborator behind {@code C_BPartner_VATaxID_Check} (the manual
 * process) and any future {@code AD_Scheduler} entry point too — both callers select
 * {@code C_BPartner_ID}s their own way and hand the resulting ids to {@link #run(VATaxIDCheckRunRequest)}
 * unchanged.
 *
 * <p>Every actual check is delegated to {@link VATaxIDCheckService#check(VATaxIDCheckRequest)}, which alone
 * owns the format-check-first, the not-supported short-circuit, de-duplication, evidence, and the
 * parent-status refresh — this class owns only the selection, ordering, throttling, and looping around it.
 *
 * <p><b>Selecting a partner also covers its locations.</b> The Business Partner window's selection is of
 * {@code C_BPartner} records — a user cannot select an address directly — so "check the VAT-IDs of the
 * selected partners" is read as "check the selected partners' own VAT-IDs <em>and</em> every VAT-ID on one
 * of their locations", not "the partner header only". {@link #retrieveCheckTargets(ImmutableList)} builds
 * exactly that combined set: every selected partner's own header (if it carries a VAT-ID) plus every
 * location of every selected partner that carries one — regardless of whether that location's owning
 * partner itself has a VAT-ID. This unconditional expansion is deliberate for a user-triggered
 * selection/manual run; the nightly run additionally filters it (see {@link VATaxIDCheckRunRequest#isNightlyRun()}).
 *
 * <p><b>{@code MaxChecksPerRun}</b> throttles a selection that is bigger than the organisation wants to hit
 * the online service with in one go: empty or {@code <= 0} means no limit at all (see the parameter's own
 * {@code AD_Element} description, which is the authoritative statement of that rule). It bounds the
 * <em>combined</em> partner+location work, not each type separately — a run does not check up to
 * {@code MaxChecksPerRun} partners and then, on top, up to {@code MaxChecksPerRun} locations. The rest of
 * the combined set is left completely untouched — still {@code NotChecked} or whatever it already was —
 * and the run logs how many were left pending, so a re-run (manual or the next nightly pass) picks them up.
 *
 * <p><b>Attempt vs success — why a failing target cannot starve the nightly queue.</b>
 * {@code VATaxIDCheckedAt} advances only on a completed, non-rolled-back check (see
 * {@link VATaxIDCheckService#check}, written just before its own {@code updateParentStatus} call). A
 * target that can never complete a check — a malformed value that bypassed the save-time gate and always
 * fails the online check's own format re-validation, or a triggered order-tax refresh that always rolls
 * the whole check-and-refresh unit back — therefore never advances {@code VATaxIDCheckedAt} at all, and
 * would otherwise sort first of every future nightly run forever (a never-checked record sorts before any
 * checked one), permanently occupying the whole {@code MaxChecksPerRun} budget without ever making
 * progress. {@code VATaxIDLastAttemptedAt} exists to break exactly that: it is stamped unconditionally —
 * regardless of whether the attempt that follows succeeds, fails, or throws — in its own,
 * already-committed transaction that runs strictly <em>before</em> {@link #checkOneInOwnTrx} even starts
 * the check-and-refresh unit (see that method's javadoc for why that ordering, and that isolation, matter).
 * The nightly run's own ordering (see {@link #filterAndOrderForNightlyRun}) sorts by this attempt
 * timestamp, nulls first, rather than by success — so a target that just failed sorts <em>behind</em> a
 * target that was never attempted at all, and gets picked up again on some later run instead of
 * monopolising every run. This does not bound how often a permanently-broken target is retried — it only
 * guarantees it can no longer crowd out every other target every single night, which is the actual defect
 * this exists to prevent. Whether a target is due at all (as opposed to where it sorts) is unaffected: that
 * still depends on {@code VATaxIDStatus}/{@code VATaxIDCheckedAt} exactly as before — a record that keeps
 * failing keeps {@code VATaxIDStatus=NotChecked}, so it stays due and keeps getting retried, just no longer
 * at the permanent expense of everyone behind it.
 *
 * <p><b>Deterministic ordering under throttling.</b> {@link #retrieveCheckTargets(ImmutableList)} follows
 * {@link VATaxIDCheckRunRequest#getSelectedBPartnerIds()}'s own order — the caller already orders that by
 * {@code C_BPartner_ID} — and, within one partner, the partner's own header check (if it has a VAT-ID)
 * before that partner's locations (ordered by {@code C_BPartner_Location_ID}). A throttled manual/selection
 * run therefore always processes the same deterministic prefix of the same selection. The nightly run
 * re-orders the resulting targets by attempt time instead (see above), which deliberately supersedes this
 * id-based ordering for that path — see {@link #filterAndOrderForNightlyRun}.
 *
 * <p><b>No ambient transaction is assumed</b>: each check target's check — together with the
 * order-line-tax refresh a status change triggers — is wrapped in its own new transaction (see
 * {@link #checkOneInOwnTrx}) so that one target's failure — a throwing checker, a malformed value the
 * format re-check rejects, a refresh failure — can never affect any other target already checked or still
 * to be checked in the same run. {@code callInNewTrx} is deliberately used here even though the caller
 * (the process) already runs outside any ambient transaction — the point is not "there is no caller
 * transaction to escape", it is that each target's <em>whole</em> unit of work must commit or fail
 * completely independently of every other target's.
 *
 * <p><b>Availability pre-filter</b> ({@code GET /check-status}): before checking anything,
 * {@link #run(VATaxIDCheckRunRequest)} asks {@link VATaxIDCheckService#getUnavailableCountryCodes(OrgId)}
 * once per distinct organisation among the selection's targets — not once per VAT-ID — and skips every
 * target whose {@link VATIdentifier#getCountryCodePrefix()} is in the returned set. A skipped target is
 * never checked, never recorded as checked, and its stored status stands: it is never funnelled into
 * {@link VATaxIDConfig#getOnServiceUnavailable()}'s policy, which is reached only from inside an actual
 * {@link VATaxIDCheckService#check(VATaxIDCheckRequest)} call. This is what keeps a member-state outage
 * from mass-marking that country's partners {@code Invalid} under a fail-closed policy — the very
 * failure mode the pre-filter exists to prevent. Skipped targets do not count toward
 * {@link VATaxIDCheckRunResult#getPendingCount()} either: {@code MaxChecksPerRun} throttles the
 * remaining, actually-checkable targets, not the ones the pre-filter already removed.
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
	 * The nightly schedule's own selection (see the class javadoc on {@code CheckTarget}'s caller,
	 * {@code C_BPartner_VATaxID_Check}): every {@code C_BPartner_ID} that either has a due header VAT-ID or
	 * has at least one due location VAT-ID — "due" meaning never checked, or stale past its own
	 * organisation's {@code RecheckAfterDays}, the same staleness window {@link VATaxIDCheckService#check}
	 * itself applies (now also applied at selection time rather than left entirely to that per-record
	 * de-duplication). Header-due ids are ordered by the header's own {@code VATaxIDLastAttemptedAt}, nulls
	 * first (see the class javadoc, "Attempt vs success"); a partner reached only through a due location —
	 * its own header has no VAT-ID at all, so no header attempt exists to sort by — is appended after them
	 * in whatever order the location query returned it. Neither detail matters: this method is a coarse
	 * selection surface, not the actual throttled work queue, and its own internal order is never read by
	 * anything downstream. The real per-target throttling and ordering happens in
	 * {@link #filterAndOrderForNightlyRun}, which re-derives it from each resolved {@code CheckTarget}'s own
	 * attempt timestamp at header-or-location grain, independently of this list's order.
	 *
	 * <p><b>Location due-ness must be checked independently of header due-ness.</b> A location's own
	 * staleness must be visible to this selection even when its owning partner's header carries no VAT-ID
	 * at all (header-only due-ness would leave such a location permanently unreachable by the nightly
	 * sweep) or is itself already fresh (a fresh header must not hide a stale sibling location).
	 *
	 * <p>Scoped to the caller's own client (see {@link IBPartnerDAO#retrieveBPartnerIdsWithVATaxID()} and
	 * {@link IBPartnerDAO#retrieveBPartnerLocationsWithVATaxID()}, which this delegates to).
	 * {@link #isDueForNightlyRecheck} additionally excludes any record whose own organisation has the
	 * online check switched off entirely: such a record can never actually be checked
	 * ({@link VATaxIDCheckService#check} returns before doing anything once it resolves that same flag),
	 * so it would otherwise stay {@code NotChecked} forever — see the class javadoc's "Attempt vs success"
	 * section for why a record that can never advance either timestamp must be excluded, not merely
	 * deprioritised.
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
	 * @return whether a record with {@code status}/{@code checkedAt} under {@code orgId} is due for the
	 * nightly run: its own organisation has the online check switched on at all, AND it is either never
	 * checked or its own organisation's {@code RecheckAfterDays} has elapsed since it last was. Shared by
	 * the header selection ({@link #retrieveAllBPartnerIdsWithVATaxID}), the location selection (same
	 * method), and the per-target nightly filter ({@link #filterAndOrderForNightlyRun}) — one rule, applied
	 * at whichever grain (header, location, or resolved {@link CheckTarget}) is being tested, so the three
	 * call sites cannot silently drift apart on what "due" means.
	 *
	 * <p>The staleness half mirrors {@link VATaxIDCheckService#check}'s own de-duplication window exactly —
	 * this is a cheaper, coarser pre-filter over the same rule, not a competing one; {@code check} still
	 * runs its own, authoritative de-duplication against the check-log evidence for whatever passes this
	 * filter.
	 *
	 * <p>The VIES-enabled check comes first and short-circuits everything else, including the never-checked
	 * case: {@code check} itself does nothing at all for such an organisation, so a record under it must
	 * never be reported "due" no matter how long it has sat {@code NotChecked} — see the class javadoc's
	 * "Attempt vs success" section for why leaving it in would starve the nightly budget.
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
	 * Filters {@code checkTargets} down to the ones actually due for a nightly run — applying
	 * {@link #isDueForNightlyRecheck} at the SAME grain the caller already selected (header or location),
	 * rather than only at the coarser partner grain {@link #retrieveAllBPartnerIdsWithVATaxID} uses to
	 * decide which partners to fetch at all — and re-orders the survivors by
	 * {@link CheckTarget#getLastAttemptedAt()}, nulls first (see the class javadoc, "Attempt vs success").
	 *
	 * <p>Matching the grain of the filter to the grain of the expansion is the point: without this,
	 * {@link #retrieveCheckTargets(ImmutableList)}'s unconditional per-selected-partner expansion — correct
	 * and deliberate for a manual/selection run — would also add every one of a due partner's locations
	 * regardless of that location's OWN staleness for the nightly run too, burning the budget on no-op
	 * re-checks of already-fresh locations exactly as the original header-only starvation fix did one grain
	 * up. Applied only when {@link VATaxIDCheckRunRequest#isNightlyRun()} — a manual/selection run keeps the
	 * unconditional expansion, which is the documented, deliberate "selecting a partner also covers its
	 * locations" behaviour.
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
	 * Runs one check target's ATTEMPT STAMP plus its check — and, where it changed the status, the
	 * resulting order-line-tax refresh.
	 *
	 * <p><b>The attempt stamp is deliberately its own, already-committed transaction, written BEFORE the
	 * check-and-refresh unit below even starts</b> (see {@link #stampAttemptInOwnTrx}) — that ordering and
	 * that isolation are the entire point of tracking attempts separately from successes (class javadoc,
	 * "Attempt vs success"): a write sharing the check-and-refresh unit's transaction would be erased by
	 * exactly the rollback it exists to survive.
	 *
	 * <p>The check-and-refresh unit itself is wrapped in its own, brand-new transaction — deliberately
	 * {@code callInNewTrx} (normally a hack, per {@code docs/coding-rules/java-general.md}): the whole point
	 * here is that each target's <em>whole</em> unit of work must commit (or fail) completely independently
	 * of every other target's — a throwing online checker or a rejected malformed value must not roll back,
	 * delay or block any target already checked or still queued in this same run. This would only go away
	 * if per-target commit isolation were no longer required — not expected to change.
	 *
	 * <p>The check and the order-line-tax refresh it may trigger are deliberately ONE transaction, not two:
	 * {@link #orderTaxRefresher} is called from inside this same {@code callInNewTrx} block (its own
	 * {@code refreshOrderLinesTaxForBPartner} joins it via {@code runInThreadInheritedTrx}), so they commit
	 * or roll back together. A separate, later transaction for the refresh would let it commit independently
	 * of the check that triggered it — leaving refreshed order-line tax with no committed check to justify
	 * it if the check's own write had failed, an inconsistency worse than the alternative (a refresh failure
	 * rolling back an otherwise-successful check, to be retried on the next run).
	 *
	 * <p><b>Two separate {@code try}/{@code catch} blocks, not one</b> — the stamp attempt and the
	 * check-and-refresh unit fail in ways an operator needs to tell apart. A stamp-write failure means the
	 * check for this target was never even attempted (see {@link #stampAttemptInOwnTrx}'s own javadoc for
	 * why that is an accepted residual risk, not a defended-against one) and leaves no other trace anywhere
	 * — no {@code VATaxID_CheckLog} row exists either, since {@code writeRequestSent} runs only once the
	 * check itself starts. A check-and-refresh failure, by contrast, means the attempt WAS made and is
	 * already durably recorded. Logging both under the same message would make the more useful, rarer
	 * signal indistinguishable from the routine one.
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
	 * Stamps {@code checkTarget}'s own record's {@code VATaxIDLastAttemptedAt} to "now", unconditionally —
	 * this runs before {@link #checkOneInOwnTrx} even attempts the check, so it records that an attempt is
	 * ABOUT to happen rather than that one has already succeeded or failed.
	 *
	 * <p><b>Deliberately {@code runInNewTrx}, not {@code runInThreadInheritedTrx}</b> — the one genuinely
	 * required use of the hack {@code docs/coding-rules/java-general.md} otherwise warns against. The whole
	 * point of {@code VATaxIDLastAttemptedAt} is to survive the rollback of the check-and-refresh unit that
	 * follows it (see the class javadoc, "Attempt vs success"): if this write joined that unit's
	 * transaction — via {@code runInThreadInheritedTrx}, or by simply running inside the same
	 * {@code callInNewTrx} block — a rolled-back check would erase the very evidence that an attempt was
	 * made, reproducing the exact defect this mechanism exists to prevent. A genuinely independent,
	 * already-committed transaction is therefore not a shortcut here; it is the mechanism.
	 *
	 * <p><b>This method's own chronic failure is an accepted residual risk, not a defended-against one.</b>
	 * If this write itself fails on every attempt for one target — row-lock contention with an unrelated job
	 * touching the same record, or a save-veto interceptor unconnected to VAT-ID checking — that target's
	 * {@code VATaxIDLastAttemptedAt} never advances either, and {@link #checkOneInOwnTrx} logs it distinctly
	 * from an ordinary check failure rather than retrying it differently. Because due-ness and ordering are
	 * decoupled (class javadoc, "Attempt vs success"), such a target sorts first on every run but consumes
	 * only its own {@code MaxChecksPerRun} slot each time — it does not crowd out any other target, unlike
	 * the original defect this whole mechanism exists to prevent. Not hardened further (e.g. an exclude-
	 * after-N-failures counter): ordinary Postgres constraint semantics make a write failure on an
	 * already-selectable row — updating one nullable column — genuinely rare, and a chronic occurrence is
	 * meant to be diagnosed from the run log this method's caller writes, not auto-excluded.
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
