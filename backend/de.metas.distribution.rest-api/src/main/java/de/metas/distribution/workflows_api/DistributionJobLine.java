package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.function.UnaryOperator;

@Value
public class DistributionJobLine
{
	@NonNull DDOrderLineId ddOrderLineId;

	@NonNull ProductInfo product;
	@NonNull LocatorInfo pickFromLocator;
	@NonNull LocatorInfo dropToLocator;

	@NonNull ImmutableList<DistributionJobStep> steps;

	@NonNull WFActivityStatus status;

	@Builder(toBuilder = true)
	private DistributionJobLine(
			@NonNull final DDOrderLineId ddOrderLineId,
			@NonNull final ProductInfo product,
			@NonNull final LocatorInfo pickFromLocator,
			@NonNull final LocatorInfo dropToLocator,
			@NonNull final ImmutableList<DistributionJobStep> steps)
	{
		this.ddOrderLineId = ddOrderLineId;
		this.product = product;
		this.pickFromLocator = pickFromLocator;
		this.dropToLocator = dropToLocator;
		this.steps = steps;

		this.status = WFActivityStatus.computeStatusFromLines(steps, DistributionJobStep::getStatus);
	}

	public DistributionJobLine withChangedSteps(@NonNull final UnaryOperator<DistributionJobStep> stepMapper)
	{
		final ImmutableList<DistributionJobStep> changedSteps = CollectionUtils.map(steps, stepMapper);
		return changedSteps.equals(steps)
				? this
				: toBuilder().steps(changedSteps).build();
	}
}
