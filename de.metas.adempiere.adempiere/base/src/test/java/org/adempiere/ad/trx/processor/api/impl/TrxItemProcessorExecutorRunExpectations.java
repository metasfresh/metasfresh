package org.adempiere.ad.trx.processor.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder.OnItemErrorPolicy;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.LoggerTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnableAdapter;

import junit.framework.AssertionFailedError;

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

/**
 * Executes a given {@link ITrxItemChunkProcessor} and asserts expectations.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <IT>
 * @param <RT>
 */
class TrxItemProcessorExecutorRunExpectations<IT, RT>
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private ITrxItemChunkProcessor<IT, RT> _processor;
	private boolean _runInTrx = false;
	private boolean useTrxSavepoints = ITrxItemProcessorExecutor.DEFAULT_UseTrxSavepoints;
	//
	private List<IT> _items;
	private RT expectedResult;
	private Class<?> expectedExceptionClass;
	private OnItemErrorPolicy onItemErrorPolicy;

	/**
	 * The exception handler that is used when we test the executor. Extend as needed.
	 */
	private final ITrxItemExceptionHandler exceptionHandler = new LoggerTrxItemExceptionHandler()
	{
		@Override
		public void onItemError(Exception e, Object item)
		{
			super.onItemError(e, item);
			if (item instanceof Item)
			{
				((Item)item).setOnItemErrorException(e);
			}
		}
	};

	public TrxItemProcessorExecutorRunExpectations()
	{
		super();
	}

	/**
	 * Executes the executor and asserts that all expectations hold true.
	 */
	public void assertExpected()
	{
		//
		// Get the running parameters
		final ITrxItemChunkProcessor<IT, RT> processor = getProcessor();
		final List<IT> items = getItems();

		//
		// Thread Inherited transaction: set a dummy transaction and later check if it was restored
		final String threadIneritedTrxNameBefore = "ThreadIneritedTrxName_before_Processing_" + UUID.randomUUID();
		trxManager.setThreadInheritedTrxName(threadIneritedTrxNameBefore);

		//
		// Prepare the trx runnable
		final IMutable<RT> resultActual = new Mutable<>();
		final IMutable<Exception> exceptionActual = new Mutable<>();
		final TrxRunnable trxRunnable = new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final ITrx trx = trxManager.getTrx(localTrxName);
				assertEquals("Null transaction", !isRunInTrx(), trxManager.isNull(trx));

				//
				// Create the context
				final TrxItemProcessorContext processorCtx = new TrxItemProcessorContext(Env.getCtx());
				processorCtx.setTrx(trx);

				//
				// Create the executor
				final TrxItemChunkProcessorExecutor<IT, RT> executor = new TrxItemChunkProcessorExecutor<>(
						processorCtx,    // processing context
						processor,    // processor
						exceptionHandler,
						onItemErrorPolicy,
						useTrxSavepoints);

				//
				// Run the executor and gather the result
				try
				{
					final RT result = executor.execute(items.iterator());
					resultActual.setValue(result);
				}
				catch (final Exception e)
				{
					exceptionActual.setValue(e);

					// In case execution fails, we need to grab our result directly from processor
					resultActual.setValue(processor.getResult());
				}
			}
		};

		//
		// Execute the runnable
		if (isRunInTrx())
		{
			trxManager.run(trxRunnable);
		}
		else
		{
			trxManager.runOutOfTransaction(trxRunnable);
		}

		//
		// Check the result
		assertException(getExpectedExceptionClass(), exceptionActual.getValue());
		assertEquals("Invalid result", getExpectedResult(), resultActual.getValue());

		//
		// Make sure all all transactions were closed
		final List<ITrx> activeTrxs = trxManager.getActiveTransactionsList();
		assertTrue("All transactions shall be closed: " + activeTrxs, activeTrxs.isEmpty());

		//
		// Make sure the thread inherited transaction was restored
		final String threadIneritedTrxNameAfter = trxManager.getThreadInheritedTrxName();
		assertEquals("ThreadInherited transaction shall be restored to the value that it was before",
				threadIneritedTrxNameBefore,    // expected,
				threadIneritedTrxNameAfter // actual
		);
		trxManager.setThreadInheritedTrxName(null); // just reset it to have it clean
	}

	private static void assertException(final Class<?> expectedExceptionClass, final Exception exceptionActual)
	{
		if (expectedExceptionClass == null)
		{
			if (exceptionActual == null)
			{
				// no exception expected, no exception got => OK
				return;
			}
			else
			{
				// no exception expected but we got an exception
				final AssertionFailedError ex = new AssertionFailedError("We were not expecting an exception but we got: " + exceptionActual.toString());
				ex.initCause(exceptionActual);
				throw ex;
			}
		}
		else
		{
			if (exceptionActual == null)
			{
				// exception expected but we got no exception
				fail("We were expecting expection " + expectedExceptionClass + " but we got nothing");
				return;
			}
			else if (expectedExceptionClass.isAssignableFrom(exceptionActual.getClass()))
			{
				// exception expected and we got it, of same type => OK
				return;
			}
			else
			{
				// exception expected but we got an exception of different type
				final AssertionFailedError ex = new AssertionFailedError("We were expecting an exception of type " + expectedExceptionClass
						+ " but we got an exception of type " + exceptionActual.getClass());
				ex.initCause(exceptionActual);
				throw ex;
			}
		}
	}

	public TrxItemProcessorExecutorRunExpectations<IT, RT> setProcessor(final ITrxItemChunkProcessor<IT, RT> processor)
	{
		this._processor = processor;
		return this;
	}

	private final ITrxItemChunkProcessor<IT, RT> getProcessor()
	{
		return _processor;
	}

	public TrxItemProcessorExecutorRunExpectations<IT, RT> setItems(final List<IT> items)
	{
		this._items = items;
		return this;
	}

	private List<IT> getItems()
	{
		return _items;
	}

	public TrxItemProcessorExecutorRunExpectations<IT, RT> setExpectedResult(final RT expectedResult)
	{
		this.expectedResult = expectedResult;
		return this;
	}

	private RT getExpectedResult()
	{
		return expectedResult;
	}

	public TrxItemProcessorExecutorRunExpectations<IT, RT> setExpectedExceptionClass(final Class<?> expectedExceptionClass)
	{
		this.expectedExceptionClass = expectedExceptionClass;
		return this;
	}

	private Class<?> getExpectedExceptionClass()
	{
		return expectedExceptionClass;
	}

	public TrxItemProcessorExecutorRunExpectations<IT, RT> setRunInTrx(boolean runInTrx)
	{
		this._runInTrx = runInTrx;
		return this;
	}

	private boolean isRunInTrx()
	{
		return _runInTrx;
	}

	public TrxItemProcessorExecutorRunExpectations<IT, RT> setUseTrxSavepoints(final boolean useTrxSavepoints)
	{
		this.useTrxSavepoints = useTrxSavepoints;
		return this;
	}

	public TrxItemProcessorExecutorRunExpectations<IT, RT> setOnItemErrorPolicy(OnItemErrorPolicy onItemErrorPolicy)
	{
		this.onItemErrorPolicy = onItemErrorPolicy;
		return this;
	}

}
