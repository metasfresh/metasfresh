package de.metas.ui.web.pattribute;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
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

public class ASILayout
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int M_AttributeSet_ID;
	private final ITranslatableString caption;
	private final ITranslatableString description;

	private final List<DocumentLayoutElementDescriptor> elements;

	private ASILayout(final Builder builder)
	{
		super();
		M_AttributeSet_ID = builder.M_AttributeSet_ID;
		caption = builder.caption != null ? builder.caption : ImmutableTranslatableString.empty();
		description = builder.description != null ? builder.description : ImmutableTranslatableString.empty();
		elements = ImmutableList.copyOf(builder.buildElements());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("M_AttributeSet_ID", M_AttributeSet_ID)
				.add("caption", caption)
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

	public static final class Builder
	{
		public Integer M_AttributeSet_ID;
		private ITranslatableString caption;
		private ITranslatableString description;

		private final List<DocumentLayoutElementDescriptor.Builder> elementBuilders = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public ASILayout build()
		{
			return new ASILayout(this);
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
					.add("M_AttributeSet_ID", M_AttributeSet_ID)
					.add("caption", caption)
					.add("elements-count", elementBuilders.size())
					.toString();
		}

		public Builder setM_AttributeSet_ID(final int M_AttributeSet_ID)
		{
			this.M_AttributeSet_ID = M_AttributeSet_ID;
			return this;
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

		public Builder addElement(final DocumentLayoutElementDescriptor.Builder elementBuilder)
		{
			Check.assumeNotNull(elementBuilder, "Parameter elementBuilder is not null");
			elementBuilders.add(elementBuilder);
			return this;
		}
	}

}
