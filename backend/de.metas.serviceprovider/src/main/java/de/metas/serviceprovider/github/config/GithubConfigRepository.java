/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.github.config;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.model.I_S_GithubConfig;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GithubConfigRepository
{
	private final IQueryBL queryBL;

	public GithubConfigRepository(final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}

	@NonNull
	public String getConfigByNameAndOrg(@NonNull final GithubConfigName name, @NonNull final OrgId orgId)
	{
		return getOptionalConfigByNameAndOrg(name, orgId)
				.orElseThrow(() -> new AdempiereException("Missing S_GithubConfig for name & org!")
						.appendParametersToMessage()
						.setParameter("name", name)
						.setParameter("org", orgId));
	}

	@NonNull
	public Optional<String> getOptionalConfigByNameAndOrg(@NonNull final GithubConfigName name, @NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_S_GithubConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_GithubConfig.COLUMNNAME_Name, name.getCode())
				.addInArrayFilter(I_S_GithubConfig.COLUMNNAME_AD_Org_ID, orgId.getRepoId(), OrgId.ANY)
				.orderByDescending(I_S_GithubConfig.COLUMNNAME_AD_Org_ID)
				.create()
				.firstOptional(I_S_GithubConfig.class)
				.map(I_S_GithubConfig::getConfig);
	}

	@NonNull
	public List<I_S_GithubConfig> listByName(@NonNull final GithubConfigName name)
	{
		return queryBL.createQueryBuilder(I_S_GithubConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_GithubConfig.COLUMNNAME_Name, name.getCode())
				.list();
	}
}
