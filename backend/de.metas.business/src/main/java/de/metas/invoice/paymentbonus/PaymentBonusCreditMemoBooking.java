package de.metas.invoice.paymentbonus;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/**
 * The grand total of the payment bonus credit memo that a payment's completion created, with the invoice's open amount from before that credit memo.
 */
@Value
@Builder
public class PaymentBonusCreditMemoBooking
{
	@NonNull BigDecimal creditMemoGrandTotal;
	/**
	 * In the payment's currency. It still contains the bonus, because the credit memo allocates itself against the invoice.
	 */
	@NonNull BigDecimal invoiceOpenAmtBeforeCreditMemo;
}
