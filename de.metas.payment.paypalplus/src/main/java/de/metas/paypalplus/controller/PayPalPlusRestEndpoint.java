package de.metas.paypalplus.controller;

import de.metas.paypalplus.model.PaymentCaptureRequest;
import de.metas.paypalplus.model.PaymentCaptureResponse;
import de.metas.paypalplus.model.PaymentReservationRequest;
import de.metas.paypalplus.model.PaymentReservationResponse;
import de.metas.util.web.MetasfreshRestAPIConstants;

/*
 * #%L
 * de.metas.paypalplus.controller
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

public interface PayPalPlusRestEndpoint
{
	String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/paypalplus/";

	/**
	 * Authorize a payment and reserve it for 30 days
	 */
	PaymentReservationResponse reservePayment(PaymentReservationRequest request);

	/**
	 * Capture a PayPal Plus payment: honor the payment
	 */
	PaymentCaptureResponse capturePayment(PaymentCaptureRequest request);

	/**
	 * Cancel(void) a PayPal Plus payment
	 */
	String cancelPayment(String paymentId);

}
