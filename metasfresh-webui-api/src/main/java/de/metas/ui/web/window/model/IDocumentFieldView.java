package de.metas.ui.web.window.model;

import org.adempiere.ad.expression.api.LogicExpressionResult;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;

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

/**
 * Document field view.
 *
 * @implNote
 * 			This interface it's just a view of a {@link Document}'s field. Please don't setters or any other method which can chance document field's state.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IDocumentFieldView
{
	//@formatter:off
	DocumentFieldDescriptor getDescriptor();
	DocumentPath getDocumentPath();
	String getFieldName();
	boolean isKey();
	boolean isCalculated();
	boolean isVirtualField();
	boolean hasChangesToSave();
	//@formatter:on

	//@formatter:off
	boolean isReadonly();
	LogicExpressionResult getReadonly();
	//
	boolean isMandatory();
	LogicExpressionResult getMandatory();
	//
	boolean isDisplayed();
	LogicExpressionResult getDisplayed();
	//
	boolean isLookupValuesStale();
	/** @return true if this field is public and will be published to API clients */
	boolean isPublicField();
	boolean isAdvancedField();
	//@formatter:on
	
	default DocumentFieldWidgetType getWidgetType()
	{
		return getDescriptor().getWidgetType();
	}

	//@formatter:off
	/**
	 * @return field's current value
	 */
	Object getValue();
	Object getValueAsJsonObject();
	boolean getValueAsBoolean();
	int getValueAsInt(final int defaultValue);
	<T> T getValueAs(final Class<T> returnType);
	//@formatter:on

	/**
	 * @return initial value / last saved value
	 */
	Object getInitialValue();

	/**
	 * @return old value (i.e. the value as it was when the document was checked out from repository/documents collection)
	 */
	Object getOldValue();
}
