package de.metas.tourplanning.api;

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


import org.adempiere.util.ISingletonService;

import de.metas.tourplanning.model.I_M_Tour_Instance;

public interface ITourInstanceDAO extends ISingletonService
{
	/**
	 * Retrieves open (not processed) and generic (a tour version without shipper transportation) tour instance.
	 * 
	 * @param params
	 * @return existing tour instance or null
	 */
	I_M_Tour_Instance retrieveTourInstance(final Object contextProvider, final ITourInstanceQueryParams params);

	boolean isTourInstanceMatches(I_M_Tour_Instance tourInstance, ITourInstanceQueryParams params);

	/**
	 * 
	 * @param tourInstance
	 * @return true if given <code>tourInstance</code> has delivery days assigned
	 */
	boolean hasDeliveryDays(I_M_Tour_Instance tourInstance);
}
