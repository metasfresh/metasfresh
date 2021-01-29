/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand;

import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

class ProjectQuotationPriceCalculator
{
	private final IPricingBL pricingBL;
	private final ProjectQuotationPricingInfo pricingInfo;

	@Builder
	private ProjectQuotationPriceCalculator(
			@NonNull final IPricingBL pricingBL,
			@NonNull final ProjectQuotationPricingInfo pricingInfo)
	{
		this.pricingBL = pricingBL;
		this.pricingInfo = pricingInfo;
	}

	public CurrencyId getCurrencyId()
	{
		return pricingInfo.getCurrencyId();
	}

	public IPricingResult calculatePrice(@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		final IEditablePricingContext pricingCtx = createPricingContext(costCollector)
				.setFailIfNotCalculated();

		try
		{
			return pricingBL.calculatePrice(pricingCtx);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("pricingInfo", pricingInfo)
					.setParameter("pricingContext", pricingCtx)
					.setParameter("costCollector", costCollector);
		}
	}

	private IEditablePricingContext createPricingContext(
			@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		return pricingBL.createPricingContext()
				.setFailIfNotCalculated()
				.setOrgId(pricingInfo.getOrgId())
				.setProductId(costCollector.getProductId())
				.setBPartnerId(pricingInfo.getShipBPartnerId())
				.setQty(costCollector.getQtyReservedOrConsumed())
				.setConvertPriceToContextUOM(true)
				.setSOTrx(SOTrx.SALES)
				.setPriceDate(pricingInfo.getDatePromised().toLocalDate())
				.setPricingSystemId(pricingInfo.getPricingSystemId())
				.setPriceListId(pricingInfo.getPriceListId())
				.setPriceListVersionId(pricingInfo.getPriceListVersionId())
				.setCountryId(pricingInfo.getCountryId())
				.setCurrencyId(pricingInfo.getCurrencyId());
	}

}
