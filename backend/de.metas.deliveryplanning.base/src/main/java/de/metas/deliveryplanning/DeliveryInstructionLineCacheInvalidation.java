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
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * The cache-invalidation request that makes a delivery instruction's {@code M_ShippingPackage} line show a
 * planning's CURRENT quantities without a manual reload.
 * <p>
 * The shape is root {@code M_ShipperTransportation} + child {@code M_ShippingPackage} because a request has to
 * resolve to a window to reach an open document, and {@code M_ShippingPackage} is a child tab (tabLevel 1) in
 * both 540020 and 541657 - the root table of no window. A bare
 * {@code rootRecord("M_ShippingPackage", id)} resolves to no window and is dropped: the model cache is reset,
 * the open document is not.
 */
@UtilityClass
public final class DeliveryInstructionLineCacheInvalidation
{
	/**
	 * One request per allocation - a planning may sit on more than one delivery instruction.
	 *
	 * @return {@code null} when there is nothing to invalidate, so the caller can skip the broadcast entirely.
	 */
	@Nullable
	public static CacheInvalidateMultiRequest requestForAllocationsOrNull(@NonNull final Collection<DeliveryPlanningAlloc> allocations)
	{
		if (allocations.isEmpty())
		{
			return null;
		}

		return CacheInvalidateMultiRequest.of(allocations.stream()
				.map(DeliveryInstructionLineCacheInvalidation::toRequest)
				.collect(ImmutableList.toImmutableList()));
	}

	private static CacheInvalidateRequest toRequest(@NonNull final DeliveryPlanningAlloc allocation)
	{
		return CacheInvalidateRequest.builder()
				.rootRecord(I_M_ShipperTransportation.Table_Name, allocation.getDeliveryInstructionId().getRepoId())
				.childRecord(I_M_ShippingPackage.Table_Name, allocation.getShippingPackageId().getRepoId())
				.build();
	}
}
