package org.adempiere.ad.trx.processor.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * An {@link ITrxItemProcessorExecutor}'s exception handler which just logs the exception but does nothing.
 * Use {@link #instance} to obtain your instance.
 * <p>
 * May be overridden.
 *
 * @author tsa
 *
 */
public class LoggerTrxItemExceptionHandler implements ITrxItemExceptionHandler
{
	public static final LoggerTrxItemExceptionHandler instance = new LoggerTrxItemExceptionHandler();

	private final transient Logger logger = LogManager.getLogger(getClass());

	protected LoggerTrxItemExceptionHandler()
	{
	}

	@Override
	public void onNewChunkError(final Exception e, final Object item)
	{
		logger.warn("Error while trying to create a new chunk for item: " + item, e);
	}

	@Override
	public void onItemError(final Exception e, final Object item)
	{
		logger.warn("Error while trying to process item: " + item, e);
	}

	@Override
	public void onCompleteChunkError(final Exception e)
	{
		logger.warn("Error while completing current chunk", e);
	}

	@Override
	public void onCommitChunkError(final Exception e)
	{
		logger.info("Processor failed to commit current chunk => rollback transaction", e);
	}

	@Override
	public void onCancelChunkError(Exception e)
	{
		logger.warn("Error while cancelling current chunk. Ignored.", e);
	}
}
