/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.conversionRate;

import de.metas.camel.externalsystems.sap.model.conversionRate.ConversionRateRow;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class ConversionRateContext
{
	@NonNull
	private final String orgCode;

	@Nullable
	private JsonCurrencyRateCreateRequestBuilder jsonCurrencyRateCreateRequestBuilder;

	@NonNull
	public static ConversionRateContext of(@NonNull final String orgCode)
	{
		return ConversionRateContext.builder()
				.orgCode(orgCode)
				.build();
	}

	public void initConversionRateRequestBuilderFor(@NonNull final ConversionRateRow row)
	{
		this.jsonCurrencyRateCreateRequestBuilder = JsonCurrencyRateCreateRequestBuilder.of(row, orgCode);
	}
}
