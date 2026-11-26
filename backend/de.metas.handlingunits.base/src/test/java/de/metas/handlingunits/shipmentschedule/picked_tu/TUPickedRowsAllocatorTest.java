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
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.quantity.Quantity;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pins how the content of a TU (CUs per attribute combination) is handed out to the picked rows. The CUs are real HUs, so that
 * their weights are the ones the production code reads.
 */
class TUPickedRowsAllocatorTest extends TUPicksTestBase
{
	@BeforeEach
	void enableCatchWeight()
	{
		enableCatchWeightOfSalad();
	}

	private Quantity qty(final int qty)
	{
		return Quantity.of(BigDecimal.valueOf(qty), uomEach);
	}

	private Quantity kg(final String weight)
	{
		return Quantity.of(new BigDecimal(weight), uomKg);
	}

	/**
	 * @param weightNet the net weight of the CU in kg; {@code null} for a CU without weight
	 */
	private TUContentCU cu(final int qty, @Nullable final String weightNet)
	{
		final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI().setLocatorId(defaultLocatorId);
		helper.load(producer, helper.pSaladProductId, BigDecimal.valueOf(qty), uomEach);
		final I_M_HU cu = producer.getCreatedHUs().get(0);
		if (weightNet != null)
		{
			setWeightNet(cu, weightNet);
		}
		return new TUContentCU(cu, qty(qty));
	}

	private TUContentCU cu(final int qty)
	{
		return cu(qty, null);
	}

	private TUContentGroup group(final TUContentCU... cus)
	{
		return new TUContentGroup(ImmutableList.copyOf(cus));
	}

