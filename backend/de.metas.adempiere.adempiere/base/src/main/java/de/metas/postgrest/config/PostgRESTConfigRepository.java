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
import org.compiere.model.I_S_PostgREST_Config;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostgRESTConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<OrgId, Optional<I_S_PostgREST_Config>> cache = CCache.<OrgId, Optional<I_S_PostgREST_Config>> builder()
			.tableName(I_S_PostgREST_Config.Table_Name)
			.build();

	@NonNull
	public PostgRESTConfig getConfigFor(@NonNull final OrgId orgId)
	{
		final Optional<I_S_PostgREST_Config> config = getOptionalConfigFor(orgId);

		final boolean missingMandatoryConfigs = !config.isPresent();

		if (missingMandatoryConfigs)
		{
			throw new AdempiereException("Missing PostgREST configs for the given orgID!")
					.appendParametersToMessage()
					.setParameter("OrgId", orgId);
		}


		return PostgRESTConfig.builder()
				.readTimeout(config.get().getRead_timeout())
				.connectionTimeout(config.get().getConnection_timeout())
				.baseURL(config.get().getBase_url())
				.build();
	}

	@NonNull
	private Optional<I_S_PostgREST_Config> getOptionalConfigFor(@NonNull final OrgId orgId)
	{
		return cache.getOrLoad(orgId, this::retrieveConfigFor);
	}

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
}
