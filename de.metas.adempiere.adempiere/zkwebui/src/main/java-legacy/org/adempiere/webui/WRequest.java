/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
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

package org.adempiere.webui;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.ADWindow;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.MAsset;
import org.compiere.model.MBPartner;
import org.compiere.model.MCampaign;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.MQuery;
import org.compiere.model.MRMA;
import org.compiere.model.MRequest;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 *	Request Button Action.
 *	Popup Menu
 *	
 *  @author Jorg Janke
 *  @version $Id: ARequest.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1904928 ] Request: Related Request field not filled
 */
public class WRequest implements EventListener
{
	/**
	 * 	Constructor
	 *	@param invoker invoker button
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@param C_BPartner_ID optional bp
	 */
	public WRequest (Component invoker, int AD_Table_ID, int Record_ID, int C_BPartner_ID)
	{
		log.config("AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID);
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;
		m_C_BPartner_ID = C_BPartner_ID;
		getRequests(invoker);
		
	}	//	AReport

	/**	The Table						*/
	private int			m_AD_Table_ID;
	/** The Record						*/
	private int			m_Record_ID;
	/** BPartner						*/
	private int			m_C_BPartner_ID;
	
	/**	The Popup						*/
	private Menupopup 	m_popup = new Menupopup();
	private Menuitem 	m_new = null;
	private Menuitem 	m_active = null;
	private Menuitem 	m_all = null;
	/** Where Clause					*/
	StringBuffer 		m_where = null;
	
	/**	Logger	*/
	private static CLogger	log	= CLogger.getCLogger (WRequest.class);

	/**
	 * 	Display Request Options - New/Existing.
	 * 	@param invoker button
	 */
	private void getRequests (Component invoker)
	{
		m_new = new Menuitem(Msg.getMsg(Env.getCtx(), "RequestNew"));
		m_new.setImage("/images/New16.png");
		m_new.addEventListener(Events.ON_CLICK, this);
		m_popup.appendChild(m_new);
		//
		int activeCount = 0;
		int inactiveCount = 0;
		m_where = new StringBuffer();
		m_where.append("(AD_Table_ID=").append(m_AD_Table_ID)
			.append(" AND Record_ID=").append(m_Record_ID)
			.append(")");
		//
		if (m_AD_Table_ID == MUser.Table_ID)
			m_where.append(" OR AD_User_ID=").append(m_Record_ID)
				.append(" OR SalesRep_ID=").append(m_Record_ID);
		else if (m_AD_Table_ID == MBPartner.Table_ID)
			m_where.append(" OR C_BPartner_ID=").append(m_Record_ID);
		else if (m_AD_Table_ID == MOrder.Table_ID)
			m_where.append(" OR C_Order_ID=").append(m_Record_ID);
		else if (m_AD_Table_ID == MInvoice.Table_ID)
			m_where.append(" OR C_Invoice_ID=").append(m_Record_ID);
		else if (m_AD_Table_ID == MPayment.Table_ID)
			m_where.append(" OR C_Payment_ID=").append(m_Record_ID);
		else if (m_AD_Table_ID == MProduct.Table_ID)
			m_where.append(" OR M_Product_ID=").append(m_Record_ID);
		else if (m_AD_Table_ID == MProject.Table_ID)
			m_where.append(" OR C_Project_ID=").append(m_Record_ID);
		else if (m_AD_Table_ID == MCampaign.Table_ID)
			m_where.append(" OR C_Campaign_ID=").append(m_Record_ID);
		else if (m_AD_Table_ID == MAsset.Table_ID)
			m_where.append(" OR A_Asset_ID=").append(m_Record_ID);
		//
		String sql = "SELECT Processed, COUNT(*) "
			+ "FROM R_Request WHERE " + m_where 
			+ " GROUP BY Processed "
			+ "ORDER BY Processed DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				if ("Y".equals(rs.getString(1)))
					inactiveCount = rs.getInt(2);
				else
					activeCount += rs.getInt(2);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; 
			pstmt = null;
		}
		//
		if (activeCount > 0)
		{
			m_active = new Menuitem(Msg.getMsg(Env.getCtx(), "RequestActive") 
					+ " (" + activeCount + ")");
			m_active.addEventListener(Events.ON_CLICK, this);
			m_popup.appendChild(m_active);
		}
		if (inactiveCount > 0)
		{
			m_all = new Menuitem(Msg.getMsg(Env.getCtx(), "RequestAll") 
					+ " (" + (activeCount + inactiveCount) + ")");
			m_all.addEventListener(Events.ON_CLICK, this);
			m_popup.appendChild(m_all);
		}
		
		m_popup.setPage(invoker.getPage());
		m_popup.open(invoker);
	}	//	getZoomTargets
	
