package de.metas.i18n.impl;

import java.util.Properties;

import org.adempiere.bpartner.service.OrgHasNoBPartnerLinkException;

import de.metas.i18n.ADLanguageList;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.Language;

public class PlainLanguageBL implements ILanguageBL
{
	@Override
	public ADLanguageList getAvailableLanguages()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getOrgAD_Language(Properties ctx) throws OrgHasNoBPartnerLinkException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getOrgAD_Language(Properties ctx, int AD_Org_ID) throws OrgHasNoBPartnerLinkException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Language getOrgLanguage(Properties ctx, int AD_Org_ID) throws OrgHasNoBPartnerLinkException
	{
		throw new UnsupportedOperationException();
	}
}
