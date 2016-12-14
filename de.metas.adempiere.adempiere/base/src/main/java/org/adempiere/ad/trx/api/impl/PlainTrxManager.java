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

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;

/**
 * This implementation is intended for unit and module testing in scenarios where you want the trxManager to get out of the way.
 * <p>
 * Hint: if you want to actually test trx related behavior (e.g. if some trx was committed and so on), then there is {@link MockedTrxManager}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PlainTrxManager extends AbstractTrxManager
{
	//
	// Flags used to check transaction lifecycle and consistency: COMMIT and ROLLBACK
	// NOTE: atm, the actual JDBC are not failing in this case, but, i think is helpful in tests to be much more strict to enforce consistency
	private boolean failCommitIfTrxNotStarted = true;
	private boolean failRollbackIfTrxNotStarted = true;
	
	public PlainTrxManager()
	{
		super();
	}

	@Override
	protected ITrx createTrx(String trxName, final boolean autoCommit)
	{
		try
		{
			return new PlainTrx(this, trxName, autoCommit);
		}
		catch (Exception e)
		{
			throw new DBException(e.getLocalizedMessage(), e);
		}
	}

	public PlainTrxManager setFailCommitIfTrxNotStarted(final boolean failCommitIfTrxNotStarted)
	{
		this.failCommitIfTrxNotStarted = failCommitIfTrxNotStarted;
		return this;
	}

	public boolean isFailCommitIfTrxNotStarted()
	{
		return failCommitIfTrxNotStarted;
	}

	public PlainTrxManager setFailRollbackIfTrxNotStarted(final boolean failRollbackIfTrxNotStarted)
	{
		this.failRollbackIfTrxNotStarted = failRollbackIfTrxNotStarted;
		return this;
	}

	public boolean isFailRollbackIfTrxNotStarted()
	{
		return failRollbackIfTrxNotStarted;
	}

	public void assertNoActiveTransactions()
	{
		final List<ITrx> activeTrxs = getActiveTransactionsList();
		Check.assume(activeTrxs.isEmpty(), "Expected no active transactions but got: {}", activeTrxs);
	}
}
