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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.spi.ITrxListener;

/**
 * Null {@link ITrxListenerManager} implementation
 * 
 * @author tsa
 * 
 */
public final class NullTrxListenerManager implements ITrxListenerManager
{
	public static final NullTrxListenerManager instance = new NullTrxListenerManager();

	private NullTrxListenerManager()
	{
		super();
	}

	/**
	 * @throws UnsupportedOperationException always
	 */
	@Override
	public void registerListener(ITrxListener listener)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException always
	 */
	@Override
	public void registerListener(boolean weak, ITrxListener listener)
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @throws UnsupportedOperationException always
	 */
	@Override
	public void onAfterCommit(Runnable runnable)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Does nothing
	 */
	@Override
	public void fireBeforeCommit(final ITrx trx)
	{
		// nothing
	}

	/**
	 * Does nothing
	 */
	@Override
	public void fireAfterCommit(final ITrx trx)
	{
		// nothing
	}

	/**
	 * Does nothing
	 */
	@Override
	public void fireAfterRollback(final ITrx trx)
	{
		// nothing
	}

	/**
	 * Does nothing
	 */
	@Override
	public void fireAfterClose(ITrx trx)
	{
		// nothing
	}
}
