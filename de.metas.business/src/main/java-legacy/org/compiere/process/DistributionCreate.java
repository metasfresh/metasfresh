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
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

import org.compiere.model.MBPartner;
import org.compiere.model.MDistributionList;
import org.compiere.model.MDistributionListLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.util.Env;

/**
 *	Create Distribution List Order
 *	
 *  @author Jorg Janke
 *  @version $Id: DistributionCreate.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class DistributionCreate extends SvrProcess
{
	/**	Product					*/
	private int 			p_M_Product_ID = 0;
	/** Quantity				*/
	private BigDecimal		p_Qty;
	/**	Single Order			*/
	private boolean			p_IsCreateSingleOrder;
	/** Single Order BP			*/
	private int				p_Bill_BPartner_ID;
	/** SingleOrder Location	*/
	private int				p_Bill_Location_ID;
	/** Test Mode				*/
	private boolean			p_IsTest;
	/**	Distribution List		*/
	private int				p_M_DistributionList_ID;
	
//	DatePromised
//	C_DocType_ID
	
	/**	Distribution List		*/
	private MDistributionList m_dl;
	/** Single Order			*/
	private MOrder			m_singleOrder = null;
	/** Product					*/
	private MProduct		m_product = null;
	/** Total Quantity			*/
	private BigDecimal		m_totalQty = Env.ZERO;
	
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
			else if (name.equals("M_Product_ID"))
				p_M_Product_ID = para[i].getParameterAsInt();
			else if (name.equals("Qty"))
				p_Qty = (BigDecimal)para[i].getParameter();
			else if (name.equals("IsCreateSingleOrder"))
				p_IsCreateSingleOrder = "Y".equals(para[i].getParameter());
			else if (name.equals("Bill_BPartner_ID"))
				p_Bill_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("p_Bill_Location_ID"))
				p_Bill_Location_ID = para[i].getParameterAsInt();
			else if (name.equals("IsTest"))
				p_IsTest = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);		
		}
		p_M_DistributionList_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (text with variables)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		log.info("M_DistributionList_ID=" + p_M_DistributionList_ID 
			+ ", M_Product_ID=" + p_M_Product_ID + ", Qty=" + p_Qty
			+ ", Test=" + p_IsTest);
		if (p_IsCreateSingleOrder)
			log.info("SingleOrder=" + p_IsCreateSingleOrder
				+ ", BPartner_ID=" + p_Bill_BPartner_ID 
				+ ", Location_ID=" + p_Bill_Location_ID);
		//
		if (p_M_DistributionList_ID == 0)
			throw new IllegalArgumentException ("No Distribution List ID");
		m_dl = new MDistributionList(getCtx(), p_M_DistributionList_ID, get_TrxName());
		if (m_dl.get_ID() == 0)
			throw new Exception ("Distribution List not found -  M_DistributionList_ID=" +  p_M_DistributionList_ID);
		//
		if (p_M_Product_ID == 0)
			throw new IllegalArgumentException ("No Product");
		m_product = MProduct.get (getCtx(), p_M_Product_ID);
		if (m_product.get_ID() == 0)
			throw new Exception ("Product not found -  M_Product_ID=" +  p_M_Product_ID);
		if (p_Qty == null || p_Qty.signum() != 1)
			throw new IllegalArgumentException ("No Quantity");
		//
		if (p_IsCreateSingleOrder && p_Bill_BPartner_ID == 0)
			throw new IllegalArgumentException ("Invoice Business Partner required for single Order");
		//	Create Single Order
		if (!p_IsTest && p_IsCreateSingleOrder) 
		{
			MBPartner bp = new MBPartner (getCtx(), p_Bill_BPartner_ID, get_TrxName());
			if (bp.get_ID() == 0)
				throw new IllegalArgumentException("Single Business Partner not found - C_BPartner_ID=" + p_Bill_BPartner_ID);
			//
			m_singleOrder = new MOrder (getCtx(), 0, get_TrxName());
			m_singleOrder.setIsSOTrx(true);
			m_singleOrder.setC_DocTypeTarget_ID(MOrder.DocSubType_Standard);
			m_singleOrder.setBPartner(bp);
			if (p_Bill_Location_ID != 0)
				m_singleOrder.setC_BPartner_Location_ID(p_Bill_Location_ID);
			if (!m_singleOrder.save())
				throw new IllegalStateException("Single Order not created");
		}
		
		MDistributionListLine[] lines = m_dl.getLines();
		int counter = 0;
		for (int i = 0; i < lines.length; i++)
		{
			if (createOrder(lines[i]))
				counter++;
		}
		
		//	Update Qty
		if (m_singleOrder != null)
		{
			m_singleOrder.setDescription("# " + counter + " - " + m_totalQty);
			m_singleOrder.save();
		}
		
		return "@Created@ #" + counter + " - @Qty@=" + m_totalQty;
	}	//	doIt

	/**
	 * 	Create Order for Distribution Line
	 *	@param dll Distribution Line
	 *	@return true if created
	 */
	private boolean createOrder (MDistributionListLine dll)
	{
		MBPartner bp = new MBPartner (getCtx(), dll.getC_BPartner_ID(), get_TrxName());
		if (bp.get_ID() == 0)
			throw new IllegalArgumentException("Business Partner not found - C_BPartner_ID=" + dll.getC_BPartner_ID());

		//	Create Order
		MOrder order = m_singleOrder;
		if (!p_IsTest && order == null)
		{
			order = new MOrder (getCtx(), 0, get_TrxName());
			order.setIsSOTrx(true);
			order.setC_DocTypeTarget_ID(MOrder.DocSubType_Standard);
			order.setBPartner(bp);
			if (dll.getC_BPartner_Location_ID() != 0)
				order.setC_BPartner_Location_ID(dll.getC_BPartner_Location_ID());
			if (!order.save())
			{
				log.error("Order not saved");
				return false;
			}
		}
		//	Calculate Qty
		BigDecimal ratio = dll.getRatio();
		BigDecimal qty = p_Qty.multiply(ratio);
		if (qty.compareTo(Env.ZERO) != 0)
			qty = qty.divide(m_dl.getRatioTotal(), m_product.getUOMPrecision(), BigDecimal.ROUND_HALF_UP);
		BigDecimal minQty = dll.getMinQty();
		if (qty.compareTo(minQty) < 0)
			qty = minQty;
		m_totalQty = m_totalQty.add(qty);
		//
		if (p_IsTest)
		{
			addLog(0,null, qty, bp.getName());
			return false;
		}

		//	Create Order Line
		MOrderLine line = new MOrderLine(order);
		line.setC_BPartner_ID(dll.getC_BPartner_ID());
		if (dll.getC_BPartner_Location_ID() != 0)
			line.setC_BPartner_Location_ID(dll.getC_BPartner_Location_ID());
		//
		line.setM_Product_ID(p_M_Product_ID, true);
		line.setQty(qty);
		line.setPrice();
		if (!line.save())
		{
			log.error("OrderLine not saved");
			return false;
		}
		
		addLog(0,null, qty, order.getDocumentNo() + ": " + bp.getName());
		return true;
	}	//	createOrder
	
}	//	DistributionCreate
