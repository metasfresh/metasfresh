package org.adempiere.mm.attributes.api;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.util.ISingletonService;

public interface IAttributeSetInstanceAwareFactoryService extends ISingletonService
{
	/**
	 * Register a factory for a given table name.<br>
	 * For a given input instance, {@link InterfaceWrapperHelper#getModelTableNameOrNull(Object)} will be used to see if is has a table name.<br>
	 * Factories registered with this method take precedence over factories registered with {@link #registerFactoryForOtherClass(Class, IAttributeSetInstanceAwareFactory)}.
	 */
	void registerFactoryForTableName(String tableNameOfInput, IAttributeSetInstanceAwareFactory factory);

	void registerFactoryForOtherClass(Class<?> classOfInput, IAttributeSetInstanceAwareFactory factory);

	IAttributeSetInstanceAware createOrNull(Object referencedObj);
}
