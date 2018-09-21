package de.metas.adempiere.service;

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


import java.util.List;
import java.util.Properties;

import org.compiere.model.I_M_Warehouse;

import de.metas.util.ISingletonService;

/**
 * Service that contains methods regarding POS access to data.
 * 
 * @author ad
 *
 */
public interface IPOSAccessBL extends ISingletonService
{
	/**
	 * Gets the warehouses available for this role's POS profile.
	 * @param ctx
	 * @return
	 */
	List<I_M_Warehouse> getAvailableWarehouses(Properties ctx);
	
	/**
	 * Returns only the warehouses in the initial list that exist in the role's POS profile
	 * 
	 * @param ctx
	 * @param initialList
	 * @return
	 */
	List<I_M_Warehouse> filterWarehousesByProfile(Properties ctx, List<I_M_Warehouse> initialList);
}
