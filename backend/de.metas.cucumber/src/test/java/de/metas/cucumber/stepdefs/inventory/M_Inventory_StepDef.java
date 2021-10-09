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
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.uom.UomId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;

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

	public M_Inventory_StepDef(
			final StepDefData<I_M_Inventory> inventoryTable,
			final StepDefData<I_M_InventoryLine> inventoryLineTable)
	{
		this.inventoryTable = inventoryTable;
		this.inventoryLineTable = inventoryLineTable;
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
