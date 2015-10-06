package de.metas.handlingunits.attribute.strategy.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;

public class SumAggregationStrategy implements IAttributeAggregationStrategy
{
	/**
	 * Adds the given <code>valueDelta</code> to the given <code>valueOld</code> <code>null</code> values are allowed and will be intnerpreted as {@link BigDecimal#ZERO}. The given
	 * <code>attribute</code> parameter is ignored.
	 */
	@Override
	public Object aggregate(final I_M_Attribute ignored, final Object valueOld, final Object valueDelta)
	{
		final BigDecimal valueOldBD = coerceToBigDecimal(valueOld);
		final BigDecimal valueDeltaBD = coerceToBigDecimal(valueDelta);
		final BigDecimal valueNewBD = valueOldBD.add(valueDeltaBD);

		return valueNewBD;
	}

	private BigDecimal coerceToBigDecimal(final Object value)
	{
		final BigDecimal convertedValue;

		if (value == null)
		{
			convertedValue = BigDecimal.ZERO;
		}
		else
		{
			try
			{
				convertedValue = new BigDecimal(value.toString());
			}
			catch (final Exception e)
			{
				throw new AdempiereException("Could not create BigDecimal from object: " + value, e);
			}
		}
		return convertedValue;
	}
}
