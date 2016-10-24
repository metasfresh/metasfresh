/**
 * 
 */
package de.metas.letters.report;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Process;
import org.compiere.model.Query;
import org.compiere.print.MPrintFormat;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.ReportStarter;
import org.compiere.util.Ini;
import org.compiere.util.Trx;

import de.metas.letters.model.I_T_BoilerPlate_Spool;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.X_T_BoilerPlate_Spool;

/**
 * @author teo_sarca
 *
 */
public class AD_BoilerPlate_Report extends SvrProcess
{
	private String p_MsgText = null;
	private int p_AD_PrintFormat_ID = -1;
	
	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParameter())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
				;			
			else if (X_T_BoilerPlate_Spool.COLUMNNAME_MsgText.equals(name))
			{
				p_MsgText = para.getParameter().toString();
			}
			else if ("AD_PrintFormat_ID".equals(name))
			{
				p_AD_PrintFormat_ID = para.getParameterAsInt();
			}
		}
		//
		if (p_MsgText == null && MADBoilerPlate.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			final MADBoilerPlate bp = new MADBoilerPlate(getCtx(), getRecord_ID(), get_TrxName());
			p_MsgText = bp.getTextSnippetParsed(null);
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		createRecord(p_MsgText);
		//
		// Set Custom PrintFormat if needed
		if (p_AD_PrintFormat_ID > 0)
		{
			MPrintFormat pf = MPrintFormat.get(getCtx(), p_AD_PrintFormat_ID, false);
			if (Ini.isClient())
				getProcessInfo().setTransientObject (pf);
			else
				getProcessInfo().setSerializableObject(pf);
		}
		//
		// Jasper Report
		else if (isJasperReport())
		{
			startJasper();
		}
		return "OK";
	}
	
	private void createRecord(String text)
	{
		MADBoilerPlate.createSpoolRecord(getCtx(), getAD_Client_ID(), getAD_PInstance_ID(), text, get_TrxName());
	}
	
	private boolean isJasperReport()
	{
		final String whereClause = I_AD_Process.COLUMNNAME_AD_Process_ID+"=?"
		+" AND "+I_AD_Process.COLUMNNAME_JasperReport+" IS NOT NULL";
		return new Query(getCtx(), I_AD_Process.Table_Name, whereClause, get_TrxName())
		.setParameters(getProcessInfo().getAD_Process_ID())
		.match();
	}
	
	private void startJasper()
	{
		final ProcessInfo pi = ProcessInfo.builder()
				.setAD_Process_ID(0)
				.setTableName(I_T_BoilerPlate_Spool.Table_Name)
				.build();
		pi.setAD_Client_ID(getAD_Client_ID());
		pi.setAD_User_ID(getAD_User_ID());
		pi.setAD_PInstance_ID(getAD_PInstance_ID());
		//
		ReportStarter proc = new ReportStarter();
		proc.startProcess(getCtx(), pi, Trx.get(get_TrxName(), false));
		if (pi.isError())
		{
			throw new AdempiereException(pi.getSummary());
		}
	}
}
