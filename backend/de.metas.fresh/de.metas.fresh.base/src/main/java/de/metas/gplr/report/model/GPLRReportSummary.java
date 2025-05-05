package de.metas.gplr.report.model;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GPLRReportSummary
{
	// 030 - Local Currency - company-wide base currency EUR
	// 038 - Foreign Currency - Foreign currency used for sales
	@NonNull CurrencyCode localCurrency;
	@NonNull CurrencyCode foreignCurrency;

	// 031 - Sales (LC) - summarize price of all invoice lines
	// 039 - Sales (FC) - Summarize price of all invoice lines in foreign currency
	@NonNull Amount salesLC;
	@NonNull Amount salesFC;

	// 032 - Taxes - Needed, summary tax line from sales invoice
	@NonNull Amount taxesLC;

	// 033 - Estimated - summarize estimated costs from sales order (presume order : delivery 1:1)
	// 040 - Estimated - summarize estimated costs in foreign currency from sales order (presume order : delivery 1:1)
	@NonNull Amount estimatedLC;
	@NonNull Amount estimatedFC;

	// 034 - COGS - summarize material costs all invoice lines
	@NonNull Amount cogsLC;

	// 035 - Charge - If non-invoiceable charges on sales invoice to customer
	// 041 - Charge - Costs in foreign currency charged to customer
	@NonNull Amount chargesLC;
	@NonNull Amount chargesFC;

	// 036 - PROFIT / LOSS Profit - calculate Sales minus Estimated minus COGS
	@NonNull Amount profitOrLossLC;

	// 037 - Rate - calculate percentage profit/loss against Sales
	@NonNull Percent profitRate;
}
