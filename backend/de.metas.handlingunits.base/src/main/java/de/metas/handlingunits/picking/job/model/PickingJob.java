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
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static de.metas.handlingunits.picking.job.service.PickingJobService.MISSING_PICKING_SLOT_ID_ERROR_MSG;
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
	@NonNull @Getter private final Optional<PickingSlotIdAndCaption> pickingSlot;
	@NonNull @Getter private final Optional<LUPickingTarget> luPickTarget;
	@NonNull @Getter private final Optional<TUPickingTarget> tuPickTarget;

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
			final @Nullable Optional<HUInfo> pickFromHU,
			final @Nullable Optional<PickingSlotIdAndCaption> pickingSlot,
			final @Nullable Optional<LUPickingTarget> luPickTarget,
			final @Nullable Optional<TUPickingTarget> tuPickTarget,
			final @NonNull ImmutableList<PickingJobLine> lines,
			final @NonNull ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives,
			final @NonNull PickingJobDocStatus docStatus)
	{
		Check.assumeNotEmpty(lines, "lines not empty");

		this.id = id;
		this.header = header;
		this.pickFromHU = pickFromHU != null ? pickFromHU : Optional.empty();
		this.pickingSlot = pickingSlot != null ? pickingSlot : Optional.empty();
		this.luPickTarget = luPickTarget != null ? luPickTarget : Optional.empty();
		this.tuPickTarget = tuPickTarget != null ? tuPickTarget : Optional.empty();
		this.lines = lines;
		this.pickFromAlternatives = pickFromAlternatives;
		this.docStatus = docStatus;

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

	public void assertPickingSlotScanned()
	{
		//noinspection ResultOfMethodCallIgnored
		getPickingSlotNotNull();
	}

	@NonNull
	public PickingSlotIdAndCaption getPickingSlotNotNull()
	{
		return pickingSlot.orElseThrow(() -> new AdempiereException(MISSING_PICKING_SLOT_ID_ERROR_MSG));
	}

	public boolean isProcessed()
	{
		return docStatus.isProcessed();
	}

	public boolean isAllowAbort() {return !isProcessed() && isNothingPicked();}

	public boolean isNothingPicked() {return getProgress().isNotStarted();}

	public Optional<PickingSlotId> getPickingSlotId() {return pickingSlot.map(PickingSlotIdAndCaption::getPickingSlotId);}

	@NonNull
	public PickingJob withLuPickTarget(@Nullable final LUPickingTarget pickTarget)
	{
		if (LUPickingTarget.equals(this.luPickTarget.orElse(null), pickTarget))
		{
			return this;
		}

		final PickingJobAggregationType aggregationType = getAggregationType();
		if (aggregationType.isLineLevelPickTargets() && pickTarget != null && pickTarget.isExistingLU())
		{
			throw new AdempiereException("Setting existing HU as picking targets on job level is not allowed for " + aggregationType + " aggregation type.");
		}

		return toBuilder()
				.luPickTarget(Optional.ofNullable(pickTarget))
				.tuPickTarget(pickTarget == null ? Optional.empty() : this.getTuPickTarget())
				.build();
	}

	@NonNull
	public PickingJob withTuPickTarget(@Nullable final TUPickingTarget pickTarget)
	{
		return TUPickingTarget.equals(this.tuPickTarget.orElse(null), pickTarget)
				? this
				: toBuilder().tuPickTarget(Optional.ofNullable(pickTarget)).build();
	}

	public PickingJob withPickingSlot(@Nullable final PickingSlotIdAndCaption pickingSlot)
	{
		return PickingSlotIdAndCaption.equals(this.pickingSlot.orElse(null), pickingSlot)
				? this
				: toBuilder().pickingSlot(Optional.ofNullable(pickingSlot)).build();
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
		return lines.stream()
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
	public ImmutableSet<HuId> getPickedHuIds()
	{
		return lines.stream()
				.map(PickingJobLine::getPickedHUIds)
				.flatMap(List::stream)
				.collect(ImmutableSet.toImmutableSet());
	}
}
