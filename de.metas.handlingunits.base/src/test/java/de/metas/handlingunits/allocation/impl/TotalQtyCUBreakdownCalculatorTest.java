package de.metas.handlingunits.allocation.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator.LUQtys;

public class TotalQtyCUBreakdownCalculatorTest
{
	/**
	 * Test case:
	 * <ul>
	 * <li>total QtyCU=100
	 * <li>standard Qty CU/TU = 6
	 * <li>standard Qty TU/LU = 5
	 * </ul>
	 * 
	 * Expectation:
	 * <ul>
	 * <li>3 complete LUs
	 * <li>1 incomplete LU with CU/TU=6, TU/LU=2, CU/LU=10
	 * </ul>
	 */
	@Test
	public void test_StandardScenario()
	{
		//
		// Define input quantities and expectations
		final BigDecimal standard_QtyCUsPerTU = new BigDecimal("6");
		final BigDecimal standard_QtyTUsPerLU = new BigDecimal("5");
		final BigDecimal total_QtyCUs = new BigDecimal("100");
		//
		final LUQtys expectation_fullLU = LUQtys.builder()
				.setQtyCUsPerTU(standard_QtyCUsPerTU)
				.setQtyTUsPerLU(standard_QtyTUsPerLU)
				.build();
		final LUQtys expectation_partialLU = LUQtys.builder()
				.setQtyCUsPerTU(standard_QtyCUsPerTU)
				.setQtyTUsPerLU(new BigDecimal("2"))
				.setQtyCUsPerLU_IfGreaterThanZero(new BigDecimal("10"))
				.build();

		//
		// Create calculator
		final TotalQtyCUBreakdownCalculator calculator = TotalQtyCUBreakdownCalculator.builder()
				.setQtyCUsTotal(total_QtyCUs)
				.setQtyTUsTotal(null)
				.setStandardQtyCUsPerTU(standard_QtyCUsPerTU)
				.setStandardQtyTUsPerLU(standard_QtyTUsPerLU)
				.build();

		//
		// Assertions before we remove all LUs
		Assert.assertEquals("Invalid IsInfiniteCapacity", false, calculator.isInfiniteCapacity());
		Assert.assertEquals("Invalid hasAvailableQty", true, calculator.hasAvailableQty());
		Assert.assertEquals("Invalid number of LUs available estimated", 3 + 1, calculator.getAvailableLUs());

		//
		// Remove all LUs and test
		final List<LUQtys> luQtys = calculator.subtractAllLUs();
		Assert.assertEquals("Invalid number of LUs: " + luQtys, 3 + 1, luQtys.size());
		Assert.assertEquals(expectation_fullLU, luQtys.get(0));
		Assert.assertEquals(expectation_fullLU, luQtys.get(1));
		Assert.assertEquals(expectation_fullLU, luQtys.get(2));
		Assert.assertEquals(expectation_partialLU, luQtys.get(3));
		Assert.assertEquals("Invalid hasAvailableQty", false, calculator.hasAvailableQty());
	}

	@Test
	public void test_AvailableCUs_lessThan_QtyCUsPerTU()
	{
		//
		// Define input quantities and expectations
		final BigDecimal standard_QtyCUsPerTU = new BigDecimal("6");
		final BigDecimal standard_QtyTUsPerLU = new BigDecimal("5");
		final BigDecimal total_QtyCUs = new BigDecimal("2");
		//
		final LUQtys expectation_partialLU = LUQtys.builder()
				.setQtyCUsPerTU(total_QtyCUs)
				.setQtyTUsPerLU(new BigDecimal("1"))
				.setQtyCUsPerLU_IfGreaterThanZero(new BigDecimal("2"))
				.build();

		//
		// Create calculator
		final TotalQtyCUBreakdownCalculator calculator = TotalQtyCUBreakdownCalculator.builder()
				.setQtyCUsTotal(total_QtyCUs)
				.setQtyTUsTotal(null)
				.setStandardQtyCUsPerTU(standard_QtyCUsPerTU)
				.setStandardQtyTUsPerLU(standard_QtyTUsPerLU)
				.build();

		//
		// Assertions before we remove all LUs
		Assert.assertEquals("Invalid IsInfiniteCapacity", false, calculator.isInfiniteCapacity());
		Assert.assertEquals("Invalid hasAvailableQty", true, calculator.hasAvailableQty());
		Assert.assertEquals("Invalid number of LUs available estimated", 1, calculator.getAvailableLUs());

		//
		// Remove all LUs and test
		final List<LUQtys> luQtys = calculator.subtractAllLUs();
		Assert.assertEquals("Invalid number of LUs: " + luQtys, 1, luQtys.size());
		Assert.assertEquals(expectation_partialLU, luQtys.get(0));
		Assert.assertEquals("Invalid hasAvailableQty", false, calculator.hasAvailableQty());
	}

	/**
	 * Checks the returned values of {@link TotalQtyCUBreakdownCalculator#NULL}.
	 * 
	 * Mainly it makes sure
	 * <ul>
	 * <li>state does not change
	 * <li>the LU subtract methods always return {@link LUQtys#NULL}.
	 * </ul>
	 */
	@Test
	public void test_NullCalculator_Behaviour()
	{
		final TotalQtyCUBreakdownCalculator calculator = TotalQtyCUBreakdownCalculator.NULL;
		Assert.assertEquals("Invalid IsInfiniteCapacity", true, calculator.isInfiniteCapacity());
		Assert.assertEquals("Invalid hasAvailableQty", false, calculator.hasAvailableQty());
		Assert.assertEquals("Invalid number of LUs available estimated", 0, calculator.getAvailableLUs());
		Assert.assertSame("Invalid subtract one LU returned value", LUQtys.NULL, calculator.subtractOneLU());

		Assert.assertEquals("Invalid subtract allLUs returned value", Collections.emptyList(), calculator.subtractAllLUs());

		calculator.subtractLU(LUQtys.builder()
				.setQtyCUsPerTU(5)
				.setQtyTUsPerLU(6)
				.build());

		Assert.assertThat("Invalid QtyCUsTotalAvailable", calculator.getQtyCUsTotalAvailable(), Matchers.comparesEqualTo(BigDecimal.ZERO));
		Assert.assertNull("Invalid QtyTUsTotalAvailable", calculator.getQtyTUsTotalAvailable());
	}
}
