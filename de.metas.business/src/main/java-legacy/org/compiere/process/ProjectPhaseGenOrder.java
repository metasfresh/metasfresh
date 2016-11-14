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


import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProject;
import org.compiere.model.MProjectPhase;
import org.compiere.model.MProjectTask;
import org.compiere.util.Env;


/**
 *  Generate Order from Project Phase
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectPhaseGenOrder.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ProjectPhaseGenOrder  extends SvrProcess
{
	private int		m_C_ProjectPhase_ID = 0;

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
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		m_C_ProjectPhase_ID = getRecord_ID();
		log.info("doIt - C_ProjectPhase_ID=" + m_C_ProjectPhase_ID);
		if (m_C_ProjectPhase_ID == 0)
			throw new IllegalArgumentException("C_ProjectPhase_ID == 0");
		MProjectPhase fromPhase = new MProjectPhase (getCtx(), m_C_ProjectPhase_ID, get_TrxName());
		MProject fromProject = ProjectGenOrder.getProject (getCtx(), fromPhase.getC_Project_ID(), get_TrxName());
		MOrder order = new MOrder (fromProject, true, MOrder.DocSubType_OnCredit);
		order.setDescription(order.getDescription() + " - " + fromPhase.getName());
		if (!order.save())
			throw new Exception("Could not create Order");

		//	Create an order on Phase Level
		if (fromPhase.getM_Product_ID() != 0)
		{
			MOrderLine ol = new MOrderLine(order);
			ol.setLine(fromPhase.getSeqNo());
			StringBuffer sb = new StringBuffer (fromPhase.getName());
			if (fromPhase.getDescription() != null && fromPhase.getDescription().length() > 0)
				sb.append(" - ").append(fromPhase.getDescription());
			ol.setDescription(sb.toString());
			//
			ol.setM_Product_ID(fromPhase.getM_Product_ID(), true);
			ol.setQty(fromPhase.getQty());
			ol.setPrice();
			if (fromPhase.getPriceActual() != null && fromPhase.getPriceActual().compareTo(Env.ZERO) != 0)
				ol.setPrice(fromPhase.getPriceActual());
			ol.setTax();
			if (!ol.save())
				log.error("doIt - Lines not generated");
			return "@C_Order_ID@ " + order.getDocumentNo() + " (1)";
		}

		//	Project Tasks
		int count = 0;
		MProjectTask[] tasks = fromPhase.getTasks ();
		for (int i = 0; i < tasks.length; i++)
		{
			MOrderLine ol = new MOrderLine(order);
			ol.setLine(tasks[i].getSeqNo());
			StringBuffer sb = new StringBuffer (tasks[i].getName());
			if (tasks[i].getDescription() != null && tasks[i].getDescription().length() > 0)
				sb.append(" - ").append(tasks[i].getDescription());
			ol.setDescription(sb.toString());
			//
			ol.setM_Product_ID(tasks[i].getM_Product_ID(), true);
			ol.setQty(tasks[i].getQty());
			ol.setPrice();
			ol.setTax();
			if (ol.save())
				count++;
		}	//	for all lines
		if (tasks.length != count)
			log.error("doIt - Lines difference - ProjectTasks=" + tasks.length + " <> Saved=" + count);

		return "@C_Order_ID@ " + order.getDocumentNo() + " (" + count + ")";
	}	//	doIt

}	//	ProjectPhaseGenOrder
