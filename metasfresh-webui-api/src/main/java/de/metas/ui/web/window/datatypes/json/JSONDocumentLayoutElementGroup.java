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
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
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

@ApiModel("elementGroup")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutElementGroup implements Serializable
{
	static List<JSONDocumentLayoutElementGroup> ofList(final List<DocumentLayoutElementGroupDescriptor> elementGroups, final JSONOptions jsonOpts)
	{
		return elementGroups.stream()
				.map(elementGroup -> of(elementGroup, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static JSONDocumentLayoutElementGroup of(final DocumentLayoutElementGroupDescriptor elementGroup, final JSONOptions jsonOpts)
	{
		return new JSONDocumentLayoutElementGroup(elementGroup, jsonOpts);
	}

	static List<JSONDocumentLayoutElementGroup> ofDetailTab(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		final JSONDocumentLayoutElementGroup elementGroup = new JSONDocumentLayoutElementGroup(detailLayout, jsonOpts);
		return ImmutableList.of(elementGroup);
	}

	static List<JSONDocumentLayoutElementGroup> ofSideListLayout(final DocumentLayoutSideListDescriptor sideListLayout, final JSONOptions jsonOpts)
	{
		final JSONDocumentLayoutElementGroup elementGroup = new JSONDocumentLayoutElementGroup(sideListLayout, jsonOpts);
		return ImmutableList.of(elementGroup);
	}

	/** Element group type (primary aka bordered, transparent etc) */
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLayoutType type;

	@JsonProperty("elementsLine")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElementLine> elementLines;

	private JSONDocumentLayoutElementGroup(final DocumentLayoutElementGroupDescriptor elementGroup, final JSONOptions jsonOpts)
	{
		super();
		type = JSONLayoutType.fromNullable(elementGroup.getLayoutType());
		elementLines = JSONDocumentLayoutElementLine.ofList(elementGroup.getElementLines(), jsonOpts);
	}

	@JsonCreator
	private JSONDocumentLayoutElementGroup(@JsonProperty("type") final JSONLayoutType type, @JsonProperty("elementsLine") final List<JSONDocumentLayoutElementLine> elementLines)
	{
		super();
		this.type = type;
		this.elementLines = elementLines == null ? ImmutableList.of() : ImmutableList.copyOf(elementLines);
	}

	/**
	 * From detail tab constructor
	 *
	 * @param detailLayout
	 * @param jsonOpts
	 */
	private JSONDocumentLayoutElementGroup(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		super();
		type = null;
		elementLines = JSONDocumentLayoutElementLine.ofElementsOnePerLine(detailLayout.getElements(), jsonOpts);
	}

	/**
	 * From side-list layout constructor
	 *
	 * @param sideListLayout
	 * @param jsonOpts
	 */
	public JSONDocumentLayoutElementGroup(final DocumentLayoutSideListDescriptor sideListLayout, final JSONOptions jsonOpts)
	{
		super();
		type = null;
		elementLines = JSONDocumentLayoutElementLine.ofElementsOnePerLine(sideListLayout.getElements(), jsonOpts);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("type", type)
				.add("elements", elementLines.isEmpty() ? null : elementLines)
				.toString();
	}

	public JSONLayoutType getType()
	{
		return type;
	}

	public List<JSONDocumentLayoutElementLine> getElementLines()
	{
		return elementLines;
	}
}
