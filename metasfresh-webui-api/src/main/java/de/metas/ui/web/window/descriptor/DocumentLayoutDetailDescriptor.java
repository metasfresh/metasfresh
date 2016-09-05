package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

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
public final class DocumentLayoutDetailDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String detailId;
	private final String caption;
	private final Map<String, String> captionTrls;
	private final String description;
	private final Map<String, String> descriptionTrls;

	private final List<DocumentLayoutElementDescriptor> elements;

	private DocumentLayoutDetailDescriptor(final Builder builder)
	{
		super();
		detailId = builder.getDetailId();
		caption = builder.caption;
		captionTrls = builder.captionTrls == null ? ImmutableMap.of() : ImmutableMap.copyOf(builder.captionTrls);
		description = builder.description;
		descriptionTrls = builder.descriptionTrls == null ? ImmutableMap.of() : ImmutableMap.copyOf(builder.descriptionTrls);
		elements = ImmutableList.copyOf(builder.buildElements());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("detailId", detailId)
				.add("caption", caption)
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
	}

	public String getDetailId()
	{
		return detailId;
	}

	public String getCaption(final String adLanguage)
	{
		return captionTrls.getOrDefault(adLanguage, caption);
	}

	public String getDescription(final String adLanguage)
	{
		return descriptionTrls.getOrDefault(adLanguage, description);
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
		private String detailId;
		private String caption;
		private Map<String, String> captionTrls;
		private String description;
		private Map<String, String> descriptionTrls;
		private final List<DocumentLayoutElementDescriptor.Builder> elementBuilders = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public DocumentLayoutDetailDescriptor build()
		{
			return new DocumentLayoutDetailDescriptor(this);
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
					.add("detailId", detailId)
					.add("caption", caption)
					.add("elements-count", elementBuilders.size())
					.toString();
		}

		public Builder setDetailId(final String detailId)
		{
			this.detailId = detailId;
			return this;
		}

		public String getDetailId()
		{
			return detailId;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = Strings.emptyToNull(caption);
			return this;
		}

		public Builder setCaptionTrls(final Map<String, String> captionTrls)
		{
			this.captionTrls = captionTrls;
			return this;
		}

		public Builder setDescription(final String description)
		{
			this.description = Strings.emptyToNull(description);
			return this;
		}

		public Builder setDescriptionTrls(final Map<String, String> descriptionTrls)
		{
			this.descriptionTrls = descriptionTrls;
			return this;
		}

		public Builder addElement(final DocumentLayoutElementDescriptor.Builder elementBuilder)
		{
			Check.assumeNotNull(elementBuilder, "Parameter elementBuilder is not null");
			elementBuilders.add(elementBuilder);
			return this;
		}

		public boolean hasElements()
		{
			return !elementBuilders.isEmpty();
		}

		private final DocumentLayoutElementDescriptor.Builder findElementBuilderByFieldName(final String fieldName)
		{
			for (final DocumentLayoutElementDescriptor.Builder elementBuilder : elementBuilders)
			{
				if (elementBuilder.hasFieldName(fieldName))
				{
					return elementBuilder;
				}
			}

			return null;
		}

		public boolean hasElement(final String fieldName)
		{
			return findElementBuilderByFieldName(fieldName) != null;
		}

		public boolean isAdvancedField(final String fieldName)
		{
			final DocumentLayoutElementDescriptor.Builder elementBuilder = findElementBuilderByFieldName(fieldName);
			return elementBuilder != null && elementBuilder.isAdvancedField();
		}

	}

}
