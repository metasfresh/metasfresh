package de.metas.ui.web.window.descriptor;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;

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

public final class DocumentDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final DocumentLayoutDescriptor layout;
	private final DocumentEntityDescriptor entityDescriptor;

	private DocumentDescriptor(final Builder builder)
	{
		super();
		layout = Preconditions.checkNotNull(builder.layout, "layout not null");
		entityDescriptor = Preconditions.checkNotNull(builder.entityDescriptor, "entityDescriptor not null");
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("layout", layout)
				.add("entity", entityDescriptor)
				.toString();
	}

	public DocumentLayoutDescriptor getLayout()
	{
		return layout;
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	/** @return root entity's filters */
	public DocumentFilterDescriptorsProvider getDocumentFiltersProvider()
	{
		return getEntityDescriptor().getFiltersProvider();
	}

	public static final class Builder
	{
		private DocumentLayoutDescriptor layout;
		private DocumentEntityDescriptor entityDescriptor;

		private Builder()
		{
			super();
		}

		public DocumentDescriptor build()
		{
			return new DocumentDescriptor(this);
		}

		public Builder setLayout(final DocumentLayoutDescriptor layout)
		{
			this.layout = layout;
			return this;
		}

		public Builder setEntityDescriptor(final DocumentEntityDescriptor entityDescriptor)
		{
			this.entityDescriptor = entityDescriptor;
			return this;
		}
	}
}
