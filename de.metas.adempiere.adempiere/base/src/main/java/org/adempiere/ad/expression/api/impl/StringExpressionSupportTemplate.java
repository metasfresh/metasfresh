package org.adempiere.ad.expression.api.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionCompiler;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.util.Check;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableSet;

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

public abstract class StringExpressionSupportTemplate<V, ET extends IExpression<V>>
{
	private final Compiler<V, ET> compiler;

	protected StringExpressionSupportTemplate(final Compiler<V, ET> compiler)
	{
		super();

		Check.assumeNotNull(compiler, "Parameter compiler is not null");
		this.compiler = compiler;
	}

	public final IExpressionCompiler<V, ET> getCompiler()
	{
		return compiler;
	}

	protected static abstract class Compiler<V, ET extends IExpression<V>> extends AbstractChunkBasedExpressionCompiler<V, ET>
	{
		protected final Class<V> valueClass;
		protected final ValueConverter<V, ET> valueConverter;
		protected final V noResultValue;

		protected Compiler(final Class<V> valueClass, final ValueConverter<V, ET> valueConverter)
		{
			super();
			Check.assumeNotNull(valueClass, "Parameter valueClass is not null");
			this.valueClass = valueClass;

			Check.assumeNotNull(valueConverter, "Parameter valueConverter is not null");
			this.valueConverter = valueConverter;

			this.noResultValue = null;
		}

		public final boolean isNoResult(final Object value)
		{
			return value == null || value == noResultValue;
		}

		@Override
		protected abstract ET getNullExpression();

		@Override
		protected final ET createConstantExpression(final ExpressionContext context, final String expressionStr)
		{
			final V constantValue = valueConverter.convertFrom(expressionStr, context);
			if (isNoResult(constantValue))
			{
				return getNullExpression();
			}

			return createConstant(context, expressionStr, constantValue);
		}

		protected abstract ET createConstant(ExpressionContext context, final String expressionStr, final V constantValue);

		@Override
		protected abstract ET createSingleParamaterExpression(final ExpressionContext context, final String expressionStr, final CtxName parameter);

		@Override
		protected abstract ET createGeneralExpression(final ExpressionContext context, final String expressionStr, final List<Object> expressionChunks);
	}

	protected static interface ValueConverter<V, ET extends IExpression<V>>
	{
		V convertFrom(final Object value, final ExpressionContext context);
	}

	private static abstract class ExpressionTemplateBase<V, ET extends IExpression<V>> implements IExpression<V>
	{
		private final Class<V> valueClass;
		protected final V noResultValue;
		protected final ValueConverter<V, ET> valueConverter;
		private final Compiler<V, ET> compiler;

		protected final ExpressionContext options;

		private ExpressionTemplateBase(final ExpressionContext context, final Compiler<V, ET> compiler)
		{
			super();
			this.valueClass = compiler.valueClass;
			this.noResultValue = compiler.noResultValue;
			this.valueConverter = compiler.valueConverter;
			this.compiler = compiler;
			this.options = extractOptions(context);
		}

		protected ExpressionContext extractOptions(final ExpressionContext context)
		{
			return context;
		}

		@Override
		public final Class<V> getValueClass()
		{
			return valueClass;
		}

		@Override
		public final boolean isNoResult(final Object value)
		{
			return compiler.isNoResult(value);
		}

		@Override
		public boolean isNullExpression()
		{
			return false;
		}
	}

	protected abstract static class NullExpressionTemplate<V, ET extends IExpression<V>> extends ExpressionTemplateBase<V, ET>
	{
		protected NullExpressionTemplate(final Compiler<V, ET> compiler)
		{
			super(ExpressionContext.EMPTY, compiler);
		}

		@Override
		public String getExpressionString()
		{
			return "";
		}

		@Override
		public String getFormatedExpressionString()
		{
			return "";
		}

		@Override
		public Set<String> getParameters()
		{
			return ImmutableSet.of();
		}

		@Override
		public boolean isNullExpression()
		{
			return true;
		}

		@Override
		public V evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
		{
			return noResultValue;
		}

		@Override
		public V evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
		{
			return noResultValue;
		}
	}

	protected abstract static class ConstantExpressionTemplate<V, ET extends IExpression<V>> extends ExpressionTemplateBase<V, ET>
	{
		private final String expressionStr;
		private final V constantValue;

		protected ConstantExpressionTemplate(final ExpressionContext context, final Compiler<V, ET> compiler, final String expressionStr, final V constantValue)
		{
			super(context, compiler);
			Check.assumeNotNull(expressionStr, "Parameter expressionStr is not null");
			this.expressionStr = expressionStr;
			this.constantValue = constantValue;
		}

