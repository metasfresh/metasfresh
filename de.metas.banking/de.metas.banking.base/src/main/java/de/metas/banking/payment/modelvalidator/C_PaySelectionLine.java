package de.metas.banking.payment.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.ModelValidator;

import de.metas.banking.model.I_C_BP_BankAccount;
import de.metas.banking.model.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import lombok.NonNull;

@Interceptor(I_C_PaySelectionLine.class)
public class C_PaySelectionLine
{
	public static final transient C_PaySelectionLine instance = new C_PaySelectionLine();

	private static final String MSG_PaySelectionLine_Invoice_InvalidCurrency = "PaySelectionLine.Invoice.InvalidCurrency";

	private C_PaySelectionLine()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeNewOrChange(@NonNull final I_C_PaySelectionLine paySelectionLine)
	{
		paySelectionLine.setDifferenceAmt(
				paySelectionLine.getOpenAmt()
						.subtract(paySelectionLine.getPayAmt())
						.subtract(paySelectionLine.getDiscountAmt()));

		if (InterfaceWrapperHelper.isValueChanged(paySelectionLine, I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID))
		{
			updateFromPaymentRequestOrInvoice(paySelectionLine);
		}

		assertValidInvoiceCurrency(paySelectionLine);
	}

	/**
	 * Updates given pay selection line from invoice's payment request.
	 */
	private void updateFromPaymentRequestOrInvoice(final I_C_PaySelectionLine paySelectionLine)
	{
		final IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);
		final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

		if (paymentRequestBL.isUpdatedFromPaymentRequest(paySelectionLine))
		{
			return; // already updated, nothing to do
		}

		// see if we can update from a payment request
		final boolean updated = paymentRequestBL.updatePaySelectionLineFromPaymentRequestIfExists(paySelectionLine);
		if (!updated)
		{
			// fallback and update from the invoice
			paySelectionBL.updateFromInvoice(paySelectionLine);
		}
	}

	private void assertValidInvoiceCurrency(final I_C_PaySelectionLine paySelectionLine)
	{
		final I_C_PaySelection paySelection = paySelectionLine.getC_PaySelection();
		final I_C_BP_BankAccount bankAccount = InterfaceWrapperHelper.create(paySelection.getC_BP_BankAccount(), I_C_BP_BankAccount.class);

		final I_C_Invoice invoice = paySelectionLine.getC_Invoice();

		//
		// Match currency
		if (invoice.getC_Currency_ID() == bankAccount.getC_Currency_ID())
		{
			return;
		}

		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		final CurrencyCode invoiceCurrencyCode = currenciesRepo.getCurrencyCodeById(CurrencyId.ofRepoId(invoice.getC_Currency_ID()));
		final CurrencyCode bankAccountCurrencyCode = currenciesRepo.getCurrencyCodeById(CurrencyId.ofRepoId(bankAccount.getC_Currency_ID()));

		throw new AdempiereException(MSG_PaySelectionLine_Invoice_InvalidCurrency, new Object[] {
				invoice.getDocumentNo(),     // invoice
				invoiceCurrencyCode.toThreeLetterCode(),      // Actual
				bankAccountCurrencyCode.toThreeLetterCode() }); // Expected
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterNewOrChange(final I_C_PaySelectionLine paySelectionLine)
	{
		final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

		final PaySelectionId paySelectionId = PaySelectionId.ofRepoId(paySelectionLine.getC_PaySelection_ID());
		paySelectionDAO.updatePaySelectionTotalAmt(paySelectionId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(final I_C_PaySelectionLine paySelectionLine)
	{
		final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

		final PaySelectionId paySelectionId = PaySelectionId.ofRepoId(paySelectionLine.getC_PaySelection_ID());
		paySelectionDAO.updatePaySelectionTotalAmt(paySelectionId);
	}

}
