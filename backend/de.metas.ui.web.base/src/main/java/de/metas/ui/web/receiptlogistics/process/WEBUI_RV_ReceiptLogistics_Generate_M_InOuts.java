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

package de.metas.ui.web.receiptlogistics.process;

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
 * "Wareneingangsdispo zu Wareneingang" on the receipt-logistics window: receive the WHOLE selection in one
 * gesture - planned rows, unplanned rows, or a mixture.
 * <p>
 * The heavy action of the set, and deliberately NOT a quick action (`WEBUI_ViewQuickAction='N'`, mirroring
 * window 541954's own multi-row entry): it books goods for every selected row at once, which is not something to
 * put one careless click away.
 * <p>
 * <b>It decides nothing itself.</b> Routing per row on the nullable planning id, the grouping, and the quantity
 * each row contributes all live in {@link ReceiptFromReceiptScheduleService#receiveRows} - the ONE receive this
 * domain has, which the single-row actions and the delivery-planning window's generate also go through. This
 * class turns the selected GRID ROWS into their source ids (which is how it can see the plannings at all),
 * applies the shared precondition, and hands them over.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptLogistics_Generate_M_InOuts extends ReceiptLogisticsViewBasedProcess
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

		// A planning may hold AT MOST ONE receipt or shipment. Asked of the WHOLE selection, of the one
		// definition, exactly as the single-row receive asks it - never re-implemented here.
		//
		// The receipt schedule's own eligibility is deliberately NOT asked per row: a row with nothing left to
		// receive contributes nothing and the rest of the selection still goes through, which is the batch
		// behaviour the underlying generate already has (REQUIREMENTS 3.4 - no new precondition messaging).
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
