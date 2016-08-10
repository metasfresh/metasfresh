package de.metas.ui.web.window.controller;

import java.io.Serializable;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;

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
public final class JSONDocumentLayoutElementField implements Serializable
{
	public static Set<JSONDocumentLayoutElementField> ofSet(final Set<DocumentLayoutElementFieldDescriptor> fieldDescriptors)
	{
		return fieldDescriptors.stream()
				.map(field -> of(field))
				.collect(GuavaCollectors.toImmutableSet());
	}

	public static JSONDocumentLayoutElementField of(final DocumentLayoutElementFieldDescriptor fieldDescriptor)
	{
		return new JSONDocumentLayoutElementField(fieldDescriptor);
	}

	private final String field;

	private JSONDocumentLayoutElementField(final DocumentLayoutElementFieldDescriptor fieldDescriptor)
	{
		super();
		field = fieldDescriptor.getField();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("field", field)
				.toString();
	}

	public String getField()
	{
		return field;
	}
}
