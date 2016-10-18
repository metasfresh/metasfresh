package de.metas.ui.web.window.model.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;
import org.compiere.model.MQuery;

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
public final class DocumentFilter
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static DocumentFilter of(final MQuery mquery)
	{
		final List<DocumentFilterParam> parameters = new ArrayList<>();
		for (int i = 0, restrictionsCount = mquery.getRestrictionCount(); i < restrictionsCount; i++)
		{
			final DocumentFilterParam param = DocumentFilterParam.of(mquery, i);
			parameters.add(param);
		}

		final String filterId;
		if (parameters.size() == 1 && !parameters.get(0).isSqlFilter())
		{
			filterId = parameters.get(0).getFieldName();
		}
		else
		{
			filterId = "MQuery-" + UUID.randomUUID(); // FIXME: find a better filterId
		}

		return builder()
				.setFilterId(filterId)
				.setParameters(parameters)
				.build();
	}

	private final String filterId;
	private final List<DocumentFilterParam> parameters;

	private DocumentFilter(final Builder builder)
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

	public List<DocumentFilterParam> getParameters()
	{
		return parameters;
	}

	public static final class Builder
	{
		private String filterId;
		private List<DocumentFilterParam> parameters;

		private Builder()
		{
			super();
		}

		public DocumentFilter build()
		{
			return new DocumentFilter(this);
		}

		public Builder setFilterId(final String filterId)
		{
			this.filterId = filterId;
			return this;
		}

		public Builder setParameters(final List<DocumentFilterParam> parameters)
		{
			this.parameters = parameters;
			return this;
		}

		public Builder addParameter(final DocumentFilterParam parameter)
		{
			if (parameters == null)
			{
				parameters = new ArrayList<>();
			}
			parameters.add(parameter);
			return this;
		}
	}

}
