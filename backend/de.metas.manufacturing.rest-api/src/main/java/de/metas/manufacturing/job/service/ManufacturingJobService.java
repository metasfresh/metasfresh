package de.metas.manufacturing.job.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.dao.ValueRestriction;
import de.metas.device.accessor.DeviceAccessor;
import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.device.accessor.DeviceId;
import de.metas.device.websocket.DeviceWebsocketNamingStrategy;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleProcessRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.manufacturing.generatedcomponents.ManufacturingComponentGeneratorService;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.model.ManufacturingJobReference;
import de.metas.manufacturing.job.model.ReceivingTarget;
import de.metas.manufacturing.job.model.ScaleDevice;
import de.metas.manufacturing.job.service.commands.ReceiveGoodsCommand;
import de.metas.manufacturing.job.service.commands.create_job.ManufacturingJobCreateCommand;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonReceivingTarget;
import de.metas.material.planning.IResourceDAO;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Service
public class ManufacturingJobService
{
	private static final Logger logger = LogManager.getLogger(ManufacturingJobService.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ManufacturingComponentGeneratorService manufacturingComponentGeneratorService;
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHUPPOrderBL ppOrderBL;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final PPOrderIssueScheduleService ppOrderIssueScheduleService;
	private final HUReservationService huReservationService;
	private final PPOrderSourceHUService ppOrderSourceHUService;
	private final DeviceAccessorsHubFactory deviceAccessorsHubFactory;
	private final DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy;
	private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

	@VisibleForTesting
	static final String SYSCONFIG_defaultFilters = "mobileui.manufacturing.defaultFilters";
	private static final AdMessageKey MSG_ScaleDeviceNotRegistered = AdMessageKey.of("ScaleDeviceNotRegistered");

	public ManufacturingJobService(
			final @NonNull ManufacturingComponentGeneratorService manufacturingComponentGeneratorService,
			final @NonNull PPOrderIssueScheduleService ppOrderIssueScheduleService,
			final @NonNull HUReservationService huReservationService,
			final @NonNull PPOrderSourceHUService ppOrderSourceHUService,
			final @NonNull DeviceAccessorsHubFactory deviceAccessorsHubFactory,
			final @NonNull DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy,
			final @NonNull HUQRCodesService huQRCodeService)
	{
		this.manufacturingComponentGeneratorService = manufacturingComponentGeneratorService;
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
		this.huReservationService = huReservationService;
		this.ppOrderSourceHUService = ppOrderSourceHUService;
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
				.handlingUnitsBL(Services.get(IHandlingUnitsBL.class))
				.ppOrderIssueScheduleService(ppOrderIssueScheduleService)
				.huQRCodeService(huQRCodeService)
				.build();
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId) {return newLoader().load(ppOrderId);}

	public ManufacturingJob assignJob(@NonNull final PPOrderId ppOrderId, @NonNull final UserId newResponsibleId)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		final UserId oldResponsibleId = UserId.ofRepoIdOrNull(ppOrder.getAD_User_Responsible_ID());
		if (oldResponsibleId == null)
		{
			ppOrder.setAD_User_Responsible_ID(newResponsibleId.getRepoId());
			ppOrderBL.save(ppOrder);
		}
		else if (!UserId.equals(oldResponsibleId, newResponsibleId))
		{
			throw new AdempiereException("Already assigned")
					.setParameter("ppOrder", ppOrder)
					.setParameter("oldResponsibleId", oldResponsibleId)
					.setParameter("newResponsibleId", newResponsibleId);
		}

		return newLoader().load(ppOrder);
	}

