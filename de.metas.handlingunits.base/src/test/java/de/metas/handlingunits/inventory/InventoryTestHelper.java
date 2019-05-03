package de.metas.handlingunits.inventory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;

import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class InventoryTestHelper
{

	public static final int AV1_ID = 10;
	public static final int AV2_ID = 11;
	public static final int AV3_ID = 12;

	public static I_M_AttributeSetInstance createAsi()
	{
		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();

		final I_M_Attribute a1 = createStorageRelevantAttribute(attributesTestHelper, "a1");
		final I_M_AttributeValue av1 = createAttributeValue(attributesTestHelper, a1, "av1", AV1_ID);

		final I_M_Attribute a2 = createStorageRelevantAttribute(attributesTestHelper, "a2");
		final I_M_AttributeValue av2 = createAttributeValue(attributesTestHelper, a2, "av2", AV2_ID);

		final I_M_Attribute a3 = createStorageRelevantAttribute(attributesTestHelper, "a3");
		final I_M_AttributeValue av3 = createAttributeValue(attributesTestHelper, a3, "av3", AV3_ID);

		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asi);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, av1);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, av2);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, av3);
		return asi;
	}

	public static void createAttributeValues()
	{
		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();
		org.adempiere.ad.wrapper.POJOLookupMap.get().dumpStatus();
		final I_M_Attribute a1 = createStorageRelevantAttribute(attributesTestHelper, "a1");
		createAttributeValue(attributesTestHelper, a1, "av1", AV1_ID);

		final I_M_Attribute a2 = createStorageRelevantAttribute(attributesTestHelper, "a2");
		createAttributeValue(attributesTestHelper, a2, "av2", AV2_ID);

		final I_M_Attribute a3 = createStorageRelevantAttribute(attributesTestHelper, "a3");
		createAttributeValue(attributesTestHelper, a3, "av3", AV3_ID);
	}

	private static I_M_Attribute createStorageRelevantAttribute(
			@NonNull final AttributesTestHelper attributesTestHelper,
			@NonNull final String name)
	{
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute(name, X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribute.setIsStorageRelevant(true);
		saveRecord(attribute);
		return attribute;
	}

	private static I_M_AttributeValue createAttributeValue(
			@NonNull final AttributesTestHelper attributesTestHelper,
			@NonNull final I_M_Attribute attributeRecord,
			String valueName,
			int valueRepoId)
	{
		final I_M_AttributeValue attributeValueRecord = attributesTestHelper.createM_AttributeValue(attributeRecord, valueRepoId, valueName);
		return attributeValueRecord;
	}
}
