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

package de.metas.cucumber.stepdefs.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.product.v2.response.JsonGetProductsResponse;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.product.v2.response.JsonProductBPartner;
import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductRestController_StepDef
{
	private static final String BPARTNER_PRODUCT_RESPONSE_PATH = "bpartners.";

	private final TestContext testContext;
	private final M_Product_StepDefData productTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final M_Product_Category_StepDefData productCategoryTable;

	public ProductRestController_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final M_Product_Category_StepDefData productCategoryTable)
	{
		this.testContext = testContext;
		this.productTable = productTable;
		this.bPartnerTable = bPartnerTable;
		this.productCategoryTable = productCategoryTable;
	}

	@Then("validate get products response")
	public void verify_getProducts_response_v2(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		DataTableRows.of(dataTable).forEach(this::verifyGetProductsResponseV2);
	}

	@Then("validate retrieve product response")
	public void validate_retrieve_product_response(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonProduct jsonProduct = mapper.readValue(testContext.getApiResponse().getContent(), JsonProduct.class);
		assertThat(jsonProduct).isNotNull();

		DataTableRows.of(dataTable).forEach(row -> validateJsonProduct(row, jsonProduct));
	}

	private void verifyGetProductsResponseV2(@NonNull final DataTableRow row) throws JsonProcessingException
	{
		final String value = extractProductValue(row);
		final String name = extractProductName(row);
		final String x12de355Code = row.getAsString(I_C_UOM.COLUMNNAME_UOMSymbol);
		final String ean = row.getAsString(I_M_Product.COLUMNNAME_UPC);
		final String description = row.getAsString(I_M_Product.COLUMNNAME_Description);

		final I_M_Product productRecord = productTable.get(row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID));

		final JsonGetProductsResponse jsonGetProductsResponse = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(testContext.getApiResponse().getContent(), JsonGetProductsResponse.class);

		final JsonProduct returnedProduct = jsonGetProductsResponse.getProducts().stream()
				.filter(product -> product.getId().getValue() == productRecord.getM_Product_ID())
				.findFirst()
				.orElse(null);

		assertThat(returnedProduct).isNotNull();

		assertThat(returnedProduct.getProductNo()).isEqualTo(value);
		assertThat(returnedProduct.getName()).isEqualTo(name);
		assertThat(returnedProduct.getUom()).isEqualTo(x12de355Code);
		assertThat(returnedProduct.getEan()).isEqualTo(ean);
		assertThat(returnedProduct.getDescription()).isEqualTo(description);

		final JsonProductBPartner bpartnerProduct = Check.singleElement(returnedProduct.getBpartners());

		verifyBPartnerProduct(bpartnerProduct, row);
	}

	private void verifyBPartnerProduct(@NonNull final JsonProductBPartner returnedProductBPartner, @NonNull final DataTableRow row)
	{
		final String productNo = row.getAsString(BPARTNER_PRODUCT_RESPONSE_PATH + I_C_BPartner_Product.COLUMNNAME_ProductNo);
		final boolean isExcludedFromSale = row.getAsBoolean(BPARTNER_PRODUCT_RESPONSE_PATH + I_C_BPartner_Product.COLUMNNAME_IsExcludedFromSale);
		final String exclusionFromSaleReason = row.getAsOptionalString(BPARTNER_PRODUCT_RESPONSE_PATH + I_C_BPartner_Product.COLUMNNAME_ExclusionFromSaleReason).orElse(null);
		final boolean isExcludedFromPurchase = row.getAsBoolean(BPARTNER_PRODUCT_RESPONSE_PATH + I_C_BPartner_Product.COLUMNNAME_IsExcludedFromPurchase);
		final String exclusionFromPurchaseReason = row.getAsOptionalString(BPARTNER_PRODUCT_RESPONSE_PATH + I_C_BPartner_Product.COLUMNNAME_ExclusionFromPurchaseReason).orElse(null);
		final String ean = row.getAsOptionalString(BPARTNER_PRODUCT_RESPONSE_PATH + "ean").orElse(null);

		final I_C_BPartner bPartnerRecord = bPartnerTable.get(row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID));

		assertThat(returnedProductBPartner.getBpartnerId().getValue()).isEqualTo(bPartnerRecord.getC_BPartner_ID());
		assertThat(returnedProductBPartner.getProductNo()).isEqualTo(productNo);
		assertThat(returnedProductBPartner.isExcludedFromSale()).isEqualTo(isExcludedFromSale);
		assertThat(returnedProductBPartner.getExclusionFromSaleReason()).isEqualTo(exclusionFromSaleReason);
		assertThat(returnedProductBPartner.isExcludedFromPurchase()).isEqualTo(isExcludedFromPurchase);
		assertThat(returnedProductBPartner.getExclusionFromPurchaseReason()).isEqualTo(exclusionFromPurchaseReason);

		if (Check.isNotBlank(ean))
		{
			assertThat(returnedProductBPartner.getEan()).isEqualTo(ean);
		}
	}

	private void validateJsonProduct(@NonNull final DataTableRow row, @NonNull final JsonProduct jsonProduct)
	{
		final ProductId productId = productTable.getId(row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID));
		final ProductCategoryId productCategoryId = productCategoryTable.getId(row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_Category_ID));
		final String name = extractProductName(row);
		final String uomSymbol = row.getAsString("UomSymbol");

		assertThat(jsonProduct.getId().getValue()).isEqualTo(productId.getRepoId());
		assertThat(jsonProduct.getName()).isEqualTo(name);
		assertThat(jsonProduct.getUom()).isEqualTo(uomSymbol);
		assertThat(jsonProduct.getProductCategoryId().getValue()).isEqualTo(productCategoryId.getRepoId());

		final JsonProductBPartner bpartnerProduct = Check.singleElement(jsonProduct.getBpartners());

		verifyBPartnerProduct(bpartnerProduct, row);
	}

	private String extractProductValue(final @NonNull DataTableRow row)
	{
		return parseString(row, I_M_Product.COLUMNNAME_Value, newProductParsingContext());
	}

	private String extractProductName(final @NonNull DataTableRow row)
	{
		return parseString(row, I_M_Product.COLUMNNAME_Name, newProductParsingContext());
	}

	private Evaluatee newProductParsingContext()
	{
		final HashMap<String, String> map = new HashMap<>();
		productTable.forEach((identifier, product) -> map.put(identifier.getAsString() + ".productName", product.getName()));
		return Evaluatees.ofMap(map);
	}

	private String parseString(final @NonNull DataTableRow row, final String columnName, final Evaluatee context)
	{
		return StringExpressionCompiler.instance.compile(row.getAsString(columnName))
				.evaluate(context, IExpressionEvaluator.OnVariableNotFound.Fail);
	}

}