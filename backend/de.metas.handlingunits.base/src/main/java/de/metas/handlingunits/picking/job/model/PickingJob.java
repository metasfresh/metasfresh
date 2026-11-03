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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import de.metas.shipping.CarrierProductId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.StreamUtils;
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
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static de.metas.handlingunits.picking.job.service.PickingJobService.PICKING_JOB_PROCESSED_ERROR_MSG;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class PickingJob implements PickingJobHeaderOrLine
{
	@NonNull @Getter private final PickingJobId id;

	@NonNull private final PickingJobHeader header;

	@NonNull @Getter private final Optional<HUInfo> pickFromHU;
	@NonNull @Getter private final CurrentPickingTarget currentPickingTarget;

	@NonNull @Getter private final ImmutableList<PickingJobLine> lines;
	@NonNull @Getter @JsonIgnore private final ImmutableMap<PickingJobLineId, PickingJobLine> linesById;

	@NonNull @Getter private final ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives;

	@Getter private final PickingJobDocStatus docStatus;

	@Getter private final PickingJobProgress progress;

	@Builder(toBuilder = true)
	@SuppressWarnings("OptionalAssignedToNull")
	private PickingJob(
			final @NonNull PickingJobId id,
			final @NonNull PickingJobHeader header,
			final @Nullable Optional<HUInfo> pickFromHU,
			final @Nullable CurrentPickingTarget currentPickingTarget,
			final @NonNull ImmutableList<PickingJobLine> lines,
			final @NonNull ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives,
			final @NonNull PickingJobDocStatus docStatus)
	{
		Check.assumeNotEmpty(lines, "lines not empty");

		this.id = id;
		this.header = header;
		this.pickFromHU = pickFromHU != null ? pickFromHU : Optional.empty();
		this.currentPickingTarget = currentPickingTarget != null ? currentPickingTarget : CurrentPickingTarget.EMPTY;
		this.lines = lines;
		this.linesById = Maps.uniqueIndex(this.lines, PickingJobLine::getId);
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

	public Set<BPartnerLocationId> getDeliveryBPLocationIds()
	{
		final ImmutableSet.Builder<BPartnerLocationId> result = ImmutableSet.builder();
		if (getDeliveryBPLocationId() != null)
		{
			result.add(getDeliveryBPLocationId());
		}

		streamLines()
				.map(PickingJobLine::getDeliveryBPLocationId)
				.forEach(result::add);

		return result.build();
	}

	@Nullable
	public BPartnerLocationId getHandoverLocationId() {return header.getHandoverLocationId();}

	@JsonIgnore
	public boolean isAllowPickingAnyHU() {return header.isAllowPickingAnyHU();}

	@JsonIgnore
	public boolean isAnonymousPickHUsOnTheFly() {return header.isAnonymousPickHUsOnTheFly();}

	@Nullable
	public UserId getLockedBy() {return header.getLockedBy();}

	public PickingJob withLockedBy(@Nullable final UserId lockedBy)
	{
		return UserId.equals(header.getLockedBy(), lockedBy)
				? this
				: toBuilder().header(header.toBuilder().lockedBy(lockedBy).build()).build();
	}

	@Nullable
	public CarrierProductId getCarrierProductId() {return header.getCarrierProductId();}

	public boolean isCarrierAdviseReadOnly() {return header.isCarrierAdviseReadOnly();}

	public PickingJob withCarrierProductId(@Nullable final CarrierProductId carrierProductId)
	{
		return CarrierProductId.equals(header.getCarrierProductId(), carrierProductId)
				? this
				: toBuilder().header(header.toBuilder().carrierProductId(carrierProductId).build()).build();
	}

	public PickingJob withCarrierAdviseReadOnly(final boolean carrierAdviseReadOnly)
	{
		return header.isCarrierAdviseReadOnly() == carrierAdviseReadOnly
				? this
				: toBuilder().header(header.toBuilder().carrierAdviseReadOnly(carrierAdviseReadOnly).build()).build();
	}

	/**
	 * (Re-)initialises the header's carrier state from the lines that are still to be picked — the batch that will
	 * land on the NEXT top-level parcel. Called when a new top-level parcel starts (LU / top-level TU select) or when
	 * a top-level parcel is closed. The header then carries the single distinct carrier product of the unprocessed
	 * lines (all-same → that carrier; divergent or none → {@code null}) and is no longer read-only (advise can run
	 * again against the fresh parcel). This module has no shipper repository, so the RAW carrier aggregate is stored;
	 * the read side ({@code PackedHUCarrierAdviseService}) applies the api-advise filter.
	 */
	public PickingJob withHeaderCarrierFromUnprocessedLines()
	{
		final ImmutableSet<CarrierProductId> unprocessedCarriers = lines.stream()
				.filter(line -> line.getQtyRemainingToPick().signum() > 0)
				.map(PickingJobLine::getCarrierProductId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final CarrierProductId headerCarrier = unprocessedCarriers.size() == 1
				? unprocessedCarriers.iterator().next()
				: null;

		return withCarrierProductId(headerCarrier).withCarrierAdviseReadOnly(false);
	}

	/**
	 * Folds a just-picked line into the header carrier state (the header tracks the CURRENT top-level parcel).
	 * <ul>
	 *     <li><b>non-manual</b> pick → the carrier is set only by advise, so the header carrier + read-only flag are
	 *         left UNCHANGED;</li>
	 *     <li><b>manual</b> pick → the picked carrier is a human override the parcel now carries → header becomes
	 *         read-only, and the header carrier becomes that line's carrier — EXCEPT when the header already holds a
	 *         DIFFERENT non-null carrier (a divergent manual mix on the parcel), where the single carrier collapses
	 *         to {@code null}.</li>
	 * </ul>
	 */
	public PickingJob withHeaderCarrierFromPickedLine(@NonNull final PickingJobLine pickedLine)
	{
		if (!pickedLine.isManual())
		{
			return this;
		}

		final CarrierProductId lineCarrier = pickedLine.getCarrierProductId();
		final CarrierProductId currentHeaderCarrier = header.getCarrierProductId();
		final CarrierProductId newHeaderCarrier = (currentHeaderCarrier != null
				&& !CarrierProductId.equals(currentHeaderCarrier, lineCarrier))
				? null // divergent manual carriers on the same parcel → no single carrier
				: lineCarrier;

		return withCarrierProductId(newHeaderCarrier).withCarrierAdviseReadOnly(true);
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

	public void assertCanBeEditedBy(final UserId userId)
	{
		assertNotProcessed();
		if (!Objects.equals(userId, getLockedBy()))
		{
			throw new AdempiereException("Can be edited only by the user who locked the job");
		}
	}

	public void assertPickingSlotScanned() {currentPickingTarget.assertPickingSlotScanned();}

	@NonNull
	public PickingSlotIdAndCaption getPickingSlotNotNull() {return currentPickingTarget.getPickingSlotNotNull();}

	public Optional<PickingSlotId> getPickingSlotIdEffective(@Nullable final PickingJobLineId lineId)
	{
		return getCurrentPickingTargetEffectiveValue(lineId, CurrentPickingTarget::getPickingSlotId);
	}

	public boolean isPickingSlotRequired() {return header.isPickingSlotRequired();}

	public boolean isDisplayPickingSlotSuggestions() {return header.isDisplayPickingSlotSuggestions();}

	public boolean isProcessed()
	{
		return docStatus.isProcessed();
	}

	public boolean isAllowAbort() {return !isProcessed() && isNothingPicked();}

	public boolean isNothingPicked() {return getProgress().isNotStarted();}

	private CurrentPickingTarget getCurrentPickingTarget(@Nullable final PickingJobLineId lineId) {return getHeaderOrLine(lineId).getCurrentPickingTarget();}

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

	/**
	 * The first <b>existing</b> LU picking target across line- then header-scope, skipping not-yet-materialised
	 * ones (unlike {@link #getLuPickingTargetEffective(PickingJobLineId)}, which returns the first present target).
	 */
	public Optional<LUPickingTarget> getExistingLuPickingTargetEffective(@Nullable final PickingJobLineId lineId)
	{
		return getCurrentPickingTargetEffectiveValue(
				lineId,
				currentPickingTarget -> currentPickingTarget.getLuPickingTarget().filter(LUPickingTarget::isExistingLU));
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

	public PickingJob withClosedLUAndTUPickingTargets(
			boolean isCloseOnHeader,
			boolean isCloseOnLines,
			@Nullable PickingJobLineId onlyLineId,
			@Nullable final LUIdsAndTopLevelTUIdsCollector closedHuIdCollector)
	{
		final PickingJobBuilder builder = toBuilder();
		boolean hasChanges = false;

		if (isCloseOnHeader)
		{
			final CurrentPickingTarget changedCurrentPickingTarget = currentPickingTarget.withClosedLUAndTUPickingTarget(closedHuIdCollector);
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
					return line.withCurrentPickingTarget(currentPickingTarget -> currentPickingTarget.withClosedLUAndTUPickingTarget(closedHuIdCollector));
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

	public ShipmentScheduleAndJobScheduleIdSet getScheduleIds()
	{
		return streamScheduleIds().collect(ShipmentScheduleAndJobScheduleIdSet.collect());
	}

	public Stream<ShipmentScheduleAndJobScheduleId> streamScheduleIds()
	{
		return streamLines().flatMap(PickingJobLine::streamScheduleIds);
	}

	private PickingJobHeaderOrLine getHeaderOrLine(@Nullable final PickingJobLineId lineId) {return lineId != null ? getLineById(lineId) : this;}

	public PickingJobLine getLineById(@NonNull final PickingJobLineId lineId)
	{
		final PickingJobLine line = linesById.get(lineId);
		if (line == null)
		{
			throw new AdempiereException("No line found for " + lineId);
		}
		return line;
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

	@Nullable
	public Quantity getPackedQty(@NonNull final ProductId productId)
	{
		return streamSteps()
				.filter(step -> ProductId.equals(step.getProductId(), productId))
				.map(PickingJobStep::getPackedQty)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(Quantity::add)
				.orElse(null);
	}

	@Nullable
	public ProductValueAndName getSingleProductValueAndName()
	{
		ProductId productId = null;
		ProductValueAndName productValueAndName = null;
		for (final PickingJobLine line : lines)
		{
			if (productId == null)
			{
				productId = line.getProductId();
			}
			else if (!ProductId.equals(productId, line.getProductId()))
			{
				// found different products
				return null;
			}

			productValueAndName = line.getProductValueAndName();
		}

		return productValueAndName;
	}

	/**
	 * Un-joined product names, in sales-order line order (see the sort below). The caller decides the
	 * separator — see {@link #getProductNamesJoined(String)}.
	 */
	@NonNull
	public ImmutableList<ITranslatableString> getProductNameParts()
	{
		// distinct by ProductId (never by displayed text, so two distinct products sharing a name both appear).
		// Order by the sales-order line (C_OrderLine.Line, carried on each line as orderLineSeqNo — already loaded,
		// no query here), tie-broken by the picking-job-line id: a stable, meaningful caption order that matches the
		// order the picker reads off the sales document. Sorting happens HERE (caption-only) so PickingJob.lines'
		// own order is left untouched for the workflow logic that iterates it.
		return lines.stream()
				.filter(StreamUtils.distinctByKey(PickingJobLine::getProductId))
				.sorted(Comparator.comparingInt(PickingJobLine::getOrderLineSeqNo)
						.thenComparingInt(line -> line.getId().getRepoId()))
				.map(line -> line.getProductValueAndName().getName())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ITranslatableString getProductNamesJoined(@NonNull final String separator)
	{
		return getProductNameParts().stream().collect(TranslatableStrings.joining(separator));
	}

	@Nullable
	public Quantity getSingleQtyToPickOrNull()
	{
		return extractQtyToPickOrNull(lines, PickingJobLine::getProductId, PickingJobLine::getQtyToPick);
	}

	@Nullable
	private static <T> Quantity extractQtyToPickOrNull(
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
