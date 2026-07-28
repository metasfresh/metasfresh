package de.metas.distribution.mobileui.job.service;

import de.metas.ad_reference.ADRefList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.mobileui.external_services.hu.DistributionHUService;
import de.metas.distribution.mobileui.external_services.product.DistributionProductService;
import de.metas.distribution.mobileui.external_services.warehouse.DistributionWarehouseService;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobId;
import de.metas.distribution.mobileui.job.model.DistributionJobLine;
import de.metas.distribution.mobileui.job.model.DistributionJobLineId;
import de.metas.distribution.mobileui.job.service.commands.abort_job.DistributionJobAbortCommand;
import de.metas.distribution.mobileui.job.service.commands.create_job.DistributionJobCreateCommand;
import de.metas.distribution.mobileui.job.service.commands.drop_to.DistributionJobDropToCommand;
import de.metas.distribution.mobileui.job.service.commands.pick_from.DistributionJobPickFromCommand;
import de.metas.distribution.mobileui.job.service.commands.unpick.DistributionJobUnpickCommand;
import de.metas.distribution.mobileui.rest_api.json.JsonDistributionEvent;
import de.metas.distribution.mobileui.rest_api.json.JsonDropAllRequest;
import de.metas.distribution.mobileui.rest_api.json.JsonGetNextEligiblePickFromLineRequest;
import de.metas.distribution.mobileui.rest_api.json.JsonGetNextEligiblePickFromLineResponse;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributionRestService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final MobileUIDistributionConfigRepository configRepository;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionJobHUReservationService distributionJobHUReservationService;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final DistributionHUService huService;
	@NonNull private final DistributionWarehouseService warehouseService;
	@NonNull private final DistributionProductService productService;

	public MobileUIDistributionConfig getConfig() {return configRepository.getConfig();}

	public ADRefList getQtyRejectedReasons()
	{
		return ddOrderMoveScheduleService.getQtyRejectedReasons();
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
					.warehouseService(warehouseService)
					.huService(huService)
					.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
					.loadingSupportServices(loadingSupportServices)
					.userId(callerId)
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
					.onlyStepId(event.getDistributionStepId())
					.dropToQRCode(event.getDropToNonNull().getQrCode())
					.completeJobsIfFullyMoved(false)
					.build().execute()
					.getJobById(jobId);
		}
		else if (event.getUnpick() != null)
		{
			return DistributionJobUnpickCommand.builder()
					.trxManager(trxManager)
					.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
					.huService(huService)
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
		final MobileUIDistributionConfig config = getConfig();
		final LocatorId inTransitLocatorId = config.isRequireTrolley()
				? warehouseService.getTrolleyByUserId(callerId).map(LocatorQRCode::getLocatorId).orElse(null)
				: null;

		newDropToCommand()
				.userId(callerId)
				.inTransitLocatorId(inTransitLocatorId)
				.dropToQRCode(request.getDropToQRCode())
				.completeJobsIfFullyMoved(true)
				.build().execute();
	}

	private DistributionJobDropToCommand.DistributionJobDropToCommandBuilder newDropToCommand()
	{
		return DistributionJobDropToCommand.builder()
				.trxManager(trxManager)
				.distributionJobService(this)
				.ddOrderMoveScheduleService(ddOrderMoveScheduleService)
				.loadingSupportServices(loadingSupportServices)
				.warehouseService(warehouseService);
	}

	public DistributionJob complete(@NonNull final DistributionJobId jobId, @NonNull final UserId callerId)
	{
		final DistributionJob job = getJobById(jobId);
		job.assertCanEdit(callerId);
		return complete(job);
	}

	public DistributionJob complete(@NonNull final DistributionJob job)
	{
		// just to make sure there is nothing reserved to this job
		distributionJobHUReservationService.releaseAllReservations(job);

		final DDOrderId ddOrderId = job.getDdOrderId();
		ddOrderService.close(ddOrderId);

		if (configRepository.getConfig().isPrintDDOrderOnComplete())
		{
			ddOrderService.print(ddOrderId);
		}

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

	public JsonGetNextEligiblePickFromLineResponse getNextEligiblePickFromLine(@NonNull final JsonGetNextEligiblePickFromLineRequest request, @NonNull final UserId callerId)
	{
		final DistributionJobId jobId = DistributionJobId.ofWFProcessId(request.getWfProcessId());
		final DistributionJob job = getJobById(jobId);
		job.assertCanEdit(callerId);

		final HUQRCode huQRCode = huService.resolveHUQRCode(request.getHuQRCode());

		final ProductId productId;
		if (request.getProductScannedCode() != null)
		{
			productId = productService.getProductIdByScannedProductCode(request.getProductScannedCode());
			huService.assetHUContainsProduct(huQRCode, productId);
		}
		else
		{
			productId = huService.getSingleProductId(huQRCode);
		}

		final DistributionJobLineId nextEligiblePickFromLineId;
		if (request.getLineId() != null)
		{
			final DistributionJobLine line = job.getLineById(request.getLineId());
			nextEligiblePickFromLineId = line.isEligibleForPicking() ? line.getId() : null;
		}
		else
		{
			nextEligiblePickFromLineId = job.getNextEligiblePickFromLineId(productId).orElse(null);
		}

		return JsonGetNextEligiblePickFromLineResponse.builder()
				.lineId(nextEligiblePickFromLineId)
				.build();
	}

	public void printMaterialInTransitReport(
			@NonNull final UserId userId,
			@NonNull final String adLanguage)
	{
		@NonNull final LocatorId inTransitLocatorId = warehouseService.getTrolleyByUserId(userId)
				.map(LocatorQRCode::getLocatorId)
				.orElseThrow(() -> new AdempiereException("No trolley found for user: " + userId));

		ddOrderMoveScheduleService.printMaterialInTransitReport(inTransitLocatorId, adLanguage);
	}
}
