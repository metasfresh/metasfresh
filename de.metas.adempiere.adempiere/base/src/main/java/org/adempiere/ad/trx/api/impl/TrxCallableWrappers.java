package org.adempiere.ad.trx.api.impl;

import java.util.concurrent.Callable;

import org.adempiere.ad.trx.api.ITrx;
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

	public static TrxCallable<Void> wrapIfNeeded(final TrxRunnable trxRunnable)
	{
		if (trxRunnable == null)
		{
			return null;
		}

		if (trxRunnable instanceof TrxRunnable2)
		{
			return wrapIfNeeded((TrxRunnable2)trxRunnable);
		}

		return new TrxCallable<Void>()
		{
			@Override
			public String toString()
			{
				return "TrxCallableWrappers[" + trxRunnable + "]";
			}

			@Override
			public Void call() throws Exception
			{
				trxRunnable.run(ITrx.TRXNAME_ThreadInherited);
				return null;
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

	public static TrxCallable<Void> wrapIfNeeded(final TrxRunnable2 trxRunnable)
	{
		if (trxRunnable == null)
		{
			return null;
		}

		return new TrxCallable<Void>()
		{
			@Override
			public String toString()
			{
				return "TrxCallableWrappers[" + trxRunnable + "]";
			}

			@Override
			public Void call() throws Exception
			{
				trxRunnable.run(ITrx.TRXNAME_ThreadInherited);
				return null;
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
}
