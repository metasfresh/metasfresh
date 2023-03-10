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

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.countryattribute.ICountryAttributeDAO;
import org.adempiere.mm.attributes.spi.AbstractAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;

import de.metas.util.Check;
import de.metas.util.Services;

public class CountryAttributeGenerator extends AbstractAttributeValueGenerator
{
	private final int c_countryTableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Country.Table_Name);

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_List;
	}

	/**
	 * @return <code>false</code>, because "country" is a List attribute.
	 */
	@Override
	public boolean canGenerateValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public AttributeListValue generateAttributeValue(final Properties ctx, final int tableId, final int recordId, final boolean isSOTrx, final String trxName)
	{
		Check.assume( c_countryTableId == tableId, "Wrong table.");
		final I_C_Country country = InterfaceWrapperHelper.create(ctx, recordId, I_C_Country.class, trxName);

		final AttributeId attributeId = Services.get(ICountryAttributeDAO.class).retrieveCountryAttributeId(Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));

		return Services.get(IAttributeDAO.class).createAttributeValue(AttributeListValueCreateRequest.builder()
				.attributeId(attributeId)
				.value(country.getCountryCode())
				.name(country.getName())
				.build());
	}
}
