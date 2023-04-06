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

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.helper.JsonRequestHelper;
import de.metas.camel.externalsystems.grssignum.from_grs.product.PushRawMaterialsRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonAllergen;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBPartnerProduct;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonProduct;
import de.metas.common.product.v2.request.JsonRequestAllergenItem;
import de.metas.common.product.v2.request.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.product.v2.request.JsonRequestUpsertProductAllergen;
import de.metas.common.product.v2.request.JsonRequestUpsertQualityAttribute;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXCLUSION_FROM_PURCHASE_REASON;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXCLUSION_FROM_SALES_REASON;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT;

public class PushRawMaterialsProcessor implements Processor
{
	@NonNull
	private final ProcessLogger processLogger;

	public PushRawMaterialsProcessor(final @NonNull ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

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

		getAllergens(grsJsonProduct).ifPresent(requestProduct::setProductAllergens);
		requestProduct.setQualityAttributes(getQualityAttributes(grsJsonProduct));

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
	private Optional<JsonRequestUpsertProductAllergen> getAllergens(@NonNull final JsonProduct jsonProduct)
	{
		if (jsonProduct.getBPartnerProducts() == null || jsonProduct.getBPartnerProducts().size() != 1)
		{
			processLogger.logMessage("Skipping Allergen import due to multiple 'KRED' entries received! ARTNRID (GRS-ProductId) = " + jsonProduct.getProductId(), getPInstanceIdFromLoggedInIdentity().getValue());

			return Optional.empty();
		}

		final List<JsonAllergen> allergenList = jsonProduct.getBPartnerProducts().get(0).getAllergens();

		if (allergenList == null)
		{
			return Optional.of(JsonRequestHelper.getAllergenUpsertRequest(ImmutableList.of()));
		}

		final List<JsonRequestAllergenItem> allergenItemList = allergenList
				.stream()
				.map(allergen -> JsonRequestAllergenItem.builder()
						.identifier(ExternalIdentifierFormat.asExternalIdentifier(String.valueOf(allergen.getId())))
						.name(allergen.getName())
						.build())
				.collect(ImmutableList.toImmutableList());

		return Optional.of(JsonRequestHelper.getAllergenUpsertRequest(allergenItemList));
	}

	@NonNull
	private static JsonRequestBPartnerProductUpsert getJsonRequestBPartnerProductUpsert(@NonNull final JsonBPartnerProduct grsBPartnerProductItem)
	{
		final JsonRequestBPartnerProductUpsert upsertRequest = new JsonRequestBPartnerProductUpsert();

		upsertRequest.setBpartnerIdentifier(computeBPartnerIdentifier(grsBPartnerProductItem));
		upsertRequest.setUsedForVendor(true);
		upsertRequest.setCurrentVendor(grsBPartnerProductItem.isCurrentVendor());
		upsertRequest.setActive(grsBPartnerProductItem.isActive());

		upsertRequest.setExcludedFromPurchase(grsBPartnerProductItem.isExcludedFromPurchase() || !grsBPartnerProductItem.isActive());
		upsertRequest.setExclusionFromPurchaseReason(upsertRequest.getExcludedFromPurchase() ? EXCLUSION_FROM_PURCHASE_REASON : null);

		upsertRequest.setExcludedFromSales(!grsBPartnerProductItem.isActive());
		upsertRequest.setExclusionFromSalesReason(upsertRequest.getExcludedFromSales() ? EXCLUSION_FROM_SALES_REASON : null);

		return upsertRequest;
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

	@NonNull
	private static JsonMetasfreshId getPInstanceIdFromLoggedInIdentity()
	{
		try
		{
			return ((TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials()).getPInstance();
		}
		catch (final Throwable e)
		{
			throw new RuntimeException("Cannot get the token credentials from the authenticated user! See message" + e.getMessage(), e);
		}
	}

	@NonNull
	private static JsonRequestUpsertQualityAttribute getQualityAttributes(@NonNull final JsonProduct jsonProduct)
	{
		return JsonRequestHelper.getQualityAttributeRequest(
				JsonRequestHelper.initQualityAttributeLabelList(jsonProduct).build());
	}
}
