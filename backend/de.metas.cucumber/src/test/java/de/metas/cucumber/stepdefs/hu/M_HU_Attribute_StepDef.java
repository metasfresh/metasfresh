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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_Attribute_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_Attribute.COLUMNNAME_AttributeValueType;

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
		DataTableRows.of(dataTable).forEach(this::validateHUAttribute);
	}

	@And("update M_HU_Attribute recursive:")
	public void update_M_HU_Attribute_recursive(@NonNull final DataTable dataTable)
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

	@And("update M_HU_Attribute:")
	public void update_M_HU_Attribute(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			updateHuAttribute(row);
		}
	}

	private void updateHuAttribute(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_Attribute.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_HU huRecord = huTable.get(huIdentifier);

		final AttributeId attributeId = AttributeId.ofRepoId(DataTableUtil.extractIntForColumnName(row, I_M_HU_Attribute.COLUMNNAME_M_Attribute_ID));

		final I_M_HU_Attribute huAttributeRecord = huAttributesDAO.retrieveAttribute(huRecord, attributeId);
		Assertions.assertThat(huAttributeRecord).isNotNull();

		final AttributeValueType attributeValueType = AttributeValueType.ofCode(DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AttributeValueType));

		switch (attributeValueType)
		{
			case DATE:
				final Timestamp attributeValueTimestamp = DataTableUtil.extractDateTimestampForColumnName(row, I_M_HU_Attribute.COLUMNNAME_Value);
				huAttributeRecord.setValueDate(attributeValueTimestamp);
				break;
			case STRING:
				final String attributeValueString = DataTableUtil.extractStringForColumnName(row, I_M_HU_Attribute.COLUMNNAME_Value);
				huAttributeRecord.setValue(attributeValueString);
				break;
			default:
				throw new AdempiereException(COLUMNNAME_AttributeValueType + " is not supported: " + attributeValueType);
		}

		saveRecord(huAttributeRecord);
	}

	private void validateHUAttribute(@NonNull final DataTableRow row)
	{
		final List<StepDefDataIdentifier> huIdentifiers = row.getAsIdentifier(I_M_HU_Attribute.COLUMNNAME_M_HU_ID).toCommaSeparatedList();
		assertThat(huIdentifiers).as("M_HU_ID").isNotEmpty();

		for (final StepDefDataIdentifier huIdentifier : huIdentifiers)
		{
			SharedTestContext.put("huIdentifier", huIdentifier);

			final int huId = huIdentifier.lookupIn(huTable).getM_HU_ID();
			SharedTestContext.put("huId", huId);

			final I_M_HU huRecord = InterfaceWrapperHelper.load(huId, I_M_HU.class);
			assertThat(huRecord).isNotNull();

			final String attributeCodeString = row.getAsString(I_M_Attribute.COLUMNNAME_M_Attribute_ID + "." + I_M_Attribute.COLUMNNAME_Value);
			final AttributeCode attributeCode = AttributeCode.ofString(attributeCodeString);
			SharedTestContext.put("attributeCode", attributeCode);

			final I_M_Attribute attributeRecord = attributeDAO.retrieveAttributeByValue(attributeCode);

			final AttributeId attributeId = AttributeId.ofRepoId(attributeRecord.getM_Attribute_ID());
			final I_M_HU_Attribute huAttribute = huAttributesDAO.retrieveAttribute(huRecord, attributeId);
			assertThat(huAttribute).as("M_HU_Attribute exists").isNotNull();
			SharedTestContext.put("huAttribute", () -> toString(huAttribute));

			row.getAsOptionalString(I_M_HU_Attribute.COLUMNNAME_Value)
					.ifPresent(valueString -> {
						final String valueStringNorm = !valueString.equalsIgnoreCase("-") ? valueString : null;
						assertThat(huAttribute.getValue()).as("Value(string)").isEqualTo(valueStringNorm);
					});
			row.getAsOptionalBigDecimal(I_M_HU_Attribute.COLUMNNAME_ValueNumber)
					.ifPresent(valueNumber -> {
						assertThat(huAttribute.getValueNumber()).as("ValueNumber").isEqualByComparingTo(valueNumber);
					});
			row.getAsOptionalBoolean(I_M_HU_Attribute.COLUMNNAME_ValueNumber + ".IsNull")
					.ifPresent(expectedIsNull -> {
						final boolean actualIsNull = InterfaceWrapperHelper.isNull(huAttribute, I_M_HU_Attribute.COLUMNNAME_ValueNumber);
						assertThat(expectedIsNull).as("ValueNumber.IsNull").isEqualTo(actualIsNull);
					});
			row.getAsOptionalString(I_M_HU_Attribute.COLUMNNAME_ValueDate)
					.ifPresent(valueString -> {
						final LocalDate valueDate = !valueString.equalsIgnoreCase("-") ? LocalDate.parse(valueString) : null;
						assertThat(TimeUtil.asLocalDate(huAttribute.getValueDate())).as("ValueDate").isEqualTo(valueDate);
					});
		}
	}

	private static String toString(final I_M_HU_Attribute huAttribute)
	{
		if (huAttribute == null)
		{
			return null;
		}
		return "M_HU_Attribute_ID=" + huAttribute.getM_Attribute_ID()
				+ ", Value=" + huAttribute.getValue()
				+ ", ValueNumber=" + huAttribute.getValueNumber()
				+ ", ValueDate=" + huAttribute.getValueDate();
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
