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
import de.metas.common.pricing.v2.productprice.JsonResponsePrice;
import de.metas.common.pricing.v2.productprice.JsonResponsePriceList;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PricesRestController_StepDef
{
	private final ITaxBL taxBL = Services.get(ITaxBL.class);

	private final M_Product_StepDefData productTable;

	private final TestContext testContext;

	public PricesRestController_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final TestContext testContext)
	{
		this.productTable = productTable;
		this.testContext = testContext;
	}

	@And("validate product price response")
	public void validate_product_price_response(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonResponsePriceList jsonResponsePriceList = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponsePriceList.class);
		assertThat(jsonResponsePriceList).isNotNull();

		final List<Map<String, String>> productTableList = dataTable.asMaps();
		assertThat(productTableList.size()).isEqualTo(jsonResponsePriceList.getPrices().size());

		for (int tableIndex = 0; tableIndex < productTableList.size(); tableIndex++)
		{
			validateJsonResponsePriceList(productTableList.get(tableIndex), jsonResponsePriceList.getPrices().get(tableIndex));
		}
	}

	private void validateJsonResponsePriceList(@NonNull final Map<String, String> row, @NonNull final JsonResponsePrice jsonResponsePrice)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ProductPrice.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String productCode = DataTableUtil.extractStringForColumnName(row, "ProductCode");
		final BigDecimal priceStd = DataTableUtil.extractBigDecimalForColumnName(row, I_M_ProductPrice.COLUMNNAME_PriceStd);
		final String currencyCode = DataTableUtil.extractStringForColumnName(row, "CurrencyCode");
		final String countryCode = DataTableUtil.extractStringForColumnName(row, "CountryCode");

		final String taxCategoryInternalName = DataTableUtil.extractStringForColumnName(row, I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName);
		final Optional<TaxCategoryId> taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName);
		assertThat(taxCategoryId).as("Missing taxCategory for internalName=%s", taxCategoryInternalName).isPresent();

		final String soTRX = DataTableUtil.extractStringForColumnName(row, "IsSOTrx");

		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();

		assertThat(jsonResponsePrice.getProductId().getValue()).isEqualTo(product.getM_Product_ID());
		assertThat(jsonResponsePrice.getProductCode()).isEqualTo(productCode);
		assertThat(jsonResponsePrice.getPrice()).isEqualTo(priceStd);
		assertThat(jsonResponsePrice.getCountryCode()).isEqualTo(countryCode);
		assertThat(jsonResponsePrice.getCurrencyCode()).isEqualTo(currencyCode);
		assertThat(jsonResponsePrice.getIsSOTrx().name()).isEqualTo(soTRX);
		assertThat(jsonResponsePrice.getTaxCategoryId().getValue()).isEqualTo(taxCategoryId.get().getRepoId());
	}
}
