package de.metas.ui.web.window.model;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import de.metas.ui.web.window.datatypes.DocumentPath;
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

public class MutableDocumentFieldChangedEvent implements IDocumentFieldChangedEvent
{
	public static final MutableDocumentFieldChangedEvent of(final DocumentPath documentPath, final String fieldName, DocumentFieldWidgetType widgetType)
	{
		return new MutableDocumentFieldChangedEvent(documentPath, fieldName, widgetType);
	}

	private final DocumentPath documentPath;
	private final String fieldName;
	private final DocumentFieldWidgetType widgetType;

	private Object value;
	private boolean valueSet = false;

	private MutableDocumentFieldChangedEvent(final DocumentPath documentPath, final String fieldName, DocumentFieldWidgetType widgetType)
	{
		super();

		Check.assumeNotNull(documentPath, "Parameter documentPath is not null");
		this.documentPath = documentPath;

		Check.assumeNotEmpty(fieldName, "fieldName is not empty");
		this.fieldName = fieldName;
		
		Check.assumeNotNull(widgetType, "Parameter widgetType is not null");
		this.widgetType = widgetType;
	}

	@Override
	public String toString()
	{
		final ToStringHelper helper = MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("documentPath", documentPath)
				.add("fieldName", fieldName);

		if (valueSet)
		{
			helper.add("value", value == null ? "<NULL>" : value);
		}

		return helper.toString();
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public String getFieldName()
	{
		return fieldName;
	}

	@Override
	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}

	@Override
	public boolean isValueSet()
	{
		return valueSet;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	public MutableDocumentFieldChangedEvent setValue(final Object value)
	{
		this.value = value;
		valueSet = true;
		return this;
	}

}
