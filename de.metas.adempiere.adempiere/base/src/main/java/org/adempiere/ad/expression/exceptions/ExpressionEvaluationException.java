package org.adempiere.ad.expression.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.util.Check;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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
 * Exception thrown when expression evaluation fails
 *
 * @author tsa
 *
 */
public class ExpressionEvaluationException extends ExpressionException
{
	public static final ExpressionEvaluationException wrapIfNeeded(final Throwable throwable)
	{
		Check.assumeNotNull(throwable, "throwable not null");

		if (throwable instanceof ExpressionEvaluationException)
		{
			return (ExpressionEvaluationException)throwable;
		}

		final Throwable cause = extractCause(throwable);
		if (cause != throwable)
		{
			return wrapIfNeeded(cause);
		}

		return new ExpressionEvaluationException(extractMessage(throwable), cause);
	}

	private static final long serialVersionUID = -4311254481298308224L;

	private final List<IExpression<?>> expressions = new ArrayList<>();

	private String partialEvaluatedExpression;

	public ExpressionEvaluationException(final String msg)
	{
		super(msg);
	}

	public ExpressionEvaluationException(final String msg, final Throwable cause)
	{
		super(msg, cause);
	}

	public ExpressionEvaluationException addExpression(final IExpression<?> expression)
	{
		if (expression == null)
		{
			return this;
		}

		expressions.add(expression);
		resetMessageBuilt();
		return this;
	}

	public ExpressionEvaluationException setPartialEvaluatedExpression(final String partialEvaluatedExpression)
	{
		this.partialEvaluatedExpression = partialEvaluatedExpression;
		resetMessageBuilt();
		return this;
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder message = new StringBuilder();
		final String originalMessage = getOriginalMessage();
		if (!Check.isEmpty(originalMessage))
		{
			message.append(originalMessage);
		}
		else
		{
			message.append("Unknown evaluation error");
		}

		if (!expressions.isEmpty())
		{
			message.append("\nExpressions trace:");

			for (final IExpression<?> expression : expressions)
			{
				message.append("\n * ").append(expression);
			}
		}

		if (!Check.isEmpty(partialEvaluatedExpression))
		{
			message.append("\nPartial evaluated expression: ").append(partialEvaluatedExpression);
		}

		return message.toString();
	}
}
