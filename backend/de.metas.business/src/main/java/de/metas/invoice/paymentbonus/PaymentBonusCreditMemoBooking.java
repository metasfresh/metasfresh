package de.metas.invoice.paymentbonus;

import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The grand total of the payment bonus credit memo that a payment's completion created, with the invoice's open amount from before that credit memo.
 */
@Value
@Builder
public class PaymentBonusCreditMemoBooking
{
	@NonNull Money creditMemoGrandTotal;
	/**
	 * It still contains the bonus, because the credit memo allocates itself against the invoice.
	 */
	@NonNull Money invoiceOpenAmtBeforeCreditMemo;
}
