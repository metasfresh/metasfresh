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

package de.metas.picking.workflow.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;
import lombok.experimental.Delegate;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
public final class PickingJob
{
	@Delegate
	@NonNull private final PickingJobHeader header;

	@Getter
	@NonNull private final Optional<PickingSlotIdAndCaption> pickingSlot;

	@Getter
	@NonNull private final ImmutableList<PickingJobLine> lines;

	@Getter
	private final boolean processed;

	@Getter
	private final PickingJobProgress progress;

	@Builder(toBuilder = true)
	private PickingJob(
			final @NonNull PickingJobHeader header,
			final @Nullable Optional<PickingSlotIdAndCaption> pickingSlot,
			final boolean processed, final @NonNull ImmutableList<PickingJobLine> lines)
	{
		Check.assumeNotEmpty(lines, "lines not empty");

		this.header = header;

		//noinspection OptionalAssignedToNull
		this.pickingSlot = pickingSlot != null ? pickingSlot : Optional.empty();

		this.lines = lines;

		this.processed = processed;

		this.progress = computeProgress(lines);
	}

	private PickingJobProgress computeProgress(@NonNull final ImmutableList<PickingJobLine> lines)
	{
		int countFullyPickedLines = 0;
		int countNotFullyPickedLines = 0;
		for (final PickingJobLine line : lines)
		{
			if (line.getProgress().isFullyPicked())
			{
				countFullyPickedLines++;
			}
			else
			{
				countNotFullyPickedLines++;
			}
		}

		if (countFullyPickedLines <= 0)
		{
			return countNotFullyPickedLines <= 0
					? PickingJobProgress.FULLY_PICKED // shall NOT happen because we have at least one line
					: PickingJobProgress.NOTHING_PICKED;
		}
		else
		{
			return countNotFullyPickedLines <= 0
					? PickingJobProgress.FULLY_PICKED
					: PickingJobProgress.PARTIAL_PICKED;
		}
	}

	public void assertNotProcessed()
	{
		if (processed)
		{
			throw new AdempiereException("Picking Job was already processed");
		}
	}

	public PickingJob withProcessedTrue()
	{
		return !processed ? toBuilder().processed(true).build() : this;
	}

	public Optional<PickingSlotId> getPickingSlotId() {return pickingSlot.map(PickingSlotIdAndCaption::getPickingSlotId);}

	public PickingJob withPickingSlot(@Nullable final PickingSlotIdAndCaption pickingSlot)
	{
		return PickingSlotIdAndCaption.equals(this.pickingSlot.orElse(null), pickingSlot)
				? this
				: toBuilder().pickingSlot(Optional.ofNullable(pickingSlot)).build();
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return streamShipmentScheduleIds().collect(ImmutableSet.toImmutableSet());
	}

	public Stream<ShipmentScheduleId> streamShipmentScheduleIds()
	{
		return lines.stream().flatMap(PickingJobLine::streamShipmentScheduleId);
	}

	public PickingJob withChangedSteps(final UnaryOperator<PickingJobStep> stepMapper)
	{
		return withChangedLines(line -> line.withChangedSteps(stepMapper));
	}

	public PickingJob withChangedLines(final UnaryOperator<PickingJobLine> lineMapper)
	{
		final ImmutableList<PickingJobLine> changedLines = CollectionUtils.map(lines, lineMapper);
		return changedLines.equals(lines)
				? this
				: toBuilder().lines(changedLines).build();
	}

	public PickingJobStep getStepById(@NonNull final PickingJobStepId stepId)
	{
		return lines.stream()
				.flatMap(line -> line.getSteps().stream())
				.filter(step -> PickingJobStepId.equals(step.getId(), stepId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No step found for " + stepId));
	}

	public ImmutableSet<PickingCandidateId> getPickingCandidateIds()
	{
		return lines.stream()
				.flatMap(line -> line.getSteps().stream())
				.map(PickingJobStep::getPickingCandidateId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
