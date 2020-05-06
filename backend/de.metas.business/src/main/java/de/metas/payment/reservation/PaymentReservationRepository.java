package de.metas.payment.reservation;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Payment_Reservation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerContactId;
import de.metas.email.EMailAddress;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
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
	public PaymentReservation getById(@NonNull final PaymentReservationId id)
	{
		final I_C_Payment_Reservation record = load(id, I_C_Payment_Reservation.class);
		if (record == null)
		{
			throw new AdempiereException("@NotFound@ @C_Payment_Reservation_ID@: " + id);
		}
		return toPaymentReservation(record);
	}

	public Optional<PaymentReservation> getBySalesOrderIdNotVoided(@NonNull final OrderId salesOrderId)
	{
		final I_C_Payment_Reservation record = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Payment_Reservation.class)
				.addEqualsFilter(I_C_Payment_Reservation.COLUMN_C_Order_ID, salesOrderId)
				.addNotEqualsFilter(I_C_Payment_Reservation.COLUMN_Status, PaymentReservationStatus.VOIDED)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_Payment_Reservation.class);
		if (record == null)
		{
			return Optional.empty();
		}

		final PaymentReservation reservation = toPaymentReservation(record);
		return Optional.of(reservation);
	}

	public void save(@NonNull final PaymentReservation reservation)
	{
		final I_C_Payment_Reservation record = reservation.getId() != null
				? load(reservation.getId(), I_C_Payment_Reservation.class)
				: newInstance(I_C_Payment_Reservation.class);

		updateReservationRecord(record, reservation);

		saveRecord(record);
		reservation.setId(PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID()));
	}

	private static void updateReservationRecord(
			@NonNull final I_C_Payment_Reservation record,
			@NonNull final PaymentReservation from)
	{
		InterfaceWrapperHelper.setValue(record, "AD_Client_ID", from.getClientId().getRepoId());
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setAmount(from.getAmount().toBigDecimal());
		record.setBill_BPartner_ID(from.getPayerContactId().getBpartnerId().getRepoId());
		record.setBill_User_ID(from.getPayerContactId().getUserId().getRepoId());
		record.setBill_EMail(from.getPayerEmail().getAsString());
		record.setC_Currency_ID(from.getAmount().getCurrencyId().getRepoId());
		record.setC_Order_ID(from.getSalesOrderId().getRepoId());
		record.setDateTrx(TimeUtil.asTimestamp(from.getDateTrx()));
		record.setPaymentRule(from.getPaymentRule().getCode());
		record.setStatus(from.getStatus().getCode());
	}

	private static PaymentReservation toPaymentReservation(@NonNull final I_C_Payment_Reservation record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return PaymentReservation.builder()
				.id(PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.amount(Money.of(record.getAmount(), currencyId))
				.payerContactId(BPartnerContactId.ofRepoId(record.getBill_BPartner_ID(), record.getBill_User_ID()))
				.payerEmail(EMailAddress.ofString(record.getBill_EMail()))
				.salesOrderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.dateTrx(TimeUtil.asLocalDate(record.getDateTrx()))
				.paymentRule(PaymentRule.ofCode(record.getPaymentRule()))
				.status(PaymentReservationStatus.ofCode(record.getStatus()))
				.build();
	}
}
