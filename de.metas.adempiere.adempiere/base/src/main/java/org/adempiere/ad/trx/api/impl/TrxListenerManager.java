package org.adempiere.ad.trx.api.impl;

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


import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.ad.trx.spi.ITrxListener;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.util.WeakList;

/**
 * Default {@link ITrxListenerManager} implementation
 *
 * @author tsa
 *
 */
public class TrxListenerManager implements ITrxListenerManager
{
	private static final Logger logger = LogManager.getLogger(TrxListenerManager.class);

	private volatile WeakList<ITrxListener> listeners = null; // NOSONAR ts: i think volatile is required here

	public TrxListenerManager()
	{
	}

	@Override
	public void registerListener(final ITrxListener listener)
	{
		final boolean weak = false;
		registerListener(weak, listener);
	}

	@Override
	public void onAfterFirstCommit(Runnable runnable)
	{
		registerListener(new TrxListenerAdapter()
		{
			@Override
			public String toString()
			{
				return MoreObjects.toStringHelper("runOnAfterFirstCommit").addValue(runnable).toString();
			}
			
			@Override
			public void afterCommit(final ITrx trx)
			{
				runnable.run();
				listeners.remove(this);
			}
		});
	}
	
	@Override
	public void registerListener(final boolean weak, final ITrxListener listener)
	{
		if (listener == null || !listener.isActive())
		{
			return;
		}

		if (listeners == null)
		{
			listeners = new WeakList<ITrxListener>();
			listeners.setWeakDefault(false);
		}

		// NOTE: this checking is quite expensive and in most of the cases we are registering annonymous inner classes
		// so i think is quite save to take it down
		// if (listeners.contains(listener))
		// {
		// return;
		// }

		listeners.add(listener, weak);
	}

	@Override
	public void fireBeforeCommit(final ITrx trx)
	{
		if (listeners == null)
		{
			return;
		}

		for (final ITrxListener listener : listeners.hardList())
		{
			// shouldn't be necessary but in fact i just had an NPE at this place
			if (listener == null || !listener.isActive())
			{
				continue;
			}

			// Execute the "beforeCommit"
			try
			{
				listener.beforeCommit(trx);
			}
			catch (final Exception e)
			{
				throw new TrxException("Error on before commit", e);
			}
		}
	}

	@Override
	public void fireAfterCommit(final ITrx trx)
	{
		if (listeners == null)
		{
			return;
		}

		for (final ITrxListener listener : listeners.hardList())
		{
			// shouldn't be necessary but in fact i just had an NPE at this place
			if (listener == null || !listener.isActive())
			{
				continue;
			}

			//
			// Execute the "afterCommit", but don't fail because we are not allowed to fail by method's contract
			try
			{
				listener.afterCommit(trx);
			}
			catch (Exception e)
			{
				logger.error("Error while invoking afterCommit on transaction " + trx + " using " + listener + ". Error was discarded.", e);
			}
		}
	}

	@Override
	public void fireAfterRollback(final ITrx trx)
	{
		if (listeners == null)
		{
			return;
		}

		for (final ITrxListener listener : listeners.hardList())
		{
			// shouldn't be necessary but in fact i just had an NPE at this place
			if (listener == null || !listener.isActive())
			{
				continue;
			}

			//
			// Execute the "afterRollback", but don't fail because we are not allowed to fail by method's contract
			try
			{
				listener.afterRollback(trx);
			}
			catch (Exception e)
			{
				logger.error("Error while invoking afterRollback on transaction " + trx + " using " + listener + ". Error was discarded.", e);
			}
		}
	}


	@Override
	public void fireAfterClose(final ITrx trx)
	{
		if (listeners == null)
		{
			return;
		}

		for (final ITrxListener listener : listeners.hardList())
		{
			// shouldn't be necessary but in fact i just had an NPE at this place
			if (listener == null || !listener.isActive())
			{
				continue;
			}

			//
			// Execute the "afterClose", but don't fail because we are not allowed to fail by method's contract
			try
			{
				listener.afterClose(trx);
			}
			catch (Exception e)
			{
				logger.error("Error while invoking afterClose on transaction " + trx + " using " + listener + ". Error was discarded.", e);
			}
		}
	}
}
