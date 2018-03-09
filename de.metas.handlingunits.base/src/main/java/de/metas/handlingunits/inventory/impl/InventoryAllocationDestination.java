package de.metas.handlingunits.inventory.impl;

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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Inventory;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.inventory.IHUInventoryBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
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
import de.metas.inventory.IInventoryBL;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/**
 * {@link IAllocationDestination} which is used to generate Internal Use Inventory documents for quantity that is asked to be loaded here.
 *
 * Useful when you want to destroy an HU and you want also to "internal use" it's M_Storage counter-part.
 *
 * @task http://dewiki908/mediawiki/index.php/07050_Eigenverbrauch_metas_in_Existing_Window_Handling_Unit_Pos
 */
class InventoryAllocationDestination implements IAllocationDestination
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final transient IHUPIItemProductDAO huPiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final transient IHUInventoryBL huInventoryBL = Services.get(IHUInventoryBL.class);
	private final transient IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	private final transient IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);
	private final transient IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	private final int warehouseId;
	private final int warehouseLocatorId;
	private final int inventoryDocTypeId;
	private final int chargeId;

	private final Map<Integer, I_M_Inventory> inventoriesByOrderId = new LinkedHashMap<>();
	/** Map the inventory lines to the base receipt lines */
	private final Map<ArrayKey, I_M_InventoryLine> inventoryLinesByKey = new HashMap<>();

	//
	// Packing materials
	private final Map<Integer, HUPackingMaterialsCollector> packingMaterialsCollectorByInventoryId = new HashMap<>();
	private final Set<Integer> packingMaterialsCollectedHUIds = new HashSet<>();
	// NOTE: using a shared collector for counting because we want to avoid counting same HU in multiple places
	private HUPackingMaterialsCollector pmCollectorForCountingTUs; // will be created on demand

	//
	// HU snapshoting
	private final Map<Integer, ISnapshotProducer<I_M_HU>> huSnapshotProducerByInventoryId = new HashMap<>();

	public InventoryAllocationDestination(@NonNull final I_M_Warehouse warehouse, final int inventoryDocTypeId)
	{
		warehouseId = warehouse.getM_Warehouse_ID();
		warehouseLocatorId = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse).getM_Locator_ID();
		this.inventoryDocTypeId = inventoryDocTypeId;
		chargeId = Services.get(IInventoryBL.class).getDefaultInternalChargeId();
	}

	/** @return created inventory documents */
	private List<I_M_Inventory> getInventories()
	{
		return ImmutableList.copyOf(inventoriesByOrderId.values());
	}

	@Override
	public IAllocationResult load(final IAllocationRequest request)
	{
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		final I_M_HU hu = extractHUOrNull(request);
		if (hu == null)
		{
			return result;
		}
		final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParent(hu);

		final Date movementDate = request.getDate(); // to be used for inventory documents
		final Quantity qtySource = request.getQuantity(); // Qty to add, in request's UOM
		final Quantity qty = getQtyInStockingUOM(request);

		//
		// For each receipt line which received this HU
		for (final I_M_InOutLine receiptLine : getReceiptLines(topLevelHU, request.getProductId()))
		{
			final BigDecimal qtyToMoveTotal = qty.getQty();
			final BigDecimal qualityDiscountPerc = huAttributesBL.getQualityDiscountPercent(hu);
			final BigDecimal qtyToMoveInDispute = qtyToMoveTotal.multiply(qualityDiscountPerc);
			final BigDecimal qtyToMove = qtyToMoveTotal.subtract(qtyToMoveInDispute);

			//
			// Get/create the inventory line based on the info from material receipt and request
			final I_M_InventoryLine inventoryLine = getCreateInventoryLine(receiptLine, hu.getM_HU_ID(), movementDate);

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
				final I_M_InventoryLine inventoryLineInDispute = getCreateInventoryLineInDispute(receiptLine, hu.getM_HU_ID(), movementDate);

				final BigDecimal inventoryLine_Qty_Old = inventoryLineInDispute.getQtyInternalUse();
				final BigDecimal inventoryLine_Qty_New = inventoryLine_Qty_Old.add(qtyToMoveInDispute);
				inventoryLineInDispute.setQtyInternalUse(inventoryLine_Qty_New);

				// Make sure the inout line is saved
				InterfaceWrapperHelper.save(inventoryLineInDispute);
			}

			inventoryLine.setM_HU_PI_Item_Product(extractPackingOrNull(hu, inventoryLine));

			final I_M_HU tuHU = retrieveTU(hu);
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
			InterfaceWrapperHelper.save(inventoryLine);
			huInventoryBL.assignHU(inventoryLine, topLevelHU);

			//
			// Update the result
			{
				result.subtractAllocatedQty(qtySource.getQty());

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

	private I_M_HU extractHUOrNull(final IAllocationRequest request)
	{
		//
		// Get the HU Item from were we are unloading
		// If no HU Item found then return immediately.
		final ITableRecordReference reference = request.getReference();
		if (reference == null || !I_M_HU_Item.Table_Name.equals(reference.getTableName()))
		{
			return null;
		}
		final I_M_HU_Item huItem = reference.getModel(I_M_HU_Item.class);
		return huItem.getM_HU();
	}

	private Quantity getQtyInStockingUOM(final IAllocationRequest request)
	{
		final Quantity qtySource = request.getQuantity(); // Qty to add, in request's UOM
		return uomConversionBL.convertToProductUOM(qtySource, request.getProductId());
	}

	private List<I_M_InOutLine> getReceiptLines(final I_M_HU topLevelHU, final int productId)
	{
		final List<I_M_InOutLine> receiptLines = huInOutDAO.retrieveInOutLinesForHU(topLevelHU)
				.stream()
				.filter(inoutLine -> inoutLine.getM_Product_ID() == productId) // #1604: skip inoutlines for other products
				.peek(this::assertReceipt) // make sure it's a material receipt (and NOT a shipment)
				.collect(ImmutableList.toImmutableList());
		if (receiptLines.isEmpty())
		{
			throw new HUException("No receipt lines found for " + topLevelHU);
		}
		return receiptLines;
	}

	private void assertReceipt(final I_M_InOutLine inoutLine)
	{
		final I_M_InOut receipt = inoutLine.getM_InOut();
		if (receipt.isSOTrx())
		{
			throw new AdempiereException("Document type {0} is not suitable for material disposal", new Object[] { receipt.getC_DocType() });
		}
	}

	private I_M_InventoryLine getCreateInventoryLineInDispute(final I_M_InOutLine receiptLine, final int huId, final Date movementDate)
	{
		final I_M_InOutLine originReceiptLineInDispute = InterfaceWrapperHelper.create(inOutDAO.retrieveLineWithQualityDiscount(receiptLine), I_M_InOutLine.class);
		if (originReceiptLineInDispute == null)
		{
			return null;
		}

		final I_M_InventoryLine inoutLineInDispute = getCreateInventoryLine(originReceiptLineInDispute, huId, movementDate);
		inoutLineInDispute.setIsInDispute(true);
		inoutLineInDispute.setQualityDiscountPercent(originReceiptLineInDispute.getQualityDiscountPercent());
		inoutLineInDispute.setQualityNote(originReceiptLineInDispute.getQualityNote());
		return inoutLineInDispute;
	}

	public List<I_M_Inventory> processInventories()
	{
		final List<I_M_Inventory> inventories = getInventories();
		inventories.forEach(this::processInventory);
		return inventories;
	}

	private void processInventory(final I_M_Inventory inventory)
	{
		createHUSnapshotsForInventory(inventory);
		completeInventory(inventory);
		createEmptiesMovementForInventory(inventory);
	}

	/**
	 * Complete inventory document
	 * 
	 * @param inventory
	 */
	private void completeInventory(final I_M_Inventory inventory)
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
	 */
	private I_M_InventoryLine getCreateInventoryLine(
			@NonNull final I_M_InOutLine receiptLine,
			final int huId,
			@NonNull final Date movementDate)
	{
		return inventoryLinesByKey.computeIfAbsent(
				createInventoryLineKey(receiptLine.getM_InOutLine_ID(), huId),
				k -> createInventoryLine(receiptLine, huId, movementDate));
	}

	private ArrayKey createInventoryLineKey(final int receiptLineId, final int huId)
	{
		return ArrayKey.of(receiptLineId, huId);
	}

	private I_M_InventoryLine createInventoryLine(
			@NonNull final I_M_InOutLine receiptLine,
			final int huId,
			@NonNull final Date movementDate)
	{
		final I_M_Inventory inventoryHeader = getCreateInventoryHeader(receiptLine, movementDate);

		final I_M_InventoryLine inventoryLine = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
		inventoryLine.setM_Inventory_ID(inventoryHeader.getM_Inventory_ID());
		inventoryLine.setM_Product_ID(receiptLine.getM_Product_ID());
		inventoryLine.setC_Charge_ID(chargeId);
		inventoryLine.setM_Locator_ID(warehouseLocatorId);
		inventoryLine.setM_InOutLine(receiptLine);
		inventoryLine.setC_UOM(receiptLine.getC_UOM());
		inventoryLine.setM_HU_ID(huId);

		attributeSetInstanceBL.cloneASI(inventoryLine, receiptLine);

		// NOTE: we are not saving here
		return inventoryLine;
	}

	private I_M_Inventory getCreateInventoryHeader(final I_M_InOutLine receiptLine, final Date movementDate)
	{
		final I_M_InOut receipt = receiptLine.getM_InOut();
		final int purchaseOrderId = receipt.getC_Order_ID();
		Check.assume(purchaseOrderId > 0, "Receipt {0} does not have a purchase order set", receipt);

		return inventoriesByOrderId.computeIfAbsent(purchaseOrderId, k -> createInventoryHeader(receipt.getC_Order(), movementDate));
	}

	private I_M_Inventory createInventoryHeader(final I_C_Order purchaseOrder, final Date movementDate)
	{
		trxManager.assertThreadInheritedTrxExists();

		final I_M_Inventory inventory = InterfaceWrapperHelper.newInstance(I_M_Inventory.class);
		inventory.setMovementDate(TimeUtil.asTimestamp(movementDate));
		inventory.setM_Warehouse_ID(warehouseId);

		if (inventoryDocTypeId > 0)
		{
			inventory.setC_DocType_ID(inventoryDocTypeId);
		}

		inventory.setPOReference(purchaseOrder.getPOReference());
		InterfaceWrapperHelper.save(inventory);

		createAndLinkSnapshotProducerForInventory(inventory);

		return inventory;
	}

	private void createAndLinkSnapshotProducerForInventory(final I_M_Inventory inventory)
	{
		final ISnapshotProducer<I_M_HU> snapshotProducerForInventory = createSnapshotProducerForInventory(inventory.getM_Inventory_ID());
		linkHUSnapshotProducerWithInventory(snapshotProducerForInventory, inventory);
	}

	private ISnapshotProducer<I_M_HU> createSnapshotProducerForInventory(final int inventoryId)
	{
		// #2143 HU snapshots
		final ISnapshotProducer<I_M_HU> huSnapshotProducer = huSnapshotDAO
				.createSnapshot()
				.setContext(PlainContextAware.newWithThreadInheritedTrx());

		huSnapshotProducerByInventoryId.put(inventoryId, huSnapshotProducer);

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

	private I_M_HU_PI_Item_Product extractPackingOrNull(@NonNull final I_M_HU hu, @NonNull final I_M_InventoryLine inventoryLine)
	{
		//
		// Direct
		{
			final I_M_HU_PI_Item_Product huPIP = hu.getM_HU_PI_Item_Product();
			if (huPIP != null)
			{
				return huPIP;
			}
		}

		//
		// From HU's PI and material receipt's bpartner, date and product
		{
			final I_M_HU_PI effectivePI = handlingUnitsBL.getEffectivePI(hu);
			final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(inventoryLine.getM_InOutLine(), I_M_InOutLine.class);
			final I_M_InOut receipt = receiptLine.getM_InOut();
			final I_C_BPartner bpartner = receipt.getC_BPartner();

			final I_M_HU_PI_Item materialItem = handlingUnitsDAO
					.retrievePIItems(effectivePI, bpartner)
					.stream()
					.filter(piItem -> X_M_HU_PI_Item.ITEMTYPE_Material.equals(piItem.getItemType()))
					.findFirst()
					.orElse(null);
			if (materialItem != null)
			{
				final I_M_Product product = receiptLine.getM_Product();
				final Date date = receipt.getMovementDate();
				return huPiItemProductDAO.retrievePIMaterialItemProduct(materialItem, bpartner, product, date);
			}
		}

		//
		return null;
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
	 */
	private I_M_HU retrieveTU(@NonNull final I_M_HU hu)
	{
		if (handlingUnitsBL.isTransportUnitOrAggregate(hu))
		{
			return hu;
		}
		else if (handlingUnitsBL.isVirtual(hu))
		{
			final I_M_HU parentHU = handlingUnitsDAO.retrieveParent(hu);
			return retrieveTU(parentHU);
		}
		else
		{
			throw new HUException("HU shall be TU or VHU: " + hu);
		}
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
