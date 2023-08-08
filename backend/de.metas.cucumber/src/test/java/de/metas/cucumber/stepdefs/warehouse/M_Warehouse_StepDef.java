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
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.adempiere.warehouse.model.I_M_Warehouse.COLUMNNAME_IsIssueWarehouse;
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
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable)
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

			final boolean isIssueWarehouse = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_M_Warehouse.COLUMNNAME_IsIssueWarehouse, false);
			final boolean isInTransit = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_M_Warehouse.COLUMNNAME_IsInTransit, false);
			final boolean isQuarantineWarehouse = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_M_Warehouse.COLUMNNAME_IsQuarantineWarehouse, false);
			final boolean isQualityReturnWarehouse = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse, false);

			final int bPartnerId = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Warehouse.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER))
					.map(bPartnerTable::get)
					.map(I_C_BPartner::getC_BPartner_ID)
					.orElse(StepDefConstants.METASFRESH_AG_BPARTNER_ID.getRepoId());

			final int bPartnerLocationId = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER))
					.map(bPartnerLocationTable::get)
					.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
					.orElse(StepDefConstants.METASFRESH_AG_BPARTNER_LOCATION_ID.getRepoId());

			warehouseRecord.setValue(value);
			warehouseRecord.setName(name);
			warehouseRecord.setC_BPartner_ID(bPartnerId);
			warehouseRecord.setC_BPartner_Location_ID(bPartnerLocationId);
			warehouseRecord.setIsIssueWarehouse(isIssueWarehouse);
			warehouseRecord.setIsInTransit(isInTransit);
			warehouseRecord.setIsQuarantineWarehouse(isQuarantineWarehouse);
			warehouseRecord.setIsQualityReturnWarehouse(isQualityReturnWarehouse);

			saveRecord(warehouseRecord);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			warehouseTable.put(warehouseIdentifier, warehouseRecord);
		}
	}
}
