package org.adempiere.ad.api.impl;

import java.util.Properties;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.ad.language.ADLanguageList;
import org.adempiere.bpartner.service.OrgHasNoBPartnerLinkException;
import org.compiere.util.Language;

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
