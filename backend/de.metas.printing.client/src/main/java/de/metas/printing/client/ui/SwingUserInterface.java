package de.metas.printing.client.ui;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class SwingUserInterface implements IUserInterface
{
	private final transient Logger log = Logger.getLogger(getClass().getName());

	private final Component parent;

	public SwingUserInterface(final Component parent)
	{
		if (parent == null)
		{
			throw new IllegalArgumentException("parent is null");
		}
		this.parent = parent;
	}

	@Override
	public void showError(final String title, final Throwable ex)
	{
		final String message = buildErrorMessage(ex);
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);

		if (log.isLoggable(Level.FINE))
		{
			log.log(Level.FINE, title + " - " + message, ex);
		}
	}

	private String buildErrorMessage(final Throwable ex)
	{
		final StringBuilder msg = new StringBuilder();
		if (ex.getLocalizedMessage() != null)
		{
			msg.append(ex.getLocalizedMessage());
		}

		final Throwable cause = ex.getCause();
		if (cause != null)
		{
			if (msg.length() > 0)
			{
				msg.append(": ");
			}

			msg.append(cause.toString());
		}

		return msg.toString();
	}
}
