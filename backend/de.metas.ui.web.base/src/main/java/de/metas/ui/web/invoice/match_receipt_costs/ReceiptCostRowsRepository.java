package de.metas.ui.web.invoice.match_receipt_costs;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyRepository;
import de.metas.edi.model.I_C_Order;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.inout.InOutCost;
import de.metas.order.costs.inout.InOutCostQuery;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Cost_Type;
import org.compiere.model.I_M_InOut;

class ReceiptCostRowsRepository
{
	@NonNull private final OrderCostService orderCostService;
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull final LookupDataSource bpartnerLookup;
	@NonNull final LookupDataSource orderLookup;
	@NonNull final LookupDataSource inoutLookup;
	@NonNull final LookupDataSource costTypeLookup;

	@Builder
	private ReceiptCostRowsRepository(
			final @NonNull OrderCostService orderCostService,
			final @NonNull CurrencyRepository currencyRepository,
			final @NonNull LookupDataSourceFactory lookupDataSourceFactory)
	{
		this.orderCostService = orderCostService;
		this.currencyRepository = currencyRepository;
		this.bpartnerLookup = lookupDataSourceFactory.searchInTableLookup(I_C_BPartner.Table_Name);
		this.orderLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Order.Table_Name);
		this.inoutLookup = lookupDataSourceFactory.searchInTableLookup(I_M_InOut.Table_Name);
		this.costTypeLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Cost_Type.Table_Name);
	}

	public ReceiptCostRowsData query(@NonNull final ReceiptCostRowsQuery query)
	{
		final ImmutableList<InOutCost> inoutCosts = orderCostService.stream(
						InOutCostQuery.builder()
								.limit(query.getQueryLimit())
								.bpartnerId(query.getBpartnerId())
								.orderId(query.getOrderId())
								.costTypeId(query.getCostTypeId())
								.includeReversed(false)
								.build())
				.collect(ImmutableList.toImmutableList());

		return ReceiptCostRowsData.builder()
				.rows(newLoader().loadRows(inoutCosts))
				.build();
	}

	private ReceiptCostRowsLoader newLoader()
	{
		return ReceiptCostRowsLoader.builder()
				.currencyRepository(currencyRepository)
				.bpartnerLookup(bpartnerLookup)
				.orderLookup(orderLookup)
				.inoutLookup(inoutLookup)
				.costTypeLookup(costTypeLookup)
				.build();
	}
}
