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
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.acct.api.ProductAcctType;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

/**
 * Project Issue.
 * Note:
 * Will load the default GL Category.
 * Set up a document type to set the GL Category.
 * 
 * @author Jorg Janke
 * @version $Id: Doc_ProjectIssue.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_ProjectIssue extends Doc<DocLine_ProjectIssue>
{
	private static final Logger logger = LogManager.getLogger(Doc_ProjectIssue.class);

	public Doc_ProjectIssue(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_ProjectIssue);
	}   // Doc_ProjectIssue

	/** Pseudo Line */
	private DocLine_ProjectIssue m_line = null;
	/** Issue */
	private I_C_ProjectIssue m_issue = null;

	@Override
	protected void loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		m_issue = getModel(I_C_ProjectIssue.class);
		setDateDoc(m_issue.getMovementDate());
		setDateAcct(m_issue.getMovementDate());

		// Pseudo Line
		m_line = new DocLine_ProjectIssue(m_issue, this);
	}

	/**
	 * Get DocumentNo
	 * 
	 * @return document no
	 */
	@Override
	public String getDocumentNo()
	{
		I_C_Project project = m_issue.getC_Project();
		if (project != null)
			return project.getValue() + " #" + m_issue.getLine();
		return "(" + m_issue.getC_Project_ID() + ")";
	}	// getDocumentNo

	/**
	 * Get Balance
	 * 
	 * @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for
	 * PJI
	 * 
	 * <pre>
	 *  Issue
	 *      ProjectWIP      DR
	 *      Inventory               CR
	 * </pre>
	 * 
	 * Project Account is either Asset or WIP depending on Project Type
	 * 
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		// create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		MProject project = new MProject(getCtx(), m_issue.getC_Project_ID(), getTrxName());
		String ProjectCategory = project.getProjectCategory();
		MProduct product = MProduct.get(getCtx(), m_issue.getM_Product_ID());

		// Line pointers
		FactLine dr = null;
		FactLine cr = null;

		// Issue Cost
		BigDecimal cost = null;
		if (m_issue.getM_InOutLine_ID() != 0)
			cost = getPOCost(as);
		else if (m_issue.getS_TimeExpenseLine_ID() != 0)
			cost = getLaborCost(as);
		if (cost == null)	// standard Product Costs
			cost = m_line.getProductCosts(as, getAD_Org_ID(), false);

		// Project DR
		{
			int acctType = ACCTTYPE_ProjectWIP;
			if (MProject.PROJECTCATEGORY_AssetProject.equals(ProjectCategory))
				acctType = ACCTTYPE_ProjectAsset;
			dr = fact.createLine(m_line,
					getAccount(acctType, as), as.getC_Currency_ID(), cost, null);
			dr.setQty(m_line.getQty().negate());
		}

		// Inventory CR
		{
			ProductAcctType acctType = ProductAcctType.Asset;
			if (Services.get(IProductBL.class).isService(product))
			{
				acctType = ProductAcctType.Expense;
			}
			cr = fact.createLine(m_line,
					m_line.getAccount(acctType, as),
					as.getC_Currency_ID(), null, cost);
			cr.setM_Locator_ID(m_line.getM_Locator_ID());
			cr.setLocationFromLocator(m_line.getM_Locator_ID(), true);	// from Loc
		}

		//
		ArrayList<Fact> facts = new ArrayList<>();
		facts.add(fact);
		return facts;
	}   // createFact

	/**
	 * Get PO Costs in Currency of AcctSchema
	 * 
	 * @param as Account Schema
	 * @return Unit PO Cost
	 */
	private BigDecimal getPOCost(MAcctSchema as)
	{
		// Uses PO Date
		String sql = "SELECT currencyConvert(ol.PriceActual, o.C_Currency_ID, ?, o.DateOrdered, o.C_ConversionType_ID, ?, ?) "
				+ "FROM C_OrderLine ol"
				+ " INNER JOIN M_InOutLine iol ON (iol.C_OrderLine_ID=ol.C_OrderLine_ID)"
				+ " INNER JOIN C_Order o ON (o.C_Order_ID=ol.C_Order_ID) "
				+ "WHERE iol.M_InOutLine_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			pstmt.setInt(1, as.getC_Currency_ID());
			pstmt.setInt(2, getAD_Client_ID());
			pstmt.setInt(3, getAD_Org_ID());
			pstmt.setInt(4, m_issue.getM_InOutLine_ID());
			rs = pstmt.executeQuery();

			final BigDecimal retValue;
			if (rs.next())
			{
				retValue = rs.getBigDecimal(1);
				logger.debug("POCost = {}", retValue);
			}
			else
			{
				logger.warn("Not found for M_InOutLine_ID={}", m_issue.getM_InOutLine_ID());
				retValue = BigDecimal.ZERO;
			}
			
			return retValue;
		}
		catch (Exception e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			pstmt = null;
			rs = null;
		}
	}	// getPOCost();

	/**
	 * Get Labor Cost from Expense Report
	 * 
	 * @param as Account Schema
	 * @return Unit Labor Cost
	 */

	private BigDecimal getLaborCost(MAcctSchema as)
	{
		String sql = "SELECT ConvertedAmt, Qty FROM S_TimeExpenseLine " +
				" WHERE S_TimeExpenseLine.S_TimeExpenseLine_ID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), getTrxName());
			pstmt.setInt(1, m_issue.getS_TimeExpenseLine_ID());
			rs = pstmt.executeQuery();
			BigDecimal retValue;
			if (rs.next())
			{
				retValue = rs.getBigDecimal(1);
				final BigDecimal qty = rs.getBigDecimal(2);
				retValue = retValue.multiply(qty);
				logger.debug("ExpLineCost = {}", retValue);
			}
			else
			{
				logger.warn("Not found for S_TimeExpenseLine_ID={}", m_issue.getS_TimeExpenseLine_ID());
				retValue = BigDecimal.ZERO;
			}
			return retValue;
		}
		catch (Exception e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			pstmt = null;
			rs = null;
		}
	}	// getLaborCost

}	// DocProjectIssue
