package de.metas.adempiere.addon.impl;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.KeyStroke;

import org.compiere.apps.APanel;
import org.compiere.apps.AppsAction;
import org.compiere.swing.CToggleButton;

import de.metas.adempiere.addon.AppsActionAdapter;
import de.metas.adempiere.addon.IAddonService;

public class AddonService implements IAddonService {

	private final Map<AppsAction, AppsActionAdapter> toolBarActions = new HashMap<AppsAction, AppsActionAdapter>();

	// private final Map<APanel, Set<AppsAction>> parent2Action = new
	// HashMap<APanel, Set<AppsAction>>();

	private final Map<AppsAction, APanel> action2parent = new HashMap<AppsAction, APanel>();

	@Override
	public List<AppsAction> getToolBarActions() {

		//
		// need to return a copy, in case of multiple APanels
		final List<AppsAction> copies = new ArrayList<AppsAction>(
				toolBarActions.size());
		for (final AppsAction action : toolBarActions.keySet()) {

			final AppsAction copy = mkCopy(action);
			copies.add(copy);
		}
		return Collections.unmodifiableList(copies);
	}

	@Override
	public void registerToolBarAction(final AppsAction appsAction,
			final AppsActionAdapter adapter) {

		toolBarActions.put(appsAction, adapter);
	}

	@Override
	public void registerAPanelAsParent(final APanel parent,
			final AppsAction appsAction) {

		action2parent.put(appsAction, parent);
	}

	@Override
	public APanel getParentForAction(final AppsAction appsAction) {

		return action2parent.get(appsAction);
	}

	private AppsAction mkCopy(final AppsAction action) {

		final KeyStroke accelerator = (KeyStroke) action
				.getValue(Action.ACCELERATOR_KEY);
		final String toolTipText = (String) action
				.getValue(Action.SHORT_DESCRIPTION);

		final boolean toggle;
		final AbstractButton button = action.getButton();
		if (button instanceof CToggleButton) {
			toggle = true;
		} else {
			toggle = false;
		}

		final AppsAction actionCopy = new AppsAction(action.getName(), accelerator,
				toolTipText, toggle);
		final AppsActionAdapter adapter = toolBarActions
				.get(action);
		
		final AppsActionAdapter adapterCopy = new AppsActionAdapter();
		adapterCopy.setAppsAction(actionCopy);
		adapterCopy.setAppsActionListener(adapter.getAppsActionListener());
		
		actionCopy.setDelegate(adapterCopy);

		return actionCopy;
	}
}
