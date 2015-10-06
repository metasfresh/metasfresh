package org.adempiere.util.api;

/*
 * #%L
 * org.adempiere.util
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
import java.sql.Timestamp;

/**
 * Generic readonly parameters. Use {@link IParamsBL#createParams(java.util.Map)} to get yours.
 * 
 * @author ts
 * 
 */
public interface IParams
{
	/** No params */
	IParams NULL = NullParams.instance;

	/**
	 * @return true if the parameter exists
	 */
	boolean hasParameter(String parameterName);

	/**
	 * @param parameterName
	 * @return string value or <code>null</code> if parameter is missing
	 */
	String getParameterAsString(String parameterName);
	
	/**
	 * @param parameterName
	 * @return int value or <code>0</code> if parameter is missing or cannot be converted to integer
	 */
	int getParameterAsInt(String parameterName);
	
	/**
	 * @param parameterName
	 * @return boolean value or <code>false</code> if parameter is missing
	 */
	boolean getParameterAsBool(String parameterName);

	/**
	 * @param parameterName
	 * @return timestamp value or <code>null</code> if parameter is missing
	 */
	Timestamp getParameterAsTimestamp(String parameterName);
	
	/**
	 * @param parameterName
	 * @return {@link BigDecimal} value or <code>null</code> if parameter is missing or cannot be converted to {@link BigDecimal}
	 */
	BigDecimal getParameterAsBigDecimal(String parameterName);


}
