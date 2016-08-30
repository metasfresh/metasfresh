package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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
		private LayoutType layoutType;
		private final List<DocumentLayoutElementLineDescriptor.Builder> elementLinesBuilders = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public DocumentLayoutElementGroupDescriptor build()
		{
			return new DocumentLayoutElementGroupDescriptor(this);
		}

		private List<DocumentLayoutElementLineDescriptor> buildElementLines()
		{
			return elementLinesBuilders
					.stream()
					.map(elementLinesBuilder -> elementLinesBuilder.build())
					.filter(elementLine -> elementLine.hasElements())
					.collect(GuavaCollectors.toImmutableList());
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

	}
}
