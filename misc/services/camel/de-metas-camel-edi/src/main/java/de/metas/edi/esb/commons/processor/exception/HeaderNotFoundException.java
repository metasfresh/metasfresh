/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.commons.processor.exception;

import org.apache.camel.CamelException;

/**
 * Exception thrown by processors. Log or throw it, as needed.
 * 
 * @author al
 */
public class HeaderNotFoundException extends CamelException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2778293243952804662L;

	public HeaderNotFoundException()
	{
		super();
	}

	public HeaderNotFoundException(final String message)
	{
		super(message);
	}

	public HeaderNotFoundException(final Throwable cause)
	{
		super(cause);
	}

	public HeaderNotFoundException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
