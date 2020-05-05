package de.metas.ui.web.document.filter.sql;

import java.util.Collection;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableList;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Immutable collection of {@link SqlDocumentFilterConverter}s indexed by filterId.
 *
 * To create new instances, please use {@link SqlDocumentFilterConverters}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
@ToString
@EqualsAndHashCode
public final class SqlDocumentFilterConvertersList
{
	/* package */static Builder builder()
	{
		return new Builder();
	}

	/* package */static final SqlDocumentFilterConvertersList EMPTY = new SqlDocumentFilterConvertersList(ImmutableList.of());

	private final ImmutableList<SqlDocumentFilterConverter> converters;

	private SqlDocumentFilterConvertersList(@NonNull final ImmutableList<SqlDocumentFilterConverter> converters)
	{
		this.converters = converters;
	}

	public SqlDocumentFilterConverter getConverterOrDefault(final String filterId, final SqlDocumentFilterConverter defaultConverter)
	{
		for (final SqlDocumentFilterConverter converter : converters)
		{
			if (converter.canConvert(filterId))
			{
				return converter;
			}
		}

		return defaultConverter;
	}

	//
	//
	//
	//
	//
	public static class Builder
	{
		private ImmutableList.Builder<SqlDocumentFilterConverter> converters = null;

		private Builder()
		{
		}

		public SqlDocumentFilterConvertersList build()
		{
			if (converters == null)
			{
				return EMPTY;
			}

			final ImmutableList<SqlDocumentFilterConverter> converters = this.converters.build();
			if (converters.isEmpty())
			{
				return EMPTY;
			}

			return new SqlDocumentFilterConvertersList(converters);
		}

		public Builder converter(@NonNull final SqlDocumentFilterConverter converter)
		{
			if (converters == null)
			{
				converters = ImmutableList.builder();
			}
			converters.add(converter);
			return this;
		}

		public Builder converters(@NonNull final Collection<SqlDocumentFilterConverter> converters)
		{
			if (converters.isEmpty())
			{
				return this;
			}

			if (this.converters == null)
			{
				this.converters = ImmutableList.builder();
			}
			this.converters.addAll(converters);
			return this;
		}

	}
}
