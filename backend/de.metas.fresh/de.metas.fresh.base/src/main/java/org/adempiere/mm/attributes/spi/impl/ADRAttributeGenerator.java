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

import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.fresh.model.I_C_BPartner;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IADRAttributeBL;
import org.adempiere.mm.attributes.api.IADRAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.AbstractAttributeValueGenerator;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import java.util.Properties;

public class ADRAttributeGenerator extends AbstractAttributeValueGenerator
{

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_List;
	}

	/**
	 * @return <code>false</code>, because ADR is a List attribute.
	 */
	@Override
	public boolean canGenerateValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return false;
	}

	/**
	 * Creates an ADR attribute value for the C_BPartner which is specified by <code>tableId</code> and <code>recordId</code>.
	 */
	@Override
	public AttributeListValue generateAttributeValue(final Properties ctx, final int tableId, final int recordId, final boolean isSOTrx, final String trxName)
	{
		final IContextAware context = new PlainContextAware(ctx, trxName);
		final ITableRecordReference record = TableRecordReference.of(tableId, recordId);
		final I_C_BPartner partner = record.getModel(context, I_C_BPartner.class);
		Check.assumeNotNull(partner, "partner not null");

		final I_M_Attribute adrAttribute = Services.get(IADRAttributeDAO.class).retrieveADRAttribute(partner);
		if (adrAttribute == null)
		{
			// ADR Attribute was not configured, nothing to do
			return null;
		}

		final String adrRegionValue = Services.get(IADRAttributeBL.class).getADRForBPartner(partner, isSOTrx);
		if (Check.isEmpty(adrRegionValue, true))
		{
			return null;
		}

		//
		// Fetched AD_Ref_List record
		final ADReferenceService adReferenceService = ADReferenceService.get();
		final ADRefListItem adRefList = adReferenceService.retrieveListItemOrNull(I_C_BPartner.ADRZertifizierung_L_AD_Reference_ID, adrRegionValue);
		Check.assumeNotNull(adRefList, "adRefList not null");

		final String adrRegionName = adRefList.getName().getDefaultValue();

		return Services.get(IAttributeDAO.class).createAttributeValue(AttributeListValueCreateRequest.builder()
				.attributeId(AttributeId.ofRepoId(adrAttribute.getM_Attribute_ID()))
				.value(adrRegionValue)
				.name(adrRegionName)
				.build());
	}
}
