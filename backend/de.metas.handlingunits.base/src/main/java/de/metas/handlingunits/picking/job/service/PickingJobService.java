package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADRefList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.dao.ValueRestriction;
import de.metas.document.location.DocumentLocation;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.grai.DummyGRAITemplate;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUIdsAndTopLevelTUIdsCollector;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobQtyAvailable;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingSlotSuggestions;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServicesFactory;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.commands.PickingJobAbortCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobAllocatePickingSlotCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCompleteCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobReopenCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobUnPickCommand;
import de.metas.handlingunits.picking.job.service.commands.get_next_eligible_line.GetNextEligibleLineToPackCommand;
import de.metas.handlingunits.picking.job.service.commands.get_next_eligible_line.GetNextEligibleLineToPackRequest;
import de.metas.handlingunits.picking.job.service.commands.get_next_eligible_line.GetNextEligibleLineToPackResponse;
import de.metas.handlingunits.picking.job.service.commands.get_qty_available.PickingJobGetQtyAvailableCommand;
import de.metas.handlingunits.picking.job.service.commands.pick.PickingJobPickCommand;
import de.metas.handlingunits.picking.job.service.commands.pick_all.PickingJobPickAllCommand;
import de.metas.handlingunits.picking.job.service.commands.retrieve.PickingJobCandidateRetrieveCommand;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.salesorder.PickingJobSalesOrderService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import de.metas.handlingunits.picking.job.shipment.PickingShipmentService;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.picking.requests.ReleasePickingSlotRequest;
import de.metas.handlingunits.picking.slot.PickingSlotListener;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.job_schedule.model.PickingJobScheduleCollection;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PickingJobService implements PickingSlotListener
{
	public final static AdMessageKey PICKING_JOB_PROCESSED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.model.PICKING_JOB_PROCESSED_ERROR_MSG");
	private final static AdMessageKey JOB_ALREADY_ASSIGNED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.model.JOB_ALREADY_ASSIGNED_ERROR_MSG");
	private final static AdMessageKey ONGOING_PICKING_JOBS_ERR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.ONGOING_PICKING_JOBS_ERR_MSG");

	@NonNull private final PickingJobBPartnerService bpartnerService;
	@NonNull private final PickingJobWarehouseService warehouseService;
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobShipmentScheduleService shipmentScheduleService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final PickingJobLoaderSupportingServicesFactory pickingJobLoaderSupportingServicesFactory;
	@NonNull private final PickingShipmentService shipmentService;
	@NonNull private final MobileUIPickingUserProfileService configService;
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final PickingJobGraiTargetService graiTargetService;
	@NonNull private final PickingJobSalesOrderService salesOrderService;

	@NonNull
	public PickingJob getById(final PickingJobId pickingJobId)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.getById(pickingJobId, loadingSupportingServices);
	}

	public PickingJob updateById(@NonNull final PickingJobId pickingJobId, @NonNull final UnaryOperator<PickingJob> updater)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.updateById(pickingJobId, loadingSupportingServices, updater);
	}

	public List<PickingJob> getDraftJobsByPickerId(@NonNull final UserId pickerId)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.getDraftJobsByPickerId(ValueRestriction.equalsTo(pickerId), loadingSupportingServices);
	}

	public PickingJob createPickingJob(@NonNull final PickingJobCreateRequest request)
	{
		return PickingJobCreateCommand.builder()
				.configService(configService)
				.shipmentScheduleService(shipmentScheduleService)
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingCandidateService(pickingCandidateService)
				.pickingJobSlotService(pickingSlotService)
				.huService(huService)
				.loadingSupportServices(pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices())
				.warehouseService(warehouseService)
				.pickingJobScheduleService(pickingJobScheduleService)
				//
				.request(request)
				//
				.build().execute();
	}

	public PickingJob complete(@NonNull final PickingJobId pickingJobId, @NonNull final UserId callerId)
	{
		final PickingJob pickingJob = getById(pickingJobId);
		pickingJob.assertCanBeEditedBy(callerId);
		return complete(pickingJob);
	}

	public PickingJob complete(@NonNull final PickingJob pickingJob)
	{
		return PickingJobCompleteCommand.builder()
				.configService(configService)
				.pickingJobService(this)
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingSlotService(pickingSlotService)
				.huService(huService)
				.shipmentService(shipmentService)
				.bpartnerService(bpartnerService)
				//
				.pickingJob(pickingJob)
				.execute();
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
				.huService(huService)
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
				.shipmentScheduleService(shipmentScheduleService)
				.configService(configService)
				.pickingJobScheduleService(pickingJobScheduleService)
				//
				.query(query)
				//
				.build().execute().stream();
	}

	@NonNull
	public Stream<Packageable> streamPackageable(@NonNull final PickingJobQuery query)
	{
		final Set<ShipmentScheduleId> onlyShipmentScheduleIds;
		if (query.isScheduledForWorkplaceOnly())
		{
			final PickingJobScheduleCollection jobSchedules = pickingJobScheduleService.list(query.toPickingJobScheduleQuery());
			if (jobSchedules.isEmpty())
			{
				return Stream.of();
			}

			onlyShipmentScheduleIds = jobSchedules.getShipmentScheduleIds();
		}
		else
		{
			onlyShipmentScheduleIds = null;
		}

		return shipmentScheduleService.stream(
				query.toPackageableQueryBuilder()
						.onlyShipmentScheduleIds(onlyShipmentScheduleIds)
						.build()
		);
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
				return newPickCommand()
						//
						.pickingJob(pickingJob)
						.pickingJobLineId(event.getPickingLineId())
						.pickingJobStepId(event.getPickingStepId())
						.pickFromKey(event.getPickFromKey())
						.pickFromQRCode(event.getQrCode())
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
						.isSetGrais(event.isSetGrais())
						.graiCodes(event.getGraiCodes())
						//
						.build()
						.execute();
			}
			case UNPICK:
			{
				return PickingJobUnPickCommand.builder()
						.shipmentScheduleService(shipmentScheduleService)
						.pickingJobRepository(pickingJobRepository)
						.pickingCandidateService(pickingCandidateService)
						.huService(huService)
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

	public PickingJobPickCommand.PickingJobPickCommandBuilder newPickCommand()
	{
		return PickingJobPickCommand.builder()
				.productService(productService)
				.bpartnerService(bpartnerService)
				.warehouseService(warehouseService)
				.shipmentScheduleService(shipmentScheduleService)
				.configService(configService)
				.pickingJobService(this)
				.pickingJobRepository(pickingJobRepository)
				.pickingSlotService(pickingSlotService)
				.huService(huService);
	}

	public void unassignAllByUserId(@NonNull final UserId userId)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(() -> {
			for (final PickingJob job : getDraftJobsByPickerId(userId))
			{
				unassignPickingJob(job);
				pickingJobLockService.unlockSchedules(job);
			}
		});
	}

	@Override
	public void beforeReleasePickingSlot(final @NonNull ReleasePickingSlotRequest request)
	{
		final boolean clearedAllPickingJobs = clearAssignmentsForSlot(request.getPickingSlotId(), request.isForceRemoveForOngoingJobs());
		if (!clearedAllPickingJobs)
		{
			throw new AdempiereException(ONGOING_PICKING_JOBS_ERR_MSG).markAsUserValidationError();
		}
	}

	/**
	 * @return true, if all picking jobs have been removed from the slot, false otherwise
	 */
	private boolean clearAssignmentsForSlot(@NonNull final PickingSlotId slotId, final boolean forceRemoveForOngoingJobs)
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
		// Unassign it from the current user
		job = job.withLockedBy(null);

		pickingJobRepository.save(job);
	}

	public PickingJob assignPickingJob(@NonNull final PickingJobId pickingJobId, @NonNull final UserId newResponsibleId)
	{
		PickingJob job = getById(pickingJobId);
		if (job.getLockedBy() == null)
		{
			pickingJobLockService.lockSchedules(job.getScheduleIds(), newResponsibleId);

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
		return huService.getLUPIs(getTUPIItems(pickingJob, lineId), pickingJob.getCustomerId())
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
		Optional.ofNullable(huService.retrievePIDefaultForPicking())
				.map(defaultPI -> TUPickingTarget.builder()
						.tuPIId(HuPackingInstructionsId.ofRepoId(defaultPI.getM_HU_PI_ID()))
						.caption(defaultPI.getName())
						.isDefaultPacking(true)
						.build())
				.ifPresent(pickingTargetBuilder::add);

		huService.retrievePIInfo(getTUPIItems(pickingJob, lineId))
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

		return huService.getPIItemProducts(productIds, customerId)
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

	/**
	 * GRAI-scan picking entry point (lazy path): resolves the TU type and capacity from the scanned GRAI,
	 * builds a new-TU {@link TUPickingTarget} carrying the parsed GRAI, and stores it on the line or at
	 * job/header level (when {@code lineId == null}).
	 * No physical HU is created here; the real TU is materialised later at first-pick time by the
	 * framework, and {@link de.metas.handlingunits.picking.job.service.commands.pick.PickingJobPickCommand}
	 * stamps the GRAI on it afterwards via {@link PickingJobHUService#setGrais}.
	 *
	 * @param lineId      the picking-job line being picked; used to resolve the line's product for capacity checks
	 *                    and the effective LU target for the TU-allowed-on-LU check.
	 *                    {@code null} → header-level (no-line) scan: ONLY the per-product capacity check is skipped
	 *                    (there is no single line product at header level). The TU-allowed-on-LU check still runs
	 *                    against the job-level LU target, since {@link PickingJob#getLuPickingTargetEffective}
	 *                    returns the job-level target when {@code lineId == null}; the TU target is stored at job level.
	 * @param scannedGrai the raw scanned GRAI barcode.
	 */
	public PickingJob createTUFromGRAI(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId,
			@NonNull final ScannedCode scannedGrai)
	{
		// Resolve the TU type from the GRAI, validated against the effective LU target (line-level if set, else
		// job-level). At header level (lineId == null) the effective LU target is the job-level one, so the
		// TU-allowed-on-LU check still runs; ONLY the per-product capacity check is skipped, because there
		// is no single line product at header level.
		final LUPickingTarget luTarget = pickingJob.getLuPickingTargetEffective(lineId).orElse(null);
		final ProductId lineProductId = (lineId != null) ? pickingJob.getLineById(lineId).getProductId() : null;
		final String poReference = resolvePOReferenceForGraiCheck(pickingJob, lineId);
		final GraiTuResolution resolved = graiTargetService.resolveTuTypeAndCapacity(
				scannedGrai,
				luTarget,
				lineProductId,
				poReference);

		final GRAI grai = resolved.getGrai();
		final HuPackingInstructionsId tuPIId = resolved.getTuPIId();
		final I_M_HU_PI tuPI = huService.getPI(tuPIId);

		huService.assertTUTypeSupportsGraiAttribute(tuPIId, tuPI);

		final TUPickingTarget tuTarget = TUPickingTarget.ofPackingInstructions(tuPIId, tuPI.getName(), grai);

		return setTUPickingTarget(pickingJob, lineId, tuTarget);
	}

	/**
	 * Resolves the current sales order's PO reference for the Migros GRAI-ownership check
	 * ({@link PickingJobGraiTargetService#resolveTuTypeAndCapacity}), via the {@code external/salesorder} facade.
	 *
	 * @return {@code null} when no sales order can be unambiguously resolved (a header-level scan of a job whose
	 * lines span more than one sales order), or when the resolved order has no PO reference set. A {@code null}
	 * result means the Migros-ownership check is skipped, not failed.
	 */
	@Nullable
	private String resolvePOReferenceForGraiCheck(@NonNull final PickingJob pickingJob, @Nullable final PickingJobLineId lineId)
	{
		final OrderId salesOrderId = resolveSalesOrderIdForGraiCheck(pickingJob, lineId);
		return salesOrderId != null ? salesOrderService.getPOReferenceById(salesOrderId) : null;
	}

	@Nullable
	private static OrderId resolveSalesOrderIdForGraiCheck(@NonNull final PickingJob pickingJob, @Nullable final PickingJobLineId lineId)
	{
		if (lineId != null)
		{
			return pickingJob.getLineById(lineId).getSalesOrderAndLineId().getOrderId();
		}

		// Header-level scan: only unambiguous when every line of the job belongs to the same sales order
		// (always true for SALES_ORDER-aggregated jobs; a DELIVERY_LOCATION job can span several orders).
		final ImmutableSet<OrderId> salesOrderIds = pickingJob.streamLines()
				.map(line -> line.getSalesOrderAndLineId().getOrderId())
				.collect(ImmutableSet.toImmutableSet());
		return salesOrderIds.size() == 1 ? salesOrderIds.iterator().next() : null;
	}

	/**
	 * @return {@code true} if the GRAI-scan TU-target flow is enabled for the given customer;
	 * {@code false} if {@code customerId} is {@code null} or the customer has GRAI scanning disabled.
	 */
	public boolean isGraiScanEnabled(@Nullable final BPartnerId customerId)
	{
		return !getGRAIRequired(customerId).isNo();
	}

	@NonNull
	private GRAIRequired getGRAIRequired(@Nullable final BPartnerId customerId)
	{
		if (customerId == null)
		{
			return GRAIRequired.No;
		}
		return bpartnerService.getGRAIRequired(customerId);
	}

	/**
	 * @return the GRAIs already assigned to the line's effective loading unit (from prior picks on this LU), so the
	 * mobile capture panel can mirror the server-side LU-wide dedupe. Resolves the effective LU the same way
	 * {@link de.metas.handlingunits.picking.job.service.commands.pick.PickingJobPickCommand#stampGraisIfRequired}
	 * does. Empty when no LU is resolved yet for the line (nothing to stamp against yet).
	 */
	@NonNull
	public List<GRAI> getExistingLuGrais(@NonNull final PickingJob pickingJob, @Nullable final PickingJobLineId lineId)
	{
		final HuId pickedLuId = pickingJob.getLuPickingTargetEffective(lineId)
				.filter(LUPickingTarget::isExistingLU)
				.map(LUPickingTarget::getLuIdNotNull)
				.orElse(null);

		if (pickedLuId == null)
		{
			return ImmutableList.of();
		}

		final GRAISet existingGrais = huService.getGrais(pickedLuId);
		return ImmutableList.copyOf(existingGrais);
	}

	/**
	 * Validates the dummy-GRAI prerequisites for a sales order whose customer is in
	 * {@link GRAIRequired#YesWithDummyGRAIs} mode: the order's PO reference must be able to form a valid
	 * dummy-GRAI serial prefix (present after trim, max 10 characters). Throws the translated, operator-facing
	 * message otherwise. No-op for any other GRAI mode.
	 * <p>
	 * The GRAI mode is resolved from the order's customer — the same source the picking-completion backstop
	 * uses — so this early validation predicts (and stays consistent with) the completion-time check.
	 */
	public void assertDummyGRAIPrerequisitesForSalesOrder(
			@NonNull final OrderId salesOrderId,
			@Nullable final BPartnerId customerId,
			@Nullable final String poReference)
	{
		if (getGRAIRequired(customerId) != GRAIRequired.YesWithDummyGRAIs)
		{
			return;
		}

		final String serialPrefix = StringUtils.trimBlankToNull(poReference);
		if (serialPrefix == null)
		{
			throw new AdempiereException(DummyGRAITemplate.MSG_DUMMY_GRAI_POREFERENCE_MISSING, salesOrderId);
		}

		DummyGRAITemplate.assertValidSerialPrefix(serialPrefix);
	}

	public PickingJob closeLUAndTUPickingTargets(@NonNull final PickingJob pickingJob)
	{
		return closeLUAndTUPickingTargets(pickingJob, true, true, null, false);
	}

	public PickingJob closeLUAndTUPickingTargets(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		final boolean isCloseOnHeader = lineId == null;
		final boolean isCloseOnLines = lineId != null;
		final PickingJobOptions pickingJobOptions = configService.getPickingJobOptions(pickingJob.getCustomerId());
		return closeLUAndTUPickingTargets(
				pickingJob,
				isCloseOnHeader,
				isCloseOnLines,
				lineId,
				pickingJobOptions.isShipOnCloseLU());
	}

	private PickingJob closeLUAndTUPickingTargets(
			@NonNull final PickingJob pickingJob,
			final boolean isCloseOnHeader,
			final boolean isCloseOnLines,
			@Nullable final PickingJobLineId onlyLineId,
			final boolean isShipClosedHUs)
	{
		final LUIdsAndTopLevelTUIdsCollector closedHUIdsCollector = new LUIdsAndTopLevelTUIdsCollector();
		final PickingJob pickingJobChanged = pickingJob.withClosedLUAndTUPickingTargets(isCloseOnHeader, isCloseOnLines, onlyLineId, closedHUIdsCollector);

		if (!Util.equals(pickingJob, pickingJobChanged))
		{
			pickingJobRepository.save(pickingJobChanged);
		}

		if (!closedHUIdsCollector.isEmpty())
		{
			pickingJobChanged.getPickingSlotIdEffective(onlyLineId)
					.ifPresent(pickingSlotId -> pickingSlotService.addToPickingSlotQueue(pickingSlotId, closedHUIdsCollector.getAllTopLevelHUIds()));

			final ImmutableSet<HuId> closedLUIds = closedHUIdsCollector.getLUIds();

			// me03 #30763: persist the picking consignee (BPartner + delivery location) on the just-closed LUs when
			// they carry no partner, so the per-BPartner M_HU_Label_Config matches and the SSCC label auto-prints.
			// Must run BEFORE printLULabels so the label lookup (keyed on the LU's own bpartner) selects the config.
			stampConsigneeOnClosedLUs(pickingJob, closedLUIds);

			huService.printLULabels(closedLUIds);

			if (isShipClosedHUs)
			{
				if (!closedHUIdsCollector.getTopLevelTUIds().isEmpty())
				{
					throw new AdempiereException("Shipping on close top level TUs is not supported yet. Found TUIds: " + closedHUIdsCollector.getTopLevelTUIds() + ". PickingJob: " + pickingJobChanged.getId() + ".");
				}

				if (!closedLUIds.isEmpty())
				{
					shipmentService.createShipmentForLUs(pickingJobChanged, closedLUIds);
				}
			}
		}

		return pickingJobChanged;
	}

	/**
	 * me03 #30763 — persists the picking consignee on each just-closed LU that carries no BPartner yet, so the
	 * per-BPartner {@code M_HU_Label_Config} matches and the SSCC label auto-prints (both at close and on later re-print).
	 * <p>
	 * The consignee is resolved <b>per LU</b> from the pre-close picking job: header-level pick targets
	 * (SALES_ORDER / DELIVERY_LOCATION aggregation) carry the job's delivery location; line-level pick targets
	 * (PRODUCT aggregation) carry their own line's delivery location. A job may span multiple consignees, so this
	 * never applies a blanket customer id. Only LUs actually closed by this operation (in {@code closedLUIds}) are stamped.
	 */
	private void stampConsigneeOnClosedLUs(
			@NonNull final PickingJob pickingJob,
			@NonNull final ImmutableSet<HuId> closedLUIds)
	{
		if (closedLUIds.isEmpty())
		{
			return;
		}

		final Map<HuId, BPartnerLocationId> luId2consignee = new HashMap<>();

		// header-level pick target (SALES_ORDER / DELIVERY_LOCATION aggregation) -> job delivery location
		final BPartnerLocationId headerConsignee = pickingJob.getDeliveryBPLocationId();
		if (headerConsignee != null)
		{
			pickingJob.getLuPickingTarget(null)
					.filter(LUPickingTarget::isExistingLU)
					.ifPresent(target -> luId2consignee.put(target.getLuIdNotNull(), headerConsignee));
		}

		// line-level pick targets (PRODUCT aggregation) -> each line's own delivery location
		pickingJob.streamLines().forEach(line ->
				pickingJob.getLuPickingTarget(line.getId())
						.filter(LUPickingTarget::isExistingLU)
						.ifPresent(target -> luId2consignee.put(target.getLuIdNotNull(), line.getDeliveryBPLocationId())));

		for (final HuId closedLUId : closedLUIds)
		{
			final BPartnerLocationId consignee = luId2consignee.get(closedLUId);
			if (consignee != null)
			{
				huService.setBPartnerAndLocationIfNotSet(closedLUId, consignee);
			}
		}
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

	/**
	 * @return the set of picked HU ids (VHU level) that are covered by a picking job for any of the given
	 * shipment schedules — regardless of the job's doc-status. Used by the shipment-reverse restore safety net
	 * to fire ONLY for picked-qty shipments that originated from a picking job, and never for QtyToDeliver /
	 * on-the-fly shipments (which have no picking job and whose reverse must clear the HU's BPartner + return it
	 * to Active stock).
	 */
	public ImmutableSet<HuId> getHuIdsCoveredByPickingJobs(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		return pickingJobRepository.getPickingJobIdsByScheduleId(shipmentScheduleIds)
				.values().stream()
				.flatMap(List::stream)
				.collect(ImmutableSet.toImmutableSet())
				.stream()
				.map(this::getById)
				.flatMap(pickingJob -> pickingJob.getAllPickedHuIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	public void reopenPickingJobs(@NonNull final ReopenPickingJobRequest request)
	{
		final Map<ShipmentScheduleId, List<PickingJobId>> scheduleId2JobIds = pickingJobRepository
				.getPickingJobIdsByScheduleId(request.getShipmentScheduleIds());

		final PickingJobReopenCommand.PickingJobReopenCommandBuilder commandBuilder = PickingJobReopenCommand.builder()
				.pickingSlotService(pickingSlotService)
				.pickingJobRepository(pickingJobRepository)
				.shipmentScheduleService(shipmentScheduleService)
				.huService(huService);

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

	public PickingSlotSuggestions getPickingSlotsSuggestions(final @NonNull PickingJob pickingJob)
	{
		final Set<DocumentLocation> deliveryLocations = bpartnerService.getDocumentLocations(pickingJob.getDeliveryBPLocationIds());
		return pickingSlotService.getPickingSlotsSuggestions(deliveryLocations);
	}

	public PickingJob pickAll(@NonNull final PickingJobId pickingJobId, final @NonNull UserId callerId)
	{
		return PickingJobPickAllCommand.builder()
				.pickingJobService(this)
				//
				.pickingJobId(pickingJobId)
				.callerId(callerId)
				//
				.build().execute();
	}

	public PickingJobQtyAvailable getQtyAvailable(@NonNull final PickingJobId pickingJobId, final @NonNull UserId callerId)
	{
		return PickingJobGetQtyAvailableCommand.builder()
				.pickingJobService(this)
				.warehouseService(warehouseService)
				.huService(huService)
				//
				.pickingJobId(pickingJobId)
				.callerId(callerId)
				//
				.build().execute();
	}

	public GetNextEligibleLineToPackResponse getNextEligibleLineToPack(@NonNull final GetNextEligibleLineToPackRequest request)
	{
		return GetNextEligibleLineToPackCommand.builder()
				.pickingJobService(this)
				.huService(huService)
				.shipmentSchedules(shipmentScheduleService.newLoadingCache())
				.request(request)
				.build().execute();
	}
}
