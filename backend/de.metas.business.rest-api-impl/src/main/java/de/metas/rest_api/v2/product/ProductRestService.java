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
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.product.v2.request.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReferenceValueAndSystem;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.CreateProductRequest;
import de.metas.product.IProductDAO;
import de.metas.product.Product;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
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
import java.util.Objects;
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
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ProductRepository productRepository;
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;

	public ProductRestService(final ProductRepository productRepository, final ExternalReferenceRestControllerService externalReferenceRestControllerService)
	{
		this.productRepository = productRepository;
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
	}

	@NonNull
	public JsonResponseUpsert upsertProducts(
			@Nullable final String orgCode,
			@NonNull final JsonRequestProductUpsert request)
	{
		return trxManager.callInNewTrx(() -> upsertProductsWithinTrx(orgCode, request));
	}

	@NonNull
	public Optional<ProductId> resolveProductExternalIdentifier(
			@NonNull final ExternalIdentifier productIdentifier,
			@NonNull final OrgId orgId)
	{
		switch (productIdentifier.getType())
		{
			case METASFRESH_ID:
				return Optional.of(ProductId.ofRepoId(productIdentifier.asMetasfreshId().getValue()));

			case EXTERNAL_REFERENCE:
				return externalReferenceRestControllerService
						.getJsonMetasfreshIdFromExternalReference(orgId, productIdentifier, ProductExternalReferenceType.PRODUCT)
						.map(JsonMetasfreshId::getValue)
						.map(ProductId::ofRepoId);

			case VALUE:
				final IProductDAO.ProductQuery query = IProductDAO.ProductQuery.builder()
						.value(productIdentifier.asValue())
						.orgId(orgId)
						.includeAnyOrg(true)
						.build();

				return Optional.ofNullable(productDAO.retrieveProductIdBy(query));
			default:
				throw new InvalidIdentifierException(productIdentifier.getRawValue());
		}
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

			syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
		}

		handleProductExternalReference(org,
									   jsonRequestProductUpsertItem.getProductIdentifier(),
									   JsonMetasfreshId.of(productId.getRepoId()),
									   jsonRequestProductUpsertItem.getExternalVersion(),
									   jsonRequestProductUpsertItem.getExternalReferenceUrl());

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
			@Nullable final String externalReferenceUrl)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(identifier);

		if (!ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(externalIdentifier.getType()))
		{
			return;
		}

		final ExternalReferenceValueAndSystem externalReferenceValueAndSystem = externalIdentifier.asExternalValueAndSystem();

		final JsonExternalSystemName systemName = JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem());
		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.id(externalReferenceValueAndSystem.getValue())
				.type(ProductExternalReferenceType.PRODUCT.getCode())
				.build();

		final JsonExternalReferenceItem externalReferenceItem = JsonExternalReferenceItem.builder()
				.lookupItem(externalReferenceLookupItem)
				.metasfreshId(metasfreshId)
				.version(externalVersion)
				.externalReferenceUrl(externalReferenceUrl)
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
	private ProductCategoryId getProductCategoryId(@NonNull final String categoryIdentifier, @NonNull final I_AD_Org org)
	{

		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(categoryIdentifier);
		switch (externalIdentifier.getType())
		{
			case METASFRESH_ID:
				return ProductCategoryId.ofRepoId(externalIdentifier.asMetasfreshId().getValue());

			case EXTERNAL_REFERENCE:
				final Optional<ProductCategoryId> productCategoryId =
						getJsonMetasfreshIdFromExternalReference(org.getValue(), externalIdentifier, ProductCategoryExternalReferenceType.PRODUCT_CATEGORY)
								.map(JsonMetasfreshId::getValue)
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

		return resolveProductExternalIdentifier(ExternalIdentifier.of(productIdentifier), OrgId.ofRepoId(org.getAD_Org_ID()));
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
																							org.getValue()));
		}

	}

	private void createOrUpdateBpartnerProduct(
			@NonNull final JsonRequestBPartnerProductUpsert jsonRequestBPartnerProductUpsert,
			@NonNull final SyncAdvise effectiveSyncAdvise,
			@NonNull final ProductId productId,
			@NonNull final String orgCode)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(jsonRequestBPartnerProductUpsert.getBpartnerIdentifier());

		final BPartnerId bPartnerId = getBPartnerId(externalIdentifier, orgCode);

		final BPartnerProduct existingBPartnerProduct = productRepository.getByIdOrNull(productId, bPartnerId);

		if (existingBPartnerProduct != null)
		{
			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final BPartnerProduct bPartnerProduct = syncBPartnerProductWithJson(jsonRequestBPartnerProductUpsert, existingBPartnerProduct, bPartnerId);
				productRepository.updateBPartnerProduct(bPartnerProduct);
			}
		}
		else
		{
			validateCreateSyncAdvise(jsonRequestBPartnerProductUpsert, jsonRequestBPartnerProductUpsert.getBpartnerIdentifier(),
									 effectiveSyncAdvise, "BPartnerProduct");

			final CreateBPartnerProductRequest createBPartnerProductRequest = getCreateBPartnerProductRequest(jsonRequestBPartnerProductUpsert, productId, bPartnerId);
			productRepository.createBPartnerProduct(createBPartnerProductRequest);

		}
	}

	@NonNull
	private BPartnerId getBPartnerId(@NonNull final ExternalIdentifier externalIdentifier, @NonNull final String orgCode)
	{
		if (ExternalIdentifier.Type.METASFRESH_ID.equals(externalIdentifier.getType()))
		{
			return BPartnerId.ofRepoId(Integer.parseInt(externalIdentifier.getRawValue()));
		}
		else if (ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(externalIdentifier.getType()))
		{

			final Optional<JsonMetasfreshId> metasfreshId = getJsonMetasfreshIdFromExternalReference(orgCode, externalIdentifier, BPartnerExternalReferenceType.BPARTNER);

			if (metasfreshId.isPresent())
			{
				return BPartnerId.ofRepoId(metasfreshId.get().getValue());
			}
		}
		else
		{
			throw new AdempiereException("External identifier type is not supported: " + externalIdentifier.getType());
		}

		throw new AdempiereException("BPartnerIdentifier could not be found: " + externalIdentifier.getRawValue());
	}

	@NonNull
	private Optional<JsonMetasfreshId> getJsonMetasfreshIdFromExternalReference(
			@NonNull final String orgCode,
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		final JsonExternalSystemName externalSystemName = JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem());

		final JsonExternalReferenceLookupRequest lookupRequest = JsonExternalReferenceLookupRequest.builder()
				.systemName(externalSystemName)
				.item(JsonExternalReferenceLookupItem.builder()
							  .type(externalReferenceType.getCode())
							  .id(externalIdentifier.asExternalValueAndSystem().getValue())
							  .build())
				.build();

		final JsonExternalReferenceLookupResponse lookupResponse = externalReferenceRestControllerService.performLookup(orgCode, lookupRequest);
		return lookupResponse.getItems()
				.stream()
				.map(JsonExternalReferenceItem::getMetasfreshId)
				.filter(Objects::nonNull)
				.findFirst();
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

		// exclusionFromSalesReason
		if (jsonRequestBPartnerProductUpsert.isExclusionFromSalesReasonSet())
		{
			builder.exclusionFromSalesReason(jsonRequestBPartnerProductUpsert.getExclusionFromSalesReason());
		}
		else
		{
			builder.exclusionFromSalesReason(existingBPartnerProduct.getExclusionFromSalesReason());
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

		builder.productId(existingBPartnerProduct.getProductId());

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
			final ProductCategoryId productCategoryId = getProductCategoryId(jsonRequestProductUpsertItem.getProductCategoryIdentifier(), org);
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
			if (jsonRequestProductUpsertItem.getDiscontinued() == null)
			{
				logger.debug("Ignoring boolean property \"discontinued\" : null ");
			}
			else
			{
				builder.discontinued(jsonRequestProductUpsertItem.getDiscontinued());
			}
		}
		else
		{
			builder.discontinued(existingProduct.getDiscontinued());
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

		builder.id(existingProduct.getId())
				.orgId(orgId)
				.productNo(existingProduct.getProductNo())
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
		final String productType;

		productType = getType(jsonRequestProductUpsertItem);

		final ProductCategoryId productCategoryId = jsonRequestProductUpsertItem.isProductCategoryIdentifierSet() ?
				getProductCategoryId(jsonRequestProductUpsertItem.getProductCategoryIdentifier(), org) : defaultProductCategoryId;

		return CreateProductRequest.builder()
				.orgId(OrgId.ofRepoId(org.getAD_Org_ID()))
				.productName(jsonRequestProductUpsertItem.getName())
				.productType(productType)
				.productCategoryId(productCategoryId)
				.uomId(uomId)
				.stocked(jsonRequestProductUpsertItem.getStocked())
				.active(jsonRequestProductUpsertItem.getActive())
				.discontinued(jsonRequestProductUpsertItem.getDiscontinued())
				.description(jsonRequestProductUpsertItem.getDescription())
				.gtin(jsonRequestProductUpsertItem.getGtin())
				.ean(jsonRequestProductUpsertItem.getEan())
				.productValue(jsonRequestProductUpsertItem.getCode())
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
				.build();
	}

	@NonNull
	private String getType(final @NonNull JsonRequestProduct jsonRequestProductUpsertItem)
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
			default:
				throw Check.fail("Unexpected type={}; jsonRequestProductUpsertItem={}", jsonRequestProductUpsertItem.getType(), jsonRequestProductUpsertItem);
		}
		return productType;
	}

}
