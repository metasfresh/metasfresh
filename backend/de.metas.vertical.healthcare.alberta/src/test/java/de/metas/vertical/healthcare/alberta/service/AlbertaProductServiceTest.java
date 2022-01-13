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

import com.google.common.collect.ImmutableSet;
import de.metas.externalreference.AlbertaExternalSystem;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.dao.AlbertaProductDAO;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaArticle;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaBillableTherapy;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaPackagingUnit;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaTherapy;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class AlbertaProductServiceTest
{
	private AlbertaProductService albertaProductService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final ExternalSystems externalSystems = new ExternalSystems();
		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();

		albertaProductService = new AlbertaProductService(new AlbertaProductDAO(),
														  new ExternalReferenceRepository(Services.get(IQueryBL.class), externalSystems, externalReferenceTypes));
	}

	@Test
	public void getAlbertaInfoByProductId()
	{
		//given
		final ProductId targetProductId = ProductId.ofRepoId(911);
		final ProductCategoryId targetProductCategoryId = ProductCategoryId.ofRepoId(999);

		final PriceListId pharmacyPriceListId = PriceListId.ofRepoId(123);

		final I_M_Product_AlbertaTherapy albertaTherapy = createAlbertaTherapy(targetProductId);
		final I_M_Product_AlbertaBillableTherapy billableTherapy = createAlbertaBillableTherapy(targetProductId);
		final I_M_Product_AlbertaArticle albertaArticle = createAlbertaArticle(targetProductId);
		final I_M_Product_AlbertaPackagingUnit albertaPackagingUnit = createAlbertaPackagingUnit(targetProductId);
		final I_M_PriceList_Version priceListVersion = createPriceListVersion(pharmacyPriceListId);
		final I_M_ProductPrice productPrice = createProductPrice(targetProductId, PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()));
		final I_S_ExternalReference productExternalReference = createExternalReference(targetProductId.getRepoId(), ProductExternalReferenceType.PRODUCT);
		final I_M_Product product = createProduct(targetProductId, targetProductCategoryId);
		final I_S_ExternalReference productCategoryExternalReference = createExternalReference(targetProductCategoryId.getRepoId(),
																							   ProductCategoryExternalReferenceType.PRODUCT_CATEGORY);

		final GetAlbertaProductsInfoRequest getAlbertaProductsInfoRequest = GetAlbertaProductsInfoRequest.builder()
				.productIdSet(ImmutableSet.of(targetProductId))
				.pharmacyPriceListId(pharmacyPriceListId)
				.since(Instant.ofEpochMilli(0))
				.build();

		//when
		final Map<ProductId, AlbertaCompositeProductInfo> albertaCompositeProductInfoMap =
				albertaProductService.getAlbertaInfoByProductId(getAlbertaProductsInfoRequest);

		//then
		final AlbertaCompositeProductInfo compositeProductInfo = albertaCompositeProductInfoMap.get(targetProductId);

		assertThat(compositeProductInfo).isNotNull();
		assertThat(compositeProductInfo.getProductId()).isEqualTo(targetProductId);

		//article
		assertThat(compositeProductInfo.getAdditionalDescription()).isEqualTo(albertaArticle.getAdditionalDescription());
		assertThat(compositeProductInfo.getAssortmentType()).isEqualTo(albertaArticle.getAssortmentType());
		assertThat(compositeProductInfo.getInventoryType()).isEqualTo(albertaArticle.getArticleInventoryType());
		assertThat(compositeProductInfo.getMedicalAidPositionNumber()).isEqualTo(albertaArticle.getMedicalAidPositionNumber());
		assertThat(compositeProductInfo.getPurchaseRating()).isEqualTo(albertaArticle.getPurchaseRating());
		assertThat(compositeProductInfo.getSize()).isEqualTo(albertaArticle.getSize());
		assertThat(compositeProductInfo.getStars()).isEqualTo(albertaArticle.getArticleStars());
		assertThat(compositeProductInfo.getStatus()).isEqualTo(albertaArticle.getArticleStatus());

		//therapy
		assertThat(compositeProductInfo.getTherapyIds().get(0)).isEqualTo(albertaTherapy.getTherapy());

		//billable therapy
		assertThat(compositeProductInfo.getBillableTherapyIds().get(0)).isEqualTo(billableTherapy.getTherapy());

		//packaging units
		assertThat(compositeProductInfo.getAlbertaPackagingUnitList().get(0).getQuantity()).isEqualTo(albertaPackagingUnit.getQty());
		assertThat(compositeProductInfo.getAlbertaPackagingUnitList().get(0).getUnit()).isEqualTo(albertaPackagingUnit.getArticleUnit());

		//price
		assertThat(compositeProductInfo.getPharmacyPrice()).isEqualTo(productPrice.getPriceStd());
		assertThat(compositeProductInfo.getFixedPrice()).isEqualTo(productPrice.getPriceList());

		//external ref
		assertThat(compositeProductInfo.getAlbertaArticleId()).isEqualTo(productExternalReference.getExternalReference());
		assertThat(compositeProductInfo.getProductGroupId()).isEqualTo(productCategoryExternalReference.getExternalReference());

	}

	private I_M_Product createProduct(@NonNull final ProductId productId, @NonNull final ProductCategoryId productCategoryId)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setM_Product_ID(productId.getRepoId());
		product.setM_Product_Category_ID(productCategoryId.getRepoId());

		saveRecord(product);

		return product;
	}

	private I_M_Product_AlbertaTherapy createAlbertaTherapy(@NonNull final ProductId productId)
	{
		final I_M_Product_AlbertaTherapy albertaTherapy = InterfaceWrapperHelper.newInstance(I_M_Product_AlbertaTherapy.class);
		albertaTherapy.setM_Product_ID(productId.getRepoId());
		albertaTherapy.setTherapy("Therapy");

		saveRecord(albertaTherapy);

		return albertaTherapy;
	}

	private I_M_Product_AlbertaBillableTherapy createAlbertaBillableTherapy(@NonNull final ProductId productId)
	{
		final I_M_Product_AlbertaBillableTherapy albertaBillableTherapy = InterfaceWrapperHelper.newInstance(I_M_Product_AlbertaBillableTherapy.class);
		albertaBillableTherapy.setM_Product_ID(productId.getRepoId());
		albertaBillableTherapy.setTherapy("BillableTherapy");

		saveRecord(albertaBillableTherapy);

		return albertaBillableTherapy;
	}

	private I_M_Product_AlbertaPackagingUnit createAlbertaPackagingUnit(@NonNull final ProductId productId)
	{
		final I_M_Product_AlbertaPackagingUnit albertaPackagingUnit = InterfaceWrapperHelper.newInstance(I_M_Product_AlbertaPackagingUnit.class);
		albertaPackagingUnit.setM_Product_ID(productId.getRepoId());
		albertaPackagingUnit.setArticleUnit("ArticleUnit");
		albertaPackagingUnit.setQty(BigDecimal.ONE);

		saveRecord(albertaPackagingUnit);

		return albertaPackagingUnit;
	}

	private I_M_Product_AlbertaArticle createAlbertaArticle(@NonNull final ProductId productId)
	{
		final I_M_Product_AlbertaArticle albertaArticle = InterfaceWrapperHelper.newInstance(I_M_Product_AlbertaArticle.class);
		albertaArticle.setM_Product_ID(productId.getRepoId());
		albertaArticle.setAdditionalDescription("setAdditionalDescription");
		albertaArticle.setAssortmentType("setAssortmentType");
		albertaArticle.setArticleInventoryType("setArticleInventoryType");
		albertaArticle.setArticleStars(BigDecimal.ONE);
		albertaArticle.setArticleStatus("setArticleStatus");
		albertaArticle.setMedicalAidPositionNumber("setMedicalAidPositionNumber");
		albertaArticle.setPurchaseRating("setPurchaseRating");
		albertaArticle.setSize("setSize");

		saveRecord(albertaArticle);

		return albertaArticle;
	}

	private I_M_PriceList_Version createPriceListVersion(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList_Version priceListVersion = newInstance(I_M_PriceList_Version.class);

		priceListVersion.setM_PriceList_ID(priceListId.getRepoId());
		priceListVersion.setValidFrom(Timestamp.from(Instant.now()));

		saveRecord(priceListVersion);

		return priceListVersion;
	}

	private I_M_ProductPrice createProductPrice(@NonNull final ProductId productId, @NonNull final PriceListVersionId priceListVersionId)
	{
		final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);

		productPrice.setM_Product_ID(productId.getRepoId());
		productPrice.setM_PriceList_Version_ID(priceListVersionId.getRepoId());
		productPrice.setPriceList(BigDecimal.TEN);
		productPrice.setPriceStd(BigDecimal.ONE);

		saveRecord(productPrice);

		return productPrice;
	}

	private I_S_ExternalReference createExternalReference(final int recordId,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		final I_S_ExternalReference externalReference = newInstance(I_S_ExternalReference.class);

		externalReference.setRecord_ID(recordId);
		externalReference.setType(externalReferenceType.getCode());
		externalReference.setExternalReference("albertaRecord");
		externalReference.setExternalSystem(AlbertaExternalSystem.ALBERTA.getCode());

		saveRecord(externalReference);

		return externalReference;
	}
}
