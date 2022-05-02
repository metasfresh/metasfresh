package de.metas.manufacturing.job.service;

import de.metas.dao.ValueRestriction;
import de.metas.device.accessor.DeviceAccessor;
import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.device.accessor.DeviceId;
import de.metas.device.websocket.DeviceWebsocketNamingStrategy;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleProcessRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.manufacturing.job.model.CurrentReceivingHU;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.model.ManufacturingJobReference;
import de.metas.manufacturing.job.model.ScaleDevice;
import de.metas.manufacturing.job.service.commands.ReceiveGoodsCommand;
import de.metas.manufacturing.job.service.commands.create_job.ManufacturingJobCreateCommand;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonAggregateToLU;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ManufacturingJobService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUPPOrderBL ppOrderBL;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final PPOrderIssueScheduleService ppOrderIssueScheduleService;
	private final HUReservationService huReservationService;
	private final DeviceAccessorsHubFactory deviceAccessorsHubFactory;
	private final DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy;
	private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

	public ManufacturingJobService(
			final @NonNull PPOrderIssueScheduleService ppOrderIssueScheduleService,
			final @NonNull HUReservationService huReservationService,
			final @NonNull DeviceAccessorsHubFactory deviceAccessorsHubFactory,
			final @NonNull DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy,
			final @NonNull HUQRCodesService huQRCodeService)
	{
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
		this.huReservationService = huReservationService;
		this.deviceAccessorsHubFactory = deviceAccessorsHubFactory;
		this.deviceWebsocketNamingStrategy = deviceWebsocketNamingStrategy;

		this.loadingAndSavingSupportServices = ManufacturingJobLoaderAndSaverSupportingServices.builder()
				.orgDAO(Services.get(IOrgDAO.class))
				.warehouseBL(Services.get(IWarehouseBL.class))
				.productBL(Services.get(IProductBL.class))
				.attributeDAO(Services.get(IAttributeDAO.class))
				.ppOrderBL(ppOrderBL = Services.get(IHUPPOrderBL.class))
				.ppOrderBOMBL(ppOrderBOMBL = Services.get(IPPOrderBOMBL.class))
				.ppOrderRoutingRepository(Services.get(IPPOrderRoutingRepository.class))
				.ppOrderIssueScheduleService(ppOrderIssueScheduleService)
				.huQRCodeService(huQRCodeService)
				.build();
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId) {return newLoader().load(ppOrderId);}

	@NonNull
	private ManufacturingJobLoaderAndSaver newLoader() {return new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices);}

	@NonNull
	private ManufacturingJobLoaderAndSaver newSaver() {return new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices);}

	public Stream<ManufacturingJobReference> streamJobReferencesForUser(
			final @NonNull UserId responsibleId,
			final @NonNull QueryLimit suggestedLimit)
	{
		final ArrayList<ManufacturingJobReference> result = new ArrayList<>();

		//
		// Already started jobs
		streamAlreadyStartedJobs(responsibleId)
				.forEach(result::add);

		//
		// New possible jobs
		if (!suggestedLimit.isLimitHitOrExceeded(result))
		{
			streamJobCandidatesToCreate()
					.limit(suggestedLimit.minusSizeOf(result).toIntOr(Integer.MAX_VALUE))
					.forEach(result::add);
		}

		return result.stream();
	}

	private Stream<ManufacturingJobReference> streamAlreadyStartedJobs(@NonNull final UserId responsibleId)
	{
		return ppOrderBL.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.equalsTo(responsibleId))
						.build())
				.map(ppOrder -> toManufacturingJobReference(ppOrder, true));
	}

	private Stream<ManufacturingJobReference> streamJobCandidatesToCreate()
	{
		return ppOrderBL.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.isNull())
						.build())
				.map(ppOrder -> toManufacturingJobReference(ppOrder, false));
	}

	private ManufacturingJobReference toManufacturingJobReference(final I_PP_Order ppOrder, final boolean isJobStarted)
	{
		return ManufacturingJobReference.builder()
				.ppOrderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
				.documentNo(ppOrder.getDocumentNo())
				.datePromised(InstantAndOrgId.ofTimestamp(ppOrder.getDatePromised(), ppOrder.getAD_Org_ID()).toZonedDateTime(orgDAO::getTimeZone))
				.productName(loadingAndSavingSupportServices.getProductName(ProductId.ofRepoId(ppOrder.getM_Product_ID())))
				.qtyRequiredToProduce(loadingAndSavingSupportServices.getQuantities(ppOrder).getQtyRequiredToProduce())
				.isJobStarted(isJobStarted)
				.build();
	}

	public ManufacturingJob createJob(final PPOrderId ppOrderId, final UserId responsibleId)
	{
		return ManufacturingJobCreateCommand.builder()
				.trxManager(trxManager)
				.ppOrderBL(ppOrderBL)
				.huReservationService(huReservationService)
				.ppOrderIssueScheduleService(ppOrderIssueScheduleService)
				.loadingSupportServices(loadingAndSavingSupportServices)
				//
				.ppOrderId(ppOrderId)
				.responsibleId(responsibleId)
				.build()
				.execute();
	}

	public void abortJob(@NonNull final PPOrderId ppOrderId, @NonNull final UserId responsibleId)
	{
		unassignFromResponsible(ppOrderId, responsibleId);
	}

	private void unassignFromResponsible(final @NonNull PPOrderId ppOrderId, final @NonNull UserId responsibleId)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		final UserId currentResponsibleId = ManufacturingJobLoaderAndSaver.extractResponsibleId(ppOrder);

		//noinspection StatementWithEmptyBody
		if (currentResponsibleId == null)
		{
			// already unassigned, do nothing
		}
		else if (UserId.equals(currentResponsibleId, responsibleId))
		{
			ppOrder.setAD_User_Responsible_ID(-1);
			ppOrderBL.save(ppOrder);
		}
		else
		{
			throw new AdempiereException("Cannot unassign " + ppOrder.getDocumentNo()
					+ " because its assigned to a different responsible than the one we thought (expected: " + responsibleId + ", actual: " + currentResponsibleId + ")");
		}
	}

	public ManufacturingJob withActivityCompleted(ManufacturingJob job, ManufacturingJobActivityId jobActivityId)
	{
		final PPOrderId ppOrderId = job.getPpOrderId();
		final ManufacturingJobActivity jobActivity = job.getActivityById(jobActivityId);
		final PPOrderRoutingActivityId orderRoutingActivityId = jobActivity.getOrderRoutingActivityId();

		boolean jobNeedsReload = changeRoutingActivityStatusToCompleted(ppOrderId, orderRoutingActivityId);

		if (job.isLastActivity(jobActivityId))
		{
			ppOrderBL.closeOrder(ppOrderId);
			jobNeedsReload = true;
		}

		//
		return jobNeedsReload ? getJobById(ppOrderId) : job;

	}

	private boolean changeRoutingActivityStatusToCompleted(final PPOrderId ppOrderId, final PPOrderRoutingActivityId orderRoutingActivityId)
	{
		final ManufacturingJobLoaderAndSaver saver = newSaver();
		final PPOrderRouting orderRouting = saver.getRouting(ppOrderId);
		final PPOrderRouting orderRoutingBeforeChange = orderRouting.copy();
		orderRouting.completeActivity(orderRoutingActivityId);

		boolean jobNeedsReload = false;
		if (!orderRouting.equals(orderRoutingBeforeChange))
		{
			saver.saveRouting(orderRouting);
			jobNeedsReload = true;
		}

		return jobNeedsReload;
	}

	private void saveActivityStatuses(@NonNull final ManufacturingJob job)
	{
		newSaver().saveActivityStatuses(job);
	}

	public ManufacturingJob issueRawMaterials(@NonNull final ManufacturingJob job, @NonNull final PPOrderIssueScheduleProcessRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> issueRawMaterialsInTrx(job, request));
	}

	private ManufacturingJob issueRawMaterialsInTrx(final @NonNull ManufacturingJob job, final @NonNull PPOrderIssueScheduleProcessRequest request)
	{
		final ManufacturingJob changedJob = job.withChangedRawMaterialsIssueStep(request.getIssueScheduleId(), step -> {
			step.assertNotIssued();
			final PPOrderIssueSchedule issueSchedule = ppOrderIssueScheduleService.issue(request);
			return step.withIssued(issueSchedule.getIssued());
		});

		saveActivityStatuses(changedJob);

		return changedJob;
	}

	public ManufacturingJob receiveGoodsAndAggregateToLU(
			@NonNull final ManufacturingJob job,
			@NonNull final FinishedGoodsReceiveLineId lineId,
			@NonNull final JsonAggregateToLU aggregateToLU,
			@NonNull final BigDecimal qtyToReceiveBD,
			@NonNull final ZonedDateTime date)
	{
		final PPOrderId ppOrderId = job.getPpOrderId();

		final ManufacturingJob changedJob = job.withChangedReceiveLine(lineId, line -> {
			final CurrentReceivingHU currentReceivingHU = trxManager.callInThreadInheritedTrx(() -> receiveGoodsAndAggregateToLU(
					ppOrderId,
					line.getCoProductBOMLineId(),
					aggregateToLU,
					qtyToReceiveBD,
					date));

			return line.withCurrentReceivingHU(currentReceivingHU);
		});

		saveActivityStatuses(changedJob);

		return changedJob;
	}

	private CurrentReceivingHU receiveGoodsAndAggregateToLU(
			final @NonNull PPOrderId ppOrderId,
			final @Nullable PPOrderBOMLineId coProductBOMLineId,
			final @NonNull JsonAggregateToLU aggregateToLU,
			final @NonNull BigDecimal qtyToReceiveBD,
			final @NonNull ZonedDateTime date)
	{
		return ReceiveGoodsCommand.builder()
				.handlingUnitsDAO(handlingUnitsDAO)
				.ppOrderBL(ppOrderBL)
				.ppOrderBOMBL(ppOrderBOMBL)
				.loadingAndSavingSupportServices(loadingAndSavingSupportServices)
				//
				.ppOrderId(ppOrderId)
				.coProductBOMLineId(coProductBOMLineId)
				.aggregateToLU(aggregateToLU)
				.qtyToReceiveBD(qtyToReceiveBD)
				.date(date)
				//
				.build().execute();
	}

	public ManufacturingJob withCurrentScaleDevice(@NonNull final ManufacturingJob job, @Nullable final DeviceId currentScaleDeviceId)
	{
		if (!DeviceId.equals(job.getCurrentScaleDeviceId(), currentScaleDeviceId))
		{
			final ManufacturingJob jobChanged = job.withCurrentScaleDevice(currentScaleDeviceId);
			newSaver().saveHeader(jobChanged);
			return jobChanged;
		}
		else
		{
			return job;
		}
	}

	public Optional<ScaleDevice> getCurrentScaleDevice(final ManufacturingJob job)
	{
		final DeviceId currentScaleDeviceId = job.getCurrentScaleDeviceId();
		return currentScaleDeviceId != null
				? getScaleDevice(currentScaleDeviceId)
				: Optional.empty();
	}

	private Optional<ScaleDevice> getScaleDevice(@NonNull final DeviceId currentScaleDeviceId)
	{
		return deviceAccessorsHubFactory
				.getDefaultDeviceAccessorsHub()
				.getDeviceAccessorById(currentScaleDeviceId)
				.map(this::toScaleDevice);
	}

	private ScaleDevice toScaleDevice(@NonNull final DeviceAccessor deviceAccessor)
	{
		return ScaleDevice.builder()
				.deviceId(deviceAccessor.getId())
				.caption(deviceAccessor.getDisplayName())
				.websocketEndpoint(deviceWebsocketNamingStrategy.toWebsocketEndpoint(deviceAccessor.getId()))
				.build();
	}

	public Stream<ScaleDevice> streamAvailableScaleDevices(@NonNull final ManufacturingJob job)
	{
		return deviceAccessorsHubFactory
				.getDefaultDeviceAccessorsHub()
				.getDeviceAccessors(Weightables.ATTR_WeightGross)
				.stream(job.getWarehouseId())
				.map(this::toScaleDevice);
	}

}
