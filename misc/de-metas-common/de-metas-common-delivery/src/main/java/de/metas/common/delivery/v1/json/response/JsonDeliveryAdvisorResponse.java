/*
 * #%L
 * de-metas-common-delivery
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

package de.metas.common.delivery.v1.json.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryAdvisorResponse
{
	@NonNull String requestId;
	@Nullable String errorMessage;
	@Nullable JsonShipperProduct shipperProduct;
	@NonNull @Singular Set<JsonCarrierService> shipperProductServices;
	@NonNull JsonGoodsType goodsType;

	@JsonIgnore
	public boolean isError()
	{
		return (getErrorMessage() != null && !getErrorMessage().isEmpty());
	}
}
