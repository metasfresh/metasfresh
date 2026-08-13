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

package de.metas.vatid.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.logging.LogManager;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDCheckRequest;
import de.metas.vatid.VATaxIDCheckService;
import de.metas.vatid.VATaxIDOrderTaxRefresher;
import de.metas.vatid.VATaxIDStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * The manual/scheduled VAT-ID check: available on the Business Partner window (table {@code C_BPartner}),
 * runnable on a single partner or on a selection alike (via {@link #retrieveSelectedRecordsQueryBuilder}) —
 * the same as the nightly schedule (a separate task wires the {@code AD_Scheduler}) and the same code path
 * a user runs by hand. Selects, loops, and delegates every actual check to
 * {@link VATaxIDCheckService#check(VATaxIDCheckRequest)}, which alone owns the format-check-first, the
 * not-supported short-circuit, de-duplication, evidence, and the parent-status refresh.
 *
 * <p><b>Selecting a partner also covers its locations.</b> The Business Partner window's selection is of
 * {@code C_BPartner} records — a user cannot select an address directly — so "check the VAT-IDs of the
 * selected partners" is read as "check the selected partners' own VAT-IDs <em>and</em> every VAT-ID on one
 * of their locations", not "the partner header only". {@link #retrieveCheckTargets()} builds exactly that
 * combined set: every selected partner's own header (if it carries a VAT-ID) plus every location of every
 * selected partner that carries one — regardless of whether that location's owning partner itself has a
 * VAT-ID.
 *
 * <p><b>{@code MaxChecksPerRun}</b> throttles a selection that is bigger than the organisation wants to hit
 * the online service with in one go: empty or {@code <= 0} means no limit at all (see the parameter's own
 * {@code AD_Element} description, which is the authoritative statement of that rule). It bounds the
 * <em>combined</em> partner+location work, not each type separately — a run does not check up to
 * {@code MaxChecksPerRun} partners and then, on top, up to {@code MaxChecksPerRun} locations. The rest of
 * the combined set is left completely untouched — still {@code NotChecked} or whatever it already was —
 * and the run logs how many were left pending, so a re-run (manual or the next nightly pass) picks them up.
 *
 * <p><b>Deterministic ordering under throttling.</b> {@link #retrieveCheckTargets()} orders the combined set
 * by {@code C_BPartner_ID} first — exactly the ordering the partner-only selection used before locations
 * existed — and, within one partner, the partner's own header check (if it has a VAT-ID) before that
 * partner's locations (ordered by {@code C_BPartner_Location_ID}). A throttled run therefore always
 * processes the same deterministic prefix of the same selection: it is stable under a wider
 * {@code MaxChecksPerRun} (the prefix only grows) and reproducible across repeated runs of the same
 * selection.
 *
 * <p><b>{@code @RunOutOfTrx}</b>: {@link #doIt()} runs with no ambient transaction, and each check target's
 * check — together with the order-line-tax refresh a status change triggers — is additionally wrapped in
 * its own new transaction (see {@link #checkOneInOwnTrx}) so that one target's failure — a throwing
 * checker, a malformed value the format re-check rejects, a refresh failure — can never affect any other
 * target already checked or still to be checked in the same run.
 */
public class C_BPartner_VATaxID_Check extends JavaProcess implements IProcessPrecondition
{
	private static final Logger logger = LogManager.getLogger(C_BPartner_VATaxID_Check.class);

	private static final String PARA_MaxChecksPerRun = "MaxChecksPerRun";

	@NonNull private final VATaxIDCheckService checkService = SpringContextHolder.instance.getBean(VATaxIDCheckService.class);
	@NonNull private final VATaxIDOrderTaxRefresher orderTaxRefresher = SpringContextHolder.instance.getBean(VATaxIDOrderTaxRefresher.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	/**
	 * Empty or {@code <= 0} means no limit — see the {@code AD_Process_Para}'s own description, which this
	 * field's default (0) already satisfies: a blank parameter is never bound (see {@link Param}), leaving
	 * the field at 0.
	 */
	@Param(parameterName = PARA_MaxChecksPerRun, mandatory = false)
	private int p_MaxChecksPerRun = 0;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final PInstanceId pinstanceId = getPinstanceId();
		final ImmutableList<CheckTarget> checkTargets = retrieveCheckTargets();

		final int maxChecksPerRun = p_MaxChecksPerRun;
		final boolean unlimited = maxChecksPerRun <= 0;
		final int checksAllowed = unlimited ? checkTargets.size() : Math.min(maxChecksPerRun, checkTargets.size());

		int checkedCount = 0;
		for (final CheckTarget checkTarget : checkTargets)
		{
			if (checkedCount >= checksAllowed)
			{
				break;
			}

			checkOneInOwnTrx(pinstanceId, checkTarget);
			checkedCount++;
		}

		final int pendingCount = checkTargets.size() - checkedCount;
		if (pendingCount > 0)
		{
			Loggables.addLog("VAT-ID check: checked {} of {} selected, MaxChecksPerRun={}, pendingCount={}",
					checkedCount, checkTargets.size(), maxChecksPerRun, pendingCount);
		}

		return checkedCount + " checked, " + pendingCount + " pending";
	}

	/**
	 * @return every VAT-ID to check for this run's selection: the header of every selected {@code C_BPartner}
	 * that carries one, plus every {@code C_BPartner_Location} of every selected partner that carries one —
	 * regardless of whether that location's own partner header has a VAT-ID (see the class javadoc,
	 * "Selecting a partner also covers its locations").
	 *
	 * <p>Ordered by {@code C_BPartner_ID} first and, within one partner, the header before that partner's
	 * locations (themselves ordered by {@code C_BPartner_Location_ID}) — see the class javadoc, "Deterministic
	 * ordering under throttling". A partner with neither its own VAT-ID nor any location VAT-ID contributes
	 * nothing and is not counted towards either the checked or the pending count.
	 */
	@NonNull
	private ImmutableList<CheckTarget> retrieveCheckTargets()
	{
		final ImmutableList<I_C_BPartner> selectedBPartners = retrieveSelectedRecordsQueryBuilder(I_C_BPartner.class)
				.orderBy(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
				.create()
				.listImmutable(I_C_BPartner.class);

		final ImmutableList<BPartnerId> selectedBPartnerIds = selectedBPartners.stream()
				.map(bpartnerRecord -> BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
				.collect(ImmutableList.toImmutableList());

		// The persistence access to C_BPartner_Location belongs on IBPartnerDAO, which already owns it
		// (retrieveBPartnerLocations, retrieveBPartnerLocationsByIds, …) — a JavaProcess must not build its
		// own IQueryBL query for another module's table (docs/REVIEW.md).
		final ImmutableListMultimap<BPartnerId, I_C_BPartner_Location> locationsByBPartnerId = bpartnerDAO
				.retrieveBPartnerLocationsWithVATaxID(selectedBPartnerIds)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						locationRecord -> BPartnerId.ofRepoId(locationRecord.getC_BPartner_ID()),
						Function.identity()));

		final ImmutableList.Builder<CheckTarget> checkTargets = ImmutableList.builder();
		for (final I_C_BPartner bpartnerRecord : selectedBPartners)
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());

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
	 * Runs one check target's check — and, where it changed the status, the resulting order-line-tax refresh
	 * — in its own, brand-new transaction — deliberately {@code callInNewTrx} (normally a hack, per
	 * {@code docs/coding-rules/java-general.md}): {@link #doIt()} is {@link RunOutOfTrx}, so there is no
	 * caller transaction to inherit anyway, and the whole point here is that each target's <em>whole</em>
	 * unit of work must commit (or fail) completely independently of every other target's — a throwing
	 * online checker or a rejected malformed value must not roll back, delay or block any target already
	 * checked or still queued in this same run. This would only go away if per-target commit isolation
	 * were no longer required — not expected to change.
	 *
	 * <p>The check and the order-line-tax refresh it may trigger are deliberately ONE transaction, not two:
	 * {@link #orderTaxRefresher} is called from inside this same {@code callInNewTrx} block (its own
	 * {@code refreshOrderLinesTaxForBPartner} joins it via {@code runInThreadInheritedTrx}), so they commit
	 * or roll back together. A separate, later transaction for the refresh would let it commit independently
	 * of the check that triggered it — leaving refreshed order-line tax with no committed check to justify
	 * it if the check's own write had failed, an inconsistency worse than the alternative (a refresh failure
	 * rolling back an otherwise-successful check, to be retried on the next run).
	 */
	private void checkOneInOwnTrx(@NonNull final PInstanceId pinstanceId, @NonNull final CheckTarget checkTarget)
	{
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

	@NonNull
	private VATaxIDStatus checkAndRefreshIfStatusChanged(@NonNull final PInstanceId pinstanceId, @NonNull final CheckTarget checkTarget)
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

		@NonNull String logLabel;

		@NonNull
		private static CheckTarget ofPartner(@NonNull final BPartnerId bpartnerId, @NonNull final I_C_BPartner bpartnerRecord)
		{
			return CheckTarget.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(null)
					.vataxID(VATIdentifier.of(bpartnerRecord.getVATaxID()))
					.previousStatus(resolvePreviousStatus(bpartnerRecord.getVATaxIDStatus()))
					.logLabel("C_BPartner_ID=" + bpartnerRecord.getC_BPartner_ID())
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
					.previousStatus(resolvePreviousStatus(bpartnerLocationRecord.getVATaxIDStatus()))
					.logLabel("C_BPartner_ID=" + bpartnerLocationRecord.getC_BPartner_ID()
							+ ", C_BPartner_Location_ID=" + bpartnerLocationRecord.getC_BPartner_Location_ID())
					.build();
		}

		/**
		 * A blank status column (a record never checked before) reads as {@link VATaxIDStatus#NotChecked},
		 * which is what it means — mirrors {@code VATaxIDParentStatusRepository#extractStatus}.
		 */
		@NonNull
		private static VATaxIDStatus resolvePreviousStatus(@Nullable final String statusCode)
		{
			return VATaxIDStatus.optionalOfNullableCode(statusCode).orElse(VATaxIDStatus.NotChecked);
		}
	}
}
