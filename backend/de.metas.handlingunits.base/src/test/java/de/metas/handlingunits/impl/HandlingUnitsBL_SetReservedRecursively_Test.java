package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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

/**
 * Tests for {@link HandlingUnitsBL#setReservedRecursively(I_M_HU, boolean)} method
 * focusing on nested HU structures (LU-TU-CU hierarchies).
 */
@ExtendWith(AdempiereTestWatcher.class)
public class HandlingUnitsBL_SetReservedRecursively_Test
{
	private IHandlingUnitsBL handlingUnitsBL;
	private IHandlingUnitsDAO handlingUnitsDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	}

	@Nested
	class SetReservedRecursively
	{
		/**
		 * Test reserving a simple single HU with no children
		 */
		@Test
		void singleHU_setToReserved()
		{
			// given
			final I_M_HU_Item huItem = createHU(null);
			final I_M_HU hu = huItem.getM_HU();
			assertThat(hu.isReserved()).isFalse();

			// when
			final boolean result = handlingUnitsBL.setReservedRecursively(hu, true);

			// then
			assertThat(result).isTrue();
			final I_M_HU updatedHU = InterfaceWrapperHelper.load(hu.getM_HU_ID(), I_M_HU.class);
			assertThat(updatedHU.isReserved()).isTrue();
		}

		/**
		 * Test unreserving a single HU
		 */
		@Test
		void singleHU_setToUnreserved()
		{
			// given
			final I_M_HU_Item huItem = createHU(null);
			final I_M_HU hu = huItem.getM_HU();
			hu.setIsReserved(true);
			saveRecord(hu);

			// when
			handlingUnitsBL.setReservedRecursively(hu, false);

			// then
			final I_M_HU updatedHU = InterfaceWrapperHelper.load(hu.getM_HU_ID(), I_M_HU.class);
			assertThat(updatedHU.isReserved()).isFalse();
		}

		/**
		 * Test reserving a 2-level hierarchy: TU -> CU
		 */
		@Test
		void twoLevelHierarchy_TU_CU_setToReserved()
		{
			// given: TU with one CU child
			final I_M_HU_Item tu = createHU(null);
			final I_M_HU_Item cu = createHU(tu);

			assertThat(tu.getM_HU().isReserved()).isFalse();
			assertThat(cu.getM_HU().isReserved()).isFalse();

			// when: reserve TU (should cascade to CU)
			handlingUnitsBL.setReservedRecursively(tu.getM_HU(), true);

			// then: both TU and CU should be reserved
			final I_M_HU updatedTU = InterfaceWrapperHelper.load(tu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU = InterfaceWrapperHelper.load(cu.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedTU.isReserved()).isTrue();
			assertThat(updatedCU.isReserved()).isTrue();
		}

		/**
		 * Test reserving a complete 3-level hierarchy: LU -> TU -> CU
		 */
		@Test
		void threeLevelHierarchy_LU_TU_CU_setToReserved()
		{
			// given: LU -> TU -> CU hierarchy
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu = createHU(lu);
			final I_M_HU_Item cu = createHU(tu);

			assertThat(lu.getM_HU().isReserved()).isFalse();
			assertThat(tu.getM_HU().isReserved()).isFalse();
			assertThat(cu.getM_HU().isReserved()).isFalse();

			// when: reserve LU (should cascade to TU and CU)
			handlingUnitsBL.setReservedRecursively(lu.getM_HU(), true);

			// then: all three levels should be reserved
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU = InterfaceWrapperHelper.load(tu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU = InterfaceWrapperHelper.load(cu.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.isReserved()).isTrue();
			assertThat(updatedTU.isReserved()).isTrue();
			assertThat(updatedCU.isReserved()).isTrue();
		}

		/**
		 * Test unreserving a complete 3-level hierarchy
		 */
		@Test
		void threeLevelHierarchy_LU_TU_CU_setToUnreserved()
		{
			// given: all HUs in hierarchy are reserved
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu = createHU(lu);
			final I_M_HU_Item cu = createHU(tu);

			lu.getM_HU().setIsReserved(true);
			tu.getM_HU().setIsReserved(true);
			cu.getM_HU().setIsReserved(true);
			saveRecord(lu.getM_HU());
			saveRecord(tu.getM_HU());
			saveRecord(cu.getM_HU());

			// when: unreserve LU
			handlingUnitsBL.setReservedRecursively(lu.getM_HU(), false);

			// then: all should be unreserved
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU = InterfaceWrapperHelper.load(tu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU = InterfaceWrapperHelper.load(cu.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.isReserved()).isFalse();
			assertThat(updatedTU.isReserved()).isFalse();
			assertThat(updatedCU.isReserved()).isFalse();
		}

		/**
		 * Test reserving middle level (TU) in a 3-level hierarchy
		 * Should only affect TU and its children (CU), not parent (LU)
		 */
		@Test
		void threeLevelHierarchy_reserveMiddleLevel_TU()
		{
			// given: LU -> TU -> CU hierarchy
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu = createHU(lu);
			final I_M_HU_Item cu = createHU(tu);

			// when: reserve only TU (middle level)
			handlingUnitsBL.setReservedRecursively(tu.getM_HU(), true);

			// then: TU and CU should be reserved, but not LU
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU = InterfaceWrapperHelper.load(tu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU = InterfaceWrapperHelper.load(cu.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.isReserved()).isFalse();
			assertThat(updatedTU.isReserved()).isTrue();
			assertThat(updatedCU.isReserved()).isTrue();
		}

		/**
		 * Test reserving an LU with multiple TUs
		 */
		@Test
		void multipleChildren_LU_withMultipleTUs()
		{
			// given: LU with 3 TU children
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu1 = createHU(lu);
			final I_M_HU_Item tu2 = createHU(lu);
			final I_M_HU_Item tu3 = createHU(lu);

			// when: reserve LU
			handlingUnitsBL.setReservedRecursively(lu.getM_HU(), true);

			// then: LU and all TUs should be reserved
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU1 = InterfaceWrapperHelper.load(tu1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU2 = InterfaceWrapperHelper.load(tu2.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU3 = InterfaceWrapperHelper.load(tu3.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.isReserved()).isTrue();
			assertThat(updatedTU1.isReserved()).isTrue();
			assertThat(updatedTU2.isReserved()).isTrue();
			assertThat(updatedTU3.isReserved()).isTrue();
		}

		/**
		 * Test reserving a complex nested structure: LU -> multiple TUs -> multiple CUs each
		 */
		@Test
		void complexNestedStructure_LU_multipleTUs_multipleCUs()
		{
			// given: LU with 2 TUs, each TU has 2 CUs
			final I_M_HU_Item lu = createHU(null);

			final I_M_HU_Item tu1 = createHU(lu);
			final I_M_HU_Item cu1_1 = createHU(tu1);
			final I_M_HU_Item cu1_2 = createHU(tu1);

			final I_M_HU_Item tu2 = createHU(lu);
			final I_M_HU_Item cu2_1 = createHU(tu2);
			final I_M_HU_Item cu2_2 = createHU(tu2);

			// when: reserve LU
			handlingUnitsBL.setReservedRecursively(lu.getM_HU(), true);

			// then: all HUs in the hierarchy should be reserved
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU1 = InterfaceWrapperHelper.load(tu1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU1_1 = InterfaceWrapperHelper.load(cu1_1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU1_2 = InterfaceWrapperHelper.load(cu1_2.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU2 = InterfaceWrapperHelper.load(tu2.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU2_1 = InterfaceWrapperHelper.load(cu2_1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU2_2 = InterfaceWrapperHelper.load(cu2_2.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.isReserved()).isTrue();
			assertThat(updatedTU1.isReserved()).isTrue();
			assertThat(updatedCU1_1.isReserved()).isTrue();
			assertThat(updatedCU1_2.isReserved()).isTrue();
			assertThat(updatedTU2.isReserved()).isTrue();
			assertThat(updatedCU2_1.isReserved()).isTrue();
			assertThat(updatedCU2_2.isReserved()).isTrue();
		}

		/**
		 * Test deep hierarchy (4+ levels)
		 */
		@Test
		void deepHierarchy_fourLevels()
		{
			// given: 4-level hierarchy
			final I_M_HU_Item level1 = createHU(null);
			final I_M_HU_Item level2 = createHU(level1);
			final I_M_HU_Item level3 = createHU(level2);
			final I_M_HU_Item level4 = createHU(level3);

			// when: reserve top level
			handlingUnitsBL.setReservedRecursively(level1.getM_HU(), true);

			// then: all levels should be reserved
			final I_M_HU updated1 = InterfaceWrapperHelper.load(level1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updated2 = InterfaceWrapperHelper.load(level2.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updated3 = InterfaceWrapperHelper.load(level3.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updated4 = InterfaceWrapperHelper.load(level4.getM_HU_ID(), I_M_HU.class);

			assertThat(updated1.isReserved()).isTrue();
			assertThat(updated2.isReserved()).isTrue();
			assertThat(updated3.isReserved()).isTrue();
			assertThat(updated4.isReserved()).isTrue();
		}

		/**
		 * Test using the DAO method retrieveIncludedHUs to verify hierarchy
		 */
		@Test
		void verifyRecursion_usingDAO()
		{
			// given: LU -> 2 TUs -> 2 CUs each
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu1 = createHU(lu);
			final I_M_HU_Item cu1_1 = createHU(tu1);
			final I_M_HU_Item cu1_2 = createHU(tu1);
			final I_M_HU_Item tu2 = createHU(lu);
			final I_M_HU_Item cu2_1 = createHU(tu2);
			final I_M_HU_Item cu2_2 = createHU(tu2);

			// when: reserve LU
			handlingUnitsBL.setReservedRecursively(lu.getM_HU(), true);

			// then: verify using DAO
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu.getM_HU());
			assertThat(includedHUs).hasSize(2); // 2 TUs

			for (final I_M_HU tu : includedHUs)
			{
				assertThat(tu.isReserved()).isTrue();

				final List<I_M_HU> includedCUs = handlingUnitsDAO.retrieveIncludedHUs(tu);
				assertThat(includedCUs).hasSize(2); // 2 CUs per TU
				assertThat(includedCUs).allMatch(I_M_HU::isReserved);
			}
		}

		/**
		 * Test that attempting to reserve an already reserved HU returns false
		 */
		@Test
		void alreadyReserved_shouldReturnFalse()
		{
			// given: a reserved HU
			final I_M_HU_Item huItem = createHU(null);
			final I_M_HU hu = huItem.getM_HU();
			hu.setIsReserved(true);
			saveRecord(hu);

			// when: attempting to reserve again
			final boolean result = handlingUnitsBL.setReservedRecursively(hu, true);

			// then: should return false
			assertThat(result).isFalse();
		}

		/**
		 * Test that attempting to reserve parent returns false if child is already reserved
		 */
		@Test
		void childAlreadyReserved_shouldReturnFalse()
		{
			// given: TU -> CU hierarchy where CU is already reserved
			final I_M_HU_Item tu = createHU(null);
			final I_M_HU_Item cu = createHU(tu);
			cu.getM_HU().setIsReserved(true);
			saveRecord(cu.getM_HU());

			// when: attempting to reserve TU
			final boolean result = handlingUnitsBL.setReservedRecursively(tu.getM_HU(), true);

			// then: should return false because CU is already reserved
			assertThat(result).isFalse();
		}

		/**
		 * Test that validation fails early in deep hierarchy
		 */
		@Test
		void deepHierarchy_validationFailsEarly()
		{
			// given: LU -> TU -> CU hierarchy where CU is reserved
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu = createHU(lu);
			final I_M_HU_Item cu = createHU(tu);
			cu.getM_HU().setIsReserved(true);
			saveRecord(cu.getM_HU());

			// when: attempting to reserve LU
			final boolean result = handlingUnitsBL.setReservedRecursively(lu.getM_HU(), true);

			// then: should return false
			assertThat(result).isFalse();

			// verify: LU and TU remain unreserved (validation failed before any changes)
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU = InterfaceWrapperHelper.load(tu.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.isReserved()).isFalse();
			assertThat(updatedTU.isReserved()).isFalse();
		}

		/**
		 * Test that validation checks all branches in multi-child hierarchy
		 */
		@Test
		void multipleChildren_oneReserved_shouldFailValidation()
		{
			// given: LU with 3 TUs, second TU is already reserved
			final I_M_HU_Item lu = createHU(null);
			final I_M_HU_Item tu1 = createHU(lu);
			final I_M_HU_Item tu2 = createHU(lu);
			final I_M_HU_Item tu3 = createHU(lu);

			tu2.getM_HU().setIsReserved(true);
			saveRecord(tu2.getM_HU());

			// when: attempting to reserve LU
			final boolean result = handlingUnitsBL.setReservedRecursively(lu.getM_HU(), true);

			// then: should return false
			assertThat(result).isFalse();

			// verify: no changes were made
			final I_M_HU updatedLU = InterfaceWrapperHelper.load(lu.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU1 = InterfaceWrapperHelper.load(tu1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU3 = InterfaceWrapperHelper.load(tu3.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedLU.isReserved()).isFalse();
			assertThat(updatedTU1.isReserved()).isFalse();
			assertThat(updatedTU3.isReserved()).isFalse();
		}

		/**
		 * Test that unreserving does NOT validate (can unreserve already unreserved HUs)
		 */
		@Test
		void unreserve_doesNotValidate()
		{
			// given: unreserved HU
			final I_M_HU_Item huItem = createHU(null);
			final I_M_HU hu = huItem.getM_HU();
			assertThat(hu.isReserved()).isFalse();

			// when: unreserve (should not throw)
			assertThatCode(() -> handlingUnitsBL.setReservedRecursively(hu, false))
					.doesNotThrowAnyException();

			// then: still unreserved
			final I_M_HU updatedHU = InterfaceWrapperHelper.load(hu.getM_HU_ID(), I_M_HU.class);
			assertThat(updatedHU.isReserved()).isFalse();
		}
	}

	@Nested
	class SetReservedByHUIds
	{
		/**
		 * Test bulk reservation using setReservedByHUIds with empty set
		 */
		@Test
		void emptySet_shouldDoNothing()
		{
			// given
			final Set<HuId> emptySet = new HashSet<>();

			// when/then: should not throw exception
			assertThatCode(() -> handlingUnitsBL.setReservedByHUIds(emptySet, true))
					.doesNotThrowAnyException();
		}

		/**
		 * Test bulk reservation of multiple independent HUs
		 */
		@Test
		void multipleIndependentHUs()
		{
			// given: 3 independent HUs
			final I_M_HU_Item hu1 = createHU(null);
			final I_M_HU_Item hu2 = createHU(null);
			final I_M_HU_Item hu3 = createHU(null);

			final Set<HuId> huIds = ImmutableSet.of(
					HuId.ofRepoId(hu1.getM_HU_ID()),
					HuId.ofRepoId(hu2.getM_HU_ID()),
					HuId.ofRepoId(hu3.getM_HU_ID())
			);

			// when: reserve all using setReservedByHUIds
			handlingUnitsBL.setReservedByHUIds(huIds, true);

			// then: all should be reserved
			final I_M_HU updated1 = InterfaceWrapperHelper.load(hu1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updated2 = InterfaceWrapperHelper.load(hu2.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updated3 = InterfaceWrapperHelper.load(hu3.getM_HU_ID(), I_M_HU.class);

			assertThat(updated1.isReserved()).isTrue();
			assertThat(updated2.isReserved()).isTrue();
			assertThat(updated3.isReserved()).isTrue();
		}

		/**
		 * Test bulk reservation with nested structures
		 */
		@Test
		void multipleHUsWithChildren()
		{
			// given: 2 TUs, each with 1 CU
			final I_M_HU_Item tu1 = createHU(null);
			final I_M_HU_Item cu1 = createHU(tu1);

			final I_M_HU_Item tu2 = createHU(null);
			final I_M_HU_Item cu2 = createHU(tu2);

			final Set<HuId> tuIds = ImmutableSet.of(
					HuId.ofRepoId(tu1.getM_HU_ID()),
					HuId.ofRepoId(tu2.getM_HU_ID())
			);

			// when: reserve TUs using setReservedByHUIds
			handlingUnitsBL.setReservedByHUIds(tuIds, true);

			// then: both TUs and their CUs should be reserved
			final I_M_HU updatedTU1 = InterfaceWrapperHelper.load(tu1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU1 = InterfaceWrapperHelper.load(cu1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU2 = InterfaceWrapperHelper.load(tu2.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU2 = InterfaceWrapperHelper.load(cu2.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedTU1.isReserved()).isTrue();
			assertThat(updatedCU1.isReserved()).isTrue();
			assertThat(updatedTU2.isReserved()).isTrue();
			assertThat(updatedCU2.isReserved()).isTrue();
		}

		/**
		 * Test unreserving multiple HUs
		 */
		@Test
		void unreserveMultipleHUs()
		{
			// given: 2 reserved HUs with children
			final I_M_HU_Item tu1 = createHU(null);
			final I_M_HU_Item cu1 = createHU(tu1);
			tu1.getM_HU().setIsReserved(true);
			cu1.getM_HU().setIsReserved(true);
			saveRecord(tu1.getM_HU());
			saveRecord(cu1.getM_HU());

			final I_M_HU_Item tu2 = createHU(null);
			final I_M_HU_Item cu2 = createHU(tu2);
			tu2.getM_HU().setIsReserved(true);
			cu2.getM_HU().setIsReserved(true);
			saveRecord(tu2.getM_HU());
			saveRecord(cu2.getM_HU());

			final Set<HuId> tuIds = ImmutableSet.of(
					HuId.ofRepoId(tu1.getM_HU_ID()),
					HuId.ofRepoId(tu2.getM_HU_ID())
			);

			// when: unreserve using setReservedByHUIds
			handlingUnitsBL.setReservedByHUIds(tuIds, false);

			// then: all should be unreserved
			final I_M_HU updatedTU1 = InterfaceWrapperHelper.load(tu1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU1 = InterfaceWrapperHelper.load(cu1.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedTU2 = InterfaceWrapperHelper.load(tu2.getM_HU_ID(), I_M_HU.class);
			final I_M_HU updatedCU2 = InterfaceWrapperHelper.load(cu2.getM_HU_ID(), I_M_HU.class);

			assertThat(updatedTU1.isReserved()).isFalse();
			assertThat(updatedCU1.isReserved()).isFalse();
			assertThat(updatedTU2.isReserved()).isFalse();
			assertThat(updatedCU2.isReserved()).isFalse();
		}
	}

	/**
	 * Helper method to create a simple HU structure for testing.
	 * Returns an I_M_HU_Item which contains the created HU.
	 *
	 * @param parent optional parent HU item
	 * @return the created HU item containing the new HU
	 */
	private static I_M_HU_Item createHU(@Nullable final I_M_HU_Item parent)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		if (parent != null)
		{
			hu.setM_HU_Item_Parent(parent);
		}
		saveRecord(hu);

		final I_M_HU_Item item = newInstance(I_M_HU_Item.class);
		item.setM_HU(hu);
		saveRecord(item);

		return item;
	}
}
