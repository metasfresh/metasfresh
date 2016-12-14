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

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_C_BPartner_Location;

@Callout(I_C_BPartner_Location.class)
public class C_BPartner_Location
{
	@CalloutMethod(columnNames = { I_C_BPartner_Location.COLUMNNAME_IsBillToDefault })
	public void updateBillToColumn(final I_C_BPartner_Location location, final ICalloutField field)
	{
		if (location == null)
		{
			return;
		}

		// 07224
		// In case the isBillToDefault flag is set on true, the IsBillTo must be true, as well
		// The field will also be read only (set readonly logic in database)
		final boolean isBillToDefault = location.isBillToDefault();

		if (!isBillToDefault)
		{
			return;
		}

		location.setIsBillTo(true);
	}

	@CalloutMethod(columnNames = { I_C_BPartner_Location.COLUMNNAME_IsShipToDefault })
	public void updateShipToColumn(final I_C_BPartner_Location location, final ICalloutField field)
	{
		if (location == null)
		{
			return;
		}

		// 07224
		// In case the isBillToDefault flag is set on true, the IsBillTo must be true, as well
		// The field will also be read only (set readonly logic in database)
		final boolean isShipToDefault = location.isShipToDefault();

		if (!isShipToDefault)
		{
			return;
		}

		location.setIsShipTo(true);
	}

	@CalloutMethod(columnNames = { I_C_BPartner_Location.COLUMNNAME_C_Location_ID, I_C_BPartner_Location.COLUMNNAME_Name })
	public void updateAddressString(final ICalloutField calloutField)
	{
		final I_C_BPartner_Location bpLocation = calloutField.getModel(I_C_BPartner_Location.class);

		final int locationId = bpLocation.getC_Location_ID();
		if (locationId <= 0)
		{
			return;
		}

		final int bPartnerId = bpLocation.getC_BPartner_ID();
		if (bPartnerId <= 0)
		{
			return;
		}

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		bpartnerBL.setAddress(bpLocation);
	}

}
