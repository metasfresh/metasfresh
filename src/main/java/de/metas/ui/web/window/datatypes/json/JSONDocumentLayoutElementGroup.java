package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
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

@ApiModel("elementGroup")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutElementGroup implements Serializable
{
	static List<JSONDocumentLayoutElementGroup> ofList(
			@NonNull final List<DocumentLayoutElementGroupDescriptor> elementGroups,
			@NonNull final JSONOptions jsonOpts)
	{
		return elementGroups.stream()
				.map(elementGroup -> of(elementGroup, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static JSONDocumentLayoutElementGroup of(
			@NonNull final DocumentLayoutElementGroupDescriptor elementGroup,
			@NonNull final JSONOptions jsonOpts)
	{
		return new JSONDocumentLayoutElementGroup(elementGroup, jsonOpts);
	}

	/** Element group type (primary aka bordered, transparent etc) */
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	private final JSONLayoutType type;

	@JsonProperty("columnCount")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	private final Integer columnCount;

	@JsonProperty("internalName")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	private final String internalName;

	@JsonProperty("elementsLine")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Getter
	private final List<JSONDocumentLayoutElementLine> elementLines;

	private JSONDocumentLayoutElementGroup(
			@NonNull final DocumentLayoutElementGroupDescriptor elementGroup,

			@NonNull final JSONOptions jsonOpts)
	{
		this.type = JSONLayoutType.fromNullable(elementGroup.getLayoutType());
		this.columnCount = elementGroup.getColumnCount();
		this.internalName = elementGroup.getInternalName();
		this.elementLines = JSONDocumentLayoutElementLine.ofList(elementGroup.getElementLines(), jsonOpts);
	}

	@JsonCreator
	private JSONDocumentLayoutElementGroup(
			@JsonProperty("type") final JSONLayoutType type,
			@JsonProperty("columnCount") @Nullable final Integer columnCount,
			@JsonProperty("internalName") @Nullable final String internalName,
			@JsonProperty("elementsLine") @Nullable final List<JSONDocumentLayoutElementLine> elementLines)
	{
		this.type = type;
		this.columnCount = columnCount;
		this.internalName = internalName;
		this.elementLines = elementLines == null ? ImmutableList.of() : ImmutableList.copyOf(elementLines);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("type", type)
				.add("columnCount", columnCount)
				.add("elements", elementLines.isEmpty() ? null : elementLines)
				.toString();
	}
}
