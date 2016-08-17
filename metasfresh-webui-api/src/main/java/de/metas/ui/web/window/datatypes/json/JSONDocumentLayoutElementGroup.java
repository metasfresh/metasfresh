package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
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

	public static List<JSONDocumentLayoutElementGroup> ofList(final List<DocumentLayoutElementGroupDescriptor> elementGroups)
	{
		return elementGroups.stream()
				.map(elementGroup -> of(elementGroup))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static JSONDocumentLayoutElementGroup of(final DocumentLayoutElementGroupDescriptor elementGroup)
	{
		return new JSONDocumentLayoutElementGroup(elementGroup);
	}

	/** Element group type (primary aka bordered, transparent etc) */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLayoutType type;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElementLine> elements;

	private JSONDocumentLayoutElementGroup(final DocumentLayoutElementGroupDescriptor elementGroup)
	{
		super();
		type = JSONLayoutType.fromNullable(elementGroup.getLayoutType());
		elements = JSONDocumentLayoutElementLine.ofList(elementGroup.getElementLines());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("type", type)
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
	}

	public JSONLayoutType getType()
	{
		return type;
	}

	public List<JSONDocumentLayoutElementLine> getElements()
	{
		return elements;
	}

}
