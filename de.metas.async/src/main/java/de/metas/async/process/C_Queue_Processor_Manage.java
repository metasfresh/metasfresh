package de.metas.async.process;

/*
 * #%L
 * de.metas.async
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


import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorExecutorService;
import de.metas.async.processor.IQueueProcessorsExecutor;

/**
 * Start/Stop/Restart an {@link IQueueProcessor}
 * 
 * @author tsa
 * 
 */
public class C_Queue_Processor_Manage extends SvrProcess
{
	private static final String PARAM_Action = "Action";

	private static final String ACTION_RESTART = "RESTART";
	private static final String ACTION_STOP = "STOP";
	private static final String ACTION_START = "START";

	private int p_C_Queue_Processor_ID = -1;
	private String action;

	@Override
	protected void prepare()
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		
		if (getTable_ID() == adTableDAO.retrieveTableId(I_C_Queue_Processor.Table_Name))
		{
			p_C_Queue_Processor_ID = getRecord_ID();
		}

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				continue;
			}

			if (PARAM_Action.equals(name))
			{
				action = para.getParameter().toString();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_C_Queue_Processor_ID <= 0)
		{
			throw new FillMandatoryException(I_C_Queue_Processor.COLUMNNAME_C_Queue_Processor_ID);
		}
		if (action == null)
		{
			throw new FillMandatoryException(PARAM_Action);
		}

		final I_C_Queue_Processor processorDef = InterfaceWrapperHelper.create(getCtx(), p_C_Queue_Processor_ID, I_C_Queue_Processor.class, ITrx.TRXNAME_None);
		final IQueueProcessorsExecutor executor = Services.get(IQueueProcessorExecutorService.class).getExecutor();
		if (ACTION_START.equals(action))
		{
			executor.addQueueProcessor(processorDef);
		}
		else if (ACTION_STOP.equals(action))
		{
			executor.removeQueueProcessor(p_C_Queue_Processor_ID);
		}
		else if (ACTION_RESTART.equals(action))
		{
			executor.removeQueueProcessor(p_C_Queue_Processor_ID);
			executor.addQueueProcessor(processorDef);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Action@: " + action);
		}

		return "OK";
	}

}
