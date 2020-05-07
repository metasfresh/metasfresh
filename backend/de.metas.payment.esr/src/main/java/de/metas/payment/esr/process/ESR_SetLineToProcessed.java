package de.metas.payment.esr.process;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.process.JavaProcess;

/**
 * 
 * @author cg
 *
 */
public class ESR_SetLineToProcessed extends JavaProcess
{

	private int p_ESR_ImportLine_ID;

	@Override
	protected void prepare()
	{
		if (I_ESR_ImportLine.Table_Name.equals(getTableName()))
		{
			p_ESR_ImportLine_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_ESR_ImportLine_ID <= 0)
		{
			throw new FillMandatoryException(I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID);
		}

		final I_ESR_ImportLine esrImportLine = InterfaceWrapperHelper.create(getCtx(), p_ESR_ImportLine_ID, I_ESR_ImportLine.class, get_TrxName());

		Check.assume(get_TrxName().equals(InterfaceWrapperHelper.getTrxName(esrImportLine)), "TrxName {0} of {1} is equal to the process-TrxName {2}",
				InterfaceWrapperHelper.getTrxName(esrImportLine),
				esrImportLine,
				get_TrxName());


		final String description = getProcessInfo().getTitle() + " #" + getAD_PInstance_ID();
		esrImportLine.setProcessed(true);
		esrImportLine.setDescription(description);
		InterfaceWrapperHelper.save(esrImportLine);
		
		return "OK";
	}

}
