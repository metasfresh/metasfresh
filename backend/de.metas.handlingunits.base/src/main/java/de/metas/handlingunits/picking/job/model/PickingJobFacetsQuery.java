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
import de.metas.bpartner.BPartnerId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PickingJobFacetsQuery
{
	public static final PickingJobFacetsQuery EMPTY = builder().build();

	@NonNull @Singular ImmutableSet<BPartnerId> customerIds;
	@NonNull @Singular ImmutableSet<LocalDate> deliveryDays;

	public boolean isMatching(final PickingJobReference pickingJobReference)
	{
		return isCustomerMatching(pickingJobReference)
				&& isDeliveryDateMatching(pickingJobReference);
	}

	private boolean isCustomerMatching(final PickingJobReference pickingJobReference)
	{
		return customerIds.isEmpty() || customerIds.contains(pickingJobReference.getCustomerId());
	}

	private boolean isDeliveryDateMatching(final PickingJobReference pickingJobReference)
	{
		return deliveryDays.isEmpty() || deliveryDays.stream().anyMatch(deliveryDay -> isDeliveryDayMatching(pickingJobReference, deliveryDay));
	}

	private static boolean isDeliveryDayMatching(final PickingJobReference pickingJobReference, final LocalDate deliveryDay)
	{
		return pickingJobReference.getDeliveryDate().toLocalDate().equals(deliveryDay);
	}
}
