package de.metas.distribution.ddorder.lowlevel.interceptor;

import de.metas.document.engine.DocStatus;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.validator.PP_Order;
import org.springframework.stereotype.Component;

/**
 * A dedicated model interceptor whose job it is to fire events on the {@link MetasfreshEventBusService}.<br>
 * I add this into a dedicated interceptor (as opposed to adding the method to {@link DD_Order}) because there is at least one test case where I want {@link PP_Order} to be invoked without events being fired.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Interceptor(I_DD_Order.class)
@Component
@RequiredArgsConstructor
public class DD_Order_PostMaterialEvent
{
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull final private final DDOrderLowLevelService ddOrderLowLevelService;
	@NonNull final private final ReplenishInfoRepository replenishInfoRepository;
	@NonNull final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final PostMaterialEventService materialEventService;


	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void fireMaterialEvent(@NonNull final I_DD_Order ddOrder)
	{
		// when going with @DocAction, here the ppOrder's docStatus would still be "IP" even if we are invoked on afterComplete..
		// also, it might still be rolled back
		// those aren't show-stoppers, but we therefore rather work with @ModelChange

		final List<DDOrderCreatedEvent> events = createEvents(ddOrder);

		final PostMaterialEventService materialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
		events.forEach(materialEventService::enqueueEventAfterNextCommit);
	}

	@NonNull
	public static DDOrderBuilder createAndInitPPOrderPojoBuilder(@NonNull final I_DD_Order ddOrderRecord)
	{
		return DDOrder.builder()
				.datePromised(TimeUtil.asInstantNonNull(ddOrderRecord.getDatePromised()))
				.ddOrderId(ddOrderRecord.getDD_Order_ID())
				.docStatus(ddOrderRecord.getDocStatus())
				.orgId(OrgId.ofRepoId(ddOrderRecord.getAD_Org_ID()))
				.plantId(ddOrderRecord.getPP_Plant_ID())
				.productPlanningId(ddOrderRecord.getPP_Product_Planning_ID())
				.shipperId(ddOrderRecord.getM_Shipper_ID())
				.simulated(ddOrderRecord.isSimulated());
	}

	@NonNull
	private List<DDOrderCreatedEvent> createEvents(@NonNull final I_DD_Order ddOrderRecord)
	{
		final DDOrderBuilder ddOrderPojoBuilder = createAndInitPPOrderPojoBuilder(ddOrderRecord);

		final List<DDOrderCreatedEvent> events = new ArrayList<>();

		final MaterialDispoGroupId groupIdFromDDOrderRequestedEvent = DDOrderProducer.ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID.getValue(ddOrderRecord);
		ddOrderPojoBuilder.materialDispoGroupId(groupIdFromDDOrderRequestedEvent);

		final List<I_DD_OrderLine> ddOrderLines = ddOrderLowLevelService.retrieveLines(ddOrderRecord);
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			final int durationDays = DDOrderUtil.calculateDurationDays(
					ddOrderRecord.getPP_Product_Planning(), ddOrderLine.getDD_NetworkDistributionLine());

			ddOrderPojoBuilder.lines(ImmutableList.of(createDDOrderLinePojo(replenishInfoRepository, ddOrderLine, ddOrderRecord, durationDays)));
			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(ddOrderRecord.getAD_Client_ID(), ddOrderRecord.getAD_Org_ID());

			final DDOrder ddOrder = ddOrderPojoBuilder.build();

			final DDOrderCreatedEvent event = DDOrderCreatedEvent.builder()
					.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(clientAndOrgId, getDDOrderRequestedEventTrace(ddOrderRecord)))
					.ddOrder(ddOrder)
					.fromWarehouseId(warehouseDAO.getWarehouseIdByLocatorRepoId(ddOrderLine.getM_Locator_ID()))
					.toWarehouseId(warehouseDAO.getWarehouseIdByLocatorRepoId(ddOrderLine.getM_LocatorTo_ID()))
					.build();

			events.add(event);
		}
		return events;
	}

	public static DDOrderLine createDDOrderLinePojo(
			@NonNull final ReplenishInfoRepository replenishInfoRepository,
			@NonNull final I_DD_OrderLine ddOrderLine,
			@NonNull final I_DD_Order ddOrder,
			final int durationDays)
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = SpringContextHolder.instance.getBean(ModelProductDescriptorExtractor.class);

		final int bPartnerId = CoalesceUtil.firstGreaterThanZero(ddOrderLine.getC_BPartner_ID(), ddOrder.getC_BPartner_ID());

		final ReplenishInfo replenishInfo = replenishInfoRepository.getBy(
				WarehouseId.ofRepoId(ddOrder.getM_Warehouse_From_ID()), // both from-warehouse and product are mandatory DB-columns
				ProductId.ofRepoId(ddOrderLine.getM_Product_ID()));

		return DDOrderLine.builder()
				.productDescriptor(productDescriptorFactory.createProductDescriptor(ddOrderLine))
				.bPartnerId(bPartnerId)
				.ddOrderLineId(ddOrderLine.getDD_OrderLine_ID())
				.qty(ddOrderLine.getQtyDelivered())
				.qtyPending(ddOrderLine.getQtyOrdered().subtract(ddOrderLine.getQtyDelivered()))
				.networkDistributionLineId(ddOrderLine.getDD_NetworkDistributionLine_ID())
				.salesOrderLineId(ddOrderLine.getC_OrderLineSO_ID())
				.durationDays(durationDays)
				.fromWarehouseMinMaxDescriptor(replenishInfo.toMinMaxDescriptor())
				.build();
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_DD_Order.COLUMNNAME_DocStatus)
	public void postMaterialEvent_ddOrderDocStatusChange(@NonNull final I_DD_Order ddOrder)
	{
		materialEventService.enqueueEventAfterNextCommit(
				DDOrderDocStatusChangedEvent.builder()
						.eventDescriptor(EventDescriptor.ofClientAndOrg(ddOrder.getAD_Client_ID(), ddOrder.getAD_Org_ID()))
						.ddOrderId(ddOrder.getDD_Order_ID())
				.newDocStatus(DocStatus.ofCode(ddOrder.getDocStatus()))
				.build());
	}

	@NonNull
	private static String getDDOrderRequestedEventTrace(@NonNull final I_DD_Order ddOrderRecord)
	{
		return DDOrderProducer.ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID.getValue(ddOrderRecord);
	}
}
