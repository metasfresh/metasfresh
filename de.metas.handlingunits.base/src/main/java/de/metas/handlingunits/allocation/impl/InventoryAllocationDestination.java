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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.minventory.api.IInventoryBL;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTransaction;
import de.metas.handlingunits.hutransaction.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.snapshot.ISnapshotProducer;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialsCollector;
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
	protected final Map<Integer, ISnapshotProducer<I_M_HU>> inventoryToSnapshot = new HashMap<>();
	// services
	private static transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private final I_M_Warehouse warehouse;
	private final int chargeId;
	private final I_M_Locator defaultLocator;

	private List<I_M_Inventory> inventories = new ArrayList<I_M_Inventory>();

	private final I_C_DocType inventoryDocType;

	/**
	 * There will be an inventory entry for each partner
	 */
	private final Map<Integer, I_M_Inventory> orderIdToInventory = new HashMap<Integer, I_M_Inventory>();

	private HUPackingMaterialsCollector collector = null;

	/**
	 * Map the inventory lines to the base inout lines
	 */
	private final Map<Integer, I_M_InventoryLine> inOutLineId2InventoryLine = new HashMap<Integer, I_M_InventoryLine>();

	public InventoryAllocationDestination(final I_M_Warehouse warehouse, final I_C_DocType inventoryDocType)
	{
		Check.assumeNotNull(warehouse, "Warehouse not null");

		this.warehouse = warehouse;
		defaultLocator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);

		this.inventoryDocType = inventoryDocType;

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

		final ITableRecordReference reference = request.getReference();

		if (collector == null)
		{
			collector = new HUPackingMaterialsCollector(request.getHUContext());
		}

		if (InterfaceWrapperHelper.isInstanceOf(reference, I_M_HU_Item.class))
		{
			final I_M_HU_Item huItem = InterfaceWrapperHelper.create(
					request.getHUContext().getCtx(), reference.getRecord_ID(), I_M_HU_Item.class, trxName);

			final I_M_HU hu = huItem.getM_HU();

			final I_M_HU topLevelParent = handlingUnitsBL.getTopLevelParent(hu);

			final List<I_M_InOutLine> inOutLines = Services.get(IHUAssignmentDAO.class).retrieveModelsForHU(topLevelParent, I_M_InOutLine.class);

			for (final I_M_InOutLine inOutLine : inOutLines)
			{

			//	collector.addHURecursively(hu, inOutLine);

				final I_M_InOut inout = inOutLine.getM_InOut();

				if (inout.isSOTrx())
				{
					// in case the base inout line is from a shipment, it is not relevant for the material disposal ( for the time being)
					throw new AdempiereException("Document type {0} is not suitable for material disposal", new Object[] { inout.getC_DocType() });

				}

				// #1604: skip inoutlines for other products; request.getProduct() is not null, see AllocationRequest constructor
				if (inOutLine.getM_Product_ID() != request.getProduct().getM_Product_ID())
				{
					continue;
				}

				// create the inventory line based on the info from inoutline and request
				final I_M_InventoryLine inventoryLine = getCreateInventoryLine(inOutLine, topLevelParent, request);

				final BigDecimal qtyInternalUseOld = inventoryLine.getQtyInternalUse();
				final BigDecimal qtyInternalUseNew = qtyInternalUseOld.add(qty);
				inventoryLine.setQtyInternalUse(qtyInternalUseNew);

				collector.addHURecursively(hu, inOutLine);
				final int countTUs = collector.getAndResetCountTUs();

				final BigDecimal qtyTU = inventoryLine.getQtyTU().add(new BigDecimal(countTUs));
				inventoryLine.setQtyTU(qtyTU);
				InterfaceWrapperHelper.save(inventoryLine, trxName);
				
				
				Services.get(IHUAssignmentBL.class).assignHU(inventoryLine, topLevelParent, ITrx.TRXNAME_ThreadInherited);

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

	private I_M_Inventory getCreateInventoryHeader(final I_M_InOutLine inOutLine, final IAllocationRequest request)
	{

		final I_M_InOut inout = inOutLine.getM_InOut();

		// final I_C_BPartner partner = inout.getC_BPartner();

		final I_C_Order order = inout.getC_Order();

		Check.assumeNotNull(order, "Inout {0} does not have an order", inout);

		final int orderId = order.getC_Order_ID();

		if (orderIdToInventory.containsKey(orderId))
		{
			return orderIdToInventory.get(orderId);
		}

		// No inventory for the given partner. Create it now.
		final IHUContext huContext = request.getHUContext();
		Services.get(ITrxManager.class).assertTrxNotNull(huContext);
		final I_M_Inventory inventory = InterfaceWrapperHelper.newInstance(I_M_Inventory.class, huContext);
		inventory.setMovementDate(TimeUtil.asTimestamp(request.getDate()));
		inventory.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());

		if (inventoryDocType != null)
		{
			inventory.setC_DocType_ID(inventoryDocType.getC_DocType_ID());
		}

		inventory.setPOReference(order.getPOReference());
		InterfaceWrapperHelper.save(inventory);

		orderIdToInventory.put(orderId, inventory);
		inventories.add(inventory);

		// #2143 HU snapshots
		final ISnapshotProducer<I_M_HU> huSnapshotProducer = Services.get(IHUSnapshotDAO.class)
				.createSnapshot()
				.setContext(huContext);

		inventoryToSnapshot.put(inventory.getM_Inventory_ID(), huSnapshotProducer);
		
		final de.metas.handlingunits.model.I_M_Inventory huInventory = InterfaceWrapperHelper.create(inventory, de.metas.handlingunits.model.I_M_Inventory.class);
		huInventory.setSnapshot_UUID(huSnapshotProducer.getSnapshotId());
		InterfaceWrapperHelper.save(huInventory);

		return inventory;
	}

	public List<I_M_Inventory> getInventories()
	{
		return inventories;
	}

//	public void createHUSnapshots()
//	{
//		for (final I_M_Inventory inventory : inventories)
//		{
//			final ISnapshotProducer<I_M_HU> currentSnapshotProducer = inventoryToSnapshot.get(inventory.getM_Inventory_ID());
//
//			// Create the snapshots for all enqueued HUs so far.
//			currentSnapshotProducer.createSnapshots();
//
//			// Set the Snapshot_UUID to current receipt (for later recall and reporting).
//
//			final de.metas.handlingunits.model.I_M_Inventory huInventory = InterfaceWrapperHelper.create(inventory, de.metas.handlingunits.model.I_M_Inventory.class);
//			huInventory.setSnapshot_UUID(currentSnapshotProducer.getSnapshotId());
//			InterfaceWrapperHelper.save(huInventory);
//		}
//	}

	private I_M_InventoryLine getCreateInventoryLine(final I_M_InOutLine inOutLine, final I_M_HU hu, final IAllocationRequest request)
	{
		final int inOutLineId = inOutLine.getM_InOutLine_ID();

		if (inOutLineId2InventoryLine.containsKey(inOutLineId))
		{

			return inOutLineId2InventoryLine.get(inOutLineId);
		}

		//
		// Create a new Inventory Line
		final IHUContext huContext = request.getHUContext();
		final I_M_InventoryLine inventoryLine = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class, huContext);

		final I_M_Inventory inventoryHeader = getCreateInventoryHeader(inOutLine, request);
		inventoryLine.setM_Inventory_ID(inventoryHeader.getM_Inventory_ID());
		inventoryLine.setM_Product_ID(inOutLine.getM_Product_ID());
		inventoryLine.setC_Charge_ID(chargeId);
		inventoryLine.setM_Locator_ID(defaultLocator.getM_Locator_ID());
		inventoryLine.setM_InOutLine(inOutLine);

		inventoryLine.setC_UOM(inOutLine.getC_UOM());

		// set the M_HU_PI_Item_Product from the HU
		inventoryLine.setM_HU_PI_Item_Product(hu.getM_HU_PI_Item_Product());

		Services.get(IAttributeSetInstanceBL.class).cloneASI(inventoryLine, inOutLine);

		inOutLineId2InventoryLine.put(inOutLineId, inventoryLine);

		// #2143 hu snapshots
		final ISnapshotProducer<I_M_HU> currentSnapshotProducer = inventoryToSnapshot.get(inventoryHeader.getM_Inventory_ID());
		currentSnapshotProducer.addModel(hu);
		currentSnapshotProducer.createSnapshots();
		


		// NOTE: we are not saving here

		return inventoryLine;
	}

}
