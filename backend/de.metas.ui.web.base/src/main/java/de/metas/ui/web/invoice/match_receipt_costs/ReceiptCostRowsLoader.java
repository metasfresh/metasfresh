package de.metas.ui.web.invoice.match_receipt_costs;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.costs.inout.InOutCost;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

class ReceiptCostRowsLoader
{
	@NonNull private final MoneyService moneyService;
	@NonNull final LookupDataSource bpartnerLookup;
	@NonNull final LookupDataSource orderLookup;
	@NonNull final LookupDataSource inoutLookup;
	@NonNull final LookupDataSource costTypeLookup;

	@Builder
	private ReceiptCostRowsLoader(
			final @NonNull MoneyService moneyService,
			final @NonNull LookupDataSource bpartnerLookup,
			final @NonNull LookupDataSource orderLookup,
			final @NonNull LookupDataSource inoutLookup,
			final @NonNull LookupDataSource costTypeLookup)
	{
		this.moneyService = moneyService;
		this.bpartnerLookup = bpartnerLookup;
		this.orderLookup = orderLookup;
		this.inoutLookup = inoutLookup;
		this.costTypeLookup = costTypeLookup;
	}

	public ImmutableList<ReceiptCostRow> loadRows(final List<InOutCost> inoutCostsList)
	{
		if (inoutCostsList.isEmpty())
		{
			return ImmutableList.of();
		}

		return inoutCostsList.stream()
				.map(this::toReceiptCostRow)
				.collect(ImmutableList.toImmutableList());
	}

	private ReceiptCostRow toReceiptCostRow(final InOutCost inoutCost)
	{
		final Money costAmountToInvoice = inoutCost.getCostAmountToInvoice();

		return ReceiptCostRow.builder()
				.inoutCostId(inoutCost.getId())
				.bpartner(bpartnerLookup.findById(inoutCost.getBpartnerId()))
				.purchaseOrder(orderLookup.findById(inoutCost.getOrderId()))
				.receipt(inoutLookup.findById(inoutCost.getReceiptId()))
				.costType(costTypeLookup.findById(inoutCost.getCostTypeId()))
				.currency(moneyService.getCurrencyCodeByCurrencyId(costAmountToInvoice.getCurrencyId()).toThreeLetterCode())
				.costAmountToInvoice(costAmountToInvoice.toBigDecimal())
				.build();
	}
}
