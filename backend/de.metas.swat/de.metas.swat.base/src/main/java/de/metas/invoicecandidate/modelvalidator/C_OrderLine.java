package de.metas.invoicecandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.util.Services;

@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_QtyOrdered // task 08452: make sure the IC gets invalidated when we sort of "close" a single line
					, I_C_OrderLine.COLUMNNAME_QtyOrderedOverUnder
					, I_C_OrderLine.COLUMNNAME_IsPackagingMaterial
					, I_C_OrderLine.COLUMNNAME_M_Product_ID
			})
	public void invalidateInvoiceCandidates(final I_C_OrderLine ol)
	{
		Services.get(IInvoiceCandidateHandlerBL.class).invalidateCandidatesFor(ol);
	}

	/**
	 * Cascades the delete onto the {@code C_Invoice_Candidate}s of an order line, guarding against deleting a
	 * sales order line whose candidate already has an invoice line on a non-voided/reversed invoice.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteInvoiceCandidates(final I_C_OrderLine ol)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		if (ol.getC_Order().isSOTrx())
		{
			invoiceCandDAO.deleteOrGuardReferencingInvoiceCandidates(ol);
		}
		else
		{
			invoiceCandDAO.deleteAllReferencingInvoiceCandidates(ol);
		}
	}
}
