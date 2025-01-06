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

package de.metas.cucumber.stepdefs.distribution;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_DD_NetworkDistributionLine;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class DD_NetworkDistributionLine_StepDef
{
	@NonNull private final DD_NetworkDistributionLine_StepDefData ddNetworkLineTable;
	@NonNull private final DD_NetworkDistribution_StepDefData ddNetworkTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;

	@And("metasfresh contains DD_NetworkDistributionLine")
	public void metasfresh_contains_DD_NetworkDistributionLine(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::metasfresh_contains_DD_NetworkDistributionLine);
	}

	private void metasfresh_contains_DD_NetworkDistributionLine(final DataTableRow row)
	{
		final I_DD_NetworkDistributionLine record = InterfaceWrapperHelper.newInstance(I_DD_NetworkDistributionLine.class);
		record.setDD_NetworkDistribution_ID(row.getAsIdentifier(I_DD_NetworkDistributionLine.COLUMNNAME_DD_NetworkDistribution_ID).lookupIdIn(ddNetworkTable).getRepoId());
		record.setM_WarehouseSource_ID(row.getAsIdentifier(I_DD_NetworkDistributionLine.COLUMNNAME_M_WarehouseSource_ID).lookupIdIn(warehouseTable).getRepoId());
		record.setM_Warehouse_ID(row.getAsIdentifier(I_DD_NetworkDistributionLine.COLUMNNAME_M_Warehouse_ID).lookupIdIn(warehouseTable).getRepoId());
		record.setM_Shipper_ID(row.getAsIdentifier(I_DD_NetworkDistributionLine.COLUMNNAME_M_Shipper_ID).lookupIdIn(shipperTable).getRepoId());
		record.setPercent(BigDecimal.valueOf(100));
		InterfaceWrapperHelper.saveRecord(record);

		row.getAsOptionalIdentifier(I_DD_NetworkDistributionLine.COLUMNNAME_DD_NetworkDistributionLine_ID)
				.ifPresent(identifier -> ddNetworkLineTable.put(identifier, record));
	}
}
