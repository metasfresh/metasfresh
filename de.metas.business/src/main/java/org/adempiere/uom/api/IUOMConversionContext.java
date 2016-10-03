package org.adempiere.uom.api;

import java.math.BigDecimal;

import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.compiere.model.I_M_Product;

/**
 * Conversion context
 *
 * @author tsa
 *
 */
public interface IUOMConversionContext
{
	public static IUOMConversionContext of(final I_M_Product product)
	{
		return new UOMConversionContext(product);
	}

	I_M_Product getM_Product();

	/**
	 * Convert quantity from <code>uomFrom</code> to <code>uomTo</code> using this context.
	 *
	 * @param qty
	 * @param uomFrom
	 * @param uomTo
	 * @return converted quantity; never return NULL.
	 */
	default BigDecimal convertQty(final BigDecimal qty, final I_C_UOM uomFrom, final I_C_UOM uomTo)
	{
		return Services.get(IUOMConversionBL.class).convertQty(this, qty, uomFrom, uomTo);
	}

	/**
	 * Rounds given qty to UOM standard precision.
	 *
	 * If qty's actual precision is bigger than UOM standard precision then the qty WON'T be rounded.
	 *
	 * @param qty
	 * @param uom
	 * @return qty rounded to UOM precision
	 */
	default BigDecimal roundToUOMPrecisionIfPossible(final BigDecimal qty, final I_C_UOM uom)
	{
		return Services.get(IUOMConversionBL.class).roundToUOMPrecisionIfPossible(qty, uom);
	}
}
