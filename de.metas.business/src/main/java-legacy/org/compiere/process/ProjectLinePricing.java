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
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

import org.compiere.model.MProductPricing;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.util.Msg;

/**
 *  Price Project Line.
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectLinePricing.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ProjectLinePricing extends SvrProcess
{
	/**	Project Line from Record			*/
	private int 		m_C_ProjectLine_ID = 0;

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
		m_C_ProjectLine_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (m_C_ProjectLine_ID == 0)
			throw new IllegalArgumentException("No Project Line");
		MProjectLine projectLine = new MProjectLine (getCtx(), m_C_ProjectLine_ID, get_TrxName());
		log.info("doIt - " + projectLine);
		if (projectLine.getM_Product_ID() == 0)
			throw new IllegalArgumentException("No Product");
		//
		MProject project = new MProject (getCtx(), projectLine.getC_Project_ID(), get_TrxName());
		if (project.getM_PriceList_ID() == 0)
			throw new IllegalArgumentException("No PriceList");
		//
		boolean isSOTrx = true;
		MProductPricing pp = new MProductPricing (projectLine.getM_Product_ID(), project.getC_BPartner_ID(),
			projectLine.getPlannedQty(), isSOTrx);
		pp.setM_PriceList_ID(project.getM_PriceList_ID());
		pp.setPriceDate(project.getDateContract());
		//
		projectLine.setPlannedPrice(pp.getPriceStd());
		projectLine.setPlannedMarginAmt(pp.getPriceStd().subtract(pp.getPriceLimit()));
		projectLine.save();
		//
		String retValue = Msg.getElement(getCtx(), "PriceList") + pp.getPriceList() + " - "
			+ Msg.getElement(getCtx(), "PriceStd") + pp.getPriceStd() + " - "
			+ Msg.getElement(getCtx(), "PriceLimit") + pp.getPriceLimit();
		return retValue;
	}	//	doIt

}	//	ProjectLinePricing
