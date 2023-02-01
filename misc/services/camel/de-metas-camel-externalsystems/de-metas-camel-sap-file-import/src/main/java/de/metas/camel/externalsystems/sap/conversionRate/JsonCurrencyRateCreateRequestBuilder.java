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

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.sap.model.conversionRate.ConversionRateRow;
import de.metas.common.rest_api.v2.conversionRate.JsonCurrencyRateCreateRequest;
import de.metas.common.rest_api.v2.conversionRate.JsonCurrencyRateCreateRequestItem;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class JsonCurrencyRateCreateRequestBuilder
{
	private static final String EURX = "EURX";
	private static final String COMPANY_CONVERSION_TYPE = "Company";

	@NonNull
	String orgCode;

	@NonNull
	String currencyCodeFrom;

	@NonNull
	ImmutableList.Builder<JsonCurrencyRateCreateRequestItem> conversionRateUpsertGroupBuilder = new ImmutableList.Builder<>();

	@NonNull
	public static JsonCurrencyRateCreateRequestBuilder of(@NonNull final ConversionRateRow row, @NonNull final String orgCode)
	{
		final JsonCurrencyRateCreateRequestBuilder requestBuilder = new JsonCurrencyRateCreateRequestBuilder(orgCode, row.getFrom());

		requestBuilder.addConversionRateRow(row);

		return requestBuilder;
	}

	public boolean addConversionRateRow(@NonNull final ConversionRateRow conversionRateRow)
	{
		if (!isForSameCurrency(conversionRateRow))
		{
			return false;
		}

		conversionRateUpsertGroupBuilder.add(getItem(conversionRateRow));

		return true;
	}

	@NonNull
	public JsonCurrencyRateCreateRequest build()
	{
		return JsonCurrencyRateCreateRequest.builder()
				.orgCode(orgCode)
				.currencyCodeFrom(currencyCodeFrom)
				.requestItems(conversionRateUpsertGroupBuilder.build())
				.build();
	}

	@NonNull
	private JsonCurrencyRateCreateRequestItem getItem(@NonNull final ConversionRateRow row)
	{
		final JsonCurrencyRateCreateRequestItem jsonCurrencyRateCreateRequestItem = new JsonCurrencyRateCreateRequestItem();

		jsonCurrencyRateCreateRequestItem.setCurrencyCodeTo(row.getTo());
		jsonCurrencyRateCreateRequestItem.setValidFrom(row.getValidFrom());

		final BigDecimal divideRate = BigDecimal.valueOf(Double.parseDouble(row.getIndirQuot())).abs();
		jsonCurrencyRateCreateRequestItem.setDivideRate(divideRate);

		if (row.getExrt().equals(EURX))
		{
			jsonCurrencyRateCreateRequestItem.setConversionType(COMPANY_CONVERSION_TYPE);
		}

		return jsonCurrencyRateCreateRequestItem;
	}

	private boolean isForSameCurrency(@NonNull final ConversionRateRow conversionRateRow)
	{
		return currencyCodeFrom.equals(conversionRateRow.getFrom());
	}
}
