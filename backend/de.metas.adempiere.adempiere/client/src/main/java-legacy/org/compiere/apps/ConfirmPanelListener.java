package org.compiere.apps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

/**
 * Convenient {@link ActionListener} implementation to listen for the confirm panel's various buttons.
 *
 * @author ts
 *
 */
public abstract class ConfirmPanelListener implements ActionListener
{
	@Override
	public final void actionPerformed(final ActionEvent e)
	{
		final String action = e.getActionCommand();
		if (action == null || action.length() == 0)
		{
			return;
		}
		else if (action.equals(ConfirmPanel.A_CANCEL))
		{
			cancelButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_OK))
		{
			okButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_REFRESH))
		{
			refreshButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_RESET))
		{
			resetButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_CUSTOMIZE))
		{
			customizeButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_HISTORY))
		{
			historyButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_ZOOM))
		{
			zoomButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_PROCESS))
		{
			processButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_PRINT))
		{
			printButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_EXPORT))
		{
			exportButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_HELP))
		{
			helpButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_DELETE))
		{
			deleteButtonPressed(e);
		}
		else if (action.equals(ConfirmPanel.A_NEW))
		{
			newButtonPressed(e);
		}
	}

	public void okButtonPressed(final ActionEvent e)
	{
	}

	public void cancelButtonPressed(final ActionEvent e)
	{
	}

	public void refreshButtonPressed(final ActionEvent e)
	{
	}

	public void resetButtonPressed(final ActionEvent e)
	{
	}

	public void customizeButtonPressed(final ActionEvent e)
	{
	}

	public void historyButtonPressed(final ActionEvent e)
	{
	}

	public void zoomButtonPressed(final ActionEvent e)
	{
	}

	public void processButtonPressed(final ActionEvent e)
	{
	}

	public void printButtonPressed(final ActionEvent e)
	{
	}

	public void exportButtonPressed(final ActionEvent e)
	{
	}

	public void helpButtonPressed(final ActionEvent e)
	{
	}

	public void deleteButtonPressed(final ActionEvent e)
	{
	}

	public void pAttributeButtonPressed(final ActionEvent e)
	{
	}

	public void newButtonPressed(final ActionEvent e)
	{
	}
}