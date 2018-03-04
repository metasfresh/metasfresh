package de.metas.ui.web.process.view;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.process.view.ViewActionDescriptor.ViewActionMethodArgumentExtractor;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.Document;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Builder
public final class ViewActionParamDescriptor
{
	private static final Logger logger = LogManager.getLogger(ViewActionParamDescriptor.class);

	private final @NonNull String parameterName;
	private final @NonNull Class<?> parameterValueClass;
	private final ViewActionParam parameterAnnotation;

	private final @NonNull ViewActionMethodArgumentExtractor methodArgumentExtractor;

	public boolean isUserParameter()
	{
		return parameterAnnotation != null;
	}

	public DocumentFieldDescriptor.Builder createParameterFieldDescriptor()
	{
		Preconditions.checkState(isUserParameter(), "parameter is internal");

		LookupDescriptorProvider lookupDescriptorProvider = LookupDescriptorProvider.NULL;
		if (parameterAnnotation.widgetType().isLookup())
		{
			if (Check.isEmpty(parameterAnnotation.sqlLookupTableName(), true))
			{
				logger.warn("sqlLookupTableName not provided in " + parameterAnnotation + " (" + this + "). Continuing...");
			}
			else
			{
				lookupDescriptorProvider = SqlLookupDescriptor.builder()
						.setCtxTableName(null) // tableName
						.setCtxColumnName(InterfaceWrapperHelper.getKeyColumnName(parameterAnnotation.sqlLookupTableName()))
						.setDisplayType(DisplayType.Search)
						.setReadOnlyAccess()
						.buildProvider();
			}
		}
		
		return DocumentFieldDescriptor.builder(parameterName)
				.setCaption(parameterAnnotation.caption())
				// .setDescription(parameterAnnotation.description())
				//
				.setValueClass(parameterValueClass)
				.setWidgetType(parameterAnnotation.widgetType())
				.setLookupDescriptorProvider(lookupDescriptorProvider != null ? lookupDescriptorProvider : LookupDescriptorProvider.NULL)
				//
				// .setDefaultValueExpression(defaultValueExpr)
				.setReadonlyLogic(false)
				.setDisplayLogic(true)
				.setMandatoryLogic(parameterAnnotation.mandatory())
				//
				.addCharacteristic(Characteristic.PublicField)
		//
		// .setDataBinding(new ASIAttributeFieldBinding(attributeId, fieldName, attribute.isMandatory(), readMethod, writeMethod))
		;

	}

	public Object extractArgument(final IView view, final Document processParameters, final DocumentIdsSelection selectedDocumentIds)
	{
		return methodArgumentExtractor.extractArgument(view, processParameters, selectedDocumentIds);
	}

}
