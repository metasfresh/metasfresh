package org.adempiere.bpartner.callout;

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


import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import de.metas.adempiere.model.I_C_BPartner_Location;

public class BPartnerLocation extends CalloutEngine
{
	public String cLocationId(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		return evalInput(mTab);
	}

	public String name(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		return evalInput(mTab);
	}

	private String evalInput(final GridTab mTab)
	{
		if (isCalloutActive())
		{
			return "";
		}

		I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.create(mTab, I_C_BPartner_Location.class);

		final int locationId = bpLocation.getC_Location_ID();
		if (locationId <= 0)
		{
			return "";
		}

		final int bPartnerId = bpLocation.getC_BPartner_ID();
		if (bPartnerId <= 0)
		{
			return "";
		}

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		bpartnerBL.setAddress(bpLocation);

		return "";
	}
}
