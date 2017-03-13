package de.metas.ui.web.window.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

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

public final class DocumentLayoutElementLineDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String internalName;
	private final List<DocumentLayoutElementDescriptor> elements;

	private DocumentLayoutElementLineDescriptor(final Builder builder)
	{
		super();
		internalName = builder.internalName;
		elements = ImmutableList.copyOf(builder.buildElements());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("internalName", internalName)
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
	}

	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

	public boolean hasElements()
	{
		return !elements.isEmpty();
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutElementLineDescriptor.Builder.class);

		private String internalName;
		private final List<DocumentLayoutElementDescriptor.Builder> elementsBuilders = new ArrayList<>();

		private Builder()
		{
			super();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("internalName", internalName)
					.add("elements-count", elementsBuilders.size())
					.toString();
		}

		public DocumentLayoutElementLineDescriptor build()
		{
			final DocumentLayoutElementLineDescriptor result = new DocumentLayoutElementLineDescriptor(this);

			logger.trace("Built {} for {}", result, this);
			return result;
		}

		private List<DocumentLayoutElementDescriptor> buildElements()
		{
			return elementsBuilders
					.stream()
					.filter(elementBuilder -> checkValid(elementBuilder))
					.map(elementBuilder -> elementBuilder.build())
					.filter(element -> checkValid(element))
					.collect(GuavaCollectors.toImmutableList());
		}

		private final boolean checkValid(final DocumentLayoutElementDescriptor.Builder elementBuilder)
		{
			if (elementBuilder.isConsumed())
			{
				logger.trace("Skip adding {} to {} because it's already consumed", elementBuilder, this);
				return false;
			}
			
			if(elementBuilder.getFieldsCount() <= 0)
			{
				logger.trace("Skip adding {} to {} because it does not have fields", elementBuilder, this);
				return false;
			}
			
			return true;
		}

		private final boolean checkValid(final DocumentLayoutElementDescriptor element)
		{
			if (!element.hasFields())
			{
				logger.trace("Skip adding {} to {} because it does not have fields", element, this);
				return false;
			}

			return true;
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		public Builder addElement(final DocumentLayoutElementDescriptor.Builder elementBuilder)
		{
			elementsBuilders.add(elementBuilder);
			return this;
		}
		
		public boolean hasElements()
		{
			return !elementsBuilders.isEmpty();
		}
		
		public DocumentLayoutElementDescriptor.Builder getFirstElement()
		{
			return elementsBuilders.get(0);
		}

		public DocumentLayoutElementDescriptor.Builder findElementBuilderByFieldName(final String fieldName)
		{
			for (final DocumentLayoutElementDescriptor.Builder elementBuilder : elementsBuilders)
			{
				if (elementBuilder.hasFieldName(fieldName))
				{
					return elementBuilder;
				}
			}

			return null;
		}
		
		public Stream<DocumentLayoutElementDescriptor.Builder> streamElementBuilders()
		{
			return elementsBuilders.stream();
		}

	}

}
