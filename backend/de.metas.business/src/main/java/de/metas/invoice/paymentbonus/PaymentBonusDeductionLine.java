package de.metas.invoice.paymentbonus;

import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

/**
 * One bonus product of a {@link PaymentBonusDeduction}: it becomes one line of the payment bonus credit memo.
 */
@Value
@Builder(toBuilder = true)
public class PaymentBonusDeductionLine
{
	/** The product of the credit memo line; the line is booked on its accounts. */
	@NonNull ProductId bonusProductId;

	/** The tax of the bonus product; the credit memo line gets this tax. */
	@NonNull Tax tax;

	/** The bonus without VAT; the VAT is added on top. */
	@With
	@NonNull Money netAmt;
}
