/*
 * #%L
 * de-metas-commons-shipmentschedule
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

package de.metas.commons.shipmentschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Value
public class JsonResponseShipmentSchedule
{
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String orderDocumentNo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	String poReference;

	LocalDateTime dateOrdered;

	@NonNull
	String productNo; // we also call it "productNo" in the product-rest-api

	@JsonCreator
	@Builder
	private JsonResponseShipmentSchedule(
			@JsonProperty("orderDocumentNo") @Nullable final String orderDocumentNo,
			@JsonProperty("poReference") @Nullable final String poReference,
			@JsonProperty("dateOrdered") @NonNull final LocalDateTime dateOrdered,
			@JsonProperty("productNo") @NonNull final String productNo)
	{
		this.orderDocumentNo = orderDocumentNo;
		this.poReference = poReference;
		this.dateOrdered = dateOrdered;
		this.productNo = productNo;
	}
}

