/*
 * #%L
 * marketing-activecampaign
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

package de.metas.marketing.gateway.activecampaign;

import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.gateway.activecampaign.model.I_MKTG_ActiveCampaign_Config;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveCampaignConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ActiveCampaignConfig getByPlatformId(@NonNull final PlatformId platformId)
	{
		return queryBL.createQueryBuilder(I_MKTG_ActiveCampaign_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_ActiveCampaign_Config.COLUMNNAME_MKTG_Platform_ID, platformId.getRepoId())
				.create()
				.firstOnlyOptional(I_MKTG_ActiveCampaign_Config.class)
				.map(ActiveCampaignConfigRepository::fromRecord)
				.orElseThrow(() -> new AdempiereException("Unable to load MKTG_ActiveCampaign_Config")
						.appendParametersToMessage()
						.setParameter("MKTG_Platform_ID", platformId.getRepoId()));
	}

	@NonNull
	public static ActiveCampaignConfig fromRecord(@NonNull final I_MKTG_ActiveCampaign_Config record)
	{
		return ActiveCampaignConfig.builder()
				.platformId(PlatformId.ofRepoId(record.getMKTG_Platform_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.baseUrl(record.getBaseURL())
				.apiKey(record.getApiKey())
				.build();
	}
}
