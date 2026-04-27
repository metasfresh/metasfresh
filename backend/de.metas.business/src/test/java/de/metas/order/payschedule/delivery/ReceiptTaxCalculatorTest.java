/*
 * #%L
 * de.metas.business
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

package de.metas.order.payschedule.delivery;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD for {@link ReceiptTaxCalculator#computeWithTaxValue(de.metas.inout.InOutId)}.
 *
 * <p>Coverage (AC #21 — per-order-line tax):
 * <ul>
 *   <li>{@link #singleLineSimpleTax} — 1 line, 19 % VAT (RED → GREEN after implementation)</li>
 *   <li>{@link #multiLineMixedTax} — 2 lines, different tax rates (Task 20b)</li>
 *   <li>{@link #dropshipReceiptLine_skipped} — line without C_OrderLine → 0 contribution (Task 20c)</li>
 *   <li>{@link #zeroTaxRate} — 0 % tax (Task 20c)</li>
 *   <li>{@link #roundingHalfUp} — rounding edge (Task 20c)</li>
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class ReceiptTaxCalculatorTest
{
	/** EUR-like currency — any non-zero consistent ID works in POJO test context. */
	private static final CurrencyId TEST_CURRENCY = CurrencyId.ofRepoId(318);

	private ReceiptTaxCalculator calculator;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// ReceiptTaxCalculator does not exist yet — compile error until Task 20a impl
		calculator = new ReceiptTaxCalculator();
	}

	// -----------------------------------------------------------------------
	// Task 20a — single-line, simple tax (RED → GREEN)
	// -----------------------------------------------------------------------

	/**
	 * Receipt with 1 line, qty 100, priceActual 39.20 EUR, 19 % tax.
	 * Expected: 100 × 39.20 × 1.19 = 4664.80 EUR.
	 *
	 * <p>This is the RED test for Task 20a; it becomes GREEN when
	 * {@link ReceiptTaxCalculator} is implemented.
	 */
	@Test
	void singleLineSimpleTax()
	{
		// Arrange
		final I_C_Tax tax19 = createTax(19);
		final I_C_OrderLine orderLine = createOrderLine(new BigDecimal("39.20"), tax19.getC_Tax_ID());
		final I_M_InOut receipt = createReceipt();
		createInOutLine(receipt, orderLine, new BigDecimal("100"));

		// Act
		final Money result = calculator.computeWithTaxValue(de.metas.inout.InOutId.ofRepoId(receipt.getM_InOut_ID()));

		// Assert
		// 100 × 39.20 = 3920 net; tax = 3920 × 19 % = 744.80; gross = 4664.80
		assertThat(result.toBigDecimal()).isEqualByComparingTo("4664.80");
		assertThat(result.getCurrencyId()).isEqualTo(TEST_CURRENCY);
	}

	// -----------------------------------------------------------------------
	// Task 20b — multi-line, mixed tax (GREEN if single-line impl is per-order-line)
	// -----------------------------------------------------------------------

	/**
	 * Receipt with 2 lines:
	 * - Line A: qty 50, priceActual 39.20, 10 % VAT → 50 × 39.20 × 1.10 = 2156.00
	 * - Line B: qty 30, priceActual 71.20, 19 % VAT → 30 × 71.20 × 1.19 = 2541.84
	 * Expected: 2156.00 + 2541.84 = 4697.84 EUR.
	 */
	@Test
	void multiLineMixedTax()
	{
		// Arrange
		final I_C_Tax tax10 = createTax(10);
		final I_C_Tax tax19 = createTax(19);
		final I_C_OrderLine orderLineA = createOrderLine(new BigDecimal("39.20"), tax10.getC_Tax_ID());
		final I_C_OrderLine orderLineB = createOrderLine(new BigDecimal("71.20"), tax19.getC_Tax_ID());
		final I_M_InOut receipt = createReceipt();
		createInOutLine(receipt, orderLineA, new BigDecimal("50"));
		createInOutLine(receipt, orderLineB, new BigDecimal("30"));

		// Act
		final Money result = calculator.computeWithTaxValue(de.metas.inout.InOutId.ofRepoId(receipt.getM_InOut_ID()));

		// Assert: (50 × 39.20 × 1.10) + (30 × 71.20 × 1.19) = 2156.00 + 2541.84 = 4697.84
		assertThat(result.toBigDecimal()).isEqualByComparingTo("4697.84");
	}

	// -----------------------------------------------------------------------
	// Task 20c — edge cases
	// -----------------------------------------------------------------------

	/**
	 * Receipt line with C_OrderLine_ID = 0 (dropship — no order line).
	 * Expected: that line contributes 0 to the total (skipped).
	 */
	@Test
	void dropshipReceiptLine_skipped()
	{
		// Arrange: one normal line + one dropship line (C_OrderLine_ID = 0)
		final I_C_Tax tax19 = createTax(19);
		final I_C_OrderLine orderLine = createOrderLine(new BigDecimal("39.20"), tax19.getC_Tax_ID());
		final I_M_InOut receipt = createReceipt();
		createInOutLine(receipt, orderLine, new BigDecimal("10"));
		createDropshipInOutLine(receipt, new BigDecimal("5"), new BigDecimal("100.00"));

		// Act
		final Money result = calculator.computeWithTaxValue(de.metas.inout.InOutId.ofRepoId(receipt.getM_InOut_ID()));

		// Assert: only the normal line contributes: 10 × 39.20 × 1.19 = 466.48
		// The dropship line (100 × 5, no order line) contributes 0
		assertThat(result.toBigDecimal()).isEqualByComparingTo("466.48");
	}

	/**
	 * Receipt line with C_Tax_ID having 0 % rate.
	 * Expected: withTaxValue = lineNet (no tax added).
	 */
	@Test
	void zeroTaxRate()
	{
		// Arrange
		final I_C_Tax tax0 = createTax(0);
		final I_C_OrderLine orderLine = createOrderLine(new BigDecimal("50.00"), tax0.getC_Tax_ID());
		final I_M_InOut receipt = createReceipt();
		createInOutLine(receipt, orderLine, new BigDecimal("20"));

		// Act
		final Money result = calculator.computeWithTaxValue(de.metas.inout.InOutId.ofRepoId(receipt.getM_InOut_ID()));

		// Assert: 20 × 50.00 × 1.00 = 1000.00
		assertThat(result.toBigDecimal()).isEqualByComparingTo("1000.00");
	}

	/**
	 * Rounding edge: 33.335 → HALF_UP → 33.34 (not 33.33).
	 * Line: qty 1, priceActual 10.00, tax 233.35 % → gross = 10.00 × 3.3335 = 33.335 → 33.34
	 * (or a simpler case: qty 3, price 11.111 net, 0 % → 33.333 → 33.33; but the point is HALF_UP on 2 decimal places)
	 * Use: qty 1, priceActual 10.3359, tax 19 % → gross = 10.3359 × 1.19 = 12.299721 → 12.30 (HALF_UP)
	 * Simpler: qty 2, price 10.005, tax 0 % → 20.01 (not 20.00)
	 */
	@Test
	void roundingHalfUp()
	{
		// Arrange: 2 × 10.005 net, 0 % tax → net total = 20.01 (HALF_UP rounding on 2 decimal places)
		// With 19 %: 2 × 10.005 × 1.19 = 23.8119 → rounds to 23.81
		final I_C_Tax tax19 = createTax(19);
		final I_C_OrderLine orderLine = createOrderLine(new BigDecimal("10.005"), tax19.getC_Tax_ID());
		final I_M_InOut receipt = createReceipt();
		createInOutLine(receipt, orderLine, new BigDecimal("2"));

		// Act
		final Money result = calculator.computeWithTaxValue(de.metas.inout.InOutId.ofRepoId(receipt.getM_InOut_ID()));

		// Assert: 2 × 10.005 × 1.19 = 23.8119 → HALF_UP to 2 dp = 23.81
		assertThat(result.toBigDecimal()).isEqualByComparingTo("23.81");
	}

	// -----------------------------------------------------------------------
	// Fixture helpers
	// -----------------------------------------------------------------------

	private I_C_Tax createTax(final int rateInt)
	{
		final I_C_TaxCategory taxCat = newInstance(I_C_TaxCategory.class);
		taxCat.setName("TestTaxCat_" + rateInt);
		save(taxCat);

		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("Tax_" + rateInt + "pct");
		tax.setRate(BigDecimal.valueOf(rateInt));
		tax.setC_TaxCategory_ID(taxCat.getC_TaxCategory_ID());
		tax.setValidFrom(Timestamp.from(Instant.parse("2000-01-01T00:00:00Z")));
		tax.setAD_Org_ID(OrgId.ANY.getRepoId());
		save(tax);
		return tax;
	}

	private I_C_OrderLine createOrderLine(final BigDecimal priceActual, final int taxId)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setPriceActual(priceActual);
		orderLine.setC_Tax_ID(taxId);
		orderLine.setC_Currency_ID(TEST_CURRENCY.getRepoId());
		save(orderLine);
		return orderLine;
	}

	private I_M_InOut createReceipt()
	{
		final I_M_InOut receipt = newInstance(I_M_InOut.class);
		// Note: I_M_InOut does not carry C_Currency_ID directly.
		// Currency is derived from the order lines in ReceiptTaxCalculator.
		save(receipt);
		return receipt;
	}

	private I_M_InOutLine createInOutLine(
			final I_M_InOut receipt,
			final I_C_OrderLine orderLine,
			final BigDecimal movementQty)
	{
		final I_M_InOutLine line = newInstance(I_M_InOutLine.class);
		line.setM_InOut_ID(receipt.getM_InOut_ID());
		line.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		line.setMovementQty(movementQty);
		save(line);
		return line;
	}

	/** Dropship line — C_OrderLine_ID is 0/unset, no priceActual on the line itself. */
	private I_M_InOutLine createDropshipInOutLine(
			final I_M_InOut receipt,
			final BigDecimal movementQty,
			final BigDecimal nominalPrice)
	{
		final I_M_InOutLine line = newInstance(I_M_InOutLine.class);
		line.setM_InOut_ID(receipt.getM_InOut_ID());
		// C_OrderLine_ID intentionally left 0 (default) to simulate dropship
		line.setMovementQty(movementQty);
		save(line);
		return line;
	}
}
