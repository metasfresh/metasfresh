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
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperId;
import de.metas.shipping.ShipperRepository;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Shipper;
import org.springframework.stereotype.Repository;

/**
 * Repository Tables: DHL_Shipper_Config
 * Repository Cluster: DhlClientConfigRepository
 */
@Repository
@RequiredArgsConstructor
public class DhlClientConfigRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ShipperRepository shipperRepository;

	private final CCache<ShipperId, DhlClientConfig> cache = CCache.<ShipperId, DhlClientConfig>builder()
			.tableName(I_DHL_Shipper_Config.Table_Name)
			.additionalTableNameToResetFor(I_M_Shipper.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	@NonNull
	public DhlClientConfig getByShipperId(@NonNull final ShipperId shipperId)
	{
		return cache.getOrLoadNonNull(shipperId, this::retrieveConfig);
	}

	@NonNull
	private DhlClientConfig retrieveConfig(@NonNull final ShipperId shipperId)
	{
		final I_DHL_Shipper_Config configPO = queryBL
				.createQueryBuilder(I_DHL_Shipper_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_Shipper_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.orderBy(I_DHL_Shipper_Config.COLUMNNAME_DHL_Shipper_Config_ID)
				.create()
				.first();
		if (configPO == null)
		{
			throw new AdempiereException("No DHL shipper configuration found for shipperId=" + shipperId);
		}
		final Shipper shipper = shipperRepository.findById(shipperId) // only active shippers
				.orElseThrow(() -> new AdempiereException("The shipper of DHL_Shipper_Config_ID=" + configPO.getDHL_Shipper_Config_ID() + " is inactive; shipperId=" + shipperId.getRepoId()));
		return DhlClientConfig.builder()
				.shipperConfigId(configPO.getDHL_Shipper_Config_ID())
				.shipperName(shipper.getName())
				.applicationID(configPO.getapplicationID())
				.applicationToken(configPO.getApplicationToken())
				.baseUrl(configPO.getdhl_api_url())
				.accountNumber(configPO.getAccountNumber())
				.accountNumberInternational(configPO.getAccountNumber_International())
				.username(configPO.getUserName())
				.signature(configPO.getSignature())
				.lengthUomId(UomId.ofRepoId(configPO.getDhl_LenghtUOM_ID()))
				.trackingUrlBase(shipper.getTrackingUrl())
				.build();
	}
}
