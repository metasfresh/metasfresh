package de.metas.ui.web.window.datatypes.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.LogicExpressionResult;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentChanges;
import de.metas.ui.web.window.model.DocumentSaveStatus;
import de.metas.ui.web.window.model.DocumentValidStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IIncludedDocumentsCollection;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
// @JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE) // cannot use it because of "otherProperties"
public final class JSONDocument extends JSONDocumentBase
{
	public static final JSONDocument ofDocument(final Document document, final JSONOptions jsonOpts)
	{
		final JSONDocument jsonDocument = new JSONDocument(document.getDocumentPath());

		//
		// Fields
		{
			final List<JSONDocumentField> jsonFields = new ArrayList<>();

			// Add pseudo "ID" field first
			jsonFields.add(0, JSONDocumentField.idField(document.getDocumentIdAsJson()));

			// Append the other fields
			document.getFieldViews()
					.stream()
					.filter(jsonOpts.documentFieldFilter())
					.map(JSONDocumentField::ofDocumentField)
					.forEach(jsonFields::add);

			jsonDocument.setFields(jsonFields);
		}

		//
		// Valid Status
		final DocumentValidStatus documentValidStatus = document.getValidStatus();
		if (documentValidStatus != null)
		{
			jsonDocument.setValidStatus(documentValidStatus);
		}

		//
		// Save Status
		final DocumentSaveStatus documentSaveStatus = document.getSaveStatus();
		if (documentSaveStatus != null)
		{
			jsonDocument.setSaveStatus(documentSaveStatus);
		}

		//
		// Included tabs info
		document.getIncludedDocumentsCollections()
				.stream()
				.map(JSONIncludedTabInfo::new)
				.forEach(jsonDocument::setIncludedTabInfo);

		//
		// Set debugging info
		if (WindowConstants.isProtocolDebugging())
		{
			jsonDocument.putDebugProperty("tablename", document.getEntityDescriptor().getTableNameOrNull());
			jsonDocument.putDebugProperty(JSONOptions.DEBUG_ATTRNAME, jsonOpts.toString());
			jsonDocument.putDebugProperty("fields-count", jsonDocument.getFieldsCount());
		}

		return jsonDocument;
	}

	/**
	 * @param documents
	 * @param includeFieldsList
	 * @return list of {@link JSONDocument}s
	 */
	public static List<JSONDocument> ofDocumentsList(final Collection<Document> documents, final JSONOptions jsonOpts)
	{
		return documents.stream()
				.map(document -> ofDocument(document, jsonOpts))
				.collect(Collectors.toList());
	}

	public static List<JSONDocument> ofEvents(final IDocumentChangesCollector documentChangesCollector, final JSONOptions jsonOpts)
	{
		return documentChangesCollector.streamOrderedDocumentChanges()
				.map(documentChanges -> ofEventOrNull(documentChanges, jsonOpts))
				.filter(jsonDocument -> jsonDocument != null)
				.collect(ImmutableList.toImmutableList());
	}

	private static JSONDocument ofEventOrNull(final DocumentChanges documentChangedEvents, final JSONOptions jsonOpts)
	{
		if (documentChangedEvents.isEmpty())
		{
			return null;
		}

		final JSONDocument jsonDocument = new JSONDocument(documentChangedEvents.getDocumentPath());

		// If the document was deleted, we just need to export that flag. All the other changes are not relevant.
		if(documentChangedEvents.isDeleted())
		{
			jsonDocument.setDeleted();
			return jsonDocument;
		}

		//
		// Fields
		{
			final List<JSONDocumentField> jsonFields = new ArrayList<>();
			documentChangedEvents.getFieldChangesList()
					.stream()
					.filter(jsonOpts.documentFieldChangeFilter())
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
		}

		//
		// Valid status
		final DocumentValidStatus documentValidStatus = documentChangedEvents.getDocumentValidStatus();
		if (documentValidStatus != null)
		{
			jsonDocument.setValidStatus(documentValidStatus);
		}

		//
		// Save status
		final DocumentSaveStatus documentSaveStatus = documentChangedEvents.getDocumentSaveStatus();
		if (documentSaveStatus != null)
		{
			jsonDocument.setSaveStatus(documentSaveStatus);
		}

		//
		// Included tabs info
		documentChangedEvents.getIncludedDetailInfos()
				.stream()
				.map(JSONIncludedTabInfo::new)
				.forEach(jsonDocument::setIncludedTabInfo);

		return jsonDocument;
	}

	
	@JsonProperty("validStatus")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private DocumentValidStatus validStatus;

