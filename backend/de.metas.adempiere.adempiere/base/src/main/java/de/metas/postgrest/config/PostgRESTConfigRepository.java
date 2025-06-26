/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.postgrest.config;

import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_PostgREST_Config;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
public class PostgRESTConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<OrgId, Optional<I_S_PostgREST_Config>> cache = CCache.<OrgId, Optional<I_S_PostgREST_Config>>builder()
			.tableName(I_S_PostgREST_Config.Table_Name)
			.build();

	@NonNull
	public PostgRESTConfig getConfigFor(@NonNull final OrgId orgId)
	{
		final Optional<PostgRESTConfig> config = getOptionalConfigFor(orgId);
		return config.orElseThrow(() ->
				new AdempiereException("Missing PostgREST configs for the given orgID!")
						.appendParametersToMessage()
						.setParameter("OrgId", orgId));
	}

	public Optional<PostgRESTConfig> getOptionalConfigFor(@NonNull final OrgId orgId)
	{
		return getOptionalConfigRecordFor(orgId)
				.map(record -> PostgRESTConfig.builder()
						.id(PostgRESTConfigId.ofRepoId(record.getS_PostgREST_Config_ID()))
						.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
						.readTimeout(Duration.ofMillis(record.getRead_timeout()))
						.connectionTimeout(Duration.ofMillis(record.getConnection_timeout()))
						.baseURL(record.getBase_url())
						.resultDirectory(record.getPostgREST_ResultDirectory())
						.build());
	}

	@NonNull
	private Optional<I_S_PostgREST_Config> getOptionalConfigRecordFor(@NonNull final OrgId orgId)
	{
		return cache.getOrLoad(orgId, this::retrieveConfigFor);
	}

	@NonNull
	private Optional<I_S_PostgREST_Config> retrieveConfigFor(@NonNull final OrgId orgId)
	{
		return queryBL
				.createQueryBuilder(I_S_PostgREST_Config.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_S_PostgREST_Config.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.orderBy(I_S_PostgREST_Config.COLUMNNAME_AD_Org_ID)
				.create()
				.firstOptional(I_S_PostgREST_Config.class);
	}

	public void save(@NonNull final PostgRESTConfig postgRESTConfig)
	{
		final I_S_PostgREST_Config configRecord = InterfaceWrapperHelper.loadOrNew(postgRESTConfig.getId(), I_S_PostgREST_Config.class);
		
		configRecord.setAD_Org_ID(postgRESTConfig.getOrgId().getRepoId());
		configRecord.setBase_url(postgRESTConfig.getBaseURL());
		
		if (postgRESTConfig.getConnectionTimeout() == null)
		{
			configRecord.setConnection_timeout(0);
		}
		else
		{
			final long millis = postgRESTConfig.getConnectionTimeout().toMillis();
			configRecord.setConnection_timeout((int)millis);
		}
		if (postgRESTConfig.getReadTimeout() == null)
		{
			configRecord.setRead_timeout(0);
		}
		else
		{
			final long millis = postgRESTConfig.getReadTimeout().toMillis();
			configRecord.setRead_timeout((int)millis);
		}
		configRecord.setPostgREST_ResultDirectory(postgRESTConfig.getResultDirectory());
		
		InterfaceWrapperHelper.saveRecord(configRecord);
	}
}
