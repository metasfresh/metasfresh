/*
 * #%L
 * de-metas-common-manufacturing
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

package de.metas.common.manufacturing.v2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.common.shipping.v2.JsonProduct;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = JsonResponseManufacturingOrder.JsonResponseManufacturingOrderBuilder.class)
public class JsonResponseManufacturingOrder
{
	@NonNull
	JsonMetasfreshId orderId;

	@NonNull
	String orgCode;

	@NonNull
	String documentNo;

	@Nullable
	String description;

	@NonNull
	ZonedDateTime dateOrdered;

	@NonNull
	ZonedDateTime datePromised;

	@NonNull
	ZonedDateTime dateStartSchedule;

	@NonNull
	JsonProduct finishGoodProduct;

	@NonNull
	JsonQuantity qtyToProduce;

	@NonNull
	JsonMetasfreshId productId;

	@Nullable
	JsonMetasfreshId bpartnerId;

	@NonNull
	@Singular
	List<JsonResponseManufacturingOrderBOMLine> components;

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonResponseManufacturingOrderBuilder
	{
	}
}
