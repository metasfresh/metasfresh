package de.metas.ui.web.procurement.process;

import java.math.BigDecimal;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.async.PMM_GenerateOrders;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.procurement.base
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
 * Mass enqueue {@link I_PMM_PurchaseCandidate} records to be processed and purchase orders to be generated.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Profile(Profiles.PROFILE_Webui)
public class PMM_Purchase_Candidate_CreatePurchaseOrder
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition

{

	private int recordsEnqueued;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		// only showing the action if there are rows in the view
		if (getView().size() <= 0)
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final SqlOptions sqlOptions = SqlOptions.usingTableName(I_PMM_PurchaseCandidate.Table_Name);
		final SqlViewRowsWhereClause sqlWhereClause = getView().getSqlWhereClause(DocumentIdsSelection.ALL, sqlOptions);

		final ICompositeQueryFilter<I_PMM_PurchaseCandidate> procurementPurchaseCandidateQueryFilter = queryBL.createCompositeQueryFilter(I_PMM_PurchaseCandidate.class)
				.addCompareFilter(I_PMM_PurchaseCandidate.COLUMNNAME_QtyToOrder, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
				.addFilter(sqlWhereClause.toQueryFilter());

		recordsEnqueued = PMM_GenerateOrders.prepareEnqueuing()
				.filter(procurementPurchaseCandidateQueryFilter)
				.enqueue();
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
		if (recordsEnqueued > 0)
		{
			invalidateView();
		}
	}

}
