package de.metas.datev;

import de.metas.cache.CCache;
import de.metas.datev.model.I_DATEV_Export_Config;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

/*
 * #%L
 * metasfresh-datev
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class DATEVExportConfigRepository
{
	private final CCache<OrgId, DATEVExportConfig> exportConfigsByOrgId = CCache.newCache(I_DATEV_Export_Config.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	public @Nullable DATEVExportConfig getByOrgId(final OrgId orgId)
	{
		return exportConfigsByOrgId.getOrLoad(orgId, () -> retrieveByOrgId(orgId));
	}

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private @Nullable DATEVExportConfig retrieveByOrgId(final OrgId orgId)
	{
		final I_DATEV_Export_Config config = queryBL
				.createQueryBuilder(I_DATEV_Export_Config.class)
				.addEqualsFilter(I_DATEV_Export_Config.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnly(I_DATEV_Export_Config.class);

		if (config == null)
		{
			return null;
		}

		return DATEVExportConfig.builder()
				.id(config.getDATEV_Export_Config_ID())
				.advisorNumber(config.getAdvisorNumber())
				.clientNumber(config.getClientNumber())
				.chartOfAccounts(config.getChartOfAccounts())
				.chartOfAccountsNumberLength(config.getChartOfAccountsNumberLength())
				.build();
	}
}
