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

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.alberta.common.AlbertaUtil;
import de.metas.camel.externalsystems.alberta.product.PurchaseRatingEnum;
import de.metas.camel.externalsystems.alberta.product.UpsertArticleRequest;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.product.v2.response.alberta.JsonAlbertaPackagingUnit;
import de.metas.common.product.v2.response.alberta.JsonAlbertaProductInfo;
import io.swagger.client.model.Article;
import io.swagger.client.model.PackagingUnit;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static de.metas.camel.externalsystems.alberta.common.AlbertaUtil.fromJavaLocalDate;

public class PrepareAlbertaArticlesProcessor implements Processor
{
	private static final String ARTICLE_PCN_PREFIX = "PZN-";

	@Override
	public void process(final Exchange exchange)
	{
		final JsonProduct jsonProduct = exchange.getIn().getBody(JsonProduct.class);

		if (jsonProduct == null)
		{
			throw new RuntimeException("No JsonProduct found in the exchange body!");
		}

		final UpsertArticleRequest upsertArticleRequest = jsonProductToUpsertArticle(jsonProduct)
				.orElse(null);

		exchange.getIn().setBody(upsertArticleRequest);
	}

	private Optional<UpsertArticleRequest> jsonProductToUpsertArticle(@NonNull final JsonProduct product)
	{
		if (product.getAlbertaProductInfo() == null || CollectionUtils.isEmpty(product.getAlbertaProductInfo().getPackagingUnits()))
		{
			return Optional.empty();
		}

		final Article article = new Article();

		final String productNo = product.getProductNo();
		final String pcn = productNo.startsWith(ARTICLE_PCN_PREFIX) ? productNo.substring(ARTICLE_PCN_PREFIX.length()) : null;

		article.customerNumber(productNo)
				.name(product.getName())
				.description(product.getDescription())
				.manufacturer(product.getManufacturerName())
				.manufacturerNumber(product.getManufacturerNumber())
				.unavailableFrom(fromJavaLocalDate(product.getDiscontinuedFrom()));

		final JsonAlbertaProductInfo albertaProductInfo = product.getAlbertaProductInfo();

		article.additionalDescription(albertaProductInfo.getAdditionalDescription())
				.size(albertaProductInfo.getSize())
				.purchaseRating(PurchaseRatingEnum.getValueByCodeOrNull(albertaProductInfo.getPurchaseRating()))
				.inventoryType(albertaProductInfo.getInventoryType() != null ? new BigDecimal(albertaProductInfo.getInventoryType()) : null)
				.status(albertaProductInfo.getStatus() != null ? new BigDecimal(albertaProductInfo.getStatus()) : null)
				.medicalAidPositionNumber(albertaProductInfo.getMedicalAidPositionNumber())
				.stars(albertaProductInfo.getStars())
				.assortmentType(albertaProductInfo.getAssortmentType() != null ? new BigDecimal(albertaProductInfo.getAssortmentType()) : null)
				.pharmacyPrice(albertaProductInfo.getPharmacyPrice() != null ? String.valueOf(albertaProductInfo.getPharmacyPrice()) : null)
				.fixedPrice(albertaProductInfo.getFixedPrice() != null ? String.valueOf(albertaProductInfo.getFixedPrice()) : null)
				.therapyIds(AlbertaUtil.asBigDecimalIds(albertaProductInfo.getTherapyIds()))
				.billableTherapies(AlbertaUtil.asBigDecimalIds(albertaProductInfo.getBillableTherapies()))
				.packagingUnits(toPackageUnitList(albertaProductInfo.getPackagingUnits(), pcn))
				.productGroupId(albertaProductInfo.getProductGroupId());

		return Optional.of(UpsertArticleRequest.builder()
								   .article(article)
								   .productId(product.getId())
								   .firstExport(product.getAlbertaProductInfo().getAlbertaProductId() == null)
								   .build());
	}

	@Nullable
	private List<PackagingUnit> toPackageUnitList(
			@NonNull final List<JsonAlbertaPackagingUnit> albertaPackagingUnits,
			@Nullable final String pcn)
	{
		if (CollectionUtils.isEmpty(albertaPackagingUnits))
		{
			return null;
		}

		return albertaPackagingUnits
				.stream()
				.map(mfPackageUnit -> new PackagingUnit()
					.pcn(pcn)
					.quantity(mfPackageUnit.getQuantity())
					.unit(mfPackageUnit.getUnit()))
				.collect(ImmutableList.toImmutableList());
	}
}
