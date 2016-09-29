package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.model.DocumentQueryFilter;

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

public class JSONDocumentQueryFilter
{
	public static List<DocumentQueryFilter> unwrapList(final List<JSONDocumentQueryFilter> jsonFilters)
	{
		if (jsonFilters == null || jsonFilters.isEmpty())
		{
			return ImmutableList.of();
		}
		return jsonFilters
				.stream()
				.map(jsonFilter -> unwrap(jsonFilter))
				.filter(filter -> filter != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final DocumentQueryFilter unwrap(final JSONDocumentQueryFilter jsonFilter)
	{
		return DocumentQueryFilter.builder()
				.setFilterId(jsonFilter.getFilterId())
				.setParameters(JSONDocumentQueryFilterParam.unwrapList(jsonFilter.getParameters()))
				.build();
	}
	
	public static final JSONDocumentQueryFilter of(final DocumentQueryFilter filter)
	{
		final String filterId = filter.getFilterId();
		final List<JSONDocumentQueryFilterParam> jsonParameters = filter.getParameters()
				.stream()
				.map(filterParam->JSONDocumentQueryFilterParam.of(filterParam))
				.collect(GuavaCollectors.toImmutableList());
		
		return new JSONDocumentQueryFilter(filterId, jsonParameters);
	}

	@JsonProperty("filterId")
	private final String filterId;

	@JsonProperty("parameters")
	private final List<JSONDocumentQueryFilterParam> parameters;

	@JsonCreator
	private JSONDocumentQueryFilter(
			@JsonProperty("filterId") final String filterId //
			, @JsonProperty("parameters") final List<JSONDocumentQueryFilterParam> parameters //
	)
	{
		super();
		this.filterId = filterId;
		this.parameters = parameters;
	}

	public String getFilterId()
	{
		return filterId;
	}

	public List<JSONDocumentQueryFilterParam> getParameters()
	{
		return parameters;
	}
}
