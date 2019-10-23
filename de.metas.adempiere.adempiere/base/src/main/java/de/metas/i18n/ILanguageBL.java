/**
 * 
 */
package de.metas.i18n;

import java.util.Properties;

import org.compiere.model.I_AD_Language;

import de.metas.bpartner.service.OrgHasNoBPartnerLinkException;
import de.metas.util.ISingletonService;

/**
 * {@link I_AD_Language} related helpers.
 * 
 * @author tsa
 * 
 */
public interface ILanguageBL extends ISingletonService
{
	ADLanguageList getAvailableLanguages();

	/**
	 * Gets Organization Language.
	 * 
	 * @param ctx
	 * @return AD_Language string
	 * @throws OrgHasNoBPartnerLinkException if the current org has no linked C_BParther to get the language from
	 */
	String getOrgAD_Language(Properties ctx) throws OrgHasNoBPartnerLinkException;

	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @return AD_Language string
	 * @throws OrgHasNoBPartnerLinkException
	 */
	String getOrgAD_Language(Properties ctx, int AD_Org_ID) throws OrgHasNoBPartnerLinkException;

	Language getOrgLanguage(Properties ctx, int AD_Org_ID) throws OrgHasNoBPartnerLinkException;
}
