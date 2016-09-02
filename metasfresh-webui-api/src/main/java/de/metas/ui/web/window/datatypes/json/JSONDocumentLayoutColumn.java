package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
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

@ApiModel("column")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutColumn implements Serializable
{
	static List<JSONDocumentLayoutColumn> ofList(final List<DocumentLayoutColumnDescriptor> columns, final JSONFilteringOptions jsonFilteringOpts)
	{
		return columns.stream()
				.map(column -> of(column, jsonFilteringOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONDocumentLayoutColumn of(final DocumentLayoutColumnDescriptor column, final JSONFilteringOptions jsonFilteringOpts)
	{
		return new JSONDocumentLayoutColumn(column, jsonFilteringOpts);
	}

	static List<JSONDocumentLayoutColumn> ofDetailTab(final DocumentLayoutDetailDescriptor detailLayout, final JSONFilteringOptions jsonFilteringOpts)
	{
		final JSONDocumentLayoutColumn column = new JSONDocumentLayoutColumn(detailLayout, jsonFilteringOpts);
		return ImmutableList.of(column);
	}

	@JsonProperty("elementGroups")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElementGroup> elementGroups;

	private JSONDocumentLayoutColumn(final DocumentLayoutColumnDescriptor column, final JSONFilteringOptions jsonFilteringOpts)
	{
		super();
		elementGroups = JSONDocumentLayoutElementGroup.ofList(column.getElementGroups(), jsonFilteringOpts);
	}

	@JsonCreator
	private JSONDocumentLayoutColumn(@JsonProperty("elementGroups") final List<JSONDocumentLayoutElementGroup> elementGroups)
	{
		super();
		this.elementGroups = elementGroups == null ? ImmutableList.of() : ImmutableList.copyOf(elementGroups);
	}

	/**
	 * From detail tab constructor
	 *
	 * @param detailLayout
	 * @param jsonFilteringOpts
	 */
	private JSONDocumentLayoutColumn(final DocumentLayoutDetailDescriptor detailLayout, final JSONFilteringOptions jsonFilteringOpts)
	{
		super();
		elementGroups = JSONDocumentLayoutElementGroup.ofDetailTab(detailLayout, jsonFilteringOpts);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("elementGroups", elementGroups)
				.toString();
	}

	public List<JSONDocumentLayoutElementGroup> getElementGroups()
	{
		return elementGroups;
	}
}
