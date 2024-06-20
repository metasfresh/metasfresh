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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class DD_NetworkDistributionLine_StepDef
{
	private final DD_NetworkDistributionLine_StepDefData ddNetworkLineTable;
	private final DD_NetworkDistribution_StepDefData ddNetworkTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Shipper_StepDefData shipperTable;

	public DD_NetworkDistributionLine_StepDef(
			@NonNull final DD_NetworkDistributionLine_StepDefData ddNetworkLineTable,
			@NonNull final DD_NetworkDistribution_StepDefData ddNetworkTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Shipper_StepDefData shipperTable)
	{
		this.ddNetworkLineTable = ddNetworkLineTable;
		this.ddNetworkTable = ddNetworkTable;
		this.warehouseTable = warehouseTable;
		this.shipperTable = shipperTable;
	}

	@And("metasfresh contains DD_NetworkDistributionLine")
	public void metasfresh_contains_DD_NetworkDistributionLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String ddNetworkIdentifier = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistributionLine.COLUMNNAME_DD_NetworkDistribution_ID + "." + TABLECOLUMN_IDENTIFIER);

			final String sourceWarehouseIdentifier = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistributionLine.COLUMNNAME_M_WarehouseSource_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String targetWarehouseIdentifier = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistributionLine.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String shipperIdentifier = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistributionLine.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_DD_NetworkDistributionLine ddNetworkLine = InterfaceWrapperHelper.newInstance(I_DD_NetworkDistributionLine.class);

			final I_DD_NetworkDistribution ddNetwork = ddNetworkTable.get(ddNetworkIdentifier);
			assertThat(ddNetwork).isNotNull();
			ddNetworkLine.setDD_NetworkDistribution_ID(ddNetwork.getDD_NetworkDistribution_ID());

			final I_M_Warehouse sourceWarehouse = warehouseTable.get(sourceWarehouseIdentifier);
			assertThat(sourceWarehouse).isNotNull();
			ddNetworkLine.setM_WarehouseSource_ID(sourceWarehouse.getM_Warehouse_ID());

			final I_M_Warehouse targetWarehouse = warehouseTable.get(targetWarehouseIdentifier);
			assertThat(targetWarehouse).isNotNull();
			ddNetworkLine.setM_Warehouse_ID(targetWarehouse.getM_Warehouse_ID());

			final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
			assertThat(shipper).isNotNull();
			ddNetworkLine.setM_Shipper_ID(shipper.getM_Shipper_ID());
			ddNetworkLine.setPercent(BigDecimal.valueOf(100));

			InterfaceWrapperHelper.saveRecord(ddNetworkLine);

			final String ddNetworkLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistributionLine.COLUMNNAME_DD_NetworkDistributionLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			ddNetworkLineTable.put(ddNetworkLineIdentifier, ddNetworkLine);
		}
	}
}
