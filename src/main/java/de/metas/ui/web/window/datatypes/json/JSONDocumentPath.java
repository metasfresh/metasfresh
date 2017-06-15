package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
@Value
public class JSONDocumentPath implements Serializable
{
	public static final JSONDocumentPath ofWindowDocumentPath(@NonNull final DocumentPath documentPath)
	{
		final String fieldName = null;
		return ofWindowDocumentPath(documentPath, fieldName);
	}

	public static final JSONDocumentPath ofWindowDocumentPath(@NonNull final DocumentPath documentPath, final String fieldName)
	{
		final JSONDocumentPathBuilder builder = builder()
				.fieldName(fieldName) // optional
				.windowId(documentPath.getWindowId())
				.documentId(documentPath.getDocumentId());

		if (documentPath.isRootDocument())
		{
			return builder.build();
		}
		else if (documentPath.isSingleIncludedDocument())
		{
			return builder
					.tabId(documentPath.getDetailId())
					.rowId(documentPath.getSingleRowId())
					.build();
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert " + documentPath + " to JSON");
		}
	}

	public static final DocumentPath toDocumentPathOrNull(final JSONDocumentPath jsonDocumentPath)
	{
		if(jsonDocumentPath == null)
		{
			return null;
		}

		final DocumentPath.Builder builder = DocumentPath.builder();

		// Window
		if (jsonDocumentPath.getWindowId() != null)
		{
			builder.setDocumentType(jsonDocumentPath.getWindowId());
		}
		// Process
		else if (jsonDocumentPath.getProcessId() != null)
		{
			builder.setDocumentType(DocumentType.Process, jsonDocumentPath.getProcessId().toDocumentId());
		}
		else
		{
			throw new AdempiereException("Cannot identify the document type because it's not window nor process")
					.setParameter("documentPath", jsonDocumentPath);
		}
		return builder
				.setDocumentId(jsonDocumentPath.getDocumentId())
				.setDetailId(jsonDocumentPath.getTabId())
				.setRowId(jsonDocumentPath.getRowId())
				.build();
	}

	@JsonProperty("windowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final WindowId windowId;

	@JsonProperty("processId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final ProcessId processId;

	//
	@JsonProperty("documentId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DocumentId documentId;
	//
	@JsonProperty("tabId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DetailId tabId;
	//
	@JsonProperty("tabid")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private final DetailId legacyTabId;

	//
	@JsonProperty("rowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DocumentId rowId;

	@JsonProperty("fieldName")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String fieldName;

	@JsonCreator
	@Builder
	private JSONDocumentPath(
			@JsonProperty("windowId") final WindowId windowId //
			, @JsonProperty("processId") final ProcessId processId //
			, @JsonProperty("documentId") final DocumentId documentId //
			, @JsonProperty("tabId") final DetailId tabId //
			, @JsonProperty("tabid") @Deprecated final DetailId legacyTabId //
			, @JsonProperty("rowId") final DocumentId rowId //
			, @JsonProperty("fieldName") final String fieldName)
	{
		if (windowId == null && processId == null)
		{
			throw new IllegalArgumentException("windowId or processId shall be set");
		}
		else if (windowId != null && processId != null)
		{
			throw new IllegalArgumentException("windowId or processId shall be set but not all of them");
		}
		else
		{
			this.windowId = windowId;
			this.processId = processId;
		}

		this.documentId = documentId;

		this.tabId = extractTabId(tabId, legacyTabId);
		this.legacyTabId = this.tabId;
		this.rowId = rowId;
		this.fieldName = fieldName;
	}

	private static final DetailId extractTabId(final DetailId tabId, final DetailId legacyTabId)
	{
		if (tabId == legacyTabId)
		{
			return tabId;
		}
		else if (tabId != null && legacyTabId == null)
		{
			return tabId;
		}
		else if (tabId == null && legacyTabId != null)
		{
			return legacyTabId;
		}
		else if (Objects.equals(tabId, legacyTabId))
		{
			return tabId;
		}
		else
		{
			throw new IllegalArgumentException("tabId and tabid(legacy) does not match");
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("windowId", windowId)
				.add("processId", processId)
				.add("documentId", documentId)
				.add("tabId", tabId)
				.add("rowId", rowId)
				.add("fieldName", fieldName)
				.toString();
	}

	public DocumentPath toSingleDocumentPath()
	{
		if (windowId != null)
		{
			return DocumentPath.singleWindowDocumentPath(windowId, documentId, tabId, rowId);
		}
		else if (processId != null)
		{
			return DocumentPath.rootDocumentPath(DocumentType.Process, processId.toDocumentId(), documentId);
		}
		else
		{
			throw new IllegalStateException("Cannot create single document path from " + this);
		}
	}
}
