/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.v2.productprice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.pricing.v2.constants.SwaggerDocConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Schema
public class JsonRequestProductPriceUpsertItem
{
	@Schema(description = SwaggerDocConstants.PRODUCT_PRICE_IDENTIFIER)
	@JsonProperty("productPriceIdentifier")
	@NonNull
	String productPriceIdentifier;

	@Schema
	@JsonProperty("productPrice")
	@NonNull
	JsonRequestProductPrice jsonRequestProductPrice;

	@JsonCreator
	@Builder
	public JsonRequestProductPriceUpsertItem(
			@NonNull @JsonProperty("productPriceIdentifier") final String productPriceIdentifier,
			@NonNull @JsonProperty("productPrice") final JsonRequestProductPrice jsonRequestProductPrice)
	{
		this.productPriceIdentifier = productPriceIdentifier;
		this.jsonRequestProductPrice = jsonRequestProductPrice;
	}
}
