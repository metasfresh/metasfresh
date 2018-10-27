package org.adempiere.mm.attributes.api;

import de.metas.util.ISingletonService;

public interface IModelAttributeSetInstanceListenerService extends ISingletonService
{
	public void registerListener(final IModelAttributeSetInstanceListener listener);
}
