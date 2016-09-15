package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

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
	public static List<JSONDocumentQueryFilterDescriptor> ofList(final List<DocumentQueryFilterDescriptor> filters, final String adLanguage)
	{
		if (filters == null || filters.isEmpty())
		{
			return ImmutableList.of();
		}

		return filters.stream()
				.map(filter -> of(filter, adLanguage))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final JSONDocumentQueryFilterDescriptor of(final DocumentQueryFilterDescriptor filter, final String adLanguage)
	{
		return new JSONDocumentQueryFilterDescriptor(filter, adLanguage);
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

	private JSONDocumentQueryFilterDescriptor(final DocumentQueryFilterDescriptor filter, final String adLanguage)
	{
		super();
		filterId = filter.getId();
		caption = filter.getDisplayName(adLanguage);
		frequentUsed = filter.isFrequentUsed();
		parameters = JSONDocumentQueryFilterParamDescriptor.ofList(filter.getParameters(), adLanguage);
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
}
