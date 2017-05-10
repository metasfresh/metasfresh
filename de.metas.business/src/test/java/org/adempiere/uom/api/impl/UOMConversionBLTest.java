package org.adempiere.uom.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.uom.api.QuantityExpectation;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.metas.quantity.Quantity;
import de.metas.uom.UOMConstants;

public class UOMConversionBLTest extends UOMTestBase
{
	/** Service under test */
	private UOMConversionBL conversionBL;
	private Properties ctx;

	@Override
	protected void afterInit()
	{
		this.ctx = Env.getCtx();

		// Service under test
		this.conversionBL = new UOMConversionBL();
		Services.registerService(IUOMConversionBL.class, conversionBL);
	}

	@Test
	public void test_roundToUOMPrecisionIfPossible()
	{
		final int uomPrecision = 2;

		test_roundToUOMPrecisionIfPossible("0.0000000000", uomPrecision, "0.00", 2);
		test_roundToUOMPrecisionIfPossible("0.0000000000", uomPrecision, "0.00", 2);
		test_roundToUOMPrecisionIfPossible("-0.0000000000", uomPrecision, "0.00", 2);

		test_roundToUOMPrecisionIfPossible("12.00000000000", uomPrecision, "12.00", 2);
		test_roundToUOMPrecisionIfPossible("-12.00000000000", uomPrecision, "-12.00", 2);

		test_roundToUOMPrecisionIfPossible("10.0", uomPrecision, "10.00", 2);
		test_roundToUOMPrecisionIfPossible("-10.0", uomPrecision, "-10.00", 2);

		test_roundToUOMPrecisionIfPossible("12.3", uomPrecision, "12.30", 2);
		test_roundToUOMPrecisionIfPossible("-12.3", uomPrecision, "-12.30", 2);

		test_roundToUOMPrecisionIfPossible("12.30", uomPrecision, "12.30", 2);
		test_roundToUOMPrecisionIfPossible("-12.30", uomPrecision, "-12.30", 2);

		test_roundToUOMPrecisionIfPossible("10.00", uomPrecision, "10.00", 2);
		test_roundToUOMPrecisionIfPossible("-10.00", uomPrecision, "-10.00", 2);

		test_roundToUOMPrecisionIfPossible("12.34", uomPrecision, "12.34", 2);
		test_roundToUOMPrecisionIfPossible("-12.34", uomPrecision, "-12.34", 2);

		test_roundToUOMPrecisionIfPossible("10.000", uomPrecision, "10.00", 2);
		test_roundToUOMPrecisionIfPossible("-10.000", uomPrecision, "-10.00", 2);

		test_roundToUOMPrecisionIfPossible("12.345", uomPrecision, "12.345", 3);
		test_roundToUOMPrecisionIfPossible("-12.345", uomPrecision, "-12.345", 3);

		test_roundToUOMPrecisionIfPossible("12.34500", uomPrecision, "12.345", 3);
		test_roundToUOMPrecisionIfPossible("-12.34500", uomPrecision, "-12.345", 3);

		test_roundToUOMPrecisionIfPossible("12.345000000000", uomPrecision, "12.345", 3);
		test_roundToUOMPrecisionIfPossible("-12.345000000000", uomPrecision, "-12.345", 3);
	}

	private void test_roundToUOMPrecisionIfPossible(
			final String qtyStr, final int uomPrecision,
			final String qtyStrExpected,
			final int uomPrecisionExpected)
	{
		final I_C_UOM uom = uomConversionHelper.createUOM(uomPrecision);
		BigDecimal qty = new BigDecimal(qtyStr);

		final BigDecimal qtyRounded = conversionBL.roundToUOMPrecisionIfPossible(qty, uom);
		Assert.assertThat("Rounded qty value shall equal with initial qty value", qtyRounded, Matchers.comparesEqualTo(qty));

		Assert.assertEquals("Invalid rounded qty precision for '" + qtyRounded + "'",
				uomPrecisionExpected, qtyRounded.scale());

		final BigDecimal qtyExpected = new BigDecimal(qtyStrExpected);
		Assert.assertEquals("Invalid qty", qtyExpected, qtyRounded);
	}
	

