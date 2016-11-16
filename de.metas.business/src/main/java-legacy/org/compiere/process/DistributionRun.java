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
import java.sql.Timestamp;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDistributionRun;
import org.compiere.model.MDistributionRunDetail;
import org.compiere.model.MDistributionRunLine;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MTable;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.MDDOrder;
import org.eevolution.model.MDDOrderLine;

/**
 *	Create Distribution	
 *	
 *  @author Jorg Janke
 *  @author  victor.perez@e-evolution.com
 * 			<li>FR Let use the Distribution List and Distribution Run for DO
 * 	@see 	http://sourceforge.net/tracker/index.php?func=detail&aid=2030865&group_id=176962&atid=879335		
 *  @version $Id: DistributionRun.java,v 1.4 2006/07/30 00:51:02 jjanke Exp $
 */
public class DistributionRun extends SvrProcess
{
	/**	The Run to execute		*/
	private int					p_M_DistributionRun_ID = 0;
	/**	Date Promised			*/
	private Timestamp			p_DatePromised = null;
	/**	Date Promised To			*/
	//private Timestamp			p_DatePromised_To = null;
	/** Document Type			*/
	private int					p_C_DocType_ID = 0;
	/** Test Mode				*/
	private boolean				p_IsTest = false;
	/** Warehouse to Distribution Order */
	private int 				p_M_Warehouse_ID = 0;
	/** Consolidate Document **/
	private boolean				p_ConsolidateDocument = false;
	/** Distribution List    **/
	private int					p_M_DistributionList_ID = 0;
	/** Distribute Based in DRP Demand **/
	private boolean 			p_BasedInDamnd = false;
	
	/**	Distribution Run			*/
	private MDistributionRun		m_run = null;
	/**	Distribution Run Lines		*/
	private MDistributionRunLine[]	m_runLines = null;
	/** Distribution Run Details	*/
	private MDistributionRunDetail[]	m_details = null;

	/**	Date Ordered			*/
	private Timestamp			m_DateOrdered = null;
	/**	Orders Created			*/
	private int					m_counter = 0;
	/** Document Type			*/
	private MDocType			m_docType = null;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
		//	log.debug("prepare - " + para[i]);
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_DocType_ID"))
			{	
				p_C_DocType_ID = ((BigDecimal)para[i].getParameter()).intValue();
				m_docType = new MDocType(getCtx(),p_C_DocType_ID, get_TrxName());
			}	
			else if (name.equals("DatePromised"))
			{	
				p_DatePromised = (Timestamp)para[i].getParameter();
				//p_DatePromised_To = (Timestamp)para[i].getParameter_To();
			}	
			else if (name.equals("IsTest"))
				p_IsTest = "Y".equals(para[i].getParameter());
			else if (m_docType.getDocBaseType().equals(MDocType.DOCBASETYPE_DistributionOrder) & name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID=((BigDecimal)para[i].getParameter()).intValue();
			else if (m_docType.getDocBaseType().equals(MDocType.DOCBASETYPE_DistributionOrder) & name.equals("ConsolidateDocument"))
				p_ConsolidateDocument="Y".equals((String)para[i].getParameter());
			else if (m_docType.getDocBaseType().equals(MDocType.DOCBASETYPE_DistributionOrder) & name.equals("M_DistributionList_ID"))
				p_M_DistributionList_ID=para[i].getParameterAsInt();
			else if (m_docType.getDocBaseType().equals(MDocType.DOCBASETYPE_DistributionOrder) & name.equals("IsRequiredDRP"))
				p_BasedInDamnd = "Y".equals((String)para[i].getParameter());
			else
				log.error("prepare - Unknown Parameter: " + name);		
		}
		p_M_DistributionRun_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (text with variables)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		log.info("M_DistributionRun_ID=" + p_M_DistributionRun_ID 
			+ ", C_DocType_ID=" + p_C_DocType_ID
			+ ", DatePromised=" + p_DatePromised
			+ ", Test=" + p_IsTest);
		//	Distribution Run
		if (p_M_DistributionRun_ID == 0)
			throw new IllegalArgumentException ("No Distribution Run ID");
		m_run = new MDistributionRun(getCtx(), p_M_DistributionRun_ID, get_TrxName());
		if (m_run.get_ID() == 0)
			throw new Exception ("Distribution Run not found -  M_DistributionRun_ID=" +  p_M_DistributionRun_ID);
		m_runLines = m_run.getLines(true);
		if (m_runLines == null || m_runLines.length == 0)
			throw new Exception ("No active, non-zero Distribution Run Lines found");
		
		//	Document Type
		if (p_C_DocType_ID == 0)
			throw new IllegalArgumentException ("No Document Type ID");
		m_docType = new MDocType(getCtx(), p_C_DocType_ID, null);	//	outside trx
		if (m_docType.get_ID() == 0)
			throw new Exception ("Document Type not found -  C_DocType_ID=" +  p_C_DocType_ID);
		//
		m_DateOrdered = new Timestamp (System.currentTimeMillis());
		if (p_DatePromised == null)
			p_DatePromised = m_DateOrdered;
		
		if(m_docType.getDocBaseType().equals(MDocType.DOCBASETYPE_DistributionOrder)  & p_M_Warehouse_ID > 0)
		{
			if(p_BasedInDamnd)
			{
				if (insertDetailsDistributionDemand() == 0)
					throw new Exception ("No Lines");
				
			}
			else  //Create Temp Lines
			{	
				if (insertDetailsDistribution() == 0)
					throw new Exception ("No Lines");
			}
		}
		else
		{	
			//	Create Temp Lines
			if (insertDetails() == 0)
				throw new Exception ("No Lines");
		}
		
		//	Order By Distribution Run Line
		m_details = MDistributionRunDetail.get(getCtx(), p_M_DistributionRun_ID, false, get_TrxName());
		//	First Run -- Add & Round
		addAllocations ();		

		//	Do Allocation
		int loops = 0;
		while (!isAllocationEqTotal ())
		{
			adjustAllocation();
			addAllocations();
			if (++loops > 10)
				throw new Exception ("Loop detected - more than 10 Allocation attempts");
		}
		
