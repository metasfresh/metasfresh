/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps.form;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.IStatusBar;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.Lookup;
import org.compiere.model.MQuery;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Base class for Material Transactions Info form (Warenbewegungen - Übersicht)
 */
public class TrxMaterial
{
	// services
	protected final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	
	/**	Window No			*/
	public int         	m_WindowNo = 0;
	/** MWindow                 */
	private GridWindow         m_mWindow = null;
	/** MTab pointer            */
	private GridTab            m_mTab = null;

	public MQuery          m_staticQuery = null;
	/**	Logger			*/
	public static CLogger log = CLogger.getCLogger(TrxMaterial.class);
	
	protected final int getWindowNo()
	{
		return m_WindowNo;
	}
	
	protected void dispose()
	{
		m_mTab = null;
		
		if (m_mWindow != null)
		{
			m_mWindow.dispose();
		}
		m_mWindow = null;
	}
	
	protected GridTab getGridTab()
	{
		if (m_mTab != null)
		{
			return m_mTab;
		}

		final int AD_Window_ID = 223; // Hardcoded: "Waren-Bewegungen (indirekte Verwendung)"
		final GridWindowVO wVO = AEnv.getMWindowVO (m_WindowNo, AD_Window_ID, 0);
		Check.assumeNotNull(wVO, "wVO not null"); // shall not happen

		m_mWindow = new GridWindow (wVO);
		wVO.Tabs.get(0).IsSearchActive = false;
		m_mTab = m_mWindow.getTab(0);
		m_mWindow.initTab(0);

		return m_mTab;
	}
	
	/**
	 *  Dynamic Layout (Grid).
	 * 	Based on AD_Window: Material Transactions
	 */
	public void dynInit(IStatusBar statusBar)
	{
		m_staticQuery = new MQuery();
		m_staticQuery.addRestriction("AD_Client_ID", MQuery.EQUAL, Env.getAD_Client_ID(Env.getCtx()));

		final GridTab gridTab = getGridTab();
		//
		gridTab.setQuery(MQuery.getEqualQuery("1", "2"));
		gridTab.query(false);
		statusBar.setStatusLine(" ", false);
		statusBar.setStatusDB(" ");
	}   //  dynInit
	
	/**************************************************************************
	 *  Refresh - Create Query and refresh grid
	 * @param bpartnerId 
	 */
	public void refresh(Object organization,
			Object locator,
			Object product,
			Object movementType, 
			Timestamp movementDateFrom, Timestamp movementDateTo,
			final Object bpartnerId,
			IStatusBar statusBar)
	{
		/**
		 *  Create Where Clause
		 */
		final MQuery query = m_staticQuery.deepCopy();
		//  Organization
		if (organization != null && organization.toString().length() > 0)
			query.addRestriction(I_M_Transaction.COLUMNNAME_AD_Org_ID, MQuery.EQUAL, organization);
		//  Locator
		if (locator != null && locator.toString().length() > 0)
			query.addRestriction(I_M_Transaction.COLUMNNAME_M_Locator_ID, MQuery.EQUAL, locator);
		//  Product
		if (product != null && product.toString().length() > 0)
			query.addRestriction(I_M_Transaction.COLUMNNAME_M_Product_ID, MQuery.EQUAL, product);
		//  MovementType
		if (movementType != null && movementType.toString().length() > 0)
			query.addRestriction(I_M_Transaction.COLUMNNAME_MovementType, MQuery.EQUAL, movementType);
		//  DateFrom
		if (movementDateFrom != null)
			query.addRestriction("TRUNC("+I_M_Transaction.COLUMNNAME_MovementDate+")", MQuery.GREATER_EQUAL, movementDateFrom);
		//  DateTO
		if (movementDateTo != null)
			query.addRestriction("TRUNC("+I_M_Transaction.COLUMNNAME_MovementDate+")", MQuery.LESS_EQUAL, movementDateTo);
		// C_BPartner_ID
		if (bpartnerId != null && bpartnerId.toString().length() > 0)
		{
			final String columnSql = getGridTab().getField(I_M_Transaction.COLUMNNAME_C_BPartner_ID).getColumnSQL(false);
			query.addRestriction(columnSql, MQuery.EQUAL, bpartnerId);
		}
		log.info("TrxMaterial.refresh query=" + query.toString());

		/**
		 *  Refresh/Requery
		 */
		statusBar.setStatusLine(msgBL.getMsg(Env.getCtx(), "StartSearch"), false);
		//
		m_mTab.setQuery(query);
		m_mTab.query(false);
		//
		final int no = m_mTab.getRowCount();
		statusBar.setStatusLine(" ", false);
		statusBar.setStatusDB(Integer.toString(no));
	}   //  refresh
	
	public int AD_Window_ID;
	public MQuery query;
	
	/**
	 *  Zoom
	 */
	public void zoom()
	{
		log.info("");
		//
		AD_Window_ID = 0;
		String ColumnName = null;
		String SQL = null;
		//
		int lineID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "M_InOutLine_ID");
		if (lineID > 0)
		{
			log.fine("M_InOutLine_ID=" + lineID);
			if (Env.getContext(Env.getCtx(), m_WindowNo, "MovementType").startsWith("C"))
				AD_Window_ID = 169;     //  Customer
			else
				AD_Window_ID = 184;     //  Vendor
			ColumnName = "M_InOut_ID";
			SQL = "SELECT M_InOut_ID FROM M_InOutLine WHERE M_InOutLine_ID=?";
		}
		else
		{
			lineID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "M_InventoryLine_ID");
			if (lineID > 0)
			{
				log.fine("M_InventoryLine_ID=" + lineID);
				AD_Window_ID = 168;
				ColumnName = "M_Inventory_ID";
				SQL = "SELECT M_Inventory_ID FROM M_InventoryLine WHERE M_InventoryLine_ID=?";
			}
			else
			{
				lineID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "M_MovementLine_ID");
				if (lineID > 0)
				{
					log.fine("M_MovementLine_ID=" + lineID);
					AD_Window_ID = 170;
					ColumnName = "M_Movement_ID";
					SQL = "SELECT M_Movement_ID FROM M_MovementLine WHERE M_MovementLine_ID=?";
				}
				else
				{
					lineID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "M_ProductionLine_ID");
					if (lineID > 0)
					{
						log.fine("M_ProductionLine_ID=" + lineID);
						AD_Window_ID = 191;
						ColumnName = "M_Production_ID";
						SQL = "SELECT M_Production_ID FROM M_ProductionLine WHERE M_ProductionLine_ID=?";
					}
					else
						log.fine("Not found WindowNo=" + m_WindowNo);
				}
			}
		}
		if (AD_Window_ID <= 0)
			return;

		//  Get Parent ID
		final int parentID = DB.getSQLValueEx(ITrx.TRXNAME_None, SQL, lineID);
		query = MQuery.getEqualQuery(ColumnName, parentID);
		log.config("AD_Window_ID=" + AD_Window_ID + " - " + query);
		if (parentID <= 0)
			log.log(Level.SEVERE, "No ParentValue - " + SQL + " - " + lineID);
	}   //  zoom
	
	protected final Lookup getLookup(final String columnName)
	{
		final GridTab gridTab = getGridTab();
		final GridField gridField = gridTab.getField(columnName);
		if (gridField == null)
		{
			return null;
		}
		return gridField.getLookup();
	}
}
