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
import java.util.ArrayList;
import java.util.List;

import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MMovement;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.ICostDetailService;

/**
 * Post Invoice Documents.
 * 
 * <pre>
 *  Table:              M_Movement (323)
 *  Document Types:     MMM
 * </pre>
 * 
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @version $Id: Doc_Movement.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Movement extends Doc<DocLine_Movement>
{
	private int m_Reversal_ID = 0;
	private String m_DocStatus = "";

	/**
	 * Constructor
	 * 
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_Movement(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_MatMovement);
	}   // Doc_Movement

	@Override
	protected void loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		final I_M_Movement move = getModel(I_M_Movement.class);
		setDateDoc(move.getMovementDate());
		setDateAcct(move.getMovementDate());
		m_Reversal_ID = move.getReversal_ID();// store original (voided/reversed) document
		m_DocStatus = move.getDocStatus();
		setDocLines(loadLines(move));
	}

	private List<DocLine_Movement> loadLines(I_M_Movement move)
	{
		final List<DocLine_Movement> list = new ArrayList<>();
		for (I_M_MovementLine line : Services.get(IMovementDAO.class).retrieveLines(move))
		{
			final DocLine_Movement docLine = new DocLine_Movement(line, this);
			docLine.setQty(line.getMovementQty(), false);
			docLine.setReversalLine_ID(line.getReversalLine_ID());
			list.add(docLine);
		}

		return list;
	}	// loadLines

	/**
	 * Get Balance
	 * 
	 * @return balance (ZERO) - always balanced
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for
	 * MMM.
	 * 
	 * <pre>
	 *  Movement
	 *      Inventory       DR      CR
	 *      InventoryTo     DR      CR
	 * </pre>
	 * 
	 * @param as account schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final MAcctSchema as)
	{
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		for (final DocLine_Movement line : getDocLines())
		{
			createFactsForMovementLine(fact, line);
		}

		return ImmutableList.of(fact);
	}

	private void createFactsForMovementLine(final Fact fact, final DocLine_Movement line)
	{
		final I_C_AcctSchema as = fact.getAcctSchema();
		CostAmount costs = line.getCosts(as);

		//
		// Inventory CR/DR
		final FactLine dr = fact.createLine(line,
				line.getAccount(ProductAcctType.Asset, as),
				costs.getCurrencyId(),
				costs.getValue().negate());		// from (-) CR
		if (dr == null)
		{
			return;
		}
		dr.setM_Locator_ID(line.getM_Locator_ID());
		dr.setQty(line.getQty().negate());	// outgoing
		dr.setC_Activity_ID(line.getC_ActivityFrom_ID());
		if (m_DocStatus.equals(MMovement.DOCSTATUS_Reversed) && m_Reversal_ID != 0 && line.getReversalLine_ID() != 0)
		{
			// Set AmtAcctDr from Original Movement
			if (!dr.updateReverseLine(MMovement.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
			{
				throw newPostingException().setDetailMessage("Original Inventory Move not posted yet");
			}
		}

		//
		// InventoryTo DR/CR
		final FactLine cr = fact.createLine(line,
				line.getAccount(ProductAcctType.Asset, as),
				costs.getCurrencyId(),
				costs.getValue());			// to (+) DR
		if (cr == null)
		{
			return;
		}
		cr.setM_Locator_ID(line.getM_LocatorTo_ID());
		cr.setQty(line.getQty());
		cr.setC_Activity_ID(line.getC_Activity_ID());
		if (m_DocStatus.equals(MMovement.DOCSTATUS_Reversed) && m_Reversal_ID != 0 && line.getReversalLine_ID() != 0)
		{
			// Set AmtAcctCr from Original Movement
			if (!cr.updateReverseLine(MMovement.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
			{
				throw newPostingException().setDetailMessage("Original Inventory Move not posted yet");
			}
			costs = CostAmount.of(cr.getAcctBalance(), as.getC_Currency_ID()); // get original cost
		}

		// Only for between-org movements
		if (dr.getAD_Org_ID() != cr.getAD_Org_ID())
		{
			final CostingLevel costingLevel = line.getProductCostingLevel(as);
			if (CostingLevel.Organization != costingLevel)
			{
				return;
			}

			//
			String description = line.getDescription();
			if (description == null)
				description = "";
			// Cost Detail From
			final ICostDetailService costDetailService = Services.get(ICostDetailService.class);
			costDetailService.createCostDetail(CostDetailCreateRequest.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.clientId(line.getAD_Client_ID())
					.orgId(dr.getAD_Org_ID())
					.productId(line.getM_Product_ID())
					.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
					.documentRef(CostingDocumentRef.ofOutboundMovementLineId(line.get_ID()))
					.costElementId(0)
					.qty(line.getQty().negate())
					.amt(costs.negate())
					.currencyConversionTypeId(getC_ConversionType_ID())
					.date(TimeUtil.asLocalDate(getDateAcct()))
					.description(description + "(|->)")
					.build());
			// Cost Detail To
			costDetailService.createCostDetail(CostDetailCreateRequest.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.clientId(line.getAD_Client_ID())
					.orgId(cr.getAD_Org_ID())
					.productId(line.getM_Product_ID())
					.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
					.documentRef(CostingDocumentRef.ofInboundMovementLineId(line.get_ID()))
					.costElementId(0)
					.qty(line.getQty())
					.amt(costs)
					.currencyConversionTypeId(getC_ConversionType_ID())
					.date(TimeUtil.asLocalDate(getDateAcct()))
					.description(description + "(|<-)")
					.build());
		}
	}

}   // Doc_Movement
