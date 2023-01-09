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

package de.metas.cucumber.stepdefs.inventory;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class M_Inventory_StepDef
{
	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);

	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final M_Inventory_StepDefData inventoryTable;
	private final M_InventoryLine_StepDefData inventoryLineTable;
	private final M_Product_StepDefData productTable;

	public M_Inventory_StepDef(
			@NonNull final M_Inventory_StepDefData inventoryTable,
			@NonNull final M_InventoryLine_StepDefData inventoryLineTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.inventoryTable = inventoryTable;
		this.inventoryLineTable = inventoryLineTable;
		this.productTable = productTable;
	}

	@Given("metasfresh contains M_Inventories:")
	public void addNewInventory(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> tableRow : tableRows)
		{
			addNewInventory(tableRow);
		}
	}

	@Given("metasfresh contains M_InventoriesLines:")
	public void addNewInventoryLine(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			addNewInventoryLine(tableRow);
		}
	}

	@Given("^the inventory identified by (.*) is completed$")
	public void inventory_is_completed(@NonNull final String inventoryIdentifier)
	{
		final I_M_Inventory inventory = inventoryTable.get(inventoryIdentifier);
		inventory.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(inventory, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@And("metasfresh initially has M_Inventory data")
	public void setupM_Inventory_Data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createM_Inventory(dataTableRow);
		}
	}

	@And("metasfresh initially has M_InventoryLine data")
	public void setupM_InventoryLine_Data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createM_InventoryLine(dataTableRow);
		}
	}

	@And("complete inventory with inventoryIdentifier {string}")
	public void complete_inventory(@NonNull final String inventoryIdentifier)
	{
		final I_M_Inventory inventory = inventoryTable.get(inventoryIdentifier);

		inventoryService.completeDocument(InventoryId.ofRepoId(inventory.getM_Inventory_ID()));
	}

	private void addNewInventory(@NonNull final Map<String, String> tableRow)
	{
		final int warehouseId = DataTableUtil.extractIntForColumnName(tableRow, I_M_Inventory.COLUMNNAME_M_Warehouse_ID);

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);

		inventoryRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryRecord.setM_Warehouse_ID(WarehouseId.ofRepoId(warehouseId).getRepoId());
		inventoryRecord.setMovementDate(DataTableUtil.extractDateTimestampForColumnName(tableRow, I_M_Inventory.COLUMNNAME_MovementDate));

		saveRecord(inventoryRecord);

		inventoryTable.put(DataTableUtil.extractRecordIdentifier(tableRow, "M_Inventory"), inventoryRecord);
	}

	private void addNewInventoryLine(@NonNull final Map<String, String> tableRow)
	{
		final de.metas.invoicecandidate.model.I_M_InventoryLine inventoryLine = newInstance(de.metas.invoicecandidate.model.I_M_InventoryLine.class);

		final String inventoryIdentifier = DataTableUtil.extractStringForColumnName(tableRow, de.metas.invoicecandidate.model.I_M_InventoryLine.COLUMNNAME_M_Inventory_ID + ".Identifier");
		final I_M_Inventory inventory = inventoryTable.get(inventoryIdentifier);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(inventory.getM_Warehouse_ID());

		inventoryLine.setM_Inventory_ID(inventory.getM_Inventory_ID());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, de.metas.invoicecandidate.model.I_M_InventoryLine.COLUMNNAME_M_Product_ID + ".Identifier");
		final I_M_Product product = productTable.get(productIdentifier);


		inventoryLine.setM_Locator_ID(warehouseBL.getDefaultLocatorId(warehouseId).getRepoId());
		inventoryLine.setM_Product_ID(product.getM_Product_ID());

		inventoryLine.setQtyCount(DataTableUtil.extractBigDecimalForColumnName(tableRow, "QtyCount"));
		inventoryLine.setQtyBook(DataTableUtil.extractBigDecimalForColumnName(tableRow, "QtyBooked"));
		inventoryLine.setIsCounted(true);

		final String uomX12DE355Code = DataTableUtil.extractStringForColumnName(tableRow, "UOM.X12DE355");
		final X12DE355 uom = X12DE355.ofCode(uomX12DE355Code);

		final I_C_UOM eachUomRecord = uomDAO.getByX12DE355(uom);

		inventoryLine.setC_UOM_ID(eachUomRecord.getC_UOM_ID());

		saveRecord(inventoryLine);

		inventoryLineTable.put(DataTableUtil.extractRecordIdentifier(tableRow, "M_InventoryLine"), inventoryLine);
	}

	private void createM_Inventory(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, "M_Inventory_ID.Identifier");
		final Timestamp movementDate = DataTableUtil.extractDateTimestampForColumnName(row, "MovementDate");
		final String documentNo = DataTableUtil.extractStringForColumnName(row, "DocumentNo");

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryRecord.setC_DocType_ID(StepDefConstants.DOC_TYPE_ID_MMI.getRepoId());
		inventoryRecord.setDocStatus(DocStatus.Drafted.getCode());

		inventoryRecord.setMovementDate(movementDate);
		inventoryRecord.setDocumentNo(documentNo);

		saveRecord(inventoryRecord);

		inventoryTable.put(identifier, inventoryRecord);
	}

	private void createM_InventoryLine(@NonNull final Map<String, String> row)
	{
		final String inventoryIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Inventory_ID.Identifier");
		final String inventoryLineIdentifier = DataTableUtil.extractStringForColumnName(row, "M_InventoryLine_ID.Identifier");
		final int productId = DataTableUtil.extractIntForColumnName(row, "M_Product_ID");
		final BigDecimal qtyBook = DataTableUtil.extractBigDecimalForColumnName(row, "QtyBook");
		final BigDecimal qtyCount = DataTableUtil.extractBigDecimalForColumnName(row, "QtyCount");

		final I_M_Inventory mInventory = inventoryTable.get(inventoryIdentifier);

		final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);
		inventoryLineRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryLineRecord.setC_UOM_ID(UomId.toRepoId(UomId.EACH));
		inventoryLineRecord.setM_Locator_ID(StepDefConstants.LOCATOR_ID.getRepoId());
		inventoryLineRecord.setM_AttributeSetInstance(null);
		inventoryLineRecord.setHUAggregationType(HUAggregationType.SINGLE_HU.getCode());
		inventoryLineRecord.setIsCounted(true);

		inventoryLineRecord.setM_Inventory_ID(mInventory.getM_Inventory_ID());
		inventoryLineRecord.setM_Product_ID(productId);
		inventoryLineRecord.setQtyBook(qtyBook);
		inventoryLineRecord.setQtyCount(qtyCount);

		save(inventoryLineRecord);

		inventoryLineTable.put(inventoryLineIdentifier, inventoryLineRecord);
	}
}