	@Override
	public void onEvent(Event e) throws Exception 
	{
		if (e.getTarget() instanceof Menuitem) 
		{
			MQuery query = null;
			if (e.getTarget() == m_active)
			{
				query = new MQuery("");
				String where = "(" + m_where + ") AND Processed='N'";
				query.addRestriction(where);
				query.setRecordCount(0);
			}
			else if (e.getTarget() == m_all)
			{
				query = new MQuery("");
				query.addRestriction(m_where.toString());
				query.setRecordCount(0);
			}
			else if (e.getTarget() == m_new)
			{
				query = new MQuery("");
				query.addRestriction("1=2");
				query.setRecordCount(0);
			}
			
			int AD_Window_ID = 232;		//	232=all - 201=my
			ADWindow frame = SessionManager.getAppDesktop().openWindow(AD_Window_ID, query);
			if(frame == null)
				return;
			//	New - set Table/Record
			if (e.getTarget() == m_new)
			{
				GridTab tab = frame.getADWindowPanel().getActiveGridTab();
				tab.dataNew(DataNewCopyMode.NoCopy);
				tab.setValue("AD_Table_ID", new Integer(m_AD_Table_ID));
				tab.setValue("Record_ID", new Integer(m_Record_ID));
				//
				if (m_C_BPartner_ID != 0)
					tab.setValue("C_BPartner_ID", new Integer(m_C_BPartner_ID));
				//
				if (m_AD_Table_ID == MBPartner.Table_ID)
					tab.setValue("C_BPartner_ID", new Integer(m_Record_ID));
				else if (m_AD_Table_ID == MUser.Table_ID)
					tab.setValue("AD_User_ID", new Integer(m_Record_ID));
				//
				else if (m_AD_Table_ID == MProject.Table_ID)
					tab.setValue("C_Project_ID", new Integer(m_Record_ID));
				else if (m_AD_Table_ID == MAsset.Table_ID)
					tab.setValue("A_Asset_ID", new Integer(m_Record_ID));
				//
				else if (m_AD_Table_ID == MOrder.Table_ID)
					tab.setValue("C_Order_ID", new Integer(m_Record_ID));
				else if (m_AD_Table_ID == MInvoice.Table_ID)
					tab.setValue("C_Invoice_ID", new Integer(m_Record_ID));
				//
				else if (m_AD_Table_ID == MProduct.Table_ID)
					tab.setValue("M_Product_ID", new Integer(m_Record_ID));
				else if (m_AD_Table_ID == MPayment.Table_ID)
					tab.setValue("C_Payment_ID", new Integer(m_Record_ID));
				//
				else if (m_AD_Table_ID == MInOut.Table_ID)
					tab.setValue("M_InOut_ID", new Integer(m_Record_ID));
				else if (m_AD_Table_ID == MRMA.Table_ID)
					tab.setValue("M_RMA_ID", new Integer(m_Record_ID));
				//
				else if (m_AD_Table_ID == MCampaign.Table_ID)
					tab.setValue("C_Campaign_ID", new Integer(m_Record_ID));
				//
				else if (m_AD_Table_ID == MRequest.Table_ID)
					tab.setValue(MRequest.COLUMNNAME_R_RequestRelated_ID, new Integer(m_Record_ID));
				// FR [2842165] - Order Ref link from SO line creating new request
				else if (m_AD_Table_ID == MOrderLine.Table_ID) {
					MOrderLine oLine = new MOrderLine(Env.getCtx(), m_Record_ID, null);
					if (oLine != null) {
						tab.setValue(MOrderLine.COLUMNNAME_C_Order_ID, new Integer(oLine.getC_Order_ID()));
					}
				}
			}
			
			frame = null;
		}
	}
}
