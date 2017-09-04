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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

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
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Inventory;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.hutransaction.IHUTransaction;
import de.metas.handlingunits.hutransaction.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.snapshot.ISnapshotProducer;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector.HUpipToHUPackingMaterialCollectorSource;
import de.metas.inoutcandidate.spi.impl.InOutLineHUPackingMaterialCollectorSource;
import de.metas.product.IProductBL;
import lombok.NonNull;

/**
 * {@link IAllocationDestination} which is used to generate Internal Use Inventory documents for quantity that is asked to be loaded here.
 *
 * Useful when you want to destroy an HU and you want also to "internal use" it's M_Storage counter-part.
 *
 * @task http://dewiki908/mediawiki/index.php/07050_Eigenverbrauch_metas_in_Existing_Window_Handling_Unit_Pos
 */
public class InventoryAllocationDestination implements IAllocationDestination
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final transient IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);
	private final transient IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final Map<Integer, ISnapshotProducer<I_M_HU>> huSnapshotProducerByInventoryId = new HashMap<>();

	private final I_M_Warehouse warehouse;
	private final int chargeId;
	private final I_M_Locator defaultLocator;

	private final List<I_M_Inventory> inventories = new ArrayList<>();

	private final I_C_DocType inventoryDocType;

	/**
	 * There will be an inventory entry for each partner
	 */
	private final Map<Integer, I_M_Inventory> orderIdToInventory = new HashMap<>();

	//
	// Packing materials
	private final Map<Integer, HUPackingMaterialsCollector> packingMaterialsCollectorByInventoryId = new HashMap<>();
	private final Set<Integer> packingMaterialsCollectedHUIds = new HashSet<>();
	private HUPackingMaterialsCollector pmCollectorForCountingTUs; // will be created on demand

	/**
	 * Map the inventory lines to the base inout lines
	 */
	private final Map<Integer, I_M_InventoryLine> inOutLineId2InventoryLine = new HashMap<>();

	public InventoryAllocationDestination(@NonNull final I_M_Warehouse warehouse, @Nullable final I_C_DocType inventoryDocType)
	{
		this.warehouse = warehouse;
		defaultLocator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);

		this.inventoryDocType = inventoryDocType;

		final Properties ctx = InterfaceWrapperHelper.getCtx(warehouse);
		chargeId = Services.get(IInventoryBL.class).getDefaultInternalChargeId(ctx);
	}

	/** @return created inventory documents */
	private List<I_M_Inventory> getInventories()
	{
		return ImmutableList.copyOf(inventories);
	}

	@Override
	public IAllocationResult load(final IAllocationRequest request)
	{
		final BigDecimal qtySource = request.getQty(); // Qty to add, in request's UOM
		final I_M_Product product = request.getProduct();
		final String trxName = request.getHUContext().getTrxName(); // We are using the huContext's trxName in case we have more than one line.
		final I_C_UOM uomTo = productBL.getStockingUOM(product);
		final BigDecimal qty = uomConversionBL.convertQty(product,
				qtySource,
				request.getC_UOM(),// uomFrom
				uomTo // uomTo
		);

		//
		// Create result
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		//
		// Get the HU Item from were we are unloading
		// If no HU Item found then return immediately.
		final ITableRecordReference reference = request.getReference();
		if (reference == null || !I_M_HU_Item.Table_Name.equals(reference.getTableName()))
		{
			return result;
		}
		final I_M_HU_Item huItem = reference.getModel(request.getHUContext(), I_M_HU_Item.class);

		//
		// Get receipt line(s) which received this HU
		final I_M_HU hu = huItem.getM_HU();
		final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParent(hu);
		final List<I_M_InOutLine> receiptLines = huAssignmentDAO.retrieveModelsForHU(topLevelHU, I_M_InOutLine.class);

		for (final I_M_InOutLine receiptLine : receiptLines)
		{
			final I_M_InOut receipt = receiptLine.getM_InOut();
			if (receipt.isSOTrx())
			{
				// in case the base inout line is from a shipment, it is not relevant for the material disposal (for the time being)
				throw new AdempiereException("Document type {0} is not suitable for material disposal", new Object[] { receipt.getC_DocType() });
			}

			// #1604: skip inoutlines for other products; request.getProduct() is not null, see AllocationRequest constructor
			if (receiptLine.getM_Product_ID() != request.getProduct().getM_Product_ID())
			{
				continue;
			}

			//
			// Get/create the inventory line based on the info from material receipt and request
			final I_M_InventoryLine inventoryLine = getCreateInventoryLine(receiptLine, topLevelHU, request);

			//
			// Update inventory line's internal use Qty
			{
				// FIXME: we are adding the whole "qty" for each inout line.
				// That might be a problem in case we have more than one receipt inout line.
				final BigDecimal qtyInternalUseOld = inventoryLine.getQtyInternalUse();
				final BigDecimal qtyInternalUseNew = qtyInternalUseOld.add(qty);
				inventoryLine.setQtyInternalUse(qtyInternalUseNew);
			}

			//
			// Calculate and update inventory line's QtyTU
			{
				 final BigDecimal countTUs = countTUs(request.getHUContext(), hu, receiptLine);
				 final BigDecimal qtyTU = inventoryLine.getQtyTU().add(countTUs);
				 inventoryLine.setQtyTU(qtyTU);
			}

			//
			// Collect HU's packing materials
			{

				final InOutLineHUPackingMaterialCollectorSource source = InOutLineHUPackingMaterialCollectorSource.of(receiptLine);
				collectPackingMaterials(request.getHUContext(), inventoryLine.getM_Inventory_ID(), hu, source);
				if (topLevelHU.getM_HU_ID() != hu.getM_HU_ID())
				{
					collectPackingMaterials_LUOnly(request.getHUContext(), inventoryLine.getM_Inventory_ID(), topLevelHU, source);
				}
			}

			setQtyTUAndHUPIP(request.getHUContext(), inventoryLine, receiptLine);

			//
			// Save the inventory line and assign the top level HU to it
			InterfaceWrapperHelper.save(inventoryLine, trxName);
			huAssignmentBL.assignHU(inventoryLine, topLevelHU, ITrx.TRXNAME_ThreadInherited);

			//
			// Update the result
			{
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

	private void setQtyTUAndHUPIP(final IHUContext huContext,final I_M_InventoryLine inventoryLine,final  I_M_InOutLine receiptLine)
	{
		final HUPackingMaterialsCollector createPackingMaterialsCollectorForInventory = getCreatePackingMaterialsCollectorForInventory(huContext, inventoryLine.getM_Inventory_ID());
		final Map<HUpipToHUPackingMaterialCollectorSource, Integer> huPIPToSource = createPackingMaterialsCollectorForInventory.getHuPIPToSource();

		Set<HUpipToHUPackingMaterialCollectorSource> keySet = huPIPToSource.keySet();

		for (HUpipToHUPackingMaterialCollectorSource huPipToHUPackingMaterialCollectorSource : keySet)
		{
			if (huPipToHUPackingMaterialCollectorSource.getOriginalSourceID() == receiptLine.getM_InOutLine_ID())
			{
				
				final BigDecimal qtyTU = inventoryLine.getQtyTU();
				Integer hupipid = huPIPToSource.get(huPipToHUPackingMaterialCollectorSource);
				inventoryLine.setQtyTU(qtyTU.add(BigDecimal.valueOf(hupipid)));

				inventoryLine.setM_HU_PI_Item_Product_ID(huPipToHUPackingMaterialCollectorSource.getM_HU_PI_Item_Product_ID());

			}
		}

	}

	public List<I_M_Inventory> completeInventories()
	{
		final List<I_M_Inventory> inventories = getInventories();
		for (final I_M_Inventory inventory : inventories)
		{
			docActionBL.processEx(inventory, X_M_Inventory.DOCACTION_Complete, X_M_Inventory.DOCSTATUS_Completed);

			//
			// Create empties movement
			final int inventoryId = inventory.getM_Inventory_ID();
			final HUPackingMaterialsCollector packingMaterialsCollector = getPackingMaterialsCollectorForInventory(inventoryId);
			if (packingMaterialsCollector != null)
			{
				huEmptiesService.newEmptiesMovementProducer()
						.setEmptiesMovementDirectionAuto()
						.addCandidates(packingMaterialsCollector.getAndClearCandidates())
						.setReferencedInventoryId(inventoryId)
						.createMovements();
			}
		}

		return inventories;
	}

	private I_M_Inventory getCreateInventoryHeader(final I_M_InOutLine inOutLine, final IAllocationRequest request)
	{
		final I_M_InOut inout = inOutLine.getM_InOut();
		final I_C_Order order = inout.getC_Order();
		Check.assumeNotNull(order, "Inout {0} does not have an order", inout);
		final int orderId = order.getC_Order_ID();

		// Existing inventory
		if (orderIdToInventory.containsKey(orderId))
		{
			return orderIdToInventory.get(orderId);
		}

		// No inventory for the given partner. Create it now.
		final IHUContext huContext = request.getHUContext();
		trxManager.assertTrxNotNull(huContext);
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
		final ISnapshotProducer<I_M_HU> huSnapshotProducer = huSnapshotDAO
				.createSnapshot()
				.setContext(huContext);

		huSnapshotProducerByInventoryId.put(inventory.getM_Inventory_ID(), huSnapshotProducer);

		inventory.setSnapshot_UUID(huSnapshotProducer.getSnapshotId());
		InterfaceWrapperHelper.save(inventory);

		return inventory;
	}

	// public void createHUSnapshots()
	// {
	// for (final I_M_Inventory inventory : inventories)
	// {
	// final ISnapshotProducer<I_M_HU> currentSnapshotProducer = inventoryToSnapshot.get(inventory.getM_Inventory_ID());
	//
	// // Create the snapshots for all enqueued HUs so far.
	// currentSnapshotProducer.createSnapshots();
	//
	// // Set the Snapshot_UUID to current receipt (for later recall and reporting).
	//
	// final de.metas.handlingunits.model.I_M_Inventory huInventory = InterfaceWrapperHelper.create(inventory, de.metas.handlingunits.model.I_M_Inventory.class);
	// huInventory.setSnapshot_UUID(currentSnapshotProducer.getSnapshotId());
	// InterfaceWrapperHelper.save(huInventory);
	// }
	// }

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
		// if (hu.getM_HU_PI_Item_Product() != null)
		// {
		// inventoryLine.setM_HU_PI_Item_Product(hu.getM_HU_PI_Item_Product());
		// }

		Services.get(IAttributeSetInstanceBL.class).cloneASI(inventoryLine, inOutLine);

		inOutLineId2InventoryLine.put(inOutLineId, inventoryLine);

		// #2143 hu snapshots
		final ISnapshotProducer<I_M_HU> currentSnapshotProducer = huSnapshotProducerByInventoryId.get(inventoryHeader.getM_Inventory_ID());
		currentSnapshotProducer.addModel(hu);
		currentSnapshotProducer.createSnapshots();

		// NOTE: we are not saving here

		return inventoryLine;
	}

	 private BigDecimal countTUs(final IHUContext huContext, final I_M_HU hu, final I_M_InOutLine receiptLine)
	 {
	 final InOutLineHUPackingMaterialCollectorSource inOutLineSource = InOutLineHUPackingMaterialCollectorSource.of(receiptLine);
	 if (pmCollectorForCountingTUs == null)
	 {
	 pmCollectorForCountingTUs = new HUPackingMaterialsCollector(huContext);
	 }
	 pmCollectorForCountingTUs.addHURecursively(hu, inOutLineSource);
	 final int countTUs = pmCollectorForCountingTUs.getAndResetCountTUs();
	 return BigDecimal.valueOf(countTUs);
	 }

	private void collectPackingMaterials(final IHUContext huContext, final int inventoryId, final I_M_HU hu, final IHUPackingMaterialCollectorSource source)
	{
		final HUPackingMaterialsCollector collector = getCreatePackingMaterialsCollectorForInventory(huContext, inventoryId);
		// final IHUPackingMaterialCollectorSource source = null;
		collector.addHURecursively(hu, source);
	}

	private void collectPackingMaterials_LUOnly(final IHUContext huContext, final int inventoryId, final I_M_HU luHU, final IHUPackingMaterialCollectorSource source)
	{
		final HUPackingMaterialsCollector collector = getCreatePackingMaterialsCollectorForInventory(huContext, inventoryId);
		// final IHUPackingMaterialCollectorSource source = null;
		collector.addLU(luHU, source);
	}

	private HUPackingMaterialsCollector getCreatePackingMaterialsCollectorForInventory(final IHUContext huContext, final int inventoryId)
	{
		return packingMaterialsCollectorByInventoryId.computeIfAbsent(inventoryId, k -> {
			final HUPackingMaterialsCollector c = new HUPackingMaterialsCollector(huContext);
			c.setSeenM_HU_IDs_ToAdd(packingMaterialsCollectedHUIds);
			c.setisCollectAggregatedHUs(true);
			c.setisCollectTUNumberPerOrigin(true);
			return c;
		});
	}

	private HUPackingMaterialsCollector getPackingMaterialsCollectorForInventory(final int inventoryId)
	{
		return packingMaterialsCollectorByInventoryId.get(inventoryId);
	}
}
