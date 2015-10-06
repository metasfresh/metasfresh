package de.metas.handlingunits.client.terminal.mmovement.view.impl;

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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.event.UIPropertyChangeListener;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.mmovement.model.distribute.impl.HUDistributeCUTUModel;

/**
 * Panel responsible for distributing VPIs (CUs) to TUs
 *
 * @author al
 */
public class HUDistributeCUTUPanel extends AbstractLTCUPanel<HUDistributeCUTUModel>
{
	/**
	 * Flags in PCS system-triggered refreshes.
	 */
	public static final String PROPERTY_RefreshSelectAllButton = "RefreshSelectAllButton";

	/**
	 * Select / deselect all TUs
	 */
	private ITerminalButton bSelectAllTUs;

	public HUDistributeCUTUPanel(final HUDistributeCUTUModel model)
	{
		super(model);
	}

	@Override
	protected final void initLayout(final ITerminalFactory terminalFactory)
	{
		// create CU-Lane
		addCULane(true); // useQtyField=true

		// create TU-Lane (with SelectAll button)
		final List<LayoutComponent> otherTUComponents = new ArrayList<>();
		{
			bSelectAllTUs = terminalFactory.createButton("-");

			final PropertyChangeListener selectAllProprtyChangeListener = new UIPropertyChangeListener(terminalFactory, bSelectAllTUs)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					if (!PROPERTY_RefreshSelectAllButton.equals(evt.getPropertyName()))
					{
						doSelectDeselectAll(); // only execute if it's not system-refresh trigger
					}
					reloadSelectAllButtonText(); // refresh button text
				}
			};
			bSelectAllTUs.addListener(selectAllProprtyChangeListener);

			// TODO button not working properly; not adding it to the panel for now

			//
			// Also add the listener to the TU-Key layout for key pressed events
			// model.getTUKeyLayout().addListener(selectAllProprtyChangeListener);

			//
			// final LayoutComponent bSelectAllTUsComponent = new LayoutComponent(bSelectAllTUs, "");
			// otherTUComponents.add(bSelectAllTUsComponent);
		}
		addTULane(false, otherTUComponents); // useQtyField=false
	}

	private final void doSelectDeselectAll()
	{
		final IKeyLayout tuKeyLayout = model.getTUKeyLayout();
		final IKeyLayoutSelectionModel keyLayoutSelectionModel = tuKeyLayout.getKeyLayoutSelectionModel();

		final boolean selectAll = isSelectAll(); // true if select, false if deselect

		keyLayoutSelectionModel.clearSelection(); // pre-clear selection
		if (!selectAll)
		{
			return; // selection cleared already; nothing left to do
		}

		for (final ITerminalKey key : tuKeyLayout.getKeys())
		{
			keyLayoutSelectionModel.setSelectedKey(key);
		}
	}

	private final void reloadSelectAllButtonText()
	{
		final boolean selectAll = isSelectAll();

		final String toggleAllButtonMsg = selectAll ? HUEditorPanel.ACTION_SelectAll : HUEditorPanel.ACTION_DeselectAll;
		bSelectAllTUs.setTextAndTranslate(toggleAllButtonMsg);
		bSelectAllTUs.setPressed(selectAll);
		bSelectAllTUs.setEnabled(true);
		bSelectAllTUs.setVisible(true);
	}

	private final boolean isSelectAll()
	{
		final IKeyLayout tuKeyLayout = model.getTUKeyLayout();
		final int tuKeyCount = tuKeyLayout.getKeysCount();
		final int selectedTUKeys = tuKeyLayout.getKeyLayoutSelectionModel().getSelectedKeys().size();

		final boolean selectAll = selectedTUKeys < tuKeyCount ? true : false;
		return selectAll;
	}

	@Override
	protected final void doOnDialogCanceling(final ITerminalDialog dialog)
	{
		// nothing for now
	}

	@Override
	public final void onDialogOpened(final ITerminalDialog dialog)
	{
		doSelectDeselectAll(); // initialize dialog with all TU keys selected
		reloadSelectAllButtonText();
	}
}
