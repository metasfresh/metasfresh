/*
 * #%L
 * de-metas-common-shipping
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

package de.metas.common.shipping.v1.customerreturns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonAttributeInstance;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonCreateCustomerReturnInfo.JsonCreateCustomerReturnInfoBuilder.class)
public class JsonCreateCustomerReturnInfo
{
	@JsonProperty
	@NonNull
	String orgCode;

	@JsonProperty("productSearchKey")
	@NonNull
	String productSearchKey;

	@JsonProperty("movementQuantity")
	@NonNull
	BigDecimal movementQuantity;

	@JsonProperty("shipmentScheduleId")
	JsonMetasfreshId shipmentScheduleId;

	@JsonProperty("shipmentDocumentNumber")
	String shipmentDocumentNumber;

	@JsonProperty("externalId")
	String externalId;

	@JsonProperty("dateReceived")
	LocalDateTime dateReceived;

	@JsonProperty("movementDate")
	LocalDate movementDate;

	@JsonProperty("attributes")
	List<JsonAttributeInstance> attributes;

	@JsonProperty("externalResourceURL")
	String externalResourceURL;

	@JsonProperty("hupiItemProductId")
	JsonMetasfreshId hupiItemProductId;

	@JsonProperty("movementTUQty")
	BigDecimal movementTUQty;

	@JsonProperty("returnedGoodsWarehouseType")
	String returnedGoodsWarehouseType;
}
