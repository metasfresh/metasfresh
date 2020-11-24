/*
 * #%L
 * de-metas-common-shipmentschedule
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.shipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonCreateShipmentInfo.JsonCreateShipmentInfoBuilder.class)
public class JsonCreateShipmentInfo
{
	@JsonProperty("shipmentScheduleId")
    @NonNull
	JsonMetasfreshId shipmentScheduleId;

	@JsonProperty("productSearchKey")
	String productSearchKey;

	@JsonProperty("businessPartnerSearchKey")
	String businessPartnerSearchKey;

	@JsonProperty("movementQuantity")
	BigDecimal movementQuantity;

	@JsonProperty("movementDate")
	LocalDateTime movementDate;

	@JsonProperty("documentNo")
	String documentNo;

	@JsonProperty("deliveryRule")
	String deliveryRule;

	@JsonProperty("attributes")
	List<JsonAttributeInstance> attributes;

	@JsonProperty("location")
	JsonLocation shipToLocation;

	@JsonProperty("trackingNumbers")
	List<String> trackingNumbers;
}
