/*
 * #%L
 * de-metas-common-shipping
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

package de.metas.common.shipping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
public class JsonProduct
{
	String productNo; // we also call it "productNo" in the product-rest-api

	String name;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	String description;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	String packageSize;

	BigDecimal weight;

	@JsonCreator
	@Builder
	private JsonProduct(
			@JsonProperty("productNo") @NonNull final String productNo,
			@JsonProperty("name") @NonNull final String name,
			@JsonProperty("description") @Nullable final String description,
			@JsonProperty("packageSize") @Nullable final String packageSize,
			@JsonProperty("weight") @Nullable final BigDecimal weight)
	{
		this.productNo = productNo;
		this.name = name;
		this.description = description;
		this.packageSize = packageSize;
		this.weight = weight;
	}
}