	@Test
	public void test_Convert()
	{
		// Mocking the case for Folie AB Alicesalat (1000 lm)
		// Multiply Rate = 1500000.000000000000;
		// Divide Rate = 0.000000666667

		final I_C_UOM rolle = uomConversionHelper.createUOM("Rolle", 2, 0, "RL");
		final I_C_UOM millimeter = uomConversionHelper.createUOM("Millimeter", 2, 0, "mm");

		final I_M_Product folie = uomConversionHelper.createProduct("Folie", rolle);

		BigDecimal qtyToConvert = BigDecimal.ONE;

		final BigDecimal multiplyRate = new BigDecimal("1500000.000000000000");
		final BigDecimal divideRate = new BigDecimal("0.000000666667");

		uomConversionHelper.createUOMConversion(
				folie.getM_Product_ID(),
				rolle,
				millimeter,
				multiplyRate,
				divideRate);

		BigDecimal convertedQty = conversionBL.convertQty(folie, qtyToConvert, rolle, millimeter);
		BigDecimal expectedQty = new BigDecimal(1500000.00);
		Assert.assertTrue(expectedQty.compareTo(convertedQty) == 0);

		qtyToConvert = new BigDecimal(1500000);
		convertedQty = conversionBL.convertQty(folie, qtyToConvert, millimeter, rolle);
		expectedQty = new BigDecimal(1);
		Assert.assertTrue(expectedQty.compareTo(convertedQty) == 0);

	}

	@Test(expected = AdempiereException.class)
	public void test_Convert_NoProductInConversion()
	{
		final I_C_UOM rolle = uomConversionHelper.createUOM("Rolle", 2, 0, "RL");

		final I_M_Product folie = uomConversionHelper.createProduct("Folie", rolle);

		final I_C_UOM millimeter = uomConversionHelper.createUOM("Millimeter", 2, 0, "mm");
		final I_C_UOM meter = uomConversionHelper.createUOM("meter", 2, 0, "MTR");
		final BigDecimal multiplyRate = new BigDecimal(1000);
		final BigDecimal divideRate = new BigDecimal("1.00000000000000000000");

		uomConversionHelper.createUOMConversion(
				-1,
				meter,
				millimeter,
				multiplyRate,
				divideRate);

		BigDecimal qtyToConvert = new BigDecimal(2000);
		BigDecimal expectedQty = new BigDecimal(2);
		BigDecimal convertedQty = conversionBL.convertQty(folie, qtyToConvert, millimeter, meter);

		Assert.assertTrue(expectedQty.compareTo(convertedQty) == 0);
	}

	@Test
	public void test_ConvertGeneralConversion()
	{
		final I_C_UOM millimeter = uomConversionHelper.createUOM("Millimeter", 2, 0, "mm");
		final I_C_UOM meter = uomConversionHelper.createUOM("Meter", 2, 0, "MTR");
		final BigDecimal multiplyRate = new BigDecimal(1000);
		final BigDecimal divideRate = new BigDecimal("1.00000000000000000000");

		uomConversionHelper.createUOMConversion(
				-1,
				meter,
				millimeter,
				multiplyRate,
				divideRate);

		BigDecimal qtyToConvert = new BigDecimal(2);
		BigDecimal expectedQty = new BigDecimal(2000);
		BigDecimal convertedQty = conversionBL.convert(ctx, meter, millimeter, qtyToConvert, true);

		Assert.assertTrue(expectedQty.compareTo(convertedQty) == 0);
	}

