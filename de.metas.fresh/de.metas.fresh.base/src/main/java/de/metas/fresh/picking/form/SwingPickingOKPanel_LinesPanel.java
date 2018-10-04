package de.metas.fresh.picking.form;

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

final class SwingPickingOKPanel_LinesPanel //extends TerminalSubPanel
{
////	private final SwingPickingOKPanel pickingOKPanel;
////	private final IConfirmPanel confirmPanel;
//
////	private SpecialTerminalTable lines;
////	private ITerminalScrollPane linesContainer;
//
//	public SwingPickingOKPanel_LinesPanel(final SwingPickingOKPanel pickingOKPanel)
//	{
//		super(pickingOKPanel.getPickingTerminalPanel());
////		this.pickingOKPanel = pickingOKPanel;
////		this.confirmPanel = pickingOKPanel.getConfirmPanel();
//	}
//
//	private SpecialTerminalTable createTerminalTable()
//	{
//		final SpecialTerminalTable lines = new SpecialTerminalTable(getTerminalContext());
//		lines.setAutoResize(true);
//		lines.setPackingStateProvider(PackingStateProvider.instance);
//		return lines;
//	}
//
//	@Override
//	protected void init()
//	{
//		lines = createTerminalTable();
//		final FreshPackingMd model = this.pickingOKPanel.createPackingModel(lines);
//		this.pickingOKPanel.setModel(model);
//
//		initComponents();
//		initLayout();
//	}
//
//	private void initComponents()
//	{
//		confirmPanel.addListener(this::onConfirmPanelAction);
//	}
//
//	private void initLayout()
//	{
//		linesContainer = getTerminalFactory().createScrollPane(lines);
//		linesContainer.setHorizontalScrollBarPolicy(ScrollPolicy.WHEN_NEEDED);
//		add(linesContainer, "dock north, spanx, growy, pushy, h 70%::");
//
//		getUI().setLayout(new MigLayout("ins 0 0", "[fill][grow]", "[nogrid]unrel[||]"));
//		//
//		add(confirmPanel, "dock south");
//	}
//
//	private void onConfirmPanelAction(final PropertyChangeEvent evt)
//	{
//		if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
//		{
//			return;
//		}
//
//		final String action = String.valueOf(evt.getNewValue());
//		if (IConfirmPanel.ACTION_OK.equals(action))
//		{
//			final SwingPickingTerminalPanel pickingTerminalPanel = pickingOKPanel.getPickingTerminalPanel();
//			if (pickingTerminalPanel != null)
//			{
//				pickingTerminalPanel.packSelectedLines();
//			}
//		}
//		else 
//			if (IConfirmPanel.ACTION_Cancel.equals(action))
//		{
//			final FormFrame framePicking = pickingOKPanel.getPickingFrame();
//			framePicking.dispose();
//		}
//		else if (SwingPickingOKPanel.ACTION_Switch.equals(action))
//		{
//			pickingOKPanel.toggleGroupByWarehouseDest();
//
//			lines = createTerminalTable();
//			pickingOKPanel.setLines(lines);
//
//			linesContainer.setViewport(lines);
//		}
//		else if (SwingPickingOKPanel.ACTION_Today.equals(action))
//		{
//			final FreshPackingMd model = pickingOKPanel.getModel();
//			final boolean displayTodayEntriesOnly = model.isDisplayTodayEntriesOnly();
//			model.setDisplayTodayEntriesOnly(!displayTodayEntriesOnly);
//			pickingOKPanel.refresh();
//		}
//	}
}
