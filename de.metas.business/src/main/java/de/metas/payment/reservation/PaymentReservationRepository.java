package de.metas.payment.reservation;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Payment_Reservation;
import org.compiere.model.I_C_Payment_Reservation_Capture;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerContactId;
import de.metas.email.EMailAddress;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.util.GuavaCollectors;
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

		final List<I_C_Payment_Reservation_Capture> captureRecords = retrieveCaptureRecords(id);
		return toPaymentReservation(record, captureRecords);
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

		final PaymentReservationId reservationId = PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID());
		final List<I_C_Payment_Reservation_Capture> captureRecords = retrieveCaptureRecords(reservationId);
		final PaymentReservation reservation = toPaymentReservation(record, captureRecords);
		return Optional.of(reservation);
	}

	private List<I_C_Payment_Reservation_Capture> retrieveCaptureRecords(@NonNull final PaymentReservationId reservationId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Payment_Reservation_Capture.class)
				.addEqualsFilter(I_C_Payment_Reservation_Capture.COLUMN_C_Payment_Reservation_ID, reservationId)
				.create()
				.listImmutable(I_C_Payment_Reservation_Capture.class);
	}

	public void save(@NonNull final PaymentReservation reservation)
	{
		final I_C_Payment_Reservation record;
		final boolean isNewRecord;
		if (reservation.getId() == null)
		{
			record = newInstance(I_C_Payment_Reservation.class);
			isNewRecord = true;
		}
		else
		{
			record = load(reservation.getId(), I_C_Payment_Reservation.class);
			isNewRecord = false;
		}

		updateReservationRecord(record, reservation);
		saveRecord(record);
		final PaymentReservationId reservationId = PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID());
		reservation.setId(reservationId);

		//
		// Captures
		{
			final HashMap<PaymentReservationCaptureId, I_C_Payment_Reservation_Capture> existingCaptureRecords;
			if (isNewRecord)
			{
				existingCaptureRecords = new HashMap<>();
			}
			else
			{
				existingCaptureRecords = retrieveCaptureRecords(reservationId)
						.stream()
						.collect(GuavaCollectors.toHashMapByKey(captureRecord -> extractCaptureId(captureRecord)));
			}

			for (final PaymentReservationCapture capture : reservation.getCaptures())
			{
				I_C_Payment_Reservation_Capture captureRecord = existingCaptureRecords.remove(capture.getId());
				if (captureRecord == null)
				{
					captureRecord = newInstance(I_C_Payment_Reservation_Capture.class);
					captureRecord.setC_Payment_Reservation_ID(reservationId.getRepoId());
					captureRecord.setAD_Org_ID(reservation.getOrgId().getRepoId());
				}

				updateCaptureRecord(captureRecord, capture);
				saveRecord(captureRecord);
			}

			//
			// Delete remaining captures
			InterfaceWrapperHelper.deleteAll(existingCaptureRecords.values());
		}
	}

	private static void updateReservationRecord(
			@NonNull final I_C_Payment_Reservation record,
			@NonNull final PaymentReservation from)
	{
		InterfaceWrapperHelper.setValue(record, "AD_Client_ID", from.getClientId().getRepoId());
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setAmount(from.getAmount().getAsBigDecimal());
		record.setBill_BPartner_ID(from.getPayerContactId().getBpartnerId().getRepoId());
		record.setBill_User_ID(from.getPayerContactId().getUserId().getRepoId());
		record.setBill_EMail(from.getPayerEmail().getAsString());
		record.setC_Currency_ID(from.getAmount().getCurrencyId().getRepoId());
		record.setC_Order_ID(from.getSalesOrderId().getRepoId());
		record.setDateTrx(TimeUtil.asTimestamp(from.getDateTrx()));
		record.setPaymentRule(from.getPaymentRule().getCode());
		record.setStatus(from.getStatus().getCode());
	}

	private static void updateCaptureRecord(
			@NonNull final I_C_Payment_Reservation_Capture record,
			@NonNull final PaymentReservationCapture from)
	{
		record.setAmount(from.getAmount().getAsBigDecimal());
		record.setStatus(from.getStatus().getCode());
	}

	private static PaymentReservationCaptureId extractCaptureId(final I_C_Payment_Reservation_Capture captureRecord)
	{
		return PaymentReservationCaptureId.ofRepoId(captureRecord.getC_Payment_Reservation_Capture_ID());
	}

	private static PaymentReservation toPaymentReservation(
			@NonNull final I_C_Payment_Reservation record,
			@NonNull final List<I_C_Payment_Reservation_Capture> captureRecords)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		final List<PaymentReservationCapture> captures = captureRecords.stream()
				.map(captureRecord -> toPaymentReservationCapture(captureRecord, currencyId))
				.collect(ImmutableList.toImmutableList());

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
				.captures(captures)
				.build();
	}

	private static PaymentReservationCapture toPaymentReservationCapture(
			@NonNull final I_C_Payment_Reservation_Capture record,
			@NonNull final CurrencyId currencyId)
	{
		return PaymentReservationCapture.builder()
				.status(PaymentReservationCaptureStatus.ofCode(record.getStatus()))
				.amount(Money.of(record.getAmount(), currencyId))
				.id(PaymentReservationCaptureId.ofRepoIdOrNull(record.getC_Payment_Reservation_Capture_ID()))
				.build();
	}

}
