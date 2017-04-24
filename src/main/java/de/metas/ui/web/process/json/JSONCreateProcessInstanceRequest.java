package de.metas.ui.web.process.json;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;

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

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONCreateProcessInstanceRequest implements Serializable
{
	@JsonProperty("processId")
	private final String processIdStr;
	@JsonIgnore
	private final ProcessId processId;

	//
	// Called from single row
	/** Document type (aka AD_Window_ID) */
	@JsonProperty("windowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final WindowId windowId;
	//
	@JsonProperty("documentId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String documentId;
	//
	@JsonProperty("tabId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String tabId;
	//
	@JsonProperty("rowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String rowId;

	//
	// Called from view
	@JsonProperty("viewId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String viewId;
	//
	@JsonProperty("viewDocumentIds")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Set<String> viewDocumentIdsStrings;

	@JsonIgnore
	private transient Set<DocumentId> _viewDocumentIds;

	//
	// Calculated values
	private final transient DocumentPath _singleDocumentPath;

	@JsonCreator
	private JSONCreateProcessInstanceRequest( //
			@JsonProperty("processId") final String processIdStr //
			//
			, @JsonProperty("documentType") final String windowIdStr //
			, @JsonProperty("documentId") final String documentId //
			, @JsonProperty("tabId") final String tabId//
			//
			, @JsonProperty("rowId") final String rowId //
			, @JsonProperty("viewId") final String viewId //
			, @JsonProperty("viewDocumentIds") final Set<String> viewDocumentIds //
			)
	{
		super();
		this.processIdStr = processIdStr;
		this.processId = ProcessId.fromJson(processIdStr);

		//
		// Called from single row
		// FIXME: atm, the frontend is not providing the windowId. Create a task for that!
		this.windowId = WindowId.fromNullableJson(windowIdStr);
		this.documentId = documentId;
		this.tabId = tabId;
		this.rowId = rowId;
		_singleDocumentPath = createDocumentPathOrNull(windowId, documentId, tabId, rowId);

		//
		// Called from view
		this.viewId = viewId;
		viewDocumentIdsStrings = viewDocumentIds == null ? null : ImmutableSet.copyOf(viewDocumentIds);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("processId", processId)
				//
				.add("documentType", windowId)
				.add("documentId", documentId)
				.add("tabId", tabId)
				.add("rowId", rowId)
				//
				.add("viewId", viewId)
				.add("viewDocumentIds", _viewDocumentIds != null ? _viewDocumentIds : viewDocumentIdsStrings)
				.toString();
	}

	private static final DocumentPath createDocumentPathOrNull(final WindowId windowId, final String documentId, final String tabId, final String rowIdStr)
	{
		if (windowId != null && !Check.isEmpty(documentId))
		{
			if (Check.isEmpty(tabId) && Check.isEmpty(rowIdStr))
			{
				return DocumentPath.rootDocumentPath(windowId, documentId);
			}
			else
			{
				return DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowIdStr);
			}
		}

		return null;
	}

	public ProcessId getProcessId()
	{
		return processId;
	}

	public DocumentPath getSingleDocumentPath()
	{
		return _singleDocumentPath;
	}

	public ViewId getViewId()
	{
		if(viewId == null || viewId.isEmpty())
		{
			return null;
		}
		return ViewId.of(windowId, viewId);
	}

	public Set<DocumentId> getViewDocumentIds()
	{
		if (_viewDocumentIds == null)
		{
			_viewDocumentIds = DocumentId.ofStringSet(viewDocumentIdsStrings);
		}
		return _viewDocumentIds;
	}
}
