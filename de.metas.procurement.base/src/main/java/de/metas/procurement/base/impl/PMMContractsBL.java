package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.procurement.base.IPMMContractsBL;
import de.metas.procurement.base.model.I_AD_User;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PMMContractsBL implements IPMMContractsBL
{
	private static final String SYSCONFIG_AD_USER_IN_CHARGE = "de.metas.procurement.C_Flatrate_Term_Create_ProcurementContract.AD_User_InCharge_ID";
	
	@Override
	public int getDefaultContractUserInCharge_ID(final Properties ctx)
	{
		final int ad_Client_ID = Env.getAD_Client_ID(ctx);
		final int ad_Org_ID = Env.getAD_Org_ID(ctx);
		final int adUserInChargeId = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_AD_USER_IN_CHARGE, -1, ad_Client_ID, ad_Org_ID);
		return adUserInChargeId <= 0 ? -1 : adUserInChargeId;
	}

	@Override
	public I_AD_User getDefaultContractUserInChargeOrNull(final Properties ctx)
	{
		final int userInChangeId = getDefaultContractUserInCharge_ID(ctx);
		if (userInChangeId <= 0)
		{
			return null;
		}
		
		final I_AD_User userInCharge = InterfaceWrapperHelper.create(ctx, userInChangeId, I_AD_User.class, ITrx.TRXNAME_None);
		return userInCharge;
	}

	@Override
	public boolean hasPriceOrQty(final I_C_Flatrate_DataEntry dataEntry)
	{
		if(dataEntry == null)
		{
			return false;
		}
		
		final BigDecimal price = dataEntry.getFlatrateAmtPerUOM();
		if (price != null && price.signum() > 0)
		{
			return true;
		}
		
		final BigDecimal qty = dataEntry.getQty_Planned();
		if(qty != null && qty.signum() > 0)
		{
			return true;
		}
		
		return false;
	}

}
