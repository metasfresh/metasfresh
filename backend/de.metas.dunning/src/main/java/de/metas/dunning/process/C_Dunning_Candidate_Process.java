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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.dunning.Dunning_Constants;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.api.impl.AbstractDunningCandidateSource;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Process responsible for creating <code>C_DunningDocs</code> from dunning candidates
 *
 * @author tsa
 *
 */
public class C_Dunning_Candidate_Process extends JavaProcess
{

	//
	// Services
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);

	public static final String PARAM_IsAutoProcess = "IsComplete";
	private boolean p_isAutoProcess = true;

	//
	// async batch
	private final String m_AsyncBatchName = "Generate dunning docs";
	private final String m_AsyncBatchDesc = "Generate dunning docs ";
	private final String m_asyncBatchType = Dunning_Constants.C_Async_Batch_InternalName_DunningDoc;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				// skip if no parameter value
				continue;
			}

			final String name = para.getParameterName();
			if (name.equals(PARAM_IsAutoProcess))
			{
				p_isAutoProcess = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt()
	{
		final IDunningBL dunningBL = Services.get(IDunningBL.class);

		final IDunningContext context = dunningBL.createDunningContext(getCtx(),
																	   null, // DunningDate, not needed
																	   null, // C_DunningLevel, not needed
																	   get_TrxName(),
																	   null); // recomputeDunningCandidatesQuery

		context.setProperty(IDunningProducer.CONTEXT_ProcessDunningDoc, p_isAutoProcess);

		//
		// Create Async Batch for tracking

		// retrieve async batch type
		final I_C_Async_Batch_Type asyncBatchType;
		asyncBatchType = asyncBatchDAO.retrieveAsyncBatchType(getCtx(), m_asyncBatchType);
		Check.assumeNotNull(asyncBatchType, "Defined Async Batch type should not be null for internal name ", m_asyncBatchType);

		// Create Async Batch for tracking
		final I_C_Async_Batch asyncBatch = asyncBatchBL.newAsyncBatch()
				.setContext(getCtx())
				.setC_Async_Batch_Type(asyncBatchType.getInternalName())
				.setAD_PInstance_Creator_ID(getPinstanceId())
				.setName(m_AsyncBatchName)
				.setDescription(m_AsyncBatchDesc)
				.build();

		context.setProperty(IDunningProducer.CONTEXT_AsyncBatchDunningDoc, asyncBatch);

		final SelectedDunningCandidatesSource source = new SelectedDunningCandidatesSource(getProcessInfo().getWhereClause());
		source.setDunningContext(context);

		dunningBL.processCandidates(context, source);

		return MSG_OK;
	}

	/**
	 * This dunning candidate source returns only those candidates that have been selected by the user.
	 *
	 * @author ts
	 *
	 */
	private static final class SelectedDunningCandidatesSource extends AbstractDunningCandidateSource
	{
		private final String whereClause;

		public SelectedDunningCandidatesSource(final String whereClause)
		{
			this.whereClause = whereClause;
		}

		@Override
		public Iterator<I_C_Dunning_Candidate> iterator()
		{
			final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
			final Iterator<I_C_Dunning_Candidate> it = dunningDAO.retrieveNotProcessedCandidatesIteratorRW(getDunningContext(), whereClause);
			return it;
		}

	}

}
