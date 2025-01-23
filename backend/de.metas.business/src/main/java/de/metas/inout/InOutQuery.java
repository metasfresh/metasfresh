package de.metas.inout;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class InOutQuery
{
	@Nullable Instant movementDateFrom;
	@Nullable Instant movementDateTo;
	@Nullable DocStatus docStatus;
	@Nullable @Singular ImmutableSet<DocStatus> excludeDocStatuses;

	@NonNull @Singular ImmutableSet<OrderId> orderIds;

	@Builder.Default QueryLimit limit = QueryLimit.ONE_HUNDRED;
}
