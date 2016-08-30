package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import io.swagger.annotations.ApiModel;

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

@ApiModel("tab")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutTab implements Serializable
{
	static List<JSONDocumentLayoutTab> ofList(final List<DocumentLayoutDetailDescriptor> details, final JSONFilteringOptions jsonFilteringOpts)
	{
		return details.stream()
				.map(detail -> of(detail, jsonFilteringOpts))
				.filter(jsonDetail -> jsonDetail.hasElements())
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONDocumentLayoutTab of(final DocumentLayoutDetailDescriptor detail, final JSONFilteringOptions jsonFilteringOpts)
	{
		return new JSONDocumentLayoutTab(detail, jsonFilteringOpts);
	}

	@JsonProperty("tabid")
	private final String tabid;

	@JsonProperty("caption")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String caption;

	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String description;

	@JsonProperty("elements")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElement> elements;

	private JSONDocumentLayoutTab(final DocumentLayoutDetailDescriptor detail, final JSONFilteringOptions jsonFilteringOpts)
	{
		super();
		tabid = detail.getDetailId();
		caption = detail.getCaption();
		description = detail.getDescription();
		elements = JSONDocumentLayoutElement.ofList(detail.getElements(), jsonFilteringOpts);
	}

	@JsonCreator
	private JSONDocumentLayoutTab(
			@JsonProperty("tabid") final String tabid //
			, @JsonProperty("caption") final String caption //
			, @JsonProperty("description") final String description //
			, @JsonProperty("elements") final List<JSONDocumentLayoutElement> elements //
	)
	{
		super();
		this.tabid = tabid;
		this.caption = caption;
		this.description = description;
		this.elements = elements == null ? ImmutableList.of() : ImmutableList.copyOf(elements);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tabid", tabid)
				.add("caption", caption)
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
	}

	public String getTabid()
	{
		return tabid;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getDescription()
	{
		return description;
	}

	public List<JSONDocumentLayoutElement> getElements()
	{
		return elements;
	}

	public boolean hasElements()
	{
		return !elements.isEmpty();
	}

}
