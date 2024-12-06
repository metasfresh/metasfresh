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
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner_Location;

import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_IsActive;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_IsIssueWarehouse;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_Value;

@RequiredArgsConstructor
public class M_Warehouse_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	@NonNull private final S_Resource_StepDefData resourceTable;

	@And("load M_Warehouse:")
	public void load_M_Warehouse(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Warehouse_ID)
				.forEach(row -> {
					final String value = row.getAsString(COLUMNNAME_Value);

					final I_M_Warehouse warehouseRecord = queryBL.createQueryBuilder(I_M_Warehouse.class)
							.addEqualsFilter(COLUMNNAME_Value, value)
							.create()
							.firstOnlyNotNull(I_M_Warehouse.class);

					row.getAsIdentifier().put(warehouseTable, warehouseRecord);
				});
	}

	@And("metasfresh contains M_Warehouse:")
	public void create_M_Warehouse(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Warehouse_ID)
				.forEach((row) -> {
					final ValueAndName valueAndName = row.suggestValueAndName();

					final I_M_Warehouse warehouseRecord = CoalesceUtil.coalesceSuppliers(
							() -> queryBL.createQueryBuilder(I_M_Warehouse.class)
									.addEqualsFilter(COLUMNNAME_Value, valueAndName.getValue())
									.create()
									.firstOnlyOrNull(I_M_Warehouse.class),
							() -> InterfaceWrapperHelper.newInstance(I_M_Warehouse.class));

					assertThat(warehouseRecord).isNotNull();

					final boolean isIssueWarehouse = row.getAsOptionalBoolean(COLUMNNAME_IsIssueWarehouse).orElse(false);
					if (isIssueWarehouse)
					{ // we can have just one issue-warehouse, so make sure that all other WHs are not issue-warehouses
						final ICompositeQueryUpdater<I_M_Warehouse> updater = queryBL.createCompositeQueryUpdater(I_M_Warehouse.class).addSetColumnValue(COLUMNNAME_IsIssueWarehouse, false);
						queryBL.createQueryBuilder(I_M_Warehouse.class).addEqualsFilter(COLUMNNAME_IsIssueWarehouse, true).addEqualsFilter(COLUMNNAME_IsActive, true).create().updateDirectly(updater);
					}

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

					final boolean isInTransit = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsInTransit).orElse(false);
					final boolean isQuarantineWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsQuarantineWarehouse).orElse(false);
					final boolean isQualityReturnWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse).orElse(false);

					warehouseRecord.setValue(valueAndName.getValue());
					warehouseRecord.setName(valueAndName.getName());
					warehouseRecord.setSeparator("*");
					warehouseRecord.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
					warehouseRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
					warehouseRecord.setIsIssueWarehouse(isIssueWarehouse);
					warehouseRecord.setIsInTransit(isInTransit);
					warehouseRecord.setIsQuarantineWarehouse(isQuarantineWarehouse);
					warehouseRecord.setIsQualityReturnWarehouse(isQualityReturnWarehouse);

					row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_PP_Plant_ID)
							.map(identifier -> resourceTable.getIdOptional(identifier).orElseGet(() -> identifier.getAsId(ResourceId.class)))
							.ifPresent(resourceId -> warehouseRecord.setPP_Plant_ID(resourceId.getRepoId()));

					saveRecord(warehouseRecord);

					warehouseBL.getOrCreateDefaultLocatorId(WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID()));

					row.getAsIdentifier().put(warehouseTable, warehouseRecord);
				});
	}
}
