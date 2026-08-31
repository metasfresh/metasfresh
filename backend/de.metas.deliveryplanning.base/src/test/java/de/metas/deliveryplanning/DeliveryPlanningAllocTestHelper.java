/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import lombok.NonNull;

import java.util.Arrays;

/**
 * Builds the {@link DeliveryPlanningAlloc}s an in-memory {@link DeliveryPlanning} carries, from the only thing the
 * rules under test actually ask about: the delivery instruction the planning sits on.
 * <p>
 * The remaining three ids are synthetic and unique per allocation.
 */
final class DeliveryPlanningAllocTestHelper
{
	private static int nextRepoId = 1_000_000;

	/** The allocations of a planning sitting on the given instructions - one allocation per instruction. */
	static ImmutableList<DeliveryPlanningAlloc> allocatedTo(@NonNull final ShipperTransportationId... deliveryInstructionIds)
	{
		return Arrays.stream(deliveryInstructionIds)
				.map(DeliveryPlanningAllocTestHelper::allocationTo)
				.collect(ImmutableList.toImmutableList());
	}

	static DeliveryPlanningAlloc allocationTo(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final int repoId = nextRepoId++;

		return DeliveryPlanningAlloc.builder()
				.id(DeliveryPlanningAllocId.ofRepoId(repoId))
				.deliveryPlanningId(DeliveryPlanningId.ofRepoId(repoId))
				.deliveryInstructionId(deliveryInstructionId)
				.shippingPackageId(ShippingPackageId.ofRepoId(repoId))
				.build();
	}

	private DeliveryPlanningAllocTestHelper()
	{
	}
}
