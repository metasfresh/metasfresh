package de.metas.manufacturing.job.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.dao.ValueRestriction;
import de.metas.device.accessor.DeviceAccessor;
import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.device.accessor.DeviceId;
import de.metas.device.config.DeviceConfigPoolFactory;
import de.metas.device.websocket.DeviceWebsocketNamingStrategy;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleCreateRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleProcessRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.i18n.AdMessageKey;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.config.ReceiveUnitType;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.model.ManufacturingJobFacets;
import de.metas.manufacturing.job.model.ManufacturingJobReference;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.manufacturing.job.model.ScaleDevice;
import de.metas.manufacturing.job.service.commands.create_job.ManufacturingJobCreateCommand;
import de.metas.manufacturing.job.service.commands.issue.IssueRawMaterialsCommand;
import de.metas.manufacturing.job.service.commands.issue.IssueRawMaterialsCommand.IssueRawMaterialsCommandBuilder;
import de.metas.manufacturing.job.service.commands.issue_what_was_received.IssueWhatWasReceivedCommand;
import de.metas.manufacturing.job.service.commands.issue_what_was_received.IssueWhatWasReceivedCommand.IssueWhatWasReceivedCommandBuilder;
import de.metas.manufacturing.job.service.commands.receive.ReceiveGoodsCommand;
import de.metas.manufacturing.job.service.commands.receive.ReceiveGoodsCommand.ReceiveGoodsCommandBuilder;
import de.metas.manufacturing.job.service.commands.receive.SelectedReceivingTarget;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.material.planning.IResourceDAO;
import de.metas.material.planning.ResourceType;
import de.metas.material.planning.ResourceTypeId;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.RawMaterialsIssueStrategy;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.lang.SeqNo;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import de.metas.workflow.rest_api.service.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ManufacturingJobService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	@NonNull private final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IHUPPOrderQtyBL huPPOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IHUPPOrderBL ppOrderBL = Services.get(IHUPPOrderBL.class);
	@NonNull private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	@NonNull private final PPOrderIssueScheduleService ppOrderIssueScheduleService;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final PPOrderSourceHUService ppOrderSourceHUService;
	@NonNull private final SourceHUsService sourceHUsService;
	@NonNull private final DeviceAccessorsHubFactory deviceAccessorsHubFactory;
	@NonNull private final DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy;
	@NonNull private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;
	@NonNull private final MobileUIManufacturingConfigRepository mobileUIManufacturingConfigRepository;
	@NonNull private final HUQRCodesService huQRCodesService;

	@VisibleForTesting
	static final String SYSCONFIG_defaultFilters = "mobileui.manufacturing.defaultFilters";
	private static final AdMessageKey MSG_ScaleDeviceNotRegistered = AdMessageKey.of("ScaleDeviceNotRegistered");

	public static ManufacturingJobService newInstanceForUnitTesting()
	{
		SpringContextHolder.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				ManufacturingJobService.class,
				() -> new ManufacturingJobService(
						PPOrderIssueScheduleService.newInstanceForUnitTesting(),
						new HUReservationService(new HUReservationRepository()),
						PPOrderSourceHUService.newInstanceForUnitTesting(),
						SourceHUsService.get(),
						new DeviceAccessorsHubFactory(new DeviceConfigPoolFactory()),
						new DeviceWebsocketNamingStrategy("/test/"),
						ManufacturingJobLoaderAndSaverSupportingServices.newInstanceForUnitTesting(),
						new MobileUIManufacturingConfigRepository(),
						SpringContextHolder.instance.getBean(HUQRCodesService.class)
				)
		);
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId) {return newLoader().load(ppOrderId);}

	@NonNull
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

	public interface ManufacturingOrderCollector<T>
	{
		void collect(I_PP_Order ppOrder, boolean isJobStarted);

		Collection<T> getCollectedItems();
	}

	@RequiredArgsConstructor
	private static class ManufacturingJobReferenceCollector implements ManufacturingOrderCollector<ManufacturingJobReference>
	{
		@NonNull private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

		private final ArrayList<ManufacturingJobReference> result = new ArrayList<>();

		@Override
		public void collect(final I_PP_Order ppOrder, final boolean isJobStarted)
		{
			result.add(toManufacturingJobReference(ppOrder, isJobStarted));
		}

		@Override
		public Collection<ManufacturingJobReference> getCollectedItems()
		{
			return result;
		}

		public Stream<ManufacturingJobReference> streamCollectedItems()
		{
			return result.stream();
		}

		private ManufacturingJobReference toManufacturingJobReference(final I_PP_Order ppOrder, final boolean isJobStarted)
		{
			return ManufacturingJobReference.builder()
					.ppOrderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
					.documentNo(ppOrder.getDocumentNo())
					.dateStartSchedule(loadingAndSavingSupportServices.getDateStartSchedule(ppOrder))
					.productName(loadingAndSavingSupportServices.getProductName(ProductId.ofRepoId(ppOrder.getM_Product_ID())))
					.productValue(loadingAndSavingSupportServices.getProductValue(ProductId.ofRepoId(ppOrder.getM_Product_ID())))
					.qtyRequiredToProduce(loadingAndSavingSupportServices.getQuantities(ppOrder).getQtyRequiredToProduce())
					.isJobStarted(isJobStarted)
					.build();
		}
	}

	public Stream<ManufacturingJobReference> streamJobReferencesForUser(@NonNull final ManufacturingJobReferenceQuery query)
	{
		final ManufacturingJobReferenceCollector collector = new ManufacturingJobReferenceCollector(loadingAndSavingSupportServices);
		collect(query, collector);
		return collector.streamCollectedItems();
	}

	@RequiredArgsConstructor
	private static class FacetsCollector implements ManufacturingOrderCollector<ManufacturingJobFacets.Facet>
	{
		@NonNull private final IResourceDAO resourceDAO;

		private final HashSet<ManufacturingJobFacets.Facet> result = new HashSet<>();
		private final HashSet<ResourceId> seenResourceIds = new HashSet<>();
		private final HashSet<ResourceTypeId> seenResourceTypeIds = new HashSet<>();

		@Override
		public void collect(final I_PP_Order ppOrder, final boolean isJobStarted_NOTUSED)
		{
			final ResourceId resourceId = ResourceId.ofRepoId(ppOrder.getS_Resource_ID());
			if (!seenResourceIds.add(resourceId))
			{
				// already considered
				return;
			}

			final ResourceTypeId resourceTypeId = resourceDAO.getResourceTypeIdByResourceId(resourceId);
			if (!seenResourceTypeIds.add(resourceTypeId))
			{
				// already considered
				return;
			}

			final ResourceType resourceType = resourceDAO.getResourceTypeById(resourceTypeId);
			final ManufacturingJobFacets.Facet facet = toFacet(resourceType);

			result.add(facet);
		}

		private static ManufacturingJobFacets.Facet toFacet(final ResourceType resourceType)
		{
			return ManufacturingJobFacets.Facet.of(
					ManufacturingJobFacets.FacetId.ofResourceTypeId(resourceType.getId()),
					resourceType.getCaption());
		}

		@Override
		public Collection<ManufacturingJobFacets.Facet> getCollectedItems()
		{
			return result;
		}

		public ManufacturingJobFacets.FacetsCollection toFacetsCollection()
		{
			return ManufacturingJobFacets.FacetsCollection.ofCollection(result);
		}
	}

	public ManufacturingJobFacets.FacetsCollection getFacets(@NonNull ManufacturingJobReferenceQuery query)
	{
		final FacetsCollector collector = new FacetsCollector(resourceDAO);
		collect(query.withNoLimit(), collector);
		return collector.toFacetsCollection();
	}

	private <T> void collect(
			@NonNull final ManufacturingJobReferenceQuery query,
			@NonNull final ManufacturingOrderCollector<T> collector)
	{
		@NonNull final UserId responsibleId = query.getResponsibleId();
		@NonNull final QueryLimit suggestedLimit = query.getSuggestedLimit();

		//
		// Already started jobs
		streamAlreadyAssignedManufacturingOrders(responsibleId)
				.forEach(ppOrder -> collector.collect(ppOrder, true));

		//
		// New possible jobs
		if (suggestedLimit.isNoLimit() || !suggestedLimit.isLimitHitOrExceeded(collector.getCollectedItems()))
		{
			ppOrderBL.streamManufacturingOrders(toManufacturingOrderQuery(query))
					.limit(suggestedLimit.minusSizeOf(collector.getCollectedItems()).toIntOr(Integer.MAX_VALUE))
					.forEach(ppOrder -> collector.collect(ppOrder, false));
		}
	}

	private Stream<de.metas.handlingunits.model.I_PP_Order> streamAlreadyAssignedManufacturingOrders(@NonNull final UserId responsibleId)
	{
		return ppOrderBL.streamManufacturingOrders(ManufacturingOrderQuery.builder()
				.onlyCompleted(true)
				.responsibleId(ValueRestriction.equalsTo(responsibleId))
				.build());
	}

	private ManufacturingOrderQuery toManufacturingOrderQuery(@NonNull ManufacturingJobReferenceQuery query)
	{
		final ManufacturingJobDefaultFilterCollection defaultFilters = getDefaultFilters();

		final ManufacturingOrderQuery.ManufacturingOrderQueryBuilder queryBuilder = ManufacturingOrderQuery.builder()
				.onlyCompleted(true)
				.responsibleId(ValueRestriction.isNull()) // NOT assigned
				;

		if (query.getPlantOrWorkstationId() != null)
		{
			queryBuilder.onlyPlantOrWorkstationId(query.getPlantOrWorkstationId());
		}

		final InSetPredicate<ResourceId> onlyPlantIds = computeOnlyPlantIds(query, defaultFilters, resourceDAO);
		if (!onlyPlantIds.isAny())
		{
			queryBuilder.onlyPlantIds(onlyPlantIds.toSet());
		}

		if (query.getWorkstationId() != null)
		{
			queryBuilder.onlyWorkstationId(query.getWorkstationId());
		}

		if (defaultFilters.contains(ManufacturingJobDefaultFilter.TodayDateStartSchedule))
		{
			queryBuilder.dateStartScheduleDay(query.getNow());
		}

		//
		return queryBuilder.build();
	}

	private static InSetPredicate<ResourceId> computeOnlyPlantIds(
			@NonNull ManufacturingJobReferenceQuery query,
			@NonNull ManufacturingJobDefaultFilterCollection defaultFilters,
			@NonNull IResourceDAO resourceDAO)
	{
		InSetPredicate<ResourceId> onlyPlantIds = InSetPredicate.any();

		if (!onlyPlantIds.isNone())
		{
			final ImmutableSet<ResourceTypeId> facetResourceTypeIds = query.getActiveFacetIds().getResourceTypeIds();
			if (!facetResourceTypeIds.isEmpty())
			{
				final ImmutableSet<ResourceId> facetPlantIds = resourceDAO.getResourceIdsByResourceTypeIds(facetResourceTypeIds);
				onlyPlantIds = onlyPlantIds.intersectWith(facetPlantIds);
			}
		}

		if (!onlyPlantIds.isNone())
		{
			if (defaultFilters.contains(ManufacturingJobDefaultFilter.UserPlant))
			{
				final ImmutableSet<ResourceId> userPlantIds = resourceDAO.getResourceIdsByUserId(query.getResponsibleId());
				if (!userPlantIds.isEmpty())
				{
					onlyPlantIds = onlyPlantIds.intersectWith(userPlantIds);
				}
			}
		}

		return onlyPlantIds;
	}

	ManufacturingJobDefaultFilterCollection getDefaultFilters()
	{
		return ManufacturingJobDefaultFilterCollection.ofCollection(sysConfigBL.getCommaSeparatedEnums(SYSCONFIG_defaultFilters, ManufacturingJobDefaultFilter.class));
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
				.mobileUIManufacturingConfigRepository(mobileUIManufacturingConfigRepository)
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

	private void unassignFromResponsible(@NonNull final PPOrderId ppOrderId, @NonNull final UserId responsibleId)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		unassignFromResponsible(ppOrder, responsibleId);
	}

	private void unassignFromResponsible(@NonNull final I_PP_Order ppOrder, @NonNull final UserId expectedResponsibleId)
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

	private void unassignFromResponsible(@NonNull final I_PP_Order ppOrder)
	{
		ppOrder.setAD_User_Responsible_ID(-1);
		ppOrderBL.save(ppOrder);
	}

	public void abortAllJobs(@NonNull final UserId responsibleId)
	{
		trxManager.runInThreadInheritedTrx(() -> streamAlreadyAssignedManufacturingOrders(responsibleId).forEach(this::unassignFromResponsible));
	}

	public ManufacturingJob withActivityCompleted(final ManufacturingJob job, final ManufacturingJobActivityId jobActivityId)
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
		return newIssueRawMaterialsCommand()
				//
				.job(job)
				.request(request)
				//
				.build().execute();
	}

	private IssueRawMaterialsCommandBuilder newIssueRawMaterialsCommand()
	{
		return IssueRawMaterialsCommand.builder()
				.trxManager(trxManager)
				.ppOrderIssueScheduleService(ppOrderIssueScheduleService)
				.loadingAndSavingSupportServices(loadingAndSavingSupportServices);
	}

	public ManufacturingJob receiveGoods(
			@NonNull final JsonManufacturingOrderEvent.ReceiveFrom receiveFrom,
			@NonNull final ManufacturingJob job,
			@NonNull final ZonedDateTime date)
	{
		final MobileUIManufacturingConfig config = mobileUIManufacturingConfigRepository.getConfig(job.getResponsibleId(), ClientId.METASFRESH);
		@NonNull final ReceiveUnitType receiveUnitType = config.getReceiveUnitTypeEffective();

		final FinishedGoodsReceiveLine receiveLine = job.getFinishedGoodsReceiveLineById(receiveFrom.getFinishedGoodsReceiveLineId());

		return newReceiveGoodsCommand()
				.job(job)
				.finishedGoodsReceiveLineId(receiveFrom.getFinishedGoodsReceiveLineId())
				.receivingTarget(SelectedReceivingTarget.builder()
						.luReceivingTarget(receiveFrom.getAggregateToLU())
						.tuReceivingTarget(receiveFrom.getAggregateToTU())
						.build())
				.qtyToReceiveBD(receiveFrom.getQtyReceived())
				.date(date)
				.bestBeforeDate(TimeUtil.asLocalDate(receiveFrom.getBestBeforeDate()))
				.productionDate(TimeUtil.asLocalDate(receiveFrom.getProductionDate()))
				.lotNo(receiveFrom.getLotNo())
				.catchWeight(extractTargetCatchWeight(receiveFrom).orElse(null))
				.barcode(receiveFrom.getBarcode())
				.receiveUnitType(receiveUnitType)
				.tuPIItemProductIdForTUMode(receiveLine.getTuPIItemProductId())
				.build().execute();
	}

	private ReceiveGoodsCommandBuilder newReceiveGoodsCommand()
	{
		return ReceiveGoodsCommand.builder()
				.trxManager(trxManager)
				.handlingUnitsBL(handlingUnitsBL)
				.huStatusBL(huStatusBL)
				.ppOrderBL(ppOrderBL)
				.ppOrderBOMBL(ppOrderBOMBL)
				.uomConversionBL(uomConversionBL)
				.jobService(this)
				.loadingAndSavingSupportServices(loadingAndSavingSupportServices);
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
		if (GlobalQRCode.equals(job.getActivityById(jobActivityId).getScannedQRCode(), scannedQRCode))
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

	@NonNull
	public Set<HuId> getFinishedGoodsReceivedHUIds(@NonNull final PPOrderId ppOrderId)
	{
		return huPPOrderQtyBL.getFinishedGoodsReceivedHUIds(ppOrderId);
	}

	@NonNull
	public ManufacturingJob recomputeQtyToIssueForSteps(@NonNull final PPOrderId ppOrderId)
	{
		final ManufacturingJob job = getJobById(ppOrderId);
		final ManufacturingJob changedJob = job.withChangedRawMaterialIssues(
				rawMaterialsIssue -> rawMaterialsIssue.withChangedLines(this::recomputeQtyToIssueForSteps));

		if (!changedJob.equals(job))
		{
			saveActivityStatuses(changedJob);
		}

		return changedJob;
	}

	@NonNull
	private RawMaterialsIssueLine recomputeQtyToIssueForSteps(@NonNull final RawMaterialsIssueLine line)
	{
		Quantity qtyLeftToBeIssued = line.getQtyLeftToIssue().toZeroIfNegative();
		final ImmutableList.Builder<RawMaterialsIssueStep> updatedStepsListBuilder = ImmutableList.builder();

		for (final RawMaterialsIssueStep step : line.getSteps())
		{
			if (step.isIssued())
			{
				updatedStepsListBuilder.add(step);
			}
			else if (qtyLeftToBeIssued.isGreaterThan(step.getQtyToIssue()))
			{
				updatedStepsListBuilder.add(step);
				qtyLeftToBeIssued = qtyLeftToBeIssued.subtract(step.getQtyToIssue());
			}
			else
			{
				ppOrderIssueScheduleService.updateQtyToIssue(step.getId(), qtyLeftToBeIssued);
				updatedStepsListBuilder.add(step.withQtyToIssue(qtyLeftToBeIssued));
				qtyLeftToBeIssued = qtyLeftToBeIssued.toZero();
			}
		}

		return line.withSteps(updatedStepsListBuilder.build());
	}

	@NonNull
	private Optional<Quantity> extractTargetCatchWeight(@NonNull final JsonManufacturingOrderEvent.ReceiveFrom receiveFrom)
	{
		if (receiveFrom.getCatchWeight() == null || Check.isBlank(receiveFrom.getCatchWeightUomSymbol()))
		{
			return Optional.empty();
		}

		return uomDAO.getBySymbol(receiveFrom.getCatchWeightUomSymbol())
				.map(uom -> Quantity.of(receiveFrom.getCatchWeight(), uom));
	}

	public QueryLimit getLaunchersLimit()
	{
		final int limitInt = sysConfigBL.getIntValue(Constants.SYSCONFIG_LaunchersLimit, -100);
		return limitInt == -100
				? Constants.DEFAULT_LaunchersLimit
				: QueryLimit.ofInt(limitInt);
	}

	public ManufacturingJob createOnTheFlyIssueSchedule(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final UserId callerId,
			@NonNull final String huQRCodeString)
	{
		return trxManager.callInThreadInheritedTrx(() -> createOnTheFlyIssueScheduleInTrx(ppOrderId, callerId, huQRCodeString));
	}

	private ManufacturingJob createOnTheFlyIssueScheduleInTrx(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final UserId callerId,
			@NonNull final String huQRCodeString)
	{
		// Validate IsAllowIssuingAnyHU=Y using the PP_Order's client
		final de.metas.handlingunits.model.I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		final ClientId clientId = ClientId.ofRepoId(ppOrder.getAD_Client_ID());
		final MobileUIManufacturingConfig config = mobileUIManufacturingConfigRepository.getConfig(callerId, clientId);
		if (!config.getIsAllowIssuingAnyHU().isTrue())
		{
			throw new AdempiereException("On-the-fly issue schedule creation is only allowed when IsAllowIssuingAnyHU=Y")
					.markAsUserValidationError();
		}

		// Resolve HU from QR code
		final HUQRCode huQRCode = HUQRCode.fromGlobalQRCodeJsonString(huQRCodeString);
		final HuId huId = huQRCodesService.getHuIdByQRCode(huQRCode);

		// Get top-level HU
		final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParent(huId);
		final HuId topLevelHUId = HuId.ofRepoId(topLevelHU.getM_HU_ID());

		// Validate HU status — only Active HUs can be issued
		if (!huStatusBL.isStatusActive(topLevelHU))
		{
			throw new AdempiereException("HU is not active and cannot be issued")
					.setParameter("huId", topLevelHUId)
					.setParameter("huStatus", topLevelHU.getHUStatus())
					.markAsUserValidationError();
		}

		// Validate HU has a locator
		final int locatorRepoId = topLevelHU.getM_Locator_ID();
		if (locatorRepoId <= 0)
		{
			throw new AdempiereException("HU has no locator assigned")
					.setParameter("huId", topLevelHUId)
					.markAsUserValidationError();
		}

		// Get all products stored in the HU
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final List<IHUProductStorage> productStorages = storageFactory.getProductStorages(topLevelHU);

		// Find a BOM line that matches one of the HU's products
		final List<I_PP_Order_BOMLine> bomLines = ppOrderBOMBL.getOrderBOMLines(ppOrderId);
		IHUProductStorage matchingStorage = null;
		PPOrderBOMLineId matchingBomLineId = null;
		ProductId matchingProductId = null;

		for (final I_PP_Order_BOMLine bomLine : bomLines)
		{
			final ProductId bomLineProductId = ProductId.ofRepoId(bomLine.getM_Product_ID());
			matchingStorage = productStorages.stream()
					.filter(ps -> ProductId.equals(ps.getProductId(), bomLineProductId))
					.findFirst()
					.orElse(null);
			if (matchingStorage != null)
			{
				matchingBomLineId = PPOrderBOMLineId.ofRepoId(bomLine.getPP_Order_BOMLine_ID());
				matchingProductId = bomLineProductId;
				break;
			}
		}

		if (matchingStorage == null)
		{
			throw new AdempiereException("HU does not contain any product matching the BOM lines")
					.setParameter("huId", topLevelHUId)
					.setParameter("ppOrderId", ppOrderId)
					.markAsUserValidationError();
		}

		// Validate qty
		final Quantity qtyToIssue = matchingStorage.getQty();
		if (qtyToIssue.isZero())
		{
			throw new AdempiereException("HU has zero quantity for the matching product")
					.setParameter("huId", topLevelHUId)
					.setParameter("productId", matchingProductId)
					.markAsUserValidationError();
		}

		// Determine next seqNo
		final ImmutableList<PPOrderIssueSchedule> existingSchedules = ppOrderIssueScheduleService.getByOrderId(ppOrderId);
		final int maxSeqNo = existingSchedules.stream()
				.mapToInt(s -> s.getSeqNo().toInt())
				.max()
				.orElse(0);
		final SeqNo nextSeqNo = SeqNo.ofInt(maxSeqNo + 10);

		// Get locator
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		final WarehouseId warehouseIdForLocator = warehouseDAO.getWarehouseIdByLocatorRepoId(locatorRepoId);
		final LocatorId locatorId = LocatorId.ofRepoId(warehouseIdForLocator, locatorRepoId);

		// Create schedule
		ppOrderIssueScheduleService.createSchedule(
				PPOrderIssueScheduleCreateRequest.builder()
						.ppOrderId(ppOrderId)
						.ppOrderBOMLineId(matchingBomLineId)
						.seqNo(nextSeqNo)
						.productId(matchingProductId)
						.qtyToIssue(qtyToIssue)
						.issueFromHUId(topLevelHUId)
						.issueFromLocatorId(locatorId)
						.isAlternativeIssue(false)
						.build());

		// Reload and return the updated job
		return getJobById(ppOrderId);
	}

	public ManufacturingJob autoIssueWhatWasReceived(
			@NonNull final ManufacturingJob job,
			@NonNull final RawMaterialsIssueStrategy issueStrategy)
	{
		return newIssueWhatWasReceivedCommand()
				.job(job)
				.issueStrategy(issueStrategy)
				.build().execute();
	}

	public IssueWhatWasReceivedCommandBuilder newIssueWhatWasReceivedCommand()
	{
		return IssueWhatWasReceivedCommand.builder()
				.issueScheduleService(ppOrderIssueScheduleService)
				.jobService(this)
				.ppOrderSourceHUService(ppOrderSourceHUService)
				.sourceHUsService(sourceHUsService);
	}

}
