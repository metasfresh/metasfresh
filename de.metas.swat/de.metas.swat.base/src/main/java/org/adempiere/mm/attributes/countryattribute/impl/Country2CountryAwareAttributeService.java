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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.countryattribute.ICountryAttributeDAO;
import org.adempiere.mm.attributes.countryattribute.ICountryAware;
import org.adempiere.mm.attributes.countryattribute.ICountryAwareAttributeService;
import org.adempiere.mm.attributes.exceptions.AttributeRestrictedException;
import org.adempiere.mm.attributes.exceptions.NoAttributeGeneratorException;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

public class Country2CountryAwareAttributeService implements ICountryAwareAttributeService
{
	public static final transient Country2CountryAwareAttributeService instance = new Country2CountryAwareAttributeService();
	
	private static final String MSG_NoCountryAttribute = "de.metas.swat.CountryAttribute.error";
	
	private Country2CountryAwareAttributeService()
	{
		super();
	}
	
	@Override
	public int getM_Attribute_ID(final ICountryAware countryAware)
	{
		final int adClientId = countryAware.getAD_Client_ID();
		final int adOrgId = countryAware.getAD_Org_ID();
		final int countryAttributeId = Services.get(ICountryAttributeDAO.class).retrieveCountryAttributeId(adClientId, adOrgId);
		return countryAttributeId;
	}


	@Override
	public I_M_AttributeValue getCreateAttributeValue(final IContextAware context, final ICountryAware countryAware)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();
		final I_C_Country country = countryAware.getC_Country();
		final boolean isSOTrx = countryAware.isSOTrx();
		
		final I_M_AttributeValue attributeValue = Services.get(ICountryAttributeDAO.class).retrieveAttributeValue(ctx, country);

		final AttributeAction attributeAction = Services.get(IAttributesBL.class).getAttributeAction(ctx);
		if (attributeValue == null)
		{
			if (attributeAction == AttributeAction.Error)
			{
				throw new AdempiereException(MSG_NoCountryAttribute, new Object[] { country.getName() });
			}
			else if (attributeAction == AttributeAction.GenerateNew)
			{
				final I_M_Attribute countryAttribute = Services.get(ICountryAttributeDAO.class).retrieveCountryAttribute(ctx);
				final IAttributeValueGenerator generator = Services.get(IAttributesBL.class).getAttributeValueGenerator(countryAttribute);

				if (generator == null)
				{
					throw new NoAttributeGeneratorException(country.getCountryCode());
				}

				return generator.generateAttributeValue(ctx, I_C_Country.Table_ID, country.getC_Country_ID(), false, trxName); // SO trx doesn't matter here

			}

			else if (attributeAction == AttributeAction.Ignore)
			{
				// Ignore: do not throw error, no not generate new attribute
			}
			else
			{
				throw new AdempiereException("@NotSupported@ AttributeAction " + attributeAction);
			}
		}
		else
		{
			if (!Services.get(IAttributesBL.class).isSameTrx(attributeValue, isSOTrx))
			{
				if (attributeAction == AttributeAction.Error)
				{
					throw new AttributeRestrictedException(ctx, isSOTrx, attributeValue, country.getCountryCode());
				}

				// We have an attribute value, but it is marked for a different transaction. Change type to "null", to make it available for both.
				attributeValue.setAvailableTrx(null);
				InterfaceWrapperHelper.save(attributeValue);
			}
		}

		return attributeValue;
	}
}
