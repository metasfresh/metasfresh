package de.metas.ui.web.document.process;

import java.util.List;

import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_Fact_Acct;

import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_Fact_Acct_Repost extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Param(parameterName = "IsEnforcePosting", mandatory = true)
	private boolean enforce;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{

		final IQueryFilter<I_Fact_Acct> userSelectionFilter = getProcessInfo().getQueryFilter();

		final IQueryBuilder<I_Fact_Acct> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class, getCtx(), ITrx.TRXNAME_None)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		final IPostingService postingService = Services.get(IPostingService.class);

		final List<I_Fact_Acct> factAccts = queryBuilder.create().list(I_Fact_Acct.class);

		for (final I_Fact_Acct factAcct : factAccts)
		{

			postingService
					.newPostingRequest()
					.setContext(getCtx(), ITrx.TRXNAME_None)
					.setAD_Client_ID(factAcct.getAD_Client_ID())
					.setDocument(factAcct.getAD_Table_ID(), factAcct.getRecord_ID())
					.setForce(enforce)
					.setPostImmediate(PostImmediate.Yes)
					.setFailOnError(true) // yes, because we will display a pop-up to user in this case (see below)
					.postIt();

		}

		return MSG_OK;
	}

}
