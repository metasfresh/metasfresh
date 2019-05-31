package de.metas.location.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.countryattribute.ICountryAttributeDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

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
@Component("de.metas.location.model.interceptor.C_Country")
public class C_Country
{
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onCreateCountry(final I_C_Country country)
	{
		final Properties ctx = Env.getCtx();
		final I_M_AttributeValue attributeValue = getAttributeValue(country);
		if (attributeValue != null)
		{
			setCountryAttributeAsActive(country, true);
		}
		else
		{
			Services.get(IAttributesBL.class).getAttributeValueGenerator(Services.get(ICountryAttributeDAO.class).retrieveCountryAttribute(ctx)).generateAttributeValue(ctx, I_C_Country.Table_ID, country.getC_Country_ID(), false, ITrx.TRXNAME_ThreadInherited);
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

	private I_M_AttributeValue setCountryAttributeAsActive(final I_C_Country country, final boolean isActive)
	{
		final I_M_AttributeValue attributeValue = getAttributeValue(country);
		if (attributeValue != null)
		{
			attributeValue.setIsActive(isActive);
			save(attributeValue);
		}
		return attributeValue;
	}

	private I_M_AttributeValue setCountryAttributeName(final I_C_Country country)
	{
		final I_M_AttributeValue attributeValue = getAttributeValue(country);
		if (attributeValue != null)
		{
			attributeValue.setName(country.getName());
			save(attributeValue);
		}
		return attributeValue;
	}

	private I_M_AttributeValue getAttributeValue(final I_C_Country country)
	{
		final I_M_AttributeValue attributeValue = Services.get(ICountryAttributeDAO.class).retrieveAttributeValue(Env.getCtx(), country, true/*includeInactive*/);
		return attributeValue;
	}
}
