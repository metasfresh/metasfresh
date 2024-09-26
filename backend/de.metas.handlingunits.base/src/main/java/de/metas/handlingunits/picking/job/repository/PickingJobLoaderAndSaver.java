package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_HUAlternative;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_PickedHU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.LocatorInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobHeader;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobPickFromAlternative;
import de.metas.handlingunits.picking.job.model.PickingJobPickFromAlternativeId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromMap;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.inout.ShipmentScheduleId;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

class PickingJobLoaderAndSaver extends PickingJobSaver
{
	@NonNull
	private final PickingJobLoaderSupportingServices loadingSupportingServices;

	private final HashMap<PickingJobId, Boolean> hasLocks = new HashMap<>();

	private PickingJobLoaderAndSaver(@NonNull final PickingJobLoaderSupportingServices loadingSupportingServices)
	{
		this.loadingSupportingServices = loadingSupportingServices;
	}

	public static PickingJobLoaderAndSaver forLoading(@NonNull final PickingJobLoaderSupportingServices loadingSupportingServices)
	{
		return new PickingJobLoaderAndSaver(loadingSupportingServices);
	}

	public static PickingJobSaver forSaving()
	{
		return new PickingJobSaver();
	}

	public PickingJob loadById(@NonNull final PickingJobId pickingJobId)
	{
		if (pickingJobs.get(pickingJobId) == null)
		{
			loadRecords(ImmutableSet.of(pickingJobId));
		}

		return loadJob(pickingJobs.get(pickingJobId));
	}

	public List<PickingJob> loadByIds(@NonNull final Set<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return ImmutableList.of();
		}

		// IMPORTANT to take a snapshot of Sets.difference because that's a live view ...and we are going to add data TO pickingJobS map...
		final HashSet<PickingJobId> pickingJobIdsToLoad = new HashSet<>(Sets.difference(pickingJobIds, pickingJobs.keySet()));
		if (!pickingJobIdsToLoad.isEmpty())
		{
			loadRecordsFromDB(pickingJobIdsToLoad);
		}

