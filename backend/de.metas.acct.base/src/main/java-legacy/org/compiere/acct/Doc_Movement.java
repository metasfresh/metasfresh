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

import org.adempiere.mmovement.api.IMovementDAO;
import org.compiere.model.I_M_Movement;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.CostAmount;
import de.metas.costing.MoveCostsResult;
import de.metas.util.Services;

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
	public Doc_Movement(final AcctDocContext ctx)
	{
		super(ctx, DOCTYPE_MatMovement);
	}

	@Override
	protected void loadDocumentDetails()
	{
		setNoCurrency();
		final I_M_Movement move = getModel(I_M_Movement.class);
		setDateDoc(move.getMovementDate());
		setDateAcct(move.getMovementDate());
		// m_Reversal_ID = move.getReversal_ID();// store original (voided/reversed) document
		// m_DocStatus = move.getDocStatus();
		setDocLines(loadLines(move));
	}

	private List<DocLine_Movement> loadLines(final I_M_Movement movement)
	{
		return Services.get(IMovementDAO.class)
				.retrieveLines(movement)
				.stream()
				.map(movementLine -> new DocLine_Movement(movementLine, this))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Get Balance
	 * 
	 * @return balance (ZERO) - always balanced
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

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
	public List<Fact> createFacts(final AcctSchema as)
	{
		final Fact fact = new Fact(this, as, PostingType.Actual);
		setC_Currency_ID(as.getCurrencyId());

		getDocLines().forEach(line -> createFactsForMovementLine(fact, line));

		return ImmutableList.of(fact);
	}

	private void createFactsForMovementLine(final Fact fact, final DocLine_Movement line)
	{
		final AcctSchema as = fact.getAcctSchema();

		final MoveCostsResult costs = line.getCreateCosts(as);

		//
		// Inventory CR/DR (from locator)
		final CostAmount outboundCosts = costs.getOutboundAmountToPost(as);
		fact.createLine()
				.setDocLine(line)
				.setAccount(line.getAccount(ProductAcctType.Asset, as))
				.setCurrencyId(outboundCosts.getCurrencyId())
				.setAmtSourceDrOrCr(outboundCosts.getValue()) // from (-) CR
				.setQty(line.getQty().negate()) // outgoing
				.locatorId(line.getM_Locator_ID())
				.activityId(line.getActivityFromId())
				.buildAndAdd();

		//
		// InventoryTo DR/CR (to locator)
		final CostAmount inboundCosts = costs.getInboundAmountToPost(as);
		fact.createLine()
				.setDocLine(line)
				.setAccount(line.getAccount(ProductAcctType.Asset, as))
				.setCurrencyId(inboundCosts.getCurrencyId())
				.setAmtSourceDrOrCr(inboundCosts.getValue()) // to (+) DR
				.setQty(line.getQty()) // incoming
				.locatorId(line.getM_LocatorTo_ID())
				.activityId(line.getActivityId())
				.buildAndAdd();
	}

}   // Doc_Movement
