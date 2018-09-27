package de.metas.fresh.picking.form.swing;

import java.beans.PropertyChangeEvent;

import org.compiere.apps.form.FormFrame;

import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.ITerminalScrollPane.ScrollPolicy;
import de.metas.adempiere.form.terminal.ITerminalTable;
import de.metas.adempiere.form.terminal.swing.TerminalSubPanel;
import de.metas.fresh.picking.form.FreshPackingMd;
import de.metas.picking.legacy.form.PackingMd;
import de.metas.picking.terminal.IPackingStateProvider;
import de.metas.picking.terminal.IPickingTerminalPanel;
import de.metas.picking.terminal.Utils.PackingStates;
import de.metas.picking.terminal.form.swing.SpecialTerminalTable;
import net.miginfocom.swing.MigLayout;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class SwingPickingOKPanel_LinesPanel extends TerminalSubPanel
{
	private final SwingPickingOKPanel pickingOKPanel;
	private final IConfirmPanel confirmPanel;

	private ITerminalTable lines;
	private ITerminalScrollPane scroll;

	public SwingPickingOKPanel_LinesPanel(
			final SwingPickingOKPanel pickingOKPanel,
			final SwingPickingTerminalPanel pickingTerminalPanel)
	{
		super(pickingTerminalPanel);
		this.pickingOKPanel = pickingOKPanel;
		this.confirmPanel = pickingOKPanel.getConfirmPanel();
	}

	private ITerminalTable createTerminalTable()
	{
		final SpecialTerminalTable lines = new SpecialTerminalTable(getTerminalContext());
		lines.setAutoResize(true);
		lines.setPackingStateProvider(PackingStateProvider.instance);
		return lines;
	}

	@Override
	protected void init()
	{
		lines = createTerminalTable();
		final FreshPackingMd model = this.pickingOKPanel.createPackingModel(lines);
		this.pickingOKPanel.setModel(model);

		initComponents();
		initLayout();
	}

	private void initComponents()
	{
		confirmPanel.addListener(this::onConfirmPanelAction);
	}

	private void initLayout()
	{
		scroll = getTerminalFactory().createScrollPane(lines);
		scroll.setHorizontalScrollBarPolicy(ScrollPolicy.WHEN_NEEDED);
		add(scroll, "dock north, spanx, growy, pushy, h 70%::");

		getUI().setLayout(new MigLayout("ins 0 0", "[fill][grow]", "[nogrid]unrel[||]"));
		//
		add(confirmPanel, "dock south");
	}

	private void onConfirmPanelAction(final PropertyChangeEvent evt)
	{
		if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
		{
			return;
		}

		final String action = String.valueOf(evt.getNewValue());
		if (IConfirmPanel.ACTION_OK.equals(action))
		{
			final IPickingTerminalPanel pickingTerminalPanel = pickingOKPanel.getPickingTerminalPanel();
			if (pickingTerminalPanel != null)
			{
				pickingTerminalPanel.createPackingDetails();
			}
		}
		else if (IConfirmPanel.ACTION_Cancel.equals(action))
		{
			final FormFrame framePicking = pickingOKPanel.getPickingFrame();
			framePicking.dispose();
		}
		else if (SwingPickingOKPanel.ACTION_Switch.equals(action))
		{
			final PackingMd model = pickingOKPanel.getModel();
			model.setGroupByWarehouseDest(!model.isGroupByWarehouseDest());
			lines = createTerminalTable();
			model.setMiniTable(lines);
			pickingOKPanel.configureMiniTable(model.getMiniTable());
			pickingOKPanel.refresh();
			//
			scroll.setViewport(lines);
		}
		else if (SwingPickingOKPanel.ACTION_Today.equals(action))
		{
			final PackingMd model = pickingOKPanel.getModel();
			final boolean displayTodayEntriesOnly = model.isDisplayTodayEntriesOnly();
			model.setDisplayTodayEntriesOnly(!displayTodayEntriesOnly);
			pickingOKPanel.refresh();
		}
	}

	private static final class PackingStateProvider implements IPackingStateProvider
	{
		public static final transient PackingStateProvider instance = new SwingPickingOKPanel_LinesPanel.PackingStateProvider();

		private PackingStateProvider()
		{
		}

		@Override
		public PackingStates getPackingState(final int row)
		{
			return null;
		}
	};
}
