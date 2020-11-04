package de.metas.rest_api.bpartner_pricelist.command;

import java.time.LocalDate;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.PriceListsCollection;
import de.metas.product.ProductId;
import de.metas.rest_api.bpartner_pricelist.BpartnerPriceListServicesFacade;
import de.metas.rest_api.bpartner_pricelist.response.JsonResponsePrice;
import de.metas.rest_api.bpartner_pricelist.response.JsonResponsePriceList;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.tax.api.TaxCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public class GetPriceListCommand
{
	// services
	private final BpartnerPriceListServicesFacade servicesFacade;

	// parameters
	private final IdentifierString bpartnerIdentifier;
	private final SOTrx soTrx;
	private final String countryCode;
	private final LocalDate date;

	// context/state
	private CountryId countryId;
	private BPartnerId bpartnerId;
	private PricingSystemId pricingSystemId;
	private PriceListId priceListId;

	@Builder(buildMethodName = "_build")
	private GetPriceListCommand(
			@NonNull final BpartnerPriceListServicesFacade servicesFacade,
			//
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final SOTrx soTrx,
			@NonNull final String countryCode,
			@NonNull final LocalDate date)
	{
		this.servicesFacade = servicesFacade;

		this.bpartnerIdentifier = bpartnerIdentifier;
		this.soTrx = soTrx;
		this.countryCode = countryCode;
		this.date = date;
	}

	public static class GetPriceListCommandBuilder
	{
		public JsonResponsePriceList execute()
		{
			return _build().execute();
		}
	}

	private AdempiereException populateWithParameters(final Throwable ex)
	{
		return AdempiereException.wrapIfNeeded(ex)
				// Command parameters:
				.setParameter("bpartnerIdentifier", bpartnerIdentifier)
				.setParameter("soTrx", soTrx)
				.setParameter("countryCode", countryCode)
				.setParameter("date", date)
				// Command status/context:
				.setParameter("countryId", countryId)
				.setParameter("bpartnerId", bpartnerId)
				.setParameter("pricingSystemId", pricingSystemId)
				.setParameter("priceListId", priceListId);
	}

	private JsonResponsePriceList execute()
	{
		try
		{
			return execute0();
		}
		catch (final Exception ex)
		{
			throw populateWithParameters(ex);
		}
	}

	private JsonResponsePriceList execute0()
	{
		countryId = servicesFacade.getCountryIdByCountryCode(countryCode);

		bpartnerId = servicesFacade.getBPartnerId(bpartnerIdentifier).orElse(null);
		if (bpartnerId == null)
		{
			throw new AdempiereException("No BPartner found for " + bpartnerIdentifier);
		}

		pricingSystemId = servicesFacade.getPricingSystemId(bpartnerId, soTrx).orElse(null);
		if (pricingSystemId == null)
		{
			throw new AdempiereException("No pricing system defined for " + bpartnerId);
		}

		final PriceListsCollection priceLists = servicesFacade.getPriceListsCollection(pricingSystemId);
		final I_M_PriceList priceList = priceLists.getPriceList(countryId, soTrx).orElse(null);
		if (priceList == null)
		{
			throw new AdempiereException("No PriceList found for given country and SOTrx");
		}

		final CurrencyId currencyId = CurrencyId.ofRepoId(priceList.getC_Currency_ID());
		final CurrencyCode currencyCode = servicesFacade.getCurrencyCodeById(currencyId);

		priceListId = PriceListId.ofRepoId(priceList.getM_PriceList_ID());
		final PriceListVersionId priceListVersionId = servicesFacade.getPriceListVersionId(priceListId, TimeUtil.asZonedDateTime(date, SystemTime.zoneId()));

		final ImmutableList<I_M_ProductPrice> productPriceRecords = servicesFacade.getProductPrices(priceListVersionId);

		final ImmutableSet<ProductId> productIds = productPriceRecords.stream()
				.map(productPrice -> ProductId.ofRepoId(productPrice.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<ProductId, String> productValues = servicesFacade.getProductValues(productIds);

		final ImmutableList<JsonResponsePrice> prices = productPriceRecords.stream()
				.map(productPrice -> toJsonResponsePrice(productPrice, productValues, currencyCode))
				.collect(ImmutableList.toImmutableList());

		return JsonResponsePriceList.builder()
				.prices(prices)
				.build();
	}

	private JsonResponsePrice toJsonResponsePrice(
			@NonNull final I_M_ProductPrice productPrice,
			@NonNull final ImmutableMap<ProductId, String> productValues,
			@NonNull final CurrencyCode currencyCode)
	{
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());

		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoId(productPrice.getC_TaxCategory_ID());

		return JsonResponsePrice.builder()
				.productId(productId)
				.productCode(productValues.get(productId))
				.price(productPrice.getPriceStd())
				.currencyCode(currencyCode)
				.taxCategoryId(taxCategoryId)
				.build();
	}

}
