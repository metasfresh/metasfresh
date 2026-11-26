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

import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.deliveryplanning.receipt.CreateReceiptFromReceiptScheduleRequest;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.process.ReceiptScheduleReceiveEligibility;
import lombok.NonNull;
import org.compiere.SpringContextHolder;


/**
 * What the receipt-disposition delivery-planning window's four single-row receive actions share: ONE receive,
 * whether the selected row is planned or not.
 * <p>
 * The row decides, not the action: a planned row hands over a planning id, an unplanned one {@code null}, and
 * either way it goes into the same {@link CreateReceiptFromReceiptScheduleRequest}. With the id present the
 * receipt carries {@code M_Delivery_Planning_ID} while still a DRAFT, so the completion inside the same call
 * derives the planning's delivered state, actual discharge quantity, {@code Processed} flag and back-link.
 */
public abstract class ReceiptDispositionDeliveryPlanningReceiveProcess extends ReceiptDispositionDeliveryPlanningViewBasedProcess
{
	@NonNull protected final ReceiptFromReceiptScheduleService receiptFromReceiptScheduleService =
			SpringContextHolder.instance.getBean(ReceiptFromReceiptScheduleService.class);

	/**
	 * Books the row's goods - the only thing the four actions differ in. Called after both guards have passed.
	 */
	protected abstract void receive(@NonNull ReceiptScheduleAndDeliveryPlanningId sourceIds);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		// The receipt schedule's own rule, asked of the ONE definition rather than restated here: this window
		// must offer an action exactly when the receipt-schedule window offers it (REQUIREMENTS 3.3 -
		// "Actionability already has an authority").
		final ProcessPreconditionsResolution scheduleEligible =
				ReceiptScheduleReceiveEligibility.check(getSelectedReceiptSchedule());
		if (!scheduleEligible.isAccepted())
		{
			return scheduleEligible;
		}

		// ... plus, on a PLANNED row, the planning's own: at most one receipt or shipment per planning.
		return checkNoneProcessed(getSelectedDeliveryPlannings());
	}

	@Override
	@RunOutOfTrx // the receipt is generated and completed in its own transaction
	protected final String doIt()
	{
		// The runtime backstop of the precondition, over the WHOLE selection and before anything is produced:
		// a process can be invoked past its precondition.
		assertNoneProcessed(getSelectedDeliveryPlannings());

		receive(getSelectedSourceIds());

		invalidateView();

		return MSG_OK;
	}

}
