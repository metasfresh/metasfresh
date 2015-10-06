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
package org.adempiere.webui.window;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.component.Window;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Hbox;


/**
 *	Display Product Attribute Instance Info
 *
 *  @author     Jorg Janke
 *  @version    $Id: PAttributeInstance.java,v 1.3 2006/07/30 00:51:27 jjanke Exp $
 */
public class WPAttributeInstance extends Window implements EventListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4052029122256207113L;

	/**
	 * 	Constructor
	 * 	@param title title
	 * 	@param M_Warehouse_ID warehouse key name pair
	 * 	@param M_Locator_ID locator
	 * 	@param M_Product_ID product key name pair
	 * 	@param C_BPartner_ID bp
	 */
	public WPAttributeInstance(String title,
		int M_Warehouse_ID, int M_Locator_ID, int M_Product_ID, int C_BPartner_ID)
	{
		super ();
		this.setTitle(Msg.getMsg(Env.getCtx(), "PAttributeInstance") + title);
		this.setAttribute("modal", Boolean.TRUE);
		this.setBorder("normal");
		this.setWidth("500px");
		this.setHeight("550px");
		
		init (M_Warehouse_ID, M_Locator_ID, M_Product_ID, C_BPartner_ID);
		AEnv.showCenterScreen(this);
	}	//	PAttributeInstance
	
	/**
	 * 	Initialization
	 *	@param M_Warehouse_ID wh
	 *	@param M_Locator_ID loc
	 *	@param M_Product_ID product
	 *	@param C_BPartner_ID partner
	 */
	private void init (int M_Warehouse_ID, int M_Locator_ID, int M_Product_ID, int C_BPartner_ID)
	{
		log.info("M_Warehouse_ID=" + M_Warehouse_ID 
			+ ", M_Locator_ID=" + M_Locator_ID
			+ ", M_Product_ID=" + M_Product_ID);
		m_M_Warehouse_ID = M_Warehouse_ID;
		m_M_Locator_ID = M_Locator_ID;
		m_M_Product_ID = M_Product_ID;
		try
		{
			init();
			dynInit(C_BPartner_ID);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
	}	// init	

	private Borderlayout mainLayout = new Borderlayout();
	private Panel northPanel = new Panel();
	private ConfirmPanel confirmPanel = new ConfirmPanel (true);
	private Checkbox showAll = new Checkbox();
	//
	private WListbox 			m_table = new WListbox();
	//	Parameter
	private int			 		m_M_Warehouse_ID;
	private int			 		m_M_Locator_ID;
	private int			 		m_M_Product_ID;
	//
	private int					m_M_AttributeSetInstance_ID = -1;
	private String				m_M_AttributeSetInstanceName = null;
	private String				m_sql;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WPAttributeInstance.class);

	/**
	 * 	Static Init
	 * 	@throws Exception
	 */
	private void init() throws Exception
	{
		showAll.setLabel(Msg.getMsg(Env.getCtx(), "ShowAll"));
		
		this.appendChild(mainLayout);
		
		//	North
		Hbox box = new Hbox();
		box.setParent(northPanel);
		box.setPack("end");
		box.appendChild(showAll);
		showAll.addEventListener(Events.ON_CHECK, this);
		
		North north = new North();
		north.setParent(mainLayout);
		north.appendChild(northPanel);
		
		//	Center
		Center center = new Center();
		center.setParent(mainLayout);
		center.setFlex(true);
		center.appendChild(m_table);
		
		//	South
		South south = new South();
		south.setParent(mainLayout);
		south.appendChild(confirmPanel);
		confirmPanel.addActionListener(this);
	}	//	jbInit

	/**	Table Column Layout Info			*/
	private static ColumnInfo[] s_layout = new ColumnInfo[] 
	{
		new ColumnInfo(" ", "s.M_AttributeSetInstance_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Description"), "asi.Description", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Lot"), "asi.Lot", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "SerNo"), "asi.SerNo", String.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "GuaranteeDate"), "asi.GuaranteeDate", Timestamp.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "M_Locator_ID"), "l.Value", KeyNamePair.class, "s.M_Locator_ID"),
		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"), "s.QtyOnHand", Double.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyReserved"), "s.QtyReserved", Double.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOrdered"), "s.QtyOrdered", Double.class),
		//	See RV_Storage
		new ColumnInfo(Msg.translate(Env.getCtx(), "GoodForDays"), "(daysbetween(asi.GuaranteeDate, now()))-p.GuaranteeDaysMin", Integer.class, true, true, null),
		new ColumnInfo(Msg.translate(Env.getCtx(), "ShelfLifeDays"), "daysbetween(asi.GuaranteeDate, now())", Integer.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "ShelfLifeRemainingPct"), "CASE WHEN p.GuaranteeDays > 0 THEN TRUNC(((daysbetween(asi.GuaranteeDate, now()))/p.GuaranteeDays)*100) ELSE 0 END", Integer.class),
	};
	/**	From Clause							*/
	private static String s_sqlFrom = "M_Storage s"
		+ " INNER JOIN M_Locator l ON (s.M_Locator_ID=l.M_Locator_ID)"
		+ " INNER JOIN M_Product p ON (s.M_Product_ID=p.M_Product_ID)"
		+ " LEFT OUTER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID)";
	/** Where Clause						*/
	private static String s_sqlWhere = "s.M_Product_ID=? AND l.M_Warehouse_ID=?"; 
	private static String s_sqlWhereWithoutWarehouse = " s.M_Product_ID=?"; 

	private String	m_sqlNonZero = " AND (s.QtyOnHand<>0 OR s.QtyReserved<>0 OR s.QtyOrdered<>0)";
	private String	m_sqlMinLife = "";

	/**
	 * 	Dynamic Init
	 * 	@param C_BPartner_ID BP
	 */
	private void dynInit(int C_BPartner_ID)
	{
		log.config("C_BPartner_ID=" + C_BPartner_ID);
		if (C_BPartner_ID != 0)
		{
			int ShelfLifeMinPct = 0;
			int ShelfLifeMinDays = 0;
			String sql = "SELECT bp.ShelfLifeMinPct, bpp.ShelfLifeMinPct, bpp.ShelfLifeMinDays "
				+ "FROM C_BPartner bp "
				+ " LEFT OUTER JOIN C_BPartner_Product bpp"
				+	" ON (bp.C_BPartner_ID=bpp.C_BPartner_ID AND bpp.M_Product_ID=?) "
				+ "WHERE bp.C_BPartner_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, m_M_Product_ID);
				pstmt.setInt(2, C_BPartner_ID);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					ShelfLifeMinPct = rs.getInt(1);		//	BP
					int pct = rs.getInt(2);				//	BP_P
					if (pct > 0)	//	overwrite
						ShelfLifeMinDays = pct;
					ShelfLifeMinDays = rs.getInt(3);
				}
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
			}
			finally {
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
			if (ShelfLifeMinPct > 0)
			{
				m_sqlMinLife = " AND COALESCE(TRUNC(((daysbetween(asi.GuaranteeDate, now()))/p.GuaranteeDays)*100),0)>=" + ShelfLifeMinPct;
				log.config( "PAttributeInstance.dynInit - ShelfLifeMinPct=" + ShelfLifeMinPct);
			}
			if (ShelfLifeMinDays > 0)
			{
				m_sqlMinLife += " AND COALESCE((daysbetween(asi.GuaranteeDate, now())),0)>=" + ShelfLifeMinDays;
				log.config( "PAttributeInstance.dynInit - ShelfLifeMinDays=" + ShelfLifeMinDays);
			}
		}	//	BPartner != 0

		m_sql = m_table.prepareTable (s_layout, s_sqlFrom, 
					m_M_Warehouse_ID == 0 ? s_sqlWhereWithoutWarehouse : s_sqlWhere, false, "s")
				+ " ORDER BY asi.GuaranteeDate, s.QtyOnHand";	//	oldest, smallest first
		//
		m_table.addEventListener(Events.ON_SELECT, this);
		//
		refresh();
	}	//	dynInit

	/**
	 * 	Refresh Query
	 */
	private void refresh()
	{
		String sql = m_sql;
		int pos = m_sql.lastIndexOf(" ORDER BY ");
		if (!showAll.isChecked())
		{
			sql = m_sql.substring(0, pos) 
				+ m_sqlNonZero;
			if (m_sqlMinLife.length() > 0)
				sql += m_sqlMinLife;
			sql += m_sql.substring(pos);
		}
		//
		log.finest(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			if (m_M_Warehouse_ID != 0)
				pstmt.setInt(2, m_M_Warehouse_ID);
			rs = pstmt.executeQuery();
			m_table.loadTable(rs);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		enableButtons();
	}	//	refresh

	public void onEvent(Event e) throws Exception 
	{
		if (e.getTarget().getId().equals("Ok"))
			detach();
		else if (e.getTarget().getId().equals("Cancel"))
		{
			detach();
			m_M_AttributeSetInstance_ID = -1;
			m_M_AttributeSetInstanceName = null;
		}
		else if (e.getTarget() == showAll)
		{
			refresh();
		}
		else if (e.getTarget() == m_table)
		{
			enableButtons();
		}
	}	//	actionPerformed
 
	/**
	 * 	Enable/Set Buttons and set ID
	 */
	private void enableButtons()
	{
		m_M_AttributeSetInstance_ID = -1;
		m_M_AttributeSetInstanceName = null;
		m_M_Locator_ID = 0;
		int row = m_table.getSelectedRow();
		boolean enabled = row != -1;
		if (enabled)
		{
			Integer ID = m_table.getSelectedRowKey();
			if (ID != null)
			{
				m_M_AttributeSetInstance_ID = ID.intValue();
				m_M_AttributeSetInstanceName = (String)m_table.getValueAt(row, 1);
				//
				Object oo = m_table.getValueAt(row, 5);
				if (oo instanceof KeyNamePair)
				{
					KeyNamePair pp = (KeyNamePair)oo;
					m_M_Locator_ID = pp.getKey();
				}
			}
		}
		confirmPanel.getButton("Ok").setEnabled(enabled);
		log.fine("M_AttributeSetInstance_ID=" + m_M_AttributeSetInstance_ID 
			+ " - " + m_M_AttributeSetInstanceName
			+ "; M_Locator_ID=" + m_M_Locator_ID);
	}	//	enableButtons

	//TODO: double click support for WListbox
	/**
	 *  Mouse Clicked
	 *  @param e event
	 */
	/*
	public void mouseClicked(MouseEvent e)
	{
		//  Double click with selected row => exit
		if (e.getClickCount() > 1 && m_table.getSelectedRow() != -1)
		{
			enableButtons();
			dispose();
		}
	}*/   //  mouseClicked


	/**
	 * 	Get Attribute Set Instance
	 *	@return M_AttributeSetInstance_ID or -1
	 */
	public int getM_AttributeSetInstance_ID()
	{
		return m_M_AttributeSetInstance_ID;
	}	//	getM_AttributeSetInstance_ID

	/**
	 * 	Get Instance Name
	 * 	@return Instance Name
	 */
	public String getM_AttributeSetInstanceName()
	{
		return m_M_AttributeSetInstanceName;
	}	//	getM_AttributeSetInstanceName

	/**
	 * 	Get Locator
	 *	@return M_Locator_ID or 0
	 */
	public int getM_Locator_ID()
	{
		return m_M_Locator_ID;
	}	//	getM_Locator_ID

	

}	//	PAttributeInstance