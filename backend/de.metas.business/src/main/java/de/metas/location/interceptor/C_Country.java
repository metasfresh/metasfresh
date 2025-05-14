package de.metas.location.interceptor;

import de.metas.location.AddressDisplaySequence;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeListValueChangeRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.countryattribute.ICountryAttributeDAO;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.Properties;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Interceptor(I_C_Country.class)
@Component
public class C_Country
{
	private final AdTableId c_countryTableId = Services.get(IADTableDAO.class).retrieveAdTableId(I_C_Country.Table_Name);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onCreateCountry(final I_C_Country country)
	{
		final Properties ctx = Env.getCtx();
		final AttributeListValue attributeValue = getAttributeValue(country);
		if (attributeValue != null)
		{
			setCountryAttributeAsActive(country, true);
		}
		else
		{
			final IAttributesBL attributesService = Services.get(IAttributesBL.class);
			final ICountryAttributeDAO countryAttributeDAO = Services.get(ICountryAttributeDAO.class);

			final I_M_Attribute countryAttribute = countryAttributeDAO.retrieveCountryAttribute(ctx);
			final IAttributeValueGenerator generator = attributesService.getAttributeValueGenerator(countryAttribute);
			generator.generateAttributeValue(ctx, c_countryTableId.getRepoId(), country.getC_Country_ID(), false, ITrx.TRXNAME_ThreadInherited);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Country.COLUMNNAME_IsActive)
	public void onChangeCountryIsActive(final I_C_Country country)
	{
		setCountryAttributeAsActive(country, country.isActive());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Country.COLUMNNAME_Name)
	public void onChangeCountryName(final I_C_Country country)
	{
		setCountryAttributeName(country);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDeleteCountry(final I_C_Country country)
	{
		setCountryAttributeAsActive(country, false);
	}

	private void setCountryAttributeAsActive(final I_C_Country country, final boolean isActive)
	{
		final AttributeListValue existingAttributeValue = getAttributeValue(country);
		if (existingAttributeValue != null)
		{
			final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
			attributesRepo.changeAttributeValue(AttributeListValueChangeRequest.builder()
					.id(existingAttributeValue.getId())
					.active(isActive)
					.build());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_C_Country.COLUMNNAME_DisplaySequence, I_C_Country.COLUMNNAME_DisplaySequenceLocal })
	public void onChangeCountryDisplaySequence(@NonNull final I_C_Country country)
	{
		assertValidDisplaySequences(country);
	}

	public void assertValidDisplaySequences(@NonNull final I_C_Country country)
	{
		AddressDisplaySequence.ofNullable(country.getDisplaySequence()).assertValid();
		AddressDisplaySequence.ofNullable(country.getDisplaySequenceLocal()).assertValid();
	}

	private void setCountryAttributeName(@NonNull final I_C_Country country)
	{
		final AttributeListValue existingAttributeValue = getAttributeValue(country);
		if (existingAttributeValue != null)
		{
			final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
			attributesRepo.changeAttributeValue(AttributeListValueChangeRequest.builder()
					.id(existingAttributeValue.getId())
					.name(country.getName())
					.build());
		}
	}

	private AttributeListValue getAttributeValue(final I_C_Country country)
	{
		return Services.get(ICountryAttributeDAO.class).retrieveAttributeValue(Env.getCtx(), country, true);
	}
}
