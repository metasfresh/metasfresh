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
 * Neither declarative mechanism can express this, for two DIFFERENT reasons - check both before replacing this
 * class with configuration:
 * <ul>
 * <li>{@code AD_SQLColumn_SourceTableColumn} produces the wrong SHAPE: it always emits
 * {@code CacheInvalidateRequest.rootRecord("M_ShippingPackage", id)}, and {@code M_ShippingPackage} is the root
 * table of no window (tabLevel 1 in both 540020 and 541657), so that request resolves to no window and is
 * dropped - the model cache is reset, the open document is not.</li>
 * <li>{@code AD_ViewSource} would produce the RIGHT shape but cannot REACH the record. Its factory does not
 * build a request itself; it delegates to the window-based parent/child factories for the target table
 * ({@code ViewSourceCacheInvalidateRequestFactory#createRequestsFromModel}), and those are seeded from
 * {@code AD_Window_ParentChildTableNames_v1}, which already registers root {@code M_ShipperTransportation} +
 * child {@code M_ShippingPackage} via {@code M_ShipperTransportation_ID}. The blocker is the link instead: the
 * descriptor carries ONE source and ONE target link column and applies them as a single
 * {@code addEqualsFilter}, but {@code M_Delivery_Planning} and {@code M_ShippingPackage} share no column -
 * neither carries the other's FK, and the linkage lives in {@code M_Delivery_Planning_Alloc}. That is two hops,
 * which the single-hop descriptor cannot traverse.</li>
 * </ul>
 * So the routable shape is root {@code M_ShipperTransportation} + child {@code M_ShippingPackage}, built here
 * from the allocation because only the allocation knows both ids.
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
