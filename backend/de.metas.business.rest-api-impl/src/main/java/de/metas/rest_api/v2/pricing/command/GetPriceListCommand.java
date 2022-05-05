/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.pricing.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.pricing.v2.productprice.JsonConverters;
import de.metas.common.pricing.v2.productprice.JsonProductPrice;
import de.metas.common.pricing.v2.productprice.JsonResponsePrice;
import de.metas.common.pricing.v2.productprice.JsonResponsePriceList;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.lang.SOTrx;
import de.metas.location.CountryCode;
import de.metas.location.CountryId;
import de.metas.location.ICountryCodeFactory;
import de.metas.location.ICountryDAO;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.PriceListsCollection;
import de.metas.product.ProductId;
import de.metas.rest_api.bpartner_pricelist.BpartnerPriceListServicesFacade;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.product.ProductRestService;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

public class GetPriceListCommand
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	// services
	private final ProductRestService productRestService;
	private final JsonRetrieverService jsonRetrieverService;
	private final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade;

	// parameters
	private final ExternalIdentifier bpartnerIdentifier;
	private final ExternalIdentifier productIdentifier;
	private final LocalDate targetDate;

	@Builder
	public GetPriceListCommand(
			@NonNull final ProductRestService productRestService,
			@NonNull final JsonRetrieverService jsonRetrieverService,
			@NonNull final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final ExternalIdentifier productIdentifier,
			@NonNull final LocalDate targetDate)
	{
		this.productRestService = productRestService;
		this.jsonRetrieverService = jsonRetrieverService;
		this.bpartnerPriceListServicesFacade = bpartnerPriceListServicesFacade;
		this.bpartnerIdentifier = bpartnerIdentifier;
		this.productIdentifier = productIdentifier;
		this.targetDate = targetDate;
	}

	public static class GetPriceListCommandBuilder
	{
		public JsonResponsePriceList execute()
		{
			return build().execute();
		}
	}

	public JsonResponsePriceList execute()
	{
		final BPartnerId bpartnerId = jsonRetrieverService.resolveBPartnerExternalIdentifier(bpartnerIdentifier, Env.getOrgId())
				.orElseThrow(() -> new InvalidIdentifierException("No BPartner found for identifier")
						.appendParametersToMessage()
						.setParameter("ExternalIdentifier", bpartnerIdentifier));

		final ProductId productId = productRestService.resolveProductExternalIdentifier(productIdentifier, Env.getOrgId())
				.orElseThrow(() -> new InvalidIdentifierException("Fail to resolve product external identifier")
						.appendParametersToMessage()
						.setParameter("ExternalIdentifier", productIdentifier));

		final Set<PricingSystemId> assignedBPPricingSystemIds = getBPartnerPricingSystemIds(bpartnerId);

		if (assignedBPPricingSystemIds.isEmpty())
		{
			throw new AdempiereException("BPartner has no PricingSystemId assigned")
					.appendParametersToMessage()
					.setParameter("BPartnerId", bpartnerId);
		}

		final ZonedDateTime zonedDateTime = TimeUtil.asZonedDateTime(targetDate, SystemTime.zoneId());
		Check.assumeNotNull(zonedDateTime, "zonedDateTime should not be null at this stage");

		final String productValue = bpartnerPriceListServicesFacade.getProductValue(productId);

		final ImmutableList.Builder<JsonResponsePrice> jsonResponsePriceCollector = ImmutableList.builder();

		for (final PricingSystemId pricingSystemId : assignedBPPricingSystemIds)
		{
			final PriceListsCollection priceListsCollection = priceListDAO.retrievePriceListsCollectionByPricingSystemId(pricingSystemId);

			for (final I_M_PriceList priceList : priceListsCollection.getPriceList())
			{
				final CurrencyCode priceListCurrencyCode = bpartnerPriceListServicesFacade.getCurrencyCodeById(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));

				final SOTrx soTrx = SOTrx.ofBoolean(priceList.isSOPriceList());

				final CountryId countryId = CountryId.ofRepoId(priceList.getC_Country_ID());

				final PriceListVersionId priceListVersionId = bpartnerPriceListServicesFacade.getPriceListVersionId(PriceListId.ofRepoId(priceList.getM_PriceList_ID()), zonedDateTime);

				final CountryCode countryCode = getCountryCode(countryId);

				bpartnerPriceListServicesFacade.getProductPricesByPLVAndProduct(priceListVersionId, productId)
						.stream()
						.map(productPrice -> JsonConverters.toJsonResponsePrice(JsonProductPrice.of(productId.getRepoId(), productPrice.getC_TaxCategory_ID(), productPrice.getPriceStd()),
																				productValue,
																				priceListCurrencyCode.toThreeLetterCode(),
																				countryCode.toString(),
																				soTrx.toBoolean()))
						.forEach(jsonResponsePriceCollector::add);
			}
		}

		return JsonResponsePriceList.builder()
				.prices(jsonResponsePriceCollector.build())
				.build();
	}

	@NonNull
	private Set<PricingSystemId> getBPartnerPricingSystemIds(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bPartner = bpartnerDAO.getById(bpartnerId);

		final ImmutableSet.Builder<PricingSystemId> pricingSystemIds = ImmutableSet.builder();

		Optional.ofNullable(PricingSystemId.ofRepoIdOrNull(bPartner.getM_PricingSystem_ID()))
				.ifPresent(pricingSystemIds::add);

		Optional.ofNullable(PricingSystemId.ofRepoIdOrNull(bPartner.getPO_PricingSystem_ID()))
				.ifPresent(pricingSystemIds::add);

		return pricingSystemIds.build();
	}

	@NonNull
	private CountryCode getCountryCode(@NonNull final CountryId countryId)
	{
		final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
		final ICountryCodeFactory countryCodeFactory = Services.get(ICountryCodeFactory.class);

		final I_C_Country country = countryDAO.getById(countryId);
		return countryCodeFactory.getCountryCodeByAlpha2(country.getCountryCode());
	}
}
