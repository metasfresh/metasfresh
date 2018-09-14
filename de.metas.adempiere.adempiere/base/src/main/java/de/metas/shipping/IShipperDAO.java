package de.metas.shipping;

import org.adempiere.service.ClientId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Shipper;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;

/**
 * 
 * @author tsa
 * 
 */
public interface IShipperDAO extends ISingletonService
{

	/**
	 * @return shipper; if no shippers found it will return an error message
	 */
	ShipperId getShipperIdByShipperPartnerId(BPartnerId shipperPartnerId);

	ShipperId getDefault(ClientId clientId);

	ITranslatableString getShipperName(ShipperId shipperId);

	I_M_Shipper getById(ShipperId id);

}
