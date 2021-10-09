/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.util.web.exception;

import de.metas.rest_api.utils.IdentifierString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@SuppressWarnings("serial")
public class InvalidIdentifierException extends AdempiereException
{
	public InvalidIdentifierException(final IdentifierString invalidIdentifier)
	{
		this(invalidIdentifier != null ? invalidIdentifier.toJson() : null,
				null,
				null);
	}

	public InvalidIdentifierException(@Nullable final String invalidIdentifierString)
	{
		this(invalidIdentifierString,
				null,
				null)
		;
	}

	public InvalidIdentifierException(
			@Nullable final String invalidIdentifierString,
			@Nullable final Object objectWithInvalidIdentifier)
	{
		this(invalidIdentifierString, objectWithInvalidIdentifier, null);
	}

	public InvalidIdentifierException(
			@Nullable final String invalidIdentifierString,
			@Nullable final Object objectWithInvalidIdentifier,
			@Nullable final Throwable cause)
	{
		super("Unable to interpret identifierString=" + invalidIdentifierString, cause);

		if (objectWithInvalidIdentifier != null)
		{
			appendParametersToMessage();
			setParameter("objectWithInvalidIdentifier", objectWithInvalidIdentifier);
		}
	}
}
