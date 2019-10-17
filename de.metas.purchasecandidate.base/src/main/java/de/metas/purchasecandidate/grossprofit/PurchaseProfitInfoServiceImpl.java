package de.metas.purchasecandidate.grossprofit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.money.grossprofit.ProfitPriceActualFactory;
import de.metas.order.OrderAndLineId;
import de.metas.order.grossprofit.OrderLineWithGrossProfitPriceRepository;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.IPricingConditionsService;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
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
public class PurchaseProfitInfoServiceImpl implements PurchaseProfitInfoService
{
	// services
	private static final Logger logger = LogManager.getLogger(PurchaseProfitInfoService.class);
	private final MoneyService moneyService;
	private final OrderLineWithGrossProfitPriceRepository grossProfitPriceRepo;
	//
	private final IPricingConditionsService pricingConditionsService = Services.get(IPricingConditionsService.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ProfitPriceActualFactory profitPriceActualFactory;

	public PurchaseProfitInfoServiceImpl(
			@NonNull final MoneyService moneyService,
			@NonNull final OrderLineWithGrossProfitPriceRepository grossProfitPriceRepo,
			@NonNull final ProfitPriceActualFactory profitPriceActualFactory)
	{
		this.moneyService = moneyService;
		this.grossProfitPriceRepo = grossProfitPriceRepo;
		this.profitPriceActualFactory = profitPriceActualFactory;
	}

	@Override
	public PurchaseProfitInfo calculateNoFail(final PurchaseProfitInfoRequest request)
	{
		try
		{
			return calculate(request);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed computing purchase profit info for {}. Returning null.", request, ex);
			return null;
		}
	}

	@Override
	public PurchaseProfitInfo calculate(@NonNull final PurchaseProfitInfoRequest request)
	{
		final LocalDate date = LocalDate.now();

		final Set<OrderAndLineId> salesOrderAndLineIds = request.getSalesOrderAndLineIds();
		final Quantity qtyToPurchase = request.getQtyToPurchase();
		final VendorProductInfo vendorProductInfo = request.getVendorProductInfo();

		final BPartnerId vendorId = vendorProductInfo.getVendorId();
		final Percent vendorFlatDiscount = vendorProductInfo.getVendorFlatDiscount();

		final PricingConditionsBreak vendorPricingConditionsBreak = vendorProductInfo.getPricingConditionsBreakOrNull(qtyToPurchase);
		if (vendorPricingConditionsBreak == null)
		{
			return null;
		}

		// Compute price (gross and net) from pricing conditions break
		final CalculatePricingConditionsRequest calculatePricingConditionsRequest = CalculatePricingConditionsRequest.builder()
				.forcePricingConditionsBreak(vendorPricingConditionsBreak) // maybe be null
				.pricingConditionsId(vendorProductInfo.getPricingConditions().getId())
				.bpartnerFlatDiscount(vendorFlatDiscount)
				.pricingCtx(createPricingContext(vendorProductInfo.getProductId(), vendorId, date))
				.build();

		final PricingConditionsResult vendorPricingConditionsResult = pricingConditionsService
				.calculatePricingConditions(calculatePricingConditionsRequest)
				.orElse(null);
		if (vendorPricingConditionsResult == null)
		{
			return null;
		}

		final BigDecimal purchaseBasePriceValue = vendorPricingConditionsResult.getPriceStdOverride();
		final CurrencyId currencyId = vendorPricingConditionsResult.getCurrencyId();
		if (purchaseBasePriceValue == null || currencyId == null)
		{
			if (currencyId == null && purchaseBasePriceValue != null)
			{
				logger.warn("Returning null because currency is not set, even though price is set: {}", vendorPricingConditionsResult);
			}
			return null;
		}

		final Money purchaseBasePrice = Money.of(purchaseBasePriceValue, currencyId);

		final Money purchaseNetPrice = moneyService.subtractPercent(vendorPricingConditionsResult.getDiscount(), purchaseBasePrice);

		final CalculateProfitPriceActualRequest calculateProfitPriceActualRequest = CalculateProfitPriceActualRequest.builder()
				.bPartnerId(vendorId)
				.productId(vendorProductInfo.getProductId())
				.date(date)
				.baseAmount(purchaseNetPrice)
				.paymentTermId(vendorPricingConditionsBreak.getDerivedPaymentTermIdOrNull())
				.quantity(qtyToPurchase)
				.build();
		final Money purchaseNetPriceWithExpectedBenefits = profitPriceActualFactory.calculateProfitPriceActual(calculateProfitPriceActualRequest);

		//
		return PurchaseProfitInfo.builder()
				.profitSalesPriceActual(grossProfitPriceRepo.getProfitMinBasePrice(salesOrderAndLineIds))
				.purchasePriceActual(purchaseBasePrice)
				.profitPurchasePriceActual(purchaseNetPriceWithExpectedBenefits)
				.build();
	}

	private IPricingContext createPricingContext(
			@NonNull final ProductId productId,
			@NonNull final BPartnerId vendorId,
			@NonNull final LocalDate date)
	{
		final CountryId countryId = bpartnersRepo.getDefaultShipToLocationCountryIdOrNull(vendorId);

		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setProductId(productId);
		pricingCtx.setQty(BigDecimal.ONE);
		pricingCtx.setBPartnerId(vendorId);
		pricingCtx.setCountryId(countryId);
		pricingCtx.setSOTrx(SOTrx.PURCHASE);
		pricingCtx.setPriceDate(date);

		return pricingCtx;
	}

	@Override
	public PurchaseProfitInfo convertToCurrency(@NonNull final PurchaseProfitInfo profitInfo, @NonNull final CurrencyId currencyIdTo)
	{
		return profitInfo.toBuilder()
				.profitSalesPriceActual(convertToCurrency(profitInfo.getProfitSalesPriceActual(), currencyIdTo))
				.profitPurchasePriceActual(convertToCurrency(profitInfo.getProfitPurchasePriceActual(), currencyIdTo))
				.purchasePriceActual(convertToCurrency(profitInfo.getPurchasePriceActual(), currencyIdTo))
				.build();
	}

	private final Optional<Money> convertToCurrency(final Optional<Money> optionalPrice, final CurrencyId currencyIdTo)
	{
		return optionalPrice.map(price -> moneyService.convertMoneyToCurrency(price, currencyIdTo));
	}
}
