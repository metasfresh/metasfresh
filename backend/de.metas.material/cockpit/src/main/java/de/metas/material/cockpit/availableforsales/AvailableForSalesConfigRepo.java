package de.metas.material.cockpit.availableforsales;

import de.metas.cache.CCache;
import de.metas.material.cockpit.model.I_MD_AvailableForSales_Config;
import de.metas.organization.OrgId;
import de.metas.util.ColorId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-cockpit
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

@Repository
public class AvailableForSalesConfigRepo
{
	private final CCache<ConfigQuery, AvailableForSalesConfig> cache = CCache
			.<ConfigQuery, AvailableForSalesConfig> builder()
			.tableName(I_MD_AvailableForSales_Config.Table_Name)
			.build();

	public AvailableForSalesConfig getConfig(@NonNull final ConfigQuery query)
	{
		cache.getOrLoad(query, () -> retrieveConfigRecord(query));

		return retrieveConfigRecord(query);
	}

	private AvailableForSalesConfig retrieveConfigRecord(@NonNull final ConfigQuery query)
	{
		final I_MD_AvailableForSales_Config configRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_AvailableForSales_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_AvailableForSales_Config.COLUMNNAME_AD_Client_ID, query.getClientId())
				.addInArrayFilter(I_MD_AvailableForSales_Config.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY)
				.orderByDescending(I_MD_AvailableForSales_Config.COLUMNNAME_AD_Org_ID)
				.create()
				.first();

		return ofRecord(configRecord);
	}

	private AvailableForSalesConfig ofRecord(@Nullable final I_MD_AvailableForSales_Config configRecord)
	{
		if (configRecord == null)
		{
			return AvailableForSalesConfig
					.builder()
					.featureEnabled(false)
					.insufficientQtyAvailableForSalesColorId(null)
					.salesOrderLookBehindHours(0)
					.shipmentDateLookAheadHours(0)
					.build();
		}

		return AvailableForSalesConfig
				.builder()
				.featureEnabled(configRecord.isFeatureActivated())
				.insufficientQtyAvailableForSalesColorId(ColorId.ofRepoId(configRecord.getInsufficientQtyAvailableForSalesColor_ID()))
				.salesOrderLookBehindHours(configRecord.getSalesOrderLookBehindHours())
				.shipmentDateLookAheadHours(configRecord.getShipmentDateLookAheadHours())
				.runAsync(configRecord.isAsync())
				.asyncTimeoutMillis(configRecord.getAsyncTimeoutMillis())
				.build();
	}

	@Value
	@Builder
	public static class ConfigQuery
	{
		ClientId clientId;

		OrgId orgId;
	}
}
