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

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.grid.CTableColumns2GridTabSynchronizer;
import org.compiere.grid.GridController;
import org.compiere.grid.VTable;
import org.compiere.model.GridTab;

import de.metas.util.Check;

/**
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/05731_Spaltenbreite_persitieren_%28103033707449%29
 */
public class AWindowSaveState
{
	public static void createAndAdd(final APanel panel, final JMenu menu)
	{
		final AWindowSaveState app = new AWindowSaveState(panel);
		final JMenuItem menuItem = app.getJMenuItem();
		menu.add(menuItem);
	}

	private final AWindowSaveStateModel model = new AWindowSaveStateModel();
	private final APanel parent;
	private final AppsAction action;

	public AWindowSaveState(final APanel parent)
	{
		super();

		Check.assumeNotNull(parent, "parent not null");
		this.parent = parent;

		this.action = AppsAction.builder()
				.setAction(model.getActionName())
				.build();

		action.setDelegate(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				save();
			}
		});

		checkEnabled();
	}

	public JMenuItem getJMenuItem()
	{
		return action.getMenuItem();
	}

	private boolean checkEnabled()
	{
		final boolean enabled = model.isEnabled();

		action.setEnabled(enabled);

		final JMenuItem menuItem = action.getMenuItem();
		menuItem.setEnabled(enabled);
		menuItem.setVisible(enabled);

		return enabled;
	}

	private void assertEnabled()
	{
		if (!checkEnabled())
		{
			throw new AdempiereException("Action is not enabled");
		}
	}

	private void save()
	{
		assertEnabled();

		final GridController gc = parent.getCurrentGridController();
		save(gc);

		for (final GridController child : gc.getChildGridControllers())
		{
			save(child);
		}
	}

	private void save(final GridController gc)
	{
		Check.assumeNotNull(gc, "gc not null");

		final GridTab gridTab = gc.getMTab();
		final VTable table = gc.getVTable();

		//
		// Check CTable to GridTab synchronizer
		final CTableColumns2GridTabSynchronizer synchronizer = CTableColumns2GridTabSynchronizer.get(table);
		if (synchronizer == null)
		{
			// synchronizer does not exist, nothing to save
			return;
		}

		//
		// Make sure we have the latest values in GridTab
		synchronizer.saveToGridTab();

		//
		// Ask model to settings to database
		model.save(gridTab);
	}
}
