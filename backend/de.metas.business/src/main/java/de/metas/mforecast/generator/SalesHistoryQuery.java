package de.metas.mforecast.generator;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

/**
 * Query parameters for retrieving historical sales quantities from completed sales orders.
 * Used by {@link ForecastCalculationStrategy} implementations to look up how much of a product
 * was sold in a given date range (the "comparison period").
 */
@Value
@Builder
public class SalesHistoryQuery
{
	@NonNull ProductId productId;
	/** Inclusive start of the comparison period. */
	@NonNull LocalDate dateFrom;
	/** Exclusive end of the comparison period. */
	@NonNull LocalDate dateTo;
	@NonNull OrgId orgId;
}
