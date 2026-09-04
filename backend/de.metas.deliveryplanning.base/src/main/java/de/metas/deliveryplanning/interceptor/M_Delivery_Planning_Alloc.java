/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningAllocRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Keeps {@code M_Delivery_Planning.IsAllocated} in step with the allocation table it mirrors - structurally,
 * via the model-change framework, rather than by trusting every write path to remember an inline call. An
 * INSERT, an {@code IsActive} flip, or a hard DELETE of an allocation row can all change which plannings have
 * an active allocation, so all three are covered; a bulk fix or a future import routine that writes this table
 * therefore keeps {@code IsAllocated} correct automatically, without needing to know the column exists.
 */
@Interceptor(I_M_Delivery_Planning_Alloc.class)
@Component
@RequiredArgsConstructor
public class M_Delivery_Planning_Alloc
{
	@NonNull private final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_M_Delivery_Planning_Alloc.COLUMNNAME_IsActive)
	public void onActiveStateChanged(@NonNull final I_M_Delivery_Planning_Alloc allocRecord)
	{
		deliveryPlanningAllocRepository.refreshIsAllocated(DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()));
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onDelete(@NonNull final I_M_Delivery_Planning_Alloc allocRecord)
	{
		deliveryPlanningAllocRepository.refreshIsAllocated(DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()));
	}
}
