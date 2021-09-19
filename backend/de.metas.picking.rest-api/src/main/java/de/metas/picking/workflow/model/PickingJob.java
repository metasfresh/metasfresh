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
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
public final class PickingJob
{
	@Getter
	@NonNull private final String salesOrderDocumentNo;

	@Getter
	@NonNull private final String customerName;
	@Getter
	@NonNull private final ZonedDateTime preparationDate;

	@Getter
	@NonNull private final String deliveryAddress;

	@Nullable private UserId lockedBy;

	@NonNull private Optional<PickingSlotIdAndCaption> pickingSlot = Optional.empty();

	@Getter
	@NonNull private final ImmutableList<PickingJobLine> lines;

	@Getter
	boolean completed;

	@Builder
	private PickingJob(
			final @NonNull String salesOrderDocumentNo,
			final @NonNull String customerName,
			final @NonNull ZonedDateTime preparationDate,
			final @NonNull String deliveryAddress,
			final @NonNull ImmutableList<PickingJobLine> lines,
			final @Nullable UserId lockedBy)
	{
		this.preparationDate = preparationDate;
		this.deliveryAddress = deliveryAddress;
		Check.assumeNotEmpty(lines, "lines");
		this.salesOrderDocumentNo = salesOrderDocumentNo;
		this.customerName = customerName;
		this.lines = lines;
		this.lockedBy = lockedBy;
	}

	public synchronized @NonNull Optional<PickingSlotIdAndCaption> getPickingSlot()
	{
		return pickingSlot;
	}

	public synchronized void setPickingSlot(@Nullable final PickingSlotIdAndCaption pickingSlot)
	{
		this.pickingSlot = Optional.ofNullable(pickingSlot);
	}

	@Nullable
	public synchronized UserId getLockedBy()
	{
		return lockedBy;
	}

	public synchronized void setLockedBy(@Nullable final UserId lockedBy)
	{
		this.lockedBy = lockedBy;
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return lines.stream()
				.flatMap(PickingJobLine::streamShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isReadyForPicking()
	{
		return getPickingSlot().isPresent();
	}

	public PickingJobProgress getProgress()
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

	public synchronized void applyChanges(@NonNull final List<QtyPickedEvent> events)
	{
		Check.assumeNotEmpty(events, "events");
		events.forEach(this::applyChanges);
	}

	private void applyChanges(@NonNull final QtyPickedEvent event)
	{
		getStepById(event.getStepId())
				.changeQtyPicked(event.getQtyPicked());
	}

	private PickingJobStep getStepById(@NonNull final PickingJobStepId stepId)
	{
		return lines.stream()
				.flatMap(line -> line.getSteps().stream())
				.filter(step -> PickingJobStepId.equals(step.getId(), stepId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No step found for " + stepId));
	}
}
