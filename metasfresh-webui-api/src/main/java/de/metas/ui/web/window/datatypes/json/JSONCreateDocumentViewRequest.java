package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;

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
public final class JSONCreateDocumentViewRequest implements Serializable
{
	public static JSONCreateDocumentViewRequest of(
			final int adWindowId //
			, final JSONViewDataType viewType //
			, final List<JSONDocumentFilter> filters //
			, final int queryFirstRow //
			, final int queryPageLength //
	)
	{
		final String documentType = String.valueOf(adWindowId);
		final JSONReferencing referencing = null; // N/A
		return new JSONCreateDocumentViewRequest(documentType, viewType, referencing, filters, queryFirstRow, queryPageLength);
	}

	@JsonProperty("documentType")
	private final String documentType;

	@JsonProperty("viewType")
	private final JSONViewDataType viewType;

	@JsonProperty("referencing")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final JSONReferencing referencing;

	@JsonProperty("filters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilter> filters;

	@JsonProperty("queryFirstRow")
	private final int queryFirstRow;

	@JsonProperty("queryPageLength")
	private final int queryPageLength;

	@JsonCreator
	private JSONCreateDocumentViewRequest(
			@JsonProperty("documentType") final String documentType //
			, @JsonProperty("viewType") final JSONViewDataType viewType //
			, @JsonProperty("referencing") final JSONReferencing referencing //
			, @JsonProperty("filters") final List<JSONDocumentFilter> filters //
			, @JsonProperty("queryFirstRow") final int queryFirstRow //
			, @JsonProperty("queryPageLength") final int queryPageLength //
	)
	{
		super();
		this.documentType = documentType;
		this.viewType = viewType;
		this.referencing = referencing;
		this.filters = filters == null ? ImmutableList.of() : ImmutableList.copyOf(filters);
		this.queryFirstRow = queryFirstRow;
		this.queryPageLength = queryPageLength;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("documentType", documentType)
				.add("viewType", viewType)
				.add("referencing", referencing)
				.add("filters", filters.isEmpty() ? null : filters)
				.add("queryFirstRow", queryFirstRow)
				.add("queryPageLength", queryPageLength)
				.toString();
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public int getAD_Window_ID()
	{
		return Integer.parseInt(getDocumentType());
	}

	public JSONViewDataType getViewType()
	{
		return viewType;
	}
	
	public Characteristic getViewTypeRequiredFieldCharacteristic()
	{
		Check.assumeNotNull(viewType, "Parameter viewType is not null for {}", this);
		return viewType.getRequiredFieldCharacteristic();
	}
	
	public JSONReferencing getReferencing()
	{
		return referencing;
	}
	
	public DocumentPath getReferencingDocumentPathOrNull()
	{
		return referencing == null ? null : referencing.toDocumentPath();
	}

	public List<JSONDocumentFilter> getFilters()
	{
		return filters;
	}

	public int getQueryFirstRow()
	{
		return queryFirstRow;
	}

	public int getQueryPageLength()
	{
		return queryPageLength;
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static final class JSONReferencing implements Serializable
	{
		@JsonProperty("documentType")
		private final String documentType;

		@JsonProperty("documentId")
		private final String documentId;

		public JSONReferencing(
				@JsonProperty("documentType") final String documentType //
				, @JsonProperty("documentId") final String documentId //
		)
		{
			super();
			this.documentType = documentType;
			this.documentId = documentId;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("documentType", documentType)
					.add("documentId", documentId)
					.toString();
		}

		public String getDocumentType()
		{
			return documentType;
		}
		
		private int getAD_Window_ID()
		{
			return Integer.parseInt(documentType);
		}

		public String getDocumentId()
		{
			return documentId;
		}
		
		public DocumentPath toDocumentPath()
		{
			return DocumentPath.rootDocumentPath(DocumentType.Window, getAD_Window_ID(), getDocumentId());
		}
	}
}
