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
 * planning's CURRENT quantities without a manual reload (Task Q14, TC11).
 * <p>
 * <b>Why this exists at all - the generic path cannot express it.</b> The four figures are {@code ColumnSQL}
 * on {@code M_ShippingPackage} reading the planning through {@code M_Delivery_Planning_Alloc}, and they carry
 * the {@code AD_SQLColumn_SourceTableColumn} rows that rule 1 of the application-dictionary skill requires. But
 * that machinery always emits {@code CacheInvalidateRequest.rootRecord("M_ShippingPackage", id)}
 * ({@code ColumnSqlCacheInvalidateRequestFactories}, both fetch methods), and
 * {@code DocumentCollection#invalidate} resolves a request's ROOT table name through {@code tableName2windowIds},
 * which is filled only from ROOT entity descriptors. {@code M_ShippingPackage} is the root table of no window -
 * it is tabLevel 1 in both windows that carry it (540020 "Transport Auftrag", 541657 "Lieferanweisungen") - so
 * that request resolves to no window and is dropped. The model cache is reset, the open document is not.
 * <p>
 * <b>The shape that IS routable</b> is the one {@code ParentChildModelCacheInvalidateRequestFactory} produces for
 * an ordinary child-record change, and which {@code DocumentCacheInvalidationDispatcher} turns into
 * {@code DocumentToInvalidate#addIncludedDocument}: root = the document's root record, child = the included row.
 * Here that is root {@code M_ShipperTransportation}, child {@code M_ShippingPackage}. The frontend is then told
 * through {@code DocumentCollection#sendWebsocketChangeEvents} -> {@code staleIncludedDocuments}, which is
 * exactly "the row refreshes itself, with no F5".
 * <p>
 * <b>Why not the {@code WEBUI_ViewInvalidateOnChange} row this task already ships:</b> that config drives
 * {@code ConfiguredViewInvalidationListener}, which only full-invalidates {@code IView}s of the configured
 * window. The instruction's list view is an {@code IView} and does benefit; the Versandpaket tab of an OPEN
 * instruction is an included document, not a view, and is untouched by it. The two are complementary, not
 * alternatives.
 * <p>
 * Kept as a pure mapping (allocations in, request out) so the routable shape is asserted by a plain unit test -
 * see {@code DeliveryInstructionLineCacheInvalidationTest}. Firing it is
 * {@link DeliveryPlanningRepository#invalidateDeliveryInstructionLinesFor(DeliveryPlanningId)}.
 */
@UtilityClass
public final class DeliveryInstructionLineCacheInvalidation
{
	/**
	 * One request per allocation - a planning may sit on more than one delivery instruction, and every one of
	 * them displays its figures.
	 *
	 * @return {@code null} when there is nothing to invalidate, so the caller can skip the broadcast entirely
	 * rather than send an empty one.
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
