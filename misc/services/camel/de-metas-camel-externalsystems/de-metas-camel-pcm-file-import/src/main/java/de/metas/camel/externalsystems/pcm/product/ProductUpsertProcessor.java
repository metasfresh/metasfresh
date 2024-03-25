package de.metas.camel.externalsystems.pcm.product;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.pcm.ExternalId;
import de.metas.camel.externalsystems.pcm.product.model.ProductRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.product.v2.request.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProduct;
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

import java.util.Optional;

import static de.metas.camel.externalsystems.pcm.product.ImportConstants.DEFAULT_PRODUCT_TYPE;
import static de.metas.camel.externalsystems.pcm.product.ImportConstants.DEFAULT_UOM_X12DE355_CODE;

@Value
@Builder
public class ProductUpsertProcessor implements Processor
{
	@NonNull JsonExternalSystemRequest externalSystemRequest;
	@NonNull PInstanceLogger pInstanceLogger;

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
		jsonRequestProduct.setCode(product.getValue());
		jsonRequestProduct.setDescription(StringUtils.trimBlankToNull(product.getDescription()));
		jsonRequestProduct.setUomCode(DEFAULT_UOM_X12DE355_CODE);
		jsonRequestProduct.setType(DEFAULT_PRODUCT_TYPE);
		getBPartnerUpsertRequest(product)
				.map(ImmutableList::of)
				.ifPresent(jsonRequestProduct::setBpartnerProductItems);

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
