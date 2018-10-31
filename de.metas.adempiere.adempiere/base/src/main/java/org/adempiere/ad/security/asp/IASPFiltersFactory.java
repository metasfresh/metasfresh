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


import java.util.Properties;

import de.metas.util.ISingletonService;

/**
 * {@link IASPFilters} factory.
 * 
 * @author tsa
 * @see http://www.adempiere.com/ASP
 */
public interface IASPFiltersFactory extends ISingletonService
{
	/**
	 * Retrieves the right filters to be used for given AD_Client_ID.
	 * 
	 * @param adClientId
	 * @return {@link IASPFilters}; never return null
	 */
	IASPFilters getASPFiltersForClient(int adClientId);

	/**
	 * Retrieves the right filters to be used for context client.
	 * 
	 * @param ctx
	 * @return {@link IASPFilters}; never return null
	 */
	IASPFilters getASPFiltersForClient(Properties ctx);

}
