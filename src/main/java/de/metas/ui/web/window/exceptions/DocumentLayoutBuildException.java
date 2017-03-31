package de.metas.ui.web.window.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Exception thrown by {@link DocumentDescriptorFactory} on any layout building issue.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DocumentLayoutBuildException extends AdempiereException
{
	public static final DocumentLayoutBuildException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}
		else if (throwable instanceof DocumentLayoutBuildException)
		{
			return (DocumentLayoutBuildException)throwable;
		}

		final Throwable cause = extractCause(throwable);
		if (cause != throwable)
		{
			return wrapIfNeeded(cause);
		}

		// default
		return new DocumentLayoutBuildException(cause.getLocalizedMessage(), cause);
	}

	public DocumentLayoutBuildException(final String message)
	{
		super(message);
	}

	public DocumentLayoutBuildException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

}
