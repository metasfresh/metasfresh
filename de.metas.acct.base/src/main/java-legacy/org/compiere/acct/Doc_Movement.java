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
import java.util.ArrayList;

import org.adempiere.util.Services;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.ProductCost;
import org.compiere.util.Env;

import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailService;

/**
 *  Post Invoice Documents.
 *  <pre>
 *  Table:              M_Movement (323)
 *  Document Types:     MMM
 *  </pre>
 *  @author Jorg Janke
 *  @author Armen Rizal, Goodwill Consulting
 * 			<li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 *  @version  $Id: Doc_Movement.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Movement extends Doc
{
	private int				m_Reversal_ID = 0;
	private String			m_DocStatus = "";
	
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_Movement (final IDocBuilder docBuilder)
	{
		super (docBuilder, DOCTYPE_MatMovement);
	}   //  Doc_Movement

	/**
	 *  Load Document Details
	 *  @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		MMovement move = (MMovement)getPO();
		setDateDoc (move.getMovementDate());
		setDateAcct(move.getMovementDate());
		m_Reversal_ID = move.getReversal_ID();//store original (voided/reversed) document
		m_DocStatus = move.getDocStatus();
		//	Contained Objects
		p_lines = loadLines(move);
		log.debug("Lines=" + p_lines.length);
		return null;
	}   //  loadDocumentDetails

	/**
	 *	Load Invoice Line
	 *	@param move move
	 *  @return document lines (DocLine_Material)
	 */
	private DocLine[] loadLines(MMovement move)
	{
		ArrayList<DocLine> list = new ArrayList<>();
		MMovementLine[] lines = move.getLines(true);
		for (int i = 0; i < lines.length; i++)
		{
			MMovementLine line = lines[i];
			DocLine docLine = new DocLine (line, this);
			docLine.setQty(line.getMovementQty(), false);
			docLine.setReversalLine_ID(line.getReversalLine_ID());
			log.debug(docLine.toString());
			list.add (docLine);
		}

		//	Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	/**
	 *  Get Balance
	 *  @return balance (ZERO) - always balanced
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  MMM.
	 *  <pre>
	 *  Movement
	 *      Inventory       DR      CR
	 *      InventoryTo     DR      CR
	 *  </pre>
	 *  @param as account schema
	 *  @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;

		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine line = p_lines[i];
			// MZ Goodwill
			// if Inventory Move CostDetail exist then get Cost from Cost Detail 
			BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), true, CostingDocumentRef.ofInboundMovementLineId(line.get_ID()));
			// end MZ

			//  ** Inventory       DR      CR
			dr = fact.createLine(line,
				line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
				as.getC_Currency_ID(), costs.negate());		//	from (-) CR
			if (dr == null)
				continue;
			dr.setM_Locator_ID(line.getM_Locator_ID());
			dr.setQty(line.getQty().negate());	//	outgoing
			dr.setC_Activity_ID(line.getC_ActivityFrom_ID());
			if (m_DocStatus.equals(MMovement.DOCSTATUS_Reversed) && m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
			{
				//	Set AmtAcctDr from Original Movement
				if (!dr.updateReverseLine (MMovement.Table_ID, 
						m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
				{
					p_Error = "Original Inventory Move not posted yet";
					return null;
				}
			}
			
			//  ** InventoryTo     DR      CR
			cr = fact.createLine(line,
				line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
				as.getC_Currency_ID(), costs);			//	to (+) DR
			if (cr == null)
				continue;
			cr.setM_Locator_ID(line.getM_LocatorTo_ID());
			cr.setQty(line.getQty());
			cr.setC_Activity_ID(line.getC_Activity_ID());
			if (m_DocStatus.equals(MMovement.DOCSTATUS_Reversed) && m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
			{
				//	Set AmtAcctCr from Original Movement
				if (!cr.updateReverseLine (MMovement.Table_ID, 
						m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
				{
					p_Error = "Original Inventory Move not posted yet";
					return null;
				}
				costs = cr.getAcctBalance(); //get original cost
			}

			//	Only for between-org movements
			if (dr.getAD_Org_ID() != cr.getAD_Org_ID())
			{
				String costingLevel = line.getProduct().getCostingLevel(as);
				if (!MAcctSchema.COSTINGLEVEL_Organization.equals(costingLevel))
					continue;
				//
				String description = line.getDescription();
				if (description == null)
					description = "";
				//	Cost Detail From
				final ICostDetailService costDetailService = Services.get(ICostDetailService.class);
				costDetailService.createCostDetail(CostDetailCreateRequest.builder()
						.acctSchemaId(as.getC_AcctSchema_ID())
						.clientId(line.getAD_Client_ID())
						.orgId(dr.getAD_Org_ID())
						.productId(line.getM_Product_ID())
						.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
						.documentRef(CostingDocumentRef.ofOutboundMovementLineId(line.get_ID()))
						.costElementId(0)
						.amt(costs.negate())
						.qty(line.getQty().negate())
						.description(description + "(|->)")
						.build());
				//	Cost Detail To
				costDetailService.createCostDetail(CostDetailCreateRequest.builder()
						.acctSchemaId(as.getC_AcctSchema_ID())
						.clientId(line.getAD_Client_ID())
						.orgId(cr.getAD_Org_ID())
						.productId(line.getM_Product_ID())
						.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
						.documentRef(CostingDocumentRef.ofInboundMovementLineId(line.get_ID()))
						.costElementId(0)
						.amt(costs)
						.qty(line.getQty())
						.description(description + "(|<-)")
						.build());
			}
		}

		//
		ArrayList<Fact> facts = new ArrayList<>();
		facts.add(fact);
		return facts;
	}   //  createFact

}   //  Doc_Movement
