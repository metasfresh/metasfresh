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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
@ApiModel(description = "A single normalized conversion rate to upsert.")
public class JsonRequestConversionRateUpsertItem
{
	@ApiModelProperty(position = 10, required = true, value = "3-letter ISO source currency code.")
	@NonNull
	String fromCurrencyCode;

	@ApiModelProperty(position = 20, required = true, value = "3-letter ISO target currency code.")
	@NonNull
	String toCurrencyCode;

	@ApiModelProperty(position = 30, required = true, value = "amount(from) x multiplyRate = amount(to); positive.")
	@NonNull
	BigDecimal multiplyRate;

	@ApiModelProperty(position = 40, required = true, value = "The date this rate is valid from (ISO yyyy-MM-dd).")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NonNull
	LocalDate validFrom;

	@ApiModelProperty(position = 50, value = "Conversion type code (S/P/A/C); omitted defaults to the org default conversion type.")
	@Nullable
	String conversionTypeCode;

	@ApiModelProperty(position = 60, value = "The date this rate is valid to; omitted leaves the rate open (no gap).")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Nullable
	LocalDate validTo;

	@ApiModelProperty(position = 70, value = "Org code; omitted defaults to org 0 (shared).")
	@Nullable
	String orgCode;

	@JsonCreator
	public JsonRequestConversionRateUpsertItem(
			@NonNull @JsonProperty("fromCurrencyCode") final String fromCurrencyCode,
			@NonNull @JsonProperty("toCurrencyCode") final String toCurrencyCode,
			@NonNull @JsonProperty("multiplyRate") final BigDecimal multiplyRate,
			@NonNull @JsonProperty("validFrom") final LocalDate validFrom,
			@Nullable @JsonProperty("conversionTypeCode") final String conversionTypeCode,
			@Nullable @JsonProperty("validTo") final LocalDate validTo,
			@Nullable @JsonProperty("orgCode") final String orgCode)
	{
		this.fromCurrencyCode = fromCurrencyCode;
		this.toCurrencyCode = toCurrencyCode;
		this.multiplyRate = multiplyRate;
		this.validFrom = validFrom;
		this.conversionTypeCode = conversionTypeCode;
		this.validTo = validTo;
		this.orgCode = orgCode;
	}
}
