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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MInOut;
import org.compiere.model.MProduct;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailService;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.order.IOrderLineBL;
import de.metas.product.IProductBL;
import de.metas.tax.api.ITaxBL;

/**
 * Post Shipment/Receipt Documents.
 * 
 * <pre>
 *  Table:              M_InOut (319)
 *  Document Types:     MMS, MMR
 * </pre>
 * 
 * metas:
 * <li>copied from // metas: from https://adempiere.svn.sourceforge.net/svnroot/adempiere/branches/metas/mvcForms/base/src/org/compiere/acct/Doc_InOut.java, Rev 10177
 * <li>Changed for "US330: Geschaeftsvorfall (G113d): Summen-/ Saldenliste (2010070510000637)"
 * <li>Goal: Use {@link MCost#getSeedCosts(MProduct, int, MAcctSchema, int, String, int)}, if a product has no cost yet
 * 
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 *         <li>BF [ 2858043 ] Correct Included Tax in Average Costing
 * 
 */
public class Doc_InOut extends Doc
{
	// private static final Logger logger = LogManager.getLogger(Doc_InOut.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final ICostDetailService costDetailService = Services.get(ICostDetailService.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_InOut.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;

	public Doc_InOut(final IDocBuilder docBuilder)
	{
		super(docBuilder);
	}

	private int m_Reversal_ID = 0;
	private String m_DocStatus = "";

