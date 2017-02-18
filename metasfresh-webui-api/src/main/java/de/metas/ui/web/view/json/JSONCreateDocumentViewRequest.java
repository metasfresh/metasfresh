package de.metas.ui.web.view.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
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
	public static final Builder builder(final String documentType, final JSONViewDataType viewType)
	{
		return new Builder(documentType, viewType);
	}

	public static final Builder builder(final int adWindowId, final JSONViewDataType viewType)
	{
		final String documentType = String.valueOf(adWindowId);
		return new Builder(documentType, viewType);
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

	@JsonProperty("filterOnlyIds")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Set<Integer> filterOnlyIds;

	@JsonProperty("queryFirstRow")
	private final int queryFirstRow;

	@JsonProperty("queryPageLength")
	private final int queryPageLength;

	private JSONCreateDocumentViewRequest(final Builder builder)
	{
		super();
		documentType = builder.getDocumentType();
		viewType = builder.getViewType();

		referencing = builder.getReferencing();
		filters = builder.getFilters();
		filterOnlyIds = builder.getFilterOnlyIds();

		queryFirstRow = builder.getQueryFirstRow();
		queryPageLength = builder.getQueryPageLength();
	}

	@JsonCreator
	private JSONCreateDocumentViewRequest(
			@JsonProperty("documentType") final String documentType //
			, @JsonProperty("viewType") final JSONViewDataType viewType //
			, @JsonProperty("referencing") final JSONReferencing referencing //
			, @JsonProperty("filters") final List<JSONDocumentFilter> filters //
			, @JsonProperty("filterOnlyIds") final List<Integer> filterOnlyIds //
			, @JsonProperty("queryFirstRow") final int queryFirstRow //
			, @JsonProperty("queryPageLength") final int queryPageLength //
	)
	{
		super();
		this.documentType = documentType;
		this.viewType = viewType;

		this.referencing = referencing;
		this.filters = filters == null ? ImmutableList.of() : ImmutableList.copyOf(filters);
		this.filterOnlyIds = filterOnlyIds == null ? ImmutableSet.of() : ImmutableSet.copyOf(filterOnlyIds);

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
				.add("filterOnlyIds", filterOnlyIds.isEmpty() ? null : filterOnlyIds)
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

	public DocumentPath getReferencingDocumentPathOrNull()
	{
		return referencing == null ? null : referencing.toDocumentPath();
	}

	public List<JSONDocumentFilter> getFilters()
	{
		return filters;
	}

	public Set<Integer> getFilterOnlyIds()
	{
		return filterOnlyIds;
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
		public static final JSONReferencing of(final int adWindowId, final int documentId)
		{
			return new JSONReferencing(String.valueOf(adWindowId), String.valueOf(documentId));
		}
		
		@JsonProperty("documentType")
		private final String documentType;

		@JsonProperty("documentId")
		private final String documentId;

		@JsonCreator
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

	public static final class Builder
	{
		private final String documentType;
		private final JSONViewDataType viewType;

		private JSONReferencing referencing;
		private List<JSONDocumentFilter> filters;
		private Set<Integer> filterOnlyIds;

		private int queryFirstRow = -1;
		private int queryPageLength = -1;

		private Builder(final String documentType, final JSONViewDataType viewType)
		{
			super();
			Check.assumeNotEmpty(documentType, "documentType is not empty");
			this.documentType = documentType;

			Check.assumeNotNull(viewType, "Parameter viewType is not null");
			this.viewType = viewType;
		}

		public JSONCreateDocumentViewRequest build()
		{
			return new JSONCreateDocumentViewRequest(this);
		}

		private String getDocumentType()
		{
			return documentType;
		}

		private JSONViewDataType getViewType()
		{
			return viewType;
		}

		public Builder setReferencing(final JSONReferencing referencing)
		{
			this.referencing = referencing;
			return this;
		}

		private JSONReferencing getReferencing()
		{
			return referencing;
		}

		public Builder setFilters(final List<JSONDocumentFilter> filters)
		{
			this.filters = filters;
			return this;
		}

		private List<JSONDocumentFilter> getFilters()
		{
			return filters == null ? ImmutableList.of() : ImmutableList.copyOf(filters);
		}

		public Builder setFilterOnlyIds(final Collection<Integer> filterOnlyIds)
		{
			if (this.filterOnlyIds == null)
			{
				this.filterOnlyIds = new HashSet<>();
			}
			this.filterOnlyIds.addAll(filterOnlyIds);
			return this;
		}

		public Builder addFilterOnlyId(final int filterOnlyId)
		{
			if (filterOnlyIds == null)
			{
				filterOnlyIds = new HashSet<>();
			}
			filterOnlyIds.add(filterOnlyId);
			return this;
		}

		private Set<Integer> getFilterOnlyIds()
		{
			return filterOnlyIds == null ? ImmutableSet.of() : ImmutableSet.copyOf(filterOnlyIds);
		}

		public Builder setFetchPage(final int queryFirstRow, final int queryPageLength)
		{
			this.queryFirstRow = queryFirstRow;
			this.queryPageLength = queryPageLength;
			return this;
		}

		private int getQueryFirstRow()
		{
			return queryFirstRow;
		}

		private int getQueryPageLength()
		{
			return queryPageLength;
		}
	}

}
