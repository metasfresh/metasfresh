package org.adempiere.ad.trx.exceptions;

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


import org.adempiere.exceptions.AdempiereException;

import java.io.Serial;

/**
 * Exception thrown by transaction manager when something went wrong.
 * 
 * @author tsa
 * 
 */
public class TrxException extends AdempiereException
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -8558441605639046411L;

	// NOTE: this constructor is needed if we want to use it with Check methods
	public TrxException(final String msg)
	{
		super(msg);
	}
	
	public TrxException(final String msg, Throwable cause)
	{
		super(msg, cause);
	}

}
