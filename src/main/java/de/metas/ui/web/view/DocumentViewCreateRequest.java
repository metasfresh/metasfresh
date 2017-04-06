package de.metas.ui.web.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import lombok.AccessLevel;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Request to create a new {@link IDocumentViewSelection}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@lombok.Data
public final class DocumentViewCreateRequest
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

	private final String documentType;
	private final JSONViewDataType viewType;

	private final Set<DocumentPath> referencingDocumentPaths;
	private final List<JSONDocumentFilter> filters;
	private final Set<Integer> filterOnlyIds;

	private final int queryFirstRow;
	private final int queryPageLength;

	@lombok.Setter(AccessLevel.NONE)
	@lombok.Getter(AccessLevel.NONE)
	private transient Integer _adWindowId; // lazy


	private DocumentViewCreateRequest(final Builder builder)
	{
		documentType = builder.getDocumentType();
		viewType = builder.getViewType();

		referencingDocumentPaths = builder.getReferencingDocumentPaths();
		filters = builder.getFilters();
		filterOnlyIds = builder.getFilterOnlyIds();

		queryFirstRow = builder.getQueryFirstRow();
		queryPageLength = builder.getQueryPageLength();
	}

	public int getAD_Window_ID()
	{
		if(_adWindowId == null)
		{
			_adWindowId = Integer.parseInt(documentType);
		}
		return _adWindowId;
	}

	public Characteristic getViewTypeRequiredFieldCharacteristic()
	{
		Check.assumeNotNull(viewType, "Parameter viewType is not null for {}", this);
		return viewType.getRequiredFieldCharacteristic();
	}

	public DocumentPath getSingleReferencingDocumentPathOrNull()
	{
		if (referencingDocumentPaths.isEmpty())
		{
			return null;
		}
		else
		{
			// NOTE: preserving the old logic and returning the first documentPath
			return referencingDocumentPaths.iterator().next();
		}
	}

	//
	//
	//
	public static final class Builder
	{
		private final String documentType;
		private final JSONViewDataType viewType;

		private Set<DocumentPath> referencingDocumentPaths;
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

		public DocumentViewCreateRequest build()
		{
			return new DocumentViewCreateRequest(this);
		}

		private String getDocumentType()
		{
			return documentType;
		}

		private JSONViewDataType getViewType()
		{
			return viewType;
		}

		public Builder setReferencingDocumentPaths(final Set<DocumentPath> referencingDocumentPaths)
		{
			this.referencingDocumentPaths = referencingDocumentPaths;
			return this;
		}

		private Set<DocumentPath> getReferencingDocumentPaths()
		{
			return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
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
