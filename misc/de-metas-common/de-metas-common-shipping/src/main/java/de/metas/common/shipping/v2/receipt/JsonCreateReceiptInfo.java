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

package de.metas.common.shipping.v2.receipt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonAttributeInstance;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCreateReceiptInfo
{
	@JsonProperty("externalHeaderId")
	String externalHeaderId;

	@JsonProperty("externalLineId")
	String externalLineId;

	@JsonProperty("receiptScheduleId")
	JsonMetasfreshId receiptScheduleId;

	@JsonProperty("orderLineId")
	JsonMetasfreshId orderLineId;

	@JsonProperty("externalId")
	String externalId;

	@JsonProperty("productSearchKey")
	String productSearchKey;

	@JsonProperty("movementQuantity")
	BigDecimal movementQuantity;

	@JsonProperty("dateReceived")
	LocalDateTime dateReceived;

	@JsonProperty("movementDate")
	LocalDate movementDate;

	@JsonProperty("attributes")
	List<JsonAttributeInstance> attributes;

	@JsonProperty("externalResourceURL")
	String externalResourceURL;

	@Builder
	@Jacksonized
	public JsonCreateReceiptInfo(
			@Nullable final String externalHeaderId,
			@Nullable final String externalLineId,
			@Nullable final JsonMetasfreshId receiptScheduleId,
			@Nullable final JsonMetasfreshId orderLineId,
			@Nullable final String externalId,
			@Nullable final String productSearchKey,
			@Nullable final BigDecimal movementQuantity,
			@Nullable final LocalDateTime dateReceived,
			@Nullable final LocalDate movementDate,
			@Nullable final List<JsonAttributeInstance> attributes,
			@Nullable final String externalResourceURL)
	{
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;
		this.receiptScheduleId = receiptScheduleId;
		this.orderLineId = orderLineId;
		this.externalId = externalId;
		this.productSearchKey = productSearchKey;
		this.movementQuantity = movementQuantity;
		this.dateReceived = dateReceived;
		this.movementDate = movementDate;
		this.attributes = attributes;
		this.externalResourceURL = externalResourceURL;

		if (countIdentificationMethods() != 1)
		{
			throw new IllegalArgumentException(
					"Exactly one identification method must be provided: " +
							"receiptScheduleId, (externalHeaderId AND externalLineId), or orderLineId");
		}
	}

	@JsonIgnore
	private int countIdentificationMethods()
	{
		int count = 0;
		if (receiptScheduleId != null)
		{
			count++;
		}
		if (Check.isNotBlank(externalHeaderId)
				&& Check.isNotBlank(externalLineId))
		{
			count++;
		}
		if (orderLineId != null)
		{
			count++;
		}
		return count;
	}
}
