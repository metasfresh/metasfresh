/**
 * 
 */
package de.metas.letters.report;

import org.compiere.model.I_AD_Process;
import org.compiere.model.Query;
import org.compiere.print.MPrintFormat;

import de.metas.letters.model.I_T_BoilerPlate_Spool;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.X_T_BoilerPlate_Spool;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * @author teo_sarca
 *
 */
public class AD_BoilerPlate_Report extends JavaProcess
{
	private String p_MsgText = null;
	private int p_AD_PrintFormat_ID = -1;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
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
			final MPrintFormat pf = MPrintFormat.get(getCtx(), p_AD_PrintFormat_ID, false);
			getResult().setPrintFormat(pf);
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
		final String whereClause = I_AD_Process.COLUMNNAME_AD_Process_ID + "=?"
				+ " AND " + I_AD_Process.COLUMNNAME_JasperReport + " IS NOT NULL";
		return new Query(getCtx(), I_AD_Process.Table_Name, whereClause, get_TrxName())
				.setParameters(getProcessInfo().getAD_Process_ID())
				.match();
	}

	private void startJasper() throws Exception
	{
		ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Client_ID(getAD_Client_ID())
				.setAD_User_ID(getAD_User_ID())
				.setAD_PInstance_ID(getAD_PInstance_ID())
				.setAD_Process_ID(0)
				.setTableName(I_T_BoilerPlate_Spool.Table_Name)
				//
				.buildAndPrepareExecution()
				.onErrorThrowException()
				.executeSync();
	}
}
