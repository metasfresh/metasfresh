package de.metas.invoicecandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;

@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_QtyDelivered // Using C_OrderLine.QtyDelivered to get notified of changes in the delivery status.
					, I_C_OrderLine.COLUMNNAME_QtyOrdered // task 08452: make sure the IC gets invalidated when we sort of "close" a single line
					, I_C_OrderLine.COLUMNNAME_QtyOrderedOverUnder
					, I_C_OrderLine.COLUMNNAME_IsPackagingMaterial
			})
	public void invalidateInvoiceCandidates(final I_C_OrderLine ol)
	{
		Services.get(IInvoiceCandidateHandlerBL.class).invalidateCandidatesFor(ol);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteInvoiceCandidates(final I_C_OrderLine ol)
	{
		Services.get(IInvoiceCandDAO.class).deleteAllReferencingInvoiceCandidates(ol);
	}
}
