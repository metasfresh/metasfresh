/*
 * #%L
 * de.metas.shipper.gateway.nshift
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

package de.metas.shipper.gateway.nshift.client;

import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.util.StringUtils;
import de.metas.shipper.client.nshift.NShiftConstants;
import de.metas.shipper.client.nshift.NShiftShipmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_Carrier_Config;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentDispatchService
{
	private final NShiftShipmentService shipmentService;

	public JsonDeliveryResponse createShipment(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final String shipTypeCode = deliveryRequest.getShipperConfig().getAdditionalProperty(I_Carrier_Config.COLUMNNAME_ShipType);
		final ShipType shipType = shipTypeCode != null ? ShipType.ofCode(shipTypeCode) : ShipType.ORDER;
		switch (shipType)
		{
			case SHIP:
				return shipmentService.createShipment(deliveryRequest);
			case ORDER:
				// order doesn't support manual carrier selection
				if(StringUtils.toBoolean(deliveryRequest.getShipperConfig().getAdditionalProperty(NShiftConstants.MANUAL), false))
				{
					return shipmentService.createShipment(deliveryRequest);
				}
				return shipmentService.createShipmentViaOrderAdvice(deliveryRequest);
			default:
				throw new AdempiereException("Unhandled " + ShipType.class.getSimpleName() + ": " + shipType);
		}
	}
}
