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

package de.metas.order.paymentschedule.core.repository;

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Round-trip test for {@link OrderPayScheduleLoaderAndSaver}: proves that
 * {@code updateRecord} writes every field that {@code fromRecord} reads.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class OrderPayScheduleLoaderAndSaverTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);

	private OrderPayScheduleRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
		repository = new OrderPayScheduleRepository();
	}

	/**
	 * Saves an {@link OrderPayScheduleLine} with BaseAmount, M_InOut_ID, and C_Invoice_ID columns set,
	 * reloads it by order, and asserts all three values survived the round-trip.
	 * <p>
	 * Before the fix, {@code updateRecord} was not calling
	 * {@code record.setBaseAmt}, {@code record.setM_InOut_ID}, or
	 * {@code record.setC_Invoice_ID}, so the reloaded line had null/0 values.
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
	 */
	@Test
	void roundTrip_preservesBaseAmountInoutAndInvoice()
	{
		// --- arrange ---
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(1001);
		final PaymentTermBreakId breakId = PaymentTermBreakId.ofRepoId(paymentTermId, 1002);

		final Money baseAmount = Money.of(BigDecimal.valueOf(68654.40), EUR);
		final InOutId inoutId = InOutId.ofRepoId(1234);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(5678);

		final OrderPayScheduleLine line = OrderPayScheduleLine.builder()
				.orderId(orderId)
				.paymentTermBreakId(breakId)
				.referenceDateType(ReferenceDateType.OrderDate)
				.percent(Percent.of(100))
				.offsetDays(0)
				.status(OrderPayScheduleStatus.Pending)
				.dueDate(LocalDate.of(2026, 6, 1))
				.baseAmount(baseAmount)
				.dueAmount(Money.of(BigDecimal.valueOf(68654.40), EUR))
				.inoutId(inoutId)
				.invoiceId(invoiceId)
				.build();

		final OrderPaySchedule schedule = OrderPaySchedule.ofList(orderId, Collections.singletonList(line));

		// --- act: save ---
		repository.save(schedule);

		// --- act: reload ---
		final Optional<OrderPaySchedule> reloaded = repository.getByOrderId(orderId);
		assertThat(reloaded).isPresent();
		final OrderPayScheduleLine reloadedLine = reloaded.get().getLines().get(0);

		// --- assert iter-3 columns round-tripped ---
		assertThat(reloadedLine.getBaseAmount())
				.as("baseAmount must survive the round-trip")
				.isEqualTo(baseAmount);

		assertThat(reloadedLine.getInoutId())
				.as("inoutId must survive the round-trip")
				.isEqualTo(inoutId);

		assertThat(reloadedLine.getInvoiceId())
				.as("invoiceId must survive the round-trip")
				.isEqualTo(invoiceId);
	}

	/**
	 * Saves an {@link OrderPayScheduleLine} without inoutId and invoiceId
	 * (LC row scenario: no shipment or invoice yet),
	 * reloads it by order, and asserts both fields are null.
	 * <p>
	 * Exercises the null-path in {@code updateRecord} where both IDs are converted to 0
	 * and then reloaded as null via {@code ofRepoIdOrNull}.
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
	 */
	@Test
	void roundTrip_nullableFKsRoundTripAsNull()
	{
		// --- arrange ---
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(1001);
		final PaymentTermBreakId breakId = PaymentTermBreakId.ofRepoId(paymentTermId, 1002);

		final Money baseAmount = Money.of(BigDecimal.valueOf(42000.00), EUR);

		final OrderPayScheduleLine line = OrderPayScheduleLine.builder()
				.orderId(orderId)
				.paymentTermBreakId(breakId)
				.referenceDateType(ReferenceDateType.OrderDate)
				.percent(Percent.of(100))
				.offsetDays(0)
				.status(OrderPayScheduleStatus.Pending)
				.dueDate(LocalDate.of(2026, 6, 1))
				.baseAmount(baseAmount)
				.dueAmount(Money.of(BigDecimal.valueOf(42000.00), EUR))
				// inoutId and invoiceId are NOT set (null)
				.build();

		final OrderPaySchedule schedule = OrderPaySchedule.ofList(orderId, Collections.singletonList(line));

		// --- act: save ---
		repository.save(schedule);

		// --- act: reload ---
		final Optional<OrderPaySchedule> reloaded = repository.getByOrderId(orderId);
		assertThat(reloaded).isPresent();
		final OrderPayScheduleLine reloadedLine = reloaded.get().getLines().get(0);

		// --- assert nullable FKs round-trip as null ---
		assertThat(reloadedLine.getBaseAmount())
				.as("baseAmount must survive the round-trip even when FKs are null")
				.isEqualTo(baseAmount);

		assertThat(reloadedLine.getInoutId())
				.as("inoutId must be null (LC row scenario)")
				.isNull();

		assertThat(reloadedLine.getInvoiceId())
				.as("invoiceId must be null (LC row scenario)")
				.isNull();
	}
}
