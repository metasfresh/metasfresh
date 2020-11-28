/*
 * #%L
 * de.metas.issue.tracking.github
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

package de.metas.issue.tracking.github.api.v3.service;

import ch.qos.logback.classic.Level;
import de.metas.issue.tracking.github.api.v3.model.RateLimit;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.CustomHeaders.RATE_LIMIT_REMAINING;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.CustomHeaders.RATE_LIMIT_RESET;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.GithubSysConfig.MAX_TIME_TO_WAIT_FOR_LIMIT_RESET;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.UTC_TIMEZONE;
import static java.time.temporal.ChronoUnit.MILLIS;

@Service
public class RateLimitService
{
	private static Logger log = LogManager.getLogger(RateLimitService.class);

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public Optional<RateLimit> extractRateLimit(final HttpHeaders httpHeaders)
	{
		final List<String> rateLimitRemaining = httpHeaders.get(RATE_LIMIT_REMAINING.getValue());
		final List<String> rateLimitReset = httpHeaders.get(RATE_LIMIT_RESET.getValue());

		if (!Check.isEmpty(rateLimitRemaining) && !Check.isEmpty(rateLimitReset))
		{
			final int remainingReq = NumberUtils.asInteger(rateLimitRemaining.get(0), 0);

			final long utcSeconds = Long.parseLong(rateLimitReset.get(0));

			final LocalDateTime rateLimitResetTimestamp = LocalDateTime.ofEpochSecond(utcSeconds,0, ZoneOffset.UTC);

			return Optional.of(
					RateLimit.builder()
					.remainingReq(remainingReq)
					.resetDate(rateLimitResetTimestamp)
					.build() );
		}

		return Optional.empty();
	}

	public void waitForLimitReset(final RateLimit rateLimit)
	{
		final long msToLimitReset = MILLIS.between(LocalDateTime.now(ZoneId.of(UTC_TIMEZONE)), rateLimit.getResetDate());

		Loggables.withLogger(log, Level.DEBUG).addLog("RateLimitService.waitForLimitReset() with rateLimit: {}, and time to wait {} ms", rateLimit, msToLimitReset);

		if (msToLimitReset < 0)
		{
			//reset timestamp is in the past
			return;
		}

		final int maxTimeToWait = sysConfigBL.getIntValue(MAX_TIME_TO_WAIT_FOR_LIMIT_RESET.getName(),
				MAX_TIME_TO_WAIT_FOR_LIMIT_RESET.getDefaultValue() );

		if (msToLimitReset > maxTimeToWait)
		{
			throw new AdempiereException("Limit Reset is too far in the future! aborting!")
					.appendParametersToMessage()
					.setParameter("RateLimit", rateLimit)
					.setParameter("MaxMsToWaitForLimitReset", maxTimeToWait);
		}

		try
		{
			Thread.sleep(msToLimitReset);
		}
		catch (final InterruptedException e)
		{
			throw new AdempiereException(e.getMessage(), e);
		}
	}
}
