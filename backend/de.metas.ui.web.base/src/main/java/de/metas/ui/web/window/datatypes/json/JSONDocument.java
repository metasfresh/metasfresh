/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentChanges;
import de.metas.ui.web.window.model.DocumentSaveStatus;
import de.metas.ui.web.window.model.DocumentStandardAction;
import de.metas.ui.web.window.model.DocumentValidStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IIncludedDocumentsCollection;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JSON format:
 * <code>
 * [ { field:someFieldName }, {...} ]
 * </code>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@ToString(callSuper = true)
public final class JSONDocument extends JSONDocumentBase
{
	public static JSONDocument ofDocument(@NonNull final Document document, @NonNull final JSONDocumentOptions options)
	{
		return ofDocument(document, options, null);
	}

	public static JSONDocument ofDocument(@NonNull final Document document, @NonNull final JSONDocumentOptions options, final @Nullable Boolean hasComments)
	{
		if (hasComments != null && !document.isRootDocument())
		{
			throw new AdempiereException("`hasComments`=" + hasComments + " passed for non-RootDocument. `hasComments` is only supported for RootDocuments.");
		}

		final JSONDocument jsonDocument = new JSONDocument(document.getDocumentPath());

		// fieldsBy
		// Fields
		{
			final List<JSONDocumentField> jsonFields = new ArrayList<>();

			// Add pseudo "ID" field first
			jsonFields.add(0, JSONDocumentField.idField(document.getDocumentIdAsJson()));

			// Append the other fields
			document.getFieldViews()
					.stream()
					.filter(options.documentFieldFilter())
					.map(field -> JSONDocumentField.ofDocumentField(field, options.getJsonOpts()))
					.peek(jsonField -> options.getDocumentPermissions().apply(document, jsonField)) // apply permissions
					.forEach(jsonFields::add);

			jsonDocument.setFields(jsonFields);
		}

		//
		// Valid Status
		final DocumentValidStatus documentValidStatus = document.getValidStatus();
		if (documentValidStatus != null)
		{
			jsonDocument.setValidStatus(JSONDocumentValidStatus.of(documentValidStatus, options.getJsonOpts()));
		}

		//
		// Save Status
		final DocumentSaveStatus documentSaveStatus = document.getSaveStatus();
		if (documentSaveStatus != null)
		{
			jsonDocument.setSaveStatus(JSONDocumentSaveStatus.of(documentSaveStatus, options.getJsonOpts()));
		}

		//
		// Included tabs info
		document.getIncludedDocumentsCollections()
				.stream()
				.map(includedDocumentsCollection -> createIncludedTabInfo(includedDocumentsCollection, document, options))
				.forEach(jsonDocument::addIncludedTabInfo);

		//
		// Available standard actions
		jsonDocument.setStandardActions(document.getStandardActions());

		//
		// Set debugging info
		if (WindowConstants.isProtocolDebugging())
		{
			jsonDocument.putDebugProperty("tablename", document.getEntityDescriptor().getTableNameOrNull());
			jsonDocument.putDebugProperty(JSONOptions.DEBUG_ATTRNAME, options.toString());
			jsonDocument.putDebugProperty("fields-count", jsonDocument.getFieldsCount());
		}

		//
		// Has Comments
		{
			jsonDocument.hasComments = hasComments;
		}

		return jsonDocument;
	}

	@NonNull
	private static JSONIncludedTabInfo createIncludedTabInfo(
			@NonNull final IIncludedDocumentsCollection includedDocumentsCollection,
			@NonNull final Document document,
			@NonNull final JSONDocumentOptions options)
	{
		final JSONIncludedTabInfo tabInfo = JSONIncludedTabInfo.newInstance(includedDocumentsCollection.getDetailId());
		if (includedDocumentsCollection.isStale())
		{
			tabInfo.markAllRowsStaled();
		}

		final LogicExpressionResult allowCreateNew = includedDocumentsCollection.getAllowCreateNewDocument();
		if (allowCreateNew != null)
		{
			tabInfo.setAllowCreateNew(allowCreateNew.booleanValue(), allowCreateNew.getName());
		}

		final LogicExpressionResult allowDelete = includedDocumentsCollection.getAllowDeleteDocument();
		if (allowDelete != null)
		{
			tabInfo.setAllowDelete(allowDelete.booleanValue(), allowDelete.getName());
		}

		//
		// Apply user permissions
		options.getDocumentPermissions().apply(document, tabInfo);

		return tabInfo;
	}

	/**
	 * @return list of {@link JSONDocument}s
	 */
	public static List<JSONDocument> ofDocumentsList(final Collection<Document> documents, final JSONDocumentOptions options)
	{
		return ofDocumentsList(documents, options, null);
	}

	/**
	 * @param hasComments null unless this is a Root Document.
	 * @return list of {@link JSONDocument}s
	 */
	public static List<JSONDocument> ofDocumentsList(final Collection<Document> documents, final JSONDocumentOptions options, final @Nullable Boolean hasComments)
	{
		return documents.stream()
				.map(document -> ofDocument(document, options, hasComments))
				.collect(Collectors.toList());
	}

	public static List<JSONDocument> ofEvents(final IDocumentChangesCollector documentChangesCollector, final JSONDocumentOptions options)
	{
		final int MAX_SIZE = 100;

		final List<JSONDocument> jsonChanges = documentChangesCollector.streamOrderedDocumentChanges()
				.map(documentChanges -> ofEventOrNull(documentChanges, options))
				.filter(Objects::nonNull)
				.limit(MAX_SIZE + 1)
				.collect(ImmutableList.toImmutableList());

		//
		// Prevent sending more than MAX_SIZE events because that will freeze the frontend application.
		if (jsonChanges.size() > MAX_SIZE)
		{
			throw new AdempiereException("Events count exceeded")
					.setParameter("maxSize", MAX_SIZE)
					.setParameter("documentChangesCollector", documentChangesCollector)
					.setParameter("first events", jsonChanges);
		}

		return jsonChanges;
	}

