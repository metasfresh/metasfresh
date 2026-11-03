package de.metas.frontend_testing.masterdata.dd_order;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Creates one {@code DD_Order_Candidate} per entry and lets the production aggregation code
 * ({@code DDOrderCandidateService#process}) generate the DD_Order from them. All candidates share the same header
 * and line key, so exactly one DD_Order results.
 * <p>
 * Note: this only holds when candidates of different manufacturing orders are bundled, i.e. the masterdata request
 * must set the sysconfig {@code DDOrderAggregation.header.byPPOrderRef} to {@code N} via {@code systemSysconfigs}
 * (SYSTEM level; the plain {@code sysconfigs} write at config level 'O', which the aggregation does not read).
 * The shipped default is {@code Y}, which creates one DD_Order per manufacturing order and makes the command fail.
 */
@Value
@Builder
@Jacksonized
public class JsonDDOrderFromCandidatesRequest
{
	@NonNull Identifier warehouseFrom;
	@NonNull Identifier warehouseTo;
	@NonNull Identifier product;
	/** Shipper (identifier from {@code shippers}); if missing, the default shipper is used, which many databases do not have. */
	@Nullable Identifier shipper;
	@NonNull List<Candidate> candidates;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Candidate
	{
		/** The manufacturing order (identifier from {@code manufacturingOrders}) this candidate is the forward demand of; set it, {@link #salesOrderLine}, or both */
		@Nullable Identifier ppOrder;
		/** The BOM line (identifier from {@code manufacturingOrders[].lines}) of {@link #ppOrder}; required together with it */
		@Nullable Identifier ppOrderBOMLine;
		/** The sales order line (identifier from {@code salesOrders[].lines}) this candidate is the demand of */
		@Nullable Identifier salesOrderLine;
		/** Quantity in the product's stock UOM */
		@NonNull BigDecimal qty;
	}
}
