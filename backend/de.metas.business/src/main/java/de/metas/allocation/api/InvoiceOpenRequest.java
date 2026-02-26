package de.metas.allocation.api;

import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class InvoiceOpenRequest
{
	@NonNull InvoiceId invoiceId;
	@NonNull @Builder.Default DateColumn dateColumn = DateColumn.DateTrx;
	/**
	 * The currency of the result. If not set, the invoice currency will be used
	 */
	@Nullable CurrencyId returnInCurrencyId;
	@Nullable Set<PaymentId> excludePaymentIds;

	public enum DateColumn
	{
		DateAcct,
		DateTrx,
	}
}
