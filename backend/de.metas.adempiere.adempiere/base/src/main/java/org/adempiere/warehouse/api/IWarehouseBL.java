package org.adempiere.warehouse.api;

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

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

public interface IWarehouseBL extends ISingletonService
{

	/**
	 * Get the first default locator.
	 * 
	 * In case there is no default locator, get the first non default locator.
	 * 
	 * In case none found, create a new one, with the coordinates (0,0,0)
	 * 
	 * @param warehouse
	 * @return default locator; never return null
	 */
	I_M_Locator getDefaultLocator(I_M_Warehouse warehouse);

	/**
	 * @param warehouse
	 * @return the address where given warehouse is located; never returns null
	 */
	I_C_Location getC_Location(I_M_Warehouse warehouse);
}
