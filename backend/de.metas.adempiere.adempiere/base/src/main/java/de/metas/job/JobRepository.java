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

package de.metas.job;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Job;
import org.springframework.stereotype.Repository;

@Repository
public class JobRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Job getById(@NonNull final JobId jobId)
	{
		final I_C_Job record = queryBL
				.createQueryBuilder(I_C_Job.class)
				.addEqualsFilter(I_C_Job.COLUMNNAME_C_Job_ID, jobId)
				.create()
				.firstOnlyNotNull(I_C_Job.class);

		return ofRecord(record);
	}

	@NonNull
	private static Job ofRecord(@NonNull final I_C_Job record)
	{
		return Job.builder()
				.id(JobId.ofRepoId(record.getC_Job_ID()))
				.name(record.getName())
				.isActive(record.isActive())
				.build();
	}
}
