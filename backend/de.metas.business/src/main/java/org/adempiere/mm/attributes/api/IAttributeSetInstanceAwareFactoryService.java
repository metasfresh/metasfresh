package org.adempiere.mm.attributes.api;

import org.adempiere.model.InterfaceWrapperHelper;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;

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
