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
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * Verifies that {@link OrderPayScheduleCreateCommand} fails loudly when given a
 * complex-but-invalid payment term (breaks sum ≠ 100%), rather than silently producing
 * a wrong {@code DueAmt}.
 *
 * <p>The test class lives in the same package as {@link OrderPayScheduleCreateCommand}
 * (package-private) so it can construct it via its {@code @Builder}.
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
	 * A complex-but-invalid payment term (single LC 30% break, breaks sum = 30% ≠ 100%)
	 * must cause {@link OrderPayScheduleCreateCommand#execute()} to throw a clear
	 * {@link AdempiereException} naming the invalid term — not silently write a wrong DueAmt.
	 */
	@Test
	void invalidComplexTerm_failsLoud()
	{
		// Complex-but-invalid: single LC break at 30% — isComplex=true, isValid=false (30% ≠ 100%)
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
				.orgId(OrgId.ANY)
				.value("pt_lc_invalid")
				.name("pt_lc (invalid — LC 30% only, OD break missing)")
				.breaks(ImmutableList.of(lcBreak))   // single break → isValid=false (30% ≠ 100%)
				.paySchedules(ImmutableList.of())
				.build();

		// Precondition: confirm the term is in the expected "complex-but-invalid" state.
		assertThat(invalidTerm.isComplex()).as("isComplex").isTrue();
		assertThat(invalidTerm.isValid()).as("isValid").isFalse();

		final OrderSchedulingContext context = OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.grandTotal(Money.of("10000.00", EUR))
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(invalidTerm)
				.build();

		final OrderPayScheduleService orderPayScheduleService = mock(OrderPayScheduleService.class);

		final OrderPayScheduleCreateCommand command = OrderPayScheduleCreateCommand.builder()
				.orderPayScheduleService(orderPayScheduleService)
				.context(context)
				.build();

		assertThatThrownBy(command::execute)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("pt_lc_invalid");
	}
}
