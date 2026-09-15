package de.metas.order.paymentschedule.referenced_docs.prepayment;

import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

/**
 * AP Prepayment (i.e. IsReceipt=N)
 */
@Value
@Builder
public class Prepayment
{
	@NonNull PaymentId id;
	@NonNull LocalDate dateTrx;
	@NonNull LocalDate dateAcct;

	/**
	 * Paid Amount (not AP adjusted)
	 */
	@NonNull Money amount;

	@NonNull InvoiceId proformaInvoiceId;
	@NonNull OrderId orderId;

	public CurrencyId getCurrencyId() {return amount.getCurrencyId();}
}
