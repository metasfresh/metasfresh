package de.metas.ui.web.pattribute;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

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

@Immutable
public final class ASIDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int attributeSetId;
	private final DocumentEntityDescriptor entityDescriptor;
	private final ASILayout layout;

	private ASIDescriptor(final Builder builder)
	{
		super();

		attributeSetId = builder.attributeSetId;

		entityDescriptor = builder.entityDescriptor;
		Check.assumeNotNull(entityDescriptor, "Parameter entityDescriptor is not null");

		layout = builder.layout;
		Check.assumeNotNull(layout, "Parameter layout is not null");
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("M_AttributeSet_ID", attributeSetId)
				.add("entityDescriptor", entityDescriptor)
				.toString();
	}

	public int getM_AttributeSet_ID()
	{
		return attributeSetId;
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	public ASILayout getLayout()
	{
		return layout;
	}

	public static final class Builder
	{
		private DocumentEntityDescriptor entityDescriptor;
		private ASILayout layout;
		private int attributeSetId = 0;

		private Builder()
		{
			super();
		}

		public ASIDescriptor build()
		{
			return new ASIDescriptor(this);
		}

		public Builder setEntityDescriptor(final DocumentEntityDescriptor entityDescriptor)
		{
			this.entityDescriptor = entityDescriptor;
			return this;
		}

		public Builder setLayout(final ASILayout layout)
		{
			this.layout = layout;
			return this;
		}

		public Builder setM_AttributeSet_ID(final int attributeSetId)
		{
			this.attributeSetId = attributeSetId;
			return this;
		}

	}

}
