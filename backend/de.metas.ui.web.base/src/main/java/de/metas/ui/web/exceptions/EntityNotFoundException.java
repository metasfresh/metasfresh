package de.metas.ui.web.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Exception thrown when some entity (document, process etc) was not found.
 *
 * NOTE: this exceptions binds to HTTP 404
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends AdempiereException
{
	public static final EntityNotFoundException wrapIfNeeded(@NonNull final Throwable throwable)
	{
		if (throwable instanceof EntityNotFoundException)
		{
			return (EntityNotFoundException)throwable;
		}

		final Throwable cause = extractCause(throwable);
		if(cause != throwable)
		{
			return wrapIfNeeded(cause);
		}

		return new EntityNotFoundException(extractMessage(throwable), cause);
	}

	public EntityNotFoundException(final String message)
	{
		super(message);
	}

	public EntityNotFoundException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
