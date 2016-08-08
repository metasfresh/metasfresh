package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
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
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String type;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<DocumentLayoutElementDescriptor> elements;

	private DocumentLayoutElementGroupDescriptor(final Builder builder)
	{
		super();
		type = builder.type;
		elements = ImmutableList.copyOf(builder.elements);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("type", type)
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
	}

	public String getType()
	{
		return type;
	}
	
	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

	public static final class Builder
	{
		private String type;
		private final List<DocumentLayoutElementDescriptor> elements = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public DocumentLayoutElementGroupDescriptor build()
		{
			return new DocumentLayoutElementGroupDescriptor(this);
		}

		public Builder setType(final String type)
		{
			this.type = type;
			return this;
		}

		public Builder addElement(final DocumentLayoutElementDescriptor element)
		{
			elements.add(element);
			return this;
		}
	}
}
