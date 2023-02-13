/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.conversionRate.JsonConversionRateResponse;
import de.metas.common.rest_api.v2.conversionRate.JsonConversionRateResponseItem;
import de.metas.cucumber.stepdefs.C_Currency_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Conversion_Rate_StepDef
{
	private final C_Conversion_Rate_StepDefData conversionRateTable;
	private final C_ConversionType_StepDefData conversionTypeTable;
	private final C_Currency_StepDefData currencyTable;

	private final TestContext testContext;

	public C_Conversion_Rate_StepDef(
			@NonNull final C_Conversion_Rate_StepDefData conversionRateTable,
			@NonNull final C_ConversionType_StepDefData conversionTypeTable,
			@NonNull final C_Currency_StepDefData currencyTable,
			@NonNull final TestContext testContext)
	{
		this.conversionRateTable = conversionRateTable;
		this.conversionTypeTable = conversionTypeTable;
		this.currencyTable = currencyTable;
		this.testContext = testContext;
	}

	@And("load created C_Conversion_Rate items:")
	public void load_created_conversion_rate_items(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final JsonConversionRateResponse response = mapper.readValue(testContext.getApiResponse().getContent(), JsonConversionRateResponse.class);
		final List<JsonConversionRateResponseItem> responseItems = response.getResponseItems();

		assertThat(responseItems.size()).isNotZero();
		assertThat(responseItems.size()).isEqualTo(dataTable.asMaps().size());

		for (int idx = 0; idx < responseItems.size(); idx++)
		{
			final JsonMetasfreshId currentItemId = responseItems.get(idx).getConversionRateId();

			final I_C_Conversion_Rate conversionRate = InterfaceWrapperHelper.load(currentItemId.getValue(), I_C_Conversion_Rate.class);

			final String conversionRateIdentifier = DataTableUtil.extractStringForColumnName(dataTable.asMaps().get(idx), I_C_Conversion_Rate.COLUMNNAME_C_Conversion_Rate_ID + "." + TABLECOLUMN_IDENTIFIER);
			conversionRateTable.putOrReplace(conversionRateIdentifier, conversionRate);
		}
	}

	@And("validate created C_Conversion_Rate:")
	public void validate_created_conversion_rate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String conversionRateIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Conversion_Rate.COLUMNNAME_C_Conversion_Rate_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Conversion_Rate conversionRate = conversionRateTable.get(conversionRateIdentifier);
			assertThat(conversionRate).isNotNull();

			final SoftAssertions softly = new SoftAssertions();

			final String currencyFromIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Currency currencyFrom = currencyTable.get(currencyFromIdentifier);
			softly.assertThat(conversionRate.getC_Currency_ID()).as(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID).isEqualTo(currencyFrom.getC_Currency_ID());

			final String currencyToIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Currency currencyTo = currencyTable.get(currencyToIdentifier);
			softly.assertThat(conversionRate.getC_Currency_ID_To()).as(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To).isEqualTo(currencyTo.getC_Currency_ID());

			final String conversionTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_ConversionType conversionType = conversionTypeTable.get(conversionTypeIdentifier);
			softly.assertThat(conversionRate.getC_ConversionType_ID()).as(I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID).isEqualTo(conversionType.getC_ConversionType_ID());

			final BigDecimal divideRate = DataTableUtil.extractBigDecimalForColumnName(row, I_C_Conversion_Rate.COLUMNNAME_DivideRate);
			softly.assertThat(conversionRate.getDivideRate()).as(I_C_Conversion_Rate.COLUMNNAME_DivideRate).isEqualTo(divideRate);

			final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(row, I_C_Conversion_Rate.COLUMNNAME_ValidFrom);
			softly.assertThat(conversionRate.getValidFrom()).as(I_C_Conversion_Rate.COLUMNNAME_ValidFrom).isEqualTo(validFrom);

			softly.assertAll();
		}
	}
}
