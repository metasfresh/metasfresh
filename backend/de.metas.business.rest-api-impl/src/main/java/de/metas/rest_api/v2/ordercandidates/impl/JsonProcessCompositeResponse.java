/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.ordercandidates.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.ordercandidates.v2.response.JsonOLCandProcessResponse;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentResponse;
import de.metas.rest_api.v2.invoice.impl.JSONInvoiceInfoResponse;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@JsonDeserialize(builder = JsonProcessCompositeResponse.JsonProcessCompositeResponseBuilder.class)
public class JsonProcessCompositeResponse
{
	JsonOLCandProcessResponse olCandProcessResponse;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonCreateShipmentResponse shipmentResponse;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	List<JSONInvoiceInfoResponse> invoiceInfoResponse;

	@Builder
	JsonProcessCompositeResponse(
			@JsonProperty("olCandProcessResponse") final JsonOLCandProcessResponse olCandProcessResponse,
			@JsonProperty("shipmentResponse") final JsonCreateShipmentResponse shipmentResponse,
			@JsonProperty("invoiceInfoResponse") final List<JSONInvoiceInfoResponse> invoiceInfoResponse)
	{
		this.olCandProcessResponse = olCandProcessResponse;
		this.shipmentResponse = shipmentResponse;
		this.invoiceInfoResponse = invoiceInfoResponse;
	}
}
