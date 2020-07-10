package de.metas.payment.reservation;

import javax.annotation.Nullable;

import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

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

@Value
@Builder
public class PaymentReservationCapture
{
	@NonNull
	PaymentReservationId reservationId;

	@NonNull
	@NonFinal
	PaymentReservationCaptureStatus status;

	@NonNull
	Money amount;

	@NonNull
	OrgId orgId;
	@NonNull
	OrderId salesOrderId;
	@NonNull
	InvoiceId salesInvoiceId;
	@NonNull
	PaymentId paymentId;

	@Nullable
	@NonFinal
	@Setter(AccessLevel.PACKAGE)
	PaymentReservationCaptureId id;

	public void setStatusAsCompleted()
	{
		status = PaymentReservationCaptureStatus.COMPLETED;
	}
}
