package de.metas.invoice.paymentbonus;

import com.google.common.annotations.VisibleForTesting;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Looks up the payment bonus that a payment on a sales invoice may book ("Abzug bei Zahlung").
 * The calculation itself is behind {@link PaymentBonusDeductionProvider}, because it lives in a module that this one cannot depend on.
 */
@Service
public class PaymentBonusOnPaymentService
{
	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final Optional<PaymentBonusDeductionProvider> provider;
	@NonNull private final PaymentBonusCreditMemoService creditMemoService;

	public PaymentBonusOnPaymentService(
			@NonNull final Optional<PaymentBonusDeductionProvider> provider,
			@NonNull final PaymentBonusCreditMemoService creditMemoService)
	{
		this.provider = provider;
		this.creditMemoService = creditMemoService;
	}

	@VisibleForTesting
	public static PaymentBonusOnPaymentService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				PaymentBonusOnPaymentService.class,
				() -> new PaymentBonusOnPaymentService(Optional.empty(), new PaymentBonusCreditMemoService()));
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
	 * Called right after the amounts were set from the invoice (the „Rechnung“ callout, or {@code PaymentDAO.updateDiscountAndPayment} on a new payment date).
	 * Without a deduction (no „Abzug bei Zahlung“ contract, or a payment in another currency than the invoice) a bonus left over from before is set to 0
	 * and PayAmt is computed with the same formula; a payment without a bonus is left as it is.
	 * Does nothing for a payment that is not a drafted receipt with an invoice.
	 */
	public void updatePaymentBonusFromInvoice(@NonNull final I_C_Payment payment)
	{
		if (!isDraftedReceiptWithInvoice(payment))
		{
			return;
		}

		final PaymentBonusDeduction deduction = computeDeduction(payment).orElse(null);
		if (deduction == null && payment.getPaymentBonusAmt().signum() == 0)
		{
			return; // nothing to pre-fill and nothing to drop
		}

		final BigDecimal openAmt = paymentDAO.getInvoiceOpenAmount(payment, true);
		final BigDecimal maxPaymentBonusAmt = openAmt.subtract(payment.getDiscountAmt());
		final BigDecimal deductionGrossAmt = deduction != null ? deduction.getGrossAmount().toBigDecimal() : BigDecimal.ZERO;
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
	 * The bonus is only ever lowered, never raised: a bonus the user lowered or set to 0 stays so, and only the pre-fill ({@link #updatePaymentBonusFromInvoice(I_C_Payment)}) sets it from the deduction.
	 * With a deduction, {@code PaymentBonusAmt} becomes the largest amount that the payment bonus credit memo can reach and that is not above
	 * {@code min(PaymentBonusAmt, max(0, open - DiscountAmt - PayAmt))}, see {@link PaymentBonusDeduction#withLargestReachableGrossAmountNotAbove(Money)}.
	 * Without a deduction, or for a payment that is not a drafted receipt with an invoice, {@code PaymentBonusAmt} is left unchanged.
	 */
	public void capPaymentBonusToShortfall(@NonNull final I_C_Payment payment)
	{
		if (!isDraftedReceiptWithInvoice(payment) || payment.getPaymentBonusAmt().signum() <= 0)
		{
			return;
		}

		final PaymentBonusDeduction deduction = computeDeduction(payment).orElse(null);
		if (deduction == null)
		{
			return;
		}

		final BigDecimal openAmt = paymentDAO.getInvoiceOpenAmount(payment, true);
		final Money maxPaymentBonus = computeMaxPaymentBonus(payment, openAmt, deduction.getCurrencyId());

		payment.setPaymentBonusAmt(deduction.withLargestReachableGrossAmountNotAbove(maxPaymentBonus)
				.map(PaymentBonusDeduction::getGrossAmount)
				.map(Money::toBigDecimal)
				.orElse(BigDecimal.ZERO));
	}

	/**
	 * Books the payment bonus of a payment that is being completed with a payment bonus credit memo, in the caller's thread-inherited transaction, and links it in {@code PaymentBonus_CreditMemo_ID}.
	 * The credit memo's amount is the largest reachable one that is not above the bonus nor above what the payment leaves open; it can differ from {@code PaymentBonusAmt}.
	 *
	 * @param allocationDate the credit memo's invoice and accounting date
	 * @return empty if {@code PaymentBonusAmt} is 0
	 */
	public Optional<PaymentBonusCreditMemoBooking> generateCreditMemoIfAny(@NonNull final I_C_Payment payment, @NonNull final LocalDate allocationDate)
	{
		if (payment.getPaymentBonusAmt().signum() == 0)
		{
			return Optional.empty();
		}

		final PaymentBonusDeduction deduction = getDeductionOrThrow(payment);
		final BigDecimal openAmtBeforeCreditMemo = paymentDAO.getInvoiceOpenAmount(payment, true);
		final PaymentBonusDeduction deductionToBook = limitToWhatThePaymentLeavesOpen(deduction, payment, openAmtBeforeCreditMemo);

		final InvoiceId creditMemoId = creditMemoService.generateCreditMemo(deductionToBook, allocationDate);
		payment.setPaymentBonus_CreditMemo_ID(creditMemoId.getRepoId());

		return Optional.of(PaymentBonusCreditMemoBooking.builder()
				.creditMemoGrandTotal(invoiceBL.getById(creditMemoId).getGrandTotal())
				.invoiceOpenAmtBeforeCreditMemo(openAmtBeforeCreditMemo)
				.build());
	}

	private PaymentBonusDeduction getDeductionOrThrow(@NonNull final I_C_Payment payment)
	{
		return computeDeduction(payment)
				.orElseThrow(() -> new AdempiereException("No payment bonus can be deducted for the payment's invoice")
						.appendParametersToMessage()
						.setParameter("C_Payment_ID", payment.getC_Payment_ID())
						.setParameter("C_Invoice_ID", payment.getC_Invoice_ID())
						.setParameter("PaymentBonusAmt", payment.getPaymentBonusAmt()));
	}

	private static PaymentBonusDeduction limitToWhatThePaymentLeavesOpen(
			@NonNull final PaymentBonusDeduction deduction,
			@NonNull final I_C_Payment payment,
			@NonNull final BigDecimal invoiceOpenAmt)
	{
		final Money maxPaymentBonus = computeMaxPaymentBonus(payment, invoiceOpenAmt, deduction.getCurrencyId());
		return deduction.withLargestReachableGrossAmountNotAbove(maxPaymentBonus)
				.orElseThrow(() -> new AdempiereException("The payment bonus is not covered by what the payment leaves open of the invoice")
						.appendParametersToMessage()
						.setParameter("C_Payment_ID", payment.getC_Payment_ID())
						.setParameter("C_Invoice_ID", payment.getC_Invoice_ID())
						.setParameter("PaymentBonusAmt", payment.getPaymentBonusAmt())
						.setParameter("maxPaymentBonus", maxPaymentBonus));
	}

	/**
	 * @return {@code min(PaymentBonusAmt, invoiceOpenAmt - DiscountAmt - PayAmt)}
	 */
	private static Money computeMaxPaymentBonus(
			@NonNull final I_C_Payment payment,
			@NonNull final BigDecimal invoiceOpenAmt,
			@NonNull final CurrencyId currencyId)
	{
		final Money shortfall = Money.of(invoiceOpenAmt.subtract(payment.getDiscountAmt()).subtract(payment.getPayAmt()), currencyId);
		return shortfall.min(Money.of(payment.getPaymentBonusAmt(), currencyId));
	}

	/**
	 * Sets the payment bonus of a drafted receipt for an invoice to 0 when it can no longer be deducted, e.g. because the payment is now in another currency than the invoice.
	 * A deductible bonus and the other amounts are left as they are, like the Skonto.
	 *
	 * @return true if the bonus was set to 0, so the caller has to recompute the write-off
	 */
	public boolean clearPaymentBonusIfNotDeductible(@NonNull final I_C_Payment payment)
	{
		if (!isDraftedReceiptWithInvoice(payment) || payment.getPaymentBonusAmt().signum() == 0)
		{
			return false;
		}
		if (computeDeduction(payment).isPresent())
		{
			return false;
		}

		payment.setPaymentBonusAmt(BigDecimal.ZERO);
		return true;
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
