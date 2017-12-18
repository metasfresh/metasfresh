package de.metas.ui.web.document.filter;

import java.util.List;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.document.filter.json.JSONDocumentFilter;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public final class DocumentFiltersList
{
	public static DocumentFiltersList ofFilters(final List<DocumentFilter> filters)
	{
		if (filters == null || filters.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONDocumentFilter> jsonFiltersEffective = null;
		final ImmutableList<DocumentFilter> filtersEffective = ImmutableList.copyOf(filters);
		return new DocumentFiltersList(jsonFiltersEffective, filtersEffective);
	}

	public static DocumentFiltersList ofJSONFilters(final List<JSONDocumentFilter> jsonFilters)
	{
		if (jsonFilters == null || jsonFilters.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONDocumentFilter> jsonFiltersEffective = ImmutableList.copyOf(jsonFilters);
		final ImmutableList<DocumentFilter> filtersEffective = null;
		return new DocumentFiltersList(jsonFiltersEffective, filtersEffective);
	}

	public static final DocumentFiltersList EMPTY = new DocumentFiltersList();

	private final ImmutableList<JSONDocumentFilter> jsonFilters;
	private final ImmutableList<DocumentFilter> filters;

	private DocumentFiltersList(final ImmutableList<JSONDocumentFilter> jsonFilters, final ImmutableList<DocumentFilter> filters)
	{
		this.jsonFilters = jsonFilters;
		this.filters = filters;
	}

	/** empty constructor */
	private DocumentFiltersList()
	{
		filters = ImmutableList.of();
		jsonFilters = null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).omitNullValues().addValue(jsonFilters).addValue(filters).toString();
	}

	public boolean isJson()
	{
		return jsonFilters != null;
	}

	public List<JSONDocumentFilter> getJsonFilters()
	{
		if (jsonFilters == null)
		{
			throw new AdempiereException("Json filters are not available for " + this);
		}
		return jsonFilters;
	}

	public List<DocumentFilter> getFilters()
	{
		if (jsonFilters != null && !jsonFilters.isEmpty())
		{
			throw new AdempiereException("Filters are not available because they were not unwrapped from JSON: " + this);
		}
		else if (filters == null)
		{
			return ImmutableList.of();
		}
		else
		{
			return filters;
		}
	}

	public DocumentFilter getFilterByIdOrNull(final String filterId)
	{
		return getFilters().stream()
				.filter(filter -> Objects.equals(filter.getFilterId(), filterId))
				.findFirst()
				.orElse(null);
	}

	public String getParamValueAsString(final String filterId, final String parameterName)
	{
		final DocumentFilter filter = getFilterByIdOrNull(filterId);
		if (filter == null)
		{
			return null;
		}

		return filter.getParameterValueAsString(parameterName);
	}

	public int getParamValueAsInt(final String filterId, final String parameterName, final int defaultValue)
	{
		final DocumentFilter filter = getFilterByIdOrNull(filterId);
		if (filter == null)
		{
			return defaultValue;
		}

		return filter.getParameterValueAsInt(parameterName, defaultValue);
	}

	public List<DocumentFilter> getOrUnwrapFilters(final DocumentFilterDescriptorsProvider descriptors)
	{
		if (filters != null)
		{
			return filters;
		}

		if (jsonFilters == null || jsonFilters.isEmpty())
		{
			return ImmutableList.of();
		}

		return JSONDocumentFilter.unwrapList(jsonFilters, descriptors);
	}

	public DocumentFiltersList unwrapAndCopy(final DocumentFilterDescriptorsProvider descriptors)
	{
		if (filters != null)
		{
			return this;
		}

		if (jsonFilters == null || jsonFilters.isEmpty())
		{
			return this;
		}

		final ImmutableList<DocumentFilter> filtersNew = JSONDocumentFilter.unwrapList(jsonFilters, descriptors);
		if (filtersNew.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONDocumentFilter> jsonFiltersNew = null;
		return new DocumentFiltersList(jsonFiltersNew, filtersNew);
	}
}