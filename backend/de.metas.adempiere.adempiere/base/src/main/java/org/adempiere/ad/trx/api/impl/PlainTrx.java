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
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Plain implementation of {@link ITrx}.
 *
 * It does almost nothing, but please note that some checkings are still performed:
 * <ul>
 * <li>makes sure savepoints are consistent
 * </ul>
 * Hint: if you actually want to test trx related behavior, then you might use MockedTrxManager and MockedTrx instead.
 *
 * @author tsa
 *
 */
public class PlainTrx extends AbstractTrx
{
	private static final transient Logger logger = LogManager.getLogger(PlainTrx.class);

	private final List<ITrxSavepoint> activeSavepoints = new ArrayList<>();

	/** Debugging: history of transaction important actions like TrxStatus change */
	private final List<String> debugLog;

	public enum TrxStatus
	{
		NEW, STARTED, ROLLBACK, COMMIT, CLOSED
	}

	private TrxStatus trxStatus = TrxStatus.NEW;

	public PlainTrx(final ITrxManager trxManager, final String trxName, final boolean autoCommit)
	{
		super(trxManager, trxName, autoCommit);

		debugLog = getPlainTrxManager().isDebugTrxLog() ? new ArrayList<>() : null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("trxStatus", trxStatus)
				.addValue(super.toString())
				.add("debugLog", debugLog)
				.toString();
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
		final boolean started = super.start();

		setTrxStatus(TrxStatus.STARTED);

		return started;
	}

	@Override
	protected ITrxSavepoint createTrxSavepointNative(final String name) throws Exception
	{
		assertActive("Transaction shall be started before creating a savepoint");

		final PlainTrxSavepoint savepoint = new PlainTrxSavepoint(this, name);
		activeSavepoints.add(savepoint);

		logTrxAction("Savepoint created: " + name);

		return savepoint;
	}

	private void removeUntilSavepoint(@NonNull final ITrxSavepoint savepointToRemove)
	{
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

		logTrxAction("Savepoint released: " + savepoint);

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

		setTrxStatus(TrxStatus.ROLLBACK);

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

		logTrxAction("Rollback to savepoint: " + savepoint);

		return true;
	}

	@Override
	protected boolean commitNative(final boolean throwException) throws SQLException
	{
		//
		// Check if we can commit
		String commitDetailMsg = null;
		final TrxStatus trxStatusCurrent = getTrxStatus();
		if (trxStatusCurrent == TrxStatus.NEW)
		{
			// it's OK to just do nothing
			commitDetailMsg = "empty";
		}
		else
		{
			if (getPlainTrxManager().isFailCommitIfTrxNotStarted())
			{
				assertActive("Transaction shall be started before");
			}
		}

		// Clear all savepoints
		activeSavepoints.clear();

		setTrxStatus(TrxStatus.COMMIT, commitDetailMsg);

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

		setTrxStatus(TrxStatus.CLOSED);

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
			final String errmsgToUse = "Inconsistent transaction state: " + errmsg;
			throw new TrxException(errmsgToUse
					+ "\n Expected active: " + activeExpected
					+ "\n Actual active: " + activeActual
					+ "\n Trx: " + this);
		}
	}

	private TrxStatus getTrxStatus()
	{
		return trxStatus;
	}

	private void setTrxStatus(final TrxStatus trxStatus)
	{
		final String detailMsg = null;
		setTrxStatus(trxStatus, detailMsg);
	}

	private void setTrxStatus(final TrxStatus trxStatus, @Nullable final String detailMsg)
	{
		final TrxStatus trxStatusOld = this.trxStatus;
		this.trxStatus = trxStatus;

		// Log it
		if (debugLog != null)
		{
			final StringBuilder msg = new StringBuilder();
			msg.append(trxStatusOld).append("->").append(trxStatus);
			if (!Check.isEmpty(detailMsg, true))
			{
				msg.append(" (").append(detailMsg).append(")");
			}
			logTrxAction(msg.toString());
		}
	}

	private void logTrxAction(final String message)
	{
		if (debugLog == null)
		{
			return;
		}

		debugLog.add(message);
		logger.info("{}: trx action: {}", getTrxName(), message);
	}
}
