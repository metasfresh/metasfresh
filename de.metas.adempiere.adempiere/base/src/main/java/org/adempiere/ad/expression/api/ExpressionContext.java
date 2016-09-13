package org.adempiere.ad.expression.api;

import com.google.common.collect.ImmutableMap;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Context used to pass different options to {@link IExpressionCompiler} and not only.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ExpressionContext
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final ExpressionContext EMPTY = new ExpressionContext();

	private final ImmutableMap<String, Object> context;

	private ExpressionContext(final ImmutableMap<String, Object> context)
	{
		super();
		this.context = context;
	}

	private ExpressionContext()
	{
		super();
		context = ImmutableMap.of();
	}

	public Object get(final String name)
	{
		return context.get(name);
	}

	public static final class Builder
	{
		private final ImmutableMap.Builder<String, Object> context = ImmutableMap.builder();

		private Builder()
		{
			super();
		}

		public ExpressionContext build()
		{
			final ImmutableMap<String, Object> context = this.context.build();
			if (context.isEmpty())
			{
				return EMPTY;
			}
			return new ExpressionContext(context);
		}

		public Builder putContext(final String name, final Object value)
		{
			if (value == null)
			{
				return this;
			}
			context.put(name, value);
			return this;
		}
	}
}
