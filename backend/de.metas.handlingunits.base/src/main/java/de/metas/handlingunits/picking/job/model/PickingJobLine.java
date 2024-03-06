/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PickingJobLine
{
	@NonNull PickingJobLineId id;

	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull ImmutableList<PickingJobStep> steps;

	// computed values
	@NonNull Quantity qtyToPick;
	@NonNull PickingJobProgress progress;

	@Builder(toBuilder = true)
	private PickingJobLine(
			@NonNull final PickingJobLineId id,
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final ImmutableList<PickingJobStep> steps)
	{
		Check.assumeNotEmpty(steps, "steps not empty");

		this.id = id;

		this.productId = productId;
		this.productName = productName;
		this.steps = steps;

		this.progress = computeProgress(steps);
		this.qtyToPick = computeQtyToPick(steps);
	}

	@NonNull
	private static Quantity computeQtyToPick(final @NonNull ImmutableList<PickingJobStep> steps)
	{
		//noinspection OptionalGetWithoutIsPresent
		return steps.stream().map(PickingJobStep::getQtyToPick).reduce(Quantity::add).get();
	}

	private static PickingJobProgress computeProgress(@NonNull ImmutableList<PickingJobStep> steps)
	{
		final ImmutableSet<PickingJobProgress> stepProgresses = steps.stream().map(PickingJobStep::getProgress).collect(ImmutableSet.toImmutableSet());
		return PickingJobProgress.reduce(stepProgresses);
	}

	Stream<ShipmentScheduleId> streamShipmentScheduleId()
	{
		return steps.stream().map(PickingJobStep::getShipmentScheduleId);
	}

	public Stream<PickingJobStep> streamSteps() {return steps.stream();}

	public PickingJobLine withChangedSteps(@NonNull final UnaryOperator<PickingJobStep> stepMapper)
	{
		final ImmutableList<PickingJobStep> changedSteps = CollectionUtils.map(steps, stepMapper);
		return changedSteps.equals(steps)
				? this
				: toBuilder().steps(changedSteps).build();
	}

	public PickingJobLine withChangedStep(
			@NonNull final PickingJobStepId stepId,
			@NonNull final UnaryOperator<PickingJobStep> stepMapper)
	{
		return withChangedSteps(
				step -> PickingJobStepId.equals(step.getId(), stepId)
						? stepMapper.apply(step)
						: step);
	}

	public PickingJobLine withChangedSteps(
			@NonNull final Set<PickingJobStepId> stepIds,
			@NonNull final UnaryOperator<PickingJobStep> stepMapper)
	{
		return withChangedSteps(
				step -> stepIds.contains(step.getId())
						? stepMapper.apply(step)
						: step);
	}
}
