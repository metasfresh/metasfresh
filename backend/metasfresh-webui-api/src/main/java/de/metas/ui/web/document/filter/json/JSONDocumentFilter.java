package de.metas.ui.web.document.filter.json;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.Value;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public final class JSONDocumentFilter
{
	public static DocumentFilterList unwrapList(final List<JSONDocumentFilter> jsonFilters, final DocumentFilterDescriptorsProvider filterDescriptorProvider)
	{
		if (jsonFilters == null || jsonFilters.isEmpty())
		{
			return DocumentFilterList.EMPTY;
		}
		return jsonFilters
				.stream()
				.map(jsonFilter -> unwrap(jsonFilter, filterDescriptorProvider))
				.filter(Objects::nonNull)
				.collect(DocumentFilterList.toDocumentFilterList());
	}

	public static DocumentFilter unwrap(
			@NonNull final JSONDocumentFilter jsonFilter,
			@NonNull final DocumentFilterDescriptorsProvider filterDescriptorProvider)
	{
		final String filterId = jsonFilter.getFilterId();
		final DocumentFilterDescriptor filterDescriptor = filterDescriptorProvider.getByFilterIdOrNull(filterId);

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

	private static DocumentFilter unwrapAsGenericFilter(final JSONDocumentFilter jsonFilter)
	{
		return DocumentFilter.builder()
				.setFilterId(jsonFilter.getFilterId())
				.setParameters(jsonFilter.getParameters()
						.stream()
						.map(jsonParam -> DocumentFilterParam.builder()
								.setFieldName(jsonParam.getParameterName())
								.setValue(jsonParam.getValue())
								.setValueTo(jsonParam.getValueTo())
								.setOperator()
								.build())
						.collect(GuavaCollectors.toImmutableList()))
				.build();
	}

	private static DocumentFilter unwrapUsingDescriptor(
			@NonNull final JSONDocumentFilter jsonFilter,
			@NonNull final DocumentFilterDescriptor filterDescriptor)
	{
		final DocumentFilter.Builder filter = DocumentFilter.builder()
				.setFilterId(jsonFilter.getFilterId())
				.setFacetFilter(filterDescriptor.isFacetFilter());

		final Map<String, JSONDocumentFilterParam> jsonParams = Maps.uniqueIndex(jsonFilter.getParameters(), JSONDocumentFilterParam::getParameterName);

		for (final DocumentFilterParamDescriptor paramDescriptor : filterDescriptor.getParameters())
		{
			final String parameterName = paramDescriptor.getParameterName();
			final JSONDocumentFilterParam jsonParam = jsonParams.get(parameterName);

			// If parameter is missing: skip it if no required, else throw exception
			if (jsonParam == null)
			{
				if (paramDescriptor.isMandatory())
				{
					throw new IllegalArgumentException("Parameter '" + parameterName + "' was not provided");
				}
				continue;
			}

			final Object value = paramDescriptor.convertValueFromJson(jsonParam.getValue());
			final Object valueTo = paramDescriptor.convertValueFromJson(jsonParam.getValueTo());

			// If there was no value/valueTo provided: skip it if no required, else throw exception
			if (value == null && valueTo == null)
			{
				if (paramDescriptor.isMandatory())
				{
					throw new IllegalArgumentException("Parameter '" + parameterName + "' has no value");
				}
				continue;
			}

			filter.addParameter(DocumentFilterParam.builder()
					.setFieldName(paramDescriptor.getFieldName())
					.setOperator(paramDescriptor.getOperator())
					.setValue(value)
					.setValueTo(valueTo)
					.build());
		}

		for (final DocumentFilterParam internalParam : filterDescriptor.getInternalParameters())
		{
			filter.addInternalParameter(internalParam);
		}

		return filter.build();
	}

	public static List<JSONDocumentFilter> ofList(final DocumentFilterList filters, final JSONOptions jsonOpts)
	{
		if (filters == null || filters.isEmpty())
		{
			return ImmutableList.of();
		}

		return filters.stream()
				.map(filter -> of(filter, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static JSONDocumentFilter of(final DocumentFilter filter, final JSONOptions jsonOpts)
	{
		final String filterId = filter.getFilterId();
		final List<JSONDocumentFilterParam> jsonParameters = filter.getParameters()
				.stream()
				.filter(filterParam -> !filter.isInternalParameter(filterParam.getFieldName()))
				.map(filterParam -> JSONDocumentFilterParam.of(filterParam, jsonOpts))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(GuavaCollectors.toImmutableList());

		return new JSONDocumentFilter(filterId, filter.getCaption(jsonOpts.getAdLanguage()), jsonParameters);
	}

	@JsonProperty("filterId")
	private final String filterId;

	@JsonProperty("caption")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String caption;

	@JsonProperty("parameters")
	private final List<JSONDocumentFilterParam> parameters;

	@JsonCreator
	private JSONDocumentFilter(
			@JsonProperty("filterId") final String filterId,
			@JsonProperty("caption") final String caption,
			@JsonProperty("parameters") final List<JSONDocumentFilterParam> parameters)
	{
		Check.assumeNotEmpty(filterId, "filterId is not empty");

		this.filterId = filterId;
		this.caption = caption;
		this.parameters = parameters == null ? ImmutableList.of() : ImmutableList.copyOf(parameters);
	}
}
