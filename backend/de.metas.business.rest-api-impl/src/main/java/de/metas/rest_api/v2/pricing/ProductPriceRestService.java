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

import de.metas.common.bpartner.response.JsonResponseUpsertItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.common.rest_api.pricing.productprice.JsonRequestProductPrice;
import de.metas.common.rest_api.pricing.productprice.JsonRequestProductPriceItemUpsert;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.pricelist.PriceListVersion;
import de.metas.pricing.productprice.CreateProductPrice;
import de.metas.pricing.productprice.ProductPrice;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class ProductPriceRestService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ProductPriceRepository productPriceRepository;

	public ProductPriceRestService(final ProductPriceRepository productPriceRepository)
	{
		this.productPriceRepository = productPriceRepository;
	}

	@NonNull
	public JsonResponseUpsertItem putProductPriceByPriceListVersionIdentifier(
			@NonNull final String priceListVersionIdentifier,
			@NonNull final JsonRequestProductPriceItemUpsert jsonRequest,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final Optional<PriceListVersion> priceListVersion = getPriceListVersionRecordOrNull(priceListVersionIdentifier);

		if (!priceListVersion.isPresent())
		{
			throw new AdempiereException("PriceListVersionIdentifier could not be found: " + priceListVersionIdentifier);
		}

		final String productPriceIdentifier = jsonRequest.getProductPriceIdentifier();
		final JsonRequestProductPrice jsonRequestProductPrice = jsonRequest.getJsonRequestProductPrice();

		final Optional<ProductId> existingProductId = getProductIdRecordOrNull(jsonRequestProductPrice.getProductId());

		if (!existingProductId.isPresent())
		{
			throw new AdempiereException("ProductIdentifier could not be found: " + jsonRequestProductPrice.getProductId());
		}

		final SyncAdvise effectiveSyncAdvise = jsonRequestProductPrice.getSyncAdvise() != null
				? jsonRequestProductPrice.getSyncAdvise()
				: parentSyncAdvise;

		final JsonResponseUpsertItem.SyncOutcome syncOutcome;
		final ProductPriceId productPriceId;

		final Optional<ProductPrice> existingProductPriceRecord = getProductPriceRecordOrNull(productPriceIdentifier);

		if (existingProductPriceRecord.isPresent())
		{
			productPriceId = existingProductPriceRecord.get().getProductPriceId();
			if (effectiveSyncAdvise.getIfExists().isUpdate())
			{
				final ProductPrice productPriceRequest = syncJsonToProductPrice(
						priceListVersion.get().getPriceListVersionId(),
						existingProductPriceRecord.get().getProductPriceId(),
						jsonRequestProductPrice,
						existingProductPriceRecord.get());
				productPriceRepository.updateProductPrice(productPriceRequest);

				syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
			}
			else if (effectiveSyncAdvise.getIfExists().isUpdateRemove())
			{
				productPriceRepository.inactivateProductPrice(existingProductPriceRecord.get());
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
			}
			else
			{
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
			}
		}
		else
		{
			if (effectiveSyncAdvise.isFailIfNotExists())
			{
				throw new AdempiereException("Missing ProductPrice record from metasfresh DB")
						.appendParametersToMessage()
						.setParameter("productPriceIdentifier", productPriceIdentifier);
			}
			else
			{
				final CreateProductPrice createProductPriceRequest = syncJsonToCreateProductPrice(
						priceListVersion.get().getPriceListVersionId(), jsonRequestProductPrice);
				final ProductPrice persistedProductPrice = productPriceRepository.createProductPrice(createProductPriceRequest);
				productPriceId = persistedProductPrice.getProductPriceId();
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
			}
		}

		return JsonResponseUpsertItem.builder()
				.identifier(priceListVersionIdentifier)
				.metasfreshId(JsonMetasfreshId.of(productPriceId.getRepoId()))
				.syncOutcome(syncOutcome)
				.build();
	}

	@NonNull
	private ProductPrice syncJsonToProductPrice(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final ProductPriceId productPriceId,
			@NonNull final JsonRequestProductPrice jsonRequest,
			@NonNull final ProductPrice existingRecord)
	{
		final ProductPrice.ProductPriceBuilder productPriceBuilder = ProductPrice.builder();

		final OrgId orgId = retrieveOrgIdOrDefault(jsonRequest.getOrgCode());
		productPriceBuilder.orgId(orgId);

		productPriceBuilder.priceListVersionId(priceListVersionId);
		productPriceBuilder.productPriceId(productPriceId);

		//productId
		if (jsonRequest.isProductIdSet())
		{
			final ProductId productId = ProductId.ofRepoId(Integer.parseInt(jsonRequest.getProductId()));
			productPriceBuilder.productId(productId);
		}
		else
		{
			productPriceBuilder.productId(existingRecord.getProductId());
		}

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
			productPriceBuilder.priceLimit(existingRecord.getPriceLimit());
		}

		//priceStd
		if (jsonRequest.isPriceStdSet())
		{
			productPriceBuilder.priceStd(jsonRequest.getPriceStd());
		}
		else
		{
			productPriceBuilder.priceStd(existingRecord.getPriceStd());
		}

		//taxCategory
		if (jsonRequest.isTaxCategorySet())
		{
			//	todo florina look for tax category in db by tax InternalName, set both
		}
		else
		{
			productPriceBuilder.taxCategoryId(existingRecord.getTaxCategoryId());
		}

		//isActive
		if (jsonRequest.isActiveSet())
		{
			final Boolean isActive = jsonRequest.getActive().equals("true");
			productPriceBuilder.isActive(isActive);
		}
		else
		{
			productPriceBuilder.isActive(existingRecord.getIsActive());
		}

		return productPriceBuilder.build();
	}

	@NonNull
	private CreateProductPrice syncJsonToCreateProductPrice(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final JsonRequestProductPrice jsonRequest
	)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(jsonRequest.getOrgCode());
		final ProductId productId = ProductId.ofRepoId(Integer.parseInt(jsonRequest.getProductId()));

		final Boolean isActive = jsonRequest.getActive().equals("true");

		//todo florina tax category id
		//todo florina tax internal name

		return CreateProductPrice.builder()
				.orgId(orgId)
				.productId(productId)
				.priceListVersionId(priceListVersionId)
				.priceLimit(jsonRequest.getPriceLimit())
				.priceList(jsonRequest.getPriceList())
				.priceStd(jsonRequest.getPriceStd())
				.isActive(isActive)
				.build();
	}

	@NonNull
	private Optional<ProductId> getProductIdRecordOrNull(@NonNull final String identifier)
	{
		final I_M_Product record = queryBL
				.createQueryBuilder(I_M_Product.class)
				.filter(item -> item.getM_Product_ID() == Integer.parseInt(identifier))
				.create()
				.firstOnly(I_M_Product.class);

		return record != null
				? Optional.of(ProductId.ofRepoId(record.getM_Product_ID()))
				: Optional.empty();
	}

	@NonNull
	private Optional<ProductPrice> getProductPriceRecordOrNull(@NonNull final String identifier)
	{
		final I_M_ProductPrice record = queryBL
				.createQueryBuilder(I_M_ProductPrice.class)
				.filter(item -> item.getM_ProductPrice_ID() == Integer.parseInt(identifier))
				.create()
				.firstOnly(I_M_ProductPrice.class);

		return record != null
				? Optional.of(buildProductPrice(record))
				: Optional.empty();
	}

	@NonNull
	private ProductPrice buildProductPrice(@NonNull final I_M_ProductPrice record)
	{
		return ProductPrice.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.productPriceId(ProductPriceId.ofRepoId(record.getM_ProductPrice_ID()))
				.priceListVersionId(PriceListVersionId.ofRepoId(record.getM_PriceList_Version_ID()))
				.priceLimit(record.getPriceLimit())
				.priceList(record.getPriceList())
				.priceStd(record.getPriceStd())
				.taxCategoryId(TaxCategoryId.ofRepoId(record.getC_TaxCategory_ID()))
				.isActive(record.isActive())
				.build();
	}

	@NonNull
	private Optional<PriceListVersion> getPriceListVersionRecordOrNull(@NonNull final String identifier)
	{
		final I_M_PriceList_Version record = queryBL
				.createQueryBuilder(I_M_PriceList_Version.class)
				.filter(item -> item.getM_PriceList_Version_ID() == Integer.parseInt(identifier))
				.create()
				.firstOnly(I_M_PriceList_Version.class);

		return record != null
				? Optional.of(buildPriceListVersion(record))
				: Optional.empty();
	}

	@NonNull
	private PriceListVersion buildPriceListVersion(@NonNull final I_M_PriceList_Version record)
	{
		return PriceListVersion.builder()
				.priceListVersionId(PriceListVersionId.ofRepoId(record.getM_PriceList_Version_ID()))
				.priceListId(PriceListId.ofRepoId(record.getM_PriceList_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.description(record.getDescription())
				.validFrom(TimeUtil.asInstant(record.getValidFrom()))
				.isActive(record.isActive())
				.build();
	}
}
