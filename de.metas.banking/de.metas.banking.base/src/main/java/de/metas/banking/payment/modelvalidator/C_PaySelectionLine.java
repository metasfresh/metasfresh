package de.metas.banking.payment.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.interfaces.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BP_BankAccount;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;

@Interceptor(I_C_PaySelectionLine.class)
public class C_PaySelectionLine
{
	public static final transient C_PaySelectionLine instance = new C_PaySelectionLine();

	private static final String MSG_PaySelectionLine_Invoice_InvalidCurrency = "PaySelectionLine.Invoice.InvalidCurrency";

	private C_PaySelectionLine()
	{
		super();
	}

	/**
	 * Updates given pay selection line from invoice's payment request.
	 *
	 * @param paySelectionLine
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID)
	public void updateFromPaymentRequestOrInvoice(final I_C_PaySelectionLine paySelectionLine)
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

	/**
	 * Remove line if currency does not match.
	 *
	 * @param paySelectionLine
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validateInvoiceCurrency(final I_C_PaySelectionLine paySelectionLine)
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

		final CurrencyCode invoiceCurrencyCode = getCurrencyCodeById(invoice.getC_Currency_ID());
		final CurrencyCode bankAccountCurrencyCode = getCurrencyCodeById(bankAccount.getC_Currency_ID());

		throw new AdempiereException(MSG_PaySelectionLine_Invoice_InvalidCurrency, new Object[] {
				invoice.getDocumentNo(),     // invoice
				invoiceCurrencyCode.toThreeLetterCode(),      // Actual
				bankAccountCurrencyCode.toThreeLetterCode() }); // Expected
	}

	private CurrencyCode getCurrencyCodeById(final int currencyRepoId)
	{
		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		return currenciesRepo.getCurrencyCodeById(CurrencyId.ofRepoId(currencyRepoId));
	}
}
