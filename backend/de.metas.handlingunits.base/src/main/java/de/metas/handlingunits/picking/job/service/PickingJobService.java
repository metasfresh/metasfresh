package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADRefList;
import de.metas.dao.ValueRestriction;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
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
import de.metas.handlingunits.picking.job.service.commands.PickingJobRequestReviewCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobUnPickCommand;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class PickingJobService
{
	private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	private final PickingJobRepository pickingJobRepository;
	private final PickingJobLockService pickingJobLockService;
	private final PickingJobSlotService pickingSlotService;
	private final PickingCandidateService pickingCandidateService;
	private final PickingJobHUReservationService pickingJobHUReservationService;
	private final PickingJobLoaderSupportingServicesFactory pickingJobLoaderSupportingServicesFactory;
	private final PickingConfigRepositoryV2 pickingConfigRepo;

	public PickingJobService(
			final PickingJobRepository pickingJobRepository,
			final PickingJobLockService pickingJobLockService,
			final PickingJobSlotService pickingSlotService,
			final PickingCandidateService pickingCandidateService,
			final PickingJobHUReservationService pickingJobHUReservationService,
			final PickingConfigRepositoryV2 pickingConfigRepo,
			final PickingJobLoaderSupportingServicesFactory pickingJobLoaderSupportingServicesFactory
	)
	{
		this.pickingSlotService = pickingSlotService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingCandidateService = pickingCandidateService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.pickingConfigRepo = pickingConfigRepo;
		this.pickingJobLoaderSupportingServicesFactory = pickingJobLoaderSupportingServicesFactory;
	}

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
				.pickingJobHUReservationService(pickingJobHUReservationService)
				.pickingConfigRepo(pickingConfigRepo)
				.loadingSupportServices(pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices())
				//
				.request(request)
				//
				.build().execute();
	}

	public PickingJob approveAndComplete(@NonNull final PickingJob pickingJob)
	{
		return prepareToComplete(pickingJob)
				.approveIfReadyToReview(true)
				.build().execute();
	}

	public PickingJob complete(@NonNull final PickingJob pickingJob)
	{
		return prepareToComplete(pickingJob)
				.build().execute();
	}

	private PickingJobCompleteCommand.PickingJobCompleteCommandBuilder prepareToComplete(final PickingJob pickingJob)
	{
		return PickingJobCompleteCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingSlotService(pickingSlotService)
				.pickingJobHUReservationService(pickingJobHUReservationService)
				//
				.pickingJob(pickingJob);
	}

	public PickingJob requestReview(final PickingJob pickingJob)
	{
		return PickingJobRequestReviewCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJob(pickingJob)
				.build().execute();
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

	public Optional<PickingJob> getByOrderId(@NonNull final OrderId orderId)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.getDraftBySalesOrderId(orderId, loadingSupportingServices);
	}

	public Stream<PickingJobReference> streamDraftPickingJobReferences(@NonNull final UserId pickerId)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.streamDraftPickingJobReferences(pickerId, loadingSupportingServices);
	}

	public Stream<PickingJobCandidate> streamPickingJobCandidates(
			@NonNull final UserId userId,
			@NonNull final Set<ShipmentScheduleId> excludeShipmentScheduleIds)
	{
		return packagingDAO
				.stream(PackageableQuery.builder()
						.onlyFromSalesOrder(true)
						.lockedBy(userId)
						.includeNotLocked(true)
						.excludeShipmentScheduleIds(excludeShipmentScheduleIds)
						.orderBys(ImmutableSet.of(
								PackageableQuery.OrderBy.PriorityRule,
								PackageableQuery.OrderBy.PreparationDate,
								PackageableQuery.OrderBy.SalesOrderId,
								PackageableQuery.OrderBy.DeliveryBPLocationId,
								PackageableQuery.OrderBy.WarehouseTypeId))
						.build())
				.map(PickingJobService::extractPickingJobCandidate)
				.distinct();
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

	private static boolean computePartiallyPickedBefore(final Packageable item)
	{
		return item.getQtyPickedPlanned().signum() != 0
				|| item.getQtyPickedNotDelivered().signum() != 0
				|| item.getQtyPickedAndDelivered().signum() != 0;
	}

	public ADRefList getQtyRejectedReasons()
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
		final ImmutableCollection<PickingJobStepEvent> aggregatedEvents = PickingJobStepEvent.aggregateByStepIdAndPickFromKey(events).values();

		PickingJob changedPickingJob = pickingJob0;
		for (final PickingJobStepEvent event : aggregatedEvents)
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
				assert event.getQtyPicked() != null;
				return PickingJobPickCommand.builder()
						.pickingJobRepository(pickingJobRepository)
						.pickingCandidateService(pickingCandidateService)
						//
						.pickingJob(pickingJob)
						.pickingJobStepId(event.getPickingStepId())
						.pickFromKey(event.getPickFromKey())
						.qtyToPickBD(Objects.requireNonNull(event.getQtyPicked()))
						.qtyRejectedBD(event.getQtyRejected())
						.qtyRejectedReasonCode(event.getQtyRejectedReasonCode())
						//
						.build().execute();
			}
			case UNPICK:
			{
				return PickingJobUnPickCommand.builder()
						.pickingJobRepository(pickingJobRepository)
						.pickingCandidateService(pickingCandidateService)
						//
						.pickingJob(pickingJob)
						.onlyPickingJobStepId(event.getPickingStepId())
						.onlyPickFromKey(event.getPickFromKey())
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
			for (PickingJob job : getDraftJobsByPickerId(userId))
			{
				pickingJobRepository.save(job.withLockedBy(null));
			}
		});
	}

	public PickingJob assignPickingJob(@NonNull final PickingJobId pickingJobId, @NonNull final UserId newResponsibleId)
	{
		PickingJob job = getById(pickingJobId);
		if (job.getLockedBy() == null)
		{
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
	public boolean hasPickingJobsReadyToReview(@NonNull final ImmutableSet<PickingJobId> pickingJobIds)
	{
		return pickingJobRepository.hasReadyToReview(pickingJobIds);
	}

	public List<PickingJob> getByIsReadyToReview(@NonNull final ImmutableSet<PickingJobId> pickingJobIds)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.getByIsReadyToReview(pickingJobIds, loadingSupportingServices);
	}

}
