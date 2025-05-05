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

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * Helper interface which can assist you configure and execute an {@link ITrxItemProcessor}.<br>
 * Use {@link ITrxItemProcessorExecutorService#createExecutor()} to get an instance of this builder.
 * 
 * In case of exception, be default {@link ITrxItemProcessorExecutor#DEFAULT_ExceptionHandler} is used.
 *
 * @author tsa
 *
 * @param <IT> input processing type
 * @param <RT> processing result type
 */
public interface ITrxItemExecutorBuilder<IT, RT>
{
	/**
	 * Used to specify what shall be done when processing a chunk failed. See {@link ITrxItemExecutorBuilder#setOnItemErrorPolicy(OnItemErrorPolicy)}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 * task https://github.com/metasfresh/metasfresh/issues/302
	 */
	enum OnItemErrorPolicy
	{
		/**
		 * Call the processor's cancelChunk() method and commit the current trx.
		 */
		CancelChunkAndCommit,

		/**
		 * Call the processor's cancelChunk() method and roll back the current trx.<br>
		 * This is what the current implementation happened to do before we added {@link ITrxItemExecutorBuilder#setOnItemErrorPolicy(OnItemErrorPolicy)}, so we keep it as the default.
		 */
		CancelChunkAndRollBack,

		/**
		 * Just go on with the current chunk, assuming that<br>
		 * the processor's {@link ITrxItemExceptionHandler#onItemError(Exception, Object)} method already dealt with it, and no other items of the chunk are affected.
		 * This is what we need for issue #302
		 *
		 * task https://github.com/metasfresh/metasfresh/issues/302
		 */
		ContinueChunkAndCommit;

		public boolean isCancel()
		{
			return this == CancelChunkAndCommit
					|| this == CancelChunkAndRollBack;
		}
	}

	/**
	 * Builds the executor
	 *
	 * @return executor
	 */
	ITrxItemProcessorExecutor<IT, RT> build();

	/**
	 * Builds the executor, runs it on given items and return the result
	 */
	RT process(Iterator<? extends IT> items);

	default RT process(@NonNull final List<? extends IT> items)
	{
		return process(items.iterator());
	}

	/** Configures the context. Also see {@link #setContext(Properties, String)}. */
	ITrxItemExecutorBuilder<IT, RT> setContext(Properties ctx);

	/**
	 * Configures the context
	 *
	 * @param trxName use {@link org.adempiere.ad.trx.api.ITrx#TRXNAME_None} if you want each chunk to be processed in a single transaction.
	 */
	ITrxItemExecutorBuilder<IT, RT> setContext(Properties ctx, @Nullable String trxName);

	/** Configures the context */
	ITrxItemExecutorBuilder<IT, RT> setContext(ITrxItemProcessorContext processorCtx);

	/** Configures the processor to be used. Here you will inject your nice code ;) */
	ITrxItemExecutorBuilder<IT, RT> setProcessor(ITrxItemProcessor<IT, RT> processor);

	/** Configures the processor to be used. Here you will inject your nice code ;) */
	default ITrxItemExecutorBuilder<IT, RT> setProcessor(final Consumer<IT> processor)
	{
		Preconditions.checkNotNull(processor, "processor is null");

		setProcessor(new TrxItemProcessorAdapter<IT, RT>()
		{
			@Override
			public void process(IT item) throws Exception
			{
				processor.accept(item);
			}
		});
		return this;
	}

	/**
	 * Sets exception handler to be used if processing fails.
	 *
	 * @see ITrxItemProcessorExecutor#setExceptionHandler(ITrxItemExceptionHandler)
	 * @see ITrxItemProcessorExecutor#DEFAULT_ExceptionHandler
	 */
	ITrxItemExecutorBuilder<IT, RT> setExceptionHandler(ITrxItemExceptionHandler exceptionHandler);

	/**
	 * Specifies what to do if processing an item fails.
	 *
	 * @see ITrxItemProcessorExecutor#DEFAULT_OnItemErrorPolicy for the default value.
	 *
	 * task https://github.com/metasfresh/metasfresh/issues/302
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
	 * This setting is only relevant, if the executor's parent-trx is null (see {@link #setContext(Properties, String)}).
	 * <p>
	 * If <code>true</code> and the executor has an external not-null transaction,
	 * then the executor will create a {@link ITrxSavepoint} when starting a new chunk.
	 * If a chunk fails and is canceled, the executor will roll back to the savepoint.
	 * Without a savepoint, the executor will not roll back.
	 *
	 *
	 * @see ITrxItemProcessorExecutor#setUseTrxSavepoints(boolean)
	 */
	ITrxItemExecutorBuilder<IT, RT> setUseTrxSavepoints(boolean useTrxSavepoints);
}
