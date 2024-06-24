package de.metas.handlingunits.inventory;

import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;

import static de.metas.handlingunits.HuPackingInstructionsVersionId.VIRTUAL;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

@UtilityClass
public class InventoryTestHelper
{

	public static final int AV1_ID = 10;
	public static final int AV2_ID = 11;
	public static final int AV3_ID = 12;

	public static I_M_AttributeSetInstance createAsi()
	{
		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();

		final I_M_Attribute a1 = createStorageRelevantAttribute(attributesTestHelper, "a1");
		final AttributeListValue av1 = createAttributeValue(attributesTestHelper, a1, "av1", AV1_ID);

		final I_M_Attribute a2 = createStorageRelevantAttribute(attributesTestHelper, "a2");
		final AttributeListValue av2 = createAttributeValue(attributesTestHelper, a2, "av2", AV2_ID);

		final I_M_Attribute a3 = createStorageRelevantAttribute(attributesTestHelper, "a3");
		final AttributeListValue av3 = createAttributeValue(attributesTestHelper, a3, "av3", AV3_ID);

		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asi);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId, av1);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId, av2);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId, av3);
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

	private static AttributeListValue createAttributeValue(
			@NonNull final AttributesTestHelper attributesTestHelper,
			@NonNull final I_M_Attribute attributeRecord,
			String valueName,
			int valueRepoId)
	{
		return attributesTestHelper.createM_AttributeValue(attributeRecord, valueRepoId, valueName);
	}

	public static DocTypeId createDocType(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseAndSubType.getDocBaseType().getCode());
		docType.setDocSubType(docBaseAndSubType.getDocSubType());
		saveRecord(docType);
		return DocTypeId.ofRepoId(docType.getC_DocType_ID());
	}

	public static void createStorageFor(final ProductId productId, final Quantity qtyCount, final HuId huId)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setM_HU_ID(huId.getRepoId());
		hu.setM_HU_PI_Version_ID(VIRTUAL.getRepoId());
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		Services.get(IHandlingUnitsDAO.class).saveHU(hu);

		final I_M_HU_Storage huStorage = newInstance(I_M_HU_Storage.class);
		huStorage.setM_HU(hu);
		huStorage.setM_Product_ID(productId.getRepoId());
		huStorage.setC_UOM_ID(qtyCount.getUomId().getRepoId());
		huStorage.setQty(qtyCount.toBigDecimal());
		Services.get(IHandlingUnitsBL.class)
				.getStorageFactory()
				.getHUStorageDAO()
				.save(huStorage);
	}
}
