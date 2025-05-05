package org.adempiere.ad.expression.exceptions;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Exception thrown when expression evaluation fails
 *
 * @author tsa
 */
public class ExpressionEvaluationException extends ExpressionException
{
	public static ExpressionEvaluationException wrapIfNeeded(final Throwable throwable)
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

	private final ArrayList<IExpression<?>> expressions = new ArrayList<>();

	private String partialEvaluatedExpression;

	protected ExpressionEvaluationException(final String msg, final Throwable cause)
	{
		super(msg, cause);
	}

	protected ExpressionEvaluationException(final @NonNull ITranslatableString message)
	{
		// ExpressionEvaluationException are usually internal errors, so userValidationError=false
		super(message, false);
	}

	public static ExpressionEvaluationException newWithPlainMessage(@Nullable final String plainMessage)
	{
		return new ExpressionEvaluationException(TranslatableStrings.constant(plainMessage));
	}

	public static AdempiereException newWithTranslatableMessage(@Nullable final String translatableMessage)
	{
		return new ExpressionEvaluationException(TranslatableStrings.parse(translatableMessage));
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
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();

		final ITranslatableString originalMessage = getOriginalMessage();
		if (!TranslatableStrings.isBlank(originalMessage))
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
				message.append("\n * ").appendObj(expression);
			}
		}

		if (!Check.isEmpty(partialEvaluatedExpression))
		{
			message.append("\nPartial evaluated expression: ").append(partialEvaluatedExpression);
		}

		return message.build();
	}
}
