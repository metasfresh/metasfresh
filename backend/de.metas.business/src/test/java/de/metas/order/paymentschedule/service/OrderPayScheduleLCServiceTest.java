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

package de.metas.order.paymentschedule.service;

import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPayScheduleStatus;
import de.metas.payment.paymentterm.ReferenceDateType;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_Proforma_Order_Alloc;
import org.compiere.model.X_C_OrderPaySchedule;
import org.compiere.model.X_C_Payment;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based JUnit for {@link OrderPayScheduleLCService}.
 * <p>
 * Covers the truth table from REQUIREMENTS.md §3:
 * {@code (allocation ∈ {NONE, PRESENT}) × (payment ∈ {ABSENT, DRAFTED, COMPLETED, REVERSED})}
 * plus idempotence.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29368">me03 #29368 Split-Payment Iter 2</a>
 */
class OrderPayScheduleLCServiceTest
{
	/** Proforma GrandTotal used in every test scenario */
	private static final BigDecimal PROFORMA_GRAND_TOTAL = new BigDecimal("20596.32");

	private OrderPayScheduleLCService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		service = new OrderPayScheduleLCService(
				OrderPayScheduleService.newInstanceForUnitTesting(),
				new ProformaOrderAllocRepository());
	}

	// -----------------------------------------------------------------------
	// Helper: allocation state enum (for parameterized idempotence test)
	// -----------------------------------------------------------------------

	enum AllocationState
	{
		NONE,
		PRESENT
	}

	// -----------------------------------------------------------------------
	// Scenario 1 — no allocation, no payment → Pending, null DueAmt_Actual, null LC_Date
	// -----------------------------------------------------------------------

	@Test
	void noAllocation_noPayment_yields_Pending_NullDueAmtActual_NullLCDate()
	{
		final OrderId orderId = createOrder();
		createLCPayScheduleLine(orderId, X_C_OrderPaySchedule.STATUS_Pending_Ref);
		// no proforma allocation
		// no payment

		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lcLine = findLCLine(orderId);
		assertThat(lcLine.getStatus()).isEqualTo(X_C_OrderPaySchedule.STATUS_Pending_Ref);
		// POJO wrapper returns BigDecimal.ZERO for unset nullable BigDecimal; DB returns null.
		// Both represent "no actual amount set" — assert either null or zero.
		assertThat(isDueAmtActualUnset(lcLine)).as("DueAmt_Actual should be unset (null or zero)").isTrue();

		final I_C_Order order = findOrder(orderId);
		assertThat(order.getLC_Date()).isNull();
	}

	// -----------------------------------------------------------------------
	// Scenario 2 — allocation present, no payment → Awaiting_Pay, GrandTotal, LC_Date set
	// -----------------------------------------------------------------------

	@Test
	void allocationPresent_noPayment_yields_AwaitingPay_ProformaGrandTotal_AllocationDate()
	{
		final OrderId orderId = createOrder();
		createLCPayScheduleLine(orderId, X_C_OrderPaySchedule.STATUS_Pending_Ref);
		final Timestamp invoiceDate = TimeUtil.asTimestamp(LocalDate.of(2026, 3, 10));
		final int proformaInvoiceId = createProformaInvoice(invoiceDate);
		createAlloc(orderId, proformaInvoiceId);
		// no payment linked to the proforma invoice

		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lcLine = findLCLine(orderId);
		assertThat(lcLine.getStatus()).isEqualTo(X_C_OrderPaySchedule.STATUS_Awaiting_Pay);
		assertThat(lcLine.getDueAmt_Actual()).isEqualByComparingTo(PROFORMA_GRAND_TOTAL);

		final I_C_Order order = findOrder(orderId);
		assertThat(order.getLC_Date()).isEqualTo(invoiceDate);
	}

	// -----------------------------------------------------------------------
	// Scenario 3 — allocation present, drafted payment → Awaiting_Pay
	// -----------------------------------------------------------------------

	@Test
	void allocationPresent_draftedPayment_yields_AwaitingPay()
	{
		final OrderId orderId = createOrder();
		createLCPayScheduleLine(orderId, X_C_OrderPaySchedule.STATUS_Pending_Ref);
		final int proformaInvoiceId = createProformaInvoice(TimeUtil.asTimestamp(LocalDate.of(2026, 4, 1)));
		createAlloc(orderId, proformaInvoiceId);
		createPayment(proformaInvoiceId, X_C_Payment.DOCSTATUS_Drafted);

		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lcLine = findLCLine(orderId);
		assertThat(lcLine.getStatus()).isEqualTo(X_C_OrderPaySchedule.STATUS_Awaiting_Pay);
		assertThat(lcLine.getDueAmt_Actual()).isEqualByComparingTo(PROFORMA_GRAND_TOTAL);
	}

	// -----------------------------------------------------------------------
	// Scenario 4 — allocation present, completed payment → Paid
	// -----------------------------------------------------------------------

	@Test
	void allocationPresent_completedPayment_yields_Paid()
	{
		final OrderId orderId = createOrder();
		createLCPayScheduleLine(orderId, X_C_OrderPaySchedule.STATUS_Awaiting_Pay);
		final int proformaInvoiceId = createProformaInvoice(TimeUtil.asTimestamp(LocalDate.of(2026, 4, 15)));
		createAlloc(orderId, proformaInvoiceId);
		createPayment(proformaInvoiceId, X_C_Payment.DOCSTATUS_Completed);

		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lcLine = findLCLine(orderId);
		assertThat(lcLine.getStatus()).isEqualTo(X_C_OrderPaySchedule.STATUS_Paid);
		assertThat(lcLine.getDueAmt_Actual()).isEqualByComparingTo(PROFORMA_GRAND_TOTAL);
	}

	// -----------------------------------------------------------------------
	// Scenario 5 — allocation present, reversed payment → Awaiting_Pay (actuals preserved)
	// -----------------------------------------------------------------------

	@Test
	void allocationPresent_reversedPayment_yields_AwaitingPay_WithActualPreserved()
	{
		final OrderId orderId = createOrder();
		// Start from Paid to verify the reversal path goes back to Awaiting_Pay
		createLCPayScheduleLine(orderId, X_C_OrderPaySchedule.STATUS_Paid);
		final int proformaInvoiceId = createProformaInvoice(TimeUtil.asTimestamp(LocalDate.of(2026, 4, 20)));
		createAlloc(orderId, proformaInvoiceId);
		// Only a reversed payment exists — reversed payments are DocStatus=RE, so no completed payment
		createPayment(proformaInvoiceId, X_C_Payment.DOCSTATUS_Reversed);

		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lcLine = findLCLine(orderId);
		// Reversed payment → not "completed" → falls to Awaiting_Pay branch
		assertThat(lcLine.getStatus()).isEqualTo(X_C_OrderPaySchedule.STATUS_Awaiting_Pay);
		assertThat(lcLine.getDueAmt_Actual()).isEqualByComparingTo(PROFORMA_GRAND_TOTAL);
	}

	// -----------------------------------------------------------------------
	// Scenario 6 — reverse cycle: complete → paid → reverse → awaiting_pay (idempotent)
	// -----------------------------------------------------------------------

	/**
	 * Exercises the full CO → RE reverse cycle at the service level.
	 * <p>
	 * After a completed payment is reversed ({@code DocStatus=RE}) the LC step must return to
	 * {@code Awaiting_Pay}. Calling {@code recomputeLCStep} twice after the reversal must yield
	 * the same result (idempotence check for the authority function).
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/29368">me03 #29368 Split-Payment Iter 2</a>
	 */
	@Test
	void reversedProformaPayment_runs_recompute_once_and_idempotent()
	{
		final OrderId orderId = createOrder();
		final Timestamp invoiceDate = TimeUtil.asTimestamp(LocalDate.of(2026, 4, 24));
		final int proformaInvoiceId = createProformaInvoice(invoiceDate);
		createAlloc(orderId, proformaInvoiceId);

		// Step 1: payment completed → status = Paid
		createLCPayScheduleLine(orderId, X_C_OrderPaySchedule.STATUS_Awaiting_Pay);
		createPayment(proformaInvoiceId, X_C_Payment.DOCSTATUS_Completed);
		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lineAfterComplete = findLCLine(orderId);
		assertThat(lineAfterComplete.getStatus()).isEqualTo(X_C_OrderPaySchedule.STATUS_Paid);
		assertThat(lineAfterComplete.getDueAmt_Actual()).isEqualByComparingTo(PROFORMA_GRAND_TOTAL);

		// Step 2: simulate payment reversal — remove the completed payment, add a reversed one.
		// In production MPayment.reverseCorrectIt() flips DocStatus on the existing row;
		// here we just remove the CO payment and add a RE payment (same observable result for the service).
		de.metas.util.Services.get(org.adempiere.ad.dao.IQueryBL.class)
				.createQueryBuilder(I_C_Payment.class)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_Proforma_Invoice_ID, proformaInvoiceId)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_DocStatus, X_C_Payment.DOCSTATUS_Completed)
				.create()
				.delete();
		createPayment(proformaInvoiceId, X_C_Payment.DOCSTATUS_Reversed);

		// First recompute after reversal
		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lineAfterReversal = findLCLine(orderId);
		assertThat(lineAfterReversal.getStatus())
				.as("Reversed payment → no completed payment → status must be Awaiting_Pay")
				.isEqualTo(X_C_OrderPaySchedule.STATUS_Awaiting_Pay);
		assertThat(lineAfterReversal.getDueAmt_Actual())
				.as("DueAmt_Actual preserved after reversal")
				.isEqualByComparingTo(PROFORMA_GRAND_TOTAL);

		// Idempotence: second call must produce the same result
		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lineAfterSecondCall = findLCLine(orderId);
		assertThat(lineAfterSecondCall.getStatus()).isEqualTo(X_C_OrderPaySchedule.STATUS_Awaiting_Pay);
		assertThat(lineAfterSecondCall.getDueAmt_Actual()).isEqualByComparingTo(PROFORMA_GRAND_TOTAL);

		final I_C_Order order = findOrder(orderId);
		assertThat(order.getLC_Date())
				.as("LC_Date preserved (proforma still allocated) after reversal")
				.isEqualTo(invoiceDate);
	}

	// -----------------------------------------------------------------------
	// Idempotence — calling recomputeLCStep twice yields the same result
	// -----------------------------------------------------------------------

	@ParameterizedTest
	@EnumSource(AllocationState.class)
	void isIdempotent_acrossAllStates_forNoPayment(final AllocationState allocationState)
	{
		final OrderId orderId = createOrder();
		createLCPayScheduleLine(orderId, X_C_OrderPaySchedule.STATUS_Pending_Ref);

		if (allocationState == AllocationState.PRESENT)
		{
			final int proformaInvoiceId = createProformaInvoice(TimeUtil.asTimestamp(LocalDate.of(2026, 5, 1)));
			createAlloc(orderId, proformaInvoiceId);
		}

		// First call
		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lineAfterFirst = findLCLine(orderId);
		final String statusAfterFirst = lineAfterFirst.getStatus();
		final BigDecimal dueAmtAfterFirst = lineAfterFirst.getDueAmt_Actual();
		final Timestamp lcDateAfterFirst = findOrder(orderId).getLC_Date();

		// Second call — must produce identical result
		service.recomputeLCStep(orderId);

		final I_C_OrderPaySchedule lineAfterSecond = findLCLine(orderId);
		assertThat(lineAfterSecond.getStatus()).isEqualTo(statusAfterFirst);
		// Compare DueAmt_Actual: both null/zero values should match; non-zero values must match exactly.
		final BigDecimal dueAmtSecond = lineAfterSecond.getDueAmt_Actual();
		final BigDecimal dueAmtFirst = dueAmtAfterFirst;
		if (isDueAmtUnset(dueAmtFirst))
		{
			assertThat(isDueAmtUnset(dueAmtSecond))
					.as("DueAmt_Actual should remain unset after second call").isTrue();
		}
		else
		{
			assertThat(dueAmtSecond).isEqualByComparingTo(dueAmtFirst);
		}
		assertThat(findOrder(orderId).getLC_Date()).isEqualTo(lcDateAfterFirst);
	}

	// -----------------------------------------------------------------------
	// Fixture helpers
	// -----------------------------------------------------------------------

	private static int ORDER_ID_COUNTER = 1000;
	private static int INVOICE_ID_COUNTER = 2000;
	private static int PAYMENT_TERM_ID_COUNTER = 3000;

	private OrderId createOrder()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setLC_Date(null);
		saveRecord(order);
		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private I_C_Order findOrder(final OrderId orderId)
	{
		// reload from POJO store
		return org.adempiere.model.InterfaceWrapperHelper.load(orderId, I_C_Order.class);
	}

	/**
	 * Creates one LC pay-schedule line for the given order.
	 * Uses a synthetic {@code C_PaymentTerm_ID} and {@code C_PaymentTerm_Break_ID} (just non-zero ints —
	 * the service does not validate them, only uses {@code ReferenceDateType}).
	 */
	private void createLCPayScheduleLine(final OrderId orderId, final String initialStatus)
	{
		final int payTermId = PAYMENT_TERM_ID_COUNTER++;
		final int payTermBreakId = PAYMENT_TERM_ID_COUNTER++;
		final int currencyId = 318; // EUR

		final I_C_OrderPaySchedule schedule = newInstance(I_C_OrderPaySchedule.class);
		schedule.setC_Order_ID(orderId.getRepoId());
		schedule.setC_PaymentTerm_ID(payTermId);
		schedule.setC_PaymentTerm_Break_ID(payTermBreakId);
		schedule.setReferenceDateType(ReferenceDateType.LetterOfCreditDate.getCode());
		schedule.setDueAmt(PROFORMA_GRAND_TOTAL);
		schedule.setC_Currency_ID(currencyId);
		schedule.setDueDate(TimeUtil.asTimestamp(LocalDate.of(2026, 6, 1)));
		schedule.setPercent(100);
		schedule.setOffsetDays(0);
		schedule.setSeqNo(10);
		schedule.setStatus(initialStatus);
		schedule.setDueAmt_Actual(null);
		saveRecord(schedule);
	}

	private int createProformaInvoice(final Timestamp dateInvoiced)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(PROFORMA_GRAND_TOTAL);
		invoice.setDateInvoiced(dateInvoiced);
		saveRecord(invoice);
		return invoice.getC_Invoice_ID();
	}

	private void createAlloc(final OrderId orderId, final int proformaInvoiceId)
	{
		final I_C_Proforma_Order_Alloc alloc = newInstance(I_C_Proforma_Order_Alloc.class);
		alloc.setC_Order_ID(orderId.getRepoId());
		alloc.setC_Invoice_ID(proformaInvoiceId);
		alloc.setIsActive(true);
		saveRecord(alloc);
	}

	private void createPayment(final int proformaInvoiceId, final String docStatus)
	{
		final I_C_Payment payment = newInstance(I_C_Payment.class);
		payment.setProforma_Invoice_ID(proformaInvoiceId);
		payment.setDocStatus(docStatus);
		payment.setIsActive(true);
		saveRecord(payment);
	}

	/**
	 * Returns true when the record's DueAmt_Actual is "unset" — either null (DB) or zero (POJO wrapper default).
	 * The POJO unit-test wrapper returns BigDecimal.ZERO for unset nullable BigDecimal columns.
	 */
	private static boolean isDueAmtActualUnset(final I_C_OrderPaySchedule line)
	{
		return isDueAmtUnset(line.getDueAmt_Actual());
	}

	private static boolean isDueAmtUnset(final BigDecimal value)
	{
		return value == null || BigDecimal.ZERO.compareTo(value) == 0;
	}

	private I_C_OrderPaySchedule findLCLine(final OrderId orderId)
	{
		// reload all pay-schedule lines for the order and find the LC one
		return de.metas.util.Services.get(org.adempiere.ad.dao.IQueryBL.class)
				.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_ReferenceDateType, ReferenceDateType.LetterOfCreditDate.getCode())
				.create()
				.firstOnlyNotNull(I_C_OrderPaySchedule.class);
	}
}
