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

package de.metas.camel.externalsystems.grssignum.bom.processor;

import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.grssignum.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.api.model.JsonBOM;
import de.metas.camel.externalsystems.grssignum.bom.JsonBOMUtil;
import de.metas.camel.externalsystems.grssignum.bom.PushBOMsRouteContext;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.core.context.SecurityContextHolder;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.DEFAULT_UOM_CODE;

public class PushProductProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
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
	private ProductUpsertCamelRequest getProductUpsertCamelRequest(@NonNull final JsonBOM jsonBOM){

		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		final JsonRequestProduct requestProduct = new JsonRequestProduct();

		requestProduct.setCode(jsonBOM.getProductValue());
		requestProduct.setActive(jsonBOM.isActive());
		requestProduct.setType(JsonRequestProduct.Type.ITEM);
		requestProduct.setName(JsonBOMUtil.getName(jsonBOM));
		requestProduct.setUomCode(DEFAULT_UOM_CODE);
		requestProduct.setGtin(jsonBOM.getGtin());

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
}
