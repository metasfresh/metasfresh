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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(name = "The newest stored conversion rate for a (from, to, type) combo.")
@Value
public class JsonNewestConversionRate
{
	@Schema(description = "3-letter ISO source currency code.")
	@NonNull
	String fromCurrencyCode;

	@Schema(description = "3-letter ISO target currency code.")
	@NonNull
	String toCurrencyCode;

	@Schema(description = "Conversion type code (S/P/A/C).")
	@NonNull
	String conversionTypeCode;

	@Schema(description = "The date this rate is valid from (ISO yyyy-MM-dd).")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NonNull
	LocalDate validFrom;

	@Schema(description = "amount(from) x multiplyRate = amount(to).")
	@NonNull
	BigDecimal multiplyRate;

	@Schema(description = "1/multiplyRate (scale 12, HALF_UP).")
	@NonNull
	BigDecimal divideRate;

	@Builder
	@JsonCreator
	public JsonNewestConversionRate(
			@NonNull @JsonProperty("fromCurrencyCode") final String fromCurrencyCode,
			@NonNull @JsonProperty("toCurrencyCode") final String toCurrencyCode,
			@NonNull @JsonProperty("conversionTypeCode") final String conversionTypeCode,
			@NonNull @JsonProperty("validFrom") final LocalDate validFrom,
			@NonNull @JsonProperty("multiplyRate") final BigDecimal multiplyRate,
			@NonNull @JsonProperty("divideRate") final BigDecimal divideRate)
	{
		this.fromCurrencyCode = fromCurrencyCode;
		this.toCurrencyCode = toCurrencyCode;
		this.conversionTypeCode = conversionTypeCode;
		this.validFrom = validFrom;
		this.multiplyRate = multiplyRate;
		this.divideRate = divideRate;
	}
}
