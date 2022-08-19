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

package de.metas.cucumber.stepdefs.warehouse;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_Value;

public class M_Warehouse_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Warehouse_StepDefData warehouseTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;

	public M_Warehouse_StepDef(
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable
	)
	{
		this.warehouseTable = warehouseTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
	}

	@And("load M_Warehouse:")
	public void load_M_Warehouse(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String value = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Value);

			final I_M_Warehouse warehouseRecord = queryBL.createQueryBuilder(I_M_Warehouse.class)
					.addEqualsFilter(COLUMNNAME_Value, value)
					.create()
					.firstOnlyNotNull(I_M_Warehouse.class);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);

			warehouseTable.put(warehouseIdentifier, warehouseRecord);
		}
	}

	@And("metasfresh contains M_Warehouse:")
	public void create_M_Warehouse(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String value = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Value);

			final I_M_Warehouse warehouseRecord = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_M_Warehouse.class)
							.addEqualsFilter(COLUMNNAME_Value, value)
							.create()
							.firstOnlyOrNull(I_M_Warehouse.class),
					() -> InterfaceWrapperHelper.newInstance(I_M_Warehouse.class));

			assertThat(warehouseRecord).isNotNull();

			final String name = DataTableUtil.extractStringForColumnName(row, I_M_Warehouse.COLUMNNAME_Name);

			final boolean isIssueWarehouse = DataTableUtil.extractBooleanForColumnName(row, I_M_Warehouse.COLUMNNAME_IsIssueWarehouse);

			warehouseRecord.setValue(value);
			warehouseRecord.setName(name);
			warehouseRecord.setC_BPartner_ID(2155894); //dev-note: org BPartner
			warehouseRecord.setC_BPartner_Location_ID(2202690);
			warehouseRecord.setIsIssueWarehouse(isIssueWarehouse);

			InterfaceWrapperHelper.saveRecord(warehouseRecord);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			warehouseTable.put(warehouseIdentifier, warehouseRecord);
		}
	}

	@And("metasfresh contains M_Warehouses:")
	public void metasfresh_contains_m_warehouses(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);

			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_M_Warehouse.COLUMNNAME_Name);
			final String value = CoalesceUtil.coalesce(DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_Value), name);

			final String bPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bPartnerLocationIdentifier);

			final boolean isInTransit = DataTableUtil.extractBooleanForColumnName(tableRow, I_M_Warehouse.COLUMNNAME_IsInTransit);

			final I_M_Warehouse warehouse = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_M_Warehouse.class)
							.addEqualsFilter(I_M_Warehouse.COLUMNNAME_Name, name)
							.orderBy(I_M_Warehouse.COLUMNNAME_Created)
							.create()
							.first(I_M_Warehouse.class),
					() -> InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_Warehouse.class));

			assertThat(warehouse).isNotNull();

			warehouse.setC_BPartner_ID(bPartner.getC_BPartner_ID());
			warehouse.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
			warehouse.setName(name);
			warehouse.setValue(value);
			warehouse.setIsInTransit(isInTransit);

			saveRecord(warehouse);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);

			warehouseTable.put(warehouseIdentifier, warehouse);
		}
	}
}
