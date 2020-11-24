package de.metas.ui.web.view.descriptor;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import lombok.NonNull;
import lombok.Value;

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

@Value
public final class ViewRowAttributesLayout
{
	public static ViewRowAttributesLayout of(@NonNull final List<DocumentLayoutElementDescriptor> elements)
	{
		return new ViewRowAttributesLayout(elements);
	}

	private final ImmutableList<DocumentLayoutElementDescriptor> elements;

	private ViewRowAttributesLayout(@NonNull final List<DocumentLayoutElementDescriptor> elements)
	{
		this.elements = ImmutableList.copyOf(elements);
	}
}
