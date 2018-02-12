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
import java.util.List;

import org.adempiere.minventory.api.IInventoryDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostAmount;

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
 * 
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @version $Id: Doc_Inventory.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Inventory extends Doc<DocLine_Inventory>
{
	public Doc_Inventory(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_MatInventory);
	}

	@Override
	protected void loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		final I_M_Inventory inventory = getModel(I_M_Inventory.class);
		setDateDoc(inventory.getMovementDate());
		setDateAcct(inventory.getMovementDate());
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
	public List<Fact> createFacts(final MAcctSchema as)
	{
		setC_Currency_ID(as.getC_Currency_ID());

		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		getDocLines().forEach(line -> createFactsForInventoryLine(fact, line));

		return ImmutableList.of(fact);
	}

	/**
	 * <pre>
	 *  Inventory
	 *      Inventory       DR      CR
	 *      InventoryDiff   DR      CR   (or Charge)
	 * </pre>
	 */
	private void createFactsForInventoryLine(final Fact fact, final DocLine_Inventory line)
	{
		final I_C_AcctSchema as = fact.getAcctSchema();

		final CostAmount costs = line.getCreateCosts(as).getTotalAmount();

		//
		// Inventory DR/CR
		fact.createLine()
				.setDocLine(line)
				.setAccount(line.getAccount(ProductAcctType.Asset, as))
				.setC_Currency_ID(costs.getCurrencyId())
				.setAmtSourceDrOrCr(costs.getValue())
				.setQty(line.getQty())
				.locatorId(line.getM_Locator_ID())
				.buildAndAdd();

		//
		// Charge/InventoryDiff CR/DR
		final MAccount invDiff = getInvDifferencesAccount(as, line, costs.getValue().negate());
		final FactLine cr = fact.createLine()
				.setDocLine(line)
				.setAccount(invDiff)
				.setC_Currency_ID(costs.getCurrencyId())
				.setAmtSourceDrOrCr(costs.getValue().negate())
				.setQty(line.getQty().negate())
				.locatorId(line.getM_Locator_ID())
				.buildAndAdd();
		if (line.getC_Charge_ID() > 0)	// explicit overwrite for charge
		{
			cr.setAD_Org_ID(line.getAD_Org_ID());
		}
	}

	private MAccount getInvDifferencesAccount(final I_C_AcctSchema as, final DocLine_Inventory line, final BigDecimal amount)
	{
		final MAccount chargeAcct = line.getChargeAccount(as, amount.negate());
		if (chargeAcct != null)
		{
			return chargeAcct;
		}

		return getAccount(Doc.ACCTTYPE_InvDifferences, as);
	}

}   // Doc_Inventory
