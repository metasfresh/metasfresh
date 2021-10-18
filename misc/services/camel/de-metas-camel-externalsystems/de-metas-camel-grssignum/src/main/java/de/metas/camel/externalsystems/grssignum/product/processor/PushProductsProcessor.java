/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.product.processor;

import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.grssignum.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.api.model.JsonBPartnerProduct;
import de.metas.camel.externalsystems.grssignum.api.model.JsonProduct;
import de.metas.common.product.v2.request.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.DEFAULT_UOM_CODE;

public class PushProductsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonProduct jsonProduct = exchange.getIn().getBody(JsonProduct.class);

		final ProductUpsertCamelRequest productUpsertCamelRequest = getProductUpsertCamelRequest(jsonProduct);

		exchange.getIn().setBody(productUpsertCamelRequest, ProductUpsertCamelRequest.class);

	}

	@NonNull
	private ProductUpsertCamelRequest getProductUpsertCamelRequest(@NonNull final JsonProduct jsonProduct)
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		final JsonRequestProduct requestProduct = new JsonRequestProduct();

		if (jsonProduct.getBPartnerProducts() != null && !Check.isEmpty(jsonProduct.getBPartnerProducts()))
		{
			final List<JsonRequestBPartnerProductUpsert> bPartnerProductItems = jsonProduct.getBPartnerProducts()
					.stream()
					.map(PushProductsProcessor::getJsonRequestBPartnerProductUpsert)
					.collect(Collectors.toList());

			requestProduct.setBpartnerProductItems(bPartnerProductItems);
		}

		requestProduct.setCode(jsonProduct.getProductValue());
		requestProduct.setActive(jsonProduct.isActive());
		requestProduct.setType(JsonRequestProduct.Type.ITEM);
		requestProduct.setName(getName(jsonProduct));
		requestProduct.setUomCode(DEFAULT_UOM_CODE);

		final JsonRequestProductUpsertItem productUpsertItem = JsonRequestProductUpsertItem.builder()
				.productIdentifier(ExternalIdentifierFormat.asExternalIdentifier(jsonProduct.getProductId()))
				.requestProduct(requestProduct)
				.build();

		final JsonRequestProductUpsert productUpsert = JsonRequestProductUpsert.builder()
				.requestItem(productUpsertItem)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return ProductUpsertCamelRequest.builder()
				.orgCode(credentials.getOrgCode())
				.jsonRequestProductUpsert(productUpsert)
				.build();
	}

	@NonNull
	private static JsonRequestBPartnerProductUpsert getJsonRequestBPartnerProductUpsert(@NonNull final JsonBPartnerProduct bPartnerProductItem)
	{
		final JsonRequestBPartnerProductUpsert jsonRequestBPartnerProductUpsert = new JsonRequestBPartnerProductUpsert();
		jsonRequestBPartnerProductUpsert.setBpartnerIdentifier(ExternalIdentifierFormat.asExternalIdentifier(bPartnerProductItem.getBpartnerId()));
		jsonRequestBPartnerProductUpsert.setUsedForVendor(true);
		jsonRequestBPartnerProductUpsert.setCurrentVendor(bPartnerProductItem.isCurrentVendor());

		return jsonRequestBPartnerProductUpsert;
	}

	@NonNull
	private static String getName(@NonNull final JsonProduct product)
	{
		final String name = Stream.of(product.getName1(), product.getName2())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" "));

		if (Check.isBlank(name))
		{
			throw new RuntimeException("Missing name for product: " + product.getProductId());
		}

		return name;
	}
}
