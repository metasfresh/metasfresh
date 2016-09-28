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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

/**
 * Plain implementation of {@link ITrx}.
 *
 * It does almost nothing, but please note that some checkings are still performed:
 * <ul>
 * <li>makes sure savepoints are consistent
 * </ul>
 * Hint: if you actually want to test trx related behavior, then you might use {@link MockedTrxManager} and {@link MockedTrx} instead.
 *
 * @author tsa
 *
 */
public class PlainTrx extends AbstractTrx
{
	private final List<ITrxSavepoint> activeSavepoints = new ArrayList<ITrxSavepoint>();

	public PlainTrx(final ITrxManager trxManager, final String trxName)
	{
		super(trxManager, trxName);
	}

	public PlainTrxManager getPlainTrxManager()
	{
		return (PlainTrxManager)super.getTrxManager();
	}

	@Override
	public boolean start()
	{
		// Make sure we are not calling start() twice.
		assertNotActive("Transaction shall not be started");
		return super.start();
	}

	@Override
	protected ITrxSavepoint createTrxSavepointNative(final String name) throws Exception
	{
		assertActive("Transaction shall be started before creating a savepoint");

		final PlainTrxSavepoint savepoint = new PlainTrxSavepoint(this, name);
		activeSavepoints.add(savepoint);
		return savepoint;
	}

	private final void removeUntilSavepoint(final ITrxSavepoint savepointToRemove)
	{
		Check.assumeNotNull(savepointToRemove, "savepointToRemove not null");

		//
		// Iterate all active savepoints,
		// Find the savepoint we were asked to remove,
		// and remove all savepoints from that point on.
		boolean remove = false;
		for (final Iterator<ITrxSavepoint> it = activeSavepoints.iterator(); it.hasNext();)
		{
			final ITrxSavepoint savepoint = it.next();

			// Activate "remove" as soon as we found our savepoint
			if (!remove && savepoint == savepointToRemove)
			{
				remove = true;
			}

			// Remove all savepoints from the point "remove" was enabled.
			if (remove)
			{
				it.remove();
			}
		}

		// Fail if savepoint was not found
		if (!remove)
		{
			throw new TrxException("Could not release savepoint because it's not in our list of active savepoints"
					+ "\n Trx: " + this
					+ "\n Savepoint to remove: " + savepointToRemove
					+ "\n Active savepoints: " + activeSavepoints);
		}
	}

	@Override
	protected boolean releaseSavepointNative(final ITrxSavepoint savepoint)
	{
		assertActive("Transaction shall be started before releasing a savepoint");

		removeUntilSavepoint(savepoint);

		return true;
	}

	public boolean hasActiveSavepoints()
	{
		return !activeSavepoints.isEmpty();
	}

	@Override
	protected boolean rollbackNative(final boolean throwException) throws SQLException
	{
		if (getPlainTrxManager().isFailRollbackIfTrxNotStarted())
		{
			assertActive("Transaction shall be started before rollbackNative; this=" + this);
		}

		// Clear all savepoints
		activeSavepoints.clear();
		return true;
	}

	@Override
	protected boolean rollbackNative(final ITrxSavepoint savepoint)
	{
		if (getPlainTrxManager().isFailRollbackIfTrxNotStarted())
		{
			assertActive("Transaction shall be started before rollbackNative; this=" + this);
		}
		removeUntilSavepoint(savepoint);

		return true;
	}

	@Override
	protected boolean commitNative(final boolean throwException) throws SQLException
	{
		if (getPlainTrxManager().isFailCommitIfTrxNotStarted())
		{
			assertActive("Transaction shall be started before");
		}
		// Clear all savepoints
		activeSavepoints.clear();

		return true;
	}

	@Override
	protected boolean closeNative()
	{
		// Assume that at this point the transaction is no longer active because it was never active or because it was commit/rollback first.
		assertNotActive("Transaction shall not be active at this point");

		if (!activeSavepoints.isEmpty())
		{
			throw new TrxException("Inconsistent transaction state: We were asked for close() but we still have active savepoints"
					+ "\n Trx: " + this
					+ "\n Active savepoints: " + activeSavepoints);
		}

		return true;
	}

	protected final void assertActive(final String errmsg)
	{
		assertActive(true, errmsg);
	}

	public List<ITrxSavepoint> getActiveSavepoints()
	{
		return ImmutableList.copyOf(activeSavepoints);
	}

	protected final void assertNotActive(final String errmsg)
	{
		assertActive(false, errmsg);
	}

	protected void assertActive(final boolean activeExpected, final String errmsg)
	{
		final boolean activeActual = isActive();
		if (activeActual != activeExpected)
		{
			String errmsgToUse = "Inconsistent transaction state: " + errmsg;
			throw new TrxException(errmsgToUse
					+ "\n Expected active: " + activeExpected
					+ "\n Actual active: " + activeActual
					+ "\n Trx: " + this);
		}
	}
}
