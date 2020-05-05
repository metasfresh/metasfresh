package org.adempiere.mm.attributes.api.impl;

/*
 * #%L
 * de.metas.fresh.base
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
import org.adempiere.mm.attributes.api.IInAusLandAttributeBL;
import org.adempiere.mm.attributes.api.IInAusLandAttributeDAO;
import org.adempiere.mm.attributes.countryattribute.ICountryAware;
import org.adempiere.mm.attributes.exceptions.AttributeRestrictedException;
import org.adempiere.mm.attributes.exceptions.NoAttributeGeneratorException;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_Attribute;

import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.util.Check;
import de.metas.util.Services;

public class InAusLandAttributeBL implements IInAusLandAttributeBL
{

	private static final AdMessageKey MSG_NoInAusLandAttribute = AdMessageKey.of("de.metas.fresh.InAuslandAttribute.error");

	/**
	 * Switzerland's C_Country_ID.
	 * 
	 * NOTE: basically if the C_Country_ID is Switzerland, we consider the {@link #ATTRIBUTEVALUE_INLAND}, else we go with {@link #ATTRIBUTEVALUE_AUSLAND}.
	 */
	// FIXME: hardcoded
	// NOTE: public for testing
	public static final int COUNTRY_ID_SCHWEIZ_ID = CountryId.SWITZERLAND.getRepoId();
	public static final String ATTRIBUTEVALUE_INLAND = "Inland";
	public static final String ATTRIBUTEVALUE_AUSLAND = "Ausland";

	@Override
	public AttributeId getAttributeId(final ICountryAware countryAware)
	{
		final IInAusLandAttributeDAO inAusLandAttributeDAO = Services.get(IInAusLandAttributeDAO.class);

		final int adClientId = countryAware.getAD_Client_ID();
		final int adOrgId = countryAware.getAD_Org_ID();
		final AttributeId inAusLandAttributeId = inAusLandAttributeDAO.retrieveInAusLandAttributeId(adClientId, adOrgId);
		return inAusLandAttributeId;
	}

	@Override
	public AttributeListValue getCreateAttributeValue(final IContextAware context, final ICountryAware countryAware)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();

		Check.assumeNotNull(countryAware, "countryAware not null");
		final I_C_Country country = countryAware.getC_Country();
		Check.assumeNotNull(country, "country not null");
		final SOTrx soTrx = SOTrx.ofBoolean(countryAware.isSOTrx());

		final String inAusLand = getAttributeStringValueByCountryId(country.getC_Country_ID());

		final AttributeListValue existingAttributeValue = Services.get(IInAusLandAttributeDAO.class).retrieveInAusLandAttributeValue(ctx, inAusLand);

		final AttributeAction attributeAction = Services.get(IAttributesBL.class).getAttributeAction(ctx);
		if (existingAttributeValue == null)
		{
			if (attributeAction == AttributeAction.Error)
			{
				throw new AdempiereException(MSG_NoInAusLandAttribute, new Object[] { inAusLand });
			}
			else if (attributeAction == AttributeAction.GenerateNew)
			{
				final I_M_Attribute inAusLandAttribute = Services.get(IInAusLandAttributeDAO.class).retrieveInAusLandAttribute(ctx);
				final IAttributeValueGenerator generator = Services.get(IAttributesBL.class).getAttributeValueGenerator(inAusLandAttribute);

				if (generator == null)
				{
					throw new NoAttributeGeneratorException(country.getCountryCode());
				}

				return generator.generateAttributeValue(ctx, I_C_Country.Table_ID, country.getC_Country_ID(), false, trxName); // SO Trx doesn't matter here
			}
			else if (attributeAction == AttributeAction.Ignore)
			{
				// Ignore: do now throw error, no not generate new attribute
			}
			else
			{
				throw new AdempiereException("@NotSupported@ AttributeAction " + attributeAction);
			}

			return existingAttributeValue;
		}
		else
		{
			if (!existingAttributeValue.isMatchingSOTrx(soTrx))
			{
				if (attributeAction == AttributeAction.Error)
				{
					throw new AttributeRestrictedException(ctx, soTrx, existingAttributeValue, country.getCountryCode());
				}

				// We have an attribute value, but it is marked for a different transaction. Change type to "null", to make it available for both.
				final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
				return attributesRepo.changeAttributeValue(AttributeListValueChangeRequest.builder()
						.id(existingAttributeValue.getId())
						.availableForTrx(AttributeListValueTrxRestriction.ANY_TRANSACTION)
						.build());
			}
			else
			{
				return existingAttributeValue;
			}
		}
	}

	@Override
	public String getAttributeStringValueByCountryId(final int countryId)
	{
		if (CountryId.SWITZERLAND.getRepoId() == countryId)
		{
			return ATTRIBUTEVALUE_INLAND;
		}
		else
		{
			return ATTRIBUTEVALUE_AUSLAND;
		}
	}

}
