/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersion;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersionUpsert;
import de.metas.common.pricing.v2.pricelist.request.JsonRequestPriceListVersionUpsertItem;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPrice;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsert;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsertItem;
import de.metas.common.pricing.v2.productprice.TaxCategory;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.RESTUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class UpsertPricingAPI_StefDef
{
	private final OrgId defaultOrgId = StepDefConstants.ORG_ID;

	JsonRequestPriceListVersionUpsert.JsonRequestPriceListVersionUpsertBuilder jsonRequestPriceListVersionUpsert = JsonRequestPriceListVersionUpsert.builder();
	JsonRequestProductPriceUpsert.JsonRequestProductPriceUpsertBuilder jsonRequestProductPriceUpsert = JsonRequestProductPriceUpsert.builder();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final TestContext testContext;

	private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper()
			.registerModule(new JavaTimeModule());

	private final M_PriceList_StepDefData priceListTable;
	private final M_Product_StepDefData productTable;

	public UpsertPricingAPI_StefDef(
			@NonNull final TestContext testContext,
			@NonNull final M_PriceList_StepDefData priceListTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.testContext = testContext;
		this.priceListTable = priceListTable;
		this.productTable = productTable;
	}

	@When("the user adds price list version data")
	public void userAddsPriceListVersion(@NonNull final DataTable dataTable)
	{
		final ImmutableList.Builder<JsonRequestPriceListVersionUpsertItem> requestItems = ImmutableList.builder();
		final List<Map<String, String>> priceListVersion = dataTable.asMaps();
		for (final Map<String, String> row : priceListVersion)
		{
			requestItems.add(mapPriceListVersionRequest(row));
		}

		jsonRequestPriceListVersionUpsert.requestItems(requestItems.build());
	}

	@And("the user adds product prices data")
	public void userAddsProductPrices(@NonNull final DataTable dataTable)
	{
		final ImmutableList.Builder<JsonRequestProductPriceUpsertItem> requestItems = ImmutableList.builder();

		final List<Map<String, String>> priceListVersion = dataTable.asMaps();
		for (final Map<String, String> row : priceListVersion)
		{
			requestItems.add(mapProductPricesRequest(row));
		}
		jsonRequestProductPriceUpsert.requestItems(requestItems.build());
	}

	@And("we create a JsonRequestPriceListVersionUpsert, set syncAdvise to {string} and store request payload it in the test context")
	public void userAddsSyncAdvisePriceListVersion(@NonNull final String string) throws com.fasterxml.jackson.core.JsonProcessingException
	{
		final SyncAdvise syncAdvise = RESTUtil.mapSyncAdvise(string);

		jsonRequestPriceListVersionUpsert.syncAdvise(syncAdvise);
		new com.fasterxml.jackson.databind.ObjectMapper()
				.registerModule(new JavaTimeModule());

		testContext.setRequestPayload(objectMapper.writeValueAsString(jsonRequestPriceListVersionUpsert.build()));
	}

	@And("we create a JsonRequestProductPriceUpsert, set syncAdvise to {string} and store request payload it in the test context")
	public void userAddsSyncAdviseProductPrices(@NonNull final String string) throws com.fasterxml.jackson.core.JsonProcessingException
	{
		final SyncAdvise syncAdvise = RESTUtil.mapSyncAdvise(string);

		jsonRequestProductPriceUpsert.syncAdvise(syncAdvise);
		testContext.setRequestPayload(objectMapper.writeValueAsString(jsonRequestProductPriceUpsert.build()));
	}

	@Then("price list version is persisted correctly")
	public void validatePriceListVersionPersistedCorrectly() throws IOException
	{
		//req
		final List<JsonRequestPriceListVersionUpsertItem> requestItems = jsonRequestPriceListVersionUpsert.build().getRequestItems();

		//resp
		final JsonResponseUpsert jsonResponseUpsertItem = objectMapper.readValue(testContext.getApiResponse().getContent(),
																				 JsonResponseUpsert.class);
		for (final JsonResponseUpsertItem upsertItem : jsonResponseUpsertItem.getResponseItems())
		{
			validatePriceListVersion(upsertItem, requestItems);
		}
	}

	@Then("product price is persisted correctly")
	public void validateProductPricesPersistedCorrectly() throws IOException
	{
		//req
		final List<JsonRequestProductPriceUpsertItem> requestItems = jsonRequestProductPriceUpsert.build().getRequestItems();

		//resp
		final JsonResponseUpsert jsonResponseUpsertItem = objectMapper.readValue(testContext.getApiResponse().getContent(),
																				 JsonResponseUpsert.class);

		for (final JsonResponseUpsertItem upsertItem : jsonResponseUpsertItem.getResponseItems())
		{
			validateProductPrices(upsertItem, requestItems);
		}
	}

	private JsonRequestPriceListVersionUpsertItem mapPriceListVersionRequest(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, "Identifier");
		final String orgCode = DataTableUtil.extractStringOrNullForColumnName(row, "OrgCode");
		final String priceListIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_PriceList.COLUMNNAME_M_PriceList_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.Description");
		final Instant validFrom = DataTableUtil.extractInstantForColumnName(row, "ValidFrom");
		final boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT.IsActive", true);

		final I_M_PriceList m_priceList = priceListTable.get(priceListIdentifier);

		final JsonRequestPriceListVersion jsonRequestPriceListVersion = new JsonRequestPriceListVersion();
		jsonRequestPriceListVersion.setPriceListIdentifier(String.valueOf(m_priceList.getM_PriceList_ID()));
		jsonRequestPriceListVersion.setOrgCode(orgCode);
		jsonRequestPriceListVersion.setValidFrom(validFrom);
		jsonRequestPriceListVersion.setActive(isActive);
		jsonRequestPriceListVersion.setDescription(description);

		return JsonRequestPriceListVersionUpsertItem.builder()
				.priceListVersionIdentifier(identifier)
				.jsonRequestPriceListVersion(jsonRequestPriceListVersion)
				.build();
	}

	@NonNull
	private JsonRequestProductPriceUpsertItem mapProductPricesRequest(@NonNull final Map<String, String> row)
	{
		final String externalId = DataTableUtil.extractStringOrNullForColumnName(row, "Identifier");
		final String orgCode = DataTableUtil.extractStringOrNullForColumnName(row, "OrgCode");
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ProductPrice.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		final String priceStd = DataTableUtil.extractStringForColumnName(row, "PriceStd");
		final String priceLimit = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.PriceLimit");
		final String priceList = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.PriceList");
		final Integer seqNo = DataTableUtil.extractIntForColumnName(row, "SeqNo");
		final String isActiveValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.IsActive");
		final String taxCategoryInternalName = DataTableUtil.extractStringForColumnName(row, "TaxCategory.InternalName");

		final boolean isActive = !isActiveValue.equals("false");

		final JsonRequestProductPrice jsonRequestProductPrice = new JsonRequestProductPrice();
		jsonRequestProductPrice.setProductIdentifier(String.valueOf(product.getM_Product_ID()));
		jsonRequestProductPrice.setOrgCode(orgCode);
		jsonRequestProductPrice.setPriceList(new BigDecimal(priceList));
		jsonRequestProductPrice.setPriceStd(new BigDecimal(priceStd));
		jsonRequestProductPrice.setPriceLimit(new BigDecimal(priceLimit));
		jsonRequestProductPrice.setActive(isActive);
		jsonRequestProductPrice.setSeqNo(seqNo);
		jsonRequestProductPrice.setTaxCategory(TaxCategory.valueOf(taxCategoryInternalName));

		return JsonRequestProductPriceUpsertItem.builder()
				.productPriceIdentifier(externalId)
				.jsonRequestProductPrice(jsonRequestProductPrice).build();
	}

	private void validatePriceListVersion(
			@NonNull final JsonResponseUpsertItem upsertItem,
			@NonNull final List<JsonRequestPriceListVersionUpsertItem> requestItems)
	{

		final String requestIdentifier = upsertItem.getIdentifier();
		final JsonMetasfreshId jsonMetasfreshId = upsertItem.getMetasfreshId();

		final JsonRequestPriceListVersionUpsertItem jsonUpsertItem = requestItems.stream()
				.filter(item -> item.getPriceListVersionIdentifier().equals(requestIdentifier))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Error"));

		final JsonRequestPriceListVersion jsonRequestPriceListVersion = jsonUpsertItem.getJsonRequestPriceListVersion();

		final I_M_PriceList_Version persistedPriceListVersion = queryBL
				.createQueryBuilderOutOfTrx(I_M_PriceList_Version.class)
				.addEqualsFilter(I_M_PriceList_Version.COLUMN_M_PriceList_Version_ID, jsonMetasfreshId.getValue())
				.create()
				.firstOnlyOptional(I_M_PriceList_Version.class).orElse(null);

		assertThat(persistedPriceListVersion).isNotNull();
		assertThat(persistedPriceListVersion.getM_PriceList_ID()).isEqualTo(Integer.parseInt(jsonRequestPriceListVersion.getPriceListIdentifier()));
		assertThat(persistedPriceListVersion.getDescription()).isEqualTo(jsonRequestPriceListVersion.getDescription());
		assertThat(persistedPriceListVersion.getAD_Org_ID()).isEqualTo(defaultOrgId.getRepoId());
		assertThat(persistedPriceListVersion.isActive()).isEqualTo(jsonRequestPriceListVersion.getActive());
		assertThat(persistedPriceListVersion.getValidFrom()).isBefore(Timestamp.from(jsonRequestPriceListVersion.getValidFrom()));
	}

	private void validateProductPrices(
			@NonNull final JsonResponseUpsertItem upsertItem,
			@NonNull final List<JsonRequestProductPriceUpsertItem> requestItems)
	{

		final String requestIdentifier = upsertItem.getIdentifier();
		final JsonMetasfreshId jsonMetasfreshId = upsertItem.getMetasfreshId();

		final JsonRequestProductPriceUpsertItem jsonUpsertItem = requestItems.stream()
				.filter(item -> item.getProductPriceIdentifier().equals(requestIdentifier))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Error"));

		final JsonRequestProductPrice jsonRequestProductPrice = jsonUpsertItem.getJsonRequestProductPrice();

		final I_M_ProductPrice persistedProductPrice = queryBL
				.createQueryBuilderOutOfTrx(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_ProductPrice_ID, jsonMetasfreshId.getValue())
				.create()
				.firstOnlyOptional(I_M_ProductPrice.class).orElse(null);

		final Optional<TaxCategoryId> taxCategoryId = Services.get(ITaxBL.class).getTaxCategoryIdByInternalName(jsonRequestProductPrice.getTaxCategory().getInternalName());

		assertThat(persistedProductPrice).isNotNull();
		assertThat(persistedProductPrice.getAD_Org_ID()).isEqualTo(defaultOrgId.getRepoId());
		assertThat(persistedProductPrice.getM_ProductPrice_ID()).isEqualTo(jsonMetasfreshId.getValue());
		assertThat(persistedProductPrice.getM_Product_ID()).isEqualTo(Integer.parseInt(jsonRequestProductPrice.getProductIdentifier()));
		assertThat(persistedProductPrice.isActive()).isEqualTo(jsonRequestProductPrice.getActive());
		assertThat(persistedProductPrice.getPriceStd()).isEqualTo(jsonRequestProductPrice.getPriceStd());
		assertThat(persistedProductPrice.getPriceLimit()).isEqualTo(jsonRequestProductPrice.getPriceLimit());
		assertThat(persistedProductPrice.getPriceList()).isEqualTo(jsonRequestProductPrice.getPriceList());
		taxCategoryId.ifPresent(categoryId -> assertThat(persistedProductPrice.getC_TaxCategory_ID()).isEqualTo(categoryId.getRepoId()));
	}
}
