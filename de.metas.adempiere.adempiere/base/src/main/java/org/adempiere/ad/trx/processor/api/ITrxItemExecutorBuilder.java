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

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;

/**
 * Helper interface which can assist you configure and execute an {@link ITrxItemProcessor}.<br>
 * Use {@link ITrxItemProcessorExecutorService#createExecutor()} to get an instance of this builder.
 *
 * @author tsa
 *
 * @param <IT> input processing type
 * @param <RT> processing result type
 */
public interface ITrxItemExecutorBuilder<IT, RT>
{
	/**
	 * Used to specify what shall be done when processing a chunk failed.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 * @task https://github.com/metasfresh/metasfresh/issues/302
	 */
	enum OnItemErrorPolicy
	{
		/**
		 * Call the processor's cancelChunk() method and commit the current trx
		 */
		CancelChunkAndCommit,

		/**
		 * This is what the current implementation happened to do before we added {@link ITrxItemExecutorBuilder#setOnItemErrorPolicy(OnItemErrorPolicy)}, so we keep it as the default.
		 */
		CancelChunkAndRollBack,

		/**
		 * Just go on with the current chunk.
		 * The processor's {@link ITrxItemExceptionHandler#onItemError(Exception, Object)} method already dealt with it, and no other items of the chunk are affected.
		 * This is what we need for issue #302
		 *
		 * @task https://github.com/metasfresh/metasfresh/issues/302
		 */
		ContinueChunkAndCommit,

		// /**
		// * this might be useful in future..discard what we did so far from the chunk, but process its further items..
		// */
		// ContinueChunkAndRollBack
	}

	/**
	 * Builds the executor
	 *
	 * @return executor
	 */
	ITrxItemProcessorExecutor<IT, RT> build();

	/**
	 * Builds the executor, runs it on given items and return the result
	 *
	 * @param items
	 * @return result
	 */
	RT process(Iterator<? extends IT> items);

	/** Configures the context. Also see {@link #setContext(Properties, String)}. */
	ITrxItemExecutorBuilder<IT, RT> setContext(Properties ctx);

	/**
	 * Configures the context
	 *
	 * @param ctx
	 * @param trxName use {@link org.adempiere.ad.trx.api.ITrx#TRXNAME_None} if you want each chunk to be processed in a single transaction.
	 */
	ITrxItemExecutorBuilder<IT, RT> setContext(Properties ctx, String trxName);

	/** Configures the context */
	ITrxItemExecutorBuilder<IT, RT> setContext(ITrxItemProcessorContext processorCtx);

	/** Configures the processor to be used. Here you will inject your nice code ;) */
	ITrxItemExecutorBuilder<IT, RT> setProcessor(ITrxItemProcessor<IT, RT> processor);

	/**
	 * Sets exception handler to be used if processing fails.
	 *
	 * @param exceptionHandler
	 * @see ITrxItemProcessorExecutor#setExceptionHandler(ITrxItemExceptionHandler)
	 */
	ITrxItemExecutorBuilder<IT, RT> setExceptionHandler(ITrxItemExceptionHandler exceptionHandler);

	/**
	 * Specifies what to do if processing an item fails.
	 *
	 * @param onItemErrorPolicy
	 * @return
	 * @task https://github.com/metasfresh/metasfresh/issues/302
	 */
	ITrxItemExecutorBuilder<IT, RT> setOnItemErrorPolicy(OnItemErrorPolicy onItemErrorPolicy);

	/**
	 * Sets how many items we shall maximally process in one batch/transaction.
	 *
	 * @param itemsPerBatch items per batch or {@link Integer#MAX_VALUE} if you want to not enforce the restriction.
	 */
	ITrxItemExecutorBuilder<IT, RT> setItemsPerBatch(int itemsPerBatch);

	/**
	 * Sets if the executor shall use transaction savepoints on individual chunks internally.
	 *
	 * @param useTrxSavepoints
	 * @see ITrxItemProcessorExecutor#setUseTrxSavepoints(boolean)
	 */
	ITrxItemExecutorBuilder<IT, RT> setUseTrxSavepoints(boolean useTrxSavepoints);
}
