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

package de.metas.cucumber.stepdefs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.cucumber.stepdefs.resourcetype.S_ResourceType_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_S_ResourceType;

import java.util.HashMap;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.getPO;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class AD_Column_StepDef
{
	private final C_Order_StepDefData orderTable;
	private final S_ResourceType_StepDefData resourceTypeTable;

	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	public AD_Column_StepDef(
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final S_ResourceType_StepDefData resourceTypeTable)
	{
		this.orderTable = orderTable;
		this.resourceTypeTable = resourceTypeTable;
	}

	@Given("^assert defaultValue is (.*) for tableName (.*) and columnName (.*)$")
	public void assertDefaultValue(
			@NonNull final String expectedDefaultValue,
			@NonNull final String tableName,
			@NonNull final String columnName)
	{
		final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
		assertThat(tableId).isNotNull();

		final String defaultValue = queryBL.createQueryBuilder(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName)
				.create()
				.firstOnlyNotNull(I_AD_Column.class)
				.getDefaultValue();

		assertThat(defaultValue).isEqualTo(expectedDefaultValue);
	}

	@And("update AD_Column:")
	public void update_AD_Column(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String tableName = DataTableUtil.extractStringForColumnName(row, "TableName");
			final String columnName = DataTableUtil.extractStringForColumnName(row, "ColumnName");
			final Boolean isRestAPICustomColumn = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_AD_Column.COLUMNNAME_IsRestAPICustomColumn, false);

			if (Boolean.TRUE.equals(isRestAPICustomColumn))
			{
				final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
				assertThat(tableId).isNotNull();

				markAsCustomColumn(tableId, columnName);
			}
		}
	}

	@When("set custom columns for C_Order:")
	public void setCustomColumn_C_Order(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			setC_Order_CustomColumnsValues(row);
		}
	}

	@When("set custom columns for S_ResourceType:")
	public void setCustomColumn_S_ResourceType(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			setS_ResourceType_customColumnsValues(row);
		}
	}

	@Then("validate customColumns:")
	public void validate_customColumns(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String resourceTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_ResourceType.COLUMNNAME_S_ResourceType_ID + "." + TABLECOLUMN_IDENTIFIER);

			final ImmutableMap.Builder<String, Object> columns = ImmutableMap.builder();
			if (Check.isNotBlank(orderIdentifier))
			{
				columns.putAll(validate_C_OrderCustomColumns(orderIdentifier));
			}
			else if (Check.isNotBlank(resourceTypeIdentifier))
			{
				columns.putAll(validate_S_ResourceType_customColumns(resourceTypeIdentifier));
			}

			final String customColumnJSONValue = DataTableUtil.extractStringForColumnName(row, "CustomColumnJSONValue");
			final String retrievedColumns = objectMapper.writeValueAsString(columns.build());

			assertThat(retrievedColumns).isEqualTo(customColumnJSONValue);
		}
	}

	private void markAsCustomColumn(@NonNull final AdTableId tableId, @NonNull final String columnName)
	{
		final I_AD_Column foundColumn = queryBL.createQueryBuilder(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName)
				.create()
				.firstOnlyNotNull(I_AD_Column.class);

		foundColumn.setIsRestAPICustomColumn(true);
		saveRecord(foundColumn);
	}

	private void setC_Order_CustomColumnsValues(@NonNull final Map<String, String> row)
	{
		final Map<String, Object> valuesByColumnName = new HashMap<>();

		final String datePromised = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_DatePromised);
		final Double volume = DataTableUtil.extractDoubleOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_Volume);
		final String bpartnerName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_BPartnerName);
		final String dateOrdered = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_DateOrdered);
		final String email = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_EMail);
		final Boolean isDropShip = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_Order.COLUMNNAME_IsDropShip, null);
		final String deliveryInfo = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_DeliveryInfo);

		if (Check.isNotBlank(datePromised))
		{
			valuesByColumnName.put(I_C_Order.COLUMNNAME_DatePromised, datePromised);
		}

		if (volume != null)
		{
			valuesByColumnName.put(I_C_Order.COLUMNNAME_Volume, volume);
		}

		if (Check.isNotBlank(bpartnerName))
		{
			valuesByColumnName.put(I_C_Order.COLUMNNAME_BPartnerName, bpartnerName);
		}

		if (Check.isNotBlank(dateOrdered))
		{
			valuesByColumnName.put(I_C_Order.COLUMNNAME_DateOrdered, dateOrdered);
		}

		if (isDropShip != null)
		{
			valuesByColumnName.put(I_C_Order.COLUMNNAME_IsDropShip, isDropShip);
		}

		if (Check.isNotBlank(email))
		{
			valuesByColumnName.put(I_C_Order.COLUMNNAME_EMail, DataTableUtil.nullToken2Null(email));
		}

		if (Check.isNotBlank(deliveryInfo))
		{
			valuesByColumnName.put(I_C_Order.COLUMNNAME_DeliveryInfo, deliveryInfo);
		}

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Order.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Order order = orderTable.get(orderIdentifier);
		assertThat(order).isNotNull();

		try
		{
			getPO(order).setCustomColumns(valuesByColumnName);
			InterfaceWrapperHelper.save(order);
		}
		catch (final Exception e)
		{
			final String errorMsg = DataTableUtil.extractStringForColumnName(row, "OPT.ErrorMessage");
			assertThat(e.getMessage()).isEqualTo(errorMsg);
		}
	}

	private void setS_ResourceType_customColumnsValues(@NonNull final Map<String, String> row)
	{
		final Map<String, Object> valuesByColumnName = new HashMap<>();

		final String timeSlotStart = DataTableUtil.extractStringForColumnName(row, "OPT." + I_S_ResourceType.COLUMNNAME_TimeSlotStart);
		final String timeSlotEnd = DataTableUtil.extractStringForColumnName(row, "OPT." + I_S_ResourceType.COLUMNNAME_TimeSlotEnd);
		final Integer chargeableQty = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_S_ResourceType.COLUMNNAME_ChargeableQty);

		valuesByColumnName.put(I_S_ResourceType.COLUMNNAME_TimeSlotStart, timeSlotStart);
		valuesByColumnName.put(I_S_ResourceType.COLUMNNAME_TimeSlotEnd, timeSlotEnd);
		valuesByColumnName.put(I_S_ResourceType.COLUMNNAME_ChargeableQty, chargeableQty);

		final String resourceTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_ResourceType.COLUMNNAME_S_ResourceType_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_S_ResourceType resourceType = resourceTypeTable.get(resourceTypeIdentifier);
		assertThat(resourceType).isNotNull();

		getPO(resourceType).setCustomColumns(valuesByColumnName);

		InterfaceWrapperHelper.save(resourceType);
	}

	@NonNull
	private ImmutableMap<String, Object> validate_C_OrderCustomColumns(@NonNull final String identifier)
	{
		final I_C_Order order = orderTable.get(identifier);
		assertThat(order).isNotNull();

		InterfaceWrapperHelper.refresh(order);

		return getPO(order).retrieveCustomColumns();
	}

	@NonNull
	private ImmutableMap<String, Object> validate_S_ResourceType_customColumns(@NonNull final String identifier)
	{
		final I_S_ResourceType resourceType = resourceTypeTable.get(identifier);
		assertThat(resourceType).isNotNull();

		InterfaceWrapperHelper.refresh(resourceType);

		return getPO(resourceType).retrieveCustomColumns();
	}
}