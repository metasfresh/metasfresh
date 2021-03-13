/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.pricing.pricelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@ApiModel
public class JsonPriceListVersionRequest
{
	@ApiModelProperty(position = 10, dataType = "java.lang.String")
	@JsonProperty("priceListVersionIdentifier")
	String priceListVersionIdentifier;

	@ApiModelProperty(position = 20, value = "The price list version json request object")
	@JsonProperty("jsonUpsertPriceListVersionRequest")
	JsonUpsertPriceListVersionRequest jsonUpsertPriceListVersionRequest;

	@JsonCreator
	@Builder
	public JsonPriceListVersionRequest(
			@NonNull @JsonProperty("priceListVersionIdentifier") final String priceListVersionIdentifier,
			@NonNull @JsonProperty("jsonUpsertPriceListVersionRequest") final JsonUpsertPriceListVersionRequest jsonUpsertPriceListVersionRequest)
	{
		this.priceListVersionIdentifier = priceListVersionIdentifier;
		this.jsonUpsertPriceListVersionRequest = jsonUpsertPriceListVersionRequest;
	}
}
