package de.metas.order.paymentschedule.referenced_docs.regular_invoice;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class RegularInvoice
{
	@NonNull InvoiceId id;
	@NonNull OrderId orderId;
	boolean isPartialInvoice;
	@NonNull OrgId orgId;
	@NonNull BPartnerId bpartnerId;
	@NonNull LocalDate dateInvoiced;
	@NonNull LocalDate dateAcct;
	@NonNull CurrencyId currencyId;
	@NonNull DocStatus docStatus;
	boolean isPaid;

	@NonNull ImmutableList<Line> lines;

	public boolean isCompletedOrClosed() {return docStatus.isCompletedOrClosed();}

	//
	//
	//
	//
	//

	@Value
	@Builder
	public static class Line
	{
		@NonNull InvoiceLineId id;
		@NonNull Quantity qtyInvoiced;
		@NonNull Money lineGrossAmt;

		public Money computeLineGrossAmtMatched(final Quantity qtyMatched, final CurrencyPrecision precision)
		{
			if (qtyMatched.isZero())
			{
				return lineGrossAmt.toZero();
			}
			else if (qtyInvoiced.isZero())
			{
				// shall not happen
				return lineGrossAmt;
			}
			else if (qtyInvoiced.equals(qtyMatched))
			{
				return lineGrossAmt;
			}

			return lineGrossAmt.divide(qtyInvoiced.toBigDecimal(), CurrencyPrecision.TEN)
					.multiply(qtyMatched.toBigDecimal())
					.roundIfNeeded(precision);
		}
	}
}
