package org.adempiere.ad.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.bpartner.service.OrgHasNoBPartnerLinkException;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MOrg;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Language;

import de.metas.adempiere.util.CacheCtx;

public class LanguageBL implements ILanguageBL
{
	@Override
	public List<I_AD_Language> getAvailableLanguages(Properties ctx)
	{
		final int clientId = Env.getAD_Client_ID(ctx);
		return retrieveAvailableLanguages(ctx, clientId);
	}

	@Override
	public I_AD_Language getByAD_Language(Properties ctx, String adLanguage)
	{
		//
		// First try getting it from available languages
		for (I_AD_Language lang : getAvailableLanguages(ctx))
		{
			if (lang.getAD_Language().equals(adLanguage))
			{
				return lang;
			}
		}

		//
		// Fallback: retrieve from database
		final I_AD_Language lang = retrieveByAD_Language(ctx, adLanguage);
		return lang;
	}

	@Cached(cacheName = I_AD_Language.Table_Name)
	/* package */ I_AD_Language retrieveByAD_Language(@CacheCtx Properties ctx, String adLanguage)
	{
		if (adLanguage == null)
		{
			return null;
		}
		return new Query(ctx, I_AD_Language.Table_Name, I_AD_Language.COLUMNNAME_AD_Language + "=?", null)
				.setParameters(new Object[] { adLanguage })
				.firstOnly(I_AD_Language.class);
	}

	@Cached(cacheName = I_AD_Language.Table_Name)
	/* package */ List<I_AD_Language> retrieveAvailableLanguages(@CacheCtx final Properties ctx, final int clientId)
	{
		final String whereClause = I_AD_Language.COLUMNNAME_AD_Client_ID + " IN (0, ?)"
				+ " AND (" + I_AD_Language.COLUMNNAME_IsBaseLanguage + "=?" + " OR " + I_AD_Language.COLUMNNAME_IsSystemLanguage + "=?)";
		List<I_AD_Language> list = new Query(ctx, I_AD_Language.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setParameters(clientId, true, true)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_AD_Language.COLUMNNAME_Name)
				.list(I_AD_Language.class);

		return Collections.unmodifiableList(list);
	}

	@Override
	public String getOrgAD_Language(Properties ctx) throws OrgHasNoBPartnerLinkException
	{
		final int orgId = Env.getAD_Org_ID(ctx);
		return getOrgAD_Language(ctx, orgId);
	}

	@Override
	public String getOrgAD_Language(Properties ctx, int AD_Org_ID) throws OrgHasNoBPartnerLinkException
	{
		//
		// Check organization Language (if found);
		if (AD_Org_ID > 0)
		{
			final I_C_BPartner bpOrg = Services.get(IBPartnerDAO.class).retrieveOrgBPartner(ctx, AD_Org_ID, I_C_BPartner.class, ITrx.TRXNAME_None);
			final String orgAD_Language = bpOrg.getAD_Language();
			if (orgAD_Language != null)
			{
				return orgAD_Language;
			}
		}

		//
		// Check client language (if found)
		final int clientId;
		if (AD_Org_ID > 0)
		{
			final I_AD_Org organization = MOrg.get(ctx, AD_Org_ID);
			clientId = organization.getAD_Client_ID();
		}
		else // AD_Org_ID <= 0
		{
			clientId = Env.getAD_Client_ID(ctx);
		}
		final String clientAD_Language = getClientAD_Language(ctx, clientId);
		return clientAD_Language;
	}

	@Override
	public Language getOrgLanguage(final Properties ctx, final int AD_Org_ID) throws OrgHasNoBPartnerLinkException
	{
		final String adLanguage = getOrgAD_Language(ctx, AD_Org_ID);
		if (!Check.isEmpty(adLanguage, true))
		{
			return Language.getLanguage(adLanguage);
		}
		return null;
	}

	public String getClientAD_Language(Properties ctx, int clientId)
	{
		//
		// Check AD_Client Language
		if (clientId >= 0)
		{
			final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(ctx, clientId);
			final String clientAD_Language = client.getAD_Language();
			if (clientAD_Language != null)
			{
				return clientAD_Language;
			}
		}

		// If none of the above was found, return the base language
		final String baseAD_Language = getBaseAD_Language(ctx);
		return baseAD_Language;
	}

	public String getBaseAD_Language(Properties ctx)
	{
		return Language.getBaseAD_Language();
	}
}
