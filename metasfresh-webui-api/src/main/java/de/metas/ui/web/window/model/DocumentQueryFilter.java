package de.metas.ui.web.window.model;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

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

@Immutable
public final class DocumentQueryFilter
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String filterId;
	private final List<DocumentQueryFilterParam> parameters;

	private DocumentQueryFilter(final Builder builder)
	{
		super();

		filterId = builder.filterId;
		Check.assumeNotEmpty(filterId, "filterId is not empty");

		parameters = builder.parameters == null ? ImmutableList.of() : ImmutableList.copyOf(builder.parameters);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("filterId", filterId)
				.add("parameters", parameters.isEmpty() ? null : parameters)
				.toString();
	}
	
	public String getFilterId()
	{
		return filterId;
	}
	
	public List<DocumentQueryFilterParam> getParameters()
	{
		return parameters;
	}

	public static final class Builder
	{
		private String filterId;
		private List<DocumentQueryFilterParam> parameters;

		private Builder()
		{
			super();
		}

		public DocumentQueryFilter build()
		{
			return new DocumentQueryFilter(this);
		}

		public Builder setFilterId(final String filterId)
		{
			this.filterId = filterId;
			return this;
		}

		public Builder setParameters(final List<DocumentQueryFilterParam> parameters)
		{
			this.parameters = parameters;
			return this;
		}
	}

}
