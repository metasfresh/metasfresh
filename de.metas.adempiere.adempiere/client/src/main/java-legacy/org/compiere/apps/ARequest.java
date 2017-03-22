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
package org.compiere.apps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_R_Request;
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
import org.compiere.swing.CMenuItem;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.logging.LogManager;


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
public class ARequest implements ActionListener
{
	/**
	 * 	Constructor
	 *	@param invoker invoker button
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@param C_BPartner_ID optional bp
	 */
	public ARequest (JComponent invoker, int AD_Table_ID, int Record_ID,
		int C_BPartner_ID)
	{
		super ();
		log.info("AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID);
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;
		m_C_BPartner_ID = C_BPartner_ID;
		getRequests(invoker);
	}	//	ARequest
	
	/**	The Table						*/
	private int			m_AD_Table_ID;
	/** The Record						*/
	private int			m_Record_ID;
	/** BPartner						*/
	private int			m_C_BPartner_ID;
	
	/**	The Popup						*/
	private JPopupMenu 	m_popup = new JPopupMenu("RequestMenu");
	private CMenuItem 	m_new = null;
	private CMenuItem 	m_active = null;
	private CMenuItem 	m_all = null;
	/** Where Clause					*/
	StringBuffer 		m_where = null;
	
	/**	Logger	*/
	private static Logger	log	= LogManager.getLogger(ARequest.class);
	
	/**
	 * 	Display Request Options - New/Existing.
	 * 	@param invoker button
	 */
	private void getRequests (JComponent invoker)
	{
		m_new = new CMenuItem(Msg.getMsg(Env.getCtx(), "RequestNew"));
		m_new.setIcon(Images.getImageIcon2("New16"));
		m_popup.add(m_new).addActionListener(this);
		//
		int activeCount = 0;
		int inactiveCount = 0;
		m_where = new StringBuffer();
		m_where.append("(AD_Table_ID=").append(m_AD_Table_ID)
			.append(" AND Record_ID=").append(m_Record_ID)
			.append(")");
		//
		if (m_AD_Table_ID == InterfaceWrapperHelper.getTableId(I_AD_User.class))
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
			log.error(sql, e);
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
			m_active = new CMenuItem(Msg.getMsg(Env.getCtx(), "RequestActive") 
				+ " (" + activeCount + ")");
			m_popup.add(m_active).addActionListener(this);
		}
		if (inactiveCount > 0)
		{
			m_all = new CMenuItem(Msg.getMsg(Env.getCtx(), "RequestAll") 
				+ " (" + (activeCount + inactiveCount) + ")");
			m_popup.add(m_all).addActionListener(this);
		}
		//
		if (invoker.isShowing())
			m_popup.show(invoker, 0, invoker.getHeight());	//	below button
	}	//	getZoomTargets
	
	/**
	 * 	Listner
	 *	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		MQuery query = null;
		if (e.getSource() == m_active)
		{
			query = new MQuery("");
			String where = "(" + m_where + ") AND Processed='N'";
			query.addRestriction(where);
		}
		else if (e.getSource() == m_all)
		{
			query = new MQuery("");
			query.addRestriction(m_where.toString());
		}
		//
		int AD_Window_ID = 232;		//	232=all - 201=my
		AWindow frame = new AWindow();
		if (!frame.initWindow(AD_Window_ID, query))
			return;
		AEnv.addToWindowManager(frame);
		//	New - set Table/Record
		if (e.getSource() == m_new)
		{
			GridTab tab = frame.getAPanel().getCurrentTab();
			tab.dataNew(DataNewCopyMode.NoCopy);
			tab.setValue("AD_Table_ID", new Integer(m_AD_Table_ID));
			tab.setValue("Record_ID", new Integer(m_Record_ID));
			//
			if (m_C_BPartner_ID != 0)
				tab.setValue("C_BPartner_ID", new Integer(m_C_BPartner_ID));
			//
			if (m_AD_Table_ID == MBPartner.Table_ID)
				tab.setValue("C_BPartner_ID", new Integer(m_Record_ID));
			else if (m_AD_Table_ID == InterfaceWrapperHelper.getTableId(I_AD_User.class))
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
			else if (m_AD_Table_ID == InterfaceWrapperHelper.getTableId(I_R_Request.class))
				tab.setValue(MRequest.COLUMNNAME_R_RequestRelated_ID, new Integer(m_Record_ID));
			// FR [2842165] - Order Ref link from SO line creating new request
			else if (m_AD_Table_ID == MOrderLine.Table_ID) {
				MOrderLine oLine = new MOrderLine(Env.getCtx(), m_Record_ID, null);
				if (oLine != null) {
					tab.setValue(MOrderLine.COLUMNNAME_C_Order_ID, new Integer(oLine.getC_Order_ID()));
				}
			}
		}
		AEnv.showCenterScreen(frame);
		frame = null;
	}	//	actionPerformed
	
}	//	ARequest
