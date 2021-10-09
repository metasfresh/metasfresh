/*
 * #%L
 * de-metas-common-product
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

package de.metas.common.product.v2.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.EXTERNAL_VERSION_DOC;

@Value
@Builder(toBuilder = true)
@ApiModel(description = "Contains an external id and the actual product to insert or update. The response will contain the given external id.")
public class JsonRequestProductUpsertItem
{
	@ApiModelProperty(position = 10, value = PRODUCT_IDENTIFIER_DOC)
	@NonNull
	String productIdentifier;

	@ApiModelProperty(position = 20, //
			value = "The version of the product." + EXTERNAL_VERSION_DOC)
	String externalVersion;

	@ApiModelProperty(position = 25, //
			value = "URL of the resource in the target external system.")
	@Nullable
	String externalReferenceUrl;

	@ApiModelProperty(position = 30)
	@NonNull
	JsonRequestProduct requestProduct;

	@JsonCreator
	public JsonRequestProductUpsertItem(
			@NonNull @JsonProperty("productIdentifier") final String productIdentifier,
			@Nullable @JsonProperty("externalVersion") final String externalVersion,
			@Nullable @JsonProperty("externalReferenceUrl") final String externalReferenceUrl,
			@NonNull @JsonProperty("requestProduct") final JsonRequestProduct requestProduct)
	{
		this.productIdentifier = productIdentifier;
		this.externalVersion = externalVersion;
		this.requestProduct = requestProduct;
		this.externalReferenceUrl = externalReferenceUrl;
	}
}
