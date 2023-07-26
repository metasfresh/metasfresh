package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@ToString
public class JSONDocumentLayoutElementLine
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
	@Getter
	private final List<JSONDocumentLayoutElement> elements;

	private JSONDocumentLayoutElementLine(
			@NonNull final DocumentLayoutElementLineDescriptor elementLine,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		final List<JSONDocumentLayoutElement> elements = JSONDocumentLayoutElement.ofList(elementLine.getElements(), options);
		this.elements = ImmutableList.copyOf(elements);
	}

	@JsonCreator
	private JSONDocumentLayoutElementLine(
			@JsonProperty("elements") @Nullable final List<JSONDocumentLayoutElement> elements)
	{
		this.elements = elements == null ? ImmutableList.of() : ImmutableList.copyOf(elements);
	}

	@JsonIgnore
	public boolean isEmpty()
	{
		return getElements().isEmpty();
	}

	Stream<JSONDocumentLayoutElement> streamInlineTabElements()
	{
		return getElements()
				.stream()
				.filter(element -> element.getInlineTabId() != null);
	}
}
