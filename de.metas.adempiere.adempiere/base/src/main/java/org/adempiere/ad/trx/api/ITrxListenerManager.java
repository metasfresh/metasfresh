package org.adempiere.ad.trx.api;

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

import org.adempiere.ad.trx.spi.ITrxListener;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

/**
 * Transactions Listeners Mananger.<br>
 * Use {@link ITrxManager#getTrxListenerManager(String)} or {@link ITrxManager#getTrxListenerManagerOrAutoCommit(String)} to get your instance.
 *
 * @author tsa
 *
 */
public interface ITrxListenerManager
{
	/**
	 * Registers a transaction listener (Hard Reference)
	 *
	 * @param listener
	 */
	void registerListener(ITrxListener listener);

	/**
	 * Registers a transaction listener
	 *
	 * @param weak if true then a weak reference to listener will be registered.
	 * @param listener
	 */
	void registerListener(boolean weak, ITrxListener listener);

	/**
	 * Convenient way to register a listener which shall be called on "after commit".
	 * 
	 * @param runnable
	 */
	default void onAfterCommit(final Runnable runnable)
	{
		Check.assumeNotNull(runnable, "Parameter runnable is not null");
		registerListener(new TrxListenerAdapter()
		{
			@Override
			public String toString()
			{
				return MoreObjects.toStringHelper("runOnAfterCommit").addValue(runnable).toString();
			}
			
			@Override
			public void afterCommit(final ITrx trx)
			{
				runnable.run();
			}
		});
	}

	void fireBeforeCommit(ITrx trx);

	void fireAfterCommit(ITrx trx);

	void fireAfterRollback(ITrx trx);

	void fireAfterClose(ITrx trx);

}
