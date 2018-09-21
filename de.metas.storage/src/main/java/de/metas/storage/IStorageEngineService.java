package de.metas.storage;

import de.metas.util.ISingletonService;

public interface IStorageEngineService extends ISingletonService
{
	/**
	 * Register a storage engine to be used by the system.<br>
	 * Currently there can be only one storage engine.
	 *
	 * @param storageEngine
	 */
	void registerStorageEngine(IStorageEngine storageEngine);

	/**
	 *
	 * @return a storage engine that was previously registered using {@link #registerStorageEngine(IStorageEngine)}.
	 */
	IStorageEngine getStorageEngine();
}
