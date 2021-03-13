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

package de.metas.common.rest_api.pricing.productprice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@ApiModel
public class JsonProductPriceRequest
{
	@ApiModelProperty(position = 10, dataType = "java.lang.String")
	@JsonProperty("productPriceIdentifier")
	@NonNull
	String productPriceIdentifier;

	@ApiModelProperty(position = 20, value = "jsonUpsertProductPriceRequest object")
	@JsonProperty("jsonUpsertProductPriceRequest")
	@NonNull
	JsonUpsertProductPriceRequest jsonUpsertProductPriceRequest;

	@JsonCreator
	@Builder
	public JsonProductPriceRequest(
			@NonNull @JsonProperty("productPriceIdentifier") final String productPriceIdentifier,
			@NonNull @JsonProperty("jsonUpsertProductPriceRequest") final JsonUpsertProductPriceRequest jsonUpsertProductPriceRequest)
	{
		this.productPriceIdentifier = productPriceIdentifier;
		this.jsonUpsertProductPriceRequest = jsonUpsertProductPriceRequest;
	}
}
