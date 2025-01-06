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

package de.metas.rest_api.v2.pricing;

import de.metas.common.externalreference.v2.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPrice;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceQuery;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsert;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsertItem;
import de.metas.common.pricing.v2.productprice.JsonResponseProductPriceQuery;
import de.metas.common.pricing.v2.productprice.TaxCategory;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReferenceValueAndSystem;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.productprice.ProductPriceExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.productprice.CreateProductPriceRequest;
import de.metas.pricing.productprice.ProductPrice;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.rest_api.bpartner_pricelist.BpartnerPriceListServicesFacade;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.pricing.command.SearchProductPricesCommand;
import de.metas.rest_api.v2.product.ExternalIdentifierResolver;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class ProductPriceRestService
{
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final ProductPriceRepository productPriceRepository;
	private final PriceListRestService priceListRestService;
	private final ExternalIdentifierResolver externalIdentifierResolver;
	private final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade;
	private final JsonRetrieverService jsonRetrieverService;

	public ProductPriceRestService(
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final ProductPriceRepository productPriceRepository,
			@NonNull final PriceListRestService priceListRestService,
			@NonNull final ExternalIdentifierResolver externalIdentifierResolver,
			@NonNull final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade,
			@NonNull final JsonServiceFactory jsonServiceFactory)
	{
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.productPriceRepository = productPriceRepository;
		this.priceListRestService = priceListRestService;
		this.externalIdentifierResolver = externalIdentifierResolver;
		this.bpartnerPriceListServicesFacade = bpartnerPriceListServicesFacade;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
	}

	@NonNull
	public JsonResponseUpsert upsertProductPrices(
			@NonNull final String priceListVersionIdentifier,
			@NonNull final JsonRequestProductPriceUpsert request)
	{
		return trxManager.callInNewTrx(() -> upsertProductPricesWithinTrx(priceListVersionIdentifier, request));
	}

	@NonNull
	public JsonResponseUpsert upsertProductPricesForPriceList(
			@NonNull final String priceListIdentifier,
			@NonNull final JsonRequestProductPriceUpsert request)
	{
		final PriceListVersionId priceListVersionId = priceListRestService.getNewestVersionId(priceListIdentifier, Env.getOrgId());

		return upsertProductPrices(String.valueOf(priceListVersionId.getRepoId()), request);
	}

	@NonNull
	public JsonResponseProductPriceQuery productPriceSearch(@NonNull final JsonRequestProductPriceQuery request, @Nullable final String orgCode)
	{
		return SearchProductPricesCommand.builder()
				.externalIdentifierResolver(externalIdentifierResolver)
				.bpartnerPriceListServicesFacade(bpartnerPriceListServicesFacade)
				.jsonRetrieverService(jsonRetrieverService)
				.bpartnerIdentifier(ExternalIdentifier.of(request.getBpartnerIdentifier()))
				.productIdentifier(ExternalIdentifier.of(request.getProductIdentifier()))
				.targetDate(request.getTargetDate())
				.orgCode(orgCode)
				.build()
				.execute();
	}

	@NonNull
	private JsonResponseUpsert upsertProductPricesWithinTrx(
			@NonNull final String priceListVersionIdentifier,
			@NonNull final JsonRequestProductPriceUpsert request)
	{
		final SyncAdvise syncAdvise = request.getSyncAdvise();

		final List<JsonResponseUpsertItem> responseList =
				request.getRequestItems()
						.stream()
						.map(reqItem -> upsertProductPricesItem(priceListVersionIdentifier, reqItem, syncAdvise))
						.collect(Collectors.toList());

		return JsonResponseUpsert.builder().responseItems(responseList).build();
	}

	@NonNull
	private JsonResponseUpsertItem upsertProductPricesItem(
			@NonNull final String priceListVersionIdentifier,
			@NonNull final JsonRequestProductPriceUpsertItem jsonRequest,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final PriceListVersionId priceListVersionId = priceListRestService
				.getPriceListVersionId(ExternalIdentifier.of(priceListVersionIdentifier), jsonRequest.getJsonRequestProductPrice().getOrgCode())
				.orElseThrow(() -> new AdempiereException("No priceListVersion was found for the given priceListVersionIdentifier")
						.appendParametersToMessage()
						.setParameter("priceListVersionIdentifier", priceListVersionIdentifier));

		final JsonRequestProductPrice jsonRequestProductPrice = jsonRequest.getJsonRequestProductPrice();
		final SyncAdvise effectiveSyncAdvise = jsonRequestProductPrice.getSyncAdvise() != null
				? jsonRequestProductPrice.getSyncAdvise()
				: parentSyncAdvise;

		final ExternalIdentifier productPriceIdentifier = ExternalIdentifier.of(jsonRequest.getProductPriceIdentifier());
		final Optional<ProductPrice> existingProductPriceRecord = getProductPriceRecord(productPriceIdentifier, jsonRequestProductPrice.getOrgCode());

		final JsonResponseUpsertItem.SyncOutcome syncOutcome;
		final ProductPriceId productPriceId;

		if (existingProductPriceRecord.isPresent())
		{
			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final ProductPrice productPriceRequest = syncJsonToProductPrice(
						priceListVersionId,
						jsonRequestProductPrice,
						existingProductPriceRecord.get()
				);

				final ProductPrice updatedProductPrice = productPriceRepository.updateProductPrice(productPriceRequest);

				productPriceId = updatedProductPrice.getProductPriceId();
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
			}
			else
			{
				productPriceId = existingProductPriceRecord.get().getProductPriceId();
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
			}
		}
		else
		{
			if (effectiveSyncAdvise.isFailIfNotExists())
			{
				throw new AdempiereException("No ProductPrice record was found for the given productPriceIdentifier!")
						.appendParametersToMessage()
						.setParameter("productPriceIdentifier", productPriceIdentifier);
			}
			else
			{
				final CreateProductPriceRequest createProductPriceRequest = buildCreateProductPriceRequest(
						priceListVersionId,
						jsonRequestProductPrice);

				final ProductPrice persistedProductPrice = productPriceRepository.createProductPrice(createProductPriceRequest);
				productPriceId = persistedProductPrice.getProductPriceId();

				handleNewProductPriceExternalReference(jsonRequestProductPrice.getOrgCode(), productPriceIdentifier, productPriceId);

				syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
			}
		}

		return JsonResponseUpsertItem.builder()
				.identifier(jsonRequest.getProductPriceIdentifier())
				.metasfreshId(JsonMetasfreshId.of(productPriceId.getRepoId()))
				.syncOutcome(syncOutcome)
				.build();
	}

	@NonNull
	private ProductPrice syncJsonToProductPrice(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final JsonRequestProductPrice jsonRequest,
			@NonNull final ProductPrice existingRecord)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(jsonRequest.getOrgCode());
		final ProductPrice.ProductPriceBuilder productPriceBuilder = ProductPrice.builder()
				.orgId(orgId)
				.productPriceId(existingRecord.getProductPriceId())
				.priceListVersionId(priceListVersionId)
				.productId(getProductId(jsonRequest.getProductIdentifier(), jsonRequest.getOrgCode()))
				.taxCategoryId(getTaxCategoryId(jsonRequest.getTaxCategory()))
				.priceStd(jsonRequest.getPriceStd());

		//priceLimit
		if (jsonRequest.isPriceLimitSet())
		{
			productPriceBuilder.priceLimit(jsonRequest.getPriceLimit());
		}
		else
		{
			productPriceBuilder.priceLimit(existingRecord.getPriceLimit());
		}

		//priceList
		if (jsonRequest.isPriceListSet())
		{
			productPriceBuilder.priceList(jsonRequest.getPriceList());
		}
		else
		{
			productPriceBuilder.priceList(existingRecord.getPriceList());
		}

		//isActive
		if (jsonRequest.isActiveSet())
		{
			productPriceBuilder.isActive(jsonRequest.getActive());
		}
		else
		{
			productPriceBuilder.isActive(existingRecord.getIsActive());
		}

		//uomId
		if (jsonRequest.isUomCodeSet())
		{
			final UomId uomId = uomDao.getUomIdByX12DE355(X12DE355.ofCode(jsonRequest.getUomCode()));
			productPriceBuilder.uomId(uomId);
		}
		else
		{
			// the update_remove case is ignored as the C_UOM_ID is a mandatory column
			productPriceBuilder.uomId(existingRecord.getUomId());
		}


		//seqNo
		if (jsonRequest.isSeqNoSet())
		{
			productPriceBuilder.seqNo(jsonRequest.getSeqNo());
		}
		else
		{
			// the update_remove case is ignored as the SeqNo is a mandatory column
			productPriceBuilder.seqNo(existingRecord.getSeqNo());
		}

		return productPriceBuilder.build();
	}

	@NonNull
	private CreateProductPriceRequest buildCreateProductPriceRequest(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final JsonRequestProductPrice jsonRequest
	)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(jsonRequest.getOrgCode());

		final ProductId productId = getProductId(jsonRequest.getProductIdentifier(), jsonRequest.getOrgCode());

		final TaxCategoryId taxCategoryId = getTaxCategoryId(jsonRequest.getTaxCategory());

		final UomId uomId = jsonRequest.isUomCodeSet()
				? uomDao.getUomIdByX12DE355(X12DE355.ofCode(jsonRequest.getUomCode()))
				: productBL.getStockUOMId(productId);

		final CreateProductPriceRequest.CreateProductPriceRequestBuilder requestBuilder = CreateProductPriceRequest.builder()
				.orgId(orgId)
				.taxCategoryId(taxCategoryId)
				.productId(productId)
				.priceListVersionId(priceListVersionId)
				.priceStd(jsonRequest.getPriceStd())
				.uomId(uomId)
				.isActive(jsonRequest.getActive())
				.seqNo(jsonRequest.getSeqNo());

		if (jsonRequest.isPriceLimitSet())
		{
			requestBuilder.priceLimit(jsonRequest.getPriceLimit());
		}

		if (jsonRequest.isPriceListSet())
		{
			requestBuilder.priceList(jsonRequest.getPriceList());
		}

		return requestBuilder.build();
	}

	@NonNull
	private Optional<ProductPriceId> getProductPriceId(@NonNull final ExternalIdentifier productPriceIdentifier, @NonNull final String orgCode)
	{
		switch (productPriceIdentifier.getType())
		{
			case METASFRESH_ID:
				return Optional.of(ProductPriceId.ofRepoId(productPriceIdentifier.asMetasfreshId().getValue()));
			case EXTERNAL_REFERENCE:
				return externalReferenceRestControllerService
						.getJsonMetasfreshIdFromExternalReference(orgCode, productPriceIdentifier, ProductPriceExternalReferenceType.PRODUCT_PRICE)
						.map(metasfreshId -> ProductPriceId.ofRepoId(metasfreshId.getValue()));
			default:
				throw new AdempiereException("Unsupported external identifier type!")
						.appendParametersToMessage()
						.setParameter("productPriceIdentifier", productPriceIdentifier);
		}
	}

	@NonNull
	private ProductId getProductId(@NonNull final String productIdentifier, @NonNull final String orgCode)
	{
		final ExternalIdentifier productExternalIdentifier = ExternalIdentifier.of(productIdentifier);

		switch (productExternalIdentifier.getType())
		{
			case METASFRESH_ID:
				return ProductId.ofRepoId(productExternalIdentifier.asMetasfreshId().getValue());
			case EXTERNAL_REFERENCE:
				return externalReferenceRestControllerService
						.getJsonMetasfreshIdFromExternalReference(orgCode, productExternalIdentifier, ProductExternalReferenceType.PRODUCT)
						.map(metasfreshId -> ProductId.ofRepoId(metasfreshId.getValue()))
						.orElseThrow(() -> new AdempiereException("No productId found for the given external productIdentifier")
								.appendParametersToMessage()
								.setParameter("productIdentifier", productExternalIdentifier));
			default:
				throw new AdempiereException("Unsupported external identifier type!")
						.appendParametersToMessage()
						.setParameter("productIdentifier", productExternalIdentifier);
		}
	}

	@NonNull
	private Optional<ProductPrice> getProductPriceRecord(@NonNull final ExternalIdentifier productPriceIdentifier, @NonNull final String orgCode)
	{
		return getProductPriceId(productPriceIdentifier, orgCode)
				.map(productPriceRepository::getById);
	}

	@NonNull
	public TaxCategoryId getTaxCategoryId(@NonNull final TaxCategory taxCategory)
	{
		return taxBL.getTaxCategoryIdByInternalName(taxCategory.getInternalName())
				.orElseThrow(() -> new AdempiereException("No TaxCategory record found for the given TaxCategory type!")
						.appendParametersToMessage()
						.setParameter("taxCategory", taxCategory));
	}

	private void handleNewProductPriceExternalReference(
			@NonNull final String orgCode,
			@NonNull final ExternalIdentifier externalProductPriceIdentifier,
			@NonNull final ProductPriceId productPriceId)
	{
		Check.assume(externalProductPriceIdentifier.getType().equals(ExternalIdentifier.Type.EXTERNAL_REFERENCE), "ExternalIdentifier must be of type external reference.");

		final ExternalReferenceValueAndSystem externalReferenceValueAndSystem = externalProductPriceIdentifier.asExternalValueAndSystem();

		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.externalReference(externalReferenceValueAndSystem.getValue())
				.type(ProductPriceExternalReferenceType.PRODUCT_PRICE.getCode())
				.build();

		final JsonMetasfreshId jsonProductPriceId = JsonMetasfreshId.of(productPriceId.getRepoId());
		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.of(externalReferenceLookupItem, jsonProductPriceId);

		final JsonExternalSystemName systemName = JsonExternalSystemName.of(externalReferenceValueAndSystem.getExternalSystem());
		final JsonExternalReferenceCreateRequest externalReferenceCreateRequest = JsonExternalReferenceCreateRequest.builder()
				.systemName(systemName)
				.item(externalReferenceItem)
				.build();

		externalReferenceRestControllerService.performInsert(orgCode, externalReferenceCreateRequest);
	}

}
