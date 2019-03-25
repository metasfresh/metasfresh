package de.metas.paypalplus.controller;

import com.paypal.api.payments.DetailedRefund;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import de.metas.paypalplus.model.PayPalPlusPayment;
import de.metas.util.web.MetasfreshRestAPIConstants;

import java.util.Optional;

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
	 * Reserve a PayPal Plus payment
	 *
	 * @return Payment
	 * @throws PayPalRESTException
	 */
	Optional<Payment> reservePayment(PayPalPlusPayment payPalPlusPayment) throws PayPalRESTException;

	Optional<Payment> capturePayment(PayPalPlusPayment payPalPlusPayment) throws PayPalRESTException;

	Optional<DetailedRefund> refundCapturedPayment(String saleId, Integer transactionNumber) throws PayPalRESTException;

}
