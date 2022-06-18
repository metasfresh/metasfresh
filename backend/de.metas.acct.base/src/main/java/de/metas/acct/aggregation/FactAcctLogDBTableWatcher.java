/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.acct.aggregation;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import java.time.Duration;

public class FactAcctLogDBTableWatcher implements Runnable
{
	private static final Logger logger = LogManager.getLogger(FactAcctLogDBTableWatcher.class);
	private final ISysConfigBL sysConfigBL;
	private final FactAcctLogService factAcctLogService;

	private static final String SYSCONFIG_PollIntervalInSeconds = "de.metas.acct.aggregation.FactAcctLogDBTableWatcher.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);

	private static final String SYSCONFIG_BatchSize = "de.metas.acct.aggregation.FactAcctLogDBTableWatcher.batchSize";
	private static final int DEFAULT_BatchSize = 1000;

	@Builder
	private FactAcctLogDBTableWatcher(
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final FactAcctLogService factAcctLogService)
	{
		this.sysConfigBL = sysConfigBL;
		this.factAcctLogService = factAcctLogService;
	}

	@Override
	public void run()
	{
		while (true)
		{
			final Duration pollInterval = getPollInterval();
			logger.debug("Sleeping {}", pollInterval);
			try
			{
				//noinspection BusyWait
				Thread.sleep(getPollInterval().toMillis());
			}
			catch (InterruptedException e)
			{
				logger.info("Got interrupt request. Exiting.");
				return;
			}

			try
			{
				processNow();
			}
			catch (final Exception ex)
			{
				logger.warn("Failed to process. Ignored.", ex);
			}
		}

	}

	private Duration getPollInterval()
	{
		final int pollIntervalInSeconds = sysConfigBL.getIntValue(SYSCONFIG_PollIntervalInSeconds, -1);
		return pollIntervalInSeconds > 0
				? Duration.ofSeconds(pollIntervalInSeconds)
				: DEFAULT_PollInterval;
	}

	private int getBatchSize()
	{
		final int batchSize = sysConfigBL.getIntValue(SYSCONFIG_BatchSize, DEFAULT_BatchSize);
		return batchSize > 0 ? batchSize : DEFAULT_BatchSize;
	}

	private void processNow()
	{
		final int batchSize = getBatchSize();
		int countProcessed;
		do
		{
			countProcessed = factAcctLogService.processAll(batchSize);
		}
		while (countProcessed > 0);
	}

}
