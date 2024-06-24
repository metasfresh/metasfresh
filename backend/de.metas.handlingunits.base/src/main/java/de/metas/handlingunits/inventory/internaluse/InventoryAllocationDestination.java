/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.inventory.internaluse;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
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
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.inout.IHUInOutDAO;
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
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inoutcandidate.spi.impl.InOutLineHUPackingMaterialCollectorSource;
import de.metas.inventory.IInventoryBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_M_Inventory;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * {@link IAllocationDestination} which is used to generate Internal Use Inventory documents for quantity that is asked to be loaded here.
 * <p>
 * Useful when you want to destroy an HU and you want also to "internal use" it's M_Storage counter-part.
 * <p>
 * Task http://dewiki908/mediawiki/index.php/07050_Eigenverbrauch_metas_in_Existing_Window_Handling_Unit_Pos
 */
class InventoryAllocationDestination implements IAllocationDestination
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final transient IHUPIItemProductDAO huPiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final transient IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	private final transient IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);
	private final transient IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);

	private final LocatorId warehouseLocatorId;
	private final DocTypeId inventoryDocTypeId;
	private final int chargeId;

	private final ActivityId activityId;
	private final String description;

	private final Map<InventoryHeaderKey, I_M_Inventory> inventoriesMap = new LinkedHashMap<>();
	/**
	 * Map the inventory lines to the base receipt lines
	 */
	private final Map<InventoryLineKey, I_M_InventoryLine> inventoryLinesMap = new HashMap<>();

	//
	// Packing materials
	private final Map<Integer, HUPackingMaterialsCollector> packingMaterialsCollectorByInventoryId = new HashMap<>();
	private final Set<Integer> packingMaterialsCollectedHUIds = new HashSet<>();
	// NOTE: using a shared collector for counting because we want to avoid counting same HU in multiple places
	private HUPackingMaterialsCollector pmCollectorForCountingTUs; // will be created on demand

	//
	// HU snapshoting
	private final Map<Integer, ISnapshotProducer<I_M_HU>> huSnapshotProducerByInventoryId = new HashMap<>();

	public InventoryAllocationDestination(
			@NonNull final WarehouseId warehouseId,
			final DocTypeId inventoryDocTypeId,
			@Nullable final ActivityId activityId,
			@Nullable final String description)
	{
		warehouseLocatorId = Services.get(IWarehouseBL.class).getDefaultLocatorId(warehouseId);
		this.inventoryDocTypeId = inventoryDocTypeId;
		chargeId = Services.get(IInventoryBL.class).getDefaultInternalChargeId();

		this.activityId = activityId;
		this.description = description;
	}

	/**
	 * @return created inventory documents
	 */
	private List<I_M_Inventory> getInventories()
	{
		return ImmutableList.copyOf(inventoriesMap.values());
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
		final HuId topLevelHUId = HuId.ofRepoId(topLevelHU.getM_HU_ID());

		final Quantity qtySource = request.getQuantity(); // Qty to add, in request's UOM
		final Quantity qty = getQtyInStockingUOM(request);

		final List<InventoryLineCandidate> candidates = prepareCandidates(topLevelHUId, request);
		//
		// For each receipt line which received this HU
		for (final InventoryLineCandidate candidate : candidates)
		{
			final BigDecimal qtyToMoveTotal = qty.toBigDecimal();
			final BigDecimal qualityDiscountPerc = huAttributesBL.getQualityDiscountPercent(hu);
			final BigDecimal qtyToMoveInDispute = qtyToMoveTotal.multiply(qualityDiscountPerc);
			final BigDecimal qtyToMove = qtyToMoveTotal.subtract(qtyToMoveInDispute);

			//
			// Get/create the inventory line based on the info from material receipt and request
			final I_M_InventoryLine inventoryLine = getCreateInventoryLine(candidate);

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

			if (qtyToMoveInDispute.signum() != 0 && candidate.getInOutLineId() != null)
			{
				final I_M_InventoryLine inventoryLineInDispute = getCreateInventoryLineInDispute(candidate);

				final BigDecimal inventoryLine_Qty_Old = inventoryLineInDispute.getQtyInternalUse();
				final BigDecimal inventoryLine_Qty_New = inventoryLine_Qty_Old.add(qtyToMoveInDispute);
				inventoryLineInDispute.setQtyInternalUse(inventoryLine_Qty_New);

				// Make sure the inout line is saved
				save(inventoryLineInDispute);
			}

			inventoryLine.setM_HU_PI_Item_Product(extractPackingOrNull(hu, candidate));

			final I_M_HU tuHU = retrieveTUOrNull(hu);

			if (tuHU != null)

			{
				//
				// Calculate and update inventory line's QtyTU
				{
					final BigDecimal countTUs = countTUs(request.getHuContext(), tuHU, inventoryLine);
					final BigDecimal qtyTU = inventoryLine.getQtyTU().add(countTUs);
					inventoryLine.setQtyTU(qtyTU);
				}

				//
				// Collect HU's packing materials
				{
					collectPackingMaterials(request.getHuContext(), inventoryLine.getM_Inventory_ID(), tuHU);
					if (!HuId.equals(topLevelHUId, HuId.ofRepoId(hu.getM_HU_ID())))
					{
						collectPackingMaterials_LUOnly(request.getHuContext(), inventoryLine.getM_Inventory_ID(), topLevelHU);
					}
				}
			}

			inventoryLine.setM_HU_ID(topLevelHUId.getRepoId());

			//
			// Save the inventory line and assign the top level HU to it
			save(inventoryLine);

			Services.get(IHUAssignmentBL.class).assignHU(inventoryLine, topLevelHU, ITrx.TRXNAME_ThreadInherited);

			//
			// Update the result
			{
				result.subtractAllocatedQty(qtySource.toBigDecimal());

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

	private List<InventoryLineCandidate> prepareCandidates(final HuId topLevelHuId, final IAllocationRequest request)
	{
		final List<InventoryLineCandidate> candidates = new ArrayList<>();

		for (final I_M_InOutLine receiptLine : getReceiptLinesOrEmpty(topLevelHuId, request.getProductId()))
		{
			final int inoutId = receiptLine.getM_InOut_ID();

			final I_M_InOut inOut = inOutDAO.getById(InOutId.ofRepoId(inoutId));

			final String poReference = inOut.getC_Order() == null ? null : inOut.getC_Order().getPOReference();

			final InventoryLineCandidate candidate = InventoryLineCandidate.builder()
					.movementDate(request.getDate())
					.productId(request.getProductId())
					.qty(Quantity.zero(loadOutOfTrx(receiptLine.getC_UOM_ID(), I_C_UOM.class)))

					.topLevelHUId(topLevelHuId)
					.poReference(poReference)
					.receiptLine(receiptLine)
					.build();

			candidates.add(candidate);

		}

		if (candidates.isEmpty())
		{

			// fallback to

			final I_M_HU hu = handlingUnitsDAO.getById(topLevelHuId);

			final IHUProductStorage huProductStorage = handlingUnitsBL.getStorageFactory()
					.getStorage(hu)
					.getProductStorage(request.getProductId());

			final InventoryLineCandidate candidate = InventoryLineCandidate.builder()
					.movementDate(request.getDate())
					.productId(request.getProductId())
					.qty(Quantity.of(BigDecimal.ZERO, huProductStorage.getC_UOM()))
					.topLevelHUId(topLevelHuId)
					.poReference(null)
					.receiptLine(null)
					.build();

			candidates.add(candidate);

		}
		return candidates;
	}

	@Nullable
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

	private List<I_M_InOutLine> getReceiptLinesOrEmpty(final HuId topLevelHUId, final ProductId productId)
	{
		final I_M_HU topLevelHu = handlingUnitsDAO.getById(topLevelHUId);

		return huInOutDAO.retrieveInOutLinesForHU(topLevelHu)
				.stream()
				.filter(inoutLine -> inoutLine.getM_Product_ID() == productId.getRepoId()) // #1604: skip inoutlines for other products
				.peek(this::assertReceipt) // make sure it's a material receipt (and NOT a shipment)
				.collect(ImmutableList.toImmutableList());
	}

	private void assertReceipt(final I_M_InOutLine inoutLine)
	{
		final I_M_InOut receipt = inoutLine.getM_InOut();
		if (receipt.isSOTrx())
		{
			throw new AdempiereException("Document type " + receipt.getC_DocType_ID() + " is not suitable for material disposal");
		}
	}

	@Nullable
	private I_M_InventoryLine getCreateInventoryLineInDispute(final InventoryLineCandidate candidate)
	{
		final I_M_InOutLine receiptLine = candidate.getReceiptLine();
		Check.assumeNotNull(receiptLine, "Only create inventory lines in dispute for HUs which are based on receipts");

		final I_M_InOutLine originReceiptLineInDispute = create(inOutDAO.retrieveLineWithQualityDiscount(receiptLine), I_M_InOutLine.class);
		if (originReceiptLineInDispute == null)
		{
			return null;
		}

		final I_M_InventoryLine inventoryLineInDispute = getCreateInventoryLine(candidate);
		inventoryLineInDispute.setIsInDispute(true);
		inventoryLineInDispute.setQualityDiscountPercent(originReceiptLineInDispute.getQualityDiscountPercent());
		inventoryLineInDispute.setQualityNote(originReceiptLineInDispute.getQualityNote());
		return inventoryLineInDispute;
	}

	public List<I_M_Inventory> processInventories(final boolean isCompleteInventory)
	{
		final List<I_M_Inventory> inventories = getInventories();
		inventories.forEach(inventory -> processInventory(inventory, isCompleteInventory));
		return inventories;
	}

	private void processInventory(final I_M_Inventory inventory, final boolean isCompleteInventory)
	{
		createHUSnapshotsForInventory(inventory);

		if (isCompleteInventory)
		{
			completeInventory(inventory);
		}
	}

	public void createEmptiesMovementForInventories()
	{
		getInventories().forEach(this::createEmptiesMovementForInventory);
	}

	/**
	 * Complete inventory document
	 */
	private void completeInventory(final I_M_Inventory inventory)
	{
		docActionBL.processEx(inventory, X_M_Inventory.DOCACTION_Complete, X_M_Inventory.DOCSTATUS_Completed);
	}

	/**
	 * Create HU snapshots recursively for all the HUs linked to this inventory
	 */
	private void createHUSnapshotsForInventory(final I_M_Inventory inventory)
	{
		final ISnapshotProducer<I_M_HU> currentSnapshotProducer = huSnapshotProducerByInventoryId.get(inventory.getM_Inventory_ID());
		currentSnapshotProducer.createSnapshots();
	}

	/**
	 * Move the handling units used in inventory from their current warehouse to the empties warehouse.
	 */
	private void createEmptiesMovementForInventory(final I_M_Inventory inventory)
	{
		final int inventoryId = inventory.getM_Inventory_ID();
		final HUPackingMaterialsCollector packingMaterialsCollector = getPackingMaterialsCollectorForInventory(inventoryId);

		if (packingMaterialsCollector == null)
		{
			// the HU was probably a virtual one (CU only). Nothing to move
			return;
		}

		huEmptiesService.newEmptiesMovementProducer()
				.setEmptiesMovementDirectionAuto()
				.addCandidates(packingMaterialsCollector.getAndClearCandidates())
				.setReferencedInventoryId(inventoryId)
				.createMovements();

	}

	/**
	 * Return inventoryLine for inoutLine if it exists, create an inventory line otherwise
	 */
	private I_M_InventoryLine getCreateInventoryLine(@NonNull final InventoryLineCandidate candidate)
	{
		return inventoryLinesMap.computeIfAbsent(
				createInventoryLineKey(candidate),
				k -> createInventoryLine(candidate));
	}

	private static InventoryLineKey createInventoryLineKey(@NonNull final InventoryLineCandidate candidate)
	{
		return InventoryLineKey.builder()
				.topLevelHU(candidate.getTopLevelHUId())
				.productId(candidate.getProductId())
				.receiptLineId(candidate.getInOutLineId())
				.build();
	}

	private I_M_InventoryLine createInventoryLine(@NonNull final InventoryLineCandidate candidate)
	{
		final I_M_Inventory inventoryHeader = getCreateInventoryHeader(candidate);
		final ProductId productId = candidate.getProductId();

		final I_M_InventoryLine inventoryLine = newInstance(I_M_InventoryLine.class);
		inventoryLine.setM_Inventory_ID(inventoryHeader.getM_Inventory_ID());
		inventoryLine.setM_Product_ID(productId.getRepoId());
		inventoryLine.setC_Charge_ID(chargeId);
		inventoryLine.setM_Locator_ID(warehouseLocatorId.getRepoId());
		inventoryLine.setC_UOM_ID(candidate.getUomId().getRepoId());
		inventoryLine.setM_HU_ID(candidate.getTopLevelHUId().getRepoId());

		final I_M_InOutLine receiptLine = candidate.getReceiptLine();
		inventoryLine.setM_InOutLine(receiptLine);

		if (receiptLine == null)
		{
			final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
			final IAttributeStorageFactory attributesFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();

			final I_M_HU hu = handlingUnitsDAO.getById(candidate.getTopLevelHUId());

			final IAttributeStorage attributeStorage = attributesFactory.getAttributeStorage(hu);

			final I_M_AttributeSetInstance asiFromStorage = attributeSetInstanceBL.createASIFromAttributeSet(attributeStorage, a -> isAttributeInAttributeSet(AttributeId.ofRepoId(a.getM_Attribute_ID()), productId));

			inventoryLine.setM_AttributeSetInstance(asiFromStorage);
		}
		else
		{
			attributeSetInstanceBL.cloneASI(inventoryLine, receiptLine);
		}

		// NOTE: we are not saving here
		return inventoryLine;
	}

	private boolean isAttributeInAttributeSet(final AttributeId attributeId, final ProductId productId)
	{
		final I_M_Attribute attribute = attributesBL.getAttributeOrNull(productId, attributeId);

		return attribute != null;

	}

	private I_M_Inventory getCreateInventoryHeader(@NonNull final InventoryLineCandidate candidate)
	{
		final ZonedDateTime movementDate = candidate.getMovementDate();

		final String poReference = candidate.getPoReference();

		return inventoriesMap.computeIfAbsent(
				createInventoryHeaderKey(candidate),
				k -> createInventoryHeader(movementDate, poReference));
	}

	private static InventoryHeaderKey createInventoryHeaderKey(@NonNull final InventoryLineCandidate candidate)
	{
		return InventoryHeaderKey.builder()
				.movementDate(candidate.getMovementDate())
				.poReference(candidate.getPoReference())
				.build();

	}

	private I_M_Inventory createInventoryHeader(
			@NonNull final ZonedDateTime movementDate,
			@Nullable final String poReference)
	{
		trxManager.assertThreadInheritedTrxExists();

		final I_M_Inventory inventory = newInstance(I_M_Inventory.class);
		inventory.setDocStatus(DocStatus.Drafted.getCode());
		inventory.setDocAction(IDocument.ACTION_Complete);
		inventory.setMovementDate(TimeUtil.asTimestamp(movementDate));
		inventory.setM_Warehouse_ID(warehouseLocatorId.getWarehouseId().getRepoId());

		inventory.setC_Activity_ID(activityId == null ? -1 : activityId.getRepoId());
		inventory.setDescription(description);

		if (inventoryDocTypeId != null)
		{
			inventory.setC_DocType_ID(inventoryDocTypeId.getRepoId());
		}

		inventory.setPOReference(poReference);
		save(inventory);

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
		save(inventory);
	}

	private void snapshotHUForInventoryLine(
			@NonNull final I_M_HU topLevelHU,
			@NonNull final I_M_InventoryLine inventoryLine)
	{
		final ISnapshotProducer<I_M_HU> currentSnapshotProducer = huSnapshotProducerByInventoryId.get(inventoryLine.getM_Inventory_ID());
		if (currentSnapshotProducer == null)
		{
			throw new AdempiereException("Missing SnapshotProducer for M_Inventory_ID=" + inventoryLine.getM_Inventory_ID());
		}

		// add each top level HU to be added to the snapshot of the inventory
		currentSnapshotProducer.addModel(topLevelHU);

	}

	@Nullable
	private I_M_HU_PI_Item_Product extractPackingOrNull(@NonNull final I_M_HU hu, @NonNull final InventoryLineCandidate inventoryLineCandidate)
	{
		//
		// Direct
		{
			final I_M_HU_PI_Item_Product huPIP = IHandlingUnitsBL.extractPIItemProductOrNull(hu);
			if (huPIP != null)
			{
				return huPIP;
			}
		}

		//
		// From HU's PI and material receipt's bpartner, date and product
		{
			final I_M_HU_PI effectivePI = handlingUnitsBL.getEffectivePI(hu);
			final BPartnerId bpartnerId;

			if (inventoryLineCandidate.getReceiptLine() != null)
			{
				final I_M_InOutLine receiptLine = create(inventoryLineCandidate.getReceiptLine(), I_M_InOutLine.class);
				final I_M_InOut receipt = receiptLine.getM_InOut();
				bpartnerId = BPartnerId.ofRepoId(receipt.getC_BPartner_ID());
			}
			else
			{
				bpartnerId = null;
			}

			final I_M_HU_PI_Item materialItem = handlingUnitsDAO
					.retrievePIItems(effectivePI, bpartnerId)
					.stream()
					.filter(piItem -> X_M_HU_PI_Item.ITEMTYPE_Material.equals(piItem.getItemType()))
					.findFirst()
					.orElse(null);
			if (materialItem != null)
			{
				final ProductId productId = inventoryLineCandidate.getProductId();
				final ZonedDateTime date = inventoryLineCandidate.getMovementDate();
				return huPiItemProductDAO.retrievePIMaterialItemProduct(materialItem, bpartnerId, productId, date);
			}
		}

		//
		return null;
	}

	/**
	 * Counts the number of TUs from from the
	 *
	 * @param tuHU TU or aggregated TU
	 */
	private BigDecimal countTUs(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU tuHU,
			@NonNull final I_M_InventoryLine inventoryLine)
	{
		final IHUPackingMaterialCollectorSource source;

		if (inventoryLine.getM_InOutLine_ID() > 0)
		{
			final I_M_InOutLine receiptLine = create(inventoryLine.getM_InOutLine(), I_M_InOutLine.class);
			source = InOutLineHUPackingMaterialCollectorSource.of(receiptLine);
		}
		else
		{
			source = null;
		}

		if (pmCollectorForCountingTUs == null)

		{
			pmCollectorForCountingTUs = new HUPackingMaterialsCollector(huContext);
		}

		pmCollectorForCountingTUs.releasePackingMaterialForHURecursively(tuHU, source);

		final int countTUs = pmCollectorForCountingTUs.getAndResetCountTUs();

		return BigDecimal.valueOf(countTUs);
	}

	/**
	 * Find get the TU for the given {@code hu}. Might be the HU itself or its parent.
	 */
	@Nullable
	private I_M_HU retrieveTUOrNull(@NonNull final I_M_HU hu)
	{
		if (handlingUnitsBL.isTransportUnitOrAggregate(hu))
		{
			return hu;
		}
		else if (handlingUnitsBL.isVirtual(hu))
		{
			final I_M_HU parentHU = handlingUnitsDAO.retrieveParent(hu);

			if (parentHU == null)
			{
				return null;
			}

			return retrieveTUOrNull(parentHU);
		}
		else
		{
			return null;
		}
	}

	private void collectPackingMaterials(final IHUContext huContext, final int inventoryId, final I_M_HU hu)
	{
		final HUPackingMaterialsCollector collector = getCreatePackingMaterialsCollectorForInventory(huContext, inventoryId);
		final IHUPackingMaterialCollectorSource source = null;
		collector.releasePackingMaterialForHURecursively(hu, source);
	}

	private void collectPackingMaterials_LUOnly(final IHUContext huContext, final int inventoryId, final I_M_HU luHU)
	{
		final HUPackingMaterialsCollector collector = getCreatePackingMaterialsCollectorForInventory(huContext, inventoryId);
		final IHUPackingMaterialCollectorSource source = null;
		collector.releasePackingMaterialForLU(luHU, source);
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

	@Value
	@Builder
	private static class InventoryHeaderKey
	{
		@NonNull
		ZonedDateTime movementDate;

		@Nullable
		String poReference;

	}

	@Value
	@Builder
	private static class InventoryLineKey
	{
		@NonNull HuId topLevelHU;
		@NonNull ProductId productId;
		@Nullable
		InOutLineId receiptLineId;
	}

	@Value
	@Builder
	private static class InventoryLineCandidate
	{
		@NonNull ZonedDateTime movementDate;
		@NonNull HuId topLevelHUId;
		@NonNull ProductId productId;
		@NonNull Quantity qty;
		@Nullable
		I_M_InOutLine receiptLine;
		@Nullable
		String poReference;

		@Nullable
		public InOutLineId getInOutLineId()
		{
			return receiptLine != null
					? InOutLineId.ofRepoId(receiptLine.getM_InOutLine_ID())
					: null;
		}

		public UomId getUomId()
		{
			return qty.getUomId();
		}
	}
}
