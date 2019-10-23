package org.adempiere.ad.migration.util;

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


import org.compiere.model.I_AD_Column;
import org.compiere.model.POInfoColumn;

public interface IDataConverter
{
	/**
	 * Value returned by converting methods, when the column was mandatory but no value was identified.
	 */
	Object VALUE_Unknown = new Object();

	/**
	 * Converts given string value to proper object format.
	 * 
	 * @param column
	 * @param value
	 * @return converted object or {@link #VALUE_Unknown}
	 */
	Object stringToObject(I_AD_Column column, String value);

	/**
	 * Converts given column value to String.
	 * 
	 * @param column
	 * @param value
	 * @return String
	 */
	String objectToString(I_AD_Column column, Object value);

	/**
	 * Converts given column info value to String.
	 * 
	 * @param columnInfo
	 * @param value
	 * @return String
	 */
	String objectToString(POInfoColumn columnInfo, Object value);

}
