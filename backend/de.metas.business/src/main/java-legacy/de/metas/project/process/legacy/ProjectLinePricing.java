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

import de.metas.i18n.Msg;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import org.compiere.model.MProductPricing;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;

/**
 *  Price Project Line.
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectLinePricing.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
@Deprecated
public class ProjectLinePricing extends JavaProcess
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
		{
			throw new IllegalArgumentException("No PriceList");
		}
		//
		boolean isSOTrx = true;
		MProductPricing pp = new MProductPricing (
				OrgId.ofRepoId(project.getAD_Org_ID()),
				projectLine.getM_Product_ID(),
				project.getC_BPartner_ID(),
				null, /* countryId */
				projectLine.getPlannedQty(),
				isSOTrx);
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
