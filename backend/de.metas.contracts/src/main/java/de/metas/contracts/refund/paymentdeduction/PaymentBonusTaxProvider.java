package de.metas.contracts.refund.paymentdeduction;

import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;

/**
 * Finds the tax of a bonus product for the payment bonus credit memo of a sales invoice.
 */
@FunctionalInterface
public interface PaymentBonusTaxProvider
{
	Tax getTax(@NonNull I_C_Invoice salesInvoice, @NonNull ProductId bonusProductId);
}
