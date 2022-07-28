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
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class PickingJob
{
	@Getter
	@NonNull private final PickingJobId id;

	@Delegate
	@NonNull private final PickingJobHeader header;

	@Getter
	@NonNull private final Optional<PickingSlotIdAndCaption> pickingSlot;

	@Getter
	@NonNull private final ImmutableList<PickingJobLine> lines;

	@Getter
	@NonNull private final ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives;

	@Getter
	private final PickingJobDocStatus docStatus;

	@Getter
	private final PickingJobProgress progress;

	@Builder(toBuilder = true)
	private PickingJob(
			final @NonNull PickingJobId id,
			final @NonNull PickingJobHeader header,
			final @Nullable Optional<PickingSlotIdAndCaption> pickingSlot,
			final @NonNull ImmutableList<PickingJobLine> lines,
			final @NonNull ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives,
			final @NonNull PickingJobDocStatus docStatus)
	{
		Check.assumeNotEmpty(lines, "lines not empty");

		this.id = id;
		this.header = header;
		//noinspection OptionalAssignedToNull
		this.pickingSlot = pickingSlot != null ? pickingSlot : Optional.empty();
		this.lines = lines;
		this.pickFromAlternatives = pickFromAlternatives;
		this.docStatus = docStatus;

		this.progress = computeProgress(lines);
	}

	private PickingJobProgress computeProgress(@NonNull final ImmutableList<PickingJobLine> lines)
	{
		int countPickedLines = 0;
		int countNotPickedLines = 0;
		for (final PickingJobLine line : lines)
		{
			if (line.getProgress().isDone())
			{
				countPickedLines++;
			}
			else
			{
				countNotPickedLines++;
			}
		}

		if (countPickedLines <= 0)
		{
			return countNotPickedLines <= 0
					? PickingJobProgress.DONE // shall NOT happen because we have at least one line
					: PickingJobProgress.NOT_STARTED;
		}
		else
		{
			return countNotPickedLines <= 0
					? PickingJobProgress.DONE
					: PickingJobProgress.IN_PROGRESS;
		}
	}

	public void assertNotProcessed()
	{
		if (docStatus.isProcessed())
		{
			throw new AdempiereException("Picking Job was already processed");
		}
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

	public Stream<PickingJobStep> streamSteps() {return lines.stream().flatMap(PickingJobLine::streamSteps);}

	public PickingJobStep getStepById(final PickingJobStepId stepId)
	{
		return lines.stream()
				.flatMap(PickingJobLine::streamSteps)
				.filter(step -> PickingJobStepId.equals(step.getId(), stepId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No step found for " + stepId));
	}

	public PickingJob withDocStatus(final PickingJobDocStatus docStatus)
	{
		return !Objects.equals(this.docStatus, docStatus)
				? toBuilder().docStatus(docStatus).build()
				: this;
	}

	public PickingJob withChangedLines(final UnaryOperator<PickingJobLine> lineMapper)
	{
		final ImmutableList<PickingJobLine> changedLines = CollectionUtils.map(lines, lineMapper);
		return changedLines.equals(lines)
				? this
				: toBuilder().lines(changedLines).build();
	}

	public PickingJob withChangedStep(
			@NonNull final PickingJobStepId stepId,
			@NonNull final UnaryOperator<PickingJobStep> stepMapper)
	{
		return withChangedLines(line -> line.withChangedStep(stepId, stepMapper));
	}

	public PickingJob withChangedSteps(final UnaryOperator<PickingJobStep> stepMapper)
	{
		return withChangedLines(line -> line.withChangedSteps(stepMapper));
	}
}
