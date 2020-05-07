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

import de.metas.adempiere.model.I_AD_User;

public class AD_User_Tab_Callout extends TabCalloutAdapter
{

	public static final String MSG_NoDefaultContactError = "NoDefaultContactError";

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_AD_User user = calloutRecord.getModel(I_AD_User.class);

		final IBPartnerDAO partnerPA = Services.get(IBPartnerDAO.class);

		// first row: the IsDefaultContact flag must be set on true
		if (!partnerPA.existsDefaultContactInTable(user, null))
		{
			user.setIsDefaultContact(true);
		}
		// any other row: the IsDefaultContact flag must be set on false
		else
		{
			user.setIsDefaultContact(false);
		}
	}
}
