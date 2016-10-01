package de.metas.ui.web.window.datatypes.json;

import java.util.List;
import java.util.Map;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.ui.web.window.descriptor.DocumentQueryFilterDescriptor;
import de.metas.ui.web.window.descriptor.DocumentQueryFilterDescriptorsProvider;
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
	public static List<DocumentQueryFilter> unwrapList(final List<JSONDocumentQueryFilter> jsonFilters, final DocumentQueryFilterDescriptorsProvider filterDescriptorProvider)
	{
		if (jsonFilters == null || jsonFilters.isEmpty())
		{
			return ImmutableList.of();
		}
		return jsonFilters
				.stream()
				.map(jsonFilter -> unwrap(jsonFilter, filterDescriptorProvider))
				.filter(filter -> filter != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	public static final DocumentQueryFilter unwrap(final JSONDocumentQueryFilter jsonFilter, final DocumentQueryFilterDescriptorsProvider filterDescriptorProvider)
	{
		final String filterId = jsonFilter.getFilterId();
		final DocumentQueryFilterDescriptor filterDescriptor = filterDescriptorProvider.getByFilterIdOrNull(filterId);

		// Ad-hoc filters (e.g. zoom references)
		if (filterDescriptor == null)
		{
			return unwrapAsGenericFilter(jsonFilter);
		}
		// Filter with descriptor
		else
		{
			return unwrapUsingDescriptor(jsonFilter, filterDescriptor);
		}
	}

	private static DocumentQueryFilter unwrapAsGenericFilter(final JSONDocumentQueryFilter jsonFilter)
	{
		return DocumentQueryFilter.builder()
				.setFilterId(jsonFilter.getFilterId())
				.setParameters(jsonFilter.getParameters()
						.stream()
						.map(jsonParam -> DocumentQueryFilterParam.builder()
								.setFieldName(jsonParam.getParameterName())
								.setValue(jsonParam.getValue())
								.setValueTo(jsonParam.getValueTo())
								.setOperator()
								.build())
						.collect(GuavaCollectors.toImmutableList()))
				.build();
	}

	private static DocumentQueryFilter unwrapUsingDescriptor(final JSONDocumentQueryFilter jsonFilter, final DocumentQueryFilterDescriptor filterDescriptor)
	{
		final DocumentQueryFilter.Builder filter = DocumentQueryFilter.builder()
				.setFilterId(jsonFilter.getFilterId());

		final Map<String, JSONDocumentQueryFilterParam> jsonParams = Maps.uniqueIndex(jsonFilter.getParameters(), jsonParam -> jsonParam.getParameterName());

		for (final DocumentQueryFilterParamDescriptor paramDescriptor : filterDescriptor.getParameters())
		{
			final String parameterName = paramDescriptor.getParameterName();
			final JSONDocumentQueryFilterParam jsonParam = jsonParams.get(parameterName);
			if (jsonParam == null)
			{
				throw new IllegalArgumentException("Parameter '" + parameterName + "' was not provided");
			}

			final Object value = jsonParam.getValue();
			final Object valueTo = jsonParam.getValueTo();
			filter.addParameter(DocumentQueryFilterParam.builder()
					.setFieldName(paramDescriptor.getFieldName())
					.setOperator(paramDescriptor.getOperator())
					.setValue(value)
					.setValueTo(valueTo)
					.build());
		}

		for (final DocumentQueryFilterParam internalParam : filterDescriptor.getInternalParameters())
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
