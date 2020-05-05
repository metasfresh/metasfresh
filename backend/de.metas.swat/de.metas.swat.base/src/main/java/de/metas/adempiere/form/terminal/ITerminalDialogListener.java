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


public interface ITerminalDialogListener
{
	/**
	 * Event fired when user pressed OK, but before window was closed
	 *
	 * @param dialog
	 * @throws TerminalDialogCancelClosingException if dialog closing shall be cancelled
	 */
	void onDialogOk(ITerminalDialog dialog);

	/**
	 * Event fired when user pressed Cancel, but before window was closed
	 *
	 * @param dialog
	 * @return true if cancel shall be performed; false if cancelling shall be stopped.
	 * @throws TerminalDialogCancelClosingException if dialog closing shall be cancelled; same as returning <code>false</code>
	 */
	boolean onDialogCanceling(ITerminalDialog dialog);

	/**
	 * Event fired after the window has opened
	 *
	 * @param dialog
	 */
	void onDialogOpened(ITerminalDialog dialog);

	/**
	 * Event fired after the window is activated
	 *
	 * @param dialog
	 */
	void onDialogActivated(ITerminalDialog dialog);

	/**
	 * Event fired after the window is deactivated
	 *
	 * @param dialog
	 */
	void onDialogDeactivated(ITerminalDialog dialog);

	/**
	 * Event fired when the window is closing
	 *
	 * @param dialog
	 */
	void onDialogClosing(ITerminalDialog dialog);

	/**
	 * Event fired after the window has closed
	 *
	 * @param dialog
	 */
	void onDialogClosed(ITerminalDialog dialog);
}
