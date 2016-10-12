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
package org.compiere.report;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_PA_Report;
import org.compiere.model.MPInstance;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;

import de.metas.process.ProcessCtl;

/**
 * Financial Report Engine
 *
 * @author Jorg Janke
 * @version $Id: FinReport.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class FinReportJasper extends FinReport
{
	@Override
	protected void postProcess(final boolean success)
	{
		super.postProcess(success);
		
		if (!success)
		{
			return;
		}

		// Now invoke the associated jasper report (must report on the T_Report table)
		final List<ProcessInfoParameter> param = new ArrayList<>();

		// Copy the list of parameters from the financial report
		for (final ProcessInfoParameter oldpara : getParameter())
			param.add(oldpara);
		// and add the T_Report_AD_PInstance_ID parameter
		param.add(new ProcessInfoParameter("T_Report_AD_PInstance_ID", getAD_PInstance_ID(), null, null, null));

		// Load Report Definition
		final I_PA_Report paReport = getPA_Report();

		final I_AD_Process proc = paReport.getJasperProcess();
		MPInstance instance = new MPInstance(proc, getTable_ID(), getRecord_ID());
		instance.setWhereClause(getProcessInfo().getWhereClause());
		instance.save();

		final ProcessInfo pi = new ProcessInfo(proc.getName(), proc.getAD_Process_ID());
		pi.setParameter(param.toArray(new ProcessInfoParameter[param.size()]));
		pi.setTable_ID(getTable_ID());
		pi.setRecord_ID(getRecord_ID());
		pi.setAD_Process_ID(proc.getAD_Process_ID());
		pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());

		Services.get(IADPInstanceDAO.class).saveParameterToDB(pi); // 07154

		ProcessCtl.builder()
				.setProcessInfo(pi)
				.execute();
	}
}	// FinReport
