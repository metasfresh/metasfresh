/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.productioncandidate.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.async.EnqueuePPOrderCandidateRequest;
import org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer;

import java.math.BigDecimal;

public class PP_Order_Candidate_EnqueueSelectionForOrdering extends JavaProcess implements IProcessPrecondition
{
	protected final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final PPOrderCandidateEnqueuer ppOrderCandidateEnqueuer = SpringContextHolder.instance.getBean(PPOrderCandidateEnqueuer.class);

	private static final String PARAM_COMPLETE_DOCUMENT = "IsDocComplete";
	@Param(parameterName = PARAM_COMPLETE_DOCUMENT)
	private Boolean isDocComplete;

	private static final String PARAM_AUTO_PROCESS_CANDIDATES_AFTER_PRODUCTION = "AutoProcessCandidatesAfterProduction";
	@Param(parameterName = PARAM_AUTO_PROCESS_CANDIDATES_AFTER_PRODUCTION)
	private boolean autoProcessCandidatesAfterProduction;

	private static final String PARAM_AUTO_CLOSE_CANDIDATES_AFTER_PRODUCTION = "AutoCloseCandidatesAfterProduction";
	@Param(parameterName = PARAM_AUTO_CLOSE_CANDIDATES_AFTER_PRODUCTION)
	private boolean autoCloseCandidatesAfterProduction;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PInstanceId pinstanceId = getPinstanceId();

		final EnqueuePPOrderCandidateRequest enqueuePPOrderCandidateRequest = EnqueuePPOrderCandidateRequest.builder()
				.adPInstanceId(pinstanceId)
				.ctx(Env.getCtx())
				.isCompleteDocOverride(isDocComplete)
				.autoProcessCandidatesAfterProduction(autoProcessCandidatesAfterProduction)
				.autoCloseCandidatesAfterProduction(autoCloseCandidatesAfterProduction)
				.build();

		ppOrderCandidateEnqueuer.enqueueSelection(enqueuePPOrderCandidateRequest);

		return MSG_OK;
	}

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		if (createSelection() <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}
	}

	protected int createSelection()
	{
		final IQueryBuilder<I_PP_Order_Candidate> queryBuilder = createOCQueryBuilder();

		final PInstanceId adPInstanceId = getPinstanceId();

		Check.assumeNotNull(adPInstanceId, "adPInstanceId is not null");

		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		return queryBuilder
				.create()
				.setRequiredAccess(Access.READ)
				.createSelection(adPInstanceId);
	}

	protected IQueryBuilder<I_PP_Order_Candidate> createOCQueryBuilder()
	{
		final IQueryFilter<I_PP_Order_Candidate> userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);

		if (userSelectionFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		return queryBL
				.createQueryBuilder(I_PP_Order_Candidate.class, getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_IsClosed, false)
				.addCompareFilter(I_PP_Order_Candidate.COLUMNNAME_QtyToProcess, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_PP_Order_Candidate.COLUMNNAME_SeqNo)
				.orderBy(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID);
	}
}