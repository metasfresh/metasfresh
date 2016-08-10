package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
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
public final class DocumentLayoutDetailDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String detailId;
	private final String caption;
	private final String description;

	private final List<DocumentLayoutElementDescriptor> elements;

	private DocumentLayoutDetailDescriptor(final Builder builder)
	{
		super();
		detailId = builder.detailId;
		caption = builder.caption;
		description = builder.description;
		elements = ImmutableList.copyOf(builder.elements);
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

	public String getCaption()
	{
		return caption;
	}

	public String getDescription()
	{
		return description;
	}

	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

	public static final class Builder
	{
		private String detailId;
		private String caption;
		private String description;
		private final List<DocumentLayoutElementDescriptor> elements = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public DocumentLayoutDetailDescriptor build()
		{
			return new DocumentLayoutDetailDescriptor(this);
		}

		public Builder setDetailId(final String detailId)
		{
			this.detailId = detailId;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = Strings.emptyToNull(caption);
			return this;
		}

		public Builder setDescription(final String description)
		{
			this.description = Strings.emptyToNull(description);
			return this;
		}

		public Builder addElement(final DocumentLayoutElementDescriptor element)
		{
			elements.add(element);
			return this;
		}
	}

}
