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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.SQLException;
import java.util.Iterator;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder.OnItemErrorPolicy;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Default executor for {@link ITrxItemChunkProcessor}.
 *
 * @author tsa
 *
 * @param <IT>
 * @param <RT>
 */
class TrxItemChunkProcessorExecutor<IT, RT> implements ITrxItemProcessorExecutor<IT, RT>
{
	//
	// Services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ITrxConstraintsBL trxConstraintsBL = Services.get(ITrxConstraintsBL.class);

	private static final transient Logger logger = LogManager.getLogger(TrxItemChunkProcessorExecutor.class);

	private static final String TRXNAME_PREFIX = "TrxItemChunkProcessorExecutor";

	//
	// Configuration parameters
	private final ITrxItemProcessorContext processorCtx;
	private final ITrxItemChunkProcessor<IT, RT> processor;

	private final OnItemErrorPolicy onItemErrorPolicy; // issue #302

	private ITrxItemExceptionHandler exceptionHandler; // non-final for historical reasons

	private boolean useTrxSavepoints; // non-final for historical reasons

	//
	// State
	private boolean chunkOpen = false;

	/** set to <code>true</code> if any item of a given chunk has an error. In this case, the chunk is canceled. */
	private boolean chunkHasErrors = false;

	// Transaction state
	private ITrx chunkTrx;
	/** true if the {@link #chunkTrx} was created locally in this executor */
	private boolean chunkTrxIsLocal;
	private ITrxSavepoint chunkTrxSavepoint;
	private ITrxItemProcessorContext chunkCtx;

	TrxItemChunkProcessorExecutor(
			final ITrxItemProcessorContext processorCtx,
			final ITrxItemChunkProcessor<IT, RT> processor,
			final ITrxItemExceptionHandler exceptionHandler,
			final OnItemErrorPolicy onItemErrorPolicy,
			final boolean useTrxSavePoints)
	{
		super();

		Check.assumeNotNull(processorCtx, "processorCtx not null");
		this.processorCtx = processorCtx;

		Check.assumeNotNull(processor, "processor not null");
		this.processor = processor;

		this.exceptionHandler = exceptionHandler;
		this.onItemErrorPolicy = onItemErrorPolicy;
		this.useTrxSavepoints = useTrxSavePoints;
	}

	@Override
	public ITrxItemProcessorExecutor<IT, RT> setExceptionHandler(final ITrxItemExceptionHandler exceptionHandler)
	{
		Check.assumeNotNull(exceptionHandler, "exceptionHandler not null");
		this.exceptionHandler = exceptionHandler;

		return this;
	}

	@Override
	public ITrxItemProcessorExecutor<IT, RT> setUseTrxSavepoints(final boolean useTrxSavepoints)
	{
		this.useTrxSavepoints = useTrxSavepoints;
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
				processItem(item);
			}

			//
			// Close last chunk if any
			if (chunkOpen)
			{
				if (chunkHasErrors
						&& (onItemErrorPolicy == OnItemErrorPolicy.CancelChunkAndRollBack
								|| onItemErrorPolicy == OnItemErrorPolicy.CancelChunkAndCommit))
				{
					final boolean processItemFailed = true;
					cancelChunk(processItemFailed);
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
			// Make sure the iterator is closed
			IteratorUtils.closeQuietly(items);

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
					logger.debug("Error while rolling back because the transaction was not already rolled back. Check console, you shall have other errors.", e);
				}
			}

