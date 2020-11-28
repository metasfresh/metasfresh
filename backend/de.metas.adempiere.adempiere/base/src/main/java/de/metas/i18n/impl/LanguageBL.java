package de.metas.i18n.impl;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.OrgHasNoBPartnerLinkException;
import de.metas.i18n.ADLanguageList;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.ILanguageDAO;
import de.metas.i18n.Language;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;

public class LanguageBL implements ILanguageBL
{
	@Override
	public ADLanguageList getAvailableLanguages()
	{
		return Services.get(ILanguageDAO.class).retrieveAvailableLanguages();
	}

	@Override
	public String getOrgAD_Language(final Properties ctx) throws OrgHasNoBPartnerLinkException
	{
		final int orgId = Env.getAD_Org_ID(ctx);
		return getOrgAD_Language(ctx, orgId);
	}

	@Override
	public String getOrgAD_Language(final Properties ctx, final int orgRepoId) throws OrgHasNoBPartnerLinkException
	{
		final OrgId orgId = OrgId.ofRepoIdOrAny(orgRepoId);
		
		//
		// Check organization Language (if found);
		if (orgId.isRegular())
		{
			final I_C_BPartner bpOrg = Services.get(IBPartnerDAO.class).retrieveOrgBPartner(ctx, orgId.getRepoId(), I_C_BPartner.class, ITrx.TRXNAME_None);
			final String orgAD_Language = bpOrg.getAD_Language();
			if (orgAD_Language != null)
			{
				return orgAD_Language;
			}
		}

		//
		// Check client language (if found)
		final ClientId clientId;
		if (orgId.isRegular())
		{
			clientId = Services.get(IOrgDAO.class).getClientIdByOrgId(orgId);
		}
		else // AD_Org_ID <= 0
		{
			clientId = Env.getClientId(ctx);
		}
		final String clientAD_Language = getClientAD_Language(ctx, clientId.getRepoId());
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
