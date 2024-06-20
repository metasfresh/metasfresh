package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.Check;
import de.metas.dao.ValueRestriction;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServicesFactory;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.commands.PickingJobAbortCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobAllocatePickingSlotCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCompleteCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobPickCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobUnPickCommand;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.order.OrderId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PickingJobService
{
	@NonNull private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final PickingJobLoaderSupportingServicesFactory pickingJobLoaderSupportingServicesFactory;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepo;
	@NonNull private final IShipmentService shipmentService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUReservationService huReservationService;

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
				//
				.request(request)
				//
				.build().execute();
	}

	public PickingJob complete(@NonNull final PickingJob pickingJob)
	{
		return prepareToComplete(pickingJob).execute();
	}

	public PickingJobCompleteCommand.PickingJobCompleteCommandBuilder prepareToComplete(@NonNull final PickingJob pickingJob)
	{
		return PickingJobCompleteCommand.builder()
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
		return packagingDAO.stream(toPackageableQuery(query))
				.map(PickingJobService::extractPickingJobCandidate)
				.distinct();
	}

	private static PackageableQuery toPackageableQuery(@NonNull final PickingJobQuery query)
	{
		final PackageableQuery.PackageableQueryBuilder builder = PackageableQuery.builder()
				.onlyFromSalesOrder(true)
				.salesOrderDocumentNo(query.getSalesOrderDocumentNo())
				.lockedBy(query.getUserId())
				.includeNotLocked(true)
				.excludeLockedForProcessing(true)
				.excludeShipmentScheduleIds(query.getExcludeShipmentScheduleIds())
				.orderBys(ImmutableSet.of(
						PackageableQuery.OrderBy.PriorityRule,
						PackageableQuery.OrderBy.PreparationDate,
						PackageableQuery.OrderBy.SetupPlaceNo_Descending,
						PackageableQuery.OrderBy.SalesOrderId,
						PackageableQuery.OrderBy.DeliveryBPLocationId,
						PackageableQuery.OrderBy.WarehouseTypeId));

		final Set<BPartnerId> onlyBPartnerIds = query.getOnlyBPartnerIdsEffective();
		if (!onlyBPartnerIds.isEmpty())
		{
			builder.customerIds(onlyBPartnerIds);
		}

		final ImmutableSet<LocalDate> deliveryDays = query.getDeliveryDays();
		if (!deliveryDays.isEmpty())
		{
			builder.deliveryDays(deliveryDays);
		}

		final ImmutableSet<BPartnerLocationId> locationIds = query.getOnlyHandoverLocationIds();
		if (!locationIds.isEmpty())
		{
			builder.handoverLocationIds(locationIds);
		}

		final WarehouseId workplaceWarehouseId = query.getWarehouseId();
		if (workplaceWarehouseId != null)
		{
			builder.warehouseId(workplaceWarehouseId);
		}

		return builder.build();
	}

	private static PickingJobCandidate extractPickingJobCandidate(@NonNull final Packageable item)
	{
		return PickingJobCandidate.builder()
				.preparationDate(item.getPreparationDate())
				.salesOrderId(Objects.requireNonNull(item.getSalesOrderId()))
				.salesOrderDocumentNo(Objects.requireNonNull(item.getSalesOrderDocumentNo()))
				.customerName(item.getCustomerName())
				.deliveryBPLocationId(item.getCustomerLocationId())
				.warehouseTypeId(item.getWarehouseTypeId())
				.partiallyPickedBefore(computePartiallyPickedBefore(item))
				.build();
	}

	@NonNull
	public Stream<Packageable> streamPackageable(@NonNull final PickingJobQuery query)
	{
		return packagingDAO.stream(toPackageableQuery(query));
	}

	private static boolean computePartiallyPickedBefore(final Packageable item)
	{
		return item.getQtyPickedPlanned().signum() != 0
				|| item.getQtyPickedNotDelivered().signum() != 0
				|| item.getQtyPickedAndDelivered().signum() != 0;
	}

	public IADReferenceDAO.ADRefList getQtyRejectedReasons()
	{
		return pickingCandidateService.getQtyRejectedReasons();
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
				PickingJobPickCommand.builder()
						.pickingJobRepository(pickingJobRepository)
						.pickingCandidateService(pickingCandidateService)
						.huQRCodesService(huQRCodesService)
						.inventoryService(inventoryService)
						.huReservationService(huReservationService)
						.pickingConfigRepo(pickingConfigRepo)
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
						//
						.build().execute();
				return getById(pickingJob.getId());
			}
			case UNPICK:
			{
				return PickingJobUnPickCommand.builder()
						.pickingJobRepository(pickingJobRepository)
						.pickingCandidateService(pickingCandidateService)
						.huQRCodesService(huQRCodesService)
						//
						.pickingJob(pickingJob)
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
	public boolean clearAssignmentsForSlot(@NonNull final PickingSlotId slotId, final boolean abortOngoingPickingJobs)
	{
		final List<PickingJob> pickingJobs = pickingJobRepository.getDraftedByPickingSlotId(slotId, pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices());
		if (pickingJobs.isEmpty())
		{
			return true;
		}

		return pickingJobs.stream().allMatch(job -> removePickingSlotAssignment(job, abortOngoingPickingJobs));
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
			throw new AdempiereException("Job already assigned")
					.setParameter("newResponsibleId", newResponsibleId)
					.setParameter("job", job);
		}

		return job;
	}

	private boolean removePickingSlotAssignment(
			@NonNull final PickingJob pickingJob,
			final boolean abortOngoingPickingJobs)
	{
		if (pickingJob.isNothingPicked())
		{
			pickingJobRepository.save(pickingJob.withPickingSlot(null));
			return true;
		}
		else if (abortOngoingPickingJobs)
		{
			final PickingJob abortedPickingJob = abort(pickingJob);
			Check.assume(!abortedPickingJob.getPickingSlotId().isPresent(), "Assuming the aborted picking job is no longer assigned to a picking slot.");
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
}
