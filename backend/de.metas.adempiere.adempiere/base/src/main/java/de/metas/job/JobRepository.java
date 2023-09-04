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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Job;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JobRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, JobMap> cache = CCache.<Integer, JobMap>builder()
			.tableName(I_C_Job.Table_Name)
			.build();

	@NonNull
	public Job getById(@NonNull final JobId jobId) {return getMap().getById(jobId);}

	public Optional<Job> getCTO(@NonNull final ClientId clientId) {return getMap().getCTO(clientId);}

	private JobMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private JobMap retrieveMap()
	{
		return queryBL.createQueryBuilder(I_C_Job.class)
				.stream()
				.map(JobRepository::fromRecord)
				.collect(GuavaCollectors.collectUsingListAccumulator(JobMap::new));
	}

	@NonNull
	private static Job fromRecord(@NonNull final I_C_Job record)
	{
		return Job.builder()
				.id(JobId.ofRepoId(record.getC_Job_ID()))
				.name(StringUtils.trimBlankToOptional(record.getName()).orElseThrow())
				.isActive(record.isActive())
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.build();
	}

	private static class JobMap
	{
		private final ImmutableMap<JobId, Job> byId;

		JobMap(@NonNull final List<Job> list)
		{
			this.byId = Maps.uniqueIndex(list, Job::getId);
		}

		public Job getById(@NonNull final JobId id)
		{
			final Job job = byId.get(id);
			if (job == null)
			{
				throw new AdempiereException("No Job found for " + id);
			}
			return job;
		}

		public Optional<Job> getCTO(@NonNull final ClientId clientId)
		{
			return byId.values()
					.stream()
					.filter(job -> job.isCTO() && ClientId.equals(job.getClientId(), clientId))
					.findFirst();
		}
	}
}
