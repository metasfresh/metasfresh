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
package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
public class JsonShipmentOptions
{

	@JsonProperty("ServiceLevel")
	String serviceLevel;

	@JsonProperty("RequiredDeliveryDate")
	String requiredDeliveryDate; // Expects DATETIME format, e.g., "YYYY-MM-DDTHH:mm:ss"

	@JsonProperty("Visibility")
	String visibility;

	@JsonProperty("Submit")
	@JsonSerialize(converter = BooleanToIntConverter.class)
	Boolean submit;

	@JsonProperty("Labels")
	JsonLabelType labelType;

	@JsonProperty("TicketUserName")
	String ticketUserName;

	@JsonProperty("WorkstationID")
	UUID workstationID;

	@JsonProperty("DropZoneLabelPrinterKey")
	String dropZoneLabelPrinterKey;

	@JsonProperty("DropZoneDocPrinterKey")
	String dropZoneDocPrinterKey;

	@JsonProperty("ValidatePostCode")
	@JsonSerialize(converter = BooleanToIntConverter.class)
	Boolean validatePostCode;

	@JsonProperty("Place")
	String place;
}