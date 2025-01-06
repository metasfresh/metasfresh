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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "Price for one item, in the given unit of measurement and curency")
@Value
public class JsonPrice
{
	@Schema(requiredMode = REQUIRED)
	BigDecimal value;

	@Schema(requiredMode = REQUIRED)
	String currencyCode;

	@Schema(requiredMode = REQUIRED,
			description = "Identify which unit of measurement this about")
	String priceUomCode;

	@Builder
	@JsonCreator
	public JsonPrice(
			@JsonProperty("value") final BigDecimal value,
			@JsonProperty("currencyCode") final String currencyCode,
			@JsonProperty("priceUomCode") final String priceUomCode)
	{
		this.value = value;
		this.currencyCode = currencyCode;
		this.priceUomCode = priceUomCode;
	}


}
