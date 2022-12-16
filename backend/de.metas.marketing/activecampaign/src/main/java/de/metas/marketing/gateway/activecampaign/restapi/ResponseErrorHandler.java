/*
 * #%L
 * marketing-activecampaign
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.gateway.activecampaign.restapi;

import de.metas.marketing.gateway.activecampaign.restapi.exception.RateLimitExceededException;
import de.metas.marketing.gateway.activecampaign.restapi.exception.RateLimitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Component
public class ResponseErrorHandler extends DefaultResponseErrorHandler
{
	private final RateLimitService rateLimitService;

	public ResponseErrorHandler(final RateLimitService rateLimitService)
	{
		super();
		this.rateLimitService = rateLimitService;
	}

	@Override
	public void handleError(final ClientHttpResponse response) throws IOException
	{
		final HttpStatus statusCode = response.getStatusCode();

		if (statusCode.value() == TOO_MANY_REQUESTS.value())
		{
			final Optional<Duration> retryAfter = rateLimitService.extractRetryAfterDuration(response.getHeaders());

			if (retryAfter.isPresent())
			{
				throw new RateLimitExceededException(response.getBody().toString(), retryAfter.get());
			}
		}

		super.handleError(response);
	}
}
