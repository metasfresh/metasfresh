package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

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
public final class DocumentLayoutElementFieldDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}
	
	public static enum LookupSource
	{
		lookup
		, list
	}

	private final String field;
	private final LookupSource lookupSource;

	private DocumentLayoutElementFieldDescriptor(final Builder builder)
	{
		super();
		field = Preconditions.checkNotNull(builder.field, "field not null");
		this.lookupSource = builder.lookupSource;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("field", field)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(field);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof DocumentLayoutElementFieldDescriptor))
		{
			return false;
		}
		final DocumentLayoutElementFieldDescriptor other = (DocumentLayoutElementFieldDescriptor)obj;
		return Objects.equals(field, other.field);
	}

	public String getField()
	{
		return field;
	}
	
	public LookupSource getLookupSource()
	{
		return lookupSource;
	}

	public static final class Builder
	{
		private String field;
		private LookupSource lookupSource;

		private Builder()
		{
			super();
		}

		public DocumentLayoutElementFieldDescriptor build()
		{
			return new DocumentLayoutElementFieldDescriptor(this);
		}

		public Builder setField(final String field)
		{
			this.field = Strings.emptyToNull(field);
			return this;
		}
		
		public Builder setLookupSource(LookupSource lookupSource)
		{
			this.lookupSource = lookupSource;
			return this;
		}

	}
}
