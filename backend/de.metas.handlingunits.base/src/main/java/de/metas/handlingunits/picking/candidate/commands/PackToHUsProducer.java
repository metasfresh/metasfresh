package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.allocation.transfer.LUTUResult.LU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TUsList;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class PackToHUsProducer
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final IHandlingUnitsBL handlingUnitsBL;
	private final IHUPIItemProductBL huPIItemProductBL;
	private final IUOMConversionBL uomConversionBL;
	private final InventoryService inventoryService;

	// Params
	private final boolean alwaysPackEachCandidateInItsOwnHU;
	private final PickingJobId contextPickingJobId;

	// State
	private static final int PACKAGE_NO_ZERO = 0;
	private final AtomicInteger nextPackageNo = new AtomicInteger(100);
	private final HashMap<PackToInfo, IAllocationDestination> packToDestinations = new HashMap<>();

	@Builder
	private PackToHUsProducer(
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IHUPIItemProductBL huPIItemProductBL,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull InventoryService inventoryService,
			//
			final boolean alwaysPackEachCandidateInItsOwnHU,
			@Nullable final PickingJobId contextPickingJobId)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.huPIItemProductBL = huPIItemProductBL;
		this.uomConversionBL = uomConversionBL;
		this.inventoryService = inventoryService;

		this.alwaysPackEachCandidateInItsOwnHU = alwaysPackEachCandidateInItsOwnHU;
		this.contextPickingJobId = contextPickingJobId;
	}

	@Value
	@Builder
	public static class PackToHURequest
	{
		@NonNull IHUContext huContext;
		@NonNull HuId pickFromHUId;
		@NonNull PackToInfo packToInfo;
		@NonNull ProductId productId;
		@NonNull Quantity qtyPicked;
		@Nullable Quantity catchWeight;
		@NonNull TableRecordReference documentRef;
		boolean checkIfAlreadyPacked;
		boolean createInventoryForMissingQty;
		/** Label-required CU quantity entry modes only: the source HUs are merged into one new CU that is booked as the picked HU inside a partial TU. */
		boolean isBookAsSingleCUInTU;
		@NonNull @Builder.Default ImmutableSet<HuId> allowedReservedVhuIds = ImmutableSet.of();

		/**
		 * When {@code true}, the packed CU(s) are recorded as CU parts of their (top-level, no-LU) TU so
		 * downstream records the leaf CU instead of the container TU — required only when the picked unit is a
		 * CU packed into a TU pick target (so a later unpick-to-floor can extract that CU). Leave {@code false}
		 * for a whole-TU pick, where the TU itself is the picked unit and must be recorded as a full TU
		 * (otherwise a spurious VHU is materialized on the shipment-schedule QtyPicked).
		 */
		boolean recordLeafCUsAsTUParts;
	}

	public LUTUResult packToHU(@NonNull final PackToHURequest request)
	{
		@NonNull final IHUContext huContext = request.getHuContext();
		@NonNull final HuId pickFromHUId = request.getPickFromHUId();
		@NonNull final PackToInfo packToInfo = request.getPackToInfo();
		@NonNull final ProductId productId = request.getProductId();
		@NonNull final Quantity qtyPicked = request.getQtyPicked();
		@Nullable final Quantity catchWeight = request.getCatchWeight();
		final boolean createInventoryForMissingQty = request.isCreateInventoryForMissingQty();

		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL, huContext, productId, catchWeight);

		final PickFromHUsList pickFromHUs;
		if (createInventoryForMissingQty)
		{
			final PickFromHU pickFromHU = newPickFromHU()
					.huContext(huContext)
					.hu(handlingUnitsBL.getById(pickFromHUId))
					.build();
			weightUpdater.capturePickFromHUBeforeTransfer(pickFromHU.toM_HU());

			final Quantity huQty = pickFromHU.getQuantity(productId, qtyPicked.getUOM());

			pickFromHUs = new PickFromHUsList();
			final Quantity qtyMissing;
			if (huQty.signum() <= 0)
			{
				// pickFromHU is destroyed/empty
				qtyMissing = qtyPicked;
			}
			else
			{
				qtyMissing = qtyPicked.subtract(huQty).toZeroIfNegative();
				pickFromHUs.add(pickFromHU);
			}

			if (qtyMissing.signum() > 0)
			{
				// pickFromHU contains partial quantity of what we need,
				// so we will do an inventory+ to get the missing qty

				final HuId newHuId = inventoryService.createInventoryForMissingQty(CreateVirtualInventoryWithQtyReq.builder()
						.clientId(pickFromHU.getClientId())
						.orgId(pickFromHU.getOrgId())
						.warehouseId(packToInfo.getShipFromWarehouseId())
						.productId(productId)
						.qty(qtyMissing)
						.movementDate(SystemTime.asZonedDateTime())
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.pickingJobId(contextPickingJobId)
						.build());

				pickFromHUs.add(newPickFromHU()
						.huContext(huContext)
						.hu(handlingUnitsBL.getById(newHuId))
						.isGeneratedFromInventory(true)
						.build());
			}
		}
		else
		{
			final PickFromHU pickFromHU = newPickFromHU()
					.huContext(huContext)
					.hu(handlingUnitsBL.getById(pickFromHUId))
					.build();
			weightUpdater.capturePickFromHUBeforeTransfer(pickFromHU.toM_HU());

			pickFromHUs = new PickFromHUsList();
			pickFromHUs.add(pickFromHU);
		}

		final PackContext ctx = new PackContext(request, pickFromHUs, weightUpdater);
		return request.isBookAsSingleCUInTU()
				? packAsSingleCUInTU(ctx)
				: packAsPickedQty(ctx);
	}

	/** What both pack flows share: the request, the HUs we pick from (before any consolidation) and the weight updater. */
	@Value
	private static class PackContext
	{
		@NonNull PackToHURequest request;
		@NonNull PickFromHUsList pickFromHUs;
		@NonNull PackedHUWeightNetUpdater weightUpdater;
	}

	/** Label-required CU quantity entry modes: the booked CU is the picked HU, packed as the single CU of a partial TU. */
	private LUTUResult packAsSingleCUInTU(@NonNull final PackContext ctx)
	{
		final PackToHURequest request = ctx.getRequest();
		final PickFromHUsList pickFromHUsToPack = consolidateToSingleCU(request, ctx.getPickFromHUs());

		if (request.getPackToInfo().getTu().isExistingTU())
		{
			final I_M_HU tuRecord = handlingUnitsBL.getById(request.getPackToInfo().getTu().getTuIdNotNull());
			final I_M_HU cuRecord = pickFromHUsToPack.getSingleHU().toM_HU();
			return addCUToExistingTU(
					ctx,
					newHUTransformService(request.getHuContext(), request.getAllowedReservedVhuIds()),
					tuRecord,
					cuRecord,
					() -> LUTUResult.ofTopLevelTUs(TUsList.of(TU.ofSingleTU(tuRecord, cuRecord))));
		}

		return loadIntoNewDestination(ctx, pickFromHUsToPack, this::toPartialTUWithItsSingleCU);
	}

	/** Default flow (every caller that does not book a single CU in a TU): the picked quantity is split out of the source HUs into the pack-to target. */
	private LUTUResult packAsPickedQty(@NonNull final PackContext ctx)
	{
		final PackToHURequest request = ctx.getRequest();
		final PackToInfo packToInfo = request.getPackToInfo();
		final ProductId productId = request.getProductId();
		final Quantity qtyPicked = request.getQtyPicked();
		final PickFromHUsList pickFromHUsToPack = ctx.getPickFromHUs();

		//
		// Case: the PickFrom HU can be considered already packed
		// i.e. it's an HU with exactly required qty and same packing instructions
		if (packToInfo.getLu() == null
				&& packToInfo.getTu().isNewTU()
				&& pickFromHUsToPack.isSingleHUAlreadyPacked(request.isCheckIfAlreadyPacked(), productId, qtyPicked, packToInfo.getTu().getTuPIIdNotNull()))
		{
			final I_M_HU pickFromHU = pickFromHUsToPack.getSingleHU().toM_HU();
			if (packToInfo.isPackForShipping())
			{
				handlingUnitsBL.setHUStatus(pickFromHU, X_M_HU.HUSTATUS_Picked);
			}
			ctx.getWeightUpdater().updatePackToHU(pickFromHU);

			// Intentionally independent of request.isRecordLeafCUsAsTUParts(): here the source HU already IS a
			// TU at the exact qty + packing instructions, so no CU is split out and packed INTO a container —
			// there is no distinct leaf CU to record, and a later unpick has nothing nested to strand.
			return LUTUResult.ofSingleTopLevelTU(pickFromHU);
		}
		//
		// Case: We are loading an existing TU by splitting out CUs from source HU
		else if (packToInfo.getTu().isExistingTU())
		{
			final I_M_HU tuRecord = handlingUnitsBL.getById(packToInfo.getTu().getTuIdNotNull());
			final ImmutableSet<HuId> allowedReservedVhuIds = request.getAllowedReservedVhuIds();
			final HUTransformService huTransformService = newHUTransformService(request.getHuContext(), allowedReservedVhuIds);
			final I_M_HU cuRecord = huTransformService.huToNewSingleCU(HUTransformService.HUsToNewCUsRequest.builder()
					.sourceHUs(pickFromHUsToPack.toHUsList())
					.productId(productId)
					.qtyCU(qtyPicked)
					.reservedVHUsPolicy(toReservedHUsPolicy(allowedReservedVhuIds))
					.build());
			// A CU-into-TU pick (picked unit is the CU): record the just-packed CU as a CU part of the
			// (top-level) TU — NOT the bare container TU. Recording only the container makes a later
			// unpick's extract-to-top-level a no-op (the TU is already top-level) and strands the picked CU
			// (Picked, still nested) inside the TU.
			// A whole-TU pick (picked unit is the TU) records the full TU as before (no spurious leaf VHU).
			return addCUToExistingTU(ctx, huTransformService, tuRecord, cuRecord,
					() -> request.isRecordLeafCUsAsTUParts()
							? LUTUResult.ofTopLevelTUs(TUsList.of(TU.ofSingleTU(tuRecord, cuRecord)))
							: LUTUResult.ofSingleTopLevelTU(tuRecord));
		}
		//
		// Case: We have to split out and pack our HU
		else
		{
			// For a CU-into-TU pick (picked unit is the CU) the LU/TU producer returns freshly-created
			// top-level TUs as "full" TUs (no CU parts tracked); record the packed CU(s) as CU parts so
			// downstream records the leaf CU, not the container TU (otherwise a later unpick strands the
			// picked CU inside the TU). For a whole-TU pick (picked unit is the TU) the full TUs are kept
			// as-is, so downstream records the TU and no spurious leaf VHU is materialized.
			return loadIntoNewDestination(ctx, pickFromHUsToPack,
					request.isRecordLeafCUsAsTUParts() ? this::recordPackedCUsAsParts : UnaryOperator.identity());
		}
	}

	/** Adds the CU to the existing TU and updates the weights; the result is that of the LU if the TU sits in one, else {@code resultIfNoLU}. */
	private LUTUResult addCUToExistingTU(
			@NonNull final PackContext ctx,
			@NonNull final HUTransformService huTransformService,
			@NonNull final I_M_HU tuRecord,
			@NonNull final I_M_HU cuRecord,
			@NonNull final Supplier<LUTUResult> resultIfNoLU)
	{
		huTransformService.addCUsToTU(ImmutableList.of(cuRecord), tuRecord);

		ctx.getWeightUpdater().updatePickFromHUs(ctx.getPickFromHUs().toHUsList());
		ctx.getWeightUpdater().updatePackToHU(cuRecord);

		final I_M_HU luRecord = handlingUnitsBL.getLoadingUnitHU(tuRecord);
		return luRecord == null
				? resultIfNoLU.get()
				: toLUResultOfPreExistingTU(tuRecord, luRecord, cuRecord);
	}

	private LUTUResult toLUResultOfPreExistingTU(@NonNull final I_M_HU tuRecord, @NonNull final I_M_HU luRecord, @NonNull final I_M_HU cuRecord)
	{
		if (handlingUnitsBL.isAggregateHU(tuRecord))
		{
			throw new AdempiereException("Loading to an aggregated TU is not supported because we won't be able to set the Weight and attributes afterwards");
		}
		final TU tu = TU.ofSingleTU(tuRecord, cuRecord);
		final LU lu = LU.of(luRecord, tu).markedAsPreExistingLU();
		return LUTUResult.ofLU(lu);
	}

	/**
	 * Loads the quantity into a new pack-to destination.
	 *
	 * @param adaptProducedTUs what the flow makes of the TUs/LU the destination produced
	 */
	private LUTUResult loadIntoNewDestination(
			@NonNull final PackContext ctx,
			@NonNull final PickFromHUsList pickFromHUsToPack,
			@NonNull final UnaryOperator<LUTUResult> adaptProducedTUs)
	{
		final PackToHURequest request = ctx.getRequest();
		final IAllocationDestination packToDestination;
		HULoader.builder()
				.source(HUListAllocationSourceDestination.of(pickFromHUsToPack.toHUsList()).setDestroyEmptyHUs(true))
				.destination(packToDestination = getPackToDestination(request.getPackToInfo()))
				.load(AllocationUtils.builder()
						.setHUContext(request.getHuContext())
						.setProduct(request.getProductId())
						.setQuantity(request.getQtyPicked())
						.setFromReferencedTableRecord(request.getDocumentRef())
						.setForceQtyAllocation(true)
						.setDeleteEmptyAndJustCreatedAggregatedTUs(true)
						.create());

		ctx.getWeightUpdater().updatePickFromHUs(ctx.getPickFromHUs().toHUsList());

		final LUTUResult result;
		if (packToDestination instanceof ILUTUProducerAllocationDestination)
		{
			result = adaptProducedTUs.apply(((ILUTUProducerAllocationDestination)packToDestination).getResult());
		}
		else if (packToDestination instanceof IHUProducerAllocationDestination)
		{
			result = LUTUResult.ofTopLevelTUs(((IHUProducerAllocationDestination)packToDestination).getCreatedHUs());
		}
		else if (packToDestination instanceof HUListAllocationSourceDestination)
		{
			final I_M_HU tuRecord = ((HUListAllocationSourceDestination)packToDestination).getSingleSourceHU();
			final I_M_HU luRecord = handlingUnitsBL.getLoadingUnitHU(tuRecord);
			if (luRecord == null)
			{
				result = LUTUResult.ofSingleTopLevelTU(tuRecord);
			}
			else
			{
				final TU tu = handlingUnitsBL.isAggregateHU(tuRecord)
						? TU.ofAggregatedTU(tuRecord, handlingUnitsBL.getTUsCount(tuRecord))
						: TU.ofSingleTU(tuRecord);
				final LU lu = LU.of(luRecord, tu).markedAsPreExistingLU();
				result = LUTUResult.ofLU(lu);
			}
		}
		else
		{
			throw new AdempiereException("Unknown destination type: " + packToDestination);
		}

		ctx.getWeightUpdater().updatePackToHUs(result);
		return result;
	}

	/** For CU quantity entry the booked CU must be the picked HU, so the result is the partial TU holding its single CU. */
	private LUTUResult toPartialTUWithItsSingleCU(@NonNull final LUTUResult producedResult)
	{
		if (!producedResult.isSingleFullTU() || producedResult.getSingleTU().isAggregate())
		{
			throw new AdempiereException("Expected the new TU to be exactly one full, not aggregated TU but got: " + producedResult);
		}

		final I_M_HU tuRecord = producedResult.getSingleTU().toHU();
		final List<I_M_HU> cus = handlingUnitsBL.retrieveIncludedHUs(tuRecord);
		if (cus.size() != 1)
		{
			throw new AdempiereException("Expected exactly one CU in the new TU but found " + cus.size() + ": " + tuRecord);
		}
		final TUsList partialTU = TUsList.of(TU.ofSingleTU(tuRecord, cus.get(0)));

		if (producedResult.isSingleTopLevelTUOnly())
		{
			return LUTUResult.ofTopLevelTUs(partialTU);
		}
		else if (producedResult.isSingleLU() && producedResult.getTopLevelTUs().isEmpty())
		{
			return LUTUResult.ofLU(producedResult.getSingleLU().toBuilder().tus(partialTU).build());
		}
		else
		{
			throw new AdempiereException("Expected the new TU to be either a top-level TU or on a single pallet (LU) but got: " + producedResult);
		}
	}

	/**
	 * For a CU-into-TU pick, re-wraps each fresh top-level TU to carry its packed CU(s) as CU parts, so
	 * downstream records the leaf CU (not the container TU) and a later unpick can extract it instead of
	 * leaving it Picked inside the TU. Reached only via {@link PackToHURequest#isRecordLeafCUsAsTUParts()}
	 * (set solely by {@code PickingJobPickCommand}, one pack per {@code PackToInfo}), so {@code getVHUs(tuRecord)}
	 * returns exactly this pick's CU(s).
	 */
	private LUTUResult recordPackedCUsAsParts(@NonNull final LUTUResult result)
	{
		if (!result.getLus().isEmpty() || result.getTopLevelTUs().isEmpty())
		{
			return result;
		}

		final ImmutableList.Builder<LUTUResult.TU> tus = ImmutableList.builder();
		for (final LUTUResult.TU tu : result.getTopLevelTUs())
		{
			// Only enrich a real single TU that carries no CU parts yet; aggregates / already-parted TUs stay as-is.
			if (tu.isAggregate() || !tu.isFullTU())
			{
				tus.add(tu);
				continue;
			}

			final I_M_HU tuRecord = tu.toHU();
			final List<I_M_HU> vhus = handlingUnitsBL.getVHUs(tuRecord);
			if (vhus.isEmpty())
			{
				tus.add(tu);
			}
			else
			{
				// Record ALL of this TU's VHUs as its CU parts (a pick may split into >1 CU) — not just one;
				// the loop likewise enriches every top-level TU. "ofSingleTU" means a non-aggregate TU, not one CU.
				tus.add(LUTUResult.TU.ofSingleTU(tuRecord, vhus.toArray(new I_M_HU[0])));
			}
		}

		return LUTUResult.ofTopLevelTUs(LUTUResult.TUsList.of(tus.build()));
	}

	/** Merges the source HUs into ONE new standalone CU of the given quantity and returns it as the only pick-from HU. */
	private PickFromHUsList consolidateToSingleCU(@NonNull final PackToHURequest request, @NonNull final PickFromHUsList pickFromHUs)
	{
		final IHUContext huContext = request.getHuContext();
		final ImmutableSet<HuId> allowedReservedVhuIds = request.getAllowedReservedVhuIds();
		final I_M_HU singleCU = newHUTransformService(huContext, allowedReservedVhuIds)
				.husToNewSingleCU(HUTransformService.HUsToNewCUsRequest.builder()
						.sourceHUs(pickFromHUs.toHUsList())
						.productId(request.getProductId())
						.qtyCU(request.getQtyPicked())
						.reservedVHUsPolicy(toReservedHUsPolicy(allowedReservedVhuIds))
						.build());

		final PickFromHUsList result = new PickFromHUsList();
		result.add(newPickFromHU().huContext(huContext).hu(singleCU).build());
		return result;
	}

	/** The one rule for which reserved source pieces a pick may use: only not reserved ones, except the reserved VHUs the request allows. */
	private static ReservedHUsPolicy toReservedHUsPolicy(@NonNull final ImmutableSet<HuId> allowedReservedVhuIds)
	{
		return allowedReservedVhuIds.isEmpty()
				? ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED
				: ReservedHUsPolicy.onlyNotReservedExceptVhuIds(allowedReservedVhuIds);
	}

	private static HUTransformService newHUTransformService(@NonNull final IHUContext huContext, @NonNull final ImmutableSet<HuId> allowedReservedVhuIds)
	{
		return HUTransformService.builderForHUcontext()
				.huContext(huContext)
				.allowedReservedVhuIds(allowedReservedVhuIds)
				.build();
	}

	private PickFromHU.PickFromHUBuilder newPickFromHU()
	{
		return PickFromHU.builder()
				.handlingUnitsBL(handlingUnitsBL);
	}

	public PackToInfo extractPackToInfo(
			@NonNull final ProductId productId,
			@NonNull final PackToSpec stepPackToTUSpec,
			@Nullable final LUPickingTarget pickingTarget,
			@Nullable final TUPickingTarget tuPickingTarget,
			@NonNull final BPartnerLocationId shipToBPLocationId,
			@NonNull final LocatorId shipFromLocatorId)
	{
		final TUPickingTarget tuPickingTargetEffective;
		final Capacity tuCapacity;

		if (tuPickingTarget != null)
		{
			tuPickingTargetEffective = tuPickingTarget;
			tuCapacity = Capacity.createInfiniteCapacity(productId, productBL.getStockUOM(productId));
		}
		else
		{
			final HuPackingInstructionsIdAndCaptionAndCapacity packingInstructionAndCapacity = getPackingInstructionsAndCapacity(stepPackToTUSpec);
			tuPickingTargetEffective = packingInstructionAndCapacity.toTUPickingTarget();
			tuCapacity = packingInstructionAndCapacity.getCapacityOrNull();
		}

		final int packageNo = isPackEachCandidateInItsOwnHU(tuPickingTargetEffective) ? nextPackageNo.getAndIncrement() : PACKAGE_NO_ZERO;

		return PackToInfo.builder()
				.shipToBPLocationId(shipToBPLocationId)
				.shipFromLocatorId(shipFromLocatorId)
				.tu(tuPickingTargetEffective)
				.tuCapacity(tuCapacity)
				.tuPackageNo(packageNo)
				.lu(pickingTarget)
				.build();
	}

	@NonNull
	public PackToInfo extractPackToInfo(
			@NonNull final PackToSpec packToSpec,
			@NonNull final LocatorId shipFromLocatorId)
	{
		final HuPackingInstructionsIdAndCaptionAndCapacity packingInstructionAndCapacity = getPackingInstructionsAndCapacity(packToSpec);

		return PackToInfo.builder()
				.shipFromLocatorId(shipFromLocatorId)
				.tu(packingInstructionAndCapacity.toTUPickingTarget())
				.tuCapacity(packingInstructionAndCapacity.getCapacityOrNull())
				.build();
	}

	private boolean isPackEachCandidateInItsOwnHU(@NonNull final TUPickingTarget tuPickingTarget)
	{
		if (tuPickingTarget.isExistingTU())
		{
			return false;
		}
		else if (tuPickingTarget.isNewTU())
		{
			return this.alwaysPackEachCandidateInItsOwnHU || tuPickingTarget.getTuPIIdNotNull().isVirtual();
		}
		else
		{
			return this.alwaysPackEachCandidateInItsOwnHU;
		}
	}

	public IAllocationDestination getPackToDestination(final PackToInfo packToInfo)
	{
		return packToDestinations.computeIfAbsent(packToInfo, this::createPackToDestination);
	}

	private IAllocationDestination createPackToDestination(final PackToInfo packToInfo)
	{
		final TUPickingTarget tu = packToInfo.getTu();
		final LUPickingTarget lu = packToInfo.getLu();

		if (tu.isNewTU())
		{
			final HuPackingInstructionsId tuPackingInstructionsId = tu.getTuPIIdNotNull();
			final Capacity tuCapacity = packToInfo.getTuCapacity();
			if (tuPackingInstructionsId.isVirtual())
			{
				return LUPickingTarget.apply(lu, new LUPickingTarget.CaseMapper<IHUProducerAllocationDestination>()
				{
					@Override
					public IHUProducerAllocationDestination noLU()
					{
						final HUProducerDestination vhuProducer = HUProducerDestination.ofVirtualPI();
						setupPackToDestinationCommonOptions(vhuProducer, packToInfo);
						vhuProducer.setMaxHUsToCreate(1);
						return vhuProducer;
					}

					@Override
					public IHUProducerAllocationDestination newLU(@NonNull final HuPackingInstructionsId luPackingInstructionsId)
					{
						final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
						setupPackToDestinationCommonOptions(lutuProducer, packToInfo);

						//
						// TU
						lutuProducer.setTUPI(HuPackingInstructionsId.VIRTUAL);

						//
						// LU
						lutuProducer.setLUPI(luPackingInstructionsId);
						lutuProducer.setMaxLUs(1);
						lutuProducer.setMaxTUsPerLUInfinite();

						return lutuProducer;
					}

					@Override
					public IHUProducerAllocationDestination existingLU(final HuId luId, final HUQRCode luQRCode)
					{
						final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
						setupPackToDestinationCommonOptions(lutuProducer, packToInfo);

						//
						// TU
						lutuProducer.setTUPI(HuPackingInstructionsId.VIRTUAL);

						//
						// LU
						lutuProducer.setLU(luId);
						lutuProducer.setMaxLUs(0);
						lutuProducer.setMaxTUsPerLUInfinite();

						return lutuProducer;
					}
				});

			}
			else if (tuCapacity != null)
			{
				final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
				setupPackToDestinationCommonOptions(lutuProducer, packToInfo);

				//
				// TU
				lutuProducer.setTUPI(tuPackingInstructionsId);
				lutuProducer.addCUPerTU(tuCapacity);

				//
				// LU
				// the picked qty can be loose pieces (e.g. a VHU) => no source TU tells the LU item; if the LU's PI has no item without product, use the picked product's item
				lutuProducer.setLUItemFallbackProductId(tuCapacity.getProductId());
				LUPickingTarget.apply(lu, new LUPickingTarget.CaseConsumer()
				{
					@Override
					public void noLU()
					{
						lutuProducer.setNoLU();
					}

					@Override
					public void newLU(@NonNull final HuPackingInstructionsId luPackingInstructionsId)
					{
						lutuProducer.setLUPI(luPackingInstructionsId);
						lutuProducer.setMaxLUs(1);
						lutuProducer.setMaxTUsPerLUInfinite();
					}

					@Override
					public void existingLU(final HuId luId, final HUQRCode qrCode)
					{
						final I_M_HU lu = handlingUnitsBL.getById(luId);
						lutuProducer.setLU(lu);
						// same lookup as the HU editor: the product-specific item the LU already uses for the TU PI (kept for any product,
						// so loose pieces of another product join it too), else the LU's item without product;
						// if neither exists, the producer's own lookup (with the fallback product) applies
						final I_M_HU_PI_Item luItemInUse = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(lu, handlingUnitsBL.getPI(tuPackingInstructionsId), InterfaceWrapperHelper.getContextAware(lu));
						if (luItemInUse != null)
						{
							lutuProducer.setLUItemPI(luItemInUse);
						}
						lutuProducer.setMaxLUs(0);
						lutuProducer.setMaxTUsPerLUInfinite();
					}
				});

				return lutuProducer;
			}
			else
			{
				// Shall not happen
				if (lu != null)
				{
					throw new AdempiereException("Picking to an LU when TU capacity is not known is not allowed");
				}

				final HUProducerDestination huProducer = HUProducerDestination.of(tuPackingInstructionsId);
				setupPackToDestinationCommonOptions(huProducer, packToInfo);
				huProducer.setMaxHUsToCreate(1);
				return huProducer;
			}
		}
		else if (tu.isExistingTU())
		{
			final HuId tuId = tu.getTuIdNotNull();
			return HUListAllocationSourceDestination.ofHUId(tuId);
		}
		else
		{
			throw new AdempiereException("Unknown TU picking target: " + tu);
		}
	}

	@NonNull
	private HuPackingInstructionsIdAndCaptionAndCapacity getPackingInstructionsAndCapacity(@NonNull final PackToSpec packToSpec)
	{
		if (packToSpec.isVirtual())
		{
			return HuPackingInstructionsIdAndCaptionAndCapacity.of(HuPackingInstructionsId.VIRTUAL, "-", null);
		}
		else if (packToSpec.getTuPackingInstructionsId() != null)
		{
			final HUPIItemProduct tuPIItemProduct = huPIItemProductBL.getById(packToSpec.getTuPackingInstructionsId());
			final HuPackingInstructionsId tuPackingInstructionsId = handlingUnitsBL.getPackingInstructionsId(tuPIItemProduct.getPiItemId());
			final String piName = handlingUnitsBL.getPIName(tuPackingInstructionsId);
			return HuPackingInstructionsIdAndCaptionAndCapacity.of(tuPackingInstructionsId, piName, tuPIItemProduct.toCapacity());
		}
		else if (packToSpec.getGenericPackingInstructionsId() != null)
		{
			final String piName = handlingUnitsBL.getPIName(packToSpec.getGenericPackingInstructionsId());
			return HuPackingInstructionsIdAndCaptionAndCapacity.of(packToSpec.getGenericPackingInstructionsId(), piName, null);
		}
		else
		{
			throw new AdempiereException("Unsupported pack to spec: " + packToSpec);
		}
	}

	private static void setupPackToDestinationCommonOptions(@NonNull final IHUProducerAllocationDestination producer, @NonNull final PackToInfo packToInfo)
	{
		if (packToInfo.isPackForShipping())
		{
			producer.setHUStatus(X_M_HU.HUSTATUS_Picked);
			producer.setBPartnerAndLocationId(packToInfo.getShipToBPLocationId());
		}
		producer.setLocatorId(packToInfo.getShipFromLocatorId());
	}

	//
	//
	// ------------------------------------
	//
	//

	@Value
	@Builder
	public static class PackToInfo
	{
		@Nullable BPartnerLocationId shipToBPLocationId;
		@NonNull LocatorId shipFromLocatorId;

		//
		// TU
		@NonNull TUPickingTarget tu;
		@Nullable Capacity tuCapacity;
		int tuPackageNo;

		//
		// LU
		@Nullable LUPickingTarget lu;

		@With @Builder.Default boolean packForShipping = true;

		public WarehouseId getShipFromWarehouseId() {return getShipFromLocatorId().getWarehouseId();}
	}

	//
	//
	// ------------------------------------
	//
	//

	@EqualsAndHashCode
	private static class PickFromHU
	{
		@NonNull private final IHandlingUnitsBL handlingUnitsBL;
		@NonNull private final IHUContext huContext;
		@NonNull private final I_M_HU hu;
		@Getter private final boolean isGeneratedFromInventory;
		private transient IHUStorage _huStorage = null; // lazy
		private transient HuPackingInstructionsId _packingInstructionsId = null; // lazy

		@Builder
		private PickFromHU(
				@NonNull IHandlingUnitsBL handlingUnitsBL,
				@NonNull final IHUContext huContext,
				@NonNull final I_M_HU hu,
				final boolean isGeneratedFromInventory)
		{
			this.handlingUnitsBL = handlingUnitsBL;
			this.huContext = huContext;
			this.hu = hu;
			this.isGeneratedFromInventory = isGeneratedFromInventory;
		}

		public I_M_HU toM_HU() {return hu;}

		public ClientId getClientId() {return ClientId.ofRepoId(hu.getAD_Client_ID());}

		public OrgId getOrgId() {return OrgId.ofRepoId(hu.getAD_Org_ID());}

		public Quantity getQuantity(final ProductId productId, final I_C_UOM uom) {return getStorage().getQuantity(productId, uom);}

		public IHUStorage getStorage()
		{
			IHUStorage huStorage = _huStorage;
			if (huStorage == null)
			{
				huStorage = _huStorage = huContext.getHUStorageFactory().getStorage(hu);
			}
			return huStorage;
		}

		public boolean isAlreadyPacked(@NonNull final ProductId productId, @NonNull final Quantity qty, @Nullable final HuPackingInstructionsId packingInstructionsId)
		{
			return isSingleProductWithQtyEqualsTo(productId, qty)
					&& HuPackingInstructionsId.equals(packingInstructionsId, getPackingInstructionsId());
		}

		public boolean isSingleProductWithQtyEqualsTo(@NonNull final ProductId productId, @NonNull final Quantity qty)
		{
			return getStorage().isSingleProductWithQtyEqualsTo(productId, qty);
		}

		@NonNull
		public HuPackingInstructionsId getPackingInstructionsId()
		{
			HuPackingInstructionsId packingInstructionsId = this._packingInstructionsId;
			if (packingInstructionsId == null)
			{
				packingInstructionsId = this._packingInstructionsId = handlingUnitsBL.getPackingInstructionsId(hu);
			}
			return packingInstructionsId;
		}
	}

	//
	//
	// ------------------------------------
	//
	//

	private static class PickFromHUsList
	{
		private final ArrayList<PickFromHU> list = new ArrayList<>();

		PickFromHUsList() {}

		public void add(@NonNull final PickFromHU pickFromHU) {list.add(pickFromHU);}

		public List<I_M_HU> toHUsList() {return list.stream().map(PickFromHU::toM_HU).collect(ImmutableList.toImmutableList());}

		public PickFromHU getSingleHU() {return CollectionUtils.singleElement(list);}

		public boolean isSingleHUAlreadyPacked(
				final boolean checkIfAlreadyPacked,
				@NonNull final ProductId productId,
				@NonNull final Quantity qty,
				@Nullable final HuPackingInstructionsId packingInstructionsId)
		{
			if (list.size() != 1)
			{
				return false;
			}
			final PickFromHU hu = list.get(0);

			// NOTE we check isGeneratedFromInventory because we want to avoid splitting an HU that we just generated it, even if checkIfAlreadyPacked=false
			if (checkIfAlreadyPacked || hu.isGeneratedFromInventory())
			{
				return hu.isAlreadyPacked(productId, qty, packingInstructionsId);
			}
			else
			{
				return false;
			}
		}
	}

	//
	//
	// ------------------------------------
	//
	//
	@Value(staticConstructor = "of")
	@EqualsAndHashCode(doNotUseGetters = true)
	private static class HuPackingInstructionsIdAndCaptionAndCapacity
	{
		@NonNull HuPackingInstructionsId huPackingInstructionsId;
		@NonNull String caption;
		@Nullable Capacity capacity;

		@Nullable
		public Capacity getCapacityOrNull() {return capacity;}

		@SuppressWarnings("unused")
		@NonNull
		public Optional<Capacity> getCapacity() {return Optional.ofNullable(capacity);}

		public TUPickingTarget toTUPickingTarget()
		{
			return TUPickingTarget.ofPackingInstructions(huPackingInstructionsId, caption);
		}

	}
}
