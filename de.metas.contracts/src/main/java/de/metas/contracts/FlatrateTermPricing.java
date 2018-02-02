package de.metas.contracts;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.i18n.IMsgBL;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
 *
 */
@Value
@Builder
public class FlatrateTermPricing
{
	private static final String MSG_FLATRATEBL_PRICE_MISSING_2P = "FlatrateBL_Price_Missing";
	private static final String MSG_FLATRATEBL_PRICE_LIST_MISSING_2P = "FlatrateBL_PriceList_Missing";

	@NonNull
	I_C_Flatrate_Term term;

	@NonNull
	I_M_Product termRelatedProduct;

	@NonNull
	BigDecimal qty;

	@NonNull
	Timestamp priceDate;

	public IPricingResult computeOrThrowEx()
	{
		final I_M_PriceList priceList = retrievePriceListForTerm();
		final IPricingResult pricingResult = retrievePricingResultUsingPriceList(priceList);

		return pricingResult;
	}

	private I_M_PriceList retrievePriceListForTerm()
	{
		final int pricingSystemIdToUse = Util.firstGreaterThanZero(term.getM_PricingSystem_ID(), term.getC_Flatrate_Conditions().getM_PricingSystem_ID());
		final I_C_BPartner_Location bpLocationToUse = Util.coalesceSuppliers(term::getDropShip_Location, term::getBill_Location);

		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		final I_M_PriceList priceList = priceListDAO.retrievePriceListByPricingSyst(pricingSystemIdToUse, bpLocationToUse, true);
		if (priceList == null)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(term);
			final String trxName = InterfaceWrapperHelper.getTrxName(term);
			throw new AdempiereException(
					Services.get(IMsgBL.class).getMsg(ctx, MSG_FLATRATEBL_PRICE_LIST_MISSING_2P,
							new Object[] {
									InterfaceWrapperHelper.create(ctx, pricingSystemIdToUse, I_M_PricingSystem.class, trxName).getName(),
									term.getBill_Location().getName() }));
		}
		return priceList;
	}

	private IPricingResult retrievePricingResultUsingPriceList(@NonNull final I_M_PriceList priceList)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final boolean isSOTrx = true;
		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				termRelatedProduct.getM_Product_ID(),
				term.getBill_BPartner_ID(),
				termRelatedProduct.getC_UOM_ID(),
				qty,
				isSOTrx);

		pricingCtx.setPriceDate(priceDate);
		pricingCtx.setM_PriceList_ID(priceList.getM_PriceList_ID());

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

		final I_M_PriceList priceList = load(result.getM_PriceList_ID(), I_M_PriceList.class);
		throw new AdempiereException(
				Services.get(IMsgBL.class).getMsg(Env.getCtx(), MSG_FLATRATEBL_PRICE_MISSING_2P,
						new Object[] { priceList.getName(), termRelatedProduct.getValue() }));
	}
}
