package de.metas.pricing.attributebased.impl;

/*
 * #%L
 * de.metas.swat.base
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

import org.compiere.model.X_M_DiscountSchemaLine;
import org.junit.Assert;
import org.junit.Test;

public class AttributePricingTest
{
	final BigDecimal bd1 = new BigDecimal("1.05");
	final BigDecimal bd2 = new BigDecimal("1.03");
	final BigDecimal bd3 = new BigDecimal("1.051");
	final BigDecimal bd4 = new BigDecimal("1.10");
	final BigDecimal bd5 = new BigDecimal("1.900001");
	final BigDecimal bd6 = new BigDecimal("1.10");
	final BigDecimal bd7 = new BigDecimal("0.05");
	final BigDecimal bd8 = new BigDecimal("1.95");
	final BigDecimal bd9 = new BigDecimal("11.554");
	final BigDecimal bd10 = new BigDecimal("1.00");
	final BigDecimal bd11 = new BigDecimal("1.90");
	final BigDecimal bd12 = new BigDecimal("11.60");
	final BigDecimal bd13 = new BigDecimal("0.23");
	final BigDecimal bd14 = new BigDecimal("0.25");
	final BigDecimal bd15 = new BigDecimal("5");
	final BigDecimal bd16 = new BigDecimal("9");
	final BigDecimal bd17 = new BigDecimal("15");
	final BigDecimal bd18 = new BigDecimal("19.55");
	final BigDecimal bd19 = new BigDecimal("17.1");
	final BigDecimal bd20 = new BigDecimal("29.7");
	final BigDecimal bd21 = new BigDecimal("25");

	@Test
	public void testROUNDING_Nickel051015()
	{
		final String nickel = X_M_DiscountSchemaLine.LIST_ROUNDING_Nickel051015;

		Assert.assertEquals(bd1, AttributePricing.calculatePrice(bd2, 2, nickel));
		Assert.assertEquals(bd1, AttributePricing.calculatePrice(bd3, 2, nickel));
		Assert.assertEquals(bd1, AttributePricing.calculatePrice(bd1, 2, nickel));
		Assert.assertEquals(bd11, AttributePricing.calculatePrice(bd5, 2, nickel));

		// Particulat data used in IT of the task 08256:

		// original pricelist:
		// normal: Standard: 1.003 | Listen: 1.005 | Mindest: 1.008
		// attribute: Standard: 1.003 | Listen: 1.005 | Mindest: 1.008
		// new pricelist:
		// normal: Standard: 1.00 | Listen: 1.00 | Mindest: 1.00
		// attribute: Standard: 1.05 | Listen: 1.05 | Mindest: 1.05
		// original pricelist:
		// normal: Standard: 2.04 | Listen: 2.05 | Mindest: 2.06
		// attribute: Standard: 2.04 | Listen: 2.05 | Mindest: 2.06
		// new pricelist:
		// normal: Standard: 2.05 | Listen: 2.05 | Mindest: 2.05
		// attribute: Standard: 2.05 | Listen: 2.05 | Mindest: 2.10

		final BigDecimal part0 = new BigDecimal("1.00");
		final BigDecimal part1 = new BigDecimal("1.003");
		final BigDecimal part2 = new BigDecimal("1.005");
		final BigDecimal part3 = new BigDecimal("1.008");

		final BigDecimal part4 = new BigDecimal("2.04");
		final BigDecimal part5 = new BigDecimal("2.05");
		final BigDecimal part6 = new BigDecimal("2.06");

		Assert.assertEquals(part0, AttributePricing.calculatePrice(part1, 2, nickel));

		Assert.assertEquals(part0, AttributePricing.calculatePrice(part2, 2, nickel));

		Assert.assertEquals(part0, AttributePricing.calculatePrice(part3, 2, nickel));

		Assert.assertEquals(part5, AttributePricing.calculatePrice(part4, 2, nickel));

		Assert.assertEquals(part5, AttributePricing.calculatePrice(part5, 2, nickel));

		Assert.assertEquals(part5, AttributePricing.calculatePrice(part6, 2, nickel));

	}

	@Test
	public void testROUNDING_GanzeZahl00()
	{
		final String ganze = X_M_DiscountSchemaLine.LIST_ROUNDING_GanzeZahl00;

		Assert.assertEquals(BigDecimal.ONE, AttributePricing.calculatePrice(bd2, 2, ganze));
		Assert.assertEquals(BigDecimal.ONE, AttributePricing.calculatePrice(bd3, 2, ganze));
		Assert.assertEquals(BigDecimal.ONE, AttributePricing.calculatePrice(bd1, 2, ganze));
		Assert.assertEquals(new BigDecimal("2"), AttributePricing.calculatePrice(bd5, 2, ganze));
	}

	@Test
	public void testROUNDING_Dime102030()
	{
		final String dime = X_M_DiscountSchemaLine.LIST_ROUNDING_Dime102030;

		Assert.assertEquals(bd4, AttributePricing.calculatePrice(bd1, 2, dime));
		Assert.assertEquals(bd10, AttributePricing.calculatePrice(bd2, 2, dime));
		Assert.assertEquals(bd4, AttributePricing.calculatePrice(bd3, 2, dime));
		Assert.assertEquals(bd11, AttributePricing.calculatePrice(bd5, 2, dime));
		Assert.assertEquals(bd12, AttributePricing.calculatePrice(bd9, 2, dime));
	}

	@Test
	public void testROUNDING_Ten10002000()
	{
		final String ten = X_M_DiscountSchemaLine.LIST_ROUNDING_Ten10002000;

		Assert.assertEquals(BigDecimal.ZERO, AttributePricing.calculatePrice(bd1, 2, ten));
		Assert.assertEquals(BigDecimal.ZERO, AttributePricing.calculatePrice(bd2, 2, ten));
		Assert.assertEquals(BigDecimal.ZERO, AttributePricing.calculatePrice(bd3, 2, ten));
		Assert.assertEquals(BigDecimal.ZERO, AttributePricing.calculatePrice(bd5, 2, ten));
		Assert.assertEquals(BigDecimal.TEN, AttributePricing.calculatePrice(bd9, 2, ten));
	}

	@Test
	public void testROUNDING_Quarter255075()
	{
		final String quarter = X_M_DiscountSchemaLine.LIST_ROUNDING_Quarter255075;

		Assert.assertEquals(bd10, AttributePricing.calculatePrice(bd1, 2, quarter));
		Assert.assertEquals(bd10, AttributePricing.calculatePrice(bd2, 2, quarter));
		Assert.assertEquals(bd10, AttributePricing.calculatePrice(bd3, 2, quarter));
		Assert.assertEquals(new BigDecimal("2.00"), AttributePricing.calculatePrice(bd5, 2, quarter));
		Assert.assertEquals(new BigDecimal("11.50"), AttributePricing.calculatePrice(bd9, 2, quarter));
		Assert.assertEquals(bd14, AttributePricing.calculatePrice(bd14, 2, quarter));
	}

	@Test
	public void testROUNDING_EndingIn95()
	{
		final String ending95 = X_M_DiscountSchemaLine.LIST_ROUNDING_EndingIn95;

		Assert.assertEquals(bd15, AttributePricing.calculatePrice(bd1, 2, ending95));
		Assert.assertEquals(bd15, AttributePricing.calculatePrice(bd2, 2, ending95));
		Assert.assertEquals(bd15, AttributePricing.calculatePrice(bd3, 2, ending95));
		Assert.assertEquals(bd15, AttributePricing.calculatePrice(bd5, 2, ending95));
		Assert.assertEquals(bd15, AttributePricing.calculatePrice(bd9, 2, ending95));
		Assert.assertEquals(bd15, AttributePricing.calculatePrice(bd14, 2, ending95));
		Assert.assertEquals(bd16, AttributePricing.calculatePrice(bd19, 2, ending95));
		Assert.assertEquals(bd21, AttributePricing.calculatePrice(bd20, 2, ending95));
		Assert.assertEquals(bd17, AttributePricing.calculatePrice(bd18, 2, ending95));
	}
}
