package org.adempiere.process.rpl.imp;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.interfaces.I_IMP_Processor;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.Query;
import org.compiere.server.ReplicationProcessor;
import org.compiere.util.Env;

/**
 * Start all active {@link I_IMP_Processor}s.
 * 
 * @author tsa
 * 
 */
public class IMP_Processors_StandaloneLauncher
{
	public IMP_Processors_StandaloneLauncher()
	{
		super();
	}

	public void start()
	{
		System.out.println("Retrieving processor definitions...");
		final List<I_IMP_Processor> processorDefs = retrieveProcessors();

		if (processorDefs.isEmpty())
		{
			throw new RuntimeException("No import processors were found");
		}

		for (final I_IMP_Processor processorDef : processorDefs)
		{
			start(processorDef);
		}

		while (true)
		{
			try
			{
				Thread.sleep(Long.MAX_VALUE);
			}
			catch (InterruptedException e)
			{
				System.out.println("Interrupted!");
			}
		}
	}

	private void start(final I_IMP_Processor processorDef)
	{
		final AdempiereProcessor adempiereProcessorDef = Services.get(IIMPProcessorBL.class).asAdempiereProcessor(processorDef);
		final ReplicationProcessor processorThread = new ReplicationProcessor(
				adempiereProcessorDef,
				0 // initialNap=0sec
		);

		processorThread.setDaemon(true);
		processorThread.start();

		System.out.println("Started " + processorThread);
	}

	private List<I_IMP_Processor> retrieveProcessors()
	{
		final Properties ctx = Env.getCtx();

		final List<I_IMP_Processor> processorDefs = new Query(ctx, I_IMP_Processor.Table_Name, null, ITrx.TRXNAME_None)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_IMP_Processor.COLUMNNAME_AD_Client_ID
						+ ", " + I_IMP_Processor.COLUMNNAME_IMP_Processor_ID)
				.list(I_IMP_Processor.class);

		return processorDefs;
	}

	public static final void main(String[] args) throws InterruptedException
	{
		Env.getSingleAdempiereInstance(null).startup(RunMode.BACKEND);

		final IMP_Processors_StandaloneLauncher launcher = new IMP_Processors_StandaloneLauncher();
		launcher.start();
	}

}
