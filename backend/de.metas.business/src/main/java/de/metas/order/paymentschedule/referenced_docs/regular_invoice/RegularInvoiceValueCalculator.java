package de.metas.order.paymentschedule.referenced_docs.regular_invoice;

import de.metas.currency.CurrencyPrecision;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.MatchInvCollection;
import de.metas.invoice.matchinv.MatchInvQuery;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceiptCollection;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.OrderPayScheduleMaterialReceiptService;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxBL;
import lombok.Builder;
import lombok.NonNull;

@Builder
class RegularInvoiceValueCalculator
{
	private static final int INTERMEDIATE_SCALE = 12;

	@NonNull private final ITaxBL taxBL;
	@NonNull private final MoneyService moneyService;
	@NonNull private final MatchInvoiceRepository matchInvoiceRepository;
	@NonNull private final OrderPayScheduleMaterialReceiptService materialReceiptService;

	@NonNull
	public Money computeValueMatchedByReceipts(@NonNull final RegularInvoice invoice)
	{
		final MaterialReceiptCollection materialReceipts = materialReceiptService.getByOrderId(invoice.getOrderId());
		final MatchInvCollection matchInvs = findMatchInvsByInvoiceId(invoice.getId())
				.filter(matchInv -> materialReceipts.containsInOutLineId(matchInv.getInoutLineId()));

		final CurrencyId currencyId = invoice.getCurrencyId();
		final CurrencyPrecision precision = moneyService.getStdPrecision(currencyId);
		Money total = Money.zero(currencyId);

		for (final RegularInvoice.Line invoiceLine : invoice.getLines())
		{
			final Quantity qtyMatched = matchInvs.getQtyMatched(invoiceLine.getId()).orElse(null);
			if (qtyMatched == null || qtyMatched.isZero())
			{
				continue;
			}

			final Money lineGrossAmtMatched = invoiceLine.computeLineGrossAmtMatched(qtyMatched, precision);
			total = total.add(lineGrossAmtMatched);
		}

		return total;
	}

	private MatchInvCollection findMatchInvsByInvoiceId(final InvoiceId invoiceId)
	{
		return matchInvoiceRepository.stream(MatchInvQuery.builder().type(MatchInvType.Material).invoiceId(invoiceId).build())
				.collect(MatchInvCollection.collect());
	}

}
