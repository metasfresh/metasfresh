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
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import java.math.BigDecimal;

@ToString
public class PickingJobStep
{
	@Getter
	@NonNull private final PickingJobStepId id;

	@Getter
	@NonNull private final ShipmentScheduleId shipmentScheduleId;

	//
	// What?
	@Getter
	@NonNull ProductId productId;
	@Getter
	@NonNull ITranslatableString productName;
	@Getter
	@NonNull private final Quantity qtyToPick;

	//
	// From where?
	@Getter
	@NonNull LocatorId locatorId;
	@Getter
	@NonNull String locatorName;

	@Getter
	@NonNull private final HuId huId;
	@Getter
	@NonNull private final String huBarcode;

	//
	// Status
	@Getter
	@NonNull private Quantity qtyPicked;
	@Getter
	boolean pickingDone;

	@Builder
	private PickingJobStep(
			@NonNull final PickingJobStepId id,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			//
			// What?
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final Quantity qtyToPick,
			//
			// From where?
			@NonNull final LocatorId locatorId,
			@NonNull final String locatorName,
			@NonNull final HuId huId,
			@NonNull final String huBarcode)
	{
		this.id = id;
		this.shipmentScheduleId = shipmentScheduleId;
		this.productId = productId;
		this.productName = productName;
		this.qtyToPick = qtyToPick;
		this.qtyPicked = qtyToPick.toZero();
		this.locatorId = locatorId;
		this.locatorName = locatorName;
		this.huId = huId;
		this.huBarcode = huBarcode;
	}

	void changeQtyPicked(@NonNull final BigDecimal qtyPickedBD)
	{
		if (qtyPickedBD.signum() < 0)
		{
			throw new AdempiereException("Negative qty picked is not allowed");
		}

		this.qtyPicked = Quantity.of(qtyPickedBD, this.qtyPicked.getUOM());
		this.pickingDone = true;
	}
}
