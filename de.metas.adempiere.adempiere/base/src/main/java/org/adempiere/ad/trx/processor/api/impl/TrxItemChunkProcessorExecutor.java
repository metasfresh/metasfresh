package org.adempiere.ad.trx.processor.api.impl;

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


import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.util.CLogger;

/**
 * Default executor for {@link ITrxItemChunkProcessor}.
 * 
 * @author tsa
 * 
 * @param <IT>
 * @param <RT>
 */
public class TrxItemChunkProcessorExecutor<IT, RT> implements ITrxItemProcessorExecutor<IT, RT>
{
	//
	// Services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ITrxConstraintsBL trxConstraintsBL = Services.get(ITrxConstraintsBL.class);

	private static final transient CLogger logger = CLogger.getCLogger(TrxItemChunkProcessorExecutor.class);

	private static final String TRXNAME_PREFIX = "TrxItemChunkProcessorExecutor";

	private final ITrxItemProcessorContext processorCtx;
	private final ITrxItemChunkProcessor<IT, RT> processor;
	private ITrxItemExceptionHandler exceptionHandler = DEFAULT_ExceptionHandler;

	private boolean chunkOpen = false;
	private boolean chunkHasErrors = false;

	private ITrx chunkTrx;
	private ITrxSavepoint chunkTrxSavepoint;

	public TrxItemChunkProcessorExecutor(final ITrxItemProcessorContext processorCtx, final ITrxItemChunkProcessor<IT, RT> processor)
	{
		super();

		Check.assumeNotNull(processorCtx, "processorCtx not null");
		this.processorCtx = processorCtx;

		Check.assumeNotNull(processor, "processor not null");
		this.processor = processor;
	}

	@Override
	public ITrxItemProcessorExecutor<IT, RT> setExceptionHandler(final ITrxItemExceptionHandler exceptionHandler)
	{
		Check.assumeNotNull(exceptionHandler, "exceptionHandler not null");
		this.exceptionHandler = exceptionHandler;

		return this;
	}

	@Override
	public ITrxItemProcessor<IT, RT> getProcessor()
	{
		return processor;
	}

	@Override
	public RT execute(final Iterator<? extends IT> items)
	{
		Check.assumeNotNull(items, "items not null");

		final boolean requiresNewTrxs = trxManager.isNull(processorCtx.getTrx());

		trxConstraintsBL.saveConstraints();
		final String threadTrxName = trxManager.getThreadInheritedTrxName();
		try
		{
			// If this executor will create new transactions, make sure transaction constraints are allowing them
			if (requiresNewTrxs)
			{
				final ITrxConstraints trxConstraints = trxConstraintsBL.getConstraints();
				trxConstraints.addAllowedTrxNamePrefix(TRXNAME_PREFIX);
				trxConstraints.incMaxTrx(1);
			}

			while (items.hasNext())
			{
				final IT item = items.next();
				process(item);
			}

			//
			// Close last chunk if any
			if (chunkOpen)
			{
				if (chunkHasErrors)
				{
					cancelChunk();
				}
				else
				{
					completeChunk();
				}
			}

			return processor.getResult();
		}
		// NOTE: don't catch exceptions here, there are handled in called methods
		finally
		{
			//
			// Make sure we are not letting the transaction open
			if (chunkTrx != null)
			{
				try
				{
					rollbackChunkTrx();
				}
				catch (Exception e)
				{
					// it's futile to log severe/throw any exception here but if we reach this point, another exception was already thrown
					logger.log(Level.FINE, "Error while rolling back because the transaction was not already rolled back. Check console, you shall have other errors.", e);
				}
			}

			trxConstraintsBL.restoreConstraints();
			// restore thread's transaction name (just in case some of our transaction managing methods are failing to do so)
			trxManager.setThreadInheritedTrxName(threadTrxName);
		}
	}

	private void process(final IT item)
	{
		if (chunkHasErrors)
		{
			Check.assume(chunkOpen, "When we have a chunk item with errors, chunk shall be opened");

			if (processor.isSameChunk(item))
			{
				// current item is part of current chunk which has errors
				// we are skipping it
				return;
			}
			else
			{
				// item is NOT part of current chunk.
				cancelChunk();
			}
		}

		//
		// Close current chunk if needed
		if (chunkOpen && !processor.isSameChunk(item))
		{
			completeChunk();
		}

		//
		// Start a new chunk if needed
		if (!chunkOpen)
		{
			newChunk(item);
		}
		if (!chunkOpen)
		{
			// Starting a new chunk failed.
			// We are skipping only this item because we cannot skip the whole chunk (which was not created yet)
			return;
		}

		//
		// Process current item in current chunk
		// for the time of processing, use the transaction of the processor context, no matter which trxName the item was loaded with.
		// note: it's not illegal to process items that are not handled by InterfaceWrapperHelper, so we tell InterfaceWrapperHelper not to make a fuzz.
		final boolean ignoreIfNotHandled = true; 
		final String trxNameBkp = InterfaceWrapperHelper.getTrxName(item, ignoreIfNotHandled);
		try
		{
			InterfaceWrapperHelper.setTrxName(item, processorCtx.getTrxName(), ignoreIfNotHandled);
			processor.process(item);
		}
		catch (final Exception e)
		{
			chunkHasErrors = true;
			exceptionHandler.onItemError(e, item);

			return;
		}
		finally
		{
			InterfaceWrapperHelper.setTrxName(item, trxNameBkp, ignoreIfNotHandled);
		}
	}

