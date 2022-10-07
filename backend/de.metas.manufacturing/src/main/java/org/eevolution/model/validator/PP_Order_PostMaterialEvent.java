package org.eevolution.model.validator;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;

/**
 * A dedicated model interceptor whose job it is to fire events on the {@link MetasfreshEventBusService}.<br>
 * I add this into a dedicated interceptor (as opposed to adding the method to {@link PP_Order}) because there is at least one test case where I want {@link PP_Order} to be invoked without events being fired.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Interceptor(I_PP_Order.class)
public class PP_Order_PostMaterialEvent
{
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPOrderBL ppOrderService = Services.get(IPPOrderBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

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
		trxManager.runAfterCommit(() -> postPPOrderCreatedEvent(ppOrderRecord, type));
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
	public void postMaterialEvent_ppOrderDocStatusChange(@NonNull final I_PP_Order ppOrderRecord)
	{
		final PPOrderChangedEvent changeEvent = PPOrderChangedEventFactory
				.newWithPPOrderBeforeChange(ppOrderConverter, ppOrderRecord)
				.inspectPPOrderAfterChange();

		materialEventService.postEventAfterNextCommit(changeEvent);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_PP_Order.COLUMNNAME_QtyDelivered)
	public void postMaterialEvent_qtyDelivered(@NonNull final I_PP_Order ppOrderRecord)
	{
		final PPOrderChangedEvent changeEvent = PPOrderChangedEventFactory
				.newWithPPOrderBeforeChange(ppOrderConverter, ppOrderRecord)
				.inspectPPOrderAfterChange();

		materialEventService.postEventAfterNextCommit(changeEvent);
	}

	private void postPPOrderCreatedEvent(@NonNull final I_PP_Order ppOrderRecord, @NonNull final ModelChangeType type)
	{
		final boolean newPPOrder = type.isNew() || ModelChangeUtil.isJustActivated(ppOrderRecord);
		if (!newPPOrder)
		{
			return;
		}

		if (isPPOrderCreatedFromCandidate(ppOrderRecord))
		{
			// dev-note: see org.eevolution.productioncandidate.service.produce.PPOrderProducerFromCandidate#postPPOrderCreatedEvent(I_PP_Order)
			return;
		}

		ppOrderService.postPPOrderCreatedEvent(ppOrderRecord);
	}

	private boolean isPPOrderCreatedFromCandidate(@NonNull final I_PP_Order ppOrderRecord)
	{
		final ImmutableList<I_PP_OrderCandidate_PP_Order> orderAllocations = ppOrderDAO.getPPOrderAllocations(PPOrderId.ofRepoId(ppOrderRecord.getPP_Order_ID()));

		return !orderAllocations.isEmpty();
	}
}