	@NonNull
	private ManufacturingJobLoaderAndSaver newLoader() {return new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices);}

	@NonNull
	private ManufacturingJobLoaderAndSaver newSaver() {return new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices);}

	public Stream<ManufacturingJobReference> streamJobReferencesForUser(
			final @NonNull UserId responsibleId,
			final @Nullable ResourceId plantId,
			final @NonNull Instant now,
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
			streamJobCandidatesToCreate(responsibleId, plantId, now)
					.limit(suggestedLimit.minusSizeOf(result).toIntOr(Integer.MAX_VALUE))
					.forEach(result::add);
		}

		return result.stream();
	}

	private Stream<ManufacturingJobReference> streamAlreadyStartedJobs(@NonNull final UserId responsibleId)
	{
		return streamAlreadyAssignedManufacturingOrders(responsibleId)
				.map(ppOrder -> toManufacturingJobReference(ppOrder, true));
	}

	private Stream<de.metas.handlingunits.model.I_PP_Order> streamAlreadyAssignedManufacturingOrders(final @NonNull UserId responsibleId)
	{
		return ppOrderBL.streamManufacturingOrders(ManufacturingOrderQuery.builder()
				.onlyCompleted(true)
				.responsibleId(ValueRestriction.equalsTo(responsibleId))
				.build());
	}

	private Stream<ManufacturingJobReference> streamJobCandidatesToCreate(
			@NonNull final UserId userId,
			@Nullable final ResourceId plantId,
			@Nullable Instant now)
	{
		final ManufacturingOrderQuery.ManufacturingOrderQueryBuilder queryBuilder = ManufacturingOrderQuery.builder()
				.onlyCompleted(true)
				.responsibleId(ValueRestriction.isNull());

		Set<ResourceId> onlyPlantIds = plantId != null ? ImmutableSet.of(plantId) : ImmutableSet.of();

		for (ManufacturingJobDefaultFilter defaultFilter : getDefaultFilters())
		{
			switch (defaultFilter)
			{
				case UserPlant:
				{
					final ImmutableSet<ResourceId> userPlantIds = resourceDAO.getResourceIdsByUserId(userId);
					if (!userPlantIds.isEmpty())
					{
						if (onlyPlantIds.isEmpty())
						{
							onlyPlantIds = userPlantIds;
						}
						else
						{
							onlyPlantIds = Sets.intersection(onlyPlantIds, userPlantIds);
						}
					}
					break;
				}
				case TodayDatePromised:
				{
					queryBuilder.datePromisedDay(now);
					break;
				}
				default:
				{
					logger.warn("Skip unhandled default filter option: {}", defaultFilter);
					break;
				}
			}
		}

		return ppOrderBL.streamManufacturingOrders(
						queryBuilder
								.onlyPlantIds(onlyPlantIds)
								.build())
				.map(ppOrder -> toManufacturingJobReference(ppOrder, false));
	}

	ImmutableSet<ManufacturingJobDefaultFilter> getDefaultFilters()
	{
		return sysConfigBL.getCommaSeparatedEnums(SYSCONFIG_defaultFilters, ManufacturingJobDefaultFilter.class);
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
				.ppOrderSourceHUService(ppOrderSourceHUService)
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
		unassignFromResponsible(ppOrder, responsibleId);
	}

	private void unassignFromResponsible(final @NonNull I_PP_Order ppOrder, final @NonNull UserId expectedResponsibleId)
	{
		final UserId currentResponsibleId = ManufacturingJobLoaderAndSaver.extractResponsibleId(ppOrder);

		//noinspection StatementWithEmptyBody
		if (currentResponsibleId == null)
		{
			// already unassigned, do nothing
		}
		else if (UserId.equals(currentResponsibleId, expectedResponsibleId))
		{
			unassignFromResponsible(ppOrder);
		}
		else
		{
			throw new AdempiereException("Cannot un-assign " + ppOrder.getDocumentNo()
					+ " because its assigned to a different responsible than the one we thought (expected: " + expectedResponsibleId + ", actual: " + currentResponsibleId + ")");
		}
	}

	private void unassignFromResponsible(final @NonNull I_PP_Order ppOrder)
	{
		ppOrder.setAD_User_Responsible_ID(-1);
		ppOrderBL.save(ppOrder);
	}

	public void abortAllJobs(@NonNull final UserId responsibleId)
	{
		trxManager.runInThreadInheritedTrx(() -> streamAlreadyAssignedManufacturingOrders(responsibleId).forEach(this::unassignFromResponsible));
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
		final AtomicBoolean processed = new AtomicBoolean();

		final ManufacturingJob changedJob = job.withChangedRawMaterialsIssueStep(
				request.getActivityId(),
				request.getIssueScheduleId(),
				(step) -> {
					if (processed.getAndSet(true))
					{
						// shall not happen
						logger.warn("Ignoring request because was already processed: request={}, step={}", request, step);
						return step;
					}

					step.assertNotIssued();
					final PPOrderIssueSchedule issueSchedule = ppOrderIssueScheduleService.issue(request);
					return step.withIssued(issueSchedule.getIssued());
				});

		if (!processed.get())
		{
			throw new AdempiereException("Failed fulfilling issue request")
					.setParameter("request", request)
					.setParameter("job", job);
		}

		saveActivityStatuses(changedJob);

		return changedJob;
	}

	public ManufacturingJob receiveGoods(
			@NonNull final ManufacturingJob job,
			@NonNull final FinishedGoodsReceiveLineId lineId,
			@NonNull final JsonReceivingTarget receivingTarget,
			@NonNull final BigDecimal qtyToReceiveBD,
			@NonNull final ZonedDateTime date)
	{
		final ManufacturingJob changedJob = job.withChangedReceiveLine(lineId, line -> {
			final ReceivingTarget newReceivingTarget = trxManager.callInThreadInheritedTrx(() -> ReceiveGoodsCommand.builder()
					.handlingUnitsBL(handlingUnitsBL)
					.ppOrderBL(ppOrderBL)
					.ppOrderBOMBL(ppOrderBOMBL)
					.loadingAndSavingSupportServices(loadingAndSavingSupportServices)
					//
					.ppOrderId(job.getPpOrderId())
					.coProductBOMLineId(line.getCoProductBOMLineId())
					.receivingTarget(receivingTarget)
					.qtyToReceiveBD(qtyToReceiveBD)
					.date(date)
					//
					.build().execute());

			return line.withReceivingTarget(newReceivingTarget);
		});

		saveActivityStatuses(changedJob);

		return changedJob;
	}

	public ManufacturingJob withCurrentScaleDevice(@NonNull final ManufacturingJob job, @Nullable final DeviceId currentScaleDeviceId)
	{
		// Make sure the device really exists, to avoid future issues in mobile UI
		if (currentScaleDeviceId != null && !getScaleDevice(currentScaleDeviceId).isPresent())
		{
			throw new AdempiereException(MSG_ScaleDeviceNotRegistered).markAsUserValidationError();
		}

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

	public ManufacturingJob withScannedQRCode(
			@NonNull final ManufacturingJob job,
			@NonNull final ManufacturingJobActivityId jobActivityId,
			@Nullable final GlobalQRCode scannedQRCode)
	{
		// No change
		if(GlobalQRCode.equals(job.getActivityById(jobActivityId).getScannedQRCode(), scannedQRCode))
		{
			return job;
		}

		final ManufacturingJobLoaderAndSaver loaderAndSaver = newSaver();
		final PPOrderId ppOrderId = job.getPpOrderId();
		final PPOrderRouting orderRouting = loaderAndSaver.getRouting(ppOrderId);
		final PPOrderRouting orderRoutingBeforeChange = orderRouting.copy();

		final PPOrderRoutingActivity orderRoutingActivity = orderRouting.getActivityById(jobActivityId.toPPOrderRoutingActivityId(ppOrderId));
		orderRoutingActivity.setScannedQRCode(scannedQRCode);
		orderRoutingActivity.completeIt();

		if (!PPOrderRouting.equals(orderRouting, orderRoutingBeforeChange))
		{
			loaderAndSaver.saveRouting(orderRouting);
		}

		return loaderAndSaver.load(ppOrderId);
	}
}