	private final void newChunk(final IT item)
	{
		Check.assume(!chunkOpen, "When creating a new chunk, previous chunk shall be closed first");
		//
		// Create chunk trx
		startChunkTrx();

		try
		{
			//
			// Setup chunk context
			final ITrxItemProcessorContext chunkCtx = processorCtx.copy();
			chunkCtx.setTrx(chunkTrx);
			processor.setTrxItemProcessorCtx(chunkCtx);

			//
			// Start a new chunk
			processor.newChunk(item);
			chunkOpen = true;
			chunkHasErrors = false;
		}
		catch (final Exception e)
		{
			chunkOpen = false;
			rollbackChunkTrx();

			// NOTE: call it after rollback (just in case this one fails, rollback needs to be done anyway)
			exceptionHandler.onNewChunkError(e, item);
		}
	}

	private final void completeChunk()
	{
		//
		// Ask processor to complete the chunk
		boolean completed = false;
		try
		{
			processor.completeChunk();
			completed = true;
		}
		catch (final Exception e)
		{
			exceptionHandler.onCompleteChunkError(e);
		}
		finally
		{
			chunkOpen = false;
			chunkHasErrors = false;
		}

		//
		// Completing chunk failed
		if (!completed)
		{
			logger.log(Level.INFO, "Processor failed to complete current chunk => cancel chunk");
			cancelChunk();
			return;
		}

		//
		//
		logger.log(Level.INFO, "Processor succeeded to complete the chunk => commit transaction");
		try
		{
			commitChunkTrx();
		}
		catch (final Exception commitEx)
		{
			boolean rollbackOK = false;
			try
			{
				rollbackChunkTrx();
				rollbackOK = true;
			}
			finally
			{
				if (!rollbackOK)
				{
					logger.log(Level.SEVERE, "Got rollback failed and exception will be thrown, while handling a commit exception. Below see the commit exception.", commitEx);
				}
			}
			
			exceptionHandler.onCommitChunkError(commitEx);
		}
	}

	/**
	 * Cancel current chunk.
	 * 
	 * If something went wrong this method will throw an exception right away, exception which will stop entire batch processing.
	 */
	private void cancelChunk()
	{
		try
		{
			processor.cancelChunk();
		}
		catch (final Exception e)
		{
			exceptionHandler.onCancelChunkError(e);

			// If canceling fails, we need to throw the exception right away because
			// it means that the processor is in inconsistent state
			// which will lead to false-fails on next chunks.
			// Also we can do nothing here, processor is compromised.
			//
			// NOTE: no need to restore/reset thread transaction because "execute" method will retore it anyways at the end
			throw new AdempiereException("Failed canceling current chunk", e);
		}
		finally
		{
			chunkOpen = false;
			chunkHasErrors = false;

			rollbackChunkTrx();
		}
	}

	/**
	 * Start a new transaction for the the new chunk that will come.
	 * 
	 * If something went wrong this method will throw an exception right away, exception which will stop entire batch processing.
	 */
	private void startChunkTrx() throws DBException
	{
		Check.assumeNull(chunkTrx, "chunkTrx shall be null before starting a new chunk transaction");
		Check.assumeNull(chunkTrxSavepoint, "chunkTrxSavepoint shall be null before starting a new chunk transaction");

		final ITrx parentTrx = processorCtx.getTrx();

		if (trxManager.isNull(parentTrx))
		{
			final String trxName = trxManager.createTrxName(TRXNAME_PREFIX, true);
			chunkTrx = trxManager.get(trxName, OnTrxMissingPolicy.Fail);
			chunkTrxSavepoint = null;
		}
		else
		{
			chunkTrx = parentTrx;
			chunkTrxSavepoint = parentTrx.createTrxSavepoint(null);
		}

		//
		// Set current thread transaction
		// NOTE: we will not reset it because we relly that "execute" method will restore the state in any case
		trxManager.setThreadInheritedTrxName(chunkTrx.getTrxName());
	}

	/**
	 * Commit current chunk's transaction
	 * 
	 * If something went wrong this method will throw an exception right away, exception which will stop entire batch processing.
	 */
	private void commitChunkTrx() throws DBException
	{
		Check.assumeNotNull(chunkTrx, "chunkTrx shall NOT be null");

		if (chunkTrxSavepoint != null)
		{
			chunkTrx.releaseSavepoint(chunkTrxSavepoint);
			chunkTrxSavepoint = null;
		}
		else
		{
			try
			{
				chunkTrx.commit(true);
			}
			catch (final SQLException e)
			{
				throw new DBException(e);
			}
			chunkTrx.close();
		}

		chunkTrx = null;

		// NOTE: no need to restore/reset thread transaction because "execute" method will retore it anyways at the end
		// trxManager.setThreadInheritedTrxName(null);
	}

	/**
	 * Rollback current chunk's transaction.
	 * 
	 * If something went wrong this method will throw an exception right away, exception which will stop entire batch processing.
	 */
	private void rollbackChunkTrx()
	{
		Check.assumeNotNull(chunkTrx, "chunkTrx shall NOT be null");

		if (chunkTrxSavepoint != null)
		{
			chunkTrx.rollback(chunkTrxSavepoint);
			chunkTrxSavepoint = null;
		}
		else
		{
			chunkTrx.rollback();
			chunkTrx.close();
		}

		chunkTrx = null;

		// NOTE: no need to restore/reset thread transaction because "execute" method will retore it anyways at the end
		// trxManager.setThreadInheritedTrxName(null);
	}
}
