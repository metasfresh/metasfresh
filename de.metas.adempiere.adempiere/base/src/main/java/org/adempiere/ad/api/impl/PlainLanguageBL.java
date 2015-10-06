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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.bpartner.service.OrgHasNoBPartnerLinkException;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Language;
import org.compiere.util.Language;

public class PlainLanguageBL implements ILanguageBL
{
	@Override
	public List<I_AD_Language> getAvailableLanguages(Properties ctx)
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

	@Override
	public I_AD_Language getByAD_Language(final Properties ctx, final String adLanguage)
	{
		return POJOLookupMap.get().getFirstOnly(I_AD_Language.class, new IQueryFilter<I_AD_Language>()
		{
			@Override
			public boolean accept(final I_AD_Language pojo)
			{
				return Check.equals(pojo.getAD_Language(), adLanguage);
			}
		});
	}
}
