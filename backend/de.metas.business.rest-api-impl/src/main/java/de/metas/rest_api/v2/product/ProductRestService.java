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

package de.metas.rest_api.v2.product;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.BPartnerProduct;
import de.metas.bpartner_product.CreateBPartnerProductRequest;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import de.metas.common.externalreference.v2.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.pricing.v2.productprice.TaxCategory;
import de.metas.common.product.v2.request.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductTaxCategoryUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReferenceValueAndSystem;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.tax.CreateProductTaxCategoryRequest;
import de.metas.pricing.tax.ProductTaxCategory;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.CreateProductRequest;
import de.metas.product.IProductDAO;
import de.metas.product.Product;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.product.quality.attribute.QualityAttributeService;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.pricing.ProductPriceRestService;
import de.metas.rest_api.v2.warehouseassignment.ProductWarehouseAssignmentRestService;
import de.metas.sectionCode.SectionCodeId;
import de.metas.sectionCode.SectionCodeService;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Org;
import org.compiere.model.X_M_Product;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class ProductRestService
{
	private static final Logger logger = LogManager.getLogger(ProductRestService.class);
	private final ProductCategoryId defaultProductCategoryId = ProductCategoryId.ofRepoId(1000000); //fixme hardcoded

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ProductRepository productRepository;
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final SectionCodeService sectionCodeService;
	private final ProductAllergenRestService productAllergenRestService;
	private final QualityAttributeService qualityAttributeService;
	private final ProductWarehouseAssignmentRestService productWarehouseAssignmentRestService;
	private final JsonRetrieverService jsonRetrieverService;
	private final ExternalIdentifierResolver externalIdentifierResolver;

	private final ProductPriceRestService productPriceRestService;
	private final ProductTaxCategoryService productTaxCategoryService;
	
	public ProductRestService(
			@NonNull final ProductRepository productRepository,
			@NonNull final ProductWarehouseAssignmentRestService productWarehouseAssignmentRestService,
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final SectionCodeService sectionCodeService,
			@NonNull final ProductAllergenRestService productAllergenRestService,
			@NonNull final QualityAttributeService qualityAttributeService,
			@NonNull final JsonServiceFactory jsonServiceFactory,
			@NonNull final ExternalIdentifierResolver externalIdentifierResolver,
			@NonNull final ProductPriceRestService productPriceRestService,
			@NonNull final ProductTaxCategoryService productTaxCategoryService)
	{
		this.productRepository = productRepository;
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.sectionCodeService = sectionCodeService;
		this.productAllergenRestService = productAllergenRestService;
		this.qualityAttributeService = qualityAttributeService;
		this.productWarehouseAssignmentRestService = productWarehouseAssignmentRestService;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
		this.externalIdentifierResolver = externalIdentifierResolver;

		this.productPriceRestService = productPriceRestService;
		this.productTaxCategoryService = productTaxCategoryService;
	}

	@NonNull
	public JsonResponseUpsert upsertProducts(
			@Nullable final String orgCode,
			@NonNull final JsonRequestProductUpsert request)
	{
		return trxManager.callInNewTrx(() -> upsertProductsWithinTrx(orgCode, request));
	}

	@NonNull
	private JsonResponseUpsert upsertProductsWithinTrx(
			@Nullable final String orgCode,
			@NonNull final JsonRequestProductUpsert request)

	{
		final SyncAdvise syncAdvise = request.getSyncAdvise();

		final List<JsonResponseUpsertItem> responseList =
				request.getRequestItems()
						.stream()
						.map(reqItem -> upsertProductItem(reqItem, syncAdvise, orgCode))
						.collect(Collectors.toList());

		return JsonResponseUpsert.builder()
				.responseItems(responseList)
				.build();
	}

	@NonNull
	private JsonResponseUpsertItem upsertProductItem(
			@NonNull final JsonRequestProductUpsertItem jsonRequestProductUpsertItem,
			@NonNull final SyncAdvise parentSyncAdvise,
			@Nullable final String orgCode)
	{
		final JsonResponseUpsertItem.SyncOutcome syncOutcome;

		final JsonRequestProduct jsonRequestProduct = jsonRequestProductUpsertItem.getRequestProduct();

		final I_AD_Org org = orgDAO.getById(retrieveOrgIdOrDefault(orgCode));

		final SyncAdvise effectiveSyncAdvise = jsonRequestProduct.getSyncAdvise() != null ? jsonRequestProduct.getSyncAdvise() : parentSyncAdvise;

		final Optional<Product> existingProduct = getProductId(jsonRequestProductUpsertItem.getProductIdentifier(), org, jsonRequestProduct.getCode())
				.flatMap(productRepository::getOptionalById);

		final ProductId productId;
		if (existingProduct.isPresent())
		{
			productId = existingProduct.get().getId();

			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final Product product = syncProductWithJson(jsonRequestProduct, existingProduct.get(), org);
				productRepository.updateProduct(product);
				createOrUpdateBpartnerProducts(jsonRequestProduct.getBpartnerProductItems(), effectiveSyncAdvise, product.getId(), org);
				productWarehouseAssignmentRestService.processProductWarehouseAssignments(jsonRequestProduct.getWarehouseAssignments(), productId, OrgId.ofRepoId(org.getAD_Org_ID()));
				
				createOrUpdateProductTaxCategories(jsonRequestProduct.getProductTaxCategories(), product.getId(), effectiveSyncAdvise);

				syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
			}
			else
			{
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
			}
		}
		else
		{
			validateCreateSyncAdvise(jsonRequestProductUpsertItem, jsonRequestProductUpsertItem.getProductIdentifier(), effectiveSyncAdvise, "product");

			final CreateProductRequest createProductRequest = getCreateProductRequest(jsonRequestProduct, org);
			productId = productRepository.createProduct(createProductRequest).getId();

			createOrUpdateBpartnerProducts(jsonRequestProduct.getBpartnerProductItems(), effectiveSyncAdvise, productId, org);
			productWarehouseAssignmentRestService.processProductWarehouseAssignments(jsonRequestProduct.getWarehouseAssignments(), productId, OrgId.ofRepoId(org.getAD_Org_ID()));
			createOrUpdateProductTaxCategories(jsonRequestProduct.getProductTaxCategories(), productId, effectiveSyncAdvise);

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}

		if (jsonRequestProductUpsertItem.getRequestProduct().getProductAllergens() != null)
		{
			productAllergenRestService.upsertProductAllergens(org,
															  productId,
															  jsonRequestProductUpsertItem.getRequestProduct().getProductAllergens());
		}

		if (jsonRequestProductUpsertItem.getRequestProduct().getQualityAttributes() != null)
		{
			qualityAttributeService.upsertProductQualityAttributes(
					org,
					productId,
					jsonRequestProductUpsertItem.getRequestProduct().getQualityAttributes());
		}

		handleProductExternalReference(org,
									   jsonRequestProductUpsertItem.getProductIdentifier(),
									   JsonMetasfreshId.of(productId.getRepoId()),
									   jsonRequestProductUpsertItem.getExternalVersion(),
									   jsonRequestProductUpsertItem.getExternalReferenceUrl(),
									   jsonRequestProductUpsertItem.getExternalSystemConfigId(),
									   jsonRequestProductUpsertItem.getIsReadOnlyInMetasfresh());

		return JsonResponseUpsertItem.builder()
				.syncOutcome(syncOutcome)
				.identifier(jsonRequestProductUpsertItem.getProductIdentifier())
				.metasfreshId(JsonMetasfreshId.of(productId.getRepoId()))
				.build();
	}

	private void handleProductExternalReference(
			@NonNull final I_AD_Org org,
			@NonNull final String identifier,
			@NonNull final JsonMetasfreshId metasfreshId,
			@Nullable final String externalVersion,
			@Nullable final String externalReferenceUrl,
			@Nullable final JsonMetasfreshId externalSystemConfigId,
			@Nullable final Boolean isReadOnlyInMetasfresh)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(identifier);

		if (!ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(externalIdentifier.getType()))
		{
			return;
		}

		final ExternalReferenceValueAndSystem externalReferenceValueAndSystem = externalIdentifier.asExternalValueAndSystem();

		final JsonExternalSystemName systemName = JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem());
		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.externalReference(externalReferenceValueAndSystem.getValue())
				.type(ProductExternalReferenceType.PRODUCT.getCode())
				.build();

		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.builder()
				.lookupItem(externalReferenceLookupItem)
				.metasfreshId(metasfreshId)
				.version(externalVersion)
				.externalReferenceUrl(externalReferenceUrl)
				.externalSystemConfigId(externalSystemConfigId)
				.isReadOnlyMetasfresh(isReadOnlyInMetasfresh)
				.build();

		final JsonRequestExternalReferenceUpsert externalReferenceCreateRequest = JsonRequestExternalReferenceUpsert.builder()
				.systemName(systemName)
				.externalReferenceItem(externalReferenceItem)
				.build();

		externalReferenceRestControllerService.performUpsert(externalReferenceCreateRequest, org.getValue());
	}

	private void validateCreateSyncAdvise(
			@NonNull final Object parentResource,
			@NonNull final String resourceIdentifier,
			@NonNull final SyncAdvise effectiveSyncAdvise,
			@NonNull final String resourceName)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(resourceIdentifier);

		if (effectiveSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName(resourceName)
					.resourceIdentifier(resourceIdentifier)
					.parentResource(parentResource)
					.build()
					.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
		}
		else if (ExternalIdentifier.Type.METASFRESH_ID.equals(externalIdentifier.getType()))
		{
			throw MissingResourceException.builder().resourceName(resourceName).resourceIdentifier(resourceIdentifier)
					.parentResource(parentResource)
					.detail(TranslatableStrings.constant("With this type, only updates are allowed."))
					.build()
					.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
		}
	}

	@NonNull
	private ProductCategoryId getProductCategoryId(@NonNull final String categoryIdentifier, @NonNull final OrgId orgId)
	{

		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(categoryIdentifier);
		switch (externalIdentifier.getType())
		{
			case METASFRESH_ID:
				return ProductCategoryId.ofRepoId(externalIdentifier.asMetasfreshId().getValue());

			case EXTERNAL_REFERENCE:
				final Optional<ProductCategoryId> productCategoryId =
						jsonRetrieverService.resolveExternalReference(orgId, externalIdentifier, ProductCategoryExternalReferenceType.PRODUCT_CATEGORY)
								.map(MetasfreshId::getValue)
								.map(ProductCategoryId::ofRepoId);

				if (productCategoryId.isPresent())
				{
					return productCategoryId.get();
				}
				break;
			default:
				throw new AdempiereException("Unsupported category external identifier: " + categoryIdentifier);
		}

		throw new AdempiereException("ProductCategoryIdentifier could not be found: " + categoryIdentifier);

	}

	@NonNull
	private Optional<ProductId> getProductId(@NonNull final String productIdentifier, @NonNull final I_AD_Org org, @Nullable final String code)
	{
		if (code != null)
		{
			final IProductDAO.ProductQuery productQuery = IProductDAO.ProductQuery.builder().orgId(OrgId.ofRepoId(org.getAD_Org_ID())).value(code).build();

			final ProductId matchedProductId = productDAO.retrieveProductIdBy(productQuery);

			if (matchedProductId != null)
			{
				return Optional.of(matchedProductId);
			}
		}

		return externalIdentifierResolver.resolveProductExternalIdentifier(ExternalIdentifier.of(productIdentifier), OrgId.ofRepoId(org.getAD_Org_ID()));
	}

	private void createOrUpdateBpartnerProducts(
			@Nullable final List<JsonRequestBPartnerProductUpsert> jsonRequestBPartnerProductsUpsert,
			@NonNull final SyncAdvise effectiveSyncAdvise,
			@NonNull final ProductId productId,
			@NonNull final I_AD_Org org)
	{
		if (jsonRequestBPartnerProductsUpsert != null)
		{
			jsonRequestBPartnerProductsUpsert.forEach(jsonRequestBPartnerProductUpsert ->
															  createOrUpdateBpartnerProduct(jsonRequestBPartnerProductUpsert,
																							effectiveSyncAdvise,
																							productId,
																							OrgId.ofRepoId(org.getAD_Org_ID())));
		}

	}

	private void createOrUpdateProductTaxCategories(
			@Nullable final List<JsonRequestProductTaxCategoryUpsert> productTaxCategories,
			@NonNull final ProductId productId,
			@NonNull final SyncAdvise effectiveSyncAdvise)
	{
		if (productTaxCategories != null)
		{
			productTaxCategories.forEach(productTaxCategory ->
												 createOrUpdateProductTaxCategory(productTaxCategory,
																				  productId,
																				  effectiveSyncAdvise));
		}
	}

	private void createOrUpdateProductTaxCategory(
			@NonNull final JsonRequestProductTaxCategoryUpsert jsonRequestProductTaxCategoryUpsert,
			@NonNull final ProductId productId,
			@NonNull final SyncAdvise effectiveSyncAdvise)
	{
		validateJsonRequestProductTaxCategoryUpsert(jsonRequestProductTaxCategoryUpsert);

		final CountryId countryId = countryDAO.getCountryIdByCountryCode(jsonRequestProductTaxCategoryUpsert.getCountryCode());

		final Optional<ProductTaxCategory> existingProductTaxCategory = productTaxCategoryService.getProductTaxCategoryByUniqueKey(productId, countryId);

		if (existingProductTaxCategory.isPresent())
		{
			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final ProductTaxCategory productTaxCategory = syncProductTaxCategoryWithJson(jsonRequestProductTaxCategoryUpsert, existingProductTaxCategory.get());
				productTaxCategoryService.save(productTaxCategory);
			}
		}
		else if (effectiveSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("M_Product_TaxCategory")
					.resourceIdentifier("{ M_Product_ID:" + productId.getRepoId() + ", C_Country_ID: " + countryId)
					.build()
					.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
		}
		else
		{
			final CreateProductTaxCategoryRequest createProductTaxCategoryRequest = getCreateProductTaxCategoryRequest(jsonRequestProductTaxCategoryUpsert, productId, countryId);
			productTaxCategoryService.createProductTaxCategory(createProductTaxCategoryRequest);
		}
	}

	private void createOrUpdateBpartnerProduct(
			@NonNull final JsonRequestBPartnerProductUpsert jsonRequestBPartnerProductUpsert,
			@NonNull final SyncAdvise effectiveSyncAdvise,
			@NonNull final ProductId productId,
			@NonNull final OrgId orgId)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(jsonRequestBPartnerProductUpsert.getBpartnerIdentifier());

		final BPartnerId bPartnerId = jsonRetrieverService.resolveBPartnerExternalIdentifier(externalIdentifier, orgId)
				.orElseThrow(() -> new AdempiereException("BPartnerIdentifier could not be found: " + externalIdentifier.getRawValue()));

		final BPartnerProduct existingBPartnerProduct = productRepository.getByIdOrNull(productId, bPartnerId);

		if (existingBPartnerProduct != null)
		{
			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final BPartnerProduct bPartnerProduct = syncBPartnerProductWithJson(jsonRequestBPartnerProductUpsert, existingBPartnerProduct, bPartnerId);

				if (!Boolean.TRUE.equals(existingBPartnerProduct.getCurrentVendor())
						&& Boolean.TRUE.equals(bPartnerProduct.getCurrentVendor()))
				{
					productRepository.resetCurrentVendorFor(productId);
				}

				productRepository.updateBPartnerProduct(bPartnerProduct);
			}
		}
		else if (effectiveSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("C_BPartner_Product")
					.resourceIdentifier("{c_bpartner_identifier:" + jsonRequestBPartnerProductUpsert.getBpartnerIdentifier() + ", m_product_id: " + productId.getRepoId())
					.build()
					.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
		}
		else
		{
			final CreateBPartnerProductRequest createBPartnerProductRequest = getCreateBPartnerProductRequest(jsonRequestBPartnerProductUpsert, productId, bPartnerId);

			if (Boolean.TRUE.equals(createBPartnerProductRequest.getCurrentVendor()))
			{
				productRepository.resetCurrentVendorFor(productId);
			}

			productRepository.createBPartnerProduct(createBPartnerProductRequest);
		}
	}

	@NonNull
	private BPartnerProduct syncBPartnerProductWithJson(
			@NonNull final JsonRequestBPartnerProductUpsert jsonRequestBPartnerProductUpsert,
			@NonNull final BPartnerProduct existingBPartnerProduct,
			@NonNull final BPartnerId bPartnerId)
	{
		final BPartnerProduct.BPartnerProductBuilder builder = BPartnerProduct.builder();

		// bpartner
		if (jsonRequestBPartnerProductUpsert.isBpartnerSet())
		{
			builder.bPartnerId(bPartnerId);
		}
		else
		{
			builder.bPartnerId(existingBPartnerProduct.getBPartnerId());
		}

		// active
		if (jsonRequestBPartnerProductUpsert.isActiveSet())
		{
			if (jsonRequestBPartnerProductUpsert.getActive() == null)
			{
				logger.debug("Ignoring boolean property \"active\" : null ");
			}
			else
			{
				builder.active(jsonRequestBPartnerProductUpsert.getActive());
			}
		}
		else
		{
			builder.active(existingBPartnerProduct.getActive());
		}

		// seqNo
		if (jsonRequestBPartnerProductUpsert.isSeqNoSet())
		{
			builder.seqNo(jsonRequestBPartnerProductUpsert.getSeqNo());
		}
		else
		{
			builder.seqNo(existingBPartnerProduct.getSeqNo());
		}

		// productNo
		if (jsonRequestBPartnerProductUpsert.isProductNoSet())
		{
			builder.productNo(jsonRequestBPartnerProductUpsert.getProductNo());
		}
		else
		{
			builder.productNo(existingBPartnerProduct.getProductNo());
		}

		// description
		if (jsonRequestBPartnerProductUpsert.isDescriptionSet())
		{
			builder.description(jsonRequestBPartnerProductUpsert.getDescription());
		}
		else
		{
			builder.description(existingBPartnerProduct.getDescription());
		}

		// ean
		if (jsonRequestBPartnerProductUpsert.isCuEANSet())
		{
			builder.cuEAN(jsonRequestBPartnerProductUpsert.getCuEAN());
		}
		else
		{
			builder.cuEAN(existingBPartnerProduct.getCuEAN());
		}

		// gtin
		if (jsonRequestBPartnerProductUpsert.isGtinSet())
		{
			builder.gtin(jsonRequestBPartnerProductUpsert.getGtin());
		}
		else
		{
			builder.gtin(existingBPartnerProduct.getGtin());
		}

		// customerLabelName
		if (jsonRequestBPartnerProductUpsert.isCustomerLabelNameSet())
		{
			builder.customerLabelName(jsonRequestBPartnerProductUpsert.getCustomerLabelName());
		}
		else
		{
			builder.customerLabelName(existingBPartnerProduct.getCustomerLabelName());
		}

		// ingredients
		if (jsonRequestBPartnerProductUpsert.isIngredientsSet())
		{
			builder.ingredients(jsonRequestBPartnerProductUpsert.getIngredients());
		}
		else
		{
			builder.ingredients(existingBPartnerProduct.getIngredients());
		}

		// isCurrentVendor
		if (jsonRequestBPartnerProductUpsert.isCurrentVendorSet())
		{
			if (jsonRequestBPartnerProductUpsert.getCurrentVendor() == null)
			{
				logger.debug("Ignoring boolean property \"isCurrentVendor\" : null ");
			}
			else
			{
				builder.currentVendor(jsonRequestBPartnerProductUpsert.getCurrentVendor());
			}
		}
		else
		{
			builder.currentVendor(existingBPartnerProduct.getCurrentVendor());
		}

		// isExcludedFromSales
		if (jsonRequestBPartnerProductUpsert.isExcludedFromSalesSet())
		{
			if (jsonRequestBPartnerProductUpsert.getExcludedFromSales() == null)
			{
				logger.debug("Ignoring boolean property \"isExcludedFromSales\" : null ");
			}
			else
			{
				builder.isExcludedFromSales(jsonRequestBPartnerProductUpsert.getExcludedFromSales());
			}
		}
		else
		{
			builder.isExcludedFromSales(existingBPartnerProduct.getIsExcludedFromSales());
		}

		// exclusionFromSalesReason
		if (jsonRequestBPartnerProductUpsert.isExclusionFromSalesReasonSet())
		{
			builder.exclusionFromSalesReason(jsonRequestBPartnerProductUpsert.getExclusionFromSalesReason());
		}
		else
		{
			builder.exclusionFromSalesReason(existingBPartnerProduct.getExclusionFromSalesReason());
		}

		// isDropShip
		if (jsonRequestBPartnerProductUpsert.isDropShipSet())
		{
			if (jsonRequestBPartnerProductUpsert.getDropShip() == null)
			{
				logger.debug("Ignoring boolean property \"isDropShip\" : null ");
			}
			else
			{
				builder.dropShip(jsonRequestBPartnerProductUpsert.getDropShip());
			}
		}
		else
		{
			builder.dropShip(existingBPartnerProduct.getDropShip());
		}

		//isUsedForVendor
		if (jsonRequestBPartnerProductUpsert.isUsedForVendorSet())
		{
			if (jsonRequestBPartnerProductUpsert.getUsedForVendor() == null)
			{
				logger.debug("Ignoring boolean property \"usedForVendor\" : null ");
			}
			else
			{
				builder.usedForVendor(jsonRequestBPartnerProductUpsert.getUsedForVendor());
			}
		}
		else
		{
			builder.usedForVendor(existingBPartnerProduct.getUsedForVendor());
		}

		// isExcludedFromPurchase
		if (jsonRequestBPartnerProductUpsert.isExcludedFromPurchaseSet())
		{
			if (jsonRequestBPartnerProductUpsert.getExcludedFromPurchase() == null)
			{
				logger.debug("Ignoring boolean property \"isExcludedFromPurchase\" : null ");
			}
			else
			{
				builder.isExcludedFromPurchase(jsonRequestBPartnerProductUpsert.getExcludedFromPurchase());
			}
		}
		else
		{
			builder.isExcludedFromPurchase(existingBPartnerProduct.getIsExcludedFromPurchase());
		}

		// exclusionFromPurchaseReason
		if (jsonRequestBPartnerProductUpsert.isExclusionFromPurchaseReasonSet())
		{
			builder.exclusionFromPurchaseReason(jsonRequestBPartnerProductUpsert.getExclusionFromPurchaseReason());
		}
		else
		{
			builder.exclusionFromPurchaseReason(existingBPartnerProduct.getExclusionFromPurchaseReason());
		}

		builder.productId(existingBPartnerProduct.getProductId());

		return builder.build();
	}

	@NonNull
	private ProductTaxCategory syncProductTaxCategoryWithJson(
			@NonNull final JsonRequestProductTaxCategoryUpsert jsonRequestProductTaxCategoryUpsert,
			@NonNull final ProductTaxCategory existingProductTaxCategory)
	{
		final ProductTaxCategory.ProductTaxCategoryBuilder builder = existingProductTaxCategory.toBuilder();

		// valid from
		if (jsonRequestProductTaxCategoryUpsert.isValidFromSet())
		{
			if (jsonRequestProductTaxCategoryUpsert.getValidFrom() == null)
			{
				logger.debug("Ignoring property \"validFrom\" : null");
			}
			else
			{
				builder.validFrom(jsonRequestProductTaxCategoryUpsert.getValidFrom());
			}
		}

		// tax category
		if (jsonRequestProductTaxCategoryUpsert.isTaxCategorySet())
		{
			if (jsonRequestProductTaxCategoryUpsert.getTaxCategory() == null)
			{
				logger.debug("Ignoring property \"taxCategory\" : null");
			}
			else
			{
				final TaxCategoryId taxCategoryId = productPriceRestService.getTaxCategoryId(TaxCategory.ofInternalName(jsonRequestProductTaxCategoryUpsert.getTaxCategory()));
				builder.taxCategoryId(taxCategoryId);
			}
		}

		// active
		if (jsonRequestProductTaxCategoryUpsert.isActiveSet())
		{
			if (jsonRequestProductTaxCategoryUpsert.getActive() == null)
			{
				logger.debug("Ignoring boolean property \"active\" : null ");
			}
			else
			{
				builder.active(jsonRequestProductTaxCategoryUpsert.getActive());
			}
		}

		return builder.build();
	}

	@NonNull
	private Product syncProductWithJson(
			@NonNull final JsonRequestProduct jsonRequestProductUpsertItem,
			@NonNull final Product existingProduct,
			@NonNull final I_AD_Org org)
	{
		final Product.ProductBuilder builder = Product.builder();
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		// name
		if (jsonRequestProductUpsertItem.isNameSet())
		{
			builder.name(TranslatableStrings.constant(jsonRequestProductUpsertItem.getName()));
		}
		else
		{
			builder.name(existingProduct.getName());
		}

		// category
		if (jsonRequestProductUpsertItem.isProductCategoryIdentifierSet())
		{
			final ProductCategoryId productCategoryId = getProductCategoryId(jsonRequestProductUpsertItem.getProductCategoryIdentifier(), orgId);
			builder.productCategoryId(productCategoryId);
		}
		else
		{
			builder.productCategoryId(existingProduct.getProductCategoryId());
		}

		// type
		if (jsonRequestProductUpsertItem.isTypeSet())
		{
			builder.productType(getType(jsonRequestProductUpsertItem));
		}
		else
		{
			builder.productType(existingProduct.getProductType());
		}

		// uom
		if (jsonRequestProductUpsertItem.isUomCodeSet())
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(jsonRequestProductUpsertItem.getUomCode()));
			builder.uomId(uomId);
		}
		else
		{
			builder.uomId(existingProduct.getUomId());
		}

		// ean
		if (jsonRequestProductUpsertItem.isEanSet())
		{
			builder.ean(jsonRequestProductUpsertItem.getEan());
		}
		else
		{
			builder.ean(existingProduct.getEan());
		}

		// gtin
		if (jsonRequestProductUpsertItem.isGtinSet())
		{
			builder.gtin(jsonRequestProductUpsertItem.getGtin());
		}
		else
		{
			builder.gtin(existingProduct.getGtin());
		}

		// description
		if (jsonRequestProductUpsertItem.isDescriptionSet())
		{
			builder.description(TranslatableStrings.constant(jsonRequestProductUpsertItem.getDescription()));
		}
		else
		{
			builder.description(existingProduct.getDescription());
		}

		// discontinued
		if (jsonRequestProductUpsertItem.isDiscontinuedSet())
		{
			builder.discontinued(jsonRequestProductUpsertItem.getDiscontinued());
		}
		else
		{
			builder.discontinued(existingProduct.getDiscontinued());
		}

		// discontinuedFrom
		if (jsonRequestProductUpsertItem.isDiscontinuedFromSet())
		{
			builder.discontinuedFrom(jsonRequestProductUpsertItem.getDiscontinuedFrom());
		}
		else
		{
			builder.discontinuedFrom(existingProduct.getDiscontinuedFrom());
		}

		// active
		if (jsonRequestProductUpsertItem.isActiveSet())
		{
			if (jsonRequestProductUpsertItem.getActive() == null)
			{
				logger.debug("Ignoring boolean property \"active\" : null ");
			}
			else
			{
				builder.active(jsonRequestProductUpsertItem.getActive());
			}
		}
		else
		{
			builder.active(existingProduct.getActive());
		}

		// stocked
		if (jsonRequestProductUpsertItem.isStockedSet())
		{
			if (jsonRequestProductUpsertItem.getStocked() == null)
			{
				logger.debug("Ignoring boolean property \"stocked\" : null ");
			}
			else
			{
				builder.stocked(jsonRequestProductUpsertItem.getStocked());
			}
		}
		else
		{
			builder.stocked(existingProduct.isStocked());
		}

		if (jsonRequestProductUpsertItem.isSectionCodeSet())
		{
			final SectionCodeId sectionCodeId = Optional.ofNullable(jsonRequestProductUpsertItem.getSectionCode())
					.map(code -> sectionCodeService.getSectionCodeIdByValue(orgId, code))
					.orElse(null);

			builder.sectionCodeId(sectionCodeId);
		}
		else
		{
			builder.sectionCodeId(existingProduct.getSectionCodeId());
		}

		// purchased
		if (jsonRequestProductUpsertItem.isPurchasedSet())
		{
			if (jsonRequestProductUpsertItem.getPurchased() == null)
			{
				logger.debug("Ignoring boolean property \"purchased\" : null ");
			}
			else
			{
				builder.purchased(jsonRequestProductUpsertItem.getPurchased());
			}
		}
		else
		{
			builder.purchased(existingProduct.isPurchased());
		}

		// SAPProductHierarchy
		if (jsonRequestProductUpsertItem.isSapProductHierarchySet())
		{
			builder.sapProductHierarchy(jsonRequestProductUpsertItem.getSapProductHierarchy());
		}
		else
		{
			builder.sapProductHierarchy(existingProduct.getSapProductHierarchy());
		}

		if (jsonRequestProductUpsertItem.isGuaranteeMonthsSet())
		{
			builder.guaranteeMonths(jsonRequestProductUpsertItem.getGuaranteeMonths());
		}
		else
		{
			builder.guaranteeMonths(existingProduct.getGuaranteeMonths());
		}

		if (jsonRequestProductUpsertItem.isWarehouseTemperatureSet())
		{
			builder.warehouseTemperature(jsonRequestProductUpsertItem.getWarehouseTemperature());
		}
		else
		{
			builder.warehouseTemperature(existingProduct.getWarehouseTemperature());
		}

		if (jsonRequestProductUpsertItem.isCodeSet())
		{
			builder.productNo(jsonRequestProductUpsertItem.getCode());
		}
		else
		{
			builder.productNo(existingProduct.getProductNo());
		}

		builder.id(existingProduct.getId())
				.orgId(orgId)
				.commodityNumberId(existingProduct.getCommodityNumberId())
				.manufacturerId(existingProduct.getManufacturerId())
				.packageSize(existingProduct.getPackageSize())
				.weight(existingProduct.getWeight());

		return builder.build();
	}

	@NonNull
	private CreateProductRequest getCreateProductRequest(@NonNull final JsonRequestProduct jsonRequestProductUpsertItem, final I_AD_Org org)
	{
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(jsonRequestProductUpsertItem.getUomCode()));
		final String productType = getType(jsonRequestProductUpsertItem);
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final ProductCategoryId productCategoryId = jsonRequestProductUpsertItem.isProductCategoryIdentifierSet() ?
				getProductCategoryId(jsonRequestProductUpsertItem.getProductCategoryIdentifier(), orgId) : defaultProductCategoryId;

		final SectionCodeId sectionCodeId = Optional.ofNullable(jsonRequestProductUpsertItem.getSectionCode())
				.map(code -> sectionCodeService.getSectionCodeIdByValue(orgId, code))
				.orElse(null);

		final boolean purchased = Optional.ofNullable(jsonRequestProductUpsertItem.getPurchased())
				.orElse(false);

		return CreateProductRequest.builder()
				.orgId(orgId)
				.productName(jsonRequestProductUpsertItem.getName())
				.productType(productType)
				.productCategoryId(productCategoryId)
				.uomId(uomId)
				.stocked(jsonRequestProductUpsertItem.getStocked())
				.active(jsonRequestProductUpsertItem.getActive())
				.discontinued(jsonRequestProductUpsertItem.getDiscontinued())
				.discontinuedFrom(jsonRequestProductUpsertItem.getDiscontinuedFrom())
				.description(jsonRequestProductUpsertItem.getDescription())
				.gtin(jsonRequestProductUpsertItem.getGtin())
				.ean(jsonRequestProductUpsertItem.getEan())
				.productValue(jsonRequestProductUpsertItem.getCode())
				.sectionCodeId(sectionCodeId)
				.purchased(purchased)
				.sapProductHierarchy(jsonRequestProductUpsertItem.getSapProductHierarchy())
				.guaranteeMonths(jsonRequestProductUpsertItem.getGuaranteeMonths())
				.warehouseTemperature(jsonRequestProductUpsertItem.getWarehouseTemperature())
				.build();
	}

	@NonNull
	private CreateBPartnerProductRequest getCreateBPartnerProductRequest(
			@NonNull final JsonRequestBPartnerProductUpsert jsonRequestBPartnerProductUpsert,
			@NonNull final ProductId productId,
			@NonNull final BPartnerId bPartnerId)
	{
		return CreateBPartnerProductRequest.builder()
				.bPartnerId(bPartnerId)
				.active(jsonRequestBPartnerProductUpsert.getActive())
				.seqNo(jsonRequestBPartnerProductUpsert.getSeqNo())
				.productNo(jsonRequestBPartnerProductUpsert.getProductNo())
				.description(jsonRequestBPartnerProductUpsert.getDescription())
				.cuEAN(jsonRequestBPartnerProductUpsert.getCuEAN())
				.gtin(jsonRequestBPartnerProductUpsert.getGtin())
				.customerLabelName(jsonRequestBPartnerProductUpsert.getCustomerLabelName())
				.ingredients(jsonRequestBPartnerProductUpsert.getIngredients())
				.currentVendor(jsonRequestBPartnerProductUpsert.getCurrentVendor())
				.isExcludedFromSales(jsonRequestBPartnerProductUpsert.getExcludedFromSales())
				.exclusionFromSalesReason(jsonRequestBPartnerProductUpsert.getExclusionFromSalesReason())
				.dropShip(jsonRequestBPartnerProductUpsert.getDropShip())
				.usedForVendor(jsonRequestBPartnerProductUpsert.getUsedForVendor())
				.productId(productId)
				.isExcludedFromPurchase(jsonRequestBPartnerProductUpsert.getExcludedFromPurchase())
				.exclusionFromPurchaseReason(jsonRequestBPartnerProductUpsert.getExclusionFromPurchaseReason())
				.build();
	}

	@NonNull
	private CreateProductTaxCategoryRequest getCreateProductTaxCategoryRequest(
			@NonNull final JsonRequestProductTaxCategoryUpsert jsonRequestProductTaxCategoryUpsert,
			@NonNull final ProductId productId,
			@NonNull final CountryId countryId)
	{
		final TaxCategoryId taxCategoryId = Optional.ofNullable(jsonRequestProductTaxCategoryUpsert.getTaxCategory())
				.map(taxCategoryInternalName -> productPriceRestService.getTaxCategoryId(TaxCategory.ofInternalName(taxCategoryInternalName)))
				.orElseThrow(() -> new MissingPropertyException("taxCategory", jsonRequestProductTaxCategoryUpsert));

		return CreateProductTaxCategoryRequest.builder()
				.productId(productId)
				.taxCategoryId(taxCategoryId)
				.validFrom(jsonRequestProductTaxCategoryUpsert.getValidFrom())
				.countryId(countryId)
				.active(Optional.ofNullable(jsonRequestProductTaxCategoryUpsert.getActive()).orElse(true))
				.build();

	}

	@NonNull
	private static String getType(final @NonNull JsonRequestProduct jsonRequestProductUpsertItem)
	{
		final String productType;
		switch (jsonRequestProductUpsertItem.getType())
		{
			case SERVICE:
				productType = X_M_Product.PRODUCTTYPE_Service;
				break;
			case ITEM:
				productType = X_M_Product.PRODUCTTYPE_Item;
				break;
			case RESOURCE:
				productType = X_M_Product.PRODUCTTYPE_Resource;
				break;
			case ONLINE:
				productType = X_M_Product.PRODUCTTYPE_Online;
				break;
			case FREIGHT_COST:
				productType = X_M_Product.PRODUCTTYPE_FreightCost;
				break;
			case EXPENSE_TYPE:
				productType = X_M_Product.PRODUCTTYPE_ExpenseType;
				break;
			case NAHRUNG:
				productType = X_M_Product.PRODUCTTYPE_Nahrung;
				break;
			default:
				throw Check.fail("Unexpected type={}; jsonRequestProductUpsertItem={}", jsonRequestProductUpsertItem.getType(), jsonRequestProductUpsertItem);
		}
		return productType;
	}

	private static void validateJsonRequestProductTaxCategoryUpsert(@NonNull final JsonRequestProductTaxCategoryUpsert jsonRequestProductTaxCategoryUpsert)
	{
		if (jsonRequestProductTaxCategoryUpsert.getCountryCode() == null)
		{
			throw new MissingPropertyException("countryCode", jsonRequestProductTaxCategoryUpsert);
		}		
	}
}