/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.model;

import de.metas.cache.CCache;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

@Repository
public class DhlClientConfigRepository
{
	private final CCache<Integer, DhlClientConfig> cache = CCache.newCache(I_DHL_Shipper_Config.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	public DhlClientConfig getByShipperId(final int shipperId)
	{
		return cache.getOrLoad(shipperId, () -> readConfig(shipperId));
	}

	private static DhlClientConfig readConfig(final int shipperId)
	{
		final I_DHL_Shipper_Config configPO = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_DHL_Shipper_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_Shipper_Config.COLUMNNAME_DHL_Shipper_Config_ID, shipperId)
				.create()
				.first();
		if (configPO == null)
		{
			throw new AdempiereException("No DHL shipper configuration found for shipperId=" + shipperId);
		}
		return DhlClientConfig.builder()
				.applicationID(configPO.getapplicationID())
				.applicationToken(configPO.getApplicationToken())
				.baseUrl(configPO.getdhl_api_url())
				.accountNumber(configPO.getAccountNumber())
				.username(configPO.getUserName())
				.signature(configPO.getSignature())
				.build();
	}
}
