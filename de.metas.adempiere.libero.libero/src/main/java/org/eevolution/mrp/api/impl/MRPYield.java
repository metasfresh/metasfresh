package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import org.compiere.util.Env;

/**
 * Immutable Yield
 * 
 * @author tsa
 *
 */
public final class MRPYield
{
	public static final transient MRPYield ZERO = new MRPYield(0);
	
	public static final MRPYield valueOf(final int yield)
	{
		return new MRPYield(yield);
	}

	private final BigDecimal yield;

	/**
	 * 
	 * @param yield yield percent (between 0...100)
	 */
	private MRPYield(final int yield)
	{
		super();
		if (yield <= 0)
		{
			this.yield = BigDecimal.ZERO;
		}
		else
		{
			this.yield = BigDecimal.valueOf(yield);
		}
	}

	@Override
	public String toString()
	{
		return yield.toString() + "%";
	}

	public BigDecimal getYield()
	{
		return yield;
	}

	/**
	 * Calculates the qty with yield.
	 * 
	 * The formula for calculation is:
	 * 
	 * <pre>
	 * qty ........... yield%
	 * qty+Yield ..... 100%
	 * </pre>
	 * 
	 * @param qtyWithoutYield quantity without yield
	 * @param yield yield percent (between 0...100)
	 * @return quantity with yield included (i.e. qty * 100/yield)
	 */
	public final BigDecimal calculateQtyWithYield(final BigDecimal qtyWithoutYield)
	{
		if (qtyWithoutYield == null || qtyWithoutYield.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		// If Yield is zero we consider that it was not specified so we return the quantity untouched
		if (yield.signum() == 0)
		{
			return qtyWithoutYield;
		}

		// If yield is 100 there is no point to multiply and then divide with 100
		if (Env.ONEHUNDRED.compareTo(yield) == 0)
		{
			return qtyWithoutYield;
		}

		//
		// Increase the quantity by "adding" the yield to it
		// The correspondences are:
		// qty ........... yield%
		// qty+Yield ..... 100%
		final BigDecimal qtyWithYield = qtyWithoutYield.multiply(Env.ONEHUNDRED).divide(yield, 12, RoundingMode.HALF_UP);
		return qtyWithYield;
	}

}
