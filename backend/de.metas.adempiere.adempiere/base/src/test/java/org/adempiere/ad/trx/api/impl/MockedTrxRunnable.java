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

import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.compiere.util.TrxRunnableAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

@Disabled
@ToString(exclude = { "trxManager" })
public class MockedTrxRunnable extends TrxRunnableAdapter
{
	private final ITrxManager trxManager;
	private boolean executed = false;
	private String lastTrxName = null;
	private ITrx lastTrx = null;
	private Runnable runnable = null;

	private Runnable doFinallyRunnable = null;

	public MockedTrxRunnable(@NonNull final ITrxManager trxManager)
	{
		this.trxManager = trxManager;
	}

	@Override
	public void run(final String localTrxName) throws Exception
	{
		Check.assume(!executed, "This runnable was already executed before: " + this);

		// NOTE: don't validate if localTrxName is null because we are just collecting informations here.
		this.lastTrxName = localTrxName;

		// NOTE: don't fail if transaction was not found... we shall allow the caller test to validate this, here we are just collecting informations
		this.lastTrx = trxManager.get(localTrxName, OnTrxMissingPolicy.ReturnTrxNone);

		// Execute the runnable if defined
		// NOTE: we are executing the runnable after we set the lastTrxName because some tests are relying on that.
		if (runnable != null)
		{
			runnable.run();
		}

		// Flag this Mocked TrxRunnable as executed
		this.executed = true;
	}

	@Override
	public void doFinally()
	{
		if (doFinallyRunnable != null)
		{
			doFinallyRunnable.run();
		}
	}

	public void assertExecuted()
	{
		Assertions.assertTrue(this.executed, "Runnable was executed: " + this);
	}

	public void assertExecutedUsingTrxName(final String expectedLastTrxName)
	{
		assertExecuted();

		if (expectedLastTrxName == null)
		{
			Assertions.assertEquals(expectedLastTrxName, getLastTrxName(), "Runnable was executed using expected trxName: " + this);
		}
		else
		{
			// NOTE: until we get rid of "TrxCallableWithTrxName" our Runnables will get the "effective" localTrxName instead of ThreadInherited.
			// Assert.assertEquals("Runnable was executed using expected trxName: " + this, ITrx.TRXNAME_ThreadInherited, getLastTrxName());
			Assertions.assertNotNull(TrxCallableWithTrxName.class); // non-sense, but we just want to have a reference here for future refactoring
			Assertions.assertEquals(expectedLastTrxName, getLastTrxName(), "Runnable was executed using expected trxName: " + this);

			final String lastTrxNameEffective = getLastTrxNameEffective();
			Assertions.assertEquals(expectedLastTrxName, lastTrxNameEffective, "Runnable was executed using expected EFFECTIVE trxName: " + this);
		}
	}

	/**
	 * Sets inner runnable to be executed on {@link #run(String)}
	 */
	public void setInnerRunnable(final Runnable runnable)
	{
		this.runnable = runnable;
	}

	public void setDoFinallyRunnable(final Runnable runnable)
	{
		this.doFinallyRunnable = runnable;
	}

	public boolean isExecuted()
	{
		return executed;
	}

	public String getLastTrxName()
	{
		return lastTrxName;
	}

	public String getLastTrxNameEffective()
	{
		return lastTrx != null ? lastTrx.getTrxName() : ITrx.TRXNAME_None;
	}

	public ITrx getLastTrx()
	{
		return lastTrx;
	}
}
