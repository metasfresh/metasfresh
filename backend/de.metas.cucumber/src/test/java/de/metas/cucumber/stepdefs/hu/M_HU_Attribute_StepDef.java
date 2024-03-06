/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.hu;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.attribute.M_Attribute_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_HU_Attribute_StepDef
{
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);

	private final M_HU_StepDefData huTable;
	private final M_Attribute_StepDefData attributeTable;

	public M_HU_Attribute_StepDef(@NonNull final M_HU_StepDefData huTable, @NonNull final M_Attribute_StepDefData attributeTable)
	{
		this.huTable = huTable;
		this.attributeTable = attributeTable;
	}

	@And("M_HU_Attribute is changed")
	public void m_hu_attribute_is_changed(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			changeHUAttribute(tableRow);
		}
	}

	@And("M_HU_Attribute is validated")
	public void validate_m_hu_attribute(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			validateHUAttribute(tableRow);
		}
	}

	@And("update M_HU_Attribute:")
	public void update_M_HU_Attribute(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_Attribute.COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_HU hu = huTable.get(huIdentifier);

			final String attributeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_Attribute.COLUMNNAME_M_Attribute_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Attribute attribute = attributeTable.get(attributeIdentifier);

			final BigDecimal valueNumber = DataTableUtil.extractBigDecimalForColumnName(row, "OPT." + I_M_HU_Attribute.COLUMNNAME_ValueNumber);

			huAttributesBL.updateHUAttributeRecursive(HuId.ofRepoId(hu.getM_HU_ID()), AttributeCode.ofString(attribute.getValue()), valueNumber, null);
		}
	}

	private void validateHUAttribute(@NonNull final Map<String, String> tableRow)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_Attribute.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int huId = huTable.getOptional(huIdentifier)
				.map(I_M_HU::getM_HU_ID)
				.orElseGet(() -> Integer.parseInt(huIdentifier));

		final I_M_HU huRecord = InterfaceWrapperHelper.load(huId, I_M_HU.class);
		assertThat(huRecord).isNotNull();

		final String attributeCodeString = DataTableUtil.extractStringForColumnName(tableRow, I_M_Attribute.COLUMNNAME_M_Attribute_ID + "." + I_M_Attribute.COLUMNNAME_Value);
		final AttributeCode attributeCode = AttributeCode.ofString(attributeCodeString);

		final I_M_Attribute attributeRecord = attributeDAO.retrieveAttributeByValueOrNull(attributeCode);
		assertThat(attributeRecord).isNotNull();

		final AttributeId attributeId = AttributeId.ofRepoId(attributeRecord.getM_Attribute_ID());
		final I_M_HU_Attribute huAttribute = huAttributesDAO.retrieveAttribute(huRecord, attributeId);
		assertThat(huAttribute).isNotNull();

		final BigDecimal valueNumber = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_HU_Attribute.COLUMNNAME_ValueNumber);
		assertThat(valueNumber).isEqualByComparingTo(huAttribute.getValueNumber());
	}

	private void changeHUAttribute(@NonNull final Map<String, String> tableRow)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_Attribute.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int huID = huTable.getOptional(huIdentifier)
				.map(I_M_HU::getM_HU_ID)
				.orElseGet(() -> Integer.parseInt(huIdentifier));

		final I_M_HU huRecord = InterfaceWrapperHelper.load(huID, I_M_HU.class);
		assertThat(huRecord).isNotNull();

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IAttributeStorageFactory attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory(storageFactory);

		final IAttributeStorage attributesStorage = attributeStorageFactory.getAttributeStorage(huRecord);
		attributesStorage.setSaveOnChange(true);

		final String attributeCodeString = DataTableUtil.extractStringForColumnName(tableRow, I_M_Attribute.COLUMNNAME_M_Attribute_ID + "." + I_M_Attribute.COLUMNNAME_Value);
		final AttributeCode attributeCode = AttributeCode.ofString(attributeCodeString);

		final I_M_Attribute attributeRecord = attributesStorage.getAttributeByValueKeyOrNull(attributeCode);
		assertThat(attributeRecord).isNotNull();

		final BigDecimal valueNumber = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_M_HU_Attribute.COLUMNNAME_ValueNumber);

		if (valueNumber != null)
		{
			attributesStorage.setValue(attributeRecord, valueNumber);
		}
	}
}
