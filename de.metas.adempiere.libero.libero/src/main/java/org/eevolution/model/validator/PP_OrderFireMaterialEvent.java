package org.eevolution.model.validator;

import org.adempiere.ad.modelvalidator.InterceptorUtil;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.eevolution.event.PPOrderRequestedEventHandler;
import org.eevolution.model.I_PP_Order;

import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderChangedDocStatusEvent;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
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

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void fireMaterialEvent_newPPOrder(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final ModelChangeType type)
	{
		final boolean newPPOrder = type.isNew() || InterceptorUtil.isJustActivated(ppOrder);
		if (!newPPOrder)
		{
			return;
		}

		final PPOrderPojoConverter pojoSupplier = Adempiere.getBean(PPOrderPojoConverter.class);
		final PPOrder ppOrderPojo = pojoSupplier.asPPOrderPojo(ppOrder);
		final int groupIdFromPPOrderRequestedEvent = PPOrderRequestedEventHandler.ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.getValue(ppOrder, 0);

		final PPOrderCreatedEvent event = PPOrderCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(ppOrder))
				.ppOrder(ppOrderPojo)
				.groupId(groupIdFromPPOrderRequestedEvent)
				.build();

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		materialEventService.fireEventAfterNextCommit(event);
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void fireMaterialEvent_deletedPPOrder(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final ModelChangeType type)
	{
		final boolean deletedPPOrder = type.isDelete() || InterceptorUtil.isJustDeactivated(ppOrder);
		if (!deletedPPOrder)
		{
			return;
		}

		final PPOrderDeletedEvent event = PPOrderDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(ppOrder))
				.ppOrderId(ppOrder.getPP_Order_ID())
				.build();

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		materialEventService.fireEventAfterNextCommit(event);
	}

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = I_PP_Order.COLUMNNAME_DocStatus)
	public void fireMaterialEvent_ppOrderDocStatusChange(@NonNull final I_PP_Order ppOrder)
	{
		final PPOrderChangedDocStatusEvent event = PPOrderChangedDocStatusEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(ppOrder))
				.ppOrderId(ppOrder.getPP_Order_ID())
				.newDocStatus(ppOrder.getDocStatus())
				.build();

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		materialEventService.fireEventAfterNextCommit(event);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_CLOSE)
	public void fireMaterialEvent_ppOrderClosed(@NonNull final I_PP_Order ppOrderRecord)
	{
		final PPOrderPojoConverter ppOrderPojoConverter = Adempiere.getBean(PPOrderPojoConverter.class);
		final PPOrder ppOrder = ppOrderPojoConverter.asPPOrderPojo(ppOrderRecord);

	}
}
