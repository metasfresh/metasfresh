/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.product.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.RestUtils;
import de.metas.bpartner.BPartnerId;
import de.metas.common.product.v2.response.JsonGetProductsResponse;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.product.v2.response.JsonProductBPartner;
import de.metas.common.product.v2.response.alberta.JsonAlbertaPackagingUnit;
import de.metas.common.product.v2.response.alberta.JsonAlbertaProductInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfig;
import de.metas.externalsystem.audit.ExternalSystemExportAudit;
import de.metas.i18n.IModelTranslationMap;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.product.ProductId;
import de.metas.rest_api.v2.externlasystem.ExternalSystemService;
import de.metas.rest_api.v2.product.ProductRestService;
import de.metas.rest_api.v2.product.ProductsServicesFacade;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.service.AlbertaCompositeProductInfo;
import de.metas.vertical.healthcare.alberta.service.AlbertaPackagingUnit;
import de.metas.vertical.healthcare.alberta.service.AlbertaProductService;
import de.metas.vertical.healthcare.alberta.service.GetAlbertaProductsInfoRequest;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class GetProductsCommand
{
	private static final Instant DEFAULT_SINCE = Instant.ofEpochMilli(0);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NonNull
	private final ProductsServicesFacade servicesFacade;
	@NonNull
	private final AlbertaProductService albertaProductService;
	@NonNull
	private final ExternalSystemService externalSystemService;
	@NonNull
	private final ProductRestService productRestService;

	@NonNull
	private final String adLanguage;
	@NonNull
	private final Instant since;

	@Nullable
	private final String orgCode;

	private final ExternalSystemType externalSystemType;
	private final String externalSystemConfigValue;
	private final ExternalIdentifier productIdentifier;

	private ImmutableListMultimap<ProductId, JsonProductBPartner> productBPartners;

	private ImmutableMap<JsonMetasfreshId, String> bpartnerId2Name;

	@Nullable
	private ImmutableMap<ProductId, AlbertaCompositeProductInfo> productId2AlbertaInfo;

	@Builder(buildMethodName = "_build")
	private GetProductsCommand(
			@NonNull final ProductsServicesFacade servicesFacade,
			@NonNull final AlbertaProductService albertaProductService,
			@NonNull final ExternalSystemService externalSystemService,
			@NonNull final ProductRestService productRestService,
			@NonNull final String adLanguage,
			@Nullable final Instant since,
			@Nullable final String orgCode,
			@Nullable final ExternalSystemType externalSystemType,
			@Nullable final String externalSystemConfigValue,
			@Nullable final ExternalIdentifier productIdentifier)
	{
		this.servicesFacade = servicesFacade;
		this.albertaProductService = albertaProductService;
		this.externalSystemService = externalSystemService;
		this.productRestService = productRestService;
		this.adLanguage = adLanguage;
		this.since = CoalesceUtil.coalesceNotNull(since, DEFAULT_SINCE);
		this.orgCode = orgCode;
		this.externalSystemType = externalSystemType;
		this.externalSystemConfigValue = externalSystemConfigValue;
		this.productIdentifier = productIdentifier;
	}

	public static class GetProductsCommandBuilder
	{
		public JsonGetProductsResponse execute()
		{
			return _build().execute();
		}
	}

	@NonNull
	public JsonGetProductsResponse execute()
	{
		final ImmutableList<I_M_Product> productsToExport = getProductsToExport();
		final ImmutableSet<ProductId> productIds = extractProductIds(productsToExport);

		productBPartners = retrieveJsonProductVendors(productIds);

		final ImmutableSet<BPartnerId> manufacturerIds = productsToExport.stream()
				.map(I_M_Product::getManufacturer_ID)
				.map(BPartnerId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		bpartnerId2Name = retrieveBPartnerNames(manufacturerIds);

		final ImmutableList<JsonProduct> products = productsToExport.stream()
				.filter(product -> since.equals(DEFAULT_SINCE) || since.isAfter(DEFAULT_SINCE) || !wasAlreadyExported(product))
				.map(this::toJsonProduct)
				.collect(ImmutableList.toImmutableList());

		return JsonGetProductsResponse.builder()
				.products(products)
				.build();
	}

	@NonNull
	private ImmutableMap<JsonMetasfreshId, String> retrieveBPartnerNames(@NonNull final ImmutableSet<BPartnerId> manufacturerIds)
	{
		return servicesFacade
				.getPartnerRecords(manufacturerIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> JsonMetasfreshId.of(record.getC_BPartner_ID()),
						I_C_BPartner::getName));
	}

	@NonNull
	private static ImmutableSet<ProductId> extractProductIds(@NonNull final Collection<I_M_Product> records)
	{
		return records.stream()
				.mapToInt(I_M_Product::getM_Product_ID)
				.distinct()
				.mapToObj(ProductId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private JsonProduct toJsonProduct(final I_M_Product productRecord)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(productRecord);

		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final JsonMetasfreshId manufacturerId = productRecord.getManufacturer_ID() > 0
				? JsonMetasfreshId.of(productRecord.getManufacturer_ID())
				: null;

		final UomId uomId = UomId.ofRepoId(productRecord.getC_UOM_ID());

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(productRecord.getAD_Org_ID()));

		final LocalDate discontinuedFrom = productRecord.isDiscontinued()
				? TimeUtil.asLocalDate(productRecord.getDiscontinuedFrom(), zoneId)
				: null;

		return JsonProduct.builder()
				.id(JsonMetasfreshId.of(productId.getRepoId()))
				.externalId(productRecord.getExternalId())
				.productNo(productRecord.getValue())
				.productCategoryId(JsonMetasfreshId.of(productRecord.getM_Product_Category_ID()))
				.manufacturerId(manufacturerId)
				.manufacturerName(bpartnerId2Name.get(manufacturerId))
				.manufacturerNumber(productRecord.getManufacturerArticleNumber())
				.name(trls.getColumnTrl(I_M_Product.COLUMNNAME_Name, productRecord.getName()).translate(adLanguage))
				.description(trls.getColumnTrl(I_M_Product.COLUMNNAME_Description, productRecord.getDescription()).translate(adLanguage))
				.ean(productRecord.getUPC())
				.uom(servicesFacade.getUOMSymbol(uomId))
				.discontinuedFrom(discontinuedFrom)
				.bpartners(productBPartners.get(productId))
				.albertaProductInfo(getJsonAlbertaProductInfoFor(productId))
				.build();
	}

	@NonNull
	private ImmutableListMultimap<ProductId, JsonProductBPartner> retrieveJsonProductVendors(final Set<ProductId> productIds)
	{
		return servicesFacade.getBPartnerProductRecords(productIds)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> ProductId.ofRepoId(record.getM_Product_ID()),
						this::toJsonProductBPartner));
	}

	@NonNull
	private JsonProductBPartner toJsonProductBPartner(final I_C_BPartner_Product record)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(record);

		return JsonProductBPartner.builder()
				.bpartnerId(JsonMetasfreshId.of(record.getC_BPartner_ID()))
				//
				.productNo(record.getProductNo())
				.productName(trls.getColumnTrl(I_C_BPartner_Product.COLUMNNAME_ProductName, record.getProductName()).translate(adLanguage))
				.productDescription(trls.getColumnTrl(I_C_BPartner_Product.COLUMNNAME_ProductDescription, record.getProductDescription()).translate(adLanguage))
				.productCategory(trls.getColumnTrl(I_C_BPartner_Product.COLUMNNAME_ProductCategory, record.getProductCategory()).translate(adLanguage))
				//
				.ean(record.getUPC())
				//
				.vendor(record.isUsedForVendor())
				.currentVendor(record.isUsedForVendor() && record.isCurrentVendor())
				.customer(record.isUsedForCustomer())
				//
				.leadTimeInDays(record.getDeliveryTime_Promised())
				//
				.excludedFromSale(record.isExcludedFromSale())
				.exclusionFromSaleReason(record.getExclusionFromSaleReason())
				.excludedFromPurchase(record.isExcludedFromPurchase())
				.exclusionFromPurchaseReason(record.getExclusionFromPurchaseReason())
				//
				.build();
	}

	@NonNull
	private ImmutableList<I_M_Product> getProductsToExport()
	{
		final ImmutableList.Builder<I_M_Product> productRecordsBuilder = ImmutableList.builder();
		final HashSet<ProductId> loadedProductIds = new HashSet<>();

		streamProductsToExport().forEach(product -> {
			productRecordsBuilder.add(product);
			loadedProductIds.add(ProductId.ofRepoId(product.getM_Product_ID()));
		});

		loadAndSetAlbertaRelatedInfo(productRecordsBuilder, loadedProductIds);

		return productRecordsBuilder.build();
	}

	@Nullable
	private JsonAlbertaProductInfo getJsonAlbertaProductInfoFor(@NonNull final ProductId productId)
	{
		if (productId2AlbertaInfo == null)
		{
			return null;
		}

		final AlbertaCompositeProductInfo albertaCompositeProductInfo = productId2AlbertaInfo.get(productId);

		if (albertaCompositeProductInfo == null)
		{
			return null;
		}

		return mapToJsonAlbertaProductInfo(albertaCompositeProductInfo);
	}

	@NonNull
	private JsonAlbertaProductInfo mapToJsonAlbertaProductInfo(@NonNull final AlbertaCompositeProductInfo albertaCompositeProductInfo)
	{
		final List<JsonAlbertaPackagingUnit> jsonPackagingUnitList = Check.isEmpty(albertaCompositeProductInfo.getAlbertaPackagingUnitList())
				? null
				: albertaCompositeProductInfo.getAlbertaPackagingUnitList()
				.stream()
				.map(this::mapToJsonAlbertaPackagingUnit)
				.collect(ImmutableList.toImmutableList());

		return JsonAlbertaProductInfo.builder()
				.albertaProductId(albertaCompositeProductInfo.getAlbertaArticleId())
				.additionalDescription(albertaCompositeProductInfo.getAdditionalDescription())
				.assortmentType(albertaCompositeProductInfo.getAssortmentType())
				.inventoryType(albertaCompositeProductInfo.getInventoryType())
				.purchaseRating(albertaCompositeProductInfo.getPurchaseRating())
				.medicalAidPositionNumber(albertaCompositeProductInfo.getMedicalAidPositionNumber())
				.status(albertaCompositeProductInfo.getStatus())
				.size(albertaCompositeProductInfo.getSize())
				.stars(albertaCompositeProductInfo.getStars())
				.therapyIds(albertaCompositeProductInfo.getTherapyIds())
				.pharmacyPrice(albertaCompositeProductInfo.getPharmacyPrice())
				.fixedPrice(albertaCompositeProductInfo.getFixedPrice())
				.billableTherapies(albertaCompositeProductInfo.getBillableTherapyIds())
				.packagingUnits(jsonPackagingUnitList)
				.productGroupId(albertaCompositeProductInfo.getProductGroupId())
				.build();
	}

	private boolean wasAlreadyExported(@NonNull final I_M_Product product)
	{
		if (externalSystemType == null)
		{
			return false;
		}

		final Instant lastExported = externalSystemService
				.getMostRecentByTableReferenceAndSystem(TableRecordReference.of(I_M_Product.Table_Name, product.getM_Product_ID()), externalSystemType)
				.map(ExternalSystemExportAudit::getExportTime)
				.orElse(null);

		if (lastExported == null)
		{
			return false;
		}
		else if (product.getUpdated().toInstant().isAfter(lastExported))
		{
			return false;
		}
		else if (productId2AlbertaInfo == null)
		{
			return false;
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
			final AlbertaCompositeProductInfo albertaCompositeProductInfo = productId2AlbertaInfo.get(productId);

			return albertaCompositeProductInfo == null || albertaCompositeProductInfo.getLastUpdated().isBefore(lastExported);
		}
	}

	@Nullable
	private PriceListId getPharmacyPriceListIdOrNull()
	{
		if (!ExternalSystemType.Alberta.equals(externalSystemType) || Check.isBlank(externalSystemConfigValue))
		{
			return null;
		}

		return externalSystemService.getByTypeAndValue(ExternalSystemType.Alberta, externalSystemConfigValue)
				.map(ExternalSystemParentConfig::getChildConfig)
				.map(ExternalSystemAlbertaConfig::cast)
				.map(ExternalSystemAlbertaConfig::getPharmacyPriceListId)
				.orElse(null);
	}

	@NonNull
	private JsonAlbertaPackagingUnit mapToJsonAlbertaPackagingUnit(@NonNull final AlbertaPackagingUnit albertaPackagingUnit)
	{
		return JsonAlbertaPackagingUnit.builder()
				.quantity(albertaPackagingUnit.getQuantity())
				.unit(albertaPackagingUnit.getUnit())
				.build();
	}

	private void loadAndSetAlbertaRelatedInfo(
			@NonNull final ImmutableList.Builder<I_M_Product> productRecordsBuilder,
			@NonNull final HashSet<ProductId> loadedProductIds)
	{
		if (!ExternalSystemType.Alberta.equals(externalSystemType))
		{
			return;
		}

		final Instant since = isSingleExport()
				? Instant.now().plus(1, ChronoUnit.YEARS) //dev-note: lazy way of saying we are interested only in our product
				: this.since;

		final GetAlbertaProductsInfoRequest getAlbertaProductsInfoRequest = GetAlbertaProductsInfoRequest.builder()
				.since(since)
				.productIdSet(loadedProductIds)
				.pharmacyPriceListId(getPharmacyPriceListIdOrNull())
				.build();

		productId2AlbertaInfo = albertaProductService.getAlbertaInfoByProductId(getAlbertaProductsInfoRequest);
		final ImmutableSet<ProductId> productIdsLeftToLoaded = productId2AlbertaInfo
				.keySet()
				.stream()
				.filter(productId -> !loadedProductIds.contains(productId))
				.collect(ImmutableSet.toImmutableSet());

		servicesFacade.getProductsById(productIdsLeftToLoaded).forEach(productRecordsBuilder::add);
	}

	@NonNull
	private Stream<I_M_Product> streamProductsToExport()
	{
		if (isSingleExport())
		{
			Check.assumeNotNull(productIdentifier, "ProductIdentifier must be set in case of single export!");

			final ProductId productId = productRestService.resolveProductExternalIdentifier(productIdentifier, RestUtils.retrieveOrgIdOrDefault(orgCode))
					.orElseThrow(() -> new AdempiereException("Fail to resolve product external identifier")
							.appendParametersToMessage()
							.setParameter("ExternalIdentifier", productIdentifier));

			return Stream.of(servicesFacade.getProductById(productId));
		}
		else
		{
			return servicesFacade.streamAllProducts(since);
		}
	}

	private boolean isSingleExport()
	{
		return productIdentifier != null;
	}
}
