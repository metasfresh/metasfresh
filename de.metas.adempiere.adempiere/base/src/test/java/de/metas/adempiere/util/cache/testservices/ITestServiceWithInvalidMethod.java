package de.metas.adempiere.util.cache.testservices;

import de.metas.util.ISingletonService;

public interface ITestServiceWithInvalidMethod extends ISingletonService
{
	/**
	 * @param ctx
	 * @return random string
	 */
	String invalidCachCtxParam(String ctx);
}
