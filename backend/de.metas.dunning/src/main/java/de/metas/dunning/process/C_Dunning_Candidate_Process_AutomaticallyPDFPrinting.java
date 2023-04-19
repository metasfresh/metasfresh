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

import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.api.impl.AbstractDunningCandidateSource;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.Iterator;

/**
 * Process responsible for creating <code>C_DunningDocs</code> from dunning candidates and triggering PDF Printing
 *
 * @author cg
 */
public class C_Dunning_Candidate_Process_AutomaticallyPDFPrinting extends JavaProcess
{

	//
	// Services
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IDunningBL dunningBL = Services.get(IDunningBL.class);

	//
	// async batch
	private final String m_AsyncBatchName = "Generate dunning docs for PDF printing";
	private final String m_AsyncBatchDesc = "Generate dunning docs for PDF printing";
	private final String m_asyncBatchType = Async_Constants.C_Async_Batch_InternalName_DunningCandidate_Processing;

	@Param(parameterName = I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID, mandatory = true)
	private OrgId p_OrgId;

	@Param(parameterName = "IsComplete", mandatory = true)
	private boolean p_isAutoProcess = true;

	@Override
	protected String doIt()
	{
		final IDunningContext context = dunningBL.createDunningContext(getCtx(),
				null, // DunningDate, not needed
				null, // C_DunningLevel, not needed
				get_TrxName());

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
				.setOrgId(p_OrgId)
				.setName(m_AsyncBatchName)
				.setDescription(m_AsyncBatchDesc)
				.build();

		context.setProperty(IDunningProducer.CONTEXT_AsyncBatchDunningDoc, asyncBatch);

		final OrgDunningCandidatesSource source = new OrgDunningCandidatesSource(p_OrgId);
		source.setDunningContext(context);

		dunningBL.processCandidates(context, source);

		return MSG_OK;
	}

	/**
	 * This dunning candidate source returns only those candidates that have been filtered by Org.
	 */
	private static final class OrgDunningCandidatesSource extends AbstractDunningCandidateSource
	{
		private final OrgId orgId;

		public OrgDunningCandidatesSource(@NonNull final OrgId orgId)
		{
			this.orgId = orgId;
		}

		@Override
		public Iterator<I_C_Dunning_Candidate> iterator()
		{
			final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
			final String whereClause = I_C_Dunning_Candidate.COLUMNNAME_AD_Org_ID + "=" + orgId.getRepoId();
			final Iterator<I_C_Dunning_Candidate> it = dunningDAO.retrieveNotProcessedCandidatesIteratorRW(getDunningContext(), whereClause);
			return it;
		}

	}

}
