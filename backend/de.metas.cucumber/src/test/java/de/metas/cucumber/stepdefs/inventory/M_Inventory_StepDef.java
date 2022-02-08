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
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
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

	private final StepDefData<I_M_Inventory> inventoryTable;
	private final StepDefData<I_M_InventoryLine> inventoryLineTable;
	private final StepDefData<I_M_ShipmentSchedule> shipmentScheduleTable;
	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_M_HU> huTable;
	private final StepDefData<I_M_AttributeSetInstance> attributeSetInstanceTable;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public M_Inventory_StepDef(
			final StepDefData<I_M_Inventory> inventoryTable,
			final StepDefData<I_M_InventoryLine> inventoryLineTable,
			final StepDefData<I_M_ShipmentSchedule> shipmentScheduleTable,
			final StepDefData<I_M_Product> productTable,
			final StepDefData<I_M_HU> huTable,
			final StepDefData<I_M_AttributeSetInstance> attributeSetInstanceTable)
	{
		this.inventoryTable = inventoryTable;
		this.inventoryLineTable = inventoryLineTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.productTable = productTable;
		this.huTable = huTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
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
		final BigDecimal qtyBook = DataTableUtil.extractBigDecimalForColumnName(row, "QtyBook");
		final BigDecimal qtyCount = DataTableUtil.extractBigDecimalForColumnName(row, "QtyCount");
		final Integer productId = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT.M_Product_ID");
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");

		final I_M_Inventory mInventory = inventoryTable.get(inventoryIdentifier);

		final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);
		inventoryLineRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryLineRecord.setC_UOM_ID(UomId.toRepoId(UomId.EACH));
		inventoryLineRecord.setM_Locator_ID(StepDefConstants.LOCATOR_ID.getRepoId());
		inventoryLineRecord.setM_AttributeSetInstance(null);
		inventoryLineRecord.setHUAggregationType(HUAggregationType.SINGLE_HU.getCode());
		inventoryLineRecord.setIsCounted(true);

		inventoryLineRecord.setM_Inventory_ID(mInventory.getM_Inventory_ID());
		inventoryLineRecord.setQtyBook(qtyBook);
		inventoryLineRecord.setQtyCount(qtyCount);

		if (productId == null)
		{
			final I_M_Product product = productTable.get(productIdentifier);
			inventoryLineRecord.setM_Product_ID(product.getM_Product_ID());
		}
		else
		{
			final I_M_Product productById = InterfaceWrapperHelper.load(productId, I_M_Product.class);
			productTable.put(productIdentifier, productById);

			inventoryLineRecord.setM_Product_ID(productById.getM_Product_ID());
		}
		save(inventoryLineRecord);

		inventoryLineTable.put(inventoryLineIdentifier, inventoryLineRecord);
	}
}
