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


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.junit.Assert;
import org.junit.Ignore;

@Ignore
public class MockedTrx extends AbstractTrx
{
	private boolean commitCalled = false;
	private Throwable onCommitException = null;

	private boolean rollbackCalled = false;
	private Throwable onRollbackException;

	private boolean closeCalled = false;

	private final List<ITrxSavepoint> activeSavepoints = new ArrayList<ITrxSavepoint>();
	private final List<ITrxSavepoint> releasedSavepoints = new ArrayList<ITrxSavepoint>();

	public MockedTrx(final ITrxManager trxManager, final String trxName)
	{
		super(trxManager, trxName);
	}

	@Override
	protected ITrxSavepoint createTrxSavepointNative(String name)
	{
		Assert.assertTrue("Transaction shall be started first", isActive());

		final PlainTrxSavepoint savepoint = new PlainTrxSavepoint(this, name);
		activeSavepoints.add(savepoint);

		return savepoint;
	}

	@Override
	protected boolean releaseSavepointNative(final ITrxSavepoint savepoint) throws Exception
	{
		Assert.assertTrue("Transaction shall be started first", isActive());

		final boolean removed = activeSavepoints.remove(savepoint);
		Assert.assertTrue("Savepoint " + savepoint + " shall be in the activeSavepoints list", removed);

		releasedSavepoints.add(savepoint);
		return true;
	}

	@Override
	protected boolean rollbackNative(boolean throwException) throws SQLException
	{
		Assert.assertTrue("Transaction shall be started first", isActive());

		rollbackCalled = true;

		return throwExceptionOrReturnFalse(onRollbackException, throwException);
	}

	@Override
	protected boolean rollbackNative(ITrxSavepoint savepoint)
	{
		Assert.assertTrue("Transaction shall be started first", isActive());

		return true;
	}

	@Override
	protected boolean commitNative(boolean throwException) throws SQLException
	{
		Assert.assertTrue("Transaction shall be started first", isActive());

		commitCalled = true;

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
		return commitCalled;
	}

	public boolean isRollbackCalled()
	{
		return rollbackCalled;
	}

	/**
	 * @return true if rollback() method was called, no matter if it failed
	 */
	public boolean isCloseCalled()
	{
		return closeCalled;
	}

	public List<ITrxSavepoint> getActiveSavepoints()
	{
		return activeSavepoints;
	}

	public List<ITrxSavepoint> getReleasedSavepoints()
	{
		return releasedSavepoints;
	}
}
