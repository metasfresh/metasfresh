package de.metas.ui.web.document.filter.json;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
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

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentFilterDescriptor
{
	public static List<JSONDocumentFilterDescriptor> ofCollection(
			@Nullable final Collection<DocumentFilterDescriptor> filters,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		if (filters == null || filters.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<DocumentFilterDescriptor> filtersOrdered = filters.stream()
				.sorted(Comparator.comparing(DocumentFilterDescriptor::getSortNo))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<JSONDocumentFilterDescriptor> defaultFiltersList = filtersOrdered.stream()
				.filter(filter -> !filter.isFrequentUsed())
				.map(filter -> new JSONDocumentFilterDescriptor(filter, options))
				.collect(ImmutableList.toImmutableList());
		JSONDocumentFilterDescriptor defaultFiltersGroup = !defaultFiltersList.isEmpty()
				? new JSONDocumentFilterDescriptor("Filter", defaultFiltersList)
				: null;

		final ImmutableList.Builder<JSONDocumentFilterDescriptor> result = ImmutableList.builder();
		for (final DocumentFilterDescriptor filter : filtersOrdered)
		{
			if (filter.isFrequentUsed())
			{
				result.add(new JSONDocumentFilterDescriptor(filter, options));
			}
			else if (defaultFiltersGroup != null)
			{
				result.add(defaultFiltersGroup);
				defaultFiltersGroup = null;
			}
		}

		if (defaultFiltersGroup != null)
		{
			result.add(defaultFiltersGroup);
			defaultFiltersGroup = null;
		}

		return result.build();
	}

	@JsonProperty("filterId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String filterId;

	@JsonProperty("caption")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String caption;

	@JsonProperty("inlineRenderMode")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final DocumentFilterInlineRenderMode inlineRenderMode;

	@JsonProperty("barcodeScannerType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final BarcodeScannerType barcodeScannerType;

	@JsonProperty("parametersLayoutType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final PanelLayoutType parametersLayoutType;

	@JsonProperty("parameters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilterParamDescriptor> parameters;

	@JsonProperty("includedFilters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilterDescriptor> includedFilters;

	private final Map<String, Object> debugProperties;

	/** Standard constructor */
	private JSONDocumentFilterDescriptor(
			@NonNull final DocumentFilterDescriptor filter,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		filterId = filter.getFilterId();
		caption = filter.getDisplayName(options.getAdLanguage());
		inlineRenderMode = filter.getInlineRenderMode();
		barcodeScannerType = filter.getBarcodeScannerType();

		parametersLayoutType = filter.getParametersLayoutType();
		parameters = JSONDocumentFilterParamDescriptor.ofCollection(filter.getParameters(), options);

		includedFilters = null;

		debugProperties = filter.getDebugProperties()
				.withProperty("sortNo", filter.getSortNo())
				.toMap();
	}

	/** Filters group constructor */
	private JSONDocumentFilterDescriptor(
			final String caption,
			@NonNull final List<JSONDocumentFilterDescriptor> includedFilters)
	{
		filterId = null;
		this.caption = caption;
		inlineRenderMode = null;
		barcodeScannerType = null;
		parametersLayoutType = null;
		parameters = null;

		this.includedFilters = ImmutableList.copyOf(includedFilters);

		debugProperties = new LinkedHashMap<>();
	}

	@JsonCreator
	private JSONDocumentFilterDescriptor(
			@JsonProperty("filterId") final String filterId,
			@JsonProperty("caption") final String caption,
			@JsonProperty("inlineRenderMode") final DocumentFilterInlineRenderMode inlineRenderMode,
			@JsonProperty("barcodeScannerType") final BarcodeScannerType barcodeScannerType,
			@JsonProperty("parametersLayoutType") final PanelLayoutType parametersLayoutType,
			@JsonProperty("parameters") final List<JSONDocumentFilterParamDescriptor> parameters,
			@JsonProperty("includedFilters") final List<JSONDocumentFilterDescriptor> includedFilters)
	{
		this.filterId = filterId;
		this.caption = caption;
		this.inlineRenderMode = inlineRenderMode;
		this.barcodeScannerType = barcodeScannerType;

		this.parametersLayoutType = parametersLayoutType == null ? PanelLayoutType.Panel : parametersLayoutType;
		this.parameters = parameters;

		this.includedFilters = includedFilters != null && !includedFilters.isEmpty()
				? ImmutableList.copyOf(includedFilters)
				: null;

		debugProperties = new LinkedHashMap<>();
	}

	@JsonAnyGetter
	public Map<String, Object> getDebugProperties()
	{
		return debugProperties;
	}

	@JsonAnySetter
	private void putDebugProperty(final String name, final Object value)
	{
		debugProperties.put(name, value);
	}
}
