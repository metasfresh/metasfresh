package de.metas.ui.web.window.exceptions;

<<<<<<< HEAD
=======
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

<<<<<<< HEAD
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
=======
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Exception thrown by {@link DocumentDescriptorFactory} on any layout building issue.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DocumentLayoutBuildException extends AdempiereException
{
<<<<<<< HEAD
	public static final DocumentLayoutBuildException wrapIfNeeded(final Throwable throwable)
=======
	public static DocumentLayoutBuildException wrapIfNeeded(final Throwable throwable)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
=======
	public static Throwable extractCause(final Throwable throwable)
	{
		if (throwable.getClass().equals(DocumentLayoutBuildException.class))
		{
			final DocumentLayoutBuildException documentLayoutBuildException = (DocumentLayoutBuildException) throwable;
			final Throwable cause = documentLayoutBuildException.getCause();
			return cause != null ? cause : documentLayoutBuildException;
		}
		else
		{
			return AdempiereException.extractCause(throwable);
		}
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public DocumentLayoutBuildException(final String message)
	{
		super(message);
	}

<<<<<<< HEAD
	public DocumentLayoutBuildException(final String message, final Throwable cause)
=======
	private DocumentLayoutBuildException(final String message, final Throwable cause)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		super(message, cause);
	}

}
