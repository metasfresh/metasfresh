package de.metas.handlingunits.picking.requests;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

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
	@Nullable PickingCandidateId existingPickingCandidateId;
	@NonNull ShipmentScheduleId shipmentScheduleId;

	@NonNull PickFrom pickFrom;
	@Nullable ImmutableList<IssueToPickingOrderRequest> issuesToPickingOrder;

	@Nullable PackToSpec packToSpec;

	@Nullable PickingSlotId pickingSlotId;

	/**
	 * Quantity to be picked. If not set, the whole HU shall be picked
	 */
	@Nullable Quantity qtyToPick;
	@Nullable QtyRejectedWithReason qtyRejected;

	boolean autoReview;

	@Builder
	private PickRequest(
			@Nullable final PickingCandidateId existingPickingCandidateId,
			@NonNull ShipmentScheduleId shipmentScheduleId,
			@NonNull PickFrom pickFrom,
			@Nullable List<IssueToPickingOrderRequest> issuesToPickingOrder,
			@Nullable PackToSpec packToSpec,
			@Nullable PickingSlotId pickingSlotId,
			@Nullable Quantity qtyToPick,
			@Nullable QtyRejectedWithReason qtyRejected,
			boolean autoReview)
	{
		this.existingPickingCandidateId = existingPickingCandidateId;
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
			Objects.requireNonNull(qtyToPick, "Parameter qtyToPick is not null");
			this.qtyToPick = qtyToPick;
			this.issuesToPickingOrder = ImmutableList.copyOf(Objects.requireNonNull(issuesToPickingOrder));
		}
		else
		{
			throw new AdempiereException("Unknown pick from: " + pickFrom);
		}

		this.qtyRejected = qtyRejected;

		this.packToSpec = packToSpec;

		this.pickingSlotId = pickingSlotId;

		this.autoReview = autoReview;
	}

	@Value
	@Builder
	public static class IssueToPickingOrderRequest
	{
		@NonNull PPOrderBOMLineId issueToOrderBOMLineId;
		@NonNull HuId issueFromHUId;
		@NonNull ProductId productId;
		@NonNull Quantity qtyToIssue;
	}
}
