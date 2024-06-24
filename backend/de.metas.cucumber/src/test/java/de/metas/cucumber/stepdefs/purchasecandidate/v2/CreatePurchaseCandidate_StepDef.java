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

package de.metas.cucumber.stepdefs.purchasecandidate.v2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonAttributeInstance;
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.common.rest_api.v2.JsonPrice;
import de.metas.common.rest_api.v2.JsonPurchaseCandidate;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateCreateItem;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateCreateRequest;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateReference;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateRequest;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateResponse;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.common.rest_api.v2.JsonVendor;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.APIResponse;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nullable;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class CreatePurchaseCandidate_StepDef
{
	private final PurchaseCandidateRepository purchaseCandidateRepo;
	private final TestContext testContext;
	private JsonPurchaseCandidateCreateItem.JsonPurchaseCandidateCreateItemBuilder jsonPurchaseCandidateCreateItem;
	private final JsonPurchaseCandidateRequest.JsonPurchaseCandidateRequestBuilder jsonPurchaseCandidateRequest = JsonPurchaseCandidateRequest.builder();

	private final C_OrderLine_StepDefData orderLineTable;

	public CreatePurchaseCandidate_StepDef(@NonNull final TestContext testContext, @NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.testContext = testContext;
		this.orderLineTable = orderLineTable;
		this.purchaseCandidateRepo = SpringContextHolder.instance.getBean(PurchaseCandidateRepository.class);
	}

	@Given("the user adds a purchase candidate")
	public void addPurchaseCandidate(@NonNull final DataTable dataTable)
	{
		final Map<String, String> dataTableEntries = dataTable.asMaps().get(0);
		jsonPurchaseCandidateCreateItem = mapPurchaseCandidate(dataTableEntries);
	}

	@And("the user adds a purchase candidate price")
	public void add_contacts(@NonNull final DataTable dataTable)
	{
		jsonPurchaseCandidateCreateItem.price(mapPrice(dataTable.asMaps().get(0)));
	}

	@And("the user adds attribute set instances")
	public void addAttributeSetInstance(@NonNull final DataTable dataTable)
	{
		jsonPurchaseCandidateCreateItem.attributeSetInstance(mapAttributes(dataTable.asMaps()));
	}

	@Given("the user adds a purchase candidate enqueue request")
	public void addPurchaseCandidateEnqueueStatus(@NonNull final DataTable dataTable)
	{
		jsonPurchaseCandidateRequest.purchaseCandidates(mapEnqueueRequest(dataTable.asMaps()));
	}

	@And("the purchase candidate request is set in context")
	public void set_req_in_context() throws JsonProcessingException
	{
		final JsonPurchaseCandidateCreateRequest jsonPurchaseCandidateCreateRequest = JsonPurchaseCandidateCreateRequest.builder()
				.purchaseCandidate(jsonPurchaseCandidateCreateItem.build())
				.build();
		testContext.setRequestPayload(new ObjectMapper().writeValueAsString(jsonPurchaseCandidateCreateRequest));
	}

	@Given("the purchase candidate enqueue-status request is set in context")
	public void set_status_req_in_context() throws JsonProcessingException
	{
		testContext.setRequestPayload(new ObjectMapper().writeValueAsString(jsonPurchaseCandidateRequest.build()));
		testContext.setApiResponse(APIResponse.builder().build());
	}

	@Then("verify if data is persisted correctly for purchase candidate")
	public void verify_purchase_candidate_is_persisted_correctly() throws IOException
	{
		final String responseJson = testContext.getApiResponse().getContent();
		final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

		//response
		final JsonPurchaseCandidateResponse response = mapper.readValue(responseJson, JsonPurchaseCandidateResponse.class);
		assertThat(response.getPurchaseCandidates()).hasSize(1);

		final JsonPurchaseCandidate candidate = response.getPurchaseCandidates().get(0);
		final JsonMetasfreshId metasfreshId = candidate.getMetasfreshId();

		final PurchaseCandidate persistedResult = purchaseCandidateRepo.getById(PurchaseCandidateId.ofRepoId(metasfreshId.getValue()));
		validatePurchaseCandidate(persistedResult, candidate);

		assertThat(candidate.isProcessed()).isFalse();
		assertThat(candidate.getPurchaseOrders()).hasSize(0);
		assertThat(candidate.getWorkPackages()).hasSize(0);
	}

	@Then("verify purchase candidate status")
	public void verify_purchase_candidate_status() throws IOException
	{
		final String responseJson = testContext.getApiResponse().getContent();
		final com.fasterxml.jackson.databind.ObjectMapper mapper = newJsonObjectMapper();

		//response
		final JsonPurchaseCandidateResponse response = mapper.readValue(responseJson, JsonPurchaseCandidateResponse.class);
		assertThat(response.getPurchaseCandidates()).hasSizeGreaterThan(0);

		final JsonPurchaseCandidate candidate = response.getPurchaseCandidates().get(0);
		final JsonMetasfreshId metasfreshId = candidate.getMetasfreshId();

		final PurchaseCandidate persistedResult = purchaseCandidateRepo.getById(PurchaseCandidateId.ofRepoId(metasfreshId.getValue()));
		validatePurchaseCandidate(persistedResult, candidate);

		assertThat(candidate.isProcessed()).isTrue();
		assertThat(candidate.getPurchaseOrders()).hasSize(1);
	}

	private JsonPrice mapPrice(final Map<String, String> map)
	{
		return JsonPrice.builder()
				.value(DataTableUtil.extractBigDecimalForColumnName(map, "value"))
				.currencyCode(DataTableUtil.extractStringOrNullForColumnName(map, "OPT.currencyCode"))
				.currencyCode(DataTableUtil.extractStringOrNullForColumnName(map, "OPT.priceUomCode"))
				.build();
	}

	@Nullable
	private JsonAttributeInstance mapAttribute(final Map<String, String> map)
	{
		final String attributeCode = DataTableUtil.extractStringOrNullForColumnName(map, "attributeCode");
		final String valueStr = DataTableUtil.extractStringOrNullForColumnName(map, "valueStr");
		final BigDecimal valueNumber = DataTableUtil.extractBigDecimalOrNullForColumnName(map, "valueNumber");
		final ZonedDateTime valueDate = DataTableUtil.extractZonedDateTimeOrNullForColumnName(map, "valueDate");
		if (CoalesceUtil.coalesce(attributeCode, valueStr, valueNumber, valueDate) == null)
		{
			return null;
		}
		return JsonAttributeInstance.builder()
				.attributeCode(attributeCode)
				.valueStr(valueStr)
				.valueDate(TimeUtil.asLocalDate(valueDate))
				.valueNumber(valueNumber)
				.build();
	}

	@NonNull
	private JsonPurchaseCandidateCreateItem.JsonPurchaseCandidateCreateItemBuilder mapPurchaseCandidate(@NonNull final Map<String, String> dataTableEntries)
	{
		final String externalHeaderId = DataTableUtil.extractStringForColumnName(dataTableEntries, "ExternalHeaderId");
		final String externalLineId = DataTableUtil.extractStringForColumnName(dataTableEntries, "ExternalLineId");
		final String poReference = DataTableUtil.extractStringForColumnName(dataTableEntries, "POReference");
		final String orgCode = DataTableUtil.extractStringForColumnName(dataTableEntries, "orgCode");
		final String warehouse = DataTableUtil.extractStringForColumnName(dataTableEntries, "warehouse");
		final String product = DataTableUtil.extractStringForColumnName(dataTableEntries, "product");
		final String vendorId = DataTableUtil.extractStringForColumnName(dataTableEntries, "vendor.id");
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(dataTableEntries, "qty");
		final String uomCode = DataTableUtil.extractStringForColumnName(dataTableEntries, "qty.uom");
		final ZonedDateTime purchaseDatePromised = DataTableUtil.extractZonedDateTimeOrNullForColumnName(dataTableEntries, "OPT.purchaseDatePromised");
		final ZonedDateTime purchaseDateOrdered = DataTableUtil.extractZonedDateTimeOrNullForColumnName(dataTableEntries, "OPT.purchaseDateOrdered");
		final Boolean isManualPrice = DataTableUtil.extractBooleanForColumnName(dataTableEntries, "isManualPrice");
		final Boolean isManualDiscount = DataTableUtil.extractBooleanForColumnName(dataTableEntries, "isManualDiscount");
		final BigDecimal priceValue = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableEntries, "OPT.price");
		final String currency = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.currency");
		final String priceUom = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.priceUom");
		final BigDecimal discount = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableEntries, "OPT.discount");
		final String externalPurchaseOrderUrl = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.ExternalPurchaseOrderURL");

		final JsonQuantity quantity = JsonQuantity.builder()
				.qty(qty)
				.uomCode(uomCode)
				.build();
		final JsonPrice price = JsonPrice.builder()
				.value(priceValue)
				.priceUomCode(priceUom)
				.currencyCode(currency)
				.build();

		return JsonPurchaseCandidateCreateItem.builder()
				.orgCode(orgCode)
				.externalHeaderId(externalHeaderId)
				.externalLineId(externalLineId)
				.poReference(poReference)
				.warehouseIdentifier(warehouse)
				.productIdentifier(product)
				.vendor(JsonVendor.builder().bpartnerIdentifier(vendorId).build())
				.qty(quantity)
				.purchaseDateOrdered(purchaseDateOrdered)
				.purchaseDatePromised(purchaseDatePromised)
				.isManualPrice(isManualPrice)
				.isManualDiscount(isManualDiscount)
				.price(price)
				.discount(discount)
				.externalPurchaseOrderUrl(externalPurchaseOrderUrl);
	}

	private void validatePurchaseCandidate(@NonNull final PurchaseCandidate persistedResult, @NonNull final JsonPurchaseCandidate candidate)
	{
		assertThat(persistedResult.getExternalHeaderId().getValue()).isEqualTo(candidate.getExternalHeaderId().getValue());
		assertThat(persistedResult.getExternalLineId().getValue()).isEqualTo(candidate.getExternalLineId().getValue());
		assertThat(persistedResult.getExternalPurchaseOrderUrl()).isEqualTo(candidate.getExternalPurchaseOrderUrl());
	}

	private Collection<JsonPurchaseCandidateReference> mapEnqueueRequest(final List<Map<String, String>> dataTable)
	{
		final Map<JsonExternalId, Collection<JsonExternalId>> jsonExternalIdCollectionMap = dataTable.stream()
				.collect(Multimaps.toMultimap(
						dataRecord -> JsonExternalId.of(DataTableUtil.extractStringForColumnName(dataRecord, "ExternalHeaderId")),
						dataRecord -> JsonExternalId.ofOrNull(DataTableUtil.extractStringOrNullForColumnName(dataRecord, "ExternalLineId")),
						MultimapBuilder.hashKeys().arrayListValues()::build))
				.asMap();
		return jsonExternalIdCollectionMap.entrySet()
				.stream()
				.map(entry -> JsonPurchaseCandidateReference.builder()
						.externalHeaderId(entry.getKey())
						.externalLineIds(entry.getValue())
						.build())
				.collect(Collectors.toList());
	}

	private JsonAttributeSetInstance mapAttributes(final List<Map<String, String>> asMaps)
	{
		final List<JsonAttributeInstance> attributes = asMaps.stream()
				.map(this::mapAttribute)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		return JsonAttributeSetInstance.builder()
				.attributeInstances(attributes)
				.build();
	}

	public static com.fasterxml.jackson.databind.ObjectMapper newJsonObjectMapper()
	{
		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant
		Check.assumeNotNull(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.class, ""); // just to get a compile error if not present

		return new com.fasterxml.jackson.databind.ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}
}
