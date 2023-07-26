/*
 * #%L
 * de.metas.purchasecandidate.base
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

package de.metas.purchasecandidate.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.pricing.IPricingResult;
import de.metas.purchasecandidate.IPurchaseCandidateBL;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.PurchaseOrderPriceCalculator;
import de.metas.purchasecandidate.purchaseordercreation.PurchaseOrderPricingInfo;
import de.metas.util.Services;
import de.metas.util.lang.Percent;

import java.math.BigDecimal;

public class PurchaseCandidateBL implements IPurchaseCandidateBL
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public void updateCandidatePricingDiscount(final PurchaseCandidate candidate)
	{
		final BPartnerId vendorId = candidate.getVendorId();
		final PurchaseOrderPricingInfo pricingInfo = PurchaseOrderPricingInfo.builder()
				.productId(candidate.getProductId())
				.orgId(candidate.getOrgId())
				.quantity(candidate.getQtyToPurchase())
				.bpartnerId(vendorId)
				.datePromised(candidate.getPurchaseDatePromised())
				.countryId(bpartnerDAO.getDefaultShipToLocationCountryIdOrNull(vendorId))
				.build();
		final IPricingResult priceAndDiscount = getPriceAndDiscount(pricingInfo);

		final BigDecimal enteredPrice = candidate.isManualPrice() ? candidate.getPrice() : priceAndDiscount.getPriceStd();
		final Percent discountPercent = candidate.isManualDiscount() ? candidate.getDiscount() : priceAndDiscount.getDiscount();

		final BigDecimal priceActual = discountPercent.subtractFromBase(enteredPrice, priceAndDiscount.getPrecision().toInt());

		candidate.setPrice(enteredPrice);
		candidate.setPriceInternal(priceAndDiscount.getPriceStd());
		candidate.setPriceEnteredEff(enteredPrice);
		candidate.setDiscount(discountPercent);
		candidate.setDiscountInternal(priceAndDiscount.getDiscount());
		candidate.setDiscountEff(discountPercent);
		candidate.setPriceActual(priceActual);
		candidate.setTaxIncluded(priceAndDiscount.isTaxIncluded());
		candidate.setTaxCategoryId(priceAndDiscount.getTaxCategoryId());
		candidate.setCurrencyId(priceAndDiscount.getCurrencyId());
	}

	private IPricingResult getPriceAndDiscount(final PurchaseOrderPricingInfo pricingInfo)
	{
		return PurchaseOrderPriceCalculator.builder()
				.pricingInfo(pricingInfo)
				.build()
				.calculatePrice();
	}
}
