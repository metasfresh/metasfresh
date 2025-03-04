/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.v2.productprice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonSOTrx;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonPriceListResponse.JsonPriceListResponseBuilder.class)
public class JsonPriceListResponse
{
	@NonNull
	@JsonProperty("metasfreshId")
	JsonMetasfreshId metasfreshId;

	@NonNull
	@JsonProperty("name")
	String name;

	@NonNull
	@JsonProperty("pricePrecision")
	Integer pricePrecision;

	@NonNull
	@JsonProperty("isSOTrx")
	JsonSOTrx isSOTrx;

	@NonNull
	@JsonProperty("currencyCode")
	String currencyCode;

	@Nullable
	@JsonProperty("countryCode")
	String countryCode;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("priceListVersions")
	@Singular
	List<JsonPriceListVersionResponse> priceListVersions;
}