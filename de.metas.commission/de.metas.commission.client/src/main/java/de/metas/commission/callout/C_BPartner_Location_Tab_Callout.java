package de.metas.commission.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;

/*
 * #%L
 * de.metas.commission.client
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
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.commission.interfaces.I_C_BPartner_Location;

public class C_BPartner_Location_Tab_Callout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_BPartner_Location address = calloutRecord.getModel(I_C_BPartner_Location.class);

		final String whereClause = I_C_BPartner_Location.COLUMNNAME_IsCommissionTo + " = ? AND "
				+ I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + " = ?";
		final boolean hasCommissionToAddress = new Query(Env.getCtx(), I_C_BPartner_Location.Table_Name, whereClause, null)
				.setOnlyActiveRecords(true)
				.setParameters(true, address.getC_BPartner_ID())
				.setClient_ID()
				.match();

		if (!hasCommissionToAddress)
		{
			address.setIsCommissionTo(true);
		}
		else
		{
			address.setIsCommissionTo(false);
		}
	}
}
