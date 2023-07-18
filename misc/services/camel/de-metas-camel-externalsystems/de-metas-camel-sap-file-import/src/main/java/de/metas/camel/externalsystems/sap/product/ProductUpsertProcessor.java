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
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.mapping.ExternalMappingsHolder;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.sap.model.product.ProductRow;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.product.v2.request.JsonRequestProductWarehouseAssignmentSave;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

import static de.metas.camel.externalsystems.sap.common.ExternalIdentifierFormat.formatExternalId;

public class ProductUpsertProcessor implements Processor
{
	@NonNull
	final JsonExternalSystemRequest externalSystemRequest;
	@NonNull
	final PInstanceLogger pInstanceLogger;
	@NonNull
	final ExternalMappingsHolder externalMappingsHolder;
	@NonNull
	final JsonMetasfreshId defaultProductCategoryId;

	public ProductUpsertProcessor(
			final @NonNull JsonExternalSystemRequest externalSystemRequest,
			final @NonNull PInstanceLogger pInstanceLogger,
			final @NonNull ExternalMappingsHolder externalMappingsHolder,
			final @NonNull JsonMetasfreshId defaultProductCategoryId)
	{
		this.externalSystemRequest = externalSystemRequest;
		this.pInstanceLogger = pInstanceLogger;
		this.externalMappingsHolder = externalMappingsHolder;
		this.defaultProductCategoryId = defaultProductCategoryId;
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
		if (skipProduct(product))
		{
			return Optional.empty();
		}

		final String productIdentifier = formatExternalId(product.getMaterialCode());

		return mapToJsonRequestProduct(product)
				.map(jsonRequestProduct -> JsonRequestProductUpsertItem.builder()
						.productIdentifier(productIdentifier)
						.requestProduct(jsonRequestProduct)
						.externalSystemConfigId(externalSystemRequest.getExternalSystemConfigId())
						.build());
	}

	@NonNull
	private Optional<JsonRequestProduct> mapToJsonRequestProduct(@NonNull final ProductRow product)
	{
		String resolvedUOMValue = externalMappingsHolder.resolveUOMValue(product.getUom()).orElse(null);
		if (resolvedUOMValue == null)
		{
			pInstanceLogger.logMessage("Warning: missing mapping for provided uom code: " + product.getUom() +
											   "! MaterialCode =" + product.getMaterialCode());

			resolvedUOMValue = product.getUom();
		}

		boolean skipProduct = false;

		final String resolvedProductTypeValue = externalMappingsHolder.resolveProductTypeValue(product.getMaterialGroup()).orElse(null);
		if (resolvedProductTypeValue == null)
		{
			pInstanceLogger.logMessage("Skipping row due to missing mapping for provided product type code: " + product.getMaterialGroup() +
											   "! MaterialCode =" + product.getMaterialCode());
			skipProduct = true;
		}

		if (skipProduct)
		{
			return Optional.empty();
		}

		final JsonRequestProduct jsonRequestProduct = new JsonRequestProduct();

		jsonRequestProduct.setName(product.getName());
		jsonRequestProduct.setCode(product.getMaterialCode() + " (" + product.getSectionCode() + ")");
		jsonRequestProduct.setSectionCode(product.getSectionCode());
		jsonRequestProduct.setStocked(true);
		jsonRequestProduct.setDiscontinued(false);

		jsonRequestProduct.setUomCode(resolvedUOMValue);
		jsonRequestProduct.setType(JsonRequestProduct.Type.ofCode(resolvedProductTypeValue));

		final JsonMetasfreshId resolvedProductCategoryId = resolveProductCategoryId(product);
		jsonRequestProduct.setProductCategoryIdentifier(String.valueOf(resolvedProductCategoryId.getValue()));

		jsonRequestProduct.setPurchased(true);
		jsonRequestProduct.setSAPProductHierarchy(product.getProductHierarchy());

		final JsonRequestProductWarehouseAssignmentSave jsonRequestProductWarehouseAssignmentCreate = JsonRequestProductWarehouseAssignmentSave.builder()
				.warehouseIdentifiers(Optional.ofNullable(product.getWarehouseName())
											  .filter(Check::isNotBlank)
											  .map(ImmutableList::of)
											  .orElseGet(ImmutableList::of))
				.syncAdvise(SyncAdvise.REPLACE)
				.build();

		jsonRequestProduct.setWarehouseAssignments(jsonRequestProductWarehouseAssignmentCreate);

		return Optional.of(jsonRequestProduct);
	}

	private boolean skipProduct(@NonNull final ProductRow product)
	{
		boolean skipProduct = false;
		if (Check.isBlank(product.getName()))
		{
			pInstanceLogger.logMessage("Skipping row due to missing product name! MaterialCode =" + product.getMaterialCode());

			skipProduct = true;
		}

		if (Check.isBlank(product.getUom()))
		{
			pInstanceLogger.logMessage("Skipping row due to missing uom code! MaterialCode =" + product.getMaterialCode());
			skipProduct = true;
		}

		if (Check.isBlank(product.getMaterialGroup()))
		{
			pInstanceLogger.logMessage("Skipping row due to missing product type! MaterialCode =" + product.getMaterialCode());
			skipProduct = true;
		}

		if (Check.isBlank(product.getMaterialType()))
		{
			pInstanceLogger.logMessage("Skipping row due to missing product category! MaterialCode =" + product.getMaterialCode());
			skipProduct = true;
		}

		return skipProduct;
	}

	@NonNull
	private JsonMetasfreshId resolveProductCategoryId(@NonNull final ProductRow productRow)
	{
		if (!externalMappingsHolder.hasProductCategoryMappings())
		{
			return defaultProductCategoryId;
		}

		final Optional<JsonMetasfreshId> productCategoryIdFromMaterialType = externalMappingsHolder.resolveProductCategoryId(productRow.getMaterialType());
		if (productCategoryIdFromMaterialType.isPresent())
		{
			return productCategoryIdFromMaterialType.get();
		}

		final boolean checkDescriptionForMaterialType = Boolean.parseBoolean(externalSystemRequest.getParameter(ExternalSystemConstants.PARAM_CHECK_DESCRIPTION_FOR_MATERIAL_TYPE));
		if (checkDescriptionForMaterialType)
		{
			return externalMappingsHolder.resolveProductCategoryIdByMatchingValue(productRow.getDescription())
					.orElse(defaultProductCategoryId);
		}

		return defaultProductCategoryId;
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
