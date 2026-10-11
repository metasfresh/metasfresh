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
import de.metas.cache.CacheMgt;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.resourcetype.S_ResourceType_StepDefData;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.custom_columns.CustomColumnRepository;
import org.adempiere.ad.persistence.custom_columns.CustomColumnService;
import org.adempiere.ad.persistence.custom_columns.RESTApiTableInfo;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_S_ResourceType;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class AD_Column_StepDef
{
	private static final Logger logger = LogManager.getLogger(AD_Column_StepDef.class);

	@NonNull private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final CustomColumnService customColumnService = SpringContextHolder.instance.getBean(CustomColumnService.class);
	@NonNull private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final S_ResourceType_StepDefData resourceTypeTable;

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
	public void update_AD_Columns(@NonNull final DataTable dataTable) throws InterruptedException
	{
		final DataTableRows rows = DataTableRows.of(dataTable);
		rows.forEach(this::updateAD_Column);

		waitUntilRestAPICustomColumnFlagsAreEffective(rows);
	}

	/**
	 * {@link CustomColumnService} does not read {@code AD_Column.IsRestAPICustomColumn} from the DB but from {@link CustomColumnRepository}'s
	 * single-entry, never-expiring cache. Each AD_Column save resets that cache, but a load that is already running in another thread when the
	 * reset happens is kept by the underlying Guava cache (invalidation does not cancel an in-flight load). Such a load can have read the DB before
	 * our last save committed, so the cache may keep the old flags until the next reset - waiting alone does not help.
	 * Therefore, check the flags via the same read path that {@link CustomColumnService} uses, and reset the AD_Column caches again until it agrees.
	 */
	private void waitUntilRestAPICustomColumnFlagsAreEffective(@NonNull final DataTableRows rows) throws InterruptedException
	{
		final ImmutableMap<TableAndColumnName, Boolean> expectedFlags = rows.stream()
				.filter(row -> row.getAsOptionalBoolean("IsRestAPICustomColumn").isPresent())
				.collect(ImmutableMap.toImmutableMap(
						row -> new TableAndColumnName(row.getAsString("TableName"), row.getAsString("ColumnName")),
						row -> row.getAsOptionalBoolean("IsRestAPICustomColumn").isTrue()));
		if (expectedFlags.isEmpty())
		{
			return;
		}

		final CustomColumnRepository customColumnRepository = SpringContextHolder.instance.getBean(CustomColumnRepository.class);
		StepDefUtil.tryAndWait(
				10,
				200,
				() -> {
					final boolean allEffective = expectedFlags.entrySet()
							.stream()
							.allMatch(entry -> isRestAPICustomColumn(customColumnRepository, entry.getKey()) == entry.getValue());
					if (!allEffective)
					{
						CacheMgt.get().reset(I_AD_Column.Table_Name);
					}
					return allEffective;
				},
				() -> logger.info("Expected IsRestAPICustomColumn flags not yet seen by CustomColumnRepository: {}", expectedFlags));
	}

	private static boolean isRestAPICustomColumn(
			@NonNull final CustomColumnRepository customColumnRepository,
			@NonNull final TableAndColumnName tableAndColumnName)
	{
		final RESTApiTableInfo tableInfo = customColumnRepository.getByTableNameOrNull(tableAndColumnName.getTableName());
		return tableInfo != null && tableInfo.isCustomRestAPIColumn(tableAndColumnName.getColumnName());
	}

	@Value
	private static class TableAndColumnName
	{
		@NonNull String tableName;
		@NonNull String columnName;
	}

	private void updateAD_Column(final DataTableRow row)
	{
		final String tableName = row.getAsString("TableName");
		final String columnName = row.getAsString("ColumnName");

		final I_AD_Column targetColumn = getExistingColumn(tableName, columnName);
		row.getAsOptionalBoolean("IsRestAPICustomColumn").ifPresent(targetColumn::setIsRestAPICustomColumn);

		saveRecord(targetColumn);
	}

	@NonNull
	private I_AD_Column getExistingColumn(final String tableName, final String columnName)
	{
		final AdTableId tableId = TableIdsCache.instance.getTableIdNotNull(tableName);

		return queryBL.createQueryBuilder(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName)
				.create()
				.firstOnlyNotNull(I_AD_Column.class);
	}

	@When("^set custom columns for C_Order( expecting error:|:)$")
	public void setCustomColumn_C_Order(@NonNull final String ignoredSemantics, @NonNull final DataTable dataTable)
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