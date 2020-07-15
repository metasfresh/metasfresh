package de.metas.handlingunits.client.terminal.mmovement.exception;

/*
 * #%L
 * de.metas.handlingunits.client
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


import de.metas.adempiere.form.terminal.TerminalException;

/**
 *
 * NOTE: if you throw this exception, it will be catched by API and a nice error message will be displayed to user. Also, the panel won't be closed but so user can correct his/her mistakes.
 */
public class MaterialMovementException extends TerminalException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -452946492943518818L;

	public MaterialMovementException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public MaterialMovementException(final String message)
	{
		super(message);
	}

	public MaterialMovementException(final Throwable cause)
	{
		super(cause);
	}
}
