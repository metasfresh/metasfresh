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

import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_M_Locator.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;

public class M_Locator_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Locator_StepDefData locatorTable;

	public M_Locator_StepDef(
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Locator_StepDefData locatorTable)
	{
		this.warehouseTable = warehouseTable;
		this.locatorTable = locatorTable;
	}

	@And("load M_Locator:")
	public void load_M_Locator(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String value = DataTableUtil.extractStringForColumnName(row, I_M_Locator.COLUMNNAME_Value);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);

			final I_M_Locator locatorRecord = queryBL.createQueryBuilder(I_M_Locator.class)
					.addEqualsFilter(I_M_Locator.COLUMNNAME_M_Warehouse_ID, warehouse.getM_Warehouse_ID())
					.addEqualsFilter(I_M_Locator.COLUMNNAME_Value, value)
					.orderByDescending(COLUMNNAME_M_Locator_ID)
					.create()
					.firstNotNull(I_M_Locator.class);

			final String locatorIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
			locatorTable.put(locatorIdentifier, locatorRecord);
		}
	}

	@And("metasfresh contains M_Locator")
	public void create_M_Locator(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			createLocator(row);
		}
	}

	private void createLocator(@NonNull final Map<String, String> row)
	{
		final String value = DataTableUtil.extractStringForColumnName(row, I_M_Locator.COLUMNNAME_Value);

		final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Locator.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer warehouseID = warehouseTable.getOptional(warehouseIdentifier)
				.map(I_M_Warehouse::getM_Warehouse_ID)
				.orElseGet(() -> Integer.parseInt(warehouseIdentifier));

		final Integer priorityNo = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_M_Locator.COLUMNNAME_PriorityNo);
		final String x = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Locator.COLUMNNAME_X);
		final String y = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Locator.COLUMNNAME_Y);
		final String z = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Locator.COLUMNNAME_Z);
		final boolean isDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_M_Locator.COLUMNNAME_IsDefault, true);

		final I_M_Locator locatorRecord = InterfaceWrapperHelper.newInstance(I_M_Locator.class);

		locatorRecord.setValue(value);
		locatorRecord.setM_Warehouse_ID(warehouseID);
		locatorRecord.setPriorityNo(priorityNo != null ? priorityNo : 50);
		locatorRecord.setIsDefault(isDefault);
		locatorRecord.setX(x != null ? x : "0");
		locatorRecord.setY(y != null ? y : "0");
		locatorRecord.setZ(z != null ? z : "0");

		saveRecord(locatorRecord);

		final String locatorIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
		locatorTable.put(locatorIdentifier, locatorRecord);
	}
}
