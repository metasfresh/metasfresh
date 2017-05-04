package org.adempiere.ad.expression.api.impl;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.IStringExpressionWrapper;
import org.adempiere.ad.expression.api.TranslatableParameterizedStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;

import ch.qos.logback.core.joran.conditional.Condition;
import de.metas.i18n.TranslatableParameterizedString;

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
 * An {@link IStringExpression} implementation which contains several other {@link IStringExpression}s.
 *
 * It has a powerful builder which is able to also reduce expressions.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class CompositeStringExpression implements IStringExpression
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Collector<IStringExpression, ?, IStringExpression> toCompositeExpression()
	{
		final Supplier<Builder> supplier = Builder::new;
		final BiConsumer<Builder, IStringExpression> accumulator = (builder, expr) -> builder.append(expr);
		final BinaryOperator<Builder> combiner = (left, right) -> left.append(right);
		final Function<Builder, IStringExpression> finisher = (builder) -> builder.build();
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	private final List<IStringExpression> expressions;

	private transient String _expressionStr;
	private transient String _formatedExpressionString;
	private transient Set<String> _parameters;

	private Integer _hashCode;

	private CompositeStringExpression(final Collection<IStringExpression> expressions)
	{
		super();
		this.expressions = ImmutableList.copyOf(expressions);
	}

	@Override
	public String toString()
	{
		final ToStringHelper builder = MoreObjects.toStringHelper("Composite");
		if (!expressions.isEmpty())
		{
			final String separator = "\n* ";
			builder.addValue(separator + Joiner.on(separator).join(expressions));
		}

		return builder.toString();
	}

	@Override
	public int hashCode()
	{
		if (_hashCode == null)
		{
			_hashCode = Objects.hash(expressions);
		}
		return _hashCode;
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

		final CompositeStringExpression other = (CompositeStringExpression)obj;
		return expressions.equals(other.expressions);
	}

	@Override
	public Class<String> getValueClass()
	{
		return String.class;
	}

	@Override
	public String getExpressionString()
	{
		if (_expressionStr == null)
		{
			_expressionStr = expressions.stream()
					.map(expression -> expression.getExpressionString())
					.collect(Collectors.joining());
		}
		return _expressionStr;
	}

	@Override
	public String getFormatedExpressionString()
	{
		if (_formatedExpressionString == null)
		{
			_formatedExpressionString = expressions.stream()
					.map(expression -> expression.getFormatedExpressionString())
					.collect(Collectors.joining());
		}
		return _formatedExpressionString;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		final StringBuilder result = new StringBuilder();
		for (final IStringExpression expression : expressions)
		{
			final String value = expression.evaluate(ctx, onVariableNotFound);
			if (value == null || value == EMPTY_RESULT)
			{
				if (onVariableNotFound == OnVariableNotFound.ReturnNoResult)
				{
					return EMPTY_RESULT;
				}

				continue;
			}

			result.append(value);
		}

		return result.toString();
	}

	@Override
	public IStringExpression resolvePartial(final Evaluatee ctx) throws ExpressionEvaluationException
	{
		try
		{
			return expressions.stream()
					.map(expression -> expression.resolvePartial(ctx))
					.collect(toCompositeExpression());
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}

	@Override
	public Set<String> getParameters()
	{
		if (_parameters == null)
		{
			_parameters = expressions.stream()
					.flatMap(expression -> expression.getParameters().stream())
					.collect(GuavaCollectors.toImmutableSet());
		}
		return _parameters;
	}

	public static final class Builder
	{
		private final ArrayDeque<IStringExpression> expressions = new ArrayDeque<>();

		private StringBuilder _lastConstantBuffer = null;

		private Map<String, ConstantStringExpression> _constantsCache = null;

		private Builder()
		{
			super();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.addValue(Joiner.on("\n").join(expressions))
					.toString();
		}

		public IStringExpression build()
		{
			appendLastConstantIfPresent();

			if (expressions.isEmpty())
			{
				return IStringExpression.NULL;
			}
			else if (expressions.size() == 1)
			{
				return expressions.getFirst();
			}

			return new CompositeStringExpression(ImmutableList.copyOf(expressions));
		}

		public boolean isEmpty()
		{
			return expressions.isEmpty()
					&& (_lastConstantBuffer == null || _lastConstantBuffer.length() == 0);
		}

		public int length()
		{
			return expressions.size();
		}

		public Builder append(final IStringExpression expr)
		{
			if (expr.isNullExpression())
			{
				return this;
			}

			if (expr instanceof CompositeStringExpression)
			{
				final CompositeStringExpression compositeExpr = (CompositeStringExpression)expr;
				appendAll(compositeExpr.expressions);
			}
			else
			{
				reduceAndAppend(expr);
			}

			return this;
		}

		public Builder append(final TranslatableParameterizedString translatableString)
		{
			if (translatableString == null || translatableString == TranslatableParameterizedString.EMPTY)
			{
				return this;
			}

			append(TranslatableParameterizedStringExpression.of(translatableString));
			return this;
		}

		/**
		 *
		 * @param lastExpression
		 * @param expr
		 * @return reduced expression or <code>null</code> if the expressions could not be reduced
		 */
		private final void reduceAndAppend(final IStringExpression expr)
		{
			if (expr instanceof ConstantStringExpression)
			{
				final ConstantStringExpression constantExpr = (ConstantStringExpression)expr;
				appendToLastConstantBuffer(constantExpr.getConstantValue());
				return;
			}

			appendLastConstantIfPresent();
			expressions.add(expr);
		}

		public Builder append(final String constant)
		{
			if (Check.isEmpty(constant))
			{
				return this;
			}

			appendToLastConstantBuffer(constant);

			return this;
		}

		private final void appendToLastConstantBuffer(final String constant)
		{
			if (_lastConstantBuffer == null)
			{
				_lastConstantBuffer = new StringBuilder();
			}
			_lastConstantBuffer.append(constant);
		}

		private final void appendLastConstantIfPresent()
		{
			if (_lastConstantBuffer == null)
			{
				return;
			}

			if (_lastConstantBuffer.length() > 0)
			{
				final IStringExpression expr = createConstantExpression(_lastConstantBuffer.toString());
				expressions.add(expr);
			}

			_lastConstantBuffer = null;
		}

		private final ConstantStringExpression createConstantExpression(final String constant)
		{
			if (_constantsCache == null)
			{
				_constantsCache = new HashMap<>();
				final ConstantStringExpression constantExpr = ConstantStringExpression.of(constant);
				_constantsCache.put(constant, constantExpr);
				return constantExpr;
			}
			else
			{
				return _constantsCache.computeIfAbsent(constant, ConstantStringExpression::of);
			}
		}

		public Builder appendAll(final List<IStringExpression> expressionsToAppend)
		{
			if (expressionsToAppend.isEmpty())
			{
				return this;
			}

			for (final IStringExpression expr : expressionsToAppend)
			{
				append(expr);
			}

			return this;
		}

		public Builder appendAllJoining(final String separator, final List<IStringExpression> expressionsToAppend)
		{
			if (expressionsToAppend.isEmpty())
			{
				return this;
			}

			final IStringExpression separatorExpr = Check.isEmpty(separator) ? null : createConstantExpression(separator);
			boolean isFirstExpr = true;
			for (final IStringExpression expr : expressionsToAppend)
			{
				if (expr == null || expr.isNullExpression())
				{
					continue;
				}

				if (separatorExpr != null && !isFirstExpr)
				{
					append(separatorExpr);
				}

				append(expr);

				isFirstExpr = false;
			}

			return this;
		}

		public Builder appendIfNotEmpty(final String string)
		{
			if (isEmpty())
			{
				return this;
			}

			return append(string);
		}

		public Builder append(final StringBuilder constant)
		{
			if (constant == null || constant.length() <= 0)
			{
				return this;
			}

			append(constant.toString());
			return this;
		}

		public Builder append(final Builder otherBuilder)
		{
			for (final IStringExpression expr : otherBuilder.expressions)
			{
				append(expr);
			}

			if (otherBuilder._lastConstantBuffer != null && otherBuilder._lastConstantBuffer.length() > 0)
			{
				appendToLastConstantBuffer(otherBuilder._lastConstantBuffer.toString());
			}

			return this;
		}

		public Builder append(final CtxName name)
		{
			append(new SingleParameterStringExpression(name.toStringWithMarkers(), name));
			return this;
		}

		/**
		 * Wraps the entire content of this builder using given wrapper.
		 * After this method invocation the builder will contain only the wrapped expression.
		 *
		 * @param wrapper
		 */
		public Builder wrap(final IStringExpressionWrapper wrapper)
		{
			Check.assumeNotNull(wrapper, "Parameter wrapper is not null");

			final IStringExpression expression = build();
			final IStringExpression expressionWrapped = wrapper.wrap(expression);

			expressions.clear();
			append(expressionWrapped);

			return this;
		}

		/**
		 * If the {@link Condition} is <code>true</code> then it wraps the entire content of this builder using given wrapper.
		 * After this method invocation the builder will contain only the wrapped expression.
		 *
		 * @param condition
		 * @param wrapper
		 */
		public Builder wrapIfTrue(final boolean condition, final IStringExpressionWrapper wrapper)
		{
			if (!condition)
			{
				return this;
			}

			return wrap(wrapper);
		}
	}
}
