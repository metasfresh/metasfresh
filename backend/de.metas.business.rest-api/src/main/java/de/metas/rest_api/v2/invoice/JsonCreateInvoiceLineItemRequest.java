/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.rest_api.v2.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Value
@Builder
@Jacksonized
public class JsonCreateInvoiceLineItemRequest
{
	@NonNull
	String externalLineId;

	@NonNull
	String productIdentifier;

	@NonNull
	BigDecimal qtyToInvoice;

	@Nullable
	Integer line;

	@Nullable
	String lineDescription;

	@Nullable
	JsonPrice priceEntered;

	@Nullable
	String uomCode;

	@Nullable
	String taxCode;

	@Nullable
	String acctCode;

	@Nullable
	String accountName;

	@Nullable
	Map<String, Object> extendedProps;

	@JsonIgnore
	@Nullable
	public BigDecimal getPriceEntered()
	{
		return Optional.ofNullable(priceEntered)
				.map(JsonPrice::getValue)
				.orElse(null);
	}

	@JsonIgnore
	@Nullable
	public String getPriceUomCode()
	{
		return Optional.ofNullable(priceEntered)
				.map(JsonPrice::getPriceUomCode)
				.orElse(null);
	}

	@Value
	@Builder
	@Jacksonized
	static class JsonPrice
	{
		@NonNull
		String priceUomCode;

		@NonNull
		BigDecimal value;
	}
}
