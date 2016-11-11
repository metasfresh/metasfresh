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

@ApiModel("column")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutColumn implements Serializable
{
	static List<JSONDocumentLayoutColumn> ofList(final List<DocumentLayoutColumnDescriptor> columns, final JSONOptions jsonOpts)
	{
		return columns.stream()
				.map(column -> of(column, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONDocumentLayoutColumn of(final DocumentLayoutColumnDescriptor column, final JSONOptions jsonOpts)
	{
		return new JSONDocumentLayoutColumn(column, jsonOpts);
	}
	
	static JSONDocumentLayoutColumn oneColumn(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		return new JSONDocumentLayoutColumn(detailLayout, jsonOpts);
	}

	static List<JSONDocumentLayoutColumn> ofDetailTab(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		return ImmutableList.of(oneColumn(detailLayout, jsonOpts));
	}

	static List<JSONDocumentLayoutColumn> ofSideListLayout(final DocumentLayoutSideListDescriptor sideListLayout, final JSONOptions jsonOpts)
	{
		final JSONDocumentLayoutColumn column = new JSONDocumentLayoutColumn(sideListLayout, jsonOpts);
		return ImmutableList.of(column);
	}

	static final JSONDocumentLayoutColumn EMPTY = new JSONDocumentLayoutColumn();

	@JsonProperty("elementGroups")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElementGroup> elementGroups;
	
	private JSONDocumentLayoutColumn()
	{
		super();
		this.elementGroups = ImmutableList.of();
	}

	private JSONDocumentLayoutColumn(final DocumentLayoutColumnDescriptor column, final JSONOptions jsonOpts)
	{
		super();
		elementGroups = JSONDocumentLayoutElementGroup.ofList(column.getElementGroups(), jsonOpts);
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
	 * @param jsonOpts
	 */
	private JSONDocumentLayoutColumn(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		super();
		elementGroups = JSONDocumentLayoutElementGroup.ofDetailTab(detailLayout, jsonOpts);
	}

	/**
	 * From side-list layout constructor
	 *
	 * @param detailLayout
	 * @param jsonOpts
	 */
	public JSONDocumentLayoutColumn(final DocumentLayoutSideListDescriptor sideListLayout, final JSONOptions jsonOpts)
	{
		super();
		elementGroups = JSONDocumentLayoutElementGroup.ofSideListLayout(sideListLayout, jsonOpts);
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
