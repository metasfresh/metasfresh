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
public class JsonRequestProductPriceItemUpsert
{
	@ApiModelProperty(position = 10, dataType = "java.lang.String")
	@JsonProperty("productPriceIdentifier")
	String productPriceIdentifier;

	@ApiModelProperty(position = 20, value = "jsonUpsertProductPriceRequest object")
	@JsonProperty("jsonRequestProductPrice")
	JsonRequestProductPrice jsonRequestProductPrice;

	@JsonCreator
	@Builder
	public JsonRequestProductPriceItemUpsert(
			@NonNull @JsonProperty("productPriceIdentifier") final String productPriceIdentifier,
			@NonNull @JsonProperty("jsonRequestProductPrice") final JsonRequestProductPrice jsonRequestProductPrice)
	{
		this.productPriceIdentifier = productPriceIdentifier;
		this.jsonRequestProductPrice = jsonRequestProductPrice;
	}
}
