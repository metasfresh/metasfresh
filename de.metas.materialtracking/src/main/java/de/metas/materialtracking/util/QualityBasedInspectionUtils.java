package de.metas.materialtracking.util;

/*
 * #%L
 * de.metas.materialtracking
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


import java.math.BigDecimal;
import java.math.RoundingMode;

import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

public final class QualityBasedInspectionUtils
{
	private QualityBasedInspectionUtils()
	{
		super();
	}

	/**
	 *
	 * @param qty
	 * @param qtyReference reference quantity (which is considered 100%)
	 * @return percentage = qty / qtyReference * 100; used precision is 2.
	 */
	public static final BigDecimal calculatePercentage(final BigDecimal qty, final BigDecimal qtyReference)
	{
		if (qty == null || qty.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		if (qtyReference == null)
		{
			// shall not happen
			return null;
		}
		if (qtyReference.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		return qty.divide(qtyReference, 12, RoundingMode.HALF_UP)
				.multiply(Env.ONEHUNDRED)
				.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 *
	 * @param qtyBase
	 * @param percentage between 0...100
	 * @param uom UOM (for precision)
	 * @return qtyBase x percentage / 100 (rounded by UOM's StdPrecision)
	 */
	public static final BigDecimal calculateQtyAsPercentageOf(final BigDecimal qtyBase, final BigDecimal percentage, final I_C_UOM uom)
	{
		if (qtyBase == null || qtyBase.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		if (percentage == null || percentage.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final int precision = uom.getStdPrecision();

		final BigDecimal qty = qtyBase
				.multiply(percentage)
				.divide(Env.ONEHUNDRED, precision, RoundingMode.HALF_UP);

		return qty;
	}

}