	@Nullable
	private static JSONDocument ofEventOrNull(final DocumentChanges documentChangedEvents, final JSONDocumentOptions options)
	{
		if (documentChangedEvents.isEmpty())
		{
			return null;
		}

		final DocumentPath documentPath = documentChangedEvents.getDocumentPath();
		final JSONDocument jsonDocument = new JSONDocument(documentPath);

		// If the document was deleted, we just need to export that flag. All the other changes are not relevant.
		if (documentChangedEvents.isDeleted())
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
					.filter(options.documentFieldChangeFilter())
					.forEach((field) -> {
						// Add the pseudo-field "ID" first
						if (field.isKey())
						{
							jsonFields.add(0, JSONDocumentField.idField(field.getValueAsJsonObject(options.getJsonOpts())));
						}

						// Append the other fields
						final JSONDocumentField jsonField = JSONDocumentField.ofDocumentFieldChangedEvent(field, options.getJsonOpts());
						options.getDocumentPermissions().apply(documentPath, jsonField); // apply permissions
						jsonFields.add(jsonField);
					});
			jsonDocument.setFields(jsonFields);
		}

		//
		// Valid status
		final DocumentValidStatus documentValidStatus = documentChangedEvents.getDocumentValidStatus();
		if (documentValidStatus != null)
		{
			jsonDocument.setValidStatus(JSONDocumentValidStatus.of(documentValidStatus, options.getJsonOpts()));
		}

		//
		// Save status
		final DocumentSaveStatus documentSaveStatus = documentChangedEvents.getDocumentSaveStatus();
		if (documentSaveStatus != null)
		{
			jsonDocument.setSaveStatus(JSONDocumentSaveStatus.of(documentSaveStatus, options.getJsonOpts()));
		}

		//
		// Included tabs info
		documentChangedEvents.getIncludedDetailInfos()
				.stream()
				.map(JSONDocument::createIncludedTabInfo)
				.peek(jsonIncludedTabInfo -> options.getDocumentPermissions().apply(documentPath, jsonIncludedTabInfo))
				.forEach(jsonDocument::addIncludedTabInfo);

		return jsonDocument;
	}

	private static JSONIncludedTabInfo createIncludedTabInfo(final DocumentChanges.IncludedDetailInfo includedDetailInfo)
	{
		final JSONIncludedTabInfo tabInfo = JSONIncludedTabInfo.newInstance(includedDetailInfo.getDetailId());
		if (includedDetailInfo.isStale())
		{
			tabInfo.markAllRowsStaled();
		}

		final LogicExpressionResult allowCreateNew = includedDetailInfo.getAllowNew();
		if (allowCreateNew != null)
		{
			tabInfo.setAllowCreateNew(allowCreateNew.booleanValue(), allowCreateNew.getName());
		}

		final LogicExpressionResult allowDelete = includedDetailInfo.getAllowDelete();
		if (allowDelete != null)
		{
			tabInfo.setAllowDelete(allowDelete.booleanValue(), allowDelete.getName());
		}

		return tabInfo;
	}

	@JsonProperty("validStatus")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Getter
	private JSONDocumentValidStatus validStatus;

	@JsonProperty("saveStatus")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private JSONDocumentSaveStatus saveStatus;

	/**
	 * {@link JSONIncludedTabInfo}s indexed by tabId
	 */
	@JsonProperty("includedTabsInfo")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	// @JsonSerialize(using = JsonMapAsValuesListSerializer.class) // serialize as Map (see #288)
	private Map<String, JSONIncludedTabInfo> includedTabsInfo;

	@JsonProperty("standardActions")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<DocumentStandardAction> standardActions;

	@JsonProperty("websocketEndpoint")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String websocketEndpoint;

	@JsonProperty("hasComments")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable
	private Boolean hasComments;

	private JSONDocument(final DocumentPath documentPath)
	{
		super(documentPath);
		setUnboxPasswordFields();
		this.websocketEndpoint = buildWebsocketEndpointOrNull(getWindowId(), getId());
	}

	@Nullable
	private static String buildWebsocketEndpointOrNull(final WindowId windowId, final DocumentId documentId)
	{
		if (windowId != null && documentId != null)
		{
			return WebsocketTopicNames.buildDocumentTopicName(windowId, documentId)
					.getAsString();
		}
		else
		{
			return null;
		}
	}

	private void setValidStatus(final JSONDocumentValidStatus validStatus)
	{
		this.validStatus = validStatus;
	}

	private void setSaveStatus(final JSONDocumentSaveStatus saveStatus)
	{
		this.saveStatus = saveStatus;
	}

	public void addIncludedTabInfo(final JSONIncludedTabInfo tabInfo)
	{
		if (includedTabsInfo == null)
		{
			includedTabsInfo = new HashMap<>();
		}
		includedTabsInfo.put(tabInfo.getTabId().toJson(), tabInfo);
	}

	@JsonIgnore
	public Collection<JSONIncludedTabInfo> getIncludedTabsInfos()
	{
		if (includedTabsInfo == null || includedTabsInfo.isEmpty())
		{
			return ImmutableSet.of();
		}
		return includedTabsInfo.values();
	}

	private void setStandardActions(final Set<DocumentStandardAction> standardActions)
	{
		this.standardActions = standardActions;
	}
}
