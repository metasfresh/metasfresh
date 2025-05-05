package de.metas.quantity;

import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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

@FunctionalInterface
public interface QuantityUOMConverter
{
	Quantity convertQuantityTo(
			@NonNull final Quantity qty,
			@Nullable final ProductId productId,
			@NonNull final UomId targetUOMId);

	default BigDecimal convertQty(
			@Nullable final ProductId productId,
			@NonNull final BigDecimal qty,
			@NonNull final I_C_UOM uomFrom,
			@NonNull final I_C_UOM uomTo)
	{
		final UomId uomToId = UomId.ofRepoId(uomTo.getC_UOM_ID());
		final Quantity qtyConv = convertQuantityTo(
				Quantity.of(qty, uomFrom),
				productId,
				uomToId);
		return qtyConv.toBigDecimal();
	}
}
