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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
public class ShipmentScheduleIdentifier
{
	@Schema(description = "This translates to 'M_Shipment_Schedule_ID' used to identify the shipment schedule that needs to be processed")
	@JsonProperty("shipmentScheduleId")
	JsonMetasfreshId shipmentScheduleId;

	@Schema(description = "This translates to 'C_OLCand.externalHeaderId' used to identify the shipment schedules that needs to be processed")
	@JsonProperty("externalHeaderId")
	String externalHeaderId;

	@Schema(description = "This translates to 'C_OLCand.externalLineId' used to identify the shipment schedules that needs to be processed")
	@JsonProperty("externalLineId")
	String externalLineId;

	@Builder
	@JsonCreator
	ShipmentScheduleIdentifier(
			@JsonProperty("shipmentScheduleId") final JsonMetasfreshId shipmentScheduleId,
			@JsonProperty("externalHeaderId") final String externalHeaderId,
			@JsonProperty("externalLineId") final String externalLineId)
	{
		if (shipmentScheduleId == null)
		{
			Check.assumeNotEmpty(externalHeaderId, "When shipmentScheduleId is not provided, externalHeaderId must be provided");
			Check.assumeNotEmpty(externalLineId, "When shipmentScheduleId is not provided, externalLineId must be provided");
		}
		else
		{
			Check.assumeNull(externalHeaderId, "When shipmentScheduleId is provided, externalHeaderId must be empty");
			Check.assumeNull(externalLineId, "When shipmentScheduleId is provided, externalLineId must be empty");
		}

		this.shipmentScheduleId = shipmentScheduleId;
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;
	}

	public boolean identifiedByHeaderAndLineId()
	{
		return externalHeaderId != null && externalLineId != null;
	}
}