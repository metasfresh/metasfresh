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

import org.adempiere.minventory.api.IInventoryDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MInventory;
import org.compiere.model.MProduct;
import org.compiere.model.ProductCost;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostingDocumentRef;

/**
 * Post Inventory Documents.
 * 
 * <pre>
 *  Table:              M_Inventory (321)
 *  Document Types:     MMI
 * </pre>
 * 
 * metas:
 * <li>copied from https://adempiere.svn.sourceforge.net/svnroot/adempiere/branches/metas/mvcForms/base/src/org/compiere/acct/Doc_Inventory.java, Rev 14597
 * <li>Changed for "US330: Geschaeftsvorfall (G113d): Summen-/ Saldenliste (2010070510000637)"
 * <li>Goal: Use {@link MCost#getSeedCosts(MProduct, int, MAcctSchema, int, String, int)}, if a product has no cost yet
 * 
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @version $Id: Doc_Inventory.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Inventory extends Doc<DocLine_Inventory>
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
	public Doc_Inventory(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_MatInventory);
	}   // Doc_Inventory

	@Override
	protected void loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		final I_M_Inventory inventory = getModel(I_M_Inventory.class);
		setDateDoc(inventory.getMovementDate());
		setDateAcct(inventory.getMovementDate());
		m_Reversal_ID = inventory.getReversal_ID();// store original (voided/reversed) document
		m_DocStatus = inventory.getDocStatus();
		setDocLines(loadLines(inventory));
	}

	private List<DocLine_Inventory> loadLines(final I_M_Inventory inventory)
	{
		return Services.get(IInventoryDAO.class)
				.retrieveLinesForInventory(inventory)
				.stream()
				.map(line -> new DocLine_Inventory(line, this))
				.filter(docLine -> !docLine.getQty().isZero())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Get Balance
	 * 
	 * @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

	/**
	 * Create Facts (the accounting logic) for
	 * MMI.
	 * 
	 * <pre>
	 *  Inventory
	 *      Inventory       DR      CR
	 *      InventoryDiff   DR      CR   (or Charge)
	 * </pre>
	 * 
	 * @param as account schema
	 * @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		// create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		// Line pointers
		FactLine dr = null;
		FactLine cr = null;

		for (final DocLine_Inventory line : getDocLines())
		{
			// MZ Goodwill
			// if Physical Inventory CostDetail is exist then get Cost from Cost Detail
			// metas-ts: US330: call with zeroCostsOK=false, because we want the system to return the result of MCost.getSeedCosts(), if there are no current costs yet
			BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), false, CostingDocumentRef.ofInventoryLineId(line.get_ID()));
			// end MZ
			if (ProductCost.isNoCosts(costs))
			{
				throw newPostingException()
						.setDetailMessage("No Costs for " + line.getProduct().getName())
						.setC_AcctSchema(as)
						.setDocLine(line);
			}
			// Inventory DR CR
			dr = fact.createLine(line,
					line.getAccount(ProductAcctType.Asset, as),
					as.getC_Currency_ID(), costs);
			// may be zero difference - no line created.
			if (dr == null)
				continue;
			dr.setM_Locator_ID(line.getM_Locator_ID());
			if (m_DocStatus.equals(MInventory.DOCSTATUS_Reversed) && m_Reversal_ID != 0 && line.getReversalLine_ID() != 0)
			{
				// Set AmtAcctDr from Original Phys.Inventory
				if (!dr.updateReverseLine(InterfaceWrapperHelper.getTableId(I_M_Inventory.class),
						m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException()
							.setDetailMessage("Original Physical Inventory not posted yet")
							.setC_AcctSchema(as)
							.setDocLine(line)
							.setPreserveDocumentPostedStatus();
				}
			}

			// InventoryDiff DR CR
			// or Charge
			MAccount invDiff = line.getChargeAccount(as, costs.negate());
			if (invDiff == null)
				invDiff = getAccount(Doc.ACCTTYPE_InvDifferences, as);
			cr = fact.createLine(line, invDiff,
					as.getC_Currency_ID(), costs.negate());
			if (cr == null)
				continue;
			cr.setM_Locator_ID(line.getM_Locator_ID());
			cr.setQty(line.getQty().negate());
			if (line.getC_Charge_ID() != 0)	// explicit overwrite for charge
				cr.setAD_Org_ID(line.getAD_Org_ID());
			if (m_DocStatus.equals(MInventory.DOCSTATUS_Reversed) && m_Reversal_ID != 0 && line.getReversalLine_ID() != 0)
			{
				// Set AmtAcctCr from Original Phys.Inventory
				if (!cr.updateReverseLine(InterfaceWrapperHelper.getTableId(I_M_Inventory.class),
						m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException()
							.setDetailMessage("Original Physical Inventory not posted yet")
							.setC_AcctSchema(as)
							.setDocLine(line)
							.setPreserveDocumentPostedStatus();
				}
				costs = cr.getAcctBalance(); // get original cost
			}

			// Cost Detail
			/*
			 * Source move to MInventory.createCostDetail()
			 * MCostDetail.createInventory(as, line.getAD_Org_ID(),
			 * line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
			 * line.get_ID(), 0,
			 * costs, line.getQty(),
			 * line.getDescription(), getTrxName());
			 */
		}
		//
		ArrayList<Fact> facts = new ArrayList<>();
		facts.add(fact);
		return facts;
	}   // createFact

}   // Doc_Inventory
