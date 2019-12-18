package de.metas.inoutcandidate.spi.impl;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.NonNull;

public class MutableQtyAndQualityTest
{
	private ProductId productId;
	private UomId uomId;

	@Before
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM stockUomRecord = newInstance(I_C_UOM.class);
		saveRecord(stockUomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());
	}

	/**
	 * Tests {@link ReceiptQty#addQtyAndQualityDiscountPercent(StockQtyAndUOMQty, Percent) and {@link ReceiptQty#add(IQtyAndQuality)}.
	 */
	@Test
	public void test_add_sameQty_ExpectConstantPercent_SimpleTest01()
	{
		final ReceiptQtyExpectation<Object> expectationForOneTransaction = ReceiptQtyExpectation.newInstance()
				.qty(StockQtyAndUOMQtys.create(new BigDecimal("8"), productId, new BigDecimal("7"), uomId)) // note that at the end of the day, we care for the uomQty, i.e. the potential catch quantity
				.qualityDiscountPercent("3")
				.qtyWithIssues(StockQtyAndUOMQtys.create(new BigDecimal("0.24"), productId, new BigDecimal("0.21"), uomId)) // 3% of 8=0.24 resp. 3% of 7=0.21
		;
		test_add_sameQty_ExpectConstantPercent(expectationForOneTransaction);
	}

	@Test
	public void test_add_sameQty_ExpectConstantPercent_SimpleTest02()
	{
		final ReceiptQtyExpectation<Object> expectationForOneTransaction = ReceiptQtyExpectation.newInstance()
				.qty(StockQtyAndUOMQtys.create(new BigDecimal("8"), productId, new BigDecimal("437.35"), uomId))
				.qualityDiscountPercent("93.18")
				.qtyWithIssues(StockQtyAndUOMQtys.create(new BigDecimal("7.4544"), productId, new BigDecimal("407.52273"), uomId)) // = 437.35 * 93.18% = 407.52273
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
				final ReceiptQtyExpectation<Object> expectationForOneTransaction = randomQtyAndQualityExpectation(productId, uomId, qtyPrecision);
				test_add_sameQty_ExpectConstantPercent(expectationForOneTransaction);
			}
		}

	}

	private void test_add_sameQty_ExpectConstantPercent(@NonNull final ReceiptQtyExpectation<?> expectationForOneTransaction)
	{
		final ReceiptQty qv1 = ReceiptQty.newWithCatchWeight(productId, uomId); // used to test #add(BigDecimal, BigDecimal)
		final ReceiptQty qv2 = ReceiptQty.newWithCatchWeight(productId, uomId); // used to test #add(IQtyAndQuality)

		final ReceiptQtyExpectation<Object> expectation = ReceiptQtyExpectation.newInstance();

		final Percent qualityDiscountPercent = expectationForOneTransaction.getQualityDiscountPercent();
		final StockQtyAndUOMQty qtyToAdd = expectationForOneTransaction.getQty();
		final StockQtyAndUOMQty qtyWithIssuesExpectedForOneTransaction = expectationForOneTransaction.getQtyWithIssues();

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

				final ReceiptQty qvToAdd = ReceiptQty.newWithCatchWeight(productId, uomId);
				qvToAdd.addQtyAndQualityDiscountPercent(qtyToAdd, qualityDiscountPercent);
				qv2.add(qvToAdd);

				message = message
						.addContextInfo("QtyToAdd", qtyToAdd)
						.addContextInfo("QualityDiscountPercent", qualityDiscountPercent)
						.addContextInfo("Values after - QV1: ", qv1)
						.addContextInfo("Values after - QV2: ", qv2);

			}

			final StockQtyAndUOMQty qtyTotalExpected = qtyToAdd
					.multiply(BigDecimal.valueOf(i));
			final StockQtyAndUOMQty qtyWithIssuesExpected = qtyWithIssuesExpectedForOneTransaction
					.multiply(BigDecimal.valueOf(i));

			final StockQtyAndUOMQty qtyWithoutIssuesExpected = qtyTotalExpected.subtract(qtyWithIssuesExpected);
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
		final ReceiptQty qv = ReceiptQty.newWithCatchWeight(productId, uomId);

		qv.addQtyAndQualityDiscountPercent(
				StockQtyAndUOMQtys.create(new BigDecimal("23"), productId, new BigDecimal("123"), uomId),
				Percent.of("10"));

		final ReceiptQty qvCopy = qv.copy();
		assertThat(qvCopy.getQtyTotal()).isEqualTo(qv.getQtyTotal());
		assertThat(qvCopy.getQtyWithIssuesExact()).isEqualTo(qv.getQtyWithIssuesExact());
	}

	@Test
	public void test_getQualityDiscountPercent_zeroQtys()
	{
		final ReceiptQty qv = ReceiptQty.newWithCatchWeight(productId, uomId);
		Assert.assertThat("Invalid QualityDiscountPercent", qv.getQualityDiscountPercent().toBigDecimal(), Matchers.comparesEqualTo(ZERO));
	}

	@Test
	public void test_getQualityDiscountPercent_QtyTotalIsZero_QtyWithIssuesNotZero()
	{
		final ReceiptQty qv = ReceiptQty.newWithCatchWeight(productId, uomId);

		qv.addQtyAndQtyWithIssues(
				StockQtyAndUOMQtys.createZero(productId, uomId),
				StockQtyAndUOMQtys.create(new BigDecimal("23"), productId, new BigDecimal("123"), uomId));

		// NOTE: at the moment we expect to return ZERO and an WARNING shall be logged
		// TBD: throwing an exception in this case...
		Assert.assertThat("Invalid QualityDiscountPercent", qv.getQualityDiscountPercent().toBigDecimal(), Matchers.comparesEqualTo(BigDecimal.ZERO));
	}

	public static ReceiptQtyExpectation<Object> randomQtyAndQualityExpectation(
			final ProductId productId,
			final UomId uomId,
			final int qtyPrecision)
	{
		// Generate random QtyToAdd
		// Avoid having qtyToAdd == 0 because that makes no sense to our test
		StockQtyAndUOMQty qtyToAdd = StockQtyAndUOMQtys.createZero(productId, uomId);
		while (qtyToAdd.getStockQty().signum() == 0 || qtyToAdd.getUOMQtyNotNull().signum() == 0)
		{
			qtyToAdd = StockQtyAndUOMQtys.create(randomBigDecimal(1000, qtyPrecision), productId, randomBigDecimal(1000, qtyPrecision), uomId);
		}

		final Percent qualityDiscountPercent = Percent.of(randomBigDecimal(100, ReceiptQty.QualityDiscountPercent_Precision));
		final StockQtyAndUOMQty qtyWithIssuesExpectedForOneTransactionExact = qtyToAdd
				.multiply(qualityDiscountPercent.toBigDecimal())
				.divide(BigDecimal.valueOf(100), ReceiptQty.INTERNAL_PRECISION, RoundingMode.UNNECESSARY);

		return ReceiptQtyExpectation.newInstance()
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
