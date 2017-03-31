package de.metas.ui.web.address;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

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

public class AddressLayout
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final List<DocumentLayoutElementDescriptor> elements;

	private AddressLayout(final Builder builder)
	{
		super();
		elements = ImmutableList.copyOf(builder.buildElements());
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
		private final List<DocumentLayoutElementDescriptor.Builder> elementBuilders = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public AddressLayout build()
		{
			return new AddressLayout(this);
		}

		private List<DocumentLayoutElementDescriptor> buildElements()
		{
			return elementBuilders
					.stream()
					.map(elementBuilder -> elementBuilder.build())
					.collect(GuavaCollectors.toImmutableList());
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("elements-count", elementBuilders.size())
					.toString();
		}

		public Builder addElement(final DocumentLayoutElementDescriptor.Builder elementBuilder)
		{
			Check.assumeNotNull(elementBuilder, "Parameter elementBuilder is not null");
			elementBuilders.add(elementBuilder);
			return this;
		}
	}

}
