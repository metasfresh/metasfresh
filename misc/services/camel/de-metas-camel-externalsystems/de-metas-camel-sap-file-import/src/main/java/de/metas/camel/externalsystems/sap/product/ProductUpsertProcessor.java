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
import de.metas.camel.externalsystems.sap.model.product.ProductRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
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
		if (product.getName() == null)
		{
			processLogger.logMessage("Skipping row due to missing product name! MaterialCode=" + product.getMaterialCode(),
									 JsonMetasfreshId.toValue(externalSystemRequest.getAdPInstanceId()));
			return Optional.empty();
		}

		if (product.getUom() == null)
		{
			processLogger.logMessage("Skipping row due to missing uom code! MaterialCode=" + product.getMaterialCode(),
									 JsonMetasfreshId.toValue(externalSystemRequest.getAdPInstanceId()));
			return Optional.empty();
		}

		final String productIdentifier = formatExternalId(product.getMaterialCode());

		final JsonRequestProduct jsonRequestProduct = new JsonRequestProduct();

		jsonRequestProduct.setName(product.getName());
		jsonRequestProduct.setUomCode(product.getUom());
		jsonRequestProduct.setCode(product.getMaterialCode() + "_" + product.getName());
		jsonRequestProduct.setSectionCode(product.getSectionCode());
		jsonRequestProduct.setType(JsonRequestProduct.Type.ITEM);
		jsonRequestProduct.setStocked(true);
		jsonRequestProduct.setDiscontinued(false);

		return Optional.of(JsonRequestProductUpsertItem.builder()
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
}
