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

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaElement;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostAmount;
import de.metas.costing.CostingMethod;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;

/**
 * Post MatchPO Documents.
 * 
 * <pre>
 *  Table:              C_MatchPO (473)
 *  Document Types:     MXP
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: Doc_MatchPO.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_MatchPO extends Doc<DocLine_MatchPO>
{
	private static final Logger logger = LogManager.getLogger(Doc_MatchPO.class);

	/** Shall we create accounting facts (08555) */
	private static final String SYSCONFIG_NoFactRecords = "org.compiere.acct.Doc_MatchPO.NoFactAccts";
	private static final boolean DEFAULT_NoFactRecords = true;

	/** pseudo line */
	private DocLine_MatchPO docLine;

	// private int m_C_OrderLine_ID = 0;
	// private I_C_OrderLine m_oLine = null;
	// //
	// private int m_M_InOutLine_ID = 0;
	// private I_M_InOutLine m_ioLine = null;

	/** Shall we create accounting facts? (08555) */
	private boolean noFactRecords = false;

	public Doc_MatchPO(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_MatMatchPO);
	}

	@Override
	protected void loadDocumentDetails()
	{
		setC_Currency_ID(Doc.NO_CURRENCY);
		docLine = new DocLine_MatchPO(getModel(I_M_MatchPO.class), this);

		setDateDoc(docLine.getDateDoc());

		this.noFactRecords = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_NoFactRecords, DEFAULT_NoFactRecords);
	}

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 * 
	 * @return Zero - always balanced
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}   // getBalance

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
	public List<Fact> createFacts(final MAcctSchema as)
	{
		//
		if (docLine.getReceipt_InOutLine_ID() <= 0)
		{
			throw newPostingException()
					.setPreserveDocumentPostedStatus()
					.setDetailMessage("Shall be posted again when receipt line is set");
		}

		//
		// Mark sure inbound costs are created
		docLine.createCostDetails(as);

		// If configured to not create accounting facts for Match PO documents
		// then don't do it (08555)
		if (noFactRecords)
		{
			return ImmutableList.of();
		}

		if (docLine.getQty().signum() == 0)
		{
			return ImmutableList.of();
		}

		//
		final CostingMethod costingMethod = docLine.getProductCostingMethod(as);
		if (!CostingMethod.StandardCosting.equals(costingMethod))
		{
			return ImmutableList.of();
		}

		//
		// Calculate PO Cost and Standard Cost
		final CostAmount poCost = docLine.getPOCostAmount(as);
		final CostAmount standardCosts = docLine.getStandardCosts(as);
		final CostAmount difference = poCost.subtract(standardCosts);
		if (difference.signum() == 0)
		{
			return ImmutableList.of();
		}

		//
		// create Fact Header
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);
		setC_Currency_ID(as.getC_Currency_ID());

		final boolean isReturnTrx = docLine.isReturnTrx();

		//
		// Product PPV
		final FactLine cr = fact.createLine(null,
				docLine.getAccount(ProductAcctType.PPV, as),
				difference.getCurrencyId(),
				difference.negateIf(isReturnTrx).getValue());
		if (cr != null)
		{
			cr.setQty(docLine.getQty().negateIf(isReturnTrx));
			updateFromPurchaseOrderLine(cr);
		}

		//
		// PPV Offset
		final FactLine dr = fact.createLine(null,
				getAccount(Doc.ACCTTYPE_PPVOffset, as),
				difference.getCurrencyId(),
				difference.negateIfNot(isReturnTrx).getValue());
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
		return facts;
	}

	private void updateFromPurchaseOrderLine(final FactLine factLine)
	{
		if (factLine == null)
		{
			return;
		}

		final I_C_OrderLine orderLine = docLine.getOrderLine();
		factLine.setC_UOM_ID(orderLine.getPrice_UOM_ID());
		factLine.setC_BPartner_ID(orderLine.getC_BPartner_ID());
		factLine.setC_Activity_ID(orderLine.getC_Activity_ID());
		factLine.setC_Campaign_ID(orderLine.getC_Campaign_ID());
		factLine.setC_Project_ID(orderLine.getC_Project_ID());
		factLine.setUser1_ID(orderLine.getUser1_ID());
		factLine.setUser2_ID(orderLine.getUser2_ID());
	}

	/**
	 * Verify if the posting involves two or more organizations
	 * 
	 * @return true if there are more than one org involved on the posting
	 */
	private boolean isInterOrg(final MAcctSchema as)
	{
		final MAcctSchemaElement elementorg = as.getAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_Organization);
		if (elementorg == null || !elementorg.isBalanced())
		{
			// no org element or not need to be balanced
			return false;
		}

		// verify if org of receipt line is different from org of order line
		// ignoring invoice line org as not used in posting
		final I_M_InOutLine receiptLine = docLine.getReceiptLine();
		final I_C_OrderLine orderLine = docLine.getOrderLine();
		if (receiptLine != null
				&& orderLine != null
				&& receiptLine.getAD_Org_ID() != orderLine.getAD_Org_ID())
		{
			return true;
		}

		return false;
	}
}   // Doc_MatchPO
