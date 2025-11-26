package org.adempiere.mm.attributes.asi_aware.factory;

import de.metas.util.ISingletonService;
import org.adempiere.mm.attributes.asi_aware.IAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;

public interface IAttributeSetInstanceAwareFactoryService extends ISingletonService
{
	/**
	 * Register a factory for a given table name.<br>
	 * For a given input instance, {@link InterfaceWrapperHelper#getModelTableNameOrNull(Object)} will be used to see if is has a table name.<br>
	 */
	void registerFactoryForTableName(String tableNameOfInput, IAttributeSetInstanceAwareFactory factory);

	IAttributeSetInstanceAware createOrNull(Object referencedObj);
}
