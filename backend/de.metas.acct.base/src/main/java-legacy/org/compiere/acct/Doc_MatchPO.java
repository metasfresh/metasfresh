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

<<<<<<< HEAD
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;

import com.google.common.collect.ImmutableList;

=======
import com.google.common.collect.ImmutableList;
import de.metas.acct.accounts.GLAccountType;
import de.metas.acct.accounts.ProductAcctType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.PostingType;
<<<<<<< HEAD
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.CostAmount;
import de.metas.costing.CostingMethod;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.util.Services;

/**
 * Post MatchPO Documents.
 * 
=======
import de.metas.acct.doc.AcctDocContext;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostingMethod;
import de.metas.document.DocBaseType;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Post MatchPO Documents.
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * <pre>
 *  Table:              C_MatchPO (473)
 *  Document Types:     MXP
 * </pre>
<<<<<<< HEAD
 * 
 * @author Jorg Janke
 * @version $Id: Doc_MatchPO.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_MatchPO extends Doc<DocLine_MatchPO>
{
	/** Shall we create accounting facts (08555) */
	private static final String SYSCONFIG_NoFactRecords = "org.compiere.acct.Doc_MatchPO.NoFactAccts";
	private static final boolean DEFAULT_NoFactRecords = true;

	/** pseudo line */
	private DocLine_MatchPO docLine;

	/** Shall we create accounting facts? (08555) */
=======
 *
 * @author Jorg Janke
 */
public class Doc_MatchPO extends Doc<DocLine_MatchPO>
{
	/**
	 * Shall we create accounting facts (08555)
	 */
	private static final String SYSCONFIG_NoFactRecords = "org.compiere.acct.Doc_MatchPO.NoFactAccts";
	private static final boolean DEFAULT_NoFactRecords = true;

	/**
	 * pseudo line
	 */
	private DocLine_MatchPO docLine;

	/**
	 * Shall we create accounting facts? (08555)
	 */
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private boolean noFactRecords = false;

	public Doc_MatchPO(final AcctDocContext ctx)
	{
<<<<<<< HEAD
		super(ctx, DOCTYPE_MatMatchPO);
=======
		super(ctx, DocBaseType.MatchPO);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	protected void loadDocumentDetails()
	{
		setNoCurrency();
		docLine = new DocLine_MatchPO(getModel(I_M_MatchPO.class), this);

		setDateDoc(docLine.getDateDoc());

		this.noFactRecords = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_NoFactRecords, DEFAULT_NoFactRecords);
	}

	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

