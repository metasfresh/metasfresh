package de.metas.banking.payment.modelvalidator;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;

@Interceptor(I_C_PaySelectionLine.class)
public class C_PaySelectionLine
{
	private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
	private final ModelCacheInvalidationService modelCacheInvalidationService = ModelCacheInvalidationService.get();
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);

	private static final AdMessageKey MSG_PaySelectionLine_Document_InvalidCurrency = AdMessageKey.of("PaySelectionLine.Document.InvalidCurrency");



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

		//
		// Match currency
		final CurrencyId documentCurrencyId = getDocumentCurrencyId(paySelectionLine);
		final CurrencyId bankAccountCurrencyId = CurrencyId.ofRepoId(bankAccount.getC_Currency_ID());
		if (CurrencyId.equals(documentCurrencyId, bankAccountCurrencyId))
		{
			return;
		}

		final CurrencyCode documentCurrencyCode = currenciesRepo.getCurrencyCodeById(documentCurrencyId);
		final CurrencyCode bankAccountCurrencyCode = currenciesRepo.getCurrencyCodeById(bankAccountCurrencyId);

		throw new AdempiereException(MSG_PaySelectionLine_Document_InvalidCurrency,
				documentCurrencyCode.toThreeLetterCode(),      // Actual
				bankAccountCurrencyCode.toThreeLetterCode()) // Expected
				.markAsUserValidationError();
	}

	private CurrencyId getDocumentCurrencyId(final I_C_PaySelectionLine paySelectionLine)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(paySelectionLine.getC_Invoice_ID());
		final PaymentId originalPaymentId = PaymentId.ofRepoIdOrNull(paySelectionLine.getOriginal_Payment_ID());
		
		if (invoiceId != null)
		{
			final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
			return CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		}
		else if (originalPaymentId != null)
		{
			final I_C_Payment payment = paymentBL.getById(originalPaymentId);
			return CurrencyId.ofRepoId(payment.getC_Currency_ID());
		}
		else
		{
			throw new AdempiereException("No invoice or original payment found for " + paySelectionLine);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE,  ModelValidator.TYPE_AFTER_DELETE })
	public void afterNewOrChangeOrDelete(final I_C_PaySelectionLine paySelectionLine)
	{
		updatePaySelectionAmount(paySelectionLine);
	}

	private void updatePaySelectionAmount(@NonNull final I_C_PaySelectionLine paySelectionLine)
	{
		final PaySelectionId paySelectionId = PaySelectionId.ofRepoId(paySelectionLine.getC_PaySelection_ID());
		paySelectionBL.updatePaySelectionTotalAmt(paySelectionId);

		// make sure the is invalidated *after* the change is visible for everyone
		trxManager
				.getCurrentTrxListenerManagerOrAutoCommit()
				.runAfterCommit(() -> modelCacheInvalidationService.invalidate(
						CacheInvalidateMultiRequest.fromTableNameAndRecordId(I_C_PaySelection.Table_Name, paySelectionId.getRepoId()),
						ModelCacheInvalidationTiming.AFTER_CHANGE));
	}
}
