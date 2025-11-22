package de.metas.distribution.workflows_api;

import de.metas.ad_reference.ADRefList;
import de.metas.distribution.config.MobileUIDistributionConfig;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.distribution.rest_api.JsonDropAllRequest;
import de.metas.distribution.workflows_api.facets.DistributionFacetsCollection;
import de.metas.distribution.workflows_api.facets.DistributionFacetsCollector;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.trace.HUAccessService;
import de.metas.order.IOrderBL;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMConversionBL;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributionRestService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final MobileUIDistributionConfigRepository configRepository;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionJobHUReservationService distributionJobHUReservationService;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUAccessService huAccessService;
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;

	public MobileUIDistributionConfig getConfig() {return configRepository.getConfig();}

	public ADRefList getQtyRejectedReasons()
	{
		return ddOrderMoveScheduleService.getQtyRejectedReasons();
	}

	public Collection<DDOrderReference> getJobReferences(@NonNull final DDOrderReferenceQuery query)
	{
		final DDOrderReferenceCollector collector = DDOrderReferenceCollector.builder()
				.loadingSupportServices(loadingSupportServices)
				.build();

		collect(query, collector);

		return collector.getCollectedItems();
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
		//
		// Already started jobs
		ddOrderService.streamDDOrders(DistributionJobQueries.ddOrdersAssignedToUser(query))
				.forEach(collector::collect);

		//
		// New possible jobs
		@NonNull final QueryLimit suggestedLimit = query.getSuggestedLimit();
		if (suggestedLimit.isNoLimit() || !suggestedLimit.isLimitHitOrExceeded(collector.getCollectedItems()))
		{
			ddOrderService.streamDDOrders(DistributionJobQueries.toActiveNotAssignedDDOrderQuery(query))
					.limit(suggestedLimit.minusSizeOf(collector.getCollectedItems()).toIntOr(Integer.MAX_VALUE))
					.forEach(collector::collect);
		}
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

		return loader.loadByRecord(ddOrderRecord);
	}

	public DistributionJob getJobById(final DistributionJobId jobId)
	{
		return newLoader().loadByJobId(jobId);
	}

	public DistributionJob getJobById(final DDOrderId ddOrderId)
	{
		return newLoader().loadByDDOrderId(ddOrderId);
	}

	public List<DistributionJob> listJobs(final DDOrderQuery query)
	{
		return newLoader().loadByQuery(query);
	}

	@NonNull
	private DistributionJobLoader newLoader()
	{
		return new DistributionJobLoader(loadingSupportServices);
	}

	public DistributionJob processEvent(@NonNull final JsonDistributionEvent event, @NonNull final UserId callerId)
	{
		final DistributionJobId jobId = DistributionJobId.ofWFProcessId(WFProcessId.ofString(event.getWfProcessId()));
		final DistributionJob job = getJobById(jobId);
		job.assertCanEdit(callerId);

		if (event.getPickFrom() != null)
		{
			return DistributionJobPickFromCommand.builder()
					.trxManager(trxManager)
					.huQRCodesService(huQRCodesService)
					.handlingUnitsBL(handlingUnitsBL)
					.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
					.loadingSupportServices(loadingSupportServices)
					.hupiItemProductBL(hupiItemProductBL)
					.inventoryService(inventoryService)
					.uomConversionBL(uomConversionBL)
					.huAccessService(huAccessService)
					.job(job)
					.lineId(event.getLineId())
					.stepId(event.getDistributionStepId())
					.pickFrom(event.getPickFrom())
					.build().execute();
		}
		else if (event.getDropTo() != null)
		{
			return newDropToCommand()
					.userId(callerId)
					.onlyJobId(jobId)
					.onlyJobId(jobId)
					.onlyStepId(event.getDistributionStepId())
					.dropToQRCode(event.getDropToNonNull().getQrCode())
					.build().execute()
					.getJobById(jobId);
		}
		else if (event.getUnpick() != null)
		{
			return DistributionJobUnpickCommand.builder()
					.trxManager(trxManager)
					.handlingUnitsBL(handlingUnitsBL)
					.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
					.job(job)
					.stepId(Check.assumeNotNull(event.getDistributionStepId(), "stepId must be set when unpicking"))
					.unpickToTargetQRCode(Check.assumeNotNull(event.getUnpick(), "unpick must be set").getUnpickToTargetQRCode())
					.build().execute();
		}
		else
		{
			throw new AdempiereException("Unknown event type: " + event);
		}
	}

	public void dropAll(final JsonDropAllRequest request, final UserId callerId)
	{
		newDropToCommand()
				.userId(callerId)
				.dropToQRCode(request.getDropToQRCode())
				.build().execute();
	}

	private DistributionJobDropToCommand.DistributionJobDropToCommandBuilder newDropToCommand()
	{
		return DistributionJobDropToCommand.builder()
				.trxManager(trxManager)
				.distributionJobService(this)
				.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
				.loadingSupportServices(loadingSupportServices)
				.locatorScannedCodeResolver(locatorScannedCodeResolver);
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
		final List<DistributionJob> jobs = newLoader().loadByQuery(DistributionJobQueries.ddOrdersAssignedToUser(responsibleId));
		if (jobs.isEmpty())
		{
			return;
		}

		abort().jobs(jobs).execute();
	}
}
