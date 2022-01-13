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

import com.google.common.collect.ImmutableMap;
import de.metas.externalreference.AlbertaExternalSystem;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.GetExternalReferenceByRecordIdReq;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.dao.AlbertaDataQuery;
import de.metas.vertical.healthcare.alberta.dao.AlbertaProductDAO;
import lombok.NonNull;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AlbertaProductService
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	private final ExternalReferenceRepository externalReferenceRepository;
	private final AlbertaProductDAO albertaProductDAO;

	public AlbertaProductService(
			@NonNull final AlbertaProductDAO albertaProductDAO,
			@NonNull final ExternalReferenceRepository externalReferenceRepository)
	{
		this.albertaProductDAO = albertaProductDAO;
		this.externalReferenceRepository = externalReferenceRepository;
	}

	@NonNull
	public ImmutableMap<ProductId, AlbertaCompositeProductInfo> getAlbertaInfoByProductId(@NonNull final GetAlbertaProductsInfoRequest request)
	{
		final AlbertaDataQuery albertaDataQuery = AlbertaDataQuery.builder()
				.productIds(request.getProductIdSet())
				.updatedAfter(request.getSince())
				.build();

		final AlbertaCompositeProductProducer albertaCompositeProductProducer = buildAlbertaCompositeProductProducer(albertaDataQuery, request.getPharmacyPriceListId());

		return albertaCompositeProductProducer.getAlbertaInfoByProductId();
	}

	@NonNull
	private AlbertaCompositeProductProducer buildAlbertaCompositeProductProducer(
			@NonNull final AlbertaDataQuery dataQuery,
			@Nullable final PriceListId pharmacyPriceListId)
	{
		final Map<ProductId, I_M_ProductPrice> productId2ProductPrice = Optional.ofNullable(pharmacyPriceListId)
				.map(priceListId -> priceListDAO.retrievePriceListVersionOrNull(priceListId, ZonedDateTime.now(), null))
				.map(priceListVersion -> PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()))
				.map(priceListVersionId -> priceListDAO.retrieveProductPrices(priceListVersionId)
						.filter(productPrice -> !productPrice.isAttributeDependant())
						.collect(ImmutableMap.toImmutableMap(
								productPrice -> ProductId.ofRepoId(productPrice.getM_Product_ID()),
								Function.identity()
						)))
				.orElse(ImmutableMap.of());

		return AlbertaCompositeProductProducer.builder()
				.productId2ProductPrice(productId2ProductPrice)
				.product2AlbertaArticle(albertaProductDAO.getAlbertaArticles(dataQuery))
				.product2BillableTherapies(albertaProductDAO.getBillableTherapies(dataQuery))
				.product2Therapies(albertaProductDAO.getTherapies(dataQuery))
				.product2PackagingUnits(albertaProductDAO.getPackagingUnits(dataQuery))
				.getAlbertaArticleIdSupplier(this::getAlbertaArticleIdByProductId)
				.getProductGroupIdentifierSupplier(this::getProductGroupIdentifierByProductId)
				.build();
	}

	@NonNull
	private Optional<String> getProductGroupIdentifierByProductId(@NonNull final ProductId productId)
	{
		final I_M_Product product = productDAO.getById(productId);

		final GetExternalReferenceByRecordIdReq getExternalReferenceByRecordIdReq = GetExternalReferenceByRecordIdReq.builder()
				.recordId(product.getM_Product_Category_ID())
				.externalSystem(AlbertaExternalSystem.ALBERTA)
				.externalReferenceType(ProductCategoryExternalReferenceType.PRODUCT_CATEGORY)
				.build();

		return externalReferenceRepository.getExternalReferenceByMFReference(getExternalReferenceByRecordIdReq)
				.map(ExternalReference::getExternalReference);
	}

	@NonNull
	private Optional<String> getAlbertaArticleIdByProductId(@NonNull final ProductId productId)
	{
		final GetExternalReferenceByRecordIdReq getExternalReferenceByRecordIdReq = GetExternalReferenceByRecordIdReq.builder()
				.recordId(productId.getRepoId())
				.externalSystem(AlbertaExternalSystem.ALBERTA)
				.externalReferenceType(ProductExternalReferenceType.PRODUCT)
				.build();

		return externalReferenceRepository.getExternalReferenceByMFReference(getExternalReferenceByRecordIdReq)
				.map(ExternalReference::getExternalReference);
	}
}
