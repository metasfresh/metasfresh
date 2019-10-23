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


import org.compiere.util.TrxRunnable2;
import org.junit.Ignore;

@Ignore
public class MockedTrxRunnable2 implements TrxRunnable2
{
	// Config
	private boolean throwExceptionOnExecution = false;
	private boolean throwExceptionOnCatch = true;
	private boolean returnValueOnCatch = true;

	// Status
	private boolean executed = false;
	private String lastTrxName = null;

	public MockedTrxRunnable2()
	{
		super();
	}

	public boolean isThrowExceptionOnExecution()
	{
		return throwExceptionOnExecution;
	}

	public void setThrowExceptionOnExecution(boolean throwExceptionOnExecution)
	{
		this.throwExceptionOnExecution = throwExceptionOnExecution;
	}

	public boolean isThrowExceptionOnCatch()
	{
		return throwExceptionOnCatch;
	}

	public void setThrowExceptionOnCatch(boolean throwExceptionOnCatch)
	{
		this.throwExceptionOnCatch = throwExceptionOnCatch;
	}

	public boolean isReturnValueOnCatch()
	{
		return returnValueOnCatch;
	}

	public void setReturnValueOnCatch(boolean returnValueOnCatch)
	{
		this.returnValueOnCatch = returnValueOnCatch;
	}

	@Override
	public void run(String localTrxName) throws MockedTrxRunnableException
	{
		this.lastTrxName = localTrxName;
		this.executed = true;
		if (throwExceptionOnExecution)
		{
			throw new MockedTrxRunnableException("Thrown exception because throwExceptionOnExecution=true");
		}
	}

	@Override
	public boolean doCatch(Throwable e) throws Throwable
	{
		if (throwExceptionOnCatch)
		{
			throw e;
		}

		return returnValueOnCatch;
	}

	@Override
	public void doFinally()
	{
	}

	public boolean isExecuted()
	{
		return executed;
	}

	public String getLastTrxName()
	{
		return lastTrxName;
	}
}
