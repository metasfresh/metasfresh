/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
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


import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import javax.swing.event.ListSelectionEvent;

import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.InfoSimple;
import org.compiere.apps.search.PAttributeInstance;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.Query;
import org.compiere.swing.CButton;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;

public final class InfoProduct extends InfoSimple implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7126314980863211833L;

	private final String SYSCONFIG_LOAD_ALL_RECORDS_INFOPRODUCT = "LOAD_ALL_RECORDS_INFOPRODUCT";
	private static final String ATTR_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
	InfoProductDetails panelDetails = null;
	/** Instance Button */
	private CButton m_PAttributeButton = null;

	public InfoProduct(final Frame frame, final Boolean modal)
	{
		super(frame, modal);
	}

	@Override
	protected void init(final boolean modal, final int WindowNo, final I_AD_InfoWindow infoWindow, final String keyColumn, final String value, final boolean multiSelection, final String whereClause)
	{
		final Integer M_PriceList_ID = (Integer)getCtxAttribute("M_PriceList_ID");
		findPLV(M_PriceList_ID == null ? Env.getContextAsInt(getCtx(), "M_PriceList_ID") : M_PriceList_ID);

		findWarehouse();

		super.init(modal, WindowNo, infoWindow, keyColumn, value, multiSelection, whereClause);

		if (Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_LOAD_ALL_RECORDS_INFOPRODUCT, false)) // metas: c.ghita@metas.ro
		{
			executeQueryOnInit(); // metas-2009_0021_AP1_CR046
		}
	}

	@Override
	public void initAddonPanel()
	{
		m_PAttributeButton = ConfirmPanel.createPAttributeButton(true);
		confirmPanel.addButton(m_PAttributeButton);
		m_PAttributeButton.addActionListener(this);
		m_PAttributeButton.setEnabled(false);
		//
		// add taskpane
		panelDetails = new InfoProductDetails(this);
		addonPanel.setLayout(new BorderLayout());
		addonPanel.add(panelDetails.getComponent(), BorderLayout.CENTER);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e)
	{
		super.valueChanged(e);
		if (e.getValueIsAdjusting())
		{
			return;
		}
		panelDetails.refresh();
	}

	@Override
	protected void customize()
	{
		log.info("");
	} // customize

	@Override
	protected boolean hasCustomize()
	{
		return false; // for now
	} // hasCustomize

	@Override
	protected void enableButtons()
	{
		setCtxAttribute(ATTR_M_AttributeSetInstance_ID, -1);
		if (m_PAttributeButton != null)
		{
			m_PAttributeButton.setEnabled(isInstanceAttribute());
		}
		super.enableButtons();
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// Query Product Attribute Instance
		final int row = p_table.getSelectedRow();
		if (e.getSource().equals(m_PAttributeButton) && row != -1)
		{
			final ProductId productId = ProductId.ofRepoIdOrNull(getM_Product_ID());
			if (productId == null)
			{
				return;
			}

			final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(getContextVariableAsInt("M_Warehouse_ID"));
			if (warehouseId == null)
			{
				return;
			}
			final int p_WindowNo = getWindowNo();
			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID"));
			final String productName = getProductName();
			final String title = productName;
			final PAttributeInstance pai = new PAttributeInstance(getWindow(), title,
					warehouseId,
					0, // locatorId
					productId,
					bpartnerId);
			final int M_AttributeSetInstance_ID = pai.getM_AttributeSetInstance_ID();
			setCtxAttribute(ATTR_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
			// m_M_Locator_ID = pai.getM_Locator_ID();
			if (M_AttributeSetInstance_ID != -1)
			{
				dispose(true);
			}
			return;
		}
		//
		super.actionPerformed(e);
	} // actionPerformed

	public int getM_Product_ID()
	{
		final Integer id = getSelectedRowKey();
		return id == null || id == 0 ? -1 : id;
	}

	public String getProductName()
	{
		final int row = p_table.getSelectedRow();
		if (row < 0)
		{
			return null;
		}

		final int col = getIndexByColumnName("ProductName");
		if (col < 0)
		{
			return null;
		}

		final Object value = p_table.getValueAt(row, col);
		return value == null ? null : value.toString();
	}

	public boolean isInstanceAttribute()
	{
		final int row = p_table.getSelectedRow();
		if (row < 0)
		{
			return false;
		}

		final int col = getIndexByColumnName(I_M_AttributeSet.COLUMNNAME_IsInstanceAttribute);
		if (col < 0)
		{
			return false;
		}

		final Object value = p_table.getValueAt(row, col);
		return Boolean.TRUE.equals(value);
	}

	/**
	 * Find Price List Version and update context
	 *
	 * @param M_PriceList_ID price list
	 * @return M_PriceList_Version_ID price list version
	 */
	// TODO keep in sync with org.adempiere.webui.panel.InfoProductPanel
	private int findPLV(final int M_PriceList_ID)
	{
		final Properties ctx = getCtx();

		Timestamp priceDate = null;
		// Sales Order Date
		final int p_WindowNo = getWindowNo();
		String dateStr = Env.getContext(ctx, p_WindowNo, "DateOrdered");
		if (dateStr != null && dateStr.length() > 0)
		{
			priceDate = Env.getContextAsDate(ctx, p_WindowNo,
					"DateOrdered");
		}
		else
		// Invoice Date
		{
			dateStr = Env.getContext(ctx, p_WindowNo, "DateInvoiced");
			if (!Check.isEmpty(dateStr))
			{
				priceDate = Env.getContextAsDate(ctx, p_WindowNo, "DateInvoiced");
			}
		}
		// Today
		if (priceDate == null)
		{
			priceDate = new Timestamp(System.currentTimeMillis());
		}
		//
		log.info("M_PriceList_ID=" + M_PriceList_ID + " - " + priceDate);
		int retValue = 0;
		final String sql = "SELECT plv.M_PriceList_Version_ID, plv.ValidFrom "
				+ "FROM M_PriceList pl, M_PriceList_Version plv "
				+ "WHERE pl.M_PriceList_ID=plv.M_PriceList_ID"
				+ " AND plv.IsActive='Y'" + " AND pl.M_PriceList_ID=? " // 1
				+ "ORDER BY plv.ValidFrom DESC";
		// find newest one
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_PriceList_ID);
			rs = pstmt.executeQuery();
			while (rs.next() && retValue == 0)
			{
				final Timestamp plDate = rs.getTimestamp(2);
				if (!priceDate.before(plDate))
				{
					retValue = rs.getInt(1);
				}
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		Env.setContext(ctx, p_WindowNo, "M_PriceList_Version_ID", retValue);
		return retValue;
	} // findPLV

	/**
	 * Find Warehouse and update context
	 *
	 * @return M_Warehousen_ID
	 */
	// TODO keep in sync with org.adempiere.webui.panel.InfoProductPanel
	private int findWarehouse()
	{
		final Properties ctx = getCtx();
		final int p_WindowNo = getWindowNo();

		int M_Warehouse_ID = Env.getContextAsInt(ctx, "M_Warehouse_ID");
		if (M_Warehouse_ID <= 0)
		{
			M_Warehouse_ID = new Query(ctx, org.compiere.model.I_M_Warehouse.Table_Name, "", ITrx.TRXNAME_None)
					.setClient_ID()
					.setRequiredAccess(Access.READ)
					.setLimit(QueryLimit.ONE)
					.setOrderBy(org.compiere.model.I_M_Warehouse.COLUMNNAME_Name)
					.firstId();
			if (M_Warehouse_ID > 0)
			{
				Env.setContext(ctx, p_WindowNo, "M_Warehouse_ID", M_Warehouse_ID);
			}

			return M_Warehouse_ID;
		}

		Env.setContext(ctx, p_WindowNo, "M_Warehouse_ID", M_Warehouse_ID);
		return M_Warehouse_ID;
	} // findPLV
} // InfoProduct
