package de.metas.material.cockpit.stock;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Value;

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
public class StockDataRecord
{
	int productCategoryId;
	int productId;
	String productValue;

	int warehouseId;

	BigDecimal qtyOnHand;

	@Builder
	private StockDataRecord(
			final int productCategoryId,
			final int productId,
			final String productValue,
			final int warehouseId,
			final BigDecimal qtyOnHand)
	{
		Check.assumeGreaterThanZero(productCategoryId, "productCategoryId");
		Check.assumeGreaterThanZero(productId, "productId");
		Check.assumeNotEmpty(productValue, "productValue is not empty");
		// Check.assumeGreaterThanZero(warehouseId, "warehouseId");
		Check.assumeNotNull(qtyOnHand, "Parameter qtyOnHand is not null");

		this.productCategoryId = productCategoryId;
		this.productId = productId;
		this.productValue = productValue;
		this.warehouseId = warehouseId > 0 ? warehouseId : -1;
		this.qtyOnHand = qtyOnHand;
	}
}
