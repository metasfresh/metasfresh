/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.common.rest_api.v2.currencyconversion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@ApiModel("An active currency.")
@Value
public class JsonCurrency
{
	@ApiModelProperty(position = 10, value = "3-letter ISO currency code.")
	@NonNull
	String currencyCode;

	@ApiModelProperty(position = 20, value = "The name of the currency.")
	@Nullable
	String name;

	@Builder
	@JsonCreator
	public JsonCurrency(
			@NonNull @JsonProperty("currencyCode") final String currencyCode,
			@Nullable @JsonProperty("name") final String name)
	{
		this.currencyCode = currencyCode;
		this.name = name;
	}
}
