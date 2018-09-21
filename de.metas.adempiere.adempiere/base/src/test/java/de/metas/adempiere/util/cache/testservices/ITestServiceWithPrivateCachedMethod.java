package de.metas.adempiere.util.cache.testservices;

import de.metas.util.ISingletonService;

public interface ITestServiceWithPrivateCachedMethod extends ISingletonService
{

	Object methodCallingPrivateCachedMethod(int param1);

}
