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

package de.metas.rest_api.pricing;

import de.metas.common.bpartner.response.JsonResponseUpsertItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.common.rest_api.pricing.productprice.JsonProductPriceRequest;
import de.metas.common.rest_api.pricing.productprice.JsonUpsertProductPriceRequest;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.productprice.CreateProductPrice;
import de.metas.pricing.productprice.ProductPrice;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

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
			@NonNull final JsonProductPriceRequest jsonProductPriceRequest)
	{

		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(Integer.parseInt(priceListVersionIdentifier));

		if (priceListVersionId == null)
		{
			throw new AdempiereException("Missing priceListVersionIdentifier from metasfresh DB")
					.appendParametersToMessage()
					.setParameter("priceListVersionIdentifier", priceListVersionIdentifier);
		}

		final String productPriceIdentifier = jsonProductPriceRequest.getProductPriceIdentifier();
		final JsonUpsertProductPriceRequest jsonUpsertProductPriceRequest = jsonProductPriceRequest.getJsonUpsertProductPriceRequest();
		final SyncAdvise syncAdvise = jsonUpsertProductPriceRequest.getSyncAdvise();

		final ProductPriceId productPriceId = ProductPriceId.ofRepoIdOrNull(Integer.parseInt(productPriceIdentifier));

		final JsonResponseUpsertItem.SyncOutcome syncOutcome;
		final ProductPrice persistedProductPrice;

		if (productPriceId == null)
		{
			if (syncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("productPriceIdentifier")
						.resourceIdentifier(productPriceIdentifier)
						.parentResource(jsonProductPriceRequest).build()
						.setParameter("effectiveSyncAdvise", syncAdvise);
			}
			else
			{
				final CreateProductPrice createProductPriceRequest = syncJsonToCreateProductPrice(priceListVersionId, jsonUpsertProductPriceRequest);
				persistedProductPrice = productPriceRepository.createProductPrice(createProductPriceRequest);
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
			}
		}
		else
		{
			final I_M_ProductPrice record = getRecordOrNull(productPriceId);

			if (record == null)
			{
				if (syncAdvise.isFailIfNotExists())
				{
					throw new AdempiereException("No I_M_ProductPrice record was found for the given ID")
							.appendParametersToMessage()
							.setParameter("M_ProductPrice_ID", productPriceId);
				}
				else
				{
					final CreateProductPrice createProductPriceRequest = syncJsonToCreateProductPrice(priceListVersionId, jsonUpsertProductPriceRequest);
					persistedProductPrice = productPriceRepository.createProductPrice(createProductPriceRequest);
					syncOutcome = JsonResponseUpsertItem.SyncOutcome.CREATED;
				}
			}
			else if (syncAdvise.isLoadReadOnly())
			{
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE;
				return JsonResponseUpsertItem.builder()
						.identifier(priceListVersionIdentifier)
						.metasfreshId(JsonMetasfreshId.of(productPriceId.getRepoId()))
						.syncOutcome(syncOutcome)
						.build();
			}
			else
			{
				final ProductPrice productPriceRequest = syncJsonToProductPrice(
						priceListVersionId,
						productPriceId,
						jsonUpsertProductPriceRequest);
				persistedProductPrice = productPriceRepository.updateProductPrice(productPriceRequest);
				syncOutcome = JsonResponseUpsertItem.SyncOutcome.UPDATED;
			}
		}

		return JsonResponseUpsertItem.builder()
				.identifier(priceListVersionIdentifier)
				.metasfreshId(JsonMetasfreshId.of(persistedProductPrice.getProductId().getRepoId()))
				.syncOutcome(syncOutcome)
				.build();
	}

	@NonNull
	private ProductPrice syncJsonToProductPrice(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final ProductPriceId productPriceId,
			@NonNull final JsonUpsertProductPriceRequest jsonUpsertProductPriceRequest)
	{
		final ProductPrice.ProductPriceBuilder productPriceBuilder = ProductPrice.builder();

		final OrgId orgId = retrieveOrgIdOrDefault(jsonUpsertProductPriceRequest.getOrgCode());
		productPriceBuilder.orgId(orgId);

		productPriceBuilder.priceListVersionId(priceListVersionId);
		productPriceBuilder.productPriceId(productPriceId);

		if (jsonUpsertProductPriceRequest.isProductIdSet())
		{
			final ProductId productId = ProductId.ofRepoId(Integer.parseInt(jsonUpsertProductPriceRequest.getProductId()));
			productPriceBuilder.productId(productId);
		}
		else
		{
			throw new AdempiereException("jsonUpsertProductPriceRequest.getProductId() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonUpsertProductPriceRequest", jsonUpsertProductPriceRequest);
		}

		if (jsonUpsertProductPriceRequest.isPriceLimitSet())
		{
			productPriceBuilder.priceLimit(jsonUpsertProductPriceRequest.getPriceLimit());
		}
		else
		{
			throw new AdempiereException("jsonUpsertProductPriceRequest.getPriceLimit() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonUpsertProductPriceRequest", jsonUpsertProductPriceRequest);
		}

		if (jsonUpsertProductPriceRequest.isPriceListSet())
		{
			productPriceBuilder.priceList(jsonUpsertProductPriceRequest.getPriceList());
		}
		else
		{
			throw new AdempiereException("jsonUpsertProductPriceRequest.getPriceList() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonUpsertProductPriceRequest", jsonUpsertProductPriceRequest);
		}

		if (jsonUpsertProductPriceRequest.isPriceStdSet())
		{
			productPriceBuilder.priceStd(jsonUpsertProductPriceRequest.getPriceStd());
		}
		else
		{
			throw new AdempiereException("jsonUpsertProductPriceRequest.getPriceStd() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonUpsertProductPriceRequest", jsonUpsertProductPriceRequest);
		}

		if (jsonUpsertProductPriceRequest.isTaxCategorySet())
		{
			//	todo florina look for tax category in db by tax InternalName, set both
		}
		else
		{
			throw new AdempiereException("jsonUpsertProductPriceRequest.getTaxCategory() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonUpsertProductPriceRequest", jsonUpsertProductPriceRequest);
		}

		if (jsonUpsertProductPriceRequest.isActiveSet())
		{
			final Boolean isActive = jsonUpsertProductPriceRequest.getActive().equals("true");
			productPriceBuilder.isActive(isActive);
		}
		else
		{
			throw new AdempiereException("jsonUpsertProductPriceRequest.getActive() should never be null at this stage!")
					.appendParametersToMessage()
					.setParameter("jsonUpsertProductPriceRequest", jsonUpsertProductPriceRequest);
		}

		return productPriceBuilder.build();
	}

	@NonNull
	private CreateProductPrice syncJsonToCreateProductPrice(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final JsonUpsertProductPriceRequest jsonUpsertProductPriceRequest
	)
	{
		final CreateProductPrice.CreateProductPriceBuilder productPriceBuilder = CreateProductPrice.builder();

		final OrgId orgId = retrieveOrgIdOrDefault(jsonUpsertProductPriceRequest.getOrgCode());
		productPriceBuilder.orgId(orgId);

		productPriceBuilder.productId(ProductId.ofRepoId(Integer.parseInt(jsonUpsertProductPriceRequest.getProductId())));
		productPriceBuilder.priceListVersionId(priceListVersionId);

		productPriceBuilder.priceLimit(jsonUpsertProductPriceRequest.getPriceLimit());
		productPriceBuilder.priceList(jsonUpsertProductPriceRequest.getPriceList());
		productPriceBuilder.priceStd(jsonUpsertProductPriceRequest.getPriceStd());

		//todo florina tax category id
		//todo florina tax internal name
		productPriceBuilder.isActive(Boolean.valueOf(jsonUpsertProductPriceRequest.getActive()));

		return productPriceBuilder.build();
	}

	@Nullable
	private I_M_ProductPrice getRecordOrNull(@NonNull final ProductPriceId productPriceId)
	{
		return queryBL
				.createQueryBuilder(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_ProductPrice_ID, productPriceId.getRepoId())
				.create()
				.firstOnly(I_M_ProductPrice.class);
	}
}
