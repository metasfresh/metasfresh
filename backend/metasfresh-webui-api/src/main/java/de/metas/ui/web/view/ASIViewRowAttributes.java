package de.metas.ui.web.view;

import java.util.List;
import java.util.stream.Collectors;

import de.metas.ui.web.pattribute.ASIDocument;
import de.metas.ui.web.pattribute.ASILayout;
import de.metas.ui.web.view.descriptor.ViewRowAttributesLayout;
import de.metas.ui.web.view.json.JSONViewRowAttributes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import de.metas.ui.web.window.datatypes.json.JSONLayoutWidgetType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.IDocumentFieldView;

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

class ASIViewRowAttributes implements IViewRowAttributes
{
	private final DocumentPath documentPath;
	private final ASIDocument asiDoc;
	private ViewRowAttributesLayout layout;

	ASIViewRowAttributes(final ASIDocument asiDoc, final ASILayout asiLayout)
	{
		final DocumentId asiDocId = asiDoc.getDocumentId();
		documentPath = DocumentPath.rootDocumentPath(DocumentType.ViewRecordAttributes, asiDocId, asiDocId);
		
		this.asiDoc = asiDoc;
		this.layout = ViewRowAttributesLayout.of(asiLayout.getElements());
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public ViewRowAttributesLayout getLayout()
	{
		return layout;
	}

	@Override
	public void processChanges(final List<JSONDocumentChangedEvent> events)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getAttributeTypeahead(final String attributeName, final String query)
	{
		throw new UnsupportedOperationException();
//		return asiDoc.getFieldLookupValuesForQuery(attributeName, query);
	}

	@Override
	public LookupValuesList getAttributeDropdown(final String attributeName)
	{
		throw new UnsupportedOperationException();
//		return asiDoc.getFieldLookupValues(attributeName);
	}

	@Override
	public JSONViewRowAttributes toJson(final JSONOptions jsonOpts)
	{
		final DocumentPath documentPath = getDocumentPath();
		final JSONViewRowAttributes jsonDocument = new JSONViewRowAttributes(documentPath);

		final List<JSONDocumentField> jsonFields = asiDoc.getFieldViews()
				.stream()
				.map(field -> toJSONDocumentField(field, jsonOpts))
				.collect(Collectors.toList());

		jsonDocument.setFields(jsonFields);

		return jsonDocument;
	}

	private final JSONDocumentField toJSONDocumentField(final IDocumentFieldView field, final JSONOptions jsonOpts)
	{
		final String fieldName = field.getFieldName();
		final Object jsonValue = field.getValueAsJsonObject(jsonOpts);
		final DocumentFieldWidgetType widgetType = field.getWidgetType();
		return JSONDocumentField.ofNameAndValue(fieldName, jsonValue)
				.setDisplayed(true)
				.setMandatory(false)
				.setReadonly(true)
				.setWidgetType(JSONLayoutWidgetType.fromNullable(widgetType));
	}

}
