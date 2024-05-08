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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeListValueTrxRestriction;
import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.AttributeListValueChangeRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.countryattribute.ICountryAttributeDAO;
import org.adempiere.mm.attributes.countryattribute.ICountryAware;
import org.adempiere.mm.attributes.countryattribute.ICountryAwareAttributeService;
import org.adempiere.mm.attributes.exceptions.AttributeRestrictedException;
import org.adempiere.mm.attributes.exceptions.NoAttributeGeneratorException;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_Attribute;

import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.util.Services;

public class Country2CountryAwareAttributeService implements ICountryAwareAttributeService
{
	public static final transient Country2CountryAwareAttributeService instance = new Country2CountryAwareAttributeService();

	private static final AdMessageKey MSG_NoCountryAttribute = AdMessageKey.of("de.metas.swat.CountryAttribute.error");

	private Country2CountryAwareAttributeService()
	{
		super();
	}

	@Override
	public AttributeId getAttributeId(final ICountryAware countryAware)
	{
		final int adClientId = countryAware.getAD_Client_ID();
		final int adOrgId = countryAware.getAD_Org_ID();
		final AttributeId countryAttributeId = Services.get(ICountryAttributeDAO.class).retrieveCountryAttributeId(adClientId, adOrgId);
		return countryAttributeId;
	}

	@Override
	public AttributeListValue getCreateAttributeValue(final IContextAware context, final ICountryAware countryAware)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();
		final I_C_Country country = countryAware.getC_Country();
		final SOTrx soTrx = SOTrx.ofBoolean(countryAware.isSOTrx());

		final AttributeListValue attributeValue = Services.get(ICountryAttributeDAO.class).retrieveAttributeValue(ctx, country, false/* includeInactive */);

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

			return attributeValue;
		}
		else
		{
			if (!attributeValue.isMatchingSOTrx(soTrx))
			{
				if (attributeAction == AttributeAction.Error)
				{
					throw new AttributeRestrictedException(ctx, soTrx, attributeValue, country.getCountryCode());
				}

				// We have an attribute value, but it is marked for a different transaction. Change type to "null", to make it available for both.
				final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
				return attributesRepo.changeAttributeValue(AttributeListValueChangeRequest.builder()
						.id(attributeValue.getId())
						.availableForTrx(AttributeListValueTrxRestriction.ANY_TRANSACTION)
						.build());
			}
			else
			{
				return attributeValue;
			}
		}

	}
}
