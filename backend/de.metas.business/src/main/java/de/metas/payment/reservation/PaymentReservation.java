package de.metas.payment.reservation;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.service.ClientId;

import de.metas.bpartner.BPartnerContactId;
import de.metas.email.EMailAddress;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
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
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PaymentReservation
{
	ClientId clientId;
	OrgId orgId;
	Money amount;

	LocalDate dateTrx;
	PaymentRule paymentRule;

	BPartnerContactId payerContactId;
	EMailAddress payerEmail;

	@NonNull
	@NonFinal
	PaymentReservationStatus status;

	OrderId salesOrderId;
	@Nullable
	@NonFinal
	@Setter(AccessLevel.PACKAGE)
	PaymentReservationId id;

	@Builder
	private PaymentReservation(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final Money amount,
			@NonNull final BPartnerContactId payerContactId,
			@NonNull final EMailAddress payerEmail,
			@NonNull final OrderId salesOrderId,
			@NonNull final LocalDate dateTrx,
			@NonNull final PaymentRule paymentRule,
			@NonNull final PaymentReservationStatus status,
			@Nullable final PaymentReservationId id)
	{
		this.clientId = clientId;
		this.orgId = orgId;
		this.amount = amount;
		this.payerContactId = payerContactId;
		this.payerEmail = payerEmail;
		this.salesOrderId = salesOrderId;
		this.dateTrx = dateTrx;
		this.paymentRule = paymentRule;
		this.status = status;
		this.id = id;
	}

	public void changeStatusTo(@NonNull final PaymentReservationStatus status)
	{
		this.status = status;
	}
}
