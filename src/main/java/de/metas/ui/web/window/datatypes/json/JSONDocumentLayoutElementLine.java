package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModel;
import lombok.NonNull;

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

@ApiModel("element-line")
@SuppressWarnings("serial")
public class JSONDocumentLayoutElementLine implements Serializable
{
	static List<JSONDocumentLayoutElementLine> ofList(
			@NonNull final List<DocumentLayoutElementLineDescriptor> elementsLines,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		return elementsLines.stream()
				.map(elementsLine -> ofDocumentLayoutElementLineDescriptor(elementsLine, options))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONDocumentLayoutElementLine ofDocumentLayoutElementLineDescriptor(
			@NonNull final DocumentLayoutElementLineDescriptor elementLine,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		return new JSONDocumentLayoutElementLine(elementLine, options);
	}

	@JsonProperty("elements")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElement> elements;

	private JSONDocumentLayoutElementLine(
			@NonNull final DocumentLayoutElementLineDescriptor elementLine,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		final List<JSONDocumentLayoutElement> elements = JSONDocumentLayoutElement.ofList(elementLine.getElements(), options);
		this.elements = ImmutableList.copyOf(elements);
	}

	@JsonCreator
	private JSONDocumentLayoutElementLine(@Nullable @JsonProperty("elements") final List<JSONDocumentLayoutElement> elements)
	{
		this.elements = elements == null ? ImmutableList.of() : ImmutableList.copyOf(elements);
	}

	private JSONDocumentLayoutElementLine(final JSONDocumentLayoutElement element)
	{
		elements = ImmutableList.of(element);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("elements", elements)
				.toString();
	}

	public List<JSONDocumentLayoutElement> getElements()
	{
		return elements;
	}

	@JsonIgnore
	public boolean isEmpty()
	{
		return elements.isEmpty();
	}
}
