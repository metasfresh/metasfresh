package org.adempiere.ad.security.asp;

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


import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.model.I_AD_Window_Access;

/**
 * Defines ASP filters that shall be applied when retrieving application dictionary elements (Windows, Processes etc).
 * 
 * @author tsa
 * @see http://www.adempiere.com/ASP
 */
public interface IASPFilters
{
	/**
	 * 
	 * @param accessTableClass {@link I_AD_Window_Access}, {@link I_AD_Process_Access} etc
	 * @return filter
	 */
	<AccessTableType> IQueryFilter<AccessTableType> getFilter(final Class<AccessTableType> accessTableClass);

	/**
	 * @param accessTableClass
	 * @return SQL where clause prefixed with "AND" or empty string.
	 */
	<AccessTableType> String getSQLWhereClause(Class<AccessTableType> accessTableClass);

	boolean isDisplayField(int adFieldId);
}
