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
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static org.adempiere.model.InterfaceWrapperHelper.isUIAction;

@Interceptor(I_M_Delivery_Planning.class)
@Component
public class M_Delivery_Planning
{
	private final DeliveryPlanningService deliveryPlanningService;

	public M_Delivery_Planning(
			@NonNull final DeliveryPlanningService deliveryPlanningService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
	}

	/**
	 * The currently-allocated guard runs for EVERY delete, UI-triggered or not: {@code M_Delivery_Planning_Alloc}'s
	 * FKs cascade on delete (they must, so a genuinely retired allocation doesn't block deleting the planning it
	 * once named), so nothing downstream of this interceptor stops a delete of an ACTIVELY allocated planning from
	 * silently taking its live allocation and shipping package down with it. The rest of {@code validateDeletion}
	 * (AC14's "at least one planning per order line") stays UI-only - it is a single-record safeguard against an
	 * operator error, not a rule a schedule-driven cleanup of every planning on that schedule should have to obey.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.assertNotCurrentlyAllocated(deliveryPlanning);

		if (isUIAction(deliveryPlanning))
		{
			deliveryPlanningService.validateDeletion(deliveryPlanning);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_Delivery_Planning.COLUMNNAME_ATD)
	public void onActualLoadingDateChanged(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryPlanning);
	}

	/**
	 * Fires only on the transition TO closed (AC14: no process, this one included, acts on a planning that is
	 * already closed) - {@link DeliveryPlanningService#onDeliveryPlanningClosed} either refuses the close outright
	 * or deactivates the now-closed planning's allocation.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_Delivery_Planning.COLUMNNAME_IsClosed)
	public void onClosedChanged(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		if (deliveryPlanning.isClosed())
		{
			deliveryPlanningService.onDeliveryPlanningClosed(DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()));
		}
	}
}
