/*
 * #%L
 * de-metas-camel-ebay-camelroutes
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

package de.metas.camel.externalsystems.ebay.processor.product;

import de.metas.camel.externalsystems.ebay.EbayConstants;
import de.metas.camel.externalsystems.ebay.EbayUtils;
import de.metas.camel.externalsystems.ebay.api.model.LineItem;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
public class ProductUpsertRequestProducer
{
	@NonNull
	String orgCode;

	@NonNull
	List<LineItem> articles;

	@NonNull
	ProductRequestProducerResult.ProductRequestProducerResultBuilder resultBuilder;

	@Builder
	public ProductUpsertRequestProducer(
			@NonNull final String orgCode,
			@NonNull final List<LineItem> articles)
	{
		this.orgCode = orgCode;
		this.articles = articles;
		this.resultBuilder = ProductRequestProducerResult.builder();
	}

	@NonNull
	public Optional<ProductRequestProducerResult> run()
	{

		if(articles.isEmpty())
		{
			return Optional.empty();
		}

		final JsonRequestProductUpsert jsonRequestProductUpsert = JsonRequestProductUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(getProductItems())
				.build();

		resultBuilder.jsonRequestProductUpsert(jsonRequestProductUpsert);

		return Optional.of(resultBuilder.build());
	}

	@NonNull
	private List<JsonRequestProductUpsertItem> getProductItems()
	{
		return articles.stream()
				.map(this::mapArticleToProductRequestItem)
				.collect(Collectors.toList());
	}

	@NonNull
	private JsonRequestProductUpsertItem mapArticleToProductRequestItem(@NonNull final LineItem article)
	{
		final String externalIdentifier = EbayUtils.formatExternalId(article.getSku());
		final JsonRequestProduct jsonRequestProduct = new JsonRequestProduct();
		

		jsonRequestProduct.setCode(article.getSku());
		jsonRequestProduct.setName(article.getTitle());
		jsonRequestProduct.setType(JsonRequestProduct.Type.ITEM);
		jsonRequestProduct.setUomCode(EbayConstants.DEFAULT_PRODUCT_UOM);

		return JsonRequestProductUpsertItem.builder()
				.productIdentifier(externalIdentifier)
				.requestProduct(jsonRequestProduct)
				.build();
	}
}
