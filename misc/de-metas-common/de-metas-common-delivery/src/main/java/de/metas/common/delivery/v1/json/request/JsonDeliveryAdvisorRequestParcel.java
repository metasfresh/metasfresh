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

package de.metas.common.delivery.v1.json.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/**
 * The carrier-advise parcel: the PARCEL-level fields plus the LIST of per-product items.
 * Mirrors the two-level structure of the ship path's {@link JsonDeliveryOrderParcel} and is the unit the
 * advise builders hand to {@link JsonDeliveryAdvisorRequest} (parcel fields are copied onto the request and the
 * items become the request's item list).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryAdvisorRequestParcel
{
	@NonNull BigDecimal grossWeightKg;
	@Nullable JsonPackageDimensions packageDimensions;
	@Nullable String topLevelType;
	@NonNull List<JsonDeliveryAdvisorRequestItem> items;
}
