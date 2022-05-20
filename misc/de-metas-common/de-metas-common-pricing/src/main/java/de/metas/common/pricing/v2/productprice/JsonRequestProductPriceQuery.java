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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.v2.SwaggerDocConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
@ApiModel
public class JsonRequestProductPriceQuery
{
	@ApiModelProperty(position = 10,
			dataType = "java.lang.String",
			value = SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC)
	@JsonProperty("bpartnerIdentifier")
	String bpartnerIdentifier;

	@ApiModelProperty(position = 20,
			dataType = "java.lang.String",
			value = SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC)
	@JsonProperty("productIdentifier")
	String productIdentifier;

	@ApiModelProperty(position = 30, dataType = "java.time.LocalDate")
	@JsonProperty("targetDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate targetDate;

	@JsonCreator
	@Builder
	public JsonRequestProductPriceQuery(
			@NonNull @JsonProperty("bpartnerIdentifier") final String bpartnerIdentifier,
			@NonNull @JsonProperty("productIdentifier") final String productIdentifier,
			@NonNull @JsonProperty("targetDate") final LocalDate targetDate)
	{
		this.bpartnerIdentifier = bpartnerIdentifier;
		this.productIdentifier = productIdentifier;
		this.targetDate = targetDate;
	}
}
