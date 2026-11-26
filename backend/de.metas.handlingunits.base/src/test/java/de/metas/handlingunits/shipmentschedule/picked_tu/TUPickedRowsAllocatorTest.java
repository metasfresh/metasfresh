package de.metas.handlingunits.shipmentschedule.picked_tu;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.quantity.Quantity;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pins how the content of a TU (CUs per attribute combination) is handed out to the picked rows. No HU harness needed: pure arithmetic.
 */
class TUPickedRowsAllocatorTest
{
	private final I_C_UOM each = uom(1, 0);
	private final I_C_UOM kg = uom(2, 1);

	private static I_C_UOM uom(final int id, final int precision)
	{
		final I_C_UOM uom = mock(I_C_UOM.class);
		when(uom.getC_UOM_ID()).thenReturn(id);
		when(uom.getStdPrecision()).thenReturn(precision);
		return uom;
	}

	private Quantity qty(final int qty)
	{
		return Quantity.of(BigDecimal.valueOf(qty), each);
	}

	private Quantity kg(final String weight)
	{
		return Quantity.of(new BigDecimal(weight), kg);
	}

	private TUContentCU cu(final int qty)
	{
		return new TUContentCU(mock(I_M_HU.class), qty(qty));
	}

	private TUContentGroup group(final TUContentCU... cus)
	{
		return new TUContentGroup(ImmutableList.copyOf(cus));
	}

	private TUPickedRowsAllocator allocator(final Map<TUContentCU, String> weightsByCU, final TUContentGroup... groups)
	{
		final Function<TUContentCU, Quantity> weightOfCU = cu -> weightsByCU.containsKey(cu) ? kg(weightsByCU.get(cu)) : null;
		return new TUPickedRowsAllocator(ImmutableList.copyOf(groups), weightOfCU);
	}

	private TUPickedRowsAllocator.Row row(final int qty, @Nullable final String weight)
	{
		return new TUPickedRowsAllocator.Row(qty(qty), weight == null ? null : kg(weight));
	}

	@Nested
	class WholeCU
	{
		@Test
		void groupOfOneCU_partIsAllOfIt_isThatCU()
		{
			final TUContentCU cu = cu(2);
			final List<TUPickedRowsAllocator.Part> parts = allocator(ImmutableMap.of(), group(cu)).allocate(row(2, null));

			assertThat(parts).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isSameAs(cu));
		}

		@Test
		void groupOfOneCU_partIsLessThanTheCU_isNoCU()
		{
			final List<TUPickedRowsAllocator.Part> parts = allocator(ImmutableMap.of(), group(cu(5))).allocate(row(2, null));

			assertThat(parts).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isNull());
		}

		@Test
		void groupOfSeveralCUs_partEqualsTheFirstCU_isNoCU()
		{
			// two CUs of 2 and 3 of one lot: a part of 2 equals the first CU's qty, but the CU cannot be told from another one
			final TUPickedRowsAllocator allocator = allocator(ImmutableMap.of(), group(cu(2), cu(3)));

			assertThat(allocator.allocate(row(2, null))).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isNull());
		}

		@Test
		void groupOfOneCU_afterAPartWasHandedOut_isNoCU()
		{
			final TUPickedRowsAllocator allocator = allocator(ImmutableMap.of(), group(cu(4)));
			allocator.allocate(row(2, null));

			assertThat(allocator.allocate(row(2, null))).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isNull());
		}
	}

	@Nested
	class CatchWeight
	{
		@Test
		void partCoveringCUsInPart_weighsThemProRata()
		{
			// two CUs of one lot, 2 pieces / 4.0 kg and 4 pieces / 6.0 kg
			final TUContentCU first = cu(2);
			final TUContentCU second = cu(4);
			final TUPickedRowsAllocator allocator = allocator(ImmutableMap.of(first, "4.0", second, "6.0"), group(first, second));

			assertThat(allocator.allocate(row(4, "0.0")).get(0).getCatchWeight().toBigDecimal()).as("first CU + half of the second").isEqualByComparingTo("7.0");
			assertThat(allocator.allocate(row(2, "0.0")).get(0).getCatchWeight().toBigDecimal()).as("rest of the second").isEqualByComparingTo("3.0");
		}

		@Test
		void cuWithoutWeight_partTakesItsShareOfTheRowsWeightByQty()
		{
			final TUContentGroup lotA = group(cu(2));
			final TUContentGroup lotB = group(cu(2));
			final List<TUPickedRowsAllocator.Part> parts = allocator(ImmutableMap.of(), lotA, lotB).allocate(row(4, "4.1"));

			assertThat(parts).extracting(part -> part.getCatchWeight().toBigDecimal())
					.as("shares by quantity, the last part gets the remainder")
					.containsExactly(new BigDecimal("2.1"), new BigDecimal("2.0"));
		}

		@Test
		void rowWithoutCatchWeight_partsHaveNone()
		{
			final TUContentCU cu = cu(2);
			final List<TUPickedRowsAllocator.Part> parts = allocator(ImmutableMap.of(cu, "3.0"), group(cu)).allocate(row(2, null));

			assertThat(parts).singleElement().satisfies(part -> assertThat(part.getCatchWeight()).isNull());
		}

		@Test
		void interleavedLots_rowLandingOnTheOtherLotsGroup_carriesTheWeightOfThatGroupsCUs()
		{
			// picked in this order: lot A, lot B, lot A (2 pieces each); the TU lists its CUs grouped by lot: A (2+2), B (2)
			final TUContentCU a1 = cu(2);
			final TUContentCU a2 = cu(2);
			final TUContentCU b1 = cu(2);
			final TUPickedRowsAllocator allocator = allocator(ImmutableMap.of(a1, "2.1", a2, "1.9", b1, "2.0"), group(a1, a2), group(b1));

			final List<TUPickedRowsAllocator.Part> rowB = allocator.allocate(row(2, "2.0"));
			final List<TUPickedRowsAllocator.Part> rowA2 = allocator.allocate(row(2, "1.9"));
			final List<TUPickedRowsAllocator.Part> rowA3 = allocator.allocate(row(2, "9.9"));

			assertThat(rowB).singleElement().satisfies(part -> assertThat(part.getCatchWeight().toBigDecimal()).isEqualByComparingTo("2.1"));
			assertThat(rowA2).singleElement().satisfies(part -> assertThat(part.getCatchWeight().toBigDecimal()).isEqualByComparingTo("1.9"));
			assertThat(rowA3).singleElement().satisfies(part -> assertThat(part.getCatchWeight().toBigDecimal()).isEqualByComparingTo("2.0"));
		}
	}

	@Test
	void rowWithoutQty_hasNoParts()
	{
		assertThat(allocator(ImmutableMap.of(), group(cu(2))).allocate(row(0, null))).isEmpty();
	}
}
