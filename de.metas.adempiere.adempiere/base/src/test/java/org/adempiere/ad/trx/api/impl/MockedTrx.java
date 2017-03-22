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
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.compiere.util.Util;
import org.junit.Assert;
import org.junit.Ignore;

@Ignore
public class MockedTrx extends PlainTrx
{
	/** exception this mock shall throw on rollback */
	private Throwable onRollbackException;

	private List<Exception> rollbacks = new ArrayList<>();
	private List<ITrxSavepoint> rollbacksToSavePoint = new ArrayList<>();

	private Throwable onCommitException;
	private List<Exception> commits = new ArrayList<>();

	private boolean closeCalled = false;

	private final List<ITrxSavepoint> releasedSavepoints = new ArrayList<ITrxSavepoint>();

	public MockedTrx(final ITrxManager trxManager, final String trxName, final boolean autoCommit)
	{
		super(trxManager, trxName, autoCommit);
	}

	@Override
	protected boolean releaseSavepointNative(final ITrxSavepoint savepoint)
	{
		super.releaseSavepointNative(savepoint);

		releasedSavepoints.add(savepoint);
		return true;
	}

	@Override
	protected boolean rollbackNative(boolean throwException) throws SQLException
	{
		super.rollbackNative(throwException);

		rollbacks.add(new Exception("Stacktrace of rollbackNative"));
		rollbacksToSavePoint.add(null);

		return throwExceptionOrReturnFalse(onRollbackException, throwException);
	}

	@Override
	protected boolean rollbackNative(ITrxSavepoint savepoint)
	{
		super.releaseSavepointNative(savepoint);

		rollbacks.add(new Exception("Stacktrace of rollbackNative"));
		rollbacksToSavePoint.add(savepoint);

		return true;
	}

	@Override
	protected boolean commitNative(boolean throwException) throws SQLException
	{
		super.commitNative(throwException);

		commits.add(new Exception("Stacktrace of commitNative"));

		return throwExceptionOrReturnFalse(onCommitException, throwException);
	}

	private static final boolean throwExceptionOrReturnFalse(final Throwable exception, final boolean throwException) throws SQLException
	{
		if (exception != null)
		{
			if (throwException)
			{
				if (exception instanceof RuntimeException)
				{
					throw (RuntimeException)exception;
				}
				else if (exception instanceof Error)
				{
					throw (Error)exception;
				}
				else if (exception instanceof SQLException)
				{
					throw (SQLException)exception;
				}
				else
				{
					Assert.fail("Invalid type of exception to be thrown: " + exception + " (" + exception.getClass() + ")");
				}
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean closeNative()
	{
		super.closeNative();
		// Assume that at this point the transaction is no longer active because it was never active or because it was commit/rollback first.
		Assert.assertFalse("Transaction shall not be active at this point", isActive());

		closeCalled = true;
		return true;
	}

	public void setOnCommitException(final Throwable onCommitException)
	{
		this.onCommitException = onCommitException;
	}

	public void setOnRollbackException(final Throwable onRollbackException)
	{
		this.onRollbackException = onRollbackException;
	}

	/**
	 * @return true if commit() method was called, no matter if it failed
	 */
	public boolean isCommitCalled()
	{
		return !commits.isEmpty();
	}

	public boolean isRollbackCalled()
	{
		return !rollbacks.isEmpty();
	}

	/**
	 * @return true if rollback() method was called, no matter if it failed
	 */
	public boolean isCloseCalled()
	{
		return closeCalled;
	}

	public List<ITrxSavepoint> getReleasedSavepoints()
	{
		return releasedSavepoints;
	}

	/**
	 *
	 *
	 * @return a stacktrace for each time, {@link #commit()} was called on this trx.
	 *
	 * @see {@link Util#dumpStackTraceToString(Throwable)}
	 */
	public List<Exception> getCommits()
	{
		return commits;
	}

	/**
	 *
	 * @return a stacktrace for each time, {@link #rollback()} or {@link #rollback(ITrxSavepoint)} was called on this trx.
	 *
	 * @see {@link Util#dumpStackTraceToString(Throwable)}
	 */
	public List<Exception> getRollbacks()
	{
		return rollbacks;
	}

	/**
	 *
	 * @return a list for each time {@link #rollback()} or {@link #rollback(ITrxSavepoint)} was called on this trx.
	 *         the list has the same size as the one returned by {@link #getRollbacks()}.
	 *         If {@link #rollback()} was called without a save point, then the list contains <code>null</code> at this position.
	 */
	public List<ITrxSavepoint> getRollbacksToSavePoint()
	{
		return rollbacksToSavePoint;
	}
}
