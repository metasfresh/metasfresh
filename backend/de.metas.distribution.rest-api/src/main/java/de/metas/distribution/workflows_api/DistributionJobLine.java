package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Value
public class DistributionJobLine
{
	@NonNull DistributionJobLineId id;

	@NonNull ProductInfo product;
	@NonNull Quantity qtyToMove;

	@NonNull LocatorInfo pickFromLocator;
	@NonNull LocatorInfo dropToLocator;

	@NonNull ImmutableList<DistributionJobStep> steps;

	@NonNull WFActivityStatus status;

	@Builder(toBuilder = true)
	private DistributionJobLine(
			@NonNull final DistributionJobLineId id,
			@NonNull final ProductInfo product,
			@NonNull final Quantity qtyToMove,
			@NonNull final LocatorInfo pickFromLocator,
			@NonNull final LocatorInfo dropToLocator,
			@NonNull final ImmutableList<DistributionJobStep> steps)
	{
		this.id = id;
		this.product = product;
		this.qtyToMove = qtyToMove;
		this.pickFromLocator = pickFromLocator;
		this.dropToLocator = dropToLocator;
		this.steps = steps;

		status = computeStatusFromSteps(steps);
	}

	private static WFActivityStatus computeStatusFromSteps(final @NonNull List<DistributionJobStep> steps)
	{
		return steps.isEmpty()
				? WFActivityStatus.NOT_STARTED
				: WFActivityStatus.computeStatusFromLines(steps, DistributionJobStep::getStatus);
	}

	public DDOrderLineId getDdOrderLineId() {return id.toDDOrderLineId();}

	public DistributionJobLine withNewStep(final DistributionJobStep stepToAdd)
	{
		final ArrayList<DistributionJobStep> changedSteps = new ArrayList<>(this.steps);
		boolean added = false;
		boolean changed = false;

		for (final DistributionJobStep step : steps)
		{
			if (DistributionJobStepId.equals(step.getId(), stepToAdd.getId()))
			{
				changedSteps.add(stepToAdd);
				added = true;

				if (!Objects.equals(step, stepToAdd))
				{
					changed = true;
				}
			}
			else
			{
				changedSteps.add(step);
			}
		}

		if (!added)
		{
			changedSteps.add(stepToAdd);
			changed = true;
		}

		return changed
				? toBuilder().steps(ImmutableList.copyOf(changedSteps)).build()
				: this;
	}

	public DistributionJobLine withChangedSteps(@NonNull final UnaryOperator<DistributionJobStep> stepMapper)
	{
		final ImmutableList<DistributionJobStep> changedSteps = CollectionUtils.map(steps, stepMapper);
		return changedSteps.equals(steps)
				? this
				: toBuilder().steps(changedSteps).build();
	}

	@NonNull
	public Optional<DistributionJobStep> getStepById(@NonNull final DistributionJobStepId stepId)
	{
		return getSteps().stream().filter(step -> step.getId().equals(stepId)).findFirst();
	}
}
