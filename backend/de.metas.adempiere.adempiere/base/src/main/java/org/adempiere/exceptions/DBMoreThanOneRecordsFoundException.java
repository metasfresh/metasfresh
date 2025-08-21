/**
 * 
 */
package org.adempiere.exceptions;

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


import org.compiere.model.IQuery;

import java.io.Serial;

/**
 * Exception thrown by {@link IQuery} class when there were more records and only one was expected
 * 
 * @author Teo Sarca
 * 
 */
public class DBMoreThanOneRecordsFoundException extends DBException
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 3925120991619286612L;

	private static final String AD_Message = "QueryMoreThanOneRecordsFound";

	/**
	 * @param msg
	 */
	public DBMoreThanOneRecordsFoundException(String detailMessage)
	{
		super("@" + AD_Message + "@ (" + detailMessage + ")");
	}

}
