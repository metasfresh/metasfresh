package org.eevolution.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.eevolution.model.MHRProcess;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public class PayrollProcessing extends JavaProcess
{
	public static final String PARAM_HR_Process_ID = "HR_Process_ID";

	/** Payroll Process */
	private int 			m_HR_Process_ID;
	
	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				log.debug("Null Parameter: " + name);
			}
			else if (name.equals(PARAM_HR_Process_ID))
			{
				m_HR_Process_ID = para.getParameterAsInt();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}
	
	@Override
	protected String doIt() throws Exception
	{
		if (m_HR_Process_ID <= 0)
			throw new FillMandatoryException(PARAM_HR_Process_ID);
		
		MHRProcess process = new MHRProcess(getCtx(), m_HR_Process_ID, get_TrxName());
		long start = System.currentTimeMillis();
		
		boolean ok = process.processIt(MHRProcess.ACTION_Complete);
		process.saveEx();
		if (!ok)
		{
			throw new AdempiereException(process.getProcessMsg());
		}
		
		return "@Processed@ " + process.getName() +" - "+(System.currentTimeMillis()-start) + "ms";
	}
}
