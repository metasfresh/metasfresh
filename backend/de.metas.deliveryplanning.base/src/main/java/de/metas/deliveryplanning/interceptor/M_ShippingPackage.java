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

import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.shipping.model.I_M_ShippingPackage;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ShippingPackage.class)
@Component
public class M_ShippingPackage
{
	private final DeliveryPlanningService deliveryPlanningService;

	public M_ShippingPackage(@NonNull final DeliveryPlanningService deliveryPlanningService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
	}

	/**
	 * A delivery instruction is cancelled or closed, never deleted - so a shipping package that carries a
	 * delivery-planning allocation refuses the delete and names the instruction to cancel instead.
	 * <p>
	 * Registered here, in {@code de.metas.deliveryplanning.base}, rather than beside the two existing
	 * {@code M_ShippingPackage} interceptors in {@code de.metas.business} / {@code de.metas.handlingunits.base}:
	 * the allocation and its repository live in this module, which is DOWNSTREAM of both, so the check cannot
	 * be pushed up into either without inverting the dependency. Both existing handlers are side-effect only
	 * (closing the {@code M_Package}, destroying the HU package) and neither refuses anything, so there is no
	 * competing verdict to order against - and whichever runs first, this guard's exception rolls the whole
	 * transaction back.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(@NonNull final I_M_ShippingPackage shippingPackage)
	{
		deliveryPlanningService.assertShippingPackageNotAllocated(shippingPackage);
	}
}
