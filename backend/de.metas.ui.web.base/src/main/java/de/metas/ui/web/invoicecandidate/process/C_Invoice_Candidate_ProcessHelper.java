/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.invoicecandidate.process;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.C_Invoice_Candidate_ProcessCaptionMapperHelper;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;

public abstract class C_Invoice_Candidate_ProcessHelper extends ViewBasedProcessTemplate implements IProcessPrecondition
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
		return processCaptionMapperHelper.getProcessCaptionMapperForNetAmountsFromQuery(retrieveQuery(false));
	}
	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final IQuery<I_C_Invoice_Candidate> query = retrieveQuery(true);

		//
		// Fail if there is nothing to update
		final int countToUpdate = query.count();
		if (countToUpdate <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}

		final boolean flagToSet = isApproveForInvoicing();
		//
		// Update selected invoice candidates
		countUpdated = query.update(ic -> {
			ic.setApprovalForInvoicing(flagToSet);
			return IQueryUpdater.MODEL_UPDATED;
		});

		return MSG_OK;
	}

	protected abstract boolean isApproveForInvoicing();

	protected abstract IQuery<I_C_Invoice_Candidate> retrieveQuery(final boolean includeProcessInfoFilters);


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
}
