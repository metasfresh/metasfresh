package org.compiere.util;

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
}
