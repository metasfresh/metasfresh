/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.commission.commissioninstance.services;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static java.math.BigDecimal.ONE;

@Service
public class CommissionPointsService
{
	public Optional<Money> getCommissionPointsValue(@NonNull final CommissionPoints commissionPoints,
			@NonNull final FlatrateTermId flatrateTermId,
			@NonNull final LocalDate priceDate)
	{
		final IPricingResult pricingResult = calculateCommissionPointPriceFor(flatrateTermId, priceDate);

		if (!pricingResult.isCalculated())
		{
			return Optional.empty();
		}

		final Money customerTradeMarginAmount = Money.of(
				pricingResult.getPriceStd().multiply(commissionPoints.getPoints()),
				pricingResult.getCurrencyId());

		return Optional.of(customerTradeMarginAmount);
	}

	private IPricingResult calculateCommissionPointPriceFor(
			@NonNull final FlatrateTermId flatrateTermId,
			@NonNull final LocalDate requestedDate)
	{
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.retrieveTerm(flatrateTermId);

		final BPartnerLocationAndCaptureId commissionToLocationId = ContractLocationHelper.extractBillToLocationId(flatrateTerm);

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(flatrateTerm.getBill_BPartner_ID());

		final PricingSystemId pricingSystemId = bPartnerDAO.retrievePricingSystemIdOrNull(bPartnerId, SOTrx.PURCHASE);

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, commissionToLocationId, SOTrx.PURCHASE);

		final ProductId commissionProductId = ProductId.ofRepoId(flatrateTerm.getM_Product_ID());

		final IEditablePricingContext pricingContext = pricingBL
				.createInitialContext(
						OrgId.ofRepoId(flatrateTerm.getAD_Org_ID()),
						commissionProductId,
						bPartnerId,
						Quantitys.of(ONE, commissionProductId),
						SOTrx.PURCHASE)
				.setPriceListId(priceListId)
				.setPriceDate(requestedDate);

		return pricingBL.calculatePrice(pricingContext);
	}
}
