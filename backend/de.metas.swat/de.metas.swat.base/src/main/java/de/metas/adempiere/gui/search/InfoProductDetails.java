package de.metas.adempiere.gui.search;

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


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.jdesktop.swingx.JXTaskPane;

import de.metas.i18n.IMsgBL;

public class InfoProductDetails
{
	private IInfoSimple parent;

	private List<InfoProductDetailTab> detailTabs;
	private InfoProductStock panelWarehouse = null;

	// UI
	private CTabbedPane tabbedPane;
	private JXTaskPane warehouseStockPanel = null;

	public InfoProductDetails(IInfoSimple parent)
	{
		super();
		this.parent = parent;
		initUI();
	}

	private void initUI()
	{
		panelWarehouse = new InfoProductStock();
		final InfoProductSubstitute panelSubstitute = new InfoProductSubstitute();
		final InfoProductRelated panelRelated = new InfoProductRelated();
		final InfoProductATP panelATP = new InfoProductATP();

		detailTabs = Arrays.asList(
				new InfoProductDetailTab("Warehouse", panelWarehouse, panelWarehouse.getComponent(InfoProductStock.PANELTYPE_Stock)),
				new InfoProductDetailTab("Description", panelWarehouse, panelWarehouse.getComponent(InfoProductStock.PANELTYPE_Description)),
				new InfoProductDetailTab("Substitute_ID", panelSubstitute, panelSubstitute.getComponent()),
				new InfoProductDetailTab("RelatedProduct_ID", panelRelated, panelRelated.getComponent()),
				new InfoProductDetailTab("ATP", panelATP, panelATP.getComponent())
				);

		tabbedPane = new CTabbedPane();
		for (final InfoProductDetailTab detailTab : detailTabs)
		{
			tabbedPane.addTab(detailTab.getTitleTrl(), detailTab.getComponent());
		}
		tabbedPane.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				final InfoProductDetailTab detailTab = getSelectedTab();
				if (detailTab == null)
				{
					return;
				}

				detailTab.refreshIfStale();
			}
		});

		warehouseStockPanel = new JXTaskPane();
		warehouseStockPanel.setTitle(Services.get(IMsgBL.class).translate(Env.getCtx(), "WarehouseStock"));
		warehouseStockPanel.setCollapsed(true);
		warehouseStockPanel.setLayout(new MigLayout(new LC().fill()));
		warehouseStockPanel.add(tabbedPane, new CC()
				.width("100%")
				.minHeight("110px")
				.maxHeight("150px"));
	}

	private InfoProductDetailTab getSelectedTab()
	{
		int index = tabbedPane.getSelectedIndex();
		if ((index == -1) || (null == detailTabs))
		{
			return null;
		}
		return detailTabs.get(index);
	}

	private int getM_Product_ID()
	{
		final Integer id = parent.getSelectedRowKey();
		return id == null ? -1 : id;
	}

	private int getM_Warehouse_ID()
	{
		int M_Warehouse_ID = panelWarehouse.getM_Warehouse_ID();
		if (M_Warehouse_ID > 0)
		{
			return M_Warehouse_ID;
		}

		return parent.getContextVariableAsInt("M_Warehouse_ID");
	}

	private int getM_PriceList_Version_ID()
	{
		return parent.getContextVariableAsInt("M_PriceList_Version_ID");
	}

	private int getM_AttributeSetInstance_ID()
	{
		return parent.getContextVariableAsInt("M_AttributeSetInstance_ID");
	}

	public void refresh()
	{
		final int productID = getM_Product_ID(); // get the M_Product_ID for which we do the refresh
		
		final Timer timer = new Timer(500, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (parent.isDisposed())
				{
					return;
				}
				if (productID != getM_Product_ID())
				{
					// task 08020_ we already skipped to another product, no use getting the data for the former one
					return;
				}
				
				final InfoProductDetailTab selectedTab = getSelectedTab();
				if (selectedTab != null)
				{
					selectedTab.refresh();
				}

				setCollapsed(false);
			}
		});

		timer.setRepeats(false); // task 08020: this is updatant: only rune once!
		timer.restart();
	} // refresh

	private void setCollapsed(boolean collapsed)
	{
		warehouseStockPanel.setCollapsed(collapsed);
	}

	public java.awt.Component getComponent()
	{
		return warehouseStockPanel;
	}

	/**
	 * Wrapper class for each of the detail tabs.
	 * 
	 * @author ad
	 *
	 */
	private class InfoProductDetailTab
	{
		private final IInfoProductDetail detail;
		private final Component component;
		private final String titleTrl;
		private Object refreshKeyLast;

		public InfoProductDetailTab(String title, final IInfoProductDetail detail, final Component component)
		{
			super();
			this.titleTrl = Services.get(IMsgBL.class).translate(Env.getCtx(), title);
			this.detail = detail;
			
			if(component instanceof JTable)
			{
				// Wrap the JTable in a scroll pane, else the table header won't be visible
				// see http://stackoverflow.com/questions/2320812/jtable-wont-show-column-headers
				this.component = new CScrollPane(component);
			}
			else
			{
				this.component = component;
			}
		}

		public String getTitleTrl()
		{
			return titleTrl;
		}

		public Component getComponent()
		{
			return component;
		}

		/**
		 * Refresh (always)
		 */
		public void refresh()
		{
			final int M_Product_ID = getM_Product_ID();
			final int M_Warehouse_ID = getM_Warehouse_ID();
			final int M_PriceList_Version_ID = getM_PriceList_Version_ID();
			final int M_AttributeSetInstance_ID = getM_AttributeSetInstance_ID();
			final boolean onlyIfStale = false;
			refresh(onlyIfStale, M_Product_ID, M_Warehouse_ID, M_AttributeSetInstance_ID, M_PriceList_Version_ID);
		}

		/**
		 * Refresh only if stale
		 */
		private void refreshIfStale()
		{
			final int M_Product_ID = getM_Product_ID();
			final int M_Warehouse_ID = getM_Warehouse_ID();
			final int M_PriceList_Version_ID = getM_PriceList_Version_ID();
			final int M_AttributeSetInstance_ID = getM_AttributeSetInstance_ID();
			final boolean onlyIfStale = true;
			refresh(onlyIfStale, M_Product_ID, M_Warehouse_ID, M_AttributeSetInstance_ID, M_PriceList_Version_ID);
		}

		private void refresh(boolean onlyIfStale, int M_Product_ID, int M_Warehouse_ID, int M_AttributeSetInstance_ID, int M_PriceList_Version_ID)
		{
			final ArrayKey refreshKey = Util.mkKey(M_Product_ID, M_Warehouse_ID, M_AttributeSetInstance_ID, M_PriceList_Version_ID);

			if (!onlyIfStale || !isStale(refreshKey))
			{
				detail.refresh(M_Product_ID, M_Warehouse_ID, M_AttributeSetInstance_ID, M_PriceList_Version_ID);
			}

			this.refreshKeyLast = refreshKey;
		}

		private boolean isStale(final ArrayKey refreshKey)
		{
			return !Check.equals(refreshKey, refreshKeyLast);
		}
	}
}
