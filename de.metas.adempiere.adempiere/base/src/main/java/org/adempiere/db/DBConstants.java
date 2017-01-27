package org.adempiere.db;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


public final class DBConstants
{
	/**
	 * Alters text from accented to unaccented or vice-versa
	 * 
	 * Params:
	 * <ul>
	 * <li>String - text to be altered
	 * <li>numeric - 1 for accented->unaccented ; 2 for unaccented->accented
	 * </ul>
	 */
	public static final String FUNCNAME_unaccent_string = "unaccent_string";

	/**
	 * 
	 * @param sqlString SQL string expression
	 * @return SQL code for stripping
	 */
	public static final String FUNC_unaccent_string(final String sqlString)
	{
		return FUNCNAME_unaccent_string + "(" + sqlString + ", 1)";
	}

	private DBConstants()
	{
		super();
	}
}
