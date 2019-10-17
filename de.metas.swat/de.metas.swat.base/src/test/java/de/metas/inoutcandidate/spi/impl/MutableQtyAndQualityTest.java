package de.metas.inoutcandidate.spi.impl;

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
import java.math.RoundingMode;

import org.adempiere.util.test.ErrorMessage;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class MutableQtyAndQualityTest
{
	/**
	 * Tests {@link ReceiptQty#addQtyAndQualityDiscountPercent(BigDecimal, BigDecimal)} and {@link ReceiptQty#add(IQtyAndQuality)}.
	 */
	@Test
	public void test_add_sameQty_ExpectConstantPercent_SimpleTest01()
	{
		final ReceiptQtyExpectation<Object> expectationForOneTransaction = ReceiptQtyExpectation.newInstance()
				.qtyPrecision(2)
				.qty("7")
				.qualityDiscountPercent("3")
				.qtyWithIssues("0.21") // = 3 * 7% = 0.21
		;
		test_add_sameQty_ExpectConstantPercent(expectationForOneTransaction);
	}

	@Test
	public void test_add_sameQty_ExpectConstantPercent_SimpleTest02()
	{
		final ReceiptQtyExpectation<Object> expectationForOneTransaction = ReceiptQtyExpectation.newInstance()
				.qtyPrecision(2)
				.qty("437.35")
				.qualityDiscountPercent("93.18")
				.qtyWithIssues("407.52273") // = 437.35 * 93.18% = 407.52273
		;
		test_add_sameQty_ExpectConstantPercent(expectationForOneTransaction);
	}

	/**
	 * Tests {@link ReceiptQty#addQtyAndQualityDiscountPercent(BigDecimal, BigDecimal)} and {@link ReceiptQty#add(IQtyAndQuality)}.
	 */
	@Test
	public void test_add_sameQty_ExpectConstantPercent_RandomValues()
	{
		for (int qtyPrecision = 0; qtyPrecision <= 6; qtyPrecision++)
		{
			for (int i = 1; i <= 10; i++)
			{
				final ReceiptQtyExpectation<Object> expectationForOneTransaction = randomQtyAndQualityExpectation(qtyPrecision);
				test_add_sameQty_ExpectConstantPercent(expectationForOneTransaction);
			}
		}

	}

	private void test_add_sameQty_ExpectConstantPercent(final ReceiptQtyExpectation<?> expectationForOneTransaction)
	{
		final int qtyPrecision = expectationForOneTransaction.getQtyPrecisionToUse();

		final ReceiptQty qv1 = new ReceiptQty(); // used to test #add(BigDecimal, BigDecimal)
		final ReceiptQty qv2 = new ReceiptQty(); // used to test #add(IQtyAndQuality)

		final ReceiptQtyExpectation<Object> expectation = ReceiptQtyExpectation.newInstance()
				.qtyPrecision(qtyPrecision);

		final BigDecimal qualityDiscountPercent = expectationForOneTransaction.getQualityDiscountPercent();
		final BigDecimal qtyToAdd = expectationForOneTransaction.getQty();
		final BigDecimal qtyWithIssuesExpectedForOneTransaction = expectationForOneTransaction.getQtyWithIssues();

		for (int i = 1; i <= 100; i++)
		{
			ErrorMessage message = ErrorMessage.newInstance()
					.addContextInfo("Iteration ", i)
					.addContextInfo("ExpectationForOneTransaction", expectationForOneTransaction)
					.addContextInfo("Values before - QV1: ", qv1)
					.addContextInfo("Values before - QV2: ", qv2);

			//
			// Add to qv1 and qv2
			{
				qv1.addQtyAndQualityDiscountPercent(qtyToAdd, qualityDiscountPercent);

				final ReceiptQty qvToAdd = new ReceiptQty();
				qvToAdd.addQtyAndQualityDiscountPercent(qtyToAdd, qualityDiscountPercent);
				qv2.add(qvToAdd);

				message = message
						.addContextInfo("QtyPrecision", qtyPrecision)
						.addContextInfo("QtyToAdd", qtyToAdd)
						.addContextInfo("QualityDiscountPercent", qualityDiscountPercent)
						.addContextInfo("Values after - QV1: ", qv1)
						.addContextInfo("Values after - QV2: ", qv2);

			}

			final BigDecimal qtyTotalExpected = qtyToAdd
					.multiply(BigDecimal.valueOf(i))
					.setScale(qtyPrecision, RoundingMode.HALF_UP); // mind the rounding mode (this is what we agreed)
			final BigDecimal qtyWithIssuesExpected = qtyWithIssuesExpectedForOneTransaction
					.multiply(BigDecimal.valueOf(i))
					.setScale(qtyPrecision, RoundingMode.HALF_DOWN); // mind the rounding mode (this is what we agreed)
			final BigDecimal qtyWithoutIssuesExpected = qtyTotalExpected.subtract(qtyWithIssuesExpected);
			expectation
					.qty(qtyTotalExpected)
					.qualityDiscountPercent(qualityDiscountPercent) // percent shall be constant ALL the time
					.qtyWithIssues(qtyWithIssuesExpected)
					.qtyWithoutIssues(qtyWithoutIssuesExpected)
					.assertExpected(message.expect("valid qty&quality when adding by qty/percent"), qv1)
					.assertExpected(message.expect("valid qty&quality when adding by " + ReceiptQty.class), qv2);
		}
	}

	@Test
	public void test_copy()
	{
		final ReceiptQty qv = new ReceiptQty();
		qv.addQtyAndQualityDiscountPercent(new BigDecimal("123"), new BigDecimal("10"));

		final ReceiptQty qvCopy = qv.copy();
		Assert.assertThat("Invalid Qty", qvCopy.getQtyTotal(), Matchers.comparesEqualTo(qv.getQtyTotal()));
		Assert.assertThat("Invalid QtyWithIssues", qvCopy.getQtyWithIssuesExact(), Matchers.comparesEqualTo(qv.getQtyWithIssuesExact()));
	}

	@Test
	public void test_getQualityDiscountPercent_zeroQtys()
	{
		final ReceiptQty qv = new ReceiptQty();
		Assert.assertThat("Invalid QualityDiscountPercent", qv.getQualityDiscountPercent(), Matchers.comparesEqualTo(BigDecimal.ZERO));
	}

	@Test
	public void test_getQualityDiscountPercent_QtyTotalIsZero_QtyWithIssuesNotZero()
	{
		final ReceiptQty qv = new ReceiptQty();
		qv.addQtyAndQtyWithIssues(BigDecimal.ZERO, new BigDecimal("123"));

		// NOTE: at the moment we expect to return ZERO and an WARNING shall be logged
		// TBD: throwing an exception in this case...
		Assert.assertThat("Invalid QualityDiscountPercent", qv.getQualityDiscountPercent(), Matchers.comparesEqualTo(BigDecimal.ZERO));
	}

	public static ReceiptQtyExpectation<Object> randomQtyAndQualityExpectation(final int qtyPrecision)
	{
		// Generate random QtyToAdd
		// Avoid having qtyToAdd == 0 because that makes no sense to our test
		BigDecimal qtyToAdd = BigDecimal.ZERO;
		while(qtyToAdd.signum() == 0)
		{
			qtyToAdd = randomBigDecimal(1000, qtyPrecision);
		}

		final BigDecimal qualityDiscountPercent = randomBigDecimal(100, ReceiptQty.QualityDiscountPercent_Precision);
		final BigDecimal qtyWithIssuesExpectedForOneTransactionExact = qtyToAdd.multiply(qualityDiscountPercent)
				.divide(BigDecimal.valueOf(100), ReceiptQty.INTERNAL_PRECISION, RoundingMode.UNNECESSARY);
		return ReceiptQtyExpectation.newInstance()
				.qtyPrecision(qtyPrecision)
				.qty(qtyToAdd)
				.qualityDiscountPercent(qualityDiscountPercent)
				.qtyWithIssues(qtyWithIssuesExpectedForOneTransactionExact);

	}

	public static BigDecimal randomBigDecimal(final int maxValue, final int precision)
	{
		final BigDecimal min = BigDecimal.ZERO;
		final BigDecimal max = BigDecimal.valueOf(maxValue);
		final BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
		return randomBigDecimal.setScale(precision, RoundingMode.HALF_DOWN);
	}
}