	@Test
	public void test_Convert_GeneralConversion()
	{
		final I_C_UOM rolle = uomConversionHelper.createUOM("Rolle", 2, 0, "RL");

		final I_M_Product folie = uomConversionHelper.createProduct("Folie", rolle);

		final I_C_UOM millimeter = uomConversionHelper.createUOM("Millimeter", 2, 0, "mm");

		BigDecimal multiplyRate = new BigDecimal("1500000.000000000000");
		BigDecimal divideRate = new BigDecimal("0.000000666667");

		uomConversionHelper.createUOMConversion(
				-1,
				rolle,
				millimeter,
				multiplyRate,
				divideRate);

		final BigDecimal qtyToConvert = new BigDecimal(3000000);
		BigDecimal expectedQty = new BigDecimal(2);
		BigDecimal convertedQty = conversionBL.convertQty(folie, qtyToConvert, millimeter, rolle);

		Assert.assertTrue(expectedQty.compareTo(convertedQty) == 0);
	}

	@Test
	public void test_Convert_GeneralConversion_UseStdPrecision()
	{
		final I_C_UOM rolle = uomConversionHelper.createUOM("Rolle", 2, 0, "RL");

		final I_C_UOM millimeter = uomConversionHelper.createUOM("Millimeter", 3, 2, "mm");

		BigDecimal multiplyRate = new BigDecimal("1500000.1290000000");
		BigDecimal divideRate = new BigDecimal("0.000000666667");

		uomConversionHelper.createUOMConversion(
				-1,
				rolle,
				millimeter,
				multiplyRate,
				divideRate);

		final BigDecimal qtyToConvert = new BigDecimal(2);
		BigDecimal expectedQty = new BigDecimal("3000000.258");
		BigDecimal convertedQty = conversionBL.convert(ctx, rolle, millimeter, qtyToConvert, true);

		Assert.assertTrue(expectedQty.compareTo(convertedQty) == 0);
	}

	@Test
	public void test_Convert_GeneralConversion_DoNotUseStdPrecision()
	{
		final I_C_UOM rolle = uomConversionHelper.createUOM("Rolle", 2, 0, "RL");

		final I_C_UOM millimeter = uomConversionHelper.createUOM("Millimeter", 3, 2, "mm");

		BigDecimal multiplyRate = new BigDecimal("1500000.1290000000");
		BigDecimal divideRate = new BigDecimal("0.000000666667");

		uomConversionHelper.createUOMConversion(
				-1,
				rolle,
				millimeter,
				multiplyRate,
				divideRate);

		final BigDecimal qtyToConvert = new BigDecimal(2);
		BigDecimal expectedQty = new BigDecimal("3000000.26");
		BigDecimal convertedQty = conversionBL.convert(ctx, rolle, millimeter, qtyToConvert, false);

		Assert.assertTrue(expectedQty.compareTo(convertedQty) == 0);
	}

