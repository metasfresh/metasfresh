/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockResponse;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockResponseItem;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.document.engine.IDocument;
<<<<<<< HEAD
=======
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
>>>>>>> 01acf328a21 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
import de.metas.invoicecandidate.model.I_M_InventoryLine;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Inventory;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OutOfStockNotice_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final TestContext testContext;
<<<<<<< HEAD

	public OutOfStockNotice_StepDef(@NonNull final TestContext testContext)
=======
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;

	public OutOfStockNotice_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable)
>>>>>>> 01acf328a21 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
	{
		this.testContext = testContext;
	}

	@Then("there is a new completed inventory for the issued out of stock notice")
	public void inventoryIsCompleted(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String content = testContext.getApiResponse().getContent();

			final JsonOutOfStockResponse outOfStockResponse = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(content, JsonOutOfStockResponse.class);

			assertEquals(1, outOfStockResponse.getAffectedWarehouses().size());

			final JsonOutOfStockResponseItem affectedWarehouse = outOfStockResponse.getAffectedWarehouses().get(0);

			assertNotNull(affectedWarehouse.getInventoryDocNo());
			assertEquals(affectedWarehouse.getWarehouseId().getValue(), DataTableUtil.extractIntForColumnName(tableRow, I_M_Inventory.COLUMNNAME_M_Warehouse_ID));

			final I_M_Inventory inventory = queryBL.createQueryBuilderOutOfTrx(I_M_Inventory.class)
					.addEqualsFilter(I_M_Inventory.COLUMNNAME_DocumentNo, affectedWarehouse.getInventoryDocNo())
					.create()
					.firstOnly(I_M_Inventory.class);

			assertNotNull(inventory);

			assertEquals(IDocument.STATUS_Completed, inventory.getDocStatus());

			final I_M_InventoryLine inventoryLineRecord = queryBL.createQueryBuilderOutOfTrx(I_M_InventoryLine.class)
					.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventory.getM_Inventory_ID())
					.create()
					.firstOnly(I_M_InventoryLine.class);

			assertNotNull(inventoryLineRecord);

			assertEquals(DataTableUtil.extractIntForColumnName(tableRow, I_M_InventoryLine.COLUMNNAME_QtyCount),
						 inventoryLineRecord.getQtyCount().intValue());

			assertEquals(DataTableUtil.extractIntForColumnName(tableRow, I_M_InventoryLine.COLUMNNAME_QtyBook),
						 inventoryLineRecord.getQtyBook().intValue());
		}
	}
}