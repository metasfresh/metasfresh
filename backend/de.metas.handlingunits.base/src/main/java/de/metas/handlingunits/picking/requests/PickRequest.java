package de.metas.handlingunits.picking.requests;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
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

	PickFrom pickFrom;
	ImmutableList<IssueToPickingOrderRequest> issuesToPickingOrder;

	HuPackingInstructionsId packToId;

	PickingSlotId pickingSlotId;

	/** Quantity to be picked. If not set, the whole HU shall be picked */
	Quantity qtyToPick;

	boolean autoReview;

	@Builder
	private PickRequest(
			@NonNull ShipmentScheduleId shipmentScheduleId,
			@NonNull PickFrom pickFrom,
			@Nullable List<IssueToPickingOrderRequest> issuesToPickingOrder,
			@Nullable HuPackingInstructionsId packToId,
			@Nullable PickingSlotId pickingSlotId,
			@Nullable Quantity qtyToPick,
			boolean autoReview)
	{
		this.shipmentScheduleId = shipmentScheduleId;

		this.pickFrom = pickFrom;

		//
		// Pick from HU:
		if (pickFrom.isPickFromHU())
		{
			this.qtyToPick = qtyToPick; // accept null
			this.issuesToPickingOrder = null;
		}
		//
		// Pick from picking/manufacturing order:
		else if (pickFrom.isPickFromPickingOrder())
		{
			Check.assumeNotNull(qtyToPick, "Parameter qtyToPick is not null");
			this.qtyToPick = qtyToPick;
			this.issuesToPickingOrder = ImmutableList.copyOf(issuesToPickingOrder);
		}
		else
		{
			throw new AdempiereException("Unknown pick from: " + pickFrom);
		}

		this.packToId = packToId;

		this.pickingSlotId = pickingSlotId;

		this.autoReview = autoReview;
	}

	@Value
	@Builder
	public static class IssueToPickingOrderRequest
	{
		@NonNull
		PPOrderBOMLineId issueToOrderBOMLineId;

		@NonNull
		HuId issueFromHUId;

		@NonNull
		ProductId productId;

		@NonNull
		Quantity qtyToIssue;
	}
}
