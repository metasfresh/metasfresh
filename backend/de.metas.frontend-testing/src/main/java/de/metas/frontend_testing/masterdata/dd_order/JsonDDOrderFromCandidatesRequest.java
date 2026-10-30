package de.metas.frontend_testing.masterdata.dd_order;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

/**
 * Creates one {@code DD_Order_Candidate} per entry and lets the production aggregation code
 * ({@code DDOrderCandidateService#process}) generate the DD_Order from them. All candidates share the same header
 * and line key, so exactly one DD_Order results.
 */
@Value
@Builder
@Jacksonized
public class JsonDDOrderFromCandidatesRequest
{
	@NonNull Identifier warehouseFrom;
	@NonNull Identifier warehouseTo;
	@NonNull Identifier product;
	@NonNull List<Candidate> candidates;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Candidate
	{
		/** The manufacturing order (identifier from {@code manufacturingOrders}) this candidate is the forward demand of */
		@NonNull Identifier ppOrder;
		/** The BOM line (identifier from {@code manufacturingOrders[].lines}) of {@link #ppOrder} */
		@NonNull Identifier ppOrderBOMLine;
		/** Quantity in the product's stock UOM */
		@NonNull BigDecimal qty;
	}
}
