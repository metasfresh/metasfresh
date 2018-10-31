package de.metas.ui.web.window.descriptor;

import java.util.function.Supplier;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.ui.web.cache.ETag;
import de.metas.ui.web.cache.ETagAware;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class DocumentDescriptor implements ETagAware
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final DocumentLayoutDescriptor layout;
	private final DocumentEntityDescriptor entityDescriptor;

	// ETag support
	private static final Supplier<ETag> nextETagSupplier = ETagAware.newETagGenerator();
	private final ETag eTag = nextETagSupplier.get();

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
				.add("entity", entityDescriptor)
				.add("layout", layout)
				.add("eTag", eTag)
				.toString();
	}

	public DocumentLayoutDescriptor getLayout()
	{
		return layout;
	}

	public ViewLayout getViewLayout(final JSONViewDataType viewDataType)
	{
		switch (viewDataType)
		{
			case grid:
			{
				return layout.getGridViewLayout();
			}
			case list:
			{
				return layout.getSideListViewLayout();
			}
			default:
			{
				throw new IllegalArgumentException("Invalid viewDataType: " + viewDataType);
			}
		}
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	@Override
	public ETag getETag()
	{
		return eTag;
	}

	//
	public static final class Builder
	{
		private DocumentLayoutDescriptor layout;
		private DocumentEntityDescriptor entityDescriptor;

		private Builder()
		{
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
