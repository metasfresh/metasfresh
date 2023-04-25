/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.product.processor;

import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.product.PushRawMaterialsRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBPartnerProduct;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonProduct;
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

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXCLUSION_FROM_PURCHASE_REASON;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT;

public class PushRawMaterialsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final PushRawMaterialsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																							 ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT,
																							 PushRawMaterialsRouteContext.class);

		final JsonProduct jsonProduct = context.getJsonProduct();

		final ProductUpsertCamelRequest productUpsertCamelRequest = getProductUpsertCamelRequest(jsonProduct);

		exchange.getIn().setBody(productUpsertCamelRequest, ProductUpsertCamelRequest.class);

	}

	@NonNull
	private ProductUpsertCamelRequest getProductUpsertCamelRequest(@NonNull final JsonProduct grsJsonProduct)
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		final JsonRequestProduct requestProduct = new JsonRequestProduct();

		if (grsJsonProduct.getBPartnerProducts() != null && !Check.isEmpty(grsJsonProduct.getBPartnerProducts()))
		{
			final List<JsonRequestBPartnerProductUpsert> bPartnerProductItems = grsJsonProduct.getBPartnerProducts()
					.stream()
					.map(PushRawMaterialsProcessor::getJsonRequestBPartnerProductUpsert)
					.collect(Collectors.toList());

			requestProduct.setBpartnerProductItems(bPartnerProductItems);
		}

		requestProduct.setCode(grsJsonProduct.getProductValue());
		requestProduct.setActive(grsJsonProduct.isActive());
		requestProduct.setType(JsonRequestProduct.Type.ITEM);
		requestProduct.setName(getName(grsJsonProduct));
		requestProduct.setUomCode(GRSSignumConstants.DEFAULT_UOM_CODE);

		final JsonRequestProductUpsertItem productUpsertItem = JsonRequestProductUpsertItem.builder()
				.productIdentifier(ExternalIdentifierFormat.asExternalIdentifier(grsJsonProduct.getProductId()))
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
	private static JsonRequestBPartnerProductUpsert getJsonRequestBPartnerProductUpsert(@NonNull final JsonBPartnerProduct grsBPartnerProductItem)
	{
		final JsonRequestBPartnerProductUpsert jsonRequestBPartnerProductUpsert = new JsonRequestBPartnerProductUpsert();

		jsonRequestBPartnerProductUpsert.setBpartnerIdentifier(computeBPartnerIdentifier(grsBPartnerProductItem));
		jsonRequestBPartnerProductUpsert.setUsedForVendor(true);
		jsonRequestBPartnerProductUpsert.setCurrentVendor(grsBPartnerProductItem.isCurrentVendor());
		jsonRequestBPartnerProductUpsert.setExcludedFromPurchase(grsBPartnerProductItem.isExcludedFromPurchase());
		jsonRequestBPartnerProductUpsert.setExclusionFromPurchaseReason(grsBPartnerProductItem.isExcludedFromPurchase() ? EXCLUSION_FROM_PURCHASE_REASON : null);
		jsonRequestBPartnerProductUpsert.setActive(grsBPartnerProductItem.isActive());

		return jsonRequestBPartnerProductUpsert;
	}

	@NonNull
	private static String getName(@NonNull final JsonProduct grsProduct)
	{
		final String name = Stream.of(grsProduct.getName1(), grsProduct.getName2())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" "));

		if (Check.isBlank(name))
		{
			throw new RuntimeException("Missing name for product: " + grsProduct.getProductId());
		}

		return name;
	}

	@NonNull
	private static String computeBPartnerIdentifier(@NonNull final JsonBPartnerProduct jsonBPartnerProduct)
	{
		if (jsonBPartnerProduct.getBPartnerMetasfreshId() != null && Check.isNotBlank(jsonBPartnerProduct.getBPartnerMetasfreshId()))
		{
			return jsonBPartnerProduct.getBPartnerMetasfreshId();
		}

		throw new RuntimeException("Missing mandatory METASFRESHID! see JsonBPartnerProduct: " + jsonBPartnerProduct);
	}
}
