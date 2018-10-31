package org.adempiere.mm.attributes.countryattribute.impl;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.countryattribute.ICountryAttributeDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.Env;

import de.metas.util.Check;
import de.metas.util.Services;

public class CountryAttributeDAO implements ICountryAttributeDAO
{
	public static final String SYSCONFIG_CountryAttribute = "de.metas.swat.CountryAttribute";

	@Override
	public I_M_AttributeValue retrieveAttributeValue(final Properties ctx, final I_C_Country country)
	{
		Check.assumeNotNull(country, "country not null");

		final String countryValue = country.getCountryCode();
		if (Check.isEmpty(countryValue, true))
		{
			return null;
		}

		final I_M_Attribute countryAttribute = retrieveCountryAttribute(ctx);
		if (countryAttribute == null)
		{
			return null;
		}

		//
		// First try: search by CountryCode
		final I_M_AttributeValue attributeValueByCode = Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(countryAttribute, countryValue);
		if (attributeValueByCode != null)
		{
			return attributeValueByCode;
		}
		
		//
		// Second try: Search by country name
		final String countryName = country.getName();
		final I_M_AttributeValue attributeValueByName = Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(countryAttribute, countryName);
		if (attributeValueByName != null)
		{
			return attributeValueByName;
		}

		//
		// Nothing found, just return null
		return null;
	}

	@Override
	public I_M_Attribute retrieveCountryAttribute(final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final AttributeId countryAttributeId = retrieveCountryAttributeId(adClientId, adOrgId);
		if (countryAttributeId == null)
		{
			return null;
		}

		return Services.get(IAttributeDAO.class).getAttributeById(countryAttributeId);
	}

	@Override
	public AttributeId retrieveCountryAttributeId(final int adClientId, final int adOrgId)
	{
		final int countryAttributeId = Services.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_CountryAttribute,
						-1, // defaultValue
						adClientId,
						adOrgId);
		return AttributeId.ofRepoIdOrNull(countryAttributeId);
	}

}
