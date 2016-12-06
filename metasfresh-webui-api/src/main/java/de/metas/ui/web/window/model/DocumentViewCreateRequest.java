package de.metas.ui.web.window.model;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.filters.DocumentFilter;

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

public class DocumentViewCreateRequest
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final DocumentEntityDescriptor entityDescriptor;
	private final List<DocumentFieldDescriptor> viewFields;

	private final List<DocumentFilter> stickyFilters;
	private final List<DocumentFilter> filters;

	private DocumentViewCreateRequest(final Builder builder)
	{
		super();
		entityDescriptor = builder.entityDescriptor;
		viewFields = builder.viewFields;
		stickyFilters = builder.stickyFilters == null ? ImmutableList.of() : ImmutableList.copyOf(builder.stickyFilters);
		filters = builder.filters == null ? ImmutableList.of() : ImmutableList.copyOf(builder.filters);
	}
	
	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}
	
	public List<DocumentFieldDescriptor> getViewFields()
	{
		return viewFields;
	}
	
	public List<DocumentFilter> getStickyFilters()
	{
		return stickyFilters;
	}
	
	public List<DocumentFilter> getFilters()
	{
		return filters;
	}

	public static final class Builder
	{
		private DocumentEntityDescriptor entityDescriptor;
		private List<DocumentFieldDescriptor> viewFields;

		private List<DocumentFilter> stickyFilters;
		private List<DocumentFilter> filters;

		private Builder()
		{
			super();
		}

		public DocumentViewCreateRequest build()
		{
			return new DocumentViewCreateRequest(this);
		}

		public Builder setEntityDescriptor(final DocumentEntityDescriptor entityDescriptor)
		{
			this.entityDescriptor = entityDescriptor;
			return this;
		}

		public Builder setViewFields(final List<DocumentFieldDescriptor> viewFields)
		{
			this.viewFields = viewFields;
			return this;
		}

		public Builder addStickyFilter(final DocumentFilter filter)
		{
			Check.assumeNotNull(filter, "Parameter filter is not null");

			if (stickyFilters == null)
			{
				stickyFilters = new ArrayList<>();
			}
			stickyFilters.add(filter);
			return this;
		}

		public Builder addFilters(final List<DocumentFilter> filtersToAdd)
		{
			if (filtersToAdd == null || filtersToAdd.isEmpty())
			{
				return this;
			}

			if (filters == null)
			{
				filters = new ArrayList<>();
			}
			filters.addAll(filtersToAdd);
			return this;
		}

	}
}
