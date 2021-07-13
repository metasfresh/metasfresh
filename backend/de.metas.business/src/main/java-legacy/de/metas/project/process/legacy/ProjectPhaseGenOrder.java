/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package de.metas.project.process.legacy;


import org.compiere.model.I_C_ProjectTask;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProject;
import org.compiere.model.MProjectPhase;
import org.compiere.util.Env;

import java.util.List;

/**
 *  Generate Order from Project Phase
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectPhaseGenOrder.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
@Deprecated
public class ProjectPhaseGenOrder  extends JavaProcess
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
		List<I_C_ProjectTask> tasks = fromPhase.getTasks ();
		for (int i = 0; i < tasks.size(); i++)
		{
			MOrderLine ol = new MOrderLine(order);
			ol.setLine(tasks.get(i).getSeqNo());
			StringBuffer sb = new StringBuffer (tasks.get(i).getName());
			if (tasks.get(i).getDescription() != null && tasks.get(i).getDescription().length() > 0)
				sb.append(" - ").append(tasks.get(i).getDescription());
			ol.setDescription(sb.toString());
			//
			ol.setM_Product_ID(tasks.get(i).getM_Product_ID(), true);
			ol.setQty(tasks.get(i).getQty());
			ol.setPrice();
			ol.setTax();
			if (ol.save())
				count++;
		}	//	for all lines
		if (tasks.size() != count)
			log.error("doIt - Lines difference - ProjectTasks=" + tasks.size() + " <> Saved=" + count);

		return "@C_Order_ID@ " + order.getDocumentNo() + " (" + count + ")";
	}	//	doIt

}	//	ProjectPhaseGenOrder
