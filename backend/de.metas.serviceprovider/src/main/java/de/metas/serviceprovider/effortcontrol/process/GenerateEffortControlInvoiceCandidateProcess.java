/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.effortcontrol.process;

import de.metas.process.JavaProcess;
import de.metas.serviceprovider.effortcontrol.EffortControlService;
import de.metas.serviceprovider.effortcontrol.repository.EffortControl;
import de.metas.serviceprovider.effortcontrol.repository.EffortControlRepository;
import de.metas.serviceprovider.model.I_S_EffortControl;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;

import java.util.stream.Stream;

public class GenerateEffortControlInvoiceCandidateProcess extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EffortControlService effortControlService = SpringContextHolder.instance.getBean(EffortControlService.class);

	@Override
	protected String doIt() throws Exception
	{
		retrieveSelectedEffort().forEach(effortControlService::generateICFromEffortControl);

		return MSG_OK;
	}

	@NonNull
	private Stream<EffortControl> retrieveSelectedEffort()
	{
		final IQuery<I_S_Issue> notProcessedIssues = queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_S_Issue.COLUMNNAME_EffortAggregationKey)
				.addEqualsFilter(I_S_Issue.COLUMNNAME_Processed, false)
				.create();

		final IQueryFilter<I_S_EffortControl> notProcessedEffortControlFilter = queryBL.createCompositeQueryFilter(I_S_EffortControl.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_S_EffortControl.COLUMNNAME_EffortAggregationKey, I_S_Issue.COLUMNNAME_EffortAggregationKey, notProcessedIssues);

		final IQueryFilter<I_S_EffortControl> selectedFilter = getProcessInfo()
				.getQueryFilterOrElseTrue();

		return queryBL.createQueryBuilder(I_S_EffortControl.class)
				.addFilter(selectedFilter)
				.addFilter(notProcessedEffortControlFilter)
				.create()
				.iterateAndStream()
				.map(EffortControlRepository::fromRecord);
	}
}
