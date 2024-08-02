package de.metas.distribution.ddorder.lowlevel.interceptor;

import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.document.engine.DocStatus;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.validator.PP_Order;
import org.springframework.stereotype.Component;

import static de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO.ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID;

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
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final DDOrderLowLevelService ddOrderLowLevelService;
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;
	@NonNull private final PostMaterialEventService materialEventService;

	private static String getDDOrderRequestedEventTrace(@NonNull final I_DD_Order ddOrderRecord)
	{
		return ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID.getValue(ddOrderRecord);
	}

	private DDOrderLoader newLoader()
	{
		return DDOrderLoader.builder()
				.productPlanningDAO(productPlanningDAO)
				.warehouseDAO(warehouseDAO)
				.distributionNetworkRepository(distributionNetworkRepository)
				.ddOrderLowLevelService(ddOrderLowLevelService)
				.replenishInfoRepository(replenishInfoRepository)
				.build();
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void fireMaterialEvent(@NonNull final I_DD_Order ddOrderRecord)
	{
		// when going with @DocAction, here the DD_Order's docStatus would still be "IP" even if we are invoked on afterComplete.
		// also, it might still be rolled back
		// those aren't show-stoppers, but we therefore rather work with @ModelChange

		final DDOrder ddOrder = newLoader().load(ddOrderRecord);
		final String traceId = getDDOrderRequestedEventTrace(ddOrderRecord);
		materialEventService.postEventAfterNextCommit(DDOrderCreatedEvent.of(ddOrder, traceId));
	}

	@ModelChange(
			timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_DD_Order.COLUMNNAME_DocStatus)
	public void postMaterialEvent_ddOrderDocStatusChange(@NonNull final I_DD_Order ddOrder)
	{
		materialEventService.postEventAfterNextCommit(
				DDOrderDocStatusChangedEvent.builder()
						.eventDescriptor(EventDescriptor.ofClientAndOrg(ddOrder.getAD_Client_ID(), ddOrder.getAD_Org_ID()))
						.ddOrderId(ddOrder.getDD_Order_ID())
						.newDocStatus(DocStatus.ofCode(ddOrder.getDocStatus()))
						.build()
		);
	}

}
