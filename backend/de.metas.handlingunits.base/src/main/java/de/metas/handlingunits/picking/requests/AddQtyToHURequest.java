package de.metas.handlingunits.picking.requests;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class AddQtyToHURequest
{
	@NonNull
	Quantity qtyToPack;
	@NonNull
	HuId packToHuId;
	@NonNull
	PickingSlotId pickingSlotId;
	@NonNull
	ShipmentScheduleId shipmentScheduleId;
	@NonNull
	ImmutableList<HuId> sourceHUIds;

	boolean allowOverDelivery;

	@Builder
	private AddQtyToHURequest(
			@NonNull final Quantity qtyToPack,
			@NonNull final HuId packToHuId,
			@NonNull final PickingSlotId pickingSlotId,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final ImmutableList<HuId> sourceHUIds,
			final boolean allowOverDelivery)
	{
		if (qtyToPack.signum() <= 0)
		{
			throw new AdempiereException("@Invalid@ @QtyToPack@");
		}

		this.qtyToPack = qtyToPack;
		this.packToHuId = packToHuId;
		this.pickingSlotId = pickingSlotId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.allowOverDelivery = allowOverDelivery;
		this.sourceHUIds = sourceHUIds;
	}

}
