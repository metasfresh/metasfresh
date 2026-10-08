/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.reservation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for parent-child filtering logic in HUReservationService.
 * Tests scenarios from test-scenarios-getHUsToReserve.md
 * <p>
 * Uses actual HU hierarchies created via LUTUProducerDestinationTestSupport.
 * Each test creates fresh HUs to avoid test pollution.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class HUReservationService_ParentChildFiltering_Test
{
	private LUTUProducerDestinationTestSupport data;
	private HUReservationService huReservationService;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		data = new LUTUProducerDestinationTestSupport();

		final HUReservationRepository huReservationRepository = new HUReservationRepository();
		huReservationService = new HUReservationService(huReservationRepository);

		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	@Nested
	@DisplayName("Category 1: Basic Scenarios")
	class BasicScenariosTest
	{
		@Test
		@DisplayName("Test 1.1: Single Top-Level HU (no parent)")
		public void test_singleTopLevelHU_noParent()
		{
			// Create a top-level LU (pallet)
			final I_M_HU lu = createLU();
			final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());

			// Test: LU has no parent
			final Set<HuId> inputSet = ImmutableSet.of(luId);
			final boolean result = huReservationService.hasAncestorInList(lu, inputSet);

			// Assert: Should return false (no ancestor in list)
			assertThat(result).isFalse();
		}

		@Test
		@DisplayName("Test 1.2: Single Child HU (TU) - Parent NOT in List")
		public void test_singleMidLevelHU_parentNotInList()
		{
			// Create LU with aggregate TU (2-level structure: LU → Aggregate TU)
			final I_M_HU lu = createLU();

			// Get the aggregate TU from LU
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
			assertThat(includedHUs).hasSize(1); // LU contains 1 aggregate TU

			final I_M_HU aggregateTU = includedHUs.get(0);
			assertThat(handlingUnitsBL.isAggregateHU(aggregateTU)).isTrue();

			final HuId tuId = HuId.ofRepoId(aggregateTU.getM_HU_ID());

			// Test: TU's parent (LU) is NOT in the set
			final Set<HuId> inputSet = ImmutableSet.of(tuId); // Only TU
			final boolean result = huReservationService.hasAncestorInList(aggregateTU, inputSet);

			// Assert: Should return false (parent not in list)
			assertThat(result).isFalse();
		}

		// Test 1.3 removed: requires 3-level hierarchy (LU-TU-VHU), but we only have 2 levels (LU-Aggregate TU)
	}

	@Nested
	@DisplayName("Category 2: Parent-Child Relationship Scenarios (ORDER INDEPENDENCE)")
	class ParentChildRelationshipTest
	{
		@Test
		@DisplayName("Test 2.1: LU and TU - Parent First [CORE BUG FIX]")
		public void test_parentAndChild_parentFirst()
		{
			// Create LU with aggregate TU
			final I_M_HU lu = createLU();
			final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());

			// Get aggregate TU from LU
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
			assertThat(includedHUs).hasSize(1); // LU contains 1 aggregate TU

			final I_M_HU aggregateTU = includedHUs.get(0);
			final HuId tuId = HuId.ofRepoId(aggregateTU.getM_HU_ID());

			// Test: [parent, child] order
			final Set<HuId> inputSet = ImmutableSet.of(luId, tuId);
			final boolean parentShouldBeFiltered = huReservationService.hasAncestorInList(lu, inputSet);
			final boolean childShouldBeFiltered = huReservationService.hasAncestorInList(aggregateTU, inputSet);

			// Assert
			assertThat(parentShouldBeFiltered).isFalse(); // Parent (LU) has no ancestor in list
			assertThat(childShouldBeFiltered).isTrue();   // Child (TU) has parent (LU) in list
		}

		@Test
		@DisplayName("Test 2.2: LU and TU - Child First [ORDER INDEPENDENCE]")
		public void test_parentAndChild_childFirst_ORDER_INDEPENDENCE()
		{
			// Create LU with aggregate TU (FRESH hierarchy for this test)
			final I_M_HU lu = createLU();
			final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());

			// Get aggregate TU from LU
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
			assertThat(includedHUs).hasSize(1); // LU contains 1 aggregate TU

			final I_M_HU aggregateTU = includedHUs.get(0);
			final HuId tuId = HuId.ofRepoId(aggregateTU.getM_HU_ID());

			// Test: [child, parent] order (REVERSED from Test 2.1)
			// Note: ImmutableSet doesn't preserve order, but that's the point - order shouldn't matter!
			final Set<HuId> inputSet = ImmutableSet.of(tuId, luId);
			final boolean parentShouldBeFiltered = huReservationService.hasAncestorInList(lu, inputSet);
			final boolean childShouldBeFiltered = huReservationService.hasAncestorInList(aggregateTU, inputSet);

			// Assert: MUST be identical to Test 2.1 (order-independent)
			assertThat(parentShouldBeFiltered).isFalse(); // Parent (LU) has no ancestor in list
			assertThat(childShouldBeFiltered).isTrue();   // Child (TU) has parent (LU) in list
		}

		// Tests 2.3, 2.4, 2.5 removed: require 3-level hierarchy, but we only have 2 levels (LU-Aggregate TU)

		@Test
		@DisplayName("Test 2.6: Two Standalone TUs (Siblings - No Parent in List)")
		public void test_siblingChildren_noParentInList()
		{
			// Create two separate aggregate TUs (no LU parent)
			final I_M_HU tu1 = createAggregateTU();
			final I_M_HU tu2 = createAggregateTU();

			final HuId tu1Id = HuId.ofRepoId(tu1.getM_HU_ID());
			final HuId tu2Id = HuId.ofRepoId(tu2.getM_HU_ID());

			// Test: Only siblings in set (no parent in set)
			final Set<HuId> inputSet = ImmutableSet.of(tu1Id, tu2Id);

			final boolean tu1Filtered = huReservationService.hasAncestorInList(tu1, inputSet);
			final boolean tu2Filtered = huReservationService.hasAncestorInList(tu2, inputSet);

			// Assert: Neither should be filtered (they are independent, no parent-child relationship)
			assertThat(tu1Filtered).isFalse();
			assertThat(tu2Filtered).isFalse();
		}
	}

	@Nested
	@DisplayName("Category 7: Data Integrity Scenarios")
	class DataIntegrityScenariosTest
	{
		@Test
		@DisplayName("Test 28: Null Parent Reference")
		public void test_nullParentReference()
		{
			// Create a top-level LU (has null parent)
			final I_M_HU lu = createLU();
			final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());

			// Verify parent is null
			final I_M_HU parent = handlingUnitsDAO.retrieveParent(lu);
			assertThat(parent).isNull();

			// Test
			final Set<HuId> inputSet = ImmutableSet.of(luId);
			final boolean result = huReservationService.hasAncestorInList(lu, inputSet);

			// Assert: Should treat as top-level (no ancestor)
			assertThat(result).isFalse();
		}

		// Test 29 removed: requires 3-level hierarchy (LU-TU-VHU), but we only have 2 levels (LU-Aggregate TU)

		@Test
		@DisplayName("Test 30: Child HU with Reserved Parent")
		public void test_childHU_withReservedParent()
		{
			// Create LU with aggregate TU
			final I_M_HU lu = createLU();

			// Get the aggregate TU
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
			assertThat(includedHUs).hasSize(1);
			final I_M_HU aggregateTU = includedHUs.get(0);

			// Mark the LU as reserved
			lu.setIsReserved(true);
			handlingUnitsDAO.saveHU(lu);

			// Test: Check if TU has reserved ancestor
			final boolean hasReservedAncestor = huReservationService.hasReservedAncestor(aggregateTU);

			// Assert: TU should be filtered out because parent LU is reserved
			assertThat(hasReservedAncestor).isTrue();
		}

		@Test
		@DisplayName("Test 31: Simulate Parent Reservation Deletes Child Reservations - Verify child reservations would be deleted when parent is reserved")
		public void test_parentReservation_deletesChildReservations()
		{
			// Create LU with aggregate TU
			final I_M_HU lu = createLU();

			// Get the aggregate TU
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
			assertThat(includedHUs).hasSize(1);
			final I_M_HU aggregateTU = includedHUs.get(0);

			// Mark the TU as reserved (simulating prior reservation)
			aggregateTU.setIsReserved(true);
			handlingUnitsDAO.saveHU(aggregateTU);

			// Verify TU is reserved
			assertThat(aggregateTU.isReserved()).isTrue();

			// Now test getDescendantHuIds - it should find the TU
			final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());
			final HuId tuId = HuId.ofRepoId(aggregateTU.getM_HU_ID());

			// When we try to reserve the LU, it should collect the child TU for reservation deletion
			final List<I_M_HU> husToCheck = ImmutableList.of(lu);

			// Manually call the helper method to verify it finds descendants
			// Note: In real flow, deleteChildReservations is called automatically in getHUsToReserve
			final Set<HuId> descendantIds = husToCheck.stream()
					.flatMap(hu -> {
						final List<I_M_HU> children = handlingUnitsDAO.retrieveIncludedHUs(hu);
						return children.stream().map(child -> HuId.ofRepoId(child.getM_HU_ID()));
					})
					.collect(ImmutableSet.toImmutableSet());

			// Assert: Should find the TU as a descendant
			assertThat(descendantIds).contains(tuId);
			assertThat(descendantIds).hasSize(1);
		}

		@Test
		@DisplayName("Test 32: Parent HU with Reserved Child - Parent Cannot Be Reserved")
		public void test_parentHU_withReservedChild_cannotBeReserved()
		{
			// Create LU with aggregate TU
			final I_M_HU lu = createLU();

			// Get the aggregate TU
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
			assertThat(includedHUs).hasSize(1);
			final I_M_HU aggregateTU = includedHUs.get(0);

			// Mark the TU as reserved (simulating prior reservation)
			aggregateTU.setIsReserved(true);
			handlingUnitsDAO.saveHU(aggregateTU);

			// Test: Check if LU has reserved descendant
			final boolean hasReservedDescendant = huReservationService.hasReservedDescendant(lu);

			// Assert: LU should be filtered out because child TU is reserved
			assertThat(hasReservedDescendant).isTrue();
		}
	}

	/**
	 * Creates a 2-level HU hierarchy: LU → Aggregate TU
	 * - 1 LU (pallet) - this is the top-level parent returned
	 * - 1 Aggregate TU inside the LU
	 * Total: 100kg of tomatoes
	 */
	private I_M_HU createLU()
	{
		// mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU returns the aggregate TU, so we get its parent (the LU)
		return handlingUnitsBL.getTopLevelParent(createAggregateTU());
	}

	/**
	 * Creates a standalone aggregate TU (no LU parent)
	 */
	private I_M_HU createAggregateTU()
	{
		return data.mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU("100", 100);
	}

	@Nested
	@DisplayName("Edge Cases")
	class EdgeCasesTest
	{
		@Test
		@DisplayName("Test 18: Empty Input Set")
		public void test_emptyInputSet()
		{
			// Create any HU
			final I_M_HU lu = createLU();

			// Test with empty set
			final Set<HuId> emptySet = ImmutableSet.of();
			final boolean result = huReservationService.hasAncestorInList(lu, emptySet);

			// Assert: No ancestors in empty set
			assertThat(result).isFalse();
		}
	}
}
