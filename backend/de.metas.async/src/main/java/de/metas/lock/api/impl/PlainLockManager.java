package de.metas.lock.api.impl;

import de.metas.lock.api.ILockManager;
import de.metas.lock.spi.impl.PlainLockDatabase;
import de.metas.util.Services;
import org.adempiere.util.lang.IAutoCloseable;

public class PlainLockManager extends LockManager
{
	public static PlainLockManager get()
	{
		return (PlainLockManager)Services.get(ILockManager.class);
	}

	private final PlainLockDatabase lockDatabase = new PlainLockDatabase();

	@Override
	public PlainLockDatabase getLockDatabase()
	{
		return lockDatabase;
	}

	public void dump()
	{
		lockDatabase.dump();
	}

	public IAutoCloseable withFailOnWarnings(final boolean fail)
	{
		return lockDatabase.withFailOnWarnings(fail);
	}

}