	@Test
	public void testDeriveRate()
	{
		final I_C_UOM minute = uomConversionHelper.createUOM(
				"Minute",
				1,
				0,
				UOMConstants.X12_MINUTE);

		final I_C_UOM hour = uomConversionHelper.createUOM(
				"Hour",
				1,
				0,
				UOMConstants.X12_HOUR);

		final I_C_UOM day = uomConversionHelper.createUOM(
				"Day",
				1,
				0,
				UOMConstants.X12_DAY);

		final I_C_UOM week = uomConversionHelper.createUOM(
				"Week",
				1,
				0,
				UOMConstants.X12_WEEK);

		final I_C_UOM month = uomConversionHelper.createUOM(
				"Month",
				1,
				0,
				UOMConstants.X12_MONTH);

		final I_C_UOM year = uomConversionHelper.createUOM(
				"Year",
				1,
				0,
				UOMConstants.X12_YEAR);

		BigDecimal rate;

		final BigDecimal minutesPerDay = new BigDecimal(60 * 24);
		rate = conversionBL.deriveRate(ctx, day, minute);
		Assert.assertTrue(minutesPerDay.equals(rate));

		final BigDecimal daysPerWeek = new BigDecimal(7);
		rate = conversionBL.deriveRate(ctx, week, day);
		Assert.assertTrue(daysPerWeek.equals(rate));

		final BigDecimal hoursPerDay = new BigDecimal(24);
		rate = conversionBL.deriveRate(ctx, day, hour);
		Assert.assertTrue(hoursPerDay.equals(rate));

		final BigDecimal hoursPerWeek = daysPerWeek.multiply(hoursPerDay);
		rate = conversionBL.deriveRate(ctx, week, hour);
		Assert.assertTrue(hoursPerWeek.equals(rate));

		final BigDecimal weeksPerMonth = new BigDecimal(4);
		rate = conversionBL.deriveRate(ctx, month, week);
		Assert.assertTrue(weeksPerMonth.equals(rate));

		final BigDecimal daysPerMinute = new BigDecimal(1.0 / 1440.0);
		rate = conversionBL.deriveRate(ctx, minute, day);
		Assert.assertTrue(daysPerMinute.equals(rate));

		final BigDecimal weeksPerDay = new BigDecimal(1.0 / 7.0);
		rate = conversionBL.deriveRate(ctx, day, week);
		Assert.assertTrue(weeksPerDay.equals(rate));

		final BigDecimal daysPerHour = new BigDecimal(1.0 / 24.0);
		rate = conversionBL.deriveRate(ctx, hour, day);
		Assert.assertTrue(daysPerHour.equals(rate));

		final BigDecimal weeksPerHour = new BigDecimal(1.0 / 168.0);
		rate = conversionBL.deriveRate(ctx, hour, week);
		Assert.assertTrue(weeksPerHour.equals(rate));

		final BigDecimal monthsPerWeek = new BigDecimal(1.0 / 4.0);
		rate = conversionBL.deriveRate(ctx, week, month);
		Assert.assertTrue(monthsPerWeek.equals(rate));

		final BigDecimal minutesPerYear = new BigDecimal(1.0 / 525600.0);
		rate = conversionBL.deriveRate(ctx, minute, year);
		Assert.assertTrue(minutesPerYear.equals(rate));
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07433_Folie_Zuteilung_Produktion_Fertigstellung_POS_%28102170996938%29
	 */
	@Test
	public void test_convertToProductUOM_CheckProductUOMPrecisionIsUsed()
	{
		final I_C_UOM uomMillimeter = uomConversionHelper.createUOM("Millimeter", 2, 4);
		final I_C_UOM uomRolle = uomConversionHelper.createUOM("Rolle", 4, 4);

		final I_M_Product product = uomConversionHelper.createProduct("Folie", uomRolle);

		//
		// Conversion: Rolle -> Millimeter
		// 1 Rolle = 1_500_000 millimeters
		uomConversionHelper.createUOMConversion(
				product.getM_Product_ID(), // M_Product_ID
				uomRolle,
				uomMillimeter,
				new BigDecimal(1_500_000), // multiply rate
				new BigDecimal(0.000000666667)  // divide rate
				);

		final I_C_UOM uomSource = uomMillimeter;
		final BigDecimal qtyToConvert = new BigDecimal("28600");

		// Expected converted qty: 0.0191 = 28600 x 0.000000666667(divideRate) rounded to 4 digits
		// NOTE: we particulary picked those numbers to make sure that Product UOM's precision (i.e. Rolle, precision=4) is used and not source UOM's precision
		final BigDecimal qtyConvertedExpected = new BigDecimal("0.0191");
		final BigDecimal qtyConvertedActual = conversionBL.convertToProductUOM(ctx, product, uomSource, qtyToConvert);

		// NOTE: we don't use compareTo because we also want to match the precision
		Assert.assertEquals("Invalid converted qty", qtyConvertedExpected, qtyConvertedActual);
	}

	@Test
	public void test_convertFromProductUOM_CheckProductUOMPrecisionIsUsed()
	{
		final I_C_UOM uomMillimeter = uomConversionHelper.createUOM("Millimeter", 2, 4);
		final I_C_UOM uomRolle = uomConversionHelper.createUOM("Rolle", 4, 4);

		final I_M_Product product = uomConversionHelper.createProduct("Folie", uomMillimeter);

		//
		// Conversion: Rolle -> Millimeter
		// 1 Rolle = 1_500_000 millimeters
		uomConversionHelper.createUOMConversion(
				product.getM_Product_ID(), // M_Product_ID
				uomRolle,
				uomMillimeter,
				new BigDecimal(1_500_000), // multiply rate
				new BigDecimal(0.000000666667)  // divide rate
				);

		final I_C_UOM uomDest = uomRolle;
		final BigDecimal qtyToConvert = new BigDecimal("28600");

		// Expected converted qty: 0.0191 = 28600 x 0.000000666667(divideRate) rounded to 4 digits
		// NOTE: we particulary picked those numbers to make sure that Product UOM's precision (i.e. Rolle, precision=4) is used and not source UOM's precision
		final BigDecimal qtyConvertedExpected = new BigDecimal("0.0191");
		final BigDecimal qtyConvertedActual = conversionBL.convertFromProductUOM(ctx, product, uomDest, qtyToConvert);

		// NOTE: we don't use compareTo because we also want to match the precision
		Assert.assertEquals("Invalid converted qty", qtyConvertedExpected, qtyConvertedActual);
	}

	@Test
	public void testServerSide_NoProductConversion()
	{
		Ini.setClient(false);
		final I_C_UOM rolle = uomConversionHelper.createUOM("Rolle", 2, 0, "RL");

		final I_M_Product folie = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Product.class, ITrx.TRXNAME_None);
		folie.setC_UOM_ID(rolle.getC_UOM_ID());
		folie.setName("Folie");
		folie.setValue("Folie");
		InterfaceWrapperHelper.save(folie);

		final I_C_UOM millimeter = uomConversionHelper.createUOM("Millimeter", 2, 0, "mm");

		BigDecimal multiplyRate = new BigDecimal("1500000.000000000000");
		BigDecimal divideRate = new BigDecimal("0.000000666667");

		uomConversionHelper.createUOMConversion(
				-1,
				rolle,
				millimeter,
				multiplyRate,
				divideRate);

		final BigDecimal qtyToConvert = new BigDecimal(3000000);
		BigDecimal expectedQty = new BigDecimal(2);
		BigDecimal convertedQty = Services.get(IUOMConversionBL.class).convertQty(folie, qtyToConvert, millimeter, rolle);

		Assert.assertTrue(expectedQty.compareTo(convertedQty) == 0);
	}

