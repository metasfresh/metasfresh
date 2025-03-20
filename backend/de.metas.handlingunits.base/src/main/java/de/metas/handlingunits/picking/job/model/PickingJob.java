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
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static de.metas.handlingunits.picking.job.service.PickingJobService.PICKING_JOB_PROCESSED_ERROR_MSG;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class PickingJob
{
	@Getter
	@NonNull private final PickingJobId id;

	@NonNull private final PickingJobHeader header;

	@NonNull @Getter private final Optional<HUInfo> pickFromHU;
	@NonNull @Getter private final CurrentPickingTarget currentPickingTarget;

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
	private PickingJob(
			final @NonNull PickingJobId id,
			final @NonNull PickingJobHeader header,
			final @Nullable Optional<HUInfo> pickFromHU,
			final @Nullable CurrentPickingTarget currentPickingTarget,
			final @NonNull ImmutableList<PickingJobLine> lines,
			final @NonNull ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives,
			final @NonNull PickingJobDocStatus docStatus,
			final boolean isReadyToReview,
			final boolean isApproved)
	{
		Check.assumeNotEmpty(lines, "lines not empty");

		this.id = id;
		this.header = header;
		this.pickFromHU = pickFromHU != null ? pickFromHU : Optional.empty();
		this.currentPickingTarget = currentPickingTarget != null ? currentPickingTarget : CurrentPickingTarget.EMPTY;
		this.lines = lines;
		this.pickFromAlternatives = pickFromAlternatives;
		this.docStatus = docStatus;
		this.isReadyToReview = isReadyToReview;
		this.isApproved = isApproved;

		this.progress = computeProgress(lines);
	}

	@NonNull
	public PickingJobAggregationType getAggregationType() {return header.getAggregationType();}

	@Nullable
	public String getSalesOrderDocumentNo() {return header.getSalesOrderDocumentNo();}

	@Nullable
	public ZonedDateTime getPreparationDate() {return header.getPreparationDate();}

	@Nullable
	public ZonedDateTime getDeliveryDate() {return header.getDeliveryDate();}

	@Nullable
	public BPartnerId getCustomerId() {return header.getCustomerId();}

	@Nullable
	public String getCustomerName() {return header.getCustomerName();}

	@Nullable
	public BPartnerLocationId getDeliveryBPLocationId() {return header.getDeliveryBPLocationId();}

	@Nullable
	public BPartnerLocationId getHandoverLocationId() {return header.getHandoverLocationId();}

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
			throw new AdempiereException(PICKING_JOB_PROCESSED_ERROR_MSG);
		}
	}

	public void assertPickingSlotScanned() {currentPickingTarget.assertPickingSlotScanned();}

	@NonNull
	public PickingSlotIdAndCaption getPickingSlotNotNull() {return currentPickingTarget.getPickingSlotNotNull();}

	public boolean isProcessed()
	{
		return docStatus.isProcessed();
	}

	public boolean isAllowAbort() {return !isProcessed() && isNothingPicked();}

	public boolean isNothingPicked() {return getProgress().isNotStarted();}

	private CurrentPickingTarget getCurrentPickingTarget(@Nullable final PickingJobLineId lineId)
	{
		return lineId != null ? getLineById(lineId).getCurrentPickingTarget() : currentPickingTarget;
	}

	private <T> Optional<T> getCurrentPickingTargetEffectiveValue(
			@Nullable final PickingJobLineId lineId,
			@NonNull final Function<CurrentPickingTarget, Optional<T>> valueMapper)
	{
		return Optionals.firstPresentOfSuppliers(
				() -> lineId != null ? valueMapper.apply(getLineById(lineId).getCurrentPickingTarget()) : Optional.empty(),
				() -> valueMapper.apply(currentPickingTarget)
		);
	}

	private PickingJob withCurrentPickingTarget(@NonNull final CurrentPickingTarget currentPickingTarget)
	{
		if (CurrentPickingTarget.equals(this.currentPickingTarget, currentPickingTarget))
		{
			return this;
		}

		assertCurrentPickingTargetAllowedOnHeader(currentPickingTarget);
		return toBuilder().currentPickingTarget(currentPickingTarget).build();
	}

	private PickingJob withCurrentPickingTarget(
			@Nullable final PickingJobLineId lineId,
			@NonNull final UnaryOperator<CurrentPickingTarget> currentPickingTargetMapper)
	{
		if (lineId != null)
		{
			return withChangedLine(lineId, (line) -> {
				final CurrentPickingTarget currentPickingTarget = line.getCurrentPickingTarget();
				final CurrentPickingTarget currentPickingTargetNew = currentPickingTargetMapper.apply(currentPickingTarget);
				return line.withCurrentPickingTarget(currentPickingTargetNew);
			});
		}
		else
		{
			final CurrentPickingTarget currentPickingTargetNew = currentPickingTargetMapper.apply(this.currentPickingTarget);
			return withCurrentPickingTarget(currentPickingTargetNew);
		}
	}

	private void assertCurrentPickingTargetAllowedOnHeader(@NonNull final CurrentPickingTarget currentPickingTargetNew)
	{
		if (!isLineLevelPickTarget())
		{
			return;
		}

		final LUPickingTarget luPickingTarget = currentPickingTargetNew.getLuPickingTarget().orElse(null);
		if (luPickingTarget != null && luPickingTarget.isExistingLU())
		{
			throw new AdempiereException("Setting existing HU as picking targets on job level is not allowed");
		}
	}

	public Optional<PickingSlotIdAndCaption> getPickingSlot() {return currentPickingTarget.getPickingSlot();}

	public Optional<PickingSlotId> getPickingSlotId() {return currentPickingTarget.getPickingSlotId();}

	public PickingJob withPickingSlot(@Nullable final PickingSlotIdAndCaption pickingSlot)
	{
		return withCurrentPickingTarget(currentPickingTarget.withPickingSlot(pickingSlot));
	}

	public boolean isLineLevelPickTarget() {return getAggregationType().isLineLevelPickTarget();}

	public Optional<LUPickingTarget> getLuPickingTarget(@Nullable final PickingJobLineId lineId)
	{
		return getCurrentPickingTarget(lineId).getLuPickingTarget();
	}

	public Optional<LUPickingTarget> getLuPickingTargetEffective(@Nullable final PickingJobLineId lineId)
	{
		return getCurrentPickingTargetEffectiveValue(lineId, CurrentPickingTarget::getLuPickingTarget);
	}

	@NonNull
	public PickingJob withLuPickingTarget(
			@Nullable final PickingJobLineId lineId,
			@Nullable final LUPickingTarget luPickingTarget)
	{
		return withCurrentPickingTarget(lineId, currentPickingTarget -> currentPickingTarget.withLuPickingTarget(luPickingTarget));
	}

	@NonNull
	public PickingJob withLuPickingTarget(
			@Nullable final PickingJobLineId lineId,
			@NonNull final UnaryOperator<LUPickingTarget> luPickingTargetMapper)
	{
		return withCurrentPickingTarget(lineId, currentPickingTarget -> currentPickingTarget.withLuPickingTarget(luPickingTargetMapper));
	}

	public PickingJob withClosedLuPickingTargets(
			boolean isCloseOnHeader,
			boolean isCloseOnLines,
			@Nullable PickingJobLineId onlyLineId,
			@Nullable final Consumer<HuId> closedLuIdCollector)
	{
		final PickingJobBuilder builder = toBuilder();
		boolean hasChanges = false;

		if (isCloseOnHeader)
		{
			final CurrentPickingTarget changedCurrentPickingTarget = currentPickingTarget.withClosedLuPickingTarget(closedLuIdCollector);
			builder.currentPickingTarget(changedCurrentPickingTarget);
			if (!CurrentPickingTarget.equals(changedCurrentPickingTarget, currentPickingTarget))
			{
				hasChanges = true;
			}
		}
		if (isCloseOnLines)
		{
			final ImmutableList<PickingJobLine> changedLines = CollectionUtils.map(this.lines, line -> {
				if (onlyLineId == null || PickingJobLineId.equals(line.getId(), onlyLineId))
				{
					return line.withCurrentPickingTarget(currentPickingTarget -> currentPickingTarget.withClosedLuPickingTarget(closedLuIdCollector));
				}
				else
				{
					return line;
				}
			});
			builder.lines(changedLines);

			if (!Objects.equals(this.lines, changedLines))
			{
				hasChanges = true;
			}
		}

		return hasChanges ? builder.build() : this;
	}

	public Optional<TUPickingTarget> getTuPickingTarget(@Nullable final PickingJobLineId lineId)
	{
		return getCurrentPickingTarget(lineId).getTuPickingTarget();
	}

	public Optional<TUPickingTarget> getTuPickingTargetEffective(@Nullable final PickingJobLineId lineId)
	{
		return getCurrentPickingTargetEffectiveValue(lineId, CurrentPickingTarget::getTuPickingTarget);
	}

	@NonNull
	public PickingJob withTuPickingTarget(
			@Nullable final PickingJobLineId lineId,
			@Nullable final TUPickingTarget tuPickingTarget)
	{
		return withCurrentPickingTarget(lineId, currentPickingTarget -> currentPickingTarget.withTuPickingTarget(tuPickingTarget));
	}

	public PickingJob withPickFromHU(@Nullable final HUInfo pickFromHU)
	{
		return HUInfo.equals(this.pickFromHU.orElse(null), pickFromHU)
				? this
				: toBuilder().pickFromHU(Optional.ofNullable(pickFromHU)).build();
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return streamShipmentScheduleIds().collect(ImmutableSet.toImmutableSet());
	}

	public Stream<ShipmentScheduleId> streamShipmentScheduleIds()
	{
		return streamLines().flatMap(PickingJobLine::streamShipmentScheduleId);
	}

	public PickingJobLine getLineById(@NonNull final PickingJobLineId lineId)
	{
		return streamLines()
				.filter(line -> PickingJobLineId.equals(line.getId(), lineId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for " + lineId));
	}

	public Stream<PickingJobStep> streamSteps() {return streamLines().flatMap(PickingJobLine::streamSteps);}

	public Stream<PickingJobLine> streamLines() {return lines.stream();}

	public PickingJobStep getStepById(@NonNull final PickingJobStepId stepId)
	{
		return streamLines()
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

	@NonNull
	public ImmutableSet<ProductId> getProductIds()
	{
		return streamLines()
				.map(PickingJobLine::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public ITranslatableString getSingleProductNameOrEmpty()
	{
		ProductId productId = null;
		ITranslatableString productName = TranslatableStrings.empty();
		for (final PickingJobLine line : lines)
		{
			if (productId == null)
			{
				productId = line.getProductId();
			}
			else if (!ProductId.equals(productId, line.getProductId()))
			{
				// found different products
				return TranslatableStrings.empty();
			}

			productName = line.getProductName();
		}

		return productName;
	}

	@Nullable
	public Quantity getSingleQtyToPickOrNull()
	{
		return extractQtyToPickOrNull(lines, PickingJobLine::getProductId, PickingJobLine::getQtyToPick);
	}

	@Nullable
	public static <T> Quantity extractQtyToPickOrNull(
			@NonNull final Collection<T> lines,
			@NonNull final Function<T, ProductId> extractProductId,
			@NonNull final Function<T, Quantity> extractQtyToPick)
	{
		ProductId productId = null;
		Quantity qtyToPick = null;

		for (final T line : lines)
		{
			final ProductId lineProductId = extractProductId.apply(line);
			if (productId == null)
			{
				productId = lineProductId;
			}
			else if (!ProductId.equals(productId, lineProductId))
			{
				// found different products
				return null;
			}

			final Quantity lineQtyToPick = extractQtyToPick.apply(line);
			if (qtyToPick == null)
			{
				qtyToPick = lineQtyToPick;
			}
			else if (UomId.equals(qtyToPick.getUomId(), lineQtyToPick.getUomId()))
			{
				qtyToPick = qtyToPick.add(lineQtyToPick);
			}
			else
			{
				// found different UOMs
				return null;
			}
		}

		return qtyToPick;
	}

	@NonNull
	public ImmutableSet<HuId> getPickedHuIds(@Nullable final PickingJobLineId lineId)
	{
		return lineId != null
				? getLineById(lineId).getPickedHUIds()
				: getAllPickedHuIds();
	}

	public ImmutableSet<HuId> getAllPickedHuIds()
	{
		return streamLines()
				.map(PickingJobLine::getPickedHUIds)
				.flatMap(Set::stream)
				.collect(ImmutableSet.toImmutableSet());
	}

	public ImmutableSet<PPOrderId> getManufacturingOrderIds()
	{
		return streamLines()
				.map(PickingJobLine::getPickFromManufacturingOrderId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

}
