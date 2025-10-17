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

package de.metas.order.paymentschedule;

import de.metas.adempiere.model.I_C_Order;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermConstants;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.api.ShipperTransportationReference;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleService
{
	@NonNull private final OrderPayScheduleRepository orderPayScheduleRepository;
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

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

	public void updatePayScheduleStatus(@NonNull final OrderId orderId)
	{
		final OrderSchedulingContext context = extractContext(orderBL.getById(orderId));
		if (context == null)
		{
			return;
		}

		orderPayScheduleRepository.updateById(orderId, orderPaySchedule -> orderPaySchedule.updateStatusFromContext(context));
	}

	@NonNull
	public Optional<OrderPaySchedule> getByOrderId(@NonNull final OrderId orderId) {return orderPayScheduleRepository.getByOrderId(orderId);}

	public void deleteByOrderId(@NonNull final OrderId orderId) {orderPayScheduleRepository.deleteByOrderId(orderId);}

	@Nullable OrderSchedulingContext extractContext(final @NotNull org.compiere.model.I_C_Order orderRecord)
	{
		final PaymentTermId paymentTermId = orderBL.getPaymentTermId(orderRecord);
		final PaymentTerm paymentTerm = paymentTermService.getById(paymentTermId);
		if (!paymentTerm.isComplex())
		{
			return null;
		}
		if (paymentTermService.hasPaySchedule(paymentTermId))
		{
			throw new AdempiereException(PaymentTermConstants.MSG_ComplexTermConflict)
					.appendParametersToMessage()
					.setParameter("PaymentTerm", paymentTerm.getName());
		}

		final Money grandTotal = orderBL.getGrandTotal(orderRecord);
		if (grandTotal.isZero())
		{
			return null;
		}

		final OrderId orderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());
		final Optional<ShipperTransportationReference> shipperTransportationReference = shipperTransportationDAO.getEarliestShipperTransportationByOrderId(orderId);
		final DatesFromShipper shipperDates = extractShipperDates(shipperTransportationReference);

		return OrderSchedulingContext.builder()
				.orderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.orderDate(TimeUtil.asInstant(orderRecord.getDateOrdered()))
				.letterOfCreditDate(TimeUtil.asInstant(orderRecord.getLC_Date()))
				.billOfLadingDate(shipperDates.getBillOfLadingDate())
				.ETADate(shipperDates.getEtaDate())
				.invoiceDate(invoiceBL.getUniqueInvoiceDateForOrderId(orderId))
				.grandTotal(grandTotal)
				.precision(orderBL.getAmountPrecision(orderRecord))
				.paymentTerm(paymentTerm)
				.build();
	}

	public void create(@NonNull final OrderPayScheduleCreateRequest request)
	{
		orderPayScheduleRepository.create(request);
	}

	@Value(staticConstructor = "of")
	private static class DatesFromShipper
	{
		@Getter @Nullable Instant billOfLadingDate;
		@Getter @Nullable Instant etaDate;
	}

	private static DatesFromShipper extractShipperDates(@NonNull final Optional<ShipperTransportationReference> shipperTransportationReference)
	{
		return shipperTransportationReference.map(ref -> DatesFromShipper.of(
				ref.getBillOfLadingDate(),
				ref.getETADate()
		)).orElseGet(() -> DatesFromShipper.of(null, null));
	}
}
