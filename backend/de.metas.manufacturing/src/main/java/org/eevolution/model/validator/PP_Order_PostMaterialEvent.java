package org.eevolution.model.validator;

import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Order;

/**
 * A dedicated model interceptor whose job it is to fire events on the {@link MetasfreshEventBusService}.<br>
 * I add this into a dedicated interceptor (as opposed to adding the method to {@link PP_Order}) because there is at least one test case where I want {@link PP_Order} to be invoked without events being fired.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Interceptor(I_PP_Order.class)
public class PP_Order_PostMaterialEvent
{
	private final PPOrderPojoConverter ppOrderConverter;
	private final PostMaterialEventService materialEventService;

	public PP_Order_PostMaterialEvent(
			@NonNull final PPOrderPojoConverter ppOrderConverter,
			@NonNull final PostMaterialEventService materialEventService)
	{
		this.ppOrderConverter = ppOrderConverter;
		this.materialEventService = materialEventService;
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void postMaterialEvent_newPPOrder(
			@NonNull final I_PP_Order ppOrderRecord,
			@NonNull final ModelChangeType type)
	{
		final boolean newPPOrder = type.isNew() || ModelChangeUtil.isJustActivated(ppOrderRecord);
		if (!newPPOrder)
		{
			return;
		}

		final PPOrder ppOrderPojo = ppOrderConverter.toPPOrder(ppOrderRecord);

		final PPOrderCreatedEvent ppOrderCreatedEvent = PPOrderCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ppOrderRecord.getAD_Client_ID(), ppOrderRecord.getAD_Org_ID()))
				.ppOrder(ppOrderPojo)
				.build();

		materialEventService.postEventAfterNextCommit(ppOrderCreatedEvent);
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void fireMaterialEvent_deletedPPOrder(
			@NonNull final I_PP_Order ppOrderRecord,
			@NonNull final ModelChangeType type)
	{
		final boolean deletedPPOrder = type.isDelete() || ModelChangeUtil.isJustDeactivated(ppOrderRecord);
		if (!deletedPPOrder)
		{
			return;
		}

		final PPOrderDeletedEvent event = PPOrderDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ppOrderRecord.getAD_Client_ID(), ppOrderRecord.getAD_Org_ID()))
				.ppOrderId(ppOrderRecord.getPP_Order_ID())
				.build();

		materialEventService.postEventAfterNextCommit(event);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_COMPLETE,
			// Note: close is currently handled in MPPOrder.closeIt()
			// for the other timings, we shall first figure out what we actually want
			//ModelValidator.TIMING_AFTER_REACTIVATE,
			//ModelValidator.TIMING_AFTER_UNCLOSE,
			//ModelValidator.TIMING_AFTER_VOID
	})
	public void postMaterialEvent_ppOrderDocStatusChange(
			@NonNull final I_PP_Order ppOrderRecord,
			@NonNull final DocTimingType type)
	{
		final PPOrderChangedEvent changeEvent = PPOrderChangedEventFactory
				.newWithPPOrderBeforeChange(ppOrderConverter, ppOrderRecord)
				.inspectPPOrderAfterChange();

		materialEventService.postEventAfterNextCommit(changeEvent);
	}
}
