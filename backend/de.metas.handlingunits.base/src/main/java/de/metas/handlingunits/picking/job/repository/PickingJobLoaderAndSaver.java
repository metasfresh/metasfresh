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
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.model.CurrentPickingTarget;
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
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
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
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.compiere.util.TimeUtil;
import org.eevolution.api.PPOrderId;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
			loadRecordsFromDB(ImmutableSet.of(pickingJobId));
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

	protected void loadRecordsFromDB(final Set<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return;
		}

		super.loadRecordsFromDB(pickingJobIds);

		loadingSupportingServices.warmUpSalesOrderDocumentNosCache(extractSalesOrderIdsFromCachedObjects());
		loadingSupportingServices.warmUpBPartnerNamesCache(extractCustomerIdsFromCachedObjects());

		hasLocks.putAll(computePickingJobHasLocks(pickingJobIds));
	}

	private ImmutableSet<OrderId> extractSalesOrderIdsFromCachedObjects()
	{
		final ImmutableSet.Builder<OrderId> result = ImmutableSet.builder();

		this.pickingJobs.values().forEach((pickingJobRecord) -> {
			final OrderId salesOrderId = extractSalesOrderId(pickingJobRecord);
			if (salesOrderId != null)
			{
				result.add(salesOrderId);
			}
		});

		this.pickingJobLines.values().forEach((pickingJobLineRecord) -> {
			final OrderId salesOrderId = extractSalesOrderId(pickingJobLineRecord);
			result.add(salesOrderId);
		});

		return result.build();
	}

	private ImmutableSet<BPartnerId> extractCustomerIdsFromCachedObjects()
	{
		final ImmutableSet.Builder<BPartnerId> result = ImmutableSet.builder();

		this.pickingJobs.values().forEach((pickingJobRecord) -> {
			final BPartnerLocationId deliveryBPLocationId = extractDeliveryBPLocationId(pickingJobRecord);
			if (deliveryBPLocationId != null)
			{
				result.add(deliveryBPLocationId.getBpartnerId());
			}
		});

		this.pickingJobLines.values().forEach((pickingJobLineRecord) -> {
			final BPartnerLocationId deliveryBPLocationId = extractDeliveryBPLocationId(pickingJobLineRecord);
			result.add(deliveryBPLocationId.getBpartnerId());
		});

		return result.build();
	}

	private PickingJob loadJob(final I_M_Picking_Job record)
	{
		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
		final PickingJobHeader pickingJobHeader = toPickingJobHeader(record);

		return PickingJob.builder()
				.id(pickingJobId)
				.header(pickingJobHeader)
				.pickFromHU(extractPickFromHU(record))
				.currentPickingTarget(extractCurrentPickingTarget(record))
				.docStatus(PickingJobDocStatus.ofCode(record.getDocStatus()))
				.lines(pickingJobLines.get(pickingJobId)
						.stream()
						.map(lineRecord -> loadLine(lineRecord, pickingJobHeader.getAggregationType()))
						.sorted(Comparator.comparing(PickingJobLine::getId)) // make sure we have a predictable order
						.collect(ImmutableList.toImmutableList()))
				.pickFromAlternatives(pickingJobHUAlternatives.get(pickingJobId)
						.stream()
						.map(this::loadPickFromAlternative)
						.collect(ImmutableSet.toImmutableSet()))
				.build();
	}

	private Optional<HUInfo> extractPickFromHU(final I_M_Picking_Job record)
	{
		final HuId pickFromHUId = HuId.ofRepoIdOrNull(record.getPickFrom_HU_ID());
		if (pickFromHUId == null)
		{
			return Optional.empty();
		}

		return Optional.of(
				HUInfo.builder()
						.id(pickFromHUId)
						.qrCode(HUQRCode.fromGlobalQRCodeJsonString(Objects.requireNonNull(record.getPickFrom_HUQRCode())))
						.build()
		);
	}

	private PickingJobHeader toPickingJobHeader(final I_M_Picking_Job record)
	{
		@NonNull final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		@Nullable final OrderId salesOrderId = extractSalesOrderId(record);
		@Nullable final BPartnerLocationId deliveryBPLocationId = extractDeliveryBPLocationId(record);
		@Nullable final Timestamp preparationDate = record.getPreparationDate();
		@Nullable final Timestamp deliveryDate = record.getDeliveryDate();

		return PickingJobHeader.builder()
				.aggregationType(PickingJobAggregationType.ofCode(record.getPickingJobAggregationType()))
				.salesOrderDocumentNo(salesOrderId != null ? loadingSupportingServices.getSalesOrderDocumentNo(salesOrderId) : null)
				.preparationDate(preparationDate != null ? loadingSupportingServices.toZonedDateTime(preparationDate, orgId) : null)
				.deliveryDate(deliveryDate != null ? loadingSupportingServices.toZonedDateTime(deliveryDate, orgId) : null)
				.customerName(deliveryBPLocationId != null ? loadingSupportingServices.getBPartnerName(deliveryBPLocationId.getBpartnerId()) : null)
				.deliveryBPLocationId(deliveryBPLocationId)
				.deliveryRenderedAddress(record.getDeliveryToAddress())
				.isAllowPickingAnyHU(record.isAllowPickingAnyHU())
				.lockedBy(UserId.ofRepoIdOrNullIfSystem(record.getPicking_User_ID()))
				.handoverLocationId(BPartnerLocationId.ofRepoIdOrNull(record.getHandOver_Partner_ID(), record.getHandOver_Location_ID()))
				.build();
	}

	private CurrentPickingTarget extractCurrentPickingTarget(final I_M_Picking_Job record)
	{
		return CurrentPickingTarget.builder()
				.pickingSlot(extractPickingSlot(record))
				.luPickingTarget(extractLUPickingTarget(record))
				.tuPickingTarget(extractTUPickingTarget(record))
				.build();
	}

	@Nullable
	private PickingSlotIdAndCaption extractPickingSlot(final I_M_Picking_Job record)
	{
		return PickingSlotId.optionalOfRepoId(record.getM_PickingSlot_ID())
				.map(loadingSupportingServices::getPickingSlotIdAndCaption)
				.orElse(null);
	}

	@Nullable
	private LUPickingTarget extractLUPickingTarget(final I_M_Picking_Job record)
	{
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoIdOrNull(record.getM_LU_HU_PI_ID());
		final HuId luId = HuId.ofRepoIdOrNull(record.getM_LU_HU_ID());

		if (luId != null)
		{
			final HUQRCode qrCode = loadingSupportingServices.getQRCodeByHUId(luId);
			return LUPickingTarget.ofExistingHU(luId, qrCode);
		}
		else if (luPIId != null)
		{
			final String caption = loadingSupportingServices.getPICaption(luPIId);
			return LUPickingTarget.ofPackingInstructions(luPIId, caption);
		}
		else
		{
			return null;
		}
	}

	@Nullable
	private TUPickingTarget extractTUPickingTarget(final I_M_Picking_Job record)
	{
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoIdOrNull(record.getM_TU_HU_PI_ID());

		if (tuPIId != null)
		{
			final String caption = loadingSupportingServices.getPICaption(tuPIId);
			return TUPickingTarget.ofPackingInstructions(tuPIId, caption);
		}
		else
		{
			return null;
		}
	}

	@Nullable
	private static BPartnerLocationId extractDeliveryBPLocationId(final I_M_Picking_Job record)
	{
		return BPartnerLocationId.ofRepoIdOrNull(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID());
	}

	@Nullable
	private static OrderId extractSalesOrderId(final I_M_Picking_Job record)
	{
		return OrderId.ofRepoIdOrNull(record.getC_Order_ID());
	}

	@NonNull
	private PickingJobLine loadLine(
			@NonNull final I_M_Picking_Job_Line record,
			@NonNull final PickingJobAggregationType aggregationType)
	{
		final ProductId productId = extractProductId(record);
		final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID());

		final HUPIItemProductId huPIItemProductId = HUPIItemProductId.ofRepoIdOrNone(record.getM_HU_PI_Item_Product_ID());
		final HUPIItemProduct packingInfo = loadingSupportingServices.getPackingInfo(huPIItemProductId);
		final OrderAndLineId salesOrderAndLineId = extractSalesOrderAndLineId(record);
		final BPartnerLocationId deliveryBPLocationId = extractDeliveryBPLocationId(record);
		final PickingJobOptions pickingJobOptions = getPickingJobOptions(deliveryBPLocationId.getBpartnerId());

		final String salesOrderDocumentNo = loadingSupportingServices.getSalesOrderDocumentNo(salesOrderAndLineId.getOrderId());
		final ITranslatableString productName = loadingSupportingServices.getProductName(productId);
		final CurrentPickingTarget currentPickingTarget = extractCurrentPickingTarget(record);

		final ITranslatableString caption;
		switch (aggregationType)
		{
			case SALES_ORDER:
				caption = productName;
				break;
			case PRODUCT:
				caption = TranslatableStrings.builder()
						.append(currentPickingTarget.getPickingSlotCaption().orElse(""))
						.appendIfNotEmpty(", ")
						.append(salesOrderDocumentNo)
						.build();
				break;
			default:
				throw new AdempiereException("Unknown aggregation type: " + aggregationType);
		}

		return PickingJobLine.builder()
				.id(pickingJobLineId)
				.caption(caption)
				.productId(productId)
				.productNo(loadingSupportingServices.getProductNo(productId))
				.ean13ProductCode(loadingSupportingServices.getEAN13ProductCode(productId, deliveryBPLocationId.getBpartnerId()).orElse(null))
				.productName(productName)
				.productCategoryId(loadingSupportingServices.getProductCategoryId(productId))
				.packingInfo(packingInfo)
				.qtyToPick(extractQtyToPick(record))
				.salesOrderAndLineId(salesOrderAndLineId)
				.salesOrderDocumentNo(salesOrderDocumentNo)
				.orderLineSeqNo(loadingSupportingServices.getSalesOrderLineSeqNo(salesOrderAndLineId))
				.deliveryBPLocationId(deliveryBPLocationId)
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.catchUomId(UomId.ofRepoIdOrNull(record.getCatch_UOM_ID()))
				.steps(pickingJobSteps.get(pickingJobLineId)
						.stream()
						.map(this::loadStep)
						.collect(ImmutableList.toImmutableList()))
				.isManuallyClosed(record.isManuallyClosed())
				.pickingUnit(computePickingUnit(UomId.ofRepoIdOrNull(record.getCatch_UOM_ID()), packingInfo, pickingJobOptions))
				.currentPickingTarget(currentPickingTarget)
				.pickFromManufacturingOrderId(PPOrderId.ofRepoIdOrNull(record.getPP_Order_ID()))
				.build();
	}

	private CurrentPickingTarget extractCurrentPickingTarget(final I_M_Picking_Job_Line record)
	{
		return CurrentPickingTarget.builder()
				.pickingSlot(extractPickingSlot(record))
				.luPickingTarget(extractLUPickingTarget(record))
				.tuPickingTarget(extractTUPickingTarget(record))
				.build();
	}

	@Nullable
	private PickingSlotIdAndCaption extractPickingSlot(final I_M_Picking_Job_Line record)
	{
		return PickingSlotId.optionalOfRepoId(record.getM_PickingSlot_ID())
				.map(loadingSupportingServices::getPickingSlotIdAndCaption)
				.orElse(null);
	}

	@Nullable
	private LUPickingTarget extractLUPickingTarget(final I_M_Picking_Job_Line record)
	{
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoIdOrNull(record.getCurrent_PickTo_LU_PI_ID());
		final HuId luId = HuId.ofRepoIdOrNull(record.getCurrent_PickTo_LU_ID());

		if (luId != null)
		{
			final HUQRCode qrCode = StringUtils.trimBlankToOptional(record.getCurrent_PickTo_LU_QRCode())
					.map(HUQRCode::fromGlobalQRCodeJsonString)
					.orElseGet(() -> loadingSupportingServices.getQRCodeByHUId(luId));
			return LUPickingTarget.ofExistingHU(luId, qrCode);
		}
		else if (luPIId != null)
		{
			final String caption = loadingSupportingServices.getPICaption(luPIId);
			return LUPickingTarget.ofPackingInstructions(luPIId, caption);
		}
		else
		{
			return null;
		}
	}

	@Nullable
	private TUPickingTarget extractTUPickingTarget(final I_M_Picking_Job_Line record)
	{
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoIdOrNull(record.getCurrent_PickTo_TU_PI_ID());

		if (tuPIId != null)
		{
			final String caption = loadingSupportingServices.getPICaption(tuPIId);
			return TUPickingTarget.ofPackingInstructions(tuPIId, caption);
		}
		else
		{
			return null;
		}
	}

	private static @NotNull BPartnerLocationId extractDeliveryBPLocationId(final @NotNull I_M_Picking_Job_Line record)
	{
		return BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID());
	}

	@NotNull
	private static OrderId extractSalesOrderId(final @NotNull I_M_Picking_Job_Line record)
	{
		return OrderId.ofRepoId(record.getC_Order_ID());
	}

	@NotNull
	private static OrderAndLineId extractSalesOrderAndLineId(final @NotNull I_M_Picking_Job_Line record)
	{
		return OrderAndLineId.ofRepoIds(extractSalesOrderId(record), record.getC_OrderLine_ID());
	}

	@NonNull
	private static Quantity extractQtyToPick(final @NotNull I_M_Picking_Job_Line record)
	{
		return Quantitys.of(record.getQtyToPick(), UomId.ofRepoId(record.getC_UOM_ID()));
	}

	@NotNull
	private static ProductId extractProductId(final @NotNull I_M_Picking_Job_Line record)
	{
		return ProductId.ofRepoId(record.getM_Product_ID());
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
				.qtyToPick(Quantitys.of(record.getQtyToPick(), uomId))
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
				? Optional.of(QtyRejectedWithReason.of(Quantitys.of(record.getQtyRejectedToPick(), uomId), reasonCode))
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
				.qtyPicked(Quantitys.of(record.getQtyPicked(), UomId.ofRepoId(record.getC_UOM_ID())))
				.catchWeight(extractCatchWeight(record))
				.createdAt(TimeUtil.asInstant(record.getCreated()))
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
				? Quantitys.of(record.getCatchWeight(), catchWeightUomId)
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
				? Optional.of(QtyRejectedWithReason.of(Quantitys.of(record.getQtyRejectedToPick(), uomId), reasonCode))
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
				.qtyAvailable(Quantitys.of(record.getQtyAvailable(), UomId.ofRepoId(record.getC_UOM_ID())))
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
		loadRecordsFromDB(pickingJobIdsToLoad);

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
				.aggregationType(PickingJobAggregationType.ofCode(record.getPickingJobAggregationType()))
				.salesOrderDocumentNo(header.getSalesOrderDocumentNo())
				.salesOrderId(extractSalesOrderId(record))
				.customerId(header.getCustomerId())
				.customerName(header.getCustomerName())
				.deliveryBPLocationId(header.getDeliveryBPLocationId())
				.deliveryDate(header.getDeliveryDate())
				.preparationDate(header.getPreparationDate())
				.shipmentScheduleIds(getShipmentScheduleIds(pickingJobId))
				.isShipmentSchedulesLocked(getShipmentSchedulesIsLocked(pickingJobId).isTrue())
				.handoverLocationId(header.getHandoverLocationId())
				.productId(extractSingleProductIdOrNull(pickingJobId))
				.productName(extractSingleProductNameOrNull(pickingJobId))
				.qtyToDeliver(extractQtyToPickOrNull(pickingJobId))
				.build();
	}

	@Nullable
	private ITranslatableString extractSingleProductNameOrNull(final PickingJobId pickingJobId)
	{
		final ProductId productId = extractSingleProductIdOrNull(pickingJobId);
		return productId != null ? loadingSupportingServices.getProductName(productId) : null;
	}

	@Nullable
	private ProductId extractSingleProductIdOrNull(final PickingJobId pickingJobId)
	{
		ProductId productId = null;

		for (final I_M_Picking_Job_Line line : this.pickingJobLines.get(pickingJobId))
		{
			final ProductId lineProductId = extractProductId(line);
			if (productId == null)
			{
				productId = lineProductId;
			}
			else if (!ProductId.equals(productId, lineProductId))
			{
				// different products found
				return null;
			}
		}

		return productId;
	}

	@Nullable
	private Quantity extractQtyToPickOrNull(final PickingJobId pickingJobId)
	{
		return PickingJob.extractQtyToPickOrNull(
				this.pickingJobLines.get(pickingJobId),
				PickingJobLoaderAndSaver::extractProductId,
				PickingJobLoaderAndSaver::extractQtyToPick
		);
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
			for (final ShipmentScheduleId shipmentScheduleId : shipmentScheduleIdsByPickingJobId.get(pickingJobId))
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

	private PickingUnit computePickingUnit(@Nullable final UomId catchUomId, @NonNull final HUPIItemProduct packingInfo, @NonNull final PickingJobOptions options)
	{
		// If catch weight, always pick at CU level because user has to weight the products
		if (!options.isCatchWeightTUPickingEnabled() && catchUomId != null)
		{
			return PickingUnit.CU;
		}

		return packingInfo.isFiniteTU() ? PickingUnit.TU : PickingUnit.CU;
	}

	private PickingJobOptions getPickingJobOptions(@NonNull final BPartnerId customerId)
	{
		return loadingSupportingServices.getPickingJobOptions(customerId);
	}
}
