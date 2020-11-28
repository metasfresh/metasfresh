package de.metas.payment.reservation;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_Payment_Reservation_Capture;
import org.springframework.stereotype.Repository;

import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
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
public class PaymentReservationCaptureRepository
{
	public void save(@NonNull final PaymentReservationCapture capture)
	{
		final I_C_Payment_Reservation_Capture captureRecord = capture.getId() != null
				? load(capture.getId(), I_C_Payment_Reservation_Capture.class)
				: newInstance(I_C_Payment_Reservation_Capture.class);

		updateCaptureRecord(captureRecord, capture);

		saveRecord(captureRecord);
		capture.setId(extractCaptureId(captureRecord));
	}

	private static void updateCaptureRecord(
			@NonNull final I_C_Payment_Reservation_Capture record,
			@NonNull final PaymentReservationCapture from)
	{
		record.setC_Payment_Reservation_ID(from.getReservationId().getRepoId());
		record.setStatus(from.getStatus().getCode());

		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setC_Order_ID(from.getSalesOrderId().getRepoId());
		record.setC_Invoice_ID(from.getSalesInvoiceId().getRepoId());
		record.setC_Payment_ID(from.getPaymentId().getRepoId());

		record.setAmount(from.getAmount().toBigDecimal());
		record.setC_Currency_ID(from.getAmount().getCurrencyId().getRepoId());
	}

	private static PaymentReservationCaptureId extractCaptureId(final I_C_Payment_Reservation_Capture captureRecord)
	{
		return PaymentReservationCaptureId.ofRepoId(captureRecord.getC_Payment_Reservation_Capture_ID());
	}

	@SuppressWarnings("unused")
	private static PaymentReservationCapture toPaymentReservationCapture(@NonNull final I_C_Payment_Reservation_Capture record)
	{
		return PaymentReservationCapture.builder()
				.id(PaymentReservationCaptureId.ofRepoIdOrNull(record.getC_Payment_Reservation_Capture_ID()))
				.reservationId(PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID()))
				.status(PaymentReservationCaptureStatus.ofCode(record.getStatus()))
				//
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.salesOrderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.salesInvoiceId(InvoiceId.ofRepoId(record.getC_Invoice_ID()))
				.paymentId(PaymentId.ofRepoId(record.getC_Payment_ID()))
				//
				.amount(Money.of(record.getAmount(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				//
				.build();
	}
}
