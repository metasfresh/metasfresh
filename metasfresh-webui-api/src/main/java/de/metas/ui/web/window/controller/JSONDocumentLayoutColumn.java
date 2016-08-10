package de.metas.ui.web.window.controller;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;

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

@SuppressWarnings("serial")
public final class JSONDocumentLayoutColumn implements Serializable
{
	public static List<JSONDocumentLayoutColumn> ofList(final List<DocumentLayoutColumnDescriptor> columns)
	{
		return columns.stream()
				.map(column -> of(column))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static JSONDocumentLayoutColumn of(final DocumentLayoutColumnDescriptor column)
	{
		return new JSONDocumentLayoutColumn(column);
	}

	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElementGroup> elementGroups;

	private JSONDocumentLayoutColumn(final DocumentLayoutColumnDescriptor column)
	{
		super();
		elementGroups = JSONDocumentLayoutElementGroup.ofList(column.getElementGroups());
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
