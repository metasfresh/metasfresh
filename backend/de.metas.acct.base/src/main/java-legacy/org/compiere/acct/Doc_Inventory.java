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

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.accounts.WarehouseAccountType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.CostAmount;
import de.metas.document.DocBaseType;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_Inventory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Post Inventory Documents.
 *
 * <pre>
 *  Table:              M_Inventory (321)
 *  Document Types:     MMI
 * </pre>
 * <p>
 *
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 * <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @version $Id: Doc_Inventory.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Inventory extends Doc<DocLine_Inventory>
{
	public Doc_Inventory(final AcctDocContext ctx)
	{
		super(ctx, DocBaseType.MaterialPhysicalInventory);
	}

	@Override
	protected void loadDocumentDetails()
	{
		setNoCurrency();
		final I_M_Inventory inventory = getModel(I_M_Inventory.class);
		setDateDoc(inventory.getMovementDate());
		setDateAcct(inventory.getMovementDate());
		setDocLines(loadLines(inventory));
	}

	private List<DocLine_Inventory> loadLines(final I_M_Inventory inventory)
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());

		return Services.get(IInventoryDAO.class)
				.retrieveLinesForInventoryId(inventoryId)
				.stream()
				.map(line -> new DocLine_Inventory(line, this))
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
	public List<Fact> createFacts(final AcctSchema as)
	{
		setC_Currency_ID(as.getCurrencyId());

		final Fact fact = new Fact(this, as, PostingType.Actual);
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
		final AcctSchema as = fact.getAcctSchema();

		final CostAmount costs = line.getCreateCosts(as);

		//
		// Inventory DR/CR
		fact.createLine()
				.setDocLine(line)
				.setAccount(line.getAccount(ProductAcctType.P_Asset_Acct, as))
				.setAmtSourceDrOrCr(costs.toMoney())
				.setQty(line.getQty())
				.locatorId(line.getM_Locator_ID())
				.buildAndAdd();

		//
		// Charge/InventoryDiff CR/DR
		final Account invDiff = getInvDifferencesAccount(as, line, costs.toBigDecimal().negate());
		final FactLine cr = fact.createLine()
				.setDocLine(line)
				.setAccount(invDiff)
				.setAmtSourceDrOrCr(costs.toMoney().negate())
				.setQty(line.getQty().negate())
				.locatorId(line.getM_Locator_ID())
				.buildAndAdd();
		if (line.getC_Charge_ID().isPresent())    // explicit overwrite for charge
		{
			cr.setAD_Org_ID(line.getOrgId());
		}
	}

	@NonNull
	private Account getInvDifferencesAccount(final AcctSchema as, final DocLine_Inventory line, final BigDecimal amount)
	{
		final Account chargeAcct = line.getChargeAccount(as, amount.negate());
		if (chargeAcct != null)
		{
			return chargeAcct;
		}

		return getAccountProvider().getWarehouseAccount(as.getId(), getWarehouseId(), WarehouseAccountType.W_Differences_Acct);
	}

}   // Doc_Inventory
