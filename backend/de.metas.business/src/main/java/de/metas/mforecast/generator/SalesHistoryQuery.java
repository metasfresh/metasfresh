package de.metas.mforecast.generator;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class SalesHistoryQuery
{
	@NonNull ProductId productId;
	@NonNull LocalDate dateFrom;
	@NonNull LocalDate dateTo;
	@NonNull OrgId orgId;
}
