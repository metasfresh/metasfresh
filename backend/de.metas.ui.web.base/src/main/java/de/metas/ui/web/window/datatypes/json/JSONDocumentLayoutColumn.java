package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ApiModel("column")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@ToString
public final class JSONDocumentLayoutColumn
{
	static List<JSONDocumentLayoutColumn> ofList(final List<DocumentLayoutColumnDescriptor> columns, final JSONDocumentLayoutOptions jsonOpts)
	{
		return columns.stream()
				.map(column -> of(column, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONDocumentLayoutColumn of(final DocumentLayoutColumnDescriptor column, final JSONDocumentLayoutOptions jsonOpts)
	{
		return new JSONDocumentLayoutColumn(column, jsonOpts);
	}

	@JsonProperty("elementGroups")
	@JsonInclude(Include.NON_EMPTY)
	@Getter
	private final List<JSONDocumentLayoutElementGroup> elementGroups;

	@JsonCreator
	private JSONDocumentLayoutColumn(
			@JsonProperty("elementGroups") @Nullable final List<JSONDocumentLayoutElementGroup> elementGroups)
	{
		this.elementGroups = elementGroups == null ? ImmutableList.of() : ImmutableList.copyOf(elementGroups);
	}

	private JSONDocumentLayoutColumn(final DocumentLayoutColumnDescriptor column, final JSONDocumentLayoutOptions jsonOpts)
	{
		elementGroups = JSONDocumentLayoutElementGroup.ofList(column.getElementGroups(), jsonOpts);
	}

	Stream<JSONDocumentLayoutElement> streamInlineTabElements()
	{
		return getElementGroups().stream().flatMap(JSONDocumentLayoutElementGroup::streamInlineTabElements);
	}
}
