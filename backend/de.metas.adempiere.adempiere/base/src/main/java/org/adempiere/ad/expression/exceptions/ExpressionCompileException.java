package org.adempiere.ad.expression.exceptions;

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

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Exception thrown when we have an expression compilation failure
 *
 * @author tsa
 */
public class ExpressionCompileException extends ExpressionException
{
	private static final long serialVersionUID = 7284538289150002848L;

	public ExpressionCompileException(final String msg)
	{
		// ExpressionCompileException are usually internal errors, so userValidationError=false
		super(TranslatableStrings.parse(msg), false);
	}

	protected ExpressionCompileException(final @NonNull ITranslatableString message, final boolean userValidationError)
	{
		super(message, userValidationError);
	}

	public static ExpressionCompileException newWithPlainMessage(@Nullable final String plainMessage) {return new ExpressionCompileException(TranslatableStrings.constant(plainMessage), false);}

}
