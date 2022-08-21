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
import org.adempiere.ad.persistence.custom_columns.CustomColumnService;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_S_ResourceType;

import java.util.HashMap;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class AD_Column_StepDef
{
	private final C_Order_StepDefData orderTable;
	private final S_ResourceType_StepDefData resourceTypeTable;

	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CustomColumnService customColumnService = SpringContextHolder.instance.getBean(CustomColumnService.class);

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
			final boolean isRestAPICustomColumn = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_AD_Column.COLUMNNAME_IsRestAPICustomColumn, false);

			final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
			assertThat(tableId).isNotNull();

			final I_AD_Column targetColumn = queryBL.createQueryBuilder(I_AD_Column.class)
					.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, tableId)
					.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName)
					.create()
					.firstOnlyNotNull(I_AD_Column.class);

			targetColumn.setIsRestAPICustomColumn(isRestAPICustomColumn);
			saveRecord(targetColumn);
		}
	}

	@When("^set custom columns for C_Order( expecting error:|:)$")
	public void setCustomColumn_C_Order(@NonNull final String semantics, @NonNull final DataTable dataTable)
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

			final Map<String, Object> columns;
			if (Check.isNotBlank(orderIdentifier))
			{
				columns = getOrderCustomColumns(orderIdentifier);
			}
			else if (Check.isNotBlank(resourceTypeIdentifier))
			{
				columns = getResourceTypeCustomColumns(resourceTypeIdentifier);
			}
			else
			{
				throw new RuntimeException("One of " + "OPT." + I_C_Order.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER
												   + " OR " + "OPT." + I_S_ResourceType.COLUMNNAME_S_ResourceType_ID + "." + TABLECOLUMN_IDENTIFIER + " must be set!");
			}

			final String customColumnJSONValue = DataTableUtil.extractStringForColumnName(row, "CustomColumnJSONValue");
			final String retrievedColumns = objectMapper.writeValueAsString(columns);

			assertThat(retrievedColumns).isEqualTo(customColumnJSONValue);
		}
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

		final String errorMsg = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.ErrorMessage");

		try
		{
			customColumnService.setCustomColumns(InterfaceWrapperHelper.getPO(order), valuesByColumnName);

			InterfaceWrapperHelper.save(order);

			if (Check.isNotBlank(errorMsg))
			{
				throw new RuntimeException("Was expecting operation to fail!");
			}
		}
		catch (final AdempiereException e)
		{
			assertThat(e.getMessage()).isEqualTo(errorMsg);
		}
	}

	private void setS_ResourceType_customColumnsValues(@NonNull final Map<String, String> row)
	{
		final Map<String, Object> valuesByColumnName = new HashMap<>();

		final String timeSlotStart = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_ResourceType.COLUMNNAME_TimeSlotStart);
		final String timeSlotEnd = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_ResourceType.COLUMNNAME_TimeSlotEnd);
		final Integer chargeableQty = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_S_ResourceType.COLUMNNAME_ChargeableQty);

		if (timeSlotStart != null)
		{
			valuesByColumnName.put(I_S_ResourceType.COLUMNNAME_TimeSlotStart, timeSlotStart);
		}

		if (timeSlotEnd != null)
		{
			valuesByColumnName.put(I_S_ResourceType.COLUMNNAME_TimeSlotEnd, timeSlotEnd);
		}

		if (chargeableQty != null)
		{
			valuesByColumnName.put(I_S_ResourceType.COLUMNNAME_ChargeableQty, chargeableQty);
		}

		final String resourceTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_ResourceType.COLUMNNAME_S_ResourceType_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_S_ResourceType resourceType = resourceTypeTable.get(resourceTypeIdentifier);
		assertThat(resourceType).isNotNull();

		customColumnService.setCustomColumns(InterfaceWrapperHelper.getPO(resourceType), valuesByColumnName);

		InterfaceWrapperHelper.save(resourceType);
	}

	@NonNull
	private Map<String, Object> getOrderCustomColumns(@NonNull final String identifier)
	{
		final I_C_Order order = orderTable.get(identifier);
		assertThat(order).isNotNull();

		InterfaceWrapperHelper.refresh(order);

		return customColumnService.getCustomColumnsJsonValues(InterfaceWrapperHelper.getPO(order)).toMap();
	}

	@NonNull
	private Map<String, Object> getResourceTypeCustomColumns(@NonNull final String identifier)
	{
		final I_S_ResourceType resourceType = resourceTypeTable.get(identifier);
		assertThat(resourceType).isNotNull();

		InterfaceWrapperHelper.refresh(resourceType);

		return customColumnService.getCustomColumnsJsonValues(InterfaceWrapperHelper.getPO(resourceType)).toMap();
	}
}