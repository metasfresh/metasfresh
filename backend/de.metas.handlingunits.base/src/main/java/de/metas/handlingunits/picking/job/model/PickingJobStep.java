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

import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;

@Value
@ToString
public class PickingJobStep
{
	@NonNull PickingJobStepId id;

	@NonNull OrderAndLineId salesOrderAndLineId;
	@NonNull ShipmentScheduleId shipmentScheduleId;

	//
	// What?
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToPick;

	//
	// Pick From
	@NonNull LocatorId locatorId;
	@NonNull String locatorName;
	@NonNull HuId pickFromHUId;
	@NonNull HUBarcode pickFromHUBarcode;

	@Nullable PickingJobStepPickedInfo picked;

	@Builder(toBuilder = true)
	private PickingJobStep(
			@NonNull final PickingJobStepId id,
			@NonNull final OrderAndLineId salesOrderAndLineId,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			//
			// What?
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final Quantity qtyToPick,
			//
			// Pick From
			@NonNull final LocatorId locatorId,
			@NonNull final String locatorName,
			@NonNull final HuId pickFromHUId,
			@NonNull final HUBarcode pickFromHUBarcode,
			//
			@Nullable PickingJobStepPickedInfo picked)
	{
		this.id = id;
		this.salesOrderAndLineId = salesOrderAndLineId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.productId = productId;
		this.productName = productName;
		this.qtyToPick = qtyToPick;

		this.locatorId = locatorId;
		this.locatorName = locatorName;
		this.pickFromHUId = pickFromHUId;
		this.pickFromHUBarcode = pickFromHUBarcode;
		this.picked = picked;

		if (this.picked != null)
		{
			Quantity.assertSameUOM(qtyToPick, this.picked.getQtyPicked()); // make sure they have the same UOM
			this.picked.validateQtyRejectedReasonCode(this.qtyToPick);
		}
	}

	public I_C_UOM getUOM() {return qtyToPick.getUOM();}

	public void assertNotPicked()
	{
		if (isPicked())
		{
			throw new AdempiereException("Step already picked: " + getId());
		}
	}

	public void assertPicked()
	{
		if (!isPicked())
		{
			throw new AdempiereException("Step was not picked: " + getId());
		}
	}

	public boolean isPicked() {return picked != null;}

	public PickingJobStep reduceWithPickedEvent(@NonNull PickingJobStepPickedInfo picked) {return toBuilder().picked(picked).build();}

	public PickingJobStep reduceWithUnpickEvent() {return toBuilder().picked(null).build();}
}
