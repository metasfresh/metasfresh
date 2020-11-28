package de.metas.payment.paypal;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.payment.paypal.client.PayPalOrderExternalId;
import lombok.NonNull;

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

@RestController
@Profile(Profiles.PROFILE_Webui)
@RequestMapping({
		"/paypal",
		"/rest/api/paypal" // NOTE: actually this one is used by frontend because in config.js, the API_URL ends with /rest/api
})
public class PayPalRestController
{
	private final PayPal paypal;

	public PayPalRestController(@NonNull final PayPal paypal)
	{
		this.paypal = paypal;
	}

	@RequestMapping(path = "/approved", method = { RequestMethod.GET, RequestMethod.POST })
	public void notifyOrderApprovedByPayer(
			@RequestParam(value = "token", required = true) final String token)
	{
		final PayPalOrderExternalId apiOrderId = PayPalOrderExternalId.ofString(token);
		paypal.onOrderApprovedByPayer(apiOrderId);
	}

}
