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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.logging.Level;

import org.compiere.util.CLogger;

/**
 * An {@link ITrxItemProcessorExecutor}'s exception handler which just logs the exception but does nothing.
 * 
 * @author tsa
 * 
 */
public final class LogTrxItemExceptionHandler implements ITrxItemExceptionHandler
{
	public static final LogTrxItemExceptionHandler instance = new LogTrxItemExceptionHandler();

	private final transient CLogger logger = CLogger.getCLogger(getClass());
	
	private LogTrxItemExceptionHandler()
	{
		super();
	}

	@Override
	public void onNewChunkError(final Exception e, final Object item)
	{
		logger.log(Level.WARNING, "Error while trying to create a new chunk for item: " + item, e);
	}

	@Override
	public void onItemError(final Exception e, final Object item)
	{
		logger.log(Level.WARNING, "Error while trying to process item: " + item, e);
	}

	@Override
	public void onCompleteChunkError(final Exception e)
	{
		logger.log(Level.WARNING, "Error while completing current chunk", e);
	}

	@Override
	public void onCommitChunkError(final Exception e)
	{
		logger.log(Level.INFO, "Processor failed to commit current chunk => rollback transaction", e);
	}

	@Override
	public void onCancelChunkError(Exception e)
	{
		logger.log(Level.WARNING, "Error while cancelling current chunk. Ignored.", e);
	}
}
