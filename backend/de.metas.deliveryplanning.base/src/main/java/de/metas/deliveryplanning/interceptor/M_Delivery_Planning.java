/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.deliveryplanning.interceptor;

import com.google.common.annotations.VisibleForTesting;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.shipping.TransportDirection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static org.adempiere.model.InterfaceWrapperHelper.isUIAction;

@Interceptor(I_M_Delivery_Planning.class)
@Component
@RequiredArgsConstructor
public class M_Delivery_Planning
{
	@NonNull private final DeliveryPlanningService deliveryPlanningService;

	/**
	 * Refuses the delete while a live allocation still points here, then removes the retired allocation history.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.assertNotCurrentlyAllocated(deliveryPlanning);

		if (isUIAction(deliveryPlanning))
		{
			deliveryPlanningService.validateDeletion(deliveryPlanning);
		}

		// only retired history can still be pointing here, the assert above having refused every live booking
		deliveryPlanningService.deleteAllocationsFor(DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()));
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_Delivery_Planning.COLUMNNAME_ATD)
	public void onActualLoadingDateChanged(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryPlanning);
	}

	/**
	 * The quantity-coupling rules, applied as ONE ORDERED PASS.
	 * <p>
	 * They belong in a single pointcut because one rule's input is another's output, and
	 * {@code ModelValidationEngine} orders pointcuts by declaring class and then METHOD NAME. Split across
	 * separate {@code @ModelChange} methods, the rules would run in alphabetical order - an order no reader
	 * chose, that no test could pin, and that a rename would silently change - and a rule whose input is produced
	 * by a later-sorting rule would read a stale value with nothing to re-run it.
	 * <p>
	 * <b>A plan is fed by a plan; an actual is fed by an actual.</b> That one sentence decides all three:
	 * <ol>
	 * <li><b>plan to plan, every direction</b> - what we intend to load is what we intend to discharge;</li>
	 * <li><b>plan to actual, inbound only</b> - a vendor never reports what they loaded, so the plan is the only
	 * figure that exists for that end. An outgoing load is ours, and only a shipment writes it;</li>
	 * <li><b>actual to actual, outbound only</b> - the customer's unload is never reported back, so what actually
	 * left our dock is the best knowledge there is. Incoming and dropship are excluded for one good reason: their
	 * discharge IS our own receipt, which settles the actual for real.</li>
	 * </ol>
	 * Rule 2 feeds rule 3, which is why rule 3 keys off whether an EARLIER RULE settled the actual load rather
	 * than only off what the user edited.
	 * <p>
	 * What is deliberately NOT a coupling: an actual load does not touch the planned discharge. A short load
	 * leaves the plan standing to be re-planned rather than silently rewriting what was agreed - so a directly
	 * edited {@code PlannedDischargeQuantity} settles nothing downstream, and is not a trigger column.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity,
			I_M_Delivery_Planning.COLUMNNAME_ActualLoadQty })
	public void onQuantityEdited(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		settleEnds(deliveryPlanning,
				InterfaceWrapperHelper.isValueChanged(deliveryPlanning, I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity),
				InterfaceWrapperHelper.isValueChanged(deliveryPlanning, I_M_Delivery_Planning.COLUMNNAME_ActualLoadQty));
	}

	/**
	 * The cascade itself, taking WHAT THE USER EDITED as plain flags so the ordering is testable without standing
	 * up a record's change-tracking state. See {@link #onQuantityEdited} for why the order is the point.
	 */
	@VisibleForTesting
	void settleEnds(
			@NonNull final I_M_Delivery_Planning deliveryPlanning,
			final boolean plannedLoadEdited,
			final boolean actualLoadEdited)
	{
		final TransportDirection transportDirection = TransportDirection.ofCode(deliveryPlanning.getTransportDirection());

		// PLAN to PLAN, every direction. What we intend to load is what we intend to discharge; the planned
		// discharge is NEVER touched by an actual, so a short load leaves the plan standing to be re-planned
		// rather than silently rewriting what was agreed.
		if (plannedLoadEdited)
		{
			deliveryPlanning.setPlannedDischargeQuantity(deliveryPlanning.getPlannedLoadedQuantity());
		}

		// PLAN to ACTUAL, inbound only: a vendor never reports what they loaded, so the plan is the only figure
		// there is. An outgoing load is ours and only a shipment writes it.
		boolean actualLoadSettled = actualLoadEdited;
		if (plannedLoadEdited && transportDirection.isIncomingOrDropship())
		{
			deliveryPlanning.setActualLoadQty(deliveryPlanning.getPlannedLoadedQuantity());
			actualLoadSettled = true;
		}

		// ACTUAL to ACTUAL, outbound only: the customer's unload is never reported back, so what actually left
		// our dock is the best knowledge there is. An incoming or dropship discharge is observed on our own
		// receipt and must not be guessed.
		//
		// An ACTUAL is only ever derived from another ACTUAL. Taking this from the planned discharge - as an
		// earlier version did - let a planner who merely typed a plan mark goods as discharged that were never
		// loaded.
		if (actualLoadSettled && transportDirection.isOutgoing())
		{
			deliveryPlanning.setActualDischargeQuantity(deliveryPlanning.getActualLoadQty());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onNew(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.recomputeOpenQuantitiesForOrderLine(deliveryPlanning);
	}

	/**
	 * Re-entrant by design: the recompute writes QtyTotalOpen/QtyTotalOpenPlanned on every sibling planning of
	 * the line. Safe only as long as neither of those two columns is listed in ifColumnsChanged below.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
			I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity,
			I_M_Delivery_Planning.COLUMNNAME_PlannedDischargeQuantity,
			I_M_Delivery_Planning.COLUMNNAME_ActualLoadQty,
			I_M_Delivery_Planning.COLUMNNAME_ActualDischargeQuantity,
			// Closing changes no quantity, but it DOES change what this planning claims: a closed planning's actual
			// is final, so one that took nothing stops claiming its plan and hands that share back to the open pool
			// (DeliveryPlanningList.PoolEnd#effectiveQty). Without these two columns here the computation is right
			// and the stored QtyTotalOpenPlanned is stale - it would only catch up on some later, unrelated qty edit.
			I_M_Delivery_Planning.COLUMNNAME_Processed,
			I_M_Delivery_Planning.COLUMNNAME_IsClosed })
	public void onQuantityChanged(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.recomputeOpenQuantitiesForOrderLine(deliveryPlanning);

		// AD_SQLColumn_SourceTableColumn invalidates the model cache, not an open document: without this call an
		// OPEN Lieferanweisungen keeps showing the stale Versandpaket line.
		deliveryPlanningService.invalidateDeliveryInstructionLinesFor(deliveryPlanning);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onDeleted(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.recomputeOpenQuantitiesForOrderLine(deliveryPlanning);
	}
}
