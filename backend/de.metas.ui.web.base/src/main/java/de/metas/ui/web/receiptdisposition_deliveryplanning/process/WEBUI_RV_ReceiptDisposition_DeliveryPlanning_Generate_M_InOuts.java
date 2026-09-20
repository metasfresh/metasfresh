/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.inout.InOutId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * "Wareneingangsdispo zu Wareneingang" on the receipt-disposition delivery-planning window: receive the WHOLE
 * selection in one gesture - planned rows, unplanned rows, or a mixture.
 * <p>
 * Deliberately NOT a quick action ({@code WEBUI_ViewQuickAction='N'}): it books goods for every selected row at
 * once. The routing, grouping and per-row quantity all live in {@link
 * ReceiptFromReceiptScheduleService#receiveRows}.
 * <p>
 * <b>It shares its name with {@code M_ReceiptSchedule_Generate_M_InOuts} (540557) but is NOT the same
 * operation</b>, and the difference is visible in what the operator gets back:
 * <ul>
 * <li><b>Scope.</b> 540557 selects by QUERY: `Processed='N'` + `QtyToMove>0`, narrowed by a mandatory warehouse
 * and an optional date range, and ALSO by the window selection when one exists - it passes
 * {@code getProcessInfo().getQueryFilterOrElseTrue()} into that query. The warehouse is mandatory because the
 * same process is reachable from the menu, where there is no selection to narrow anything. This one takes the
 * selection alone ({@code getSelectedRowIds()}, refusing an empty one), so it needs no scoping parameters.</li>
 * <li><b>Packing.</b> Both build the real LU/TU structure from the schedule's packing configuration - this one
 * through {@code ReceiptFromReceiptScheduleService#createPackedHUs}, deliberately aligned with 540557 so the
 * two do not produce differently-packed receipts for the same goods. What this one adds is the planning: the
 * configuration is capped at the ROW's planned share before anything is generated, so a split's sibling cannot
 * draw the whole order line.</li>
 * <li><b>Grouping.</b> 540557 drives a {@code TrxItemProcessorExecutorService} over the schedules ONE AT A TIME,
 * committing after each, and creates that schedule's receipt inside the loop - so two schedules of the SAME order
 * still land on two separate receipts; nothing aggregates across them. This one collects every VHU first and
 * hands them to a single {@code generateReceipts} call, so one receipt covers the whole selection.</li>
 * </ul>
 * The per-row HU actions ({@code ..._ReceiveHUs_UsingDefaults} / {@code ..._UsingConfig}) remain the way to
 * receive ONE row with a configuration the operator gets to see and change first; this batch action takes the
 * schedule's configuration as it stands.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptDisposition_DeliveryPlanning_Generate_M_InOuts extends ReceiptDispositionDeliveryPlanningViewBasedProcess
{
	@NonNull private final ReceiptFromReceiptScheduleService receiptFromReceiptScheduleService =
			SpringContextHolder.instance.getBean(ReceiptFromReceiptScheduleService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		// The receipt schedule's own eligibility is deliberately NOT asked per row: a row with nothing left to receive
		// contributes nothing and the rest of the selection still goes through, which is the batch behaviour the
		// underlying generate already has.
		return checkNoneProcessed(getSelectedDeliveryPlannings());
	}

	@Override
	@RunOutOfTrx // the receipts are generated and completed in their own transaction
	protected String doIt()
	{
		// The runtime backstop of the precondition, over the WHOLE selection and before anything is produced:
		// a process can be invoked past its precondition, and a guard discovered halfway through would leave
		// part of the selection received and part not.
		assertNoneProcessed(getSelectedDeliveryPlannings());

		final ImmutableList<InOutId> receiptIds = receive(getReceiptScheduleAndPlanningIds());

		invalidateView();

		return "@Created@ " + receiptIds.size() + " @M_InOut_ID@";
	}

	@VisibleForTesting
	ImmutableList<InOutId> receive(@NonNull final List<ReceiptScheduleAndDeliveryPlanningId> sourceIds)
	{
		return receiptFromReceiptScheduleService.receiveRows(sourceIds);
	}
}
