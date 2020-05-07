package de.metas.process.processtools;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;

import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class AD_Process_Para_CopyFrom extends JavaProcess
{
	private final transient IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private static final String PARAM_From_Process_ID = "From_Process_ID";
	@Param(parameterName = PARAM_From_Process_ID, mandatory = true)
	private int p_From_Process_ID;

	@Override
	protected String doIt() throws Exception
	{
		if (p_From_Process_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_From_Process_ID);
		}
		final I_AD_Process adProcessFrom = InterfaceWrapperHelper.create(getCtx(), p_From_Process_ID, I_AD_Process.class, ITrx.TRXNAME_None);
		if (adProcessFrom == null || adProcessFrom.getAD_Process_ID() <= 0)
		{
			throw new FillMandatoryException(PARAM_From_Process_ID);
		}

		final I_AD_Process adProcessTo = getRecord(I_AD_Process.class);

		if (adProcessFrom.getAD_Process_ID() == adProcessTo.getAD_Process_ID())
		{
			throw new AdempiereException("Cannot copy from same process");
		}

		for (final I_AD_Process_Para processParamFrom : adProcessDAO.retrieveProcessParameters(adProcessFrom))
		{
			copyParameter(adProcessTo, processParamFrom);
		}

		return MSG_OK;
	}

	private void copyParameter(final I_AD_Process adProcessTo, final I_AD_Process_Para processParamFrom)
	{
		I_AD_Process_Para processParamTo = adProcessDAO.retriveProcessParameter(getCtx(), adProcessTo.getAD_Process_ID(), processParamFrom.getColumnName());
		if (processParamTo == null)
		{
			processParamTo = InterfaceWrapperHelper.create(getCtx(), I_AD_Process_Para.class, ITrx.TRXNAME_ThreadInherited);
		}
		final boolean wasNew = InterfaceWrapperHelper.isNew(processParamTo);

		InterfaceWrapperHelper.copy()
				.setFrom(processParamFrom)
				.setTo(processParamTo)
				.copy();

		processParamTo.setAD_Process(adProcessTo);
		processParamTo.setAD_Org_ID(adProcessTo.getAD_Org_ID());

		if (processParamTo.getSeqNo() <= 0)
		{
			final int lastSeqNo = adProcessDAO.retrieveProcessParaLastSeqNo(adProcessTo);
			processParamTo.setSeqNo(lastSeqNo + 10);
		}

		InterfaceWrapperHelper.save(processParamTo, ITrx.TRXNAME_ThreadInherited);

		if (wasNew)
		{
			addLog("@Created@ {}", processParamTo.getColumnName());
		}
		else
		{
			addLog("@Updated@ {}", processParamTo.getColumnName());
		}
	}
}