		@Override
		public String toString()
		{
			return expressionStr;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(expressionStr);
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
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final ConstantExpressionTemplate<?, ?> other = (ConstantExpressionTemplate<?, ?>)obj;

			return Objects.equals(expressionStr, other.expressionStr)
					&& Objects.equals(constantValue, constantValue)
					&& Objects.equals(getValueClass(), other.getValueClass());
		}

		@Override
		public String getExpressionString()
		{
			return expressionStr;
		}

		@Override
		public String getFormatedExpressionString()
		{
			return expressionStr;
		}

		@Override
		public Set<String> getParameters()
		{
			return ImmutableSet.of();
		}

		@Override
		public V evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
		{
			return constantValue;
		}

		@Override
		public V evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound)
		{
			return constantValue;
		}
	}

	protected abstract static class SingleParameterExpressionTemplate<V, ET extends IExpression<V>> extends ExpressionTemplateBase<V, ET>
	{
		private final String expressionStr;

		protected final CtxName parameter;
		private final Set<String> _parametersList;

		/* package */ SingleParameterExpressionTemplate(final ExpressionContext context, final Compiler<V, ET> compiler, final String expressionStr, final CtxName parameter)
		{
			super(context, compiler);

			Check.assumeNotNull(expressionStr, "Parameter expressionStr is not null");
			this.expressionStr = expressionStr;

			Check.assumeNotNull(parameter, "Parameter parameter is not null");
			this.parameter = parameter;
			_parametersList = ImmutableSet.of(parameter.getName()); // NOTE: we need only the parameter name (and not all modifiers)
		}

		@Override
		public String toString()
		{
			return expressionStr;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(parameter);
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
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final SingleParameterExpressionTemplate<?, ?> other = (SingleParameterExpressionTemplate<?, ?>)obj;
			return Objects.equals(parameter, other.parameter)
					&& Objects.equals(getValueClass(), other.getValueClass());
		}

		@Override
		public String getExpressionString()
		{
			return expressionStr;
		}

		@Override
		public String getFormatedExpressionString()
		{
			return expressionStr; // expressionStr is good enough in this case
		}

		@Override
		public Set<String> getParameters()
		{
			return _parametersList;
		}

		protected Object extractParameterValue(final Evaluatee ctx)
		{
			final String valueStr = parameter.getValueAsString(ctx);

			if (valueStr == null || valueStr == CtxName.VALUE_NULL)
			{
				return null;
			}

			return valueStr;
		}

		@Override
		public final V evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
		{
			final Object valueObj = extractParameterValue(ctx);

			if (valueObj == null)
			{
				if (onVariableNotFound == OnVariableNotFound.ReturnNoResult)                            // i.e. !ignoreUnparsable
				{
					return noResultValue;
				}
				else if (onVariableNotFound == OnVariableNotFound.Fail)
				{
					throw new ExpressionEvaluationException("@NotFound@: " + parameter);
				}
				else
				{
					throw new IllegalArgumentException("Unknown " + OnVariableNotFound.class + " value: " + onVariableNotFound);
				}
			}

			return valueConverter.convertFrom(valueObj, options);
		}
	}

	protected abstract static class GeneralExpressionTemplate<V, ET extends IExpression<V>> extends ExpressionTemplateBase<V, ET>
	{
		private final StringExpression stringExpression;

		/* package */ GeneralExpressionTemplate(final ExpressionContext context, final Compiler<V, ET> compiler, final String expressionStr, final List<Object> expressionChunks)
		{
			super(context, compiler);
			stringExpression = new StringExpression(expressionStr, expressionChunks);
		}

		@Override
		public String getExpressionString()
		{
			return stringExpression.getExpressionString();
		}

		@Override
		public String getFormatedExpressionString()
		{
			return stringExpression.getFormatedExpressionString();
		}

		@Override
		public Set<String> getParameters()
		{
			return stringExpression.getParameters();
		}

		@Override
		public V evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
		{
			final String resultStr = stringExpression.evaluate(ctx, onVariableNotFound);
			if (stringExpression.isNoResult(resultStr))
			{
				if (onVariableNotFound == OnVariableNotFound.ReturnNoResult)                            // i.e. !ignoreUnparsable
				{
					return noResultValue;
				}
				// else if (onVariableNotFound == OnVariableNotFound.Fail) // no need to handle this case because we expect an exception to be already thrown
				else
				{
					throw new IllegalArgumentException("Unknown " + OnVariableNotFound.class + " value: " + onVariableNotFound);
				}
			}

			return valueConverter.convertFrom(resultStr, options);
		}
	}
}
