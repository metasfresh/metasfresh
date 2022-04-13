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

import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Product;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
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
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_HU_StepDefData huTable;

	public M_Inventory_StepDef(
			@NonNull final M_Inventory_StepDefData inventoryTable,
			@NonNull final M_InventoryLine_StepDefData inventoryLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable,
			@NonNull final M_HU_StepDefData huTable)
	{
		this.inventoryTable = inventoryTable;
		this.inventoryLineTable = inventoryLineTable;
		this.productTable = productTable;
		this.huTable = huTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
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

	@And("the following virtual inventory is created")
	public void createVirtualInventory(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentScheduleRecord = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final I_C_UOM productUOM = uomDAO.getById(productRecord.getC_UOM_ID());

		final int qtyToBeAddedParam = DataTableUtil.extractIntForColumnName(row, "QtyToBeAdded");

		final Quantity qtyToBeAdded = Quantity.of(qtyToBeAddedParam, productUOM);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(shipmentScheduleRecord.getM_Warehouse_ID());
		final OrgId orgId = OrgId.ofRepoId(shipmentScheduleRecord.getAD_Org_ID());
		final ClientId clientId = ClientId.ofRepoId(shipmentScheduleRecord.getAD_Client_ID());
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNull(shipmentScheduleRecord.getM_AttributeSetInstance_ID());

		final CreateVirtualInventoryWithQtyReq req = CreateVirtualInventoryWithQtyReq.builder()
				.clientId(clientId)
				.orgId(orgId)
				.warehouseId(warehouseId)
				.productId(productId)
				.qty(qtyToBeAdded)
				.movementDate(SystemTime.asZonedDateTime())
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		final HuId huId = inventoryService.createInventoryForMissingQty(req);

		huTable.put(huIdentifier, InterfaceWrapperHelper.load(huId, I_M_HU.class));
	}

	private void addNewInventory(@NonNull final Map<String, String> tableRow)
	{
		final int warehouseId = DataTableUtil.extractIntForColumnName(tableRow, I_M_Inventory.COLUMNNAME_M_Warehouse_ID);

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);

		inventoryRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryRecord.setM_Warehouse_ID(WarehouseId.ofRepoId(warehouseId).getRepoId());
		inventoryRecord.setMovementDate(DataTableUtil.extractDateTimestampForColumnName(tableRow, I_M_Inventory.COLUMNNAME_MovementDate));

		final String documentNo = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_Inventory.COLUMNNAME_DocumentNo);
		if (Check.isNotBlank(documentNo))
		{
			inventoryRecord.setDocumentNo(documentNo);
		}
		else
		{
			inventoryRecord.setDocumentNo("not_important");
		}

		saveRecord(inventoryRecord);

		inventoryTable.put(DataTableUtil.extractRecordIdentifier(tableRow, I_M_Inventory.COLUMNNAME_M_Inventory_ID, "M_Inventory"), inventoryRecord);
	}

	private void addNewInventoryLine(@NonNull final Map<String, String> tableRow)
	{
		final de.metas.invoicecandidate.model.I_M_InventoryLine inventoryLine = newInstance(de.metas.invoicecandidate.model.I_M_InventoryLine.class);

		final String inventoryIdentifier = DataTableUtil.extractStringForColumnName(tableRow, de.metas.invoicecandidate.model.I_M_InventoryLine.COLUMNNAME_M_Inventory_ID + ".Identifier");
		final I_M_Inventory inventory = inventoryTable.get(inventoryIdentifier);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(inventory.getM_Warehouse_ID());

		inventoryLine.setM_Inventory_ID(inventory.getM_Inventory_ID());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, de.metas.invoicecandidate.model.I_M_InventoryLine.COLUMNNAME_M_Product_ID + ".Identifier");
		final Integer productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		inventoryLine.setM_Locator_ID(warehouseBL.getDefaultLocatorId(warehouseId).getRepoId());
		inventoryLine.setM_Product_ID(productId);

		inventoryLine.setQtyCount(DataTableUtil.extractBigDecimalForColumnName(tableRow, "QtyCount"));
		inventoryLine.setQtyBook(DataTableUtil.extractBigDecimalForColumnName(tableRow, "QtyBook"));
		inventoryLine.setIsCounted(true);

		final String uomX12DE355Code = DataTableUtil.extractStringForColumnName(tableRow, "UOM.X12DE355");
		final X12DE355 uom = X12DE355.ofCode(uomX12DE355Code);

		final I_C_UOM eachUomRecord = uomDAO.getByX12DE355(uom);

		inventoryLine.setC_UOM_ID(eachUomRecord.getC_UOM_ID());

		saveRecord(inventoryLine);

		inventoryLineTable.put(DataTableUtil.extractRecordIdentifier(tableRow, I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID, "M_InventoryLine"), inventoryLine);
	}
}
