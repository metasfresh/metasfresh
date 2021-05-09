/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.product.processor;

import de.metas.camel.externalsystems.alberta.product.UpsertArticleRequest;
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.client.model.ArticleMapping;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.alberta.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.alberta.product.PushProductsRouteConstants.ALBERTA_EXTERNAL_REFERENCE_SYSTEM;
import static de.metas.camel.externalsystems.alberta.product.PushProductsRouteConstants.PRODUCT_EXTERNAL_REFERENCE_TYPE;
import static de.metas.camel.externalsystems.alberta.product.PushProductsRouteConstants.ROUTE_PROPERTY_ALBERTA_PRODUCT_API;

public class PushArticlesProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final AlbertaProductApi albertaProductApi = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_ALBERTA_PRODUCT_API, AlbertaProductApi.class);

		final UpsertArticleRequest upsertArticleRequest = exchange.getIn().getBody(UpsertArticleRequest.class);

		if (upsertArticleRequest == null)
		{
			exchange.getIn().setBody(null);
			return; //nothing more to do
		}

		final ArticleMapping articleMapping = upsertArticleRequest.isFirstExport()
				? albertaProductApi.addProductFallbackUpdate(upsertArticleRequest.getArticle())
				: albertaProductApi.updateProductFallbackAdd(upsertArticleRequest.getArticle());

		final JsonRequestExternalReferenceUpsert externalReferenceUpsert =
				buildUpsertExternalRefRequest(articleMapping, upsertArticleRequest.getProductId());

		exchange.getIn().setBody(externalReferenceUpsert);
	}

	@NonNull
	private JsonRequestExternalReferenceUpsert buildUpsertExternalRefRequest(
			@NonNull final ArticleMapping articleMapping,
			@NonNull final JsonMetasfreshId productId)
	{
		return JsonRequestExternalReferenceUpsert.builder()
				.systemName(JsonExternalSystemName.of(ALBERTA_EXTERNAL_REFERENCE_SYSTEM))
				.externalReferenceItem(JsonExternalReferenceItem.builder()
											   .metasfreshId(productId)
											   .lookupItem(JsonExternalReferenceLookupItem.builder()
																   .type(PRODUCT_EXTERNAL_REFERENCE_TYPE)
																   .id(articleMapping.getId().toString())
																   .build())
											   .build())
				.build();
	}

}
