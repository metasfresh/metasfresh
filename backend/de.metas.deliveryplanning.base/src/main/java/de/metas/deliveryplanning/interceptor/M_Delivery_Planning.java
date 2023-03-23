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

import de.metas.deliveryplanning.DeliveryInstructionsViewInvalidator;
import de.metas.deliveryplanning.DeliveryPlanningService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
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
	private final DeliveryInstructionsViewInvalidator deliveryInstructionsViewInvalidator;

	public M_Delivery_Planning(
			@NonNull final DeliveryPlanningService deliveryPlanningService,
			@NonNull final DeliveryInstructionsViewInvalidator deliveryInstructionsViewInvalidator)
	{
		this.deliveryPlanningService = deliveryPlanningService;
		this.deliveryInstructionsViewInvalidator = deliveryInstructionsViewInvalidator;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void onBeforeChange(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryInstructionsViewInvalidator.invalidateByDeliveryPlanning(deliveryPlanning, ModelChangeType.BEFORE_CHANGE);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void onAfterChange(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryInstructionsViewInvalidator.invalidateByDeliveryPlanning(deliveryPlanning, ModelChangeType.AFTER_CHANGE);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		if (isUIAction(deliveryPlanning))
		{
			deliveryPlanningService.validateDeletion(deliveryPlanning);
		}
	}
}
