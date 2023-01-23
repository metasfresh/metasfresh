package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryInstructionUserNotificationsProducer;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.event.IEventBusFactory;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ShipperTransportation.class)
@Component
public class M_ShipperTransportation
{
	private final DeliveryPlanningService deliveryPlanningService;
	@Init
	public void onInit()
	{
		// Setup event bus topics on which client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(DeliveryInstructionUserNotificationsProducer.EVENTBUS_TOPIC);
	}

	public M_ShipperTransportation(@NonNull final DeliveryPlanningService deliveryPlanningService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_VOID)
	public void unlinkDeliveryPlannings(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		deliveryPlanningService.unlinkDeliveryPlannings(shipperTransportation.getDocumentNo());

	}
}
