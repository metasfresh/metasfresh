package de.metas.ui.web.menu.exception;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Exception thrown when requested node(s), indentified by ID or by query or by something else, were not found.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoMenuNodesFoundException extends AdempiereException
{
	public NoMenuNodesFoundException()
	{
		super();
	}

	public NoMenuNodesFoundException(final String message)
	{
		super(message);
	}

}
