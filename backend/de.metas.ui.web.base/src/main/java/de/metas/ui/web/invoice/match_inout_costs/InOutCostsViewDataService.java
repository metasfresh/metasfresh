package de.metas.ui.web.invoice.match_inout_costs;

import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.edi.model.I_C_Order;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.inout.InOutCost;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Cost_Type;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;

class InOutCostsViewDataService
{
	@NonNull private final OrderCostService orderCostService;
	@NonNull private final MoneyService moneyService;
	@NonNull final LookupDataSource bpartnerLookup;
	@NonNull final LookupDataSource orderLookup;
	@NonNull final LookupDataSource inoutLookup;
	@NonNull final LookupDataSource costTypeLookup;

	@Builder
	private InOutCostsViewDataService(
			final @NonNull OrderCostService orderCostService,
			final @NonNull MoneyService moneyService,
			final @NonNull LookupDataSourceFactory lookupDataSourceFactory)
	{
		this.orderCostService = orderCostService;
		this.moneyService = moneyService;
		this.bpartnerLookup = lookupDataSourceFactory.searchInTableLookup(I_C_BPartner.Table_Name);
		this.orderLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Order.Table_Name);
		this.inoutLookup = lookupDataSourceFactory.searchInTableLookup(I_M_InOut.Table_Name);
		this.costTypeLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Cost_Type.Table_Name);
	}

	public InOutCostsViewData getData(
			@NonNull final SOTrx soTrx,
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@Nullable final DocumentFilter filter)
	{
		return InOutCostsViewData.builder()
				.viewDataService(this)
				.soTrx(soTrx)
				.invoiceAndLineId(invoiceAndLineId)
				.filter(filter)
				.build();
	}

	ImmutableList<InOutCostRow> retrieveRows(
			@NonNull final SOTrx soTrx,
			@Nullable final DocumentFilter filter)
	{
		final ImmutableList<InOutCost> inoutCosts = orderCostService
				.stream(InOutCostsViewFilterHelper.toInOutCostQuery(soTrx, filter))
				.collect(ImmutableList.toImmutableList());

		return newLoader().loadRows(inoutCosts);
	}

	private InOutCostRowsLoader newLoader()
	{
		return InOutCostRowsLoader.builder()
				.moneyService(moneyService)
				.bpartnerLookup(bpartnerLookup)
				.orderLookup(orderLookup)
				.inoutLookup(inoutLookup)
				.costTypeLookup(costTypeLookup)
				.build();
	}

	public Amount getInvoiceLineOpenAmount(final InvoiceAndLineId invoiceAndLineId)
	{
		final Money invoiceLineOpenAmt = orderCostService.getInvoiceLineOpenAmt(invoiceAndLineId);
		return moneyService.toAmount(invoiceLineOpenAmt);
	}
}
