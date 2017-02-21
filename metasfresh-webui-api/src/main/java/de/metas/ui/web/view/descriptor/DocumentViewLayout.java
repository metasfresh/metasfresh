package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DocumentViewLayout
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int AD_Window_ID;
	private final DetailId detailId;
	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final ITranslatableString emptyResultText;
	private final ITranslatableString emptyResultHint;

	private final List<DocumentLayoutElementDescriptor> elements;
	
	private final String idFieldName;
	
	private final boolean hasAttributesSupport;
	private final boolean hasTreeSupport;


	private DocumentViewLayout(final Builder builder)
	{
		super();
		AD_Window_ID = builder.AD_Window_ID;
		detailId = builder.getDetailId();
		caption = builder.caption != null ? builder.caption : ImmutableTranslatableString.empty();
		description = builder.description != null ? builder.description : ImmutableTranslatableString.empty();
		emptyResultText = ImmutableTranslatableString.copyOfNullable(builder.emptyResultText);
		emptyResultHint = ImmutableTranslatableString.copyOfNullable(builder.emptyResultHint);
		elements = ImmutableList.copyOf(builder.buildElements());
		idFieldName = builder.getIdFieldName();
		
		hasAttributesSupport = builder.hasAttributesSupport;
		hasTreeSupport = builder.hasTreeSupport;
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
	
	public int getAD_Window_ID()
	{
		return AD_Window_ID;
	}

	public DetailId getDetailId()
	{
		return detailId;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public String getEmptyResultText(final String adLanguage)
	{
		return emptyResultText.translate(adLanguage);
	}

	public String getEmptyResultHint(final String adLanguage)
	{
		return emptyResultHint.translate(adLanguage);
	}

	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

	public boolean hasElements()
	{
		return !elements.isEmpty();
	}
	
	public String getIdFieldName()
	{
		return idFieldName;
	}
	
	public boolean isAttributesSupport()
	{
		return hasAttributesSupport;
	}
	
	public boolean isTreeSupport()
	{
		return hasTreeSupport;
	}

	public static final class Builder
	{
		public Integer AD_Window_ID;
		private DetailId detailId;
		private ITranslatableString caption;
		private ITranslatableString description;
		private ITranslatableString emptyResultText;
		private ITranslatableString emptyResultHint;
		
		private boolean hasAttributesSupport = false;
		private boolean hasTreeSupport = false;

		private final List<DocumentLayoutElementDescriptor.Builder> elementBuilders = new ArrayList<>();
		
		private String idFieldName;

		private Builder()
		{
			super();
		}

		public DocumentViewLayout build()
		{
			return new DocumentViewLayout(this);
		}

		private List<DocumentLayoutElementDescriptor> buildElements()
		{
			return elementBuilders
					.stream()
					.filter(elementBuilder -> elementBuilder.getFieldsCount() > 0) // have some field builders
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
		
		public Builder setAD_Window_ID(final int AD_Window_ID)
		{
			this.AD_Window_ID = AD_Window_ID;
			return this;
		}

		public Builder setDetailId(final DetailId detailId)
		{
			this.detailId = detailId;
			return this;
		}

		public DetailId getDetailId()
		{
			return detailId;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setEmptyResultText(final ITranslatableString emptyResultText)
		{
			this.emptyResultText = emptyResultText;
			return this;
		}

		public Builder setEmptyResultHint(ITranslatableString emptyResultHint)
		{
			this.emptyResultHint = emptyResultHint;
			return this;
		}

		public Builder addElement(final DocumentLayoutElementDescriptor.Builder elementBuilder)
		{
			Check.assumeNotNull(elementBuilder, "Parameter elementBuilder is not null");
			elementBuilders.add(elementBuilder);
			return this;
		}

		public Builder addElements(final Stream<DocumentLayoutElementDescriptor.Builder> elementBuilders)
		{
			Check.assumeNotNull(elementBuilders, "Parameter elementBuilders is not null");
			elementBuilders.forEach(this::addElement);
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

		public Set<String> getFieldNames()
		{
			return elementBuilders
					.stream()
					.flatMap(element -> element.getFieldNames().stream())
					.collect(GuavaCollectors.toImmutableSet());
		}
		
		public Builder setIdFieldName(String idFieldName)
		{
			this.idFieldName = idFieldName;
			return this;
		}
		
		private String getIdFieldName()
		{
			return idFieldName;
		}
		
		public Builder setHasAttributesSupport(boolean hasAttributesSupport)
		{
			this.hasAttributesSupport = hasAttributesSupport;
			return this;
		}
		
		public Builder setHasTreeSupport(boolean hasTreeSupport)
		{
			this.hasTreeSupport = hasTreeSupport;
			return this;
		}
	}
}
