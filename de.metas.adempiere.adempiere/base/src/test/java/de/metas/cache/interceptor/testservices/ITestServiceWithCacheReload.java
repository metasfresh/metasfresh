package de.metas.cache.interceptor.testservices;

import de.metas.util.ISingletonService;

public interface ITestServiceWithCacheReload extends ISingletonService
{
	public Object getReload(final String id, final boolean cacheReload);
}
