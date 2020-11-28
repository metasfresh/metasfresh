package de.metas.adempiere.service.impl;

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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.service.IPOSAccessBL;

/**
 * Mockable implementation for {@link IPOSAccessBL}, used for testing, which allows the developer to configure what these service methods will return.
 * 
 * @author tsa
 *
 */
public class MockedPOSAccessBL implements IPOSAccessBL
{

	private List<I_M_Warehouse> availableWarehouses;

	@Override
	public List<I_M_Warehouse> getAvailableWarehouses(Properties ctx)
	{
		return availableWarehouses;
	}

	public void setAvailableWarehouses(final List<I_M_Warehouse> availableWarehouses)
	{
		this.availableWarehouses = availableWarehouses;
	}

	/**
	 * @return initialList
	 */
	@Override
	public List<I_M_Warehouse> filterWarehousesByProfile(Properties ctx, List<I_M_Warehouse> initialList)
	{
		// FIXME: for now we are returning the whole list of warehouses
		// because in most of the cases the POSes are fetching the warehouses which have some special warehouse routings and most of our tests don't setup that.
		return new ArrayList<>(availableWarehouses);
		// return initialList;
	}

}
