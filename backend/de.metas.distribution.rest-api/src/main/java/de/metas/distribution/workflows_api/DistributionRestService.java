package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADRefList;
import de.metas.dao.ValueRestriction;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderDropToRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleCreateRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderPickFromRequest;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.distribution.workflows_api.facets.DistributionFacetIdsCollection;
import de.metas.distribution.workflows_api.facets.DistributionFacetsCollection;
import de.metas.distribution.workflows_api.facets.DistributionFacetsCollector;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.order.IOrderBL;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class DistributionRestService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionJobHUReservationService distributionJobHUReservationService;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	public DistributionRestService(
			final @NonNull MobileUIDistributionConfigRepository configRepository,
			final @NonNull DDOrderService ddOrderService,
			final @NonNull DDOrderMoveScheduleService ddOrderMoveScheduleService,
			final @NonNull DistributionJobHUReservationService distributionJobHUReservationService,
			final @NonNull HUQRCodesService huQRCodeService,
			final @NonNull HUQRCodesService huQRCodesService)
	{
		this.ddOrderService = ddOrderService;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.distributionJobHUReservationService = distributionJobHUReservationService;
		this.huQRCodesService = huQRCodesService;

		this.loadingSupportServices = DistributionJobLoaderSupportingServices.builder()
				.configRepository(configRepository)
				.ddOrderService(ddOrderService)
				.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
				.huQRCodeService(huQRCodeService)
				.warehouseBL(warehouseBL)
				.productBL(productBL)
				.orgDAO(Services.get(IOrgDAO.class))
				.orderBL(orderBL)
				.ppOrderBL(ppOrderBL)
				.build();
	}

	public ADRefList getQtyRejectedReasons()
	{
		return ddOrderMoveScheduleService.getQtyRejectedReasons();
	}

	public Stream<DDOrderReference> streamJobReferencesForUser(@NonNull final DDOrderReferenceQuery query)
	{
		final DDOrderReferenceCollector collector = DDOrderReferenceCollector.builder()
				.ddOrderService(ddOrderService)
				.build();

		collect(query, collector);

		return collector.streamCollectedItems();
	}

	public DistributionFacetsCollection getFacets(@NonNull final DDOrderReferenceQuery query)
	{
		final DistributionFacetsCollector collector = DistributionFacetsCollector.builder()
				.warehouseBL(warehouseBL)
				.orderBL(orderBL)
				.ppOrderBL(ppOrderBL)
				.ddOrderService(ddOrderService)
				.productBL(productBL)
				.build();

		collect(query, collector);

		return collector.toFacetsCollection();
	}

	private <T> void collect(
			@NonNull final DDOrderReferenceQuery query,
			@NonNull final DistributionOrderCollector<T> collector)
	{
		final @NonNull UserId responsibleId = query.getResponsibleId();
		final @NonNull QueryLimit suggestedLimit = query.getSuggestedLimit();

		//
		// Already started jobs
		streamDDOrdersAssignedTo(responsibleId)
				.forEach(ddOrder -> collector.collect(ddOrder, true));

		//
		// New possible jobs
		if (suggestedLimit.isNoLimit() || !suggestedLimit.isLimitHitOrExceeded(collector.getCollectedItems()))
		{
			ddOrderService.streamDDOrders(toActiveNotAssignedDDOrderQuery(query))
					.limit(suggestedLimit.minusSizeOf(collector.getCollectedItems()).toIntOr(Integer.MAX_VALUE))
					.forEach(ddOrder -> collector.collect(ddOrder, false));
		}
	}

	private static DDOrderQuery toActiveNotAssignedDDOrderQuery(final @NonNull DDOrderReferenceQuery query)
	{
		final DistributionFacetIdsCollection activeFacetIds = query.getActiveFacetIds();
		return DDOrderQuery.builder()
				.orderBy(DDOrderQuery.OrderBy.PriorityRule)
				.orderBy(DDOrderQuery.OrderBy.DatePromised)
				.docStatus(DocStatus.Completed)
				.responsibleId(ValueRestriction.isNull())
				.warehouseFromIds(activeFacetIds.getWarehouseFromIds())
				.warehouseToIds(activeFacetIds.getWarehouseToIds())
				.salesOrderIds(activeFacetIds.getSalesOrderIds())
				.manufacturingOrderIds(activeFacetIds.getManufacturingOrderIds())
				.datesPromised(activeFacetIds.getDatesPromised())
				.productIds(activeFacetIds.getProductIds())
				.qtysEntered(activeFacetIds.getQuantities())
				.build();
	}

	private Stream<I_DD_Order> streamDDOrdersAssignedTo(final @NonNull UserId responsibleId)
	{
		return ddOrderService.streamDDOrders(DDOrderQuery.builder()
				.docStatus(DocStatus.Completed)
				.responsibleId(ValueRestriction.equalsTo(responsibleId))
				.orderBy(DDOrderQuery.OrderBy.PriorityRule)
				.orderBy(DDOrderQuery.OrderBy.DatePromised)
				.build());
	}

	public DistributionJob createJob(
			final @NonNull DDOrderId ddOrderId,
			final @NonNull UserId responsibleId)
	{
		return DistributionJobCreateCommand.builder()
				.trxManager(trxManager)
				.ddOrderService(ddOrderService)
				.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
				.distributionJobHUReservationService(distributionJobHUReservationService)
				.loadingSupportServices(loadingSupportServices)
				//
				.ddOrderId(ddOrderId)
				.responsibleId(responsibleId)
				//
				.build().execute();
	}

	public DistributionJob assignJob(
			final @NonNull DistributionJobId jobId,
			final @NonNull UserId newResponsibleId)
	{
		final DistributionJobLoader loader = newLoader();

		final DDOrderId ddOrderId = jobId.toDDOrderId();
		final I_DD_Order ddOrderRecord = loader.getDDOrder(ddOrderId);
		final UserId oldResponsibleId = DistributionJobLoader.extractResponsibleId(ddOrderRecord);
		if (oldResponsibleId == null)
		{
			ddOrderRecord.setAD_User_Responsible_ID(newResponsibleId.getRepoId());
			ddOrderService.save(ddOrderRecord);
		}
		else if (!UserId.equals(oldResponsibleId, newResponsibleId))
		{
			throw new AdempiereException("Already assigned")
					.setParameter("ddOrder", ddOrderRecord)
					.setParameter("oldResponsibleId", oldResponsibleId)
					.setParameter("newResponsibleId", newResponsibleId);
		}

		return loader.load(ddOrderRecord);
	}

	public DistributionJob getJobById(final DistributionJobId jobId)
	{
		return newLoader().load(jobId);
	}

	public DistributionJob getJobById(final DDOrderId ddOrderId)
	{
		return newLoader().load(ddOrderId);
	}

	@NonNull
	private DistributionJobLoader newLoader()
	{
		return new DistributionJobLoader(loadingSupportServices);
	}

	public DistributionJob processEvent(@NonNull final DistributionJob job, @NonNull final JsonDistributionEvent event)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			DistributionJob changedJob = job;

			if (event.getPickFrom() != null)
			{
				changedJob = processEvent_PickFrom(changedJob, event);
			}
			if (event.getDropTo() != null)
			{
				changedJob = processEvent_DropTo(changedJob, event);
			}

			return changedJob;
		});
	}

	private DistributionJob processEvent_PickFrom(final @NonNull DistributionJob job0, final @NonNull JsonDistributionEvent event)
	{
		final DistributionJobLineId lineId = event.getLineId();
		DistributionJobStepId stepId = event.getDistributionStepId();
		DistributionJob changedJob = job0;

		if (lineId != null && stepId == null)
		{
			final DistributionJobLine line = changedJob.getLineById(lineId);

			final JsonDistributionEvent.PickFrom pickFrom = Check.assumeNotNull(event.getPickFrom(), "pickFrom must be set");
			final HUQRCode huQRCode = HUQRCode.fromGlobalQRCodeJsonString(Check.assumeNotNull(pickFrom.getQrCode(), "pickFrom.qrCode must be set"));
			final HuId huId = huQRCodesService.getHuIdByQRCode(huQRCode);

			final IHUProductStorage huStorage = handlingUnitsBL.getSingleHUProductStorage(huId);
			if (!ProductId.equals(huStorage.getProductId(), line.getProduct().getProductId()))
			{
				throw new AdempiereException("Product not matching");
			}

			final DDOrderMoveSchedule newSchedule = ddOrderMoveScheduleService.createScheduleToMove(
					DDOrderMoveScheduleCreateRequest.builder()
							.ddOrderId(changedJob.getDdOrderId())
							.ddOrderLineId(lineId.toDDOrderLineId())
							.productId(line.getProduct().getProductId())
							.pickFromLocatorId(line.getPickFromLocator().getLocatorId())
							.pickFromHUId(huId)
							.qtyToPick(huStorage.getQty())
							.isPickWholeHU(true)
							.dropToLocatorId(line.getDropToLocator().getLocatorId())
							.build()
			);

			final DistributionJobStep step = DistributionJobLoader.toDistributionJobStep(newSchedule, loadingSupportServices);
			changedJob = changedJob.withNewStep(lineId, step);
			stepId = step.getId();
		}

		if (stepId == null)
		{
			throw new AdempiereException("At least lineId or stepId must be set");
		}

		final DDOrderMoveSchedule changedSchedule = ddOrderMoveScheduleService.pickFromHU(DDOrderPickFromRequest.builder()
				.scheduleId(stepId.toScheduleId())
				// TODO make sure the scanned HU is the one that is scheduled to be moved
				//.huQRCode(StringUtils.trimBlankToOptional(event.getPickFrom().getQrCode()).map(HUQRCode::fromGlobalQRCodeJsonString).orElse(null))
				.build());

		final DistributionJobStep changedStep = DistributionJobLoader.toDistributionJobStep(changedSchedule, loadingSupportServices);
		return changedJob.withChangedStep(stepId, ignored -> changedStep);
	}

	private DistributionJob processEvent_DropTo(final @NonNull DistributionJob job, final @NonNull JsonDistributionEvent event)
	{
		final DistributionJobStepId stepId = Check.assumeNotNull(event.getDistributionStepId(), "stepId must be set");
		final DDOrderMoveSchedule changedSchedule = ddOrderMoveScheduleService.dropTo(DDOrderDropToRequest.builder()
				.scheduleId(stepId.toScheduleId())
				.build());

		final DistributionJobStep changedStep = DistributionJobLoader.toDistributionJobStep(changedSchedule, loadingSupportServices);
		return job.withChangedStep(stepId, ignored -> changedStep);
	}

	public DistributionJob complete(@NonNull final DistributionJob job)
	{
		// just to make sure there is nothing reserved to this job
		distributionJobHUReservationService.releaseAllReservations(job);

		final DDOrderId ddOrderId = job.getDdOrderId();
		ddOrderService.close(ddOrderId);
		ddOrderService.print(ddOrderId);

		return getJobById(ddOrderId);
	}

	public void abort(@NonNull final DistributionJob job)
	{
		abort().job(job).execute();
	}

	private DistributionJobAbortCommand.DistributionJobAbortCommandBuilder abort()
	{
		return DistributionJobAbortCommand.builder()
				.ddOrderService(ddOrderService)
				.distributionJobHUReservationService(distributionJobHUReservationService);
	}

	public void abortAll(@NonNull final UserId responsibleId)
	{
		final DistributionJobLoader loader = newLoader();
		final ImmutableList<DistributionJob> jobs = streamDDOrdersAssignedTo(responsibleId)
				.map(loader::load)
				.collect(ImmutableList.toImmutableList());
		if (jobs.isEmpty())
		{
			return;
		}

		abort().jobs(jobs).execute();
	}
}
