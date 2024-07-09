package de.metas.handlingunits.shipmentschedule.spi.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.InOutLineDimensionFactory;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.ASIAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequestBuilder;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.util.HUTopLevel;
import de.metas.inout.InOutLineId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet.Builder;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static de.metas.util.Check.assumeNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * Aggregates given {@link ShipmentScheduleWithHU}s (see {@link #add(ShipmentScheduleWithHU)}) and creates the shipment line (see {@link #createShipmentLine()}).
 */
/* package */class ShipmentLineBuilder
{
	private static final Logger logger = LogManager.getLogger(ShipmentLineBuilder.class);

	// Services
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final transient IHUShipmentAssignmentBL huShipmentAssignmentBL = Services.get(IHUShipmentAssignmentBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final InOutLineDimensionFactory inOutLineDimensionFactory = new InOutLineDimensionFactory();

	/**
	 * Shipment on which the new shipment line will be created
	 */
	private final I_M_InOut currentShipment;

	//
	// Shipment Line attributes
	private IHUContext huContext;
	private ProductId productId = null;
	@Nullable
	private FlatrateTermId flatrateTermId = null;

	private Object attributesAggregationKey = null;
	private OrderAndLineId orderLineId = null;

	private Quantity qtyEntered = null;

	// note that we maintain both QtyEntered and MovementQty to avoid rounding/conversion issues
	private Quantity movementQty = null;

	// note that catchQty does not necessarily have a fixed UOM conversion rate with qtyEntered and movementQty
	private Quantity catchQty = null;

	/* Used to collect the candidates' QtyTU for the case that we need to create the shipment line without actually picked HUs. */
	private final HashMap<HUPIItemProductId, QuantityTU> piipId2TuQtyFromShipmentSchedule = new HashMap<>();

	/**
	 * Candidates which were added to this builder
	 */
	private final List<ShipmentScheduleWithHU> candidates = new ArrayList<>();

	/**
	 * Loading Units(LUs)/Transport Units(TUs) to assign to the shipment line that will be created
	 */
	private final Set<HUTopLevel> husToAssign = new TreeSet<>();
	private Set<HuId> alreadyAssignedTUIds = null; // to be configured by called

	@Getter
	private M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse = M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER; // #4507 keep this al fallback. This is how it was before the qtyTypeToUse introduction.

	//
	// Manual packing materials related:
	private boolean _manualPackingMaterial = false;

	private final TreeSet<I_M_HU_PI_Item_Product> packingMaterial_huPIItemProducts = new TreeSet<>(Comparator.comparing(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID));

	private final TreeSet<IAttributeValue> //
			attributeValues = new TreeSet<>(Comparator.comparing(av -> av.getM_Attribute().getM_Attribute_ID()));

	private final ShipmentLineNoInfo shipmentLineNoInfo;

	/**
	 * @param shipment shipment on which the new shipment line will be created
	 */
	public ShipmentLineBuilder(
			@NonNull final I_M_InOut shipment,
			@NonNull final ShipmentLineNoInfo shipmentLineNoInfo)
	{
		this.currentShipment = shipment;
		this.shipmentLineNoInfo = shipmentLineNoInfo;
	}

	/**
	 * @return true if there are no candidates appended so far
	 */
	public boolean isEmpty()
	{
		return candidates.isEmpty();
	}

	public boolean canCreateShipmentLine()
	{
		if (isEmpty())
		{
			return false;
		}

		// Disallow creating shipment lines with negative or ZERO qty
		if (qtyEntered.signum() <= 0)
		{
			return false;
		}

		return true;
	}

	/**
	 * @return true if we can append to current shipment line
	 */
	boolean canAdd(final ShipmentScheduleWithHU candidate)
	{
		// If there were no candidates added so far, obviously we allow our first candidate
		if (isEmpty())
		{
			return true;
		}

		// Check: HU context
		if (!Objects.equals(huContext, candidate.getHUContext()))
		{
			return false;
		}

		// Check: same product
		if (!ProductId.equals(productId, candidate.getProductId()))
		{
			return false;
		}

		// Check: same attributes aggregation key
		if (!Objects.equals(this.attributesAggregationKey, candidate.getAttributesAggregationKey()))
		{
			return false;
		}

		// Check: same Order Line
		// NOTE: this is also EDI requirement
		if (!OrderAndLineId.equals(orderLineId, candidate.getOrderLineId()))
		{
			return false;
		}

		// Else, we can allow this candidate to be added here
		return true;
	}

	public void add(@NonNull final ShipmentScheduleWithHU candidate)
	{
		if (isEmpty())
		{
			init(candidate);
		}
		append(candidate);
	}

	/**
	 * Initialize shipment line's fields (without Qtys and UOM)
	 */
	private void init(@NonNull final ShipmentScheduleWithHU candidate)
	{
		Check.assume(isEmpty(), "builder shall be empty");

		huContext = candidate.getHUContext();

		//
		// Product, ASI, UOM (retrieved from Shipment Schedule)
		productId = candidate.getProductId();
		attributeValues.addAll(candidate.getAttributeValues());
		attributesAggregationKey = candidate.getAttributesAggregationKey();

		qtyEntered = Quantity.zero(candidate.getUOM());

		final I_C_UOM stockingUOM = productBL.getStockUOM(productId);
		movementQty = Quantity.zero(stockingUOM);

		final Optional<UomId> catchUOMId = productBL.getCatchUOMId(productId);
		if (catchUOMId.isPresent())
		{
			catchQty = Quantitys.zero(catchUOMId.get());
		}

		//
		// Order Line Link (retrieved from current Shipment)
		orderLineId = candidate.getOrderLineId();

		flatrateTermId = FlatrateTermId.ofRepoIdOrNull(candidate.getM_ShipmentSchedule().getC_Flatrate_Term_ID());
	}

	private void append(@NonNull final ShipmentScheduleWithHU candidate)
	{
		Check.assume(canAdd(candidate), "The given candidate can be added to shipment line builder; candidate={}", candidate);
		attributeValues.addAll(candidate.getAttributeValues()); // because of canAdd()==true, we may assume that it's all fine

		logger.trace("Adding candidate to {}: candidate={}", this, candidate);

		final Quantity qtyToAdd = candidate.getQtyPicked();
		if (qtyToAdd.signum() <= 0)
		{
			Loggables.addLog("IShipmentScheduleWithHU {} has QtyPicked={}", candidate, qtyToAdd);
		}
		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);
		final UomId stockUomId = productBL.getStockUOMId(productId);

		final Quantity qtyToAddInStockUom = uomConversionBL.convertQuantityTo(qtyToAdd, conversionCtx, stockUomId);
		movementQty = movementQty.add(qtyToAddInStockUom); // NOTE: we assume qtyToAdd is in stocking UOM

		if (candidate.getCatchQty().isPresent())
		{
			// catchQty might be null in a unit test, if you forgot to set up a catch-UOM for the productId's product
			assumeNotNull(catchQty, "Param candidate has a catch qty, but this instance has no catchQty; candidate={}; this={}", candidate, this);
			catchQty = Quantitys.add(UOMConversionContext.of(productId), catchQty, candidate.getCatchQty().get());
		}

		// Convert qtyToAdd (from candidate) to shipment line's UOM
		final Quantity qtyToAddConverted = uomConversionBL.convertQuantityTo(qtyToAdd, conversionCtx, qtyEntered.getUomId());
		qtyEntered = qtyEntered.add(qtyToAddConverted);

		// Enqueue candidate's LU/TU to list of HUs to be assigned
		appendHUsFromCandidate(candidate);

		final I_M_HU_PI_Item_Product piip = candidate.retrieveM_HU_PI_Item_Product();
		packingMaterial_huPIItemProducts.add(piip);

		// collect the candidates' QtyTU for the case that we need to create the shipment line without actually picked HUs.
		if (isManualPackingMaterial())
		{
			final I_M_ShipmentSchedule shipmentSchedule = create(candidate.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);

			final boolean qtTuOverrideIsSet = !isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Override);
			final QuantityTU qtyTUtoUse;
			if (qtTuOverrideIsSet)
			{
				qtyTUtoUse = QuantityTU.ofBigDecimal(shipmentSchedule.getQtyTU_Override());
			}
			else
			{
				// https://github.com/metasfresh/metasfresh/issues/4028 Multiple shipment lines for one order line all have the order line's TU-Qty
				// this is a dirty hack;
				// note that for "no-HU" shipment we assume a homogeneous PiiP and therefore may simply add up those TU quantities
				// TODO if we get to it before we ditch HU-less shipments altogether:
				// * store the shipment line's TU-qtys in M_ShipmentSchedule_QtyPicked (there is a column for that) even if no HUs were picked
				// * introduce a column like M_ShipmentSchedule.QtyTUToDeliver, keep it up to date and use that column in here
				// * note: updating that column should happen from the shipment-schedule-updater
				final List<I_M_InOutLine> shipmentLinesOfShipmentSchedule = Services.get(IShipmentScheduleAllocDAO.class)
						.retrieveOnShipmentLineRecordsQuery(shipmentSchedule)
						.andCollect(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID)
						.addOnlyActiveRecordsFilter()
						.create()
						.list(I_M_InOutLine.class);

				QuantityTU qtyTU = QuantityTU.ofBigDecimal(shipmentSchedule.getQtyTU_Calculated());
				for (final I_M_InOutLine shipmentLine : shipmentLinesOfShipmentSchedule)
				{
					qtyTU = qtyTU.subtract(QuantityTU.ofBigDecimal(shipmentLine.getQtyEnteredTU()));
				}
				qtyTUtoUse = qtyTU;
			}
			piipId2TuQtyFromShipmentSchedule.merge(
					HUPIItemProductId.ofRepoId(piip.getM_HU_PI_Item_Product_ID()),
					qtyTUtoUse,
					QuantityTU::add);
		}

		// Add current candidate to the list of candidates that will compose the generated shipment line
		candidates.add(candidate);
	}

	/**
	 * Gets LU or TU (if LU was not found) from candidate and append it to {@link #husToAssign} set.
	 * <p>
	 * When we will generate the shipment line, we will link those HUs to the generated shipment line (see {@link #createShipmentLine()}).
	 */
	private void appendHUsFromCandidate(@NonNull final ShipmentScheduleWithHU candidate)
	{
		I_M_HU topLevelHU = null;

		//
		// Append LU if any
		final I_M_HU luHU = candidate.getM_LU_HU();
		if (luHU != null && luHU.getM_HU_ID() > 0)
		{
			topLevelHU = luHU;
		}

		//
		// No LU found, append TU if any (if there was no LU)
		final I_M_HU tuHU = candidate.getM_TU_HU();
		if (topLevelHU == null &&
				tuHU != null && tuHU.getM_HU_ID() > 0)
		{
			// Guard: our TU shall be a top level HU
			if (!handlingUnitsBL.isTopLevel(tuHU))
			{
				throw new HUException("Candidate's TU is not a top level and there was no LU: " + candidate);
			}

			topLevelHU = tuHU;
		}

		final I_M_HU vhu = candidate.getVHU();
		if (topLevelHU == null && vhu != null)
		{
			topLevelHU = vhu;
		}

		if (topLevelHU != null)
		{
			final HUTopLevel huToAssign = new HUTopLevel(topLevelHU, luHU, tuHU, vhu);
			husToAssign.add(huToAssign);
		}
	}

	public I_M_InOutLine createShipmentLine()
	{
		if (isEmpty())
		{
			throw new AdempiereException("Cannot create shipment line because no ShipmentScheduleWithHU were added");
		}

		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class, currentShipment);
		shipmentLine.setAD_Org_ID(currentShipment.getAD_Org_ID());
		shipmentLine.setM_InOut(currentShipment);

		shipmentLine.setM_SectionCode_ID(currentShipment.getM_SectionCode_ID());

		// Line Warehouse & Locator (retrieved from current Shipment)
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoId(currentShipment.getM_Warehouse_ID());
			final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);
			shipmentLine.setM_Locator_ID(locatorId.getRepoId());
		}

		//
		// Product & ASI (retrieved from Shipment Schedule)
		shipmentLine.setM_Product_ID(productId.getRepoId());

		final I_M_AttributeSetInstance newASI;
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		if (attributeValues.isEmpty())
		{
			newASI = Services.get(IAttributeDAO.class).retrieveNoAttributeSetInstance();
		}
		else
		{
			final Builder attributeSetBuilder = ImmutableAttributeSet.builder();
			for (final IAttributeValue attributeValue : attributeValues)
			{
				attributeSetBuilder.attributeValue(
						attributeValue.getM_Attribute(),
						attributeValue.getValue());
			}
			newASI = attributeSetInstanceBL.createASIFromAttributeSet(attributeSetBuilder.build());
		}
		shipmentLine.setM_AttributeSetInstance(newASI);

		//
		// Order Line Link (retrieved from current Shipment)
		shipmentLine.setC_Order_ID(OrderAndLineId.toOrderRepoId(orderLineId));
		shipmentLine.setC_OrderLine_ID(OrderAndLineId.toOrderLineRepoId(orderLineId));
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId.getOrderLineId());
		if (orderLine != null)
		{
			shipmentLine.setC_Project_ID(orderLine.getC_Project_ID());
		}

		optimisticallySetLineNo(shipmentLine);

		//
		// Qty Entered and UOM
		shipmentLine.setC_UOM_ID(qtyEntered.getUomId().getRepoId());
		shipmentLine.setQtyEntered(qtyEntered.toBigDecimal());

		// Set MovementQty
		{
			// Don't do conversions. The movementQty which we summed up already contains exactly what we need (in the stocking-UOM!)
			shipmentLine.setQtyCU_Calculated(movementQty.toBigDecimal());
			shipmentLine.setMovementQty(movementQty.toBigDecimal());
		}

		if (catchQty != null)
		{
			if (catchQty.signum() != 0)
			{
				shipmentLine.setQtyDeliveredCatch(catchQty.toBigDecimal());
			}

			// Set the catch UOM also if the shipmentline has no catch qty.
			// Also we can end up with C_InvoiceCandidate_InoutLine records that refer to the same invoice candidate but have different UOMs
			shipmentLine.setCatch_UOM_ID(catchQty.getUomId().getRepoId());
		}

		// Update packing materials info, if there is "one" info
		shipmentLine.setIsManualPackingMaterial(isManualPackingMaterial());

		// https://github.com/metasfresh/metasfresh/issues/3503
		if (packingMaterial_huPIItemProducts != null && packingMaterial_huPIItemProducts.size() == 1)
		{
			final I_M_HU_PI_Item_Product piipForShipmentLine = packingMaterial_huPIItemProducts.iterator().next();
			shipmentLine.setM_HU_PI_Item_Product_Override(piipForShipmentLine); // this field is currently displayed in the swing client, so we set it, even if it's redundant
			shipmentLine.setM_HU_PI_Item_Product_Calculated(piipForShipmentLine);

			if (getQtyTypeToUse().isOnlyUseToDeliver())
			{
				final QuantityTU qtyTUs = computeQtyTUs(piipForShipmentLine);
				shipmentLine.setQtyTU_Override(qtyTUs.toBigDecimal());
			}
			else if (isManualPackingMaterial())
			{
				// there are no real HUs, so we need to calculate what the tu-qty would be
				final HUPIItemProductId piipForShipmentLineId = HUPIItemProductId.ofRepoId(piipForShipmentLine.getM_HU_PI_Item_Product_ID());
				final QuantityTU qtyTU = piipId2TuQtyFromShipmentSchedule.get(piipForShipmentLineId);

				if (qtyTU != null && !getQtyTypeToUse().isUseBoth())
				{
					shipmentLine.setQtyTU_Override(qtyTU.toBigDecimal());
				}
				else
				{
					// there are no real HUs, *and* we don't have any infos from the shipment schedule;
					// therefore, we make an educated guess, based on the packing instruction
					final QuantityTU qtyTUsCalculated = computeQtyTUs(piipForShipmentLine);
					shipmentLine.setQtyTU_Override(qtyTUsCalculated.toBigDecimal());
				}
			}
		}
		else
		{
			Loggables.withLogger(logger, Level.INFO)
					.addLog("Not setting the shipment line's M_HU_PI_Item_Product, because the added ShipmentScheduleWithHUs have different ones; huPIItemProducts={}",
							packingMaterial_huPIItemProducts);
		}

		//C_Flatrate_Term_ID
		Optional.ofNullable(flatrateTermId)
				.map(FlatrateTermId::getRepoId)
				.ifPresent(shipmentLine::setC_Flatrate_Term_ID);

		updateDimensionFromCandidates(shipmentLine);

		// Save Shipment Line
		save(shipmentLine);

		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(shipmentLine))
		{
			shipmentLineNoInfo.put(InOutLineId.ofRepoId(shipmentLine.getM_InOutLine_ID()), shipmentLine.getLine());

			//
			// Notify candidates that we have a shipment line
			for (final ShipmentScheduleWithHU candidate : getCandidates())
			{
				candidate.setM_InOutLine(shipmentLine);
			}

			//
			// Create HU Assignments
			createShipmentLineHUAssignments(shipmentLine);

			return shipmentLine;
		}
	}

	private void optimisticallySetLineNo(@NonNull final I_M_InOutLine shipmentLineRecord)
	{
		if (shipmentLineRecord.getC_OrderLine_ID() > 0)
		{
			// consider adding "Line" from the respective C_OrderLine (or other source) directly to the shipmentSchedule
			final int lineNo = shipmentLineRecord.getC_OrderLine().getLine();

			logger.debug("inoutLine has C_OrderLine_ID={} which has LineNo={}", shipmentLineRecord.getC_OrderLine_ID(), lineNo);
			shipmentLineRecord.setLine(lineNo); // if we hit a collision, we'll unset it later
		}
		else
		{
			logger.debug("inoutLine has C_OrderLine_ID=0");
		}
	}

	private QuantityTU computeQtyTUs(final I_M_HU_PI_Item_Product piipForShipmentLine)
	{
		final I_C_UOM productUOM = productBL.getStockUOM(productId);
		final Capacity capacity = Services.get(IHUCapacityBL.class).getCapacity(piipForShipmentLine, productId, productUOM);
		return capacity.calculateQtyTU(movementQty.toBigDecimal(), productUOM, uomConversionBL)
				.orElseThrow(() -> new AdempiereException("Invalid capacity: " + capacity));
	}

	/**
	 * Assign collected LU/TU pairs.
	 */
	private void createShipmentLineHUAssignments(final I_M_InOutLine shipmentLine)
	{
		// Assign Handling Units to shipment line
		boolean haveHUAssigments = false;
		for (final HUTopLevel huToAssign : husToAssign)
		{
			// transfer packing materials only if this TU was not already assigned to other document line (partial TUs case)
			final boolean isTransferPackingMaterials = alreadyAssignedTUIds.add(huToAssign.getTuHUId());

			huShipmentAssignmentBL.assignHU(shipmentLine, huToAssign, isTransferPackingMaterials);
			transferAttributesToShipmentLine(shipmentLine, huToAssign.getM_HU_TopLevel());
			haveHUAssigments = true;
		}

		// Guard: while generating shipment line from candidates, we shall have HUs for them
		if (!haveHUAssigments && !isManualPackingMaterial())
		{
			throw new HUException("No HUs to assign and manualPackingMaterial==false."
					+ "\n @M_InOutLine_ID@: " + shipmentLine
					+ "\n @M_HU_ID@: " + husToAssign);
		}
	}

	private void transferAttributesToShipmentLine(
			@NonNull final I_M_InOutLine shipmentLine,
			@NonNull final I_M_HU hu)
	{
		// Transfer attributes from HU to receipt line's ASI
		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor(huContext);
		executor.run(huContext -> {

			final IHUTransactionAttributeBuilder trxAttributesBuilder = executor.getTrxAttributesBuilder();
			final IAttributeStorageFactory attributeStorageFactory = trxAttributesBuilder.getAttributeStorageFactory();
			final IAttributeStorage huAttributeStorageFrom = attributeStorageFactory.getAttributeStorage(hu);
			// attributeStorageFactory.getAttributeStorage() would have given us an instance that would have
			// included also the packagingItemTemplate's attributes.
			// However, we only want the attributes that are declared in our iolcand-handler's attribute config.
			final IAttributeStorage shipmentLineAttributeStorageTo = //
					ASIAttributeStorage.createNew(attributeStorageFactory, shipmentLine.getM_AttributeSetInstance());

			final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
			final IHUStorage huStorageFrom = storageFactory.getStorage(hu);

			final I_C_UOM productUOM = productBL.getStockUOM(productId);
			final IHUAttributeTransferRequestBuilder requestBuilder = new HUAttributeTransferRequestBuilder(huContext)
					.setProductId(productId)
					.setQty(shipmentLine.getMovementQty())
					.setUOM(productUOM)
					.setAttributeStorageFrom(huAttributeStorageFrom)
					.setAttributeStorageTo(shipmentLineAttributeStorageTo)
					.setHUStorageFrom(huStorageFrom);

			final IHUAttributeTransferRequest request = requestBuilder.create();
			trxAttributesBuilder.transferAttributes(request);

			return IHUContextProcessor.NULL_RESULT;
		});
	}

	private void updateDimensionFromCandidates(final I_M_InOutLine shipmentLine)
	{
		final ImmutableSet<Dimension> candidatesDimensions = candidates.stream()
				.map(ShipmentScheduleWithHU::getDimension)
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());

		final Dimension dimension = Dimension.extractCommonDimension(candidatesDimensions);

		inOutLineDimensionFactory.updateRecord(shipmentLine, dimension);
	}

	public List<ShipmentScheduleWithHU> getCandidates()
	{
		return ImmutableList.copyOf(candidates);
	}

	/**
	 * {@code false} by default. Set to {@code} true if there aren't any real picked HUs, but we still want to createa a shipment line.
	 */
	public void setManualPackingMaterial(final boolean manualPackingMaterial)
	{
		this._manualPackingMaterial = manualPackingMaterial;
	}

	private boolean isManualPackingMaterial()
	{
		return _manualPackingMaterial
				|| getQtyTypeToUse().isOnlyUseToDeliver();
	}

	/**
	 * Sets a online {@link Set} which contains the list of TU Ids which were already assigned.
	 * <p>
	 * This set will be updated by this builder when TUs are assigned.
	 * <p>
	 * When this shipment line will try to assign an TU which is on this list, it will set the {@link I_M_HU_Assignment#setIsTransferPackingMaterials(boolean)} to <code>false</code>.
	 */
	public void setAlreadyAssignedTUIds(final Set<HuId> alreadyAssignedTUIds)
	{
		this.alreadyAssignedTUIds = alreadyAssignedTUIds;
	}

	public void setQtyTypeToUse(final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		this.qtyTypeToUse = qtyTypeToUse;
	}
}
