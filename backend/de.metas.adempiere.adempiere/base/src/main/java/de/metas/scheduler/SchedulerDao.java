/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.scheduler;

import de.metas.common.util.Check;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Scheduler;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SchedulerDao
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<I_AD_Scheduler> getSchedulerByProcessIdIfUnique(@NonNull final AdProcessId processId)
	{
		final List<I_AD_Scheduler> records = queryBL.createQueryBuilder(I_AD_Scheduler.class)
				.addEqualsFilter(I_AD_Scheduler.COLUMNNAME_AD_Process_ID, processId.getRepoId())
				.create()
				.list(I_AD_Scheduler.class);

		if (!Check.isSingleElement(records))
		{
			return Optional.empty();
		}

		return Optional.of(records.get(0));
	}

	@NonNull
	public I_AD_Scheduler getById(@NonNull final AdSchedulerId adSchedulerId)
	{
		return InterfaceWrapperHelper.load(adSchedulerId, I_AD_Scheduler.class);
	}

	public void save(@NonNull final I_AD_Scheduler scheduler)
	{
		InterfaceWrapperHelper.save(scheduler);
	}
}
