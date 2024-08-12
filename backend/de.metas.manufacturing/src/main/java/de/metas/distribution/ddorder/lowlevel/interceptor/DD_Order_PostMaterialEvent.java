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
	@NonNull final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final PostMaterialEventService materialEventService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_DD_Order.COLUMNNAME_DocStatus)
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
