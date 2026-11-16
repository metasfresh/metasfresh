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

package de.metas.order.paymentschedule.steps.material_receipt;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceipt;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ReceiptValueCalculator#computeValue(MaterialReceipt)}.
 *
 * <p>Covers:
 * <ul>
 *   <li>{@link #singleLineSingleTax} — 1 line, 19% VAT, full qty delivered.</li>
 *   <li>{@link #mixedTaxOrder_perOrderLineTaxApplied} — 2 lines with different tax rates; verifies per-order-line tax (AC #21).</li>
 *   <li>{@link #partialQty_proratedByMovementQtyOverQtyOrdered} — partial delivery prorated by movementQty/qtyOrdered.</li>
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class ReceiptValueCalculatorTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(9001);
	private static final LocalDate MOVEMENT_DATE = LocalDate.of(2026, 3, 15);

	private I_C_UOM uom;
	private IOrderBL orderBL;
	private IOrderLineBL orderLineBL;
	private MoneyService moneyService;
	private ReceiptValueCalculator calculator;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		orderBL = mock(IOrderBL.class);
		orderLineBL = mock(IOrderLineBL.class);
		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IOrderLineBL.class, orderLineBL);

		moneyService = mock(MoneyService.class);
		when(moneyService.getStdPrecision(any(CurrencyId.class))).thenReturn(CurrencyPrecision.TWO);

		calculator = ReceiptValueCalculator.builder()
				.moneyService(moneyService)
				.orderBL(orderBL)
				.orderLineBL(orderLineBL)
				.build();
	}

	// -----------------------------------------------------------------------
	// Test 1 — single line, single tax rate
	// -----------------------------------------------------------------------

	/**
	 * Receipt with 1 line, order line priceActual=1000, qty=10, tax=19%.
	 * Expected: lineGrossAmt = 1000 × 10 × 1.19 = 11900.00 EUR.
	 * Full delivery (movementQty == qtyOrdered) → returns lineGrossAmt directly.
	 */
	@Test
	void singleLineSingleTax()
	{
		// Arrange
		final OrderAndLineId orderLineId = OrderAndLineId.ofRepoIds(ORDER_ID.getRepoId(), 101);
		final Money lineGrossAmt = Money.of("11900.00", EUR);
		final Quantity qtyOrdered = qty("10");
		final Quantity qtyReceived = qty("10");

		stubOrderLine(orderLineId, lineGrossAmt, qtyOrdered);

		final MaterialReceipt receipt = buildReceipt(
				InOutId.ofRepoId(8001),
				ImmutableList.of(
						buildLine(InOutLineId.ofRepoId(8101), orderLineId, qtyReceived)));

		// Act
		final Optional<Money> result = calculator.computeValue(receipt);

		// Assert: full delivery → lineGrossAmt returned as-is = 11900.00
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualByComparingTo(Money.of("11900.00", EUR));
	}

	// -----------------------------------------------------------------------
	// Test 2 — mixed-tax order, per-order-line tax applied (AC #21 receipt-side)
	// -----------------------------------------------------------------------

	/**
	 * Receipt with 2 lines tied to 2 order lines with DIFFERENT tax rates.
	 * <ul>
	 *   <li>Line A: lineGrossAmt = 1000 × 10 × 1.10 = 11000.00</li>
	 *   <li>Line B: lineGrossAmt = 500  × 20 × 1.20 = 12000.00</li>
	 * </ul>
	 * Expected total = 23000.00.  Verifies per-order-line tax rather than order-aggregate tax.
	 */
	@Test
	void mixedTaxOrder_perOrderLineTaxApplied()
	{
		// Arrange
		final OrderAndLineId orderLineIdA = OrderAndLineId.ofRepoIds(ORDER_ID.getRepoId(), 101);
		final OrderAndLineId orderLineIdB = OrderAndLineId.ofRepoIds(ORDER_ID.getRepoId(), 102);

		// Line A: 1000 × 10 × 1.10 = 11000.00
		final Money grossAmtA = Money.of("11000.00", EUR);
		final Quantity qtyA = qty("10");

		// Line B: 500 × 20 × 1.20 = 12000.00
		final Money grossAmtB = Money.of("12000.00", EUR);
		final Quantity qtyB = qty("20");

		// Stub IOrderBL.getLinesByIds to return one mock per id
		final Map<OrderAndLineId, I_C_OrderLine> linesMap = new HashMap<>();
		final I_C_OrderLine olA = mock(I_C_OrderLine.class);
		final I_C_OrderLine olB = mock(I_C_OrderLine.class);
		linesMap.put(orderLineIdA, olA);
		linesMap.put(orderLineIdB, olB);
		when(orderBL.getLinesByIds(any())).thenReturn(linesMap);

		when(orderLineBL.getLineGrossAmt(olA)).thenReturn(grossAmtA);
		when(orderLineBL.getQtyOrdered(olA)).thenReturn(qtyA);
		when(orderLineBL.getLineGrossAmt(olB)).thenReturn(grossAmtB);
		when(orderLineBL.getQtyOrdered(olB)).thenReturn(qtyB);

		final MaterialReceipt receipt = buildReceipt(
				InOutId.ofRepoId(8001),
				ImmutableList.of(
						buildLine(InOutLineId.ofRepoId(8101), orderLineIdA, qty("10")),
						buildLine(InOutLineId.ofRepoId(8102), orderLineIdB, qty("20"))));

		// Act
		final Optional<Money> result = calculator.computeValue(receipt);

		// Assert: 11000.00 + 12000.00 = 23000.00
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualByComparingTo(Money.of("23000.00", EUR));
	}

	// -----------------------------------------------------------------------
	// Test 3 — partial qty prorated by movementQty / qtyOrdered
	// -----------------------------------------------------------------------

	/**
	 * Order line: priceActual=1000, qtyOrdered=10, lineGrossAmt=1000×10×1.19=11900.00.
	 * Receipt line: movementQty=4 (partial).
	 * Expected: lineGrossAmt × (4/10) = 11900.00 × 0.4 = 4760.00.
	 */
	@Test
	void partialQty_proratedByMovementQtyOverQtyOrdered()
	{
		// Arrange
		final OrderAndLineId orderLineId = OrderAndLineId.ofRepoIds(ORDER_ID.getRepoId(), 101);
		final Money lineGrossAmt = Money.of("11900.00", EUR);
		final Quantity qtyOrdered = qty("10");
		final Quantity qtyReceived = qty("4");  // partial

		stubOrderLine(orderLineId, lineGrossAmt, qtyOrdered);

		final MaterialReceipt receipt = buildReceipt(
				InOutId.ofRepoId(8001),
				ImmutableList.of(
						buildLine(InOutLineId.ofRepoId(8101), orderLineId, qtyReceived)));

		// Act
		final Optional<Money> result = calculator.computeValue(receipt);

		// Assert: 11900.00 × (4/10) = 4760.00
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualByComparingTo(Money.of("4760.00", EUR));
	}

	// -----------------------------------------------------------------------
	// Fixture helpers
	// -----------------------------------------------------------------------

	/** Stubs IOrderBL.getLinesByIds + IOrderLineBL for a single order line. */
	private void stubOrderLine(
			final OrderAndLineId orderLineId,
			final Money lineGrossAmt,
			final Quantity qtyOrdered)
	{
		final I_C_OrderLine orderLine = mock(I_C_OrderLine.class);
		when(orderBL.getLinesByIds(any())).thenAnswer(inv -> {
			final Map<OrderAndLineId, I_C_OrderLine> map = new HashMap<>();
			map.put(orderLineId, orderLine);
			return map;
		});
		when(orderLineBL.getLineGrossAmt(orderLine)).thenReturn(lineGrossAmt);
		when(orderLineBL.getQtyOrdered(orderLine)).thenReturn(qtyOrdered);
	}

	private MaterialReceipt buildReceipt(final InOutId id, final ImmutableList<MaterialReceipt.Line> lines)
	{
		return MaterialReceipt.builder()
				.id(id)
				.orderId(ORDER_ID)
				.movementDate(MOVEMENT_DATE)
				.lines(lines)
				.build();
	}

	private MaterialReceipt.Line buildLine(
			final InOutLineId lineId,
			final OrderAndLineId orderLineId,
			final Quantity movementQty)
	{
		return MaterialReceipt.Line.builder()
				.id(lineId)
				.orderLineId(orderLineId)
				.movementQty(movementQty)
				.build();
	}

	private Quantity qty(final String value)
	{
		return Quantity.of(new BigDecimal(value), uom);
	}
}
