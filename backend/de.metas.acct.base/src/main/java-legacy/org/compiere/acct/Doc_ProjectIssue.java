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
import java.util.List;

import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_TimeExpenseLine;
import org.compiere.model.MProject;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.CostAmount;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.util.Services;

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
	private final ProjectRepository projectRepository = SpringContextHolder.instance.getBean(ProjectRepository.class);

	public Doc_ProjectIssue(final AcctDocContext ctx)
	{
		super(ctx, DOCTYPE_ProjectIssue);
	}

	/** Pseudo Line */
	private DocLine_ProjectIssue m_line = null;
	/** Issue */
	private I_C_ProjectIssue m_issue = null;

	@Override
	protected void loadDocumentDetails()
	{
		setNoCurrency();
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
		final ProjectId projectId = ProjectId.ofRepoId(m_issue.getC_Project_ID());
		final I_C_Project project = projectRepository.getById(projectId);
		if (project != null)
		{
			return project.getValue() + " #" + m_issue.getLine();
		}
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
	public List<Fact> createFacts(final AcctSchema as)
	{
		// create Fact Header
		Fact fact = new Fact(this, as, PostingType.Actual);
		setC_Currency_ID(as.getCurrencyId());

		MProject project = new MProject(Env.getCtx(), m_issue.getC_Project_ID(), ITrx.TRXNAME_ThreadInherited);
		String ProjectCategory = project.getProjectCategory();
		I_M_Product product = Services.get(IProductDAO.class).getById(m_issue.getM_Product_ID());

		// Line pointers
		FactLine dr = null;
		FactLine cr = null;

		// Issue Cost
		CostAmount cost = null;
		if (m_issue.getM_InOutLine_ID() > 0)
		{
			cost = getPOCost(as);
		}
		else if (m_issue.getS_TimeExpenseLine_ID() > 0)
		{
			cost = getLaborCost(as);
		}
		if (cost == null)
		{
			cost = m_line.getCreateCosts(as);
		}

		//
		// Project DR
		{
			AccountType acctType = AccountType.ProjectWIP;
			if (MProject.PROJECTCATEGORY_AssetProject.equals(ProjectCategory))
			{
				acctType = AccountType.ProjectAsset;
			}
			dr = fact.createLine(m_line,
					getAccount(acctType, as),
					cost.getCurrencyId(),
					cost.getValue(), null);
			dr.setQty(m_line.getQty().negate());
		}

		//
		// Inventory CR
		{
			ProductAcctType acctType = ProductAcctType.Asset;
			if (Services.get(IProductBL.class).isService(product))
			{
				acctType = ProductAcctType.Expense;
			}
			cr = fact.createLine(m_line,
					m_line.getAccount(acctType, as),
					cost.getCurrencyId(),
					null, cost.getValue());
			cr.setM_Locator_ID(m_line.getM_Locator_ID());
			cr.setLocationFromLocator(m_line.getM_Locator_ID(), true);	// from Loc
		}

		//
		return ImmutableList.of(fact);
	}

	/**
	 * Get PO Costs in Currency of AcctSchema
	 * 
	 * @param as Account Schema
	 * @return Unit PO Cost
	 */
	private CostAmount getPOCost(final AcctSchema as)
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
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, as.getCurrencyId().getRepoId());
			pstmt.setInt(2, getClientId().getRepoId());
			pstmt.setInt(3, getOrgId().getRepoId());
			pstmt.setInt(4, m_issue.getM_InOutLine_ID());
			rs = pstmt.executeQuery();

			final BigDecimal costs;
			if (rs.next())
			{
				costs = rs.getBigDecimal(1);
				logger.debug("POCost = {}", costs);
			}
			else
			{
				logger.warn("Not found for M_InOutLine_ID={}", m_issue.getM_InOutLine_ID());
				costs = BigDecimal.ZERO;
			}

			return CostAmount.of(costs, as.getCurrencyId());
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

	private CostAmount getLaborCost(final AcctSchema as)
	{
		String sql = "SELECT ConvertedAmt, Qty" +
				" FROM " + I_S_TimeExpenseLine.Table_Name +
				" WHERE S_TimeExpenseLine.S_TimeExpenseLine_ID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, m_issue.getS_TimeExpenseLine_ID());
			rs = pstmt.executeQuery();
			BigDecimal costs;
			if (rs.next())
			{
				costs = rs.getBigDecimal(1);
				final BigDecimal qty = rs.getBigDecimal(2);
				costs = costs.multiply(qty);
				logger.debug("ExpLineCost = {}", costs);
			}
			else
			{
				logger.warn("Not found for S_TimeExpenseLine_ID={}", m_issue.getS_TimeExpenseLine_ID());
				costs = BigDecimal.ZERO;
			}
			return CostAmount.of(costs, as.getCurrencyId());
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
