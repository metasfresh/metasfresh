package de.metas.purchasecandidate.grossprofit;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Location;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.money.grossprofit.GrossProfitComputeRequest;
import de.metas.money.grossprofit.GrossProfitPrice;
import de.metas.money.grossprofit.GrossProfitPriceFactory;
import de.metas.order.OrderLine;
import de.metas.order.grossprofit.OrderLineWithGrossProfitPrice;
import de.metas.order.grossprofit.OrderLineWithGrossProfitPriceRepository;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPricingBL;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

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

	@Value
	public static class PurchaseProfitInfoRequest
	{
		@NonNull
		OrderLine salesOrderLine;

		@NonNull
		VendorProductInfo vendorProductInfo;
	}

	public List<PurchaseProfitInfo> createInfos(@NonNull final PurchaseProfitInfoRequest request)
	{
		final Money customerGrossProfitPrice = retrieveCustomerGrossProfitPrice(request);

		final Map<PriceListVersionId, Money> plvId2Price = retrievePurchasePrices(request);

		Check.errorIf(plvId2Price.isEmpty(), "Unable to find pricing information for request={}", request);
		final ImmutableList.Builder<PurchaseProfitInfo> result = ImmutableList.builder();

		for (final Entry<PriceListVersionId, Money> entry : plvId2Price.entrySet())
		{
			final Money purchasePriceActual = entry.getValue();
			final GrossProfitPrice grossProfitPrice = retrieveOurOwnGrossProfitPrice(
					request,
					purchasePriceActual);

			final PurchaseProfitInfo info = PurchaseProfitInfo.builder()
					.purchasePlvId(entry.getKey())
					.purchasePriceActual(purchasePriceActual)
					.customerPriceGrossProfit(customerGrossProfitPrice)
					.priceGrossProfit(grossProfitPrice.compute())
					.build();
			result.add(info);
		}
		return result.build();
	}

	private Money retrieveCustomerGrossProfitPrice(final PurchaseProfitInfoRequest request)
	{
		final OrderLineWithGrossProfitPrice orderLineWithGrossProfitPrice = grossProfitPriceRepo.getForOrderLineId(request.getSalesOrderLine().getId());
		final Money customerGrossProfitPrice = orderLineWithGrossProfitPrice.getGrossProfitPrice();
		return customerGrossProfitPrice;
	}

	private GrossProfitPrice retrieveOurOwnGrossProfitPrice(
			@NonNull final PurchaseProfitInfoRequest request,
			@NonNull final Money purchasePriceActual)
	{
		final VendorProductInfo vendorProductInfo = request.getVendorProductInfo();
		final OrderLine salesOrderLine = request.getSalesOrderLine();

		final GrossProfitComputeRequest grossProfitComputeRequest = GrossProfitComputeRequest
				.builder()
				.baseAmount(purchasePriceActual)
				.bPartnerId(vendorProductInfo.getVendorBPartnerId())
				.paymentTermId(vendorProductInfo.getPaymentTermId())
				.date(salesOrderLine.getDatePromised().toLocalDate())
				.productId(salesOrderLine.getProductId())
				.build();

		final GrossProfitPrice grossProfitPrice = grossProfitPriceFactory.createGrossProfitPrice(grossProfitComputeRequest);
		return grossProfitPrice;
	}

	private Map<PriceListVersionId, Money> retrievePurchasePrices(final PurchaseProfitInfoRequest request)
	{
		final OrderLine salesOrderLine = request.getSalesOrderLine();
		final VendorProductInfo vendorProductInfo = request.getVendorProductInfo();

		final int bPartnerId = vendorProductInfo.getVendorBPartnerId().getRepoId();

		final ImmutableList<Integer> countryIds = Services.get(IBPartnerDAO.class)
				.retrieveBPartnerLocations(bPartnerId)
				.stream()
				.map(I_C_BPartner_Location::getC_Location)
				.map(I_C_Location::getC_Country_ID)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		final boolean soTrx = false;

		final int pricingSystemId = Services.get(IBPartnerDAO.class).retrievePricingSystemId(vendorProductInfo.getVendorBPartnerId(), soTrx);

		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final Quantity orderedQty = salesOrderLine.getOrderedQty();

		final ImmutableMap.Builder<PriceListVersionId, Money> result = ImmutableMap.builder();

		for (final int countryId : countryIds)
		{
			final IEditablePricingContext pricingCtx = pricingBL
					.createInitialContext(
							salesOrderLine.getProductId().getRepoId(),
							bPartnerId,
							orderedQty.getUOM().getC_UOM_ID(),
							orderedQty.getQty(),
							soTrx)
					.setPriceDate(TimeUtil.asTimestamp(salesOrderLine.getDatePromised()))
					.setC_Country_ID(countryId)
					.setM_PricingSystem_ID(pricingSystemId);

			final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
			Check.errorUnless(pricingResult.isCalculated(),
					"Unable to compute a price for the given pricingContext; pricingCtx={}; pricingResult={}",
					pricingCtx, pricingResult);

			final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(pricingResult.getM_PriceList_Version_ID());
			final Currency currency = currencyRepository.getById(CurrencyId.ofRepoId(pricingResult.getC_Currency_ID()));

			result.put(priceListVersionId, Money.of(pricingResult.getPriceStd(), currency));
		}
		return result.build();
	}
}
