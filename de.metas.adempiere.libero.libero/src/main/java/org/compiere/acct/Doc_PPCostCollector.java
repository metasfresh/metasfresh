/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.compiere.acct;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.ProductAcctType;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCostDetail;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.model.ProductCost;
import org.compiere.model.Query;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.eevolution.model.MPPCostCollector;
import org.eevolution.model.RoutingService;
import org.eevolution.model.RoutingServiceFactory;

/**
 *  Post Cost Collector
 *  <pre>
 *  Table:              PP_Cost_Collector
 *  Document Types:     MOP
 *  </pre>
 *  @author victor.perez@e-evolution.com http://www.e-evolution.com
 */
public class Doc_PPCostCollector extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_PPCostCollector (final IDocBuilder docBuilder)
	{
		super(docBuilder, X_C_DocType.DOCBASETYPE_ManufacturingCostCollector);
	}   //Doc Cost Collector


	/**	Pseudo Line */
	protected DocLine_CostCollector m_line = null;

	/** Collector Cost */
	protected MPPCostCollector m_cc = null;

	/** Routing Service */
	protected RoutingService m_routingService = null;


	/**
	 *  Load Document Details
	 *  @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		setC_Currency_ID (NO_CURRENCY);
		m_cc = (MPPCostCollector)getPO();
		setDateDoc (m_cc.getMovementDate());
		setDateAcct(m_cc.getMovementDate());

		//	Pseudo Line
		m_line = new DocLine_CostCollector (m_cc, this);
		m_line.setQty (m_cc.getMovementQty(), false);    //  sets Trx and Storage Qty

		//	Pseudo Line Check
		if (m_line.getM_Product_ID() == 0)
			log.warn(m_line.toString() + " - No Product");
		log.debug(m_line.toString());

		// Load the RoutingService
		m_routingService = RoutingServiceFactory.get().getRoutingService(m_cc.getAD_Client_ID());

		return null;
	}   //  loadDocumentDetails

	/**
	 *  Get Balance
	 *  @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  @param as accounting schema
	 *  @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		setC_Currency_ID (as.getC_Currency_ID());
		final ArrayList<Fact> facts = new ArrayList<Fact>();

		if(MPPCostCollector.COSTCOLLECTORTYPE_MaterialReceipt.equals(m_cc.getCostCollectorType()))
		{
			facts.add(createMaterialReceipt(as));
		}
		else if (MPPCostCollector.COSTCOLLECTORTYPE_ComponentIssue.equals(m_cc.getCostCollectorType()))
		{
			facts.add(createComponentIssue(as));
		}
		else if (MPPCostCollector.COSTCOLLECTORTYPE_MethodChangeVariance.equals(m_cc.getCostCollectorType()))
		{
			facts.add(createVariance(as, ProductCost.ACCTTYPE_P_MethodChangeVariance));
		}
		else if (MPPCostCollector.COSTCOLLECTORTYPE_UsegeVariance.equals(m_cc.getCostCollectorType()))
		{
			facts.add(createVariance(as, ProductCost.ACCTTYPE_P_UsageVariance));
		}
		else if (MPPCostCollector.COSTCOLLECTORTYPE_UsegeVariance.equals(m_cc.getCostCollectorType()))
		{
			facts.add(createVariance(as, ProductCost.ACCTTYPE_P_UsageVariance));
		}
		else if (MPPCostCollector.COSTCOLLECTORTYPE_RateVariance.equals(m_cc.getCostCollectorType()))
		{
			facts.add(createVariance(as, ProductCost.ACCTTYPE_P_RateVariance));
		}
		else if (MPPCostCollector.COSTCOLLECTORTYPE_MixVariance.equals(m_cc.getCostCollectorType()))
		{
			facts.add(createVariance(as, ProductCost.ACCTTYPE_P_MixVariance));
		}
		else if (MPPCostCollector.COSTCOLLECTORTYPE_ActivityControl.equals(m_cc.getCostCollectorType()))
		{
			facts.addAll(createActivityControl(as));
		}
		//
		return facts;
	}   //  createFact

	protected void createLines(MCostElement element, MAcctSchema as, Fact fact , MProduct product,
								MAccount debit, MAccount credit, BigDecimal cost, BigDecimal qty)
	{
		if(cost == null || debit == null || credit == null)
			return;

		log.info("CostElement: " +element+ "Product: "+product.getName()
					+" Debit: " + debit.getDescription() + " Credit: "+ credit.getDescription()
					+ " Cost: " + cost +" Qty: "+ qty);
		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;
		if(cost.signum() != 0)
		{
			dr = fact.createLine(m_line, debit , as.getC_Currency_ID(), cost, null);
			dr.setQty(qty);
			String desc = element.getName();
			dr.addDescription(desc);
			dr.setC_Project_ID(m_cc.getC_Project_ID());
			dr.setC_Activity_ID(m_cc.getC_Activity_ID());
			dr.setC_Campaign_ID(m_cc.getC_Campaign_ID());
			dr.setM_Locator_ID(m_cc.getM_Locator_ID());

			cr = fact.createLine(m_line, credit,as.getC_Currency_ID(), null, cost);
			cr.setQty(qty);
			cr.addDescription(desc);
			cr.setC_Project_ID(m_cc.getC_Project_ID());
			cr.setC_Activity_ID(m_cc.getC_Activity_ID());
			cr.setC_Campaign_ID(m_cc.getC_Campaign_ID());
			cr.setM_Locator_ID(m_cc.getM_Locator_ID());
		}
	}

	/**
	 * The Receipt Finish good product created the next account fact
	 * Debit Product Asset Account for each Cost Element using Current Cost
	 * Create a fact line for each cost element type
	 * 		Material
	 * 		Labor(Resources)
	 * 		Burden
	 * 		Overhead
	 * 		Outsite Processing
	 * Debit Scrap Account for each Cost Element using Current Cost
	 * Create a fact line for each cost element type
	 * 		Material
	 * 		Labor(Resources)
	 * 		Burden
	 * 		Overhead
	 * 		Outsite Processing
	 * Credit Work in Process Account for each Cost Element using Current Cost
	 * Create a fact line for each cost element type
	 * 		Material
	 * 		Labor(Resources)
	 * 		Burden
	 * 		Overhead
	 * 		Outsite Processing
	 */
	protected Fact createMaterialReceipt(MAcctSchema as)
	{
		final Fact fact = new Fact(this, as, Fact.POST_Actual);

		final MProduct product = m_cc.getM_Product();
		final MAccount credit = m_line.getAccount(ProductCost.ACCTTYPE_P_WorkInProcess, as);

		for (MCostDetail cd : getCostDetails())
		{
			MCostElement element = MCostElement.get(getCtx(), cd.getM_CostElement_ID());
			if (m_cc.getMovementQty().signum() != 0)
			{
				MAccount debit = m_line.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
				BigDecimal cost = cd.getAmt();
				if (cost.scale() > as.getStdPrecision())
					cost = cost.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
				createLines(element, as, fact, product, debit, credit, cost, m_cc.getMovementQty());
			}
			if(m_cc.getScrappedQty().signum() != 0)
			{
				MAccount debit = m_line.getAccount(ProductCost.ACCTTYPE_P_Scrap, as);
				BigDecimal cost = cd.getPrice().multiply(m_cc.getScrappedQty());
				if (cost.scale() > as.getStdPrecision())
					cost = cost.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
				createLines(element, as, fact, product, debit, credit, cost, m_cc.getScrappedQty());
			}
		}
		return fact;
	}

	/**
	 * The Issue Component product created next account fact
	 * Debit Work in Process Account for each Cost Element using current cost
	 * Create a fact line for each cost element type
	 * 		Material
	 * 		Labor(Resources)
	 * 		Burden
	 * 		Overhead
	 * 		Outsite Processing
	 * Credit Product Asset Account for each Cost Element using current cost
	 * Create a fact line for each cost element type
	 * 		Material
	 * 		Labor(Resources)
	 * 		Burden
	 * 		Overhead
	 * 		Outsite Processing
	 * Credit Floor Stock Account for each Cost Element using current cost
	 * Create a fact line for each cost element type
	 * 		Material
	 * 		Labor(Resources)
	 * 		Burden
	 * 		Overhead
	 * 		Outsite Processing
	 */
	protected Fact createComponentIssue(MAcctSchema as)
	{
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		final MProduct product = m_cc.getM_Product();

		MAccount debit = m_line.getAccount(ProductCost.ACCTTYPE_P_WorkInProcess, as);
		MAccount credit = m_line.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
		if(m_cc.isFloorStock())
		{
			credit = m_line.getAccount(ProductCost.ACCTTYPE_P_FloorStock, as);
		}

		for (MCostDetail cd : getCostDetails())
		{
			MCostElement element = MCostElement.get(getCtx(), cd.getM_CostElement_ID());
			BigDecimal cost = cd.getAmt().negate();
			if (cost.scale() > as.getStdPrecision())
				cost = cost.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
			createLines(element, as, fact, product, debit, credit, cost, m_cc.getMovementQty());
		}

		return fact;
	}

	/**
	 * Feedback Labor and Burden absorbed
	 * Debit Work in Process Account for each Labor or Burden using the current cost rate
	 * Create a fact line for labor or burden
	 * 		Labor(Resources)
	 * 		Burden
	 * Credit Labor Absorbed or Burden Absorbed Account
	 * Create a fact line for labor or burden
	 * 		Labor Absorbed
	 * 		Burden Absorbed
	 */
	protected List<Fact> createActivityControl(MAcctSchema as)
	{
		final ArrayList<Fact> facts = new ArrayList<Fact>();
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		facts.add(fact);

		final MProduct product = m_cc.getM_Product();

		MAccount debit = m_line.getAccount(ProductCost.ACCTTYPE_P_WorkInProcess, as);

		for (MCostDetail cd : getCostDetails())
		{
			BigDecimal costs = cd.getAmt();
			if (costs.signum() == 0)
				continue;
			MCostElement element = MCostElement.get(getCtx(), cd.getM_CostElement_ID());
			MAccount credit = m_line.getAccount(as, element);
			createLines(element, as, fact, product, debit, credit, costs, m_cc.getMovementQty());
		}
		//
		return facts;
	}

	protected Fact createVariance(MAcctSchema as, ProductAcctType VarianceAcctType)
	{
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		final MProduct product = m_cc.getM_Product();

		MAccount debit = m_line.getAccount(VarianceAcctType, as);
		MAccount credit = m_line.getAccount(ProductCost.ACCTTYPE_P_WorkInProcess, as);

		for (MCostDetail cd : getCostDetails())
		{
			MCostElement element = MCostElement.get(getCtx(), cd.getM_CostElement_ID());
			BigDecimal costs = cd.getAmt().negate();
			if (costs.scale() > as.getStdPrecision())
				costs = costs.setScale(as.getStdPrecision(), RoundingMode.HALF_UP);
			BigDecimal qty = cd.getQty().negate();
			createLines(element, as, fact, product, debit, credit, costs, qty);
		}
		return fact;
	}


	public Collection<I_M_CostElement> getCostElements()
	{
		final String costingMethod = MCostElement.COSTINGMETHOD_StandardCosting;
		final Collection<I_M_CostElement> elements = MCostElement.getByCostingMethod(getCtx(), costingMethod);
		return elements;
	}

	protected static final MProduct getProductForResource(Properties ctx, int S_Resource_ID, String trxName)
	{
		final String whereClause = MProduct.COLUMNNAME_S_Resource_ID+"=?";
		int M_Product_ID = new Query(ctx, MProduct.Table_Name, whereClause, trxName)
		.setParameters(new Object[]{S_Resource_ID})
		.firstIdOnly();
		return MProduct.get(ctx, M_Product_ID);
	}

	private List<MCostDetail> getCostDetails()
	{
		if (m_costDetails == null)
		{
			String whereClause = MCostDetail.COLUMNNAME_PP_Cost_Collector_ID+"=?";
			m_costDetails = new Query(getCtx(), MCostDetail.Table_Name, whereClause, getTrxName())
			.setParameters(new Object[]{m_cc.getPP_Cost_Collector_ID()})
			.setOrderBy(MCostDetail.COLUMNNAME_M_CostDetail_ID)
			.list();
		}
		return m_costDetails;
	}
	private List<MCostDetail> m_costDetails = null;
}   //  Doc Cost Collector
