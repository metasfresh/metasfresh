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

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Optionals;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Product;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.waitUtilPosted;
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
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@NonNull private final M_Inventory_StepDefData inventoryTable;
	@NonNull private final M_InventoryLine_StepDefData inventoryLineTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	@NonNull private final M_HU_PI_Item_Product_StepDefData huPIItemProductTable;
	@NonNull private final M_HU_PI_StepDefData huPITable;

	@Given("metasfresh contains M_Inventories:")
	public void createInventories(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_Inventory.COLUMNNAME_M_Inventory_ID)
				.forEach(this::createInventory);
	}

	@Given("metasfresh contains M_InventoriesLines:")
	public void addNewInventoryLines(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID)
				.forEach(this::addNewInventoryLine);
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

	@And("complete inventory with inventoryIdentifier {string}")
	public void complete_inventory(@NonNull final String identifiersString)
	{
		final List<StepDefDataIdentifier> identifiers = StepDefDataIdentifier.ofCommaSeparatedString(identifiersString);
		identifiers.forEach(identifier -> {
			final InventoryId inventoryId = inventoryTable.getId(identifier);
			inventoryService.completeDocument(inventoryId);
		});
	}

	private InventoryId createInventory(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier warehouseIdentifier = row.getAsIdentifier(I_M_Inventory.COLUMNNAME_M_Warehouse_ID);
		final WarehouseId warehouseId = warehouseTable.getIdOptional(warehouseIdentifier)
				.orElseGet(() -> warehouseIdentifier.getAsId(WarehouseId.class));

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryRecord.setM_Warehouse_ID(warehouseId.getRepoId());
		inventoryRecord.setMovementDate(row.getAsLocalDateTimestamp(I_M_Inventory.COLUMNNAME_MovementDate));
		inventoryRecord.setDocumentNo(row.getAsOptionalString(I_M_Inventory.COLUMNNAME_DocumentNo).map(StringUtils::trimBlankToNull).orElse("not_important"));
		saveRecord(inventoryRecord);

		row.getAsOptionalIdentifier().ifPresent(inventoryIdentifier -> inventoryTable.put(inventoryIdentifier, inventoryRecord));

		return InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());
	}

	private I_M_InventoryLine addNewInventoryLine(@NonNull final DataTableRow row)
	{
		final I_M_InventoryLine inventoryLine = newInstance(I_M_InventoryLine.class);

		final I_M_Inventory inventory = inventoryTable.get(row.getAsIdentifier(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID));
		final WarehouseId warehouseId = WarehouseId.ofRepoId(inventory.getM_Warehouse_ID());

		inventoryLine.setM_Inventory_ID(inventory.getM_Inventory_ID());

		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_InventoryLine.COLUMNNAME_M_Product_ID);
		final ProductId productId = productTable.getIdOptional(productIdentifier)
				.orElseGet(() -> productIdentifier.getAsId(ProductId.class));

		final LocatorId locatorId = row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_Locator_ID)
				.map(locatorTable::getId)
				.orElseGet(() -> warehouseBL.getOrCreateDefaultLocatorId(warehouseId));
		assertThat(locatorId.getWarehouseId()).as("line locator is matching header warehouse").isEqualTo(warehouseId);

		inventoryLine.setM_Locator_ID(locatorId.getRepoId());
		inventoryLine.setM_Product_ID(productId.getRepoId());

		final Quantity qtyCount = row.getAsQuantity("QtyCount", "UOM.X12DE355", uomDAO::getByX12DE355);
		final Quantity qtyBook = row.getAsQuantity("QtyBook", "UOM.X12DE355", uomDAO::getByX12DE355);
		final UomId uomId = Quantity.getCommonUomIdOfAll(qtyCount, qtyBook);
		inventoryLine.setC_UOM_ID(uomId.getRepoId());
		inventoryLine.setQtyCount(qtyCount.toBigDecimal());
		inventoryLine.setQtyBook(qtyBook.toBigDecimal());
		inventoryLine.setIsCounted(true);

		row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(asiId -> inventoryLine.setM_AttributeSetInstance_ID(asiId.getRepoId()));

		row.getAsOptionalBigDecimal(I_M_InventoryLine.COLUMNNAME_QtyInternalUse)
				.ifPresent(inventoryLine::setQtyInternalUse);

		row.getAsOptionalBigDecimal(I_M_InventoryLine.COLUMNNAME_CostPrice)
				.ifPresent(costPrice -> {
					inventoryLine.setIsExplicitCostPrice(true);
					inventoryLine.setCostPrice(costPrice);
				});

		row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.map(huPIItemProductTable::getId)
				.ifPresent(huPIItemProductId -> inventoryLine.setM_HU_PI_Item_Product_ID(huPIItemProductId.getRepoId()));
		row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_LU_HU_PI_ID)
				.map(huPITable::getId)
				.ifPresent(luPIId -> inventoryLine.setM_LU_HU_PI_ID(luPIId.getRepoId()));

		if (isInboundTrx(inventoryLine))
		{
			row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_HU_ID)
					.flatMap(huTable::getIdOptional)
					.ifPresent(huId -> inventoryLine.setM_HU_ID(huId.getRepoId()));
		}
		else
		{
			final HuId huId = row.getAsIdentifier(I_M_InventoryLine.COLUMNNAME_M_HU_ID).lookupIdIn(huTable);
			assert huId != null;
			inventoryLine.setM_HU_ID(huId.getRepoId());
		}

		saveRecord(inventoryLine);

		row.getAsOptionalIdentifier().ifPresent(identifier -> inventoryLineTable.put(identifier, inventoryLine));

		return inventoryLine;
	}

	private static boolean isInboundTrx(final I_M_InventoryLine inventoryLine)
	{
		return inventoryLine.getQtyCount().subtract(inventoryLine.getQtyBook()).signum() >= 0;
	}

	@Given("metasfresh contains single line completed inventories")
	public void addSingleLineInventories(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::addSingleLineInventory);
	}

	private void addSingleLineInventory(@NonNull final DataTableRow row) throws InterruptedException
	{
		row.setAdditionalRowIdentifierColumnName(I_M_Inventory.COLUMNNAME_M_Inventory_ID);
		final InventoryId inventoryId = createInventory(row);

		row.setAdditionalRowIdentifierColumnName(I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID);
		final I_M_InventoryLine inventoryLine = addNewInventoryLine(row);

		inventoryService.completeDocument(inventoryId);

		if (isInboundTrx(inventoryLine))
		{
			final InventoryLineId inventoryLineId = InventoryLineId.ofRepoId(inventoryLine.getM_InventoryLine_ID());
			loadInventoryLineHUs(row, inventoryId, inventoryLineId);
		}

		waitUtilPosted(TableRecordReference.of(I_M_Inventory.Table_Name, inventoryId));
	}

	private void loadInventoryLineHUs(final @NotNull DataTableRow row, final InventoryId inventoryId, final InventoryLineId inventoryLineId)
	{
		final ArrayList<StepDefDataIdentifier> huIdentifiers = new ArrayList<>();
		for (int i = 1; i <= 10; i++)
		{
			if (i == 1)
			{
				Optionals.firstPresentOfSuppliers(
						() -> row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_HU_ID),
						() -> row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_HU_ID + "1")
				).ifPresent(huIdentifiers::add);
			}
			else
			{
				row.getAsOptionalIdentifier(I_M_InventoryLine.COLUMNNAME_M_HU_ID + i).ifPresent(huIdentifiers::add);
			}
		}

		final InventoryLine inventoryLine = inventoryService.getById(inventoryId).getLineById(inventoryLineId);
		final ImmutableList<InventoryLineHU> inventoryLineHUs = inventoryLine.getInventoryLineHUs();
		assertThat(inventoryLineHUs).as("inventoryLineHUs").hasSameSizeAs(huIdentifiers);
		for (int i = 0; i < huIdentifiers.size(); i++)
		{
			final InventoryLineHU inventoryLineHU = inventoryLineHUs.get(i);
			final HuId huId = inventoryLineHU.getHuId();
			assertThat(huId).as(() -> "inventory line HU has HU set for line " + inventoryLineHU).isNotNull();
			huTable.put(huIdentifiers.get(i), handlingUnitsBL.getById(huId));
		}
	}
}
