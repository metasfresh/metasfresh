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

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.shipping.TransportDirection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
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
	 * An inbound (or dropship) planning has no vendor load report, so its plan is the only source of ActualLoadQty.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_Delivery_Planning.COLUMNNAME_PlannedLoadedQuantity)
	public void onPlannedLoadedQuantityChanged(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		final TransportDirection transportDirection = TransportDirection.ofCode(deliveryPlanning.getTransportDirection());
		if (!transportDirection.isIncomingOrDropship())
		{
			return;
		}

		deliveryPlanning.setActualLoadQty(deliveryPlanning.getPlannedLoadedQuantity());
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
			I_M_Delivery_Planning.COLUMNNAME_ActualDischargeQuantity })
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
