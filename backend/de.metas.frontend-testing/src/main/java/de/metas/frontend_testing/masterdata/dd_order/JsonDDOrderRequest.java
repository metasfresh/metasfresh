package de.metas.frontend_testing.masterdata.dd_order;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonDDOrderRequest
{
	@NonNull Identifier warehouseFrom;
	@NonNull Identifier warehouseTo;
	@NonNull Identifier warehouseInTransit;
	@Nullable Identifier plant;
	@Nullable String priority;
	@Nullable SeqNo seqNo;
	@NonNull List<Line> lines;
	@Nullable Identifier forwardPPOrder;
	@Nullable Identifier forwardPPOrderBOMLine;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Line
	{
		@Nullable Identifier locatorFrom;
		@Nullable Identifier locatorTo;
		@NonNull Identifier product;
		@NonNull BigDecimal qtyEntered;

		/**
		 * Sales-order line this DD_OrderLine is demand for — mirrors {@code JsonPPOrderRequest#salesOrderLine}
		 * ({@link de.metas.frontend_testing.masterdata.pp_order.PPOrderCommand}): a fixture-only link by
		 * {@link Identifier}, set directly on {@code DD_OrderLine.C_OrderLineSO_ID}, without simulating the real
		 * material-disposition candidate pipeline that populates it in production. Lets a test build the exact
		 * DB shape the sales-order-line demand route reads (e.g.
		 * {@code DDOrderLineDemandSqlHelper.byCarrierProductIds}).
		 */
		@Nullable Identifier salesOrderLine;
	}
}