	private TUPickedRowsAllocator allocator(final TUContentGroup... groups)
	{
		return new TUPickedRowsAllocator(TUContentGroupList.of(ImmutableList.copyOf(groups)), helper.getHUContext(), helper.pSaladProductId);
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
			final List<TUPickedRowsAllocator.Part> parts = allocator(group(cu)).allocate(row(2, null));

			assertThat(parts).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isSameAs(cu));
		}

		@Test
		void groupOfOneCU_partIsLessThanTheCU_isNoCU()
		{
			final List<TUPickedRowsAllocator.Part> parts = allocator(group(cu(5))).allocate(row(2, null));

			assertThat(parts).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isNull());
		}

		@Test
		void groupOfSeveralCUs_partEqualsTheFirstCU_isNoCU()
		{
			// two CUs of 2 and 3 of one lot: a part of 2 equals the first CU's qty, but the CU cannot be told from another one
			final TUPickedRowsAllocator allocator = allocator(group(cu(2), cu(3)));

			assertThat(allocator.allocate(row(2, null))).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isNull());
			assertThat(allocator(group(cu(2), cu(3))).allocate(row(5, null))).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isNull());
		}

		@Test
		void groupOfOneCU_afterAPartWasHandedOut_isNoCU()
		{
			final TUPickedRowsAllocator allocator = allocator(group(cu(4)));
			allocator.allocate(row(2, null));

			assertThat(allocator.allocate(row(2, null))).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isNull());
			assertThat(allocator(group(cu(2), cu(3))).allocate(row(5, null))).singleElement().satisfies(part -> assertThat(part.getWholeCU()).isNull());
		}

		@Test
		void afterAPartWasHandedOut_nextPartTakesWhatIsLeftOfTheGroup()
		{
			final TUPickedRowsAllocator allocator = allocator(group(cu(4)), group(cu(1)));
			allocator.allocate(row(2, null));

			assertThat(allocator.allocate(row(3, null))).extracting(part -> part.getQty().toBigDecimal())
					.as("2 left of the first group, then the second group")
					.containsExactly(new BigDecimal("2"), new BigDecimal("1"));
		}

		@Test
		void cuKeptInAnotherUOM_groupCountsInStockUOM()
		{
			// the rows' QtyPicked is in the stock UOM; a storage kept in another UOM must not be compared in that UOM
			final I_C_UOM storageUOM = uomKg; // any UOM other than the stock UOM
			final IHUProductStorage storage = mock(IHUProductStorage.class);
			final I_M_HU cu = mock(I_M_HU.class);
			when(storage.getM_HU()).thenReturn(cu);
			when(storage.getQty()).thenReturn(Quantity.of(BigDecimal.ONE, storageUOM));
			when(storage.getQtyInStockingUOM()).thenReturn(qty(10));
			final TUContentGroup group = new TUContentGroup(ImmutableList.of(TUContentCU.ofStorage(storage)));

			assertThat(group.stockingUOM()).isSameAs(uomEach);
			assertThat(group.totalQty().toBigDecimal()).isEqualByComparingTo("10");
			assertThat(allocator(group).allocate(row(10, null))).singleElement().satisfies(part -> assertThat(part.getWholeCU().getCu()).isSameAs(cu));
		}
	}

	@Nested
	class CatchWeight
	{
		@Test
		void partCoveringCUsInPart_weighsThemProRata()
		{
			// two CUs of one lot, 2 pieces / 4.0 kg and 4 pieces / 6.0 kg
			final TUContentCU first = cu(2, "4.0");
			final TUContentCU second = cu(4, "6.0");
			final TUPickedRowsAllocator allocator = allocator(group(first, second));

			assertThat(allocator.allocate(row(4, "0.0")).get(0).getCatchWeight().toBigDecimal()).as("first CU + half of the second").isEqualByComparingTo("7.0");
			assertThat(allocator.allocate(row(2, "0.0")).get(0).getCatchWeight().toBigDecimal()).as("rest of the second").isEqualByComparingTo("3.0");
		}

		@Test
		void cuWithoutWeight_partTakesItsShareOfTheRowsWeightByQty()
		{
			final TUContentGroup lotA = group(cu(2));
			final TUContentGroup lotB = group(cu(2));
			final List<TUPickedRowsAllocator.Part> parts = allocator(lotA, lotB).allocate(row(4, "4.001"));

			assertThat(parts).extracting(part -> part.getCatchWeight().toBigDecimal())
					.usingElementComparator(BigDecimal::compareTo)
					.as("shares by quantity, the last part gets the remainder")
					.containsExactly(new BigDecimal("2.001"), new BigDecimal("2.000"));
		}

		@Test
		void cuWithoutWeight_rowOfOnePart_keepsItsOwnWeight()
		{
			final List<TUPickedRowsAllocator.Part> parts = allocator(group(cu(2))).allocate(row(2, "2.0"));

			assertThat(parts).singleElement().satisfies(part -> assertThat(part.getCatchWeight()).as("empty: the row's weight stays").isNull());
		}

		@Test
		void rowWithoutCatchWeight_partsHaveNone()
		{
			final TUContentCU cu = cu(2, "3.0");
			final List<TUPickedRowsAllocator.Part> parts = allocator(group(cu)).allocate(row(2, null));

			assertThat(parts).singleElement().satisfies(part -> assertThat(part.getCatchWeight()).isNull());
		}

		@Test
		void cuCoveredInPart_weightRoundsUpInTheUOMsRoundingMode()
		{
			// one CU of 3 pieces / 0.001 kg, handed out piece by piece: the first piece takes the indivisible gram, the others none
			final TUContentCU cu = cu(3, "0.001");
			final TUPickedRowsAllocator allocator = allocator(group(cu));
			final TUPickedRowsAllocator.Row rowOfOnePiece = row(1, "0");

			assertThat(ImmutableList.of(allocator.allocate(rowOfOnePiece), allocator.allocate(rowOfOnePiece), allocator.allocate(rowOfOnePiece)))
					.extracting(parts -> parts.get(0).getCatchWeight().toBigDecimal())
					.usingElementComparator(BigDecimal::compareTo)
					.containsExactly(new BigDecimal("0.001"), BigDecimal.ZERO, BigDecimal.ZERO);
		}

		@Test
		void splitByQty_weightBelowThePrecisionOfTheShares_noNegativeShareAndSumIsTheWeight()
		{
			// 0.003 kg over 5 pieces: 0.0006 kg each is below the gram; rounding each share alone gives 0.001 x 4 and -0.001
			final List<Quantity> shares = TUPickedRowsAllocator.splitByQty(kg("0.003"), ImmutableList.of(qty(1), qty(1), qty(1), qty(1), qty(1)));

			assertThat(shares).extracting(share -> share.toBigDecimal())
					.usingElementComparator(BigDecimal::compareTo)
					.as("rounded up cumulatively: never negative, never more than the weight")
					.containsExactly(new BigDecimal("0.001"), new BigDecimal("0.001"), BigDecimal.ZERO, new BigDecimal("0.001"), BigDecimal.ZERO);
		}

		@Test
		void splitByQty_weightWithMoreDecimalsThanItsUOM_isNotRoundedPastItself()
		{
			// 0.0035 kg (more decimals than the gram precision) over 99 + 1 pieces: 0.003465 kg rounds up to 0.004 kg, more than the weight
			final List<Quantity> shares = TUPickedRowsAllocator.splitByQty(kg("0.0035"), ImmutableList.of(qty(99), qty(1)));

			assertThat(shares).extracting(share -> share.toBigDecimal())
					.usingElementComparator(BigDecimal::compareTo)
					.containsExactly(new BigDecimal("0.0035"), BigDecimal.ZERO);
		}

		@Test
		void cuWithoutWeight_weightBelowThePrecisionOfTheParts_noPartGetsANegativeWeight()
		{
			// 5 lots of one piece each, no CU weights: the row's 0.003 kg is split by quantity
			final List<TUPickedRowsAllocator.Part> parts = allocator(group(cu(1)), group(cu(1)), group(cu(1)), group(cu(1)), group(cu(1)))
					.allocate(row(5, "0.003"));

			assertThat(parts).extracting(part -> part.getCatchWeight().toBigDecimal()).allSatisfy(weight -> assertThat(weight).isNotNegative());
			assertThat(parts.stream().map(part -> part.getCatchWeight().toBigDecimal()).reduce(BigDecimal.ZERO, BigDecimal::add)).isEqualByComparingTo("0.003");
		}

		@Test
		void interleavedLots_rowLandingOnTheOtherLotsGroup_carriesTheWeightOfThatGroupsCUs()
		{
			// picked in this order: lot A, lot B, lot A (2 pieces each); the TU lists its CUs grouped by lot: A (2+2), B (2)
			final TUContentCU a1 = cu(2, "2.1");
			final TUContentCU a2 = cu(2, "1.9");
			final TUContentCU b1 = cu(2, "2.0");
			final TUPickedRowsAllocator allocator = allocator(group(a1, a2), group(b1));

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
		assertThat(allocator(group(cu(2))).allocate(row(0, null))).isEmpty();
	}
}
