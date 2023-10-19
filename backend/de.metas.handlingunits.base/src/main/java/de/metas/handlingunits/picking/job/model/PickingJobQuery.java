/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.bpartner.BPartnerId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.user.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Set;

@Value
@Builder
public class PickingJobQuery
{
	@NonNull UserId userId;
	@NonNull @Builder.Default ImmutableSet<ShipmentScheduleId> excludeShipmentScheduleIds = ImmutableSet.of();
	@Nullable PickingJobFacetsQuery facets;
	@NonNull @Builder.Default @Getter(AccessLevel.NONE) ImmutableSet<BPartnerId> onlyBPartnerIds = ImmutableSet.of();

	@NonNull
	public Set<BPartnerId> getOnlyBPartnerIdsEffective()
	{
		if (onlyBPartnerIds.isEmpty())
		{
			return facets != null ? facets.getCustomerIds() : ImmutableSet.of();
		}
		else
		{
			if (facets != null && !facets.getCustomerIds().isEmpty())
			{
				return Sets.intersection(onlyBPartnerIds, facets.getCustomerIds());
			}
			else
			{
				return onlyBPartnerIds;
			}
		}
	}

	public ImmutableSet<LocalDate> getDeliveryDays()
	{
		return facets != null ? facets.getDeliveryDays() : ImmutableSet.of();
	}
}