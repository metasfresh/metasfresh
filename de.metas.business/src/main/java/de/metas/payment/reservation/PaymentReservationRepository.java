package de.metas.payment.reservation;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.service.OrgId;
import org.compiere.model.I_C_Payment_Reservation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.processor.PaymentProcessorType;
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

@Repository
public class PaymentReservationRepository
{
	public PaymentReservation saveNew(@NonNull final PaymentReservationCreateRequest request)
	{
		final I_C_Payment_Reservation record = newInstance(I_C_Payment_Reservation.class);
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setAmount(request.getAmount().getAsBigDecimal());
		record.setC_Currency_ID(request.getAmount().getCurrencyId().getRepoId());
		record.setC_Order_ID(request.getOrderId().getRepoId());
		record.setDateTrx(TimeUtil.asTimestamp(request.getDateTrx()));
		record.setPaymentProcessorType(request.getProcessorType().getCode());

		saveRecord(record);

		return toPaymentReservation(record);
	}

	private static PaymentReservation toPaymentReservation(final I_C_Payment_Reservation record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return PaymentReservation.builder()
				.id(PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.amount(Money.of(record.getAmount(), currencyId))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.dateTrx(TimeUtil.asLocalDate(record.getDateTrx()))
				.processorType(PaymentProcessorType.ofCode(record.getPaymentProcessorType()))
				.build();
	}
}
