package de.metas.handlingunits.picking.requests;

import javax.annotation.Nullable;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.quantity.Quantity;
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
@Builder
public class PickHURequest
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;
	@NonNull
	HuId pickFromHuId;
	@Nullable
	HuPackingInstructionsId packToId;

	@Nullable
	PickingSlotId pickingSlotId;

	/** Quantity to be picked. If not set, the whole HU shall be picked */
	@Nullable
	Quantity qtyToPick;

	boolean autoReview;

	boolean createPickingCandidatesOnly;
}