		return pickingJobIds.stream()
				.map(pickingJobs::get)
				.map(this::loadJob)
				.collect(ImmutableList.toImmutableList());
	}

	public void addAlreadyLoadedFromDB(final I_M_Picking_Job record)
	{
		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
		pickingJobs.put(pickingJobId, record);
	}

	public void addAlreadyLoadedFromDB(final I_M_Picking_Job_Line record)
	{
		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
		pickingJobLines.put(pickingJobId, record);
	}

	public void addAlreadyLoadedFromDB(final I_M_Picking_Job_Step record)
	{
		final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID());
		pickingJobSteps.put(pickingJobLineId, record);
	}

	public void addAlreadyLoadedFromDB(final I_M_Picking_Job_HUAlternative record)
	{
		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
		pickingJobHUAlternatives.put(pickingJobId, record);
	}

	public void addAlreadyLoadedFromDB(final I_M_Picking_Job_Step_HUAlternative record)
	{
		final PickingJobStepId pickingJobStepId = extractPickingJobStepId(record);
		pickingJobStepAlternatives.put(pickingJobStepId, record);
	}

	public PickingJobPickFromAlternativeId getPickingJobHUAlternativeId(
			@NonNull final PickingJobId pickingJobId,
			@NonNull final HuId alternativeHUId,
			@NonNull final ProductId productId)
	{
		return pickingJobHUAlternatives.get(pickingJobId)
				.stream()
				.filter(alt -> HuId.equals(HuId.ofRepoId(alt.getPickFrom_HU_ID()), alternativeHUId)
						&& ProductId.equals(ProductId.ofRepoId(alt.getM_Product_ID()), productId))
				.map(alt -> PickingJobPickFromAlternativeId.ofRepoId(alt.getM_Picking_Job_HUAlternative_ID()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No HU alternative found for " + pickingJobId + ", " + alternativeHUId + ", " + productId
																  + ". Available HU alternatives are: " + pickingJobHUAlternatives));
	}

	private void loadRecords(final Set<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return;
		}

		loadRecordsFromDB(pickingJobIds);

		final Collection<I_M_Picking_Job> records = pickingJobs.values();

		final ImmutableSet<OrderId> salesOrderIds = records.stream().map(PickingJobLoaderAndSaver::extractSalesOrderId).collect(ImmutableSet.toImmutableSet());
		loadingSupportingServices.warmUpSalesOrderDocumentNosCache(salesOrderIds);

		final ImmutableSet<BPartnerId> customerIds = records.stream().map(record -> extractDeliveryBPLocationId(record).getBpartnerId()).collect(ImmutableSet.toImmutableSet());
		loadingSupportingServices.warmUpBPartnerNamesCache(customerIds);

		hasLocks.putAll(computePickingJobHasLocks(pickingJobIds));
	}

	private PickingJob loadJob(final I_M_Picking_Job record)
	{
		final Optional<PickingSlotIdAndCaption> pickingSlot = Optional.ofNullable(PickingSlotId.ofRepoIdOrNull(record.getM_PickingSlot_ID()))
				.map(loadingSupportingServices::getPickingSlotIdAndCaption);

		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());

		return PickingJob.builder()
				.id(pickingJobId)
				.header(toPickingJobHeader(record))
				.pickingSlot(pickingSlot)
				.luPickTarget(extractLUPickingTarget(record))
				.tuPickTarget(extractTUPickingTarget(record))
				.docStatus(PickingJobDocStatus.ofCode(record.getDocStatus()))
				.lines(pickingJobLines.get(pickingJobId)
						.stream()
						.map(this::loadLine)
						.collect(ImmutableList.toImmutableList()))
				.pickFromAlternatives(pickingJobHUAlternatives.get(pickingJobId)
						.stream()
						.map(this::loadPickFromAlternative)
						.collect(ImmutableSet.toImmutableSet()))
				.build();
	}

	private PickingJobHeader toPickingJobHeader(final I_M_Picking_Job record)
	{
		final BPartnerLocationId deliveryBPLocationId = extractDeliveryBPLocationId(record);
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());

		return PickingJobHeader.builder()
				.salesOrderDocumentNo(loadingSupportingServices.getSalesOrderDocumentNo(extractSalesOrderId(record)))
				.preparationDate(loadingSupportingServices.toZonedDateTime(record.getPreparationDate(), orgId))
				.deliveryDate(loadingSupportingServices.toZonedDateTime(record.getDeliveryDate(), orgId))
				.customerName(loadingSupportingServices.getBPartnerName(deliveryBPLocationId.getBpartnerId()))
				.deliveryBPLocationId(deliveryBPLocationId)
				.deliveryRenderedAddress(record.getDeliveryToAddress())
				.isAllowPickingAnyHU(record.isAllowPickingAnyHU())
				.lockedBy(UserId.ofRepoIdOrNullIfSystem(record.getPicking_User_ID()))
				.handoverLocationId(BPartnerLocationId.ofRepoIdOrNull(record.getHandOver_Partner_ID(), record.getHandOver_Location_ID()))
				.build();
	}

	@NonNull
	private Optional<LUPickingTarget> extractLUPickingTarget(final I_M_Picking_Job record)
	{
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoIdOrNull(record.getM_LU_HU_PI_ID());
		final HuId luId = HuId.ofRepoIdOrNull(record.getM_LU_HU_ID());

		if (luId != null)
		{
			final HUQRCode qrCode = loadingSupportingServices.getQRCodeByHUId(luId);
			return Optional.of(LUPickingTarget.ofExistingHU(luId, qrCode));
		}
		else if (luPIId != null)
		{
			final String caption = loadingSupportingServices.getPICaption(luPIId);
			return Optional.of(LUPickingTarget.ofPackingInstructions(luPIId, caption));
		}
		else
		{
			return Optional.empty();
		}
	}

	@NonNull
	private Optional<TUPickingTarget> extractTUPickingTarget(final I_M_Picking_Job record)
	{
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoIdOrNull(record.getM_TU_HU_PI_ID());

		if (tuPIId != null)
		{
			final String caption = loadingSupportingServices.getPICaption(tuPIId);
			return Optional.of(TUPickingTarget.ofPackingInstructions(tuPIId, caption));
		}
		else
		{
			return Optional.empty();
		}
	}

	@NonNull
	private static BPartnerLocationId extractDeliveryBPLocationId(final I_M_Picking_Job record)
	{
		return BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID());
	}

	@NonNull
	private static OrderId extractSalesOrderId(final I_M_Picking_Job record)
	{
		return OrderId.ofRepoId(record.getC_Order_ID());
	}

	private PickingJobLine loadLine(@NonNull final I_M_Picking_Job_Line record)
	{
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID());

		final HUPIItemProductId huPIItemProductId = HUPIItemProductId.ofRepoIdOrNone(record.getM_HU_PI_Item_Product_ID());
		final HUPIItemProduct packingInfo = loadingSupportingServices.getPackingInfo(huPIItemProductId);

		return PickingJobLine.builder()
				.id(pickingJobLineId)
				.productId(productId)
				.productNo(loadingSupportingServices.getProductNo(productId))
				.productName(loadingSupportingServices.getProductName(productId))
				.packingInfo(packingInfo)
				.qtyToPick(Quantitys.create(record.getQtyToPick(), UomId.ofRepoId(record.getC_UOM_ID())))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.catchUomId(UomId.ofRepoIdOrNull(record.getCatch_UOM_ID()))
				.steps(pickingJobSteps.get(pickingJobLineId)
						.stream()
						.map(this::loadStep)
						.collect(ImmutableList.toImmutableList()))
				.isManuallyClosed(record.isManuallyClosed())
				.pickingUnit(computePickingUnit(UomId.ofRepoIdOrNull(record.getCatch_UOM_ID()), packingInfo))
				.build();
	}

	private PickingJobStep loadStep(@NonNull final I_M_Picking_Job_Step record)
	{
		final PickingJobStepId pickingJobStepId = PickingJobStepId.ofRepoId(record.getM_Picking_Job_Step_ID());
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		final ArrayList<PickingJobStepPickFrom> pickFroms = new ArrayList<>();
		pickFroms.add(loadMainPickFrom(record));

		pickingJobStepAlternatives.get(pickingJobStepId)
				.stream()
				.map(this::loadPickFrom)
				.forEach(pickFroms::add);

		return PickingJobStep.builder()
				.id(pickingJobStepId)
				.isGeneratedOnFly(record.isDynamic())
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				//
				// What?
				.productId(productId)
				.productName(loadingSupportingServices.getProductName(productId))
				.qtyToPick(Quantitys.create(record.getQtyToPick(), uomId))
				//
				// Pick From
				.pickFroms(PickingJobStepPickFromMap.ofList(pickFroms))
				//
				// Pick To Specification
				.packToSpec(PackToSpec.ofTUPackingInstructionsId(HUPIItemProductId.ofRepoIdOrNone(record.getPackTo_HU_PI_Item_Product_ID())))
				//
				.build();
	}

	private PickingJobStepPickFrom loadMainPickFrom(final I_M_Picking_Job_Step record)
	{
		final LocatorId locatorId = LocatorId.ofRepoId(record.getPickFrom_Warehouse_ID(), record.getPickFrom_Locator_ID());
		final HuId pickFromHUId = HuId.ofRepoId(record.getPickFrom_HU_ID());

		return PickingJobStepPickFrom.builder()
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.pickFromLocator(LocatorInfo.builder()
						.id(locatorId)
						.caption(loadingSupportingServices.getLocatorName(locatorId))
						.build())
				.pickFromHU(getHUInfo(pickFromHUId))
				.pickedTo(loadPickedTo(record))
				.build();
	}

	private HUInfo getHUInfo(@NonNull final HuId huId)
	{
		return HUInfo.builder()
				.id(huId)
				.qrCode(getQRCode(huId))
				.build();
	}

	private PickingJobStepPickFrom loadPickFrom(final I_M_Picking_Job_Step_HUAlternative record)
	{
		final PickingJobPickFromAlternativeId alternativeId = extractAlternativeId(record);

		final LocatorId pickFromLocatorId = LocatorId.ofRepoId(record.getPickFrom_Warehouse_ID(), record.getPickFrom_Locator_ID());
		final HuId pickFromHUId = HuId.ofRepoId(record.getPickFrom_HU_ID());

		return PickingJobStepPickFrom.builder()
				.pickFromKey(PickingJobStepPickFromKey.alternative(alternativeId))
				.pickFromLocator(LocatorInfo.builder()
						.id(pickFromLocatorId)
						.caption(loadingSupportingServices.getLocatorName(pickFromLocatorId))
						.build())
				.pickFromHU(getHUInfo(pickFromHUId))
				.pickedTo(loadPickedTo(record))
				.build();
	}

	private HUQRCode getQRCode(final HuId huId)
	{
		return loadingSupportingServices.getQRCodeByHUId(huId);
	}

	@Nullable
	private PickingJobStepPickedTo loadPickedTo(final I_M_Picking_Job_Step record)
	{
		final PickingJobStepId pickingJobStepId = PickingJobStepId.ofRepoId(record.getM_Picking_Job_Step_ID());
		final ImmutableList<PickingJobStepPickedToHU> pickedHUs = loadPickedToHUs(pickingJobStepId, null);
		final QtyRejectedWithReason qtyRejected = extractQtyRejected(record).orElse(null);
		if (!pickedHUs.isEmpty() || qtyRejected != null)
		{
			return PickingJobStepPickedTo.builder()
					.qtyRejected(qtyRejected)
					.actualPickedHUs(pickedHUs)
					.build();
		}
		else
		{
			return null;
		}
	}

	private static Optional<QtyRejectedWithReason> extractQtyRejected(final I_M_Picking_Job_Step record)
	{
		//noinspection DuplicatedCode
		final UomId uomId = UomId.ofRepoIdOrNull(record.getC_UOM_ID());
		final QtyRejectedReasonCode reasonCode = QtyRejectedReasonCode.ofNullableCode(record.getRejectReason()).orElse(null);
		return reasonCode != null && uomId != null
				? Optional.of(QtyRejectedWithReason.of(Quantitys.create(record.getQtyRejectedToPick(), uomId), reasonCode))
				: Optional.empty();
	}

	private ImmutableList<PickingJobStepPickedToHU> loadPickedToHUs(
			@NonNull final PickingJobStepId pickingJobStepId,
			@Nullable final PickingJobPickFromAlternativeId alternativeId)
	{
		return pickedHUs.get(pickingJobStepId)
				.stream()
				.filter(record -> PickingJobPickFromAlternativeId.equals(extractAlternativeId(record), alternativeId))
				.map(this::loadPickedToHU)
				.collect(ImmutableList.toImmutableList());
	}

	private PickingJobStepPickedToHU loadPickedToHU(@NonNull final I_M_Picking_Job_Step_PickedHU record)
	{
		return PickingJobStepPickedToHU.builder()
				.pickFromHUId(HuId.ofRepoId(record.getPickFrom_HU_ID()))
				.actualPickedHU(extractPickedHUInfo(record))
				.qtyPicked(Quantitys.create(record.getQtyPicked(), UomId.ofRepoId(record.getC_UOM_ID())))
				.catchWeight(extractCatchWeight(record))
				.build();
	}

	private HUInfo extractPickedHUInfo(@NonNull final I_M_Picking_Job_Step_PickedHU record)
	{
		final HuId pickedHUId = HuId.ofRepoId(record.getPicked_HU_ID());
		HUQRCode pickedQRCode = HUQRCode.fromNullableGlobalQRCodeJsonString(record.getPicked_RenderedQRCode());
		if (pickedQRCode == null)
		{
			pickedQRCode = getQRCode(pickedHUId);
		}

		return HUInfo.builder().id(pickedHUId).qrCode(pickedQRCode).build();

	}

	@Nullable
	private static Quantity extractCatchWeight(@NonNull final I_M_Picking_Job_Step_PickedHU record)
	{
		final UomId catchWeightUomId = UomId.ofRepoIdOrNull(record.getCatch_UOM_ID());
		return catchWeightUomId != null
				? Quantitys.create(record.getCatchWeight(), catchWeightUomId)
				: null;
	}

	@Nullable
	private PickingJobStepPickedTo loadPickedTo(@NonNull final I_M_Picking_Job_Step_HUAlternative record)
	{
		final PickingJobStepId pickingJobStepId = extractPickingJobStepId(record);
		final PickingJobPickFromAlternativeId alternativeId = extractAlternativeId(record);
		final ImmutableList<PickingJobStepPickedToHU> pickedHUs = loadPickedToHUs(pickingJobStepId, alternativeId);
		final QtyRejectedWithReason qtyRejected = extractQtyRejected(record).orElse(null);
		if (!pickedHUs.isEmpty() || qtyRejected != null)
		{
			return PickingJobStepPickedTo.builder()
					.qtyRejected(qtyRejected)
					.actualPickedHUs(pickedHUs)
					.build();
		}
		else
		{
			return null;
		}
	}

	@NonNull
	private static Optional<QtyRejectedWithReason> extractQtyRejected(final I_M_Picking_Job_Step_HUAlternative record)
	{
		//noinspection DuplicatedCode
		final UomId uomId = UomId.ofRepoIdOrNull(record.getC_UOM_ID());
		final QtyRejectedReasonCode reasonCode = QtyRejectedReasonCode.ofNullableCode(record.getRejectReason()).orElse(null);
		return reasonCode != null && uomId != null
				? Optional.of(QtyRejectedWithReason.of(Quantitys.create(record.getQtyRejectedToPick(), uomId), reasonCode))
				: Optional.empty();
	}

	private PickingJobPickFromAlternative loadPickFromAlternative(final I_M_Picking_Job_HUAlternative record)
	{
		final LocatorId locatorId = LocatorId.ofRepoId(record.getPickFrom_Warehouse_ID(), record.getPickFrom_Locator_ID());
		final HuId pickFromHUId = HuId.ofRepoId(record.getPickFrom_HU_ID());

		return PickingJobPickFromAlternative.builder()
				.id(PickingJobPickFromAlternativeId.ofRepoId(record.getM_Picking_Job_HUAlternative_ID()))
				.locatorInfo(LocatorInfo.builder()
						.id(locatorId)
						.caption(loadingSupportingServices.getLocatorName(locatorId))
						.build())
				.pickFromHU(getHUInfo(pickFromHUId))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyAvailable(Quantitys.create(record.getQtyAvailable(), UomId.ofRepoId(record.getC_UOM_ID())))
				.build();
	}

	public Stream<PickingJobReference> streamPickingJobReferences(@NonNull final Set<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return Stream.of();
		}

		// IMPORTANT to take a snapshot of Sets.difference because that's a live view ...and we are going to add data TO pickingJobS map...
		final ImmutableSet<PickingJobId> pickingJobIdsToLoad = ImmutableSet.copyOf(Sets.difference(pickingJobIds, pickingJobs.keySet()));
		if (!pickingJobIdsToLoad.isEmpty())
		{
			loadRecordsFromDB(pickingJobIdsToLoad);
		}

		return pickingJobIds.stream()
				.map(pickingJobs::get)
				.map(this::loadPickingJobReference);
	}

	private PickingJobReference loadPickingJobReference(final I_M_Picking_Job record)
	{
		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
		final PickingJobHeader header = toPickingJobHeader(record);

		return PickingJobReference.builder()
				.pickingJobId(pickingJobId)
				.salesOrderDocumentNo(header.getSalesOrderDocumentNo())
				.customerId(header.getCustomerId())
				.customerName(header.getCustomerName())
				.deliveryDate(header.getDeliveryDate())
				.preparationDate(header.getPreparationDate())
				.shipmentScheduleIds(getShipmentScheduleIds(pickingJobId))
				.isShipmentSchedulesLocked(getShipmentSchedulesIsLocked(pickingJobId).isTrue())
				.deliveryLocationId(header.getDeliveryBPLocationId())
				.handoverLocationId(header.getHandoverLocationId())
				.build();
	}

	private ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds(final PickingJobId pickingJobId)
	{
		final ImmutableSet.Builder<ShipmentScheduleId> shipmentScheduleIds = ImmutableSet.builder();

		for (final I_M_Picking_Job_Line line : this.pickingJobLines.get(pickingJobId))
		{
			final ShipmentScheduleId lineShipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(line.getM_ShipmentSchedule_ID());
			if (lineShipmentScheduleId != null)
			{
				shipmentScheduleIds.add(lineShipmentScheduleId);
			}

			final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(line.getM_Picking_Job_Line_ID());
			for (final I_M_Picking_Job_Step step : this.pickingJobSteps.get(pickingJobLineId))
			{
				final ShipmentScheduleId stepShipmentScheduleId = ShipmentScheduleId.ofRepoId(step.getM_ShipmentSchedule_ID());
				shipmentScheduleIds.add(stepShipmentScheduleId);
			}
		}

		return shipmentScheduleIds.build();
	}

	private ImmutableSetMultimap<PickingJobId, ShipmentScheduleId> getShipmentScheduleIds(final Set<PickingJobId> pickingJobIds)
	{
		final ImmutableSetMultimap.Builder<PickingJobId, ShipmentScheduleId> result = ImmutableSetMultimap.builder();
		for (final PickingJobId pickingJobId : pickingJobIds)
		{
			final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = getShipmentScheduleIds(pickingJobId);
			result.putAll(pickingJobId, shipmentScheduleIds);
		}
		return result.build();
	}

	private Map<PickingJobId, Boolean> computePickingJobHasLocks(@NonNull final Set<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSetMultimap<PickingJobId, ShipmentScheduleId> shipmentScheduleIdsByPickingJobId = getShipmentScheduleIds(pickingJobIds);

		final SetMultimap<ShipmentScheduleId, ExistingLockInfo> existingLocks = loadingSupportingServices.getLocks(shipmentScheduleIdsByPickingJobId.values());

		final ImmutableMap.Builder<PickingJobId, Boolean> result = ImmutableMap.builder();
		for (final PickingJobId pickingJobId : pickingJobIds)
		{
			boolean hasLocks = false;
			for (ShipmentScheduleId shipmentScheduleId : shipmentScheduleIdsByPickingJobId.get(pickingJobId))
			{
				if (existingLocks.containsKey(shipmentScheduleId))
				{
					hasLocks = true;
					break;
				}
			}

			result.put(pickingJobId, hasLocks);
		}

		return result.build();
	}

	private OptionalBoolean getShipmentSchedulesIsLocked(@NonNull final PickingJobId pickingJobId)
	{
		return OptionalBoolean.ofNullableBoolean(hasLocks.get(pickingJobId));
	}

	private PickingUnit computePickingUnit(@Nullable final UomId catchUomId, @NonNull final HUPIItemProduct packingInfo)
	{
		// If catch weight, always pick at CU level because user has to weight the products
		if (!loadingSupportingServices.isCatchWeightTUPickingEnabled() && catchUomId != null)
		{
			return PickingUnit.CU;
		}

		return packingInfo.isFiniteTU() ? PickingUnit.TU : PickingUnit.CU;
	}
}
