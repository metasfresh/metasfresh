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

import java.math.BigDecimal;

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
	 * The three quantity-coupling rules, applied as ONE ORDERED PASS.
	 * <p>
	 * They used to be three separate {@code @ModelChange} methods, and that was a latent bug rather than a style
	 * choice. {@code ModelValidationEngine} sorts pointcuts by declaring class and then METHOD NAME, so the firing
	 * order was alphabetical: {@code onActualLoadQtyChanged}, {@code onPlannedDischargeQuantityChanged},
	 * {@code onPlannedLoadedQuantityChanged}. Editing the planned load of an INCOMING planning therefore ran the
	 * load-to-discharge rule FIRST - before the rule that derives {@code ActualLoadQty} from the plan had set it -
	 * so the planned discharge kept describing goods that would never be loaded. Nothing re-ran it. A rename of any
	 * of those methods would have silently reshuffled the semantics again.
	 * <p>
	 * Order matters and is now stated once, here, as a cascade:
	 * <ol>
	 * <li>an inbound or dropship planning has no vendor load report, so its plan is the only source of
	 * {@code ActualLoadQty};</li>
	 * <li>what was ACTUALLY loaded is the upper bound on what can ever be discharged, so the planned discharge
	 * follows it - in EVERY direction, because the constraint is physical and does not care who loaded. A zero
	 * actual load is NOT a settlement: it means "not loaded yet", the same reading of a zero that
	 * {@code DeliveryPlanningList.PoolEnd#effectiveQty} applies, and without it a reversal would plan the discharge
	 * down to nothing instead of returning it to be re-planned;</li>
	 * <li>an OUTGOING planning discharges at the customer, which nobody reports back to us, so that actual is
	 * assumed from its plan. Incoming and dropship are excluded for the one good reason: their discharge IS our own
	 * receipt, which settles the actual for real.</li>
	 * </ol>
	 * Each step feeds the next, which is why a later step keys off "was it settled by an earlier step" and not
	 * merely off what the user edited.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity,
			I_M_Delivery_Planning.COLUMNNAME_ActualLoadQty,
			I_M_Delivery_Planning.COLUMNNAME_PlannedDischargeQuantity })
	public void onQuantityEdited(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		settleEnds(deliveryPlanning,
				InterfaceWrapperHelper.isValueChanged(deliveryPlanning, I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity),
				InterfaceWrapperHelper.isValueChanged(deliveryPlanning, I_M_Delivery_Planning.COLUMNNAME_ActualLoadQty),
				InterfaceWrapperHelper.isValueChanged(deliveryPlanning, I_M_Delivery_Planning.COLUMNNAME_PlannedDischargeQuantity));
	}

	/**
	 * The cascade itself, taking WHAT THE USER EDITED as plain flags so the ordering can be tested without
	 * standing up a record's change-tracking state. See {@link #onQuantityEdited} for why the order is the point.
	 */
	@VisibleForTesting
	void settleEnds(
			@NonNull final I_M_Delivery_Planning deliveryPlanning,
			final boolean plannedLoadEdited,
			final boolean actualLoadEdited,
			final boolean plannedDischargeEdited)
	{
		final TransportDirection transportDirection = TransportDirection.ofCode(deliveryPlanning.getTransportDirection());

		boolean actualLoadSettled = actualLoadEdited;
		if (plannedLoadEdited && transportDirection.isIncomingOrDropship())
		{
			deliveryPlanning.setActualLoadQty(deliveryPlanning.getPlannedLoadedQuantity());
			actualLoadSettled = true;
		}

		boolean plannedDischargeSettled = plannedDischargeEdited;
		if (actualLoadSettled)
		{
			final BigDecimal actualLoadQty = deliveryPlanning.getActualLoadQty();
			if (actualLoadQty != null && actualLoadQty.signum() > 0)
			{
				deliveryPlanning.setPlannedDischargeQuantity(actualLoadQty);
				plannedDischargeSettled = true;
			}
		}

		if (plannedDischargeSettled && transportDirection.isOutgoing())
		{
			deliveryPlanning.setActualDischargeQuantity(deliveryPlanning.getPlannedDischargeQuantity());
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
