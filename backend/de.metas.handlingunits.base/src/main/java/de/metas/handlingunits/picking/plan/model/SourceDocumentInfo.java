/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.plan.model;

import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateSnapshot;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder(toBuilder = true)
public class SourceDocumentInfo
{
	@NonNull ShipmentScheduleId shipmentScheduleId;
	@Nullable OrderAndLineId salesOrderLineId;
	@Nullable ShipperId shipperId;
	@NonNull PackToSpec packToSpec;
	@Nullable PickingCandidateSnapshot existingPickingCandidate;

	public SourceDocumentInfo withExistingPickingCandidate(@Nullable final PickingCandidateSnapshot existingPickingCandidate)
	{
		return !Objects.equals(this.existingPickingCandidate, existingPickingCandidate)
				? toBuilder().existingPickingCandidate(existingPickingCandidate).build()
				: this;
	}

	public SourceDocumentInfo withExistingPickingCandidate(@Nullable final PickingCandidate existingPickingCandidate)
	{
		return withExistingPickingCandidate(existingPickingCandidate != null ? existingPickingCandidate.snapshot() : null);
	}
}
