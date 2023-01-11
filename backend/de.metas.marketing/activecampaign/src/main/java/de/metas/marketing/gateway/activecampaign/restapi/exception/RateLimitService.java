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

package de.metas.marketing.gateway.activecampaign.restapi.exception;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.ACTIVE_CAMPAIGN_API_RATE_LIMIT_RETRY_HEADER;
import static de.metas.marketing.gateway.activecampaign.ActiveCampaignConstants.MAX_SECONDS_TO_WAIT_FOR_ACTIVE_CAMPAIGN_LIMIT_RESET;

@Service
public class RateLimitService
{
	private static final Logger log = LogManager.getLogger(RateLimitService.class);

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull
	public Optional<Duration> extractRetryAfterDuration(final HttpHeaders httpHeaders)
	{
		final List<String> retryAfterValue = httpHeaders.get(ACTIVE_CAMPAIGN_API_RATE_LIMIT_RETRY_HEADER);

		if (retryAfterValue == null || Check.isEmpty(retryAfterValue))
		{
			return Optional.empty();
		}

		final int retryAfter = NumberUtils.asInteger(retryAfterValue.get(0), 0);

		return Optional.of(Duration.ofSeconds(retryAfter));
	}

	public void waitForLimitReset(@NonNull final Duration retryAfterDuration)
	{
		Loggables.withLogger(log, Level.DEBUG).addLog("RateLimitService.waitForLimitReset() with retryAfter seconds: {}", retryAfterDuration.getSeconds());

		final int maxSecondsToWait = sysConfigBL.getIntValue(MAX_SECONDS_TO_WAIT_FOR_ACTIVE_CAMPAIGN_LIMIT_RESET, 3600);

		if (retryAfterDuration.getSeconds() > maxSecondsToWait)
		{
			throw new AdempiereException("API Rate limit exceeded! Please wait before syncing again.")
					.appendParametersToMessage()
					.setParameter("seconds to wait:", retryAfterDuration.getSeconds());
		}

		try
		{
			Thread.sleep(retryAfterDuration.getSeconds());
		}
		catch (final InterruptedException e)
		{
			throw new AdempiereException(e.getMessage(), e);
		}
	}
}
