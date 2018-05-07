package org.adempiere.uom.api;

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
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.uom.api.impl.UOMTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.quantity.QuantitiesUOMNotMatchingExpection;
import de.metas.quantity.Quantity;

public class QuantityTest
{
	private IContextAware contextProvider;
	private UOMTestHelper uomHelper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.contextProvider = PlainContextAware.newOutOfTrx(Env.getCtx());
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
					.weightedAverage(quantity.getQty(), previousAverageWeight);
			// System.out.println("Actual quantity avg=" + quantity.getQty());

			//
			// Assume their are equal
			Assert.assertThat("Invalid Quantity.weightedAverage result (count=" + count + ")",
					quantity.getQty(), // Actual
					BigDecimalCloseTo.closeTo(currentQtyAvg, comparationError) // expectation
			);
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
		Assert.assertSame("Invalid Qty", qty, quantity.getQty());
		Assert.assertSame("Invalid UOM", uom, quantity.getUOM());
		Assert.assertSame("Invalid Source Qty", sourceQty, quantity.getSourceQty());
		Assert.assertSame("Invalid Source UOM", sourceUOM, quantity.getSourceUOM());

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
		Assert.assertSame("Invalid QtyNew", qty, qtyNew);
	}

	@Test
	public void test_add_UomMatching_SourceUomNotMatching()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);
		final I_C_UOM qty_sourceUom = uomHelper.createUOM("qty_sourceUom", 2);
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
		final I_C_UOM qtyToAdd_uom = qty_uom;
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

	@Test(expected = QuantitiesUOMNotMatchingExpection.class)
	public void test_add_UomNotMatching_SourceUomMatching()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);
		final I_C_UOM qty_sourceUom = uomHelper.createUOM("qty_sourceUom", 2);
		final I_C_UOM qtyToAdd_uom = uomHelper.createUOM("qtyToAdd_Uom", 2);
		final I_C_UOM qtyToAdd_sourceUom = qty_sourceUom;

		final Quantity qty = new Quantity(new BigDecimal("123"), qty_uom, new BigDecimal("456"), qty_sourceUom);
		final Quantity qtyToAdd = new Quantity(new BigDecimal("100"), qtyToAdd_uom, new BigDecimal("200"), qtyToAdd_sourceUom);

		qty.add(qtyToAdd); // expect: QuantitiesUOMNotMatchingExpection
	}

	/**
	 * Expecting same result as {@link #test_add_UomNotMatching_SourceUomMatching()}.
	 */
	@Test(expected = QuantitiesUOMNotMatchingExpection.class)
	public void test_add_UomNotMatching_SourceUomNotMatching()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);
		final I_C_UOM qty_sourceUom = uomHelper.createUOM("qty_sourceUom", 2);
		final I_C_UOM qtyToAdd_uom = uomHelper.createUOM("qtyToAdd_Uom", 2);
		final I_C_UOM qtyToAdd_sourceUom = uomHelper.createUOM("qtyToAdd_sourceUom", 2);

		final Quantity qty = new Quantity(new BigDecimal("123"), qty_uom, new BigDecimal("456"), qty_sourceUom);
		final Quantity qtyToAdd = new Quantity(new BigDecimal("100"), qtyToAdd_uom, new BigDecimal("200"), qtyToAdd_sourceUom);

		qty.add(qtyToAdd); // expect: QuantitiesUOMNotMatchingExpection
	}

	@Test
	public void compare_same_uom_different_amout()
	{
		final I_C_UOM qty_uom = uomHelper.createUOM("qty_uom", 2);

		final BigDecimal qtyAmount = new BigDecimal("123");

		final Quantity qty = Quantity.of(qtyAmount, qty_uom);
		final Quantity qtyToCompare = Quantity.of(qtyAmount.add(ONE), qty_uom);

		assertThat(qtyToCompare).isGreaterThan(qty);
		assertThat(qty).isLessThan(qtyToCompare);
	}
}
