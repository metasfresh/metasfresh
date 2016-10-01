package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentQueryFilterDescriptor;

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

@SuppressWarnings("serial")
public final class JSONDocumentQueryFilterDescriptor implements Serializable
{
	public static List<JSONDocumentQueryFilterDescriptor> ofCollection(@Nullable final Collection<DocumentQueryFilterDescriptor> filters, final JSONFilteringOptions jsonOpts)
	{
		if (filters == null || filters.isEmpty())
		{
			return ImmutableList.of();
		}

		return filters.stream()
				.map(filter -> new JSONDocumentQueryFilterDescriptor(filter, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	@JsonProperty("filterId")
	private final String filterId;

	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("frequent")
	private final boolean frequentUsed;

	@JsonProperty("parameters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentQueryFilterParamDescriptor> parameters;

	private final Map<String, Object> debugProperties;

	private JSONDocumentQueryFilterDescriptor(final DocumentQueryFilterDescriptor filter, final JSONFilteringOptions jsonOpts)
	{
		super();
		filterId = filter.getFilterId();
		caption = filter.getDisplayName(jsonOpts.getAD_Language());
		frequentUsed = filter.isFrequentUsed();
		parameters = JSONDocumentQueryFilterParamDescriptor.ofCollection(filter.getParameters(), jsonOpts);

		debugProperties = filter.getDebugProperties();
	}

	@JsonCreator
	private JSONDocumentQueryFilterDescriptor(
			@JsonProperty("filterId") final String filterId //
			, @JsonProperty("caption") final String caption //
			, @JsonProperty("frequent") final boolean frequentUsed //
			, @JsonProperty("parameters") final List<JSONDocumentQueryFilterParamDescriptor> parameters //
	)
	{
		this.filterId = filterId;
		this.caption = caption;
		this.frequentUsed = frequentUsed;
		this.parameters = parameters;
		debugProperties = new LinkedHashMap<>();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("filterId", filterId)
				.add("caption", caption)
				.add("frequentUsed", frequentUsed)
				.add("parameters", parameters.isEmpty() ? null : parameters)
				.add("debugProperties", debugProperties.isEmpty() ? null : debugProperties)
				.toString();
	}

	public String getFilterId()
	{
		return filterId;
	}

	public String getCaption()
	{
		return caption;
	}

	public boolean isFrequentUsed()
	{
		return frequentUsed;
	}

	public List<JSONDocumentQueryFilterParamDescriptor> getParameters()
	{
		return parameters;
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
