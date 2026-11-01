package de.metas.invoice.paymentbonus;

import de.metas.invoice.InvoiceId;
import lombok.NonNull;

import java.util.Optional;

/**
 * SPI that lets {@code de.metas.business} reach the payment bonus calculation, which lives in {@code de.metas.contracts}.
 */
public interface PaymentBonusDeductionProvider
{
	/**
	 * @return the bonus that the customer may deduct when paying the given invoice; empty if there is none
	 */
	Optional<PaymentBonusDeduction> computeForInvoice(@NonNull InvoiceId invoiceId);
}
