package de.metas.ui.web.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
 * Exception thrown when some feature is not implemented yet.
 *
 * NOTE:
 * <ul>
 * <li>this exceptions binds to HTTP 501
 * <li>usually "A 501 response is cacheable by default; i.e., unless otherwise indicated by the method definition or explicit cache controls", see https://tools.ietf.org/html/rfc7231#section-6.6.2
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends AdempiereException
{
	public NotImplementedException()
	{
		super();
	}

	public NotImplementedException(final String message)
	{
		super(message);
	}

}
