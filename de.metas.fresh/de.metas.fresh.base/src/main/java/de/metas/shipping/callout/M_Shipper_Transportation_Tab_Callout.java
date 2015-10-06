package de.metas.shipping.callout;

/*
 * #%L
 * de.metas.fresh.base
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


import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;

import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.model.I_M_ShipperTransportation;

public class M_Shipper_Transportation_Tab_Callout extends TabCalloutAdapter
{
	@Override
	public void onNew(final GridTab gridTab)
	{
		final I_M_ShipperTransportation shipperTransportation = InterfaceWrapperHelper.create(gridTab, I_M_ShipperTransportation.class);

		Services.get(IShipperTransportationBL.class).setC_DocType(shipperTransportation);
	}

}
