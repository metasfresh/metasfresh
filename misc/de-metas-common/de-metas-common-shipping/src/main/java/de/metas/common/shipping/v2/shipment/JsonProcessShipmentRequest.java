/*
 * #%L
 * de-metas-common-shipping
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.shipping.v2.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class JsonProcessShipmentRequest
{
	@JsonProperty("createShipmentRequest")
	@NonNull
	JsonCreateShipmentRequest createShipmentRequest;

	@JsonProperty("invoice")
	@NonNull
	Boolean invoice;

	@JsonProperty("closeShipmentSchedule")
	@NonNull
	Boolean closeShipmentSchedule;

	@Builder
	JsonProcessShipmentRequest(
			@JsonProperty("createShipmentRequest") @NonNull final JsonCreateShipmentRequest createShipmentRequest,
			@JsonProperty("invoice") @NonNull final Boolean invoice,
			@JsonProperty("closeShipmentSchedule") @NonNull final Boolean closeShipmentSchedule)
	{
		this.createShipmentRequest = createShipmentRequest;
		this.invoice = invoice;
		this.closeShipmentSchedule = closeShipmentSchedule;
	}
}
