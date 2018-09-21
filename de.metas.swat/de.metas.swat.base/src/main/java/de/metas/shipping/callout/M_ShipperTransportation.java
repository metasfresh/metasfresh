package de.metas.shipping.callout;

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


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.I_M_Shipper;

import de.metas.shipping.api.IShipperDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;

@Callout(I_M_ShipperTransportation.class)
public class M_ShipperTransportation
{
	/**
	 * Sets the {@link I_M_ShipperTransportation}'s M_Shipper_ID accordingly to {@link I_M_ShipperTransportation}'s Shipper_BPartner_ID
	 * 
	 * @param shipperTransportation
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID })
	public void onShipperBPartner(final I_M_ShipperTransportation shipperTransportation, final ICalloutField field)
	{
		// fix to avoid NPE when new entry
		if (shipperTransportation == null)
		{
			// new entry
			return;
		}
		if (shipperTransportation.getShipper_BPartner() == null)
		{
			return;
		}

		final I_M_Shipper shipper = Services.get(IShipperDAO.class).retrieveForShipperBPartner(shipperTransportation.getShipper_BPartner());
		shipperTransportation.setM_Shipper(shipper);
	}
}
