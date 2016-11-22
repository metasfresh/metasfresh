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

import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.util.Env;

/**
 *  Generate Sales Order from Project.
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectGenOrder.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class ProjectGenOrder extends JavaProcess
{
	/**	Project ID from project directly		*/
	private int		m_C_Project_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.error("Unknown Parameter: " + name);
		}
		m_C_Project_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		log.info("C_Project_ID=" + m_C_Project_ID);
		if (m_C_Project_ID == 0)
			throw new IllegalArgumentException("C_Project_ID == 0");
		MProject fromProject = getProject (getCtx(), m_C_Project_ID, get_TrxName());
		Env.setSOTrx(getCtx(), true);	//	Set SO context

		/** @todo duplicate invoice prevention */

		MOrder order = new MOrder (fromProject, true, MOrder.DocSubType_OnCredit);
		if (!order.save())
			throw new Exception("Could not create Order");

		//	***	Lines ***
		int count = 0;
		
		//	Service Project	
		if (MProject.PROJECTCATEGORY_ServiceChargeProject.equals(fromProject.getProjectCategory()))
		{
			/** @todo service project invoicing */
			throw new Exception("Service Charge Projects are on the TODO List");
		}	//	Service Lines

		else	//	Order Lines
		{
			MProjectLine[] lines = fromProject.getLines ();
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine ol = new MOrderLine(order);
				ol.setLine(lines[i].getLine());
				ol.setDescription(lines[i].getDescription());
				//
				ol.setM_Product_ID(lines[i].getM_Product_ID(), true);
				ol.setQty(lines[i].getPlannedQty().subtract(lines[i].getInvoicedQty()));
				ol.setPrice();
				if (lines[i].getPlannedPrice() != null && lines[i].getPlannedPrice().compareTo(Env.ZERO) != 0)
					ol.setPrice(lines[i].getPlannedPrice());
				ol.setDiscount();
				ol.setTax();
				if (ol.save())
					count++;
			}	//	for all lines
			if (lines.length != count)
				log.error("Lines difference - ProjectLines=" + lines.length + " <> Saved=" + count);
		}	//	Order Lines

		return "@C_Order_ID@ " + order.getDocumentNo() + " (" + count + ")";
	}	//	doIt

	/**
	 * 	Get and validate Project
	 * 	@param ctx context
	 * 	@param C_Project_ID id
	 * 	@return valid project
	 * 	@param trxName transaction
	 */
	static protected MProject getProject (Properties ctx, int C_Project_ID, String trxName)
	{
		MProject fromProject = new MProject (ctx, C_Project_ID, trxName);
		if (fromProject.getC_Project_ID() == 0)
			throw new IllegalArgumentException("Project not found C_Project_ID=" + C_Project_ID);
		if (fromProject.getM_PriceList_Version_ID() == 0)
			throw new IllegalArgumentException("Project has no Price List");
		if (fromProject.getM_Warehouse_ID() == 0)
			throw new IllegalArgumentException("Project has no Warehouse");
		if (fromProject.getC_BPartner_ID() == 0 || fromProject.getC_BPartner_Location_ID() == 0)
			throw new IllegalArgumentException("Project has no Business Partner/Location");
		return fromProject;
	}	//	getProject

}	//	ProjectGenOrder
