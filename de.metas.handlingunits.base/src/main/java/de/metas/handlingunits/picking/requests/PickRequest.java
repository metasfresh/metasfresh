package de.metas.handlingunits.picking.requests;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.picking.api.PickingSlotId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
public class PickRequest
{
	ShipmentScheduleId shipmentScheduleId;

	HuId pickFromHuId;

	PPOrderId pickFromPickingOrderId;
	ImmutableList<IssueToPickingOrder> issuesToPickingOrder;

	HuPackingInstructionsId packToId;

	PickingSlotId pickingSlotId;

	/** Quantity to be picked. If not set, the whole HU shall be picked */
	Quantity qtyToPick;

	boolean autoReview;

	@Builder
	private PickRequest(
			@NonNull ShipmentScheduleId shipmentScheduleId,
			@Nullable HuId pickFromHuId,
			@Nullable PPOrderId pickFromPickingOrderId,
			@Nullable List<IssueToPickingOrder> issuesToPickingOrder,
			@Nullable HuPackingInstructionsId packToId,
			@Nullable PickingSlotId pickingSlotId,
			@Nullable Quantity qtyToPick,
			boolean autoReview)
	{
		this.shipmentScheduleId = shipmentScheduleId;

		//
		// Pick from HU:
		if (pickFromHuId != null)
		{
			this.pickFromHuId = pickFromHuId;
			this.pickFromPickingOrderId = null;
			this.qtyToPick = qtyToPick; // accept null
			this.issuesToPickingOrder = null;
		}
		//
		// Pick from picking/manufacturing order:
		else if (pickFromPickingOrderId != null)
		{
			Check.assumeNotNull(qtyToPick, "Parameter qtyToPick is not null");

			this.pickFromHuId = null;
			this.pickFromPickingOrderId = pickFromPickingOrderId;
			this.qtyToPick = qtyToPick;
			this.issuesToPickingOrder = ImmutableList.copyOf(issuesToPickingOrder);
		}
		else
		{
			throw new AdempiereException("No pick from source set");
		}

		this.packToId = packToId;

		this.pickingSlotId = pickingSlotId;

		this.autoReview = autoReview;
	}

	@Value
	@Builder
	public static class IssueToPickingOrder
	{
		@NonNull
		PPOrderBOMLineId issueToOrderBOMLineId;

		@NonNull
		Quantity qtyToIssue;
	}
}
