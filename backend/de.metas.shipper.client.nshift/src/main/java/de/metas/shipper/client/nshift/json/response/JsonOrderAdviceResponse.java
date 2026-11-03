/*
 * #%L
 * de.metas.shipper.client.nshift
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

package de.metas.shipper.client.nshift.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Response of the nShift {@code OrderAdvice} endpoint. Unlike the {@code Shipments} endpoint
 * (which returns the shipment object directly), OrderAdvice wraps the advised shipment under
 * {@code Shipment} alongside a {@code Status} and {@code CorrelationID}. When the booking fails,
 * {@code Shipment} is absent and nShift reports the reason(s) under {@code ErrorMessages} (a return
 * shipment, if the product generates one, may still be present under a separate key).
 */
@Value
@Builder
@Jacksonized
public class JsonOrderAdviceResponse
{
	@JsonProperty("Shipment")
	JsonShipmentResponse shipment;

	/** nShift's own failure reason(s); present (instead of {@link #shipment}) when the booking failed. */
	@JsonProperty("ErrorMessages")
	List<String> errorMessages;

	@JsonProperty("Status")
	String status;

	@JsonProperty("CorrelationID")
	String correlationID;

	/**
	 * Human-readable failure reason for a no-Shipment response: nShift's {@code ErrorMessages} joined when present
	 * (absent key ⇒ null, so guard), otherwise the {@code Status}. Shared by the advise and booking error paths.
	 */
	public String failureReason()
	{
		return errorMessages != null && !errorMessages.isEmpty()
				? "nShift errors: " + String.join(" | ", errorMessages)
				: "Status=" + status;
	}
}
