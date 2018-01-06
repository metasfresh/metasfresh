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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.exceptions.TrxException;

import lombok.NonNull;

/**
 * An {@link ITrxListenerManager} implementation which directly executes {@link ITrxListener#beforeCommit(ITrx)} and {@link ITrxListener#afterCommit(ITrx)} when a listener is registered.
 *
 * @author tsa
 *
 */
/* package */final class AutoCommitTrxListenerManager implements ITrxListenerManager
{
	public static final transient AutoCommitTrxListenerManager instance = new AutoCommitTrxListenerManager();

	private AutoCommitTrxListenerManager()
	{
	}

	@Override
	public void registerListener(@NonNull final RegisterListenerRequest listener)
	{
		execute(listener);
	}
	
	@Override
	public boolean canRegisterOnTiming(@NonNull final TrxEventTiming timing)
	{
		// any timing is accepted because we are executing directly
		return true;
	}

	private final void execute(final RegisterListenerRequest listener)
	{
		if (!listener.isActive())
		{
			return; // nothing to do
		}
		if (!TrxEventTiming.BEFORE_COMMIT.equals(listener.getTiming())
				&& !TrxEventTiming.AFTER_COMMIT.equals(listener.getTiming())
				&& !TrxEventTiming.AFTER_CLOSE.equals(listener.getTiming()))
		{
			return; // nothing to do
		}
		try
		{
			listener.getHandlingMethod().onTransactionEvent(ITrx.TRX_None);
		}
		catch (Exception e)
		{
			throw new TrxException("Caught " + e.getLocalizedMessage() + " for listener=" + listener, e);
		}
	}

	@Override
	public void fireBeforeCommit(final ITrx trx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fireAfterCommit(final ITrx trx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fireAfterRollback(final ITrx trx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void fireAfterClose(ITrx trx)
	{
		throw new UnsupportedOperationException();
	}
}
