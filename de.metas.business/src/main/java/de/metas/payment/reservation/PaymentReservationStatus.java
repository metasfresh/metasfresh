package de.metas.payment.reservation;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Payment_Reservation;

import com.google.common.collect.ImmutableMap;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
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

public enum PaymentReservationStatus implements ReferenceListAwareEnum
{
	WAITING_PAYER_APPROVAL(X_C_Payment_Reservation.STATUS_WAITING_PAYER_APPROVAL), //
	APPROVED_BY_PAYER(X_C_Payment_Reservation.STATUS_APPROVED), //
	COMPLETED(X_C_Payment_Reservation.STATUS_COMPLETED), //
	VOIDED(X_C_Payment_Reservation.STATUS_VOIDED) //
	;

	@Getter
	private final String code;

	private PaymentReservationStatus(final String code)
	{
		this.code = code;
	}

	public static PaymentReservationStatus ofCode(@NonNull final String code)
	{
		PaymentReservationStatus type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PaymentReservationStatus.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PaymentReservationStatus> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	public boolean isCompleted()
	{
		return this == COMPLETED;
	}

	public boolean isVoided()
	{
		return this == VOIDED;
	}

	public boolean isApprovedByPayer()
	{
		return this == APPROVED_BY_PAYER;
	}

	public void assertApprovedByPayer()
	{
		assertEquals(APPROVED_BY_PAYER);
	}

	public void assertCompleted()
	{
		assertEquals(COMPLETED);
	}

	public void assertEquals(@NonNull final PaymentReservationStatus expected)
	{
		if (!this.equals(expected))
		{
			throw new AdempiereException("Invalid reservation status. Expected " + expected + " but got " + this);
		}
	}

	public boolean isWaitingForPayerApproval()
	{
		return this == WAITING_PAYER_APPROVAL;
	}

	public void assertWaitingForPayerApproval()
	{
		assertEquals(WAITING_PAYER_APPROVAL);
	}

	public boolean isWaitingToComplete()
	{
		return isWaitingForPayerApproval()
				|| isApprovedByPayer();
	}

}
