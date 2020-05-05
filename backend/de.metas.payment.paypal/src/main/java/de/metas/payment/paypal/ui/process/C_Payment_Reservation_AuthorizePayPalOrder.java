package de.metas.payment.paypal.ui.process;

import org.compiere.SpringContextHolder;

import de.metas.payment.paypal.PayPal;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

/*
 * #%L
 * de.metas.payment.paypal
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

public class C_Payment_Reservation_AuthorizePayPalOrder extends JavaProcess implements IProcessPrecondition
{
	private final PayPal paypal = SpringContextHolder.instance.getBean(PayPal.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		final PaymentReservationId reservationId = PaymentReservationId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (reservationId == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		if (!paypal.hasActivePaypalOrder(reservationId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no active paypal order exists");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PaymentReservationId reservationId = getPaymentReservationId();
		paypal.authorizePayPalReservation(reservationId);
		return MSG_OK;
	}

	private PaymentReservationId getPaymentReservationId()
	{
		return PaymentReservationId.ofRepoId(getRecord_ID());
	}
}
