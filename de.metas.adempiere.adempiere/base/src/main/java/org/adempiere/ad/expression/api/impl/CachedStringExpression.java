package org.adempiere.ad.expression.api.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.ICachedStringExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;
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
 * Wraps a given {@link IStringExpression} and caches it's evaluation results.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class CachedStringExpression implements ICachedStringExpression
{
	public static final ICachedStringExpression wrapIfPossible(final IStringExpression expression)
	{
		if (expression instanceof ICachedStringExpression)
		{
			return (ICachedStringExpression)expression;
		}
		else
		{
			return new CachedStringExpression(expression);
		}
	}

	private final IStringExpression expression;

	private final transient ConcurrentHashMap<ArrayKey, String> _cachedValues = new ConcurrentHashMap<>();
	private final transient ConcurrentHashMap<ArrayKey, IStringExpression> _cachedPartialExpressions = new ConcurrentHashMap<>();

	private CachedStringExpression(final IStringExpression expression)
	{
		Check.assumeNotNull(expression, "Parameter expression is not null");
		this.expression = expression;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("Cached")
				.addValue(expression)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(expression);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		final CachedStringExpression other = (CachedStringExpression)obj;
		// NOTE: we are not comparing the cache
		return expression.equals(other.expression);
	}

	@Override
	public Class<String> getValueClass()
	{
		return String.class;
	}

	@Override
	public String getExpressionString()
	{
		return expression.getExpressionString();
	}

	@Override
	public Set<String> getParameters()
	{
		return expression.getParameters();
	}

	@Override
	public ICachedStringExpression caching()
	{
		return this; // already cached
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		try
		{
			// Build the effective context
			final boolean failIfNotFound = onVariableNotFound == OnVariableNotFound.Fail;
			final EffectiveValuesEvaluatee ctxEffective = EffectiveValuesEvaluatee.extractFrom(expression.getParameters(), ctx, failIfNotFound);

			// Caching key: the effective context and the OnVariableNotFound
			final ArrayKey key = mkCachingKey(ctxEffective, onVariableNotFound);

			//
			// Get from cache / compute
			return _cachedValues.computeIfAbsent(key, akey -> expression.evaluate(ctxEffective, onVariableNotFound));
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}

	@Override
	public IStringExpression resolvePartial(final Evaluatee ctx) throws ExpressionEvaluationException
	{
		try
		{
			// Build the effective context
			final boolean failIfNotFound = false;
			final EffectiveValuesEvaluatee ctxEffective = EffectiveValuesEvaluatee.extractFrom(expression.getParameters(), ctx, failIfNotFound);

			// Caching key: the effective context and the OnVariableNotFound
			final OnVariableNotFound onVariableNotFound = null; // not relevant
			final ArrayKey key = mkCachingKey(ctxEffective, onVariableNotFound);

			//
			// Get from cache / compute
			return _cachedPartialExpressions.computeIfAbsent(key, akey -> expression.resolvePartial(ctxEffective));
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}

	private static final ArrayKey mkCachingKey(final EffectiveValuesEvaluatee ctx, final OnVariableNotFound onVariableNotFound)
	{
		// NOTE: OnVariableNotFound is absolutely mandatory to be part of the caching key because:
		// * this method might be called also for resolvePartial
		// * me might have different results for different OnVariableNotFound
		return Util.mkKey(ctx, onVariableNotFound);
	}

	private static final class EffectiveValuesEvaluatee implements Evaluatee2
	{
		public static final EffectiveValuesEvaluatee extractFrom(final Collection<String> parameters, final Evaluatee ctx, final boolean failIfNotFound)
		{
			if (parameters == null || parameters.isEmpty())
			{
				return EMPTY;
			}

			HashMap<String, String> values = null;
			for (final String parameterName : parameters)
			{
				final String value = extractParameter(ctx, parameterName);

				//
				// Handle variable not found
				if (value == null)
				{
					if (failIfNotFound)
					{
						throw new ExpressionEvaluationException("@NotFound@: " + parameterName);
					}

					continue;
				}

				//
				// Add value to the map
				if (values == null)
				{
					values = new HashMap<>();
				}
				values.put(parameterName, value);
			}

			return EffectiveValuesEvaluatee.of(values);
		}

		/**
		 * @return extracted parameter or null if does not exist
		 */
		private static String extractParameter(final Evaluatee ctx, final String parameterName)
		{
			if (ctx instanceof Evaluatee2)
			{
				final Evaluatee2 ctx2 = (Evaluatee2)ctx;

				if (!ctx2.has_Variable(parameterName))
				{
					return null;
				}

				return ctx2.get_ValueAsString(parameterName);
			}
			else
			{
				final String value = ctx.get_ValueAsString(parameterName);
				if (Env.isPropertyValueNull(parameterName, value))
				{
					return null;
				}

				return value;
			}
		}

		public static final EffectiveValuesEvaluatee of(@Nullable final Map<String, String> values)
		{
			if (values == null || values.isEmpty())
			{
				return EMPTY;
			}

			return new EffectiveValuesEvaluatee(ImmutableMap.copyOf(values));
		}

		public static final EffectiveValuesEvaluatee EMPTY = new EffectiveValuesEvaluatee(ImmutableMap.of());

		private final ImmutableMap<String, String> values;

		private EffectiveValuesEvaluatee(final ImmutableMap<String, String> values)
		{
			this.values = values;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.addValue(values)
					.toString();
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(values);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (!getClass().equals(obj.getClass()))
			{
				return false;
			}
			final EffectiveValuesEvaluatee other = (EffectiveValuesEvaluatee)obj;
			return values.equals(other.values);
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			return values.get(variableName);
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			return values.containsKey(variableName);
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			// TODO Auto-generated method stub
			return null;
		}

	}
}
