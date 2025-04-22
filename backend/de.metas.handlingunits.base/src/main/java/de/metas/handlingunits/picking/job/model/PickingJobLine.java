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
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.ean13.EAN13ProductCode;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PickingJobLine
{
	@NonNull PickingJobLineId id;
	@NonNull ITranslatableString caption;

	@NonNull ProductId productId;
	@NonNull String productNo;
	@Nullable EAN13ProductCode ean13ProductCode;
	@NonNull ProductCategoryId productCategoryId;
	@NonNull ITranslatableString productName;
	@NonNull HUPIItemProduct packingInfo;
	@NonNull Quantity qtyToPick;
	@NonNull OrderAndLineId salesOrderAndLineId;
	@NonNull String salesOrderDocumentNo;
	int orderLineSeqNo;
	@NonNull BPartnerLocationId deliveryBPLocationId;
	@NonNull ShipmentScheduleId shipmentScheduleId;
	@Nullable UomId catchUomId;
	@Nullable PPOrderId pickFromManufacturingOrderId;
	@NonNull ImmutableList<PickingJobStep> steps;
	boolean isManuallyClosed;

	// computed values
	@NonNull PickingJobProgress progress;

	//
	// Pick Target
	@NonNull @Getter CurrentPickingTarget currentPickingTarget;

	@NonNull PickingUnit pickingUnit;
	@NonNull Quantity qtyPicked;
	@NonNull Quantity qtyRejected;
	@NonNull Quantity qtyRemainingToPick;
	@Nullable QtyTU qtyToPickTUs; // not null if pickingUnit==TU
	@Nullable QtyTU qtyPickedTUs;// not null if pickingUnit==TU
	@Nullable QtyTU qtyRejectedTUs;// not null if pickingUnit==TU
	@Nullable QtyTU qtyRemainingToPickTUs;// not null if pickingUnit==TU

	@Builder(toBuilder = true)
	private PickingJobLine(
			@NonNull final PickingJobLineId id,
			@NonNull final ITranslatableString caption,
			@NonNull final ProductId productId,
			@NonNull final String productNo,
			@Nullable final EAN13ProductCode ean13ProductCode,
			@NonNull final ProductCategoryId productCategoryId,
			@NonNull final ITranslatableString productName,
			@NonNull final HUPIItemProduct packingInfo,
			@NonNull final Quantity qtyToPick,
			@NonNull final OrderAndLineId salesOrderAndLineId,
			@NonNull final String salesOrderDocumentNo,
			@NonNull final Integer orderLineSeqNo,
			@NonNull final BPartnerLocationId deliveryBPLocationId,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final UomId catchUomId,
			@Nullable final PPOrderId pickFromManufacturingOrderId,
			@NonNull final ImmutableList<PickingJobStep> steps,
			@Nullable final CurrentPickingTarget currentPickingTarget,
			@NonNull final PickingUnit pickingUnit,
			final boolean isManuallyClosed)
	{
		this.id = id;
		this.caption = caption;
		this.productId = productId;
		this.productNo = productNo;
		this.ean13ProductCode = ean13ProductCode;
		this.productCategoryId = productCategoryId;
		this.productName = productName;
		this.packingInfo = packingInfo;
		this.qtyToPick = qtyToPick;
		this.salesOrderAndLineId = salesOrderAndLineId;
		this.salesOrderDocumentNo = salesOrderDocumentNo;
		this.orderLineSeqNo = orderLineSeqNo;
		this.deliveryBPLocationId = deliveryBPLocationId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.catchUomId = catchUomId;
		this.pickFromManufacturingOrderId = pickFromManufacturingOrderId;
		this.steps = steps;
		this.isManuallyClosed = isManuallyClosed;

		this.currentPickingTarget = currentPickingTarget != null ? currentPickingTarget : CurrentPickingTarget.EMPTY;

		this.pickingUnit = pickingUnit;
		this.qtyPicked = steps.stream().map(PickingJobStep::getQtyPicked).reduce(Quantity::add).orElseGet(qtyToPick::toZero);
		this.qtyRejected = steps.stream().map(PickingJobStep::getQtyRejected).reduce(Quantity::add).orElseGet(qtyToPick::toZero);
		final Quantity qtyPickedOrRejected = qtyPicked.add(qtyRejected);
		this.qtyRemainingToPick = this.qtyToPick.subtract(qtyPickedOrRejected).toZeroIfNegative();

		if (this.pickingUnit.isTU())
		{
			this.qtyToPickTUs = this.packingInfo.computeQtyTUsOfTotalCUs(this.qtyToPick, this.productId);
			this.qtyPickedTUs = this.packingInfo.computeQtyTUsOfTotalCUs(this.qtyPicked, this.productId);
			this.qtyRejectedTUs = this.packingInfo.computeQtyTUsOfTotalCUs(this.qtyRejected, this.productId);
			final QtyTU qtyPickedOrRejectedTUs = qtyPickedTUs.add(qtyRejectedTUs);
			this.qtyRemainingToPickTUs = this.qtyToPickTUs.subtractOrZero(qtyPickedOrRejectedTUs);
		}
		else
		{
			this.qtyToPickTUs = null;
			this.qtyPickedTUs = null;
			this.qtyRejectedTUs = null;
			this.qtyRemainingToPickTUs = null;
		}

		this.progress = computeProgress(this.isManuallyClosed, this.qtyToPick, this.qtyPicked);
	}

	public BPartnerId getCustomerId() {return this.deliveryBPLocationId.getBpartnerId();}

	private static PickingJobProgress computeProgress(final boolean isManuallyClosed, final Quantity qtyToPick, final Quantity qtyPicked)
	{
		if (isManuallyClosed || qtyPicked.compareTo(qtyToPick) >= 0)
		{
			return PickingJobProgress.DONE;
		}
		if (qtyPicked.isPositive())
		{
			return PickingJobProgress.IN_PROGRESS;
		}
		return PickingJobProgress.NOT_STARTED;
	}

	public I_C_UOM getUOM() {return qtyToPick.getUOM();}

	Stream<ShipmentScheduleId> streamShipmentScheduleId()
	{
		return Stream.concat(
						Stream.of(shipmentScheduleId),
						streamSteps().map(PickingJobStep::getShipmentScheduleId)
				)
				.filter(Objects::nonNull);
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

	public PickingJobLine withNewStep(@NonNull final PickingJob.AddStepRequest request)
	{
		final PickingJobStep newStep = PickingJobStep.builder()
				.id(request.getNewStepId())
				.isGeneratedOnFly(request.isGeneratedOnFly())
				.salesOrderAndLineId(salesOrderAndLineId)
				.shipmentScheduleId(shipmentScheduleId)
				.productId(productId)
				.productName(productName)
				.qtyToPick(request.getQtyToPick())
				.pickFroms(PickingJobStepPickFromMap.ofList(ImmutableList.of(
						PickingJobStepPickFrom.builder()
								.pickFromKey(PickingJobStepPickFromKey.MAIN)
								.pickFromLocator(request.getPickFromLocator())
								.pickFromHU(request.getPickFromHU())
								.build()
				)))
				.packToSpec(request.getPackToSpec())
				.build();

		return toBuilder()
				.steps(ImmutableList.<PickingJobStep>builder()
						.addAll(this.steps)
						.add(newStep)
						.build())
				.build();
	}

	public PickingJobLine withManuallyClosed(final boolean isManuallyClosed)
	{
		return this.isManuallyClosed != isManuallyClosed
				? toBuilder().isManuallyClosed(isManuallyClosed).build()
				: this;
	}

	@NonNull
	public ImmutableSet<HuId> getPickedHUIds()
	{
		return steps.stream()
				.map(PickingJobStep::getPickedHUIds)
				.flatMap(List::stream)
				.collect(ImmutableSet.toImmutableSet());
	}

	PickingJobLine withCurrentPickingTarget(@NonNull final CurrentPickingTarget currentPickingTarget)
	{
		return !CurrentPickingTarget.equals(this.currentPickingTarget, currentPickingTarget)
				? toBuilder().currentPickingTarget(currentPickingTarget).build()
				: this;
	}

	PickingJobLine withCurrentPickingTarget(@NonNull final UnaryOperator<CurrentPickingTarget> currentPickingTargetMapper)
	{
		final CurrentPickingTarget changedCurrentPickingTarget = currentPickingTargetMapper.apply(this.currentPickingTarget);
		return !CurrentPickingTarget.equals(this.currentPickingTarget, changedCurrentPickingTarget)
				? toBuilder().currentPickingTarget(changedCurrentPickingTarget).build()
				: this;
	}

	public boolean isPickingSlotSet() {return currentPickingTarget.isPickingSlotSet();}

	public Optional<PickingSlotId> getPickingSlotId() {return currentPickingTarget.getPickingSlotId();}

	public PickingJobLine withPickingSlot(@Nullable final PickingSlotIdAndCaption pickingSlot)
	{
		return withCurrentPickingTarget(currentPickingTarget.withPickingSlot(pickingSlot));
	}
}
