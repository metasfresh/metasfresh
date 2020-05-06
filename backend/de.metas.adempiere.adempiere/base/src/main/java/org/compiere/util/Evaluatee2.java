package org.compiere.util;

import java.math.BigDecimal;

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



/**
 * Extend {@link Evaluatee} interface with more methods.
 * 
 * To create {@link Evaluatee2} instances, please use {@link Evaluatees}.

 * @author tsa
 *
 */
public interface Evaluatee2 extends Evaluatee
{
	/**
	 * Check if variableName exists.
	 * Note: that in case when is not sure if the variable exist, the implementation of this method should return true
	 * @param variableName
	 * @return true if the variable exists 
	 */
	public boolean has_Variable(String variableName);
	
	/**
	 * 	Get Old Variable Value
	 *	@param variableName name
	 *	@return value
	 */
	public String get_ValueOldAsString (String variableName);
	
	default Integer get_ValueOldAsInt(final String variableName, final Integer defaultValue)
	{
		final String valueStr = get_ValueOldAsString(variableName);
		return Evaluatee.convertToInteger(variableName, valueStr, defaultValue);
	}
	
	default Boolean get_ValueOldAsBoolean(final String variableName, final Boolean defaultValue)
	{
		final String valueStr = get_ValueOldAsString(variableName);
		return DisplayType.toBoolean(valueStr, defaultValue);
	}

	default BigDecimal get_ValueOldAsBigDecimal(final String variableName, final BigDecimal defaultValue)
	{
		final String valueStr = get_ValueOldAsString(variableName);
		return Evaluatee.convertToBigDecimal(variableName, valueStr, defaultValue);
	}
	
	default java.util.Date get_ValueOldAsDate(final String variableName, final java.util.Date defaultValue)
	{
		final String valueStr = get_ValueOldAsString(variableName);
		return Evaluatee.convertToDate(variableName, valueStr, defaultValue);
	}

}
