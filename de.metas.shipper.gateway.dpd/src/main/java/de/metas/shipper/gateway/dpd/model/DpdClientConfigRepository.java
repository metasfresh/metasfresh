/*
 * #%L
 * de.metas.shipper.gateway.dpd
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

package de.metas.shipper.gateway.dpd.model;

import de.metas.cache.CCache;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.springframework.stereotype.Repository;

@Repository
public class DpdClientConfigRepository
{
	private final CCache<Integer, DpdClientConfig> cache = CCache.newCache(I_DPD_Shipper_Config.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	@NonNull
	public DpdClientConfig getByShipperId(@NonNull final ShipperId shipperId)
	{
		final int repoId = shipperId.getRepoId();
		return cache.getOrLoad(repoId, () -> retrieveConfig(repoId));
	}

	@NonNull
	private static DpdClientConfig retrieveConfig(final int shipperId)
	{
		final I_DPD_Shipper_Config configPO = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DPD_Shipper_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DPD_Shipper_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.create()
				.first();
		if (configPO == null)
		{
			throw new AdempiereException("No DPD shipper configuration found for shipperId=" + shipperId);
		}

		return DpdClientConfig.builder()
				.loginApiUrl(configPO.getLoginApiUrl())
				.shipmentServiceApiUrl(configPO.getShipmentServiceApiUrl())
				.delisID(configPO.getDelisID())
				.delisPassword(configPO.getDelisPassword())
				.trackingUrlBase(retrieveTrackingUrl(configPO.getM_Shipper_ID()))
				.paperFormat(DpdPaperFormat.ofCode(configPO.getPaperFormat()))
				.shipperProduct(DpdShipperProduct.ofCode(configPO.getShipperProduct()))
				.build();
	}

	private static String retrieveTrackingUrl(final int shipperId)
	{
		final I_M_Shipper shipperPo = InterfaceWrapperHelper.load(shipperId, I_M_Shipper.class);
		return shipperPo.getTrackingURL();
	}
}
