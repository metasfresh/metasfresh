/*
 * #%L
 * de-metas-camel-sap
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

package de.metas.camel.externalsystems.sap.product;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.sap.common.MessageLogger;
import de.metas.camel.externalsystems.sap.model.product.ProductRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

import static de.metas.camel.externalsystems.sap.common.ExternalIdentifierFormat.formatExternalId;
import static de.metas.camel.externalsystems.sap.product.GetProductsFromFileRouteBuilder.ROUTE_PROPERTY_UPSERT_PRODUCT_ROUTE_CONTEXT;

public class ProductUpsertProcessor implements Processor
{
	@NonNull
	final JsonExternalSystemRequest externalSystemRequest;
	@NonNull
	final ProcessLogger processLogger;

	public ProductUpsertProcessor(
			final @NonNull JsonExternalSystemRequest externalSystemRequest,
			final @NonNull ProcessLogger processLogger)
	{
		this.externalSystemRequest = externalSystemRequest;
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ProductRow product = exchange.getIn().getBody(ProductRow.class);

		final UpsertProductRouteContext routeContext = exchange.getProperty(ROUTE_PROPERTY_UPSERT_PRODUCT_ROUTE_CONTEXT, UpsertProductRouteContext.class);
		routeContext.setProductRow(product);

		final ProductUpsertCamelRequest productUpsertCamelRequest = mapProductToProductRequestItem(routeContext)
				.map(ProductUpsertProcessor::wrapUpsertItem)
				.map(upsertRequest -> ProductUpsertCamelRequest.builder()
						.jsonRequestProductUpsert(upsertRequest)
						.orgCode(externalSystemRequest.getOrgCode())
						.build())
				.orElse(null);

		exchange.getIn().setBody(productUpsertCamelRequest);
	}

	@NonNull
	private Optional<JsonRequestProductUpsertItem> mapProductToProductRequestItem(@NonNull final UpsertProductRouteContext routeContext)
	{
		if (!validProductRow(routeContext) || !existingMappings(routeContext))
		{
			return Optional.empty();
		}

		final ProductRow product = routeContext.getProductRow();
		Check.assumeNotNull(product, "ProductRow cannot be missing at this point!");

		final String productIdentifier = formatExternalId(product.getMaterialCode());

		return mapToJsonRequestProduct(routeContext)
				.map(jsonRequestProduct -> JsonRequestProductUpsertItem.builder()
						.productIdentifier(productIdentifier)
						.requestProduct(jsonRequestProduct)
						.externalSystemConfigId(externalSystemRequest.getExternalSystemConfigId())
						.isReadOnlyInMetasfresh(true)
						.build());
	}

	@NonNull
	private static JsonRequestProductUpsert wrapUpsertItem(@NonNull final JsonRequestProductUpsertItem upsertItem)
	{
		return JsonRequestProductUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(ImmutableList.of(upsertItem))
				.build();
	}

	@NonNull
	private Optional<JsonRequestProduct> mapToJsonRequestProduct(@NonNull final UpsertProductRouteContext routeContext)
	{
		final ProductRow product = routeContext.getProductRow();
		Check.assumeNotNull(product, "ProductRow cannot be missing at this point!");

		final ExternalMappingsHolder externalMappingsHolder = routeContext.getSapExternalMappingsHolder();
		final MessageLogger messageLogger = routeContext.getMessageLogger();

		final String resolvedUOMValue = externalMappingsHolder.resolveUOMSAPValue(product.getUom()).orElse(null);
		if (resolvedUOMValue == null)
		{
			messageLogger.logErrorMessage("Skipping row due to missing mapping for provided uom code: " + product.getUom());
			return Optional.empty();
		}

		final String resolvedProductTypeValue = externalMappingsHolder.resolveProductTypeSAPValue(product.getMaterialGroup()).orElse(null);
		if (resolvedProductTypeValue == null)
		{
			messageLogger.logErrorMessage("Skipping row due to missing mapping for provided product type code: " + product.getMaterialGroup());
			return Optional.empty();
		}

		final JsonMetasfreshId resolvedProductCategoryIdentifier = externalMappingsHolder.resolveProductCategorySAPValue(product.getMaterialCategory()).orElse(null);
		if (resolvedProductCategoryIdentifier == null)
		{
			messageLogger.logErrorMessage("Skipping row due to missing mapping for provided product category code: " + product.getMaterialCategory());
			return Optional.empty();
		}

		final JsonRequestProduct jsonRequestProduct = new JsonRequestProduct();

		jsonRequestProduct.setName(product.getName());
		jsonRequestProduct.setCode(product.getMaterialCode() + "_" + product.getName());
		jsonRequestProduct.setSectionCode(product.getSectionCode());
		jsonRequestProduct.setStocked(true);
		jsonRequestProduct.setDiscontinued(false);

		jsonRequestProduct.setUomCode(resolvedUOMValue);
		jsonRequestProduct.setType(JsonRequestProduct.Type.valueOf(resolvedProductTypeValue));
		jsonRequestProduct.setProductCategoryIdentifier(String.valueOf(resolvedProductCategoryIdentifier.getValue()));

		return Optional.of(jsonRequestProduct);
	}

	private boolean validProductRow(@NonNull final UpsertProductRouteContext routeContext)
	{
		final ProductRow product = routeContext.getProductRow();
		Check.assumeNotNull(product, "ProductRow cannot be missing at this point!");

		final MessageLogger messageLogger = routeContext.getMessageLogger();

		if (Check.isBlank(product.getName()))
		{
			messageLogger.logErrorMessage("Skipping row due to missing product name! MaterialCode=" + product.getMaterialCode());

			return false;
		}

		if (Check.isBlank(product.getUom()))
		{
			messageLogger.logErrorMessage("Skipping row due to missing uom code! MaterialCode=" + product.getMaterialCode());
			return false;
		}

		if (Check.isBlank(product.getMaterialGroup()))
		{
			messageLogger.logErrorMessage("Skipping row due to missing product type! MaterialCode=" + product.getMaterialCode());
			return false;
		}

		if (Check.isBlank(product.getMaterialCategory()))
		{
			messageLogger.logErrorMessage("Skipping row due to missing product category! MaterialCode=" + product.getMaterialCode());
			return false;
		}

		return true;
	}

	private boolean existingMappings(@NonNull final UpsertProductRouteContext routeContext)
	{
		final ProductRow product = routeContext.getProductRow();
		Check.assumeNotNull(product, "ProductRow cannot be missing at this point!");

		final MessageLogger messageLogger = routeContext.getMessageLogger();

		final ExternalMappingsHolder externalMappingsHolder = routeContext.getSapExternalMappingsHolder();
		if (externalMappingsHolder.getExternalRef2UOMMapping().isEmpty())
		{
			messageLogger.logErrorMessage("Skipping row due to missing mappings for S_ExternalReference.Type = UOM! MaterialCode=" + product.getMaterialCode());
			return false;
		}

		if (externalMappingsHolder.getExternalRef2ProductCategoryMapping().isEmpty())
		{
			messageLogger.logErrorMessage("Skipping row due to missing mappings for S_ExternalReference.Type = ProductCategory! MaterialCode=" + product.getMaterialCode());
			return false;
		}

		if (externalMappingsHolder.getExternalValue2ProductType().isEmpty())
		{
			messageLogger.logErrorMessage("Skipping row due to missing ProductType_External_Mapping mappings! MaterialCode=" + product.getMaterialCode());
			return false;
		}

		return true;
	}
}
