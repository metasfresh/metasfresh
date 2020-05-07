package de.metas.shipping.api;

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
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Shipper;

/**
 * 
 * @author tsa
 * 
 */
public interface IShipperDAO extends ISingletonService
{

	/**
	 * Retrieves the first active {@link I_M_Shipper} which has the given <code>bpartner</code> and same client with the given bpartner. If there is any default {@link I_M_Shipper} it will take that one.
	 * 
	 * @param bpartner
	 * @return shipper; if no shippers found it will return an error message
	 */
	I_M_Shipper retrieveForShipperBPartner(I_C_BPartner bpartner);

}
