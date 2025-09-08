package de.metas.rest_api.bpartner_pricelist;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.productprice.ProductPrice;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.PriceListsCollection;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.IdentifierString.Type;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Facade of all services on which this REST endpoints depends
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
public class BpartnerPriceListServicesFacade
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final ICountryDAO countriesRepo = Services.get(ICountryDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);

	private final ProductPriceRepository productPriceRepository;

	public BpartnerPriceListServicesFacade(@NonNull final ProductPriceRepository productPriceRepository)
	{
		this.productPriceRepository = productPriceRepository;
	}

	public CountryId getCountryIdByCountryCode(final String countryCode)
	{
		return countriesRepo.getCountryIdByCountryCode(countryCode);
	}

	public Optional<PricingSystemId> getPricingSystemId(final BPartnerId bpartnerId, final SOTrx soTrx)
	{
		final PricingSystemId pricingSystemId = bpartnersRepo.retrievePricingSystemIdOrNull(bpartnerId, soTrx);
		return Optional.ofNullable(pricingSystemId);
	}

	public PriceListsCollection getPriceListsCollection(final PricingSystemId pricingSystemId)
	{
		return priceListsRepo.retrievePriceListsCollectionByPricingSystemId(pricingSystemId);
	}

	public CurrencyCode getCurrencyCodeById(final CurrencyId currencyId)
	{
		return currenciesRepo.getCurrencyCodeById(currencyId);
	}

	public PriceListVersionId getPriceListVersionId(final PriceListId priceListId, final ZonedDateTime date)
	{
		return priceListsRepo.retrievePriceListVersionId(priceListId, date);
	}

	public I_M_PriceList_Version getPriceListVersionOrNull(final PriceListId priceListId, final ZonedDateTime date, final Boolean processedPLVFiltering)
	{
		return priceListsRepo.retrievePriceListVersionOrNull(priceListId, date, processedPLVFiltering);
	}

	public ImmutableList<ProductPrice> getProductPrices(@NonNull final PriceListVersionId priceListVersionId)
	{
		return priceListsRepo.retrieveProductPrices(priceListVersionId)
				.map(productPriceRepository::toProductPrice)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableList<I_M_ProductPrice> getProductPricesByPLVAndProduct(@NonNull final PriceListVersionId priceListVersionId, @NonNull final ProductId productId)
	{
		return priceListsRepo.retrieveProductPrices(priceListVersionId, productId);
	}

	@NonNull
	public ImmutableMap<ProductId, String> getProductValues(final ImmutableSet<ProductId> productIds)
	{
		return productsService.getProductValues(productIds);
	}

	@NonNull
	public String getProductValue(@NonNull final ProductId productId)
	{
		return productsService.getProductValue(productId);
	}

	// TODO move this method to de.metas.bpartner.service.IBPartnerDAO since it has nothing to do with price list
	// 		TODO: IdentifierString must also be moved to the module containing IBPartnerDAO
	public Optional<BPartnerId> getBPartnerId(final IdentifierString bpartnerIdentifier, final OrgId orgId)
	{
		final BPartnerQuery query = createBPartnerQuery(bpartnerIdentifier, orgId);
		return bpartnersRepo.retrieveBPartnerIdBy(query);
	}

	private static BPartnerQuery createBPartnerQuery(@NonNull final IdentifierString bpartnerIdentifier, final OrgId orgId)
	{
		final Type type = bpartnerIdentifier.getType();
		final BPartnerQuery.BPartnerQueryBuilder builder = BPartnerQuery.builder();
		if (orgId != null)
		{
			builder.onlyOrgId(orgId);
		}
		if (Type.METASFRESH_ID.equals(type))
		{
			return builder
					.bPartnerId(bpartnerIdentifier.asMetasfreshId(BPartnerId::ofRepoId))
					.build();
		}
		else if (Type.EXTERNAL_ID.equals(type))
		{
			return builder
					.externalId(bpartnerIdentifier.asExternalId())
					.build();
		}
		else if (Type.VALUE.equals(type))
		{
			return builder
					.bpartnerValue(bpartnerIdentifier.asValue())
					.build();
		}
		else if (Type.GLN.equals(type))
		{
			return builder
					.gln(bpartnerIdentifier.asGLN())
					.build();
		}
		else
		{
			throw new AdempiereException("Invalid bpartnerIdentifier: " + bpartnerIdentifier);
		}
	}
}
