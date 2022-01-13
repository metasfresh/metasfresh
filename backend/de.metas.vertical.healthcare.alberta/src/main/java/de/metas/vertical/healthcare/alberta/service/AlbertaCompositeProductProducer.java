/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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

package de.metas.vertical.healthcare.alberta.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaArticle;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaBillableTherapy;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaPackagingUnit;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaTherapy;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_M_ProductPrice;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Value
@Builder
public class AlbertaCompositeProductProducer
{
	@NonNull
	Map<ProductId, List<I_M_Product_AlbertaTherapy>> product2Therapies;

	@NonNull
	Map<ProductId, List<I_M_Product_AlbertaBillableTherapy>> product2BillableTherapies;

	@NonNull
	Map<ProductId, List<I_M_Product_AlbertaPackagingUnit>> product2PackagingUnits;

	@NonNull
	Map<ProductId, I_M_Product_AlbertaArticle> product2AlbertaArticle;

	@NonNull
	Map<ProductId, I_M_ProductPrice> productId2ProductPrice;

	@NonNull
	Function<ProductId, Optional<String>> getAlbertaArticleIdSupplier;

	@NonNull
	Function<ProductId, Optional<String>> getProductGroupIdentifierSupplier;

	@NonNull
	public ImmutableMap<ProductId, AlbertaCompositeProductInfo> getAlbertaInfoByProductId()
	{
		return getProductIdSet()
				.stream()
				.map(this::getProductCompositeInfoFor)
				.collect(ImmutableMap.toImmutableMap(
						AlbertaCompositeProductInfo::getProductId,
						Function.identity()));
	}

	@NonNull
	private AlbertaCompositeProductInfo getProductCompositeInfoFor(@NonNull final ProductId productId)
	{
		final AlbertaCompositeProductInfo.AlbertaCompositeProductInfoBuilder compositeProductBuilder = AlbertaCompositeProductInfo.builder();
		compositeProductBuilder
				.productId(productId)
				.albertaArticleId(getAlbertaArticleIdSupplier.apply(productId).orElse(null));

		compositeProductBuilder
				.productId(productId)
				.productGroupId(getProductGroupIdentifierSupplier.apply(productId).orElse(null));

		final Optional<List<I_M_Product_AlbertaPackagingUnit>> packagingUnitRecords = Optional.ofNullable(product2PackagingUnits.get(productId));
		packagingUnitRecords.map(records -> records.stream()
				.map(this::recordToPackingUnit)
				.collect(ImmutableList.toImmutableList()))
				.ifPresent(compositeProductBuilder::albertaPackagingUnitList);

		final Optional<List<I_M_Product_AlbertaTherapy>> therapyRecords = Optional.ofNullable(product2Therapies.get(productId));
		therapyRecords.map(records -> records
				.stream()
				.map(I_M_Product_AlbertaTherapy::getTherapy)
				.collect(ImmutableList.toImmutableList()))
				.ifPresent(compositeProductBuilder::therapyIds);

		final Optional<List<I_M_Product_AlbertaBillableTherapy>> billableTherapyRecords = Optional.ofNullable(product2BillableTherapies.get(productId));
		billableTherapyRecords.map(records -> records
				.stream()
				.map(I_M_Product_AlbertaBillableTherapy::getTherapy)
				.collect(ImmutableList.toImmutableList()))
				.ifPresent(compositeProductBuilder::billableTherapyIds);

		final Optional<I_M_Product_AlbertaArticle> albertaArticle = Optional.ofNullable(product2AlbertaArticle.get(productId));
		albertaArticle.ifPresent(article -> compositeProductBuilder
				.additionalDescription(article.getAdditionalDescription())
				.assortmentType(article.getAssortmentType())
				.inventoryType(article.getArticleInventoryType())
				.medicalAidPositionNumber(article.getMedicalAidPositionNumber())
				.purchaseRating(article.getPurchaseRating())
				.size(article.getSize())
				.stars(article.getArticleStars())
				.status(article.getArticleStatus()));

		final Optional<I_M_ProductPrice> productPrice = Optional.ofNullable(productId2ProductPrice.get(productId));
		productPrice.ifPresent(price -> compositeProductBuilder
						.pharmacyPrice(price.getPriceStd())
						.fixedPrice(price.getPriceList()));

		final Instant mostRecentDate = getLastUpdate(packagingUnitRecords.orElseGet(ImmutableList::of),
													 therapyRecords.orElseGet(ImmutableList::of),
													 billableTherapyRecords.orElseGet(ImmutableList::of),
													 albertaArticle.orElse(null),
													 productPrice.orElse(null));
		compositeProductBuilder.lastUpdated(mostRecentDate);

		return compositeProductBuilder.build();
	}

	@NonNull
	private Instant getLastUpdate(
			@NonNull final List<I_M_Product_AlbertaPackagingUnit> packagingUnitList,
			@NonNull final List<I_M_Product_AlbertaTherapy> therapies,
			@NonNull final List<I_M_Product_AlbertaBillableTherapy> billableTherapies,
			@Nullable final I_M_Product_AlbertaArticle albertaArticle,
			@Nullable final I_M_ProductPrice productPrice)
	{
		final List<Instant> updateTimestamps = new ArrayList<>();

		packagingUnitList.stream()
				.map(I_M_Product_AlbertaPackagingUnit::getUpdated)
				.map(Timestamp::toInstant)
				.max(Comparator.comparing(Instant::toEpochMilli))
				.ifPresent(updateTimestamps::add);

		therapies.stream()
				.map(I_M_Product_AlbertaTherapy::getUpdated)
				.map(Timestamp::toInstant)
				.max(Instant::compareTo)
				.ifPresent(updateTimestamps::add);

		billableTherapies.stream()
				.map(I_M_Product_AlbertaBillableTherapy::getUpdated)
				.map(Timestamp::toInstant)
				.max(Instant::compareTo)
				.ifPresent(updateTimestamps::add);

		Optional.ofNullable(albertaArticle)
				.map(I_M_Product_AlbertaArticle::getUpdated)
				.map(Timestamp::toInstant)
				.ifPresent(updateTimestamps::add);

		Optional.ofNullable(productPrice)
				.map(I_M_ProductPrice::getUpdated)
				.map(Timestamp::toInstant)
				.ifPresent(updateTimestamps::add);

		return updateTimestamps
				.stream()
				.max(Instant::compareTo)
				.orElseGet(() -> Instant.ofEpochMilli(0));
	}

	@NonNull
	private AlbertaPackagingUnit recordToPackingUnit(@NonNull final I_M_Product_AlbertaPackagingUnit record)
	{
		return AlbertaPackagingUnit.builder()
				.quantity(record.getQty())
				.unit(record.getArticleUnit())
				.build();
	}

	@NonNull
	private Set<ProductId> getProductIdSet()
	{
		final ImmutableSet.Builder<ProductId> productIds = ImmutableSet.builder();

		product2Therapies.keySet().forEach(productIds::add);
		product2BillableTherapies.keySet().forEach(productIds::add);
		product2PackagingUnits.keySet().forEach(productIds::add);
		product2AlbertaArticle.keySet().forEach(productIds::add);

		return productIds.build();
	}
}
