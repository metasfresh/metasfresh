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
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MInOut;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.CostAmount;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutLineId;
import de.metas.invoice.MatchInvId;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
=======
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.acct.accounts.BPartnerGroupAccountType;
import de.metas.acct.accounts.CostElementAccountType;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostAmountAndQty;
import de.metas.costing.CostElement;
import de.metas.costing.ShipmentCosts;
import de.metas.currency.CurrencyConversionContext;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.inout.InOutCost;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/**
 * Post Shipment/Receipt Documents.
 *
 * <pre>
 *  Table:              M_InOut (319)
 *  Document Types:     MMS, MMR
 * </pre>
 *
<<<<<<< HEAD
 * metas:
 * <li>copied from // metas: from https://adempiere.svn.sourceforge.net/svnroot/adempiere/branches/metas/mvcForms/base/src/org/compiere/acct/Doc_InOut.java, Rev 10177
 * <li>Changed for "US330: Geschaeftsvorfall (G113d): Summen-/ Saldenliste (2010070510000637)"
 *
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 *         <li>BF [ 2858043 ] Correct Included Tax in Average Costing
 *
 */
public class Doc_InOut extends Doc<DocLine_InOut>
{
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);

	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_InOut.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;

	private int m_Reversal_ID = 0;
	private String m_DocStatus = "";

	public Doc_InOut(final AcctDocContext ctx)
	{
		super(ctx);
=======
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 * <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * <li>BF [ 2858043 ] Correct Included Tax in Average Costing
 */
public class Doc_InOut extends Doc<DocLine_InOut>
{
	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_InOut.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;

	@NonNull final IInOutBL inOutBL;
	@NonNull final IOrderBL orderBL;
	@NonNull private final MatchInvoiceService matchInvoiceService;
	@NonNull private final OrderCostService orderCostService;

	private InOutId m_reversalId = null;
	private DocStatus m_docStatus = DocStatus.Unknown;

	public Doc_InOut(
			@NonNull final IInOutBL inOutBL,
			@NonNull final IOrderBL orderBL,
			@NonNull final AcctDocContext ctx)
	{
		super(ctx);
		this.inOutBL = inOutBL;
		this.orderBL = orderBL;
		this.matchInvoiceService = services.getMatchInvoiceService();
		this.orderCostService = services.getOrderCostService();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	protected void loadDocumentDetails()
	{
		setNoCurrency();
		final I_M_InOut inout = getModel(I_M_InOut.class);
		setDateDoc(inout.getMovementDate());
<<<<<<< HEAD
		m_Reversal_ID = inout.getReversal_ID();// store original (voided/reversed) document
		m_DocStatus = inout.getDocStatus();
=======
		m_reversalId = InOutId.ofRepoIdOrNull(inout.getReversal_ID());// store original (voided/reversed) document
		m_docStatus = DocStatus.ofNullableCodeOrUnknown(inout.getDocStatus());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		setDocLines(loadLines(inout));
	}

	private List<DocLine_InOut> loadLines(final I_M_InOut inout)
	{
<<<<<<< HEAD
		final List<DocLine_InOut> docLines = new ArrayList<>();
		for (final I_M_InOutLine inoutLine : inOutBL.getLines(inout))
=======
		final ImmutableListMultimap<InOutAndLineId, InOutCost> inoutCostsByInOutLineId = Multimaps.index(
				orderCostService.getByInOutId(InOutId.ofRepoId(inout.getM_InOut_ID())),
				InOutCost::getInoutAndLineId);

		final List<I_M_InOutLine> inoutLines = inOutBL.getLines(inout);

		final List<DocLine_InOut> docLines = new ArrayList<>();
		for (final I_M_InOutLine inoutLine : inoutLines)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			if (inoutLine.isDescription()
					|| inoutLine.getM_Product_ID() <= 0
					|| inoutLine.getMovementQty().signum() == 0)
			{
				continue;
			}

<<<<<<< HEAD
			final DocLine_InOut docLine = DocLine_InOut.builder()
					.doc(this)
					.inoutLine(inoutLine)
					.build();
=======
			final ImmutableList<InOutCost> inoutCosts = inoutCostsByInOutLineId.get(InOutAndLineId.ofRepoId(inoutLine.getM_InOut_ID(), inoutLine.getM_InOutLine_ID()));

			final DocLine_InOut docLine = new DocLine_InOut(
					this,
					inoutLine,
					inoutCosts
			);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			if (inOutBL.isReversal(inoutLine))
			{
				// NOTE: to be consistent with current logic
				// we are setting docLine's ReversalLine_ID only if this is the actual reversal line
				// and not the line which was reversed
				docLine.setReversalLine_ID(inoutLine.getReversalLine_ID());
			}

			//
			docLines.add(docLine);
		}

		//
		return docLines;
<<<<<<< HEAD
	}	// loadLines

	/** @return zero (always balanced) */
=======
	}

	/**
	 * @return zero (always balanced)
	 */
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Override
	protected BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

	/**
	 * Create Facts (the accounting logic) for MMS, MMR.
	 *
	 * <pre>
	 *  Shipment
	 *      CoGS (RevOrg)   DR
	 *      Inventory               CR
	 *  Shipment of Project Issue
	 *      CoGS            DR
	 *      Project                 CR
	 *  Receipt
	 *      Inventory       DR
	 *      NotInvoicedReceipt      CR
	 * </pre>
	 *
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	protected List<Fact> createFacts(final AcctSchema as)
	{
		setC_Currency_ID(as.getCurrencyId());

<<<<<<< HEAD
		final String docBaseType = getDocumentType();

		//
		// *** Sales - Shipment
		if (DOCTYPE_MatShipment.equals(docBaseType) && isSOTrx())
=======
		final DocBaseAndSubType docBaseAndSubType = getDocBaseAndSubType();
		final DocBaseType docBaseType = docBaseAndSubType.getDocBaseType();

		//
		// *** Sales - Shipment
		if (docBaseType.isShipment() && isSOTrx())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return createFacts_SalesShipment(as);
		}
		//
		// *** Sales - Return
<<<<<<< HEAD
		else if (DOCTYPE_MatReceipt.equals(docBaseType) && isSOTrx())
=======
		else if (docBaseType.isReceipt() && isSOTrx())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return createFacts_SalesReturn(as);
		}
		//
		// *** Purchasing - Receipt
<<<<<<< HEAD
		else if (DOCTYPE_MatReceipt.equals(docBaseType) && !isSOTrx())
=======
		else if (docBaseType.isReceipt() && !isSOTrx())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return createFacts_PurchasingReceipt(as);
		}
		// *** Purchasing - return
<<<<<<< HEAD
		else if (DOCTYPE_MatShipment.equals(docBaseType) && !isSOTrx())
=======
		else if (docBaseType.isShipment() && !isSOTrx())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return createFacts_PurchasingReturn(as);
		}
		else
		{
			throw newPostingException()
<<<<<<< HEAD
					.setDetailMessage("DocumentType unknown: " + docBaseType);
		}
	}

	private List<Fact> createFacts_SalesShipment(final AcctSchema as)
	{
		final Fact fact = new Fact(this, as, PostingType.Actual);
		getDocLines().forEach(line -> createFacts_SalesShipmentLine(fact, line));
		return ImmutableList.of(fact);
	}

	private void createFacts_SalesShipmentLine(final Fact fact, final DocLine_InOut line)
=======
					.setDetailMessage("DocumentType unknown: " + docBaseAndSubType);
		}
	}

	@NonNull
	private Fact newFacts(final AcctSchema as)
	{
		return new Fact(this, as, PostingType.Actual)
				.setCurrencyConversionContext(getCurrencyConversionContext(as));
	}

	private List<Fact> createFacts_SalesShipment(final AcctSchema as)
	{
		final ArrayList<Fact> facts = new ArrayList<>();
		getDocLines().forEach(line -> facts.addAll(createFacts_SalesShipmentLine(as, line)));
		return facts;
	}

	private List<Fact> createFacts_SalesShipmentLine(final AcctSchema as, final DocLine_InOut line)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		// Skip not stockable (e.g. service products) because they have no cost
		if (!line.isItem())
		{
<<<<<<< HEAD
			return;
		}

		final AcctSchema as = fact.getAcctSchema();
		final CostAmount costs = line.getCreateShipmentCosts(as);

		//
		// CoGS DR
		final FactLine dr = fact.createLine(line,
				line.getAccount(ProductAcctType.Cogs, as),
				costs.getCurrencyId(),
				mkCostsValueToUse(costs), null);
		if (dr == null)
		{
			throw newPostingException().setDetailMessage("FactLine DR not created: " + line);
		}
		dr.setM_Locator_ID(line.getM_Locator_ID());
		dr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
		dr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
		dr.setAD_Org_ID(line.getOrderOrgId().getRepoId());		// Revenue X-Org
		dr.setQty(line.getQty().negate());

		//
		// Inventory CR
		final FactLine cr = fact.createLine(line,
				line.getAccount(ProductAcctType.Asset, as),
				costs.getCurrencyId(),
				null, mkCostsValueToUse(costs));
		if (cr == null)
		{
			throw newPostingException().setDetailMessage("FactLine CR not created: " + line);
		}
		cr.setM_Locator_ID(line.getM_Locator_ID());
		cr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
		cr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
=======
			return ImmutableList.of();
		}

		final ArrayList<Fact> facts = new ArrayList<>();
		final ShipmentCosts costs = line.getCreateShipmentCosts(as);

		createFacts_SalesShipmentLine(
				facts,
				as,
				line,
				ProductAcctType.P_COGS_Acct,
				ProductAcctType.P_Asset_Acct,
				costs.getShippedButNotNotified(),
				true);

		return facts;
	}

	private void createFacts_SalesShipmentLine(
			@NonNull final ArrayList<Fact> facts,
			@NonNull final AcctSchema as,
			@NonNull final DocLine_InOut line,
			@NonNull final ProductAcctType debitAccount,
			@NonNull final ProductAcctType creditAccount,
			@NonNull final CostAmountAndQty amountAndQty,
			boolean addZeroLine)
	{
		if (!addZeroLine && amountAndQty.isZero())
		{
			return;
		}

		final CostAmount amount = roundToStdPrecision(amountAndQty.getAmt());
		final Quantity qty = amountAndQty.getQty();

		final Fact fact = newFacts(as);
		fact.createLine()
				.setDocLine(line)
				.setAccount(line.getAccount(debitAccount, as))
				.setAmt(amount, null)
				.setQty(qty)
				.orgId(debitAccount.isCOGS() ? line.getOrderOrgId() : line.getOrgId())
				.locatorId(line.getM_Locator_ID())
				.fromLocationOfLocator(line.getM_Locator_ID())
				.toLocationOfBPartner(getBPartnerLocationId())
				.alsoAddZeroLineIf(addZeroLine)
				.buildAndAdd();
		fact.createLine()
				.setDocLine(line)
				.setAccount(line.getAccount(creditAccount, as))
				.setAmt(null, amount)
				.orgId(creditAccount.isCOGS() ? line.getOrderOrgId() : line.getOrgId())
				.setQty(qty.negate())
				.locatorId(line.getM_Locator_ID())
				.fromLocationOfLocator(line.getM_Locator_ID())
				.toLocationOfBPartner(getBPartnerLocationId())
				.alsoAddZeroLineIf(addZeroLine)
				.buildAndAdd();

		if (!fact.isEmpty())
		{
			facts.add(fact);
		}
	}

	private void createFacts_SalesReturnLine(
			@NonNull final ArrayList<Fact> facts,
			@NonNull final AcctSchema as,
			@NonNull final DocLine_InOut line,
			@NonNull final ProductAcctType debitAccount,
			@NonNull final ProductAcctType creditAccount,
			@NonNull final CostAmountAndQty amountAndQty,
			boolean addZeroLine)
	{
		if (!addZeroLine && amountAndQty.isZero())
		{
			return;
		}

		final CostAmount amount = roundToStdPrecision(amountAndQty.getAmt());
		final Quantity qty = amountAndQty.getQty();

		final Fact fact = newFacts(as);
		fact.createLine()
				.setDocLine(line)
				.setAccount(line.getAccount(debitAccount, as))
				.setAmt(amount, null)
				.setQty(qty)
				.orgId(debitAccount.isCOGS() ? line.getOrderOrgId() : line.getOrgId())
				.locatorId(line.getM_Locator_ID())
				.fromLocationOfBPartner(getBPartnerLocationId())
				.toLocationOfLocator(line.getM_Locator_ID())
				.alsoAddZeroLineIf(addZeroLine)
				.buildAndAdd();
		fact.createLine()
				.setDocLine(line)
				.setAccount(line.getAccount(creditAccount, as))
				.setAmt(null, amount)
				.setQty(qty.negate())
				.orgId(creditAccount.isCOGS() ? line.getOrderOrgId() : line.getOrgId())
				.locatorId(line.getM_Locator_ID())
				.fromLocationOfBPartner(getBPartnerLocationId())
				.toLocationOfLocator(line.getM_Locator_ID())
				.alsoAddZeroLineIf(addZeroLine)
				.buildAndAdd();

		if (!fact.isEmpty())
		{
			facts.add(fact);
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private List<Fact> createFacts_SalesReturn(final AcctSchema as)
	{
<<<<<<< HEAD
		final Fact fact = new Fact(this, as, PostingType.Actual);
		getDocLines().forEach(line -> createFacts_SalesReturnLine(fact, line));

		return ImmutableList.of(fact);
	}

	private void createFacts_SalesReturnLine(final Fact fact, final DocLine_InOut line)
=======
		final ArrayList<Fact> facts = new ArrayList<>();
		getDocLines().forEach(line -> facts.addAll(createFacts_SalesReturnLine(as, line)));
		return facts;
	}

	private List<Fact> createFacts_SalesReturnLine(final AcctSchema as, final DocLine_InOut line)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		// Skip not stockable (e.g. service products) because they have no cost
		if (!line.isItem())
		{
<<<<<<< HEAD
			return;
		}

		final AcctSchema as = fact.getAcctSchema();
		final CostAmount costs = line.getCreateShipmentCosts(as);

		//
		// Inventory DR
		final FactLine dr = fact.createLine(line,
				line.getAccount(ProductAcctType.Asset, as),
				costs.getCurrencyId(),
				mkCostsValueToUse(costs), null);
		if (dr == null)
		{
			throw newPostingException().addDetailMessage("FactLine DR not created: " + line);
		}
		dr.setM_Locator_ID(line.getM_Locator_ID());
		dr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
		dr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc

		//
		// CoGS CR
		final FactLine cr = fact.createLine(line,
				line.getAccount(ProductAcctType.Cogs, as),
				costs.getCurrencyId(),
				null, mkCostsValueToUse(costs));
		if (cr == null)
		{
			throw newPostingException().setDetailMessage("FactLine CR not created: " + line);
		}
		cr.setM_Locator_ID(line.getM_Locator_ID());
		cr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
		cr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
		cr.setAD_Org_ID(line.getOrderOrgId());		// Revenue X-Org
		cr.setQty(line.getQty().negate());
=======
			return ImmutableList.of();
		}

		final ArrayList<Fact> facts = new ArrayList<>();
		final ShipmentCosts costs = line.getCreateShipmentCosts(as);

		createFacts_SalesReturnLine(
				facts,
				as,
				line,
				ProductAcctType.P_Asset_Acct,
				ProductAcctType.P_COGS_Acct,
				costs.getShippedButNotNotified(),
				true);

		return facts;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private List<Fact> createFacts_PurchasingReceipt(final AcctSchema as)
	{
<<<<<<< HEAD
		final Fact fact = new Fact(this, as, PostingType.Actual);
		getDocLines().forEach(line -> createFacts_PurchasingReceiptLine(fact, line));

		return ImmutableList.of(fact);
	}

	private void createFacts_PurchasingReceiptLine(final Fact fact, final DocLine_InOut line)
=======
		return getDocLines()
				.stream()
				.flatMap(line -> createFacts_PurchasingReceiptLine(as, line).stream())
				.collect(ImmutableList.toImmutableList());
	}

	private List<Fact> createFacts_PurchasingReceiptLine(final AcctSchema as, final DocLine_InOut line)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		// Skip not stockable (e.g. service products) because they have no cost
		if (!line.isItem())
		{
<<<<<<< HEAD
			return;
		}

		final AcctSchema as = fact.getAcctSchema();
		final CostAmount costs = line.getCreateReceiptCosts(as);

		//
		// Inventory/Asset DR
		final FactLine dr = fact.createLine(line,
				line.getProductAssetAccount(as),
				costs.getCurrencyId(),
				mkCostsValueToUse(costs), null);
=======
			return ImmutableList.of();
		}

		final ArrayList<Fact> facts = new ArrayList<>();
		final AggregatedCostAmount aggregatedCosts = line.getCreateReceiptCosts(as).retainOnlyAccountable(as);
		for (final CostElement costElement : aggregatedCosts.getCostElements())
		{
			final CostAmount costs = aggregatedCosts.getCostAmountForCostElement(costElement).getMainAmt();
			facts.add(createFacts_PurchasingReceiptLine(as, line, costElement, costs));
		}

		return facts;
	}

	private Fact createFacts_PurchasingReceiptLine(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_InOut line,
			@NonNull final CostElement costElement,
			@NonNull final CostAmount costs)
	{
		final Fact fact = newFacts(as);

		//
		// Inventory/Asset DR
		final FactLine dr = fact.createLine()
				.setDocLine(line)
				.setAccount(line.getProductAssetAccount(as))
				.setAmt(roundToStdPrecision(costs), null)
				.setQty(line.getQty()) // (+) Qty
				.bPartnerAndLocationId(line.getBPartnerId(costElement.getId()), line.getBPartnerLocationId(costElement.getId()))
				.locatorId(line.getM_Locator_ID())
				.fromLocationOfBPartner(line.getBPartnerLocationId(costElement.getId()))
				.toLocationOfLocator(line.getM_Locator_ID())
				.costElement(costElement)
				.buildAndAdd();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (dr == null)
		{
			throw newPostingException().setDetailMessage("DR not created: " + line);
		}
<<<<<<< HEAD
		dr.setM_Locator_ID(line.getM_Locator_ID());
		dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
		dr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc

		//
		// NotInvoicedReceipt CR
		final FactLine cr = fact.createLine(line,
				getAccount(AccountType.NotInvoicedReceipts, as),
				costs.getCurrencyId(),
				null, mkCostsValueToUse(costs));
		//
=======

		//
		// NotInvoicedReceipt CR
		final FactLine cr = fact.createLine()
				.setDocLine(line)
				.setAccount(costElement.isMaterial()
						? getBPGroupAccount(BPartnerGroupAccountType.NotInvoicedReceipts, as)
						: getCostElementAccount(as, costElement.getId(), CostElementAccountType.P_CostClearing_Acct))
				.setAmt(null, roundToStdPrecision(costs))
				.setQty(line.getQty().negate()) // (-) Qty
				.bPartnerAndLocationId(line.getBPartnerId(costElement.getId()), line.getBPartnerLocationId(costElement.getId()))
				.locatorId(line.getM_Locator_ID())
				.fromLocationOfBPartner(line.getBPartnerLocationId(costElement.getId()))
				.toLocationOfLocator(line.getM_Locator_ID())
				.costElement(costElement)
				.buildAndAdd();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (cr == null)
		{
			throw newPostingException().setDetailMessage("CR not created: " + line);
		}
<<<<<<< HEAD
		cr.setM_Locator_ID(line.getM_Locator_ID());
		cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
		cr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
		cr.setQty(line.getQty().negate());
=======

		return fact;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private List<Fact> createFacts_PurchasingReturn(final AcctSchema as)
	{
<<<<<<< HEAD
		final Fact fact = new Fact(this, as, PostingType.Actual);
=======
		final Fact fact = newFacts(as);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		getDocLines().forEach(line -> createFacts_PurchasingReturnLine(fact, line));

		return ImmutableList.of(fact);
	}

	private void createFacts_PurchasingReturnLine(final Fact fact, final DocLine_InOut line)
	{
		// Skip not stockable (e.g. service products) because they have no cost
		if (!line.isItem())
		{
			return;
		}

		final AcctSchema as = fact.getAcctSchema();
<<<<<<< HEAD
		final CostAmount costs = line.getCreateReceiptCosts(as);

		//
		// NotInvoicedReceipt DR
		final FactLine dr = fact.createLine(line, getAccount(AccountType.NotInvoicedReceipts, as),
				costs.getCurrencyId(),
				mkCostsValueToUse(costs), null);
=======
		final CostAmount costs = line.getCreateReceiptCosts(as).getTotalAmountToPost(as).getMainAmt();

		//
		// NotInvoicedReceipt DR
		final FactLine dr = fact.createLine()
				.setDocLine(line)
				.setAccount(getBPGroupAccount(BPartnerGroupAccountType.NotInvoicedReceipts, as))
				.setAmt(roundToStdPrecision(costs), null)
				.setQty(line.getQty().negate())
				.locatorId(line.getM_Locator_ID())
				.fromLocationOfBPartner(getBPartnerLocationId())
				.toLocationOfLocator(line.getM_Locator_ID())
				.buildAndAdd();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (dr == null)
		{
			throw newPostingException().setDetailMessage("DR not created: " + line);
		}
<<<<<<< HEAD
		dr.setM_Locator_ID(line.getM_Locator_ID());
		dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
		dr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
		dr.setQty(line.getQty().negate());
		if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
		{
			// Set AmtAcctDr from Original Shipment/Receipt
			if (!dr.updateReverseLine(getTableId(I_M_InOut.class), m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
=======
		if (m_docStatus.isReversed() && m_reversalId != null && line.getReversalLine_ID() > 0)
		{
			// Set AmtAcctDr from Original Shipment/Receipt
			if (!dr.updateReverseLine(I_M_InOut.Table_Name, m_reversalId.getRepoId(), line.getReversalLine_ID(), BigDecimal.ONE))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			{
				throw newPostingException().setDetailMessage("Original Receipt not posted yet");
			}
		}

		//
		// Inventory/Asset CR
<<<<<<< HEAD
		final FactLine cr = fact.createLine(line,
				line.getProductAssetAccount(as),
				costs.getCurrencyId(),
				null, mkCostsValueToUse(costs));
=======
		final FactLine cr = fact.createLine()
				.setDocLine(line)
				.setAccount(line.getProductAssetAccount(as))
				.setAmt(null, roundToStdPrecision(costs))
				.setQty(line.getQty())
				.locatorId(line.getM_Locator_ID())
				.fromLocationOfBPartner(getBPartnerLocationId())
				.toLocationOfLocator(line.getM_Locator_ID())
				.buildAndAdd();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (cr == null)
		{
			throw newPostingException().setDetailMessage("CR not created: " + line);
		}
<<<<<<< HEAD
		cr.setM_Locator_ID(line.getM_Locator_ID());
		cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
		cr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
		if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
		{
			// Set AmtAcctCr from Original Shipment/Receipt
			if (!cr.updateReverseLine(getTableId(I_M_InOut.class), m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
=======
		if (m_docStatus.isReversed() && m_reversalId != null && line.getReversalLine_ID() > 0)
		{
			// Set AmtAcctCr from Original Shipment/Receipt
			if (!cr.updateReverseLine(I_M_InOut.Table_Name, m_reversalId.getRepoId(), line.getReversalLine_ID(), BigDecimal.ONE))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			{
				throw newPostingException().setDetailMessage("Original Receipt not posted yet");
			}
		}
	}

	@Override
	protected void afterPost()
	{
		postDependingMatchInvsIfNeeded();
	}

<<<<<<< HEAD
	private final void postDependingMatchInvsIfNeeded()
=======
	private void postDependingMatchInvsIfNeeded()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (!services.getSysConfigBooleanValue(SYSCONFIG_PostMatchInvs, DEFAULT_PostMatchInvs))
		{
			return;
		}

		final ImmutableSet<InOutLineId> inoutLineIds = getDocLines()
				.stream()
				.map(DocLine_InOut::getInOutLineId)
				.collect(ImmutableSet.toImmutableSet());
		if (inoutLineIds.isEmpty())
		{
			return;
		}

<<<<<<< HEAD
		final Set<MatchInvId> matchInvIds = matchInvDAO.retrieveIdsProcessedButNotPostedForInOutLines(inoutLineIds);
		postDependingDocuments(I_M_MatchInv.Table_Name, matchInvIds);
	}

	/**
	 * Creating a not null cost value whose precision matches the given <code>C_Currency_ID</code>'s.
	 * Goal of this method is to avoid the warnings in FactLine.setAmtSource().
	 *
	 * @return costs rounded to currency precision
	 */
	private BigDecimal mkCostsValueToUse(final CostAmount costs)
	{
		if (costs == null)
		{
			return BigDecimal.ZERO;
		}

		final CurrencyId currencyId = costs.getCurrencyId();
		final CurrencyPrecision precision = services.getCurrencyStandardPrecision(currencyId);
		return precision.round(costs.getValue());
=======
		final Set<MatchInvId> matchInvIds = matchInvoiceService.getIdsProcessedButNotPostedByInOutLineIds(inoutLineIds);
		postDependingDocuments(I_M_MatchInv.Table_Name, matchInvIds);
	}

	@NonNull
	private CostAmount roundToStdPrecision(@NonNull final CostAmount costs)
	{
		return costs.round(services::getCurrencyStandardPrecision);
	}

	public CurrencyConversionContext getCurrencyConversionContext(final AcctSchema ignoredAcctSchema)
	{
		final I_M_InOut inout = getModel(I_M_InOut.class);
		return inOutBL.getCurrencyConversionContext(inout);
	}

	@Nullable
	@Override
	protected OrderId getSalesOrderId()
	{
		final Optional<OrderId> optionalSalesOrderId = CollectionUtils.extractSingleElementOrDefault(
				getDocLines(),
				docLine -> Optional.ofNullable(docLine.getSalesOrderId()),
				Optional.empty());

		//noinspection DataFlowIssue
		return optionalSalesOrderId.orElse(null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}   // Doc_InOut
