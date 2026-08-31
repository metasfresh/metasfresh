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

import static de.metas.deliveryplanning.DeliveryPlanningAllocTestHelper.allocatedTo;
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
	@DisplayName("aggregationKeyViolations")
	class AggregationKeyViolations
	{
		@Test
		@DisplayName("all rows shipperless reads as 'all the same', not as a mismatch")
		void allRowsShipperless()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withShipper(null),
					withShipper(null),
					withShipper(null));

			assertThat(list.aggregationKeyViolations()).isEmpty();
		}

		@Test
		@DisplayName("all rows incoterm-less (and incoterm-location-less) reads as 'all the same'")
		void allRowsIncotermless()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withIncoterms(null, null),
					withIncoterms(null, null));

			assertThat(list.aggregationKeyViolations()).isEmpty();
		}

		@Test
		@DisplayName("a single-row selection is trivially admissible, even with every optional field missing")
		void singleRowSelection()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(planning().build());

			assertThat(list.size()).isEqualTo(1);
			assertThat(list.aggregationKeyViolations()).isEmpty();
		}

		@Test
		@DisplayName("an empty selection has nothing to disagree about")
		void emptySelection()
		{
			assertThat(DeliveryPlanningList.EMPTY.aggregationKeyViolations()).isEmpty();
		}

		@Test
		@DisplayName("NULL is a value: a shipperless row beside a row with a shipper IS a mismatch")
		void someRowsShipperless()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withShipper(540001),
					withShipper(null));

			assertThat(list.aggregationKeyViolations()).containsExactly(AggregationKeyField.Forwarder);
		}

		@Test
		@DisplayName("NULL is a value: a missing incoterm location beside a filled one IS a mismatch")
		void someRowsWithoutIncotermLocation()
		{
			final DeliveryPlanningList list = DeliveryPlanningList.of(
					withIncoterms(540002, "Hamburg"),
					withIncoterms(540002, null));

			assertThat(list.aggregationKeyViolations()).containsExactly(AggregationKeyField.IncotermLocation);
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

			assertThat(DeliveryPlanningList.of(row1, row2).aggregationKeyViolations())
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
			assertThat(union.aggregationKeyViolations()).isEmpty();
		}

		@Test
		@DisplayName("a field the two lists disagree on is a mismatch of the union, even when each list agrees with itself")
		void disagreementBetweenTheTwoListsIsAMismatch()
		{
			final DeliveryPlanningList target = DeliveryPlanningList.of(withShipper(540001), withShipper(540001));

			final DeliveryPlanningList union = target.union(DeliveryPlanningList.of(withShipper(540002)));

			assertThat(target.aggregationKeyViolations()).as("each list on its own is admissible").isEmpty();
			assertThat(union.aggregationKeyViolations()).containsExactly(AggregationKeyField.Forwarder);
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
					.allocations(allocatedTo(ShipperTransportationId.ofRepoId(540021)))
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

	@Nested
	@DisplayName("allocations")
	class Allocations
	{
		// instance fields, not static: a Java 8 inner class may only hold static fields that are compile-time
		// constants, which an id value object is not
		private final ShipperTransportationId firstLeg = ShipperTransportationId.ofRepoId(540021);
		private final ShipperTransportationId secondLeg = ShipperTransportationId.ofRepoId(540022);

		@Test
		@DisplayName("a planning on no instruction has no allocations at all - never null")
		void unallocated()
		{
			final DeliveryPlanning unallocated = planning().build();

			assertThat(unallocated.getAllocations()).isEmpty();
			assertThat(unallocated.isAllocated()).isFalse();
			assertThat(unallocated.getAllocationCount()).isZero();
			assertThat(unallocated.getDeliveryInstructionIds()).isEmpty();
		}

		@Test
		@DisplayName("one allocation: one instruction, counted once")
		void oneAllocation()
		{
			final DeliveryPlanning allocated = planning().allocations(allocatedTo(firstLeg)).build();

			assertThat(allocated.isAllocated()).isTrue();
			assertThat(allocated.getAllocationCount()).isEqualTo(1);
			assertThat(allocated.getDeliveryInstructionIds()).containsExactly(firstLeg);
		}

		/**
		 * The case the DB still forbids and the whole list shape exists for: multi-leg transport, one planning on
		 * one instruction per leg. Pinned NOW, so the semantics are a decision rather than something discovered
		 * when the partial unique index is dropped.
		 */
		@Test
		@DisplayName("two allocations to DIFFERENT instructions: both are counted, both are named")
		void twoAllocationsToDifferentInstructions()
		{
			final DeliveryPlanning multiLeg = planning().allocations(allocatedTo(firstLeg, secondLeg)).build();

			assertThat(multiLeg.isAllocated()).isTrue();
			assertThat(multiLeg.getAllocationCount()).isEqualTo(2);
			assertThat(multiLeg.getDeliveryInstructionIds()).containsExactly(firstLeg, secondLeg);
		}

		@Test
		@DisplayName("two allocations to the SAME instruction are two allocations but ONE instruction")
		void twoAllocationsToTheSameInstruction()
		{
			final DeliveryPlanning twiceOnOneLeg = planning().allocations(allocatedTo(firstLeg, firstLeg)).build();

			assertThat(twiceOnOneLeg.getAllocationCount())
					.as("the count answers how many allocations, which is what a caller asking about rows needs")
					.isEqualTo(2);
			assertThat(twiceOnOneLeg.getDeliveryInstructionIds())
					.as("the instructions are DISTINCT - a caller asking 'is it on THIS document' asks about documents")
					.containsExactly(firstLeg);
		}

		@Test
		@DisplayName("a multi-leg planning counts as allocated for the list-level checks, exactly like a single-leg one")
		void multiLegCountsAsAllocated()
		{
			final DeliveryPlanning multiLeg = planning().allocations(allocatedTo(firstLeg, secondLeg)).build();

			final DeliveryPlanningList list = DeliveryPlanningList.of(planning().build(), multiLeg);

			assertThat(list.anyAllocated()).isTrue();
			assertThat(list.allocatedOnes()).containsExactly(multiLeg);
		}
	}
}
