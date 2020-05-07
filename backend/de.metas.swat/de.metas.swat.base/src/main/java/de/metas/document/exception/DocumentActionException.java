package de.metas.document.exception;

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


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;

/**
 * Handles document action related exceptions
 * 
 * @author RC
 * 
 */
public class DocumentActionException extends AdempiereException
{

	private static final long serialVersionUID = 843949211914720900L;

	/**
	 * 
	 */
	
	public DocumentActionException(String message) {
		super(message);
	}

	public DocumentActionException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
	
	public DocumentActionException(Properties ctx, String message, Object[] params)
	{
		this(Env.getAD_Language(ctx), message, params);
	}

	public DocumentActionException(String language, String message, Object[] params)
	{
		super(Services.get(IMsgBL.class).getMsg(language, message, params));
	}

	public DocumentActionException(final Throwable cause)
	{
		super(cause);
	}
}
