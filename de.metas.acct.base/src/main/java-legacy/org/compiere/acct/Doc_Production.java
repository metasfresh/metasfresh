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
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Production;
import org.compiere.model.MAcctSchema;
import org.compiere.model.ProductCost;
import org.compiere.model.X_M_ProductionLine;
import org.compiere.model.X_M_ProductionPlan;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICostDetailService;

/**
 *  Post Invoice Documents.
 *  <pre>
 *  Table:              M_Production (325)
 *  Document Types:     MMP
 *  </pre>
 *  @author Jorg Janke
 *  @version  $Id: Doc_Production.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Production extends Doc<DocLine_Production>
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_Production (final IDocBuilder docBuilder)
	{
		super (docBuilder, DOCTYPE_MatProduction);
	}   //  Doc_Production

	@Override
	protected void loadDocumentDetails()
	{
		setC_Currency_ID (NO_CURRENCY);
		I_M_Production prod = getModel(I_M_Production.class);
		setDateDoc (prod.getMovementDate());
		setDateAcct(prod.getMovementDate());
		setDocLines(loadLines(prod));
	}

	private List<DocLine_Production> loadLines(I_M_Production prod)
	{
		final List<DocLine_Production> list = new ArrayList<>();
		//	Production
		//	-- ProductionPlan
		//	-- -- ProductionLine	- the real level
		String sqlPP = "SELECT * FROM M_ProductionPlan pp "
			+ "WHERE pp.M_Production_ID=? "
			+ "ORDER BY pp.Line";
		String sqlPL = "SELECT * FROM M_ProductionLine pl "
			+ "WHERE pl.M_ProductionPlan_ID=? "
			+ "ORDER BY pl.Line";

		try
		{
			PreparedStatement pstmtPP = DB.prepareStatement(sqlPP, getTrxName());
			pstmtPP.setInt(1, get_ID());
			ResultSet rsPP = pstmtPP.executeQuery();
			//
			while (rsPP.next())
			{
				int M_Product_ID = rsPP.getInt("M_Product_ID");
				int M_ProductionPlan_ID = rsPP.getInt("M_ProductionPlan_ID");
				//
				try
				{
					PreparedStatement pstmtPL = DB.prepareStatement(sqlPL, getTrxName());
					pstmtPL.setInt(1, M_ProductionPlan_ID);
					ResultSet rsPL = pstmtPL.executeQuery();
					while (rsPL.next())
					{
						X_M_ProductionLine line = new X_M_ProductionLine(getCtx(), rsPL, getTrxName());
						if (line.getMovementQty().signum() == 0)
						{
							continue;
						}
						DocLine_Production docLine = new DocLine_Production(line, this);
						docLine.setQty (line.getMovementQty(), false);
						//	Identify finished BOM Product
						docLine.setProductionBOM(line.getM_Product_ID() == M_Product_ID);
						//
						list.add (docLine);
					}
					rsPL.close();
					pstmtPL.close();
				}
				catch (Exception ee)
				{
					throw new DBException(ee, sqlPL);
				}
			}
			rsPP.close();
			pstmtPP.close();
		}
		catch (SQLException e)
		{
			throw new DBException(e, sqlPP);
		}
		
		return list;
	}

	/**
	 *  Get Balance
	 *  @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = BigDecimal.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  MMP.
	 *  <pre>
	 *  Production
	 *      Inventory       DR      CR
	 *  </pre>
	 *  @param as account schema
	 *  @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());

		//  Line pointer
		FactLine fl = null;
		for (DocLine_Production line : getDocLines())
		{
			//	Calculate Costs
			BigDecimal costs = null;
			
			// MZ Goodwill
			// if Production CostDetail exist then get Cost from Cost Detail 
			final BigDecimal costDetailAmt = Services.get(ICostDetailRepository.class)
					.getCostDetailAmtOrNull(CostDetailQuery.builder()
							.acctSchemaId(as.getC_AcctSchema_ID())
							.documentRef(CostingDocumentRef.ofProductionLineId(line.get_ID()))
							.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
							.build());
			if (costDetailAmt != null)
			{
				costs = costDetailAmt;
			}
			else
			{	
				if (line.isProductionBOM())
				{
					//	Get BOM Cost - Sum of individual lines
					BigDecimal bomCost = BigDecimal.ZERO;
					for (DocLine_Production line0 : getDocLines())
					{
						if (line0.getM_ProductionPlan_ID() != line.getM_ProductionPlan_ID())
							continue;
						if (!line0.isProductionBOM())
							bomCost = bomCost.add(line0.getProductCosts(as, line.getAD_Org_ID(), false));
					}
					costs = bomCost.negate();
					// [ 1965015 ] Posting not balanced when is producing more than 1 product - Globalqss 2008/06/26
					X_M_ProductionPlan mpp = new X_M_ProductionPlan(getCtx(), line.getM_ProductionPlan_ID(), getTrxName());
				    if (line.getQty() != mpp.getProductionQty()) {
				    	// if the line doesn't correspond with the whole qty produced then apply prorate
				    	// costs = costs * line_qty / production_qty
				    	costs = costs.multiply(line.getQty());
				    	costs = costs.divide(mpp.getProductionQty(), as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
				    }
				}
				else
				{
					costs = line.getProductCosts(as, line.getAD_Org_ID(), false);
				}
			}
			// end MZ
			
			//  Inventory       DR      CR
			fl = fact.createLine(line,
				line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
				as.getC_Currency_ID(), costs);
			if (fl == null)
			{
				throw newPostingException().setDetailMessage("No Costs for Line " + line.getLine() + " - " + line);
			}
			fl.setM_Locator_ID(line.getM_Locator_ID());
			fl.setQty(line.getQty());
			
			//	Cost Detail
			String description = line.getDescription();
			if (description == null)
				description = "";
			if (line.isProductionBOM())
				description += "(*)";
			Services.get(ICostDetailService.class)
					.createCostDetail(CostDetailCreateRequest.builder()
							.acctSchemaId(as.getC_AcctSchema_ID())
							.clientId(line.getAD_Client_ID())
							.orgId(line.getAD_Org_ID())
							.productId(line.getM_Product_ID())
							.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
							.documentRef(CostingDocumentRef.ofProductionLineId(line.get_ID()))
							.costElementId(0)
							.qty(line.getQty())
							.amt(costs)
							.currencyId(as.getC_Currency_ID())
							.currencyConversionTypeId(getC_ConversionType_ID())
							.date(TimeUtil.asLocalDate(getDateAcct()))
							.description(description)
							.build());
		}
		//
		ArrayList<Fact> facts = new ArrayList<>();
		facts.add(fact);
		return facts;
	}   //  createFact

}   //  Doc_Production
