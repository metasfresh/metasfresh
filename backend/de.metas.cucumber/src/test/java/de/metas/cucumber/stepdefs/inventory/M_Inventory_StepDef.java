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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.docType.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
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

import java.sql.Timestamp;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_Inventory_StepDef
{
	@NonNull private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@NonNull private final M_Inventory_StepDefData inventoryTable;
	@NonNull private final M_InventoryLine_StepDefData inventoryLineTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	@NonNull private final C_Flatrate_Term_StepDefData flatrateTermTable;
	@NonNull private final C_DocType_StepDefData docTypeTable;

	@Given("metasfresh contains M_Inventories:")
	public void addNewInventory(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::addNewInventory);
	}

	@Given("metasfresh contains M_InventoriesLines:")
	public void addNewInventoryLines(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::addNewInventoryLine);
	}

	@Given("^the inventory identified by (.*) is (completed|reversed)")
	public void inventory_is_completed(@NonNull final String inventoryIdentifier, @NonNull final String action)
	{
		final I_M_Inventory inventory = inventoryTable.get(inventoryIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case completed ->
			{
				inventory.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(inventory, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
			}
			case reversed ->
			{
				inventory.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(inventory, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
			}
			default -> throw new AdempiereException("Unhandled M_Inventory action")
					.appendParametersToMessage()
					.setParameter("action:", action);
		}
	}

	@And("the following virtual inventory is created")
	public void createVirtualInventory(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentScheduleRecord = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		assertThat(productRecord).isNotNull();

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

	@And("metasfresh initially has M_Inventory data")
	public void setupM_Inventory_Data(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createM_Inventory);
	}

	@And("metasfresh initially has M_InventoryLine data")
	public void setupM_InventoryLine_Data(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createM_InventoryLine);
	}

	@And("complete inventory with inventoryIdentifier {string}")
	public void complete_inventory(@NonNull final String inventoryIdentifier)
	{
		final InventoryId inventoryId = inventoryTable.getId(inventoryIdentifier);
		inventoryService.completeDocument(inventoryId);
	}

	private void addNewInventory(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier warehouseIdentifier = row.getAsIdentifier(I_M_Inventory.COLUMNNAME_M_Warehouse_ID);
		final WarehouseId warehouseId = warehouseTable.getIdOptional(warehouseIdentifier)
				.orElseGet(() -> warehouseIdentifier.getAsId(WarehouseId.class));

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);

		inventoryRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryRecord.setM_Warehouse_ID(warehouseId.getRepoId());
		inventoryRecord.setMovementDate(row.getAsInstantTimestamp(I_M_Inventory.COLUMNNAME_MovementDate));

		final String documentNo = row.getAsOptionalString(I_M_Inventory.COLUMNNAME_DocumentNo)
				.map(StringUtils::trimBlankToNull)
				.orElse("not_important");
		inventoryRecord.setDocumentNo(documentNo);

		row.getAsOptionalIdentifier(I_M_Inventory.COLUMNNAME_C_DocType_ID)
				.map(docTypeTable::get)
				.ifPresent(docTypeRecord -> inventoryRecord.setC_DocType_ID(docTypeRecord.getC_DocType_ID()));

		saveRecord(inventoryRecord);

		row.getAsOptionalIdentifier(I_M_Inventory.COLUMNNAME_M_Inventory_ID).ifPresent(inventoryIdentifier -> inventoryTable.put(inventoryIdentifier, inventoryRecord));
		row.getAsOptionalIdentifier().ifPresent(inventoryIdentifier -> inventoryTable.put(inventoryIdentifier, inventoryRecord));
	}

	private void addNewInventoryLine(@NonNull final DataTableRow row)
	{
		final de.metas.invoicecandidate.model.I_M_InventoryLine inventoryLine = newInstance(de.metas.invoicecandidate.model.I_M_InventoryLine.class);

		final I_M_Inventory inventory = inventoryTable.get(row.getAsIdentifier(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID));
		final WarehouseId warehouseId = WarehouseId.ofRepoId(inventory.getM_Warehouse_ID());

		inventoryLine.setM_Inventory_ID(inventory.getM_Inventory_ID());

		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_InventoryLine.COLUMNNAME_M_Product_ID);
		final ProductId productId = productTable.getIdOptional(productIdentifier)
				.orElseGet(() -> productIdentifier.getAsId(ProductId.class));

		inventoryLine.setM_Locator_ID(warehouseBL.getOrCreateDefaultLocatorId(warehouseId).getRepoId());
		inventoryLine.setM_Product_ID(productId.getRepoId());

		inventoryLine.setQtyCount(row.getAsBigDecimal("QtyCount"));
		inventoryLine.setQtyBook(row.getAsBigDecimal("QtyBook"));
		inventoryLine.setIsCounted(true);

		final X12DE355 uom = X12DE355.ofCode(row.getAsString("UOM.X12DE355"));
		final UomId uomId = uomDAO.getUomIdByX12DE355(uom);
		inventoryLine.setC_UOM_ID(uomId.getRepoId());

		row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(asiId -> inventoryLine.setM_AttributeSetInstance_ID(asiId.getRepoId()));

		row.getAsOptionalBigDecimal(I_M_InventoryLine.COLUMNNAME_QtyInternalUse)
				.ifPresent(inventoryLine::setQtyInternalUse);

		row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_Modular_Flatrate_Term_ID)
				.map(flatrateTermTable::getId)
				.ifPresent(modularContractId -> inventoryLine.setModular_Flatrate_Term_ID(modularContractId.getRepoId()));

		saveRecord(inventoryLine);

		row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID).ifPresent(identifier -> inventoryLineTable.put(identifier, inventoryLine));
		row.getAsOptionalIdentifier().ifPresent(identifier -> inventoryLineTable.put(identifier, inventoryLine));
	}

	private void createM_Inventory(@NonNull final DataTableRow row)
	{
		final @NonNull StepDefDataIdentifier identifier = row.getAsIdentifier("M_Inventory_ID");
		final Timestamp movementDate = row.getAsLocalDateTimestamp("MovementDate");
		final String documentNo = row.getAsString("DocumentNo");

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryRecord.setC_DocType_ID(StepDefConstants.DOC_TYPE_ID_MMI.getRepoId());
		inventoryRecord.setDocStatus(DocStatus.Drafted.getCode());

		inventoryRecord.setMovementDate(movementDate);
		inventoryRecord.setDocumentNo(documentNo);

		saveRecord(inventoryRecord);

		inventoryTable.put(identifier, inventoryRecord);
	}

	private void createM_InventoryLine(@NonNull final DataTableRow row)
	{
		final InventoryId inventoryId = inventoryTable.getId(row.getAsIdentifier("M_Inventory_ID"));

		final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);
		inventoryLineRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryLineRecord.setC_UOM_ID(UomId.toRepoId(UomId.EACH));
		inventoryLineRecord.setM_Locator_ID(StepDefConstants.LOCATOR_ID.getRepoId());

		row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(asiId -> inventoryLineRecord.setM_AttributeSetInstance_ID(asiId.getRepoId()));

		inventoryLineRecord.setHUAggregationType(HUAggregationType.SINGLE_HU.getCode());
		inventoryLineRecord.setIsCounted(true);

		inventoryLineRecord.setM_Inventory_ID(inventoryId.getRepoId());
		inventoryLineRecord.setQtyBook(row.getAsBigDecimal("QtyBook"));
		inventoryLineRecord.setQtyCount(row.getAsBigDecimal("QtyCount"));

		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier("M_Product_ID");
		ProductId productId = productTable.getIdOptional(productIdentifier).orElse(null);
		if (productId == null)
		{
			productId = productIdentifier.getAsId(ProductId.class);
			final I_M_Product productById = productDAO.getById(productId);
			productTable.put(productIdentifier, productById);

		}
		inventoryLineRecord.setM_Product_ID(productId.getRepoId());

		InterfaceWrapperHelper.save(inventoryLineRecord);

		row.getAsOptionalIdentifier("M_InventoryLine_ID")
				.ifPresent(inventoryLineIdentifier -> inventoryLineTable.put(inventoryLineIdentifier, inventoryLineRecord));
	}
}
