package de.metas.adempiere.form.terminal;

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

/**
 * Exception thrown from {@link ITerminalDialogListener} when the listener implementation wants to cancel the closing of {@link ITerminalDialog}.
 * 
 * @author tsa
 *
 */
public class TerminalDialogCancelClosingException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1902336897077702326L;

	public TerminalDialogCancelClosingException()
	{
		super("CancelDialogClosing");
	}
}
