package de.metas.quantity;

import javax.annotation.Nullable;

import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.NonNull;

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

public class QuantityUOMConverters
{
	public static QuantityUOMConverter noConversion()
	{
		return NoConversion.instance;
	}

	private static class NoConversion implements QuantityUOMConverter
	{
		public static final transient QuantityUOMConverters.NoConversion instance = new QuantityUOMConverters.NoConversion();

		@Override
		public Quantity convertQuantityTo(
				@NonNull final Quantity qty,
				@Nullable final ProductId productId,
				@NonNull final UomId targetUOMId)
		{
			if (UomId.equals(qty.getUomId(), targetUOMId))
			{
				return qty;
			}
			if (UomId.equals(qty.getSourceUomId(), targetUOMId))
			{
				return qty.switchToSource();
			}
			else
			{
				throw new QuantitiesUOMNotMatchingExpection("Cannot convert " + qty + " to " + targetUOMId);
			}
		}
	}
}
