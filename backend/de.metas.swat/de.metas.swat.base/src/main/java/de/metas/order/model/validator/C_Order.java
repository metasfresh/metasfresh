package de.metas.order.model.validator;

import de.metas.document.IDocTypeDAO;
import de.metas.request.service.async.spi.impl.R_Request_CreateFromOrder_Async;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

@Interceptor(I_C_Order.class)
public class C_Order
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createRequest(@NonNull final de.metas.adempiere.model.I_C_Order order)
	{
		final I_C_DocType docType = docTypeDAO.getById(order.getC_DocTypeTarget_ID());
		if (docType.getR_RequestType_ID() != 0)
		{
			R_Request_CreateFromOrder_Async.createWorkpackage(order);
		}

	}

}
