package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.Check;
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

@SuppressWarnings("serial")
public final class DocumentLayoutElementGroupDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	/** Element group type (primary aka bordered, transparent etc) */
	private final LayoutType layoutType;

	private final List<DocumentLayoutElementLineDescriptor> elementLines;

	private DocumentLayoutElementGroupDescriptor(final Builder builder)
	{
		super();
		layoutType = builder.layoutType;
		elementLines = ImmutableList.copyOf(builder.buildElementLines());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("type", layoutType)
				.add("elements", elementLines.isEmpty() ? null : elementLines)
				.toString();
	}

	public LayoutType getLayoutType()
	{
		return layoutType;
	}

	public List<DocumentLayoutElementLineDescriptor> getElementLines()
	{
		return elementLines;
	}

	public boolean hasElementLines()
	{
		return !elementLines.isEmpty();
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutElementGroupDescriptor.Builder.class);

		private String internalName;
		private LayoutType layoutType;
		private final List<DocumentLayoutElementLineDescriptor.Builder> elementLinesBuilders = new ArrayList<>();

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
					.add("layoutType", layoutType)
					.add("elementsLines-count", elementLinesBuilders.size())
					.toString();
		}

		public DocumentLayoutElementGroupDescriptor build()
		{
			final DocumentLayoutElementGroupDescriptor result = new DocumentLayoutElementGroupDescriptor(this);

			logger.trace("Built {} for {}", result, this);
			return result;
		}

		private List<DocumentLayoutElementLineDescriptor> buildElementLines()
		{
			return elementLinesBuilders
					.stream()
					.map(elementLinesBuilder -> elementLinesBuilder.build())
					.filter(elementLine -> checkValid(elementLine))
					.collect(GuavaCollectors.toImmutableList());
		}

		private final boolean checkValid(final DocumentLayoutElementLineDescriptor elementLine)
		{
			if (!elementLine.hasElements())
			{
				logger.trace("Skip adding {} to {} because it's empty", elementLine, this);
				return false;
			}

			return true;
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		public Builder setLayoutType(final LayoutType layoutType)
		{
			this.layoutType = layoutType;
			return this;
		}

		public Builder setLayoutType(final String layoutTypeStr)
		{
			layoutType = LayoutType.fromNullable(layoutTypeStr);
			return this;
		}

		public LayoutType getLayoutType()
		{
			return layoutType;
		}

		public Builder addElementLine(final DocumentLayoutElementLineDescriptor.Builder elementLineBuilder)
		{
			Check.assumeNotNull(elementLineBuilder, "Parameter elementLineBuilder is not null");
			elementLinesBuilders.add(elementLineBuilder);
			return this;
		}

		public boolean hasElementLines()
		{
			return !elementLinesBuilders.isEmpty();
		}

		public DocumentLayoutElementDescriptor.Builder findElementBuilderByFieldName(final String fieldName)
		{
			for (final DocumentLayoutElementLineDescriptor.Builder elementsLineBuilder : elementLinesBuilders)
			{
				final DocumentLayoutElementDescriptor.Builder elementBuilder = elementsLineBuilder.findElementBuilderByFieldName(fieldName);
				if (elementBuilder == null)
				{
					continue;
				}

				return elementBuilder;

			}
			return null;
		}

		public Stream<DocumentLayoutElementDescriptor.Builder> streamElementBuilders()
		{
			return elementLinesBuilders.stream()
					.flatMap(elementLineBuilder -> elementLineBuilder.streamElementBuilders());
		}
	}
}
