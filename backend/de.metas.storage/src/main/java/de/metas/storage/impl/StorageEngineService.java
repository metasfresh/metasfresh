package de.metas.storage.impl;

import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.util.Check;

public class StorageEngineService implements IStorageEngineService
{
	private IStorageEngine storageEngine = null;

	@Override
	public void registerStorageEngine(final IStorageEngine storageEngine)
	{
		Check.assumeNotNull(storageEngine, "storageEngine not null");

		if (this.storageEngine == storageEngine)
		{
			return;
		}
		
		Check.assumeNull(this.storageEngine, "Storage engine was not already configured");
		this.storageEngine = storageEngine;
	}

	@Override
	public IStorageEngine getStorageEngine()
	{
		Check.assumeNotNull(this.storageEngine, "Storage engine shall be configured first");
		return this.storageEngine;
	}

}
