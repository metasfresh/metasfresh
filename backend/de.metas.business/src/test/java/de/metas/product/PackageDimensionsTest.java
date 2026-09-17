/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.product;

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import de.metas.uom.impl.UOMTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link PackageDimensions#ofItems(PackageDimensionCalcMethod, java.util.List)}.
 *
 * <p>Tests all three calculation modes (Strapping, Repacking, Nesting) and the
 * {@link PackageDimensions#UNSPECIFIED} propagation contract (any item with no dimensions,
 * or an empty item list, yields {@code UNSPECIFIED}).</p>
 */
public class PackageDimensionsTest
{
	private I_C_UOM each;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		each = new UOMTestHelper().createUOM("Each", 0);
	}

	private Quantity qty(final int n)
	{
		return Quantity.of(n, each);
	}

	// -----------------------------------------------------------------------
	// STRAPPING
	// -----------------------------------------------------------------------

	@Nested
	class Strapping
	{
		/**
		 * Single product with dims (L=6, W=4, H=2) × qty 3.
		 * Sorted ascending: [2, 4, 6].
		 * Stacking axis (min edge) = 2 × 3 = 6.
		 * Other two edges = max(4) = 4, max(6) = 6.
		 * <p>
		 * Must match {@link PackageDimensions#ofProductDimensionsAndQty} for the same input.
		 */
		@Test
		void singleProduct_parityWithExistingMethod()
		{
			final PackageDimensions dims = PackageDimensions.builder()
					.lengthInCM(6).widthInCM(4).heightInCM(2)
					.build();

			final PackageDimensions expected = PackageDimensions.ofProductDimensionsAndQty(dims, qty(3));

			final List<PackageDimensionItem> items = ImmutableList.of(
					PackageDimensionItem.of(dims, qty(3))
			);
			final PackageDimensions result = PackageDimensions.ofItems(PackageDimensionCalcMethod.Strapping, items);

			assertThat(result).isEqualTo(expected);
		}

		/**
		 * Two products:
		 * A: dims (L=6, W=4, H=2) sorted → [2,4,6], qty 3  → stacking contribution = 2*3 = 6
		 * B: dims (L=7, W=5, H=3) sorted → [3,5,7], qty 2  → stacking contribution = 3*2 = 6
		 * <p>
		 * Stacking axis total = 6 + 6 = 12.
		 * Larger edge 1 = max(4, 5) = 5.
		 * Larger edge 2 = max(6, 7) = 7.
		 * <p>
		 * Per {@link PackageDimensions#ofProductDimensionsAndQty} convention the stacking result
		 * is stored in {@code lengthInCM}, mid-edge in {@code heightInCM}, max-edge in {@code widthInCM}.
		 * We assert the numeric values only (not field mapping) to allow a width/height swap.
		 */
		@Test
		void mixedTwoProducts()
		{
			final PackageDimensions dimsA = PackageDimensions.builder()
					.lengthInCM(6).widthInCM(4).heightInCM(2)
					.build();
			final PackageDimensions dimsB = PackageDimensions.builder()
					.lengthInCM(7).widthInCM(5).heightInCM(3)
					.build();

			final List<PackageDimensionItem> items = ImmutableList.of(
					PackageDimensionItem.of(dimsA, qty(3)),
					PackageDimensionItem.of(dimsB, qty(2))
			);
			final PackageDimensions result = PackageDimensions.ofItems(PackageDimensionCalcMethod.Strapping, items);

			// The three edge values must be {5, 7, 12} regardless of which field holds which
			assertThat(ImmutableList.of(result.getLengthInCM(), result.getWidthInCM(), result.getHeightInCM()))
					.containsExactlyInAnyOrder(12, 7, 5);
		}
	}

	// -----------------------------------------------------------------------
	// REPACKING
	// -----------------------------------------------------------------------

	@Nested
	class Repacking
	{
		/**
		 * Two products:
		 * A: (L=6, W=4, H=2) × qty 3 → volume contribution = 6*4*2*3 = 144
		 * B: (L=7, W=5, H=3) × qty 2 → volume contribution = 7*5*3*2 = 210
		 * Total volume V = (144 + 210) * 1.05 = 354 * 1.05 = 371.7
		 * <p>
		 * Shape formula (all rounded via {@link Math#round}):
		 *   height = round(⅔ * 371.7^(1/3)) = round(⅔ * 7.191) = round(4.794) = 5
		 *   width  = round(⅗ * √(371.7 / 5)) = round(⅗ * √74.34) = round(⅗ * 8.622) = round(5.173) = 5
		 *   length = round((371.7 / 5) / 5) = round(74.34 / 5) = round(14.868) = 15
		 * <p>
		 * Self-consistency: 5 * 5 * 15 = 375; V = 371.7 → ratio 1.009 — within rounding.
		 * We assert the volume product is within 10% of V to accommodate integer rounding.
		 */
		@Test
		void mixedTwoProducts_volumeFormula()
		{
			final PackageDimensions dimsA = PackageDimensions.builder()
					.lengthInCM(6).widthInCM(4).heightInCM(2)
					.build();
			final PackageDimensions dimsB = PackageDimensions.builder()
					.lengthInCM(7).widthInCM(5).heightInCM(3)
					.build();

			final List<PackageDimensionItem> items = ImmutableList.of(
					PackageDimensionItem.of(dimsA, qty(3)),
					PackageDimensionItem.of(dimsB, qty(2))
			);
			final PackageDimensions result = PackageDimensions.ofItems(PackageDimensionCalcMethod.Repacking, items);

			assertThat(result.isUnspecified()).isFalse();
			assertThat(result.getLengthInCM()).isGreaterThan(0);
			assertThat(result.getWidthInCM()).isGreaterThan(0);
			assertThat(result.getHeightInCM()).isGreaterThan(0);

			// Volume self-consistency: h*w*l should be within 10% of V
			final double expectedVolume = (6.0 * 4 * 2 * 3 + 7.0 * 5 * 3 * 2) * 1.05; // 371.7
			final double actualVolume = (double)result.getLengthInCM()
					* result.getWidthInCM()
					* result.getHeightInCM();
			assertThat(actualVolume).isBetween(expectedVolume * 0.90, expectedVolume * 1.10);
		}
	}

	// -----------------------------------------------------------------------
	// NESTING
	// -----------------------------------------------------------------------

	@Nested
	class Nesting
	{
		/**
		 * A: dims (L=6, W=4, H=2) — max single edge = 6
		 * B: dims (L=5, W=3, H=20) — max single edge = 20  ← winner
		 * <p>
		 * Result must equal B's dimensions exactly.
		 */
		@Test
		void itemWithLargestSingleEdgeWins()
		{
			final PackageDimensions dimsA = PackageDimensions.builder()
					.lengthInCM(6).widthInCM(4).heightInCM(2)
					.build();
			final PackageDimensions dimsB = PackageDimensions.builder()
					.lengthInCM(5).widthInCM(3).heightInCM(20)
					.build();

			final List<PackageDimensionItem> items = ImmutableList.of(
					PackageDimensionItem.of(dimsA, qty(1)),
					PackageDimensionItem.of(dimsB, qty(1))
			);
			final PackageDimensions result = PackageDimensions.ofItems(PackageDimensionCalcMethod.Nesting, items);

			assertThat(result).isEqualTo(dimsB);
		}
	}

	// -----------------------------------------------------------------------
	// UNSPECIFIED propagation
	// -----------------------------------------------------------------------

	@Nested
	class UnspecifiedPropagation
	{
		/**
		 * If any item carries UNSPECIFIED dimensions, the result must be UNSPECIFIED
		 * regardless of calc mode.
		 */
		@Test
		void anyItemUnspecified_returnsUnspecified()
		{
			final PackageDimensions dims = PackageDimensions.builder()
					.lengthInCM(6).widthInCM(4).heightInCM(2)
					.build();

			final List<PackageDimensionItem> items = ImmutableList.of(
					PackageDimensionItem.of(dims, qty(2)),
					PackageDimensionItem.of(PackageDimensions.UNSPECIFIED, qty(1))
			);

			for (final PackageDimensionCalcMethod mode : PackageDimensionCalcMethod.values())
			{
				final PackageDimensions result = PackageDimensions.ofItems(mode, items);
				assertThat(result)
						.as("mode %s should return UNSPECIFIED when any item has no dims", mode)
						.isEqualTo(PackageDimensions.UNSPECIFIED);
			}
		}

		/**
		 * An empty item list has no dimensions to compute from; result is UNSPECIFIED
		 * for all modes.
		 */
		@Test
		void emptyList_returnsUnspecified()
		{
			final List<PackageDimensionItem> items = ImmutableList.of();

			for (final PackageDimensionCalcMethod mode : PackageDimensionCalcMethod.values())
			{
				final PackageDimensions result = PackageDimensions.ofItems(mode, items);
				assertThat(result)
						.as("mode %s should return UNSPECIFIED for an empty item list", mode)
						.isEqualTo(PackageDimensions.UNSPECIFIED);
			}
		}
	}
}
