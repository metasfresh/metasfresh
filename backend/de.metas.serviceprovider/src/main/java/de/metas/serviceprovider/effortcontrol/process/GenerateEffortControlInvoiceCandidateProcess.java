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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;

import java.util.Iterator;

public class GenerateEffortControlInvoiceCandidateProcess extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EffortControlService effortControlService = SpringContextHolder.instance.getBean(EffortControlService.class);

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_S_EffortControl> effortControlIterator = retrieveSelectedIterator();

		while (effortControlIterator.hasNext())
		{
			final I_S_EffortControl record = effortControlIterator.next();

			if (record.isIssueClosed())
			{
				continue;
			}

			final EffortControl effortControl = EffortControlRepository.fromRecord(record);

			effortControlService.generateICFromEffortControl(effortControl);
		}

		return MSG_OK;
	}

	@NonNull
	private Iterator<I_S_EffortControl> retrieveSelectedIterator()
	{
		final IQueryFilter<I_S_EffortControl> allActiveRecordsFilter = queryBL.createCompositeQueryFilter(I_S_EffortControl.class)
				.addOnlyActiveRecordsFilter();

		final IQueryFilter<I_S_EffortControl> selectedFilter = getProcessInfo()
				.getQueryFilterOrElse(allActiveRecordsFilter);

		return queryBL.createQueryBuilder(I_S_EffortControl.class)
				.addFilter(selectedFilter)
				.create()
				.iterate(I_S_EffortControl.class);
	}
}
