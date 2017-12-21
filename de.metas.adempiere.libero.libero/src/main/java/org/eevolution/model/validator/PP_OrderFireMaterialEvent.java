package org.eevolution.model.validator;

import org.adempiere.ad.modelvalidator.InterceptorUtil;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.eevolution.event.PPOrderRequestedEventHandler;
import org.eevolution.model.I_PP_Order;

import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
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
		if(!newPPOrder)
		{
			return;
		}
		// when going with @DocAction, at this point the ppOrder's docStatus would still be "IP" even if we are invoked on afterComplete..
		// also, it might still be rolled back
		// those aren't show-stoppers, but we therefore rather work with @ModelChange
//		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
//		if (!docActionBL.isDocumentCompletedOrClosed(ppOrder))
//		{
//			// quick workaround for https://github.com/metasfresh/metasfresh/issues/1581
//			// don't send an event while the PP_Order is not yet completed.
//			// this doesn't help when a PP_Order is reactivated
//			return;
//		}

		final PPOrderPojoConverter pojoSupplier = Adempiere.getBean(PPOrderPojoConverter.class);
		final PPOrder ppOrderPojo = pojoSupplier.asPPOrderPojo(ppOrder);
		final int groupIdFromPPOrderRequestedEvent = PPOrderRequestedEventHandler.ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.getValue(ppOrder, 0);

		final PPOrderCreatedEvent event = PPOrderCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(ppOrder))
				.ppOrder(ppOrderPojo)
				.groupId(groupIdFromPPOrderRequestedEvent)
				.build();

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		materialEventService.fireEventAfterNextCommit(event, InterfaceWrapperHelper.getTrxName(ppOrder));
	}


}
