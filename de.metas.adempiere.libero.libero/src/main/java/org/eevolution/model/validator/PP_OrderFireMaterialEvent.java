package org.eevolution.model.validator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.event.PPOrderRequestedEventHandler;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.document.engine.IDocumentBL;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrder.PPOrderBuilder;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.pporder.PPOrderUtil;
import lombok.NonNull;

/**
 * A dedicated model interceptor whose job it is to fire events on the {@link MaterialEventService}.<br>
 * I add this into a dedicated interceptor (as opposed to adding the method to {@link PP_Order}) because there is at least one test case where I want {@link PP_Order} to be invoked without events being fired.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Interceptor(I_PP_Order.class)
public class PP_OrderFireMaterialEvent
{

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE
	}, ifColumnsChanged = I_PP_Order.COLUMNNAME_DocStatus)
	public void fireMaterialEvent(final I_PP_Order ppOrder)
	{
		// when going with @DocAction, at this point the ppOrder's docStatus would still be "IP" even if we are invoked on afterComplete..
		// also, it might still be rolled back
		// those aren't show-stoppers, but we therefore rather work with @ModelChange
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
		if (!docActionBL.isDocumentCompletedOrClosed(ppOrder))
		{
			// quick workaround for https://github.com/metasfresh/metasfresh/issues/1581
			// don't send an event while the PP_Order is not yet completed.
			// this doesn't help when a PP_Order is reactivated
			return;
		}

		final PPOrder ppOrderPojo = createPPOrderPojo(ppOrder);
		final int groupIdFromPPOrderRequestedEvent = PPOrderRequestedEventHandler.ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.getValue(ppOrder, 0);

		final PPOrderAdvisedOrCreatedEvent event = PPOrderAdvisedOrCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(ppOrder))
				.ppOrder(ppOrderPojo)
				.groupId(groupIdFromPPOrderRequestedEvent)
				.build();

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		materialEventService.fireEventAfterNextCommit(event, InterfaceWrapperHelper.getTrxName(ppOrder));
	}

	private PPOrder createPPOrderPojo(@NonNull final I_PP_Order ppOrder)
	{
		final PPOrderBuilder ppOrderPojoBuilder = createPPorderPojoBuilder(ppOrder);

		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);

		final List<I_PP_Order_BOMLine> orderBOMLines = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrder);
		for (final I_PP_Order_BOMLine line : orderBOMLines)
		{
			ppOrderPojoBuilder.line(PPOrderLine.builder()
					.productDescriptor(productDescriptorFactory.createProductDescriptor(line))
					.description(line.getDescription())
					.ppOrderLineId(line.getPP_Order_BOMLine_ID())
					.productBomLineId(line.getPP_Product_BOMLine_ID())
					.qtyRequired(line.getQtyRequiered())
					.receipt(PPOrderUtil.isReceipt(line.getComponentType()))
					.build());
		}
		return ppOrderPojoBuilder.build();
	}

	private PPOrderBuilder createPPorderPojoBuilder(@NonNull final I_PP_Order ppOrder)
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);

		final PPOrderBuilder ppOrderPojoBuilder = PPOrder.builder()
				.datePromised(ppOrder.getDatePromised())
				.dateStartSchedule(ppOrder.getDateStartSchedule())
				.docStatus(ppOrder.getDocStatus())
				.orderLineId(ppOrder.getC_OrderLine_ID())
				.orgId(ppOrder.getAD_Org_ID())
				.plantId(ppOrder.getS_Resource_ID())
				.ppOrderId(ppOrder.getPP_Order_ID())
				.productDescriptor(productDescriptorFactory.createProductDescriptor(ppOrder))
				.productPlanningId(ppOrder.getPP_Product_Planning_ID())
				.quantity(ppOrder.getQtyOrdered())
				.uomId(ppOrder.getC_UOM_ID())
				.warehouseId(ppOrder.getM_Warehouse_ID())
				.bPartnerId(ppOrder.getC_BPartner_ID())
				.orderLineId(ppOrder.getC_OrderLine_ID());
		return ppOrderPojoBuilder;
	}

}
