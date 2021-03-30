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

package de.metas.purchasecandidate.purchaseordercreation;

import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.IPricingBL;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public class PurchaseOrderPriceCalculator
{
	private final IPricingBL pricingBL;
	PurchaseOrderPricingInfo pricingInfo;

	@Builder
	private PurchaseOrderPriceCalculator(
			@NonNull final IPricingBL pricingBL,
			@NonNull final PurchaseOrderPricingInfo pricingInfo)
	{
		this.pricingBL = pricingBL;
		this.pricingInfo = pricingInfo;
	}

	public IPricingResult calculatePrice()
	{
		final IEditablePricingContext pricingCtx = createPricingContext()
				.setFailIfNotCalculated();
		try
		{
			return pricingBL.calculatePrice(pricingCtx);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("pricingContext", pricingCtx);
		}
	}

	private IEditablePricingContext createPricingContext()
	{
		return pricingBL.createPricingContext()
				.setFailIfNotCalculated()
				.setOrgId(pricingInfo.getOrgId())
				.setProductId(pricingInfo.getProductId())
				.setBPartnerId(pricingInfo.getBpartnerId())
				.setQty(pricingInfo.getQuantity())
				.setConvertPriceToContextUOM(true)
				.setSOTrx(SOTrx.PURCHASE)
				.setCountryId(pricingInfo.getCountryId());
	}

}
