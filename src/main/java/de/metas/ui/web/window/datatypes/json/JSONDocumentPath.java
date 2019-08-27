package de.metas.ui.web.window.datatypes.json;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.util.Check;
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDocumentPath
{
	public static final JSONDocumentPath ofWindowDocumentPath(@NonNull final DocumentPath documentPath)
	{
		final String fieldName = null;
		return ofWindowDocumentPath(documentPath, fieldName);
	}

	public static final JSONDocumentPath ofWindowDocumentPath(@NonNull final DocumentPath documentPath, @Nullable final String fieldName)
	{
		final JSONDocumentPathBuilder builder = builder()
				.windowId(documentPath.getWindowId())
				.documentId(documentPath.getDocumentId())
				.fieldName(fieldName); // optional

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

	public static final DocumentPath toDocumentPathOrNull(@Nullable final JSONDocumentPath jsonDocumentPath)
	{
		return jsonDocumentPath != null ? jsonDocumentPath.toDocumentPath() : null;
	}

	public static JSONDocumentPath newWindowRecord(@NonNull final WindowId windowId)
	{
		return builder().windowId(windowId).documentId(DocumentId.NEW).build();
	}

	@JsonProperty("windowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final WindowId windowId;

	@JsonProperty("processId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final ProcessId processId;

	@JsonProperty("viewId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final ViewId viewId;

	//
	@JsonProperty("documentId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DocumentId documentId;
	//
	@JsonProperty("tabId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DetailId tabId;

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
			@JsonProperty("windowId") final WindowId windowId,
			@JsonProperty("processId") final ProcessId processId,
			@JsonProperty("viewId") final ViewId viewId,
			@JsonProperty("documentId") final DocumentId documentId,
			@JsonProperty("tabId") final DetailId tabId,
			@JsonProperty("rowId") final DocumentId rowId,
			@JsonProperty("fieldName") final String fieldName)
	{
		if (windowId != null)
		{
			Check.assumeNull(processId, "processId shall be null when windowId is set");
			Check.assumeNull(viewId, "viewId shall be null when windowId is set");
			Check.assumeNotNull(documentId, "Parameter documentId is not null");

			this.windowId = windowId;
			this.processId = null;
			this.viewId = null;

			this.documentId = documentId;
			this.tabId = tabId;
			this.rowId = rowId;
		}
		else if (processId != null)
		{
			Check.assumeNull(windowId, "windowId shall be null when processId is set");
			Check.assumeNull(viewId, "viewId shall be null when processId is set");

			this.windowId = null;
			this.processId = processId;
			this.viewId = null;

			this.documentId = documentId;
			this.tabId = null;
			this.rowId = null;
		}
		else if (viewId != null)
		{
			Check.assumeNull(windowId, "windowId shall be null when viewId is set");
			Check.assumeNull(processId, "processId shall be null when viewId is set");
			Check.assumeNotNull(rowId, "Parameter rowId is not null");

			this.windowId = null;
			this.processId = null;
			this.viewId = viewId;

			this.documentId = null;
			this.tabId = null;
			this.rowId = rowId;
		}
		else
		{
			throw new AdempiereException("At least windowId or processId or viewId shall be set");
		}

		this.fieldName = fieldName;
	}

	public boolean isWindow()
	{
		return windowId != null;
	}

	public boolean isProcess()
	{
		return processId != null;
	}

	public boolean isView()
	{
		return viewId != null;
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

	private DocumentPath toDocumentPath()
	{
		final DocumentPath.Builder builder = DocumentPath.builder();

		// Window
		if (windowId != null)
		{
			builder.setDocumentType(windowId);
		}
		// Process
		else if (processId != null)
		{
			builder.setDocumentType(DocumentType.Process, processId.toDocumentId());
		}
		else
		{
			throw new AdempiereException("Cannot identify the document type because it's not window nor process")
					.setParameter("documentPath", this);
		}

		return builder
				.setDocumentId(documentId)
				.setDetailId(tabId)
				.setRowId(rowId)
				.build();
	}
}
