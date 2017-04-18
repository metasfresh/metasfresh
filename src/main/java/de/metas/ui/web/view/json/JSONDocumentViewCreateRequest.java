package de.metas.ui.web.view.json;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;

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
@lombok.Data
public final class JSONDocumentViewCreateRequest implements Serializable
{
	@JsonProperty("documentType")
	private final WindowId windowId;

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

	@JsonCreator
	private JSONDocumentViewCreateRequest(
			@JsonProperty("documentType") final WindowId windowId //
			, @JsonProperty("viewType") final JSONViewDataType viewType //
			, @JsonProperty("referencing") final JSONReferencing referencing //
			, @JsonProperty("filters") final List<JSONDocumentFilter> filters //
			, @JsonProperty("filterOnlyIds") final List<Integer> filterOnlyIds //
			, @JsonProperty("queryFirstRow") final int queryFirstRow //
			, @JsonProperty("queryPageLength") final int queryPageLength //
	)
	{
		super();
		this.windowId = windowId;
		this.viewType = viewType;

		this.referencing = referencing;
		this.filters = filters == null ? ImmutableList.of() : ImmutableList.copyOf(filters);
		this.filterOnlyIds = filterOnlyIds == null ? ImmutableSet.of() : ImmutableSet.copyOf(filterOnlyIds);

		this.queryFirstRow = queryFirstRow;
		this.queryPageLength = queryPageLength;
	}

	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		if (referencing == null)
		{
			return ImmutableSet.of();
		}

		final Set<String> documentIds = referencing.getDocumentIds();
		if (documentIds == null || documentIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final WindowId windowId = WindowId.fromJson(referencing.getDocumentType());
		return documentIds.stream()
				.map(id -> DocumentPath.rootDocumentPath(windowId, id))
				.collect(GuavaCollectors.toImmutableSet());
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Data
	public static final class JSONReferencing implements Serializable
	{
		@JsonProperty("documentType")
		private final String documentType;

		@JsonProperty("documentIds")
		private final Set<String> documentIds;

		@JsonCreator
		private JSONReferencing(
				@JsonProperty("documentType") final String documentType //
				, @JsonProperty("documentIds") final Set<String> documentIds //
				, @JsonProperty("documentId") @Deprecated final String documentId //
		)
		{
			super();
			Preconditions.checkNotNull(documentType, "documentType is missing");
			this.documentType = documentType;

			if (documentIds != null)
			{
				Preconditions.checkArgument(documentId == null, "deprecated 'documentId' shall be not set if 'documentIds' is set");
				Preconditions.checkArgument(!documentIds.isEmpty(), "'documentIds' is empty");
				this.documentIds = ImmutableSet.copyOf(documentIds);
			}
			else if (documentId != null)
			{
				this.documentIds = ImmutableSet.of(documentId);
			}
			else
			{
				throw new IllegalArgumentException("'documentIds' not provided");
			}
		}
	}
}
