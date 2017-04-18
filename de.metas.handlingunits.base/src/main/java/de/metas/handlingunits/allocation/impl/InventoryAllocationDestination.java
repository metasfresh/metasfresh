package de.metas.handlingunits.allocation.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.minventory.api.IInventoryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.process.DocAction;
import org.compiere.util.TimeUtil;

import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.product.IProductBL;

/**
 * {@link IAllocationDestination} which is used to generate Internal Use Inventory documents for quantity that is asked to be loaded here.
 *
 * Useful when you want to destroy an HU and you want also to "internal use" it's M_Storage counter-part.
 *
 * @task http://dewiki908/mediawiki/index.php/07050_Eigenverbrauch_metas_in_Existing_Window_Handling_Unit_Pos
 */
public class InventoryAllocationDestination implements IAllocationDestination
{
	private final I_M_Warehouse warehouse;
	private final int chargeId;
	private final I_M_Locator defaultLocator;

	private I_M_Inventory inventory = null;
	private final Map<Integer, I_M_InventoryLine> inOutLineId2InventoryLine = new HashMap<Integer, I_M_InventoryLine>();

	public InventoryAllocationDestination(final I_M_Warehouse warehouse)
	{
		Check.assumeNotNull(warehouse, "Warehouse not null");

		this.warehouse = warehouse;
		defaultLocator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);

		final Properties ctx = InterfaceWrapperHelper.getCtx(warehouse);
		chargeId = Services.get(IInventoryBL.class).getDefaultInternalChargeId(ctx);
	}

	@Override
	public IAllocationResult load(final IAllocationRequest request)
	{
		final BigDecimal qtySource = request.getQty(); // Qty to add, in request's UOM
		final I_M_Product product = request.getProduct();
		final String trxName = request.getHUContext().getTrxName(); // We are using the huContext's trxName in case we have more than one line.
		final I_C_UOM uomTo = Services.get(IProductBL.class).getStockingUOM(product);
		final BigDecimal qty = Services.get(IUOMConversionBL.class).convertQty(product,
				qtySource,
				request.getC_UOM(),// uomFrom
				uomTo // uomTo
		);

		//
		// Create result
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		// TODO!!!!!!!!
		final ITableRecordReference reference = request.getReference();

		if (InterfaceWrapperHelper.isInstanceOf(reference, I_M_HU_Item.class))
		{
			final I_M_HU_Item huItem = InterfaceWrapperHelper.create(
					request.getHUContext().getCtx(), reference.getRecord_ID(), I_M_HU_Item.class, trxName);

			final I_M_HU hu = huItem.getM_HU();
			
			final I_M_HU topLevelParent = Services.get(IHandlingUnitsBL.class).getTopLevelParent(hu);
			

			final List<I_M_InOutLine> inOutLines = Services.get(IHUAssignmentDAO.class).retrieveModelsForHU(topLevelParent, I_M_InOutLine.class);

			for (final I_M_InOutLine inOutLine : inOutLines)
			{
				final I_M_InventoryLine inventoryLine = getCreateInventoryLine(inOutLine, request);

				final BigDecimal qtyInternalUseOld = inventoryLine.getQtyInternalUse();
				final BigDecimal qtyInternalUseNew = qtyInternalUseOld.add(qty);
				inventoryLine.setQtyInternalUse(qtyInternalUseNew);
				InterfaceWrapperHelper.save(inventoryLine, trxName);

				result.substractAllocatedQty(qtySource);
				final IHUTransaction trx = new HUTransaction(
						inventoryLine, // Reference model
						null, // HU item
						null, // vHU item
						request, // request
						false); // out trx
				result.addTransaction(trx);
			}

		}

		return result;
	}

	private I_M_Inventory getCreateInventoryHeader(final IAllocationRequest request)
	{
		if (null != inventory)
		{
			return inventory;
		}

		// No inventory. Create it now.
		final IHUContext huContext = request.getHUContext();
		Services.get(ITrxManager.class).assertTrxNotNull(huContext);
		final I_M_Inventory inventory = InterfaceWrapperHelper.newInstance(I_M_Inventory.class, huContext);
		inventory.setMovementDate(TimeUtil.asTimestamp(request.getDate()));
		inventory.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());

		InterfaceWrapperHelper.save(inventory);

		this.inventory = inventory;

		return inventory;
	}

	public I_M_Inventory getInventory()
	{
		return inventory;
	}

	private I_M_InventoryLine getCreateInventoryLine(final I_M_InOutLine inOutLine, final IAllocationRequest request)
	{
		// final int productId = request.getProduct().getM_Product_ID();
		final int inOutLineId = inOutLine.getM_InOutLine_ID();

		if (inOutLineId2InventoryLine.containsKey(inOutLineId))
		{
			return inOutLineId2InventoryLine.get(inOutLineId);
		}

		//
		// Create a new Inventory Line
		final IHUContext huContext = request.getHUContext();
		final I_M_InventoryLine inventoryLine = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class, huContext);

		final I_M_Inventory inventoryHeader = getCreateInventoryHeader(request);
		inventoryLine.setM_Inventory_ID(inventoryHeader.getM_Inventory_ID());
		inventoryLine.setM_Product_ID(inOutLine.getM_Product_ID());
		inventoryLine.setC_Charge_ID(chargeId);
		inventoryLine.setM_Locator_ID(defaultLocator.getM_Locator_ID());
		inventoryLine.setM_InOutLine(inOutLine);

		inOutLineId2InventoryLine.put(inOutLineId, inventoryLine);

		// NOTE: we are not saving here

		return inventoryLine;
	}

	public void processInventory()
	{
		if (inventory == null)
		{
			// No inventory was created. Nothing to do.
			return;
		}

		// Finally, process the inventory document.
		Services.get(IDocActionBL.class).processEx(inventory, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

	}

	@Override
	public void loadComplete(final IHUContext huContext)
	{
		// Finally, process the inventory doc.

	}
}
