package de.metas.manufacturing.issue.plan;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

@Value
@Builder
public class PPOrderIssuePlan
{
	@NonNull PPOrderId orderId;

	@NonNull ImmutableList<PPOrderIssuePlanStep> steps;
}
