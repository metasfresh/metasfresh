package de.metas.adempiere.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_User;

public interface IBPartnerOrgBL extends ISingletonService
{
	I_C_BPartner retrieveLinkedBPartner(I_AD_Org org);

	I_C_BPartner retrieveLinkedBPartner(int adOrgId);

	/**
	 * Returns the default location of the organization.
	 * 
	 * @param ctx
	 * @param orgId
	 * @param clazz
	 * @param trxName
	 * @return
	 */
	I_C_Location retrieveOrgLocation(Properties ctx, int orgId, String trxName);
	
	default I_C_Location retrieveOrgLocation(final int orgId)
	{
		return retrieveOrgLocation(Env.getCtx(), orgId, ITrx.TRXNAME_None);
	}


	/**
	 * 
	 * @param ctx
	 * @param orgId
	 * @param trxName
	 * @return
	 */
	I_C_BPartner_Location retrieveOrgBPLocation(Properties ctx, int orgId, String trxName);

	/**
	 * Returns the default contact of the given <code>orgId</code>'s bpartner. If there is no bpartner and/or user, then the user with the ID defined in {@link #AD_User_In_Charge_DEFAULT_ID} is
	 * returned.
	 * 
	 * @param ctx
	 * @param orgId
	 * @param trxName
	 * @return
	 */
	I_AD_User retrieveUserInChargeOrNull(Properties ctx, int orgId, String trxName);
}
