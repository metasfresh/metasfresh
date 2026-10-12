package de.metas.invoice.paymentbonus;

import com.google.common.annotations.VisibleForTesting;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Looks up the payment bonus that a payment on a sales invoice may book ("Abzug bei Zahlung").
 * The calculation itself is behind {@link PaymentBonusDeductionProvider}, because it lives in a module that this one cannot depend on.
 */
@Service
public class PaymentBonusOnPaymentService
{
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
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

	/**
	 * Pre-fills the payment bonus of a drafted receipt for an invoice, like the Skonto: the bonus is the deduction's gross amount, and the payment amount leaves it out:
	 * {@code PayAmt = open - DiscountAmt - PaymentBonusAmt - WriteOffAmt - OverUnderAmt}.
	 * Like the allocation view, the bonus is not pre-filled (so 0) when it is bigger than the open amount minus the Skonto.
	 * <p>
	 * Without a deduction (no „Abzug bei Zahlung“ contract, or a payment in another currency than the invoice) a bonus left over from before is set to 0 and added back to the payment amount;
	 * a payment without a bonus is left as it is.
	 * Does nothing for a payment that is not a drafted receipt with an invoice.
	 */
	public void updatePaymentBonusFromInvoice(@NonNull final I_C_Payment payment)
	{
		if (!isDraftedReceiptWithInvoice(payment))
		{
			return;
		}

		final PaymentBonusDeduction deduction = computeDeduction(payment).orElse(null);
		if (deduction == null)
		{
			final BigDecimal previousPaymentBonusAmt = payment.getPaymentBonusAmt();
			if (previousPaymentBonusAmt.signum() != 0)
			{
				payment.setPaymentBonusAmt(BigDecimal.ZERO);
				payment.setPayAmt(payment.getPayAmt().add(previousPaymentBonusAmt));
			}
			return;
		}

		final BigDecimal openAmt = paymentDAO.getInvoiceOpenAmount(payment, true);
		final BigDecimal maxPaymentBonusAmt = openAmt.subtract(payment.getDiscountAmt());
		final BigDecimal deductionGrossAmt = deduction.getGrossAmount().toBigDecimal();
		final BigDecimal paymentBonusAmt = deductionGrossAmt.compareTo(maxPaymentBonusAmt) <= 0 ? deductionGrossAmt : BigDecimal.ZERO;

		payment.setPaymentBonusAmt(paymentBonusAmt);
		payment.setPayAmt(openAmt
				.subtract(payment.getDiscountAmt())
				.subtract(paymentBonusAmt)
				.subtract(payment.getWriteOffAmt())
				.subtract(payment.getOverUnderAmt()));
	}

	/**
	 * Lets the payment bonus of a drafted receipt for an invoice be at most what the payment leaves open: a customer who pays the full amount gets no bonus.
	 * With a deduction, {@code PaymentBonusAmt} becomes the largest amount the payment bonus credit memo can reach that is neither above the deduction's gross amount
	 * nor above {@code max(0, open - DiscountAmt - PayAmt)}, see {@link PaymentBonusDeduction#computeLargestReachableGrossAmountNotAbove(Money)}.
	 * Without a deduction, or for a payment that is not a drafted receipt with an invoice, {@code PaymentBonusAmt} is left unchanged.
	 */
	public void capPaymentBonusToShortfall(@NonNull final I_C_Payment payment)
	{
		if (!isDraftedReceiptWithInvoice(payment))
		{
			return;
		}

		final PaymentBonusDeduction deduction = computeDeduction(payment).orElse(null);
		if (deduction == null)
		{
			return;
		}

		final BigDecimal openAmt = paymentDAO.getInvoiceOpenAmount(payment, true);
		final Money shortfall = Money.of(openAmt.subtract(payment.getDiscountAmt()).subtract(payment.getPayAmt()), deduction.getCurrencyId());

		payment.setPaymentBonusAmt(deduction.computeLargestReachableGrossAmountNotAbove(shortfall).toBigDecimal());
	}

	private Optional<PaymentBonusDeduction> computeDeduction(@NonNull final I_C_Payment payment)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(payment.getC_Invoice_ID());
		final CurrencyId paymentCurrencyId = CurrencyId.ofRepoIdOrNull(payment.getC_Currency_ID());
		if (paymentCurrencyId == null)
		{
			return Optional.empty();
		}
		return computeDeduction(invoiceId, paymentCurrencyId);
	}

	private static boolean isDraftedReceiptWithInvoice(@NonNull final I_C_Payment payment)
	{
		return payment.getC_Invoice_ID() > 0
				&& payment.isReceipt()
				&& DocStatus.ofNullableCodeOrUnknown(payment.getDocStatus()).isDrafted();
	}
}
