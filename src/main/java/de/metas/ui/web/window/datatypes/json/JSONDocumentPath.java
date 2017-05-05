package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;

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
public class JSONDocumentPath implements Serializable
{
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
	@JsonProperty("tabid")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DetailId tabid;
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
			, @JsonProperty("tabid") final DetailId tabid //
			, @JsonProperty("rowId") final DocumentId rowId //
			, @JsonProperty("fieldName") final String fieldName)
	{
		if (windowId == null && processId == null)
		{
			throw new IllegalArgumentException("windowId or processId shall be set");
		}
		else if(windowId != null && processId != null)
		{
			throw new IllegalArgumentException("windowId or processId shall be set but not all of them");
		}
		else
		{
			this.windowId = windowId;
			this.processId = processId;
		}

		this.documentId = documentId;
		this.tabid = tabid;
		this.rowId = rowId;
		this.fieldName = fieldName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("windowId", windowId)
				.add("processId", processId)
				.add("documentId", documentId)
				.add("tabid", tabid)
				.add("rowId", rowId)
				.add("fieldName", fieldName)
				.toString();
	}

	public DocumentPath toSingleDocumentPath()
	{
		if (windowId != null)
		{
			return DocumentPath.singleWindowDocumentPath(windowId, documentId, tabid, rowId);
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
