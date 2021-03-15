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

package de.metas.camel.alberta.product.processor;

import com.google.common.collect.ImmutableList;
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

import static de.metas.camel.alberta.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.alberta.product.PushProductsRouteConstants.ROUTE_PROPERTY_ALBERTA_PRODUCT_API;

public class PushProductsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final AlbertaProductApi albertaProductApi = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_ALBERTA_PRODUCT_API, AlbertaProductApi.class);

		final JsonProduct jsonProduct = exchange.getIn().getBody(JsonProduct.class);

		jsonProductToArticle(jsonProduct)
				.ifPresent(albertaProductApi::upsertProduct);
	}

	private Optional<Article> jsonProductToArticle(@NonNull final JsonProduct product)
	{
		if (product.getAlbertaProductInfo() == null || CollectionUtils.isEmpty(product.getAlbertaProductInfo().getPackagingUnits()))
		{
			return Optional.empty();
		}

		final String manufacturerId = product.getManufacturerId() != null
				? String.valueOf(product.getManufacturerId().getValue())
				: null;

		final Article article = new Article();
		article.customerNumber(product.getProductNo())
				.name(product.getName())
				.description(product.getDescription())
				.manufacturer(manufacturerId)
				.manufacturerNumber(product.getManufacturerNumber());

		final JsonAlbertaProductInfo albertaProductInfo = product.getAlbertaProductInfo();

		article.additionalDescription(albertaProductInfo.getAdditionalDescription())
				.size(albertaProductInfo.getSize())
				.inventoryType(albertaProductInfo.getInventoryType() != null ? new BigDecimal(albertaProductInfo.getInventoryType()): null)
				.status(albertaProductInfo.getStatus() != null ? new BigDecimal(albertaProductInfo.getStatus()) : null)
				.medicalAidPositionNumber(albertaProductInfo.getMedicalAidPositionNumber())
				.stars(albertaProductInfo.getStars())
				.assortmentType(albertaProductInfo.getAssortmentType() != null ? new BigDecimal(albertaProductInfo.getAssortmentType()) : null)
				.pharmacyPrice(albertaProductInfo.getPharmacyPrice() != null ? String.valueOf(albertaProductInfo.getPharmacyPrice()) : null)
				.fixedPrice(albertaProductInfo.getFixedPrice() != null ? String.valueOf(albertaProductInfo.getFixedPrice()) : null)
				.purchaseRating(albertaProductInfo.getPurchaseRating() != null ? new BigDecimal(albertaProductInfo.getPurchaseRating()) : null)
				.therapyIds(albertaProductInfo.getTherapyIds())
				.billableTherapies(albertaProductInfo.getBillableTherapies())
				.packagingUnits(toPackageUnitList(albertaProductInfo.getPackagingUnits()));

		return Optional.of(article);
	}

	@Nullable
	private List<PackagingUnit> toPackageUnitList(@NonNull final List<JsonAlbertaPackagingUnit> albertaPackagingUnits)
	{
		if (CollectionUtils.isEmpty(albertaPackagingUnits))
		{
			return null;
		}

		return albertaPackagingUnits
				.stream()
				.map(mfPackageUnit -> {
					final PackagingUnit packagingUnit = new PackagingUnit();

					return packagingUnit.quantity(mfPackageUnit.getQuantity())
							.unit(mfPackageUnit.getUnit());
				})
				.collect(ImmutableList.toImmutableList());
	}
}
