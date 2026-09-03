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
import de.metas.shipping.model.ShipperTransportationId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The shape of the cache-invalidation request that makes a delivery instruction's Versandpaket line show a
 * planning's new quantity without a manual reload (Task Q14, TC11).
 * <p>
 * The four figures are {@code ColumnSQL} on {@code M_ShippingPackage}, and the generic
 * {@code AD_SQLColumn_SourceTableColumn} machinery answers a planning change with
 * {@code CacheInvalidateRequest.rootRecord("M_ShippingPackage", id)}
 * ({@code ColumnSqlCacheInvalidateRequestFactories}). That request cannot reach the WebUI document:
 * {@code DocumentCollection#invalidate} resolves the request's ROOT table name through
 * {@code tableName2windowIds}, which is filled only from ROOT entity descriptors, and
 * {@code M_ShippingPackage} is the root table of no window - it is tabLevel 1 in both windows that carry it
 * (540020 "Transport Auftrag" and 541657 "Lieferanweisungen"). The request is therefore dropped.
 * <p>
 * So these tests pin the two properties the request must have, both of which the generic path lacks: the ROOT
 * record is the delivery INSTRUCTION (the shape {@code DocumentCollection} can route), and the CHILD record is
 * the shipping package (so only that included row is marked stale, rather than the whole document being
 * evicted) - the same {@code rootRecord(...).childRecord(...)} pairing
 * {@code ParentChildModelCacheInvalidateRequestFactory} produces for an ordinary child-record change.
 */
public class DeliveryInstructionLineCacheInvalidationTest
{
	private static final ShipperTransportationId INSTRUCTION_1 = ShipperTransportationId.ofRepoId(4_000_001);
	private static final ShipperTransportationId INSTRUCTION_2 = ShipperTransportationId.ofRepoId(4_000_002);

	@Test
	void noAllocation_yieldsNoRequest()
	{
		assertThat(DeliveryInstructionLineCacheInvalidation.requestForAllocationsOrNull(ImmutableList.of())).isNull();
	}

	@Test
	void oneAllocation_namesTheInstructionAsRootAndThePackageAsChild()
	{
		final DeliveryPlanningAlloc allocation = DeliveryPlanningAllocTestHelper.allocationTo(INSTRUCTION_1);

		final CacheInvalidateMultiRequest multiRequest =
				DeliveryInstructionLineCacheInvalidation.requestForAllocationsOrNull(ImmutableList.of(allocation));

		assertThat(multiRequest).isNotNull();
		final List<CacheInvalidateRequest> requests = ImmutableList.copyOf(multiRequest.getRequests());
		assertThat(requests).hasSize(1);

		final CacheInvalidateRequest request = requests.get(0);
		assertThat(request.getRootRecordOrNull())
				.as("the ROOT record must be the delivery instruction - a request rooted at M_ShippingPackage is "
						+ "dropped by DocumentCollection#invalidate, which is the whole defect this fixes")
				.isEqualTo(TableRecordReference.of(I_M_ShipperTransportation.Table_Name, INSTRUCTION_1.getRepoId()));
		assertThat(request.getChildRecordOrNull())
				.as("the CHILD record must be the shipping package, so only its included row is marked stale")
				.isEqualTo(TableRecordReference.of(
						I_M_ShippingPackage.Table_Name, allocation.getShippingPackageId().getRepoId()));
	}

	/**
	 * A planning may sit on more than one delivery instruction ({@code getAllocationsByPlanningId} returns a
	 * multimap for exactly that reason), and every one of them displays the planning's figures - so each gets
	 * its own request rather than only the first.
	 */
	@Test
	void twoAllocations_yieldOneRequestPerInstruction()
	{
		final ImmutableList<DeliveryPlanningAlloc> allocations =
				DeliveryPlanningAllocTestHelper.allocatedTo(INSTRUCTION_1, INSTRUCTION_2);

		final CacheInvalidateMultiRequest multiRequest =
				DeliveryInstructionLineCacheInvalidation.requestForAllocationsOrNull(allocations);

		assertThat(multiRequest).isNotNull();
		assertThat(multiRequest.getRequests())
				.extracting(CacheInvalidateRequest::getRootRecordOrNull)
				.containsExactlyInAnyOrder(
						TableRecordReference.of(I_M_ShipperTransportation.Table_Name, INSTRUCTION_1.getRepoId()),
						TableRecordReference.of(I_M_ShipperTransportation.Table_Name, INSTRUCTION_2.getRepoId()));
	}
}
