package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.DocumentPath;

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

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONDocumentPath implements Serializable
{
	@JsonProperty("documentType")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String documentType;
	//
	@JsonProperty("documentId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String documentId;
	//
	@JsonProperty("tabid")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String tabid;
	//
	@JsonProperty("rowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String rowId;

	@JsonProperty("fieldName")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String fieldName;

	@JsonCreator
	private JSONDocumentPath(
			@JsonProperty("documentType") final String documentType //
			, @JsonProperty("documentId") final String documentId //
			, @JsonProperty("tabid") final String tabid //
			, @JsonProperty("rowId") final String rowId //
			, @JsonProperty("fieldName") final String fieldName)
	{
		super();
		this.documentType = documentType;
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
				.add("documentType", documentType)
				.add("documentId", documentId)
				.add("tabid", tabid)
				.add("rowId", rowId)
				.add("fieldName", fieldName)
				.toString();
	}

	public String getType()
	{
		return documentType;
	}

	public int getAD_Window_ID()
	{
		if (Check.isEmpty(documentType, true))
		{
			return -1;
		}

		return Integer.parseInt(documentType.trim());
	}

	public String getDocumentId()
	{
		return documentId;
	}

	public String getTabid()
	{
		return tabid;
	}

	public String getRowId()
	{
		return rowId;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public DocumentPath toSingleDocumentPath()
	{
		return DocumentPath.singleWindowDocumentPath(getAD_Window_ID(), getDocumentId(), getTabid(), getRowId());
	}
}
