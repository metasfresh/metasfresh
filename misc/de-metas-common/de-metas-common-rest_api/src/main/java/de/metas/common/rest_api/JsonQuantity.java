/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.rest_api;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class JsonQuantity
{
	@ApiModelProperty(position = 10, required = true)
	BigDecimal qty;

	@ApiModelProperty(position = 20, required = true, //
			value = "Unit of measurement; this translates to `C_UOM.X12DE355`.")
	String uomCode;

	@JsonCreator
	@Builder
	private JsonQuantity(
			@JsonProperty("qty") @NonNull final BigDecimal qty,
			@JsonProperty("uomCode") @NonNull final String uomCode)
	{
		this.qty = qty;
		this.uomCode = uomCode;
	}
}
