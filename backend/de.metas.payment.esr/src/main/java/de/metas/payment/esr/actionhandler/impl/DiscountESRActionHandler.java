package de.metas.payment.esr.actionhandler.impl;

import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TrxRunnable;

import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#EESR_PAYMENT_ACTION_Discount}. This handler discounts the open amount of the line's invoice. For lines that don't have an
 * invoice, the handler does nothing.
 * 
 */
public class DiscountESRActionHandler extends AbstractESRActionHandler
{
	final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		super.process(line, message);

		Check.assumeNotNull(line.getESR_Payment_Action(), "@" + ESRConstants.ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION + "@");

		final I_C_Invoice invoice = line.getC_Invoice();
		if (invoice == null)
		{
			// We have nothing to do, but the line should still be flagged as processed.
			return true;
		}

		// sanity check: there must be an payment with a negative OverUnderAmt

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		final I_C_Payment payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		Check.assumeNotNull(payment, "Null payment for line {}", line.getESR_ImportLine_ID());
		Check.errorIf(payment.getOverUnderAmt().signum() > 0, "Exces payment for line {}. Can't discount this", line.getESR_ImportLine_ID());

		final Amount discount = invoiceDAO.retrieveOpenAmt(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));

		trxManager.runInThreadInheritedTrx(new TrxRunnable()
		{
			@Override
			public void run(String trxName) throws Exception
			{
				invoiceBL.discountInvoice(invoice, discount, payment.getDateAcct());
			}
		});
		
		return true;
	}

}
