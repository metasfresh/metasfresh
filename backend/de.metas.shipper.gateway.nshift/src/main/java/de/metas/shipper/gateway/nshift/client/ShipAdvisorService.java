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

import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.shipper.client.nshift.NShiftShipAdvisorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipAdvisorService
{
	private final NShiftShipAdvisorService shipAdvisorService;

	public JsonDeliveryAdvisorResponse advise(@NonNull final JsonDeliveryAdvisorRequest deliveryRequest)
	{
		return shipAdvisorService.advise(deliveryRequest);
	}
}
