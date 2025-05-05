package de.metas.contracts;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Location;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * This class uses the pricing engine to compute the {@code StdPrice} (standard price) for a given term.
 * <p>
 * Note: this code use uses the pricing engine.
 * It does not care about special contract related pricing rules that might or might not be applied by the pricing engine.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@Builder
public class FlatrateTermPricing
{
	private static final AdMessageKey MSG_FLATRATEBL_PRICE_MISSING_2P = AdMessageKey.of("FlatrateBL_Price_Missing");
	private static final AdMessageKey MSG_FLATRATEBL_PRICE_LIST_MISSING_2P = AdMessageKey.of("FlatrateBL_PriceList_Missing");

	@NonNull
	I_C_Flatrate_Term term;

	@NonNull
	ProductId termRelatedProductId;

	@NonNull
	BigDecimal qty;

	@NonNull
	LocalDate priceDate;

	@NonNull
	@Builder.Default
	SOTrx soTrx = SOTrx.SALES;

	public IPricingResult computeOrThrowEx()
	{
		final PriceListId priceListId = retrievePriceListForTerm();
		return retrievePricingResultUsingPriceList(priceListId);
	}

	private PriceListId retrievePriceListForTerm()
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final PricingSystemId pricingSystemIdToUse = CoalesceUtil.coalesceSuppliersNotNull(
				() -> PricingSystemId.ofRepoIdOrNull(term.getM_PricingSystem_ID()),
				() -> PricingSystemId.ofRepoIdOrNull(term.getC_Flatrate_Conditions().getM_PricingSystem_ID()),
				() -> bpartnerDAO.retrievePricingSystemIdOrNull(BPartnerId.ofRepoId(term.getBill_BPartner_ID()), SOTrx.SALES));

		final BPartnerLocationAndCaptureId dropShipLocationId = ContractLocationHelper.extractDropshipLocationId(term);
		final BPartnerLocationAndCaptureId billLocationId = ContractLocationHelper.extractBillToLocationId(term);

		final BPartnerLocationAndCaptureId bpLocationIdToUse = dropShipLocationId != null ? dropShipLocationId : billLocationId;

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(
				pricingSystemIdToUse,
				bpLocationIdToUse,
				soTrx);
		if (priceListId == null)
		{
			final I_C_BPartner_Location billLocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(billLocationId.getBpartnerLocationId());

			throw new AdempiereException(MSG_FLATRATEBL_PRICE_LIST_MISSING_2P,
										 priceListDAO.getPricingSystemName(pricingSystemIdToUse),
										 billLocationRecord.getName());
		}
		return priceListId;
	}

	private IPricingResult retrievePricingResultUsingPriceList(@NonNull final PriceListId priceListId)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final boolean isSOTrx = true;
		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				term.getAD_Org_ID(),
				termRelatedProductId.getRepoId(),
				term.getBill_BPartner_ID(),
				Services.get(IProductBL.class).getStockUOMId(termRelatedProductId).getRepoId(),
				qty,
				isSOTrx);

		pricingCtx.setPriceDate(priceDate);
		pricingCtx.setPriceListId(priceListId);

		final IPricingResult result = pricingBL.calculatePrice(pricingCtx);
		throwExceptionIfNotCalculated(result);

		return result;
	}

	private void throwExceptionIfNotCalculated(@NonNull final IPricingResult result)
	{
		if (result.isCalculated())
		{
			return;
		}

		final String priceListName = Services.get(IPriceListDAO.class).getPriceListName(result.getPriceListId());
		final String productName = Services.get(IProductBL.class).getProductValueAndName(termRelatedProductId);
		throw new AdempiereException(MSG_FLATRATEBL_PRICE_MISSING_2P, priceListName, productName);
	}
}
