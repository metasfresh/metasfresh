/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrderBOMLine;
import de.metas.common.rest_api.v2.JsonQuantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

@Builder
@Value
@JsonDeserialize(builder = JsonPPOrder.JsonPPOrderBuilder.class)
public class JsonPPOrder
{
	@NonNull
	@JsonProperty("orderId")
	String orderId;

	@NonNull
	@JsonProperty("orgCode")
	String orgCode;

	@NonNull
	@JsonProperty("documentNo")
	String documentNo;

	@Nullable
	@JsonProperty("description")
	String description;

	@NonNull
	@JsonProperty("dateOrdered")
	Instant dateOrdered;

	@NonNull
	@JsonProperty("dateStartSchedule")
	Instant dateStartSchedule;

	@NonNull
	@JsonProperty("qtyToProduce")
	JsonQuantity qtyToProduce;

	@NonNull
	@JsonProperty("finishGoodProduct")
	JsonProductInfo finishGoodProduct;

	@Nullable
	@JsonProperty("bpartner")
	JsonBPartner bPartner;

	@NonNull
	@JsonProperty("components")
	@Singular
	List<JsonResponseManufacturingOrderBOMLine> components;
}
