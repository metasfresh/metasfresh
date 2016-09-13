package de.metas.ui.web.window.exceptions;

import org.adempiere.exceptions.AdempiereException;

import de.metas.ui.web.window.datatypes.DocumentPath;

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

@SuppressWarnings("serial")
public class InvalidDocumentPathException extends AdempiereException
{
	public InvalidDocumentPathException(final DocumentPath documentPath)
	{
		super(buildMsg(documentPath, null));
	}

	public InvalidDocumentPathException(final DocumentPath documentPath, final String reason)
	{
		super(buildMsg(documentPath, reason));
	}

	public InvalidDocumentPathException(final String message)
	{
		super(message);
	}

	private static String buildMsg(final DocumentPath documentPath, final String reason)
	{
		final StringBuilder msg = new StringBuilder();
		msg.append("Invalid document path ").append(documentPath).append(".");
		if (reason != null)
		{
			msg.append(" Reason: ").append(reason);
		}
		return msg.toString();
	}
}
