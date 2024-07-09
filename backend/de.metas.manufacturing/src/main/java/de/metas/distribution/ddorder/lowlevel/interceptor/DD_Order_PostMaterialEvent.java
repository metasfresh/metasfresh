package de.metas.distribution.ddorder.lowlevel.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddorder.material_dispo.DDOrderProducer;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrder.DDOrderBuilder;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DDOrderUtil;
import de.metas.material.replenish.ReplenishInfo;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.validator.PP_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * A dedicated model interceptor whose job it is to fire events on the {@link MetasfreshEventBusService}.<br>
 * I add this into a dedicated interceptor (as opposed to adding the method to {@link DD_Order}) because there is at least one test case where I want {@link PP_Order} to be invoked without events being fired.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Interceptor(I_DD_Order.class)
@Component
public class DD_Order_PostMaterialEvent
{
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final DDOrderLowLevelService ddOrderLowLevelService;
	private final ReplenishInfoRepository replenishInfoRepository;

	public DD_Order_PostMaterialEvent(
			@NonNull final DDOrderLowLevelService ddOrderLowLevelService,
			@NonNull final ReplenishInfoRepository replenishInfoRepository)
	{
		this.ddOrderLowLevelService = ddOrderLowLevelService;
		this.replenishInfoRepository = replenishInfoRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void fireMaterialEvent(@NonNull final I_DD_Order ddOrder)
	{
		// when going with @DocAction, here the ppOrder's docStatus would still be "IP" even if we are invoked on afterComplete..
		// also, it might still be rolled back
		// those aren't show-stoppers, but we therefore rather work with @ModelChange

		if (ddOrder.isSimulated())
		{
			return;
		}

		// dev-note: running after commit to make sure the DD_OrderLines are created
		trxManager.runAfterCommit(() -> {
			final List<DDOrderCreatedEvent> events = createEvents(ddOrder);

			final PostMaterialEventService materialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
			events.forEach(materialEventService::enqueueEventAfterNextCommit);
		});
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
			final ProductPlanning productPlanning = getProductPlanning(ddOrderRecord);
			final int durationDays = DDOrderUtil.calculateDurationDays(
					productPlanning, ddOrderLine.getDD_NetworkDistributionLine());

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

	@Nullable
	private ProductPlanning getProductPlanning(final @NonNull I_DD_Order ddOrderRecord)
	{
		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoIdOrNull(ddOrderRecord.getPP_Product_Planning_ID());
		return productPlanningId != null ? productPlanningDAO.getById(productPlanningId) : null;
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

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = I_DD_Order.COLUMNNAME_DocStatus)
	public void postMaterialEvent_ddOrderDocStatusChange(@NonNull final I_DD_Order ddOrder)
	{
		final DDOrderDocStatusChangedEvent event = DDOrderDocStatusChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ddOrder.getAD_Client_ID(), ddOrder.getAD_Org_ID()))
				.ddOrderId(ddOrder.getDD_Order_ID())
				.newDocStatus(ddOrder.getDocStatus())
				.build();

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		materialEventService.enqueueEventAfterNextCommit(event);
	}

	@NonNull
	private static String getDDOrderRequestedEventTrace(@NonNull final I_DD_Order ddOrderRecord)
	{
		return DDOrderProducer.ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID.getValue(ddOrderRecord);
	}
}
