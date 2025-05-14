/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.forecast;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.mforecast.impl.ForecastId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Warehouse;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_Forecast.COLUMNNAME_M_Warehouse_ID;

public class M_ForecastLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Forecast_StepDefData forecastTable;

	public M_ForecastLine_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Forecast_StepDefData forecastTable)
	{
		this.productTable = productTable;
		this.warehouseTable = warehouseTable;
		this.forecastTable = forecastTable;
	}

	@Given("metasfresh contains M_ForecastLines:")
	public void metasfresh_contains_m_forecast_lines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID)
				.forEach(tableRow -> {
					final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);

					final ForecastId forecastId = tableRow.getAsIdentifier(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID).lookupIdIn(forecastTable);
					
					final StepDefDataIdentifier productIdentifier = tableRow.getAsIdentifier(COLUMNNAME_M_Product_ID);
					final ProductId productId = productTable.getIdOptional(productIdentifier)
							.orElseGet(() -> productIdentifier.getAsId(ProductId.class));

					final StepDefDataIdentifier warehouseIdentifier = tableRow.getAsIdentifier(COLUMNNAME_M_Warehouse_ID);
					final int warehouseId = warehouseTable.getOptional(warehouseIdentifier)
							.map(I_M_Warehouse::getM_Warehouse_ID)
							.orElseGet(warehouseIdentifier::getAsInt);
					
					final String uomX12DE355 = tableRow.getAsString(I_M_ForecastLine.COLUMNNAME_C_UOM_ID + "." + I_C_UOM.COLUMNNAME_X12DE355);
					final UomId uomId = queryBL.createQueryBuilder(I_C_UOM.class)
							.addEqualsFilter(I_C_UOM.COLUMNNAME_X12DE355, uomX12DE355)
							.addOnlyActiveRecordsFilter()
							.create()
							.firstIdOnly(UomId::ofRepoIdOrNull);
					assertThat(uomId).as("Found no C_UOM with X12DE355=%s", uomX12DE355).isNotNull();

					forecastLine.setM_Forecast_ID(forecastId.getRepoId());
					forecastLine.setM_Product_ID(productId.getRepoId());
					forecastLine.setQty(tableRow.getAsBigDecimal(I_M_ForecastLine.COLUMNNAME_Qty));
					forecastLine.setM_Warehouse_ID(warehouseId);
					forecastLine.setC_UOM_ID(UomId.toRepoId(uomId));
					
					saveRecord(forecastLine);
				});
	}
}