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
import de.metas.bpartner.BPartnerId;
import de.metas.logging.LogManager;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDCheckRequest;
import de.metas.vatid.VATaxIDCheckService;
import de.metas.vatid.VATaxIDOrderTaxRefresher;
import de.metas.vatid.VATaxIDStatus;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

/**
 * The manual/scheduled VAT-ID check: available on the Business Partner window (table {@code C_BPartner}),
 * runnable on a single partner or on a selection alike (via {@link #retrieveSelectedRecordsQueryBuilder}) —
 * the same as the nightly schedule (a separate task wires the {@code AD_Scheduler}) and the same code path
 * a user runs by hand. Selects, loops, and delegates every actual check to
 * {@link VATaxIDCheckService#check(VATaxIDCheckRequest)}, which alone owns the format-check-first, the
 * not-supported short-circuit, de-duplication, evidence, and the parent-status refresh.
 *
 * <p><b>{@code MaxChecksPerRun}</b> throttles a selection that is bigger than the organisation wants to hit
 * the online service with in one go: empty or {@code <= 0} means no limit at all (see the parameter's own
 * {@code AD_Element} description, which is the authoritative statement of that rule). The rest of the
 * selection is left completely untouched — still {@code NotChecked} or whatever it already was — and the
 * run logs how many were left pending, so a re-run (manual or the next nightly pass) picks them up.
 *
 * <p><b>{@code @RunOutOfTrx}</b>: {@link #doIt()} runs with no ambient transaction, and each partner's check
 * — together with the order-line-tax refresh a status change triggers — is additionally wrapped in its own
 * new transaction (see {@link #checkOneInOwnTrx}) so that one partner's failure — a throwing checker, a
 * malformed value the format re-check rejects, a refresh failure — can never affect any other partner
 * already checked or still to be checked in the same run.
 */
public class C_BPartner_VATaxID_Check extends JavaProcess implements IProcessPrecondition
{
	private static final Logger logger = LogManager.getLogger(C_BPartner_VATaxID_Check.class);

	private static final String PARA_MaxChecksPerRun = "MaxChecksPerRun";

	@NonNull private final VATaxIDCheckService checkService = SpringContextHolder.instance.getBean(VATaxIDCheckService.class);
	@NonNull private final VATaxIDOrderTaxRefresher orderTaxRefresher = SpringContextHolder.instance.getBean(VATaxIDOrderTaxRefresher.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

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
		final ImmutableList<I_C_BPartner> bpartnersToCheck = retrieveBPartnersWithVATaxID();

		final int maxChecksPerRun = p_MaxChecksPerRun;
		final boolean unlimited = maxChecksPerRun <= 0;
		final int checksAllowed = unlimited ? bpartnersToCheck.size() : Math.min(maxChecksPerRun, bpartnersToCheck.size());

		int checkedCount = 0;
		for (final I_C_BPartner bpartnerRecord : bpartnersToCheck)
		{
			if (checkedCount >= checksAllowed)
			{
				break;
			}

			checkOneInOwnTrx(pinstanceId, bpartnerRecord);
			checkedCount++;
		}

		final int pendingCount = bpartnersToCheck.size() - checkedCount;
		if (pendingCount > 0)
		{
			Loggables.addLog("VAT-ID check: checked {} of {} selected, MaxChecksPerRun={}, pendingCount={}",
					checkedCount, bpartnersToCheck.size(), maxChecksPerRun, pendingCount);
		}

		return checkedCount + " checked, " + pendingCount + " pending";
	}

	/**
	 * @return every selected (or single-record) {@code C_BPartner} that actually carries a VAT-ID, ordered
	 * by {@code C_BPartner_ID} so a run throttled by {@code MaxChecksPerRun} always processes the same
	 * deterministic prefix of the selection. A partner with no VAT-ID has nothing to check and is not
	 * counted towards either the checked or the pending count.
	 */
	@NonNull
	private ImmutableList<I_C_BPartner> retrieveBPartnersWithVATaxID()
	{
		return retrieveSelectedRecordsQueryBuilder(I_C_BPartner.class)
				.addNotNull(I_C_BPartner.COLUMNNAME_VATaxID)
				.addNotEqualsFilter(I_C_BPartner.COLUMNNAME_VATaxID, "")
				.orderBy(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
				.create()
				.listImmutable(I_C_BPartner.class);
	}

	/**
	 * Runs one partner's check — and, where it changed the status, the resulting order-line-tax refresh —
	 * in its own, brand-new transaction — deliberately {@code callInNewTrx} (normally a hack, per
	 * {@code docs/coding-rules/java-general.md}): {@link #doIt()} is {@link RunOutOfTrx}, so there is no
	 * caller transaction to inherit anyway, and the whole point here is that each partner's <em>whole</em>
	 * unit of work must commit (or fail) completely independently of every other partner's — a throwing
	 * online checker or a rejected malformed value must not roll back, delay or block any partner already
	 * checked or still queued in this same run. This would only go away if per-partner commit isolation
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
	private void checkOneInOwnTrx(@NonNull final PInstanceId pinstanceId, @NonNull final I_C_BPartner bpartnerRecord)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
		final VATaxIDStatus previousStatus = VATaxIDStatus.optionalOfNullableCode(bpartnerRecord.getVATaxIDStatus())
				.orElse(VATaxIDStatus.NotChecked);

		try
		{
			trxManager.callInNewTrx(() -> checkAndRefreshIfStatusChanged(pinstanceId, bpartnerId, bpartnerRecord, previousStatus));
		}
		catch (final Exception ex)
		{
			// One partner's failure (a throwing checker, a value the format re-check rejects, or — per the
			// message wrapping below — a refresh failure) must not abort the run for the rest of the
			// selection.
			Loggables.withWarnLoggerToo(logger)
					.addLog("VAT-ID check failed for C_BPartner_ID={}: {}", bpartnerRecord.getC_BPartner_ID(), ex.getMessage());
		}
	}

	@NonNull
	private VATaxIDStatus checkAndRefreshIfStatusChanged(
			@NonNull final PInstanceId pinstanceId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final I_C_BPartner bpartnerRecord,
			@NonNull final VATaxIDStatus previousStatus)
	{
		final VATaxIDStatus newStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(VATIdentifier.of(bpartnerRecord.getVATaxID()))
				.pinstanceId(pinstanceId)
				.build());

		if (newStatus != previousStatus)
		{
			try
			{
				orderTaxRefresher.refreshOrderLinesTaxForBPartner(bpartnerId);
			}
			catch (final Exception ex)
			{
				// Re-thrown (not swallowed): the whole transaction — check included — must roll back
				// together (see the method javadoc). Wrapped so the outer catch's log line still names the
				// check as having succeeded, rather than being misread as a check failure.
				throw new AdempiereException(
						"VAT-ID check for C_BPartner_ID=" + bpartnerRecord.getC_BPartner_ID()
								+ " succeeded (status " + previousStatus + " -> " + newStatus
								+ "), but refreshing its open orders' tax failed: " + ex.getMessage(),
						ex);
			}
		}

		return newStatus;
	}
}
