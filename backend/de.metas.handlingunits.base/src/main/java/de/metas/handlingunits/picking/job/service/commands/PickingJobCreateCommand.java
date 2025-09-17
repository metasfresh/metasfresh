package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.handlingunits.picking.job.model.ScheduledPackageableList;
import de.metas.handlingunits.picking.job.repository.PickingJobCreateRepoRequest;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.picking.plan.generator.CreatePickingPlanRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.handlingunits.picking.plan.model.PickingPlan;
import de.metas.handlingunits.picking.plan.model.PickingPlanLine;
import de.metas.handlingunits.picking.plan.model.PickingPlanLineType;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static de.metas.common.util.time.SystemTime.asZonedDateTime;

@Builder
public class PickingJobCreateCommand
{
	private static final AdMessageKey MSG_NotAllItemsAreAvailableToBePicked = AdMessageKey.of("PickingJobCreateCommand.notAllItemsAreAvailableToBePicked");
	private static final AdMessageKey MORE_THAN_ONE_JOB_ERROR_MSG = AdMessageKey.of("PickingJobCreateCommand.MORE_THAN_ONE_JOB_ERROR_MSG");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	private final PickingJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepo;
	@NonNull private final PickingJobSlotService pickingJobSlotService;
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;

	@NonNull private final PickingJobCreateRequest request;

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		trxManager.assertThreadInheritedTrxExists();

		final ScheduledPackageableList items = getItemsToPick();