	@JsonProperty("saveStatus")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private DocumentSaveStatus saveStatus;

	/** {@link JSONIncludedTabInfo}s indexed by tabId */
	@JsonProperty("includedTabsInfo")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	// @JsonSerialize(using = JsonMapAsValuesListSerializer.class) // serialize as Map (see #288)
	private Map<String, JSONIncludedTabInfo> includedTabsInfo;

	public JSONDocument(final DocumentPath documentPath)
	{
		super(documentPath);
	}
	
	private void setValidStatus(final DocumentValidStatus validStatus)
	{
		this.validStatus = validStatus;
	}

	public DocumentValidStatus getValidStatus()
	{
		return validStatus;
	}

	private void setSaveStatus(final DocumentSaveStatus documentSaveStatus)
	{
		saveStatus = documentSaveStatus;
	}

	public DocumentSaveStatus getSaveStatus()
	{
		return saveStatus;
	}
	
	private void setIncludedTabInfo(final JSONIncludedTabInfo tabInfo)
	{
		if (includedTabsInfo == null)
		{
			includedTabsInfo = new HashMap<>();
		}
		includedTabsInfo.put(tabInfo.tabid, tabInfo);
	}



	//
	//
	//
	//
	//
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	private static final class JSONIncludedTabInfo
	{
		@JsonProperty("tabid")
		private final String tabid;

		@JsonProperty("stale")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private final Boolean stale;

		@JsonProperty("allowCreateNew")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private final Boolean allowCreateNew;
		@JsonProperty("allowCreateNewReason")
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		private final String allowCreateNewReason;

		@JsonProperty("allowDelete")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private final Boolean allowDelete;
		@JsonProperty("allowDeleteReason")
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		private final String allowDeleteReason;

		private JSONIncludedTabInfo(final IIncludedDocumentsCollection includedDocumentsCollection)
		{
			tabid = DetailId.toJson(includedDocumentsCollection.getDetailId());

			final boolean stale = includedDocumentsCollection.isStale();
			this.stale = stale ? Boolean.TRUE : null;

			final LogicExpressionResult allowCreateNew = includedDocumentsCollection.getAllowCreateNewDocument();
			if (allowCreateNew != null)
			{
				this.allowCreateNew = allowCreateNew.booleanValue();
				allowCreateNewReason = allowCreateNew.getName();
			}
			else
			{
				this.allowCreateNew = null;
				allowCreateNewReason = null;
			}

			final LogicExpressionResult allowDelete = includedDocumentsCollection.getAllowDeleteDocument();
			if (allowDelete != null)
			{
				this.allowDelete = allowDelete.booleanValue();
				allowDeleteReason = allowDelete.getName();
			}
			else
			{
				this.allowDelete = null;
				allowDeleteReason = null;
			}
		}

		private JSONIncludedTabInfo(final DocumentChanges.IncludedDetailInfo includedDetailInfo)
		{
			tabid = DetailId.toJson(includedDetailInfo.getDetailId());

			final boolean stale = includedDetailInfo.isStale();
			this.stale = stale ? Boolean.TRUE : null;

			final LogicExpressionResult allowCreateNew = includedDetailInfo.getAllowNew();
			if (allowCreateNew != null)
			{
				this.allowCreateNew = allowCreateNew.booleanValue();
				allowCreateNewReason = allowCreateNew.getName();
			}
			else
			{
				this.allowCreateNew = null;
				allowCreateNewReason = null;
			}

			final LogicExpressionResult allowDelete = includedDetailInfo.getAllowDelete();
			if (allowDelete != null)
			{
				this.allowDelete = allowDelete.booleanValue();
				allowDeleteReason = allowDelete.getName();
			}
			else
			{
				this.allowDelete = null;
				allowDeleteReason = null;
			}
		}
	}
}
