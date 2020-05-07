package org.adempiere.bpartner.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;

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


import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_C_BPartner_Location;

public class C_BPartner_Location_Tab_Callout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final IBPartnerDAO bPartnerPA = Services.get(IBPartnerDAO.class);
		final I_C_BPartner_Location address = calloutRecord.getModel(I_C_BPartner_Location.class);

		if (!bPartnerPA.existsDefaultAddressInTable(address, null, I_C_BPartner_Location.COLUMNNAME_IsShipToDefault))
		{
			address.setIsShipTo(true);
			address.setIsShipToDefault(true);
		}
		else
		{
			address.setIsShipTo(false);
			address.setIsShipToDefault(false);
		}

		if (!bPartnerPA.existsDefaultAddressInTable(address, null, I_C_BPartner_Location.COLUMNNAME_IsBillToDefault))
		{
			address.setIsBillTo(true);
			address.setIsBillToDefault(true);
		}
		else
		{
			address.setIsBillTo(false);
			address.setIsBillToDefault(false);
		}
		if (!bPartnerPA.existsDefaultAddressInTable(address, null, I_C_BPartner_Location.COLUMNNAME_IsHandOverLocation))
		{
			address.setIsHandOverLocation(true);
		}
		else
		{
			address.setIsHandOverLocation(false);
		}
		// TODO: needs to be moved into de.metas.contracts project
		// if (!bPartnerPA.existsDefaultAddressInTable(address, null, I_C_BPartner_Location.COLUMNNAME_IsSubscriptionToDefault))
		// {
		// address.setIsSubscriptionTo(true);
		// address.setIsSubscriptionToDefault(true);
		// }
		// else
		// {
		// address.setIsSubscriptionTo(false);
		// address.setIsSubscriptionToDefault(false);
		// }
	}
}
