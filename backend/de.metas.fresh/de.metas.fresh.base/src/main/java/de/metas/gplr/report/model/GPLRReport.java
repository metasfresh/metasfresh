package de.metas.gplr.report.model;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@Builder
public class GPLRReport
{
	@Nullable GPLRReportId id;
	@NonNull Instant created;

	@NonNull private final GPLRReportSourceDocument sourceDocument;
	@NonNull private final GPLRReportSalesOrder salesOrder;
	@NonNull private final ImmutableList<GPLRReportShipment> shipments;
	@NonNull private final ImmutableList<GPLRReportPurchaseOrder> purchaseOrders;
	@NonNull private final GPLRReportSummary summary;
	@NonNull private final ImmutableList<GPLRReportLineItem> lineItems;
	@NonNull private final ImmutableList<GPLRReportCharge> charges;
	@NonNull private final ImmutableList<GPLRReportNote> otherNotes;
}
