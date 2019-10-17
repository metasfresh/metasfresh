package de.metas.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;

public class TableColumnPathException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8007680258422505748L;

	private final String pathStr;

	private static final String buildMessage(String pathStr, String message)
	{
		StringBuffer sb = new StringBuffer();
		if (!Check.isEmpty(message))
			sb.append(message);
		else
			sb.append("Unknown error");

		if (!Check.isEmpty(pathStr))
		{
			sb.append(" (path: " + pathStr + ")");
		}
		return sb.toString();
	}

	public TableColumnPathException(String pathStr, String message)
	{
		super(buildMessage(pathStr, message));
		this.pathStr = pathStr;
	}

	public String getPathString()
	{
		return pathStr;
	}
}