		final ShipmentScheduleAndJobScheduleIdSet scheduleIds = items.getScheduleIds();
		pickingJobLockService.lockSchedules(scheduleIds, request.getPickerId());
		try
		{
			final PickingJobHeaderKey headerKey = extractPickingJobHeaderKey(items);

			final PickingJob pickingJob = pickingJobRepository.createNewAndGet(
					PickingJobCreateRepoRequest.builder()
							.aggregationType(request.getAggregationType())
							.orgId(headerKey.getOrgId())
							.salesOrderId(headerKey.getSalesOrderId())
							.preparationDate(headerKey.getPreparationDate())
							.deliveryDate(headerKey.getDeliveryDate())
							.deliveryBPLocationId(headerKey.getDeliveryBPLocationId())
							.deliveryRenderedAddress(headerKey.getDeliveryRenderedAddress())
							.pickerId(request.getPickerId())
							.isAllowPickingAnyHU(request.isAllowPickingAnyHU())
							.lines(createLinesRequests(items))
							.handoverLocationId(headerKey.getHandoverLocationId())
							.build(),
					loadingSupportServices);

			pickingJobHUReservationService.reservePickFromHUs(pickingJob);

			return allocatePickingSlotIfPossible(pickingJob);
		}
		catch (final Exception createJobException)
		{
			try
			{
				pickingJobLockService.unlockSchedules(scheduleIds, request.getPickerId());
			}
			catch (final Exception unlockException)
			{
				createJobException.addSuppressed(unlockException);
			}

			throw AdempiereException.wrapIfNeeded(createJobException);
		}
	}

	@NonNull
	private PickingJob allocatePickingSlotIfPossible(@NonNull final PickingJob pickingJob)
	{
		final PickingSlotId pickingSlotId = workplaceService.getWorkplaceByUserId(request.getPickerId())
				.map(Workplace::getPickingSlotId)
				.orElse(null);

		if (pickingSlotId == null)
		{
			return pickingJob;
		}

		return PickingJobAllocatePickingSlotCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingSlotService(pickingJobSlotService)
				//
				.pickingJob(pickingJob)
				.pickingSlotId(pickingSlotId)
				.failIfNotAllocated(false)
				//
				.build().execute();
	}

	private ScheduledPackageableList getItemsToPick()
	{
		final Set<PickingJobScheduleId> jobScheduleIds = request.getScheduleIds() != null ? request.getScheduleIds().getJobScheduleIds() : ImmutableSet.of();
		final ImmutableListMultimap<ShipmentScheduleId, PickingJobSchedule> jobSchedulesMap = Multimaps.index(
				pickingJobScheduleService.getByIds(jobScheduleIds),
				PickingJobSchedule::getShipmentScheduleId
		);

		final PackageableQuery query = toPackageableQuery(request);
		final ScheduledPackageableList items = packagingDAO.stream(query)
				.flatMap(packageable -> toScheduledPackageables(packageable, jobSchedulesMap))
				.collect(ScheduledPackageableList.collect());
		if (items.isEmpty())
		{
			throw new AdempiereException(MSG_NotAllItemsAreAvailableToBePicked)
					.markAsUserValidationError()
					.setParameter("detail", "Absolutely nothing to pick")
					.setParameter("query", query);
		}

		return items;
	}

	private static Stream<ScheduledPackageable> toScheduledPackageables(
			@NonNull final Packageable packageable,
			final ImmutableListMultimap<ShipmentScheduleId, PickingJobSchedule> jobSchedulesMap)
	{
		final ImmutableList<PickingJobSchedule> jobSchedules = jobSchedulesMap.get(packageable.getShipmentScheduleId());
		if (jobSchedules.isEmpty())
		{
			return Stream.of(ScheduledPackageable.ofPackageable(packageable));
		}
		else
		{
			return jobSchedules.stream().map(jobSchedule -> ScheduledPackageable.of(packageable, jobSchedule));
		}
	}

	private static PackageableQuery toPackageableQuery(@NonNull final PickingJobCreateRequest request)
	{
		final ZonedDateTime zonedDateTime = asZonedDateTime();

		return PackageableQuery.builder()
				.onlyFromSalesOrder(true)
				.salesOrderId(request.getSalesOrderId())
				.deliveryBPLocationId(request.getDeliveryBPLocationId())
				.warehouseTypeId(request.getWarehouseTypeId())
				.onlyShipmentScheduleIds(request.getScheduleIds() != null ? request.getScheduleIds().getShipmentScheduleIds() : null)
				.maximumFixedPreparationDate(zonedDateTime)
				.maximumFixedPromisedDate(zonedDateTime)
				.build();
	}

	@Value
	@Builder
	private static class PickingJobHeaderKey
	{
		@NonNull OrgId orgId;
		@Nullable OrderId salesOrderId;
		@Nullable InstantAndOrgId preparationDate;
		@Nullable InstantAndOrgId deliveryDate;
		@Nullable BPartnerLocationId deliveryBPLocationId;
		@Nullable String deliveryRenderedAddress;
		@Nullable BPartnerLocationId handoverLocationId;
	}

	private PickingJobHeaderKey extractPickingJobHeaderKey(@NonNull final ScheduledPackageableList items)
	{
		return PickingJobHeaderKey.builder()
				.orgId(items.getSingleOrgId())
				.salesOrderId(items.getSingleSalesOrderId().orElse(null))
				.preparationDate(items.getSinglePreparationDate().orElse(null))
				.deliveryDate(items.getSingleDeliveryDate().orElse(null))
				.deliveryBPLocationId(items.getSingleCustomerLocationId().orElse(null))
				.deliveryRenderedAddress(items.getSingleCustomerAddress().orElse(null))
				.handoverLocationId(items.getSingleHandoverLocationId().orElse(null))
				.build();
	}

	private ImmutableList<PickingJobCreateRepoRequest.Line> createLinesRequests(final ScheduledPackageableList items)
	{
		loadingSupportServices.warmUpCachesFrom(items);

		return items.groupBy(PickingJobCreateCommand::extractPickingJobLineKey)
				.map(this::createLineRequest)
				.collect(ImmutableList.toImmutableList());
	}

	@Value
	@Builder
	private static class PickingJobLineKey
	{
		@NonNull ShipmentScheduleAndJobScheduleId scheduleId;
	}

	private static PickingJobLineKey extractPickingJobLineKey(@NonNull final ScheduledPackageable item)
	{
		return PickingJobLineKey.builder()
				.scheduleId(item.getId())
				.build();
	}

	private PickingJobCreateRepoRequest.Line createLineRequest(@NonNull final ScheduledPackageableList items)
	{
		if (request.isAllowPickingAnyHU())
		{
			return createLineRequest_NoPickingPlan(items);
		}
		else
		{
			return createLineRequest_WithPickingPlan(items);
		}
	}

	private PickingJobCreateRepoRequest.Line createLineRequest_WithPickingPlan(final @NonNull ScheduledPackageableList items)
	{
		final PickingPlan plan = pickingCandidateService.createPlan(CreatePickingPlanRequest.builder()
				.packageables(items)
				.considerAttributes(pickingConfigRepo.getPickingConfig().isConsiderAttributes())
				.build());

		final ImmutableList<PickingPlanLine> lines = plan.getLines();
		if (lines.isEmpty())
		{
			throw new AdempiereException(MSG_NotAllItemsAreAvailableToBePicked)
					.markAsUserValidationError()
					.setParameter("detail", "Not all materials are available")
					.setParameter("items", items)
					.setParameter("plan", plan);
		}

		return PickingJobCreateRepoRequest.Line.builder()
				.productId(items.getSingleProductId())
				.huPIItemProductId(items.getSinglePackToHUPIItemProductId())
				.qtyToPick(plan.getQtyToPick())
				.salesOrderAndLineId(items.getSingleSalesOrderLineId())
				.deliveryBPLocationId(items.getSingleCustomerLocationId().orElseThrow(() -> new AdempiereException("No single customer location found for " + items)))
				.scheduleId(items.getSingleScheduleIdIfUnique().orElse(null))
				.catchWeightUomId(items.getSingleCatchWeightUomIdIfUnique().orElse(null))
				.steps(lines.stream()
						.map(this::createStepRequest)
						.collect(ImmutableList.toImmutableList()))
				.pickFromAlternatives(plan.getAlternatives()
						.stream()
						.map(alt -> PickingJobCreateRepoRequest.PickFromAlternative.of(alt.getLocatorId(), alt.getHuId(), alt.getAvailableQty()))
						.collect(ImmutableSet.toImmutableSet()))
				.build();
	}

	private static PickingJobCreateRepoRequest.Line createLineRequest_NoPickingPlan(final @NonNull ScheduledPackageableList items)
	{
		return PickingJobCreateRepoRequest.Line.builder()
				.productId(items.getSingleProductId())
				.huPIItemProductId(items.getSinglePackToHUPIItemProductId())
				.qtyToPick(items.getQtyToPick())
				.salesOrderAndLineId(items.getSingleSalesOrderLineId())
				.deliveryBPLocationId(items.getSingleCustomerLocationId().orElseThrow(() -> new AdempiereException("No single customer location found for " + items)))
				.scheduleId(items.getSingleScheduleIdIfUnique().orElseThrow(() -> new AdempiereException("No single schedule found for " + items)))
				.catchWeightUomId(items.getSingleCatchWeightUomIdIfUnique().orElse(null))
				.pickFromManufacturingOrderId(items.getSingleManufacturingOrderId().orElse(null))
				.build();
	}

	private PickingJobCreateRepoRequest.Step createStepRequest(@NonNull final PickingPlanLine planLine)
	{
		final PickingPlanLineType type = planLine.getType();
		switch (type)
		{
			case PICK_FROM_HU:
			{
				return createStepRequest_PickFromHU(planLine);
			}
			case UNALLOCABLE:
			{
				throw new AdempiereException(MSG_NotAllItemsAreAvailableToBePicked)
						.markAsUserValidationError()
						.setParameter("planLine", planLine);
			}
			default:
			{
				throw new AdempiereException("Line type not supported: " + planLine);
			}
		}
	}

	private PickingJobCreateRepoRequest.Step createStepRequest_PickFromHU(final @NonNull PickingPlanLine planLine)
	{
		final ProductId productId = planLine.getProductId();
		final Quantity qtyToPick = planLine.getQty();

		final PickFromHU pickFromHU = Objects.requireNonNull(planLine.getPickFromHU());

		final PickingJobCreateRepoRequest.StepPickFrom mainPickFrom = PickingJobCreateRepoRequest.StepPickFrom.builder()
				.pickFromLocatorId(pickFromHU.getLocatorId())
				.pickFromHUId(extractTopLevelCUIfNeeded(pickFromHU.getTopLevelHUId(), productId, qtyToPick))
				.build();

		final ImmutableSet<PickingJobCreateRepoRequest.StepPickFrom> pickFromAlternatives = pickFromHU.getAlternatives()
				.stream()
				.map(alt -> PickingJobCreateRepoRequest.StepPickFrom.builder()
						.pickFromLocatorId(alt.getLocatorId())
						.pickFromHUId(alt.getHuId())
						.build())
				.collect(ImmutableSet.toImmutableSet());

		return PickingJobCreateRepoRequest.Step.builder()
				.scheduleId(planLine.getSourceDocumentInfo().getScheduleId())
				.salesOrderLineId(Objects.requireNonNull(planLine.getSourceDocumentInfo().getSalesOrderLineId()))
				.productId(productId)
				.qtyToPick(qtyToPick)
				.mainPickFrom(mainPickFrom)
				.pickFromAlternatives(pickFromAlternatives)
				.packToSpec(planLine.getSourceDocumentInfo().getPackToSpec())
				.build();
	}

	/**
	 * If the given HU is a top level CU, and it has the storage quantity greater than the qty we have to pick,
	 * then split out a top level CU for the qty we have to pick.
	 * <p>
	 * Why we do this?
	 * We do this because when we reserve the HU/Qty, the reservation service is splitting out the reserved Qty and tries to keep the CU under the same HU,
	 * but in this case our CU is a top level one, so we will end up with a new top level CU which is not our <code>pickFromHUId</code>.
	 * The <code>pickFromHUId</code> will remain there but with qtyToPick less quantity which might not be enough for picking.
	 * <p>
	 * In this case we will deliberately split out a new CU, and we will use it for picking.
	 */
	private HuId extractTopLevelCUIfNeeded(
			@NonNull final HuId pickFromHUId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToPick)
	{
		final I_M_HU pickFromHU = handlingUnitsBL.getById(pickFromHUId);

		// Not a top level CU
		if (!handlingUnitsBL.isTopLevel(pickFromHU) || !handlingUnitsBL.isVirtual(pickFromHU))
		{
			return pickFromHUId;
		}

		final Quantity storageQty = handlingUnitsBL.getStorageFactory()
				.getStorage(pickFromHU)
				.getProductStorage(productId)
				.getQty(qtyToPick.getUOM());

		// Nothing to split
		if (storageQty.compareTo(qtyToPick) <= 0)
		{
			return pickFromHUId;
		}

		final I_M_HU extractedCU = HUTransformService.newInstance()
				.huToNewSingleCU(HUTransformService.HUsToNewCUsRequest.builder()
						.sourceHU(pickFromHU)
						.productId(productId)
						.qtyCU(qtyToPick)
						//.keepNewCUsUnderSameParent(true) // not needed, our HU is top level anyways
						.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED)
						.build());

		return HuId.ofRepoId(extractedCU.getM_HU_ID());
	}
}
