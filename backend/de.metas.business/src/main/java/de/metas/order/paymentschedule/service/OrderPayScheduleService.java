/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAlloc;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.order.paymentschedule.OrderSchedulingContext;
import de.metas.order.paymentschedule.repository.OrderPayScheduleRepository;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final OrderPayScheduleRepository orderPayScheduleRepository;
	@NonNull private final PaymentTermService paymentTermService;

	public static OrderPayScheduleService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();

		return new OrderPayScheduleService(
				new OrderPayScheduleRepository(),
				new PaymentTermService()
		);
	}

	public void createOrderPaySchedules(final I_C_Order order)
	{
		OrderPayScheduleCreateCommand.builder()
				.orderPayScheduleService(this)
				.paymentTermService(paymentTermService)
				.orderRecord(order)
				.build()
				.execute();
	}

	public void updatePayScheduleStatus(@NonNull final I_C_Order order)
	{
		updatePayScheduleStatus(order, false);
	}

	public void updatePayScheduleStatus(@NonNull final I_C_Order order, final boolean updateAmounts)
	{
		final OrderSchedulingContext context = extractContext(order);
		if (context == null)
		{
			return;
		}

		orderPayScheduleRepository.updateById(context.getOrderId(), orderPaySchedule -> orderPaySchedule.updateStatusFromContext(context, updateAmounts));
	}

	public void updatePayScheduleStatusesBeforeCommit(@NonNull final OrderId orderId)
	{
		trxManager.accumulateAndProcessBeforeCommit(
				"OrderPayScheduleService.updatePayScheduleStatusesBeforeCommit",
				Collections.singleton(orderId),
				orderIds -> updatePayScheduleStatusesNow(ImmutableSet.copyOf(orderIds))
		);
	}

	private void updatePayScheduleStatusesNow(@NonNull final Set<OrderId> orderIds)
	{
		if (orderIds.isEmpty()) {return;}

		final ImmutableMap<OrderId, I_C_Order> ordersById = Maps.uniqueIndex(
				orderBL.getByIds(orderIds),
				order -> OrderId.ofRepoId(order.getC_Order_ID())
		);

		orderPayScheduleRepository.updateByIds(
				ordersById.keySet(),
				orderPaySchedule -> {
					final I_C_Order order = ordersById.get(orderPaySchedule.getOrderId());
					final OrderSchedulingContext context = extractContext(order);
					if (context == null)
					{
						return;
					}

					orderPaySchedule.updateStatusFromContext(context);
				});
	}

	public void markAsPaid(@NonNull final OrderId orderId, @NonNull final OrderPayScheduleId orderPayScheduleId)
	{
		orderPayScheduleRepository.updateById(orderId, orderPaySchedule -> orderPaySchedule.markAsPaid(orderPayScheduleId));
	}

	@NonNull
	public Optional<OrderPaySchedule> getByOrderId(@NonNull final OrderId orderId) {return orderPayScheduleRepository.getByOrderId(orderId);}

	public void deleteByOrderId(@NonNull final OrderId orderId) {orderPayScheduleRepository.deleteByOrderId(orderId);}

	@Nullable OrderSchedulingContext extractContext(final @NotNull org.compiere.model.I_C_Order orderRecord)
	{
		final Money grandTotal = orderBL.getGrandTotal(orderRecord);
		if (grandTotal.isZero())
		{
			return null;
		}

		final PaymentTermId paymentTermId = orderBL.getPaymentTermId(orderRecord);
		final PaymentTerm paymentTerm = paymentTermService.getById(paymentTermId);
		if (!paymentTerm.isComplex())
		{
			return null;
		}

		// Check if order has Proforma allocation and get the invoice amount for LC schedules
		final Money proformaAmount = getProformaInvoiceAmount(OrderId.ofRepoId(orderRecord.getC_Order_ID()));

		return OrderSchedulingContext.builder()
				.orderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.orderDate(TimeUtil.asLocalDate(orderRecord.getDateOrdered()))
				.letterOfCreditDate(TimeUtil.asLocalDate(orderRecord.getLC_Date()))
				.billOfLadingDate(TimeUtil.asLocalDate(orderRecord.getBLDate()))
				.ETADate(TimeUtil.asLocalDate(orderRecord.getETA()))
				.invoiceDate(TimeUtil.asLocalDate(orderRecord.getInvoiceDate()))
				.grandTotal(grandTotal)
				.proformaAmount(proformaAmount)
				.precision(orderBL.getAmountPrecision(orderRecord))
				.paymentTerm(paymentTerm)
				.build();
	}

	@Nullable
	private Money getProformaInvoiceAmount(@NonNull final OrderId orderId)
	{
		final ProformaOrderAllocRepository proformaOrderAllocRepository = SpringContextHolder.instance.getBean(ProformaOrderAllocRepository.class);
		final ImmutableList<ProformaOrderAlloc> proformaAllocs = proformaOrderAllocRepository.getByOrderId(orderId);

		if (proformaAllocs.isEmpty())
		{
			return null;
		}
		Check.assumeEquals(proformaAllocs.size(), 1, "Only one proforma allocation should exist for order: {}", orderId.getRepoId());

		final InvoiceId proformaInvoiceId = proformaAllocs.get(0).getInvoiceId();
		final I_C_Invoice proformaInvoice = Services.get(IInvoiceBL.class).getById(proformaInvoiceId);

		return Money.of(proformaInvoice.getGrandTotal(), CurrencyId.ofRepoId(proformaInvoice.getC_Currency_ID()));
	}

	public void create(@NonNull final OrderPayScheduleCreateRequest request)
	{
		orderPayScheduleRepository.create(request);
	}
}
