/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.payment;

import de.metas.Profiles;
import de.metas.common.rest_api.v1.payment.JsonInboundPaymentInfo;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/payment",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/payment",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/payments" })
@Profile(Profiles.PROFILE_App)
public class PaymentRestEndpointImpl
{
	private final JsonPaymentService jsonPaymentService;

	public PaymentRestEndpointImpl(final JsonPaymentService jsonPaymentService)
	{
		this.jsonPaymentService = jsonPaymentService;
	}

	@PostMapping("/inbound")
	public ResponseEntity<String> createInboundPayment(@RequestBody @NonNull final JsonInboundPaymentInfo jsonInboundPaymentInfo)
	{
		return jsonPaymentService.createInboundPaymentFromJson(jsonInboundPaymentInfo);
	}
}
