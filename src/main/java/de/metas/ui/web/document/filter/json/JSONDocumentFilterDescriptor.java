package de.metas.ui.web.document.filter.json;

import java.util.Collection;
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

import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.ToString;

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

@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentFilterDescriptor
{
	public static List<JSONDocumentFilterDescriptor> ofCollection(@Nullable final Collection<DocumentFilterDescriptor> filters, final JSONOptions jsonOpts)
	{
		if (filters == null || filters.isEmpty())
		{
			return ImmutableList.of();
		}

		return filters.stream()
				.map(filter -> new JSONDocumentFilterDescriptor(filter, jsonOpts))
				.collect(ImmutableList.toImmutableList());
	}

	@JsonProperty("filterId")
	private final String filterId;

	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("frequent")
	private final boolean frequentUsed;

	@JsonProperty("inlineRenderMode")
	private final DocumentFilterInlineRenderMode inlineRenderMode;

	@JsonProperty("parametersLayoutType")
	private final PanelLayoutType parametersLayoutType;

	@JsonProperty("parameters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilterParamDescriptor> parameters;

	private final Map<String, Object> debugProperties;

	private JSONDocumentFilterDescriptor(final DocumentFilterDescriptor filter, final JSONOptions jsonOpts)
	{
		filterId = filter.getFilterId();
		caption = filter.getDisplayName(jsonOpts.getAD_Language());
		frequentUsed = filter.isFrequentUsed();
		inlineRenderMode = filter.getInlineRenderMode();

		parametersLayoutType = filter.getParametersLayoutType();
		parameters = JSONDocumentFilterParamDescriptor.ofCollection(filter.getParameters(), jsonOpts);

		debugProperties = filter.getDebugProperties();
	}

	@JsonCreator
	private JSONDocumentFilterDescriptor(
			@JsonProperty("filterId") final String filterId,
			@JsonProperty("caption") final String caption,
			@JsonProperty("frequent") final boolean frequentUsed,
			@JsonProperty("inlineRenderMode") final DocumentFilterInlineRenderMode inlineRenderMode,
			@JsonProperty("parametersLayoutType") final PanelLayoutType parametersLayoutType,
			@JsonProperty("parameters") final List<JSONDocumentFilterParamDescriptor> parameters)
	{
		this.filterId = filterId;
		this.caption = caption;
		this.frequentUsed = frequentUsed;
		this.inlineRenderMode = inlineRenderMode;

		this.parametersLayoutType = parametersLayoutType == null ? PanelLayoutType.Panel : parametersLayoutType;
		this.parameters = parameters;

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
