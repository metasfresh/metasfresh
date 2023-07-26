package de.metas.ui.web.quickinput;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.util.Check;
import lombok.NonNull;

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

public class QuickInputLayoutDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final QuickInputLayoutDescriptor build(
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			@NonNull final String[][] fieldNames)
	{
		Check.assumeNotEmpty(fieldNames, "fieldNames is not empty");

		final Builder layoutBuilder = builder();

		for (final String[] elementFieldNames : fieldNames)
		{
			if (elementFieldNames == null || elementFieldNames.length == 0)
			{
				continue;
			}

			DocumentLayoutElementDescriptor
					.builderOrEmpty(entityDescriptor, elementFieldNames)
					.ifPresent(layoutBuilder::element);
		}
		return layoutBuilder.build();
	}

	private final List<DocumentLayoutElementDescriptor> elements;

	private QuickInputLayoutDescriptor(final List<DocumentLayoutElementDescriptor> elements)
	{
		this.elements = ImmutableList.copyOf(elements);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
	}

	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

	public static final class Builder
	{
		private final List<DocumentLayoutElementDescriptor> elements = new ArrayList<>();

		private Builder()
		{
		}

		public QuickInputLayoutDescriptor build()
		{
			return new QuickInputLayoutDescriptor(elements);
		}

		public Builder element(@NonNull final DocumentLayoutElementDescriptor.Builder elementBuilder)
		{
			elements.add(elementBuilder.build());
			return this;
		}
	}

}
