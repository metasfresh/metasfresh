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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

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
import org.compiere.model.I_C_BPartner;
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

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.inventory.IHUInventoryBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.snapshot.ISnapshotProducer;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;
import de.metas.inout.IInOutDAO;
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
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final transient IHUPIItemProductDAO huPiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final transient IHUInventoryBL huInventoryBL = Services.get(IHUInventoryBL.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final transient IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);
	private final transient IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

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

	// NOTE: using a shared collector for counting because we want to avoid counting same HU in multiple places
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

			final BigDecimal qtyToMoveTotal = qty;

			final BigDecimal qualityDiscountPerc = huAttributesBL.getQualityDiscountPercent(hu);
			final BigDecimal qtyToMoveInDispute = qtyToMoveTotal.multiply(qualityDiscountPerc);
			final BigDecimal qtyToMove = qtyToMoveTotal.subtract(qtyToMoveInDispute);

			//
			// Get/create the inventory line based on the info from material receipt and request
			final I_M_InventoryLine inventoryLine = getCreateInventoryLine(receiptLine, request);

			// #2143 hu snapshots
			snapshotHUForInventoryLine(topLevelHU, inventoryLine);

			//
			// Update inventory line's internal use Qty
			{
				// FIXME: we are adding the whole "qty" for each inout line.
				// That might be a problem in case we have more than one receipt inout line.
				final BigDecimal qtyInternalUseOld = inventoryLine.getQtyInternalUse();
				final BigDecimal qtyInternalUseNew = qtyInternalUseOld.add(qtyToMove);
				inventoryLine.setQtyInternalUse(qtyInternalUseNew);
			}

			if (qtyToMoveInDispute.signum() != 0)
			{
				final I_M_InventoryLine inventoryLineInDispute = getCreateInventoryLineInDispute(receiptLine, request);

				final BigDecimal inventoryLine_Qty_Old = inventoryLineInDispute.getQtyInternalUse();
				final BigDecimal inventoryLine_Qty_New = inventoryLine_Qty_Old.add(qtyToMoveInDispute);
				inventoryLineInDispute.setQtyInternalUse(inventoryLine_Qty_New);

				// Make sure the inout line is saved
				InterfaceWrapperHelper.save(inventoryLineInDispute);
			}

			setInventoryLinePiip(hu, inventoryLine);

			final I_M_HU tuHU = retrieveTu(hu);
			//
			// Calculate and update inventory line's QtyTU
			{
				final BigDecimal countTUs = countTUs(request.getHUContext(), tuHU, inventoryLine);
				final BigDecimal qtyTU = inventoryLine.getQtyTU().add(countTUs);
				inventoryLine.setQtyTU(qtyTU);
			}

			//
			// Collect HU's packing materials
			{
				collectPackingMaterials(request.getHUContext(), inventoryLine.getM_Inventory_ID(), tuHU);
				if (topLevelHU.getM_HU_ID() != hu.getM_HU_ID())
				{
					collectPackingMaterials_LUOnly(request.getHUContext(), inventoryLine.getM_Inventory_ID(), topLevelHU);
				}
			}

			//
			// Save the inventory line and assign the top level HU to it
			InterfaceWrapperHelper.save(inventoryLine, trxName);
			huInventoryBL.assignHU(inventoryLine, topLevelHU);

			//
			// Update the result
			{
				result.subtractAllocatedQty(qtySource);

				final IHUTransactionCandidate trx = new HUTransactionCandidate(
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

	private I_M_InventoryLine getCreateInventoryLineInDispute(final I_M_InOutLine receiptLine, final IAllocationRequest request)
	{
		final I_M_InOutLine originInOutLineInDispute = InterfaceWrapperHelper.create(inOutDAO.retrieveLineWithQualityDiscount(receiptLine), I_M_InOutLine.class);

		if (originInOutLineInDispute == null)
		{
			return null;
		}

		final I_M_InventoryLine inoutLineInDispute = getCreateInventoryLine(originInOutLineInDispute, request);

		inoutLineInDispute.setIsInDispute(true);
		inoutLineInDispute.setQualityDiscountPercent(originInOutLineInDispute.getQualityDiscountPercent());
		inoutLineInDispute.setQualityNote(originInOutLineInDispute.getQualityNote());
		return inoutLineInDispute;
	}

	public List<I_M_Inventory> processInventories()
	{

		final List<I_M_Inventory> inventories = getInventories();

		for (final I_M_Inventory inventory : inventories)
		{
			createHUSnapshotsForInventory(inventory);
			completeInventory(inventory);
			createEmptiesMovementForInventory(inventory);
		}
		return inventories;
	}

	/**
	 * Complete inventory document
	 * 
	 * @param inventory
	 */
	private void completeInventory(I_M_Inventory inventory)
	{
		docActionBL.processEx(inventory, X_M_Inventory.DOCACTION_Complete, X_M_Inventory.DOCSTATUS_Completed);
	}

	/**
	 * Create HU snapshots recursively for all the HUs linked to this inventory
	 * 
	 * @param inventory
	 */
	private void createHUSnapshotsForInventory(final I_M_Inventory inventory)
	{
		final ISnapshotProducer<I_M_HU> currentSnapshotProducer = huSnapshotProducerByInventoryId.get(inventory.getM_Inventory_ID());
		currentSnapshotProducer.createSnapshots();
	}

	/**
	 * Move the handling units used in inventory from their current warehouse to the handling units warehouse.
	 * 
	 * @param inventory
	 */
	private void createEmptiesMovementForInventory(final I_M_Inventory inventory)
	{
		final int inventoryId = inventory.getM_Inventory_ID();
		final HUPackingMaterialsCollector packingMaterialsCollector = getPackingMaterialsCollectorForInventory(inventoryId);

		huEmptiesService.newEmptiesMovementProducer()
				.setEmptiesMovementDirectionAuto()
				.addCandidates(packingMaterialsCollector.getAndClearCandidates())
				.setReferencedInventoryId(inventoryId)
				.createMovements();

	}

	/**
	 * Return inventoryLine for inoutLine if it exists, create an inventory line otherwise
	 * 
	 * @param inOutLine
	 * @param request
	 * @return
	 */
	private I_M_InventoryLine getCreateInventoryLine(
			@NonNull final I_M_InOutLine inOutLine,
			@NonNull final IAllocationRequest request)
	{
		final int inOutLineId = inOutLine.getM_InOutLine_ID();

		if (inOutLineId2InventoryLine.containsKey(inOutLineId))
		{
			return inOutLineId2InventoryLine.get(inOutLineId);
		}

		final I_M_InventoryLine inventoryLine = createInventoryLine(inOutLine, request);

		return inventoryLine;

	}

	private I_M_InventoryLine createInventoryLine(
			@NonNull final I_M_InOutLine inOutLine,
			@NonNull final IAllocationRequest request)
	{
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

		Services.get(IAttributeSetInstanceBL.class).cloneASI(inventoryLine, inOutLine);

		inOutLineId2InventoryLine.put(inOutLine.getM_InOutLine_ID(), inventoryLine);

		// NOTE: we are not saving here
		return inventoryLine;
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

		final I_M_Inventory inventory = createInventoryHeader(order, request);

		return inventory;

	}

	private I_M_Inventory createInventoryHeader(final I_C_Order order, final IAllocationRequest request)
	{
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

		orderIdToInventory.put(order.getC_Order_ID(), inventory);
		inventories.add(inventory);

		createAndLinkSnapshotProducerForInventory(huContext, inventory);

		return inventory;
	}

	private void createAndLinkSnapshotProducerForInventory(final IHUContext huContext, final I_M_Inventory inventory)
	{
		final ISnapshotProducer<I_M_HU> snapshotProducerForInventory = createSnapshotProducerForInventory(huContext, inventory);

		linkHUSnapshotProducerWithInventory(snapshotProducerForInventory, inventory);

	}

	private ISnapshotProducer<I_M_HU> createSnapshotProducerForInventory(final IHUContext huContext, final I_M_Inventory inventory)
	{
		// #2143 HU snapshots
		final ISnapshotProducer<I_M_HU> huSnapshotProducer = huSnapshotDAO
				.createSnapshot()
				.setContext(huContext);

		huSnapshotProducerByInventoryId.put(inventory.getM_Inventory_ID(), huSnapshotProducer);

		return huSnapshotProducer;

	}

	private void linkHUSnapshotProducerWithInventory(final ISnapshotProducer<I_M_HU> snapshotProducerForInventory, final I_M_Inventory inventory)
	{
		inventory.setSnapshot_UUID(snapshotProducerForInventory.getSnapshotId());
		InterfaceWrapperHelper.save(inventory);

	}

	private ISnapshotProducer<I_M_HU> snapshotHUForInventoryLine(
			@NonNull final I_M_HU topLevelHU,
			@NonNull final I_M_InventoryLine inventoryLine)
	{
		final ISnapshotProducer<I_M_HU> currentSnapshotProducer = huSnapshotProducerByInventoryId.get(inventoryLine.getM_Inventory_ID());
		Check.errorIf(currentSnapshotProducer == null, "Missing SnapshotProducer for M_Inventory_ID={}", inventoryLine.getM_Inventory_ID());

		// add each top level HU to be added to the snapshot of the inventory
		currentSnapshotProducer.addModel(topLevelHU);

		return currentSnapshotProducer;
	}

	/**
	 * Sets the given {@code inventoryLine}'s {@code M_HU_PI_Item_Product} according to the given {@code hu}.<br>
	 * If the hu does not have one, we make a very educated guess based on the hu's (effective) PI version.
	 * 
	 * @param hu
	 * @param inventoryLine
	 */
	private void setInventoryLinePiip(
			@NonNull final I_M_HU hu,
			@NonNull final I_M_InventoryLine inventoryLine)
	{
		final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(inventoryLine.getM_InOutLine(), I_M_InOutLine.class);

		final I_M_InOut inout = receiptLine.getM_InOut();
		final I_C_BPartner partner = inout.getC_BPartner();
		final I_M_Product product = receiptLine.getM_Product();
		final Date date = inout.getMovementDate();

		I_M_HU_PI_Item_Product huPIP = hu.getM_HU_PI_Item_Product();
		if (huPIP == null)
		{
			final I_M_HU_PI_Version effectivePIVersion = handlingUnitsBL.getEffectivePIVersion(hu);

			final I_M_HU_PI_Item materialItem = handlingUnitsDAO
					.retrievePIItems(effectivePIVersion.getM_HU_PI(), partner).stream()
					.filter(i -> X_M_HU_PI_Item.ITEMTYPE_Material.equals(i.getItemType()))
					.findFirst().orElse(null);

			if (materialItem != null)
			{
				huPIP = huPiItemProductDAO.retrievePIMaterialItemProduct(materialItem, partner, product, date);
			}
		}
		inventoryLine.setM_HU_PI_Item_Product(huPIP);
	}

	/**
	 * Counts the number of TUs from from the
	 * 
	 * @param huContext
	 * @param tuHU TU or aggregated TU
	 * @param inventoryLine
	 * @return
	 */
	private BigDecimal countTUs(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU tuHU,
			@NonNull final I_M_InventoryLine inventoryLine)
	{
		final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(inventoryLine.getM_InOutLine(), I_M_InOutLine.class);
		final InOutLineHUPackingMaterialCollectorSource inOutLineSource = InOutLineHUPackingMaterialCollectorSource.of(receiptLine);

		if (pmCollectorForCountingTUs == null)
		{
			pmCollectorForCountingTUs = new HUPackingMaterialsCollector(huContext);
		}

		pmCollectorForCountingTUs.addHURecursively(tuHU, inOutLineSource);

		final int countTUs = pmCollectorForCountingTUs.getAndResetCountTUs();
		return BigDecimal.valueOf(countTUs);
	}

	/**
	 * Find get the TU for the given {@code hu}. Might be the HU itself or its parent.
	 * 
	 * @param hu
	 * @return
	 */
	private I_M_HU retrieveTu(@NonNull final I_M_HU hu)
	{
		final I_M_HU tuHU;

		if (handlingUnitsBL.isAggregateHU(hu))
		{
			tuHU = hu;
		}
		else if (handlingUnitsBL.isVirtual(hu))
		{
			tuHU = handlingUnitsDAO.retrieveParent(hu);
		}
		else
		{
			// neither aggregate nor virtual. since we know from the start that we don't deal with an LU, hu must be a TU
			tuHU = hu;
		}
		return tuHU;
	}

	private void collectPackingMaterials(final IHUContext huContext, final int inventoryId, final I_M_HU hu)
	{
		final HUPackingMaterialsCollector collector = getCreatePackingMaterialsCollectorForInventory(huContext, inventoryId);
		final IHUPackingMaterialCollectorSource source = null;
		collector.addHURecursively(hu, source);
	}

	private void collectPackingMaterials_LUOnly(final IHUContext huContext, final int inventoryId, final I_M_HU luHU)
	{
		final HUPackingMaterialsCollector collector = getCreatePackingMaterialsCollectorForInventory(huContext, inventoryId);
		final IHUPackingMaterialCollectorSource source = null;
		collector.addLU(luHU, source);
	}

	private HUPackingMaterialsCollector getCreatePackingMaterialsCollectorForInventory(final IHUContext huContext, final int inventoryId)
	{
		return packingMaterialsCollectorByInventoryId.computeIfAbsent(inventoryId, k -> {
			final HUPackingMaterialsCollector c = new HUPackingMaterialsCollector(huContext);
			c.setSeenM_HU_IDs_ToAdd(packingMaterialsCollectedHUIds);
			return c;
		});
	}

	private HUPackingMaterialsCollector getPackingMaterialsCollectorForInventory(final int inventoryId)
	{
		return packingMaterialsCollectorByInventoryId.get(inventoryId);
	}
}
