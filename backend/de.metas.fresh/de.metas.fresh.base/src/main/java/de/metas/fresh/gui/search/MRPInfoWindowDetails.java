package de.metas.fresh.gui.search;

/*
 * #%L
 * de.metas.fresh.base
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


import java.awt.Container;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.swing.CTabbedPane;
import org.jdesktop.swingx.JXTaskPane;

import de.metas.i18n.IMsgBL;

/**
 * NOTE: consider moving this to either de.metas.handlingunits or org.adempiere.libero if and when one starts to depend on the other.
 */
public class MRPInfoWindowDetails
{
	// Services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String MSG_BREAKDOWN = "de.metas.handlingunits.breakdown";
	private static final String MSG_DIM_ATTRIBUTES = "de.metas.dimension.fresh.attributes";

	private static final String SYSCONFIG_UnCollapseOnProductSelected = "de.metas.customer.gui.search.MRPInfoWindowDetails.UnCollapseOnProductSelected";
	private static final boolean DEFAULT_UnCollapseOnProductSelected = false; // don't uncollpase by default (task 08915)

	//
	// Parameters
	private final IInfoSimple parent;
	private final boolean uncollapseOnProductSelected;

	//
	// UI
	private JXTaskPane warehouseStockPanel = null;
	// task 08681
	private MRPInfoWindowAttribGroups panelAttributeValues = null;

	public MRPInfoWindowDetails(final IInfoSimple parent)
	{
		super();

		Check.assumeNotNull(parent, "parent not null");
		this.parent = parent;

		this.uncollapseOnProductSelected = sysConfigBL.getBooleanValue(SYSCONFIG_UnCollapseOnProductSelected, DEFAULT_UnCollapseOnProductSelected);

		initUI();
		init();
	}

	private void initUI()
	{
		final Properties ctx = parent.getCtx();

		//
		// Left side panel
		final CTabbedPane attributeValueTabbedPane = new CTabbedPane();
		{
			panelAttributeValues = new MRPInfoWindowAttribGroups(ctx, parent.getWindowNo());
			attributeValueTabbedPane.addTab(msgBL.getMsg(ctx, MSG_DIM_ATTRIBUTES), new JScrollPane(panelAttributeValues.getComponent()));
		}

		//
		// Right side panel
		final CTabbedPane detailTabbedPane = new CTabbedPane();
		{
			final MRPInfoWindowAttribs panelWarehouse = new MRPInfoWindowAttribs(ctx, parent.getWindowNo());
			panelAttributeValues.setPanelWarehouse(panelWarehouse);
			detailTabbedPane.addTab(msgBL.getMsg(ctx, MSG_BREAKDOWN), new JScrollPane(panelWarehouse.getComponent()));
		}

		//
		// Main panel
		{
			warehouseStockPanel = new JXTaskPane();

			warehouseStockPanel.setTitle(msgBL.translate(ctx, "WarehouseStock"));
			warehouseStockPanel.setAnimated(false);
			warehouseStockPanel.setCollapsed(true); // initially collapsed

			final Container warehouseStockPanelContent = warehouseStockPanel.getContentPane();

			// FIXME: dirty workaround, to get rid of inner margin of the JX Task pane
			// see http://stackoverflow.com/questions/5647522/java-swing-jxtaskpane-how-to-set-bg-and-border
			((JComponent)warehouseStockPanelContent).setBorder(BorderFactory.createEmptyBorder());

			warehouseStockPanelContent.setLayout(new MigLayout(
					"" // layoutConstraints
					, "[fill][fill]" // column constraints
			));
			warehouseStockPanelContent.add(attributeValueTabbedPane, "w 300px:60%:, h 300px, grow");
			warehouseStockPanelContent.add(detailTabbedPane, "w 300px:40%:, h 300px, wrap");

			warehouseStockPanel.revalidate();
		}
	}

	private void init()
	{
		// Nothing more to do.
	}

	public int getM_Product_ID()
	{
		final Integer id = parent.getSelectedRowKey();
		return id == null || id <= 0 ? -1 : id;
	}

	public void refresh()
	{
		final int productId = getM_Product_ID();

		// panelWarehouse.refresh(productId); // called by panelAttributeValues
		panelAttributeValues.refresh(productId);

		//
		// Un-collpase panel only if configured
		// NOTE: in the past there were task which asked to not change the collapsed status (08328, 08915),
		// and tasks which asked to un-collpase the panel (08681)
		if (uncollapseOnProductSelected)
		{
			setCollapsed(false);
		}
	} // refresh

	private void setCollapsed(final boolean collapsed)
	{
		warehouseStockPanel.setCollapsed(collapsed);
	}

	public java.awt.Component getComponent()
	{
		return warehouseStockPanel;
	}
}
