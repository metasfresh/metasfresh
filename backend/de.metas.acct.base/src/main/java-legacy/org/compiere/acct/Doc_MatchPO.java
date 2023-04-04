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
import de.metas.acct.accounts.GLAccountType;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.PostingType;
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
 * <pre>
 *  Table:              C_MatchPO (473)
 *  Document Types:     MXP
 * </pre>
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
	private boolean noFactRecords = false;

	public Doc_MatchPO(final AcctDocContext ctx)
	{
		super(ctx, DocBaseType.MatchPO);
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
	 *
	 * <pre>
	 *      Product PPV     <difference>
	 *      PPV_Offset                  <difference>
	 * </pre>
	 *
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
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
		final CostAmount poCost = docLine.getPOCostAmountInAcctCurrency(as);
		final CostAmount standardCosts = docLine.getStandardCosts(as);
		final CostAmount difference = poCost.subtract(standardCosts);
		if (difference.signum() == 0)
		{
			return ImmutableList.of();
		}

		//
		// create Fact Header
		final Fact fact = new Fact(this, as, PostingType.Actual);
		setC_Currency_ID(as.getCurrencyId());

		final boolean isReturnTrx = docLine.isReturnTrx();

		//
		// Product PPV
		final FactLine cr = fact.createLine()
				.setDocLine(null)
				.setAccount(docLine.getAccount(ProductAcctType.P_PurchasePriceVariance_Acct, as))
				.setAmtSourceDrOrCr(difference.negateIf(isReturnTrx).toMoney())
				.buildAndAdd();
		if (cr != null)
		{
			cr.setQty(docLine.getQty().negateIf(isReturnTrx));
			updateFromPurchaseOrderLine(cr);
		}

		//
		// PPV Offset
		final FactLine dr = fact.createLine()
				.setDocLine(null)
				.setAccount(getGLAccount(GLAccountType.PPVOffset, as))
				.setAmtSourceDrOrCr(difference.negateIfNot(isReturnTrx).toMoney())
				.buildAndAdd();
		if (dr != null)
		{
			dr.setQty(docLine.getQty().negateIfNot(isReturnTrx));
			updateFromPurchaseOrderLine(dr);
		}

		// Avoid usage of clearing accounts
		// If both accounts Purchase Price Variance and Purchase Price Variance Offset are equal
		// then remove the posting
		PostingEqualClearingAccontsUtils.removeFactLinesIfEqual(fact, dr, cr, this::isInterOrg);

		//
		return ImmutableList.of(fact);
	}

	private void updateFromPurchaseOrderLine(final FactLine factLine)
	{
		if (factLine == null)
		{
			return;
		}

		final I_C_OrderLine orderLine = docLine.getOrderLine();

		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(orderLine.getC_BPartner_ID(), orderLine.getC_BPartner_Location_ID());
		factLine.setBPartnerIdAndLocation(bPartnerLocationId.getBpartnerId(), bPartnerLocationId);
		factLine.setFromDimension(services.extractDimensionFromModel(orderLine));
	}

	/**
	 * Verify if the posting involves two or more organizations
	 *
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
