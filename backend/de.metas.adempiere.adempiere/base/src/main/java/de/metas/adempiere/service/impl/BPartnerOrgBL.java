package de.metas.adempiere.service.impl;

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

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.bpartner.service.OrgHasNoBPartnerLinkException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class BPartnerOrgBL implements IBPartnerOrgBL
{
	@Override
	public I_C_BPartner retrieveLinkedBPartner(final I_AD_Org org)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(org);
		final String trxName = InterfaceWrapperHelper.getTrxName(org);
		final int adOrgId = org.getAD_Org_ID();
		return retrieveLinkedBPartner(ctx, adOrgId, trxName);
	}

	@Override
	public I_C_BPartner retrieveLinkedBPartner(final int adOrgId)
	{
		return retrieveLinkedBPartner(Env.getCtx(), adOrgId, ITrx.TRXNAME_None);
	}


	@Cached(cacheName = I_C_BPartner.Table_Name + "#By#AD_OrgBP_ID")
	/* package */ I_C_BPartner retrieveLinkedBPartner(@CacheCtx final Properties ctx, final int adOrgId, @CacheTrx final String trxName)
	{
		final IQueryBuilder<I_C_BPartner> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class, ctx, trxName);

		final ICompositeQueryFilter<I_C_BPartner> filters = queryBuilder.getCompositeFilter();
		filters.addOnlyActiveRecordsFilter();
		filters.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_OrgBP_ID, adOrgId);

		return queryBuilder.create()
				.firstOnly(I_C_BPartner.class);
	}

	@Override
	public I_C_Location retrieveOrgLocation(Properties ctx, int orgId, String trxName)
	{
		final I_C_BPartner_Location bPartnerLocation = retrieveOrgBPLocation(ctx, orgId, trxName);
		if (bPartnerLocation != null) // 03378 : Temporary. Will be removed when OrgBP_Location is mandatory.
		{
			return bPartnerLocation.getC_Location();
		}
		return null;
	}

	@Override
	public I_C_BPartner_Location retrieveOrgBPLocation(Properties ctx, int orgId, String trxName)
	{
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.create(
					orgDAO.retrieveOrgInfo(ctx, orgId, trxName),
					I_AD_OrgInfo.class);
		return orgInfo.getOrgBP_Location();
	}

	@Override
	public I_AD_User retrieveUserInChargeOrNull(final Properties ctx, final int orgId, final String trxName)
	{
		final IBPartnerDAO bPartnerPA = Services.get(IBPartnerDAO.class);
		I_AD_User defaultContact;
		try
		{
			final I_C_BPartner orgBPartner = bPartnerPA.retrieveOrgBPartner(ctx, orgId, I_C_BPartner.class, trxName);
			defaultContact = bPartnerPA.retrieveDefaultContactOrNull(orgBPartner, I_AD_User.class);
		}
		catch (OrgHasNoBPartnerLinkException e)
		{
			defaultContact = null;
		}
		return defaultContact;
	}

}
