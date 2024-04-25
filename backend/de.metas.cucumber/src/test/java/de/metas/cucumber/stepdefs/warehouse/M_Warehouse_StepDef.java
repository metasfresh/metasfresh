/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_IsIssueWarehouse;
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

	@And("update M_Warehouse:")
	public void update_M_Warehouse(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			updateWarehouse(row);
		}
	}

	@And("load M_Warehouse:")
	public void load_M_Warehouse(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String value = row.getAsString(COLUMNNAME_Value);

			final I_M_Warehouse warehouseRecord = queryBL.createQueryBuilder(I_M_Warehouse.class)
					.addEqualsFilter(COLUMNNAME_Value, value)
					.create()
					.firstOnlyNotNull(I_M_Warehouse.class);

			row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID)
					.ifPresent(bpartnerLocationIdentifier -> {
						final I_C_BPartner_Location bPartnerLocation = load(warehouseRecord.getC_BPartner_Location_ID(), I_C_BPartner_Location.class);
						assertThat(bPartnerLocation).isNotNull();
						bPartnerLocationTable.putOrReplace(bpartnerLocationIdentifier, bPartnerLocation);
					});

			row.getAsIdentifier(COLUMNNAME_M_Warehouse_ID).put(warehouseTable, warehouseRecord);
		});
	}

	@And("metasfresh contains M_Warehouse:")
	public void create_M_Warehouse(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final String value = row.getAsString(COLUMNNAME_Value);

			final I_M_Warehouse warehouseRecord = CoalesceUtil.coalesceSuppliersNotNull(
					() -> queryBL.createQueryBuilder(I_M_Warehouse.class)
							.addEqualsFilter(COLUMNNAME_Value, value)
							.create()
							.firstOnlyOrNull(I_M_Warehouse.class),
					() -> InterfaceWrapperHelper.newInstance(I_M_Warehouse.class));

			assertThat(warehouseRecord).isNotNull();

			final String name = row.getAsString(I_M_Warehouse.COLUMNNAME_Name);

			final boolean isIssueWarehouse = row.getAsOptionalBoolean(COLUMNNAME_IsIssueWarehouse).orElse(false);
			if (isIssueWarehouse)
			{ // we can have just one issue-warehouse, so make sure that all other WHs are not issue-warehouses
				final ICompositeQueryUpdater<I_M_Warehouse> updater = queryBL.createCompositeQueryUpdater(I_M_Warehouse.class).addSetColumnValue(COLUMNNAME_IsIssueWarehouse, false);
				queryBL.createQueryBuilder(I_M_Warehouse.class).addEqualsFilter(COLUMNNAME_IsIssueWarehouse, true).create().updateDirectly(updater);
			}

			final BPartnerId bpartnerId = row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_C_BPartner_ID)
					.map(bPartnerTable::getId)
					.orElse(StepDefConstants.METASFRESH_AG_BPARTNER_ID);

			final int bpartnerLocationRepoId = row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID)
					.map(bPartnerLocationTable::get)
					.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
					.orElse(-1);

			final BPartnerLocationId bpartnerLocationId = bpartnerLocationRepoId > 0
					? BPartnerLocationId.ofRepoIdOrNull(bpartnerId, bpartnerLocationRepoId)
					: StepDefConstants.METASFRESH_AG_BPARTNER_LOCATION_ID;

			final boolean isInTransit = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsInTransit).orElse(false);
			final boolean isQuarantineWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsQuarantineWarehouse).orElse(false);
			final boolean isQualityReturnWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse).orElse(false);

			warehouseRecord.setValue(value);
			warehouseRecord.setName(name);
			warehouseRecord.setSeparator("*");
			warehouseRecord.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
			warehouseRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
			warehouseRecord.setIsIssueWarehouse(isIssueWarehouse);
			warehouseRecord.setIsInTransit(isInTransit);
			warehouseRecord.setIsQuarantineWarehouse(isQuarantineWarehouse);
			warehouseRecord.setIsQualityReturnWarehouse(isQualityReturnWarehouse);

			saveRecord(warehouseRecord);

			row.getAsIdentifier(COLUMNNAME_M_Warehouse_ID).put(warehouseTable, warehouseRecord);
		});
	}

	@And("there is no in transit M_Warehouse")
	public void no_inTransit_M_Warehouse()
	{
		queryBL.createQueryBuilder(I_M_Warehouse.class)
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_IsInTransit, true)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_M_Warehouse.COLUMNNAME_IsInTransit, false)
				.execute();
	}

	private void updateWarehouse(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Warehouse warehouseRecord = warehouseTable.get(identifier);

		final Boolean isQuarantineWarehouse = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_M_Warehouse.COLUMNNAME_IsQuarantineWarehouse);
		if (isQuarantineWarehouse != null)
		{
			warehouseRecord.setIsQuarantineWarehouse(isQuarantineWarehouse);
		}

		final Boolean isQualityReturnWarehouse = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse);
		if (isQualityReturnWarehouse != null)
		{
			warehouseRecord.setIsQualityReturnWarehouse(isQualityReturnWarehouse);
		}

		final Boolean isIssueWarehouse = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + COLUMNNAME_IsIssueWarehouse);
		if (isIssueWarehouse != null)
		{
			warehouseRecord.setIsIssueWarehouse(isIssueWarehouse);
		}

		final Boolean isInTransit = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_M_Warehouse.COLUMNNAME_IsInTransit);
		if (isInTransit != null)
		{
			warehouseRecord.setIsInTransit(isInTransit);
		}

		saveRecord(warehouseRecord);
	}
}
