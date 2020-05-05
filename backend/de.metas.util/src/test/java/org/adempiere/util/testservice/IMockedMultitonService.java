package org.adempiere.util.testservice;

import de.metas.util.IMultitonService;
import de.metas.util.Services;

/**
 * Service used to test how {@link IMultitonService}s are instantiated by {@link Services}.
 * 
 * @author tsa
 *
 */
public interface IMockedMultitonService extends IMultitonService
{
	/** @return unique instance number that was assigned on instantiation */
	int getInstanceNo();
}
