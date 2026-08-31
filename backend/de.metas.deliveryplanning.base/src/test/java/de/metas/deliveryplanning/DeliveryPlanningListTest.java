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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
import de.metas.incoterms.IncotermsId;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.ShipperTransportationId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pure in-memory combinatorics of the aggregation admissibility rule.
 * <p>
 * Deliberately does NOT re-assert the happy path (combine/add/remove end-to-end is covered by cucumber):
 * this test only pins the edges a feature file is bad at, above all the NULL-vs-distinct distinction —
 * {@code count(DISTINCT col)} ignores NULLs, so a naive distinct-count cannot tell "all rows share the
 * same value" from "some rows have no value at all".
 */
class DeliveryPlanningListTest
{
	private static int nextId = 1;

	private static DeliveryPlanning.DeliveryPlanningBuilder planning()
	{
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(OrgId.ofRepoId(1000000))
				.type(TransportDirection.Outgoing);
	}

	private static DeliveryPlanning withShipper(@Nullable final Integer shipperRepoId)
	{
		return planning()
				.shipperId(shipperRepoId != null ? ShipperId.ofRepoId(shipperRepoId) : null)
				.build();
	}

	private static DeliveryPlanning withIncoterms(@Nullable final Integer incotermsRepoId, @Nullable final String incotermLocation)
	{
		return planning()
				.incotermsId(incotermsRepoId != null ? IncotermsId.ofRepoId(incotermsRepoId) : null)
				.incotermLocation(incotermLocation)
				.build();
	}

	@Nested
	@DisplayName("admissibilityMismatches")
	class AdmissibilityMismatches
	{
		@Test
		@DisplayName("all rows shipperless reads as 'all the same', not as a mismatch")
		void allRowsShipperless()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withShipper(null),
					withShipper(null),
					withShipper(null));

