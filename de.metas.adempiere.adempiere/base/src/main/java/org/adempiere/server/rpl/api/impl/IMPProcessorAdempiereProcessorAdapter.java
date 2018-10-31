package org.adempiere.server.rpl.api.impl;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.model.I_IMP_Processor;

import de.metas.util.Services;

public class IMPProcessorAdempiereProcessorAdapter implements AdempiereProcessor
{
	private final I_IMP_Processor impProcessor;

	public IMPProcessorAdempiereProcessorAdapter(@NonNull final I_IMP_Processor impProcessor)
	{
		this.impProcessor = impProcessor;
	}

	public I_IMP_Processor getIMP_Procesor()
	{
		return impProcessor;
	}

	@Override
	public int getAD_Client_ID()
	{
		return impProcessor.getAD_Client_ID();
	}

	@Override
	public String getName()
	{
		return impProcessor.getName();
	}

	@Override
	public String getDescription()
	{
		return impProcessor.getDescription();
	}

	@Override
	public Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(impProcessor);
	}

	@Override
	public String getFrequencyType()
	{
		return impProcessor.getFrequencyType();
	}

	@Override
	public int getFrequency()
	{
		return impProcessor.getFrequency();
	}

	@Override
	public String getServerID()
	{
		return "ReplicationProcessor" + impProcessor.getIMP_Processor_ID();
	}

	@Override
	public Timestamp getDateNextRun(boolean requery)
	{
		if (requery)
		{
			InterfaceWrapperHelper.refresh(impProcessor);
		}
		return impProcessor.getDateNextRun();
	}

	@Override
	public void setDateNextRun(Timestamp dateNextWork)
	{
		impProcessor.setDateNextRun(dateNextWork);
	}

	@Override
	public Timestamp getDateLastRun()
	{
		return impProcessor.getDateLastRun();
	}

	@Override
	public void setDateLastRun(Timestamp dateLastRun)
	{
		impProcessor.setDateLastRun(dateLastRun);
	}

	@Override
	public boolean saveOutOfTrx()
	{
		InterfaceWrapperHelper.save(impProcessor, ITrx.TRXNAME_None);
		return true;
	}

	@Override
	public AdempiereProcessorLog[] getLogs()
	{
		final List<AdempiereProcessorLog> list = Services.get(IIMPProcessorDAO.class).retrieveAdempiereProcessorLogs(impProcessor);
		return list.toArray(new AdempiereProcessorLog[list.size()]);
	}

	@Override
	public String get_TableName()
	{
		return I_IMP_Processor.Table_Name;
	}

}
