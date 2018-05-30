package de.metas.purchasecandidate.grossprofit;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.money.grossprofit.GrossProfitComputeRequest;
import de.metas.money.grossprofit.GrossProfitPriceFactory;
import de.metas.order.grossprofit.OrderLineWithGrossProfitPriceRepository;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPricingBL;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class PurchaseProfitInfoFactory
{
	// services
	private static final Logger logger = LogManager.getLogger(PurchaseProfitInfoFactory.class);
	private final CurrencyRepository currencyRepository;
	private final OrderLineWithGrossProfitPriceRepository grossProfitPriceRepo;
	private final GrossProfitPriceFactory grossProfitPriceFactory;

	public PurchaseProfitInfoFactory(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final OrderLineWithGrossProfitPriceRepository grossProfitPriceRepo,
			@NonNull final GrossProfitPriceFactory grossProfitPriceFactory)
	{
		this.currencyRepository = currencyRepository;
		this.grossProfitPriceRepo = grossProfitPriceRepo;
		this.grossProfitPriceFactory = grossProfitPriceFactory;
	}

	public List<PurchaseProfitInfo> createInfos(@NonNull final PurchaseProfitInfoRequest request)
	{
		final Money salesNetPrice = retrieveSalesNetPrice(request);

		final Map<PriceListVersionId, Money> purchasePrices = retrievePurchasePrices(request);
		Check.errorIf(purchasePrices.isEmpty(), "Unable to find pricing information for request={}", request);

		final ImmutableList.Builder<PurchaseProfitInfo> result = ImmutableList.builder();
		for (final Entry<PriceListVersionId, Money> entry : purchasePrices.entrySet())
		{
			final Money purchaseGrossPrice = entry.getValue();
			final Money purchaseNetPrice = retrieveOurOwnPurchaseNetPrice(request, purchaseGrossPrice);

			result.add(PurchaseProfitInfo.builder()
					.salesNetPrice(salesNetPrice)
					.purchaseGrossPrice(purchaseGrossPrice)
					.purchaseNetPrice(purchaseNetPrice)
					.build());
		}
		return result.build();
	}

	private Money retrieveSalesNetPrice(final PurchaseProfitInfoRequest request)
	{
		return grossProfitPriceRepo.getProfitBasePrice(request.getSalesOrderLineId());
	}

	private Money retrieveOurOwnPurchaseNetPrice(
			@NonNull final PurchaseProfitInfoRequest request,
			@NonNull final Money purchaseGrossPrice)
	{
		return grossProfitPriceFactory.calculateNetPrice(GrossProfitComputeRequest.builder()
				.baseAmount(purchaseGrossPrice)
				.bPartnerId(request.getVendorId())
				.paymentTermId(request.getPaymentTermId())
				.date(request.getDatePromised().toLocalDate())
				.productId(request.getProductId())
				.build());
	}

	private Map<PriceListVersionId, Money> retrievePurchasePrices(final PurchaseProfitInfoRequest request)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final BPartnerId vendorId = request.getVendorId();
		final Set<Integer> countryIds = bpartnersRepo.retrieveBPartnerLocationCountryIds(vendorId);
		if (countryIds.isEmpty())
		{
			logger.warn("No countries found for {}. Returning empty for {}.", vendorId, request);
			return ImmutableMap.of();
		}

		final boolean soTrx = false;
		final int pricingSystemId = bpartnersRepo.retrievePricingSystemId(vendorId, soTrx);
		final Quantity orderedQty = request.getOrderedQty();

		final ImmutableMap.Builder<PriceListVersionId, Money> result = ImmutableMap.builder();
		for (final int countryId : countryIds)
		{
			final IEditablePricingContext pricingCtx = pricingBL
					.createInitialContext(
							request.getProductId().getRepoId(),
							vendorId.getRepoId(),
							orderedQty.getUOMId(),
							orderedQty.getQty(),
							soTrx)
					.setPriceDate(TimeUtil.asTimestamp(request.getDatePromised()))
					.setC_Country_ID(countryId)
					.setM_PricingSystem_ID(pricingSystemId);

			final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
			if (!pricingResult.isCalculated())
			{
				logger.info("Failed calculating price for vendor's country ({}). Skipped.", pricingCtx);
				continue;
			}

			final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(pricingResult.getM_PriceList_Version_ID());
			final Currency currency = currencyRepository.getById(CurrencyId.ofRepoId(pricingResult.getC_Currency_ID()));

			result.put(priceListVersionId, Money.of(pricingResult.getPriceStd(), currency));
		}
		return result.build();
	}
}
