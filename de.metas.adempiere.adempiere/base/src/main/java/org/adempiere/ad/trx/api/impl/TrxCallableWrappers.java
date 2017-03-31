package org.adempiere.ad.trx.api.impl;

import java.util.concurrent.Callable;

import org.adempiere.ad.trx.api.TrxCallable;
import org.adempiere.ad.trx.api.TrxCallableAdapter;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnable2;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/* package */final class TrxCallableWrappers
{
	private TrxCallableWrappers()
	{
	}
	
	public static TrxCallableWithTrxName<Void> wrapIfNeeded(final Runnable runnable)
	{
		if(runnable == null)
		{
			return null;
		}
		
		return new TrxCallableWithTrxName<Void>()
		{
			@Override
			public String toString()
			{
				return "TrxCallableWithTrxName-wrapper[" + runnable + "]";
			}

			@Override
			public Void call(final String localTrxName) throws Exception
			{
				runnable.run();
				return null;
			}

			@Override
			public Void call() throws Exception
			{
				throw new IllegalStateException("This method shall not be called");
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
				throw e;
			}

			@Override
			public void doFinally()
			{
				// nothing
			}
		};
	}

	public static TrxCallableWithTrxName<Void> wrapIfNeeded(final TrxRunnable trxRunnable)
	{
		if (trxRunnable == null)
		{
			return null;
		}

		if (trxRunnable instanceof TrxRunnable2)
		{
			return wrapIfNeeded((TrxRunnable2)trxRunnable);
		}

		return new TrxCallableWithTrxName<Void>()
		{
			@Override
			public String toString()
			{
				return "TrxCallableWithTrxName-wrapper[" + trxRunnable + "]";
			}

			@Override
			public Void call(final String localTrxName) throws Exception
			{
				trxRunnable.run(localTrxName);
				return null;
			}

			@Override
			public Void call() throws Exception
			{
				throw new IllegalStateException("This method shall not be called");
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
				throw e;
			}

			@Override
			public void doFinally()
			{
				// nothing
			}
		};
	}

	public static TrxCallableWithTrxName<Void> wrapIfNeeded(final TrxRunnable2 trxRunnable)
	{
		if (trxRunnable == null)
		{
			return null;
		}

		return new TrxCallableWithTrxName<Void>()
		{
			@Override
			public String toString()
			{
				return "TrxCallableWithTrxName-wrapper[" + trxRunnable + "]";
			}

			@Override
			public Void call(final String localTrxName) throws Exception
			{
				trxRunnable.run(localTrxName);
				return null;
			}

			@Override
			public Void call() throws Exception
			{
				throw new IllegalStateException("This method shall not be called");
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
				return trxRunnable.doCatch(e);
			}

			@Override
			public void doFinally()
			{
				trxRunnable.doFinally();
			}
		};
	}

	public static <T> TrxCallable<T> wrapIfNeeded(final Callable<T> callable)
	{
		if (callable == null)
		{
			return null;
		}

		if (callable instanceof TrxCallable)
		{
			final TrxCallable<T> trxCallable = (TrxCallable<T>)callable;
			return trxCallable;
		}

		return new TrxCallableAdapter<T>()
		{
			@Override
			public String toString()
			{
				return "TrxCallableWrappers[" + callable + "]";
			}

			@Override
			public T call() throws Exception
			{
				return callable.call();
			}
		};
	}

	public static <T> TrxCallableWithTrxName<T> wrapAsTrxCallableWithTrxNameIfNeeded(final TrxCallable<T> callable)
	{
		if (callable == null)
		{
			return null;
		}

		if (callable instanceof TrxCallableWithTrxName)
		{
			final TrxCallableWithTrxName<T> callableWithTrxName = (TrxCallableWithTrxName<T>)callable;
			return callableWithTrxName;
		}

		return new TrxCallableWithTrxName<T>()
		{
			@Override
			public String toString()
			{
				return "TrxCallableWithTrxName-wrapper[" + callable + "]";
			}
			
			@Override
			public T call(final String localTrxName) throws Exception
			{
				return callable.call();
			}

			@Override
			public T call() throws Exception
			{
				throw new IllegalStateException("This method shall not be called");
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
				return callable.doCatch(e);
			}

			@Override
			public void doFinally()
			{
				callable.doFinally();
			}
		};
	}
}
