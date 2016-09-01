package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutSideListDescriptor;
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

@ApiModel("sideList")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutSideList implements Serializable
{
	public static JSONDocumentLayoutSideList of(final DocumentLayoutSideListDescriptor sideList, final JSONFilteringOptions jsonFilteringOpts)
	{
		return new JSONDocumentLayoutSideList(sideList, jsonFilteringOpts);
	}

	@JsonProperty("elements")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElement> elements;

	private JSONDocumentLayoutSideList(final DocumentLayoutSideListDescriptor sideList, final JSONFilteringOptions jsonFilteringOpts)
	{
		super();
		elements = JSONDocumentLayoutElement.ofList(sideList.getElements(), jsonFilteringOpts);
	}

	@JsonCreator
	private JSONDocumentLayoutSideList(
			@JsonProperty("elements") final List<JSONDocumentLayoutElement> elements //
	)
	{
		super();
		this.elements = elements == null ? ImmutableList.of() : ImmutableList.copyOf(elements);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
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
