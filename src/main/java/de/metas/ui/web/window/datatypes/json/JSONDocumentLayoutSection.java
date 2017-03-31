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

import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ApiModel("section")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutSection implements Serializable
{
	static List<JSONDocumentLayoutSection> ofSectionsList(final List<DocumentLayoutSectionDescriptor> sections, final JSONOptions jsonOpts)
	{
		return sections.stream()
				.map(section -> of(section, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONDocumentLayoutSection of(final DocumentLayoutSectionDescriptor section, final JSONOptions jsonOpts)
	{
		return new JSONDocumentLayoutSection(section, jsonOpts);
	}

	static List<JSONDocumentLayoutSection> ofDetailTab(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		final JSONDocumentLayoutSection section = new JSONDocumentLayoutSection(detailLayout, jsonOpts);
		return ImmutableList.of(section);
	}

	/**
	 * Build the layout sections for advanced view.
	 *
	 * @param advancedViewLayout
	 * @param jsonOpts
	 *
	 * @task https://github.com/metasfresh/metasfresh-webui/issues/26
	 */
	static List<JSONDocumentLayoutSection> ofAdvancedView(final DocumentLayoutDetailDescriptor advancedViewLayout, final JSONOptions jsonOpts)
	{
		final JSONDocumentLayoutSection section = new JSONDocumentLayoutSection(
				JSONDocumentLayoutColumn.oneColumn(advancedViewLayout, jsonOpts) //
		);

		return ImmutableList.of(section);
	}

	@JsonProperty("columns")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutColumn> columns;

	private JSONDocumentLayoutSection(final DocumentLayoutSectionDescriptor section, final JSONOptions jsonOpts)
	{
		super();
		columns = JSONDocumentLayoutColumn.ofList(section.getColumns(), jsonOpts);
	}

	/**
	 * From detail tab constructor
	 *
	 * @param detailLayout
	 * @param jsonOpts
	 */
	private JSONDocumentLayoutSection(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		super();
		columns = JSONDocumentLayoutColumn.ofDetailTab(detailLayout, jsonOpts);
	}

	@JsonCreator
	private JSONDocumentLayoutSection(@JsonProperty("columns") final List<JSONDocumentLayoutColumn> columns)
	{
		super();
		this.columns = columns == null ? ImmutableList.of() : ImmutableList.copyOf(columns);
	}

	private JSONDocumentLayoutSection(final JSONDocumentLayoutColumn... columns)
	{
		super();
		this.columns = ImmutableList.copyOf(columns);
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
