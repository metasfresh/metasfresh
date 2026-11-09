package de.metas.frontend_testing.masterdata.sales_order;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonSalesOrderCreateRequest
{
	@Nullable Identifier bpartner;
	@Nullable Identifier location;
	@NonNull Identifier warehouse;
	@Nullable Identifier shipper;
	@NonNull ZonedDateTime datePromised;
	@Nullable String poReference;

	/**
	 * {@code C_PaymentTerm.Value} of the order's payment term, which the invoice created from the order inherits.
	 * Looked up; created when the database has no such term. When {@code null}, the business partner's term applies.
	 */
	@Nullable String paymentTerm;
	@NonNull List<Line> lines;

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Line
	{
		@NonNull Identifier product;
		@NonNull BigDecimal qty;
		@Nullable Identifier piItemProduct;
		@Nullable List<Schedule> schedules;
		@Nullable Identifier workplace;
	}

	//
	//
	// 
	//
	//
	@Value
	@Builder
	@Jacksonized
	public static class Schedule
	{
		@NonNull Identifier workplace;
		@NonNull BigDecimal qty;
	}

}
