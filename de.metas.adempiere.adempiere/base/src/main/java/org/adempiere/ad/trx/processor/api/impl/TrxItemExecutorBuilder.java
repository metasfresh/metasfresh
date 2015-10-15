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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

/* package*/class TrxItemExecutorBuilder<IT, RT> implements ITrxItemExecutorBuilder<IT, RT>
{
	// services
	private final transient ITrxItemProcessorExecutorService executorService;
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private Properties _ctx = null;
	private String _trxName = null;
	private ITrxItemProcessor<IT, RT> _processor;
	private ITrxItemExceptionHandler _exceptionHandler = FailTrxItemExceptionHandler.instance;

	public TrxItemExecutorBuilder(final ITrxItemProcessorExecutorService executorService)
	{
		super();

		Check.assumeNotNull(executorService, "executorService not null");
		this.executorService = executorService;
	}

	@Override
	public RT process(final Iterator<? extends IT> items)
	{
		// Create processing context
		final ITrxItemProcessorContext processorCtx = createProcessorContext();

		// Create and configure the executor
		final ITrxItemProcessorExecutor<IT, RT> executor = executorService.createExecutor(processorCtx, getProcessor());
		executor.setExceptionHandler(getExceptionHandler());

		// Process items and return the result
		final RT result = executor.execute(items);
		return result;
	}

	private final ITrxItemProcessorContext createProcessorContext()
	{
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(getCtx(), getTrx());
		return processorCtx;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setContext(final Properties ctx)
	{
		this._ctx = ctx;
		return this;
	}

	@Override
	public ITrxItemExecutorBuilder<IT, RT> setContext(final Properties ctx, final String trxName)
	{
		this._ctx = ctx;
		this._trxName = trxName;
		return this;
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "ctx is set");
		return _ctx;
	}

	private final ITrx getTrx()
	{
		return trxManager.getTrx(_trxName);
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
	public TrxItemExecutorBuilder<IT, RT> setExceptionHandler(ITrxItemExceptionHandler exceptionHandler)
	{
		this._exceptionHandler = exceptionHandler;
		return this;
	}

	public final ITrxItemExceptionHandler getExceptionHandler()
	{
		return _exceptionHandler;
	}

}
