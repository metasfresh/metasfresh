/**
 * 
 */
package org.adempiere.ad.api;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.bpartner.service.OrgHasNoBPartnerLinkException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Language;
import org.compiere.util.Language;

/**
 * {@link I_AD_Language} related helpers.
 * 
 * @author tsa
 * 
 */
public interface ILanguageBL extends ISingletonService
{
	public List<I_AD_Language> getAvailableLanguages(Properties ctx);

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
