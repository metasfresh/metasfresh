package org.adempiere.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Delegate;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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
/** Thx to https://stackoverflow.com/questions/3446011/threadpoolexecutor-block-when-queue-is-full */
@ToString
public class BlockingExecutorWrapper implements ExecutorService
{
	private final Semaphore semaphore;

	@Delegate(excludes = Executor.class)
	private final ThreadPoolExecutor delegate;

	private @NonNull Logger logger;

	@Builder
	private BlockingExecutorWrapper(
			final int poolSize,
			@NonNull final ThreadPoolExecutor delegate,
			@NonNull final Logger loggerToUse)
	{
		this.semaphore = new Semaphore(poolSize);
		this.delegate = delegate;
		this.logger = loggerToUse;
	}

	@Override
	public void execute(@NonNull final Runnable command)
	{
		try
		{
			logger.debug("Going to aquire semaphore");
			semaphore.acquire();
			logger.debug("Done aquiring semaphore");
		}
		catch (InterruptedException e)
		{
			logger.debug("execute - InterruptedException while acquiring semaphore for command=" + command + ";-> return", e);
			return;
		}

		final Runnable wrapped = () -> {
			try
			{
				logger.debug("Going tu run command");
				command.run();
				logger.debug("Done running command");
			}
			finally
			{
				semaphore.release();
				logger.debug("Semaphore released");
			}
		};

		delegate.execute(wrapped);
	}
}
