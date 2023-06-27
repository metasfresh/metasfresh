/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.pricing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.pricing.v2.productprice.JsonPriceListResponse;
import de.metas.common.pricing.v2.productprice.JsonPriceListVersionResponse;
import de.metas.common.pricing.v2.productprice.JsonResponsePrice;
import de.metas.common.pricing.v2.productprice.JsonResponseProductPriceQuery;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PricesRestController_StepDef
{
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

	private final M_Product_StepDefData productTable;
	private final M_PriceList_Version_StepDefData priceListVersionTable;
	private final M_PriceList_StepDefData priceListTable;

	private final TestContext testContext;

	public PricesRestController_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_PriceList_Version_StepDefData priceListVersionTable,
			@NonNull final M_PriceList_StepDefData priceListTable,
			@NonNull final TestContext testContext)
	{
		this.productTable = productTable;
		this.priceListVersionTable = priceListVersionTable;
		this.priceListTable = priceListTable;
		this.testContext = testContext;
	}

	@And("validate JsonResponseProductPriceQuery.JsonPriceListResponse")
	public void validate_JsonResponseProductPriceQuery(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final JsonResponseProductPriceQuery jsonResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseProductPriceQuery.class);
		assertThat(jsonResponse).isNotNull();

		final List<JsonPriceListResponse> jsonResponsePriceList = jsonResponse.getPriceList();
		assertThat(jsonResponsePriceList.size()).isGreaterThan(0);

		final List<Map<String, String>> expectedTableList = dataTable.asMaps();
		for (int index = 0; index < expectedTableList.size(); index++)
		{
			final JsonPriceListResponse actualPriceList = jsonResponsePriceList.get(index);
			final Map<String, String> expectedPriceList = expectedTableList.get(index);

			validateJsonResponsePriceList(expectedPriceList, actualPriceList);
		}
	}

	@And("validate JsonResponseProductPriceQuery.JsonPriceListResponse.JsonPriceListVersionResponse")
	public void validateJsonPriceListVersionResponse(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final JsonResponseProductPriceQuery jsonResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseProductPriceQuery.class);
		assertThat(jsonResponse).isNotNull();
		final List<JsonPriceListResponse> jsonResponsePriceList = jsonResponse.getPriceList();

		final List<Map<String, String>> expectedTableList = dataTable.asMaps();
		for (final Map<String, String> expectedRow : expectedTableList)
		{
			final String priceListIdentifier = DataTableUtil.extractStringForColumnName(expectedRow, I_M_PriceList.COLUMNNAME_M_PriceList_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_PriceList priceList = priceListTable.get(priceListIdentifier);

			final JsonPriceListResponse actualPriceList = jsonResponsePriceList.stream()
					.filter(jsonResponsePL -> jsonResponsePL.getMetasfreshId().getValue() == priceList.getM_PriceList_ID())
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No price list found for id")
							.appendParametersToMessage()
							.setParameter("M_PriceList_ID", priceList.getM_PriceList_ID()));

			final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(expectedRow, JsonPriceListVersionResponse.class.getSimpleName() + "." + I_M_PriceList_Version.COLUMNNAME_ValidFrom);

			final JsonPriceListVersionResponse jsonResponsePriceListVersion = actualPriceList.getPriceListVersions()
					.stream()
					.filter(jsonResponsePLV -> jsonResponsePLV.getValidFrom().equals(validFrom.toInstant()))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No M_PriceList_Version for validFrom")
							.appendParametersToMessage()
							.setParameter("validFrom", validFrom));

			validateJsonResponsePriceListVersion(expectedRow, jsonResponsePriceListVersion);
		}
	}

	@And("validate JsonResponseProductPriceQuery.JsonPriceListResponse.JsonPriceListVersionResponse.JsonResponsePrice")
	public void validateJsonResponsePrice(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final JsonResponseProductPriceQuery jsonResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseProductPriceQuery.class);
		assertThat(jsonResponse).isNotNull();
		final List<JsonPriceListResponse> jsonResponsePriceList = jsonResponse.getPriceList();

		final List<Map<String, String>> expectedTableList = dataTable.asMaps();
		for (final Map<String, String> expectedRow : expectedTableList)
		{
			final String priceListIdentifier = DataTableUtil.extractStringForColumnName(expectedRow, I_M_PriceList.COLUMNNAME_M_PriceList_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_PriceList priceList = priceListTable.get(priceListIdentifier);

			final JsonPriceListResponse actualPriceList = jsonResponsePriceList.stream()
					.filter(jsonResponsePL -> jsonResponsePL.getMetasfreshId().getValue() == priceList.getM_PriceList_ID())
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No price list found for id")
							.appendParametersToMessage()
							.setParameter("M_PriceList_ID", priceList.getM_PriceList_ID()));

			final String priceListVersionIdentifier = DataTableUtil.extractStringForColumnName(expectedRow, I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_PriceList_Version priceListVersion = priceListVersionTable.get(priceListVersionIdentifier);

			final JsonPriceListVersionResponse jsonResponsePriceListVersion = actualPriceList.getPriceListVersions()
					.stream()
					.filter(jsonResponsePLV -> jsonResponsePLV.getMetasfreshId().getValue() == priceListVersion.getM_PriceList_Version_ID())
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No M_PriceList_Version for M_PriceList_Version_ID")
							.appendParametersToMessage()
							.setParameter("M_PriceList_Version_ID", priceListVersion.getM_PriceList_Version_ID()));

			final String productCode = DataTableUtil.extractStringForColumnName(expectedRow, JsonResponsePrice.class.getSimpleName() + "." + I_M_Product.COLUMNNAME_Value);
			final BigDecimal priceStd = DataTableUtil.extractBigDecimalForColumnName(expectedRow, JsonResponsePrice.class.getSimpleName() + "." + I_M_ProductPrice.COLUMNNAME_PriceStd);

			final JsonResponsePrice jsonResponsePrice = jsonResponsePriceListVersion.getJsonResponsePrices()
					.stream()
					.filter(jsonPrice -> jsonPrice.getPrice().equals(priceStd) && jsonPrice.getProductCode().equals(productCode))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No M_PriceList_Version for M_PriceList_Version_ID")
							.appendParametersToMessage()
							.setParameter("M_PriceList_Version_ID", priceListVersion.getM_PriceList_Version_ID()));

			validateJsonResponsePrice(expectedRow, jsonResponsePrice);
		}
	}

	private void validateJsonResponsePriceList(@NonNull final Map<String, String> row, @NonNull final JsonPriceListResponse jsonResponsePriceList)
	{
		final String name = DataTableUtil.extractStringForColumnName(row, JsonPriceListResponse.class.getSimpleName() + "." + I_M_PriceList.COLUMNNAME_Name);
		final int pricePrecision = DataTableUtil.extractIntForColumnName(row, JsonPriceListResponse.class.getSimpleName() + "." + I_M_PriceList.COLUMNNAME_PricePrecision);
		final String countryCode = DataTableUtil.extractStringForColumnName(row, JsonPriceListResponse.class.getSimpleName() + ".CountryCode");
		final String currencyCode = DataTableUtil.extractStringForColumnName(row, JsonPriceListResponse.class.getSimpleName() + ".CurrencyCode");
		final String soTRX = DataTableUtil.extractStringForColumnName(row, JsonPriceListResponse.class.getSimpleName() + ".IsSOTrx");

		assertThat(jsonResponsePriceList.getName()).isEqualTo(name);
		assertThat(jsonResponsePriceList.getPricePrecision()).isEqualTo(pricePrecision);
		assertThat(jsonResponsePriceList.getCountryCode()).isEqualTo(countryCode);
		assertThat(jsonResponsePriceList.getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(jsonResponsePriceList.getIsSOTrx().name()).isEqualTo(soTRX);

		final I_M_PriceList priceList = InterfaceWrapperHelper.load(jsonResponsePriceList.getMetasfreshId().getValue(), I_M_PriceList.class);
		assertThat(priceList).isNotNull();

		final String priceListIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_PriceList.COLUMNNAME_M_PriceList_ID + "." + TABLECOLUMN_IDENTIFIER);
		priceListTable.putOrReplace(priceListIdentifier, priceList);
	}

	private void validateJsonResponsePriceListVersion(@NonNull final Map<String, String> row, @NonNull final JsonPriceListVersionResponse jsonResponsePriceListVersion)
	{
		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(row, JsonPriceListVersionResponse.class.getSimpleName() + "." + I_M_PriceList_Version.COLUMNNAME_ValidFrom);

		assertThat(jsonResponsePriceListVersion.getValidFrom()).isEqualTo(validFrom.toInstant());

		final I_M_PriceList_Version priceListVersion = InterfaceWrapperHelper.load(jsonResponsePriceListVersion.getMetasfreshId().getValue(), I_M_PriceList_Version.class);
		assertThat(priceListVersion).isNotNull();

		final String priceListVersionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID + "." + TABLECOLUMN_IDENTIFIER);
		priceListVersionTable.putOrReplace(priceListVersionIdentifier, priceListVersion);
	}

	private void validateJsonResponsePrice(@NonNull final Map<String, String> row, @NonNull final JsonResponsePrice jsonResponsePrice)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ProductPrice.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();

		final String productCode = DataTableUtil.extractStringForColumnName(row, JsonResponsePrice.class.getSimpleName() + "." + I_M_Product.COLUMNNAME_Value);
		final BigDecimal priceStd = DataTableUtil.extractBigDecimalForColumnName(row, JsonResponsePrice.class.getSimpleName() + "." + I_M_ProductPrice.COLUMNNAME_PriceStd);

		final String taxCategoryInternalName = DataTableUtil.extractStringForColumnName(row, I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName);
		final Optional<TaxCategoryId> taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName);
		assertThat(taxCategoryId).as("Missing taxCategory for internalName=%s", taxCategoryInternalName).isPresent();

		assertThat(jsonResponsePrice.getProductId().getValue()).isEqualTo(product.getM_Product_ID());
		assertThat(jsonResponsePrice.getProductCode()).isEqualTo(productCode);
		assertThat(jsonResponsePrice.getPrice()).isEqualTo(priceStd);
		assertThat(jsonResponsePrice.getTaxCategoryId().getValue()).isEqualTo(taxCategoryId.get().getRepoId());
	}
}