	/**
	 * Create Facts (the accounting logic) for
	 * MXP.
<<<<<<< HEAD
	 * 
=======
	 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * <pre>
	 *      Product PPV     <difference>
	 *      PPV_Offset                  <difference>
	 * </pre>
<<<<<<< HEAD
	 * 
=======
	 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
<<<<<<< HEAD
		// Skip posting if MatchPO does not have the receipt line set.
		// It's enough to create no facts at all.
		// Usually, system creates separate M_MatchPO records for each receipt.
		if (docLine.getReceipt_InOutLine_ID() <= 0)
		{
			return ImmutableList.of();
		}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		//
		// Mark sure inbound costs are created
		docLine.createCostDetails(as);

		// If configured to not create accounting facts for Match PO documents then don't do it (08555)
		// IMPORTANT: we shall do absolutelly nothing if the sysconfig is enabled (gh6287)
		if (noFactRecords)
		{
			return ImmutableList.of();
		}

		final CostingMethod costingMethod = docLine.getProductCostingMethod(as);
		if (!CostingMethod.StandardCosting.equals(costingMethod))
		{
			return createFactsForStandardCosting(as);
		}

		else
		{
			return ImmutableList.of();
		}

	}

	private List<Fact> createFactsForStandardCosting(final AcctSchema as)
	{
		if (docLine.getQty().signum() == 0)
		{
			return ImmutableList.of();
		}

		//
		// Calculate PO Cost and Standard Cost
<<<<<<< HEAD
		final CostAmount poCost = docLine.getPOCostAmount(as);
=======
		final CostAmount poCost = docLine.getPOCostAmountInAcctCurrency(as);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final CostAmount standardCosts = docLine.getStandardCosts(as);
		final CostAmount difference = poCost.subtract(standardCosts);
		if (difference.signum() == 0)
		{
			return ImmutableList.of();
		}

		//
		// create Fact Header
<<<<<<< HEAD
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, PostingType.Actual);
		facts.add(fact);
=======
		final Fact fact = new Fact(this, as, PostingType.Actual);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		setC_Currency_ID(as.getCurrencyId());

		final boolean isReturnTrx = docLine.isReturnTrx();

		//
		// Product PPV
<<<<<<< HEAD
		final FactLine cr = fact.createLine(null,
				docLine.getAccount(ProductAcctType.PPV, as),
				difference.getCurrencyId(),
				difference.negateIf(isReturnTrx).getValue());
		if (cr != null)
		{
			cr.setQty(docLine.getQty().negateIf(isReturnTrx));
=======
		final FactLine cr = fact.createLine()
				.setDocLine(null)
				.setAccount(docLine.getAccount(ProductAcctType.P_PurchasePriceVariance_Acct, as))
				.setAmtSourceDrOrCr(difference.negateIf(isReturnTrx).toMoney())
				.setQty(docLine.getQty().negateIf(isReturnTrx))
				.buildAndAdd();
		if (cr != null)
		{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			updateFromPurchaseOrderLine(cr);
		}

		//
		// PPV Offset
<<<<<<< HEAD
		final FactLine dr = fact.createLine(null,
				getAccount(AccountType.PPVOffset, as),
				difference.getCurrencyId(),
				difference.negateIfNot(isReturnTrx).getValue());
		if (dr != null)
		{
			dr.setQty(docLine.getQty().negateIfNot(isReturnTrx));
=======
		final FactLine dr = fact.createLine()
				.setDocLine(null)
				.setAccount(getGLAccount(GLAccountType.PPVOffset, as))
				.setAmtSourceDrOrCr(difference.negateIfNot(isReturnTrx).toMoney())
				.setQty(docLine.getQty().negateIfNot(isReturnTrx))
				.buildAndAdd();
		if (dr != null)
		{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			updateFromPurchaseOrderLine(dr);
		}

		// Avoid usage of clearing accounts
		// If both accounts Purchase Price Variance and Purchase Price Variance Offset are equal
		// then remove the posting
		PostingEqualClearingAccontsUtils.removeFactLinesIfEqual(fact, dr, cr, this::isInterOrg);

		//
<<<<<<< HEAD
		return facts;
=======
		return ImmutableList.of(fact);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private void updateFromPurchaseOrderLine(final FactLine factLine)
	{
		if (factLine == null)
		{
			return;
		}

		final I_C_OrderLine orderLine = docLine.getOrderLine();
<<<<<<< HEAD
		factLine.setC_BPartner_ID(orderLine.getC_BPartner_ID());
		factLine.setC_Activity_ID(orderLine.getC_Activity_ID());
		factLine.setC_Campaign_ID(orderLine.getC_Campaign_ID());
		factLine.setC_Project_ID(orderLine.getC_Project_ID());
		factLine.setUser1_ID(orderLine.getUser1_ID());
		factLine.setUser2_ID(orderLine.getUser2_ID());
=======

		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(orderLine.getC_BPartner_ID(), orderLine.getC_BPartner_Location_ID());
		factLine.setBPartnerIdAndLocation(bPartnerLocationId.getBpartnerId(), bPartnerLocationId);
		factLine.setFromDimension(services.extractDimensionFromModel(orderLine));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * Verify if the posting involves two or more organizations
<<<<<<< HEAD
	 * 
=======
	 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return true if there are more than one org involved on the posting
	 */
	private boolean isInterOrg(final AcctSchema as)
	{
		final AcctSchemaElement orgElement = as.getSchemaElementByType(AcctSchemaElementType.Organization);
		if (orgElement == null || !orgElement.isBalanced())
		{
			// no org element or not need to be balanced
			return false;
		}

		// verify if org of receipt line is different from org of order line
		// ignoring invoice line org as not used in posting
		final I_M_InOutLine receiptLine = docLine.getReceiptLine();
		final I_C_OrderLine orderLine = docLine.getOrderLine();
		return receiptLine != null
				&& orderLine != null
				&& receiptLine.getAD_Org_ID() != orderLine.getAD_Org_ID();
	}
}   // Doc_MatchPO
