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

/*
 * #%L
 * de-metas-common-manufacturing
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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrderBOMLine;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.common.shipping.v2.JsonProduct;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Value
@JsonDeserialize(builder = JsonPPOrder.JsonPPOrderBuilder.class)
public class JsonPPOrder
{
	@NonNull
	String orderId;

	@NonNull
	String orgCode;

	@NonNull
	String documentNo;

	@Nullable
	String description;

	@NonNull
	ZonedDateTime dateOrdered;

	@NonNull
	ZonedDateTime dateStartSchedule;

	@NonNull
	JsonProduct finishGoodProduct;

	@NonNull
	JsonQuantity qtyToProduce;

	@NonNull
	JsonProductInfo productInfo;

	@Nullable
	JsonBPartner bPartner;

	@NonNull
	@Singular
	List<JsonResponseManufacturingOrderBOMLine> components;
}
