package de.metas.order.model.validator;

import de.metas.request.service.async.spi.impl.R_Request_CreateFromOrder_Async;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_DocType;

@Interceptor(I_C_Order.class)
public class C_Order
{
	@Init
	public void onInit()
	{
		// Setup event bus topics on which swing client notification listener shall subscribe
		//   Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(InOutUserNotificationsProducer.EVENTBUS_TOPIC);
		//   Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(ReturnInOutUserNotificationsProducer.EVENTBUS_TOPIC);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createRequest(@NonNull final de.metas.adempiere.model.I_C_Order order)
	{
		if (order.getC_DocTypeTarget_ID() == X_C_DocType.DOCTYPE_TEST_APPLIANCE)
		{
			R_Request_CreateFromOrder_Async.createWorkpackage(order.getC_Order_ID());
		}

	}

}
