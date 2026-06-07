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

package de.metas.order.paymentschedule.core.service;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderSchedulingContext;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * TDD RED test for AC2: a complex-but-invalid payment term (breaks sum ≠ 100%)
 * must FAIL LOUD instead of silently spreading a wrong DueAmt.
 *
 * <p>Design note: the test is placed in the SAME package as {@link OrderPayScheduleCreateCommand}
 * (package-private class) so we can construct it via its {@code @Builder}.
 * {@link AdempiereTestHelper#init()} registers the {@code PlainTrxManager}, which lets
 * {@link OrderPayScheduleCreateCommand#execute()} run synchronously so exceptions propagate
 * back to the caller.
 *
 * <p>RED condition: today {@code execute0()} guards on {@code isComplexPaymentTerm()} but NOT
 * on {@code paymentTerm.isValid()}.  A single-break (LC 30%) term is {@code isComplex=true}
 * but {@code isValid=false} (30% ≠ 100%).  The command spreads it anyway, calling
 * {@code orderPayScheduleService.create(...)} with {@code DueAmt = 10000} (the sole break
 * absorbs the full remainder).  No exception is raised, so the test assertion
 * {@code assertThatThrownBy(...).isInstanceOf(AdempiereException.class)} FAILS RED.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/30080">me03 #30080</a>
 */
class OrderPayScheduleCreateCommandTest
{
	private static final PaymentTermId PT_ID = PaymentTermId.ofRepoId(5001);
	private static final PaymentTermBreakId LC_BREAK_ID = PaymentTermBreakId.ofRepoId(PT_ID.getRepoId(), 5010);
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(7001);

	@BeforeEach
	void beforeEach()
	{
		// Registers PlainTrxManager so OrderPayScheduleCreateCommand.execute() runs synchronously.
		AdempiereTestHelper.get().init();
	}

	/**
	 * AC2 RED: a complex-but-invalid payment term (single LC 30% break, breaks sum = 30% ≠ 100%)
	 * must cause {@link OrderPayScheduleCreateCommand#execute()} to throw a clear
	 * {@link AdempiereException} naming the invalid term — instead of silently writing
	 * a wrong DueAmt of 10000.
	 *
	 * <p>Today this test FAILS RED because {@code execute0()} does not check
	 * {@code paymentTerm.isValid()} and proceeds to spread, calling
	 * {@code orderPayScheduleService.create(...)} without any exception.
	 */
	@Test
	void invalidComplexTerm_failsLoud()
	{
		// --- Build a complex-but-invalid PaymentTerm: single LC break at 30% ---
		// isComplex=true (has ≥1 break), isValid=false (30% ≠ 100%)
		final PaymentTermBreak lcBreak = PaymentTermBreak.builder()
				.id(LC_BREAK_ID)
				.referenceDateType(ReferenceDateType.LetterOfCreditDate)
				.percent(Percent.of("30"))
				.seqNo(SeqNo.ofInt(10))
				.offsetDays(0)
				.build();

		final PaymentTerm invalidTerm = PaymentTerm.builder()
				.id(PT_ID)
				.clientId(ClientId.SYSTEM)
				.orgId(de.metas.organization.OrgId.ANY)
				.value("pt_lc_invalid")
				.name("pt_lc (invalid — LC 30% only, OD break missing)")
				.breaks(ImmutableList.of(lcBreak))   // single break → isValid=false (30% ≠ 100%)
				.paySchedules(ImmutableList.of())
				.build();

		// Sanity: confirm the term is in the expected "complex-but-invalid" state.
		// These verify the RED precondition — if they fail, the test setup is wrong.
		org.assertj.core.api.Assertions.assertThat(invalidTerm.isComplex()).as("isComplex").isTrue();
		org.assertj.core.api.Assertions.assertThat(invalidTerm.isValid()).as("isValid").isFalse();

		// --- Build the scheduling context (grandTotal = 10000) ---
		final OrderSchedulingContext context = OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.grandTotal(Money.of("10000.00", EUR))
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(invalidTerm)
				.build();

		// --- Mock the service — create() must never be reached ---
		final OrderPayScheduleService orderPayScheduleService = mock(OrderPayScheduleService.class);
		final PaymentTermService paymentTermService = mock(PaymentTermService.class);

		// --- Build the command (package-private @Builder, accessible from this package) ---
		final OrderPayScheduleCreateCommand command = OrderPayScheduleCreateCommand.builder()
				.orderPayScheduleService(orderPayScheduleService)
				.paymentTermService(paymentTermService)
				.context(context)
				.build();

		// --- RED assertion: must throw before spreading the invalid term ---
		// Today: execute0() skips the isValid check → calls create() with DueAmt=10000 → no exception
		// → assertThatThrownBy receives no throwable → TEST FAILS RED (no exception was thrown).
		// GREEN (Task 3): add `if (!paymentTerm.isValid()) throw new AdempiereException(...)` guard
		// in execute0() → exception propagates through PlainTrxManager → test passes GREEN.
		assertThatThrownBy(command::execute)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("pt_lc_invalid");
	}
}
