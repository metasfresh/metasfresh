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

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.payschedule.delivery.OrderPayScheduleDeliveryRepository.DesiredDeliveryRow;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.X_C_OrderPaySchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Property-based JUnit for {@link OrderPayScheduleDeliveryService}.
 *
 * <p>Truth table: §3 of REQUIREMENTS.md (AC #3, #5, #19, #22).
 * Each test cell provides {@link DeliveryStepInputs} and asserts the resulting
 * set of {@link DesiredDeliveryRow} objects computed by the service.
 *
 * <p><strong>RED state</strong>: {@link OrderPayScheduleDeliveryService} does not exist yet —
 * this file will fail to compile until Task 21/22 implement it.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class OrderPayScheduleDeliveryServiceTest
{
	// -----------------------------------------------------------------------
	// Constants — canonical example (REQUIREMENTS.md §1 + AC #1, #3)
	// -----------------------------------------------------------------------

	/** EUR currency ID used in all test fixtures. */
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);

	/** Canonical PO grand total from the customer spreadsheet. */
	private static final Money ORDER_GRAND_TOTAL = Money.of("68654.40", EUR);

	/** LC percentage: 30 %. */
	private static final BigDecimal LC_PERCENT = new BigDecimal("30");

	/** Delivery percentage: 70 %. */
	private static final BigDecimal DELIVERY_PERCENT = new BigDecimal("70");

	/** R1 with-tax value from the customer spreadsheet. */
	private static final Money R1_VALUE = Money.of("31808.00", EUR);

	/** R2 value such that R1 + R2 = 70,000 > 68,654.40 (over-delivery). */
	private static final Money R2_VALUE = Money.of("38192.00", EUR); // 70000 − 31808

	/** R2 value in a pure-under-delivery scenario (R1 + R2 = 50,000 < 68,654.40). */
	private static final Money R2_UNDER_VALUE = Money.of("30000.00", EUR);

	/** Remainder when only R1 received: 68654.40 − 31808 = 36846.40. */
	private static final Money REMAINDER_AFTER_R1_BASE = Money.of("36846.40", EUR);

	/** R1 DueAmt = 31808.00 × 70 % = 22265.60. */
	private static final Money R1_DUE = Money.of("22265.60", EUR);

	/** Remainder DueAmt after R1 = 36846.40 × 70 % = 25792.48. */
	private static final Money REMAINDER_DUE_AFTER_R1 = Money.of("25792.48", EUR);

	// -----------------------------------------------------------------------
	// Service under test
	// -----------------------------------------------------------------------

	private OrderPayScheduleDeliveryService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// OrderPayScheduleDeliveryService does not exist yet — this line causes RED compile
		service = new OrderPayScheduleDeliveryService(new OrderPayScheduleDeliveryRepository());
	}

	// -----------------------------------------------------------------------
	// Test case DSL
	// -----------------------------------------------------------------------

	/**
	 * A single parameterised test case.
	 * Each cell specifies inputs ({@link DeliveryStepInputs}) and expected output rows.
	 */
	@Value
	@Builder
	static class TestCase
	{
		@NonNull String name;
		@NonNull DeliveryStepInputs inputs;
		@NonNull @Singular List<ExpectedRow> expectedRows;

		@Override
		public String toString()
		{
			return name;
		}
	}

	/**
	 * Expected state of one {@link DesiredDeliveryRow} — only the fields under test.
	 */
	@Value
	@Builder
	static class ExpectedRow
	{
		@NonNull BigDecimal baseAmt;
		@NonNull BigDecimal dueAmt;
		@NonNull String status;   // X_C_OrderPaySchedule.STATUS_* constants
		@Nullable InOutId mInOutId;
		@Nullable InvoiceId cInvoiceId;
	}

	// -----------------------------------------------------------------------
	// Fixture helpers
	// -----------------------------------------------------------------------

	private static final OrderId DUMMY_ORDER_ID = OrderId.ofRepoId(9001);
	private static final InOutId R1_ID = InOutId.ofRepoId(101);
	private static final InOutId R2_ID = InOutId.ofRepoId(102);
	private static final InvoiceId INV1_ID = InvoiceId.ofRepoId(201);
	private static final InvoiceId INV2_ID = InvoiceId.ofRepoId(202);

	/**
	 * Builds a base {@link DeliveryStepInputs.DeliveryStepInputsBuilder} with canonical constants.
	 * Callers add completedReceipts and set proformaPrepaymentPaymentId as needed.
	 */
	private static DeliveryStepInputs.DeliveryStepInputsBuilder baseInputs()
	{
		return DeliveryStepInputs.builder()
				.orderId(DUMMY_ORDER_ID)
				.orderGrandTotal(ORDER_GRAND_TOTAL)
				.lcPercent(LC_PERCENT)
				.deliveryPercent(DELIVERY_PERCENT)
				.completedReceipts(new ArrayList<>());
	}

	private static DeliveryStepInputs.ReceiptInfo r1NoInvoice()
	{
		return DeliveryStepInputs.ReceiptInfo.builder()
				.mInOutId(R1_ID)
				.withTaxValue(R1_VALUE)
				.build();
	}

	private static DeliveryStepInputs.ReceiptInfo r1WithPartialCOInvoice()
	{
		return DeliveryStepInputs.ReceiptInfo.builder()
				.mInOutId(R1_ID)
				.withTaxValue(R1_VALUE)
				.matchedInvoiceId(INV1_ID)
				.build();
	}

	private static DeliveryStepInputs.ReceiptInfo r2NoInvoice()
	{
		return DeliveryStepInputs.ReceiptInfo.builder()
				.mInOutId(R2_ID)
				.withTaxValue(R2_VALUE)
				.build();
	}

	// -----------------------------------------------------------------------
	// Matrix cells — 3 starter cells (Task 19a)
	// -----------------------------------------------------------------------

	static Stream<TestCase> matrixCells()
	{
		final List<TestCase> cells = new ArrayList<>();

		// ----------------------------------------------------------------
		// Cell 1 — noReceipts
		// AC #1: no receipts → 1 remainder row only, BaseAmt = order.GrandTotal, Pending
		// ----------------------------------------------------------------
		cells.add(TestCase.builder()
				.name("noReceipts")
				.inputs(baseInputs()
						.build())
				.expectedRow(ExpectedRow.builder()
						.baseAmt(new BigDecimal("68654.40"))
						.dueAmt(new BigDecimal("48058.08"))   // 68654.40 × 70 %
						.status(X_C_OrderPaySchedule.STATUS_Pending_Ref)
						.mInOutId(null)
						.cInvoiceId(null)
						.build())
				.build());

		// ----------------------------------------------------------------
		// Cell 2 — R1_only_noInvoice
		// AC #3: 1 receipt, no invoice → 1 sub-row (Pending) + 1 remainder row (Pending)
		// ----------------------------------------------------------------
		cells.add(TestCase.builder()
				.name("R1_only_noInvoice")
				.inputs(baseInputs()
						.completedReceipt(r1NoInvoice())
						.build())
				.expectedRow(ExpectedRow.builder()
						.baseAmt(new BigDecimal("31808.00"))
						.dueAmt(new BigDecimal("22265.60"))   // 31808 × 70 %
						.status(X_C_OrderPaySchedule.STATUS_Pending_Ref)
						.mInOutId(R1_ID)
						.cInvoiceId(null)
						.build())
				.expectedRow(ExpectedRow.builder()
						.baseAmt(new BigDecimal("36846.40"))   // 68654.40 − 31808
						.dueAmt(new BigDecimal("25792.48"))    // 36846.40 × 70 %
						.status(X_C_OrderPaySchedule.STATUS_Pending_Ref)
						.mInOutId(null)
						.cInvoiceId(null)
						.build())
				.build());

		// ----------------------------------------------------------------
		// Cell 3 — R1_INV1_CO_Partial
		// AC #5: 1 receipt + Partial CO invoice → sub-row Awaiting_Pay + remainder Pending
		// ----------------------------------------------------------------
		cells.add(TestCase.builder()
				.name("R1_INV1_CO_Partial")
				.inputs(baseInputs()
						.completedReceipt(r1WithPartialCOInvoice())
						.build())
				.expectedRow(ExpectedRow.builder()
						.baseAmt(new BigDecimal("31808.00"))
						.dueAmt(new BigDecimal("22265.60"))
						.status(X_C_OrderPaySchedule.STATUS_Awaiting_Pay)
						.mInOutId(R1_ID)
						.cInvoiceId(INV1_ID)
						.build())
				.expectedRow(ExpectedRow.builder()
						.baseAmt(new BigDecimal("36846.40"))
						.dueAmt(new BigDecimal("25792.48"))
						.status(X_C_OrderPaySchedule.STATUS_Pending_Ref)
						.mInOutId(null)
						.cInvoiceId(null)
						.build())
				.build());

		return cells.stream();
	}

	// -----------------------------------------------------------------------
	// Parameterised test runner
	// -----------------------------------------------------------------------

	@ParameterizedTest(name = "{0}")
	@MethodSource("matrixCells")
	void computeDesired_matchesTruthTable(@NonNull final TestCase tc)
	{
		// OrderPayScheduleDeliveryService.computeDesired does not exist yet → RED compile
		final List<DesiredDeliveryRow> actual = service.computeDesired(tc.getInputs());

		final SoftAssertions sa = new SoftAssertions();
		sa.assertThat(actual).as("row count for test case '%s'", tc.getName())
				.hasSameSizeAs(tc.getExpectedRows());

		final int checkCount = Math.min(actual.size(), tc.getExpectedRows().size());
		for (int i = 0; i < checkCount; i++)
		{
			final DesiredDeliveryRow row = actual.get(i);
			final ExpectedRow exp = tc.getExpectedRows().get(i);
			sa.assertThat(row.getBaseAmt()).as("[%d].baseAmt", i).isEqualByComparingTo(exp.getBaseAmt());
			sa.assertThat(row.getDueAmt()).as("[%d].dueAmt", i).isEqualByComparingTo(exp.getDueAmt());
			sa.assertThat(row.getStatus()).as("[%d].status", i).isEqualTo(exp.getStatus());
			sa.assertThat(row.getMInOutId()).as("[%d].mInOutId", i).isEqualTo(exp.getMInOutId());
			sa.assertThat(row.getCInvoiceId()).as("[%d].cInvoiceId", i).isEqualTo(exp.getCInvoiceId());
		}
		sa.assertAll();
	}
}