		//	Order By Business Partner
		m_details = MDistributionRunDetail.get(getCtx(), p_M_DistributionRun_ID, true, get_TrxName());
		
		//Implement Distribution Order
		if(m_docType.getDocBaseType().equals(MDocType.DOCBASETYPE_DistributionOrder))
		{
			distributionOrders();
		}
		else {
			//	Create Orders
			createOrders();
		}
		
		return "@Created@ #" + m_counter;
	}	//	doIt
	

	/**
	 * 	Insert Details
	 *	@return number of rows inserted
	 */
	private int insertDetails()
	{
		//	Handle NULL
		String sql = "UPDATE M_DistributionRunLine SET MinQty = 0 WHERE MinQty IS NULL AND M_DistributionRun_ID=?";
		int no = DB.executeUpdateEx(sql,new Object[]{p_M_DistributionRun_ID} ,get_TrxName());
		sql = "UPDATE M_DistributionListLine SET MinQty = 0 WHERE MinQty IS NULL";
		no = DB.executeUpdateEx(sql, get_TrxName());
		//	Total Ratio
		sql = "UPDATE M_DistributionList l "
			+ "SET RatioTotal = (SELECT SUM(Ratio) FROM M_DistributionListLine ll "
				+ " WHERE l.M_DistributionList_ID=ll.M_DistributionList_ID) "
			+ "WHERE EXISTS (SELECT * FROM M_DistributionRunLine rl"
				+ " WHERE l.M_DistributionList_ID=rl.M_DistributionList_ID"
				+ " AND rl.M_DistributionRun_ID=?)";
		no = DB.executeUpdateEx(sql,new Object[]{p_M_DistributionRun_ID},get_TrxName());
		
		//	Delete Old
		sql = "DELETE FROM T_DistributionRunDetail WHERE M_DistributionRun_ID=?";
		no = DB.executeUpdateEx(sql,new Object[]{p_M_DistributionRun_ID}, get_TrxName());
		log.debug("insertDetails - deleted #" + no);
		//	Insert New
		sql = "INSERT INTO T_DistributionRunDetail "
			+ "(M_DistributionRun_ID, M_DistributionRunLine_ID, M_DistributionList_ID, M_DistributionListLine_ID,"
			+ "AD_Client_ID,AD_Org_ID, IsActive, Created,CreatedBy, Updated,UpdatedBy,"
			+ "C_BPartner_ID, C_BPartner_Location_ID, M_Product_ID,"
			+ "Ratio, MinQty, Qty) "
			//
			+ "SELECT rl.M_DistributionRun_ID, rl.M_DistributionRunLine_ID,"
			+ "ll.M_DistributionList_ID, ll.M_DistributionListLine_ID, "
			+ "rl.AD_Client_ID,rl.AD_Org_ID, rl.IsActive, rl.Created,rl.CreatedBy, rl.Updated,rl.UpdatedBy,"
			+ "ll.C_BPartner_ID, ll.C_BPartner_Location_ID, rl.M_Product_ID, "
			+ "ll.Ratio, "
			+ "CASE WHEN rl.MinQty > ll.MinQty THEN rl.MinQty ELSE ll.MinQty END, "
			+ "(ll.Ratio/l.RatioTotal*rl.TotalQty)"
			+ "FROM M_DistributionRunLine rl"
			+ " INNER JOIN M_DistributionList l ON (rl.M_DistributionList_ID=l.M_DistributionList_ID)"
			+ " INNER JOIN M_DistributionListLine ll ON (rl.M_DistributionList_ID=ll.M_DistributionList_ID) "
			+ "WHERE rl.M_DistributionRun_ID=?"
			+ " AND l.RatioTotal<>0 AND rl.IsActive='Y' AND ll.IsActive='Y'";
		no = DB.executeUpdateEx(sql,new Object[]{p_M_DistributionRun_ID}, get_TrxName());
		log.debug("inserted #" + no);
		return no;
	}	//	insertDetails

	
	/**************************************************************************
	 * 	Add up Allocations
	 */
	private void addAllocations ()
	{
		//	Reset
		for (int j = 0; j < m_runLines.length; j++)
		{
			MDistributionRunLine runLine = m_runLines[j];
			runLine.resetCalculations();
		}
		//	Add Up
		for (int i = 0; i < m_details.length; i++)
		{
			MDistributionRunDetail detail = m_details[i];
			for (int j = 0; j < m_runLines.length; j++)
			{
				MDistributionRunLine runLine = m_runLines[j];
				if (runLine.getM_DistributionRunLine_ID() == detail.getM_DistributionRunLine_ID())
				{
					//	Round
					detail.round(runLine.getUOMPrecision());
					//	Add
					runLine.addActualMin(detail.getMinQty());
					runLine.addActualQty(detail.getQty());
					runLine.addActualAllocation(detail.getActualAllocation());
					runLine.setMaxAllocation(detail.getActualAllocation(), false);
					//
					log.debug("RunLine=" + runLine.getLine() 
						+ ": BP_ID=" + detail.getC_BPartner_ID() 
						+ ", Min=" + detail.getMinQty()
						+ ", Qty=" + detail.getQty()
						+ ", Allocation=" + detail.getActualAllocation());
					continue;
				}
			}
		}	//	for all detail lines
		
		//	Info
		for (int j = 0; j < m_runLines.length; j++)
		{
			MDistributionRunLine runLine = m_runLines[j];
			log.debug("Run - " + runLine.getInfo());
		}
	}	//	addAllocations
	
	
	/**
	 * 	Is Allocation Equals Total
	 *	@return true if allocation eq total
	 *	@throws Exception
	 */
	private boolean isAllocationEqTotal() throws Exception
	{
		boolean allocationEqTotal = true;
		//	Check total min qty & delta
		for (int j = 0; j < m_runLines.length; j++)
		{
			MDistributionRunLine runLine = m_runLines[j];
			if (runLine.isActualMinGtTotal())
				throw new Exception ("Line " + runLine.getLine() 
					+ " Sum of Min Qty=" + runLine.getActualMin() 
					+ " is greater than Total Qty=" + runLine.getTotalQty());
			if (allocationEqTotal && !runLine.isActualAllocationEqTotal())
				allocationEqTotal = false;
		}	//	for all run lines
		log.info("=" + allocationEqTotal);
		return allocationEqTotal;
	}	//	isAllocationEqTotal

	
	/**
	 * 	Adjust Allocation
	 * 	@throws Exception
	 */
	private void adjustAllocation() throws Exception
	{
		for (int j = 0; j < m_runLines.length; j++)
			adjustAllocation(j);
	}	//	adjustAllocation
	
	/**
	 * 	Adjust Run Line Allocation
	 * 	@param index run line index
	 * 	@throws Exception
	 */
	private void adjustAllocation(int index) throws Exception
	{
		MDistributionRunLine runLine = m_runLines[index];
		BigDecimal difference = runLine.getActualAllocationDiff();
		if (difference.compareTo(Env.ZERO) == 0)
			return;
		//	Adjust when difference is -1->1 or last difference is the same 
		boolean adjustBiggest = difference.abs().compareTo(Env.ONE) <= 0
			|| difference.abs().compareTo(runLine.getLastDifference().abs()) == 0;
		log.debug("Line=" + runLine.getLine() 
			+ ", Diff=" + difference + ", Adjust=" + adjustBiggest);
		//	Adjust Biggest Amount
		if (adjustBiggest)
		{
			for (int i = 0; i < m_details.length; i++)
			{
				MDistributionRunDetail detail = m_details[i];
				if (runLine.getM_DistributionRunLine_ID() == detail.getM_DistributionRunLine_ID())
				{
					log.debug("Biggest - DetailAllocation=" + detail.getActualAllocation()
						+ ", MaxAllocation=" + runLine.getMaxAllocation() 
						+ ", Qty Difference=" + difference);
					if (detail.getActualAllocation().compareTo(runLine.getMaxAllocation()) == 0
						&& detail.isCanAdjust())
					{
						detail.adjustQty(difference);
						detail.save();
						return;
					}
				}
			}	//	for all detail lines
			throw new Exception ("Cannot adjust Difference = " + difference 
				+ " - You need to change Total Qty or Min Qty");
		}
		else	//	Distibute
		{
			//	New Total Ratio
			BigDecimal ratioTotal = Env.ZERO;
			for (int i = 0; i < m_details.length; i++)
			{
				MDistributionRunDetail detail = m_details[i];
				if (runLine.getM_DistributionRunLine_ID() == detail.getM_DistributionRunLine_ID())
				{
					if (detail.isCanAdjust())
						ratioTotal = ratioTotal.add(detail.getRatio());
				}
			}
			if (ratioTotal.compareTo(Env.ZERO) == 0)
				throw new Exception ("Cannot distribute Difference = " + difference 
					+ " - You need to change Total Qty or Min Qty");
			//	Distribute
			for (int i = 0; i < m_details.length; i++)
			{
				MDistributionRunDetail detail = m_details[i];
				if (runLine.getM_DistributionRunLine_ID() == detail.getM_DistributionRunLine_ID())
				{
					if (detail.isCanAdjust())
					{
						BigDecimal diffRatio = detail.getRatio().multiply(difference)
							.divide(ratioTotal, BigDecimal.ROUND_HALF_UP);	// precision from total
						log.debug("Detail=" + detail.toString()
							+ ", Allocation=" + detail.getActualAllocation()
							+ ", DiffRatio=" + diffRatio);
						detail.adjustQty(diffRatio);
						detail.save();
					}
				}
			}
		}
		runLine.setLastDifference(difference);
	}	//	adjustAllocation

	
	/**************************************************************************
	 * 	Create Orders
	 * 	@return true if created
	 */
	private boolean createOrders()
	{
		//	Get Counter Org/BP
		int runAD_Org_ID = m_run.getAD_Org_ID();
		if (runAD_Org_ID == 0)
			runAD_Org_ID = Env.getAD_Org_ID(getCtx());
		MOrg runOrg = MOrg.get(getCtx(), runAD_Org_ID);
		int runC_BPartner_ID = runOrg.getLinkedC_BPartner_ID(get_TrxName());
		boolean counter = !m_run.isCreateSingleOrder()	//	no single Order 
			&& runC_BPartner_ID > 0						//	Org linked to BP
			&& !m_docType.isSOTrx();					//	PO
		MBPartner runBPartner = counter ? new MBPartner(getCtx(), runC_BPartner_ID, get_TrxName()) : null;
		if (!counter || runBPartner == null || runBPartner.get_ID() != runC_BPartner_ID)
			counter = false;
		if (counter)
			log.info("RunBP=" + runBPartner
				+ " - " + m_docType);
		log.info("Single=" + m_run.isCreateSingleOrder()
			+ " - " + m_docType + ",SO=" + m_docType.isSOTrx());
		log.debug("Counter=" + counter 
			+ ",C_BPartner_ID=" + runC_BPartner_ID + "," + runBPartner);
		//
		MBPartner bp = null;
		MOrder singleOrder = null;
		MProduct product = null;
		//	Consolidated Order
		if (m_run.isCreateSingleOrder())
		{
			bp = new MBPartner (getCtx(), m_run.getC_BPartner_ID(), get_TrxName());
			if (bp.get_ID() == 0)
				throw new IllegalArgumentException("Business Partner not found - C_BPartner_ID=" + m_run.getC_BPartner_ID());
			//
			if (!p_IsTest)
			{
				singleOrder = new MOrder (getCtx(), 0, get_TrxName());
				singleOrder.setC_DocTypeTarget_ID(m_docType.getC_DocType_ID());
				singleOrder.setC_DocType_ID(m_docType.getC_DocType_ID());
				singleOrder.setIsSOTrx(m_docType.isSOTrx());
				singleOrder.setBPartner(bp);
				if (m_run.getC_BPartner_Location_ID() != 0)
					singleOrder.setC_BPartner_Location_ID(m_run.getC_BPartner_Location_ID());
				singleOrder.setDateOrdered(m_DateOrdered);
				singleOrder.setDatePromised(p_DatePromised);
				if (!singleOrder.save())
				{
					log.error("Order not saved");
					return false;
				}
				m_counter++;
			}
		}

		int lastC_BPartner_ID = 0;
		int lastC_BPartner_Location_ID = 0;
		MOrder order = null;
		//	For all lines
		for (int i = 0; i < m_details.length; i++)
		{
			MDistributionRunDetail detail = m_details[i];
			
			//	Create Order Header
			if (m_run.isCreateSingleOrder())
				order = singleOrder;
			//	New Business Partner
			else if (lastC_BPartner_ID != detail.getC_BPartner_ID()
				|| lastC_BPartner_Location_ID != detail.getC_BPartner_Location_ID())
			{
				//	finish order
				order = null;
			}
			lastC_BPartner_ID = detail.getC_BPartner_ID();
			lastC_BPartner_Location_ID = detail.getC_BPartner_Location_ID();
			
			//	New Order
			if (order == null)
			{
				bp = new MBPartner (getCtx(), detail.getC_BPartner_ID(), get_TrxName());
				if (!p_IsTest)
				{
					order = new MOrder (getCtx(), 0, get_TrxName());
					order.setC_DocTypeTarget_ID(m_docType.getC_DocType_ID());
					order.setC_DocType_ID(m_docType.getC_DocType_ID());
					order.setIsSOTrx(m_docType.isSOTrx());
					//	Counter Doc
					if (counter && bp.getAD_OrgBP_ID_Int() > 0)
					{
						log.debug("Counter - From_BPOrg=" + bp.getAD_OrgBP_ID_Int() 
							+ "-" + bp + ", To_BP=" + runBPartner);
						order.setAD_Org_ID(bp.getAD_OrgBP_ID_Int());
						MOrgInfo oi = MOrgInfo.get(getCtx(), bp.getAD_OrgBP_ID_Int());
						if (oi.getM_Warehouse_ID() > 0)
							order.setM_Warehouse_ID(oi.getM_Warehouse_ID());
						order.setBPartner(runBPartner);
					}
					else	//	normal
					{
						log.debug("From_Org=" + runAD_Org_ID 
							+ ", To_BP=" + bp);
						order.setAD_Org_ID(runAD_Org_ID);
						order.setBPartner(bp);
						if (detail.getC_BPartner_Location_ID() != 0)
							order.setC_BPartner_Location_ID(detail.getC_BPartner_Location_ID());
					}
					order.setDateOrdered(m_DateOrdered);
					order.setDatePromised(p_DatePromised);
					if (!order.save())
					{
						log.error("Order not saved");
						return false;
					}
				}
			}
			
			//	Line
			if (product == null || product.getM_Product_ID() != detail.getM_Product_ID())
				product = MProduct.get (getCtx(), detail.getM_Product_ID());
			if (p_IsTest)
			{
				addLog(0,null, detail.getActualAllocation(), 
					bp.getName() + " - " + product.getName());
				continue;
			}

			//	Create Order Line
			MOrderLine line = new MOrderLine(order);
			if (counter && bp.getAD_OrgBP_ID_Int() > 0)
				;	//	don't overwrite counter doc
			else	//	normal - optionally overwrite
			{
				line.setC_BPartner_ID(detail.getC_BPartner_ID());
				if (detail.getC_BPartner_Location_ID() != 0)
					line.setC_BPartner_Location_ID(detail.getC_BPartner_Location_ID());
			}
			//
			line.setProduct(product);
			line.setQty(detail.getActualAllocation());
			line.setPrice();
			if (!line.save())
			{
				log.error("OrderLine not saved");
				return false;
			}
			addLog(0,null, detail.getActualAllocation(), order.getDocumentNo() 
				+ ": " + bp.getName() + " - " + product.getName());
		}
		//	finish order
		order = null;

		
		return true;
	}	//	createOrders
	
	
	/**
	 * 	Insert Details
	 *	@return number of rows inserted
	 */
	private int insertDetailsDistributionDemand()
	{
		//	Handle NULL
		String sql = "UPDATE M_DistributionRunLine SET MinQty = 0 WHERE MinQty IS NULL";
		int no = DB.executeUpdate(sql, get_TrxName());
		
		sql = "UPDATE M_DistributionListLine SET MinQty = 0 WHERE MinQty IS NULL";
		no = DB.executeUpdate(sql, get_TrxName());
		
		//	Delete Old
		sql = "DELETE FROM T_DistributionRunDetail WHERE M_DistributionRun_ID="
			+ p_M_DistributionRun_ID;
		no = DB.executeUpdate(sql, get_TrxName());
		log.debug("insertDetails - deleted #" + no);

		//	Insert New
		sql = "INSERT INTO T_DistributionRunDetail "
			+ "(M_DistributionRun_ID, M_DistributionRunLine_ID, M_DistributionList_ID, M_DistributionListLine_ID,"
			+ "AD_Client_ID,AD_Org_ID, IsActive, Created,CreatedBy, Updated,UpdatedBy,"
			+ "C_BPartner_ID, C_BPartner_Location_ID, M_Product_ID,"
			+ "Ratio, MinQty, Qty) "			
			+"SELECT MAX(rl.M_DistributionRun_ID), MAX(rl.M_DistributionRunLine_ID),MAX(ll.M_DistributionList_ID), MAX(ll.M_DistributionListLine_ID), "
			+"MAX(rl.AD_Client_ID),MAX(rl.AD_Org_ID), MAX(rl.IsActive), MAX(rl.Created),MAX(rl.CreatedBy), MAX(rl.Updated),MAX(rl.UpdatedBy), "
			+"MAX(ll.C_BPartner_ID), MAX(ll.C_BPartner_Location_ID), MAX(rl.M_Product_ID),"
			// Ration for this process is equal QtyToDeliver
			+"COALESCE (SUM(ol.QtyOrdered-ol.QtyDelivered-TargetQty), 0) , "
			// Min Qty for this process is equal to TargetQty
			+" 0 , 0 FROM M_DistributionRunLine rl "
			+"INNER JOIN M_DistributionList l ON (rl.M_DistributionList_ID=l.M_DistributionList_ID) "
			+"INNER JOIN M_DistributionListLine ll ON (rl.M_DistributionList_ID=ll.M_DistributionList_ID) "
			+"INNER JOIN DD_Order o ON (o.C_BPartner_ID=ll.C_BPartner_ID AND o.DocStatus IN ('DR','IN')) "
			+"INNER JOIN DD_OrderLine ol ON (ol.DD_Order_ID=o.DD_Order_ID AND ol.M_Product_ID=rl.M_Product_ID) "		
			+"INNER JOIN M_Locator loc ON (loc.M_Locator_ID=ol.M_Locator_ID AND loc.M_Warehouse_ID="+p_M_Warehouse_ID+") "
			+"WHERE rl.M_DistributionRun_ID="+p_M_DistributionRun_ID+" AND rl.IsActive='Y' AND ll.IsActive='Y' AND ol.DatePromised <= "+DB.TO_DATE(p_DatePromised)
			+" GROUP BY o.M_Shipper_ID , ll.C_BPartner_ID, ol.M_Product_ID";
			//+ " BETWEEN "+ DB.TO_DATE(p_DatePromised)  +" AND "+ DB.TO_DATE(p_DatePromised_To) 	
			no = DB.executeUpdate(sql, get_TrxName());
			
			List<MDistributionRunDetail> records = new Query(getCtx(), 
												   MDistributionRunDetail.Table_Name,
												   MDistributionRunDetail.COLUMNNAME_M_DistributionRun_ID + "=?",												  
												   get_TrxName()).setParameters( new Object[]{p_M_DistributionRun_ID}).list();
			
			for(MDistributionRunDetail record : records)
			{
				
					MDistributionRunLine drl = (MDistributionRunLine) MTable.get(getCtx(), MDistributionRunLine.Table_ID).getPO(record.getM_DistributionRunLine_ID(), get_TrxName());
					MProduct product = MProduct.get(getCtx(), record.getM_Product_ID());					
					BigDecimal ration = record.getRatio();
					BigDecimal totalration = getQtyDemand(record.getM_Product_ID());
					log.info("Value:" + product.getValue());
					log.info("Product:" + product.getName());
					log.info("Qty To Deliver:" + record.getRatio());
					log.info("Qty Target:" + record.getMinQty());
					log.info("Qty Total Available:" + drl.getTotalQty());
					log.info("Qty Total Demand:" +  totalration);			
					BigDecimal factor = ration.divide(totalration, 12 , BigDecimal.ROUND_HALF_UP);
					record.setQty(drl.getTotalQty().multiply(factor));
					record.save();
			}			
		log.debug("inserted #" + no);
		return no;
	}	//	insertDetails
	
	private BigDecimal getQtyDemand(int M_Product_ID)
	{
		StringBuffer sql = new StringBuffer("SELECT SUM (QtyOrdered-QtyDelivered-TargetQty)  FROM DD_OrderLine ol INNER JOIN M_Locator l ON (l.M_Locator_ID=ol.M_Locator_ID) INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) ");
    	//sql.append(" WHERE o.DocStatus IN ('DR','IN') AND ol.DatePromised BETWEEN ? AND ? AND l.M_Warehouse_ID=? AND ol.M_Product_ID=? GROUP BY M_Product_ID, l.M_Warehouse_ID");
		sql.append(" WHERE o.DocStatus IN ('DR','IN') AND ol.DatePromised <= ? AND l.M_Warehouse_ID=? AND ol.M_Product_ID=? GROUP BY M_Product_ID, l.M_Warehouse_ID");

    	
 	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
 	    try
 	    {
 	            pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
 	    		pstmt.setTimestamp(1, p_DatePromised);
 	    		//pstmt.setTimestamp(2, p_DatePromised_To);
 	    		pstmt.setInt(2, p_M_Warehouse_ID);
 	    		pstmt.setInt(3, M_Product_ID);
 	    		
 	            rs = pstmt.executeQuery();
 	            while (rs.next())
 	            {
 	            	return rs.getBigDecimal(1);
 	            }
 		}
 	    catch (Exception e)
 		{
 	            	log.error("doIt - " + sql, e);
 	                return Env.ZERO;
 		}
 		finally
 		{
 			DB.close(rs, pstmt);
 			rs = null;
 			pstmt = null;
 		}	    
    	 
    	return Env.ZERO; 
     }
	
	
	
	/**
	 * 	Insert Details
	 *	@return number of rows inserted
	 */
	private int insertDetailsDistribution()
	{
		//	Handle NULL
		String sql = "UPDATE M_DistributionRunLine SET MinQty = 0 WHERE MinQty IS NULL";
		int no = DB.executeUpdate(sql, get_TrxName());
		
		sql = "UPDATE M_DistributionListLine SET MinQty = 0 WHERE MinQty IS NULL";
		no = DB.executeUpdate(sql, get_TrxName());
		
		//	Delete Old
		sql = "DELETE FROM T_DistributionRunDetail WHERE M_DistributionRun_ID="
			+ p_M_DistributionRun_ID;
		no = DB.executeUpdate(sql, get_TrxName());
		log.debug("insertDetails - deleted #" + no);
		
		//	Insert New
		sql = "INSERT INTO T_DistributionRunDetail "
			+ "(M_DistributionRun_ID, M_DistributionRunLine_ID, M_DistributionList_ID, M_DistributionListLine_ID,"
			+ "AD_Client_ID,AD_Org_ID, IsActive, Created,CreatedBy, Updated,UpdatedBy,"
			+ "C_BPartner_ID, C_BPartner_Location_ID, M_Product_ID,"
			+ "Ratio, MinQty, Qty) "			
			+"SELECT rl.M_DistributionRun_ID, rl.M_DistributionRunLine_ID,ll.M_DistributionList_ID, ll.M_DistributionListLine_ID, "
			+"rl.AD_Client_ID,rl.AD_Org_ID, rl.IsActive, rl.Created,rl.CreatedBy, rl.Updated,rl.UpdatedBy, "
			+"ll.C_BPartner_ID, ll.C_BPartner_Location_ID, rl.M_Product_ID, 0 , "
			+"ol.TargetQty AS MinQty , 0 FROM M_DistributionRunLine rl "
			+"INNER JOIN M_DistributionList l ON (rl.M_DistributionList_ID=l.M_DistributionList_ID) "
			+"INNER JOIN M_DistributionListLine ll ON (rl.M_DistributionList_ID=ll.M_DistributionList_ID) "
			+"INNER JOIN DD_Order o ON (o.C_BPartner_ID=ll.C_BPartner_ID) "
			+"INNER JOIN DD_OrderLine ol ON (ol.DD_Order_ID=o.DD_Order_ID AND ol.M_Product_ID=rl.M_Product_ID) AND ol.DatePromised"
			//+ " BETWEEN " + DB.TO_DATE(p_DatePromised)  +" AND "+ DB.TO_DATE(p_DatePromised_To) 
			+ "<="+DB.TO_DATE(p_DatePromised)
			+" INNER JOIN M_Locator loc ON (loc.M_Locator_ID=ol.M_Locator_ID AND loc.M_Warehouse_ID="+p_M_Warehouse_ID+") "
			+" WHERE rl.M_DistributionRun_ID="+p_M_DistributionRun_ID+" AND l.RatioTotal<>0 AND rl.IsActive='Y' AND ll.IsActive='Y'";	
			no = DB.executeUpdate(sql, get_TrxName());
			
			Query query = MTable.get(getCtx(), MDistributionRunDetail.Table_ID).
			createQuery(MDistributionRunDetail.COLUMNNAME_M_DistributionRun_ID + "=?", get_TrxName());
			query.setParameters(new Object[]{p_M_DistributionRun_ID});
			
			List<MDistributionRunDetail> records = query.list();
			
			for(MDistributionRunDetail record : records)
			{
					BigDecimal total_ration = DB.getSQLValueBD(get_TrxName(), 
					"SELECT SUM(Ratio) FROM T_DistributionRunDetail WHERE M_DistributionRun_ID=? AND M_Product_ID=? GROUP BY  M_Product_ID"
					, p_M_DistributionRun_ID, record.getM_Product_ID());
					MDistributionRunLine drl = (MDistributionRunLine) MTable.get(getCtx(), MDistributionRunLine.Table_ID).getPO(record.getM_DistributionRunLine_ID(), get_TrxName());
					BigDecimal ration = record.getRatio();
					BigDecimal factor = ration.divide(total_ration,BigDecimal.ROUND_HALF_UP);
					record.setQty(factor.multiply(drl.getTotalQty()));
					record.save();
			}			
		log.debug("inserted #" + no);
		return no;
	}	//	insertDetails
	
	
	/**************************************************************************
	 * 	Create Orders
	 * 	@return true if created
	 */
	private boolean distributionOrders()
	{
		//The Quantity Available is distribute with respect to Distribution Order Demand
		if (p_BasedInDamnd)	
		{	
			int M_Warehouse_ID = 0;
			if (p_M_Warehouse_ID <= 0)
			{
				MOrgInfo oi_source = MOrgInfo.get(getCtx(), m_run.getAD_Org_ID());
				MWarehouse m_source = MWarehouse.get(getCtx(), oi_source.getM_Warehouse_ID());
				if(m_source == null)
					throw new AdempiereException("Do not exist Defautl Warehouse Source");
				M_Warehouse_ID = m_source.getM_Warehouse_ID();
			}
			else 
				M_Warehouse_ID = p_M_Warehouse_ID;
			
			//			For all lines
			for (int i = 0; i < m_details.length; i++)
			{
				MDistributionRunDetail detail = m_details[i];
				
				StringBuffer sql = new StringBuffer("SELECT * FROM DD_OrderLine ol INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID)  INNER JOIN M_Locator l ON (l.M_Locator_ID=ol.M_Locator_ID) ");
				//sql.append(" WHERE o.DocStatus IN ('DR','IN') AND o.C_BPartner_ID = ? AND M_Product_ID=? AND  l.M_Warehouse_ID=?  AND ol.DatePromised BETWEEN ? AND ? ");
				sql.append(" WHERE o.DocStatus IN ('DR','IN') AND o.C_BPartner_ID = ? AND M_Product_ID=? AND  l.M_Warehouse_ID=?  AND ol.DatePromised <=?");
				
		 	    PreparedStatement pstmt = null;
			    ResultSet rs = null; 
		 	    try
		 	    {
		 	    		pstmt = DB.prepareStatement (sql.toString(),get_TrxName());
		 	    		pstmt.setInt(1, detail.getC_BPartner_ID());
		 	    		pstmt.setInt(2, detail.getM_Product_ID());
		 	    		pstmt.setInt(3, M_Warehouse_ID);
		 	    		pstmt.setTimestamp(4, p_DatePromised);
		 	    		//pstmt.setTimestamp(5, p_DatePromised_To);

		 	            rs = pstmt.executeQuery();
		 	            while (rs.next())
		 	            {           	
			 	   			//	Create Order Line
			 	   			MDDOrderLine line = new MDDOrderLine(getCtx(), rs , get_TrxName());
			 	   			line.setM_Product_ID(detail.getM_Product_ID());
			 	   			line.setConfirmedQty(line.getTargetQty().add(detail.getActualAllocation()));
			 	   			if(p_M_Warehouse_ID>0)
			 	   			line.setDescription(Msg.translate(getCtx(), "PlannedQty"));
			 	   			else 
			 	   			line.setDescription(m_run.getName());
			 	   			line.save();
			 	   			break;
			 	   			//addLog(0,null, detail.getActualAllocation(), order.getDocumentNo() 
			 	   			//	+ ": " + bp.getName() + " - " + product.getName());
		 	            }
	 	           
		 		}
		 	    catch (Exception e)
		 		{
		 	            	log.error("doIt - " + sql, e);
		 	                return false;
		 		}
		 		finally
		 		{
		 			DB.close(rs, pstmt);
		 			rs = null;
		 			pstmt = null;
		 		}	
			}	
			return true;
		}
		
		//		Get Counter Org/BP
		int runAD_Org_ID = m_run.getAD_Org_ID();
		if (runAD_Org_ID == 0)
			runAD_Org_ID = Env.getAD_Org_ID(getCtx());
		MOrg runOrg = MOrg.get(getCtx(), runAD_Org_ID);
		int runC_BPartner_ID = runOrg.getLinkedC_BPartner_ID(get_TrxName());
		boolean counter = !m_run.isCreateSingleOrder()	//	no single Order 
			&& runC_BPartner_ID > 0						//	Org linked to BP
			&& !m_docType.isSOTrx();					//	PO
		MBPartner runBPartner = counter ? new MBPartner(getCtx(), runC_BPartner_ID, get_TrxName()) : null;
		if (!counter || runBPartner == null || runBPartner.get_ID() != runC_BPartner_ID)
			counter = false;
		if (counter)
			log.info("RunBP=" + runBPartner
				+ " - " + m_docType);
		log.info("Single=" + m_run.isCreateSingleOrder()
			+ " - " + m_docType + ",SO=" + m_docType.isSOTrx());
		log.debug("Counter=" + counter 
			+ ",C_BPartner_ID=" + runC_BPartner_ID + "," + runBPartner);
		//
		MBPartner bp = null;
		MDDOrder singleOrder = null;
		MProduct product = null;
		
		MWarehouse 	 m_source = null;
		MLocator m_locator= null ;
		MWarehouse  m_target= null;
		MLocator m_locator_to= null;
		MWarehouse[] ws = null;
		
		MOrgInfo oi_source = MOrgInfo.get(getCtx(), m_run.getAD_Org_ID());
		m_source = MWarehouse.get(getCtx(), oi_source.getM_Warehouse_ID());
		if(m_source == null)
			throw new AdempiereException("Do not exist Defautl Warehouse Source");
		
		m_locator =  MLocator.getDefault(m_source);
		
		//get the warehouse in transit
		ws = MWarehouse.getInTransitForOrg(getCtx(), m_source.getAD_Org_ID());
		
		if(ws==null)
			throw new AdempiereException("Warehouse Intransit do not found");
		
		
		//	Consolidated Single Order 
		if (m_run.isCreateSingleOrder())
		{
			bp = new MBPartner (getCtx(), m_run.getC_BPartner_ID(), get_TrxName());
			if (bp.get_ID() == 0)
				throw new IllegalArgumentException("Business Partner not found - C_BPartner_ID=" + m_run.getC_BPartner_ID());
			//
			if (!p_IsTest)
			{
				singleOrder = new MDDOrder (getCtx(), 0, get_TrxName());
				singleOrder.setC_DocType_ID(m_docType.getC_DocType_ID());
				singleOrder.setIsSOTrx(m_docType.isSOTrx());
				singleOrder.setBPartner(bp);
				if (m_run.getC_BPartner_Location_ID() != 0)
					singleOrder.setC_BPartner_Location_ID(m_run.getC_BPartner_Location_ID());
				singleOrder.setDateOrdered(m_DateOrdered);
				singleOrder.setDatePromised(p_DatePromised);
				singleOrder.setM_Warehouse_ID(ws[0].getM_Warehouse_ID());
				if (!singleOrder.save())
				{
					log.error("Order not saved");
					return false;
				}
				m_counter++;
			}
		}
		
		int lastC_BPartner_ID = 0;
		int lastC_BPartner_Location_ID = 0;
		MDDOrder order = null;

		
		//	For all lines
		for (int i = 0; i < m_details.length; i++)
		{
			MDistributionRunDetail detail = m_details[i];
			
			//	Create Order Header
			if (m_run.isCreateSingleOrder())
				order = singleOrder;
			//	New Business Partner
			else if (lastC_BPartner_ID != detail.getC_BPartner_ID()
				|| lastC_BPartner_Location_ID != detail.getC_BPartner_Location_ID())
			{
				//	finish order
				order = null;
			}
			lastC_BPartner_ID = detail.getC_BPartner_ID();
			lastC_BPartner_Location_ID = detail.getC_BPartner_Location_ID();
			
			bp = new MBPartner (getCtx(), detail.getC_BPartner_ID(), get_TrxName());
			MOrgInfo oi_target = MOrgInfo.get(getCtx(), bp.getAD_OrgBP_ID_Int());
			m_target = MWarehouse.get(getCtx(), oi_target.getM_Warehouse_ID());
			if(m_target==null)
				throw new AdempiereException("Do not exist Default Warehouse Target");
			
			m_locator_to = MLocator.getDefault(m_target); 

			if (m_locator == null || m_locator_to == null)
			{
				throw new AdempiereException("Do not exist default Locator for Warehouses");
			}
			
			if(p_ConsolidateDocument)
			{
				
				String whereClause = "DocStatus IN ('DR','IN') AND AD_Org_ID=" + bp.getAD_OrgBP_ID_Int() +	" AND "	+	
									    MDDOrder.COLUMNNAME_C_BPartner_ID  +"=? AND " +
									    MDDOrder.COLUMNNAME_M_Warehouse_ID +"=?  AND " +
									    MDDOrder.COLUMNNAME_DatePromised   +"<=? ";
				
				order = new Query(getCtx(), MDDOrder.Table_Name, whereClause, get_TrxName())
							.setParameters(new Object[]{lastC_BPartner_ID, ws[0].getM_Warehouse_ID(), p_DatePromised})
							.setOrderBy(MDDOrder.COLUMNNAME_DatePromised +" DESC")
							.first();
		}
			
			//	New Order
			if (order == null)
			{
				if (!p_IsTest)
				{
					order = new MDDOrder (getCtx(), 0, get_TrxName());
					order.setAD_Org_ID(bp.getAD_OrgBP_ID_Int());
					order.setC_DocType_ID(m_docType.getC_DocType_ID());
					order.setIsSOTrx(m_docType.isSOTrx());					

					//	Counter Doc
					if (counter && bp.getAD_OrgBP_ID_Int() > 0)
					{
						log.debug("Counter - From_BPOrg=" + bp.getAD_OrgBP_ID_Int() 
							+ "-" + bp + ", To_BP=" + runBPartner);
						order.setAD_Org_ID(bp.getAD_OrgBP_ID_Int());
						if (ws[0].getM_Warehouse_ID() > 0)
						order.setM_Warehouse_ID(ws[0].getM_Warehouse_ID());
						order.setBPartner(runBPartner);
					}
					else	//	normal
					{
						log.debug("From_Org=" + runAD_Org_ID 
							+ ", To_BP=" + bp);
						order.setAD_Org_ID(bp.getAD_OrgBP_ID_Int());
						order.setBPartner(bp);
						if (detail.getC_BPartner_Location_ID() != 0)
							order.setC_BPartner_Location_ID(detail.getC_BPartner_Location_ID());
					}
					order.setM_Warehouse_ID(ws[0].getM_Warehouse_ID());
					order.setDateOrdered(m_DateOrdered);
					order.setDatePromised(p_DatePromised);
					order.setIsInDispute(false);
					order.setIsInTransit(false);
					if (!order.save())
					{
						log.error("Order not saved");
						return false;
					}
				}
			}
			
			//	Line
			if (product == null || product.getM_Product_ID() != detail.getM_Product_ID())
				product = MProduct.get (getCtx(), detail.getM_Product_ID());
			if (p_IsTest)
			{
				addLog(0,null, detail.getActualAllocation(), 
					bp.getName() + " - " + product.getName());
				continue;
			}

			if(p_ConsolidateDocument)
			{

				String sql = "SELECT DD_OrderLine_ID FROM DD_OrderLine ol INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) WHERE o.DocStatus IN ('DR','IN') AND o.C_BPartner_ID = ? AND M_Product_ID=? AND  ol.M_Locator_ID=?  AND ol.DatePromised <= ?";				
				int DD_OrderLine_ID = DB.getSQLValueEx(get_TrxName(), sql, new Object[]{detail.getC_BPartner_ID(),product.getM_Product_ID(), m_locator.getM_Locator_ID(), p_DatePromised});	
				if (DD_OrderLine_ID  <= 0)
				{	
					MDDOrderLine line = new MDDOrderLine(order);
					line.setAD_Org_ID(bp.getAD_OrgBP_ID_Int());
					line.setM_Locator_ID(m_locator.getM_Locator_ID());
					line.setM_LocatorTo_ID(m_locator_to.getM_Locator_ID());
					line.setIsInvoiced(false);
					line.setProduct(product);
					BigDecimal QtyAllocation = detail.getActualAllocation();
					if(QtyAllocation == null)
						QtyAllocation = Env.ZERO;
					
					line.setQty(QtyAllocation);
					line.setQtyEntered(QtyAllocation);
					//line.setTargetQty(detail.getActualAllocation());
					line.setTargetQty(Env.ZERO);
					String Description ="";
					if (m_run.getName() != null)
						Description =Description.concat(m_run.getName());
					line.setDescription(Description + " " +Msg.translate(getCtx(), "Qty")+ " = " +QtyAllocation+" ");
					//line.setConfirmedQty(QtyAllocation);
					line.saveEx();
				}
				else 
				{
					MDDOrderLine line = new MDDOrderLine(getCtx(), DD_OrderLine_ID, get_TrxName());		
					BigDecimal QtyAllocation = detail.getActualAllocation();
					if(QtyAllocation == null)
						QtyAllocation = Env.ZERO;
					String Description = line.getDescription();
					if (Description ==  null)
						Description ="";
					if (m_run.getName() != null)
						Description =Description.concat(m_run.getName());
					line.setDescription(Description + " " +Msg.translate(getCtx(), "Qty")+ " = " +QtyAllocation+" ");
					line.setQty(line.getQtyEntered().add(QtyAllocation));
					//line.setConfirmedQty(line.getConfirmedQty().add( QtyAllocation));
					line.saveEx();
				}
			}
			else
			{	
				//	Create Order Line
				MDDOrderLine line = new MDDOrderLine(order);
				if (counter && bp.getAD_OrgBP_ID_Int() > 0)
					;	//	don't overwrite counter doc
				/*else	//	normal - optionally overwrite
				{
					line.setC_BPartner_ID(detail.getC_BPartner_ID());
					if (detail.getC_BPartner_Location_ID() != 0)
						line.setC_BPartner_Location_ID(detail.getC_BPartner_Location_ID());
				}*/
				//
				line.setAD_Org_ID(bp.getAD_OrgBP_ID_Int());
				line.setM_Locator_ID(m_locator.getM_Locator_ID());
				line.setM_LocatorTo_ID(m_locator_to.getM_Locator_ID());
				line.setIsInvoiced(false);
				line.setProduct(product);
				line.setQty(detail.getActualAllocation());
				line.setQtyEntered(detail.getActualAllocation());
				//line.setTargetQty(detail.getActualAllocation());
				line.setTargetQty(Env.ZERO);
				//line.setConfirmedQty(detail.getActualAllocation());
				String Description ="";
				if (m_run.getName() != null)
					Description =Description.concat(m_run.getName());
				line.setDescription(Description + " " +Msg.translate(getCtx(), "Qty")+ " = " +detail.getActualAllocation()+" ");
				line.saveEx();
				
			}	
			addLog(0,null, detail.getActualAllocation(), order.getDocumentNo() 
				+ ": " + bp.getName() + " - " + product.getName());
		}
		//	finish order
		order = null;
		return true;
	}
	
}	//	DistributionRun
