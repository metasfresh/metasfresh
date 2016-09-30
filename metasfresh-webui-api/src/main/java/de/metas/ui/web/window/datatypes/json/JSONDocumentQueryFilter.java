package de.metas.ui.web.window.datatypes.json;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.ui.web.window.descriptor.DocumentQueryFilterDescriptor;
import de.metas.ui.web.window.descriptor.DocumentQueryFilterParamDescriptor;
import de.metas.ui.web.window.model.DocumentQueryFilter;
import de.metas.ui.web.window.model.DocumentQueryFilterParam;

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
	@Deprecated
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

	public static List<DocumentQueryFilter> unwrapList(final List<JSONDocumentQueryFilter> jsonFilters, Function<String, DocumentQueryFilterDescriptor> descriptorProvider)
	{
		if (jsonFilters == null || jsonFilters.isEmpty())
		{
			return ImmutableList.of();
		}
		return jsonFilters
				.stream()
				.map(jsonFilter -> unwrap(jsonFilter, descriptorProvider))
				.filter(filter -> filter != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	public static final DocumentQueryFilter unwrap(final JSONDocumentQueryFilter jsonFilter, Function<String, DocumentQueryFilterDescriptor> descriptorProvider)
	{
		final String filterId = jsonFilter.getFilterId();
		final DocumentQueryFilterDescriptor descriptor = descriptorProvider.apply(filterId);
		final DocumentQueryFilter.Builder filter = DocumentQueryFilter.builder()
				.setFilterId(filterId);

		final Map<String, JSONDocumentQueryFilterParam> jsonParams = Maps.uniqueIndex(jsonFilter.getParameters(), jsonParam -> jsonParam.getField());

		for (final DocumentQueryFilterParamDescriptor paramDescriptor : descriptor.getParameters())
		{
			final String fieldName = paramDescriptor.getFieldName();
			final JSONDocumentQueryFilterParam jsonParam = jsonParams.get(fieldName);
			final Object value = jsonParam.getValue();
			final Object valueTo = jsonParam.getValueTo();
			filter.addParameter(DocumentQueryFilterParam.builder()
					.setFieldName(fieldName)
					.setRange(paramDescriptor.isRangeParameter())
					.setValue(value)
					.setValueTo(valueTo)
					.build());
		}

		for (final DocumentQueryFilterParam internalParam : descriptor.getInternalParameters())
		{
			filter.addParameter(internalParam);
		}

		return filter.build();
	}

	public static final JSONDocumentQueryFilter of(final DocumentQueryFilter filter)
	{
		final String filterId = filter.getFilterId();
		final List<JSONDocumentQueryFilterParam> jsonParameters = filter.getParameters()
				.stream()
				.map(filterParam -> JSONDocumentQueryFilterParam.of(filterParam))
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
