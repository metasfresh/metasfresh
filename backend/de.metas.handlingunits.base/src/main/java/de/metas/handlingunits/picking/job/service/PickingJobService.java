package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADRefList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.dao.ValueRestriction;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServicesFactory;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.commands.PickingJobAbortCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobAllocatePickingSlotCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCandidateRetrieveCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCompleteCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobPickCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobReopenCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobUnPickCommand;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.order.OrderId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PickingJobService
{
	public final static AdMessageKey PICKING_JOB_PROCESSED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.model.PICKING_JOB_PROCESSED_ERROR_MSG");
	private final static AdMessageKey JOB_ALREADY_ASSIGNED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.model.JOB_ALREADY_ASSIGNED_ERROR_MSG");

	@NonNull private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final PickingJobLoaderSupportingServicesFactory pickingJobLoaderSupportingServicesFactory;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepo;
	@NonNull private final IShipmentService shipmentService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HULabelService huLabelService;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository;

	@NonNull
	public PickingJob getById(final PickingJobId pickingJobId)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.getById(pickingJobId, loadingSupportingServices);
	}

	public List<PickingJob> getDraftJobsByPickerId(@NonNull final UserId pickerId)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.getDraftJobsByPickerId(ValueRestriction.equalsTo(pickerId), loadingSupportingServices);
	}

	public PickingJob createPickingJob(@NonNull final PickingJobCreateRequest request)
	{
		return PickingJobCreateCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingCandidateService(pickingCandidateService)
				.pickingJobSlotService(pickingSlotService)
				.pickingJobHUReservationService(pickingJobHUReservationService)
				.pickingConfigRepo(pickingConfigRepo)
				.loadingSupportServices(pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices())
				.workplaceService(workplaceService)
				//
				.request(request)
				//
				.build().execute();
	}

	public PickingJobCompleteCommand.PickingJobCompleteCommandBuilder prepareToComplete(@NonNull final PickingJob pickingJob)
	{
		return PickingJobCompleteCommand.builder()
				.configRepository(mobileUIPickingUserProfileRepository)
				.pickingJobService(this)
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingSlotService(pickingSlotService)
				.pickingJobHUReservationService(pickingJobHUReservationService)
				.shipmentService(shipmentService)
				//
				.pickingJob(pickingJob);
	}

	public PickingJob abort(@NonNull final PickingJob pickingJob)
	{
		return abort()
				.pickingJob(pickingJob)
				.build()
				.executeAndGetSingleResult();
	}

	public void abortAllByUserId(@NonNull final UserId userId)
	{
		final List<PickingJob> pickingJobs = getDraftJobsByPickerId(userId);
		if (pickingJobs.isEmpty())
		{
			return;
		}

		abort()
				.pickingJobs(pickingJobs)
				.build()
				.execute();
	}

	private PickingJobAbortCommand.PickingJobAbortCommandBuilder abort()
	{
		return PickingJobAbortCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingSlotService(pickingSlotService)
				.pickingJobHUReservationService(pickingJobHUReservationService)
				//.pickingCandidateService(pickingCandidateService)
				;
	}

	public void abortForSalesOrderId(@NonNull final OrderId salesOrderId)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		pickingJobRepository
				.getDraftBySalesOrderId(salesOrderId, loadingSupportingServices)
				.ifPresent(this::abort);
	}

	public void abortNotStartedForSalesOrderId(@NonNull final OrderId salesOrderId)
	{
		pickingJobRepository
				.getDraftBySalesOrderId(salesOrderId, pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices())
				.filter(PickingJob::isNothingPicked)
				.ifPresent(this::abort);
	}

	@NonNull
	public Stream<PickingJobReference> streamDraftPickingJobReferences(@NonNull final PickingJobReferenceQuery query)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.streamDraftPickingJobReferences(query, loadingSupportingServices);
	}

	public Stream<PickingJobCandidate> streamPickingJobCandidates(@NonNull final PickingJobQuery query)
	{
		return PickingJobCandidateRetrieveCommand.builder()
				.packagingDAO(packagingDAO)
				.configRepository(mobileUIPickingUserProfileRepository)
				//
				.query(query)
				//
				.build().execute().stream();
	}

	@NonNull
	public Stream<Packageable> streamPackageable(@NonNull final PickingJobQuery query)
	{
		return packagingDAO.stream(query.toPackageableQuery());
	}

	public ADRefList getQtyRejectedReasons()
	{
		return pickingCandidateService.getQtyRejectedReasons();
	}

	public PickingJob setPickFromHU(final @NonNull PickingJob pickingJob, final @NonNull HUInfo pickFromHU)
	{
		// TODO validate that pickFromHU is eligible pick from HU, i.e.
		// * not reserved
		// * contains at least one product that we have to pick

		final PickingJob changedPickingJob = pickingJob.withPickFromHU(pickFromHU);
		pickingJobRepository.save(changedPickingJob);
		return changedPickingJob;
	}

	public PickingJob allocateAndSetPickingSlot(
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingSlotQRCode pickingSlotQRCode)
	{
		return PickingJobAllocatePickingSlotCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingSlotService(pickingSlotService)
				//
				.pickingJob(pickingJob)
				.pickingSlotQRCode(pickingSlotQRCode)
				//
				.build().execute();
	}

	public PickingJob processStepEvents(
			@NonNull final PickingJob pickingJob0,
			@NonNull final List<PickingJobStepEvent> events)
	{
		PickingJob changedPickingJob = pickingJob0;
		for (final PickingJobStepEvent event : PickingJobStepEvent.removeDuplicates(events))
		{
			try
			{
				changedPickingJob = processStepEvent(changedPickingJob, event);
			}
			catch (final Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex)
						.setParameter("event", event);
			}
		}

		return changedPickingJob;
	}

	public PickingJob processStepEvent(
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingJobStepEvent event)
	{
		switch (event.getEventType())
		{
			case PICK:
			{
				return PickingJobPickCommand.builder()
						.pickingJobService(this)
						.pickingJobRepository(pickingJobRepository)
						.huQRCodesService(huQRCodesService)
						.inventoryService(inventoryService)
						.huReservationService(huReservationService)
						.pickingConfigRepo(pickingConfigRepo)
						.mobileUIPickingUserProfileRepository(mobileUIPickingUserProfileRepository)
						//
						.pickingJob(pickingJob)
						.pickingJobLineId(event.getPickingLineId())
						.pickingJobStepId(event.getPickingStepId())
						.pickFromKey(event.getPickFromKey())
						.pickFromHUQRCode(event.getHuQRCode())
						.qtyToPickBD(Objects.requireNonNull(event.getQtyPicked()))
						.isPickWholeTU(event.isPickWholeTU())
						.checkIfAlreadyPacked(event.isCheckIfAlreadyPacked())
						.createInventoryForMissingQty(true)
						.qtyRejectedBD(event.getQtyRejected())
						.qtyRejectedReasonCode(event.getQtyRejectedReasonCode())
						.catchWeightBD(event.getCatchWeight())
						.isSetBestBeforeDate(event.isSetBestBeforeDate())
						.bestBeforeDate(event.getBestBeforeDate())
						.isSetLotNo(event.isSetLotNo())
						.lotNo(event.getLotNo())
						.isCloseTarget(event.isCloseTarget())
						//
						.build()
						.execute();
			}
			case UNPICK:
			{
				return PickingJobUnPickCommand.builder()
						.pickingJobRepository(pickingJobRepository)
						.pickingCandidateService(pickingCandidateService)
						.huQRCodesService(huQRCodesService)
						//
						.pickingJob(pickingJob)
						.lineId(event.getPickingLineId())
						.onlyPickingJobStepId(event.getPickingStepId())
						.onlyPickFromKey(event.getPickFromKey())
						.unpickToHU(event.getUnpickToTargetQRCode())
						//
						.build().execute();
			}
			default:
			{
				throw new AdempiereException("Unhandled event type: " + event);
			}
		}
	}

	public void unassignAllByUserId(@NonNull final UserId userId)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(() -> {
			for (final PickingJob job : getDraftJobsByPickerId(userId))
			{
				unassignPickingJob(job);
				pickingJobLockService.unlockShipmentSchedules(job);
			}
		});
	}

	/**
	 * @return true, if all picking jobs have been removed form the slot, false otherwise
	 */
	public boolean clearAssignmentsForSlot(@NonNull final PickingSlotId slotId, final boolean forceRemoveForOngoingJobs)
	{
		final List<PickingJob> pickingJobs = pickingJobRepository.getDraftedByPickingSlotId(slotId, pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices());
		if (pickingJobs.isEmpty())
		{
			return true;
		}

		return pickingJobs.stream().allMatch(job -> removePickingSlotAssignment(job, forceRemoveForOngoingJobs));
	}

	private void unassignPickingJob(@NonNull final PickingJob jobParam)
	{
		PickingJob job = jobParam;
		job.assertNotProcessed();

		//
		// Abort it if nothing picked
		if (job.isNothingPicked())
		{
			job = abort(job);
		}

		//
		// Unassign & release picking slot
		final PickingSlotId pickingSlotId = job.getPickingSlotId().orElse(null);
		if (pickingSlotId != null)
		{
			if (job.isNothingPicked())
			{
				job = job.withPickingSlot(null);
			}

			pickingSlotService.release(pickingSlotId, job.getId());
		}

		//
		// Unassign it from current user
		job = job.withLockedBy(null);

		pickingJobRepository.save(job);
	}

	public PickingJob assignPickingJob(@NonNull final PickingJobId pickingJobId, @NonNull final UserId newResponsibleId)
	{
		PickingJob job = getById(pickingJobId);
		if (job.getLockedBy() == null)
		{
			pickingJobLockService.lockShipmentSchedules(job.getShipmentScheduleIds(), newResponsibleId);

			job = job.withLockedBy(newResponsibleId);
			pickingJobRepository.save(job);
		}
		else if (!UserId.equals(job.getLockedBy(), newResponsibleId))
		{
			throw new AdempiereException(JOB_ALREADY_ASSIGNED_ERROR_MSG)
					.appendParametersToMessage()
					.setParameter("newResponsibleId", newResponsibleId)
					.setParameter("job", job);
		}

		return job;
	}

	private boolean removePickingSlotAssignment(
			@NonNull final PickingJob pickingJob,
			final boolean forceRemoveForOngoingPickingJob)
	{
		if (pickingJob.isNothingPicked())
		{
			final PickingJob abortedPickingJob = abort(pickingJob);
			Check.assume(!abortedPickingJob.getPickingSlotId().isPresent(), "Assuming the aborted picking job is no longer assigned to a picking slot.");
			return true;
		}
		else if (forceRemoveForOngoingPickingJob)
		{
			pickingJobRepository.save(pickingJob.withPickingSlot(null));
			return true;
		}
		else
		{
			return false;
		}
	}

	public PickingJob closeLine(final PickingJob pickingJob, final PickingJobLineId pickingLineId)
	{
		final PickingJob pickingJobChanged = pickingJob.withChangedLine(pickingLineId, line -> line.withManuallyClosed(true));
		if (Util.equals(pickingJob, pickingJobChanged))
		{
			return pickingJob;
		}

		pickingJobRepository.save(pickingJobChanged);
		return pickingJobChanged;
	}

	public PickingJob openLine(final PickingJob pickingJob, final PickingJobLineId pickingLineId)
	{
		final PickingJob pickingJobChanged = pickingJob.withChangedLine(pickingLineId, line -> line.withManuallyClosed(false));
		if (Util.equals(pickingJob, pickingJobChanged))
		{
			return pickingJob;
		}

		pickingJobRepository.save(pickingJobChanged);
		return pickingJobChanged;
	}

	@NonNull
	public List<LUPickingTarget> getLUAvailableTargets(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		return handlingUnitsBL.getLUPIs(getTUPIItems(pickingJob, lineId), pickingJob.getCustomerId())
				.stream()
				.map(PickingJobService::toPickingTarget)
				.collect(ImmutableList.toImmutableList());

	}

	@NonNull
	public List<TUPickingTarget> getTUAvailableTargets(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		final ImmutableList.Builder<TUPickingTarget> pickingTargetBuilder = ImmutableList.builder();
		Optional.ofNullable(handlingUnitsBL.retrievePIDefaultForPicking())
				.map(defaultPI -> TUPickingTarget.builder()
						.tuPIId(HuPackingInstructionsId.ofRepoId(defaultPI.getM_HU_PI_ID()))
						.caption(defaultPI.getName())
						.isDefaultPacking(true)
						.build())
				.ifPresent(pickingTargetBuilder::add);

		handlingUnitsBL.retrievePIInfo(getTUPIItems(pickingJob, lineId))
				.stream()
				.map(idAndCaption -> TUPickingTarget.ofPackingInstructions(idAndCaption.getId(), idAndCaption.getCaption()))
				.forEach(pickingTargetBuilder::add);

		return pickingTargetBuilder.build();
	}

	@NonNull
	private ImmutableSet<HuPackingInstructionsItemId> getTUPIItems(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		final ImmutableSet<ProductId> productIds;
		final BPartnerId customerId;
		if (lineId != null)
		{
			final PickingJobLine line = pickingJob.getLineById(lineId);
			productIds = ImmutableSet.of(line.getProductId());
			customerId = CoalesceUtil.coalesce(line.getCustomerId(), pickingJob.getCustomerId());
		}
		else
		{
			productIds = pickingJob.getProductIds();
			customerId = pickingJob.getCustomerId();
		}

		return huPIItemProductDAO.retrieveForProducts(productIds, customerId)
				.stream()
				.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_ID)
				.map(HuPackingInstructionsItemId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static LUPickingTarget toPickingTarget(@NonNull final HuPackingInstructionsIdAndCaption luPIAndCaption)
	{
		return LUPickingTarget.builder()
				.caption(luPIAndCaption.getCaption())
				.luPIId(luPIAndCaption.getId())
				.build();
	}

	public PickingJob setLUPickingTarget(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId,
			@Nullable final LUPickingTarget target)
	{
		final PickingJob pickingJobChanged = pickingJob.withLuPickingTarget(lineId, target);
		if (Util.equals(pickingJob, pickingJobChanged))
		{
			return pickingJob;
		}

		pickingJobRepository.save(pickingJobChanged);
		return pickingJobChanged;
	}

	public PickingJob setTUPickingTarget(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId,
			@Nullable final TUPickingTarget target)
	{
		final PickingJob pickingJobChanged = pickingJob.withTuPickingTarget(lineId, target);
		if (Util.equals(pickingJob, pickingJobChanged))
		{
			return pickingJob;
		}

		pickingJobRepository.save(pickingJobChanged);
		return pickingJobChanged;
	}

	public PickingJob closeAllLUPickingTargets(@NonNull final PickingJob pickingJob)
	{
		return closeLUPickingTargets(pickingJob, true, true, null);
	}

	public PickingJob closeLUPickingTarget(
			@NonNull final PickingJob pickingJob0,
			@Nullable final PickingJobLineId lineId)
	{
		final boolean isCloseOnHeader = lineId == null;
		final boolean isCloseOnLines = lineId != null;
		return closeLUPickingTargets(pickingJob0, isCloseOnHeader, isCloseOnLines, lineId);
	}

	private PickingJob closeLUPickingTargets(
			@NonNull final PickingJob pickingJob,
			boolean isCloseOnHeader,
			boolean isCloseOnLines,
			@Nullable PickingJobLineId onlyLineId)
	{
		final LinkedHashSet<HuId> closedLUIds = new LinkedHashSet<>();
		final PickingJob pickingJobChanged = pickingJob.withClosedLuPickingTargets(isCloseOnHeader, isCloseOnLines, onlyLineId, closedLUIds::add);

		if (!Util.equals(pickingJob, pickingJobChanged))
		{
			pickingJobRepository.save(pickingJobChanged);
		}

		printLULabels(closedLUIds);

		return pickingJobChanged;
	}

	private void printLULabels(@NonNull final Collection<HuId> luIds)
	{
		final List<I_M_HU> lus = handlingUnitsBL.getByIds(luIds);
		if (lus.isEmpty())
		{
			return;
		}

		huLabelService.print(HULabelPrintRequest.builder()
				.sourceDocType(HULabelSourceDocType.Picking)
				.hus(HUToReportWrapper.ofList(lus))
				.onlyIfAutoPrint(true)
				.failOnMissingLabelConfig(false)
				.build());
	}

	@NonNull
	public PickingJob closeTUPickingTarget(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		final TUPickingTarget pickingTarget = getTUPickingTarget(pickingJob, lineId).orElse(null);
		if (pickingTarget == null)
		{
			return pickingJob;
		}

		return setTUPickingTarget(pickingJob, lineId, null);
	}

	private Optional<TUPickingTarget> getTUPickingTarget(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		return pickingJob.getTuPickingTarget(lineId);
	}

	public void reopenPickingJobs(@NonNull final ReopenPickingJobRequest request)
	{
		final Map<ShipmentScheduleId, List<PickingJobId>> scheduleId2JobIds = pickingJobRepository
				.getPickingJobIdsByScheduleId(request.getShipmentScheduleIds());

		final PickingJobReopenCommand.PickingJobReopenCommandBuilder commandBuilder = PickingJobReopenCommand.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.pickingSlotService(pickingSlotService)
				.huContextFactory(huContextFactory)
				.huShipmentScheduleBL(huShipmentScheduleBL)
				.shipmentScheduleBL(shipmentScheduleBL)
				.pickingJobRepository(pickingJobRepository);

		scheduleId2JobIds.values().stream()
				.flatMap(List::stream)
				.collect(ImmutableSet.toImmutableSet())
				.stream()
				.map(this::getById)
				.filter(pickingJob -> pickingJob.getAllPickedHuIds()
						.stream()
						.anyMatch(huId -> request.getHuIds().contains(huId)))
				.map(job -> commandBuilder
						.jobToReopen(job)
						.huIdsToPick(request.getHuInfoList())
						.build())
				.forEach(PickingJobReopenCommand::execute);
	}
}
