package de.metas.invoice.paymentbonus;

import com.google.common.annotations.VisibleForTesting;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Looks up the payment bonus that a payment on a sales invoice may book ("Abzug bei Zahlung").
 * The calculation itself is behind {@link PaymentBonusDeductionProvider}, because it lives in a module that this one cannot depend on.
 */
@Service
public class PaymentBonusOnPaymentService
{
	private final Optional<PaymentBonusDeductionProvider> provider;

	public PaymentBonusOnPaymentService(@NonNull final Optional<PaymentBonusDeductionProvider> provider)
	{
		this.provider = provider;
	}

	@VisibleForTesting
	public static PaymentBonusOnPaymentService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				PaymentBonusOnPaymentService.class,
				() -> new PaymentBonusOnPaymentService(Optional.empty()));
	}

	/**
	 * @return the bonus for the invoice; empty if there is no provider, the provider has none, or the payment is not in the currency of the bonus (and so of the invoice)
	 */
	public Optional<PaymentBonusDeduction> computeDeduction(@NonNull final InvoiceId invoiceId, @NonNull final CurrencyId paymentCurrencyId)
	{
		return provider
				.flatMap(p -> p.computeForInvoice(invoiceId))
				.filter(deduction -> CurrencyId.equals(deduction.getCurrencyId(), paymentCurrencyId));
	}
}
