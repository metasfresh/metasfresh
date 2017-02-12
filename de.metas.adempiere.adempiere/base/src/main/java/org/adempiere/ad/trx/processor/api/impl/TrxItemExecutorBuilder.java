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

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

/* package */class TrxItemExecutorBuilder<IT, RT> implements ITrxItemExecutorBuilder<IT, RT>
{
	// services
	private final transient TrxItemProcessorExecutorService executorService;
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	// Context
	private ITrxItemProcessorContext _processorCtx;
	private Properties _ctx = null;
	private String _trxName = null;

	private ITrxItemProcessor<IT, RT> _processor;
	private ITrxItemExceptionHandler _exceptionHandler = ITrxItemProcessorExecutor.DEFAULT_ExceptionHandler;

	private boolean _useTrxSavepoints =  ITrxItemProcessorExecutor.DEFAULT_UseTrxSavepoints;

	private Integer itemsPerBatch = null;

	private OnItemErrorPolicy _onItemErrorPolicy = ITrxItemProcessorExecutor.DEFAULT_OnItemErrorPolicy; // #302

	public TrxItemExecutorBuilder(final TrxItemProcessorExecutorService executorService)
	{
		super();

		Check.assumeNotNull(executorService, "executorService not null");
		this.executorService = executorService;
	}

	@Override
	public RT process(final Iterator<? extends IT> items)
	{
		// Create and configure the executor
		final ITrxItemProcessorExecutor<IT, RT> executor = build();

		// Process items and return the result
		final RT result = executor.execute(items);
		return result;
	}

	@Override
	public ITrxItemProcessorExecutor<IT, RT> build()
	{
		// Create processing context
		final ITrxItemProcessorContext processorCtx = createProcessorContext();

		// Get the processor and wrap it
		final ITrxItemChunkProcessor<IT, RT> processor = createProcessor();

		// Create and configure the executor
		final TrxItemChunkProcessorExecutor<IT, RT> executor = new TrxItemChunkProcessorExecutor<IT, RT>(processorCtx,
				processor,
				getExceptionHandler(),
				_onItemErrorPolicy,
				_useTrxSavepoints);

		return executor;
	}

	private final ITrxItemChunkProcessor<IT, RT> createProcessor()
	{
		ITrxItemProcessor<IT, RT> processor = getProcessor();

		if (itemsPerBatch != null)
		{
			processor = FixedBatchTrxItemProcessor.of(processor, itemsPerBatch);
		}

		return TrxItemProcessor2TrxItemChunkProcessorWrapper.wrapIfNeeded(processor);
	}

	private final ITrxItemProcessorContext createProcessorContext()
	{
		if (_processorCtx != null)
		{
			return _processorCtx;
		}

		Check.assumeNotNull(_ctx, "ctx is set");
		final ITrx trx = trxManager.getTrx(_trxName);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(_ctx, trx);
		return processorCtx;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setContext(final Properties ctx)
	{
		setContext(ctx, ITrx.TRXNAME_None);
		return this;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setContext(final Properties ctx, final String trxName)
	{
		this._processorCtx = null;
		this._ctx = ctx;
		this._trxName = trxName;
		return this;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setContext(final ITrxItemProcessorContext processorCtx)
	{
		this._processorCtx = processorCtx;
		this._ctx = null;
		this._trxName = null;
		return this;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setProcessor(final ITrxItemProcessor<IT, RT> processor)
	{
		this._processor = processor;
		return this;
	}

	private final ITrxItemProcessor<IT, RT> getProcessor()
	{
		Check.assumeNotNull(_processor, "processor is set");
		return _processor;
	}

	@Override
	public TrxItemExecutorBuilder<IT, RT> setExceptionHandler(final ITrxItemExceptionHandler exceptionHandler)
	{
		Check.assumeNotNull(exceptionHandler, "exceptionHandler not null");
		this._exceptionHandler = exceptionHandler;
		return this;
	}

	private final ITrxItemExceptionHandler getExceptionHandler()
	{
		return _exceptionHandler;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setOnItemErrorPolicy(OnItemErrorPolicy onItemErrorPolicy)
	{
		this._onItemErrorPolicy = onItemErrorPolicy;
		return this;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setItemsPerBatch(final int itemsPerBatch)
	{
		if (itemsPerBatch == Integer.MAX_VALUE)
		{
			this.itemsPerBatch = null;
		}
		else
		{
			this.itemsPerBatch = itemsPerBatch;
		}
		return this;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setUseTrxSavepoints(final boolean useTrxSavepoints)
	{
		this._useTrxSavepoints = useTrxSavepoints;
		return this;
	}
}
