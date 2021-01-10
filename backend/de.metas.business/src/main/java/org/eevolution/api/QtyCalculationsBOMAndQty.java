/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.api;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value(staticConstructor = "of")
public class QtyCalculationsBOMAndQty
{
	@NonNull QtyCalculationsBOM bom;

	/**
	 * how many BOMs we have
	 */
	@NonNull Quantity qty;

	@Nullable
	public Quantity computeQtyOfComponentsRequired(
			@NonNull final ProductId componentId,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		final QtyCalculationsBOMLine bomLine = bom.getLineByComponentId(componentId).orElse(null);
		if (bomLine == null)
		{
			return null;
		}

		final Quantity qtyInBomUOM = uomConverter.convertQuantityTo(qty, bomLine.getBomProductId(), bomLine.getBomProductUOMId());
		return bomLine.computeQtyRequired(qtyInBomUOM);
	}

}
