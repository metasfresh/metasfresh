package de.metas.order.payment_reservation;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.PaymentRule;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.payment.reservation.PaymentReservationCreateRequest;
import de.metas.payment.reservation.PaymentReservationService;
import de.metas.payment.reservation.PaymentReservationStatus;
import de.metas.util.Check;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class OrderPaymentReservationService
{
	private final PaymentReservationService paymentReservationService;

	public OrderPaymentReservationService(
			@NonNull final PaymentReservationService paymentReservationService)
	{
		this.paymentReservationService = paymentReservationService;
	}

	public OrderPaymentReservationCreateResult createPaymentReservationIfNeeded(@NonNull final I_C_Order salesOrder)
	{
		Check.assume(salesOrder.isSOTrx(), "expected sales order but got purchase order: {}", salesOrder);

		final PaymentRule paymentRule = PaymentRule.ofCode(salesOrder.getPaymentRule());
		if (!paymentReservationService.isPaymentReservationRequired(paymentRule))
		{
			return OrderPaymentReservationCreateResult.NOT_NEEDED;
		}

		final OrderId salesOrderId = OrderId.ofRepoId(salesOrder.getC_Order_ID());
		final PaymentReservation existingPaymentReservation = paymentReservationService
				.getSalesOrderReservation(salesOrderId)
				.orElse(null);

		final PaymentReservation paymentReservation;
		if (existingPaymentReservation == null)
		{
			paymentReservation = paymentReservationService.create(PaymentReservationCreateRequest.builder()
					.orgId(OrgId.ofRepoId(salesOrder.getAD_Org_ID()))
					.amount(extractGrandTotal(salesOrder))
					.salesOrderId(salesOrderId)
					.dateTrx(SystemTime.asLocalDate())
					.paymentRule(paymentRule)
					.build());

		}
		else
		{
			paymentReservation = existingPaymentReservation;
		}

		//
		// Result based on payment reservation's status
		final PaymentReservationStatus paymentReservationStatus = paymentReservation.getStatus();
		if (PaymentReservationStatus.APPROVED.equals(paymentReservationStatus))
		{
			return OrderPaymentReservationCreateResult.ALREADY_APPROVED;
		}
		else if (PaymentReservationStatus.WAITING_PAYER_APPROVAL.equals(paymentReservationStatus))
		{
			return OrderPaymentReservationCreateResult.WAITING_FOR_APPROVAL;
		}
		else
		{
			throw new AdempiereException("Invalid payment reservation status: " + paymentReservationStatus)
					.setParameter("paymentReservation", paymentReservation)
					.setParameter("salesOrderId", salesOrderId);
		}
	}

	private static Money extractGrandTotal(final I_C_Order salesOrder)
	{
		return Money.of(salesOrder.getGrandTotal(), CurrencyId.ofRepoId(salesOrder.getC_Currency_ID()));
	}

}
