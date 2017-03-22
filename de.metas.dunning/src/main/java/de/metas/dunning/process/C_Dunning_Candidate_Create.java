package de.metas.dunning.process;

/*
 * #%L
 * de.metas.dunning
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

import java.sql.Timestamp;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningCandidateProducer;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.spi.IDunnableSource;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Process responsible for generating dunning candidates for all configured {@link IDunnableSource}s
 * 
 * @author tsa
 * 
 */
public class C_Dunning_Candidate_Create extends JavaProcess
{
	private static final String PARAM_DunningDate = "DunningDate";
	private Timestamp p_DunningDate = null;

	private static final String PARAM_IsFullUpdate = "IsFullUpdate";
	private boolean p_IsFullUpdate = false;
	
	final private ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	protected void prepare()
	{
		// Defaults
		p_DunningDate = Env.getContextAsDate(getCtx(), "#Date");
		p_IsFullUpdate = false;

		for (ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				// skip if no parameter value
				continue;
			}

			final String name = para.getParameterName();
			if (PARAM_DunningDate.equals(name))
			{
				p_DunningDate = para.getParameterAsTimestamp();
			}
			else if (PARAM_IsFullUpdate.equals(name))
			{
				p_IsFullUpdate = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt()
	{
		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);

		//
		// Generate dunning candidates
		for (final I_C_Dunning dunning : dunningDAO.retrieveDunnings(getCtx()))
		{
			for (final I_C_DunningLevel dunningLevel : dunningDAO.retrieveDunningLevels(dunning))
			{
				generateCandidates(dunningLevel);
			}
		}

		return "OK";
	}

	private void generateCandidates(final I_C_DunningLevel dunningLevel)
	{
		final IDunningBL dunningBL = Services.get(IDunningBL.class);

		trxManager.run(new TrxRunnableAdapter()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				final IDunningContext context = dunningBL.createDunningContext(getCtx(), dunningLevel, p_DunningDate, get_TrxName());
				context.setProperty(IDunningCandidateProducer.CONTEXT_FullUpdate, p_IsFullUpdate);

				final int countDelete = Services.get(IDunningDAO.class).deleteNotProcessedCandidates(context, dunningLevel);
				addLog("@C_DunningLevel@ " + dunningLevel.getName() + ": " + countDelete + " record(s) deleted");

				final int countCreateUpdate = dunningBL.createDunningCandidates(context);
				addLog("@C_DunningLevel@ " + dunningLevel.getName() + ": " + countCreateUpdate + " record(s) created/updated");

			}
		});

	}
}
