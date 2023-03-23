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

package de.metas.camel.externalsystems.grssignum.from_grs.bom.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.bom.JsonBOMUtil;
import de.metas.camel.externalsystems.grssignum.from_grs.bom.PushBOMsRouteContext;
import de.metas.camel.externalsystems.grssignum.from_grs.helper.JsonRequestHelper;
import de.metas.camel.externalsystems.grssignum.to_grs.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBOM;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBOMAdditionalInfo;
import de.metas.common.product.v2.JsonQualityAttribute;
import de.metas.common.product.v2.request.JsonRequestAllergenItem;
import de.metas.common.product.v2.request.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.product.v2.request.JsonRequestUpsertProductAllergen;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXCLUSION_FROM_PURCHASE_REASON;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXCLUSION_FROM_SALES_REASON;

public class PushProductProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonBOM jsonBOM = exchange.getIn().getBody(JsonBOM.class);

		final PushBOMsRouteContext context = PushBOMsRouteContext.builder()
				.jsonBOM(jsonBOM)
				.build();

		exchange.setProperty(GRSSignumConstants.ROUTE_PROPERTY_PUSH_BOMs_CONTEXT, context);

		final ProductUpsertCamelRequest productUpsertCamelRequest = getProductUpsertCamelRequest(jsonBOM);

		exchange.getIn().setBody(productUpsertCamelRequest, ProductUpsertCamelRequest.class);
	}

	@NonNull
	private ProductUpsertCamelRequest getProductUpsertCamelRequest(@NonNull final JsonBOM jsonBOM)
	{

		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		final JsonRequestProduct requestProduct = new JsonRequestProduct();

		requestProduct.setCode(jsonBOM.getProductValue());
		requestProduct.setActive(jsonBOM.isActive());
		requestProduct.setType(JsonRequestProduct.Type.ITEM);
		requestProduct.setName(JsonBOMUtil.getName(jsonBOM));
		requestProduct.setUomCode(GRSSignumConstants.DEFAULT_UOM_CODE);
		requestProduct.setGtin(jsonBOM.getGtin());

		requestProduct.setBpartnerProductItems(ImmutableList.of(getBPartnerProductUpsertRequest(jsonBOM)));
		requestProduct.setProductAllergens(getUpsertAllergenRequest(jsonBOM));

		final ImmutableList.Builder<JsonQualityAttribute> qualityAttributeLabelBuilder = JsonRequestHelper.initQualityAttributeLabelList(jsonBOM);

		getSingleAdditionalInfo(jsonBOM)
				.ifPresent(additionalInfo -> {
					requestProduct.setGuaranteeMonths(additionalInfo.getGuaranteeMonthsAsString());
					requestProduct.setWarehouseTemperature(additionalInfo.getWarehouseTemperature());

					Optional.ofNullable(additionalInfo.getGtin()).ifPresent(requestProduct::setGtin);
					Optional.ofNullable(additionalInfo.getAgricultureOrigin())
							.filter(Check::isNotBlank)
							.map(JsonRequestHelper::getQualityLabelForAgricultureOrigin)
							.ifPresent(qualityAttributeLabelBuilder::add);
				});

		requestProduct.setQualityAttributes(JsonRequestHelper.getQualityAttributeRequest(qualityAttributeLabelBuilder.build()));

		final JsonRequestProductUpsertItem productUpsertItem = JsonRequestProductUpsertItem.builder()
				.productIdentifier(ExternalIdentifierFormat.asExternalIdentifier(jsonBOM.getProductId()))
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
	private JsonRequestBPartnerProductUpsert getBPartnerProductUpsertRequest(@NonNull final JsonBOM jsonBOM)
	{
		if (Check.isBlank(jsonBOM.getBPartnerMetasfreshId()))
		{
			throw new RuntimeException("Missing mandatory METASFRESHID! JsonBOM.ARTNRID=" + jsonBOM.getProductId());
		}

		final JsonRequestBPartnerProductUpsert upsertRequest = new JsonRequestBPartnerProductUpsert();

		upsertRequest.setBpartnerIdentifier(jsonBOM.getBPartnerMetasfreshId());
		upsertRequest.setActive(jsonBOM.isActive());

		upsertRequest.setExcludedFromSales(!jsonBOM.isActive());
		upsertRequest.setExclusionFromSalesReason(upsertRequest.getExcludedFromSales() ? EXCLUSION_FROM_SALES_REASON : null);

		upsertRequest.setExcludedFromPurchase(!jsonBOM.isActive());
		upsertRequest.setExclusionFromPurchaseReason(upsertRequest.getExcludedFromPurchase() ? EXCLUSION_FROM_PURCHASE_REASON : null);

		return upsertRequest;
	}

	@NonNull
	private JsonRequestUpsertProductAllergen getUpsertAllergenRequest(@NonNull final JsonBOM jsonBOM)
	{
		if (jsonBOM.getAllergens() == null)
		{
			return JsonRequestHelper.getAllergenUpsertRequest(ImmutableList.of());
		}

		final List<JsonRequestAllergenItem> allergenItemList = jsonBOM.getAllergens()
				.stream()
				.map(allergen -> JsonRequestAllergenItem.builder()
						.identifier(ExternalIdentifierFormat.asExternalIdentifier(String.valueOf(allergen.getId())))
						.name(allergen.getName())
						.build())
				.collect(ImmutableList.toImmutableList());

		return JsonRequestHelper.getAllergenUpsertRequest(allergenItemList);
	}

	@NonNull
	private static Optional<JsonBOMAdditionalInfo> getSingleAdditionalInfo(@NonNull final JsonBOM bom)
	{
		return Optional.ofNullable(bom.getAdditionalInfos())
				.filter(additionalInfoList -> additionalInfoList.size() == 1)
				.map(singleElementList -> singleElementList.get(0));
	}
}
