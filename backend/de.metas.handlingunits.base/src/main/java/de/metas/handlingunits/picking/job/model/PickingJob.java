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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class PickingJob
{
	@Getter
	@NonNull private final PickingJobId id;

	@NonNull private final PickingJobHeader header;

	@NonNull @Getter private final Optional<PickingSlotIdAndCaption> pickingSlot;
	@NonNull @Getter private final Optional<PickingTarget> pickTarget;

	@Getter
	@NonNull private final ImmutableList<PickingJobLine> lines;

	@Getter
	@NonNull private final ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives;

	@Getter
	private final PickingJobDocStatus docStatus;

	@Getter
	private final boolean isReadyToReview;
	@Getter
	private final boolean isApproved;

	@Getter
	private final PickingJobProgress progress;

	@Builder(toBuilder = true)
	@SuppressWarnings("OptionalAssignedToNull")
	private PickingJob(
			final @NonNull PickingJobId id,
			final @NonNull PickingJobHeader header,
			final @Nullable Optional<PickingSlotIdAndCaption> pickingSlot,
			final @Nullable Optional<PickingTarget> pickTarget,
			final @NonNull ImmutableList<PickingJobLine> lines,
			final @NonNull ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives,
			final @NonNull PickingJobDocStatus docStatus,
			final boolean isReadyToReview,
			final boolean isApproved)
	{
		Check.assumeNotEmpty(lines, "lines not empty");

		this.id = id;
		this.header = header;
		this.pickingSlot = pickingSlot != null ? pickingSlot : Optional.empty();
		this.pickTarget = pickTarget != null ? pickTarget : Optional.empty();
		this.lines = lines;
		this.pickFromAlternatives = pickFromAlternatives;
		this.docStatus = docStatus;
		this.isReadyToReview = isReadyToReview;
		this.isApproved = isApproved;

		this.progress = computeProgress(lines);
	}

	public String getSalesOrderDocumentNo() {return header.getSalesOrderDocumentNo();}

	public ZonedDateTime getPreparationDate() {return header.getPreparationDate();}

	public ZonedDateTime getDeliveryDate() {return header.getDeliveryDate();}

	public BPartnerId getCustomerId() {return header.getCustomerId();}

	public String getCustomerName() {return header.getCustomerName();}

	public BPartnerLocationId getDeliveryBPLocationId() {return header.getDeliveryBPLocationId();}

	@Nullable
	public BPartnerLocationId getHandoverLocationId() {return header.getHandoverLocationId();}

	public String getDeliveryRenderedAddress() {return header.getDeliveryRenderedAddress();}

	@JsonIgnore
	public boolean isAllowPickingAnyHU() {return header.isAllowPickingAnyHU();}

	public UserId getLockedBy() {return header.getLockedBy();}

	public PickingJob withLockedBy(@Nullable final UserId lockedBy)
	{
		return UserId.equals(header.getLockedBy(), lockedBy)
				? this
				: toBuilder().header(header.toBuilder().lockedBy(lockedBy).build()).build();
	}

	public boolean isPickingReviewRequired() { return header.isPickingReviewRequired(); }

	private PickingJobProgress computeProgress(@NonNull final ImmutableList<PickingJobLine> lines)
	{
		final ImmutableSet<PickingJobProgress> lineProgresses = lines.stream().map(PickingJobLine::getProgress).collect(ImmutableSet.toImmutableSet());
		return PickingJobProgress.reduce(lineProgresses);
	}

	public void assertNotProcessed()
	{
		if (isProcessed())
		{
			throw new AdempiereException("Picking Job was already processed");
		}
	}

	public boolean isProcessed()
	{
		return docStatus.isProcessed();
	}

	public boolean isAllowAbort() {return !isProcessed() && isNothingPicked();}

	public boolean isNothingPicked() {return getProgress().isNotStarted();}

	public Optional<PickingSlotId> getPickingSlotId() {return pickingSlot.map(PickingSlotIdAndCaption::getPickingSlotId);}

	public PickingJob withPickTarget(@Nullable final PickingTarget pickTarget)
	{
		return PickingTarget.equals(this.pickTarget.orElse(null), pickTarget)
				? this
				: toBuilder().pickTarget(Optional.ofNullable(pickTarget)).build();
	}

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

	public PickingJobLine getLineById(@NonNull final PickingJobLineId lineId)
	{
		return lines.stream()
				.filter(line -> PickingJobLineId.equals(line.getId(), lineId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for " + lineId));
	}

	public Stream<PickingJobStep> streamSteps() {return lines.stream().flatMap(PickingJobLine::streamSteps);}

	public PickingJobStep getStepById(@NonNull final PickingJobStepId stepId)
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

	public PickingJob withChangedLine(@NonNull final PickingJobLineId lineId, final UnaryOperator<PickingJobLine> lineMapper)
	{
		return withChangedLines(line -> PickingJobLineId.equals(line.getId(), lineId) ? lineMapper.apply(line) : line);
	}

	public PickingJob withChangedStep(
			@NonNull final PickingJobStepId stepId,
			@NonNull final UnaryOperator<PickingJobStep> stepMapper)
	{
		return withChangedLines(line -> line.withChangedStep(stepId, stepMapper));
	}

	public PickingJob withChangedSteps(
			@NonNull final Set<PickingJobStepId> stepIds,
			@NonNull final UnaryOperator<PickingJobStep> stepMapper)
	{
		if (stepIds.isEmpty())
		{
			return this;
		}

		return withChangedLines(line -> line.withChangedSteps(stepIds, stepMapper));
	}

	public PickingJob withIsReadyToReview() {return isReadyToReview ? this : toBuilder().isReadyToReview(true).build();}

	public PickingJob withApproved()
	{
		return toBuilder()
				.isReadyToReview(false)
				.isApproved(true)
				.build();
	}

	@Value
	@Builder
	public static class AddStepRequest
	{
		boolean isGeneratedOnFly;
		@NonNull PickingJobStepId newStepId;
		@NonNull PickingJobLineId lineId;
		@NonNull Quantity qtyToPick;
		@NonNull LocatorInfo pickFromLocator;
		@NonNull HUInfo pickFromHU;
		@NonNull PackToSpec packToSpec;
	}

	public PickingJob withNewStep(@NonNull final AddStepRequest request)
	{
		return withChangedLine(request.getLineId(), line -> line.withNewStep(request));
	}
}
