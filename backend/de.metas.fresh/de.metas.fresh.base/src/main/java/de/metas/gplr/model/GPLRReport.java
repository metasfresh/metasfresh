package de.metas.gplr.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
@Builder
public class GPLRReport
{
	@NonNull Instant created;

	@NonNull GPLRReportSource source;
	@NonNull GPLRReportSalesOrder salesOrder;
	@NonNull List<GPLRReportShipment> shipments;
	@NonNull List<GPLRReportPurchaseOrder> purchaseOrders;
	@NonNull GPLRReportSummary summary;
	@NonNull List<GPLRReportLineItem> lineItems;
	@NonNull List<GPLRReportCharge> charges;
	@NonNull List<GPLRReportNote> otherNotes;
}