			assertThat(list.admissibilityMismatches()).isEmpty();
		}

		@Test
		@DisplayName("all rows incoterm-less (and incoterm-location-less) reads as 'all the same'")
		void allRowsIncotermless()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withIncoterms(null, null),
					withIncoterms(null, null));

			assertThat(list.admissibilityMismatches()).isEmpty();
		}

		@Test
		@DisplayName("a single-row selection is trivially admissible, even with every optional field missing")
		void singleRowSelection()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(planning().build());

			assertThat(list.size()).isEqualTo(1);
			assertThat(list.admissibilityMismatches()).isEmpty();
		}

		@Test
		@DisplayName("an empty selection has nothing to disagree about")
		void emptySelection()
		{
			assertThat(DeliveryPlanningList.EMPTY.admissibilityMismatches()).isEmpty();
		}

		@Test
		@DisplayName("NULL is a value: a shipperless row beside a row with a shipper IS a mismatch")
		void someRowsShipperless()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withShipper(540001),
					withShipper(null));

			assertThat(list.admissibilityMismatches()).containsExactly(AggregationKeyField.Forwarder);
		}

		@Test
		@DisplayName("NULL is a value: a missing incoterm location beside a filled one IS a mismatch")
		void someRowsWithoutIncotermLocation()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withIncoterms(540002, "Hamburg"),
					withIncoterms(540002, null));

			assertThat(list.admissibilityMismatches()).containsExactly(AggregationKeyField.IncotermLocation);
		}

		@Test
		@DisplayName("every differing field is reported at once - never one field at a time")
		void everyDifferingFieldIsReportedAtOnce()
		{
			final DeliveryPlanning row1 = DeliveryPlanning.builder()
					.id(DeliveryPlanningId.ofRepoId(nextId++))
					.orgId(OrgId.ofRepoId(1000000))
					.type(TransportDirection.Outgoing)
					.shipperId(ShipperId.ofRepoId(540001))
					.incotermsId(IncotermsId.ofRepoId(540002))
					.incotermLocation("Hamburg")
					.meansOfTransportationId(MeansOfTransportationId.ofRepoId(540003))
					.loadingLocationId(BPartnerLocationId.ofRepoId(540004, 540005))
					.deliveryLocationId(BPartnerLocationId.ofRepoId(540006, 540007))
					.build();

			final DeliveryPlanning row2 = DeliveryPlanning.builder()
					.id(DeliveryPlanningId.ofRepoId(nextId++))
					.orgId(OrgId.ofRepoId(1000001))
					.type(TransportDirection.Incoming)
					.shipperId(ShipperId.ofRepoId(540011))
					.incotermsId(IncotermsId.ofRepoId(540012))
					.incotermLocation("Rotterdam")
					.meansOfTransportationId(MeansOfTransportationId.ofRepoId(540013))
					.loadingLocationId(BPartnerLocationId.ofRepoId(540014, 540015))
					.deliveryLocationId(BPartnerLocationId.ofRepoId(540016, 540017))
					.build();

			assertThat(DeliveryPlanningList.of(row1, row2).admissibilityMismatches())
					.containsExactlyInAnyOrder(AggregationKeyField.values());
		}
	}

	@Nested
	@DisplayName("allocation order")
	class AllocationOrder
	{
		private DeliveryPlanning withEtd(final int idRepoId, @Nullable final String etd)
		{
			return DeliveryPlanning.builder()
					.id(DeliveryPlanningId.ofRepoId(idRepoId))
					.orgId(OrgId.ofRepoId(1000000))
					.type(TransportDirection.Outgoing)
					.etd(etd != null ? Instant.parse(etd) : null)
					.build();
		}

		@Test
		@DisplayName("earliest ETD first - NOT the order the plannings were handed over in")
		void earliestEtdFirst()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withEtd(101, "2026-03-03T00:00:00Z"),
					withEtd(102, "2026-03-01T00:00:00Z"),
					withEtd(103, "2026-03-02T00:00:00Z"));

			assertThat(list.getIdsInAllocationOrder()).extracting(DeliveryPlanningId::getRepoId)
					.containsExactly(102, 103, 101);
		}

		@Test
		@DisplayName("the planning id breaks a tie on equal ETD")
		void planningIdBreaksTheTie()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withEtd(203, "2026-03-01T00:00:00Z"),
					withEtd(201, "2026-03-01T00:00:00Z"),
					withEtd(202, "2026-03-01T00:00:00Z"));

			assertThat(list.getIdsInAllocationOrder()).extracting(DeliveryPlanningId::getRepoId)
					.containsExactly(201, 202, 203);
		}

		@Test
		@DisplayName("a planning without an ETD sorts last, and still deterministically among its kind")
		void withoutEtdSortsLast()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withEtd(302, null),
					withEtd(303, "2026-03-05T00:00:00Z"),
					withEtd(301, null));

			assertThat(list.getIdsInAllocationOrder()).extracting(DeliveryPlanningId::getRepoId)
					.containsExactly(303, 301, 302);
		}

		@Test
		@DisplayName("iteration sees the same order the ids do, so anything derived from the list agrees with the LineNo")
		void iterationAgreesWithTheIds()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withEtd(401, "2026-03-09T00:00:00Z"),
					withEtd(402, "2026-03-08T00:00:00Z"));

			assertThat(list.stream().map(DeliveryPlanning::getId))
					.containsExactlyElementsOf(list.getIdsInAllocationOrder());
		}
	}

	@Nested
	@DisplayName("union")
	class Union
	{
		@Test
		@DisplayName("a planning in both lists is carried ONCE, so it is never a mismatch with itself")
		void aPlanningInBothIsCarriedOnce()
		{
			final DeliveryPlanning onTheTarget = withShipper(540001);
			final DeliveryPlanningList target = DeliveryPlanningList.of(onTheTarget, withShipper(540001));

			// exactly what add-to hands over for a planning the target already holds
			final DeliveryPlanningList union = target.union(DeliveryPlanningList.of(onTheTarget));

			assertThat(union.size()).isEqualTo(2);
			assertThat(union.getIdsInAllocationOrder()).doesNotHaveDuplicates();
			assertThat(union.admissibilityMismatches()).isEmpty();
		}

		@Test
		@DisplayName("a field the two lists disagree on is a mismatch of the union, even when each list agrees with itself")
		void disagreementBetweenTheTwoListsIsAMismatch()
		{
			final DeliveryPlanningList target = DeliveryPlanningList.of(withShipper(540001), withShipper(540001));

			final DeliveryPlanningList union = target.union(DeliveryPlanningList.of(withShipper(540002)));

			assertThat(target.admissibilityMismatches()).as("each list on its own is admissible").isEmpty();
			assertThat(union.admissibilityMismatches()).containsExactly(AggregationKeyField.Forwarder);
		}

		@Test
		@DisplayName("with an empty list on either side the other one is returned unchanged")
		void unionWithEmpty()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(withShipper(540001));

			assertThat(list.union(DeliveryPlanningList.EMPTY)).isEqualTo(list);
			assertThat(DeliveryPlanningList.EMPTY.union(list)).isEqualTo(list);
		}
	}

	@Nested
	@DisplayName("admissibility field labels")
	class AggregationKeyFieldLabels
	{
		@Test
		@DisplayName("every field has its own label, so no two fields collapse into one word in the rejection message")
		void everyFieldHasItsOwnLabel()
		{
			assertThat(Arrays.stream(AggregationKeyField.values()).map(AggregationKeyField::getLabel))
					.doesNotHaveDuplicates()
					.hasSize(AggregationKeyField.values().length);
		}
	}

	@Nested
	@DisplayName("closed and allocated")
	class ClosedAndAllocated
	{
		@Test
		@DisplayName("no row closed")
		void noneClosed()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(planning().build(), planning().build());

			assertThat(list.anyClosed()).isFalse();
			assertThat(list.closedOnes().isEmpty()).isTrue();
		}

		@Test
		@DisplayName("closedOnes names WHICH rows are closed, not just how many")
		void someClosed()
		{
			final DeliveryPlanning open = planning().build();
			final DeliveryPlanning closed = planning().closed(true).build();

			final DeliveryPlanningList list = DeliveryPlanningList.of(open, closed);

			assertThat(list.anyClosed()).isTrue();
			assertThat(list.closedOnes()).containsExactly(closed);
		}

		@Test
		@DisplayName("a mixed selection is both anyClosed and anyOpen - the two are not each other's negation")
		void mixedIsBothClosedAndOpen()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(planning().build(), planning().closed(true).build());

			assertThat(list.anyClosed()).isTrue();
			assertThat(list.anyOpen()).isTrue();
		}

		@Test
		@DisplayName("an all-closed selection has no open row")
		void noneOpen()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(planning().closed(true).build(), planning().closed(true).build());

			assertThat(list.anyOpen()).isFalse();
		}

		@Test
		@DisplayName("an empty selection has no open row either - so a process refusing 'nothing open' also refuses it")
		void noneOpenWhenEmpty()
		{
			assertThat(DeliveryPlanningList.EMPTY.anyOpen()).isFalse();
		}

		@Test
		@DisplayName("a row without a delivery instruction is not allocated")
		void noneAllocated()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(planning().build(), planning().build());

			assertThat(list.anyAllocated()).isFalse();
			assertThat(list.allocatedOnes().isEmpty()).isTrue();
		}

		@Test
		@DisplayName("allocatedOnes names WHICH rows already sit on a delivery instruction")
		void someAllocated()
		{
			final DeliveryPlanning unallocated = planning().build();
			final DeliveryPlanning allocated = planning()
					.deliveryInstructionId(ShipperTransportationId.ofRepoId(540021))
					.build();

			final DeliveryPlanningList list = DeliveryPlanningList.of(unallocated, allocated);

			assertThat(list.anyAllocated()).isTrue();
			assertThat(list.allocatedOnes()).containsExactly(allocated);
		}

		@Test
		@DisplayName("withoutShipper names the rows that have no forwarder - the check admissibility cannot make")
		void withoutShipper()
		{
			final DeliveryPlanning withShipper = withShipper(540001);
			final DeliveryPlanning shipperless = withShipper(null);

			final DeliveryPlanningList list = DeliveryPlanningList.of(withShipper, shipperless);

			assertThat(list.withoutShipper()).containsExactly(shipperless);
		}
	}
}
