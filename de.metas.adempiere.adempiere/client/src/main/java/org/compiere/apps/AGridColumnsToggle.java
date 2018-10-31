package org.compiere.apps;

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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import org.compiere.grid.GridController;
import org.compiere.grid.VTable;
import org.compiere.swing.CColumnControlButton;

import de.metas.util.Check;

/**
 * Toolbar action used to show/hide the grid columns in a standard window tab, grid mode view.
 * 
 * @author tsa
 *
 */
public class AGridColumnsToggle
{
	private static final String ACTION_Name = "ToggleGridColumns";

	private final APanel parent;
	private AppsAction action;

	/**
	 * 
	 * @param parent
	 * @param small if <code>true</code> then use a small icon
	 * @return
	 */
	public static AppsAction createAppsAction(final APanel parent, final boolean small)
	{
		AGridColumnsToggle app = new AGridColumnsToggle(parent, small);
		return app.action;
	}

	private AGridColumnsToggle(final APanel parent, final boolean small)
	{
		super();
		Check.assumeNotNull(parent, "parent not null");
		this.parent = parent;
		initAction(small);
	}

	private void initAction(final boolean small)
	{
		this.action = AppsAction.builder()
				.setAction(ACTION_Name)
				.setSmallSize(small)
				.build();
		action.setDelegate(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				showPopup();
			}
		});
	}

	private CColumnControlButton getCColumnControlButton()
	{
		final GridController currentGridController = this.parent.getCurrentGridController();
		if (currentGridController == null)
		{
			return null;
		}

		final VTable table = currentGridController.getVTable();
		if (table == null)
		{
			return null;
		}

		final CColumnControlButton columnControlButton = table.getColumnControl();
		return columnControlButton;
	}

	public void showPopup()
	{
		final CColumnControlButton columnControlButton = getCColumnControlButton();
		if (columnControlButton == null)
		{
			return;
		}

		final AbstractButton button = action.getButton();
		columnControlButton.togglePopup(button);
	}
}
