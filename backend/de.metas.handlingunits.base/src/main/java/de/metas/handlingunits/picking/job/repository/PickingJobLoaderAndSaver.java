package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_HUAlternative;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_PickedHU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.job.model.HUInfo;
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
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.inout.ShipmentScheduleId;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

class PickingJobLoaderAndSaver
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final PickingJobLoaderSupportingServices _loadingSupportingServices;

	private final HashMap<PickingJobId, I_M_Picking_Job> pickingJobs = new HashMap<>();
	private final ArrayListMultimap<PickingJobId, I_M_Picking_Job_HUAlternative> pickingJobHUAlternatives = ArrayListMultimap.create();
	private final ArrayListMultimap<PickingJobId, I_M_Picking_Job_Line> pickingJobLines = ArrayListMultimap.create();
	private final ArrayListMultimap<PickingJobLineId, I_M_Picking_Job_Step> pickingJobSteps = ArrayListMultimap.create();
	private final ArrayListMultimap<PickingJobStepId, I_M_Picking_Job_Step_HUAlternative> pickingJobStepAlternatives = ArrayListMultimap.create();
	private final ArrayListMultimap<PickingJobStepId, I_M_Picking_Job_Step_PickedHU> pickedHUs = ArrayListMultimap.create();
	private final HashMap<PickingJobId, Boolean> hasLocks = new HashMap<>();

	private PickingJobLoaderAndSaver(@Nullable final PickingJobLoaderSupportingServices loadingSupportingServices)
	{
		this._loadingSupportingServices = loadingSupportingServices;
	}

	public static PickingJobLoaderAndSaver forLoading(@NonNull final PickingJobLoaderSupportingServices loadingSupportingServices)
	{
		return new PickingJobLoaderAndSaver(loadingSupportingServices);
	}

	public static PickingJobLoaderAndSaver forSaving()
	{
		return new PickingJobLoaderAndSaver(null);
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

	public void save(@NonNull final PickingJob pickingJob)
	{
		final PickingJobId pickingJobId = pickingJob.getId();
		if (pickingJobs.get(pickingJobId) == null)
		{
			loadRecordsFromDB(ImmutableSet.of(pickingJobId));
		}

		final I_M_Picking_Job record = pickingJobs.get(pickingJobId);
		updateRecord(record, pickingJob);
		InterfaceWrapperHelper.save(record);
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());

		saveLines(pickingJob.getLines(), pickingJobId, orgId, pickingJob.getDocStatus());
	}

	private void saveLines(
			final ImmutableList<PickingJobLine> lines,
			final PickingJobId pickingJobId,
			final OrgId orgId,
			final PickingJobDocStatus docStatus)
	{
		final HashMap<PickingJobLineId, I_M_Picking_Job_Line> existingRecords = pickingJobLines.get(pickingJobId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(line -> PickingJobLineId.ofRepoId(line.getM_Picking_Job_Line_ID())));

		for (final PickingJobLine line : lines)
		{
			final I_M_Picking_Job_Line existingRecord = existingRecords.get(line.getId());
			Check.assumeNotNull(existingRecord, "line record shall exist for {}", line);

			// NOTE: atm we have nothing to sync on line level
			updateRecord(existingRecord, line, docStatus);
			InterfaceWrapperHelper.save(existingRecord);

			saveSteps(line.getSteps(), pickingJobId, line.getId(), orgId, docStatus);
		}
	}

	private void saveSteps(
			final ImmutableList<PickingJobStep> steps,
			final PickingJobId pickingJobId,
			final PickingJobLineId pickingJobLineId,
			final OrgId orgId,
			final PickingJobDocStatus docStatus)
	{
		final HashMap<PickingJobStepId, I_M_Picking_Job_Step> existingRecords = pickingJobSteps.get(pickingJobLineId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(step -> PickingJobStepId.ofRepoId(step.getM_Picking_Job_Step_ID())));

		for (final PickingJobStep step : steps)
		{
			final PickingJobStepPickFrom mainPickFrom = step.getPickFrom(PickingJobStepPickFromKey.MAIN);

			I_M_Picking_Job_Step stepRecord = existingRecords.remove(step.getId());
			if (stepRecord == null)
			{
				stepRecord = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step.class);
				stepRecord.setM_Picking_Job_Step_ID(step.getId().getRepoId());
				stepRecord.setM_Picking_Job_ID(pickingJobId.getRepoId());
				stepRecord.setM_Picking_Job_Line_ID(pickingJobLineId.getRepoId());
				stepRecord.setIsDynamic(step.isGeneratedOnFly());
				stepRecord.setAD_Org_ID(orgId.getRepoId());
				stepRecord.setM_ShipmentSchedule_ID(step.getShipmentScheduleId().getRepoId());
				stepRecord.setC_Order_ID(step.getSalesOrderAndLineId().getOrderRepoId());
				stepRecord.setC_OrderLine_ID(step.getSalesOrderAndLineId().getOrderLineRepoId());

				//
				// What?
				stepRecord.setM_Product_ID(step.getProductId().getRepoId());
				stepRecord.setC_UOM_ID(step.getQtyToPick().getUomId().getRepoId());
				stepRecord.setQtyToPick(step.getQtyToPick().toBigDecimal());
			}

			updateRecord(stepRecord, step, docStatus);
			InterfaceWrapperHelper.save(stepRecord);

			saveStepPickFromAlternatives(step, pickingJobId);

			saveStepPickedTo(
					mainPickFrom.getPickedTo(),
					pickingJobId,
					step.getId(),
					null);
		}

		deleteStepsCascade(existingRecords.values());
	}

	private void saveStepPickFromAlternatives(
			@NonNull final PickingJobStep step,
			@NonNull final PickingJobId pickingJobId)
	{
		final ImmutableMap<PickingJobStepPickFromKey, I_M_Picking_Job_Step_HUAlternative> records = Maps.uniqueIndex(
				pickingJobStepAlternatives.get(step.getId()),
				record -> PickingJobStepPickFromKey.alternative(extractAlternativeId(record)));

		for (final PickingJobStepPickFromKey pickFromKey : step.getPickFromKeys())
		{
			if (pickFromKey.isAlternative())
			{
				final PickingJobStepPickFrom pickFrom = step.getPickFrom(pickFromKey);
				final I_M_Picking_Job_Step_HUAlternative record = Objects.requireNonNull(records.get(pickFromKey));

				updateRecord(record, pickFrom);
				InterfaceWrapperHelper.save(record);

				saveStepPickedTo(
						pickFrom.getPickedTo(),
						pickingJobId,
						step.getId(),
						pickFromKey.getAlternativeId());
			}
		}
	}

	private void saveStepPickedTo(
			@Nullable final PickingJobStepPickedTo pickedTo,
			@NonNull final PickingJobId pickingJobId,
			@NonNull final PickingJobStepId pickingStepId,
			@Nullable final PickingJobPickFromAlternativeId alternativeId)
	{
		final HashMap<HuId, I_M_Picking_Job_Step_PickedHU> existingRecordsByPickedHUId = pickedHUs.get(pickingStepId)
				.stream()
				.filter(record -> PickingJobPickFromAlternativeId.equals(extractAlternativeId(record), alternativeId))
				.collect(GuavaCollectors.toHashMapByKey(record -> HuId.ofRepoId(record.getPicked_HU_ID())));

		if (pickedTo != null)
		{
			for (final PickingJobStepPickedToHU pickedHU : pickedTo.getActualPickedHUs())
			{
				I_M_Picking_Job_Step_PickedHU record = existingRecordsByPickedHUId.remove(pickedHU.getActualPickedHUId());
				if (record == null)
				{
					record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step_PickedHU.class);
					record.setM_Picking_Job_ID(pickingJobId.getRepoId());
					record.setM_Picking_Job_Step_ID(pickingStepId.getRepoId());
					record.setM_Picking_Job_HUAlternative_ID(PickingJobPickFromAlternativeId.toRepoId(alternativeId));
				}

				record.setPickFrom_HU_ID(pickedHU.getPickFromHUId().getRepoId());
				record.setPicked_HU_ID(pickedHU.getActualPickedHUId().getRepoId());
				record.setC_UOM_ID(pickedHU.getQtyPicked().getUomId().getRepoId());
				record.setQtyPicked(pickedHU.getQtyPicked().toBigDecimal());
				record.setM_Picking_Candidate_ID(pickedHU.getPickingCandidateId().getRepoId());
				InterfaceWrapperHelper.save(record);
			}
		}

		InterfaceWrapperHelper.deleteAll(existingRecordsByPickedHUId.values());
	}

	private void deleteStepsCascade(final Collection<I_M_Picking_Job_Step> steps)
	{
		if (steps.isEmpty())
		{
			return;
		}

		final ImmutableSet<PickingJobStepId> pickingJobStepIds = steps.stream().map(step -> PickingJobStepId.ofRepoId(step.getM_Picking_Job_Step_ID())).collect(ImmutableSet.toImmutableSet());

		pickingJobStepIds.forEach(pickingJobStepId -> {
			InterfaceWrapperHelper.deleteAll(pickingJobStepAlternatives.removeAll(pickingJobStepId));
			InterfaceWrapperHelper.deleteAll(pickedHUs.removeAll(pickingJobStepId));
		});

		InterfaceWrapperHelper.deleteAll(steps);
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

	private void loadRecordsFromDB(final Set<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return;
		}

		final List<I_M_Picking_Job> records = InterfaceWrapperHelper.loadByRepoIdAwares(pickingJobIds, I_M_Picking_Job.class);

		records.forEach(record -> pickingJobs.put(PickingJobId.ofRepoId(record.getM_Picking_Job_ID()), record));

		queryBL.createQueryBuilder(I_M_Picking_Job_HUAlternative.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_HUAlternative.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(alt -> pickingJobHUAlternatives.put(PickingJobId.ofRepoId(alt.getM_Picking_Job_ID()), alt));

		queryBL.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_Line.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(line -> pickingJobLines.put(PickingJobId.ofRepoId(line.getM_Picking_Job_ID()), line));

		queryBL.createQueryBuilder(I_M_Picking_Job_Step.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_Step.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(step -> pickingJobSteps.put(PickingJobLineId.ofRepoId(step.getM_Picking_Job_Line_ID()), step));

		queryBL.createQueryBuilder(I_M_Picking_Job_Step_HUAlternative.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_Step_HUAlternative.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(stepAlt -> pickingJobStepAlternatives.put(extractPickingJobStepId(stepAlt), stepAlt));

		queryBL.createQueryBuilder(I_M_Picking_Job_Step_PickedHU.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Job_Step_PickedHU.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create()
				.stream()
				.forEach(pickedHU -> pickedHUs.put(PickingJobStepId.ofRepoId(pickedHU.getM_Picking_Job_Step_ID()), pickedHU));

		final PickingJobLoaderSupportingServices loadingSupportingServices = loadingSupportingServicesOrNull();
		if (loadingSupportingServices != null)
		{
			final ImmutableSet<OrderId> salesOrderIds = records.stream().map(PickingJobLoaderAndSaver::extractSalesOrderId).collect(ImmutableSet.toImmutableSet());
			loadingSupportingServices.warmUpSalesOrderDocumentNosCache(salesOrderIds);

			final ImmutableSet<BPartnerId> customerIds = records.stream().map(record -> extractDeliveryBPLocationId(record).getBpartnerId()).collect(ImmutableSet.toImmutableSet());
			loadingSupportingServices.warmUpBPartnerNamesCache(customerIds);

			hasLocks.putAll(computePickingJobHasLocks(pickingJobIds));
		}
	}

	@NonNull
	private PickingJobLoaderSupportingServices loadingSupportingServices()
	{
		if (_loadingSupportingServices == null)
		{
			throw new AdempiereException("No loadingSupportingServices available when saving");
		}
		return _loadingSupportingServices;
	}

	private PickingJobLoaderSupportingServices loadingSupportingServicesOrNull()
	{
		return _loadingSupportingServices;
	}

	private PickingJob loadJob(final I_M_Picking_Job record)
	{
		final Optional<PickingSlotIdAndCaption> pickingSlot = Optional.ofNullable(PickingSlotId.ofRepoIdOrNull(record.getM_PickingSlot_ID()))
				.map(loadingSupportingServices()::getPickingSlotIdAndCaption);

		final PickingJobId pickingJobId = PickingJobId.ofRepoId(record.getM_Picking_Job_ID());

		return PickingJob.builder()
				.id(pickingJobId)
				.header(toPickingJobHeader(record))
				.pickingSlot(pickingSlot)
				.docStatus(PickingJobDocStatus.ofCode(record.getDocStatus()))
				.isReadyToReview(record.isReadyToReview())
				.isApproved(record.isApproved())
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
		final PickingJobLoaderSupportingServices loadingSupportingServices = loadingSupportingServices();

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
				.isPickingReviewRequired(record.isPickingReviewRequired())
				.handoverLocationId(BPartnerLocationId.ofRepoIdOrNull(record.getHandover_Partner_ID(), record.getHandover_Location_ID()))
				.build();
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

	private static void updateRecord(final I_M_Picking_Job record, final PickingJob from)
	{
		record.setPicking_User_ID(UserId.toRepoId(from.getLockedBy()));
		record.setM_PickingSlot_ID(from.getPickingSlotId().map(PickingSlotId::getRepoId).orElse(-1));
		record.setDocStatus(from.getDocStatus().getCode());
		record.setProcessed(from.getDocStatus().isProcessed());
		record.setIsReadyToReview(from.isReadyToReview());
		record.setIsApproved(from.isApproved());
	}

	private PickingJobLine loadLine(@NonNull final I_M_Picking_Job_Line record)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = loadingSupportingServices();

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final PickingJobLineId pickingJobLineId = PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID());

		final HUPIItemProductId huPIItemProductId = HUPIItemProductId.ofRepoIdOrNone(record.getM_HU_PI_Item_Product_ID());

		return PickingJobLine.builder()
				.id(pickingJobLineId)
				.productId(productId)
				.productNo(loadingSupportingServices.getProductNo(productId))
				.productName(loadingSupportingServices.getProductName(productId))
				.packingInfo(loadingSupportingServices.getPackingInfo(huPIItemProductId))
				.qtyToPick(Quantitys.of(record.getQtyToPick(), UomId.ofRepoId(record.getC_UOM_ID())))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.catchUomId(UomId.ofRepoIdOrNull(record.getCatch_UOM_ID()))
				.steps(pickingJobSteps.get(pickingJobLineId)
						.stream()
						.map(this::loadStep)
						.collect(ImmutableList.toImmutableList()))
				.isManuallyClosed(record.isManuallyClosed())
				.build();
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Line record,
			@NonNull final PickingJobLine line,
			@NonNull final PickingJobDocStatus docStatus)
	{
		final boolean isManuallyClosed = line.isManuallyClosed();
		record.setIsManuallyClosed(isManuallyClosed);

		record.setProcessed(isManuallyClosed || docStatus.isProcessed());
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
				.map(alternativeHU -> loadPickFrom(productId, alternativeHU))
				.forEach(pickFroms::add);

		return PickingJobStep.builder()
				.id(pickingJobStepId)
				.isGeneratedOnFly(record.isDynamic())
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				//
				// What?
				.productId(productId)
				.productName(loadingSupportingServices().getProductName(productId))
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
						.caption(loadingSupportingServices().getLocatorName(locatorId))
						.build())
				.pickFromHU(HUInfo.builder()
						.id(pickFromHUId)
						.qrCode(getQRCode(pickFromHUId))
						.build())
				.pickedTo(loadPickedTo(record))
				.build();
	}

	private PickingJobStepPickFrom loadPickFrom(
			final ProductId pickedProductId,
			final I_M_Picking_Job_Step_HUAlternative record)
	{
		final PickingJobPickFromAlternativeId alternativeId = extractAlternativeId(record);

		final LocatorId pickFromLocatorId = LocatorId.ofRepoId(record.getPickFrom_Warehouse_ID(), record.getPickFrom_Locator_ID());
		final HuId pickFromHUId = HuId.ofRepoId(record.getPickFrom_HU_ID());

		return PickingJobStepPickFrom.builder()
				.pickFromKey(PickingJobStepPickFromKey.alternative(alternativeId))
				.pickFromLocator(LocatorInfo.builder()
						.id(pickFromLocatorId)
						.caption(loadingSupportingServices().getLocatorName(pickFromLocatorId))
						.build())
				.pickFromHU(HUInfo.builder()
						.id(pickFromHUId)
						.qrCode(getQRCode(pickFromHUId))
						.build())
				.pickedTo(loadPickedTo(pickedProductId, record))
				.build();
	}

	private HUQRCode getQRCode(final HuId huId)
	{
		return loadingSupportingServices().getQRCodeByHUId(huId);
	}

	@NonNull
	private static PickingJobPickFromAlternativeId extractAlternativeId(final I_M_Picking_Job_Step_HUAlternative record)
	{
		return PickingJobPickFromAlternativeId.ofRepoId(record.getM_Picking_Job_HUAlternative_ID());
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
					.productId(ProductId.ofRepoId(record.getM_Product_ID()))
					.build();
		}
		else
		{
			return null;
		}
	}

	private static Optional<QtyRejectedWithReason> extractQtyRejected(final I_M_Picking_Job_Step record)
	{
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

	@Nullable
	private PickingJobPickFromAlternativeId extractAlternativeId(final I_M_Picking_Job_Step_PickedHU record)
	{
		return PickingJobPickFromAlternativeId.ofRepoIdOrNull(record.getM_Picking_Job_HUAlternative_ID());
	}

	private PickingJobStepPickedToHU loadPickedToHU(final I_M_Picking_Job_Step_PickedHU record)
	{
		return PickingJobStepPickedToHU.builder()
				.pickFromHUId(HuId.ofRepoId(record.getPickFrom_HU_ID()))
				.actualPickedHUId(HuId.ofRepoId(record.getPicked_HU_ID()))
				.qtyPicked(Quantitys.of(record.getQtyPicked(), UomId.ofRepoId(record.getC_UOM_ID())))
				.pickingCandidateId(PickingCandidateId.ofRepoId(record.getM_Picking_Candidate_ID()))
				.build();
	}

	@Nullable
	private PickingJobStepPickedTo loadPickedTo(
			@NonNull final ProductId pickedProductId,
			@NonNull final I_M_Picking_Job_Step_HUAlternative record)
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
					.productId(pickedProductId)
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
		final UomId uomId = UomId.ofRepoIdOrNull(record.getC_UOM_ID());
		final QtyRejectedReasonCode reasonCode = QtyRejectedReasonCode.ofNullableCode(record.getRejectReason()).orElse(null);
		return reasonCode != null && uomId != null
				? Optional.of(QtyRejectedWithReason.of(Quantitys.of(record.getQtyRejectedToPick(), uomId), reasonCode))
				: Optional.empty();
	}

	@NonNull
	private static PickingJobStepId extractPickingJobStepId(final I_M_Picking_Job_Step_HUAlternative record)
	{
		return PickingJobStepId.ofRepoId(record.getM_Picking_Job_Step_ID());
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Step record,
			@NonNull final PickingJobStep from,
			@NonNull final PickingJobDocStatus docStatus)
	{

		record.setProcessed(docStatus.isProcessed());

		updateRecord(record, from.getPickFrom(PickingJobStepPickFromKey.MAIN));
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Step record,
			@NonNull final PickingJobStepPickFrom mainPickFrom)
	{
		record.setPickFrom_Warehouse_ID(mainPickFrom.getPickFromWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(mainPickFrom.getPickFromLocatorId().getRepoId());
		record.setPickFrom_HU_ID(mainPickFrom.getPickFromHUId().getRepoId());
		updateRecord(record, mainPickFrom.getPickedTo());
	}

	private static void updateRecord(
			@NonNull final I_M_Picking_Job_Step record,
			@Nullable final PickingJobStepPickedTo pickedTo)
	{
		final BigDecimal qtyRejectedBD;
		final String rejectReason;
		if (pickedTo != null)
		{
			qtyRejectedBD = pickedTo.getQtyRejected() != null ? pickedTo.getQtyRejected().toBigDecimal() : BigDecimal.ZERO;
			rejectReason = pickedTo.getQtyRejected() != null ? pickedTo.getQtyRejected().getReasonCode().getCode() : null;
		}
		else
		{
			qtyRejectedBD = BigDecimal.ZERO;
			rejectReason = null;
		}

		record.setQtyRejectedToPick(qtyRejectedBD);
		record.setRejectReason(rejectReason);
	}

	private static void updateRecord(final I_M_Picking_Job_Step_HUAlternative existingRecord, final PickingJobStepPickFrom pickFrom)
	{
		existingRecord.setPickFrom_HU_ID(pickFrom.getPickFromHUId().getRepoId());
		updateRecord(existingRecord, pickFrom.getPickedTo());
	}

	private static void updateRecord(final I_M_Picking_Job_Step_HUAlternative existingRecord, final PickingJobStepPickedTo pickedTo)
	{
		final UomId uomId;
		final BigDecimal qtyRejectedBD;
		final String rejectReason;
		if (pickedTo != null && pickedTo.getQtyRejected() != null)
		{
			final QtyRejectedWithReason qtyRejected = pickedTo.getQtyRejected();
			uomId = qtyRejected.toQuantity().getUomId();
			qtyRejectedBD = qtyRejected.toBigDecimal();
			rejectReason = qtyRejected.getReasonCode().getCode();
		}
		else
		{
			uomId = null;
			qtyRejectedBD = BigDecimal.ZERO;
			rejectReason = null;
		}

		existingRecord.setC_UOM_ID(UomId.toRepoId(uomId));
		existingRecord.setQtyRejectedToPick(qtyRejectedBD);
		existingRecord.setRejectReason(rejectReason);
	}

	private PickingJobPickFromAlternative loadPickFromAlternative(final I_M_Picking_Job_HUAlternative record)
	{
		final LocatorId locatorId = LocatorId.ofRepoId(record.getPickFrom_Warehouse_ID(), record.getPickFrom_Locator_ID());
		final HuId pickFromHUId = HuId.ofRepoId(record.getPickFrom_HU_ID());

		return PickingJobPickFromAlternative.builder()
				.id(PickingJobPickFromAlternativeId.ofRepoId(record.getM_Picking_Job_HUAlternative_ID()))
				.locatorInfo(LocatorInfo.builder()
						.id(locatorId)
						.caption(loadingSupportingServices().getLocatorName(locatorId))
						.build())
				.pickFromHU(HUInfo.builder()
						.id(pickFromHUId)
						.qrCode(getQRCode(pickFromHUId))
						.build())
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

		final SetMultimap<ShipmentScheduleId, ExistingLockInfo> existingLocks = loadingSupportingServices().getLocks(shipmentScheduleIdsByPickingJobId.values());

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
}
