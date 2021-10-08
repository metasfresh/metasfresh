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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.ShipmentScheduleId;
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
import java.util.Optional;

@Value
@ToString
public class PickingJobStep
{
	@NonNull PickingJobStepId id;

	@NonNull ShipmentScheduleId shipmentScheduleId;
	@NonNull PickingCandidateId pickingCandidateId;

	//
	// What?
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToPick;
	@NonNull Quantity qtyPicked;
	boolean qtyPickedConfirmed;

	//
	// From where?
	@NonNull LocatorId locatorId;
	@NonNull String locatorName;

	@NonNull HuId huId;
	@NonNull String huBarcode;

	@Builder(toBuilder = true)
	private PickingJobStep(
			@NonNull final PickingJobStepId id,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final PickingCandidateId pickingCandidateId,
			//
			// What?
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final Quantity qtyToPick,
			@Nullable final Quantity qtyPicked,
			final boolean qtyPickedConfirmed,
			//
			// From where?
			@NonNull final LocatorId locatorId,
			@NonNull final String locatorName,
			@NonNull final HuId huId,
			@NonNull final String huBarcode)
	{
		this.id = id;
		this.shipmentScheduleId = shipmentScheduleId;
		this.pickingCandidateId = pickingCandidateId;
		this.productId = productId;
		this.productName = productName;

		this.qtyToPick = qtyToPick;
		this.qtyPicked = qtyPicked != null ? qtyPicked : qtyToPick.toZero();
		Quantity.assertSameUOM(this.qtyToPick, this.qtyPicked); // make sure they have the same UOM
		this.qtyPickedConfirmed = qtyPickedConfirmed;

		this.locatorId = locatorId;
		this.locatorName = locatorName;
		this.huId = huId;
		this.huBarcode = huBarcode;
	}

	public I_C_UOM getUOM()
	{
		return qtyToPick.getUOM();
	}

	public PickingJobStep withQtyPickedAndConfirmed(@NonNull final Quantity newQtyPicked)
	{
		if (newQtyPicked.signum() < 0)
		{
			throw new AdempiereException("Negative qty picked is not allowed");
		}
		else if (newQtyPicked.compareTo(this.qtyToPick) > 0)
		{
			throw new AdempiereException("Maximum allowed qty to pick is " + qtyToPick);
		}

		if (this.qtyPicked.equals(newQtyPicked)
				&& this.qtyPickedConfirmed)
		{
			return this;
		}
		else
		{
			return toBuilder()
					.qtyPicked(newQtyPicked)
					.qtyPickedConfirmed(true)
					.build();
		}
	}
}
