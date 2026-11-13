package de.metas.invoice.paymentbonus;

import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.tax.api.Tax;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;

/**
 * One bonus product and project of a {@link PaymentBonusDeduction}: it becomes one line of the payment bonus credit memo.
 */
@Value
@Builder(toBuilder = true)
public class PaymentBonusDeductionLine
{
	/** The product of the credit memo line; the line is booked on its accounts. */
	@NonNull ProductId bonusProductId;

	/** The project of the invoice lines this part of the bonus was earned on; {@code null} for the part earned on lines without project. The credit memo line gets this project. */
	@Nullable ProjectId projectId;

	/** The tax of the bonus product; the credit memo line gets this tax. */
	@NonNull Tax tax;

	/** The bonus without VAT; the VAT is added on top. */
	@With
	@NonNull Money netAmt;
}
