package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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

	@JsonProperty("title")
	@JsonInclude(Include.NON_EMPTY)
	private final String title;

	@JsonProperty("description")
	@JsonInclude(Include.NON_EMPTY)
	private final String description;

	@JsonProperty("columns")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutColumn> columns;

	private JSONDocumentLayoutSection(final DocumentLayoutSectionDescriptor section, final JSONOptions jsonOpts)
	{
		// Show section title only for advanced layouts
		if (jsonOpts.isShowAdvancedFields())
		{
			title = section.getCaption(jsonOpts.getAD_Language()).trim();
		}
		else
		{
			title = null;
		}
		
		description = section.getDescription(jsonOpts.getAD_Language()).trim();
		columns = JSONDocumentLayoutColumn.ofList(section.getColumns(), jsonOpts);
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
