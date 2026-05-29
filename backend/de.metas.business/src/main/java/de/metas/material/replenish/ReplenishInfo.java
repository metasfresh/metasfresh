/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.material.replenish;

import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class ReplenishInfo
{
	@NonNull
	Identifier identifier;

	@NonNull
	StockQtyAndUOMQty min;

	@NonNull
	StockQtyAndUOMQty max;

	Boolean highPriority;

	public MinMaxDescriptor toMinMaxDescriptor()
	{
		return MinMaxDescriptor.builder()
				.min(min.getStockQty().toBigDecimal())
				.max(max.getStockQty().toBigDecimal())
				.highPriority(highPriority)
				.build();
	}

	@Builder
	@Value
	public static class Identifier
	{
		@NonNull
		ProductId productId;

		@NonNull
		WarehouseId warehouseId;

		@Nullable
		LocatorId locatorId;

		public static Identifier of(@NonNull final WarehouseId warehouseId, @Nullable final LocatorId locatorId, @NonNull final ProductId productId)
		{
			return builder()
					.warehouseId(warehouseId)
					.locatorId(locatorId)
					.productId(productId)
					.build();
		}

		public static Identifier of(@NonNull final WarehouseId warehouseId, @NonNull final ProductId productId)
		{
			return builder()
					.warehouseId(warehouseId)
					.productId(productId)
					.build();
		}
	}
}
