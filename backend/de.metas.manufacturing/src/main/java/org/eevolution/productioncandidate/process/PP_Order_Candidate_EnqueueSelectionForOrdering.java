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
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer;

import java.math.BigDecimal;

public class PP_Order_Candidate_EnqueueSelectionForOrdering extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final PPOrderCandidateEnqueuer ppOrderCandidateEnqueuer = SpringContextHolder.instance.getBean(PPOrderCandidateEnqueuer.class);

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

		ppOrderCandidateEnqueuer
				.enqueueSelection(pinstanceId, getCtx());

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

	private int createSelection()
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

	private IQueryBuilder<I_PP_Order_Candidate> createOCQueryBuilder()
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
				.addOnlyContextClient();
	}
}