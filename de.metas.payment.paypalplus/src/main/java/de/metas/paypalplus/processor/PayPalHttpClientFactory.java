package de.metas.paypalplus.processor;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

import de.metas.paypalplus.PayPalConfig;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypalplus
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

final class PayPalHttpClientFactory
{
	public PayPalHttpClient getPayPalHttpClient(@NonNull final PayPalConfig config)
	{
		final PayPalEnvironment environment = createPayPalEnvironment(config);
		return new PayPalHttpClient(environment);
	}

	private static PayPalEnvironment createPayPalEnvironment(@NonNull final PayPalConfig config)
	{
		if (!config.isSandbox())
		{
			return new PayPalEnvironment(
					config.getClientId(),
					config.getClientSecret(),
					config.getBaseUrl(),
					config.getWebUrl());
		}
		else
		{
			return new PayPalEnvironment.Sandbox(
					config.getClientId(),
					config.getClientSecret());
		}
	}

}
