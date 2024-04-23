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
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.compiere.model.I_C_UOM;

import java.util.Objects;
import java.util.function.UnaryOperator;

@Value
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PickingJobStep
{
	@NonNull PickingJobStepId id;
	boolean isGeneratedOnFly;

	@NonNull OrderAndLineId salesOrderAndLineId;
	@NonNull ShipmentScheduleId shipmentScheduleId;

	//
	// What?
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToPick;

	//
	// Pick From
	@NonNull PickingJobStepPickFromMap pickFroms;

	//
	// Pick To Specification
	@NonNull PackToSpec packToSpec;

	@NonNull PickingJobProgress progress;

	@Builder(toBuilder = true)
	@Jacksonized
	private PickingJobStep(
			@NonNull final PickingJobStepId id,
			final boolean isGeneratedOnFly,
			@NonNull final OrderAndLineId salesOrderAndLineId,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			//
			// What?
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final Quantity qtyToPick,
			//
			// Pick From
			@NonNull final PickingJobStepPickFromMap pickFroms,
			//
			// Pick To Specification
			@NonNull final PackToSpec packToSpec)
	{
		this.id = id;
		this.isGeneratedOnFly = isGeneratedOnFly;
		this.salesOrderAndLineId = salesOrderAndLineId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.productId = productId;
		this.productName = productName;
		this.qtyToPick = qtyToPick;
		this.pickFroms = pickFroms;
		this.packToSpec = packToSpec;

		this.progress = this.pickFroms.getProgress();
	}

	public I_C_UOM getUOM() {return qtyToPick.getUOM();}

	public boolean isNothingPicked() {return pickFroms.isNothingPicked();}

	public Quantity getQtyPicked()
	{
		return pickFroms.getQtyPicked().orElseGet(qtyToPick::toZero);
	}

	public Quantity getQtyRejected()
	{
		return pickFroms.getQtyRejected().orElseGet(qtyToPick::toZero);
	}

	public PickingJobStep reduceWithPickedEvent(
			@NonNull PickingJobStepPickFromKey key,
			@NonNull PickingJobStepPickedTo pickedTo)
	{
		return withChangedPickFroms(pickFroms -> pickFroms.reduceWithPickedEvent(key, pickedTo));
	}

	public PickingJobStep reduceWithUnpickEvent(
			@NonNull PickingJobStepPickFromKey key,
			@NonNull PickingJobStepUnpickInfo unpicked)
	{
		return withChangedPickFroms(pickFroms -> pickFroms.reduceWithUnpickEvent(key, unpicked));
	}

	private PickingJobStep withChangedPickFroms(@NonNull final UnaryOperator<PickingJobStepPickFromMap> mapper)
	{
		final PickingJobStepPickFromMap newPickFroms = mapper.apply(this.pickFroms);
		return !Objects.equals(this.pickFroms, newPickFroms)
				? toBuilder().pickFroms(newPickFroms).build()
				: this;
	}

	public ImmutableSet<PickingJobStepPickFromKey> getPickFromKeys()
	{
		return pickFroms.getKeys();
	}

	public PickingJobStepPickFrom getPickFrom(@NonNull final PickingJobStepPickFromKey key)
	{
		return pickFroms.getPickFrom(key);
	}

	public PickingJobStepPickFrom getPickFromByHUQRCode(@NonNull final HUQRCode qrCode)
	{
		return pickFroms.getPickFromByHUQRCode(qrCode);
	}

}
