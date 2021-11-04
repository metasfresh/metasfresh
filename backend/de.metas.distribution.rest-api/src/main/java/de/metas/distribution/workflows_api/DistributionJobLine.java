package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.function.UnaryOperator;

@Value
@Builder(toBuilder = true)
public class DistributionJobLine
{
	@NonNull DDOrderLineId ddOrderLineId;

	@NonNull ProductInfo product;
	@NonNull LocatorInfo pickFromLocator;
	@NonNull LocatorInfo dropToLocator;

	@NonNull ImmutableList<DistributionJobStep> steps;

	public DistributionJobLine withChangedSteps(@NonNull final UnaryOperator<DistributionJobStep> stepMapper)
	{
		final ImmutableList<DistributionJobStep> changedSteps = CollectionUtils.map(steps, stepMapper);
		return changedSteps.equals(steps)
				? this
				: toBuilder().steps(changedSteps).build();
	}
}
