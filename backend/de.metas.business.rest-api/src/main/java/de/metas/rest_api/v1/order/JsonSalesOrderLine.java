package de.metas.rest_api.v1.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonSalesOrderLine
{
	@JsonProperty("productCode")
	String productCode;

	@JsonProperty("qty")
	BigDecimal qty;

	@JsonProperty("price")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	BigDecimal price;

	@Builder
	@JsonCreator
	public JsonSalesOrderLine(
			@JsonProperty("productCode") @NonNull final String productCode,
			@JsonProperty("qty") @NonNull final BigDecimal qty,
			@JsonProperty("price") @Nullable final BigDecimal price)
	{
		Check.assumeGreaterThanZero(qty, "qty");

		this.productCode = productCode;
		this.qty = qty;
		this.price = price;
	}

}
