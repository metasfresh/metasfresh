package de.metas.camel.externalsystems.pcm.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.pcm.ExternalId;
import de.metas.camel.externalsystems.pcm.product.model.ProductRow;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonTaxCategoryMapping;
import de.metas.common.externalsystem.JsonTaxCategoryMappings;
import de.metas.common.product.v2.request.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductTaxCategoryUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import de.metas.common.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.camel.externalsystems.pcm.bpartner.ImportConstants.DEFAULT_COUNTRY_CODE;
import static de.metas.camel.externalsystems.pcm.product.ImportConstants.DEFAULT_PRODUCT_TYPE;
import static de.metas.camel.externalsystems.pcm.product.ImportConstants.DEFAULT_UOM_X12DE355_CODE;

@Value
public class ProductUpsertProcessor implements Processor
{
	@NonNull JsonExternalSystemRequest externalSystemRequest;
	@NonNull PInstanceLogger pInstanceLogger;

	@NonNull
	ImmutableMap<String, List<BigDecimal>> taxCategory2TaxRates;

	@Builder
	public ProductUpsertProcessor(
			@NonNull final JsonExternalSystemRequest externalSystemRequest,
			@NonNull final PInstanceLogger pInstanceLogger)
	{
		this.externalSystemRequest = externalSystemRequest;
		this.pInstanceLogger = pInstanceLogger;

		this.taxCategory2TaxRates = getTaxCategoryMappingRules(externalSystemRequest);
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ProductRow product = exchange.getIn().getBody(ProductRow.class);

		final ProductUpsertCamelRequest productUpsertCamelRequest = mapProductToProductRequestItem(product)
				.map(ProductUpsertProcessor::wrapUpsertItem)
				.map(upsertRequest -> ProductUpsertCamelRequest.builder()
						.jsonRequestProductUpsert(upsertRequest)
						.orgCode(externalSystemRequest.getOrgCode())
						.build())
				.orElse(null);

		exchange.getIn().setBody(productUpsertCamelRequest);
	}

	@NonNull
	private Optional<JsonRequestProductUpsertItem> mapProductToProductRequestItem(@NonNull final ProductRow product)
	{
		return mapToJsonRequestProduct(product)
				.map(jsonRequestProduct -> JsonRequestProductUpsertItem.builder()
						.productIdentifier(ExternalId.ofId(product.getProductIdentifier()).toExternalIdentifierString())
						.requestProduct(jsonRequestProduct)
						.externalSystemConfigId(externalSystemRequest.getExternalSystemConfigId())
						.build());
	}

	@NonNull
	private Optional<JsonRequestProduct> mapToJsonRequestProduct(@NonNull final ProductRow product)
	{
		if (hasMissingFields(product))
		{
			pInstanceLogger.logMessage("Skipping row due to missing mandatory information, see row: " + product);
			return Optional.empty();
		}

		final JsonRequestProduct jsonRequestProduct = new JsonRequestProduct();

		jsonRequestProduct.setName(product.getName());
		jsonRequestProduct.setCode(product.getValue() + "_" + product.getBpartnerIdentifier());
		jsonRequestProduct.setDescription(StringUtils.trimBlankToNull(product.getDescription()));
		jsonRequestProduct.setUomCode(DEFAULT_UOM_X12DE355_CODE);
		jsonRequestProduct.setType(DEFAULT_PRODUCT_TYPE);
		getBPartnerUpsertRequest(product)
				.map(ImmutableList::of)
				.ifPresent(jsonRequestProduct::setBpartnerProductItems);

		getProductTaxCategoryUpsertRequest(product)
				.map(ImmutableList::of)
				.ifPresent(jsonRequestProduct::setProductTaxCategories);

		return Optional.of(jsonRequestProduct);
	}

	@NonNull
	public Optional<JsonRequestBPartnerProductUpsert> getBPartnerUpsertRequest(@NonNull final ProductRow productRow)
	{
		if (Check.isBlank(productRow.getBpartnerIdentifier()))
		{
			return Optional.empty();
		}

		final JsonRequestBPartnerProductUpsert request = new JsonRequestBPartnerProductUpsert();
		request.setBpartnerIdentifier(ExternalId.ofId(productRow.getBpartnerIdentifier()).toExternalIdentifierString());
		request.setProductNo(productRow.getValue());
		request.setDescription(StringUtils.trimBlankToNull(productRow.getDescription()));
		request.setCuEAN(StringUtils.trimBlankToNull(productRow.getEan()));
		request.setCurrentVendor(true);
		return Optional.of(request);
	}

	@NonNull
	public Optional<JsonRequestProductTaxCategoryUpsert> getProductTaxCategoryUpsertRequest(@NonNull final ProductRow productRow)
	{
		if (productRow.getTaxRate() == null)
		{
			return Optional.empty();
		}

		final JsonRequestProductTaxCategoryUpsert request = new JsonRequestProductTaxCategoryUpsert();

		request.setTaxCategory(getTaxCategory(productRow.getTaxRate()));
		request.setCountryCode(DEFAULT_COUNTRY_CODE);
		request.setValidFrom(Instant.now().minus(365, ChronoUnit.DAYS));
		request.setActive(true);

		return Optional.of(request);
	}

	@NonNull
	private String getTaxCategory(@NonNull final BigDecimal taxRate)
	{
		return taxCategory2TaxRates
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue().stream().anyMatch(rate -> rate.compareTo(taxRate) == 0))
				.map(Map.Entry::getKey)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("No Tax Category found for Tax Rate = " + taxRate));
	}

	@NonNull
	private static ImmutableMap<String, List<BigDecimal>> getTaxCategoryMappingRules(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		final String taxCategoryMappings = externalSystemRequest.getParameters().get(ExternalSystemConstants.PARAM_TAX_CATEGORY_MAPPINGS);

		if (Check.isBlank(taxCategoryMappings))
		{
			return ImmutableMap.of();
		}

		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		try
		{
			return mapper.readValue(taxCategoryMappings, JsonTaxCategoryMappings.class)
					.getJsonTaxCategoryMappingList()
					.stream()
					.collect(ImmutableMap.toImmutableMap(JsonTaxCategoryMapping::getTaxCategory, JsonTaxCategoryMapping::getTaxRates));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static boolean hasMissingFields(@NonNull final ProductRow productRow)
	{
		return Check.isBlank(productRow.getProductIdentifier())
				|| Check.isBlank(productRow.getName())
				|| Check.isBlank(productRow.getValue());
	}

	@NonNull
	private static JsonRequestProductUpsert wrapUpsertItem(@NonNull final JsonRequestProductUpsertItem upsertItem)
	{
		return JsonRequestProductUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(ImmutableList.of(upsertItem))
				.build();
	}
}
