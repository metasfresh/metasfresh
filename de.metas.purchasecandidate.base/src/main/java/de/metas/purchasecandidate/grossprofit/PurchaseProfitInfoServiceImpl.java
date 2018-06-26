package de.metas.purchasecandidate.grossprofit;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.lang.Percent;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.OrderAndLineId;
import de.metas.order.grossprofit.OrderLineWithGrossProfitPriceRepository;
import de.metas.payment.api.IPaymentTermRepository;
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
	private final CurrencyRepository currencyRepo;
	private final MoneyService moneyService;
	private final OrderLineWithGrossProfitPriceRepository grossProfitPriceRepo;
	//
	private final IPricingConditionsService pricingConditionsService = Services.get(IPricingConditionsService.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);

	public PurchaseProfitInfoServiceImpl(
			@NonNull final CurrencyRepository currencyRepo,
			@NonNull final MoneyService moneyService,
			@NonNull final OrderLineWithGrossProfitPriceRepository grossProfitPriceRepo)
	{
		this.currencyRepo = currencyRepo;
		this.moneyService = moneyService;
		this.grossProfitPriceRepo = grossProfitPriceRepo;
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
		final Set<OrderAndLineId> salesOrderAndLineIds = request.getSalesOrderAndLineIds();
		final Quantity qtyToPurchase = request.getQtyToPurchase();
		final VendorProductInfo vendorProductInfo = request.getVendorProductInfo();

		final BPartnerId vendorId = vendorProductInfo.getVendorId();
		final Percent vendorFlatDiscount = vendorProductInfo.getVendorFlatDiscount();
		final PricingConditionsBreak vendorPricingConditionsBreak = vendorProductInfo.getPricingConditionsBreakOrNull(qtyToPurchase);

		//
		// Compute price (gross and net) from pricing conditions break
		final Money purchaseBasePrice;
		Money purchaseNetPrice;
		if (vendorPricingConditionsBreak != null)
		{
			final PricingConditionsResult vendorPricingConditionsResult = pricingConditionsService.calculatePricingConditions(CalculatePricingConditionsRequest.builder()
					.forcePricingConditionsBreak(vendorPricingConditionsBreak)
					.bpartnerFlatDiscount(vendorFlatDiscount)
					.pricingCtx(createPricingContext(vendorPricingConditionsBreak, vendorId))
					.build())
					.orElse(null);
			if(vendorPricingConditionsResult == null)
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

			final Currency currency = currencyRepo.getById(currencyId);
			purchaseBasePrice = Money.of(purchaseBasePriceValue, currency);

			purchaseNetPrice = purchaseBasePrice.subtract(vendorPricingConditionsBreak.getDiscount());
		}
		else
		{
			purchaseBasePrice = null;
			purchaseNetPrice = null;
		}

		//
		// Subtract paymentTerm discount if any
		if (purchaseNetPrice != null
				&& purchaseNetPrice.signum() != 0
				&& vendorPricingConditionsBreak.getPaymentTermId() != null)
		{
			final Percent discount = paymentTermRepo.getPaymentTermDiscount(vendorPricingConditionsBreak.getPaymentTermId());
			purchaseNetPrice = purchaseNetPrice.subtract(discount);

		}

		//
		return PurchaseProfitInfo.builder()
				.salesNetPrice(grossProfitPriceRepo.getProfitMinBasePrice(salesOrderAndLineIds))
				.purchaseGrossPrice(purchaseBasePrice)
				.purchaseNetPrice(purchaseNetPrice)
				.build();
	}

	private IPricingContext createPricingContext(final PricingConditionsBreak pricingConditionsBreak, final BPartnerId vendorId)
	{
		final int countryId = bpartnersRepo.getDefaultShipToLocationCountryId(vendorId);

		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		final ProductId productId = pricingConditionsBreak.getMatchCriteria().getProductId();
		pricingCtx.setM_Product_ID(ProductId.toRepoId(productId));
		pricingCtx.setQty(BigDecimal.ONE);
		pricingCtx.setBPartnerId(vendorId);
		pricingCtx.setC_Country_ID(countryId);
		pricingCtx.setSOTrx(SOTrx.PURCHASE.toBoolean());

		return pricingCtx;
	}

	@Override
	public PurchaseProfitInfo convertToCurrency(@NonNull final PurchaseProfitInfo profitInfo, @NonNull final Currency currencyTo)
	{
		return profitInfo.toBuilder()
				.salesNetPrice(convertToCurrency(profitInfo.getSalesNetPrice(), currencyTo))
				.purchaseNetPrice(convertToCurrency(profitInfo.getPurchaseNetPrice(), currencyTo))
				.purchaseGrossPrice(convertToCurrency(profitInfo.getPurchaseGrossPrice(), currencyTo))
				.build();
	}

	private final Optional<Money> convertToCurrency(final Optional<Money> optionalPrice, final Currency currencyTo)
	{
		return optionalPrice.map(price -> moneyService.convertMoneyToCurrency(price, currencyTo));
	}
}
