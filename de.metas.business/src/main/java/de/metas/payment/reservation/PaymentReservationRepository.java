package de.metas.payment.reservation;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.OrgId;
import org.compiere.model.I_C_Payment_Reservation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.PaymentRule;
import de.metas.util.Services;
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
	public Optional<PaymentReservation> getBySalesOrderIdNotVoided(final OrderId salesOrderId)
	{
		final I_C_Payment_Reservation record = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Payment_Reservation.class)
				.addEqualsFilter(I_C_Payment_Reservation.COLUMN_C_Order_ID, salesOrderId)
				// .addNotEqualsFilter(I_C_Payment_Reservation.COLUMN_Status, PaymentReservationStatus.VOIDED) // TODO
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_Payment_Reservation.class);

		return toOptionalPaymentReservation(record);
	}

	public void save(@NonNull final PaymentReservation paymentReservation)
	{
		final I_C_Payment_Reservation record;
		if (paymentReservation.getId() == null)
		{
			record = newInstance(I_C_Payment_Reservation.class);
		}
		else
		{
			record = load(paymentReservation.getId(), I_C_Payment_Reservation.class);
		}

		record.setAD_Org_ID(paymentReservation.getOrgId().getRepoId());
		record.setAmount(paymentReservation.getAmount().getAsBigDecimal());
		record.setC_Currency_ID(paymentReservation.getAmount().getCurrencyId().getRepoId());
		record.setC_Order_ID(paymentReservation.getSalesOrderId().getRepoId());
		record.setDateTrx(TimeUtil.asTimestamp(paymentReservation.getDateTrx()));
		record.setPaymentRule(paymentReservation.getPaymentRule().getCode());
		record.setStatus(paymentReservation.getStatus().getCode());

		saveRecord(record);
		paymentReservation.setId(PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID()));
	}

	private static Optional<PaymentReservation> toOptionalPaymentReservation(@Nullable final I_C_Payment_Reservation record)
	{
		return record != null
				? Optional.of(toPaymentReservation(record))
				: Optional.empty();
	}

	private static PaymentReservation toPaymentReservation(@NonNull final I_C_Payment_Reservation record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return PaymentReservation.builder()
				.id(PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.amount(Money.of(record.getAmount(), currencyId))
				.salesOrderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.dateTrx(TimeUtil.asLocalDate(record.getDateTrx()))
				.paymentRule(PaymentRule.ofCode(record.getPaymentRule()))
				.status(PaymentReservationStatus.ofCode(record.getStatus()))
				.build();
	}
}
