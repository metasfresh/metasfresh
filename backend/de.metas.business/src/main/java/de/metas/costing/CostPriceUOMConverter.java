package de.metas.costing;

import de.metas.product.ProductId;
import de.metas.uom.UomId;

/*
 * #%L
 * de.metas.business
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

/**
 * Converts a {@link CostPrice} to a target UOM using the product's UOM conversion. The price-per-UOM sibling of
 * {@link de.metas.quantity.QuantityUOMConverter}: a quantity scales with the target UOM while a price scales
 * inversely, so this is kept as its own type rather than derived from the quantity converter.
 */
@FunctionalInterface
public interface CostPriceUOMConverter
{
	CostPrice convertCostPriceTo(CostPrice costPrice, ProductId productId, UomId targetUomId);
}
