package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ApiModel("section")
public final class JSONDocumentLayoutSection
{
	static List<JSONDocumentLayoutSection> ofSectionsList(final List<DocumentLayoutSectionDescriptor> sections, final JSONOptions jsonOpts)
	{
		return sections.stream()
				.map(section -> new JSONDocumentLayoutSection(section, jsonOpts))
				.collect(ImmutableList.toImmutableList());
	}

	/** @return a section with one column contains all the elements */
	public static JSONDocumentLayoutSection ofElements(List<DocumentLayoutElementDescriptor> elements, JSONOptions jsonOpts)
	{
		final JSONDocumentLayoutColumn column = JSONDocumentLayoutColumn.oneColumn(elements, jsonOpts);
		return new JSONDocumentLayoutSection(ImmutableList.of(column));
	}

	static List<JSONDocumentLayoutSection> ofDetailTab(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		final List<JSONDocumentLayoutColumn> columns = JSONDocumentLayoutColumn.ofDetailTab(detailLayout, jsonOpts);
		final JSONDocumentLayoutSection section = new JSONDocumentLayoutSection(columns);
		return ImmutableList.of(section);
	}

	@JsonProperty("columns")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutColumn> columns;

	private JSONDocumentLayoutSection(final DocumentLayoutSectionDescriptor section, final JSONOptions jsonOpts)
	{
		columns = JSONDocumentLayoutColumn.ofList(section.getColumns(), jsonOpts);
	}

	@JsonCreator
	private JSONDocumentLayoutSection(@JsonProperty("columns") final List<JSONDocumentLayoutColumn> columns)
	{
		super();
		this.columns = columns == null ? ImmutableList.of() : ImmutableList.copyOf(columns);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("columns", columns)
				.toString();
	}

	public List<JSONDocumentLayoutColumn> getColumns()
	{
		return columns;
	}
}
