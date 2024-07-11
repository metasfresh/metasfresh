package de.metas.material.planning.event;

import de.metas.bpartner.BPartnerId;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class SupplyRequiredHandlerUtils
{

	@NonNull
	public static MaterialRequest mkRequest(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final IMaterialPlanningContext mrpContext)
	{

		final BPartnerId customerId = supplyRequiredDescriptor.getMaterialDescriptor().getCustomerId();

		return MaterialRequest.builder()
				.qtyToSupply(getQuantity(supplyRequiredDescriptor))
				.mrpContext(mrpContext)
				.mrpDemandBPartnerId(BPartnerId.toRepoIdOr(customerId, -1))
				.mrpDemandOrderLineSOId(supplyRequiredDescriptor.getOrderLineId())
				.mrpDemandShipmentScheduleId(supplyRequiredDescriptor.getShipmentScheduleId())
				.demandDate(supplyRequiredDescriptor.getMaterialDescriptor().getDate())
				.isSimulated(supplyRequiredDescriptor.isSimulated())
				.build();
	}

	private static @NonNull Quantity getQuantity(final @NonNull SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final ProductId productId = ProductId.ofRepoId(supplyRequiredDescriptor.getMaterialDescriptor().getProductId());
		final BigDecimal qtyToSupplyBD = supplyRequiredDescriptor.getMaterialDescriptor().getQuantity();
		final I_C_UOM uom = productBL.getStockUOM(productId);
		return Quantity.of(qtyToSupplyBD, uom);
	}
}
