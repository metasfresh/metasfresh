package de.metas.procurement.base.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPricingBL;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.IPMMPricingBL;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PMMPricingBL implements IPMMPricingBL
{
	private static final Logger logger = LogManager.getLogger(PMMPricingBL.class);

	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);

	@Override
	public void updatePricing(final IPMMPricingAware pricingAware)
	{
		// Get BPartner
		final I_C_BPartner bpartner = pricingAware.getC_BPartner();
		if (bpartner == null || bpartner.getC_BPartner_ID() <= 0)
		{
			throw new AdempiereException("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_C_BPartner_ID + "@");
		}

		// Get Product and UOM
		final I_M_Product product = pricingAware.getM_Product();
		if (product == null)
		{
			throw new AdempiereException("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_M_Product_ID + "@");
		}
		final I_C_UOM uom = pricingAware.getC_UOM();
		if (uom == null)
		{
			throw new AdempiereException("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_C_UOM_ID + "@");
		}

		// Always get the pricing from masterdata.
		// We need it to be there, e.g. to know if the price is incl VAT and which VAT category to use.
		updatePriceFromPricingMasterdata(pricingAware);

		//
		// contract product: override the price amount and currency from the contract, if one is set there
		if (pricingAware.isContractedProduct())
		{
			updatePriceFromContract(pricingAware);
		}
	}

	private void updatePriceFromPricingMasterdata(final IPMMPricingAware pricingAware)
	{
		final SOTrx soTrx = SOTrx.PURCHASE;

		final I_C_BPartner bpartner = pricingAware.getC_BPartner();
		Check.assumeNotNull(bpartner, "bpartner not null"); // shall not happen
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		final I_M_Product product = pricingAware.getM_Product();
		final I_C_UOM uom = pricingAware.getC_UOM();
		final LocalDate date = TimeUtil.asLocalDate(pricingAware.getDate());

		// Pricing system
		final PricingSystemId pricingSystemId = bpartnerDAO.retrievePricingSystemIdOrNullInTrx(bpartnerId, soTrx);
		if (pricingSystemId == null)
		{
			// no term and no pricing system means that we can't figure out the price
			throw new AdempiereException("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_M_PricingSystem_ID + "@ ("
												 + "@C_BPartner_ID@:" + bpartner
												 + ", @IsSOTrx@:" + soTrx
												 + ")");
		}

		// BPartner location -> Country
		final List<I_C_BPartner_Location> shipToLocations = bpartnerDAO.retrieveBPartnerShipToLocations(bpartner);
		if (shipToLocations.isEmpty())
		{
			throw new AdempiereException("@Missing@ @" + org.compiere.model.I_C_BPartner_Location.COLUMNNAME_IsShipTo + "@");
		}
		final CountryId countryId = CountryId.ofRepoId(shipToLocations.get(0).getC_Location().getC_Country_ID());

		//
		// Fetch price from pricing engine
		final BigDecimal qty = pricingAware.getQty();
		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				product.getAD_Org_ID(),
				product.getM_Product_ID(),
				bpartnerId.getRepoId(),
				uom.getC_UOM_ID(),
				qty,
				soTrx.toBoolean());
		pricingCtx.setPricingSystemId(pricingSystemId);
		pricingCtx.setPriceDate(date);
		pricingCtx.setCountryId(countryId);
		pricingCtx.setReferencedObject(pricingAware.getWrappedModel()); // important for ASI pricing

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			throw new AdempiereException("@Missing@ @" + I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID + "@: " + pricingResult);
		}

		// these will be "empty" results, if the price was not calculated
		logger.trace("Updating {} from {}", pricingAware, pricingResult);
		pricingAware.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingResult.getPricingSystemId()));
		pricingAware.setM_PriceList_ID(PriceListId.getRepoId(pricingResult.getPriceListId()));
		pricingAware.setCurrencyId(pricingResult.getCurrencyId());
		pricingAware.setPrice(pricingResult.getPriceStd());
	}

	@Override
	public boolean updatePriceFromContract(final IPMMPricingAware pricingAware)
	{
		final I_C_Flatrate_Term flatrateTerm = pricingAware.getC_Flatrate_Term();
		if (flatrateTerm == null)
		{
			throw new AdempiereException("@Missing@ @" + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "@");
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(pricingAware.getProductId());
		if (productId == null)
		{
			logger.info("Event : {} is missing M_Product_ID!", pricingAware);
			return false;
		}

		final I_C_Flatrate_DataEntry dataEntryForProduct = pricingAware.getC_Flatrate_DataEntry();
		if (dataEntryForProduct == null)
		{
			logger.info("Event is missing C_Flatrate_DataEntry: {}", pricingAware);
			return false;
		}

		final BigDecimal flatrateAmtPerUOM = dataEntryForProduct.getFlatrateAmtPerUOM();

		// in case of null or negative flatrateAmtPerUOM, do not use it for pricing
		if (InterfaceWrapperHelper.isNull(dataEntryForProduct, I_C_Flatrate_DataEntry.COLUMNNAME_FlatrateAmtPerUOM)
				|| flatrateAmtPerUOM.signum() < 0)
		{
			logger.info("Invalid FlatrateAmtPerUOM: {} (event={})", flatrateAmtPerUOM, pricingAware);
			return false;
		}

		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(flatrateTerm.getC_Currency_ID());
		if (currencyId == null)
		{
			logger.info("Missing CurrencyId on flatrateTerm: {}", flatrateTerm);
			return false;
		}

		final UomId flatrateTermUomId = UomId.ofRepoIdOrNull(flatrateTerm.getC_UOM_ID());
		if (flatrateTermUomId == null)
		{
			logger.info("Missing flatrateTermUomId on flatrateTerm: {}", flatrateTerm);
			return false;
		}

		final ProductPrice productPriceInTermUOM = ProductPrice.builder()
				.productId(productId)
				.uomId(flatrateTermUomId)
				.money(Money.of(flatrateAmtPerUOM, currencyId))
				.build();

		final UomId uomIdTo = UomId.ofRepoId(pricingAware.getC_UOM().getC_UOM_ID());

		final CurrencyPrecision currencyPrecision = currencyDAO.getStdPrecision(currencyId);
		final ProductPrice productPriceInTargetUOM = uomConversionBL.convertProductPriceToUom(productPriceInTermUOM, uomIdTo, currencyPrecision);

		pricingAware.setCurrencyId(currencyId);
		pricingAware.setPrice(productPriceInTargetUOM.toBigDecimal());
		return true;
	}

}
