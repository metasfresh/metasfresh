/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.model;

import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_Carrier_Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class ShipperConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	public ShipperConfig getByShipperIdOrNull(@NonNull final ShipperId shipperId)
	{
		return queryBL.createQueryBuilder(I_Carrier_Config.class)
				.addEqualsFilter(I_Carrier_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.create()
				.firstOnlyOptional()
				.map(this::fromPO)
				.orElse(null);
	}

	private ShipperConfig fromPO(@NotNull final I_Carrier_Config carrierConfig)
	{
		return ShipperConfig.builder()
				.id(ShipperConfigId.ofRepoId(carrierConfig.getCarrier_Config_ID()))
				.shipperId(de.metas.shipping.ShipperId.ofRepoId(carrierConfig.getM_Shipper_ID()))
				.url(carrierConfig.getBase_url())
				.username(carrierConfig.getUserName())
				.password(carrierConfig.getPassword())
				.clientId(carrierConfig.getClient_Id())
				.clientSecret(carrierConfig.getClient_Secret())
				.gatewayId(carrierConfig.getGatewayId())
				.trackingUrl(carrierConfig.getTrackingURL())
				.build();
	}

}
