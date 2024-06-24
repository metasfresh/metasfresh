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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;

import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_Value;

public class M_Warehouse_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Warehouse_StepDefData warehouseTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	public M_Warehouse_StepDef(
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable
	)
	{
		this.warehouseTable = warehouseTable;
		this.bpartnerTable = bPartnerTable;
		this.bpartnerLocationTable = bPartnerLocationTable;
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

			row.getAsIdentifier(COLUMNNAME_M_Warehouse_ID).put(warehouseTable, warehouseRecord);
		});
	}

	@And("metasfresh contains M_Warehouse:")
	public void create_M_Warehouse(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final String value = row.getAsString(COLUMNNAME_Value);

			final I_M_Warehouse warehouseRecord = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_M_Warehouse.class)
							.addEqualsFilter(COLUMNNAME_Value, value)
							.create()
							.firstOnlyOrNull(I_M_Warehouse.class),
					() -> InterfaceWrapperHelper.newInstance(I_M_Warehouse.class));

			assertThat(warehouseRecord).isNotNull();

			final String name = row.getAsString(I_M_Warehouse.COLUMNNAME_Name);

			final boolean isIssueWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsIssueWarehouse).orElse(false);
			final boolean isInTransit = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsInTransit).orElse(false);

			final BPartnerId bpartnerId = row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_C_BPartner_ID)
					.map(bpartnerTable::getId)
					.orElse(StepDefConstants.METASFRESH_AG_BPARTNER_ID);

			final int bpartnerLocationRepoId = row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID)
					.map(bpartnerLocationTable::get)
					.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
					.orElse(-1);

			final BPartnerLocationId bpartnerLocationId = bpartnerLocationRepoId > 0
					? BPartnerLocationId.ofRepoIdOrNull(bpartnerId, bpartnerLocationRepoId)
					: StepDefConstants.METASFRESH_AG_BPARTNER_LOCATION_ID;

			warehouseRecord.setValue(value);
			warehouseRecord.setName(name);
			warehouseRecord.setSeparator("*");
			warehouseRecord.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
			warehouseRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
			warehouseRecord.setIsIssueWarehouse(isIssueWarehouse);
			warehouseRecord.setIsInTransit(isInTransit);

			InterfaceWrapperHelper.saveRecord(warehouseRecord);

			row.getAsIdentifier(COLUMNNAME_M_Warehouse_ID).put(warehouseTable, warehouseRecord);
		});
	}
}
