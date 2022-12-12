/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.picking.requests.CalculatePIIPCapacityRequest;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

@Service
public class PickingService
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);

	@NonNull
	public Capacity calculatePIIPCapacity(@NonNull final CalculatePIIPCapacityRequest request)
	{
		final ProductId productId = ProductId.ofRepoId(request.getShipmentSchedule().getM_Product_ID());

		final I_C_UOM productUOM = productBL.getStockUOM(productId);

		return huCapacityBL.getCapacity(request.getPiip(), productId, productUOM);
	}
}