			trxConstraintsBL.restoreConstraints();
			// restore thread's transaction name (just in case some of our transaction managing methods are failing to do so)
			trxManager.setThreadInheritedTrxName(threadTrxName);
		}
	}

	private void processItem(final IT item)
	{
		//
		// error handling
		if (chunkHasErrors
				// at least one item in this chunk was processed with an error
				&& (onItemErrorPolicy == OnItemErrorPolicy.CancelChunkAndRollBack
						|| onItemErrorPolicy == OnItemErrorPolicy.CancelChunkAndCommit))
		{
			Check.assume(chunkOpen, "When we have a chunk item with errors, chunk shall be opened");

			if (processor.isSameChunk(item))
			{
				// the item is part of current chunk which has errors.
				// we are skipping this item (and all other items of this chunk). I.e. we skip forward to the last item, and then call cancelChunk(true)
				return;
			}
			else
			{
				// the current item is NOT part of current chunk. So we cancel the current chunk and will process the current item in a new chunk.
				cancelChunk(true); // processItemFailed = true;
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
			InterfaceWrapperHelper.setTrxName(item, chunkCtx.getTrxName(), ignoreIfNotHandled);
			processor.process(item);
		}
		catch (final Exception e)
		{
			chunkHasErrors = true;
			exceptionHandler.onItemError(e, item);
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
			this.chunkCtx = processorCtx.copy();
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
			logger.info("Processor failed to complete current chunk => cancel chunk");

			final boolean processItemFailed = false; // it's the completion that failed, not processeItem
			cancelChunk(processItemFailed);
			return;
		}

		//
		//
		logger.info("Processor succeeded to complete the chunk => commit transaction");
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
					logger.error("Got rollback failed and exception will be thrown, while handling a commit exception. Below see the commit exception.", commitEx);
				}
			}

			exceptionHandler.onCommitChunkError(commitEx);
		}
	}

	/**
	 * Invokes {@link ITrxItemChunkProcessor#cancelChunk()} to cancel the current chunk.
	 *
	 * If something went wrong this method will throw an exception right away, exception which will stop entire batch processing.
	 *
	 * @param processItemFailed if <code>true</code>, then do what our current {@link #onItemErrorPolicy} value indicates.
	 *            If <code>false</code>, then just roll back the trx.
	 */
	private void cancelChunk(boolean processItemFailed)
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
			// NOTE: no need to restore/reset thread transaction because "execute" method will restore it anyways at the end
			throw new AdempiereException("Failed canceling current chunk", e);
		}
		finally
		{
			chunkOpen = false;
			chunkHasErrors = false;
			if (processItemFailed)
			{
				// cancelChunk was called because processing an item failed
				switch (onItemErrorPolicy)
				{
					case CancelChunkAndRollBack:
						rollbackChunkTrx();
						break;
					case CancelChunkAndCommit:
						commitChunkTrx();
						break;
					default:
						Check.errorIf(true, "Unexpected onItemErrorPolicy={}, this={}", onItemErrorPolicy, this);
						break;
				}
			}
			else
			{
				// cancelChunk was called for some other reason. Just roll back because that's what we always did in such a case
				rollbackChunkTrx();
			}
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
			chunkTrxIsLocal = true;
			chunkTrxSavepoint = null;
		}
		else
		{
			chunkTrx = parentTrx;
			chunkTrxIsLocal = false;

			if (useTrxSavepoints)
			{
				chunkTrxSavepoint = parentTrx.createTrxSavepoint(null);
			}
			else
			{
				chunkTrxSavepoint = null;
			}
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

		//
		// Case: Locally created transaction => commit it
		if (chunkTrxIsLocal)
		{
			try
			{
				chunkTrx.commit(true);
			}
			catch (final SQLException e)
			{
				throw DBException.wrapIfNeeded(e);
			}
			chunkTrx.close();
		}
		//
		// Case: Savepoint on inherited transaction => release the savepoint
		else if (chunkTrxSavepoint != null)
		{
			chunkTrx.releaseSavepoint(chunkTrxSavepoint);
			chunkTrxSavepoint = null;
		}
		//
		// Case: Inherited transaction without any savepoint => do nothing
		else
		{
			// nothing
		}

		chunkTrx = null;

		// NOTE: no need to restore/reset thread transaction because "execute" method will retore it anyways at the end
		// trxManager.setThreadInheritedTrxName(null);
	}

	/**
	 * Rollback current chunk's transaction if there is a savepoint (see {@link #setUseTrxSavepoints(boolean)}) or if we have a local transaction.
	 *
	 * If something went wrong this method will throw an exception right away, exception which will stop entire batch processing.
	 */
	private void rollbackChunkTrx()
	{
		Check.assumeNotNull(chunkTrx, "chunkTrx shall NOT be null");

		//
		// Case: Locally created transaction => rollback it
		if (chunkTrxIsLocal)
		{
			chunkTrx.rollback();
			chunkTrx.close();
		}
		//
		// Case: Savepoint on inherited transaction => rollback to savepoint
		else if (chunkTrxSavepoint != null)
		{
			chunkTrx.rollback(chunkTrxSavepoint);
			chunkTrxSavepoint = null;
		}
		//
		// Case: Inherited transaction without any savepoint => do nothing
		else
		{
			// nothing
		}

		chunkTrx = null;

		// NOTE: no need to restore/reset thread transaction because the "execute" method will restore it anyways at the end
		// trxManager.setThreadInheritedTrxName(null);
	}

	@Override
	public String toString()
	{
		return "TrxItemChunkProcessorExecutor [processor=" + processor + ", exceptionHandler=" + exceptionHandler + ", onItemErrorPolicy=" + onItemErrorPolicy + ", useTrxSavepoints=" + useTrxSavepoints + ", chunkOpen=" + chunkOpen + ", chunkHasErrors=" + chunkHasErrors + ", chunkTrx=" + chunkTrx + ", chunkTrxIsLocal=" + chunkTrxIsLocal + ", chunkTrxSavepoint=" + chunkTrxSavepoint
				+ ", processorCtx=" + processorCtx + ", chunkCtx=" + chunkCtx + ", trxManager=" + trxManager + ", trxConstraintsBL=" + trxConstraintsBL + "]";
	}

}
