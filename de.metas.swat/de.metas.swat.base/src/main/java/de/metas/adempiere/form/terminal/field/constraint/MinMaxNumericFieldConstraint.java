package de.metas.adempiere.form.terminal.field.constraint;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.WrongValueException;
import de.metas.util.Check;

/**
 * Constraint that doesn't allow a numeric value to go below and/or above defined values.
 * If either min or max values are null, they are not taken into account.
 * 
 * @author ad
 *
 */
public class MinMaxNumericFieldConstraint implements ITerminalFieldConstraint<Object>
{

	private final String msgValueBelowMin;
	private final String msgValueAboveMax;
	
	private BigDecimal minValue = null;
	private BigDecimal maxValue = null;

	
	public MinMaxNumericFieldConstraint(final String msgValueBelowMin, final String msgValueAboveMax )
	{
		super();
		this.msgValueBelowMin = msgValueBelowMin;
		this.msgValueAboveMax = msgValueAboveMax;
	}


	@Override
	public void evaluate(final ITerminalField<Object> field, final Object value) throws WrongValueException
	{
		if (null == value)
		{
			// We ignore null values.
			return;
		}
		
		Check.assume(value instanceof BigDecimal, "value instanceof BigDecimal");

		final BigDecimal bdValue = (BigDecimal)value;

		if ((null != minValue) && (minValue.compareTo(bdValue) > 0))
		{
			throw new WrongValueException(field, "@" + msgValueBelowMin + "@");
		}

		if ((null != maxValue) && (maxValue.compareTo(bdValue) < 0))
		{
			throw new WrongValueException(field, "@" + msgValueAboveMax + "@");
		}
		
	}

	public BigDecimal getMinValue()
	{
		return minValue;
	}


	public void setMinValue(BigDecimal minValue)
	{
		this.minValue = minValue;
	}


	public BigDecimal getMaxValue()
	{
		return maxValue;
	}


	public void setMaxValue(BigDecimal maxValue)
	{
		this.maxValue = maxValue;
	}
	
	/**
	 *  Disables min constraint (internally sets it to null).
	 */
	public void disableMinConstraint()
	{
		this.minValue = null;
	}
	
	/**
	 *  Disables max constraint (internally sets it to null).
	 */
	public void disableMaxConstraint()
	{
		this.maxValue = null;
	}
}
