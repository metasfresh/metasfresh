package de.metas.material.cockpit.stock;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Check;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class StockDataAggregateItem
{
	int productCategoryId;
	int productId;
	String productValue;

	int warehouseId;

	BigDecimal qtyOnHand;

	@Builder
	private StockDataAggregateItem(
			final int productCategoryId,
			final int productId,
			final String productValue,
			final int warehouseId,
			@NonNull final BigDecimal qtyOnHand,
			@Singular final Map<AttributesKey, BigDecimal> qtyOnHandDetails)
	{
		Check.assumeGreaterThanZero(productCategoryId, "productCategoryId");
		Check.assumeGreaterThanZero(productId, "productId");
		Check.assumeNotEmpty(productValue, "productValue is not empty");
		// Check.assumeGreaterThanZero(warehouseId, "warehouseId");

		this.productCategoryId = productCategoryId;
		this.productId = productId;
		this.productValue = productValue;
		this.warehouseId = warehouseId > 0 ? warehouseId : -1;
		this.qtyOnHand = qtyOnHand;
	}
}
