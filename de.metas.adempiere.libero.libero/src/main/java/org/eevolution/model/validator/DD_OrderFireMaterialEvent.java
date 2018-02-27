package org.eevolution.model.validator;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.mrp.spi.impl.ddorder.DDOrderProducer;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrder.DDOrderBuilder;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.event.pporder.PPOrderDocStatusChangedEvent;
import de.metas.material.planning.ddorder.DDOrderUtil;
import lombok.NonNull;

/**
 * A dedicated model interceptor whose job it is to fire events on the {@link MetasfreshEventBusService}.<br>
 * I add this into a dedicated interceptor (as opposed to adding the method to {@link DD_Order}) because there is at least one test case where I want {@link PP_Order} to be invoked without events being fired.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Interceptor(I_DD_Order.class)
public class DD_OrderFireMaterialEvent
{

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void fireMaterialEvent(@NonNull final I_DD_Order ddOrder)
	{
		// when going with @DocAction, here the ppOrder's docStatus would still be "IP" even if we are invoked on afterComplete..
		// also, it might still be rolled back
		// those aren't show-stoppers, but we therefore rather work with @ModelChange

		final List<DDOrderCreatedEvent> events = createEvents(ddOrder);

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		events.forEach(event -> materialEventService.postEventAfterNextCommit(event));
	}

	private DDOrderBuilder createAndInitPPOrderPojoBuilder(@NonNull final I_DD_Order ddOrder)
	{
		final DDOrderBuilder ddOrderPojoBuilder = DDOrder.builder()
				.datePromised(ddOrder.getDatePromised())
				.ddOrderId(ddOrder.getDD_Order_ID())
				.docStatus(ddOrder.getDocStatus())
				.orgId(ddOrder.getAD_Org_ID())
				.plantId(ddOrder.getPP_Plant_ID())
				.productPlanningId(ddOrder.getPP_Product_Planning_ID())
				.shipperId(ddOrder.getM_Shipper_ID());
		return ddOrderPojoBuilder;
	}

	private List<DDOrderCreatedEvent> createEvents(@NonNull final I_DD_Order ddOrder)
	{
		final DDOrderBuilder ddOrderPojoBuilder = createAndInitPPOrderPojoBuilder(ddOrder);

		final List<DDOrderCreatedEvent> events = new ArrayList<>();

		final int groupIdFromDDOrderRequestedEvent = DDOrderProducer.ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID.getValue(ddOrder, 0);
		ddOrderPojoBuilder.materialDispoGroupId(groupIdFromDDOrderRequestedEvent);

		final List<I_DD_OrderLine> ddOrderLines = Services.get(IDDOrderDAO.class).retrieveLines(ddOrder);
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			final int durationDays = DDOrderUtil.calculateDurationDays(
					ddOrder.getPP_Product_Planning(), ddOrderLine.getDD_NetworkDistributionLine());

			ddOrderPojoBuilder.line(createDDOrderLinePojo(ddOrderLine, ddOrder, durationDays));

			final DDOrderCreatedEvent event = DDOrderCreatedEvent.builder()
					.eventDescriptor(EventDescriptor.createNew(ddOrder))
					.ddOrder(ddOrderPojoBuilder.build())
					.fromWarehouseId(ddOrderLine.getM_Locator().getM_Warehouse_ID())
					.toWarehouseId(ddOrderLine.getM_LocatorTo().getM_Warehouse_ID())
					.build();

			events.add(event);
		}
		return events;
	}

	private DDOrderLine createDDOrderLinePojo(
			@NonNull final I_DD_OrderLine ddOrderLine,
			@NonNull final I_DD_Order ddOrder,
			final int durationDays)
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);

		final int bPartnerId = ddOrderLine.getC_BPartner_ID() > 0 ? ddOrderLine.getC_BPartner_ID() : ddOrder.getC_BPartner_ID();

		return DDOrderLine.builder()
				.productDescriptor(productDescriptorFactory.createProductDescriptor(ddOrderLine))
				.bPartnerId(bPartnerId)
				.ddOrderLineId(ddOrderLine.getDD_OrderLine_ID())
				.qty(ddOrderLine.getQtyDelivered())
				.networkDistributionLineId(ddOrderLine.getDD_NetworkDistributionLine_ID())
				.salesOrderLineId(ddOrderLine.getC_OrderLineSO_ID())
				.durationDays(durationDays)
				.build();
	}

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = I_DD_Order.COLUMNNAME_DocStatus)
	public void postMaterialEvent_ddOrderDocStatusChange(@NonNull final I_DD_Order ddOrder)
	{
		final PPOrderDocStatusChangedEvent event = PPOrderDocStatusChangedEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(ddOrder))
				.ppOrderId(ddOrder.getDD_Order_ID())
				.newDocStatus(ddOrder.getDocStatus())
				.build();

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		materialEventService.postEventAfterNextCommit(event);
	}
}
