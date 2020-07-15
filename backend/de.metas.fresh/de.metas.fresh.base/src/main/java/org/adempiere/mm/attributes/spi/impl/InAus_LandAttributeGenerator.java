/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.mm.attributes.spi.impl;

import java.util.Properties;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IInAusLandAttributeBL;
import org.adempiere.mm.attributes.api.IInAusLandAttributeDAO;
import org.adempiere.mm.attributes.spi.AbstractAttributeValueGenerator;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import de.metas.util.Check;
import de.metas.util.Services;

public class InAus_LandAttributeGenerator extends AbstractAttributeValueGenerator
{
	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_List;
	}

	/**
	 * @return <code>false</code>, because In/Ausland is a List attribute.
	 */
	@Override
	public boolean canGenerateValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public AttributeListValue generateAttributeValue(Properties ctx, int tableId, int recordId, boolean isSOTrx, String trxName)
	{
		final ITableRecordReference record = TableRecordReference.of(tableId, recordId);
		Check.errorUnless(I_C_Country.Table_Name.equals(record.getTableName()),
				"Only C_Country table is supported. Given param 'tableId' is the ID of AD_Table {}", record.getTableName());
		final int countryId = record.getRecord_ID();

		final I_M_Attribute inAusLandAttribute = Services.get(IInAusLandAttributeDAO.class).retrieveInAusLandAttribute(ctx);
		if (inAusLandAttribute == null)
		{
			// In/Aus Land attribute was not configured => do nothing
			return null;
		}

		final String inAusLand = Services.get(IInAusLandAttributeBL.class).getAttributeStringValueByCountryId(countryId);

		return Services.get(IAttributeDAO.class).createAttributeValue(AttributeListValueCreateRequest.builder()
				.attributeId(AttributeId.ofRepoId(inAusLandAttribute.getM_Attribute_ID()))
				.value(inAusLand)
				.name(inAusLand)
				.build());
	}
}
