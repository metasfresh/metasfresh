/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostDetail;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.inout.IInOutBL;
import de.metas.product.IProductBL;
import de.metas.tax.api.ITaxBL;

/**
 * Post Shipment/Receipt Documents.
 * 
 * <pre>
 *  Table:              M_InOut (319)
 *  Document Types:     MMS, MMR
 *  </pre>
 *  
 *  metas: 
 *  <li>copied from // metas: from https://adempiere.svn.sourceforge.net/svnroot/adempiere/branches/metas/mvcForms/base/src/org/compiere/acct/Doc_InOut.java, Rev 10177
 *  <li>Changed for "US330: Geschaeftsvorfall (G113d): Summen-/ Saldenliste (2010070510000637)"
 *  <li>Goal: Use {@link MCost#getSeedCosts(MProduct, int, MAcctSchema, int, String, int)}, if a product has no cost yet
 *  
 *  @author Jorg Janke
 *  @author Armen Rizal, Goodwill Consulting
 * 			<li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * 			<li>BF [ 2858043 ] Correct Included Tax in Average Costing
 *  
 */
public class Doc_InOut extends Doc
{
	private static final String SYSCONFIG_PostMatchInvs = "org.compiere.acct.Doc_InOut.PostMatchInvs";
	private static final boolean DEFAULT_PostMatchInvs = false;
	
	/**
	 * Constructor
	 * 
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_InOut(final IDocBuilder docBuilder)
	{
		super(docBuilder);
	}   // DocInOut

	private int m_Reversal_ID = 0;
	private String m_DocStatus = "";

	/**
	 * Load Document Details
	 * 
	 * @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		MInOut inout = (MInOut)getPO();
		setDateDoc(inout.getMovementDate());
		m_Reversal_ID = inout.getReversal_ID();// store original (voided/reversed) document
		m_DocStatus = inout.getDocStatus();
		// Contained Objects
		p_lines = loadLines(inout);
		log.debug("Lines=" + p_lines.length);
		return null;
	}   // loadDocumentDetails

	/**
	 * Load InOut Line
	 *
	 * @param inout shipment/receipt
	 * @return DocLine Array
	 */
	private DocLine[] loadLines(MInOut inout)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MInOutLine[] lines = inout.getLines(true);
		for (int i = 0; i < lines.length; i++)
		{
			MInOutLine line = lines[i];
			if (line.isDescription()
					|| line.getM_Product_ID() == 0
					|| line.getMovementQty().signum() == 0)
			{
				log.trace("Ignored: " + line);
				continue;
			}

			DocLine docLine = new DocLine(line, this);
			BigDecimal Qty = line.getMovementQty();

			if (Services.get(IInOutBL.class).isReversal(line))
			{
				// NOTE: to be consistent with current logic
				// we are setting docLine's ReversalLine_ID only if this is the actual reversal line
				// and not the line which was reversed
				docLine.setReversalLine_ID(line.getReversalLine_ID());
			}
			docLine.setQty(Qty, getDocumentType().equals(DOCTYPE_MatShipment));    // sets Trx and Storage Qty

			// Define if Outside Processing
			String sql = "SELECT PP_Cost_Collector_ID  FROM C_OrderLine WHERE C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL";
			int PP_Cost_Collector_ID = DB.getSQLValueEx(getTrxName(), sql, new Object[] { line.getC_OrderLine_ID() });
			docLine.setPP_Cost_Collector_ID(PP_Cost_Collector_ID);
			//
			log.debug(docLine.toString());
			list.add(docLine);
		}

		// Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	// loadLines