	@Override
	protected String loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		final I_M_InOut inout = InterfaceWrapperHelper.create(getPO(), I_M_InOut.class);
		setDateDoc(inout.getMovementDate());
		m_Reversal_ID = inout.getReversal_ID();// store original (voided/reversed) document
		m_DocStatus = inout.getDocStatus();
		p_lines = loadLines(inout);
		return null;
	}

	private DocLine[] loadLines(final I_M_InOut inout)
	{
		final boolean isSOTrx = DOCTYPE_MatShipment.equals(getDocumentType());

		final List<DocLine> docLines = new ArrayList<>();
		for (final I_M_InOutLine inoutLine : inOutDAO.retrieveAllLines(inout))
		{
			if (inoutLine.isDescription()
					|| inoutLine.getM_Product_ID() == 0
					|| inoutLine.getMovementQty().signum() == 0)
			{
				continue;
			}

			final DocLine docLine = new DocLine(InterfaceWrapperHelper.getPO(inoutLine), this);

			if (inOutBL.isReversal(inoutLine))
			{
				// NOTE: to be consistent with current logic
				// we are setting docLine's ReversalLine_ID only if this is the actual reversal line
				// and not the line which was reversed
				docLine.setReversalLine_ID(inoutLine.getReversalLine_ID());
			}

			final BigDecimal qty = inoutLine.getMovementQty();
			docLine.setQty(qty, isSOTrx);

			// Define if Outside Processing
			if (inoutLine.getC_OrderLine_ID() > 0)
			{
				final String sql = "SELECT PP_Cost_Collector_ID  FROM C_OrderLine WHERE C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL";
				int PP_Cost_Collector_ID = DB.getSQLValueEx(getTrxName(), sql, new Object[] { inoutLine.getC_OrderLine_ID() });
				docLine.setPP_Cost_Collector_ID(PP_Cost_Collector_ID);
			}

			//
			docLines.add(docLine);
		}

		//
		return docLines.toArray(new DocLine[docLines.size()]);
	}	// loadLines

	/** @return zero (always balanced) */
	@Override
	public BigDecimal getBalance()
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
	public List<Fact> createFacts(final MAcctSchema as)
	{
		setC_Currency_ID(as.getC_Currency_ID());

		final String docBaseType = getDocumentType();

		//
		// *** Sales - Shipment
		if (DOCTYPE_MatShipment.equals(docBaseType) && isSOTrx())
		{
			return createFacts_SalesShipment(as);
		}
		//
		// *** Sales - Return
		else if (DOCTYPE_MatReceipt.equals(docBaseType) && isSOTrx())
		{
			return createFacts_SalesReturn(as);
		}
		//
		// *** Purchasing - Receipt
		else if (DOCTYPE_MatReceipt.equals(docBaseType) && !isSOTrx())
		{
			return createFacts_PurchasingReceipt(as);
		}
		// *** Purchasing - return
		else if (DOCTYPE_MatShipment.equals(docBaseType) && !isSOTrx())
		{
			return createFacts_PurchasingReturn(as);
		}
		else
		{
			throw newPostingException()
					.setDetailMessage("DocumentType unknown: " + docBaseType);
		}
	}

	private List<Fact> createFacts_SalesShipment(final MAcctSchema as)
	{
		final int acctCurrencyId = as.getC_Currency_ID();

		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		for (final DocLine line : p_lines)
		{
			// Skip not stockable (e.g. service products) because they have no cost
			final I_M_Product product = line.getProduct();
			if (!productBL.isStocked(product))
			{
				continue;
			}

			// MZ Goodwill
			// if Shipment CostDetail exist then get Cost from Cost Detail
			// metas-ts: US330: call with zeroCostsOK=false, because we want the system to return the result of MCost.getSeedCosts(), if there are no current costs yet
			BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), false, CostingDocumentRef.ofShipmentLineId(line.get_ID()));
			if (ProductCost.isNoCosts(costs) || costs.signum() == 0)
			{
				final String costingMethod = productBL.getCostingMethod(product, as);
				if (!MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
				{
					// 08447: we shall allow zero costs in case CostingMethod=Standard costing
					throw newPostingException()
							.setDetailMessage("No Costs for product=" + line.getProduct()
									+ ", product is stocked and costing method=" + costingMethod + " is != " + MAcctSchema.COSTINGMETHOD_StandardCosting);
				}
			}

			//
			// CoGS DR
			final FactLine dr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Cogs, as), acctCurrencyId, mkCostsValueToUse(acctCurrencyId, costs), null);
			if (dr == null)
			{
				throw newPostingException().setDetailMessage("FactLine DR not created: " + line);
			}
			dr.setM_Locator_ID(line.getM_Locator_ID());
			dr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
			dr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
			dr.setAD_Org_ID(line.getOrder_Org_ID());		// Revenue X-Org
			dr.setQty(line.getQty().negate());
			if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
			{
				// Set AmtAcctDr from Original Shipment/Receipt
				if (!dr.updateReverseLine(MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException().setDetailMessage("Original Shipment/Receipt not posted yet");
				}
			}

			//
			// Inventory CR
			final FactLine cr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Asset, as), acctCurrencyId, null, mkCostsValueToUse(acctCurrencyId, costs));
			if (cr == null)
			{
				throw newPostingException().setDetailMessage("FactLine CR not created: " + line);
			}
			cr.setM_Locator_ID(line.getM_Locator_ID());
			cr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
			cr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
			if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
			{
				// Set AmtAcctCr from Original Shipment/Receipt
				if (!cr.updateReverseLine(MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException().setDetailMessage("Original Shipment/Receipt not posted yet");
				}
				costs = cr.getAcctBalance(); // get original cost
			}
			//
			if (line.getM_Product_ID() > 0)
			{
				costDetailService
						.createCostDetail(CostDetailCreateRequest.builder()
								.acctSchemaId(as.getC_AcctSchema_ID())
								.clientId(line.getAD_Client_ID())
								.orgId(line.getAD_Org_ID())
								.productId(line.getM_Product_ID())
								.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
								.documentRef(CostingDocumentRef.ofShipmentLineId(line.get_ID()))
								.costElementId(0)
								.qty(line.getQty())
								.amt(costs)
								.currencyId(acctCurrencyId)
								.currencyConversionTypeId(getC_ConversionType_ID())
								.date(TimeUtil.asLocalDate(getDateAcct()))
								.description(line.getDescription())
								.build());
			}
		}	// for all lines

		//
		// Commitment release
		if (as.isAccrual() && as.isCreateSOCommitment())
		{
			for (final DocLine line : p_lines)
			{
				final Fact factcomm = Doc_Order.createFact_CommitmentSalesRelease(as, this, line.getQty(), line.get_ID(), BigDecimal.ONE);
				if (factcomm != null)
					facts.add(factcomm);
			}
		}	// Commitment

		return facts;
	}

	private List<Fact> createFacts_SalesReturn(final MAcctSchema as)
	{
		final int acctCurrencyId = as.getC_Currency_ID();

		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		for (final DocLine line : p_lines)
		{
			// Skip not stockable (e.g. service products) because they have no cost
			final I_M_Product product = line.getProduct();
			if (!productBL.isStocked(product))
			{
				continue;
			}

			// MZ Goodwill
			// if Shipment CostDetail exist then get Cost from Cost Detail
			BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), true, CostingDocumentRef.ofShipmentLineId(line.get_ID()));
			if (ProductCost.isNoCosts(costs) || costs.signum() == 0)	// zero costs OK
			{
				final String costingMethod = productBL.getCostingMethod(product, as);
				if (!MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
				{
					// 08447: we shall allow zero costs in case CostingMethod=Standard costing
					throw newPostingException()
							.setDetailMessage("No Costs for product=" + line.getProduct()
									+ ", product is stocked and costing method=" + costingMethod + " is != " + MAcctSchema.COSTINGMETHOD_StandardCosting);
				}
			}

			//
			// Inventory DR
			final FactLine dr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Asset, as), acctCurrencyId, mkCostsValueToUse(acctCurrencyId, costs), null);
			if (dr == null)
			{
				throw newPostingException().addDetailMessage("FactLine DR not created: " + line);
			}
			dr.setM_Locator_ID(line.getM_Locator_ID());
			dr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
			dr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
			if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
			{
				// Set AmtAcctDr from Original Shipment/Receipt
				if (!dr.updateReverseLine(MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException().setDetailMessage("Original Shipment/Receipt not posted yet");
				}
				costs = dr.getAcctBalance(); // get original cost
			}
			//
			if (line.getM_Product_ID() > 0)
			{
				costDetailService
						.createCostDetail(CostDetailCreateRequest.builder()
								.acctSchemaId(as.getC_AcctSchema_ID())
								.clientId(line.getAD_Client_ID())
								.orgId(line.getAD_Org_ID())
								.productId(line.getM_Product_ID())
								.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
								.documentRef(CostingDocumentRef.ofShipmentLineId(line.get_ID()))
								.costElementId(0)
								.qty(line.getQty())
								.amt(costs)
								.currencyId(acctCurrencyId)
								.currencyConversionTypeId(getC_ConversionType_ID())
								.date(TimeUtil.asLocalDate(getDateAcct()))
								.description(line.getDescription())
								.build());
			}

			//
			// CoGS CR
			final FactLine cr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Cogs, as), acctCurrencyId, null, mkCostsValueToUse(acctCurrencyId, costs));
			if (cr == null)
			{
				throw newPostingException().setDetailMessage("FactLine CR not created: " + line);
			}
			cr.setM_Locator_ID(line.getM_Locator_ID());
			cr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
			cr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
			cr.setAD_Org_ID(line.getOrder_Org_ID());		// Revenue X-Org
			cr.setQty(line.getQty().negate());
			if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
			{
				// Set AmtAcctCr from Original Shipment/Receipt
				if (!cr.updateReverseLine(MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException().setDetailMessage("Original Shipment/Receipt not posted yet");
				}
			}
		}	// for all lines

		return facts;
	}

	private List<Fact> createFacts_PurchasingReceipt(final MAcctSchema as)
	{
		final int acctCurrencyId = as.getC_Currency_ID();

		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		for (final DocLine line : p_lines)
		{
			// Skip not stockable (e.g. service products) because they have no cost
			final MProduct product = line.getProduct();
			if (!productBL.isStocked(product))
			{
				continue;
			}

			int currencyId = acctCurrencyId;

			final BigDecimal costs;
			final String costingMethod = productBL.getCostingMethod(product, as);
			if (MAcctSchema.COSTINGMETHOD_AveragePO.equals(costingMethod) ||
					MAcctSchema.COSTINGMETHOD_LastPOPrice.equals(costingMethod))
			{
				final int orderLineId = line.getC_OrderLine_ID();
				// Low - check if c_orderline_id is valid
				if (orderLineId > 0)
				{
					final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(getCtx(), orderLineId, I_C_OrderLine.class, getTrxName());
					currencyId = orderLine.getC_Currency_ID();

					final BigDecimal costPrice = Services.get(IOrderLineBL.class).getCostPrice(orderLine);
					costs = costPrice.multiply(line.getQty());
				}
				else
				{
					costs = line.getProductCosts(as, line.getAD_Org_ID(), false);	// current costs
				}
			}
			else
			{
				costs = line.getProductCosts(as, line.getAD_Org_ID(), false);	// current costs
			}

			if (ProductCost.isNoCosts(costs))
			{
				// 08447: we shall allow zero costs in case CostingMethod=Standard costing
				if (!MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
				{
					throw newPostingException().setDetailMessage("Resubmit - No Costs for product=" + line.getProduct()
							+ ", product is stocked and costing method=" + costingMethod + " is != " + MAcctSchema.COSTINGMETHOD_StandardCosting);
				}
			}

			//
			// Inventory/Asset DR
			final MAccount assets;
			if (productBL.isService(product))
			{
				// if the line is a Outside Processing then DR WIP
				if (line.getPP_Cost_Collector_ID() > 0)
				{
					assets = line.getAccount(ProductCost.ACCTTYPE_P_WorkInProcess, as);
				}
				else
				{
					assets = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
				}
			}
			else
			{
				assets = line.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
			}
			//
			final FactLine dr = fact.createLine(line, assets, currencyId, mkCostsValueToUse(currencyId, costs), null);
			if (dr == null)
			{
				throw newPostingException().setDetailMessage("DR not created: " + line);
			}
			dr.setM_Locator_ID(line.getM_Locator_ID());
			dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
			dr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
			if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
			{
				// Set AmtAcctDr from Original Shipment/Receipt
				if (!dr.updateReverseLine(MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException().setDetailMessage("Original Receipt not posted yet");
				}
			}

			//
			// NotInvoicedReceipt CR
			final FactLine cr = fact.createLine(line, getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as), currencyId, null, mkCostsValueToUse(currencyId, costs));
			//
			if (cr == null)
			{
				throw newPostingException().setDetailMessage("CR not created: " + line);
			}
			cr.setM_Locator_ID(line.getM_Locator_ID());
			cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
			cr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
			cr.setQty(line.getQty().negate());
			if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
			{
				// Set AmtAcctCr from Original Shipment/Receipt
				if (!cr.updateReverseLine(MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException().setDetailMessage("Original Receipt not posted yet");
				}
			}
		}

		return facts;
	}

	private List<Fact> createFacts_PurchasingReturn(final MAcctSchema as)
	{
		final int acctCurrencyId = as.getC_Currency_ID();

		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		for (final DocLine line : p_lines)
		{
			// Skip not stockable (e.g. service products) because they have no cost
			final MProduct product = line.getProduct();
			if (!productBL.isStocked(product))
			{
				continue;
			}

			final int currency = acctCurrencyId;
			final BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), false);	// current costs
			if (ProductCost.isNoCosts(costs))
			{
				// 08447: we shall allow zero costs in case CostingMethod=Standard costing
				final String costingMethod = productBL.getCostingMethod(product, as);
				if (!MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
				{
					throw newPostingException().setDetailMessage("Resubmit - No Costs for product=" + line.getProduct()
							+ ", product is stocked and costing method=" + costingMethod + " is != " + MAcctSchema.COSTINGMETHOD_StandardCosting);
				}
			}

			//
			// NotInvoicedReceipt DR
			final FactLine dr = fact.createLine(line, getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as), currency, mkCostsValueToUse(currency, costs), null);
			if (dr == null)
			{
				throw newPostingException().setDetailMessage("DR not created: " + line);
			}
			dr.setM_Locator_ID(line.getM_Locator_ID());
			dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
			dr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
			dr.setQty(line.getQty().negate());
			if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
			{
				// Set AmtAcctDr from Original Shipment/Receipt
				if (!dr.updateReverseLine(MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException().setDetailMessage("Original Receipt not posted yet");
				}
			}

			//
			// Inventory/Asset CR
			final MAccount assets;
			if (productBL.isService(product))
			{
				assets = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
			}
			else
			{
				assets = line.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
			}
			//
			final FactLine cr = fact.createLine(line, assets, currency, null, mkCostsValueToUse(currency, costs));
			if (cr == null)
			{
				throw newPostingException().setDetailMessage("CR not created: " + line);
			}
			cr.setM_Locator_ID(line.getM_Locator_ID());
			cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
			cr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
			if (MInOut.DOCSTATUS_Reversed.equals(m_DocStatus) && m_Reversal_ID > 0 && line.getReversalLine_ID() > 0)
			{
				// Set AmtAcctCr from Original Shipment/Receipt
				if (!cr.updateReverseLine(MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), BigDecimal.ONE))
				{
					throw newPostingException().setDetailMessage("Original Receipt not posted yet");
				}
			}
		}

		return facts;
	}

	@Override
	protected void afterPost()
	{
		postDependingMatchInvsIfNeeded();
	}

	private final void postDependingMatchInvsIfNeeded()
	{
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_PostMatchInvs, DEFAULT_PostMatchInvs))
		{
			return;
		}

		final Set<Integer> inoutLineIds = new HashSet<>();
		for (final DocLine docLine : getDocLines())
		{
			inoutLineIds.add(docLine.get_ID());
		}

		//
		// Do nothing in case there are no inout lines
		if (inoutLineIds.isEmpty())
		{
			return;
		}

		final List<I_M_MatchInv> matchInvs = queryBL
				.createQueryBuilder(I_M_MatchInv.class, getCtx(), getTrxName())
				.addInArrayOrAllFilter(I_M_MatchInv.COLUMN_M_InOutLine_ID, inoutLineIds)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_MatchInv.COLUMN_Processed, true)
				.addNotEqualsFilter(I_M_MatchInv.COLUMN_Posted, true)
				.create()
				.list();

		postDependingDocuments(matchInvs);
	}

	/**
	 * Creating a not null cost value whose precision matches the given <code>C_Currency_ID</code>'s.
	 * Goal of this method is to avoid the warnings in FactLine.setAmtSource().
	 * 
	 * @param currencyId
	 * @param costs
	 * @return costs rounded to currency precision
	 */
	private BigDecimal mkCostsValueToUse(final int currencyId, final BigDecimal costs)
	{
		if (costs == null)
		{
			return BigDecimal.ZERO;
		}

		final int precision = currencyDAO.getStdPrecision(getCtx(), currencyId);
		return costs.setScale(precision, RoundingMode.HALF_UP);
	}
}   // Doc_InOut
