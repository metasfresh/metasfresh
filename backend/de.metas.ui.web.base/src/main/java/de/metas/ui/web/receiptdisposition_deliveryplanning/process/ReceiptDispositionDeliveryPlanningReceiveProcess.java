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
 * What the receipt-disposition delivery-planning window's four single-row receive actions share: ONE receive, whether the selected
 * row is planned or not.
 * <p>
 * <b>The row decides, not the action.</b> A planned row hands the action a delivery planning id, an unplanned one
 * hands it {@code null}, and either way the id goes into the same
 * {@link CreateReceiptFromReceiptScheduleRequest} - the shared request whose nullable planning id IS the two
 * paths. With the id present the receipt carries {@code M_Delivery_Planning_ID} while still a draft, so the
 * completion inside the same call fires the delivery-planning interceptor that derives the planning's delivered
 * state, actual discharge quantity, {@code Processed} flag and receipt back-link; with it absent the result is
 * the plain receipt against the schedule that window 541954 produces. This is why the four actions do NOT reuse
 * the receipt-schedule window's process classes: those resolve their record as {@code M_ReceiptSchedule} and
 * have no way to learn which planning - if any - a row stands for, and the HU-editor path they end in never
 * sets the planning id at all.
 * <p>
 * Subclasses differ in exactly one thing: {@link #createPlanningHUs}, i.e. what is received into (bare CUs or a
 * LU/TU packing) and how much. Everything else - the two guards, the request, the write-back and the view
 * invalidation - is here, once.
 */
public abstract class ReceiptDispositionDeliveryPlanningReceiveProcess extends ReceiptDispositionDeliveryPlanningViewBasedProcess
{
	@NonNull protected final ReceiptFromReceiptScheduleService receiptFromReceiptScheduleService =
			SpringContextHolder.instance.getBean(ReceiptFromReceiptScheduleService.class);

	/**
	 * Books the row's goods: what is received into, how much, and - for a planned row - the planning id going
	 * with it. The only thing the four actions differ in. Called after both guards have passed.
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
