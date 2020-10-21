package org.eevolution.api;

import java.math.BigDecimal;

import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class ProductBOMQtys
{
	/**
	 * Calculates Qty + Scrap
	 *
	 * @param qty          qty (without scrap)
	 * @param scrapPercent scrap percent (between 0..100)
	 * @return qty * (1 + qtyScrap/100)
	 */
	public static BigDecimal computeQtyWithScrap(final BigDecimal qty, @NonNull final Percent scrapPercent)
	{
		if (qty == null || qty.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		if (scrapPercent.isZero())
		{
			return qty;
		}

		final int precision = 8;
		return scrapPercent.addToBase(qty, precision);
	}

	/**
	 * Calculates Qty + Scrap
	 *
	 * @param qty          qty (without scrap)
	 * @param scrapPercent scrap percent (between 0..100)
	 * @return qty * (1 + qtyScrap/100)
	 */
	public static Quantity computeQtyWithScrap(@NonNull final Quantity qty, @NonNull final Percent scrapPercent)
	{
		if (qty.signum() == 0)
		{
			return qty;
		}

		if (scrapPercent.isZero())
		{
			return qty;
		}

		final int precision = 8;
		return Quantity.of(
				scrapPercent.addToBase(qty.toBigDecimal(), precision),
				qty.getUOM());
	}

}