	@Test
	public void test_getRateForConversionFromProductUOM_DirectConversionShallBeUsed()
	{
		final I_C_UOM uom1 = uomConversionHelper.createUOM("uom1", 2, 4);
		final I_C_UOM uom2 = uomConversionHelper.createUOM("uom2", 2, 4);
		final I_M_Product product = uomConversionHelper.createProduct("product", uom1);

		uomConversionHelper.createUOMConversion(product, uom1, uom2, new BigDecimal("2"), new BigDecimal("3"));

		final BigDecimal rate = conversionBL.getRateForConversionFromProductUOM(ctx, product, uom2);
		Assert.assertEquals("Invalid conversion rate for  uom1->uom2", new BigDecimal("2"), rate);
	}

	@Test
	public void test_getRateForConversionFromProductUOM_ReverseConversionShallBeUsed()
	{
		final I_C_UOM uom1 = uomConversionHelper.createUOM("uom1", 2, 4);
		final I_C_UOM uom2 = uomConversionHelper.createUOM("uom2", 2, 4);
		final I_M_Product product = uomConversionHelper.createProduct("product", uom1);

		uomConversionHelper.createUOMConversion(product, uom2, uom1, new BigDecimal("2"), new BigDecimal("3"));

		final BigDecimal rate = conversionBL.getRateForConversionFromProductUOM(ctx, product, uom2);
		Assert.assertEquals("Invalid conversion rate for  uom1->uom2", new BigDecimal("3"), rate);
	}

	@Test
	public void test_getRateForConversionToProductUOM_DirectConversionShallBeUsed()
	{
		final I_C_UOM uom1 = uomConversionHelper.createUOM("uom1", 2, 4);
		final I_C_UOM uom2 = uomConversionHelper.createUOM("uom2", 2, 4);
		final I_M_Product product = uomConversionHelper.createProduct("product", uom1);

		uomConversionHelper.createUOMConversion(product, uom1, uom2, new BigDecimal("2"), new BigDecimal("3"));

		final BigDecimal rate = conversionBL.getRateForConversionToProductUOM(ctx, product, uom2);
		Assert.assertEquals("Invalid conversion rate for  uom1->uom2", new BigDecimal("3"), rate);
	}

