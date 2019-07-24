/**
 * 
 */
package de.metas.adempiere.form.swing;

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


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.I_RV_C_OrderLine_Overview;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.GridController;
import org.compiere.grid.VTable;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.model.MTable;
import org.compiere.model.MWindow;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CollapsiblePanel;
import org.compiere.util.Env;

import net.miginfocom.swing.MigLayout;

/**
 * @author tsa
 * 
 */
public class OrderOverview implements FormPanel
{
	private int windowNo;
	private FormFrame frame;
	private CPanel panel;
	private CScrollPane scrollPanel;

	private List<GridTab> tabs = null;

	private class RowChangedListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (evt.getSource() instanceof GridTab && evt.getPropertyName().equals(GridTab.PROPERTY))
			{
				GridTab tab = (GridTab)evt.getSource();
				for (GridTab t : getChildren(tab))
				{
					t.query(false);
				}
				vtableAutoSizeAll();
			}
		}
	}

	private static class TabNoComparator implements Comparator<GridTab>
	{
		@Override
		public int compare(GridTab o1, GridTab o2)
		{
			if (o1 == null)
				return +1;
			if (o2 == null)
				return -1;
			return o1.getTabNo() - o2.getTabNo();
		}
	}

	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		this.windowNo = WindowNo;
		this.frame = frame;

		this.panel = new CPanel();
		scrollPanel = new CScrollPane(panel);
		this.frame.getContentPane().add(this.scrollPanel, BorderLayout.CENTER);
		
		initComponents();
		initLayout();
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}

	private void initComponents()
	{
		final int AD_Window_ID = MTable.get(getCtx(), I_RV_C_OrderLine_Overview.Table_Name).getAD_Window_ID();

		this.tabs = getGridTabs(getCtx(), windowNo, AD_Window_ID);
		Collections.sort(this.tabs, new TabNoComparator());

		RowChangedListener rowChangedListener = new RowChangedListener();
		for (GridTab t : this.tabs)
		{
			t.addPropertyChangeListener(rowChangedListener);
		}
	}

	private void initLayout()
	{
		this.panel.setLayout(new MigLayout());
		
		for (int i = 0; i < tabs.size(); i++)
		{
			GridTab gridTab = tabs.get(i);
			int width = 1000;
			int height = (i == 0 ? 310 : 200);
			GridController gc = createGridController(gridTab, width, height);
			CollapsiblePanel pane = new CollapsiblePanel(gridTab.getName());
			pane.add(gc);
			this.panel.add(pane, "growx, pushx, wrap");
		}
		
		vtableAutoSizeAll();
	}

	private static List<GridTab> getGridTabs(Properties ctx, int windowNo, int AD_Window_ID)
	{
		final GridWindowVO wVO = GridWindowVO.create(ctx, windowNo, AD_Window_ID);
		if (wVO == null)
		{
			MWindow w = new MWindow(Env.getCtx(), AD_Window_ID, null);
			throw new AdempiereException("No access to window - " + w.getName() + " (AD_Window_ID=" + AD_Window_ID + ")");
		}
		List<GridTab> list = new ArrayList<GridTab>();
		GridWindow m_mWindow = new GridWindow(wVO);
		for (int tabIndex = 0; tabIndex < m_mWindow.getTabCount(); tabIndex++)
		{
			GridTab t = m_mWindow.getTab(tabIndex);
			m_mWindow.initTab(tabIndex);

			// TODO: Export TabLevel in context. If we don't do this, the GridTab.getParentTabNo() won't work
			// On normal window, this is done in APanel class but i think we should move it to GridWindow.initTab
			Env.setContext(ctx, windowNo, t.getTabNo(), GridTab.CTX_TabLevel, Integer.toString(t.getTabLevel()));

			t.query(false);
			list.add(t);
		}
		return list;
	}

	private List<GridTab> getChildren(GridTab tab)
	{
		List<GridTab> list = tabChildrenMap.get(tab.getAD_Tab_ID());
		if (list != null)
			return list;

		list = new ArrayList<GridTab>();
		tabChildrenMap.put(tab.getAD_Tab_ID(), list);
		for (GridTab t : tabs)
		{
			if (t.getParentTabNo() == tab.getTabNo() && t.getTabNo() != tab.getTabNo())
			{
				list.add(t);
			}
		}
		return list;
	}

	/** AD_Tab_ID(parent) -> list of GridTab (children) */
	private Map<Integer, List<GridTab>> tabChildrenMap = new HashMap<Integer, List<GridTab>>();

	private GridController createGridController(GridTab tab, int width, int height)
	{
		final GridController gc = new GridController();
		gc.initGrid(tab,
				true, // onlyMultiRow
				windowNo,
				null, // APanel
				null); // GridWindow
		if (width > 0 && height > 0)
			gc.setPreferredSize(new Dimension(width, height));
		// tab.addPropertyChangeListener(this);
		m_mapVTables.put(tab.getAD_Tab_ID(), gc.getTable());
		return gc;
	}

	private void vtableAutoSizeAll()
	{
		for (VTable t : m_mapVTables.values())
		{
			t.autoSize(true);
		}
	}

	/** Map AD_Tab_ID -> VTable */
	private Map<Integer, VTable> m_mapVTables = new HashMap<Integer, VTable>();

	@Override
	public void dispose()
	{
		if (frame != null)
			frame.dispose();
		frame = null;
	}
}
