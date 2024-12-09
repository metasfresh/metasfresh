package de.metas.invoice.service.impl;

<<<<<<< HEAD
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.document.IDocCopyHandler;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceCreditContext;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Services;
<<<<<<< HEAD
=======
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/**
 * Note: This class is currently instantiated and called directly from BLs in this package.<br>
 * <p>
 * IMPORTANT: this copy handler is special in that it could also complete the target invoice! Make sure that it's called after the other "generic" handlers.<br>
 * Also see the javadoc of {@link IInvoiceBL#creditInvoice(de.metas.adempiere.model.I_C_Invoice, InvoiceCreditContext)}.
 */
class CreditMemoInvoiceCopyHandler implements IDocCopyHandler<I_C_Invoice, I_C_InvoiceLine>
{
	private final InvoiceCreditContext creditCtx;

	public CreditMemoInvoiceCopyHandler(final InvoiceCreditContext creditCtx)
	{
		this.creditCtx = creditCtx;
	}

	@Override
	public void copyPreliminaryValues(final I_C_Invoice from, final I_C_Invoice to)
	{
		// do nothing. this is already done by the default copy handler
	}

	@Override
<<<<<<< HEAD
	public void copyValues(final I_C_Invoice from, final I_C_Invoice to)
=======
	public void copyValues(@NonNull final I_C_Invoice from, @NonNull final I_C_Invoice to)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final de.metas.adempiere.model.I_C_Invoice invoice = InterfaceWrapperHelper.create(from, de.metas.adempiere.model.I_C_Invoice.class);
		final de.metas.adempiere.model.I_C_Invoice creditMemo = InterfaceWrapperHelper.create(to, de.metas.adempiere.model.I_C_Invoice.class);

		if (creditCtx.isReferenceInvoice())
		{
			creditMemo.setRef_Invoice_ID(invoice.getC_Invoice_ID());
		}

		creditMemo.setIsCreditedInvoiceReinvoicable(creditCtx.isCreditedInvoiceReinvoicable()); // task 08927

		completeAndAllocateCreditMemo(invoice, creditMemo);
	}

	@Override
	public CreditMemoInvoiceLineCopyHandler getDocLineCopyHandler()
	{
		return CreditMemoInvoiceLineCopyHandler.getInstance();
	}

	private void completeAndAllocateCreditMemo(final de.metas.adempiere.model.I_C_Invoice invoice, final de.metas.adempiere.model.I_C_Invoice creditMemo)
	{
		if (!creditCtx.isCompleteAndAllocate())
		{
			Services.get(IDocumentBL.class).processEx(creditMemo, IDocument.ACTION_Prepare, IDocument.STATUS_InProgress);

			// nothing left to do
			return;
		}

		Services.get(IDocumentBL.class).processEx(creditMemo, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		// allocate invoice against credit memo will be done in the model validator of creditmemo complete
	}

	/**
	 * Returns the header item class, i.e. <code>I_C_Invoice</code>.
	 */
	@Override
	public Class<I_C_Invoice> getSupportedItemsClass()
	{
		return I_C_Invoice.class;
	}
}
