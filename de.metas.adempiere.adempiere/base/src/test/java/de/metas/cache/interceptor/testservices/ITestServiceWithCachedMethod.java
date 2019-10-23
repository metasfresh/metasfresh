package de.metas.cache.interceptor.testservices;

import de.metas.util.ISingletonService;

public interface ITestServiceWithCachedMethod extends ISingletonService
{
	String randomString();

	Object getCachedValue(int param1);

	Object getCachedValueWithCacheName1(int param1);

	Object getCachedValueWithCacheName2(int param1);

	Object getCachedValueWithCacheIgnore(int param1, int ignoredParam);

	Object cachedMethodWhichThrowsException(String message);

	Object cachedMethodWithTrx(int param1, String trxName);
	
	Object cachedMethodWithSkipIfNotNull(int param1, Object skipIfNotNullParam);

	Object methodCallingPackageLevelCachedMethod(int param1);
}
