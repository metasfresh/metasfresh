package de.metas.order.model.validator;

import de.metas.order.IOrderBL;
import de.metas.request.RequestTypeId;
import de.metas.request.service.async.spi.impl.R_Request_CreateFromOrder_Async;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import java.util.Optional;

@Interceptor(I_C_Order.class)
public class C_Order
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createRequest(@NonNull final de.metas.adempiere.model.I_C_Order order)
	{
		final Optional<RequestTypeId> requestType = orderBL.getRequestTypeForCreatingNewRequestsAfterComplete(order);
		if (requestType.isPresent())
		{
			R_Request_CreateFromOrder_Async.createWorkpackage(order);
		}

	}

}
