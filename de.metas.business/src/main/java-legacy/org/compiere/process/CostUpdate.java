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
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

/**
 * 	Standard Cost Update
 *	
 *  @author Jorg Janke
 *  @version $Id: CostUpdate.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class CostUpdate extends JavaProcess
{
	/**	Product Category		*/
	private int		p_M_Product_Category_ID = 0;
	/** Future Costs			*/
	private String	p_SetFutureCostTo = null;
	/** Standard Costs			*/
	private String	p_SetStandardCostTo = null;
	/** PLV						*/
	private int 	p_M_PriceList_Version_ID = 0;
	
	
	private static final String	TO_AveragePO = "A";
	private static final String	TO_AverageInvoiceHistory = "DI";
	private static final String	TO_AveragePOHistory = "DP";
	private static final String	TO_FiFo = "F";
	private static final String	TO_AverageInvoice = "I";
	private static final String	TO_LiFo = "L";
	private static final String	TO_PriceListLimit = "LL";
	private static final String	TO_StandardCost = "S";
	private static final String	TO_FutureStandardCost = "f";
	private static final String	TO_LastInvoicePrice = "i";
	private static final String	TO_LastPOPrice = "p";
	private static final String	TO_OldStandardCost = "x";

	/** Standard Cost Element		*/
	private MCostElement 	m_ce = null;
	/** Client Accounting SChema	*/
	private MAcctSchema[]	m_ass = null;
	/** Map of Cost Elements		*/
	private HashMap<String,MCostElement>	m_ces = new HashMap<String,MCostElement>();
	
	
	/**
	 * 	Prepare
	 */
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
		//	log.debug("prepare - " + para[i]);
			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_Product_Category_ID"))
				p_M_Product_Category_ID = para[i].getParameterAsInt();
			else if (name.equals("SetFutureCostTo"))
				p_SetFutureCostTo = (String)para[i].getParameter();
			else if (name.equals("SetStandardCostTo"))
				p_SetStandardCostTo = (String)para[i].getParameter();
			else if (name.equals("M_PriceList_Version_ID"))
				p_M_PriceList_Version_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);		
		}
	}	//	prepare	

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		log.info("M_Product_Category_ID=" + p_M_Product_Category_ID
			+ ", Future=" + p_SetFutureCostTo
			+ ", Standard=" + p_SetStandardCostTo
			+ "; M_PriceList_Version_ID=" + p_M_PriceList_Version_ID);
		if (p_SetFutureCostTo == null)
			p_SetFutureCostTo = "";
		if (p_SetStandardCostTo == null)
			p_SetStandardCostTo = "";
		//	Nothing to Do
		if (p_SetFutureCostTo.length() == 0 && p_SetStandardCostTo.length() == 0)
		{
			return "-";
		}
		//	PLV required
		if (p_M_PriceList_Version_ID == 0
			&& (p_SetFutureCostTo.equals(TO_PriceListLimit) || p_SetStandardCostTo.equals(TO_PriceListLimit)))
			throw new AdempiereUserError ("@FillMandatory@  @M_PriceList_Version_ID@");
		
		//	Validate Source
		if (!isValid(p_SetFutureCostTo))
			throw new AdempiereUserError ("@NotFound@ @M_CostElement_ID@ (Future) " + p_SetFutureCostTo);
		if (!isValid(p_SetStandardCostTo))
			throw new AdempiereUserError ("@NotFound@ @M_CostElement_ID@ (Standard) " + p_SetStandardCostTo);

		//	Prepare
		MClient client = MClient.get(getCtx());
		m_ce = MCostElement.getMaterialCostElement(client, MAcctSchema.COSTINGMETHOD_StandardCosting);
		if (m_ce.get_ID() == 0)
			throw new AdempiereUserError ("@NotFound@ @M_CostElement_ID@ (StdCost)");
		log.info(m_ce.toString());
		m_ass = MAcctSchema.getClientAcctSchema(getCtx(), client.getAD_Client_ID());
		for (int i = 0; i < m_ass.length; i++)
			createNew(m_ass[i]);
		commitEx();
		
		//	Update Cost
		int counter = update();
		
		return "#" + counter;
	}	//	doIt
	
	/**
	 * 	Costing Method must exist
	 *	@param to test
	 *	@return true valid
	 */
	private boolean isValid(String to)
	{
		if (p_SetFutureCostTo.length() == 0)
			return true;
		
		String toTarget = to;
		if (to.equals(TO_AverageInvoiceHistory))
			to = TO_AverageInvoice;
		if (to.equals(TO_AveragePOHistory))
			to = TO_AveragePO;
		if (to.equals(TO_FutureStandardCost))
			to = TO_StandardCost;
		//
		if (to.equals(TO_AverageInvoice)
			|| to.equals(TO_AveragePO)
			|| to.equals(TO_FiFo)
			|| to.equals(TO_LiFo)
			|| to.equals(TO_StandardCost))
		{
			MCostElement ce = getCostElement(p_SetFutureCostTo);
			return ce != null;
		}
		return true;
	}	//	isValid
	
	/**************************************************************************
	 * 	Create New Standard Costs
	 * 	@param as accounting schema
	 */
	private void createNew (MAcctSchema as)
	{
		if (!as.getCostingLevel().equals(MAcctSchema.COSTINGLEVEL_Client))
		{
			String txt = "Costing Level prevents creating new Costing records for " + as.getName();
			log.warn(txt);
			addLog(0, null, null, txt);
			return;
		}
		String sql = "SELECT * FROM M_Product p "
			+ "WHERE NOT EXISTS (SELECT * FROM M_Cost c WHERE c.M_Product_ID=p.M_Product_ID"
			+ " AND c.M_CostType_ID=? AND c.C_AcctSchema_ID=? AND c.M_CostElement_ID=?"
			+ " AND c.M_AttributeSetInstance_ID=0) "
			+ "AND AD_Client_ID=?";
		if (p_M_Product_Category_ID != 0)
			sql += " AND M_Product_Category_ID=?"; 
		int counter = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, ITrx.TRXNAME_None);
			pstmt.setInt (1, as.getM_CostType_ID());
			pstmt.setInt (2, as.getC_AcctSchema_ID());
			pstmt.setInt (3, m_ce.getM_CostElement_ID());
			pstmt.setInt (4, as.getAD_Client_ID());
			if (p_M_Product_Category_ID != 0)
				pstmt.setInt (5, p_M_Product_Category_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				if (createNew (new MProduct (getCtx(), rs, ITrx.TRXNAME_None), as))
					counter++;
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		
		log.info("#" + counter);
		addLog(0, null, new BigDecimal(counter), "Created for " + as.getName());
	}	//	createNew
	
	/**
	 * 	Create New Client level Costing Record
	 *	@param product product
	 *	@param as acct schema
	 *	@return true if created
	 */
	private boolean createNew (I_M_Product product, MAcctSchema as)
	{
		MCost cost = MCost.get(product, 0, as, 0, m_ce.getM_CostElement_ID(), ITrx.TRXNAME_None);
		if (cost.is_new())
			return cost.save();
		return false;
	}	//	createNew

	/**************************************************************************
	 * 	Update Cost Records
	 * 	@return no updated
	 */
	private int update()
	{
		int counter = 0;
		String sql = "SELECT * FROM M_Cost c WHERE M_CostElement_ID=?";
		if (p_M_Product_Category_ID != 0)
			sql += " AND EXISTS (SELECT * FROM M_Product p "
				+ "WHERE c.M_Product_ID=p.M_Product_ID AND p.M_Product_Category_ID=?)";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, m_ce.getM_CostElement_ID());
			if (p_M_Product_Category_ID != 0)
				pstmt.setInt (2, p_M_Product_Category_ID);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MCost cost = new MCost (getCtx(), rs, get_TrxName());
				for (int i = 0; i < m_ass.length; i++)
				{
					//	Update Costs only for default Cost Type
					if (m_ass[i].getC_AcctSchema_ID() == cost.getC_AcctSchema_ID() 
						&& m_ass[i].getM_CostType_ID() == cost.getM_CostType_ID())
					{
						if (update (cost))
							counter++;
					}
				}
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		log.info("#" + counter);
		addLog(0, null, new BigDecimal(counter), "@Updated@");
		return counter;
	}	//	update

	/**
	 * 	Update Cost Records
	 *	@param cost cost
	 *	@return true if updated
	 *	@throws Exception
	 */
	private boolean update (MCost cost) throws Exception
	{
		boolean updated = false;
		if (p_SetFutureCostTo.equals(p_SetStandardCostTo))
		{
			BigDecimal costs = getCosts(cost, p_SetFutureCostTo);
			if (costs != null && costs.signum() != 0)
			{
				cost.setFutureCostPrice(costs);
				cost.setCurrentCostPrice(costs);
				updated = true;
			}
		}
		else
		{
			if (p_SetStandardCostTo.length() > 0)
			{
				BigDecimal costs = getCosts(cost, p_SetStandardCostTo);
				if (costs != null && costs.signum() != 0)
				{
					cost.setCurrentCostPrice(costs);
					updated = true;
				}
			}
			if (p_SetFutureCostTo.length() > 0)
			{
				BigDecimal costs = getCosts(cost, p_SetFutureCostTo);
				if (costs != null && costs.signum() != 0)
				{
					cost.setFutureCostPrice(costs);
					updated = true;
				}
			}
		}
		if (updated)
			updated = cost.save();
		return updated;
	}	//	update
	
	/**
	 * 	Get Costs
	 *	@param cost cost
	 *	@param to where to get costs from 
	 *	@return costs (could be 0) or null if not found
	 *	@throws Exception
	 */
	private BigDecimal getCosts (MCost cost, String to) throws Exception
	{
		final String trxName = ITrx.TRXNAME_None; // backward compatibility
		
		BigDecimal retValue = null;
		
		//	Average Invoice
		if (to.equals(TO_AverageInvoice))
		{
			MCostElement ce = getCostElement(TO_AverageInvoice);
			if (ce == null)
				throw new AdempiereSystemError("CostElement not found: " + TO_AverageInvoice);
			MCost xCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), ce.getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
			if (xCost != null)
				retValue = xCost.getCurrentCostPrice();
		}
		//	Average Invoice History
		else if (to.equals(TO_AverageInvoiceHistory))
		{
			MCostElement ce = getCostElement(TO_AverageInvoice);
			if (ce == null)
				throw new AdempiereSystemError("CostElement not found: " + TO_AverageInvoice);
			MCost xCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), ce.getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
			if (xCost != null) 
				retValue = xCost.getHistoryAverage();
		}
		
		//	Average PO
		else if (to.equals(TO_AveragePO))
		{
			MCostElement ce = getCostElement(TO_AveragePO);
			if (ce == null)
				throw new AdempiereSystemError("CostElement not found: " + TO_AveragePO);
			MCost xCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), ce.getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
			if (xCost != null)
				retValue = xCost.getCurrentCostPrice();
		}
		//	Average PO History
		else if (to.equals(TO_AveragePOHistory))
		{
			MCostElement ce = getCostElement(TO_AveragePO);
			if (ce == null)
				throw new AdempiereSystemError("CostElement not found: " + TO_AveragePO);
			MCost xCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), ce.getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
			if (xCost != null) 
				retValue = xCost.getHistoryAverage();
		}
		
		//	FiFo
		else if (to.equals(TO_FiFo))
		{
			MCostElement ce = getCostElement(TO_FiFo);
			if (ce == null)
				throw new AdempiereSystemError("CostElement not found: " + TO_FiFo);
			MCost xCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), ce.getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
			if (xCost != null)
				retValue = xCost.getCurrentCostPrice();
		}

		//	Future Std Costs
		else if (to.equals(TO_FutureStandardCost))
			retValue = cost.getFutureCostPrice();
		
		//	Last Inv Price
		else if (to.equals(TO_LastInvoicePrice))
		{
			MCostElement ce = getCostElement(TO_LastInvoicePrice);
			if (ce != null)
			{
				MCost xCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), ce.getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
				if (xCost != null)
					retValue = xCost.getCurrentCostPrice();
			}
			if (retValue == null)
			{
				MProduct product = MProduct.get(getCtx(), cost.getM_Product_ID());
				MAcctSchema as = MAcctSchema.get(getCtx(), cost.getC_AcctSchema_ID());
				retValue = MCost.getLastInvoicePrice(product, 
					cost.getM_AttributeSetInstance_ID(), cost.getAD_Org_ID(), as.getC_Currency_ID(),
					trxName);				
			}
		}
		
		//	Last PO Price
		else if (to.equals(TO_LastPOPrice))
		{
			MCostElement ce = getCostElement(TO_LastPOPrice);
			if (ce != null)
			{
				MCost xCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), ce.getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
				if (xCost != null)
					retValue = xCost.getCurrentCostPrice();
			}
			if (retValue == null)
			{
				MProduct product = MProduct.get(getCtx(), cost.getM_Product_ID());
				MAcctSchema as = MAcctSchema.get(getCtx(), cost.getC_AcctSchema_ID());
				retValue = MCost.getLastPOPrice(product, 
					cost.getM_AttributeSetInstance_ID(), cost.getAD_Org_ID(), as.getC_Currency_ID(),
					trxName);				
			}
		}
	
		//	FiFo
		else if (to.equals(TO_LiFo))
		{
			MCostElement ce = getCostElement(TO_LiFo);
			if (ce == null)
				throw new AdempiereSystemError("CostElement not found: " + TO_LiFo);
			MCost xCost = MCost.get(getCtx(), cost.getAD_Client_ID(), cost.getAD_Org_ID(), cost.getM_Product_ID(), cost.getM_CostType_ID(), cost.getC_AcctSchema_ID(), ce.getM_CostElement_ID(), cost.getM_AttributeSetInstance_ID(), trxName);
			if (xCost != null)
				retValue = xCost.getCurrentCostPrice();
		}
		
		//	Old Std Costs
		else if (to.equals(TO_OldStandardCost))
			retValue = getOldCurrentCostPrice(cost);
		
		//	Price List
		else if (to.equals(TO_PriceListLimit))
			retValue = getPrice(cost);
		
		//	Standard Costs
		else if (to.equals(TO_StandardCost))
			retValue = cost.getCurrentCostPrice();
		
		return retValue;
	}	//	getCosts
	
	
	/**
	 * 	Get Cost Element
	 *	@param CostingMethod method
	 *	@return costing element or null
	 */
	private MCostElement getCostElement (String CostingMethod)
	{
		MCostElement ce = m_ces.get(CostingMethod);
		if (ce == null)
		{
			ce = MCostElement.getMaterialCostElement(getCtx(), CostingMethod);
			m_ces.put(CostingMethod, ce);
		}
		return ce;
	}	//	getCostElement

	/**
	 * 	Get Old Current Cost Price
	 *	@param cost costs
	 *	@return price if found
	 */
	private BigDecimal getOldCurrentCostPrice(MCost cost)
	{
		BigDecimal retValue = null;
		String sql = "SELECT CostStandard, CurrentCostPrice "
			+ "FROM M_Product_Costing "
			+ "WHERE M_Product_ID=? AND C_AcctSchema_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, cost.getM_Product_ID());
			pstmt.setInt (2, cost.getC_AcctSchema_ID());
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = rs.getBigDecimal(1);
				if (retValue == null || retValue.signum() == 0)
					retValue = rs.getBigDecimal(2);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		return retValue;
	}	//	getOldCurrentCostPrice

	/**
	 * 	Get Price from Price List
	 * 	@param cost cost record
	 *	@return price or null
	 */
	private BigDecimal getPrice (MCost cost)
	{
		BigDecimal retValue = null;
		String sql = "SELECT PriceLimit "
			+ "FROM M_ProductPrice "
			+ "WHERE M_Product_ID=? AND M_PriceList_Version_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, cost.getM_Product_ID());
			pstmt.setInt (2, p_M_PriceList_Version_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = rs.getBigDecimal(1);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		return retValue;
	}	//	getPrice
	
}	//	CostUpdate
