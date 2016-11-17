package de.metas.printing.process;

/*
 * #%L
 * de.metas.printing.base
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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public class C_Print_Job_Instructions_Create extends JavaProcess
{
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
	final IPrintJobBL printJobBL = Services.get(IPrintJobBL.class);

	public static final String PARAM_SeqNo = "SeqNo";
	public static final String PARAM_Copies = "Copies";

	private int p_C_Print_Job_ID = -1;
	private int p_SeqNo_From = -1;
	private int p_SeqNo_To = -1;
	private int p_copies = 1;

	private I_C_Print_Job_Instructions jobInstructions;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			else if (PARAM_SeqNo.equals(para.getParameterName()))
			{
				p_SeqNo_From = para.getParameterAsInt();
				p_SeqNo_To = para.getParameter_ToAsInt();
			}
			else if (PARAM_Copies.equals(para.getParameterName()))
			{
				p_copies = para.getParameterAsInt();
			}
		}

		final String tableName = getTableName();
		if (I_C_Print_Job.Table_Name.equals(tableName))
		{
			p_C_Print_Job_ID = getRecord_ID();
		}
		else if (I_C_Print_Job_Instructions.Table_Name.equals(tableName))
		{
			final int printJobInstructionsId = getRecord_ID();
			final I_C_Print_Job_Instructions instructions = InterfaceWrapperHelper.create(getCtx(), printJobInstructionsId, I_C_Print_Job_Instructions.class, ITrx.TRXNAME_None);
			p_C_Print_Job_ID = instructions.getC_Print_Job_ID();
		}
	}

	@Override
	protected String doIt()
	{
		if (p_C_Print_Job_ID <= 0)
		{
			throw new FillMandatoryException(I_C_Print_Job.COLUMNNAME_C_Print_Job_ID);
		}
		if (p_SeqNo_From <= 0)
		{
			p_SeqNo_From = IPrintingDAO.SEQNO_First;
		}
		if (p_SeqNo_To <= 0)
		{
			p_SeqNo_To = IPrintingDAO.SEQNO_Last;
		}

		final int adUserToPrintId = getAD_User_ID();

		// create those instructions just for us and don't let some other printing client running with the same user-id snatch it from us.
		final boolean createWithSpecificHostKey = true;

		final I_C_Print_Job printJob = InterfaceWrapperHelper.create(getCtx(), p_C_Print_Job_ID, I_C_Print_Job.class, get_TrxName());

		final I_C_Print_Job_Line firstLine = printingDAO.retrievePrintJobLine(printJob, p_SeqNo_From);
		final I_C_Print_Job_Line lastLine = printingDAO.retrievePrintJobLine(printJob, p_SeqNo_To);

		this.jobInstructions = printJobBL
				.createPrintJobInstructions(printJob,
						adUserToPrintId,
						createWithSpecificHostKey,
						firstLine,
						lastLine,
						p_copies);

		return "@Created@";
	}

	public I_C_Print_Job_Instructions getC_Print_Job_Instructions()
	{
		return jobInstructions;
	}
}
