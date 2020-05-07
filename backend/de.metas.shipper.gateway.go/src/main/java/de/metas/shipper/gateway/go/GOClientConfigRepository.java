package de.metas.shipper.gateway.go;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import de.metas.shipper.gateway.go.model.I_GO_Shipper_Config;

/*
 * #%L
 * de.metas.shipper.gateway.go
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

@Repository
public class GOClientConfigRepository
{
	private final CCache<Integer, GOClientConfig> configByShipperId = CCache.newCache(I_GO_Shipper_Config.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	public GOClientConfig getByShipperId(final int shipperId)
	{
		return configByShipperId.getOrLoad(shipperId, () -> retrieveConfigForShipperId(shipperId));
	}

	private GOClientConfig retrieveConfigForShipperId(final int shipperId)
	{
		final I_GO_Shipper_Config configPO = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_GO_Shipper_Config.class)
				.addEqualsFilter(I_GO_Shipper_Config.COLUMN_M_Shipper_ID, shipperId)
				.create()
				.firstOnly(I_GO_Shipper_Config.class);
		if (configPO == null)
		{
			throw new AdempiereException("No GO shipper config found for shipperId=" + shipperId);
		}

		return GOClientConfig.builder()
				.url(StringUtils.trimWhitespace(configPO.getGO_URL()))
				.authUsername(StringUtils.trimWhitespace(configPO.getGO_AuthUsername()))
				.authPassword(StringUtils.trimWhitespace(configPO.getGO_AuthPassword()))
				.requestUsername(StringUtils.trimWhitespace(configPO.getGO_RequestUsername()))
				.requestSenderId(StringUtils.trimWhitespace(configPO.getGO_RequestSenderId()))
				.build();
	}
}
