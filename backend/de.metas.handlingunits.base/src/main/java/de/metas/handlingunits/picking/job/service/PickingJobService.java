package de.metas.handlingunits.picking.job.service;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.api.PickingSlotBarcode;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
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
	private final IBPartnerBL bpartnerBL;

	public PickingJobService(
			final PickingJobRepository pickingJobRepository,
			final PickingJobLockService pickingJobLockService,
			final PickingJobSlotService pickingSlotService,
			final PickingCandidateService pickingCandidateService,
			final IBPartnerBL bpartnerBL)
	{
		this.pickingSlotService = pickingSlotService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingCandidateService = pickingCandidateService;
		this.bpartnerBL = bpartnerBL;
	}

	public PickingJob getById(final PickingJobId pickingJobId)
	{
		return pickingJobRepository.getById(pickingJobId, newLoadingSupportServices());
	}

	private PickingJobLoaderSupportingServices newLoadingSupportServices()
	{
		return new PickingJobLoaderSupportingServices(bpartnerBL, pickingSlotService);
	}

	public List<PickingJob> getDraftJobsByPickerId(@NonNull final UserId pickerId)
	{
		return pickingJobRepository.getDraftJobsByPickerId(pickerId, newLoadingSupportServices());
	}

	public PickingJob createPickingJob(@NonNull final PickingJobCreateRequest request)
	{
		return PickingJobCreateCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJobLockService(pickingJobLockService)
				.pickingCandidateService(pickingCandidateService)
				.pickingJobSlotService(pickingSlotService)
				.bpartnerBL(bpartnerBL)
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
				.pickingCandidateService(pickingCandidateService)
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
				//
				.pickingJob(pickingJob)
				//
				.build().execute();
	}

	public Stream<PickingJobReference> streamDraftPickingJobReferences(@NonNull final UserId pickerId)
	{
		return pickingJobRepository.streamDraftPickingJobReferences(pickerId, newLoadingSupportServices());
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
						.build())
				.map(PickingJobService::extractPickingJobCandidate)
				.distinct();
	}

	private static PickingJobCandidate extractPickingJobCandidate(@NonNull final Packageable item)
	{
		return PickingJobCandidate.builder()
				.salesOrderId(Objects.requireNonNull(item.getSalesOrderId()))
				.salesOrderDocumentNo(Objects.requireNonNull(item.getSalesOrderDocumentNo()))
				.customerName(item.getCustomerName())
				.deliveryBPLocationId(item.getCustomerLocationId())
				.warehouseTypeId(item.getWarehouseTypeId())
				.locked(item.getLockedBy() != null)
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

	private PickingJob save(@NonNull final PickingJob pickingJob)
	{
		pickingJobRepository.save(pickingJob);
		return pickingJob;
	}

	public PickingJob processStepEvents(
			@NonNull final PickingJob pickingJob,
			@NonNull final List<PickingJobStepEvent> events)
	{
		return save(pickingJob.applyingEvents(events));
	}
}
