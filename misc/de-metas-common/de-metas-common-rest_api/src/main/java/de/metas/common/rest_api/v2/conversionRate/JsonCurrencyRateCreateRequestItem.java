/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2.conversionRate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema
@Value
@Builder
@Jacksonized
public class JsonCurrencyRateCreateRequestItem
{
	@Schema(required = true, description = "Translated to `C_ConversionRate.C_Currency_To_ID")
	@NonNull
	String currencyCodeTo;

	@Schema(description = "Translated to `C_ConversionRate.C_ConversionType_ID`")
	@Nullable
	String conversionType;

	@Schema(required = true, description = "Translated to `C_ConversionRate.ValidFrom`")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NonNull
	LocalDate validFrom;

	@Schema(description = "Translated to `C_ConversionRate.ValidTo`")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Nullable
	LocalDate validTo;

	@Schema(required = true, description = "Translated to `C_ConversionRate.DivideRate`")
	@NonNull
	BigDecimal divideRate;
}