	@Test
	public void test_getRateForConversionToProductUOM_ReverseConversionShallBeUsed()
	{
		final I_C_UOM uom1 = uomConversionHelper.createUOM("uom1", 2, 4);
		final I_C_UOM uom2 = uomConversionHelper.createUOM("uom2", 2, 4);
		final I_M_Product product = uomConversionHelper.createProduct("product", uom1);

		uomConversionHelper.createUOMConversion(product, uom2, uom1, new BigDecimal("2"), new BigDecimal("3"));

		final BigDecimal rate = conversionBL.getRateForConversionToProductUOM(ctx, product, uom2);
		Assert.assertEquals("Invalid conversion rate for  uom1->uom2", new BigDecimal("2"), rate);
	}
	
	@Test
	public void test_convertTo_CurrentUOM()
	{
		final BigDecimal qty = new BigDecimal("1234");
		final I_C_UOM uom = uomConversionHelper.createUOM("UOM1", 2);
		final BigDecimal sourceQty = new BigDecimal("1235");
		final I_C_UOM sourceUOM = uomConversionHelper.createUOM("UOM2", 2);
		final Quantity quantity = new Quantity(qty, uom, sourceQty, sourceUOM);

		final IUOMConversionContext conversionCtx = null; // don't care, shall not be used
		final Quantity quantityConv = conversionBL.convertQuantityTo(quantity, conversionCtx, uom);
		Assert.assertSame("Invalid converted quantity", quantity, quantityConv);
	}

	@Test
	public void test_convertTo_SourceUOM()
	{
		final BigDecimal qty = new BigDecimal("1234");
		final I_C_UOM uom = uomConversionHelper.createUOM("UOM1", 2);
		final BigDecimal sourceQty = new BigDecimal("1235");
		final I_C_UOM sourceUOM = uomConversionHelper.createUOM("UOM2", 2);
		final Quantity quantity = new Quantity(qty, uom, sourceQty, sourceUOM);

		final IUOMConversionContext conversionCtx = null; // don't care, shall not be used
		final Quantity quantityConv = conversionBL.convertQuantityTo(quantity, conversionCtx, sourceUOM);
		new QuantityExpectation()
				.sameQty(sourceQty)
				.uom(sourceUOM)
				.sameSourceQty(qty)
				.sourceUOM(uom)
				.assertExpected("converted quantity", quantityConv);
	}

	@Test
	public void test_convertTo_OtherUOM()
	{
		//
		// Create Quantity
		final BigDecimal qty = new BigDecimal("1234");
		final I_C_UOM uom = uomConversionHelper.createUOM("UOM", 2);
		final BigDecimal sourceQty = new BigDecimal("1235");
		final I_C_UOM sourceUOM = uomConversionHelper.createUOM("UOM_Source", 2);
		final Quantity quantity = new Quantity(qty, uom, sourceQty, sourceUOM);

		//
		// Create the other UOM
		final I_C_UOM otherUOM = uomConversionHelper.createUOM("UOM_Other", 2);

		//
		// Create conversion rate: uom -> otherUOM (for product)
		final I_M_Product product = uomConversionHelper.createProduct("product", uom);
		uomConversionHelper.createUOMConversion(product, uom, otherUOM, new BigDecimal("2"), new BigDecimal("0.5"));

		//
		// Create UOM Conversion context
		final IUOMConversionContext uomConversionCtx = uomConversionHelper.createUOMConversionContext(product);

		//
		// Convert the quantity to "otherUOM" and validate
		final Quantity quantityConv = conversionBL.convertQuantityTo(quantity, uomConversionCtx, otherUOM);
		new QuantityExpectation()
				.qty("2468")
				.uom(otherUOM)
				.sourceQty(qty)
				.sourceUOM(uom)
				.assertExpected("converted quantity", quantityConv);
	}
}