	/**
	 * Get Balance
	 * 
	 * @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   // getBalance

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
	public List<Fact> createFacts (MAcctSchema as)
	{
		//
		// service(s)
		final IProductBL productBL = Services.get(IProductBL.class);

		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		final int c_Currency_ID = as.getC_Currency_ID();
		setC_Currency_ID (c_Currency_ID);

		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;

		//
		//  *** Sales - Shipment		
		if (getDocumentType().equals(DOCTYPE_MatShipment) && isSOTrx())
		{
			for (int i = 0; i < p_lines.length; i++)
			{
				final DocLine line = p_lines[i];
				
				// Skip not stockable (e.g. service products) because they have no cost
				final I_M_Product product = line.getProduct();
				if (!productBL.isStocked(product))
				{
					continue;
				}
				
				// MZ Goodwill
				// if Shipment CostDetail exist then get Cost from Cost Detail 
				// metas-ts: US330: call with zeroCostsOK=false, because we want the system to return the result of MCost.getSeedCosts(), if there are no current costs yet 
				BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), false, "M_InOutLine_ID=?");
				// end MZ
				if (ProductCost.isNoCosts(costs) || costs.signum() == 0)
				{
					final String costingMethod = productBL.getCostingMethod(product, as);
					if (!MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
					{
						// 08447: we shall allow zero costs in case CostingMethod=Standard costing
						p_Error = "No Costs for product=" + line.getProduct()
								+ ", product is stocked and costing method=" + costingMethod + " is != " + MAcctSchema.COSTINGMETHOD_StandardCosting;
						log.warn(p_Error);
						return null;
					}
				}
				//  CoGS            DR
				dr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Cogs, as), c_Currency_ID, mkCostsValueToUse(c_Currency_ID, costs), null);
				if (dr == null)
				{
					p_Error = "FactLine DR not created: " + line;
					log.warn(p_Error);
					return null;
				}
				dr.setM_Locator_ID(line.getM_Locator_ID());
				dr.setLocationFromLocator(line.getM_Locator_ID(), true);    //  from Loc
				dr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				dr.setAD_Org_ID(line.getOrder_Org_ID());		//	Revenue X-Org
				dr.setQty(line.getQty().negate());
				if (m_DocStatus.equals(MInOut.DOCSTATUS_Reversed) 
						&& m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
				{
					//	Set AmtAcctDr from Original Shipment/Receipt
					if (!dr.updateReverseLine (MInOut.Table_ID, 
							m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
					{
						p_Error = "Original Shipment/Receipt not posted yet";
						return null;
					}
				}
				
				//  Inventory               CR
				cr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Asset, as), c_Currency_ID, null, mkCostsValueToUse(c_Currency_ID, costs));
				if (cr == null)
				{
					p_Error = "FactLine CR not created: " + line;
					log.warn(p_Error);
					return null;
				}
				cr.setM_Locator_ID(line.getM_Locator_ID());
				cr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
				cr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
				if (m_DocStatus.equals(MInOut.DOCSTATUS_Reversed) 
						&& m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
				{
					//	Set AmtAcctCr from Original Shipment/Receipt
					if (!cr.updateReverseLine (MInOut.Table_ID, 
							m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
					{
						p_Error = "Original Shipment/Receipt not posted yet";
						return null;
					}
					costs = cr.getAcctBalance(); //get original cost
				}
				//
				if (line.getM_Product_ID() != 0)
				{
					MCostDetail.createShipment(as, line.getAD_Org_ID(), 
						line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
						line.get_ID(), 0,
						costs, line.getQty(),
						line.getDescription(), true, getTrxName());
				}
			}	//	for all lines
			updateProductInfo(as.getC_AcctSchema_ID());     //  only for SO!

			/** Commitment release										****/
			if (as.isAccrual() && as.isCreateSOCommitment())
			{
				for (int i = 0; i < p_lines.length; i++)
				{
					DocLine line = p_lines[i];
					Fact factcomm = Doc_Order.getCommitmentSalesRelease(as, this, 
						line.getQty(), line.get_ID(), Env.ONE);
					if (factcomm != null)
						facts.add(factcomm);
				}
			}	//	Commitment
		
		}	//	Shipment
		//
        //	  *** Sales - Return
		else if ( getDocumentType().equals(DOCTYPE_MatReceipt) && isSOTrx() )
		{
			for (int i = 0; i < p_lines.length; i++)
			{
				final DocLine line = p_lines[i];
				
				// Skip not stockable (e.g. service products) because they have no cost
				final I_M_Product product = line.getProduct();
				if (!productBL.isStocked(product))
				{
					continue;
				}

				// MZ Goodwill
				// if Shipment CostDetail exist then get Cost from Cost Detail 
				BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), true, "M_InOutLine_ID=?");
				// end MZ
				
				if (ProductCost.isNoCosts(costs) || costs.signum() == 0)	// zero costs OK
				{
					final String costingMethod = productBL.getCostingMethod(product, as);
					if (!MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
					{
						// 08447: we shall allow zero costs in case CostingMethod=Standard costing
						p_Error = "No Costs for product=" + line.getProduct()
								+ ", product is stocked and costing method=" + costingMethod + " is != " + MAcctSchema.COSTINGMETHOD_StandardCosting;
						log.warn(p_Error);
						return null;
					}
				}
				//  Inventory               DR
				dr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Asset, as), c_Currency_ID, mkCostsValueToUse(c_Currency_ID, costs), null);
				if (dr == null)
				{
					p_Error = "FactLine DR not created: " + line;
					log.warn(p_Error);
					return null;
				}
				dr.setM_Locator_ID(line.getM_Locator_ID());
				dr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
				dr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
				if (m_DocStatus.equals(MInOut.DOCSTATUS_Reversed) 
						&& m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
				{
					//	Set AmtAcctDr from Original Shipment/Receipt
					if (!dr.updateReverseLine (MInOut.Table_ID, 
							m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
					{
						p_Error = "Original Shipment/Receipt not posted yet";
						return null;
					}
					costs = dr.getAcctBalance(); //get original cost
				}
				//
				if (line.getM_Product_ID() != 0)
				{
					MCostDetail.createShipment(as, line.getAD_Org_ID(), 
						line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
						line.get_ID(), 0,
						costs, line.getQty(),
						line.getDescription(), true, getTrxName());
				}
				
				//  CoGS            CR
				cr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_P_Cogs, as), c_Currency_ID, null, mkCostsValueToUse(c_Currency_ID, costs));
				if (cr == null)
				{
					p_Error = "FactLine CR not created: " + line;
					log.warn(p_Error);
					return null;
				}
				cr.setM_Locator_ID(line.getM_Locator_ID());
				cr.setLocationFromLocator(line.getM_Locator_ID(), true);    //  from Loc
				cr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				cr.setAD_Org_ID(line.getOrder_Org_ID());		//	Revenue X-Org
				cr.setQty(line.getQty().negate());
				if (m_DocStatus.equals(MInOut.DOCSTATUS_Reversed) 
						&& m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
				{
					//	Set AmtAcctCr from Original Shipment/Receipt
					if (!cr.updateReverseLine (MInOut.Table_ID, 
							m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
					{
						p_Error = "Original Shipment/Receipt not posted yet";
						return null;
					}					
				}
			}	//	for all lines
			updateProductInfo(as.getC_AcctSchema_ID());     //  only for SO!
		}	//	Sales Return
		//
		//  *** Purchasing - Receipt
		else if (getDocumentType().equals(DOCTYPE_MatReceipt) && !isSOTrx())
		{
			for (int i = 0; i < p_lines.length; i++)
			{
				// Elaine 2008/06/26
				int C_Currency_ID = c_Currency_ID;
				//
				final DocLine line = p_lines[i];
				BigDecimal costs = null;
				final MProduct product = line.getProduct();
				
				// Skip not stockable (e.g. service products) because they have no cost
				if (!productBL.isStocked(product))
				{
					continue;
				}

				//get costing method for product
				final String costingMethod = productBL.getCostingMethod(product, as);

				if (MAcctSchema.COSTINGMETHOD_AveragePO.equals(costingMethod) ||
					MAcctSchema.COSTINGMETHOD_LastPOPrice.equals(costingMethod) )
				{
					int C_OrderLine_ID = line.getC_OrderLine_ID();
					// Low - check if c_orderline_id is valid
					if (C_OrderLine_ID > 0) 
					{
					    MOrderLine orderLine = new MOrderLine (getCtx(), C_OrderLine_ID, getTrxName());
					    // Elaine 2008/06/26 
					    C_Currency_ID = orderLine.getC_Currency_ID();
					    //
					    costs = orderLine.getPriceCost();
					    if (costs == null || costs.signum() == 0)
					    {
					    	costs = orderLine.getPriceActual();
							//	Goodwill: Correct included Tax
					    	int C_Tax_ID = orderLine.getC_Tax_ID();
							if (Services.get(IOrderLineBL.class).isTaxIncluded(orderLine) && C_Tax_ID > 0)
							{
								final MTax tax = MTax.get(getCtx(), C_Tax_ID);
								if (!tax.isZeroTax())
								{
									int stdPrecision = Services.get(IOrderLineBL.class).getPrecision(orderLine);
									BigDecimal costTax = Services.get(ITaxBL.class).calculateTax(tax, costs, true, stdPrecision);
									log.debug("Costs=" + costs + " - Tax=" + costTax);
									costs = costs.subtract(costTax);
								}
							}	//	correct included Tax					    	
					    }
					    costs = costs.multiply(line.getQty());
                    }
                    else
                    {
                        costs = line.getProductCosts(as, line.getAD_Org_ID(), false);	//	current costs
                    }
                    //
				}
				else
				{
					costs = line.getProductCosts(as, line.getAD_Org_ID(), false);	//	current costs
				}
				
				if (ProductCost.isNoCosts(costs))
				{
					// 08447: we shall allow zero costs in case CostingMethod=Standard costing
					if (!MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
					{
						p_Error = "Resubmit - No Costs for product=" + line.getProduct()
								+ ", product is stocked and costing method=" + costingMethod + " is != " + MAcctSchema.COSTINGMETHOD_StandardCosting;
						log.warn(p_Error);
						return null;
					}
				}
				//  Inventory/Asset			DR
				MAccount assets = line.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
				if (productBL.isService(product))
				{	
					//if the line is a Outside Processing then DR WIP
					if(line.getPP_Cost_Collector_ID() > 0)
					{
						assets = line.getAccount(ProductCost.ACCTTYPE_P_WorkInProcess, as);
					}
					else
					{
						assets = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
					}
				}

				// Elaine 2008/06/26
				/*dr = fact.createLine(line, assets,
					as.getC_Currency_ID(), costs, null);*/
				dr = fact.createLine(line, assets, C_Currency_ID,  mkCostsValueToUse(C_Currency_ID, costs), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + line;
					log.warn(p_Error);
					return null;
				}
				dr.setM_Locator_ID(line.getM_Locator_ID());
				dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
				dr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
				if (m_DocStatus.equals(MInOut.DOCSTATUS_Reversed) && m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
				{
					//	Set AmtAcctDr from Original Shipment/Receipt
					if (!dr.updateReverseLine (MInOut.Table_ID, m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
					{
						p_Error = "Original Receipt not posted yet";
						return null;
					}
				}
				
				//  NotInvoicedReceipt				CR
				// Elaine 2008/06/26
				/*cr = fact.createLine(line,
					getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as),
					as.getC_Currency_ID(), null, costs);*/
				cr = fact.createLine(line, getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as), C_Currency_ID, null, mkCostsValueToUse(C_Currency_ID, costs));
				//
				if (cr == null)
				{
					p_Error = "CR not created: " + line;
					log.warn(p_Error);
					return null;
				}
				cr.setM_Locator_ID(line.getM_Locator_ID());
				cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   //  from Loc
				cr.setLocationFromLocator(line.getM_Locator_ID(), false);   //  to Loc
				cr.setQty(line.getQty().negate());
				if (m_DocStatus.equals(MInOut.DOCSTATUS_Reversed) && m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
				{
					//	Set AmtAcctCr from Original Shipment/Receipt
					if (!cr.updateReverseLine (MInOut.Table_ID, 
							m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
					{
						p_Error = "Original Receipt not posted yet";
						return null;
					}
				}
			}
		}	//	Receipt
         //	  *** Purchasing - return
		else if (getDocumentType().equals(DOCTYPE_MatShipment) && !isSOTrx())
		{
			for (int i = 0; i < p_lines.length; i++)
			{
				// Elaine 2008/06/26
				final int C_Currency_ID = c_Currency_ID;
				//
				final DocLine line = p_lines[i];
				final BigDecimal costs;
				final MProduct product = line.getProduct();
				
				// Skip not stockable (e.g. service products) because they have no cost
				if (!productBL.isStocked(product))
				{
					continue;
				}

				/*
				 * BF: [ 2048984 ] Purchase Return costing logic
				//get costing method for product
				String costingMethod = product.getCostingMethod(as);
				if (MAcctSchema.COSTINGMETHOD_AveragePO.equals(costingMethod) ||
					MAcctSchema.COSTINGMETHOD_LastPOPrice.equals(costingMethod) )
				{
					int C_OrderLine_ID = line.getC_OrderLine_ID();
					MOrderLine orderLine = new MOrderLine (getCtx(), C_OrderLine_ID, getTrxName());
					costs = orderLine.getPriceCost();
					if (costs == null || costs.signum() == 0)
						costs = orderLine.getPriceActual();
					costs = costs.multiply(line.getQty());
					// Elaine 2008/06/26
					C_Currency_ID = orderLine.getC_Currency_ID();
					//
				}
				else
				{
					costs = line.getProductCosts(as, line.getAD_Org_ID(), false);	//	current costs
				}
				*/
				costs = line.getProductCosts(as, line.getAD_Org_ID(), false);	// current costs
				if (ProductCost.isNoCosts(costs))
				{
					// 08447: we shall allow zero costs in case CostingMethod=Standard costing
					final String costingMethod = productBL.getCostingMethod(product, as);
					if (!MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
					{
						p_Error = "Resubmit - No Costs for product=" + line.getProduct()
								+ ", product is stocked and costing method=" + costingMethod + " is != " + MAcctSchema.COSTINGMETHOD_StandardCosting;
						log.warn(p_Error);
						return null;
					}
				}
				//  NotInvoicedReceipt				DR
				// Elaine 2008/06/26
				/*dr = fact.createLine(line,
					getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as),
					as.getC_Currency_ID(), costs , null);*/
				dr = fact.createLine(line, getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as), C_Currency_ID, mkCostsValueToUse(C_Currency_ID, costs) , null);
				//
				if (dr == null)
				{
					p_Error = "CR not created: " + line;
					log.warn(p_Error);
					return null;
				}
				dr.setM_Locator_ID(line.getM_Locator_ID());
				dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   //  from Loc
				dr.setLocationFromLocator(line.getM_Locator_ID(), false);   //  to Loc
				dr.setQty(line.getQty().negate());
				if (m_DocStatus.equals(MInOut.DOCSTATUS_Reversed) && m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
				{
					//	Set AmtAcctDr from Original Shipment/Receipt
					if (!dr.updateReverseLine (MInOut.Table_ID, 
							m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
					{
						p_Error = "Original Receipt not posted yet";
						return null;
					}
				}
				
				//  Inventory/Asset			CR
				MAccount assets = line.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
				if (productBL.isService(product))
				{
					assets = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
				}
				// Elaine 2008/06/26
				/*cr = fact.createLine(line, assets,
					as.getC_Currency_ID(), null, costs);*/
				cr = fact.createLine(line, assets, C_Currency_ID, null, mkCostsValueToUse(C_Currency_ID, costs));
				//
				if (cr == null)
				{
					p_Error = "DR not created: " + line;
					log.warn(p_Error);
					return null;
				}
				cr.setM_Locator_ID(line.getM_Locator_ID());
				cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
				cr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
				if (m_DocStatus.equals(MInOut.DOCSTATUS_Reversed) && m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
				{
					//	Set AmtAcctCr from Original Shipment/Receipt
					if (!cr.updateReverseLine (MInOut.Table_ID, 
							m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
					{
						p_Error = "Original Receipt not posted yet";
						return null;
					}
				}
			}
		}	//	Purchasing Return
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.error(p_Error);
			return null;
		}
		//
		facts.add(fact);
		return facts;
	}// createFact
	
	
	@Override
	protected void afterPost()
	{
		postDependingMatchInvsIfNeeded();
	}

	private final void postDependingMatchInvsIfNeeded()
	{
		if (!Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_PostMatchInvs, DEFAULT_PostMatchInvs))
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

		final List<I_M_MatchInv> matchInvs = Services.get(IQueryBL.class)
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
	 * @param C_Currency_ID
	 * @param costs
	 * @return costs rounded to currency precision
	 */
	private BigDecimal mkCostsValueToUse(final int C_Currency_ID, final BigDecimal costs)
	{
		if (costs == null)
		{
			return BigDecimal.ZERO;
		}

		final int precision = currencyDAO.getStdPrecision(getCtx(), C_Currency_ID);
		return costs.setScale(precision, RoundingMode.HALF_UP);
	}

	/**
	 * Update Sales Order Costing Product Info (old). Purchase side handled in Invoice Matching. <br>
	 * decrease average cumulatives
	 * 
	 * @param C_AcctSchema_ID accounting schema
	 * @deprecated old costing
	 */
	@Deprecated
	private void updateProductInfo(int C_AcctSchema_ID)
	{
		log.debug("M_InOut_ID=" + get_ID());
		// Old Model
		StringBuffer sql = new StringBuffer(
				// FYRACLE add pc. everywhere
				"UPDATE M_Product_Costing pc "
						+ "SET (CostAverageCumQty, CostAverageCumAmt)="
						+ "(SELECT pc.CostAverageCumQty - SUM(il.MovementQty),"
						+ " pc.CostAverageCumAmt - SUM(il.MovementQty*pc.CurrentCostPrice) "
						+ "FROM M_InOutLine il "
						+ "WHERE pc.M_Product_ID=il.M_Product_ID"
						+ " AND il.M_InOut_ID=").append(get_ID()).append(") ")
				.append("WHERE EXISTS (SELECT * "
						+ "FROM M_InOutLine il "
						+ "WHERE pc.M_Product_ID=il.M_Product_ID"
						+ " AND il.M_InOut_ID=").append(get_ID()).append(")");
		final String sqlNative = DB.convertSqlToNative(sql.toString());
		int no = DB.executeUpdate(sqlNative, getTrxName());
		log.debug("M_Product_Costing - Updated=" + no);
		//
	}   // updateProductInfo

}   // Doc_InOut
