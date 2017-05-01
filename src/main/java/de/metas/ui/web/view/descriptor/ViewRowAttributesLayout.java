package de.metas.ui.web.view.descriptor;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;

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

public final class ViewRowAttributesLayout
{
	public static final ViewRowAttributesLayout of(final List<DocumentLayoutElementDescriptor> elements)
	{
		return new ViewRowAttributesLayout(elements);
	}

	private final List<DocumentLayoutElementDescriptor> elements;

	private ViewRowAttributesLayout(final List<DocumentLayoutElementDescriptor> elements)
	{
		super();
		this.elements = ImmutableList.copyOf(elements);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("elements", elements)
				.toString();
	}


	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

}
