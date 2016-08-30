package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentChanges;
import de.metas.ui.web.window.model.DocumentSaveStatus;
import de.metas.ui.web.window.model.DocumentValidStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentFieldView;
import io.swagger.annotations.ApiModel;

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
 * JSON format:
 * <code>
 * [ { field:someFieldName }, {...} ]
 * </code>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ApiModel("document")
@SuppressWarnings("serial")
public final class JSONDocument implements Serializable
{
	private static final JSONDocument ofDocument(final Document document, final JSONFilteringOptions jsonFilteringOpts)
	{
		final JSONDocument jsonDocument = new JSONDocument(document.getDocumentPath());

		final List<JSONDocumentField> jsonFields = new ArrayList<>();

		// Add pseudo "ID" field first
		final IDocumentFieldView idField = document.getIdFieldViewOrNull();
		if (idField != null)
		{
			jsonFields.add(0, JSONDocumentField.idField(idField.getValueAsJsonObject()));
		}

		// Append the other fields
		document.getFieldViews()
				.stream()
				.filter(jsonFilteringOpts.documentFieldViewFilter())
				.map(JSONDocumentField::ofDocumentField)
				.forEach(jsonFields::add);

		jsonDocument.setFields(jsonFields);

		final DocumentValidStatus documentValidStatus = document.getValidStatus();
		if (documentValidStatus != null)
		{
			jsonDocument.setValidStatus(documentValidStatus.toJson());
		}

		final DocumentSaveStatus documentSaveStatus = document.getSaveStatus();
		if (documentSaveStatus != null)
		{
			jsonDocument.setSaveStatus(documentSaveStatus.toJson());
		}

		//
		// Set debugging info
		if (WindowConstants.isProtocolDebugging())
		{
			jsonDocument.putDebugProperty("tablename", document.getEntityDescriptor().getDataBinding().getTableName());
			jsonDocument.putDebugProperty("filtering-opts", jsonFilteringOpts);
			jsonDocument.putDebugProperty("fields-count", jsonDocument.getFieldsCount());
		}

		return jsonDocument;
	}

	/**
	 * @param documents
	 * @param includeFieldsList
	 * @return list of {@link JSONDocument}s
	 */
	public static List<JSONDocument> ofDocumentsList(final Collection<Document> documents, final JSONFilteringOptions jsonFilteringOpts)
	{
		return documents.stream()
				.map(document -> ofDocument(document, jsonFilteringOpts))
				.collect(Collectors.toList());
	}

	public static List<JSONDocument> ofEvents(final IDocumentChangesCollector documentChangesCollector, final JSONFilteringOptions jsonFilteringOpts)
	{
		final Collection<DocumentChanges> documentChangedEventList = documentChangesCollector.getDocumentChangesByPath().values();
		if (documentChangedEventList.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<JSONDocument> jsonDocuments = new ArrayList<>(documentChangedEventList.size());
		for (final DocumentChanges documentChangedEvents : documentChangedEventList)
		{
			final JSONDocument jsonDocument = ofEvent(documentChangedEvents, jsonFilteringOpts);
			if (jsonDocument == null)
			{
				continue;
			}
			jsonDocuments.add(jsonDocument);
		}

		return jsonDocuments;
	}

	private static JSONDocument ofEvent(final DocumentChanges documentChangedEvents, final JSONFilteringOptions jsonFilteringOpts)
	{
		if (documentChangedEvents.isEmpty())
		{
			return null;
		}

		final JSONDocument jsonDocument = new JSONDocument(documentChangedEvents.getDocumentPath());

		//
		// Fields
		final List<JSONDocumentField> jsonFields = new ArrayList<>();
		documentChangedEvents.getFieldChangesList()
				.stream()
				// TODO apply filtering
				.filter(jsonFilteringOpts.documentFieldChangeFilter())
				.forEach((field) -> {
					// Add the pseudo-field "ID" first
					if (field.isKey())
					{
						jsonFields.add(0, JSONDocumentField.idField(field.getValueAsJsonObject()));
					}

					// Append the other fields
					jsonFields.add(JSONDocumentField.ofDocumentFieldChangedEvent(field));
				});
		jsonDocument.setFields(jsonFields);

		//
		// Valid status
		final DocumentValidStatus documentValidStatus = documentChangedEvents.getDocumentValidStatus();
		if (documentValidStatus != null)
		{
			jsonDocument.setValidStatus(documentValidStatus.toJson());
		}

		//
		// Save status
		final DocumentSaveStatus documentSaveStatus = documentChangedEvents.getDocumentSaveStatus();
		if (documentSaveStatus != null)
		{
			jsonDocument.setSaveStatus(documentSaveStatus.toJson());
		}

		return jsonDocument;
	}

	@JsonProperty("id")
	private final String id;

	@JsonProperty("tabid")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String tabid;

	@JsonProperty("rowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String rowId;

	@JsonProperty("valid-status")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String validStatus;

	@JsonProperty("save-status")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String saveStatus;

	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	@JsonProperty("fields")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonSerialize(using = JsonMapAsValuesListSerializer.class)
	private Map<String, JSONDocumentField> fieldsByName;

	public JSONDocument(final DocumentPath documentPath)
	{
		super();

		id = documentPath.getDocumentId().toJson();
		if (documentPath.isIncludedDocument())
		{
			tabid = documentPath.getDetailId();
			rowId = documentPath.getRowId().toJson();
		}
		else
		{
			tabid = null;
			rowId = null;
		}
	}

	@JsonCreator
	private JSONDocument(
			@JsonProperty("id") final String id //
			, @JsonProperty("tabid") final String tabid //
			, @JsonProperty("rowId") final String rowId //
			, @JsonProperty("valid-status") final String validStatus //
			, @JsonProperty("save-status") final String saveStatus //
			, @JsonProperty("fields") final List<JSONDocumentField> fields //
	)
	{
		super();
		this.id = id;
		this.tabid = tabid;
		this.rowId = rowId;
		setFields(fields);
	}

	public String getId()
	{
		return id;
	}

	public String getTabid()
	{
		return tabid;
	}

	public String getRowId()
	{
		return rowId;
	}

	private void setValidStatus(final String validStatus)
	{
		this.validStatus = validStatus;
	}

	public String getValidStatus()
	{
		return validStatus;
	}

	private void setSaveStatus(final String saveStatus)
	{
		this.saveStatus = saveStatus;
	}

	public String getSaveStatus()
	{
		return saveStatus;
	}

	@JsonAnyGetter
	public Map<String, Object> getOtherProperties()
	{
		return otherProperties;
	}

	@JsonAnySetter
	public void putOtherProperty(final String name, final Object jsonValue)
	{
		otherProperties.put(name, jsonValue);
	}

	public JSONDocument putDebugProperty(final String name, final Object jsonValue)
	{
		otherProperties.put("debug-" + name, jsonValue);
		return this;
	}

	private void setFields(final Collection<JSONDocumentField> fields)
	{
		fieldsByName = fields == null ? null : Maps.uniqueIndex(fields, (field) -> field.getField());
	}

	@JsonIgnore
	public int getFieldsCount()
	{
		return fieldsByName == null ? 0 : fieldsByName.size();
	}
}
