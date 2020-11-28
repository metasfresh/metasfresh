package de.metas.ui.web.invoicecandidate.process;

import de.metas.Profiles;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.C_Invoice_Candidate_ProcessCaptionMapperHelper;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.springframework.context.annotation.Profile;

import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Process used to approve (for invoicing) the selected billing candidates.
 * <p>
 * This process is the webui alternative for swing's side action: de.metas.invoicecandidate.callout.IC_ApproveForInvoicing_Action
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/2361
 */
@Profile(Profiles.PROFILE_Webui)
public class C_Invoice_Candidate_ApproveForInvoicing extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private int countUpdated = 0;

	private final C_Invoice_Candidate_ProcessCaptionMapperHelper processCaptionMapperHelper = SpringContextHolder.instance.getBean(C_Invoice_Candidate_ProcessCaptionMapperHelper.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept()
				.withCaptionMapper(getCaptionMapper());
	}

	private ProcessPreconditionsResolution.ProcessCaptionMapper getCaptionMapper()
	{
		return processCaptionMapperHelper.getProcessCaptionMapperForNetAmountsFromQuery(retrieveInvoiceCandidatesToApproveQuery(false));
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final IQuery<I_C_Invoice_Candidate> query = retrieveInvoiceCandidatesToApproveQuery(true);

		//
		// Fail if there is nothing to update
		final int countToUpdate = query.count();
		if (countToUpdate <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}

		//
		// Update selected invoice candidates
		countUpdated = query.update(ic -> {
			ic.setApprovalForInvoicing(true);
			return IQueryUpdater.MODEL_UPDATED;
		});

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		//
		// Notify frontend that the view shall be refreshed because we changed some candidates
		if (countUpdated > 0)
		{
			invalidateView();
		}
	}

	/**
	 * Implementation detail: during `checkPreconditionsApplicable` `getProcessInfo` throws exception because it is not configured for the Process, so we ignore it.
	 *
	 */
	private final IQuery<I_C_Invoice_Candidate> retrieveInvoiceCandidatesToApproveQuery(final boolean includeProcessInfoFilters)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class);
		if (includeProcessInfoFilters)
		{
			queryBuilder.filter(getProcessInfo().getQueryFilterOrElseFalse());
		}

		queryBuilder.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, true) // not processed
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_ApprovalForInvoicing, true) // not already approved
		;

		// Only selected rows
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (!selectedRowIds.isAll())
		{
			final Set<Integer> invoiceCandidateIds = selectedRowIds.toIntSet();
			if (invoiceCandidateIds.isEmpty())
			{
				// shall not happen
				throw new AdempiereException("@NoSelection@");
			}

			queryBuilder.addInArrayFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateIds);
		}

		return queryBuilder.create();
	}
}
