package de.metas.cache.interceptor.testservices;

import de.metas.util.ISingletonService;

public interface ITestServiceWithPrivateCachedMethod extends ISingletonService
{

	Object methodCallingPrivateCachedMethod(int param1);

}
