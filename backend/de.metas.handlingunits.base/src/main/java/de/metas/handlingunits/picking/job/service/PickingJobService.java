package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
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
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.api.PickingSlotBarcode;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

	public PickingJobService(
			final PickingJobRepository pickingJobRepository,
			final PickingJobLockService pickingJobLockService,
			final PickingJobSlotService pickingSlotService,
			final PickingCandidateService pickingCandidateService,
			final PickingJobHUReservationService pickingJobHUReservationService,
			final PickingJobLoaderSupportingServicesFactory pickingJobLoaderSupportingServicesFactory)
	{
		this.pickingSlotService = pickingSlotService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingCandidateService = pickingCandidateService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
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
		return pickingJobRepository.getDraftJobsByPickerId(pickerId, loadingSupportingServices);
	}

	public PickingJob createPickingJob(@NonNull final PickingJobCreateRequest request)
	{
		return PickingJobCreateCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingCandidateService(pickingCandidateService)
				.pickingJobSlotService(pickingSlotService)
				.pickingJobHUReservationService(pickingJobHUReservationService)
				.loadingSupportServices(pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices())
				//
				.request(request)
				//
				.build().execute();
	}

	public PickingJob complete(@NonNull final PickingJob pickingJob)
	{
		return PickingJobCompleteCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingSlotService(pickingSlotService)
				.pickingJobHUReservationService(pickingJobHUReservationService)
				//
				.pickingJob(pickingJob)
				//
				.build().execute();
	}

	public PickingJob abort(@NonNull final PickingJob pickingJob)
	{
		return PickingJobAbortCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingSlotService(pickingSlotService)
				.pickingJobHUReservationService(pickingJobHUReservationService)
				.pickingCandidateService(pickingCandidateService)
				//
				.pickingJob(pickingJob)
				//
				.build().execute();
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
				.build();
	}

	public IADReferenceDAO.ADRefList getQtyRejectedReasons()
	{
		return pickingCandidateService.getQtyRejectedReasons();
	}

	public PickingJob allocateAndSetPickingSlot(
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingSlotBarcode pickingSlotBarcode)
	{
		return PickingJobAllocatePickingSlotCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingSlotService(pickingSlotService)
				//
				.pickingJob(pickingJob)
				.pickingSlotBarcode(pickingSlotBarcode)
				//
				.build().execute();
	}

	public PickingJob processStepEvents(
			@NonNull final PickingJob pickingJob0,
			@NonNull final List<PickingJobStepEvent> events)
	{
		final ImmutableMap<PickingJobStepId, PickingJobStepEvent> eventsByStepId = PickingJobStepEvent.aggregateByStepId(events);

		PickingJob changedPickingJob = pickingJob0;
		for (final PickingJobStepEvent event : eventsByStepId.values())
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
						.pickingJobHUReservationService(pickingJobHUReservationService)
						.pickingCandidateService(pickingCandidateService)
						//
						.pickingJob(pickingJob)
						.pickingJobStepId(event.getPickingStepId())
						.qtyToPickBD(Objects.requireNonNull(event.getQtyPicked()))
						.qtyRejectedReasonCode(event.getQtyRejectedReasonCode())
						//
						.build().execute();
			}
			case UNPICK:
			{
				return PickingJobUnPickCommand.builder()
						.pickingJobRepository(pickingJobRepository)
						.pickingJobHUReservationService(pickingJobHUReservationService)
						.pickingCandidateService(pickingCandidateService)
						//
						.pickingJob(pickingJob)
						.onlyPickingJobStepId(event.getPickingStepId())
						//
						.build().execute();
			}
			default:
			{
				throw new AdempiereException("Unhandled event type: " + event);
			}
		}
	}

}
