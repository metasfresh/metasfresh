package de.metas.ui.web.window.descriptor;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.pojo.POJODocumentsRepository;

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


public final class POJODocumentEntityDataBindingDescriptor implements DocumentEntityDataBindingDescriptor
{
	public static final POJODocumentEntityDataBindingDescriptor ofClass(final Class<?> modelClass)
	{
		return new POJODocumentEntityDataBindingDescriptor(POJODocumentsRepository.instance, modelClass);
	}
	
	public static final Class<?> getModelClass(final Document document)
	{
		final POJODocumentEntityDataBindingDescriptor entityDescriptor = (POJODocumentEntityDataBindingDescriptor)document.getEntityDescriptor().getDataBinding();
		return entityDescriptor.getModelClass();
	}

	private final POJODocumentsRepository documentsRepository;
	private final Class<?> modelClass;
	private final String tableName;
	private final String keyColumnName;

	private POJODocumentEntityDataBindingDescriptor(final POJODocumentsRepository documentsRepository, final Class<?> modelClass)
	{
		super();
		
		Check.assumeNotNull(documentsRepository, "Parameter documentsRepository is not null");
		this.documentsRepository = documentsRepository;
		
		this.modelClass = modelClass;
		this.tableName = InterfaceWrapperHelper.getTableName(modelClass);
		this.keyColumnName = InterfaceWrapperHelper.getKeyColumnName(modelClass);
	}
	
	@Override
	public DocumentsRepository getDocumentsRepository()
	{
		return documentsRepository;
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
	
	@Override
	public String getKeyColumnName()
	{
		return keyColumnName;
	}
}