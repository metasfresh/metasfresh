package org.adempiere.ad.api.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.ad.language.ILanguageDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.bpartner.service.OrgHasNoBPartnerLinkException;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MOrg;
import org.compiere.util.Env;
import org.compiere.util.Language;

public class LanguageBL implements ILanguageBL
{
	@Override
	public List<I_AD_Language> getAvailableLanguages(final Properties ctx)
	{
		final int clientId = Env.getAD_Client_ID(ctx);
		return Services.get(ILanguageDAO.class).retrieveAvailableLanguages(ctx, clientId);
	}

	@Override
	public String getOrgAD_Language(final Properties ctx) throws OrgHasNoBPartnerLinkException
	{
		final int orgId = Env.getAD_Org_ID(ctx);
		return getOrgAD_Language(ctx, orgId);
	}

	@Override
	public String getOrgAD_Language(final Properties ctx, final int AD_Org_ID) throws OrgHasNoBPartnerLinkException
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

	public String getClientAD_Language(final Properties ctx, final int clientId)
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
		final String baseAD_Language = Language.getBaseAD_Language();
		return baseAD_Language;
	}
}
