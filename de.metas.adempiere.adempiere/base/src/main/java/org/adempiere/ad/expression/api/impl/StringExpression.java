package org.adempiere.ad.expression.api.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonStringExpressionSerializer;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Standard implementation of {@link IStringExpression}
 *
 * @author tsa
 *
 */
@JsonSerialize(using = JsonStringExpressionSerializer.class)
/* package */final class StringExpression implements IStringExpression
{
	private final List<Object> expressionChunks;
	private final Set<String> parametersAsStringList;

	// Precomputed values
	private String _expressionStr;
	private String _formatedExpressionString;

	/* package */ StringExpression(@Nullable final String expressionStr, final List<Object> expressionChunks)
	{
		super();
		_expressionStr = expressionStr;
		this.expressionChunks = ImmutableList.copyOf(expressionChunks);

		//
		// Initialize stringParams list
		final Set<String> stringParams = new LinkedHashSet<>(); // NOTE: preserve parameters order because at least some tests are relying on this
		for (final Object chunk : expressionChunks)
		{
			if (chunk instanceof CtxName)
			{
				// NOTE: we need only the parameter name (and not all modifiers)
				final String parameterName = ((CtxName)chunk).getName();
				stringParams.add(parameterName);
			}
		}
		parametersAsStringList = ImmutableSet.copyOf(stringParams);
	}

	@Override
	public String toString()
	{
		return getExpressionString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(expressionChunks);
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

		final StringExpression other = (StringExpression)obj;
		return Objects.equals(expressionChunks, other.expressionChunks);
	}

	@Override
	public String getExpressionString()
	{
		if (_expressionStr == null)
		{
			_expressionStr = buildExpressionStringFromChunks(expressionChunks);
		}
		return _expressionStr;
	}

	@Override
	public String getFormatedExpressionString()
	{
		if (_formatedExpressionString == null)
		{
			_formatedExpressionString = buildExpressionStringFromChunks(expressionChunks);
		}
		return _formatedExpressionString;
	}

	private static final String buildExpressionStringFromChunks(final List<Object> expressionChunks)
	{
		final StringBuilder sb = new StringBuilder();
		for (final Object chunk : expressionChunks)
		{
			if (chunk instanceof CtxName)
			{
				final CtxName name = (CtxName)chunk;
				sb.append(name.toStringWithMarkers());
			}
			else
			{
				sb.append(chunk.toString());
			}
		}

		return sb.toString();
	}

	@Override
	public Set<String> getParameters()
	{
		return parametersAsStringList;
	}

	@VisibleForTesting
	/* package */List<Object> getExpressionChunks()
	{
		return expressionChunks;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound)
	{
		final StringBuilder result = new StringBuilder();

		try
		{
			for (final Object chunk : expressionChunks)
			{
				if (chunk instanceof CtxName)
				{
					final CtxName token = (CtxName)chunk;
					final String value = StringExpressionsHelper.evaluateParam(token, ctx, onVariableNotFound);
					if (value == null || value == EMPTY_RESULT)
					{
						return EMPTY_RESULT;
					}

					result.append(value);
				}
				else
				{
					final String chunkStr = chunk.toString();
					result.append(chunkStr);
				}
			}

			return result.toString();
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this)
					.setPartialEvaluatedExpression(result.toString());
		}
	}

	@Override
	public IStringExpression resolvePartial(final Evaluatee ctx) throws ExpressionEvaluationException
	{
		final List<Object> expressionChunksNew = new ArrayList<>();

		try
		{
			StringBuilder lastConstantBuffer = null;
			boolean somethingChanged = false; // did we changed something (i.e. do we evaluated some context variables?)
			boolean haveVariables = false; // do we have variables between the new chunks?

			for (final Object chunk : expressionChunks)
			{
				if (chunk instanceof CtxName)
				{
					final CtxName token = (CtxName)chunk;
					final String tokenValueConstant = StringExpressionsHelper.evaluateParam(token, ctx, OnVariableNotFound.ReturnNoResult);
					if (tokenValueConstant == null || tokenValueConstant == EMPTY_RESULT)
					{
						if (lastConstantBuffer != null)
						{
							expressionChunksNew.add(lastConstantBuffer.toString());
							lastConstantBuffer = null;
						}

						expressionChunksNew.add(token);
						haveVariables = true;
					}
					else
					{
						somethingChanged = true; // we evaluated a context variable

						if (lastConstantBuffer == null)
						{
							lastConstantBuffer = new StringBuilder();
						}
						lastConstantBuffer.append(tokenValueConstant);
					}
				}
				else
				{
					final String constant = chunk.toString();
					if (lastConstantBuffer == null)
					{
						lastConstantBuffer = new StringBuilder();
					}
					lastConstantBuffer.append(constant);
				}
			}

			// If there was no change, we return the same expression
			if (!somethingChanged)
			{
				return this;
			}

			// Check if our new expression will be constant
			if (!haveVariables)
			{
				assert expressionChunksNew.isEmpty() : "We assume there were no expression chunks collected so far: " + expressionChunksNew;
				return ConstantStringExpression.of(lastConstantBuffer.toString());
			}

			// Append the last constant buffer if any
			if (lastConstantBuffer != null)
			{
				expressionChunksNew.add(lastConstantBuffer.toString());
				lastConstantBuffer = null;
			}

			final String expressionStrNew = null; // to be computed when needed
			return new StringExpression(expressionStrNew, expressionChunksNew);
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this)
					.setPartialEvaluatedExpression(expressionChunksNew == null ? null : expressionChunksNew.toString());
		}
	}
}
