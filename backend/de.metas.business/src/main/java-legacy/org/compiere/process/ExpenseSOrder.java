/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.document.engine.IDocument;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderBL;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProject;
import org.compiere.model.MTimeExpense;
import org.compiere.model.MTimeExpenseLine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Create Sales Orders from Expense Reports
 *
 * @author Jorg Janke
 * @version $Id: ExpenseSOrder.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class ExpenseSOrder extends JavaProcess
{
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);

	/** BPartner */
	private int p_C_BPartner_ID = 0;
	/** Date Drom */
	private Timestamp p_DateFrom = null;
	/** Date To */
	private Timestamp m_DateTo = null;

	/** No SO generated */
	private int m_noOrders = 0;
	/** Current Order */
	private MOrder m_order = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (ProcessInfoParameter element : para)
		{
			String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				
			}
			else if (name.equals("C_BPartner_ID"))
			{
				p_C_BPartner_ID = element.getParameterAsInt();
			}
			else if (name.equals("DateExpense"))
			{
				p_DateFrom = (Timestamp)element.getParameter();
				m_DateTo = (Timestamp)element.getParameter_To();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}	// prepare

	/**
	 * Perform process.
	 * 
	 * @return Message to be translated
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws java.lang.Exception
	{
		StringBuffer sql = new StringBuffer("SELECT * FROM S_TimeExpenseLine el "
				+ "WHERE el.AD_Client_ID=?"						// #1
				+ " AND el.C_BPartner_ID>0 AND el.IsInvoiced='Y'"	// Business Partner && to be invoiced
				+ " AND el.C_OrderLine_ID IS NULL"					// not invoiced yet
				+ " AND EXISTS (SELECT * FROM S_TimeExpense e "		// processed only
				+ "WHERE el.S_TimeExpense_ID=e.S_TimeExpense_ID AND e.Processed='Y')");
		if (p_C_BPartner_ID != 0)
		 {
			sql.append(" AND el.C_BPartner_ID=?");			// #2
		}
		if (p_DateFrom != null || m_DateTo != null)
		{
			sql.append(" AND EXISTS (SELECT * FROM S_TimeExpense e "
					+ "WHERE el.S_TimeExpense_ID=e.S_TimeExpense_ID");
			if (p_DateFrom != null)
			 {
				sql.append(" AND e.DateReport >= ?");		// #3
			}
			if (m_DateTo != null)
			 {
				sql.append(" AND e.DateReport <= ?");		// #4
			}
			sql.append(")");
		}
		sql.append(" ORDER BY el.C_BPartner_ID, el.C_Project_ID, el.S_TimeExpense_ID, el.Line");

		//
		I_C_BPartner oldBPartner = null;
		int old_Project_ID = -1;
		MTimeExpense te = null;
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			int par = 1;
			pstmt.setInt(par++, getAD_Client_ID());
			if (p_C_BPartner_ID != 0)
			{
				pstmt.setInt(par++, p_C_BPartner_ID);
			}
			if (p_DateFrom != null)
			{
				pstmt.setTimestamp(par++, p_DateFrom);
			}
			if (m_DateTo != null)
			{
				pstmt.setTimestamp(par++, m_DateTo);
			}
			rs = pstmt.executeQuery();
			while (rs.next())				// ********* Expense Line Loop
			{
				MTimeExpenseLine tel = new MTimeExpenseLine(getCtx(), rs, get_TrxName());
				if (!tel.isInvoiced())
				{
					continue;
				}

				// New BPartner - New Order
				if (oldBPartner == null
						|| oldBPartner.getC_BPartner_ID() != tel.getC_BPartner_ID())
				{
					completeOrder();
					oldBPartner = Services.get(IBPartnerDAO.class).getById(tel.getC_BPartner_ID());
				}
				// New Project - New Order
				if (old_Project_ID != tel.getC_Project_ID())
				{
					completeOrder();
					old_Project_ID = tel.getC_Project_ID();
				}
				if (te == null || te.getS_TimeExpense_ID() != tel.getS_TimeExpense_ID())
				{
					te = new MTimeExpense(getCtx(), tel.getS_TimeExpense_ID(), get_TrxName());
				}
				//
				processLine(te, tel, oldBPartner);
			}								// ********* Expense Line Loop
		}
		catch (Exception e)
		{
			log.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		completeOrder();

		return "@Created@=" + m_noOrders;
	}	// doIt

	/**
	 * Process Expense Line
	 * 
	 * @param te header
	 * @param tel line
	 * @param bp bp
	 */
	private void processLine(MTimeExpense te, MTimeExpenseLine tel, I_C_BPartner bp)
	{
		if (m_order == null)
		{
			log.info("New Order for " + bp + ", Project=" + tel.getC_Project_ID());
			m_order = new MOrder(getCtx(), 0, get_TrxName());
			m_order.setAD_Org_ID(tel.getAD_Org_ID());
			m_order.setIsSOTrx(true);
			orderBL.setSODocTypeTargetId(m_order, MOrder.DocSubType_OnCredit);
			//
			m_order.setBPartner(bp);
			if (m_order.getC_BPartner_Location_ID() == 0)
			{
				log.error("No BP Location: " + bp);
				addLog(0, te.getDateReport(),
						null, "No Location: " + te.getDocumentNo() + " " + bp.getName());
				m_order = null;
				return;
			}
			m_order.setM_Warehouse_ID(te.getM_Warehouse_ID());
			if (tel.getC_Activity_ID() != 0)
			{
				m_order.setC_Activity_ID(tel.getC_Activity_ID());
			}
			if (tel.getC_Campaign_ID() != 0)
			{
				m_order.setC_Campaign_ID(tel.getC_Campaign_ID());
			}
			if (tel.getC_Project_ID() != 0)
			{
				m_order.setC_Project_ID(tel.getC_Project_ID());
				// Optionally Overwrite BP Price list from Project
				MProject project = new MProject(getCtx(), tel.getC_Project_ID(), get_TrxName());
				if (project.getM_PriceList_ID() != 0)
				{
					m_order.setM_PriceList_ID(project.getM_PriceList_ID());
				}
			}
			m_order.setSalesRep_ID(te.getDoc_User_ID());
			//
			if (!m_order.save())
			{
				throw new IllegalStateException("Cannot save Order");
			}
		}
		else
		{
			// Update Header info
			if (tel.getC_Activity_ID() != 0 && tel.getC_Activity_ID() != m_order.getC_Activity_ID())
			{
				m_order.setC_Activity_ID(tel.getC_Activity_ID());
			}
			if (tel.getC_Campaign_ID() != 0 && tel.getC_Campaign_ID() != m_order.getC_Campaign_ID())
			{
				m_order.setC_Campaign_ID(tel.getC_Campaign_ID());
			}
			if (!m_order.save())
			{
				new IllegalStateException("Cannot save Order");
			}
		}

		// OrderLine
		MOrderLine ol = new MOrderLine(m_order);
		//
		if (tel.getM_Product_ID() != 0)
		{
			ol.setM_Product_ID(tel.getM_Product_ID(),
					tel.getC_UOM_ID());
		}
		if (tel.getS_ResourceAssignment_ID() != 0)
		{
			ol.setS_ResourceAssignment_ID(tel.getS_ResourceAssignment_ID());
		}
		ol.setQty(tel.getQtyInvoiced());		//
		ol.setDescription(tel.getDescription());
		//
		ol.setC_Project_ID(tel.getC_Project_ID());
		ol.setC_ProjectPhase_ID(tel.getC_ProjectPhase_ID());
		ol.setC_ProjectTask_ID(tel.getC_ProjectTask_ID());
		ol.setC_Activity_ID(tel.getC_Activity_ID());
		ol.setC_Campaign_ID(tel.getC_Campaign_ID());
		//
		BigDecimal price = tel.getPriceInvoiced();	//
		if (price != null && price.compareTo(Env.ZERO) != 0)
		{
			if (tel.getC_Currency_ID() != m_order.getC_Currency_ID())
			{
				price = Services.get(ICurrencyBL.class).convert(
						price,
						CurrencyId.ofRepoId(tel.getC_Currency_ID()),
						CurrencyId.ofRepoId(m_order.getC_Currency_ID()),
						ClientId.ofRepoId(m_order.getAD_Client_ID()),
						OrgId.ofRepoId(m_order.getAD_Org_ID()));
			}
			ol.setPrice(price);
		}
		else
		{
			ol.setPrice();
		}
		if (tel.getC_UOM_ID() != 0 && ol.getC_UOM_ID() == 0)
		{
			ol.setC_UOM_ID(tel.getC_UOM_ID());
		}
		ol.setTax();
		if (!ol.save())
		{
			throw new IllegalStateException("Cannot save Order Line");
		}
		// Update TimeExpense Line
		tel.setC_OrderLine_ID(ol.getC_OrderLine_ID());
		if (tel.save())
		{
			log.debug("Updated " + tel + " with C_OrderLine_ID");
		}
		else
		{
			log.error("Not Updated " + tel + " with C_OrderLine_ID");
		}

	}	// processLine

	/**
	 * Complete Order
	 */
	private void completeOrder()
	{
		if (m_order == null)
		{
			return;
		}
		m_order.setDocAction(IDocument.ACTION_Prepare);
		m_order.processIt(IDocument.ACTION_Prepare);
		if (!m_order.save())
		{
			throw new IllegalStateException("Cannot save Order");
		}
		m_noOrders++;
		addLog(m_order.get_ID(), m_order.getDateOrdered(), m_order.getGrandTotal(), m_order.getDocumentNo());
		m_order = null;
	}	// completeOrder

}	// ExpenseSOrder
