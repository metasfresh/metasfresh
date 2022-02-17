package de.metas.quantity;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.uom.UomId;
import de.metas.uom.impl.UOMTestHelper;
import de.metas.util.JSONObjectMapper;
import de.metas.util.lang.Percent;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.assertj.core.data.Offset;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuantityTest
{
	private UOMTestHelper uomHelper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final IContextAware contextProvider = PlainContextAware.newOutOfTrx(Env.getCtx());
		this.uomHelper = new UOMTestHelper(contextProvider.getCtx());
	}

	@Test
	public void test_weightedAverage()
	{
		final int stdPrecision = 2;
		final BigDecimal comparationError = new BigDecimal("0.01");

		//
		// UOM
		final I_C_UOM uom = uomHelper.createUOM("UOM", stdPrecision);

		//
		// Random number generator
		// final Random random = new Random(System.currentTimeMillis());

		//
		//
		BigDecimal currentQtySum = new BigDecimal(0);
		Quantity quantity = new Quantity(BigDecimal.ZERO, uom);

		for (int count = 1; count <= 100; count++)
		{
			// System.out.println("Iteration " + count + " ---------------------------------------------------------");

			// Generate next Qty value (randomly)
			final BigDecimal currentQtyValue = new BigDecimal(count);
			// new BigDecimal(random.nextDouble() * 10000000).setScale(stdPrecision, RoundingMode.HALF_UP);

			// System.out.println("currentQtyValue=" + currentQtyValue);

			//
			// Calculate current average quantity => our expectation
			currentQtySum = currentQtySum.add(currentQtyValue);
			// System.out.println("currentQtySum=" + currentQtySum);
			final BigDecimal currentQtyAvg = currentQtySum.divide(BigDecimal.valueOf(count), stdPrecision, RoundingMode.HALF_UP);
			// System.out.println("Expected quantity avg=" + currentQtyAvg);

			//
			// Calculate average quantity by using "weightedAverage" method
			final int previousAverageWeight = count - 1;
			quantity = new Quantity(currentQtyValue, uom)
					.weightedAverage(quantity.toBigDecimal(), previousAverageWeight);
			// System.out.println("Actual quantity avg=" + quantity.getQty());

			//
			// Assume their are equal
			assertThat(quantity.toBigDecimal())
					.overridingErrorMessage("Invalid Quantity.weightedAverage result (count=" + count + ")")
					.isCloseTo(currentQtyAvg, Offset.offset(comparationError));
		}
	}

	@Test
	public void test_switchToSource()
	{
		final BigDecimal qty = new BigDecimal("1234");
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);

		final BigDecimal sourceQty = new BigDecimal("1235");
		final I_C_UOM sourceUOM = uomHelper.createUOM("UOM2", 2);

		final Quantity quantity = new Quantity(qty, uom, sourceQty, sourceUOM);
		Assertions.assertSame(qty, quantity.toBigDecimal(), "Invalid Qty");
		Assertions.assertSame(uom, quantity.getUOM(), "Invalid UOM");
		Assertions.assertSame(sourceQty, quantity.getSourceQty(), "Invalid Source Qty");
		Assertions.assertSame(sourceUOM, quantity.getSourceUOM(), "Invalid Source UOM");

		final Quantity quantitySource = quantity.switchToSource();
		new QuantityExpectation()
				.sameQty(sourceQty)
				.uom(sourceUOM)
				.sameSourceQty(qty)
				.sourceUOM(uom)
				.assertExpected(quantitySource);
	}

	@Test
	public void test_negate()
	{
		final BigDecimal qty = new BigDecimal("1234");
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);

		final BigDecimal sourceQty = new BigDecimal("1235");
		final I_C_UOM sourceUOM = uomHelper.createUOM("UOM2", 2);

		final Quantity quantity = new Quantity(qty, uom, sourceQty, sourceUOM);
		new QuantityExpectation()
				.sameQty(qty)
				.uom(uom)
				.sameSourceQty(sourceQty)
				.sourceUOM(sourceUOM)
				.assertExpected("Initial quantity", quantity);

		final Quantity quantityNegated = quantity.negate();
		new QuantityExpectation()
				.qty(qty.negate())
				.uom(uom)
				.sourceQty(sourceQty.negate())
				.sourceUOM(sourceUOM)
				.assertExpected("Negated quantity", quantityNegated);
	}

	@Test
	public void test_negate_zero()
	{
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);
		final Quantity qty = Quantity.zero(uom);
		assertThat(qty.negate()).isSameAs(qty);
	}

	@Test
	public void test_zero_static_factory()
	{
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);
		final Quantity qty = Quantity.zero(uom);
		assertThat(qty.isZero()).isTrue();
	}

	@Test
	public void test_infinite_static_factory()
	{
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);
		final Quantity qty = Quantity.infinite(uom);
		assertThat(qty.isInfinite()).isTrue();
	}

	/**
	 * Adding a ZERO quantity shall return the original {@link Quantity} object no matter what UOMs are there
	 */
	@Test
	public void test_add_ZeroQty_TotallyDifferentUOMs()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);
		final I_C_UOM qty_sourceUom = uomHelper.createUOM("qty_sourceUom", 2);
		final I_C_UOM qtyToAdd_uom = uomHelper.createUOM("qtyToAdd_uom", 2);
		final I_C_UOM qtyToAdd_sourceUom = uomHelper.createUOM("qtyToAdd_sourceUom", 2);

		final Quantity qty = new Quantity(new BigDecimal("123"), qty_uom, new BigDecimal("456"), qty_sourceUom);
		final Quantity qtyToAdd = new Quantity(new BigDecimal("0"), qtyToAdd_uom, new BigDecimal("0"), qtyToAdd_sourceUom);
		final Quantity qtyNew = qty.add(qtyToAdd);
		Assertions.assertSame(qty, qtyNew, "Invalid QtyNew");
	}

	@Test
	public void test_add_UomMatching_SourceUomNotMatching()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);
		final I_C_UOM qty_sourceUom = uomHelper.createUOM("qty_sourceUom", 2);
		//noinspection UnnecessaryLocalVariable
		final I_C_UOM qtyToAdd_uom = qty_uom;
		final I_C_UOM qtyToAdd_sourceUom = uomHelper.createUOM("qtyToAdd_sourceUom", 2);

		final Quantity qty = new Quantity(new BigDecimal("123"), qty_uom, new BigDecimal("456"), qty_sourceUom);
		final Quantity qtyToAdd = new Quantity(new BigDecimal("100"), qtyToAdd_uom, new BigDecimal("200"), qtyToAdd_sourceUom);
		final Quantity qtyNew = qty.add(qtyToAdd);

		new QuantityExpectation()
				.qty("223") // =123+100
				.uom(qty_uom)
				.sourceSameAsCurrent()
				.assertExpected(qtyNew);
	}

	@Test
	public void test_add_UomMatching_SourceUomMatching()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);
		final I_C_UOM qty_sourceUom = uomHelper.createUOM("qty_sourceUom", 2);
		//noinspection UnnecessaryLocalVariable
		final I_C_UOM qtyToAdd_uom = qty_uom;
		//noinspection UnnecessaryLocalVariable
		final I_C_UOM qtyToAdd_sourceUom = qty_sourceUom;

		final Quantity qty = new Quantity(new BigDecimal("123"), qty_uom, new BigDecimal("456"), qty_sourceUom);
		final Quantity qtyToAdd = new Quantity(new BigDecimal("100"), qtyToAdd_uom, new BigDecimal("200"), qtyToAdd_sourceUom);
		final Quantity qtyNew = qty.add(qtyToAdd);

		new QuantityExpectation()
				.qty("223") // =123+100
				.uom(qty_uom)
				.sourceQty("656") // =456+200
				.sourceUOM(qty_sourceUom)
				.assertExpected(qtyNew);
	}

	@Test
	public void test_add_UomNotMatching_SourceUomMatching()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);
		final I_C_UOM qty_sourceUom = uomHelper.createUOM("qty_sourceUom", 2);
		final I_C_UOM qtyToAdd_uom = uomHelper.createUOM("qtyToAdd_Uom", 2);
		//noinspection UnnecessaryLocalVariable
		final I_C_UOM qtyToAdd_sourceUom = qty_sourceUom;

		final Quantity qty = new Quantity(new BigDecimal("123"), qty_uom, new BigDecimal("456"), qty_sourceUom);
		final Quantity qtyToAdd = new Quantity(new BigDecimal("100"), qtyToAdd_uom, new BigDecimal("200"), qtyToAdd_sourceUom);

		assertThatThrownBy(() -> qty.add(qtyToAdd))
				.isInstanceOf(QuantitiesUOMNotMatchingExpection.class);
	}

	/**
	 * Expecting same result as {@link #test_add_UomNotMatching_SourceUomMatching()}.
	 */
	@Test
	public void test_add_UomNotMatching_SourceUomNotMatching()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);
		final I_C_UOM qty_sourceUom = uomHelper.createUOM("qty_sourceUom", 2);
		final I_C_UOM qtyToAdd_uom = uomHelper.createUOM("qtyToAdd_Uom", 2);
		final I_C_UOM qtyToAdd_sourceUom = uomHelper.createUOM("qtyToAdd_sourceUom", 2);

		final Quantity qty = new Quantity(new BigDecimal("123"), qty_uom, new BigDecimal("456"), qty_sourceUom);
		final Quantity qtyToAdd = new Quantity(new BigDecimal("100"), qtyToAdd_uom, new BigDecimal("200"), qtyToAdd_sourceUom);

		assertThatThrownBy(() -> qty.add(qtyToAdd))
				.isInstanceOf(QuantitiesUOMNotMatchingExpection.class);
	}

	@Test
	public void min()
	{
		final I_C_UOM uom = uomHelper.createUOM("uom", 2);

		final Quantity qty = Quantity.of("123", uom);
		final Quantity qtyPlusOne = Quantity.of("124", uom);

		assertThat(qty.min(qtyPlusOne)).isSameAs(qty);
		assertThat(qtyPlusOne.min(qty)).isSameAs(qty);
	}

	@Test
	public void max()
	{
		final I_C_UOM uom = uomHelper.createUOM("uom", 2);

		final Quantity qty = Quantity.of("123", uom);
		final Quantity qtyPlusOne = Quantity.of("124", uom);

		assertThat(qty.max(qtyPlusOne)).isSameAs(qtyPlusOne);
		assertThat(qtyPlusOne.max(qty)).isSameAs(qtyPlusOne);
	}

	@Nested
	class compare
	{
		@Test
		public void misc()
		{
			final I_C_UOM uom = uomHelper.createUOM("uom", 2);

			assertThat(Quantity.of("123", uom)).isLessThan(Quantity.of("124", uom));
			assertThat(Quantity.of("124", uom)).isGreaterThan(Quantity.of("123", uom));
			assertThat(Quantity.of("123", uom)).isEqualByComparingTo(Quantity.of("123", uom));
		}
	}

	@Nested
	class addPercent
	{
		@Test
		public void qty100_plus_33perc_uomPrecision2()
		{
			final I_C_UOM uom2 = uomHelper.createUOM("uom2", 2);
			assertThat(Quantity.of(100, uom2).add(Percent.of(33)).toBigDecimal())
					.isEqualTo("133.00");
		}

		@Test
		public void qty100_plus_33perc_uomPrecision5()
		{
			final I_C_UOM uom5 = uomHelper.createUOM("uom5", 5);
			assertThat(Quantity.of(100, uom5).add(Percent.of(33)).toBigDecimal())
					.isEqualTo("133.00000");
		}
	}

	@Nested
	class subtractPercent
	{
		@Test
		public void qty100_subtract_33perc_uomPrecision2()
		{
			final I_C_UOM uom2 = uomHelper.createUOM("uom2", 2);
			assertThat(Quantity.of(100, uom2).subtract(Percent.of(33)).toBigDecimal())
					.isEqualTo("67.00");
		}

		@Test
		public void qty100_subtract_33perc_uomPrecision5()
		{
			final I_C_UOM uom5 = uomHelper.createUOM("uom5", 5);
			assertThat(Quantity.of(100, uom5).subtract(Percent.of(33)).toBigDecimal())
					.isEqualTo("67.00000");
		}
	}

	@Test
	public void divideUsingUOMScale()
	{
		final I_C_UOM uom = uomHelper.createUOM(5);
		final Quantity qty = Quantity.of(1, uom)
				.divide(BigDecimal.valueOf(3));

		assertThat(qty.toBigDecimal()).isEqualByComparingTo("0.33333");
	}

	@Test
	public void test_ToString_SameUOMs()
	{
		final I_C_UOM uom = uomHelper.createUOM("UOM", 2);
		assertThat(Quantity.of(5, uom)).hasToString("5 UOM");
	}

	@Test
	public void test_ToString_DifferentUOMs()
	{
		final I_C_UOM uom = uomHelper.createUOM("UOM", 2);
		final I_C_UOM sourceUOM = uomHelper.createUOM("SOURCE_UOM", 2);
		final Quantity qty = new Quantity(BigDecimal.valueOf(5), uom, BigDecimal.valueOf(4), sourceUOM);
		assertThat(qty).hasToString("5 UOM (source: 4 SOURCE_UOM)");
	}

	@Test
	public void serialize_deserialize_with_source()
	{
		final BigDecimal qty = new BigDecimal("1234");
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);

		final BigDecimal sourceQty = new BigDecimal("1235");
		final I_C_UOM sourceUOM = uomHelper.createUOM("UOM2", 2);

		final Quantity quantity = new Quantity(qty, uom, sourceQty, sourceUOM);

		final JSONObjectMapper<Quantity> jsonMapper = JSONObjectMapper.forClass(Quantity.class);
		final String quantityAsString = jsonMapper.writeValueAsString(quantity);
		final Quantity deserializedQuantity = jsonMapper.readValue(quantityAsString);

		assertThat(deserializedQuantity).isEqualTo(quantity);
	}

	@Test
	public void serialize_deserialize_without_source()
	{
		final BigDecimal qty = new BigDecimal("1234");
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);

		final Quantity quantity = Quantity.of(qty, uom);

		final JSONObjectMapper<Quantity> jsonMapper = JSONObjectMapper.forClass(Quantity.class);
		final String quantityAsString = jsonMapper.writeValueAsString(quantity);
		final Quantity deserializedQuantity = jsonMapper.readValue(quantityAsString);

		assertThat(deserializedQuantity).isEqualTo(quantity);
	}

	@Test
	public void roundToUOMPrecision_expect_rounded()
	{
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);
		final Quantity qty = Quantity.of(new BigDecimal("12.34567"), uom);
		final Quantity qtyRounded = qty.roundToUOMPrecision();
		assertThat(qtyRounded.toBigDecimal()).isEqualByComparingTo("12.35");
		assertThat(qtyRounded.getUOM()).isSameAs(uom);
	}

	@Test
	public void roundToUOMPrecision_expect_same()
	{
		final I_C_UOM uom = uomHelper.createUOM("UOM1", 2);
		final Quantity qty = Quantity.of(new BigDecimal("12.34"), uom);
		assertThat(qty.roundToUOMPrecision()).isSameAs(qty);
	}

	@Nested
	public class withoutSource
	{
		private I_C_UOM uom1;
		private I_C_UOM uom2;

		@BeforeEach
		public void init()
		{
			uom1 = uomHelper.createUOM("UOM1", 2);
			uom2 = uomHelper.createUOM("UOM2", 2);
		}

		@Test
		public void fromWithoutSourceQty()
		{
			final Quantity qty = Quantity.of(123, uom1);
			assertThat(qty.withoutSource()).isSameAs(qty);
		}

		@Test
		public void fromWithSourceQty()
		{
			final Quantity qty = new Quantity(new BigDecimal("123"), uom1, new BigDecimal("456"), uom2);
			assertThat(qty.withoutSource()).isEqualTo(Quantity.of(123, uom1));
		}
	}

	@Nested
	public class multiply_with_percent
	{
		@Test
		public void qty100_multiplyBy_30percent()
		{
			final I_C_UOM uom = uomHelper.createUOM("UOM", 2);
			final Quantity qty = Quantity.of("100", uom);
			assertThat(qty.multiply(Percent.of(30))).isEqualTo(Quantity.of("30", uom));
		}

		@Test
		public void qty100_multiplyBy_100percent()
		{
			final I_C_UOM uom = uomHelper.createUOM("UOM", 2);
			final Quantity qty = Quantity.of("100", uom);
			assertThat(qty.multiply(Percent.of(100))).isSameAs(qty);
		}
	}

	@Nested
	public class getCommonUomIdOfAll
	{
		@Test
		void withSomeNullQtys()
		{
			final I_C_UOM uom = uomHelper.createUOM("UOM", 2);

			final UomId commonUomId = Quantity.getCommonUomIdOfAll(Quantity.of(1, uom), null, Quantity.of(2, uom), null);
			assertThat(commonUomId.getRepoId()).isEqualTo(uom.getC_UOM_ID());
		}
	}

	@Nested
	public class assertSameUOM
	{
		@Test
		void empty()
		{
			Quantity.assertSameUOM();
		}

		@Test
		void nullArray()
		{
			Quantity.assertSameUOM((Quantity[])null);
		}

		@Test
		void nullQty()
		{
			Quantity.assertSameUOM((Quantity)null);
		}

		@Test
		void singleQty()
		{
			final I_C_UOM uom = uomHelper.createUOM("UOM", 2);
			Quantity.assertSameUOM(Quantity.of(1, uom));
		}

		@Test
		void singleQty_withSomeNullQtys()
		{
			final I_C_UOM uom = uomHelper.createUOM("UOM", 2);
			Quantity.assertSameUOM(null, Quantity.of(1, uom), null);
		}

		@Test
		void sameUOM_withSomeNullQtys()
		{
			final I_C_UOM uom = uomHelper.createUOM("UOM", 2);

			Quantity.assertSameUOM(Quantity.of(1, uom), null, Quantity.of(2, uom), null);
		}

		@Test
		void distinctUOMs_withSomeNullQtys()
		{
			final I_C_UOM uom1 = uomHelper.createUOM("UOM1", 2);
			final I_C_UOM uom2 = uomHelper.createUOM("UOM2", 2);

			assertThatThrownBy(
					() -> Quantity.assertSameUOM(Quantity.of(1, uom1), null, Quantity.of(2, uom2), null)
			)
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("at least two quantity instances have different UOMs");
		}

	}
}
