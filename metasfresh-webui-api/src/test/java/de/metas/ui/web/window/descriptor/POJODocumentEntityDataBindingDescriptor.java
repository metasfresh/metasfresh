package de.metas.ui.web.window.descriptor;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.ui.web.window.model.Document;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public class POJODocumentEntityDataBindingDescriptor implements DocumentEntityDataBindingDescriptor
{
	public static final POJODocumentEntityDataBindingDescriptor ofClass(final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		return new POJODocumentEntityDataBindingDescriptor(modelClass, tableName);
	}
	
	public static final Class<?> getModelClass(final Document document)
	{
		final POJODocumentEntityDataBindingDescriptor entityDescriptor = (POJODocumentEntityDataBindingDescriptor)document.getEntityDescriptor().getDataBinding();
		return entityDescriptor.getModelClass();
	}

	private final Class<?> modelClass;
	private final String tableName;

	public POJODocumentEntityDataBindingDescriptor(final Class<?> modelClass, final String tableName)
	{
		super();
		this.modelClass = modelClass;
		this.tableName = tableName;
	}
	
	public Class<?> getModelClass()
	{
		return modelClass;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}
}